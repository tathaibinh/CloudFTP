package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.LoginPresenter;
import com.xiaoerge.cloudftp.client.presenter.LsPresenter;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class LsView extends Composite implements LsPresenter.Display {

    private ListBox listBox;
    private Vector<FileEntry> fileEntries;

    public LsView() {
        listBox = new ListBox();
        fileEntries = new Vector<>();

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(listBox);
    }

    @Override
    public ListBox getFileList() {
        return listBox;
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setItems(Vector<FileEntry> fileEntries) {
        this.fileEntries = fileEntries;
        refresh();
    }

    private void refresh() {
        for (FileEntry fileEntry : fileEntries) {
            listBox.addItem(fileEntry.getFileName());
        }
    }
}
