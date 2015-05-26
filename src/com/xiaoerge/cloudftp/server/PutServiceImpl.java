package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.jcraft.jsch.*;
import com.xiaoerge.cloudftp.client.AuthService;
import com.xiaoerge.cloudftp.client.PutService;
import com.xiaoerge.cloudftp.client.shared.StateConstants;
import com.xiaoerge.cloudftp.server.global.SessionProfile;
import com.xiaoerge.cloudftp.server.global.UserProfile;
import com.xiaoerge.cloudftp.server.shared.EncryptionUtil;
import com.xiaoerge.cloudftp.server.shared.SessionUtil;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PutServiceImpl extends XsrfProtectedServiceServlet implements PutService {

    public static Logger logger = Logger.getLogger(PutServiceImpl.class.getName());

    @Override
    public void put() {
        logger.log(Level.SEVERE, "put");
    }
}