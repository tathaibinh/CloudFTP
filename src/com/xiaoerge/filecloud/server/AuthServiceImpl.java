package com.xiaoerge.filecloud.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xiaoerge.filecloud.client.AuthService;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {

    @Override
    public boolean authenticate() {
        return false;
    }
}