package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xiaoerge.filecloud.client.model.FileEntry;

import java.util.Vector;

public interface ShellServiceAsync {
    void ls(String path, AsyncCallback<Vector<FileEntry>> async);
}
