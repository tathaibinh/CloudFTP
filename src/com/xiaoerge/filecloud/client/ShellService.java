package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by xiaoerge on 4/17/15.
 */
@RemoteServiceRelativePath("shellservice")
public interface ShellService extends RemoteService{
    public abstract String[] ls(String path);
}
