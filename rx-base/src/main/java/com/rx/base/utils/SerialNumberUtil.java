package com.rx.base.utils;

import com.rx.base.result.type.BusinessException;

import java.util.Calendar;
	public class SerialNumberUtil {
	private final static int DEFAULT_ORDER_LENGTH = 20;
	private static String LAST_ORDER_ID;
	private static int LAST_ORDER_INC = 0;

    public static String genSerialId(String prefix){
        //TODO 多机约束
        StringBuilder orderId = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        orderId.append(String.valueOf(calendar.get(Calendar.YEAR)).substring(2))
                .append(DateUtil.formatTime(calendar.get(Calendar.MONTH) + 1))
                .append(DateUtil.formatTime(calendar.get(Calendar.DAY_OF_MONTH)))
                .append(DateUtil.formatTime(calendar.get(Calendar.HOUR_OF_DAY)))
                .append(DateUtil.formatTime(calendar.get(Calendar.MINUTE)))
                .append(DateUtil.formatTime(calendar.get(Calendar.SECOND)))
                .append(calendar.get(Calendar.MILLISECOND));
        String ordStr = orderId.toString();
        if (ordStr.equals(LAST_ORDER_ID)){
            ordStr += LAST_ORDER_INC;
            LAST_ORDER_INC++;
        }else{
            LAST_ORDER_ID = ordStr;
            LAST_ORDER_INC = 0;
        }
        if (StringUtil.isNull(prefix)){
            return formatOrderId(ordStr);
        }else {
            return prefix + "-" + formatOrderId(ordStr);
        }

    }

    public static String genSerialId(){
        return genSerialId("");
    }
	private static String formatOrderId(String autoOrderId){
	    StringBuilder orderId = new StringBuilder(autoOrderId);
        int strLen = orderId.length();
	    if (strLen < DEFAULT_ORDER_LENGTH){
            for (int i = 0; i < DEFAULT_ORDER_LENGTH - strLen; i++) {
                orderId.insert(0, "0");
            }
        }
        if (orderId.length() > DEFAULT_ORDER_LENGTH){
            throw new BusinessException("订单单号生成异常");
        }
	return orderId.toString();
	}
	public static void main(String[] args){
        for (int i = 0; i < 100 * 100; i++) {
            System.out.println(genSerialId());
        }
    }

}
