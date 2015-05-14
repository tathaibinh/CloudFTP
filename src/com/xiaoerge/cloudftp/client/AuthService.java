package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

import java.util.Map;

@RemoteServiceRelativePath("authservice")
public interface AuthService extends XsrfProtectedService {

    String authenticate(String host, byte[] passwd, int port);
    String getSessionId();
    void disconnect();
}
