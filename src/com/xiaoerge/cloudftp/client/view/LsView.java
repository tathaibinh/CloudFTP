package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.LoginPresenter;
import com.xiaoerge.cloudftp.client.presenter.LsPresenter;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LsView extends Composite implements LsPresenter.Display {

    private VerticalPanel listPanel;
    private Vector<FileEntry> fileEntries;

    public LsView() {
        listPanel = new VerticalPanel();
        initWidget(listPanel);

        fileEntries = new Vector<>();
        listPanel.setStyleName("list-group");
    }

    @Override
    public Vector<FileEntry> getItems() {
        return fileEntries;
    }

    @Override
    public void setItems(Vector<FileEntry> fileEntries) {
        this.fileEntries = fileEntries;
        refresh();
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    private void refresh() {
        for (FileEntry fileEntry : fileEntries) {
            Label label = new Label(fileEntry.getFileName());
            label.setStyleName("list-group-item");
            listPanel.add(label);
        }
    }
}
