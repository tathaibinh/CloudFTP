package com.xiaoerge.cloudftp.server;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
        boolean zipper = Boolean.valueOf(req.getParameter("zipper"));

        HttpSession session = req.getSession(false);
        String pKey = (String) session.getAttribute(StateConstants.PUBLIC_KEY);

        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        if (!publicKey.isEmpty() && publicKey.equals(pKey)) {

            try {
                resp.setContentType("application/x-download");
                resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                if (!zipper) {
                    resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                    channelSftp.get(fileName, resp.getOutputStream());
                }
                else {
                    resp.setHeader("Content-Disposition", "attachment; filename=" + fileName+".zip");

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(channelSftp.get(fileName));

                    IOUtils.copy(bufferedInputStream, resp.getOutputStream());

//                    try {
//                        // Initiate ZipFile object with the path/name of the zip file.
//                        ZipFile zipFile = new ZipFile(fileName + ".zip");
//
//                        ZipParameters parameters = new ZipParameters();
//                        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//                        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//
//                        zipFile.addStream(channelSftp.get(fileName), parameters);
//
//                    } catch (ZipException e) {
//                        e.printStackTrace();
//                    }
                }

            } catch (SftpException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ServletException();
        }
    }
}
