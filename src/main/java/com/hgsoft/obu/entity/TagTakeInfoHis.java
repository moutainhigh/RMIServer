package com.hgsoft.obu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TagTakeInfoHis implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5346132657362474142L;
	private Long id;
	private String clientName;
	private String certType;
	private String certNumber;
	private String begin_TagNo;
	private String end_TagNo;
	private Integer takeAmount;
	private BigDecimal tagPrice;
	private BigDecimal totalPrice;
	
	private BigDecimal cost;//应收
	
	private Long operID;
	private Long takeplaceID;
	private Date takeDate;
	private String memo;
	private Long hisSeqID;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	private Date CreateDate;
	private String CreateReason;
	
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long receiptId;
	private Long tagtakeid;
	
	
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	public Long getTagtakeid() {
		return tagtakeid;
	}
	public void setTagtakeid(Long tagtakeid) {
		this.tagtakeid = tagtakeid;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNumber() {
		return certNumber;
	}
	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}
	public String getBegin_TagNo() {
		return begin_TagNo;
	}
	public void setBegin_TagNo(String begin_TagNo) {
		this.begin_TagNo = begin_TagNo;
	}
	public String getEnd_TagNo() {
		return end_TagNo;
	}
	public void setEnd_TagNo(String end_TagNo) {
		this.end_TagNo = end_TagNo;
	}
	public Integer getTakeAmount() {
		return takeAmount;
	}
	public void setTakeAmount(Integer takeAmount) {
		this.takeAmount = takeAmount;
	}
	public BigDecimal getTagPrice() {
		return tagPrice;
	}
	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Long getTakeplaceID() {
		return takeplaceID;
	}
	public void setTakeplaceID(Long takeplaceID) {
		this.takeplaceID = takeplaceID;
	}
	public Date getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getCreateReason() {
		return CreateReason;
	}
	public void setCreateReason(String createReason) {
		CreateReason = createReason;
	}
	public String getIsDaySet() {
		return isDaySet;
	}
	public void setIsDaySet(String isDaySet) {
		this.isDaySet = isDaySet;
	}
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public Date getSettletTime() {
		return settletTime;
	}
	public void setSettletTime(Date settletTime) {
		this.settletTime = settletTime;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
}
