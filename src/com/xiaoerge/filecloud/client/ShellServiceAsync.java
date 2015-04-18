package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface ShellServiceAsync {
    void ls(String path, AsyncCallback<String[]> async);
}
