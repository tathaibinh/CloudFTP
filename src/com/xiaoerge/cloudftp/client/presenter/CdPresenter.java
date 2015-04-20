package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.ShellServiceAsync;
import com.xiaoerge.cloudftp.client.model.FileEntry;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class CdPresenter implements Presenter {

    private static Logger logger = Logger.getLogger(CdPresenter.class.getName());

    public interface Display {
        Vector<FileEntry> getItems();
        void setItems(Vector<FileEntry> fileEntries);
        Widget asWidget();
        TextBox getPathTf();
        Button getCdBt();
    }

    private final ShellServiceAsync shellServiceAsync;
    private final HandlerManager eventBus;
    private final Display display;

    public CdPresenter(ShellServiceAsync shellServiceAsync, HandlerManager eventBus, Display display) {
        this.shellServiceAsync = shellServiceAsync;
        this.eventBus = eventBus;
        this.display = display;
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        widgets.clear();
        widgets.add(display.asWidget());
    }

    private void bind() {

        display.getCdBt().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!display.getPathTf().getText().isEmpty()) {
                    AsyncCallback<Vector<FileEntry>> callback = new AsyncCallback<Vector<FileEntry>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            logger.log(Level.SEVERE, "error cd");
                        }

                        @Override
                        public void onSuccess(Vector<FileEntry> result) {
                            if (result == null) {
                                logger.log(Level.SEVERE, "error cd");
                            }
                            else {
                                display.setItems(result);
                            }
                        }
                    };
                    shellServiceAsync.cd(display.getPathTf().getText(), callback);
                }
            }
        });

        //default show current path
        display.getPathTf().setText(".");
        display.getCdBt().click();
    }
}
