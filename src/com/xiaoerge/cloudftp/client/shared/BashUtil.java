package com.xiaoerge.cloudftp.client.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.ShellServiceAsync;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class BashUtil
{
    private static Logger logger = Logger.getLogger(BashUtil.class.getName());

    public static String pwd() {
        final String[] pwd = {"."};

        ShellServiceAsync shellServiceAsync = GWT.create(ShellService.class);

        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                pwd[0] = ".";
            }

            @Override
            public void onSuccess(String result) {
                pwd[0] = result;
            }
        };
        shellServiceAsync.pwd(callback);

        if (pwd[0].endsWith("/")) {
            return pwd[0];
        }
        else {
            return pwd[0].concat("/");
        }
    }
}
