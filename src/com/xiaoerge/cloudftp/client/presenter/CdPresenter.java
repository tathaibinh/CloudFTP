package com.xiaoerge.cloudftp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.xiaoerge.cloudftp.client.ShellServiceAsync;
import com.xiaoerge.cloudftp.client.event.foreground.LogoutEvent;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.shared.BashUtil;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/19/15.
 */
public class CdPresenter implements Presenter {

    private static Logger logger = Logger.getLogger(CdPresenter.class.getName());

    public interface Display extends Presenter.CommonDisplay {
        Vector<FileEntry> getItems();
        void setItems(Vector<FileEntry> fileEntries);
        Widget asWidget();
        TextBox getPathTf();
        Button getCdBt();
        FlexTable getListTable();
        Button getLogoutBt();
        int getRowOffset();

        @Override
        Label getStatusLb();

        @Override
        Label getProgressLb();
    }

    private final ShellServiceAsync shellServiceAsync;
    private final HandlerManager eventBus;
    private final Display display;

    private String cwd;

    public CdPresenter(ShellServiceAsync shellServiceAsync, HandlerManager eventBus, Display display) {
        this.shellServiceAsync = shellServiceAsync;
        this.eventBus = eventBus;
        this.display = display;

        cwd = ".";
        bind();
    }

    @Override
    public void refresh(HasWidgets widgets) {
        widgets.clear();
        widgets.add(display.asWidget());

        //initial folder
        display.getPathTf().setText(cwd);
        display.getCdBt().click();
    }

    private void bind() {

        display.getCdBt().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                XsrfTokenServiceAsync xsrf = GWT.create(XsrfTokenService.class);
                ((ServiceDefTarget)xsrf).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
                xsrf.getNewXsrfToken(new AsyncCallback<XsrfToken>() {

                    public void onSuccess(XsrfToken token) {
                        ((HasRpcToken) shellServiceAsync).setRpcToken(token);

                        //CommonUtil.showLoadingAnimation(display.getProgressLb());

                        if (!display.getPathTf().getText().isEmpty()) {
                            AsyncCallback<Vector<FileEntry>> callback = new AsyncCallback<Vector<FileEntry>>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    display.getStatusLb().setText(caught.getMessage());
                                    //CommonUtil.hideLoadingAnimation(display.getProgressLb());
                                    logger.log(Level.SEVERE, "error cd");
                                }

                                @Override
                                public void onSuccess(Vector<FileEntry> result) {
                                    if (result == null) {
                                        logger.log(Level.SEVERE, "error cd");
                                    } else {
                                        display.setItems(result);

                                        showCwd();
                                        bindCellClick();
                                    }
                                    //CommonUtil.hideLoadingAnimation(display.getProgressLb());
                                }
                            };
                            shellServiceAsync.cd(display.getPathTf().getText(), callback);
                        }
                    }

                    public void onFailure(Throwable caught) {
                        try {
                            throw caught;
                        } catch (RpcTokenException e) {
                            e.printStackTrace();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        display.getLogoutBt().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new LogoutEvent());
            }
        });
    }

    private void showCwd() {
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                display.getStatusLb().setText(caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                display.getPathTf().setText(result);
            }
        };
        shellServiceAsync.pwd(callback);
    }

    private void bindCellClick() {
        Vector<FileEntry> items = display.getItems();
        for (int i = 0; i < items.size(); i++) {
            final Widget widget = display.getListTable().getWidget((i+display.getRowOffset()), 1);
            if (items.get(i).isDir()){
                final Button button = (Button) widget;
                button.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        display.getPathTf().setText(BashUtil.pwd().concat(button.getText()));
                        display.getCdBt().click();
                    }
                });
            }
//            else {
//                final Label label = (Label) widget;
//                label.addClickHandler(new ClickHandler() {
//                    @Override
//                    public void onClick(ClickEvent event) {
//
//                        String url = GWT.getModuleBaseURL() + "getservice?filename={filename}";
//                        url = url.replace("{filename}", label.getText());
//                        url = URL.encode(url);
//
//                        Window.Location.replace(url);

//                        try {
//                            RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
//                            requestBuilder.sendRequest(null, new RequestCallback() {
//                                @Override
//                                public void onResponseReceived(Request request, Response response) {
//                                    //todo
//                                    logger.log(Level.SEVERE, "request received "+ response);
//                                    logger.log(Level.SEVERE, "request received "+ response.getText());
//                                }
//
//                                @Override
//                                public void onError(Request request, Throwable exception) {
//                                    //todo
//                                }
//                            });
//                        } catch (RequestException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
        }
    }
}
