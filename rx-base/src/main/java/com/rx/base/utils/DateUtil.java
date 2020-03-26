package com.rx.base.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class DateUtil {
	
	public final static String DEFAULT_DATE_TIME_MS_FORMART = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DEFAULT_DATE_TIME_FORMART = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 返回当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String textForNow(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 返回当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String textForNow(String pattern, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String textForDate(String pattern, String date) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String format = dateFormat.format(dateFormat.parse(date));
		return format;
	}

	/**
	 * 数据库时间类型,数据库显示时、分、秒
	 * 
	 * @param date 时间类型
	 * @return 返回Timestamp时间类型
	 */
	public static Timestamp getTimestampDate(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @author LiuQing
	 * @return String
	 */
	public static String textForNow() {
		String time = textForNow(DEFAULT_DATE_TIME_FORMART);
		String cTime = time.substring(0, 19);
		return cTime;
	}

	/**
	 * 获取当前时间
	 * 
	 * @author LiuQing
	 * @return String
	 */
	public static String subStrTime(Date time) {
		String date = textForNow(DEFAULT_DATE_TIME_FORMART, time);
		String cTime = date.substring(0, 19);
		return cTime;
	}

	/**
	 * 转换字符串格式为Timestamp时间格式
	 * 
	 * @param dateStr 时间字符串格式
	 * @return Timestamp时间类型
	 */
	public static Timestamp parseStrDate(String dateType, String dateStr) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
		Timestamp date = null;
		date = getTimestampDate(dateFormat.parse(dateStr));
		return date;
	}

	/**
	 * 日期相加减,接收要操作的年数
	 * 
	 * @param year 操作天数
	 * @return 时间类型
	 */
	public static Date calendarAdd(int year) {
		Calendar cal = Calendar.getInstance();// 获取该实例
		cal.add(Calendar.YEAR, year); // 从当前的年数中减去/加传入的年数
		return cal.getTime();// 获取减去/加去后的结果
	}

	public static Date parseToDate(String dateStr, String dateType)  throws Exception {

		SimpleDateFormat inSdf = new SimpleDateFormat(dateType);

		Date dateS = inSdf.parse(dateStr);
		return dateS;
	}

	public static Date addDate(Date dateNow, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow);

		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	public static Date addMonth(Date dateNow, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow);

		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	public static Date calendarAdd(Date dateNow, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow);

		c.add(Calendar.SECOND, second);
		return c.getTime();
	}

	/**
	 * 比较两个日期的大小 date1和date2格式:yyyy-mm-dd hh:mi Jul 6, 2009 9:29:37 AM tian
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(String date1, String date2) {
		boolean flag = false;
		if (covertTimeToLong(date1) < covertTimeToLong(date2)) {
			flag = false;
		} else if (covertTimeToLong(date1) >= covertTimeToLong(date2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 将字符串日期2009-7-3 01:01转换成long型毫秒数 Jul 3, 2009 2:52:32 PM tian
	 * 
	 * @param time
	 * @return
	 */
	/**
	 * 两个GregorianCalendar的构造函数可以用来处理时间。前者创建一个表示日期，小时和分钟的对象：
	 * 
	 * GregorianCalendar(int year, int month, int date, int hour, int minute)
	 * 第二个创建一个表示一个日期，小时，分钟和秒：
	 * 
	 * GregorianCalendar(int year, int month, int date, int hour, int minute, int
	 * second)
	 * 
	 */

	public static long covertTimeToLong(String time) {
		long ll = 0l;
		int yy = 0;
		int mm = 0;
		int dd = 0;
		int hh = 0;
		int mi = 0;
		if (time != null && !"".equals(time)) {// 可以根据自己的参数进行判断控制
			yy = Integer.parseInt(time.substring(0, 4));
			mm = Integer.parseInt(time.substring(5, time.lastIndexOf("-")));
			dd = Integer.parseInt(time.substring(time.lastIndexOf("-") + 1, time.length() - 6));
			hh = Integer.parseInt(time.substring(time.length() - 5, time.indexOf(":")));
			mi = Integer.parseInt(time.substring(time.length() - 2));
			GregorianCalendar gc = new GregorianCalendar(yy, mm, dd, hh, mi);
			Date d = gc.getTime();
			ll = d.getTime();
		} else {
			ll = Long.MAX_VALUE;
		}
		return ll;
	}

	/**
	 * 得到天数差
	 * 
	 * @author BHF
	 * @return
	 */
	public static Integer getDayComparison(String startTime, String endTime)  throws Exception {
		SimpleDateFormat simple = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.CANADA);
		Date param_1 = null;
		Date param_2 = null;
		param_1 = simple.parse(endTime);
		param_2 = simple.parse(startTime);
		Long day = (param_1.getTime() - param_2.getTime()) / 3600 / 24 / 1000;
		return new Integer((day + 1) + "").intValue();
	}

	/**
	 * 得到天数差
	 * 
	 * @author BHF
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer getDayComparison(Date startDate, Date endDate)  throws Exception {
		SimpleDateFormat simple = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.CANADA);
		String startTime = textForNow(DEFAULT_DATE_FORMAT, startDate);
		String endTime = textForNow(DEFAULT_DATE_FORMAT, endDate);
		Date startParse = simple.parse(startTime);
		Date endParse = simple.parse(endTime);
		Long day = (endParse.getTime() - startParse.getTime()) / 3600 / 24 / 1000;
		return new Integer((day + 1) + "");
	}

	/**
	 * 返回时间范围的时间差
	 * 
	 * @param date1 开始时间
	 * @param date2 结束时间
	 * @param stype 1、天数、2月数、3年数
	 * @return
	 */
	public static int compareDate(Date date1, Date date2, int stype) {
		int n = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(date1);
			c2.setTime(date2);
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;

		if (stype == 2) {
			n = n / 365;
		}

		// System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
		return n;
	}

	/**
	 * 去掉时分秒毫秒
	 * 
	 * @return
	 */
	public static Date trimTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

    /**
     * minuteDiff
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int getMinuteDiff(Date beforeDate,Date afterDate) {
        long after = afterDate.getTime();
        long before = beforeDate.getTime();
        return  (int) ((after - before) / (1000 * 60));
    }

	public static void main(String[] args)  throws Exception  {
//		System.out.println(DateUtil.getDayComparison("2012-12-01", "2012-12-26"));
//		getDayComparison(new Date(), new Date());
//        System.out.println(compareDate(new Date(), new Date(), 1));
		String yyyy = DateUtil.textForNow("yyyy", new Date());
		System.out.println(yyyy);
	}
	
	
	/**
	 * 格式化月/日/时/分
	 */
	public static String formatTime(int time) {
		StringBuilder strBud = new StringBuilder();
		strBud.append(time);
		if (strBud.length() < 2) {
			strBud.delete(0, strBud.length());
			strBud.append("0");
			strBud.append(time);
		}
		return strBud.toString();
	}

	/**
	 * 返回系统当前日期 YYYY-MM-DD
	 */
	public static String getNowDate() {
		Calendar currentDay = new GregorianCalendar();
		int today = currentDay.get(Calendar.DAY_OF_MONTH);
		String strToday = formatTime(today);
		int month = currentDay.get(Calendar.MONTH) + 1;
		String strMonth = formatTime(month);
		int year = currentDay.get(Calendar.YEAR);
		String strCurrentDay = year + "-" + strMonth + "-" + strToday;
		return strCurrentDay;
	}

	/**
	 * 返回系统当前日期 yyyyMMddHHmmss
	 */
	public static String getNowDateString() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String strCurrentDay = df.format(now);
		return strCurrentDay;
	}

	/**
	 * 返回年的时间差，计算开始时间到结束时间的年时间差
	 */
	public static int getYearsBetween(Date startDate, Date endDate)  {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar= Calendar.getInstance();
		endCalendar.setTime(endDate);
		return (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR));
	}
	/**
	 * 返回月时间差，计算开始时间到结束时间的月时间差
	 */
	public static int getMonthsBetween(Date startDate,Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar= Calendar.getInstance();
		endCalendar.setTime(endDate);
		int result = getYearsBetween(startDate, endDate) * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);

	}
}
