package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCInfoHis implements Serializable{

	private static final long serialVersionUID = -2641802358105174684L;
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
	private Date genTime;
	private String genReason;
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
	
	public AccountCInfoHis() {
		super();
	}
	
	public AccountCInfoHis(Long id, String cardNo, Long customerId, Long accountId, String state, BigDecimal cost,
			BigDecimal realCost, Date issueTime, String issueFlag, Long issueOperId, Long issuePlaceId,
			String s_con_pwd_flag, String tradingPwd, Long hisSeqId, Date genTime, String genReason, String isDaySet,
			String settleDay, Date settletTime, String bind, Long agentsMay, String linkMan, String linkTel,
			String linkMobile, String linkAddr, String linkZipCode,String cardServicePwd,String writeCardFlag) {
		super();
		this.id = id;
		this.cardNo = cardNo;
		this.customerId = customerId;
		this.accountId = accountId;
		this.state = state;
		this.cost = cost;
		this.realCost = realCost;
		this.issueTime = issueTime;
		this.issueFlag = issueFlag;
		this.issueOperId = issueOperId;
		this.issuePlaceId = issuePlaceId;
		this.s_con_pwd_flag = s_con_pwd_flag;
		this.tradingPwd = tradingPwd;
		this.hisSeqId = hisSeqId;
		this.genTime = genTime;
		this.genReason = genReason;
		this.isDaySet = isDaySet;
		this.settleDay = settleDay;
		this.settletTime = settletTime;
		this.bind = bind;
		this.agentsMay = agentsMay;
		this.linkMan = linkMan;
		this.linkTel = linkTel;
		this.linkMobile = linkMobile;
		this.linkAddr = linkAddr;
		this.linkZipCode = linkZipCode;
		this.cardServicePwd = cardServicePwd;
		this.writeCardFlag=writeCardFlag;
	}

	public AccountCInfoHis(Long id, String cardNo, Long customerId,
			Long accountId, String state, BigDecimal cost, BigDecimal realCost,
			Date issueTime, String issueFlag, Long issueOperId,
			Long issuePlaceId, String s_con_pwd_flag, String tradingPwd,
			Long hisSeqId, Date genTime, String genReason,String isDaySet,String settleDay,Date settletTime,String cardServicePwd,String writeCardFlag) {
		super();
		this.id = id;
		this.cardNo = cardNo;
		this.customerId = customerId;
		this.accountId = accountId;
		this.state = state;
		this.cost = cost;
		this.realCost = realCost;
		this.issueTime = issueTime;
		this.issueFlag = issueFlag;
		this.issueOperId = issueOperId;
		this.issuePlaceId = issuePlaceId;
		this.s_con_pwd_flag = s_con_pwd_flag;
		this.tradingPwd = tradingPwd;
		this.hisSeqId = hisSeqId;
		this.genTime = genTime;
		this.genReason = genReason;
		this.isDaySet = isDaySet;
		this.settleDay = settleDay;
		this.settletTime = settletTime;
		this.cardServicePwd = cardServicePwd;
		this.writeCardFlag=writeCardFlag;
	}
	
	public AccountCInfoHis(Date genTime, String genReason, AccountCInfo accountCInfo) {
		this.genTime = genTime;
		this.genReason = genReason;
		this.id = accountCInfo.getId();
		this.cardNo = accountCInfo.getCardNo();
		this.customerId = accountCInfo.getCustomerId();
		this.accountId = accountCInfo.getAccountId();
		this.state = accountCInfo.getState();
		this.cost = accountCInfo.getCost();
		this.realCost = accountCInfo.getRealCost();
		this.issueTime = accountCInfo.getIssueTime();
		this.issueFlag = accountCInfo.getIssueFlag();
		this.issueOperId = accountCInfo.getIssueOperId();
		this.issuePlaceId = accountCInfo.getIssuePlaceId();
		this.s_con_pwd_flag = accountCInfo.getS_con_pwd_flag();
		this.tradingPwd = accountCInfo.getTradingPwd();
		this.hisSeqId = accountCInfo.getHisSeqId();
		this.isDaySet = accountCInfo.getIsDaySet();
		this.settleDay = accountCInfo.getSettleDay();
		this.settletTime = accountCInfo.getSettletTime();
		this.agentsMay = accountCInfo.getAgentsMay();
		this.linkMan = accountCInfo.getLinkMan();
		this.linkTel = accountCInfo.getLinkTel();
		this.linkMobile = accountCInfo.getLinkMobile();
		this.linkAddr = accountCInfo.getLinkAddr();
		this.linkZipCode = accountCInfo.getLinkZipCode();
		this.maintainTime = accountCInfo.getMaintainTime();
		this.bind = accountCInfo.getBind();
		this.agentsMay = accountCInfo.getAgentsMay();
		this.startDate = accountCInfo.getStartDate();
		this.endDate = accountCInfo.getEndDate();
		this.linkMan = accountCInfo.getLinkMan();
		this.linkTel = accountCInfo.getLinkTel();
		this.linkMobile = accountCInfo.getLinkMobile();
		this.linkAddr = accountCInfo.getLinkAddr();
		this.linkZipCode = accountCInfo.getLinkZipCode();
		this.operNo = accountCInfo.getOperNo();
		this.operName = accountCInfo.getOperName();
		this.placeNo = accountCInfo.getPlaceNo();
		this.placeName = accountCInfo.getPlaceName();
		this.suit = accountCInfo.getSuit();
		this.blackFlag = accountCInfo.getBlackFlag();
		this.cardServicePwd = accountCInfo.getCardServicePwd();
		this.writeCardFlag=accountCInfo.getWriteCardFlag();
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
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getGenReason() {
		return genReason;
	}
	public void setGenReason(String genReason) {
		this.genReason = genReason;
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
	public String getCardServicePwd() {
		return cardServicePwd;
	}
	public void setCardServicePwd(String cardServicePwd) {
		this.cardServicePwd = cardServicePwd;
	}
	
}
