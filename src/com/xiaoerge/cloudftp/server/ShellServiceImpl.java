package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.server.global.BashProfile;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class ShellServiceImpl extends XsrfProtectedServiceServlet implements ShellService {

    private static Logger logger = Logger.getLogger(ShellServiceImpl.class.getName());

    @Override
    public Vector<FileEntry> cd(String path) {
        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        try {
            channelSftp.cd(path);
            BashProfile.getInstance().setCwd(channelSftp.pwd());

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
            return new Vector<>();
        }
    }

    @Override
    public String pwd() {
        return BashProfile.getInstance().getCwd();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        String fileName = req.getParameter("filename");

        try {
            InputStream inputStream = channelSftp.get(fileName);

            resp.setContentType("application/x-download");
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            resp.setHeader("Content-Length", String.valueOf(inputStream.available()));//todo right length

            BufferedOutputStream bufferedOutputStream = null;

            try {
                bufferedOutputStream = new BufferedOutputStream(resp.getOutputStream());

                byte[] buffer = new byte[8192];
                for (int length = 0; (length = inputStream.read(buffer)) > 0;) {
                    bufferedOutputStream.write(buffer, 0, length);
                }
            } finally {
                if (bufferedOutputStream != null) try { bufferedOutputStream.close(); } catch (IOException ignore) {}
                if (inputStream != null) try { inputStream.close(); } catch (IOException ignore) {}
            }

            logger.log(Level.SEVERE, fileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

}
