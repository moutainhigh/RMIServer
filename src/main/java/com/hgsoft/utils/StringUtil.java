package com.hgsoft.utils;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil {
	
	private static final String UNITOLL = "unitoll";
	
	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isNotBlank(String string){
		if(string!=null&&!string.equals("")){
			return true;
		}
		return false;
	}
	

	
	public static boolean isEquals(String str1,String str2){
		boolean flag=false;
		if(str1==null&&str2==null){
			flag=true;
		}else if(str1!=null&&str1.equals(str2)){
			flag=true;
		}
		return flag;
	}

	public static String returnMessage(String content, String backurl) {
		return "<script type='text/javascript'>alert('"+content+"');window.location.href = '"+backurl+"';</script>";
	}

	
	/**
	 * 生成客户号(格式：年月日时分秒+5位随机数+1位校验数，共20位)
	 * @return
	 * @author lzl
	 */
	public static String generateUserNo(){
		StringBuffer userNo = new StringBuffer();
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		userNo.append(fomat.format(date));
		int randomNum = 0;
		while(randomNum < 10000){
			randomNum = (int)((Math.random()*100000));
		}
		//System.out.println("randomNum: " + randomNum);
		userNo.append(randomNum);
		Luhn luhn = new Luhn(userNo.toString());
		int checkSum = luhn.getCheckSum();
		//System.out.println(checkSum);
		userNo.append(checkSum);
		return userNo.toString();
	}
	
	/**
	 * 获取MD5加密哈稀值
	 * @param str
	 * @return hashText
	 */
	public static String md5(String str) {
		str = UNITOLL+str;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1,md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new ApplicationException("MD5加密错误");
		}
	}
	
	/**
	 * 获得记帐卡序号
	 * 
	 * @param subAccountInfo	该子账户对象必须是某一客户下的记帐卡子账户
	 * @return 记帐卡序号
	 */
	public static String getSerailNumber(SubAccountInfo subAccountInfo){
		
		//SubAccountInfo subAccountInfo = subAccountInfoDao.findLastDateSub();
		//System.out.println(subAccountInfo.getSubAccountNo());
		String subAccountNo = null;
		if(subAccountInfo == null){
			return "001";
		}else{
			subAccountNo = subAccountInfo.getSubAccountNo();
			//截取最后三位
			Long serailNum = Long.parseLong(subAccountNo.substring(subAccountNo.length()-3));
			Long newSerailNum = serailNum + 1;
			System.out.println(newSerailNum);
			if(newSerailNum<100 && newSerailNum>=10){
				return "0"+newSerailNum;
			}else if(newSerailNum<10){
				return "00"+newSerailNum;
			}else{
				return newSerailNum + "";
			}
		
		}
		
	}
	
	 public static int length(String s) {  
	       if (s == null)  
	           return 0;  
	       char[] c = s.toCharArray();  
	       int len = 0;  
	       for (int i = 0; i < c.length; i++) {  
	           len++;  
	           if (!isLetter(c[i])) {  
	               len++;  
	           }  
	       }  
	       return len;  
	 } 
	 
	 public static boolean isLetter(char c) {   
	       int k = 0x80;   
	       return c / k == 0 ? true : false;   
	 }
	 
	 public static boolean regExp(String str, String regex) {
	        return Pattern.matches(regex, str);
	 } 
	 
	 public static String getFlowNo(){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		int randomNum1 = (int)((Math.random()*9+1)*10000000);
		int randomNum2 = (int)((Math.random()*9+1)*10000000);
		String flowNo=fomat.format(date)+randomNum1+randomNum2;
		return flowNo;
	}
	 
	 
	 
	 public static String addOne(String number) {
			if (!isEmpty(number)) {
				try {
					BigDecimal numberBD = new BigDecimal(number);
					int n = number.length();// 取出字符串的长度
					numberBD = numberBD.add(BigDecimal.ONE);
					String newNumber = numberBD.toString();;
					if (number.startsWith("0")) {
						n = Math.min(n, newNumber.length());
						return number.subSequence(0, number.length() - n) + newNumber;
					} else {
						return newNumber;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new NumberFormatException();
				}
			} else {
				throw new NumberFormatException();
			}
		}

	 public static boolean isEmpty(Object srcStr) {
			// return null == srcStr || srcStr.equals("null");
			return nvl(srcStr, "").trim().length() == 0 || nvl(srcStr, "").equals("null");
	}
	 public static String nvl(Object src, String alt) {
		if (src == null) {
			return alt;
		} else {
			return src.toString();
		}
	}
	 public static String escape(String content) {
		if (content != null) {
			return content.replace("'", "''").replace(" ", "");
		}
		return content;
	}
	 
	 /**
	   * @Description:把15位卡号变成16位
	   * @param str
	   * @return String
	   */
	public static String getCardNo(String str){
		if(str!=null){
			char a[] =  str.toCharArray();
			int sum=0;
			int len = str.length();
			for(int i=0;i<len;i++){
				sum += (a[i]-0x30)*(len-i+1);
			}
			int k = (10-sum%10)==10?0:10-sum%10;
			return str+Integer.toString(k);
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * @param type 图片类型
	 * @param code 图片编码
	 * @param userNo 客户号
	 * @param species 图片种类  client/car/other
	 * @return String
	 */
	public static String getPicBasePath(String type,String code,String userNo,String species){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		String path = today + File.separator + userNo + File.separator + species+ File.separator + type + File.separator;
		return path;
	}
	/**
	 * 根据图片类型与编码生成图片名字
	 * @param type
	 * @param code
	 * @return String
	 */
	public static String getPicName(String type,String code){
		return type+"_"+code;
	}

	public static List<String> forList(List<String> list,int size){
		for (int i = 0; i < size-list.size(); i++) {
			list.add("");
		}
		return list;
	}

	public static void addToList(List<String> list, String element){
		if (StringUtil.isEmpty(element)) {
			list.add("");
			return;
		}
		list.add(element);
	}

	/***
	 * 获取服务密码，默认为手机号后6位
	 * @param tel
	 * @return
     */
	public static String getServicePwd(String tel){
		if(StringUtils.isNotEmpty(tel)){
			tel = tel.substring(tel.length()-6,tel.length());
		}
		return tel;
	}
	
	/**
	 * 判断两个数组里面的元素是否一致(去空格后)
	 * @param arr1
	 * @param arr2
	 * @return
	 * @return boolean  返回true就是一致
	 */
	public static boolean checkDifferentArray(String[] arr1,String[] arr2){
		boolean result = true;
		if(arr1 != null && arr2 != null){
			if(arr1.length == arr2.length){
				ArrayList<String> arr1_noSpace = new ArrayList<String>();
				ArrayList<String> arr2_noSpace = new ArrayList<String>();
				for(int i = 0;i < arr1.length;i++){
					arr1_noSpace.add(arr1[i].trim());//去空格后
					arr2_noSpace.add(arr2[i].trim());//去空格后
				}
				//arr2_noSpace里面是否有arr1_noSpace里面所有的元素
				for(String item1:arr1_noSpace){
					if(!arr2_noSpace.contains(item1)){
						result = false;
					}
				}
				//arr1_noSpace里面是否有arr2_noSpace里面所有的元素
				for(String item2:arr2_noSpace){
					if(!arr1_noSpace.contains(item2)){
						result = false;
					}
				}
			}else{
				//如果长度不一致说明元素肯定不一致了
				result = false;
			}
		}else{
			result = false;
		}
		return result;
	}


	public static String getBlackTypeString(List<BlackListTemp> blackListTempList){
		StringBuffer s = new StringBuffer();
		for(BlackListTemp item : blackListTempList){
			if("2".equals(item.getStatus())){
				s.append("挂失卡、");
			}else if ("3".equals(item.getStatus())){
				s.append("低值、");
			}else if ("4".equals(item.getStatus())){
				s.append("透支（止付卡）、");
			}else if ("5".equals(item.getStatus())){
				s.append("禁用（伪卡）、");
			}else if ("6".equals(item.getStatus())){
				s.append("拆卸电子标签、");
			}else if ("10".equals(item.getStatus())){
				s.append("注销卡、");
			}
		}
		return s.toString();
	}

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



	public static void main(String[] args) {
		System.out.println(md5("123456"));
		//fe61ade92386f8043746daecaeb7ac51
	}
}
