package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountNCApply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2481672029583699550L;
	private Long id;
	private Long accountId;
	private String oldAccName;
	private String newAccName;
	private String remark;
	private String appstate;
	private Long apprOver;
	private Date appTime;
	private Long operId;
	private Long placeId;
	private Date operTime;
	private Long hisSeqId;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String approverNo;
	private String approverName;
	
	public String getApproverNo() {
		return approverNo;
	}
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
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
	public String getOldAccName() {
		return oldAccName;
	}
	public void setOldAccName(String oldAccName) {
		this.oldAccName = oldAccName;
	}
	public String getNewAccName() {
		return newAccName;
	}
	public void setNewAccName(String newAccName) {
		this.newAccName = newAccName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppstate() {
		return appstate;
	}
	public void setAppstate(String appstate) {
		this.appstate = appstate;
	}
	public Long getApprOver() {
		return apprOver;
	}
	public void setApprOver(Long apprOver) {
		this.apprOver = apprOver;
	}
	public Date getAppTime() {
		return appTime;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

	
	
}
