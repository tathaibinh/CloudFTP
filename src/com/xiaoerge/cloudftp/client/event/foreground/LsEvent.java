package com.xiaoerge.cloudftp.client.event.foreground;

import com.google.gwt.event.shared.GwtEvent;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class LsEvent extends GwtEvent<LsEventHandler> {

    public static Type<LsEventHandler> TYPE = new Type<>();
    private String path;

    public LsEvent(String path) {
        this.path = path;
    }

    @Override
    public Type<LsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LsEventHandler handler) {
        handler.onLs(this);
    }

    public String getPath() { return path; }
}
