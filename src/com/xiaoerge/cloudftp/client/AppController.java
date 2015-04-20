package com.xiaoerge.cloudftp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.xiaoerge.cloudftp.client.event.background.SavePublicKeyEvent;
import com.xiaoerge.cloudftp.client.event.background.SavePublicKeyEventHandler;
import com.xiaoerge.cloudftp.client.event.foreground.*;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.LsPresenter;
import com.xiaoerge.cloudftp.client.presenter.LoginPresenter;
import com.xiaoerge.cloudftp.client.presenter.LogoutPresenter;
import com.xiaoerge.cloudftp.client.presenter.Presenter;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.client.view.LoginView;
import com.xiaoerge.cloudftp.client.view.LogoutView;
import com.xiaoerge.cloudftp.client.view.LsView;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class AppController implements Presenter, ValueChangeHandler<String> {

    private static Logger logger = Logger.getLogger(AppController.class.getName());

    private final AuthServiceAsync authServiceAsync;
    private final ShellServiceAsync shellServiceAsync;
    private final HandlerManager eventBus;
    private HasWidgets container;

    public AppController(AuthServiceAsync authServiceAsync, ShellServiceAsync shellServiceAsync,
                         HandlerManager eventBus) {
        this.authServiceAsync = authServiceAsync;
        this.shellServiceAsync = shellServiceAsync;
        this.eventBus = eventBus;
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        this.container = widgets;

        if (History.getToken().isEmpty()) {
            History.newItem(StateConstants.LOGIN);
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

            if (token.equals(StateConstants.LOGIN)) {
                presenter = new LoginPresenter(authServiceAsync, eventBus, new LoginView());
            }
            else if (token.equals(StateConstants.LOGOUT)) {
                presenter = new LogoutPresenter(authServiceAsync, eventBus, new LogoutView());
            }
            else if (token.equals(StateConstants.LIST_DIRECTORY)) {
                presenter = new LsPresenter(shellServiceAsync, eventBus, new LsView());
            }

            if (presenter != null) {
                presenter.refresh(container);
            }
        }
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
            @Override
            public void onLogin(LoginEvent loginEvent) {
                doLogin();
            }
        });
        eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
            @Override
            public void onLogout(LogoutEvent loginEvent) {
                doLogout();
            }
        });
        eventBus.addHandler(SavePublicKeyEvent.TYPE, new SavePublicKeyEventHandler() {
            @Override
            public void onSavePublicKey(SavePublicKeyEvent key) {
                doSavePublicKey();
            }
        });
        eventBus.addHandler(LsEvent.TYPE, new LsEventHandler() {
            @Override
            public void onLs(LsEvent lsEvent) {
                doLs(lsEvent.getPath());
            }
        });
    }

    private synchronized void doLogin() {
        logger.log(Level.SEVERE, "loading login view");
        History.newItem(StateConstants.LOGIN);
    }

    private synchronized void doLogout() {
        logger.log(Level.SEVERE, "loading logout view");
        History.newItem(StateConstants.LOGOUT);
    }

    private synchronized void doSavePublicKey() {
        //todo logic
        logger.log(Level.SEVERE, "saving public key");
    }

    private synchronized void doLs(String path) {
        //todo logic
        logger.log(Level.SEVERE, "ls directory");
        History.newItem(StateConstants.LIST_DIRECTORY);
    }
}
