package com.xiaoerge.filecloud.client.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.filecloud.client.presenter.LoginPresenter;

import java.util.List;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginView extends Composite implements LoginPresenter.Display {

    private Button signinBt;
    private TextBox hostnametf;
    private PasswordTextBox passwordtf;
    private Label authstatuslb;
    private Label titleLabel;

    public LoginView() {
        signinBt = new Button();
        hostnametf = new TextBox();
        passwordtf = new PasswordTextBox();
        authstatuslb = new Label();
        titleLabel = new Label();

        hostnametf.setStyleName("form-control");
        hostnametf.getElement().setPropertyString("placeholder", "User@host.com");

        passwordtf.setStyleName("form-control");
        passwordtf.getElement().setPropertyString("placeholder", "Password");

        signinBt.setText("Sign in");
        signinBt.setStyleName("btn btn-lg btn-primary btn-block");

        authstatuslb.getElement().setPropertyString("role", "alert");

        titleLabel.setStyleName("h2 form-signin-heading");
        titleLabel.getElement().setInnerText("SSH Sign in");

        RootPanel.get("loginform").add(authstatuslb);
        RootPanel.get("loginform").add(titleLabel);
        RootPanel.get("loginform").add(hostnametf);
        RootPanel.get("loginform").add(passwordtf);
        RootPanel.get("loginform").add(signinBt);
    }

    @Override
    public Button getLoginButton() {
        return signinBt;
    }

    @Override
    public TextBox getHostnameTextBox() {
        return hostnametf;
    }

    @Override
    public PasswordTextBox getPasswordTextBox() {
        return passwordtf;
    }

    @Override
    public Label getLoginStatusLabel() {
        return authstatuslb;
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
