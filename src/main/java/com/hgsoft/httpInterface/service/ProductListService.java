package com.hgsoft.httpInterface.service;

import com.hgsoft.httpInterface.serviceInterface.IProductListService;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
public class ProductListService extends BaseOMSService implements IProductListService{

	public ProductListService() throws IOException {
		super();
	}
	private static Logger logger = Logger.getLogger(ProductListService.class.getName());
	
	@Override
	public Map<String, Object> findProductList() throws IOException {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_productTypeList.do?au_token="+super.getToken();
			return HttpUtil.callClientByHTTP(url, null, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，产品列表查询失败！");
		}
		return null;
	}
	
	@Override
	public Map<String,Object> getStockPlace(Long costomerPointId){
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getStockPlace.do?customPointId="+costomerPointId+"&au_token="+super.getToken();
			return HttpUtil.callClientByHTTP(url, null, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，库存地查询失败！");
		}
		return null;
	}

	@Override
	public Map<String,Object> getProductDetailDay(Long stockPlace,String settleDay,Date startDate,Date endDate){
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_productDetailDay.do?stockPlace="+stockPlace+"&dateTime="+settleDay+"&au_token="+super.getToken();
			String data = "startDate="+DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss")+"&endDate="+DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss");
			return HttpUtil.callClientByHTTP(url, data, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，产品库存查询失败！");
		}
		return null;
	}


	public Map<String, Object> findAllCustomerPoint() {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_findAllCustomPoint.do?au_token="+super.getToken();
			return HttpUtil.callClientByHTTP(url, null, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，网点查询失败！");
		}
		return null;
	}

}
