package com.data.dataproducer.util;

import org.springframework.util.DigestUtils;

/**
 * @author danny
 * @date 2019/5/30 8:39 PM
 */
public class MD5Util {

    /**
     * MD5方法
     *
     * @param password 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * MD5验证方法
     *
     * @param password 明文
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String password, String md5) {
        String md5Text = md5(password);
        if(md5Text.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }
}
