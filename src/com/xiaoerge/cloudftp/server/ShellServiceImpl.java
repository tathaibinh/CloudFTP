package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.server.model.SessionModel;
import org.apache.commons.io.FileUtils;

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

                FileEntry fileEntry = new FileEntry();
                fileEntry.setFileName(lsEntry.getFilename());
                fileEntry.setLongName(lsEntry.getLongname());
                fileEntry.setPermissionString(lsEntry.getAttrs().getPermissionsString());
                fileEntry.setSizeString(FileUtils.byteCountToDisplaySize(lsEntry.getAttrs().getSize()));
                fileEntry.setIsDir(lsEntry.getAttrs().isDir());
                fileEntry.setIsLink(lsEntry.getAttrs().isLink());
                entries.add(fileEntry);
            }
            return entries;
        } catch (SftpException e) {
            e.printStackTrace();
            return null;
        }
    }
}
