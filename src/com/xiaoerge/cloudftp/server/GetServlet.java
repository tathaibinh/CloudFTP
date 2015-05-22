package com.xiaoerge.cloudftp.server;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                } else {
                    resp.setHeader("Content-Disposition", "attachment; filename=" + fileName+".zip");

                    Vector<ChannelSftp.LsEntry> entries = channelSftp.ls(channelSftp.pwd());
                    ChannelSftp.LsEntry entry = null;
                    for (ChannelSftp.LsEntry e : entries) {
                        if (e.getFilename().equals(fileName)) {
                            entry = e;
                            break;
                        }
                    }

                    String tempPath = "/tmp";

                    //it is okay for lcd to remain /tmp
                    channelSftp.lcd(tempPath);

                    get(channelSftp.pwd(), entry);

                    zipFolder(tempPath + "/" + fileName, tempPath + "/" + fileName + ".zip");

                    String pwd = channelSftp.pwd();
                    channelSftp.cd(tempPath);
                    channelSftp.get(fileName+".zip", resp.getOutputStream());
                    channelSftp.cd(pwd);
                }

            } catch (SftpException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new ServletException();
        }
    }

    private void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    /*
    * this block is from
    * https://github.com/xiaoerge/File-UI/blob/master/src/fileui/GUI.java#L364
    * */
    private void get(String path, ChannelSftp.LsEntry entry) throws SftpException {

        SessionProfile sessionProfile = SessionProfile.getInstance();
        ChannelSftp channelSftp = sessionProfile.getChannelsftp();

        if (!entry.getAttrs().isDir()) {
            channelSftp.get(entry.getFilename(), channelSftp.lpwd());

        } else if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
            //cd into the dir
            channelSftp.cd(path + "/" + entry.getFilename());

            File file = new File(channelSftp.lpwd() + "/" + entry.getFilename());
            file.mkdir();

            channelSftp.lcd(file.getAbsolutePath());

            Vector vec = channelSftp.ls(channelSftp.pwd());

            for (Object o : vec) {
                if (o instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry local_entry = (ChannelSftp.LsEntry) o;

                    if (!local_entry.getFilename().equals(".") && !local_entry.getFilename().equals("..")) {
                        get(channelSftp.pwd(), local_entry);
                    }
                }
            }

            channelSftp.cd(path);
            channelSftp.lcd(file.getParent());

        } else {
            System.err.println("Error get");
        }
    }
}
