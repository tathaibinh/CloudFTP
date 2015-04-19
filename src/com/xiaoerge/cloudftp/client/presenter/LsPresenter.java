package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.ShellServiceAsync;
import com.xiaoerge.cloudftp.client.event.foreground.LsEvent;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class LsPresenter implements Presenter {

    public interface Display {
        Vector<FileEntry> getItems();
        void setItems(Vector<FileEntry> fileEntries);
        Widget asWidget();
    }

    private final ShellServiceAsync shellServiceAsync;
    private final HandlerManager eventBus;
    private final Display display;
    private final String path;

    public LsPresenter(ShellServiceAsync shellServiceAsync, HandlerManager eventBus, Display display) {
        this.shellServiceAsync = shellServiceAsync;
        this.eventBus = eventBus;
        this.display = display;
        //todo  don't hardcode
        this.path = ".";
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        widgets.clear();
        widgets.add(display.asWidget());
    }

    private void bind() {
        AsyncCallback<Vector<FileEntry>> callback = new AsyncCallback<Vector<FileEntry>>() {
            @Override
            public void onFailure(Throwable caught) {}

            @Override
            public void onSuccess(Vector<FileEntry> result) {
                display.setItems(result);
            }
        };
        shellServiceAsync.ls(path, callback);
    }
}
