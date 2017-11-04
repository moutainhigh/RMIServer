package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 通行费汇总
 * @author FDF
 */
public class TollCollection implements Serializable{
	private Long id;//序列号
	private String cardCode;//非现金支付卡编码
	private Date payTime;//缴纳日期时间
	private int payType;//缴纳类型
	private Long etcMoney;//通行费金额
	private Long lateFee;//滞纳金金额
	private int count;//记录数
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public Long getEtcMoney() {
		return etcMoney;
	}
	public void setEtcMoney(Long etcMoney) {
		this.etcMoney = etcMoney;
	}
	public Long getLateFee() {
		return lateFee;
	}
	public void setLateFee(Long lateFee) {
		this.lateFee = lateFee;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "TollCollection [id=" + id + ", cardCode=" + cardCode
				+ ", payTime=" + payTime + ", payType=" + payType
				+ ", etcMoney=" + etcMoney + ", lateFee=" + lateFee
				+ ", count=" + count + "]";
	}
}
