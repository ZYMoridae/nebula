package com.jz.nebula.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    private static final Logger logger = LogManager.getLogger(Security.class);

    /**
     * Generate md5 hash for given string
     *
     * @param msg
     *
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

    /**
     * Encrypt password with given encoder
     *
     * @param encoderClass
     * @param credential
     *
     * @return
     */
    public static String encryptPassword(Class encoderClass, String credential) {
        String encodedCredential = credential;
        try {
            PasswordEncoder passwordEncoder = (PasswordEncoder) encoderClass.newInstance();
            encodedCredential = passwordEncoder.encode(credential);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (encodedCredential.equals(credential)) {
            logger.warn("encryptPassword:: encrypt failed due to invalid password encoder");
        }

        return encodedCredential;
    }
}
