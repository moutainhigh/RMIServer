package com.hgsoft.customer.entity;

import java.util.Date;

public class BillGet  implements java.io.Serializable {

	private static final long serialVersionUID = 8241931748768479399L;

	private Long id;
    
	private Long mainId;
    
	private String serItem;
    
	private String serType;
    
	private Date begTime;
    
	private Date endTime;
    
	private Long operId;
    
	private Long placeId;
    
	private Long hisSeqId;
	
	private String cardType;
	
	private Long cardAccountID;
	
	private String cardBankNo;
	
	private Date operTime;
	
	private String operNo;
	
	private String operName;
	
	private String placeNo;
	
	private String placeName;
	
	public BillGet(){
		
	}
	
	public BillGet(Long mainId) {
		super();
		this.mainId = mainId;
	}

	public BillGet(Long id, Long mainId, String serItem, String serType, Date begTime, Date endTime, Long operId,
			Long placeId, Long hisSeqId, String cardType, Long cardAccountID, String cardBankNo, Date operTime,
			String operNo, String operName, String placeNo, String placeName) {
		super();
		this.id = id;
		this.mainId = mainId;
		this.serItem = serItem;
		this.serType = serType;
		this.begTime = begTime;
		this.endTime = endTime;
		this.operId = operId;
		this.placeId = placeId;
		this.hisSeqId = hisSeqId;
		this.cardType = cardType;
		this.cardAccountID = cardAccountID;
		this.cardBankNo = cardBankNo;
		this.operTime = operTime;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public String getSerItem() {
		return serItem;
	}

	public void setSerItem(String serItem) {
		this.serItem = serItem;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public Date getBegTime() {
		return begTime;
	}

	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getCardAccountID() {
		return cardAccountID;
	}

	public void setCardAccountID(Long cardAccountID) {
		this.cardAccountID = cardAccountID;
	}

	public String getCardBankNo() {
		return cardBankNo;
	}

	public void setCardBankNo(String cardBankNo) {
		this.cardBankNo = cardBankNo;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
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
	
}