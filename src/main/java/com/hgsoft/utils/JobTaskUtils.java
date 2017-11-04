package com.hgsoft.utils;

public class JobTaskUtils {
	public static class Week {
		public static String obtainWeekDetial(String param) {
			int number = 0;
			try{
				number = Integer.parseInt(param);
			}
			catch(Exception e)
			{
				number = 0;
			}
			String result = "";
			switch (number) {
			case 1:
				result = "星期日";
				break;
			case 2:
				result = "星期一";
				break;
			case 3:
				result = "星期二";
				break;
			case 4:
				result = "星期三";
				break;
			case 5:
				result = "星期四";
				break;
			case 6:
				result = "星期五";
				break;
			case 7:
				result = "星期六";
				break;
			default:
				result = "请选择";
			}

			return result;

		}
	}
	
	public static class Month {
		public static String obtainMonthBackSel(String param) {
			int number = 0;
			try{
				number = Integer.parseInt(param);
			}
			catch(Exception e)
			{
				number = 0;
			}
			String result = "";
			switch (number) {
			case 1:
				result = "星期日";
				break;
			case 2:
				result = "星期一";
				break;
			case 3:
				result = "星期二";
				break;
			case 4:
				result = "星期三";
				break;
			case 5:
				result = "星期四";
				break;
			case 6:
				result = "星期五";
				break;
			case 7:
				result = "星期六";
				break;
			default:
				result = "请选择";
			}

			return result;

		}
	}
	
	public static void main(String[] args) {
//		logger.info(Month.obtainMonthBackSel("1"));
	}
}
