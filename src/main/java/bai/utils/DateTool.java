package bai.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	private static final Calendar currentTime = Calendar.getInstance();
	/**
	 * 返回当前时间 HH:mm:ss
	 * 
	 * @return --:--:--
	 */
	public static String getTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		return simpleDateFormat.format(currentTime.getTime());
	}
	/**
	 * 返回当前日期yyyy-mm-dd
	 * 
	 * @return xxxx-xx-xx
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		return sdf.format(currentTime.getTime());
	}
	/**
	 * 返回date的时间 HH:mm:ss
	 * 
	 * @return --:--:--
	 */
	public static String getTime(Calendar calendar) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		return simpleDateFormat.format(calendar.getTime());
	}
	/**
	 * 返回date的日期 yyyy-mm-dd
	 * 
	 * @return xxxx-xx-xx
	 */
	public static String getDate(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		return sdf.format(calendar.getTime());
	}
	/**
	 * 返回date的日期 yyyy-mm-dd
	 * 
	 * @return xxxx-xx-xx
	 */
	public static String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 获得当前是星期几
	 * 
	 * @return 星期x
	 */
	public static String getWeek() {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		System.out.println(Calendar.DAY_OF_WEEK);
		return weeks[Calendar.DAY_OF_WEEK - 1];
	}
	/**
	 * 获得date的星期
	 * 
	 * @return 星期x
	 */
	public static String getWeek(Calendar calendar) {

		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 将字符串转换为日期
	 * 
	 * @param date
	 *            接受 yyyy-MM-dd的字符串
	 * @return
	 */
	public static Calendar str2Date(String date) {
		Calendar newTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newTime.setTime(sdf.parse(date));
			return newTime;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	/**
	 * 为calendar设置时间 hh:mm:ss
	 * 
	 * @param calendar
	 *            要设置时间的calendar
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return
	 */
	public static Calendar setTime(Calendar calendar, int hour, int minute, int second) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar;
	}
	/**
	 * 计算calendar与当前使时间相差的 “秒” 数值 为正表示calendar大于当前时间，为负说明calendar小于当前时间
	 * 
	 * @return 返回double类型的相差“秒”数
	 */
	public static double secondsBetween(Calendar calendar) {
		return (calendar.getTimeInMillis() - currentTime.getTimeInMillis()) / 1000;
	}
	/**
	 * 计算calendar1与calendar2时间相差的 “秒”数值
	 * 为正表示calendar1大于calendar2，为负说明calendar小于calendar2
	 * 
	 * @return 返回double类型的相差“秒”数
	 */
	public static double secondsBetween(Calendar calendar1, Calendar calendar2) {
		return (calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / 1000;

	}
	/**
	 * 计算calendar与当前使时间相差的 “分钟” 数值 为正表示calendar大于当前时间，为负说明calendar小于当前时间
	 * 
	 * @return 返回double类型的相差“分钟”数
	 */
	public static double minutesBetween(Calendar calendar) {
		return secondsBetween(calendar) / 60;
	}
	/**
	 * 计算calendar1与calendar2时间相差的 “分钟”数
	 * 为正表示calendar1大于calendar2，为负说明calendar小于calendar2
	 * 
	 * @return 返回double类型的相差“分钟”数
	 */
	public static double minutesBetween(Calendar calendar1, Calendar calendar2) {
		return secondsBetween(calendar1, calendar2) / 60;

	}
	/**
	 * 计算calendar与当前使时间相差的 “小时” 数值 为正表示calendar大于当前时间，为负说明calendar小于当前时间
	 * 
	 * @return 返回double类型相差的“小时”数
	 */
	public static double hoursBetween(Calendar calendar) {
		return minutesBetween(calendar) / 60;
	}

	/**
	 * 计算calendar1与calendar2时间相差的 “小时”数
	 * 为正表示calendar1大于calendar2，为负说明calendar小于calendar2
	 * 
	 * @return 返回double类型相差的“小时”数
	 */
	public static double hoursBetween(Calendar calendar1, Calendar calendar2) {
		return minutesBetween(calendar1, calendar2) / 60;
	}
	/**
	 * 计算calendar与当前使时间相差的 “天数” 数值 为正表示calendar大于当前时间，为负说明calendar小于当前时间
	 * 
	 * @return 返回double类型相差的“天”数
	 */
	public static double daysBetween(Calendar calendar) {
		return hoursBetween(calendar) / 24;
	}
	/**
	 * 计算calendar1与calendar2时间相差的 “小时”数
	 * 为正表示calendar1大于calendar2，为负说明calendar小于calendar2
	 * 
	 * @return 返回double类型相差的“小时”数
	 */
	public static double daysBetween(Calendar calendar1, Calendar calendar2) {
		return hoursBetween(calendar1, calendar2) / 24;
	}
	/**
	 * 查询calendar是BaseDate的第几周
	 * 
	 * @param calendar
	 * @return -1表示未设置BaseDate或calendar小于BaseDate
	 */
	public static int getWeek_Of_BaseDate(Calendar calendar) {
		try {
			return (int) DateTool.daysBetween(calendar, DateTool.str2Date(PropertyRW.getProperty("basedate"))) / 7 + 1;

		} catch (Exception e) {
			return -1;
		}
	}
	/**
	 * 查询当前周是BaseDate的第几周
	 * CalendarType
	 * @param
	 * @return -1表示未设置BaseDate或当前时间小于BaseDate
	 */
	public static int getCurrent_Week_Of_BaseDate() {
		try {
			return (int) DateTool.daysBetween(currentTime, DateTool.str2Date(PropertyRW.getProperty("basedate"))) / 7
					+ 1;
		} catch (Exception e) {
			return -1;
		}

	}

}
