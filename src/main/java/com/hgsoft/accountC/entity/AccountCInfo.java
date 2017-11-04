package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCInfo implements Serializable{

	private static final long serialVersionUID = -4054887476885679297L;
	private Long id;
	private String cardNo;
	private Long customerId;
	private Long accountId;
	private String state;
	private BigDecimal cost;
	private BigDecimal realCost;
	private Date issueTime;
	private String issueFlag;
	private Long issueOperId;
	private Long issuePlaceId;
	private String s_con_pwd_flag;
	private String tradingPwd;
	private Long hisSeqId;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	private String bind;
	private Date startDate;
	private Date endDate;
	private Long agentsMay;
	private String linkMan;
	private String linkTel;
	private String linkMobile;
	private String linkAddr;
	private String linkZipCode;
	private Date maintainTime;
	private BigDecimal bail;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private String suit;
	private String blackFlag;
	
	private String cardServicePwd;
	private String writeCardFlag;
	
	public String getWriteCardFlag() {
		return writeCardFlag;
	}
	public void setWriteCardFlag(String writeCardFlag) {
		this.writeCardFlag = writeCardFlag;
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
	public AccountCInfo() {
		super();
	}
	
	public AccountCInfo(String cardNo) {
		super();
		this.cardNo = cardNo;
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
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getRealCost() {
		return realCost;
	}
	public void setRealCost(BigDecimal realCost) {
		this.realCost = realCost;
	}
	public Date getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}
	public String getIssueFlag() {
		return issueFlag;
	}
	public void setIssueFlag(String issueFlag) {
		this.issueFlag = issueFlag;
	}

	public Long getIssueOperId() {
		return issueOperId;
	}
	public void setIssueOperId(Long issueOperId) {
		this.issueOperId = issueOperId;
	}
	public Long getIssuePlaceId() {
		return issuePlaceId;
	}
	public void setIssuePlaceId(Long issuePlaceId) {
		this.issuePlaceId = issuePlaceId;
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
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	@Override
	public String toString() {
		return "AccountCInfo [id=" + id + ", cardNo=" + cardNo + ", customerId=" + customerId + ", accountId="
				+ accountId + ", state=" + state + ", cost=" + cost + ", realCost=" + realCost + ", issueTime="
				+ issueTime + ", issueFlag=" + issueFlag  + ", issueOperId=" + issueOperId
				+ ", issuePlaceId=" + issuePlaceId  + ", s_con_pwd_flag="
				+ s_con_pwd_flag + ", tradingPwd=" + tradingPwd + ", hisSeqId=" + hisSeqId+ ", bind=" + bind + "]";
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

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public Long getAgentsMay() {
		return agentsMay;
	}

	public void setAgentsMay(Long agentsMay) {
		this.agentsMay = agentsMay;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getLinkZipCode() {
		return linkZipCode;
	}

	public void setLinkZipCode(String linkZipCode) {
		this.linkZipCode = linkZipCode;
	}

	public Date getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}

	public BigDecimal getBail() {
		return bail;
	}

	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}
	public String getCardServicePwd() {
		return cardServicePwd;
	}
	public void setCardServicePwd(String cardServicePwd) {
		this.cardServicePwd = cardServicePwd;
	}
	
}
