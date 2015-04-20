package com.xiaoerge.cloudftp.client.event.foreground;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class CdEvent extends GwtEvent<CdEventHandler> {

    public static Type<CdEventHandler> TYPE = new Type<>();

    @Override
    public Type<CdEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CdEventHandler handler) {
        handler.onLs(this);
    }
}
