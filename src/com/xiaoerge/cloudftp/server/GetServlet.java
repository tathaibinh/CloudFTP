package com.xiaoerge.cloudftp.server;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.server.global.SessionProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/26/15.
 */
public class GetServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GetServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        String fileName = req.getParameter("filename");

        try {
            BufferedInputStream inputStream = new BufferedInputStream(channelSftp.get(fileName));

            resp.setContentType("application/x-download");
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            resp.setHeader("Content-Length", String.valueOf(inputStream.available()));

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
