package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PrepaidcHis entity. @author MyEclipse Persistence Tools
 */

public class PrepaidCHis implements java.io.Serializable {

	private static final long serialVersionUID = 1268175463081646675L;
	private Long id;
	private Date gentime;
	private String genreason;
	private String cardno;
	private Long customerID;
	private Long accountid;
	private String state;
	private BigDecimal facevalue;
	private BigDecimal cost_;
	private BigDecimal realcost;
	private BigDecimal transfersum;
	private BigDecimal returnmoney;
	private Date saletime;
	private String saleflag;
	private String payflag;
	private Long saleoperid;
	private Long saleplaceid;
	private String invoiceprint;
	private String s_con_pwd_flag;
	private String tradingpwd;
	private Long hisseqid;
	private String bind;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
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
	
	// Constructors

	/** default constructor */
	public PrepaidCHis() {
	}

	/** minimal constructor */
	public PrepaidCHis(String cardno, Long accountid) {
		this.cardno = cardno;
		this.accountid = accountid;
	}


	public PrepaidCHis(Date date, String genreason,PrepaidC prepaidC) {
		this.gentime = date;
		this.genreason = genreason;
		this.cardno = prepaidC.getCardNo();
		this.customerID = prepaidC.getCustomerID();
		this.accountid = prepaidC.getAccountID();
		this.state = prepaidC.getState();
		this.facevalue = prepaidC.getFaceValue();
		this.cost_ = prepaidC.getCost_();
		this.realcost = prepaidC.getRealCost();
		this.transfersum = prepaidC.getTransferSum();
		this.returnmoney = prepaidC.getReturnMoney();
		this.saletime = prepaidC.getSaleTime();
		this.saleflag = prepaidC.getSaleFlag();
		this.payflag = prepaidC.getPayFlag();
		this.saleoperid = prepaidC.getSaleOperId();
		this.saleplaceid = prepaidC.getSalePlaceId();
		this.invoiceprint = prepaidC.getInvoicePrint();
		this.s_con_pwd_flag = prepaidC.getS_con_pwd_flag();
		this.tradingpwd = prepaidC.getTradingPwd();
		this.hisseqid = prepaidC.getHisSeqID();
		this.isDaySet = prepaidC.getIsDaySet();
		this.settleDay = prepaidC.getSettleDay();
		this.settletTime = prepaidC.getSettletTime();
		this.bind = prepaidC.getBind();
		this.startDate = prepaidC.getStartDate();
		this.endDate = prepaidC.getEndDate();
		this.maintainTime = prepaidC.getMaintainTime();
		this.operNo = prepaidC.getOperNo();
		this.operName = prepaidC.getOperName();
		this.placeNo = prepaidC.getOperNo();
		this.placeName = prepaidC.getPlaceName();
		this.suit = prepaidC.getSuit();
		this.blackFlag = prepaidC.getBlackFlag();
		this.cardServicePwd = prepaidC.getCardServicePwd();
		this.invoiceChangeFlag=prepaidC.getInvoiceChangeFlag();
		this.writeCardFlag=prepaidC.getWriteCardFlag();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGentime() {
		return this.gentime;
	}

	public void setGentime(Date gentime) {
		this.gentime = gentime;
	}

	public String getGenreason() {
		return this.genreason;
	}

	public void setGenreason(String genreason) {
		this.genreason = genreason;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getFacevalue() {
		return facevalue;
	}

	public void setFacevalue(BigDecimal facevalue) {
		this.facevalue = facevalue;
	}

	public BigDecimal getCost_() {
		return cost_;
	}

	public void setCost_(BigDecimal cost) {
		this.cost_ = cost;
	}

	public BigDecimal getRealcost() {
		return realcost;
	}

	public void setRealcost(BigDecimal realcost) {
		this.realcost = realcost;
	}

	public BigDecimal getTransfersum() {
		return transfersum;
	}

	public void setTransfersum(BigDecimal transfersum) {
		this.transfersum = transfersum;
	}

	public BigDecimal getReturnmoney() {
		return returnmoney;
	}

	public void setReturnmoney(BigDecimal returnmoney) {
		this.returnmoney = returnmoney;
	}

	public Date getSaletime() {
		return this.saletime;
	}

	public void setSaletime(Date saletime) {
		this.saletime = saletime;
	}

	public String getSaleflag() {
		return this.saleflag;
	}

	public void setSaleflag(String saleflag) {
		this.saleflag = saleflag;
	}

	public String getPayflag() {
		return this.payflag;
	}

	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}

	public Long getSaleoperid() {
		return this.saleoperid;
	}

	public void setSaleoperid(Long saleoperid) {
		this.saleoperid = saleoperid;
	}

	public Long getSaleplaceid() {
		return this.saleplaceid;
	}

	public void setSaleplaceid(Long saleplaceid) {
		this.saleplaceid = saleplaceid;
	}

	public String getInvoiceprint() {
		return this.invoiceprint;
	}

	public void setInvoiceprint(String invoiceprint) {
		this.invoiceprint = invoiceprint;
	}

	public String getS_con_pwd_flag() {
		return s_con_pwd_flag;
	}

	public void setS_con_pwd_flag(String s_con_pwd_flag) {
		this.s_con_pwd_flag = s_con_pwd_flag;
	}

	public String getTradingpwd() {
		return this.tradingpwd;
	}

	public void setTradingpwd(String tradingpwd) {
		this.tradingpwd = tradingpwd;
	}

	public Long getHisseqid() {
		return this.hisseqid;
	}

	public void setHisseqid(Long hisseqid) {
		this.hisseqid = hisseqid;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
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
	
}