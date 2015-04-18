package com.xiaoerge.filecloud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import javax.security.auth.callback.TextInputCallback;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class FileCloud implements EntryPoint {

    private AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Button signinBt;
    private TextBox hostnametf, passwordtf;
    private Label authstatuslb;

    public void onModuleLoad() {

        signinBt = new Button();
        hostnametf = new TextBox();
        passwordtf = new PasswordTextBox();
        authstatuslb = Label.wrap(Document.get().getElementById("authstatuslb"));

        hostnametf.setStyleName("form-control");
        hostnametf.getElement().setPropertyString("placeholder", "User@host.com");

        passwordtf.setStyleName("form-control");
        passwordtf.getElement().setPropertyString("placeholder", "Password");

        signinBt.setText("Sign in");
        signinBt.setStyleName("btn btn-lg btn-primary btn-block");

        RootPanel.get("loginform").add(hostnametf);
        RootPanel.get("loginform").add(passwordtf);
        RootPanel.get("loginform").add(signinBt);

        signinBt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                AsyncCallback<String> callback = new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "sign in error");
                    }

                    public void onSuccess(String result) {
                        if (result != null && !result.isEmpty()) {
                            signInStatus("Success", "alert alert-success");
                            logger.log(Level.SEVERE, "true");
                        } else {
                            signInStatus("Failure", "alert alert-danger");
                            logger.log(Level.SEVERE, "false");
                        }
                    }
                };

                authServiceAsync.authenticate(hostnametf.getText(), passwordtf.getText().getBytes(), 22, callback);
            }
        });

        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String result) {
                if (result != null && !result.isEmpty()) {
                    signInStatus("Success", "alert alert-success");
                    logger.log(Level.SEVERE, "true");
                }
            }
        };
        authServiceAsync.authenticateSession(callback);
    }

    private void signInStatus(String status, String style) {
        authstatuslb.setStyleName(style);
        authstatuslb.setText(status);
    }
}
