package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {

    void authenticate(String host, byte[] passwd, int port, AsyncCallback<byte[]> async);
    void authenticateSession(AsyncCallback<byte[]> async);
}
