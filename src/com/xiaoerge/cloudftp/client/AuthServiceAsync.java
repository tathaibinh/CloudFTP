package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {

    void authenticate(String host, byte[] passwd, int port, AsyncCallback<Void> async);
    void authenticateSession(AsyncCallback<byte[]> async);

    void getSessionId(AsyncCallback<String> async);
}
