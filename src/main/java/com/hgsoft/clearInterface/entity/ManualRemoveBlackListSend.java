package com.hgsoft.clearInterface.entity;
/**
 * 手工申请解除止付数据
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ManualRemoveBlackListSend implements Serializable{

	private static final long serialVersionUID = 4436233831680060256L;
	private Long id;
	private String baccount;
	private String cardNo;
	private Timestamp handpaytime;
	private BigDecimal handpayfee;
	private BigDecimal Etcmoney;
	private BigDecimal latefee;
	private String payAccount;
	private String payFlag;
	private String remark;
	private Timestamp updateTime;
	private Long boardListNo;//20170811添加 修改bug

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaccount() {
		return baccount;
	}

	public void setBaccount(String baccount) {
		this.baccount = baccount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Timestamp getHandpaytime() {
		return handpaytime;
	}

	public void setHandpaytime(Timestamp handpaytime) {
		this.handpaytime = handpaytime;
	}

	public BigDecimal getHandpayfee() {
		return handpayfee;
	}

	public void setHandpayfee(BigDecimal handpayfee) {
		this.handpayfee = handpayfee;
	}

	public BigDecimal getEtcmoney() {
		return Etcmoney;
	}

	public void setEtcmoney(BigDecimal etcmoney) {
		Etcmoney = etcmoney;
	}

	public BigDecimal getLatefee() {
		return latefee;
	}

	public void setLatefee(BigDecimal latefee) {
		this.latefee = latefee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
}