package com.xiaoerge.filecloud.client.model;

import com.google.gwt.core.client.JavaScriptObject;

import java.io.Serializable;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class FileEntry implements Serializable{

    private String fileName;

    public FileEntry() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
