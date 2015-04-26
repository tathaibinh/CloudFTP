package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.sun.deploy.net.cookie.CookieUnavailableException;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.server.global.BashProfile;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import com.xiaoerge.cloudftp.server.shared.SessionUtil;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.PublicKey;
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

        if (validateCookie()) {

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
                logger.log(Level.SEVERE, e.getMessage());
                return new Vector<>();
            }
        }
        else {
            return new Vector<>();
        }
    }

    @Override
    public String pwd() {
        if (validateCookie()) {
            return BashProfile.getInstance().getCwd();
        }
        else {
            return "";
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (validateCookie()) {
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

    //validate public key store in cookie
    private boolean validateCookie() {
        PublicKey publickey = SessionProfile.getInstance().getKey().getPublic();
        Cookie[] cookies = this.getThreadLocalRequest().getCookies();
        for (Cookie cookie : cookies) {
            logger.log(Level.SEVERE, cookie.getValue().toString());
            logger.log(Level.SEVERE, publickey.toString());
            if (cookie.getName().equals("PUBLICKEY") && cookie.getValue().equals(publickey.toString())) {
                return true;
            }
        }
        logger.log(Level.SEVERE, "No public key in cookie");
        return false;
    }
}
