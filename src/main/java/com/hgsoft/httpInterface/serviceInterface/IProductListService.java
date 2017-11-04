package com.hgsoft.httpInterface.serviceInterface;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface IProductListService {

	/**
	 * 从 营运系统获取产品列表
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Object> findProductList() throws IOException;

	/**
	 * 从营运接口获取库存地
	 * @param costomerPointId
	 * @return
	 */
	public Map<String, Object> getStockPlace(Long costomerPointId);

	/**
	 * 获取产品库存日结数据
	 * @param stockPlace
	 * @param settleDay
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, Object> getProductDetailDay(Long stockPlace, String settleDay, Date startDate, Date endDate);

	public Map<String, Object> findAllCustomerPoint();
}
