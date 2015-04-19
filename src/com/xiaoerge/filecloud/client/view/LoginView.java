package com.xiaoerge.filecloud.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.filecloud.client.presenter.LoginPresenter;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginView extends Composite implements LoginPresenter.Display {

    private Button loginBt;
    private TextBox hostnameTf;
    private PasswordTextBox passwordTf;
    private Label loginStatusLb;
    private Label titleLb;

    public LoginView() {

        VerticalPanel verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        verticalPanel.setStyleName("form-signin");

        loginBt = new Button();
        hostnameTf = new TextBox();
        passwordTf = new PasswordTextBox();
        loginStatusLb = new Label();
        titleLb = new Label();

        hostnameTf.setStyleName("form-control");
        hostnameTf.getElement().setPropertyString("placeholder", "User@host.com");

        passwordTf.setStyleName("form-control");
        passwordTf.getElement().setPropertyString("placeholder", "Password");

        loginBt.setText("Sign in");
        loginBt.setStyleName("btn btn-lg btn-primary btn-block");

        loginStatusLb.getElement().setPropertyString("role", "alert");

        titleLb.setStyleName("h2 form-signin-heading");
        titleLb.getElement().setInnerText("SSH Sign in");

        verticalPanel.add(loginStatusLb);
        verticalPanel.add(titleLb);
        verticalPanel.add(hostnameTf);
        verticalPanel.add(passwordTf);
        verticalPanel.add(loginBt);
    }

    @Override
    public Button getLoginButton() {
        return loginBt;
    }

    @Override
    public TextBox getHostnameTextBox() {
        return hostnameTf;
    }

    @Override
    public PasswordTextBox getPasswordTextBox() {
        return passwordTf;
    }

    @Override
    public Label getLoginStatusLabel() {
        return loginStatusLb;
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
