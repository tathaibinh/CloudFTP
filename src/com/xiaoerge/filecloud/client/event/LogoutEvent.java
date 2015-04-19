package com.xiaoerge.filecloud.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LogoutEvent extends GwtEvent<LogoutEventHandler> {

    public static Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();

    @Override
    public Type<LogoutEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LogoutEventHandler handler) {
        handler.onLogout(this);
    }
}
