package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/17/15.
 */
@RemoteServiceRelativePath("shellservice")
public interface ShellService extends RemoteService{
    public abstract Vector<FileEntry> cd(String path);
}
