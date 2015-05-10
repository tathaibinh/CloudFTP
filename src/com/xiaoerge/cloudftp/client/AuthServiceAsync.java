package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

public interface AuthServiceAsync {

    void authenticate(String host, byte[] passwd, int port, AsyncCallback<String> async);

    void getSessionId(AsyncCallback<String> async);
}
