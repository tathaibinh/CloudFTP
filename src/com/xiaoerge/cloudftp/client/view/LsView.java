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
    private TextBox pathTf;
    private Button lsBt;

    public LsView() {
        listPanel = new VerticalPanel();
        fileEntries = new Vector<>();
        pathTf = new TextBox();
        lsBt = new Button("Show");

        initWidget(listPanel);

        listPanel.setStyleName("list-group");
        pathTf.setStyleName("list-group-item form-control");
        lsBt.setStyleName("list-group-item btn btn-primary");

        setItems(fileEntries);
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

    @Override
    public TextBox getPathTf() {
        return pathTf;
    }

    @Override
    public Button getLsBt() {
        return lsBt;
    }

    private void refresh() {
        listPanel.clear();
        listPanel.add(pathTf);
        listPanel.add(lsBt);

        for (FileEntry fileEntry : fileEntries) {
            Label label = new Label(fileEntry.getFileName());
            label.setStyleName("list-group-item");
            listPanel.add(label);
        }
    }
}
