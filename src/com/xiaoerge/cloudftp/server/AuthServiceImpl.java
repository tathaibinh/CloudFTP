package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.cloudftp.client.AuthService;
import com.xiaoerge.cloudftp.server.model.UserModel;
import com.xiaoerge.cloudftp.server.model.SessionModel;
import com.xiaoerge.cloudftp.server.shared.EncryptionUtil;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {

    private static String PUBLIC_KEY = "PUBLIC_KEY";
    private String username;
    private String password;
    private String host;
    private int port;

    @Override
    public byte[] authenticate(String hostname, byte[] password, int port) {

        try {
            SessionModel sessionModel = SessionModel.getInstance();

            String username = hostname.substring(0, hostname.indexOf('@'));
            String host = hostname.substring(hostname.indexOf('@') + 1);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            sessionModel.setKeyGen(keyGen);
            sessionModel.setKey(key);
            sessionModel.setCipher(cipher);

            byte[] cipherText = EncryptionUtil.encrypt(password);

            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            UserInfo userInfo = new UserModel(cipherText);
            session.setUserInfo(userInfo);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelsftp = (ChannelSftp) channel;

            sessionModel.setJsch(jSch);
            sessionModel.setAccountinfo(userInfo);
            sessionModel.setSession(session);
            sessionModel.setChannel(channel);
            sessionModel.setChannelsftp(channelsftp);

            storeSessionKey(key.getPublic().toString().getBytes());

            return key.getPublic().getEncoded();

        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Override
    public byte[] authenticateSession() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        return ((String) session.getAttribute(PUBLIC_KEY)).getBytes();
    }

    private void storeSessionKey(byte[] publicKey)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(PUBLIC_KEY, publicKey);
    }

    private void deleteSessionKey()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute(PUBLIC_KEY);
    }
}