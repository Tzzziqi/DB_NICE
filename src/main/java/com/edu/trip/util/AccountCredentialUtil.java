package com.edu.trip.util;

import com.edu.trip.model.UserEntity;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

public class AccountCredentialUtil {


    private static Set<Character> specialCharForRegister = new HashSet<>() {{
        add('+');
        add('-');
        add('*');
        add('/');
        add('|');
    }};


    public static boolean checkPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        if (password.length() < 4 || password.length() > 16) {
            return false;
        }
        // must be [0-9a-zA-Z] with [+=-/]
        boolean[] compose = new boolean[4];
        for (int i = 0; i < password.length(); ++i) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') {
                compose[0] = true;
            } else if (c >= 'a' && c <= 'z') {
                compose[1] = true;
            } else if (c >= 'A' && c <= 'Z') {
                compose[2] = true;
            } else if (specialCharForRegister.contains(c)) {
                compose[3] = true;
            } else {
                return false;
            }
        }
        int parts = 0;
        for (int i = 0; i < compose.length; ++i) {
            parts++;
        }
        return parts >= 2;
    }

    public static boolean checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        if (username.length() < 4 || username.length() > 32) {
            return false;
        }
        // must be [0-9a-zA-Z] with [+=-/]
        for (int i = 0; i < username.length(); ++i) {
            char c = username.charAt(i);
            if ((c >= '0' && c <= '9') ||
                    (c >= 'a' && c <= 'z') ||
                    (c >= 'A' && c <= 'Z')) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }


    public static boolean validateRegisterInput(UserEntity user) {
        return validateRegisterInput(user.getUsername(), user.getPassword());
    }

    public static boolean validateRegisterInput(String username, String password) {
        return checkPassword(password) && checkUsername(username);

    }



    public static String md5Encrypt(String psw) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Throwable e) {
            return null;
        }
        byte[] digests = md.digest(psw.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte digest : digests) {
            sb.append(Integer.toHexString((digest & 0xFF) | 0x100));
        }
        return sb.toString();
    }

}
