package com.xiaoerge.cloudftp.server;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.SessionProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/26/15.
 */
public class GetServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GetServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //these are from http request parameters
        String fileName = req.getParameter("filename");
        String publicKey = req.getParameter("publickey");
        String csrfToken = req.getParameter("csrftoken");

        HttpSession session = req.getSession(false);
        String pKey = (String) session.getAttribute(StateConstants.PUBLIC_KEY);

        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        if (!publicKey.isEmpty() && publicKey.equals(pKey)) {

            try {
                resp.setContentType("application/x-download");
                resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                //resp.setHeader("Content-Length", fileSize);

                channelSftp.get(fileName, resp.getOutputStream());

            } catch (SftpException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ServletException();
        }
    }
}
