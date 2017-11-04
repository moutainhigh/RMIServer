package com.hgsoft.jointCard.entity;

import java.io.Serializable;

public class CardHolder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String idType;
	private String idCode;
	private String invoiceTitle;
	private String remark;
	private String cardNo;
	private Long accountCId;
	private Long hisSeqId;
	private Long operId;
	private String operCode;
	private String operName;
	private Long placeId;
	private String placeCode;
	private String placeName;
	private String linkMan;
	private String phoneNum;
	private String mobileNum;
	private String linkAddr;

	/*YGZ RuiHaoZ Add 新增字段*/
	private String userNo;//用户号
	private String userType; //用户类型
	private String secondNo;//二级编码
	private String secondName;//二级编码名称
	private String organTel;//客户本人手机
	private String agentName;//经办人名称
	private String agentTel;//经办人手机
	private String agentIdType;//经办人证件类型
	private String agentIdCode;//经办人证件号码
	private String writeSecond; //本部/分支机构

	public String getWriteSecond() {
		return writeSecond;
	}

	public void setWriteSecond(String writeSecond) {
		this.writeSecond = writeSecond;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

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



	public boolean isEmpty() {
		if (id == null && (name == null || name.isEmpty()) && (idType == null || idType.isEmpty())
				&& (idCode == null || idCode.isEmpty()) && (invoiceTitle == null || invoiceTitle.isEmpty())
				&& (remark == null || remark.isEmpty()) && (cardNo == null || cardNo.isEmpty()) && accountCId == null
				&& hisSeqId == null && operId == null && (operCode == null || operCode.isEmpty())
				&& (operName == null || operName.isEmpty()) && placeId == null
				&& (placeCode == null || placeCode.isEmpty()) && (placeName == null || placeName.isEmpty())
				&& (linkMan == null || linkMan.isEmpty()) && (phoneNum == null || phoneNum.isEmpty())
				&& (mobileNum == null || mobileNum.isEmpty()) && (linkAddr == null || linkAddr.isEmpty())) {
			return true;
		} else {
			return false;
		} // if
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getAccountCId() {
		return accountCId;
	}

	public void setAccountCId(Long accountCId) {
		this.accountCId = accountCId;
	}

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}
	
}