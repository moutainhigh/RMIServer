package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoCardCustomer implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2067752873524120192L;
	
	private Long id;
	private Date reqTime;
	private String bankCode;
	private Long branchId;
	private String bankAccountNumber;
	private String idCardType;
	private String idCardNumber;
	private String enName;
	private String cnName;
	private Date birDate;
	private String sex;
	private String tel;
	private String address;
	private String email;
	private String userType;
	private String shortMsg;
	private String invoiceTitle;
	private String state;
	private Date sureTime;
	private String servicePwd;
	
	private Date reqOperTime;
	private Long reqOperId;
	private Long reqPlaceId;
	private String reqOperNo;
	private String reqOperName;
	private String reqPlaceNo;
	private String reqPlaceName;
	private Long sureOperId;
	private Long surePlaceId;
	private String sureOperNo;
	private String surePlaceNo;
	private String sureOperName;
	private String surePlaceName;
	private Long hisSeqId;
	
	private String reqSN;
	private String resSN;
	private Date resTime;
	private String isNotify;
	/*YGZ RuiHaoZ Add 新增字段*/
	private String userNo;//用户号
	private String secondNo;//二级编码
	private String secondName;//二级编码名称
	private String organTel;//客户本人手机
	private String agentName;//经办人名称
	private String agentTel;//经办人手机
	private String agentIdType;//经办人证件类型
	private String agentIdCode;//经办人证件号码
	private String writeSecond; //本部/分支机构

	public String getSecondNo() {
		return secondNo;
	}

	public void setSecondNo(String secondNo) {
		this.secondNo = secondNo;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getWriteSecond() {
		return writeSecond;
	}

	public void setWriteSecond(String writeSecond) {
		this.writeSecond = writeSecond;
	}

	public String getOrganTel() {
		return organTel;
	}

	public void setOrganTel(String organTel) {
		this.organTel = organTel;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentTel() {
		return agentTel;
	}

	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}

	public String getAgentIdType() {
		return agentIdType;
	}

	public void setAgentIdType(String agentIdType) {
		this.agentIdType = agentIdType;
	}

	public String getAgentIdCode() {
		return agentIdCode;
	}

	public void setAgentIdCode(String agentIdCode) {
		this.agentIdCode = agentIdCode;
	}



	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getIsNotify() {
		return isNotify;
	}
	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}
	public String getReqSN() {
		return reqSN;
	}
	public void setReqSN(String reqSN) {
		this.reqSN = reqSN;
	}
	public String getResSN() {
		return resSN;
	}
	public void setResSN(String resSN) {
		this.resSN = resSN;
	}
	public Date getResTime() {
		return resTime;
	}
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}
	public Date getReqOperTime() {
		return reqOperTime;
	}
	public void setReqOperTime(Date reqOperTime) {
		this.reqOperTime = reqOperTime;
	}
	public Long getReqOperId() {
		return reqOperId;
	}
	public void setReqOperId(Long reqOperId) {
		this.reqOperId = reqOperId;
	}
	public Long getReqPlaceId() {
		return reqPlaceId;
	}
	public void setReqPlaceId(Long reqPlaceId) {
		this.reqPlaceId = reqPlaceId;
	}
	public String getReqOperNo() {
		return reqOperNo;
	}
	public void setReqOperNo(String reqOperNo) {
		this.reqOperNo = reqOperNo;
	}
	public String getReqOperName() {
		return reqOperName;
	}
	public void setReqOperName(String reqOperName) {
		this.reqOperName = reqOperName;
	}
	public String getReqPlaceNo() {
		return reqPlaceNo;
	}
	public void setReqPlaceNo(String reqPlaceNo) {
		this.reqPlaceNo = reqPlaceNo;
	}
	public String getReqPlaceName() {
		return reqPlaceName;
	}
	public void setReqPlaceName(String reqPlaceName) {
		this.reqPlaceName = reqPlaceName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public Date getBirDate() {
		return birDate;
	}
	public void setBirDate(Date birDate) {
		this.birDate = birDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getShortMsg() {
		return shortMsg;
	}
	public void setShortMsg(String shortMsg) {
		this.shortMsg = shortMsg;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getSureTime() {
		return sureTime;
	}
	public void setSureTime(Date sureTime) {
		this.sureTime = sureTime;
	}
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	public String getServicePwd() {
		return servicePwd;
	}
	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}
	public String getSureOperName() {
		return sureOperName;
	}
	public void setSureOperName(String sureOperName) {
		this.sureOperName = sureOperName;
	}
	public String getSurePlaceName() {
		return surePlaceName;
	}
	public void setSurePlaceName(String surePlaceName) {
		this.surePlaceName = surePlaceName;
	}
	public Long getSureOperId() {
		return sureOperId;
	}
	public void setSureOperId(Long sureOperId) {
		this.sureOperId = sureOperId;
	}
	public Long getSurePlaceId() {
		return surePlaceId;
	}
	public void setSurePlaceId(Long surePlaceId) {
		this.surePlaceId = surePlaceId;
	}
	public String getSureOperNo() {
		return sureOperNo;
	}
	public void setSureOperNo(String sureOperNo) {
		this.sureOperNo = sureOperNo;
	}
	public String getSurePlaceNo() {
		return surePlaceNo;
	}
	public void setSurePlaceNo(String surePlaceNo) {
		this.surePlaceNo = surePlaceNo;
	}
	
}
