package com.hgsoft.clearInterface.serviceInterface;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

/**
 * 1.7.7.3.	资金转移接口、1.7.7.4.	转移金额充值锁定接口、1.7.7.5.	转移资金充值成功通知清算接口
 * @author FDF
 */
//@WebService(targetNamespace="http://ws.hgsoft.com/")
public interface ITransFeeService {

	/**
	 * 1.7.7.3.	资金转移接口
	 * @param cardNos
	 * @return
	 */
	public String getTransFee(String cardNos);	
	/**
	 * 1.7.7.4.	转移金额充值锁定接口
	 * @param cardNos
	 * @return
	 */
	public String lockTransFee(String cardNos,String fees);
	/**
	 * 1.7.7.5.	转移资金充值成功通知清算接口
	 * @param cardNos
	 * @return
	 */
	public String notifyTransFee(String cardNos,String fees);
}
