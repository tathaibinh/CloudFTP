package com.xiaoerge.filecloud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.Event;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {

    public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

    @Override
    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginEventHandler handler) {
        handler.onLogin(this);
    }
}
