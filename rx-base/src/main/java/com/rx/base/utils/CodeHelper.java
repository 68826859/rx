package com.rx.base.utils;
import com.rx.base.utils.StringUtil;
import com.rx.base.utils.UUIDHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CodeHelper {

    private static Map<String, String> filts = new HashMap<String, String>() {
        {
            put("0","6");
            put("1","8");
            put("O","R");
            put("I","2");
        }
    };

    /**
     * 生成10位大写码：2位校验位+8位唯一码((去除1，I, o, O))
     * @return
     */
    public static String getCode(){
        String plaintext = StringFilter(UUIDHelper.getUUID8());
        String checkDigit = StringFilter(UUIDHelper.getCheckDigit(plaintext));
        String result = checkDigit + plaintext;
        return result;
    }

    /**
     * 字符串校验
     * @param value
     * @return  Boolean
     */
    public static boolean verify( String value ){
        String upperCase = value.toUpperCase();
        boolean result = false;
        if( StringUtil.noNull(upperCase) && upperCase.length() >= 3 ){
            String checkDigit = upperCase.substring(0,2);
            String plaintext = upperCase.substring(2,upperCase.length());
            String ciphertext = StringFilter( UUIDHelper.getCheckDigit(plaintext) );
            if( StringUtil.equals( checkDigit, ciphertext) ){
                result = true;
            }
        }
        return result;
    }


    /**
     *  转大写、过滤字符
     * @param target
     * @return
     */
    public static String StringFilter(String target){
        String upperCase = target.toUpperCase();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i< upperCase.length(); i++){
            String c = Character.toString(upperCase.charAt(i));
            String fc = filts.get(c);
            if( StringUtil.noNull(fc) ) result.append(fc);
            else result.append(c);
        }
        return result.toString();
    }


}
