package com.hgsoft.accountC.entity;

import java.math.BigDecimal;
import java.util.Date;


public class Bail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 644716261348912739L;
	private Long id;
	private String userNo;
	private String cardno;
	private Long accountId;
	private String payFlag;
	private BigDecimal bailFee;
	private Date up_Date;
	private String upreason;
	private String dflag;
	private Date setTime;
	private Date applyTime;
	private Long operId;
	private Long placeId;
	private String tradingType;
	private String appState;
	private Long appPlaceId;
	private Long approver;
	private Date appTime;
	
	private String bankNo;//银行账号
	private String bankMember;//银行客户名称
	private String bankOpenBranches;//银行开户网点（在子账户表的一个字段）
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String approverNo;
	private String approverName;
	
	private BigDecimal bailAllFee;
	private BigDecimal bailFrozenBalance;
	
	public BigDecimal getBailAllFee() {
		return bailAllFee;
	}
	public void setBailAllFee(BigDecimal bailAllFee) {
		this.bailAllFee = bailAllFee;
	}
	public BigDecimal getBailFrozenBalance() {
		return bailFrozenBalance;
	}
	public void setBailFrozenBalance(BigDecimal bailFrozenBalance) {
		this.bailFrozenBalance = bailFrozenBalance;
	}
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

	// Constructors

	/** default constructor */
	public Bail() {
	}

	public Bail(Long id, String userNo, Long accountId, String payFlag,
			BigDecimal bailFee, Date up_Date, String upreason, String dflag,
			Date setTime, Long operId, Long placeId) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.accountId = accountId;
		this.payFlag = payFlag;
		this.bailFee = bailFee;
		this.up_Date = up_Date;
		this.upreason = upreason;
		this.dflag = dflag;
		this.setTime = setTime;
		this.operId = operId;
		this.placeId = placeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	

	public BigDecimal getBailFee() {
		return bailFee;
	}

	public void setBailFee(BigDecimal bailFee) {
		this.bailFee = bailFee;
	}

	public Date getUp_Date() {
		return up_Date;
	}

	public void setUp_Date(Date up_Date) {
		this.up_Date = up_Date;
	}

	public String getUpreason() {
		return upreason;
	}

	public void setUpreason(String upreason) {
		this.upreason = upreason;
	}

	public String getDflag() {
		return dflag;
	}

	public void setDflag(String dflag) {
		this.dflag = dflag;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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

	public String getTradingType() {
		return tradingType;
	}

	public void setTradingType(String tradingType) {
		this.tradingType = tradingType;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	public Long getAppPlaceId() {
		return appPlaceId;
	}

	public void setAppPlaceId(Long appPlaceId) {
		this.appPlaceId = appPlaceId;
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
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankMember() {
		return bankMember;
	}
	public void setBankMember(String bankMember) {
		this.bankMember = bankMember;
	}
	public String getBankOpenBranches() {
		return bankOpenBranches;
	}
	public void setBankOpenBranches(String bankOpenBranches) {
		this.bankOpenBranches = bankOpenBranches;
	}

}