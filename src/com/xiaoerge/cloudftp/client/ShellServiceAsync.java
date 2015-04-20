package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

public interface ShellServiceAsync {

    void cd(String path, AsyncCallback<Vector<FileEntry>> async);
}
