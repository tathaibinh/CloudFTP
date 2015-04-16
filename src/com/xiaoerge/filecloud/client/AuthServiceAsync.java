package com.xiaoerge.filecloud.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {
    void authenticate(AsyncCallback<Boolean> async);
}
