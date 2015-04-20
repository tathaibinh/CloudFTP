package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.CdPresenter;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class CdView extends Composite implements CdPresenter.Display {

    private Vector<FileEntry> fileEntries;
    private TextBox pathTf;
    private Button cdBt;
    private FlexTable flexTable;

    public CdView() {

        flexTable = new FlexTable();
        fileEntries = new Vector<>();
        pathTf = new TextBox();
        cdBt = new Button("Show");

        initWidget(flexTable);

        flexTable.setStyleName("table table-responsive table-bordered");
        pathTf.setStyleName("form-control");
        cdBt.setStyleName("btn btn-primary");

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
    public Button getCdBt() {
        return cdBt;
    }

    private void refresh() {
        flexTable.clear();
        flexTable.setWidget(0, 0, pathTf);
        flexTable.setWidget(0, 1, cdBt);
        flexTable.getFlexCellFormatter().setColSpan(0, 0, 4);

        for (int i = 0; i < fileEntries.size(); i++) {
            FileEntry fileEntry = fileEntries.get(i);

            String style = fileEntry.getFileName().startsWith(".") ? "active" : "";
            HTML fileIcon = new HTML("<i class=\"fa fa-file-o\"></i>");
            HTML folderIcon = new HTML("<i class=\"fa fa-folder-o\"></i>");

            if (fileEntry.isDir()) {
                flexTable.setWidget((i+1), 0, folderIcon);
            }
            else {
                flexTable.setWidget((i+1), 0, fileIcon);
            }

            flexTable.setText((i + 1), 1, fileEntry.getFileName());
            flexTable.setText((i + 1), 2, "Placeholder");
            flexTable.setText((i + 1), 3, "Placeholder");
            flexTable.setText((i + 1), 4, "Placeholder");

            flexTable.getRowFormatter().setStyleName((i+1), style);
        }
    }
}
