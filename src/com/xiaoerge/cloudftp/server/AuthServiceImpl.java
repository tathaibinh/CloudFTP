package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.cloudftp.client.AuthService;
import com.xiaoerge.cloudftp.server.shared.ClientSession;
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
            ClientSession clientSession = ClientSession.getInstance();

            String username = hostname.substring(0, hostname.indexOf('@'));
            String host = hostname.substring(hostname.indexOf('@') + 1);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            clientSession.setKeyGen(keyGen);
            clientSession.setKey(key);
            clientSession.setCipher(cipher);

            byte[] cipherText = EncryptionUtil.encrypt(password);

            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            UserInfo userInfo = new UserAcountInfo(cipherText);
            session.setUserInfo(userInfo);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelsftp = (ChannelSftp) channel;

            clientSession.setJsch(jSch);
            clientSession.setAccountinfo(userInfo);
            clientSession.setSession(session);
            clientSession.setChannel(channel);
            clientSession.setChannelsftp(channelsftp);

            storeSessionKey(key.getPublic().toString());

            return key.getPublic().getEncoded();

        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Override
    public String authenticateSession() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        return (String) session.getAttribute(PUBLIC_KEY);
    }

    private void storeSessionKey(String publicKey)
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