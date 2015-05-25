package com.xiaoerge.cloudftp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.presenter.CdPresenter;
import com.xiaoerge.cloudftp.client.shared.StateConstants;

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
    private Button uploadBt;
    private FormPanel uploadForm;

    public CdView() {

        VerticalPanel verticalPanel = new VerticalPanel();
        FlexTable innerTable = new FlexTable();
        VerticalPanel panel = new VerticalPanel();
        FileUpload upload = new FileUpload();

        flexTable = new FlexTable();
        fileEntries = new Vector<>();
        pathTf = new TextBox();
        cdBt = new Button("Show");
        statusLb = new Label();
        progressLb = new Label();
        logoutBt = new Button("Logout");
        uploadBt = new Button("Upload");
        uploadForm = new FormPanel();

        flexTable.getElement().setAttribute("id", "dataTable");
        flexTable.setStyleName("table table-responsive table-bordered table-hover");
        pathTf.setStyleName("form-control");
        cdBt.setStyleName("btn btn-primary btn-block");

        logoutBt.setStyleName("btn btn-danger");

        innerTable.setWidget(0, 0, statusLb);
        innerTable.setWidget(0, 1, progressLb);
        innerTable.setWidget(1, 0, pathTf);
        innerTable.setWidget(1, 1, cdBt);
        innerTable.getFlexCellFormatter().setColSpan(0, 0, 3);
        innerTable.getFlexCellFormatter().setColSpan(1, 0, 3);
        innerTable.setStyleName("table table-responsive table-bordered table-hover");

        uploadForm.setAction("/putservlet");
        uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        uploadForm.setMethod(FormPanel.METHOD_POST);
        uploadForm.setWidget(panel);

        upload.setName("fileupload");
        uploadBt.setStyleName("btn btn-success");;

        panel.add(upload);
        panel.add(uploadBt);

        verticalPanel.add(logoutBt);
        verticalPanel.add(panel);
        verticalPanel.add(innerTable);
        verticalPanel.add(flexTable);
        verticalPanel.setStyleName("col-md-12");

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

    @Override
    public int getRowOffset() { return 1; }

    @Override
    public Button getUploadBt() { return uploadBt; }

    @Override
    public FormPanel getUploadForm() {
        return uploadForm;
    }

    private void refresh() {
        flexTable.removeAllRows();
        flexTable.clear();

        flexTable.setWidget(0, 0, new HTML("<i class=\"fa fa-file\"></i>"));
        flexTable.setWidget(0, 1, new HTML("Name"));
        flexTable.setWidget(0, 2, new HTML("Download"));
        flexTable.setWidget(0, 3, new HTML("Permission"));
        flexTable.setWidget(0, 4, new HTML("Size"));

        int rowOffset = getRowOffset();

        for (int i = 0; i < fileEntries.size(); i++) {

            FileEntry fileEntry = fileEntries.get(i);
            HTML fileIcon = new HTML("<i class=\"fa fa-file-o fa-2x\"></i>");
            HTML folderIcon = new HTML("<i class=\"fa fa-folder-o fa-2x\"></i>");
            Widget widget = null;
            Anchor download = new Anchor();

            //this url routes to GetServlet
            String url = GWT.getModuleBaseURL() + "getservice?" +
                    "filename={filename}&publickey={publickey}&csrftoken={csrftoken}&zipper={zipper}";
            url = url.replace("{filename}", fileEntry.getFileName());
            url = url.replace("{publickey}", Cookies.getCookie(StateConstants.PUBLIC_KEY));

            if (fileEntry.isDir()) {
                flexTable.setWidget((i + rowOffset), 0, folderIcon);
                Button button = new Button(fileEntry.getFileName());
                button.setStyleName("btn btn-link");
                widget = button;

                url = url.replace("{zipper}", "true");
            }
            else {
                flexTable.setWidget((i + rowOffset), 0, fileIcon);
                Anchor anchor = new Anchor(fileEntry.getFileName());

                anchor.setHref(url);
                widget = anchor;

                url = url.replace("{zipper}", "false");
            }

            widget.getElement().getStyle().setCursor(Style.Cursor.POINTER);
            download.setHTML("<i class=\"fa fa-arrow-circle-o-down fa-2x\"></i>");
            download.setHref(URL.encode(url));
            download.setStyleName("btn btn-link");

            flexTable.setWidget((i + rowOffset), 1, widget);
            flexTable.setWidget((i + rowOffset), 2, download);
            flexTable.setText((i + rowOffset), 3, fileEntry.getPermissionString());
            flexTable.setText((i + rowOffset), 4, fileEntry.getSizeString());
        }
    }
}
