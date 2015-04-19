package com.xiaoerge.filecloud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.*;

import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class FileCloud implements EntryPoint {

    private static Logger logger = Logger.getLogger(FileCloud.class.getName());

//    private AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
//    private ShellServiceAsync shellServiceAsync = GWT.create(ShellService.class);

    public void onModuleLoad() {

        AuthServiceAsync authServiceAsync = GWT.create(AuthService.class);
        HandlerManager handlerManager = new HandlerManager(null);
        AppController appViewer = new AppController(authServiceAsync, handlerManager);
        appViewer.refresh(RootPanel.get("container"));

//        AsyncCallback<String> callback = new AsyncCallback<String>() {
//            @Override
//            public void onFailure(Throwable caught) {
//
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                if (result != null && !result.isEmpty()) {
//                    signInStatus("Success", "alert alert-success");
//                    logger.log(Level.SEVERE, "true");
//                    ls();
//                }
//            }
//        };
//        authServiceAsync.authenticateSession(callback);
    }

    private void signInStatus(String status, String style) {
//        authstatuslb.setStyleName(style);
//        authstatuslb.setText(status);
    }

    private void setCookie() {
//        String sessionID = result.getSessionId();
//        final int DURATION = 1000 * 60 * 60;
//        Date expires = new Date(System.currentTimeMillis() + DURATION);
//        Cookies.setCookie("sid", sessionID, expires);
    }

    private void ls() {
//        AsyncCallback<Vector<FileEntry>> callback = new AsyncCallback<Vector<FileEntry>>() {
//            public void onFailure(Throwable caught) {
//                logger.log(Level.SEVERE, "ls error");
//            }
//
//            public void onSuccess(Vector<FileEntry> result) {
//                if (result != null) {
//                    for (FileEntry fileEntry : result) {
//                        Label label = new Label();
//                        label.setText(fileEntry.getFileName());
//                        label.setStyleName("list-group-item");
//                        RootPanel.get("lsPanel").add(label);
//                    }
//                }
//            }
//        };
//
//        shellServiceAsync.ls(".", callback);
    }
}
