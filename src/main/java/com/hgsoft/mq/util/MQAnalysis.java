package com.hgsoft.mq.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.hgsoft.exception.ApplicationException;

/**
 * 参数解析  str to list or list to str
 * @author gaosiling
 * 2016年8月20日 14:16:18
 */
public class MQAnalysis {
	
	private static Logger logger = Logger.getLogger(MQAnalysis.class.getName());
	
	//数值0
	private static final int ZERO = 0;
	
	/**
	 * 解析集合参数
	 * @param param
	 * @return
	 */
	public static List<String> analysis(String param){
		logger.info("解析参数："+param);
		List<String> list = new ArrayList<String>();
		
		String[] params = param.split(String.valueOf(InterruptFlag.T));
		System.out.println("数组："+Arrays.toString(params));
		/*param = param.substring(param.indexOf(InterruptFlag.A)+String.valueOf(InterruptFlag.A).length());
		param = param.substring(0,param.lastIndexOf(InterruptFlag.A));
		String[] params= param.split(Character.toString(InterruptFlag.A),-1);*/
		logger.info("params="+Arrays.toString(params));
		if(params.length>ZERO){
			list = Arrays.asList(params); 
			logger.error("解析后返回参数："+list);
		}else{
			logger.error("解析后返回参数："+param+"解析失败");
		}
		return list;
	}
	
	/**
	 * 集合转字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list){
		StringBuffer sb = new StringBuffer();
		sb.append(InterruptFlag.A);
		try {
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i)==null){
					sb.append("");
				}else{
					sb.append(list.get(i));
				}
				/*if(list.size()-1==i){
					sb.append(InterruptFlag.ENDFLAG);
				}else{
				}*/
				sb.append(InterruptFlag.A);
			}
		} catch (ApplicationException e) {
			logger.error("业务处理返回参数："+list+"解析失败");
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		
		
		//报文发送例子
		String param = InterruptFlag.A + "96326"+InterruptFlag.A+ "1435220000000629"+InterruptFlag.A+"0036"+InterruptFlag.A+ "0014"+InterruptFlag.A;
	/*	param = param.substring(param.indexOf(InterruptFlag.A)+String.valueOf(InterruptFlag.A).length());
		param = param.substring(0,param.lastIndexOf(InterruptFlag.A));
		System.out.println(param);*/
		List<String> list = analysis(param);
		System.out.println(analysis(param));
		
		
		System.out.println(listToString(list));
		
	}

}
