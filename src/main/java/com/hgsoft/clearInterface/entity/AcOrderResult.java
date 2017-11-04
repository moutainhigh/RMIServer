package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author FDF
 */
public class AcOrderResult implements Serializable{
	private String cardCode;//非现金支付卡编码
	private Date businessTime;//业务日期时间
	private Integer businessType;//业务类型
	private Integer result;//结果
	private Integer seq;//序列号
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
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
