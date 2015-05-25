package com.xiaoerge.cloudftp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class CloudFTP implements EntryPoint {

    private static Logger logger = Logger.getLogger(CloudFTP.class.getName());

    public void onModuleLoad() {

        //todo use cryptographic safe string
//        double sid = System.currentTimeMillis() * Random.nextDouble();
//        Cookies.setCookie("CLOUDFTPSESSIONID", Double.toString(sid), null, null, "/", false);

        PublicServiceAsync publicServiceAsync = GWT.create(PublicService.class);
        AsyncCallback<String> stringAsyncCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                logger.log(Level.SEVERE, result);
            }
        };
        publicServiceAsync.getSessionId(stringAsyncCallback);

        final AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
        final ShellServiceAsync shellServiceAsync = GWT.create(ShellService.class);

        HandlerManager handlerManager = new HandlerManager(null);
        AppController appViewer = new AppController(authServiceAsync, shellServiceAsync, handlerManager);
        appViewer.refresh(RootPanel.get("container"));
    }
}
