package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.presenter.LogoutPresenter;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LogoutView extends Composite implements LogoutPresenter.Display {

    private Button logoutBt;
    private Label logoutStatusLabel;
    private Label titleLabel;

    public LogoutView() {

        VerticalPanel verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        verticalPanel.setStyleName("form-signin");

        logoutBt = new Button();
        logoutStatusLabel = new Label();
        titleLabel = new Label();

        logoutBt.setText("Yes");
        logoutBt.setStyleName("btn btn-lg btn-danger btn-block");

        logoutStatusLabel.getElement().setPropertyString("role", "alert");

        titleLabel.setStyleName("h2 form-signin-heading");
        titleLabel.setText("Sign out");

        verticalPanel.add(titleLabel);
        verticalPanel.add(logoutBt);
        verticalPanel.add(logoutStatusLabel);
    }

    @Override
    public Button getLogoutButton() {
        return logoutBt;
    }

    @Override
    public Label getLogoutStatusLabel() {
        return logoutStatusLabel;
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
