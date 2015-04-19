package com.xiaoerge.cloudftp.client.event.foreground;


import com.google.gwt.event.shared.EventHandler;

/**
 * Created by xiaoerge on 4/18/15.
 */
public interface LoginEventHandler extends EventHandler {
    void onLogin(LoginEvent loginEvent);
}
