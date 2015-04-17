package com.xiaoerge.filecloud.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("authservice")
public interface AuthService extends RemoteService {

    public String authenticate(String host, byte[] passwd, int port);

    public static class App {
        private static AuthServiceAsync ourInstance = GWT.create(AuthService.class);

        public static synchronized AuthServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
