package com.hgsoft.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * @memo 利用HttpClient进行post请求的工具类
 * @author gaosiling
 * 2016-04-18 11:23:11
 */
public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class.getName());
	public String doPost(String url, String xml, String charset,String sessionid) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			/*List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("xml", xml));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
			httpPost.setEntity(entity);*/
			
			StringEntity s = new StringEntity(xml);    
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "text/xml;charset=UTF-8"));
            httpPost.setEntity(s);
            
			httpPost.setHeader("Cookie", "JSESSIONID="+sessionid);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			logger.info(ex);
			ex.printStackTrace();
		}finally{
		}
		return result;
	}
	
	
	public String doGet(String url,String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpGet = new HttpGet(url);
			// 设置参数
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}