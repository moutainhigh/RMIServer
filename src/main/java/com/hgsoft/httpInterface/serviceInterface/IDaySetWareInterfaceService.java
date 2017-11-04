package com.hgsoft.httpInterface.serviceInterface;

public interface IDaySetWareInterfaceService {

	public String getInOutStockDay(String dateTime, Integer stockPlace);

	public String getProductDetailDay(String dateTime, Integer stockPlace);

}
