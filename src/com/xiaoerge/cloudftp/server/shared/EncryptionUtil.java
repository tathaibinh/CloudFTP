package com.xiaoerge.cloudftp.server.shared;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class EncryptionUtil
{
    private static Logger logger = Logger.getLogger(EncryptionUtil.class.getName());
    private static EncryptionUtil encryptionUtil;

    private static synchronized EncryptionUtil getEncryptionUtil() {
        if (encryptionUtil == null) {
            try {
                return new EncryptionUtil();
            } catch (Exception e) {
                return null;
            }
        }
        return encryptionUtil;
    }

    public static synchronized byte[] encrypt(byte[] plainText) {
        try {
            KeyPair key = ClientSession.getInstance().getKey();
            Cipher cipher = ClientSession.getInstance().getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static synchronized byte[] decrypt(byte[] cipherText) {
        try {
            KeyPair key = ClientSession.getInstance().getKey();
            Cipher cipher = ClientSession.getInstance().getCipher();
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
