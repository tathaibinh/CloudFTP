package com.xiaoerge.cloudftp.client.event.foreground;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by xiaoerge on 4/19/15.
 */
public interface CdEventHandler extends EventHandler {
    void onLs(CdEvent cdEvent);
}
