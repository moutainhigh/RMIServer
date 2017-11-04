package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class AcOrder {
	private String cardCode;//非现金支付卡编码
	private Date businessTime;//业务日期时间
	private Integer businessType;//业务类型
	private Long seq;//序列号
	private String backup;//备用字段
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
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getBackup() {
		return backup;
	}
	public void setBackup(String backup) {
		this.backup = backup;
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
