package com.xiaoerge.cloudftp.server.global;

import com.jcraft.jsch.UserInfo;
import com.xiaoerge.cloudftp.server.shared.EncryptionUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by xiaoerge on 4/16/15.
 */
public class UserProfile implements UserInfo {

    private byte[] password;
    public UserProfile(byte[] passwd) {
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
