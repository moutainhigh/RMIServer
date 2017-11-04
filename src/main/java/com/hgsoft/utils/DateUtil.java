package com.hgsoft.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	/**
	 * 日期差
	 * @param  p_start 开始日期
	 * @param  p_end 结束日期
	 * @author gaosiling
	 */
    public static List<String> getDates(Calendar p_start, Calendar p_end,String type) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	List<String> result = new ArrayList<String>();
    	
        Calendar temp = Calendar.getInstance();
        temp.setTime(p_start.getTime());
       
        if("1".equals(type)){
        	result.add(sdf.format(p_start.getTime()));
        }
        
        int i=0;
        while (temp.before(p_end)) {
        	if(i!=0){
        		result.add( sdf.format(temp.getTime()));
        	}
            temp.add(Calendar.DAY_OF_YEAR, 1);
            i++;
        }
        return result;
    }
    
    public static String getPreDay(String settleDay) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = null;
			date = sdf.parse(settleDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = calendar.getTime();
			settleDay = sdf.format(date);
			return settleDay;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static String getPreDay(String settleDay,String pattern){
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = null;
			date = sdf.parse(settleDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = calendar.getTime();
			settleDay = sdf.format(date);
			return settleDay;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
    public static Date addSecond(Date date,int s){
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		Date d=null;
		try {
			d = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		c.add(Calendar.SECOND, s);
		return c.getTime();
    }
    /**
	 * 获得子账户有效期，即最大不超过5年
	 * @param date
	 * @return
	 */
	public static Date getValidity(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		date = new Date();
		String strDate = format.format(date);
		String strYear = strDate.substring(0, 4);
		String year = Long.parseLong(strYear) + 5 + "";
		strDate = strDate.replace(strYear, year);
		// System.out.println(strDate);

		Date validity = null;
		try {
			validity = format.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return validity;
	}
	
	/**
	 * 根据YYYYMM获得当月有多少天
	 * @param yearMonth
	 * @return
	 */
	public static int getDaysByYearMonth(String yearMonth){
		int days = 0;
		String month = yearMonth.substring(4);
		String year = yearMonth.substring(0, 4);
		if("0".equals(month.substring(0, 1))){
			month = month.substring(1);
		}
		switch(Integer.parseInt(month)){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 2:
			days = isLeapYear(Integer.parseInt(year))?29:28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days=30;
		}
		return days;
	}
	/**
	 * 判断是否为闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year){
		if(year%100 == 0){
			if(year%400 == 0)return true;
		}else{
			if(year%4 == 0)return true;
		}
		return false;
	}
	
	public static String formatDate(Date date,String formateString){
		SimpleDateFormat format = new SimpleDateFormat(formateString);
		return format.format(date);
	}
	
	/** 
     * 使用预设格式将字符串转为Date 
     */  
    public static Date toDate(String strDate) throws ParseException  
    {  
        return StringUtils.isBlank(strDate) ? null : toDate(strDate,  
                "yyyy-MM-dd");  
    }  
  
    /** 
     * 使用参数Format将字符串转为Date 
     */  
    public static Date toDate(String strDate, String pattern)  
            throws ParseException  
    {  
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
                pattern).parse(strDate);  
    } 
    
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);    
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
            //System.out.println("相隔的天数="+day);   
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }   
        return day;
    }

	public static Date addDay(Date date, Integer expireTime) {
		return addTime(date,expireTime,Calendar.DATE);
	}
	
	public static Date addHour(Date date ,Integer expireTime){
		return addTime(date,expireTime,Calendar.HOUR);
	}
	
	public static Date addMinute(Date date,Integer expireTime){
		return addTime(date,expireTime,Calendar.MINUTE);
	}
	
	
	private static Date addTime(Date date ,Integer expireTime,Integer field){
		Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		Date d=null;
		try {
			d = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		c.add(field, expireTime);
		return c.getTime();
	}

	/**
	 * 返回格式为：2014/11/06 16:40:40
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 返回格式为：yyyyMMddHHssmm
	 * @param date
	 * @return
	 */
	public static String dateFormatYMDHSM(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	/*** 
     * 日期月份加减 
     *  
     */ 
	 public static String dateFormat(Date date,int i) {  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
         Calendar cl = Calendar.getInstance();  
         cl.setTime(date);  
         cl.add(Calendar.MONTH, i);  
         date = cl.getTime();  
         return sdf.format(date);  
     }

	public static Date string2Date(String dateString,String formatStr){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			Date date = sdf.parse(dateString);
			return  date;
		}catch (ParseException e){
			System.out.println(e.getMessage());
		}
		return  null;
	}

	public static void main(String[] args) {
		Date date = string2Date("20170926105902","yyyyMMddHHmmss");
		System.out.println(date);
	}
}
