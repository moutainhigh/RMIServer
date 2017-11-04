package com.hgsoft.httpInterface.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.httpInterface.serviceInterface.IDaySetWareInterfaceService;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.PropertiesUtil;

/**
 * 调用产品库存接口
 * @author gaosiling
 * 2016年9月7日 15:45:27
 */
@Service
public class DaySetWareInterfaceService implements IDaySetWareInterfaceService{

	private static Logger logger = Logger.getLogger(DaySetWareInterfaceService.class.getName());
	
	@Override
	public String getProductDetailDay(String dateTime,Integer stockPlace) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_productDetailDay.do";
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONObject json = new JSONObject();
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", System.currentTimeMillis());
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			
			format = new SimpleDateFormat("yyyyMMdd");
			Date date = format.parse(dateTime);
			format = new SimpleDateFormat("yyyy-MM-dd");
			dateTime = format.format(date);
			System.out.println(url);
			return HttpUtil.callByHTTPAllData(url, "dateTime="+dateTime+"&stockPlace="+stockPlace+"&au_token="+data, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，发行查库存失败！");
		}
		return "";
		
	}
	
	@Override
	public String getInOutStockDay(String dateTime,Integer stockPlace) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_inOutStockDay.do";
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONObject json = new JSONObject();
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", System.currentTimeMillis());
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			
			format = new SimpleDateFormat("yyyyMMdd");
			Date date = format.parse(dateTime);
			format = new SimpleDateFormat("yyyy-MM-dd");
			dateTime = format.format(date);
			System.out.println(url);
			return HttpUtil.callByHTTPAllData(url, "dateTime="+dateTime+"&stockPlace="+stockPlace+"&au_token="+data, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，发行查库存失败！");
		}
		return "";
		
	}
}
