package com.xiaoerge.filecloud.server;

import com.jcraft.jsch.UserInfo;
import com.xiaoerge.filecloud.server.model.EncryptionUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by xiaoerge on 4/16/15.
 */
public class UserAcountInfo implements UserInfo {

    private byte[] password;
    public UserAcountInfo(byte[] passwd) {
        password = passwd;
    }

    @Override
    public String getPassphrase() {
        return getPassword();
    }

    @Override
    public String getPassword() {
        try {
            return new String(EncryptionUtil.decrypt(password), "UTF8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
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
