package com.jz.nebula.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    /**
     * Generate md5 hash for given string
     *
     * @param msg
     * @return
     */
    public static String generateHash(String msg) {
        String md5Hash = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (messageDigest != null) {
                md5Hash = String.format("%032x", new BigInteger(1, messageDigest.digest(msg.getBytes())));
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return md5Hash;
    }

    /**
     * Get random hash
     *
     * @return
     */
    public static String getRandomHash() {
        return Security.generateHash(String.valueOf(System.currentTimeMillis()));
    }
}
