package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class AcInvoicInfoHis  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4572455573196655519L;
	private Long hisseqId;
	private Long placeId;
	private Date setTime;
	private Long opId;
	private String registerType;
	private String baccount;
	private String accode;
	private String getType;
	private String serType;
	private Date endTime;
	private Date startTime;
	private String userNo;
	private Long mainId;
	private Long id;
	private Date createDate;
	private String createReason;
	private Long lianCardInfoId;
	
	public Long getLianCardInfoId() {
		return lianCardInfoId;
	}
	public void setLianCardInfoId(Long lianCardInfoId) {
		this.lianCardInfoId = lianCardInfoId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateReason() {
		return createReason;
	}
	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Date getSetTime() {
		return setTime;
	}
	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getBaccount() {
		return baccount;
	}
	public void setBaccount(String baccount) {
		this.baccount = baccount;
	}
	public String getAccode() {
		return accode;
	}
	public void setAccode(String accode) {
		this.accode = accode;
	}
	public String getGetType() {
		return getType;
	}
	public void setGetType(String getType) {
		this.getType = getType;
	}
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}

