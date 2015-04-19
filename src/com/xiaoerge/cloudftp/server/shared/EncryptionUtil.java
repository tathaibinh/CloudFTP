package com.xiaoerge.cloudftp.server.shared;

import com.xiaoerge.cloudftp.server.model.SessionModel;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.util.logging.Logger;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class EncryptionUtil
{
    private static Logger logger = Logger.getLogger(EncryptionUtil.class.getName());

    public static synchronized byte[] encrypt(byte[] plainText) {
        try {
            KeyPair key = SessionModel.getInstance().getKey();
            Cipher cipher = SessionModel.getInstance().getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static synchronized byte[] decrypt(byte[] cipherText) {
        try {
            KeyPair key = SessionModel.getInstance().getKey();
            Cipher cipher = SessionModel.getInstance().getCipher();
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
