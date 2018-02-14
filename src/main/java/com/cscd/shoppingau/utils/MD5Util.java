package com.cscd.shoppingau.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class MD5Util {

    public static String getMD5WithSalt(String str, String salt) {
        final int substringNum = 5;
        String saltMD5 = getMD5(salt);
        if(saltMD5.length() == 32) {
            return getMD5(saltMD5.substring(substringNum) + str + saltMD5.substring(substringNum, salt.length()));
        }else {
            return getMD5(str + saltMD5);
        }
    }
	/**
     * 对字符串md5加密(小写+字母) 
     * 
     * @param str 传入要加密的字符串 
     * @return  MD5加密后的字符串 
     */  
    private static String getMD5(String str) {
        try {  
            // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            // 计算md5函数  
            md.update(str.getBytes());  
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
            return new BigInteger(1, md.digest()).toString(16);  
        } catch (Exception e) {  
           e.printStackTrace();  
           return null;  
        }  
    }

    /**
     * set salt
      * @return
     */
    public static String getSalt() {
        String s = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        String salt = "";
        for (int i =0; i < 6; i++ ) {
            int n = r.nextInt(62);
            salt += s.substring(n, n + 1);
        }
        return salt;
    }
}