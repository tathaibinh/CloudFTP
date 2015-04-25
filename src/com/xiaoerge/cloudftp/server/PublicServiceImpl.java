package com.xiaoerge.cloudftp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xiaoerge.cloudftp.client.PublicService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/24/15.
 */
public class PublicServiceImpl extends RemoteServiceServlet implements PublicService {

    public static Logger logger = Logger.getLogger(PublicServiceImpl.class.getName());

    @Override
    public String getSessionId() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);

//        if (session.getAttribute("JSESSIONID") == null) {
//            session.setAttribute("JSESSIONID", nextSessionId());
//        }

        return (String) session.getAttribute("JSESSIONID");
    }

    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}