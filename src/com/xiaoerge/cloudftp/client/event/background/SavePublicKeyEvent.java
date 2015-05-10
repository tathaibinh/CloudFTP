package com.xiaoerge.cloudftp.client.event.background;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class SavePublicKeyEvent extends GwtEvent<SavePublicKeyEventHandler> {
    public static Type<SavePublicKeyEventHandler> TYPE = new Type<>();

    private String key;
    public SavePublicKeyEvent(String k) {
        key = k;
    }

    public String getKey() { return key; }

    @Override
    public Type getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SavePublicKeyEventHandler handler) {
        handler.onSavePublicKey(this);
    }
}
