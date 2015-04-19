package com.xiaoerge.cloudftp.client.event.foreground;

import com.google.gwt.event.shared.EventHandler;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/19/15.
 */
public interface LsEventHandler extends EventHandler {
    void onLs(LsEvent lsEvent);
}
