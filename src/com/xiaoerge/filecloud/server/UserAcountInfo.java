package com.xiaoerge.filecloud.server;

import com.jcraft.jsch.UserInfo;

/**
 * Created by xiaoerge on 4/16/15.
 */
public class UserAcountInfo implements UserInfo {

    private String password;
    public UserAcountInfo(String passwd) {
        password = passwd;
    }

    @Override
    public String getPassphrase() {
        return password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassword(String s) {
        return true;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return true;
    }

    @Override
    public boolean promptYesNo(String s) {
        return true;
    }

    @Override
    public void showMessage(String s) {
    }
}
