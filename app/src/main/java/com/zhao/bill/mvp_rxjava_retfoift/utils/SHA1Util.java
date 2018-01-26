package com.zhao.bill.mvp_rxjava_retfoift.utils;

import java.security.MessageDigest;

/**
 * sha1 加密工具类
 * Created by Bill on 2017/3/16.
 */
public class SHA1Util {

    public static String sha1Encode(String strSrc) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(strSrc.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();

            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);

                if (shaHex.length() < 2) {
                    hexString.append(0);
                }

                hexString.append(shaHex);
            }

            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}