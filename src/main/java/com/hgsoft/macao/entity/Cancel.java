package com.hgsoft.macao.entity;

import java.util.Date;

public class Cancel implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -897202689050175872L;
	private Long id;
	private String flag;
	private String code;
	private String reason;
	private String remark;
	private Long operId;
	private Long placeId;
	private Long customerId;
	private String creditCardNo;
	private Date cancelTime;

	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private String bankNo;
	private String bankMember;
	private String bankOpenBranches;
	private String source;

	public Cancel(Long id, String flag, String code, String reason,
				  String remark, Long operId, Long placeId,Long customerId,Date cancelTime,String source) {
		super();
		this.id = id;
		this.flag = flag;
		this.code = code;
		this.reason = reason;
		this.remark = remark;
		this.operId = operId;
		this.placeId = placeId;
		this.customerId = customerId;
		this.cancelTime = cancelTime;
		this.source = source;
	}

	public Cancel(String flag, String code) {
		super();
		this.flag = flag;
		this.code = code;
	}

	public Cancel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
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

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankMember() {
		return bankMember;
	}

	public void setBankMember(String bankMember) {
		this.bankMember = bankMember;
	}

	public String getBankOpenBranches() {
		return bankOpenBranches;
	}

	public void setBankOpenBranches(String bankOpenBranches) {
		this.bankOpenBranches = bankOpenBranches;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}