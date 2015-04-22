package com.xiaoerge.cloudftp.server.global;

/**
 * Created by xiaoerge on 4/16/15.
 */
public class BashProfile {

    private static BashProfile bashProfile = new BashProfile();

    private String cwd;

    private BashProfile() {
        cwd = "";
    }

    public String getCwd() {
        return cwd;
    }

    public void setCwd(String cwd) {
        this.cwd = cwd;
    }

    public static synchronized BashProfile getInstance() {
        if (bashProfile == null)
            return new BashProfile();
        return bashProfile;
    }
}
