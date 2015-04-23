package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("authservice")
public interface AuthService extends XsrfProtectedService {

    public byte[] authenticate(String host, byte[] passwd, int port);

    String getSessionId();

    public byte[] authenticateSession();
}
