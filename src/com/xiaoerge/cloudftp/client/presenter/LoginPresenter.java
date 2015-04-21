package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.AuthServiceAsync;
import com.xiaoerge.cloudftp.client.event.background.SavePublicKeyEvent;
import com.xiaoerge.cloudftp.client.event.foreground.CdEvent;
import com.xiaoerge.cloudftp.client.shared.CommonUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginPresenter implements Presenter {

    private static Logger logger = Logger.getLogger(LoginPresenter.class.getName());

    public interface Display {
        Button getLoginButton();
        TextBox getHostnameTextBox();
        PasswordTextBox getPasswordTextBox();
        TextBox getPortTf();
        Label getLoginStatusLabel();
        Widget asWidget();
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
        CommonUtil.showLoadingAnimation(display.getLoginStatusLabel());

        AsyncCallback<byte[]> callback = new AsyncCallback<byte[]>() {
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "sign in error");
            }

            public void onSuccess(byte[] result) {
                if (result.length > 0) {
                    CommonUtil.hideLoadingAnimation(display.getLoginStatusLabel());
                    display.getLoginStatusLabel().setText("Success");
                    display.getLoginStatusLabel().setStyleName("alert alert-success");
                    logger.log(Level.INFO, "Log in success");

                    //todo save session
                    eventBus.fireEvent(new SavePublicKeyEvent(result));
                    eventBus.fireEvent(new CdEvent());

                } else {
                    CommonUtil.hideLoadingAnimation(display.getLoginStatusLabel());
                    display.getLoginStatusLabel().setText("Failure");
                    display.getLoginStatusLabel().setStyleName("alert alert-danger");
                    logger.log(Level.SEVERE, "log in failure");
                }
            }
        };

        synchronized (this) {
            authServiceAsync.authenticate(display.getHostnameTextBox().getText(),
                    display.getPasswordTextBox().getText().getBytes(),
                    Integer.parseInt(display.getPortTf().getText()), callback);
        }
    }
}
