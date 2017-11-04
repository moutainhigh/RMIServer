package com.hgsoft.agentCard.entity;

import java.io.Serializable;
import java.util.Date;

public class CardBusinessInfo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -400808395531278195L;
	private Long id;
	private Date businessTime;
	private String businessType;
	private String UTCardNo;
	private String creditCardNo;
	private String oldUTCardNo;
	private String oldCreditCardNo;
	private String organ;
	private String userType;
	private String idType;
	private String idCode;
	private String cusTel;
	private String cusMobile;
	private String cusAddr;
	private String cusZipCode;
	private String cusEmail;
	private String linkMan;
	private String linkTel;
	private String linkMobile;
	private String linkAddr;
	private String linkZipCode;
	private String remark;
	private String fileName;
	private String  isTransact;
	private Date importTime;
	private Long importPlace;
	private Long importOper;
	
	private String serviceFlowNO;
	private Date dealTime;
	private Long operId;
	private String operName;
	private Long placeId;
	private String placeName;
	private String operNo;
	private String placeNo;
	private String systemType;//系统类型
	private String servicePwd;//服务密码

	private String bankCode;	//银行编码

	private String errorCode;	//错误编码


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getServicePwd() {
		return servicePwd;
	}
	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
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
	public String getServiceFlowNO() {
		return serviceFlowNO;
	}
	public void setServiceFlowNO(String serviceFlowNO) {
		this.serviceFlowNO = serviceFlowNO;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getUTCardNo() {
		return UTCardNo;
	}
	public void setUTCardNo(String uTCardNo) {
		UTCardNo = uTCardNo;
	}
	public String getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public String getOldUTCardNo() {
		return oldUTCardNo;
	}
	public void setOldUTCardNo(String oldUTCardNo) {
		this.oldUTCardNo = oldUTCardNo;
	}
	public String getOldCreditCardNo() {
		return oldCreditCardNo;
	}
	public void setOldCreditCardNo(String oldCreditCardNo) {
		this.oldCreditCardNo = oldCreditCardNo;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	public String getCusTel() {
		return cusTel;
	}
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	public String getCusMobile() {
		return cusMobile;
	}
	public void setCusMobile(String cusMobile) {
		this.cusMobile = cusMobile;
	}
	public String getCusAddr() {
		return cusAddr;
	}
	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}
	public String getCusZipCode() {
		return cusZipCode;
	}
	public void setCusZipCode(String cusZipCode) {
		this.cusZipCode = cusZipCode;
	}
	public String getCusEmail() {
		return cusEmail;
	}
	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIsTransact() {
		return isTransact;
	}
	public void setIsTransact(String isTransact) {
		this.isTransact = isTransact;
	}
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	public Long getImportPlace() {
		return importPlace;
	}
	public void setImportPlace(Long importPlace) {
		this.importPlace = importPlace;
	}
	public Long getImportOper() {
		return importOper;
	}
	public void setImportOper(Long importOper) {
		this.importOper = importOper;
	}

}
