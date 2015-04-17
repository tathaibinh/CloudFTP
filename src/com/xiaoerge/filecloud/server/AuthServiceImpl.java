package com.xiaoerge.filecloud.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.filecloud.client.AuthService;
import com.xiaoerge.filecloud.server.model.ChannelSession;
import com.xiaoerge.filecloud.server.model.EncryptionUtil;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {

    private String username;
    private String password;
    private String host;
    private int port;

    @Override
    public String authenticate(String hostname, byte[] password, int port) {

        try {
            ChannelSession channelSession = ChannelSession.getInstance();

            String username = hostname.substring(0, hostname.indexOf('@'));
            String host = hostname.substring(hostname.indexOf('@') + 1);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            channelSession.setKeyGen(keyGen);
            channelSession.setKey(key);
            channelSession.setCipher(cipher);

            byte[] cipherText = EncryptionUtil.encrypt(password);

            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            UserInfo userInfo = new UserAcountInfo(cipherText);
            session.setUserInfo(userInfo);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelsftp = (ChannelSftp) channel;

            channelSession.setJsch(jSch);
            channelSession.setAccountinfo(userInfo);
            channelSession.setSession(session);
            channelSession.setChannel(channel);
            channelSession.setChannelsftp(channelsftp);

            return key.getPublic().toString();

        } catch (Exception e) {
            return "";
        }
    }
}