package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.presenter.LoginPresenter;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginView extends Composite implements LoginPresenter.Display {

    private Button loginBt;
    private TextBox hostnameTf, portTf;
    private PasswordTextBox passwordTf;
    private Label titleLb, progressLb, statusLb;

    public LoginView() {

        VerticalPanel verticalPanel = new VerticalPanel();

        initWidget(verticalPanel);
        verticalPanel.setStyleName("form-signin");

        loginBt = new Button();
        hostnameTf = new TextBox();
        portTf = new TextBox();
        passwordTf = new PasswordTextBox();
        progressLb = new Label();
        titleLb = new Label();
        statusLb = new Label();

        titleLb.setStyleName("h2 form-signin-heading");
        titleLb.setText("SSH Sign in");

        hostnameTf.setStyleName("form-control");
        hostnameTf.getElement().setPropertyString("placeholder", "User@host.com");

        passwordTf.setStyleName("form-control");
        passwordTf.getElement().setPropertyString("placeholder", "Password");

        portTf.setStyleName("form-control");
        portTf.setText(Integer.toString(22));
        portTf.getElement().setPropertyString("placeholder", "22");

        loginBt.setText("Sign in");
        loginBt.setStyleName("btn btn-lg btn-primary btn-block");

        progressLb.getElement().setPropertyString("role", "alert");

        verticalPanel.add(titleLb);
        verticalPanel.add(hostnameTf);
        verticalPanel.add(passwordTf);
        verticalPanel.add(portTf);
        verticalPanel.add(loginBt);
        verticalPanel.add(progressLb);
        verticalPanel.add(statusLb);
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
    public TextBox getPortTf() { return portTf; }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public Label getStatusLb() {
        return statusLb;
    }

    @Override
    public Label getProgressLb() {
        return progressLb;
    }
}
