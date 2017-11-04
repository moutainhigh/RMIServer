package com.hgsoft.ygz.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Timestamp getNowTime(){
		Timestamp d = new Timestamp(System.currentTimeMillis());
		return d; 
	}

	
	public static String dateFormatByPattern(Date now,String pattern){
		return new SimpleDateFormat(pattern).format(now);
	}

	public static String dateFormat(Date date){
		if (date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return sdf.format(date);
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.dateFormat(new Date()));
	}

}
