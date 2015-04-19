package com.xiaoerge.cloudftp.server.shared;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class SessionUtil
{
    private static Logger logger = Logger.getLogger(SessionUtil.class.getName());

    public static synchronized void saveToSession(HttpSession session, String key, Objects value) {
        session.setAttribute(key, value);
    }
    public static synchronized Object getFromSession(HttpSession session, String key) {
        return session.getAttribute(key);
    }
    public static synchronized void removeFromSession(HttpSession session, String key) {
        session.removeAttribute(key);
    }
}
