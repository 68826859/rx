package com.rx.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SharUtil {
    //shar加密
    public static String shar1(String object){
        String value = null;
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(object.getBytes("UTF-8"));
            value = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return value;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
