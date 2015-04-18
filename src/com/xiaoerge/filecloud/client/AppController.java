package com.xiaoerge.filecloud.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.xiaoerge.filecloud.client.event.LoginEvent;
import com.xiaoerge.filecloud.client.event.LoginEventHandler;
import com.xiaoerge.filecloud.client.presenter.LoginPresenter;
import com.xiaoerge.filecloud.client.presenter.Presenter;
import com.xiaoerge.filecloud.client.view.LoginView;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class AppController implements Presenter, ValueChangeHandler<String> {

    private final AuthServiceAsync authServiceAsync;
    private final HandlerManager handlerManager;
    private HasWidgets container;

    public AppController(AuthServiceAsync authServiceAsync, HandlerManager handlerManager) {
        this.authServiceAsync = authServiceAsync;
        this.handlerManager = handlerManager;
        bind();
    }

    @Override
    public void go(HasWidgets widgets) {
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

            if (presenter != null) {
                presenter.go(container);
            }
        }
    }

    private void bind() {
        History.addValueChangeHandler(this);

        handlerManager.addHandler(LoginEvent.handlerType, new LoginEventHandler() {
            @Override
            public void onLogin(LoginEvent loginEvent) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        History.newItem("login");
    }
}
