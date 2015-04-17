package com.xiaoerge.filecloud.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.filecloud.client.AuthService;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {

    private JSch jsch;
    private Session session;
    private Channel channel;
    private ChannelSftp channelsftp;
    private String username;
    private String password;
    private String host;
    private int port;
    private UserInfo accountinfo;

    public AuthServiceImpl() {
        jsch = new JSch();
        session = null;
        accountinfo = null;
        channel = null;
    }

    @Override
    public boolean authenticate(String hostname, char[] password, int port) {

        String username = hostname.substring(0, hostname.indexOf('@'));
        String host = hostname.substring(hostname.indexOf('@') + 1);

        try {
            session = jsch.getSession(username, host, port);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelsftp = (ChannelSftp) channel;

            return true;

        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }
    }
}