package com.hgsoft.accountC.entity;

import java.math.BigDecimal;
import java.util.Date;


public class AcctollcollectList implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1156827568884924007L;
	private Long id;
	private Long userId;
	private Long accountId;
	private Long dealNum;
	private BigDecimal dealFee;
	private BigDecimal realdealFee;
	private BigDecimal serverFee;
	private BigDecimal lateFee;
	private BigDecimal tollFee;
	private Date genTime;
	private Long hdlTime;
	private String state;
	private Date payTime;
	private Long operId;
	private Long placeId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	// Constructors

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

	/** default constructor */
	public AcctollcollectList() {
	}

	

	public AcctollcollectList(Long id, Long userId, Long accountId,
			Long dealNum, BigDecimal dealFee, BigDecimal realdealFee,
			BigDecimal serverFee, BigDecimal lateFee, BigDecimal tollFee,
			Date genTime, Long hdlTime, String state, Date payTime,
			Long operId, Long placeId, String operNo, String operName,
			String placeNo, String placeName) {
		super();
		this.id = id;
		this.userId = userId;
		this.accountId = accountId;
		this.dealNum = dealNum;
		this.dealFee = dealFee;
		this.realdealFee = realdealFee;
		this.serverFee = serverFee;
		this.lateFee = lateFee;
		this.tollFee = tollFee;
		this.genTime = genTime;
		this.hdlTime = hdlTime;
		this.state = state;
		this.payTime = payTime;
		this.operId = operId;
		this.placeId = placeId;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	

	public Long getDealNum() {
		return dealNum;
	}

	public void setDealNum(Long dealNum) {
		this.dealNum = dealNum;
	}

	public void setHdlTime(Long hdlTime) {
		this.hdlTime = hdlTime;
	}

	public Long getHdlTime() {
		return hdlTime;
	}

	public BigDecimal getDealFee() {
		return dealFee;
	}

	public void setDealFee(BigDecimal dealFee) {
		this.dealFee = dealFee;
	}

	public BigDecimal getRealdealFee() {
		return realdealFee;
	}

	public void setRealdealFee(BigDecimal realdealFee) {
		this.realdealFee = realdealFee;
	}

	public BigDecimal getServerFee() {
		return serverFee;
	}

	public void setServerFee(BigDecimal serverFee) {
		this.serverFee = serverFee;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public BigDecimal getTollFee() {
		return tollFee;
	}

	public void setTollFee(BigDecimal tollFee) {
		this.tollFee = tollFee;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}


}