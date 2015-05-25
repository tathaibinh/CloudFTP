package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.xiaoerge.cloudftp.client.AuthServiceAsync;
import com.xiaoerge.cloudftp.client.event.foreground.LoginEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LogoutPresenter implements Presenter {

    private static final Logger logger = Logger.getLogger(LogoutPresenter.class.getName());

    private final AuthServiceAsync authServiceAsync;
    private final HandlerManager eventBus;
    private final Display display;

    public interface Display extends Presenter.CommonDisplay {
        Button getLogoutButton();
        Label getLogoutStatusLabel();
        Widget asWidget();
    }

    public LogoutPresenter(AuthServiceAsync authServiceAsync, HandlerManager eventBus, Display display) {
        this.authServiceAsync = authServiceAsync;
        this.eventBus = eventBus;
        this.display = display;
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        widgets.clear();
        widgets.add(display.asWidget());
    }

    private void bind() {
        display.getLogoutButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //todo clear session
                logger.log(Level.INFO, "Logging out");

                AsyncCallback<Void> callback = new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        eventBus.fireEvent(new LoginEvent());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        eventBus.fireEvent(new LoginEvent());
                    }
                };

                authServiceAsync.disconnect(callback);
            }
        });
    }
}
