package com.xiaoerge.filecloud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.xiaoerge.filecloud.client.model.FileEntry;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class FileCloud implements EntryPoint {

    private static Logger logger = Logger.getLogger(FileCloud.class.getName());

    private AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
    private ShellServiceAsync shellServiceAsync = GWT.create(ShellService.class);

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
                            ls();
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
                    ls();
                }
            }
        };
        authServiceAsync.authenticateSession(callback);
    }

    private void signInStatus(String status, String style) {
        authstatuslb.setStyleName(style);
        authstatuslb.setText(status);
    }

    private void setCookie() {
//        String sessionID = result.getSessionId();
//        final int DURATION = 1000 * 60 * 60;
//        Date expires = new Date(System.currentTimeMillis() + DURATION);
//        Cookies.setCookie("sid", sessionID, expires);
    }

    private void ls() {
        AsyncCallback<FileEntry[]> callback = new AsyncCallback<FileEntry[]>() {
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "ls error");
            }

            public void onSuccess(FileEntry[] result) {
                if (result != null) {
                    for (FileEntry fileEntry : result) {
                        Label label = new Label();
                        label.setText(fileEntry.getFileName());
                        label.setStyleName("list-group-item");
                        RootPanel.get("lsPanel").add(label);
                    }
                }
            }
        };

        shellServiceAsync.ls(".", callback);
    }
}
