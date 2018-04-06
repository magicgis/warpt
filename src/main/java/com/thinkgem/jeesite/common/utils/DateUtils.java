/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 根据当前时间生成订单号码
	 * @fi 单据前缀
	 * @return
	 */
	public static String getDateBillCode(String fi) {
		java.util.Random random = new java.util.Random();
		int result = random.nextInt(10);
//		model.addAttribute("billcode", );
		return  fi + getDate("yyyyMMddHHmmss") + result;
	}
	

    /**
     * Description:在某个日期上增加天数.
     * 
     * @param dat 日期
     * @param days 增加天数
     * @return Date
     */
    public static Date addDate(Date dat, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dat);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();
    }
    
    /**
     * Description:获取某个日期的当前月最后一天.
     * 
     * @param dat 日期
     * @return int
     */
    public static int getLastDayOfMonth(Date dat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dat);
        return calendar.getActualMaximum(calendar.DAY_OF_MONTH);
    }
    
    public static int getDayOfDate(Date date) {
        if (null == date) {
            return 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Description:从传入日期对象中获取月份数据，当传入日期对象为<code>null</code>时，返回1.
     * 
     * @param date 日期
     * @return getInt 返回值
     */
    public static int getMonthOfDate(Date date) {
        if (null == date) {
            return 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * Description:获取周工作日
     * 
     * @param date 日期
     * @return getInt 返回值
     */
    public static int getIntDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    
    /**
     * 讲日期换算为 周几
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        if (null == date) {
            return "";
        }
        int i = DateUtils.getIntDayOfWeek(date);
        String week = "";
        switch (i) {
        case 1:
            week = "日";
            break;
        case 2:
            week = "一";
            break;
        case 3:
            week = "二";
            break;
        case 4:
            week = "三";
            break;
        case 5:
            week = "四";
            break;
        case 6:
            week = "五";
            break;
        case 7:
            week = "六";
            break;
        default:
            break;
        }
        return week;
    }
    
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		//System.out.println(formatDate(parseDate("2010/3/6")));
		
//		System.out.println(getMonthOfDate(parseDate("2017/2")));
		String curDay = "2017-2";
		int lastDay = getLastDayOfMonth(parseDate(curDay));
		String[] dayHeads = new String[lastDay];
		String[] Heads = new String[lastDay];
//		System.out.println(getLastDayOfMonth(parseDate("2017/2")));
		for(int i = 0 ; i < lastDay ; i ++){
			dayHeads[i] = i + 1 + "";
		}
//		for(int i = 0 ; i < dayHeads.length ; i ++){
//			System.out.println(i + 1);
//		}
		
		for(int i = 0 ; i < dayHeads.length ; i ++){
			String tempDay = curDay +"-" + dayHeads[i];
			System.out.println(tempDay);
			System.out.println(getDayOfWeek(parseDate(tempDay)));
		}
		
		
		
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}
}
