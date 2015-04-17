package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {
    void authenticate(String host, byte[] passwd, int port, AsyncCallback<String> async);
}
