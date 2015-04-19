package com.xiaoerge.filecloud.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.xiaoerge.filecloud.client.event.LoginEvent;
import com.xiaoerge.filecloud.client.event.LoginEventHandler;
import com.xiaoerge.filecloud.client.event.LogoutEvent;
import com.xiaoerge.filecloud.client.event.LogoutEventHandler;
import com.xiaoerge.filecloud.client.presenter.LoginPresenter;
import com.xiaoerge.filecloud.client.presenter.LogoutPresenter;
import com.xiaoerge.filecloud.client.presenter.Presenter;
import com.xiaoerge.filecloud.client.view.LoginView;
import com.xiaoerge.filecloud.client.view.LogoutView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class AppController implements Presenter, ValueChangeHandler<String> {

    private static Logger logger = Logger.getLogger(AppController.class.getName());

    private final AuthServiceAsync authServiceAsync;
    private final HandlerManager handlerManager;
    private HasWidgets container;

    public AppController(AuthServiceAsync authServiceAsync, HandlerManager handlerManager) {
        this.authServiceAsync = authServiceAsync;
        this.handlerManager = handlerManager;
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        this.container = widgets;

        if ("".equals(History.getToken())) {
            History.newItem("login");
        }
        else {
            History.fireCurrentHistoryState();
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token != null) {
            Presenter presenter = null;

            if (token.equals("login")) {
                presenter = new LoginPresenter(authServiceAsync, handlerManager, new LoginView());
            }
            else if (token.equals("logout")) {
                presenter = new LogoutPresenter(authServiceAsync, handlerManager, new LogoutView());
            }

            if (presenter != null) {
                presenter.refresh(container);
            }
        }
    }

    private void bind() {
        History.addValueChangeHandler(this);

        handlerManager.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
            @Override
            public void onLogin(LoginEvent loginEvent) {
                doLogin();
            }
        });
        handlerManager.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
            @Override
            public void onLogout(LogoutEvent loginEvent) {
                doLogout();
            }
        });
    }

    private synchronized void doLogin() {
        logger.log(Level.SEVERE, "loading login view");
        History.newItem("login");
    }

    private synchronized void doLogout() {
        logger.log(Level.SEVERE, "loading logout view");
        History.newItem("logout");
    }
}
