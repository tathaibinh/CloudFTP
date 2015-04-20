package com.xiaoerge.cloudftp.client.model;

import java.io.Serializable;

/**
 * Created by xiaoerge on 4/18/15.
 */
public class FileEntry implements Serializable{

    private String fileName;
    private String longName;
    private String sizeString;
    private String permissionString;
    private boolean isDir;
    private boolean isLink;

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

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getSizeString() {
        return sizeString;
    }

    public void setSizeString(String sizeString) {
        this.sizeString = sizeString;
    }

    public String getPermissionString() {
        return permissionString;
    }

    public void setPermissionString(String permissionString) {
        this.permissionString = permissionString;
    }

    public boolean isLink() {
        return isLink;
    }

    public void setIsLink(boolean isLink) {
        this.isLink = isLink;
    }
}
