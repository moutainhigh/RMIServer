package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Migrate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8067097246907535568L;
	private Long id;
	private Long oldAccountId;
	private Long newAccountId;
	private String remark;
	private Long operId;
	private Long placeId;
	private Date operTime;
	private String flag;
	private String appState;
	private Date effectTime;
	private Long approver;
	private Date appTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String approverNo;
	private String approverName;
	private Date reqTime;
	
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
	public Long getOldAccountId() {
		return oldAccountId;
	}
	public void setOldAccountId(Long oldAccountId) {
		this.oldAccountId = oldAccountId;
	}
	public Long getNewAccountId() {
		return newAccountId;
	}
	public void setNewAccountId(Long newAccountId) {
		this.newAccountId = newAccountId;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public Long getApprover() {
		return approver;
	}
	public void setApprover(Long approver) {
		this.approver = approver;
	}
	public Date getAppTime() {
		return appTime;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	
	
	
	
}
