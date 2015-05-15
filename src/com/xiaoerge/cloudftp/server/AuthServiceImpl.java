package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.cloudftp.client.AuthService;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.UserProfile;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import com.xiaoerge.cloudftp.server.shared.EncryptionUtil;
import com.xiaoerge.cloudftp.server.shared.SessionUtil;

import javax.crypto.Cipher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import java.util.logging.Logger;

public class AuthServiceImpl extends XsrfProtectedServiceServlet implements AuthService {

    public static java.util.logging.Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    @Override
    public String authenticate(String hostname, byte[] password, int port) {

        try {
            SessionProfile sessionProfile = SessionProfile.getInstance();

            String username = hostname.substring(0, hostname.indexOf('@'));
            String host = hostname.substring(hostname.indexOf('@') + 1);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            sessionProfile.setKeyGen(keyGen);
            sessionProfile.setKey(key);
            sessionProfile.setCipher(cipher);

            byte[] cipherText = EncryptionUtil.encrypt(password);

            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            UserInfo userInfo = new UserProfile(cipherText);
            session.setUserInfo(userInfo);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelsftp = (ChannelSftp) channel;

            sessionProfile.setJsch(jSch);
            sessionProfile.setAccountinfo(userInfo);
            sessionProfile.setSession(session);
            sessionProfile.setChannel(channel);
            sessionProfile.setChannelsftp(channelsftp);

            String pKey = Arrays.toString(key.getPublic().getEncoded());

            //if this is a new session, xsrf token will fail. should not be a new session
            HttpSession session1 = this.getThreadLocalRequest().getSession(false);
            SessionUtil.saveToSession(session1, StateConstants.PUBLIC_KEY, pKey);
            SessionUtil.saveToSession(session1, StateConstants.IS_AUTHENTICATED, true);

            logger.log(Level.SEVERE, pKey);
            logger.log(Level.SEVERE, "Authenticated successfully");
            return pKey;

        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public String getSessionId() {
        return this.getThreadLocalRequest().getRequestedSessionId();
    }

    @Override
    public void disconnect() {
        SessionProfile sessionProfile = SessionProfile.getInstance();
        sessionProfile.dispose();

        HttpSession session1 = this.getThreadLocalRequest().getSession(false);
        SessionUtil.removeFromSession(session1, StateConstants.PUBLIC_KEY);
        SessionUtil.removeFromSession(session1, StateConstants.IS_AUTHENTICATED);
    }
}