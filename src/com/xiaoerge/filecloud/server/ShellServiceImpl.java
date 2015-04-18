package com.xiaoerge.filecloud.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.filecloud.client.ShellService;
import com.xiaoerge.filecloud.server.model.ClientSession;

import java.util.Vector;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class ShellServiceImpl extends RemoteServiceServlet implements ShellService {

    @Override
    public String[] ls(String path) {
        ClientSession clientSession = ClientSession.getInstance();
        ChannelSftp channelSftp = clientSession.getChannelsftp();

        try {
            Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(path);
            String[] entries = new String[lsEntries.size()];
            for (int i = 0; i < lsEntries.size(); i++)
            {
                entries[i] = lsEntries.get(i).getFilename();
            }
            return entries;
        } catch (SftpException e) {
            e.printStackTrace();
            return null;
        }
    }
}
