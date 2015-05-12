package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by xiaoerge on 4/18/15.
 */
public interface Presenter {
    void refresh(final HasWidgets widgets);

    public interface CommonDisplay {
        Label getStatusLb();
        Label getProgressLb();
    }
}
