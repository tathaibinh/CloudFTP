package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {

    void authenticate(String host, byte[] passwd, int port, AsyncCallback<Boolean> async);

    void getSessionId(AsyncCallback<String> async);
}
