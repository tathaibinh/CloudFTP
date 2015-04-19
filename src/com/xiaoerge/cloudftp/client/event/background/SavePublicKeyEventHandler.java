package com.xiaoerge.cloudftp.client.event.background;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by xiaoerge on 4/19/15.
 */
public interface SavePublicKeyEventHandler extends EventHandler {
    void onSavePublicKey(SavePublicKeyEvent savePublicKeyEvent);
}
