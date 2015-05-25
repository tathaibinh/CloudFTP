package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.ShellService;
import com.xiaoerge.cloudftp.client.model.FileEntry;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.BashProfile;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import com.xiaoerge.cloudftp.server.shared.SessionUtil;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
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

        if (validateSession()) {

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
        if (validateSession()) {
            return BashProfile.getInstance().getCwd();
        }
        else {
            return "";
        }
    }

    //validate session
    private boolean validateSession() {
        String publickey = Arrays.toString(SessionProfile.getInstance().getKey().getPublic().getEncoded());

        //can't be a new session
        HttpSession session = this.getThreadLocalRequest().getSession(false);

        String publicKey2 = (String) SessionUtil.getFromSession(session, StateConstants.PUBLIC_KEY);
        boolean is_authenticated = (boolean) SessionUtil.getFromSession(session, StateConstants.IS_AUTHENTICATED);

        return is_authenticated && publickey.equals(publicKey2);
    }
}
