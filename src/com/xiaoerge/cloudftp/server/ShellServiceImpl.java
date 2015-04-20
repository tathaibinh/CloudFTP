package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.server.model.SessionModel;

import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class ShellServiceImpl extends RemoteServiceServlet implements ShellService {

    private static Logger logger = Logger.getLogger(ShellServiceImpl.class.getName());

    @Override
    public Vector<FileEntry> cd(String path) {
        SessionModel sessionModel = SessionModel.getInstance();
        ChannelSftp channelSftp = sessionModel.getChannelsftp();

        try {
            channelSftp.cd(path);
            Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(channelSftp.pwd());
            Vector<FileEntry> entries = new Vector<>();
            for (ChannelSftp.LsEntry lsEntry : lsEntries) {
                String fileName = lsEntry.getFilename();

                //hiddne file
//                if (fileName.startsWith(".")) {
//                    continue;
//                }

                FileEntry fileEntry = new FileEntry();
                fileEntry.setFileName(fileName);
                fileEntry.setIsDir(lsEntry.getAttrs().isDir());
                entries.add(fileEntry);
            }
            return entries;
        } catch (SftpException e) {
            e.printStackTrace();
            return null;
        }
    }
}
