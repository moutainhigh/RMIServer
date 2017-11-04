package com.hgsoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 正则判断工具类
 * @author gsf
 * 2016-08-12
 */
public class RegularUtil {
	/**
	 * 判断是否为数字
	 * @param param
	 * @return
	 */
	public static boolean isDigit(String param){
		if(param.matches("[0-9]+")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为车牌号码
	 * @param param
	 * @return
	 */
	public static boolean isVehiclePlate(String param){
		if((param.matches("^(([\u4e00-\u9fa5]{1})|([A-Z]{1}))[A-Z]{1}[A-Z0-9]{4}(([\u4e00-\u9fa5]{1})|([A-Z0-9]{1}))$")
				||param.matches("^WJ[0-9]{2}(([\u4e00-\u9fa5]{1})|([0-9]{1})[0-9]{4})$"))
				&&param.matches("^([\u4e00-\u9fa5]*[a-zA-Z0-9]+){6,}$")
				&&param.matches("^.{3}((?!.*O)(?!.*I)).*$")){
			return true;
		}
		return false;
	}
	
	/**
	 * 整数最多十位，小数最多两位
	 * @param param
	 * @return
	 */
	public static boolean isNumber12_2(String param){
		if(param.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为手机号
	 * @param param
	 * @return
	 */
	public static boolean isMobile(String param){
		if(param.matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为邮政编码
	 * @param param
	 * @return
	 */
	public static boolean isZipCode(String param){
		if(param.matches("^[0-9]{6}$")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为电子邮箱
	 * @param param
	 * @return
	 */
	public static boolean isEmail(String param){
		if(param.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为车辆识别代码（车架号）
	 * @param param
	 * @return
	 */
	public static boolean isIdentificationCode(String param){
		if(param.matches("^[A-Z0-9]{6,17}$")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为发动机号
	 * @param param
	 * @return
	 */
	public static boolean isVehicleEngineNo(String param){
		if(param.matches("^[A-Z0-9]+$")){
			return true;
		}
		return false;
	}
	
	public static boolean isDate(String param){
		if(param.matches("^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$")){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		//test
		//System.out.println(RegularUtil.isDigit("1")+"");
		//System.out.println(RegularUtil.isVehiclePlate("1111")+"");
		//System.out.println(RegularUtil.isNumber12_2("11111111111.1")+"");
		//System.out.println(RegularUtil.isZipCode("555555")+"");
		//System.out.println(RegularUtil.isEmail("5555551111111111111111111111111111111@163.com")+"");
		//System.out.println(RegularUtil.isMobile("13333333333")+"");
		//System.out.println(RegularUtil.isIdentificationCode("111111")+"");
		//System.out.println(RegularUtil.isVehicleEngineNo("234")+"");
		System.out.println(RegularUtil.isDate("﻿2016-06-23 17:33:45".trim())+"");
	}

	/**
	 * 格式是yyyy-MM-dd HH:mm:ss
	 * @param param
	 * @return
	 * @return boolean
	 */
	public static boolean isValidDate(String param) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
		try {
			format.setLenient(false);
			format.parse(param);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 判断是不是储值卡号
	 * @param cardNo
	 * @return
	 */
	public static boolean isPrepaid(String cardNo){
		if(cardNo.length()==16&&"22".equals(cardNo.substring(4, 6))){
			return true;
		}
		if(cardNo.length()==12&&cardNo.startsWith("860")){
			int cardTypeNum = Integer.parseInt(cardNo.substring(4,5));
			if(cardTypeNum<=6){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是不是记帐卡号
	 * @param cardNo
	 * @return
	 */
	public static boolean isAccountC(String cardNo){
		if(cardNo.length()==16&&"23".equals(cardNo.substring(4, 6))){
			return true;
		}
		if(cardNo.length()==12&&cardNo.startsWith("860")){
			int cardTypeNum = Integer.parseInt(cardNo.substring(4,5));
			if(cardTypeNum>6){
				return true;
			}
		}
		return false;
	}

	/***
	 * 校验卡号
	 * 国标卡：卡号为16位，第5,6位为22（储值卡），23（记账卡）
	 * 地标卡：卡号为12位，以860开头，第五位大于6的是记账卡，小于等于6的是储值卡
	 * @param cardNo
	 * @return
	 */
	public static boolean checkCardNo(String cardNo){
		if(!StringUtil.isNotBlank(cardNo)){
			return false;
		}else if(!isDigit(cardNo)){
			return false;
		}else if(cardNo.length()!=16&&cardNo.length()!=12){
			return false;
		}else if(cardNo.length()==16&&(!cardNo.substring(4,6).equals("22")&&!cardNo.substring(4,6).equals("23"))){
			return false;
		}else if(cardNo.length()==12&&!cardNo.startsWith("860")){
			return false;
		}
		return true;
	}

	/***
	 * 判断是否储值卡
	 * 注意事项：只能在进行卡号校验后使用
	 * 国标卡：卡号为16位，第5,6位为22（储值卡），23（记账卡）
	 * 地标卡：卡号为12位，以860开头，第五位大于6的是记账卡，小于等于6的是储值卡
	 * @param cardNo
	 * @return
	 */
	public static boolean isPrePaidCard(String cardNo){
		boolean flag = false;
		if(cardNo.length()==16){
			if(cardNo.substring(4,6).equals("22")){
				flag =true;
			}else{
				flag = false;
			}
		}else if(cardNo.length()==12&&cardNo.startsWith("860")){
			int cardTypeNum = Integer.parseInt(cardNo.substring(4,5));
			if(cardTypeNum<=6){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 判断是不是金额
	 * @param param
	 * @return
	 */
	public static boolean isMoney(String param){
		if(param.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$")){
			return true;
		}
		return false;
	}
}


