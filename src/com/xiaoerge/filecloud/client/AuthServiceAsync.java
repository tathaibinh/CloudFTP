package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {
    void authenticate(String host, String passwd, int port, AsyncCallback<Boolean> async);
}
