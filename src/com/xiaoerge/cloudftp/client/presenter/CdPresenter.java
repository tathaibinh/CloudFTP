package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.ShellServiceAsync;
import com.xiaoerge.cloudftp.client.event.foreground.CdEvent;
import com.xiaoerge.cloudftp.client.event.foreground.CdEventHandler;
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
        FlexTable getListTable();
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
                            } else {
                                display.setItems(result);

                                bindCellClick();
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

    private void bindCellClick() {
        Vector<FileEntry> items = display.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isDir()){
                final Button button = (Button) display.getListTable().getWidget((i+1), 1);
                button.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        display.getPathTf().setText(button.getText());
                        display.getCdBt().click();
                    }
                });
            }
        }
    }
}
