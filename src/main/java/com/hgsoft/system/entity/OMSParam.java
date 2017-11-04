package com.hgsoft.system.entity;

import java.util.Date;

public class OMSParam implements java.io.Serializable {

	private static final long serialVersionUID = -7893436259235026875L;
	private Long id;
	private Long omsId;
	private String omsType;
	private Long paramId;
	/*private String paramChName;*/
	private String paramValue;
	private String type;
	private String state;
	private Long operId;
	private String operNo;
	private String operName;
	private Long placeId;
	private String placeNo;
	private String placeName;
	private Date operTime;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOmsId() {
		return omsId;
	}
	public void setOmsId(Long omsId) {
		this.omsId = omsId;
	}
	public String getOmsType() {
		return omsType;
	}
	public void setOmsType(String omsType) {
		this.omsType = omsType;
	}
	public Long getParamId() {
		return paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
