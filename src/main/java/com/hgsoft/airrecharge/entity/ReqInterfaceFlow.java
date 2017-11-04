package com.hgsoft.airrecharge.entity;

import java.util.Date;

public class ReqInterfaceFlow implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8351361355651131818L;

	
	private Long id;
	private String reqSource;
	private String serType;
	private String interfaceName;
	private Date reqDate;
	private String placeNo;
	private String placeName;
	private String remark;
	private String tradeCode;
	
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReqSource() {
		return reqSource;
	}
	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
