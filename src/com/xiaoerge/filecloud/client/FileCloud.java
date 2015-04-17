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

        signinBt = Button.wrap(Document.get().getElementById("signinbt"));
        hostnametf = TextBox.wrap(Document.get().getElementById("hostnametf"));
        passwordtf = TextBox.wrap(Document.get().getElementById("passwordtf"));
        authstatuslb = Label.wrap(Document.get().getElementById("authstatuslb"));

        signinBt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "sign in error");
                    }

                    public void onSuccess(Boolean result) {
                        if (result) {
                            authstatuslb.setText("True");
                            logger.log(Level.SEVERE, "true");
                        }
                        else {
                            authstatuslb.setText("False");
                            logger.log(Level.SEVERE, "false");
                        }
                    }
                };

                authServiceAsync.authenticate(hostnametf.getText(), passwordtf.getText().toCharArray(), 22, callback);
                logger.log(Level.SEVERE, "begin");
            }
        });
    }
}
