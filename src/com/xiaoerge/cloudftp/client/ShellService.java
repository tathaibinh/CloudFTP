package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/17/15.
 */
@RemoteServiceRelativePath("shellservice")
public interface ShellService extends XsrfProtectedService {
    Vector<FileEntry> cd(String path);
    String pwd();
}
