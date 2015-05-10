package com.xiaoerge.cloudftp.server;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.server.global.BashProfile;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Vector;
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
            resp.setContentType("application/x-download");
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            resp.setHeader("Content-Length", String.valueOf(inputStream.available()));

            channelSftp.get(fileName, resp.getOutputStream());

            logger.log(Level.SEVERE, fileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
