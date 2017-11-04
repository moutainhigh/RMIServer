package com.hgsoft.clearInterface.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class RelieveStopPay implements java.io.Serializable {
	private static final long serialVersionUID = -7429866599584312306L;
	/**
	 * 
	 */
	private Long id;
	private String BAccount;
//	private String cardNo;
	private Timestamp lateFeeStartTime;
	private Timestamp etcFeeStartTime;
	private Timestamp feeEndTime;
	private Date applyTime;
	private Date createTime;
	private BigDecimal etcFee;
	private BigDecimal lateFee;
	private String remark;
	private Integer flag;
	private Integer state;
	
	private Long operId;
	private Long placeId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;

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

//	public String getCardNo() {
//		return cardNo;
//	}
//
//	public void setCardNo(String cardNo) {
//		this.cardNo = cardNo;
//	}

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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
}
