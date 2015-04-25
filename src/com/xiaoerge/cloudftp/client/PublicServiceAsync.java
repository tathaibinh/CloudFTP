package com.xiaoerge.cloudftp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by xiaoerge on 4/24/15.
 */
public interface PublicServiceAsync {
    void getSessionId(AsyncCallback<String> async);
}
