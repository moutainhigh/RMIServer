package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PrepaidC implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -897202689050175872L;
	private Long id;
	private String cardNo;
	private Long customerID;
	private Long accountID;
	private String state;
	private BigDecimal faceValue;
	private BigDecimal cost_;
	private BigDecimal realCost;
	private BigDecimal transferSum;
	private BigDecimal returnMoney;
	private Date saleTime;
	private String saleFlag;
	private String payFlag;
	private Long saleOperId;
	private Long salePlaceId;
	private String invoicePrint;
	private String s_con_pwd_flag;
	private String tradingPwd;
	private Long hisSeqID;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	private String bind;
	private Date startDate;
	private Date endDate;
	private Date maintainTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private String suit;
	private String blackFlag;
	
	private String cardServicePwd;
	
	private Long invoiceChangeFlag;
	
	private String writeCardFlag;
	
	private String firstRecharge;
	
	public String getWriteCardFlag() {
		return writeCardFlag;
	}
	public void setWriteCardFlag(String writeCardFlag) {
		this.writeCardFlag = writeCardFlag;
	}
	public Long getInvoiceChangeFlag() {
		return invoiceChangeFlag;
	}
	public void setInvoiceChangeFlag(Long invoiceChangeFlag) {
		this.invoiceChangeFlag = invoiceChangeFlag;
	}
	public String getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag) {
		this.blackFlag = blackFlag;
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
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
	
	@Override
	public String toString() {
		return "PrepaidC [id=" + id + ", cardNo=" + cardNo + ", customerID=" + customerID + ", accountID=" + accountID
				+ ", state=" + state + ", faceValue=" + faceValue + ", cost_=" + cost_ + ", realCost=" + realCost
				+ ", transferSum=" + transferSum + ", returnMoney=" + returnMoney + ", saleTime=" + saleTime
				+ ", saleFlag=" + saleFlag + ", payFlag=" + payFlag + ", saleOperId=" + saleOperId + ", salePlaceId="
				+ salePlaceId + ", invoicePrint=" + invoicePrint + ", s_con_pwd_flag=" + s_con_pwd_flag
				+ ", tradingPwd=" + tradingPwd + ", hisSeqID=" + hisSeqID + ", bind=" + bind + ", startDate="+ startDate + ", endDate" + endDate + "]";
	}

	public PrepaidC() {
		super();
	}


	public PrepaidC(String cardNo) {
		super();
		this.cardNo = cardNo;
	}
	

	public PrepaidC(Long id, String state) {
		super();
		this.id = id;
		this.state = state;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public Long getAccountID() {
		return accountID;
	}


	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}


	public BigDecimal getFaceValue() {
		return faceValue;
	}


	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}


	public BigDecimal getCost_() {
		return cost_;
	}


	public void setCost_(BigDecimal cost) {
		this.cost_ = cost;
	}


	public BigDecimal getRealCost() {
		return realCost;
	}


	public void setRealCost(BigDecimal realCost) {
		this.realCost = realCost;
	}


	public BigDecimal getTransferSum() {
		return transferSum;
	}


	public void setTransferSum(BigDecimal transferSum) {
		this.transferSum = transferSum;
	}


	public BigDecimal getReturnMoney() {
		return returnMoney;
	}


	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}


	public Date getSaleTime() {
		return saleTime;
	}


	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getSaleFlag() {
		return saleFlag;
	}


	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}


	public String getPayFlag() {
		return payFlag;
	}


	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}


	public Long getSaleOperId() {
		return saleOperId;
	}


	public void setSaleOperId(Long saleOperId) {
		this.saleOperId = saleOperId;
	}


	public Long getSalePlaceId() {
		return salePlaceId;
	}


	public void setSalePlaceId(Long salePlaceId) {
		this.salePlaceId = salePlaceId;
	}


	public String getInvoicePrint() {
		return invoicePrint;
	}


	public void setInvoicePrint(String invoicePrint) {
		this.invoicePrint = invoicePrint;
	}


	public String getS_con_pwd_flag() {
		return s_con_pwd_flag;
	}


	public void setS_con_pwd_flag(String s_con_pwd_flag) {
		this.s_con_pwd_flag = s_con_pwd_flag;
	}


	public String getTradingPwd() {
		return tradingPwd;
	}


	public void setTradingPwd(String tradingPwd) {
		this.tradingPwd = tradingPwd;
	}


	public Long getHisSeqID() {
		return hisSeqID;
	}


	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getIsDaySet() {
		return isDaySet;
	}

	public void setIsDaySet(String isDaySet) {
		this.isDaySet = isDaySet;
	}
	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}
	public String getCardServicePwd() {
		return cardServicePwd;
	}
	public void setCardServicePwd(String cardServicePwd) {
		this.cardServicePwd = cardServicePwd;
	}

	public String getFirstRecharge() {
		return firstRecharge;
	}

	public void setFirstRecharge(String firstRecharge) {
		this.firstRecharge = firstRecharge;
	}
}
