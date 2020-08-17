package com.pig4cloud.pigx.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 *
 */
public final class DateUtils {
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_MINUTE = "HH:mm";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 根据预设格式返回当前日期
	 *
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 *
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用预设格式格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String formatFORMATFULL(Date date) {
		return format(date, FORMAT_FULL);
	}


	/**
	 * 使用用户格式格式化日期
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期
	 *
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	public static Date parse2(String strDate) {
		return parse(strDate, FORMAT_SHORT);
	}


	/**
	 * 使用用户格式提取字符串日期
	 *
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 *
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 *
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 *
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 *
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}
	public static String getDateTimeaddMinus(long minus) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date afterDate = new Date(new Date().getTime() + minus*1000);
		return sdf.format(afterDate);
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(int time){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);
		String time_Date = sdf.format(new Date(time * 1000L));
		return time_Date;
	}

	/**
	 * 时间比较大小
	 */
	public static boolean compareDate(String beginTime, String endTime){
		int num = beginTime.compareTo(endTime);
		if (num < 0){
			return false;
		}else {
			return true;
		}
	}
	//获取当前月第一天
	public static String getFirstDayOfThisMonth() {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return myFormatter.format(cal.getTime());
	}
	public static String getFirstDayOfGivenMonth(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.add(Calendar.MONTH, 0);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取最后一天
	public static String getLastDayOfMonth(String yearMonth) {
		int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
		int month = Integer.parseInt(yearMonth.split("-")[1]); //月
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		// cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.MONTH, month); //设置当前月的上一个月
		// 获取某月最大天数
		//int lastDay = cal.getActualMaximum(Calendar.DATE);
		int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
		// 设置日历中月份的最大天数
		//cal.set(Calendar.DAY_OF_MONTH, lastDay);
		cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	//日期加一个月
	public static String getDateForPlus(Date pdate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date endDate=new Date();
		//System.out.println(sdf.format(endDate)+"=================");
		Calendar date=Calendar.getInstance();
		date.setTime(endDate);
		date.set(Calendar.MONTH, date.get(Calendar.MONTH)+1);
		//Date startDate=null;
		String returnDate = null;

		returnDate = sdf.format(date.getTime());
			//startDate = sdf.parse(sdf.format(date.getTime()));
		return returnDate;
	}
	/**
	 * 给指定日期加一年
	 * @param date
	 * @param addyear
	 * @return
	 */
	public static String getNextYear(String date,int addyear) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = dateFormat.parse(date);
		} catch (ParseException e) {
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.YEAR, addyear);
		String returnDate = dateFormat.format(cal.getTime());
		return returnDate;
	}

	//日期加一个月
	public static String getMonthPlus(String sdate,int month){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = sdf.parse(sdate);
		} catch (ParseException e) {
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(parse);
		calendar.add(Calendar.MONTH, month); //把日期往后增加一个月，整数往后推，负数往前移
		parse = calendar.getTime();
		String stringDate = sdf.format(parse);//date-->String
		return stringDate;
	}
	//日期加一个月
	public static String getDayPlus(String sdate,int day){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = sdf.parse(sdate);
		} catch (ParseException e) {
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(parse);
		calendar.add(Calendar.DAY_OF_MONTH, 1); //把日期往后增加一天，整数往后推，负数往前移
		parse = calendar.getTime();
		String stringDate = sdf.format(parse);//date-->String
		return stringDate;
	}


	public static Integer getWeek(String dates) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(dates);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w == 0) w = 7;
		return w;
	}

	/*public static void  main(String[]	s){
		System.out.println(stampToDate(0));
	}*/
	/*
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	String stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);

	Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1); //把日期往后增加一年，整数往后推，负数往前移
	date = calendar.getTime();
	stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);

        calendar.add(Calendar.MONTH, 1); //把日期往后增加一个月，整数往后推，负数往前移
	date = calendar.getTime();
	stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);

        calendar.add(Calendar.WEEK_OF_MONTH, 1); //把日期往后增加一周，整数往后推，负数往前移
	date = calendar.getTime();
	stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);

        calendar.add(Calendar.DAY_OF_MONTH, 1); //把日期往后增加一天，整数往后推，负数往前移
	date = calendar.getTime();
	stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);
*/
}