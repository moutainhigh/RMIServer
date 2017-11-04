package com.hgsoft.accountC.entity;

import java.util.Date;

public class StopList implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 7474623610461957782L;
	private Long id;
	private Long accountId;
	private String reason;
	private Date gendate;
	private String flag;
	private Date updateTime;
	private Double fee;
	private Long operId;
	private Long placeId;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
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

	// Constructors

	/** default constructor */
	public StopList() {
	}

	public StopList(Long id, Long accountId, String reason, Date gendate,
			String flag, Date updateTime, Double fee, Long operId, Long placeId) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.reason = reason;
		this.gendate = gendate;
		this.flag = flag;
		this.updateTime = updateTime;
		this.fee = fee;
		this.operId = operId;
		this.placeId = placeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getGendate() {
		return gendate;
	}

	public void setGendate(Date gendate) {
		this.gendate = gendate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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


}