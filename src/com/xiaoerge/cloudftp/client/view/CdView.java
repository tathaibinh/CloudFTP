package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.CdPresenter;
import com.xiaoerge.cloudftp.client.shared.CommonUtil;
import com.xiaoerge.cloudftp.client.shared.StateConstants;

import java.awt.*;
import java.util.Vector;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class CdView extends Composite implements CdPresenter.Display {

    private Vector<FileEntry> fileEntries;
    private TextBox pathTf;
    private Button cdBt;
    private FlexTable flexTable;
    private Label statusLb, progressLb;
    private Button logoutBt;

    public CdView() {

        VerticalPanel verticalPanel = new VerticalPanel();

        flexTable = new FlexTable();
        fileEntries = new Vector<>();
        pathTf = new TextBox();
        cdBt = new Button("Show");
        statusLb = new Label();
        progressLb = new Label();
        logoutBt = new Button("Logout");

        flexTable.getElement().setAttribute("id", "dataTable");
        flexTable.setStyleName("table table-responsive table-bordered table-hover");
        pathTf.setStyleName("form-control");
        cdBt.setStyleName("btn btn-primary btn-block");

        logoutBt.setStyleName("btn btn-danger");

        verticalPanel.setStyleName("table table-responsive table-bordered table-hover");
        verticalPanel.add(logoutBt);
        verticalPanel.add(flexTable);

        setItems(fileEntries);

        initWidget(verticalPanel);
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
    public FlexTable getListTable() { return flexTable; }

    @Override
    public Label getStatusLb() {
        return statusLb;
    }

    @Override
    public Label getProgressLb() {
        return progressLb;
    }

    @Override
    public Button getCdBt() {
        return cdBt;
    }

    @Override
    public Button getLogoutBt() {return logoutBt;}

    private void refresh() {
        flexTable.removeAllRows();
        flexTable.clear();
        flexTable.setWidget(0, 0, statusLb);
        flexTable.setWidget(0, 1, progressLb);
        flexTable.setWidget(1, 0, pathTf);
        flexTable.setWidget(1, 1, cdBt);
        flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
        flexTable.getFlexCellFormatter().setColSpan(1, 0, 3);

        int rowOffset = 2;

        for (int i = 0; i < fileEntries.size(); i++) {
            FileEntry fileEntry = fileEntries.get(i);

            HTML fileIcon = new HTML("<i class=\"fa fa-file-o fa-2x\"></i>");
            HTML folderIcon = new HTML("<i class=\"fa fa-folder-o fa-2x\"></i>");

            if (fileEntry.isDir()) {
                flexTable.setWidget((i + rowOffset), 0, folderIcon);
                Button button = new Button(fileEntry.getFileName());
                button.setStyleName("btn btn-link");
                button.getElement().getStyle().setCursor(Style.Cursor.POINTER);
                flexTable.setWidget((i + rowOffset), 1, button);
            }
            else {
                flexTable.setWidget((i + rowOffset), 0, fileIcon);
                Anchor anchor = new Anchor(fileEntry.getFileName());

                //this url routes to GetServlet
                String url = GWT.getModuleBaseURL() + "getservice?" +
                        "filename={filename}&publickey={publickey}&csrftoken={csrftoken}";

                url = url.replace("{filename}", fileEntry.getFileName());
                url = url.replace("{publickey}", Cookies.getCookie(StateConstants.PUBLIC_KEY));

                anchor.setHref(URL.encode(url));
                anchor.getElement().getStyle().setCursor(Style.Cursor.POINTER);
                flexTable.setWidget((i + rowOffset), 1, anchor);
            }

            flexTable.setText((i + rowOffset), 2, fileEntry.getPermissionString());
            flexTable.setText((i + rowOffset), 3, fileEntry.getSizeString());
        }
    }
}
