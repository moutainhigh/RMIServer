package com.hgsoft.clearInterface.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class RelieveStopPayDetail implements java.io.Serializable {
	private static final long serialVersionUID = -7429866599584312306L;
	private Long id;
	private Long relieveStopPayId;//关联手工申请解除止付记录表CSMS_RELIEVE_STOPPAY.id
	private String BAccount;
	private String cardNo;
	private Timestamp lateFeeStartTime;
	private Timestamp etcFeeStartTime;
	private Timestamp feeEndTime;
	private Date applyTime;
	private Date createTime;
	private BigDecimal etcFee;
	private BigDecimal lateFee;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBAccount() {
		return BAccount;
	}

	public void setBAccount(String BAccount) {
		this.BAccount = BAccount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Timestamp getLateFeeStartTime() {
		return lateFeeStartTime;
	}

	public void setLateFeeStartTime(Timestamp lateFeeStartTime) {
		this.lateFeeStartTime = lateFeeStartTime;
	}

	public Timestamp getEtcFeeStartTime() {
		return etcFeeStartTime;
	}

	public void setEtcFeeStartTime(Timestamp etcFeeStartTime) {
		this.etcFeeStartTime = etcFeeStartTime;
	}

	public Timestamp getFeeEndTime() {
		return feeEndTime;
	}

	public void setFeeEndTime(Timestamp feeEndTime) {
		this.feeEndTime = feeEndTime;
	}

	public BigDecimal getEtcFee() {
		return etcFee;
	}

	public void setEtcFee(BigDecimal etcFee) {
		this.etcFee = etcFee;
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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getRelieveStopPayId() {
		return relieveStopPayId;
	}

	public void setRelieveStopPayId(Long relieveStopPayId) {
		this.relieveStopPayId = relieveStopPayId;
	}
}
