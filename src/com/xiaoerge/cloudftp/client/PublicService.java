package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by xiaoerge on 4/24/15.
 */
@RemoteServiceRelativePath("publicservice")
public interface PublicService extends RemoteService {
    String getSessionId();
}
