package com.xiaoerge.cloudftp.client.shared;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class CommonUtil
{
    private static Logger logger = Logger.getLogger(CommonUtil.class.getName());

    private static final String SESSION_ID = "SESSION_ID";

    public static synchronized void showLoadingAnimation(Widget widget) {
        widget.getElement().setInnerHTML("<i class=\"fa fa-refresh fa-spin fa-3x\"></i>");
    }
    public static synchronized void hideLoadingAnimation(Widget widget) {
        widget.getElement().setInnerHTML("");
    }

    //this is not used
    public static synchronized void setCookie(String sessionId) {
        final int DURATION = 1000 * 60 * 60;
        Date expires = new Date(System.currentTimeMillis() + DURATION);
        Cookies.setCookie(SESSION_ID, sessionId, expires);
    }
}
