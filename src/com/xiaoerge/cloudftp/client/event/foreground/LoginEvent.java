package com.xiaoerge.cloudftp.client.event.foreground;

import com.google.gwt.event.shared.GwtEvent;

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
