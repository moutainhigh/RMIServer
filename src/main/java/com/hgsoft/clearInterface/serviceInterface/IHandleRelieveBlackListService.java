package com.hgsoft.clearInterface.serviceInterface;

import java.util.Date;

import com.hgsoft.clearInterface.entity.HandleRelieveBlackList;

public interface IHandleRelieveBlackListService {

	/**
	 * 手工解除止付黑名单
	 * @param handleRelieveBlackList
	 * @return
	 */
	public Boolean saveHandleRelieveBlackList(HandleRelieveBlackList handleRelieveBlackList);
	
	/**
	 * 手工解除止付黑名单
	 * @param bankAccount 银行账号
	 * @param cardNo 卡片号码
	 * @param handPayTime 缴纳时间
	 * @param handPayFee 缴纳金额
	 * @param etcMoney 通行费
	 * @param lateFee 滞纳金
	 * @param payaccount 支付账户
	 * @param payflag 支付类型
	 * @param lateFee 滞纳金
	 * @param remark 备注
	 * @return
	 */
	public Boolean saveHandleRelieveBlackList(String bankAccount,String cardNo,Date handPayTime,Double handPayFee,Double etcMoney,Double lateFee,String payaccount,String payflag,String remark);
}
