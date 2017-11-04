package com.hgsoft.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountBussiness implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4606982543641739210L;
	private Long id;
	private Long userId;
	private String State;
	private BigDecimal realPrice;
	private Date tradeTime;
	private Long operId;
	private String operNo;
	private String operName;
	private Long placeId;
	private String placeNo;
	private String placeName;
	private Long bussinessId;
	private Long hisSeqId;
	
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	public Long getBussinessId() {
		return bussinessId;
	}
	public void setBussinessId(Long bussinessId) {
		this.bussinessId = bussinessId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
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
	
	

}
