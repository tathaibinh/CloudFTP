package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.presenter.LogoutPresenter;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LogoutView extends Composite implements LogoutPresenter.Display {

    private Button logoutBt;
    private Label titleLb, progressLb, statusLb;

    public LogoutView() {

        VerticalPanel verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        verticalPanel.setStyleName("form-signin");

        logoutBt = new Button();
        statusLb = new Label();
        titleLb = new Label();

        logoutBt.setText("Yes");
        logoutBt.setStyleName("btn btn-lg btn-danger btn-block");

        statusLb.getElement().setPropertyString("role", "alert");

        statusLb.setStyleName("h2 form-signin-heading");
        statusLb.setText("Sign out");

        verticalPanel.add(titleLb);
        verticalPanel.add(logoutBt);
        verticalPanel.add(statusLb);
    }

    @Override
    public Button getLogoutButton() {
        return logoutBt;
    }

    @Override
    public Label getLogoutStatusLabel() {
        return statusLb;
    }

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
