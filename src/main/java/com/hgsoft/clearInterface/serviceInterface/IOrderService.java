package com.hgsoft.clearInterface.serviceInterface;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.AcOrder;
import com.hgsoft.clearInterface.entity.AcOrderResult;



/**
 * 订单接口
 * @author FDF
 */
//@WebService(targetNamespace="http://ws.hgsoft.com/")
public interface IOrderService {
	public AcOrderResult searchOrderResult(AcOrder acOrder);
}
