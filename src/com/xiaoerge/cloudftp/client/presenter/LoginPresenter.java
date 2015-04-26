package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.AuthService;
import com.xiaoerge.cloudftp.client.AuthServiceAsync;
import com.xiaoerge.cloudftp.client.event.background.SavePublicKeyEvent;
import com.xiaoerge.cloudftp.client.event.foreground.CdEvent;
import com.xiaoerge.cloudftp.client.shared.CommonUtil;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginPresenter implements Presenter {

    private static Logger logger = Logger.getLogger(LoginPresenter.class.getName());

    public interface Display extends Presenter.CommonDisplay {
        Button getLoginButton();
        TextBox getHostnameTextBox();
        PasswordTextBox getPasswordTextBox();
        TextBox getPortTf();
        Widget asWidget();

        @Override
        Label getStatusLb();

        @Override
        Label getProgressLb();
    }

    private final AuthServiceAsync authServiceAsync;
    private final HandlerManager eventBus;
    private final Display display;

    public LoginPresenter(AuthServiceAsync authServiceAsync, HandlerManager eventBus, Display display) {
        this.authServiceAsync = authServiceAsync;
        this.eventBus = eventBus;
        this.display = display;
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        widgets.clear();
        widgets.add(display.asWidget());
    }

    private void bind() {
        display.getPasswordTextBox().addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (!display.getPasswordTextBox().getText().isEmpty() &&
                        !display.getHostnameTextBox().getText().isEmpty() &&
                        !display.getPortTf().getText().isEmpty() &&
                        event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    doLogin();
                }
            }
        });

        display.getLoginButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!display.getPasswordTextBox().getText().isEmpty() &&
                        !display.getHostnameTextBox().getText().isEmpty() &&
                        !display.getPortTf().getText().isEmpty()) {
                    doLogin();
                }
            }
        });
    }

    private void doLogin() {

        XsrfTokenServiceAsync xsrf = GWT.create(XsrfTokenService.class);
        ((ServiceDefTarget)xsrf).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
        xsrf.getNewXsrfToken(new AsyncCallback<XsrfToken>() {

            public void onSuccess(XsrfToken token) {
                ((HasRpcToken) authServiceAsync).setRpcToken(token);

                CommonUtil.showLoadingAnimation(display.getStatusLb());

                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "sign in error");
                        logger.log(Level.SEVERE, caught.getMessage());
                    }

                    public void onSuccess(Boolean result) {

                        CommonUtil.hideLoadingAnimation(display.getStatusLb());

                        if (result) {

                            display.getStatusLb().setText("Success");
                            display.getStatusLb().setStyleName("alert alert-success");
                            logger.log(Level.INFO, "Log in success");

                            //eventBus.fireEvent(new SavePublicKeyEvent(result));
                            eventBus.fireEvent(new CdEvent());
                        }
                        else {
                            display.getStatusLb().setText("Failure");
                            display.getStatusLb().setStyleName("alert alert-danger");
                            logger.log(Level.INFO, "Log in failure");
                        }
                    }
                };

                synchronized (this) {
                    authServiceAsync.authenticate(display.getHostnameTextBox().getText(),
                            display.getPasswordTextBox().getText().getBytes(),
                            Integer.parseInt(display.getPortTf().getText()), callback);
                }
            }

            public void onFailure(Throwable caught) {
                try {
                    throw caught;
                } catch (RpcTokenException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
