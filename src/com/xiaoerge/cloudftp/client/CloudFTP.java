package com.xiaoerge.cloudftp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class CloudFTP implements EntryPoint {

    private static Logger logger = Logger.getLogger(CloudFTP.class.getName());

    public void onModuleLoad() {

        //todo use different id
        double sid = System.currentTimeMillis() * Random.nextDouble();
        Cookies.setCookie("CLOUDFTPSESSIONID", Double.toString(sid), null, null, "/", false);

        final AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
        final ShellServiceAsync shellServiceAsync = GWT.create(ShellService.class);

        XsrfTokenServiceAsync xsrf = GWT.create(XsrfTokenService.class);
        ((ServiceDefTarget)xsrf).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
        xsrf.getNewXsrfToken(new AsyncCallback<XsrfToken>() {

            public void onSuccess(XsrfToken token) {
                ((HasRpcToken) authServiceAsync).setRpcToken(token);
                ((HasRpcToken) shellServiceAsync).setRpcToken(token);
            }

            public void onFailure(Throwable caught) {
                try {
                    throw caught;
                } catch (RpcTokenException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        HandlerManager handlerManager = new HandlerManager(null);
        AppController appViewer = new AppController(authServiceAsync, shellServiceAsync, handlerManager);
        appViewer.refresh(RootPanel.get("container"));
    }
}
