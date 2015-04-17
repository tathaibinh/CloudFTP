package com.xiaoerge.filecloud.server.model;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiaoerge on 4/17/15.
 */
public class EncryptionUtil
{
    private static EncryptionUtil encryptionUtil;

    private static EncryptionUtil getEncryptionUtil() {
        if (encryptionUtil == null) {
            try {
                return new EncryptionUtil();
            } catch (Exception e) {
                return null;
            }
        }
        return encryptionUtil;
    }

    public static byte[] encrypt(byte[] plainText) {
        try {
            KeyPair key = ChannelSession.getInstance().getKey();
            Cipher cipher = ChannelSession.getInstance().getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] decrypt(byte[] cipherText) {
        try {
            KeyPair key = ChannelSession.getInstance().getKey();
            Cipher cipher = ChannelSession.getInstance().getCipher();
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
