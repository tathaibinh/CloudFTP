package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

/**
 * Created by xiaoerge on 5/26/15.
 */
@RemoteServiceRelativePath("putservice")
public interface PutService extends XsrfProtectedService {
    abstract void put();
}
