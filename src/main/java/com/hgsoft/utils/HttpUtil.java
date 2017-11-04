package com.hgsoft.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hgsoft.exception.ApplicationException;

/**
 * http访问营运接口
 * @date 2016年3月28日
 */
public class HttpUtil {
		private static Logger logger = Logger.getLogger(HttpUtil.class.getName());
		/**
		 * 使用http协议调用营运接口
		 * @param URL 接口url
		 * @param data 调用接口发送的数据
		 * @param RequestMethod 请求方式
		 * @return 调用接口结果
		 * @throws ApplicationException 
		 */
		public static String callByHTTP(String URL,String data,String RequestMethod) throws Exception{
			StringBuffer returnData=new StringBuffer();
			InputStream in = null;
			OutputStream outputStream = null;
			BufferedReader bufferIn = null;
			HttpURLConnection conn = null;
			//网络连接
			try {
				 URL url = new URL(URL);
				 conn = (HttpURLConnection) url.openConnection();  
				 conn.setDoOutput(true);  
			     conn.setUseCaches(false);  
			     conn.setRequestMethod(RequestMethod);
			     conn.setRequestProperty("Cache-Control", "no-cache");  
			     conn.setRequestProperty("Charsert", "UTF-8");
			     conn.setConnectTimeout(30000);
			     conn.setReadTimeout(30000);
			     conn.connect();
			     if(data!=null&&!data.equals("")){
			    	outputStream = conn.getOutputStream();
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();	
			     }
			     in = conn.getInputStream();
			     if (in != null) {
					bufferIn = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String temp=null;
					while((temp=bufferIn.readLine())!=null){
						returnData.append(temp); 
					}
					bufferIn.close();
					in.close();
					return getDataByJson(returnData.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("营运系统库存接口，连接超时！");
				return "营运系统库存接口，连接超时！";
			} finally{
				if(in!=null)in.close();
				if(bufferIn!=null)bufferIn.close();
				if(outputStream!=null)outputStream.close();
				if(conn!=null)conn.disconnect();
			}
			return "营运系统库存接口，连接失败！";
		}
	
		/*
		 发行的返回信息：flag,message如下：
		0：发行成功
		1：营业网点不存在或者已停用，请核对。
		2：产品编号存在多条领用信息，请核对。
		3：产品未领用或已领用，请核对。
		4：产品领用记录已用完，请核对。
		5：营业网点对应的库存地不存在或者已停用，请核对。
		6：产品编号记录不存在，请核对。
		7：产品编号存在多个产品明细，请核对。
		8：产品领用信息有误，请核对。
		回收的返回信息：flag,message如下：
		0：回收成功
		1：营业网点不存在或者已停用，请核对。
		2：营业网点对应的库存地不存在或者已停用，请核对。
		3：产品不存在，不能回收。
		4：产品编号存在多个产品明细，请核对。
		5：产品领用信息有误，请核对。
		*/
		public static String getDataByJson(String json){
			if (StringUtil.isNotBlank(json)) {
				String jsonString="["+json+"]";
				JSONArray array = JSONArray.fromObject(jsonString);
				JSONObject jsonObject = array.getJSONObject(0);
				return jsonObject.getString("message");
			}
			return "";
		}
		
		/**
		 * 使用http协议访问client项目的action方法
		 * @param URL 接口url
		 * @param data 传入client的参数
		 * @param RequestMethod 请求方式
		 * @return 调用接口结果
		 * @throws ApplicationException 
		 */
		public static Map<String, Object> callClientByHTTP(String URL,String data,String RequestMethod) throws Exception{
			StringBuffer returnResult=new StringBuffer();
			InputStream in = null;
			OutputStream outputStream = null;
			HttpURLConnection conn = null;  
			//网络连接
			try {
				 URL url = new URL(URL);
				 conn = (HttpURLConnection) url.openConnection();  
				 conn.setDoOutput(true);  
			     conn.setUseCaches(false);  
			     conn.setRequestMethod(RequestMethod);
			     conn.setRequestProperty("Cache-Control", "no-cache");  
			     conn.setRequestProperty("Charsert", "UTF-8");
			     conn.setConnectTimeout(30000);
			     conn.setReadTimeout(30000);
			     conn.connect();
			     if(data!=null&&!data.equals("")){
			    	outputStream = conn.getOutputStream();
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();	
			     }
			     in = conn.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("server访问client，连接超时！");
				Map<String, Object> returnMap = new HashMap<String,Object>();
				returnMap.put("result", "false");
				return returnMap;
			}finally{
				if(outputStream!=null)outputStream.close();
			} 
			BufferedReader bufferIn = null;
			//解析返回结果
			try{  
				if (in != null) {
					bufferIn = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String temp=null;
					while((temp=bufferIn.readLine())!=null){
						returnResult.append(temp); 
					}
					bufferIn.close();
					in.close();
					System.out.println(returnResult.toString());
					//
					//JSONObject json = JSONObject.fromObject(returnResult.toString());
					//Map<String,Object> map = new HashMap<String, Object>();
					return (Map<String, Object>) JSON.parse(returnResult.toString());
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				logger.error("server访问client，运行失败！");
				throw new ApplicationException();
			}finally{
				if (conn != null) {  
	                conn.disconnect();  
	                conn = null;  
	            } 
				
				if(bufferIn!=null)bufferIn.close();
				if(in!=null)in.close();
			}
			//return "server访问client，连接失败！";
			return new HashMap<String, Object>();
		}

		public static String callByHTTPAllData(String URL,String data,String RequestMethod) throws Exception{
			StringBuffer returnData=new StringBuffer();
			InputStream in = null;
			OutputStream outputStream = null;
			BufferedReader bufferIn = null;
			HttpURLConnection conn = null;
			//网络连接
			try {
				 URL url = new URL(URL);
				 conn = (HttpURLConnection) url.openConnection();  
				 conn.setDoOutput(true);  
			     conn.setUseCaches(false);  
			     conn.setRequestMethod(RequestMethod);
			     conn.setRequestProperty("Cache-Control", "no-cache");  
			     conn.setRequestProperty("Charsert", "UTF-8");
			     conn.setConnectTimeout(30000);
			     conn.setReadTimeout(30000);
			     conn.connect();
			     if(data!=null&&!data.equals("")){
			    	outputStream = conn.getOutputStream();
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();	
			     }
			     in = conn.getInputStream();
			     
			     if (in != null) {
					bufferIn = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String temp=null;
					while((temp=bufferIn.readLine())!=null){
						returnData.append(temp); 
					}
					bufferIn.close();
					in.close();
					return returnData.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("营运系统库存接口，连接超时！");
				return "营运系统库存接口，连接超时！";
			} finally{
				if(in!=null)in.close();
				if(bufferIn!=null)bufferIn.close();
				if(outputStream!=null)outputStream.close();
				if(conn!=null)conn.disconnect();
			}
			return "营运系统库存接口，连接失败！";
		}
}
