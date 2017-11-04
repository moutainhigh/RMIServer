package com.hgsoft.ygz.util;

import org.apache.commons.lang.StringUtils;

/**
 * 卡号工具类
 * @author saint-yeb
 *
 */
public class CardNoUtil {
	
	/**
	 * 判断是否联名记账卡
	 * @param cardNo
	 * @return
	 */
	public static boolean isJointAccountCard(String cardNo){
		if(StringUtils.isNotBlank(cardNo)){
			if(isAccountCard(cardNo)&&getCardClass(cardNo).equals("2")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取卡片种类
	 * 0代表标准粤通卡
	 * 1代表快易通粤通卡
	 * 2代表粤通卡银行联名卡
	 * 3代表粤通宝V2.0卡
	 * 4代表澳门通粤通卡
	 * 5-9待定
	 * @param cardNo
	 * @return
	 */
	public static String getCardClass(String cardNo){
		return cardNo.substring(7,8);
	}
	
	/**
	 * 获取卡片类型
	 * 22 储值卡
	 * 23 记账卡
	 * @param cardNo
	 * @return
	 */
	public static String getCardType(String cardNo){
		return cardNo.substring(4,6);
	}
	
	/**
	 * 是否记账卡
	 * @param cardNo
	 * @return
	 */
	public static boolean isAccountCard(String cardNo){
		if(getCardType(cardNo).equals("23")){
			return true;
		}
		return false;
	}
 
	
	public static void main(String[] args) {
		System.out.println("是否联名记账卡："+isJointAccountCard("1639221609020426"));
		System.out.println("是否记账卡："+isAccountCard("1639221609020426"));
	}
}
