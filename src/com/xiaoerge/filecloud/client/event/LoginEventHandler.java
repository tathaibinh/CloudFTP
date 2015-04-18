package com.xiaoerge.filecloud.client.event;


import com.google.gwt.event.shared.EventHandler;

/**
 * Created by xiaoerge on 4/18/15.
 */
public interface LoginEventHandler extends EventHandler {
    void onLogin(LoginEvent loginEvent);
}
