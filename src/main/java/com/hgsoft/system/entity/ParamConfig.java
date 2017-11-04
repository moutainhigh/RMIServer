package com.hgsoft.system.entity;

import java.util.Date;

public class ParamConfig implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7893436259235026875L;
	private Long id;
	private String paramNo;
	private String paramName;
	private String paramChName;
	private String paramValue;
	private String state;
	private String memo;

	private Long operId;

	private Long placeId;

	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;

	private Date createTime;

	private Long paramId;

	private Long paramLevel;
	
	private String paramValueTwice;
	
	public String getParamValueTwice() {
		return paramValueTwice;
	}

	public void setParamValueTwice(String paramValueTwice) {
		this.paramValueTwice = paramValueTwice;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public Long getParamLevel() {
		return paramLevel;
	}

	public void setParamLevel(Long paramLevel) {
		this.paramLevel = paramLevel;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamNo() {
		return paramNo;
	}

	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamChName() {
		return paramChName;
	}

	public void setParamChName(String paramChName) {
		this.paramChName = paramChName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "ParamConfig [id=" + id + ", paramNo=" + paramNo
				+ ", paramName=" + paramName + ", paramChName=" + paramChName
				+ ", paramValue=" + paramValue + ", state=" + state + ", memo="
				+ memo + ", operId=" + operId + ", placeId=" + placeId
				+ ", operNo=" + operNo + ", operName=" + operName
				+ ", placeNo=" + placeNo + ", placeName=" + placeName
				+ ", createTime=" + createTime + ", paramId=" + paramId
				+ ", paramLevel=" + paramLevel + "]";
	}

	
}
