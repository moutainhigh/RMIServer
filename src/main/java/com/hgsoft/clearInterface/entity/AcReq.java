package com.hgsoft.clearInterface.entity;

import java.util.Date;

/**
 * 查询通行费接口请求参数
 * @author FDF
 */
public class AcReq {
	private String cardCode;//非现金支付卡编码
	private Date payTime;//缴纳日期时间
	private Integer payType;//缴纳类型	0为通行费,1为通行费与滞纳金
	private String userNo;//用户代码
	private Integer obaNo;//银行代码
	private String acbAccount;//银行帐号
	private String accType;//记帐卡客户账户类型
	
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Integer getObaNo() {
		return obaNo;
	}
	public void setObaNo(Integer obaNo) {
		this.obaNo = obaNo;
	}
	public String getAcbAccount() {
		return acbAccount;
	}
	public void setAcbAccount(String acbAccount) {
		this.acbAccount = acbAccount;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
}
