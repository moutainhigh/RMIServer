package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NewCardApply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4487439856234056984L;
	private Long id;
	private Long applyId;
	private Long reqcount;
	private String appState;
	private Long apprOver;
	private Long operId;
	private Long placeId;
	private Long hisseqId;
	private BigDecimal bail;
	
	private BigDecimal truckBail;
	
	private Date appTime;
	private Date operTime;
	private Date applyTime;

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
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Long getReqcount() {
		return reqcount;
	}
	public void setReqcount(Long reqcount) {
		this.reqcount = reqcount;
	}
	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public Long getApprOver() {
		return apprOver;
	}
	public void setApprOver(Long apprOver) {
		this.apprOver = apprOver;
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
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public BigDecimal getBail() {
		return bail;
	}
	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}
	

	public Date getAppTime() {
		return appTime;
	}
	public BigDecimal getTruckBail() {
		return truckBail;
	}
	public void setTruckBail(BigDecimal truckBail) {
		this.truckBail = truckBail;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
