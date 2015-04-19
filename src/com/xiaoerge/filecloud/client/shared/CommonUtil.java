package com.xiaoerge.filecloud.client.shared;

import com.google.gwt.user.client.ui.Widget;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class CommonUtil
{
    public static void showLoadingAnimation(Widget widget) {
        widget.getElement().setInnerHTML("<i class=\"fa fa-refresh fa-spin fa-3x\"></i>");
    }
    public static void hideLoadingAnimation(Widget widget) {
        widget.getElement().setInnerHTML("");
    }
}
