package com.xiaoerge.cloudftp.client.shared;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Created by xiaoerge on 4/26/15.
 */
public class GraphicalUtil {

    private final static PopupPanel popupPanel = new PopupPanel();

    public static void showOverlayProgressWindow() {

        popupPanel.setWidget(new Label("Click outside of this popup to close it"));

        int windowHeight=Window.getClientHeight()-10;
        int windowWidth=Window.getClientWidth()-10;

        popupPanel.setHeight(String.valueOf(windowHeight));
        popupPanel.setWidth(String.valueOf(windowWidth));
        popupPanel.setGlassEnabled(true);

        popupPanel.show();
    }

    public static void hideOverlayProgressWindow() {
        popupPanel.hide();
    }
}
