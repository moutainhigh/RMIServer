package com.hgsoft.timerTask.vo;

public class MQTimerVo {

	private String operNo;
	private String placeNo;
	private String bankNo;
	private String reqNo;//记帐卡二次发行申请编号
	private String sysNo;//记帐卡注销系统编号
	private String installMan;//安装员名称
	private String operName;//操作员名称
	private String placeName;//网点名称
	private String issueOperNo;//发行操作员编码
	private String issueOperName;//发行操作员名称
	private String issuePlaceNo;//发行网点编码
	private String issuePlaceName;//发行网点名称
	private String saveLossFlag;//卡片挂失标志位
	private String unLossFlag;//卡片解挂标志位
	private String apliayType;//澳门通支付方式
	private String apliayAccount;//支付账号
	private String saleType;//销售方式
	private String authMemberNo;//授权人编号
	private String cancelFlag;//注销标志位
	private String cancelAccountFlag;//销户标志位
	private String cancelReason;//注销原因
	private String userNo;//客户号
	private String apliayMode;//电子标签交易方式
	private String temporary;//临时办卡点
	
	public String getTemporary() {
		return temporary;
	}
	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}
	public String getApliayMode() {
		return apliayMode;
	}
	public void setApliayMode(String apliayMode) {
		this.apliayMode = apliayMode;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCancelAccountFlag() {
		return cancelAccountFlag;
	}
	public void setCancelAccountFlag(String cancelAccountFlag) {
		this.cancelAccountFlag = cancelAccountFlag;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getAuthMemberNo() {
		return authMemberNo;
	}
	public void setAuthMemberNo(String authMemberNo) {
		this.authMemberNo = authMemberNo;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getApliayType() {
		return apliayType;
	}
	public void setApliayType(String apliayType) {
		this.apliayType = apliayType;
	}
	public String getApliayAccount() {
		return apliayAccount;
	}
	public void setApliayAccount(String apliayAccount) {
		this.apliayAccount = apliayAccount;
	}
	public String getUnLossFlag() {
		return unLossFlag;
	}
	public void setUnLossFlag(String unLossFlag) {
		this.unLossFlag = unLossFlag;
	}
	public String getSaveLossFlag() {
		return saveLossFlag;
	}
	public void setSaveLossFlag(String saveLossFlag) {
		this.saveLossFlag = saveLossFlag;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getSysNo() {
		return sysNo;
	}
	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}
	public String getInstallMan() {
		return installMan;
	}
	public void setInstallMan(String installMan) {
		this.installMan = installMan;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getIssueOperNo() {
		return issueOperNo;
	}
	public void setIssueOperNo(String issueOperNo) {
		this.issueOperNo = issueOperNo;
	}
	public String getIssueOperName() {
		return issueOperName;
	}
	public void setIssueOperName(String issueOperName) {
		this.issueOperName = issueOperName;
	}
	public String getIssuePlaceNo() {
		return issuePlaceNo;
	}
	public void setIssuePlaceNo(String issuePlaceNo) {
		this.issuePlaceNo = issuePlaceNo;
	}
	public String getIssuePlaceName() {
		return issuePlaceName;
	}
	public void setIssuePlaceName(String issuePlaceName) {
		this.issuePlaceName = issuePlaceName;
	}
	public MQTimerVo() {
		super();
		this.operNo = "AMT001";
		this.placeNo = "860223001";
		this.bankNo = "0085328727688";
		this.reqNo = "416C0T00CM";
		this.sysNo = "17";
		this.installMan = "操作员1";
		this.operName = "";
		this.placeName = "";
		this.issueOperName = "主任1";
		this.issueOperNo = "AMT001";
		this.issuePlaceName = "澳门通股份有限公司";
		this.issuePlaceNo = "860223001";
		this.saveLossFlag="2";
		this.unLossFlag="1";
		this.apliayType="5";
		this.apliayAccount="";
		this.saleType="0001";
		this.authMemberNo="AMT001";
		this.cancelFlag="2";
		this.cancelAccountFlag="";
		this.cancelReason="11";
		this.userNo="85328727688";
		this.apliayMode="40";
		this.temporary="";
	}
	
	
	
}
