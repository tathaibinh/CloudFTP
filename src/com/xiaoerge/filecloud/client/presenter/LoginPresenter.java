package com.xiaoerge.filecloud.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.filecloud.client.AuthServiceAsync;
import com.xiaoerge.filecloud.client.event.LogoutEvent;
import com.xiaoerge.filecloud.client.shared.CommonUtil;

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
        display.getLoginButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                CommonUtil.showLoadingAnimation(display.getLoginStatusLabel());

                AsyncCallback<String> callback = new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "sign in error");
                    }

                    public void onSuccess(String result) {
                        if (result != null && !result.isEmpty()) {
                            display.getLoginStatusLabel().setText("Success");
                            display.getLoginStatusLabel().setStyleName("alert alert-success");
                            logger.log(Level.INFO, "Log in success");

                            CommonUtil.hideLoadingAnimation(display.getLoginStatusLabel());
                            eventBus.fireEvent(new LogoutEvent());

                        } else {
                            display.getLoginStatusLabel().setText("Failure");
                            display.getLoginStatusLabel().setStyleName("alert alert-danger");
                            logger.log(Level.SEVERE, "log in failure");
                            CommonUtil.hideLoadingAnimation(display.getLoginStatusLabel());
                        }
                    }
                };

                authServiceAsync.authenticate(display.getHostnameTextBox().getText(),
                        display.getPasswordTextBox().getText().getBytes(), 22, callback);
            }
        });
    }
}