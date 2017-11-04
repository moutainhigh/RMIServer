package com.hgsoft.clearInterface.entity;
/**
 * 手工申请解除止付数据
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApplyRemovePaymentSend implements Serializable{

	private static final long serialVersionUID = 213624050944674153L;
	private Long id;
	private String baccount;
	private String cardNo;
	private Date genTime;
	private BigDecimal lateFee;
	private String remark;
	private Date updateTime;
	private BigDecimal boardListNo;
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
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(BigDecimal boardListNo) {
		this.boardListNo = boardListNo;
	}
}