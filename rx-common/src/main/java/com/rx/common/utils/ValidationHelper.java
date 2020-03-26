package com.rx.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    /**
     * 银行卡号一般是16位或者19位。
     * 由如下三部分构成。
     * 1,前六位是：发行者标识代码
     * 2,中间的位数是：个人账号标识（从卡号第七位开始），一般由6－12位数字组成。最多可以使用12位数字。
     * 3,最后一位是:根据卡号前面的数字,采用Luhn算法计算出的最后一位校验位
     */
    public static boolean checkBankCard(String cardId){
        if(cardId==null || cardId.trim().length()==0){
            return false;
        }
        if(cardId.length()<16 || cardId.length()>19){
            return false;
        }

        //根据Luhm法则得到校验位
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }

        //和银行卡号的校验位(最后一位)比较,相同为true 不同为false
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 该校验的过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字(0、2、4等等)，先乘以2（如果乘积为两位数，则将其减去9或个位与十位相加的和），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        //如果传的不是数字返回N
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        /**
         * 注意是从下标为0处开始的
         */
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            /**
             * 是偶数位数字做处理先乘以2（如果乘积为两位数，则将其减去9或个位与十位相加的和），再求和。
             * 不是则不做处理
             */
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
    
    
    
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }
}
