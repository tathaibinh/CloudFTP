package com.xiaoerge.cloudftp.client.model;

import java.io.Serializable;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class FileEntry implements Serializable{

    private String fileName;
    private boolean isDir;

    public FileEntry() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setIsDir(boolean isDir) {
        this.isDir = isDir;
    }
}
