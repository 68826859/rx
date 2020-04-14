package com.rx.base.utils;

import java.math.BigDecimal;

public class MathUtil {

	//默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	//构造器私有化，让这个类不能实例化
	private MathUtil(){}
	
	
	public static void main(String[] args) {
		
		System.out.println(fen2yuan(100));
		System.out.println("1.0 - 0.42 = " + MathUtil.sub(1.0, 0.42));
		System.out.println("4.015*100 = " + MathUtil.mul(4.015, 100));
		System.out.println("123.3/100 = " + MathUtil.div(123.3, 100));
	}
	
	
	public static BigDecimal fen2yuan(long fen) {
		return BigDecimal.valueOf(fen).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);
	}
	

		
	//提供精确的加法运算
	public static double add(double v1, double v2)
	{
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.add(b2).doubleValue();
	}
	//精确的减法运算
	public static double sub(double v1, double v2)
	{
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.subtract(b2).doubleValue();
	}
	//精确的乘法运算
	public static double mul(double v1, double v2)
	{
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.multiply(b2).doubleValue();
	}
	//提供（相对）精确的除法运算，当发生除不尽的情况时
	//精确到小数点后10位的数字四舍五入
	public static double div(double v1, double v2)
	{
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}	

	
}
