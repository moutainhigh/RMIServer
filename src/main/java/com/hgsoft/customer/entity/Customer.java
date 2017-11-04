
package com.hgsoft.customer.entity;

import java.util.Date;

public class Customer  implements java.io.Serializable {

	private static final long serialVersionUID = -3058602204780838593L;

	private Long id;
    
	private String userNo;
    
	private String organ;
    
	private String servicePwd;
    
	private String userType;
    
	private String linkMan;
    
	private String idType;
    
	private String idCode;
    
	private String registeredCapital;
    
	private String tel;
    
	private String mobile;
    
	private String shortTel;
    
	private String addr;
    
	private String zipCode;
    
	private String email;
    
	private String state;
    
	private Date cancelTime;
    
	private Long operId;
    
	private Date upDateTime;
    
	private Date firRunTime;
    
	private Long placeId;
    
	private Long hisSeqId;
	
	private String systemType;
	
	private String operNo;
	
	private String operName;
	
	private String placeNo;
	
	private String placeName;
	
	
	private String secondNo;//2017/04/27 需求新增的二级编码，给企业用户的下级客户用
	private String secondName;//2017/04/27 需求新增的二级名称，给企业用户的下级客户用
	private String defaultSerPwd;//是否使用默认的服务密码，代理点使用
	
	private String exceptionMessage;//数据迁移相关：异常信息
	private String handleFlag;////数据迁移相关：异常客户处理标志

	/*YGZ RuiHaoZ  add*/
	private String organTel;//客户本人手机
	private String agentName;//经办人名称
	private String agentTel;//经办人手机
	private String agentIdType;//经办人证件类型
	private String agentIdCode;//经办人证件号码
	private String writeSecond;//分支机构和本部

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

	public Customer() {
		super();
	}
	
	public Customer(String userNo) {
		super();
		this.userNo = userNo;
	}

	public Customer(String idType, String idCode) {
		super();
		this.idType = idType;
		this.idCode = idCode;
	}
	
	public Customer(String customerName,String idType, String idCode) {
		super();
		this.organ = customerName;
		this.idType = idType;
		this.idCode = idCode;
	}

	public Customer(Long id, String userNo, String organ, String servicePwd, String userType, String linkMan,
			String idType, String idCode, String registeredCapital, String tel, String mobile, String shortTel,
			String addr, String zipCode, String email, String state, Date cancelTime, Long operId, Date upDateTime,
			Date firRunTime, Long placeId, Long hisSeqId, String systemType, String operNo, String operName,
			String placeNo, String placeName,String secondNo,String secondName,String exceptionMessage,String handleFlag) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.organ = organ;
		this.servicePwd = servicePwd;
		this.userType = userType;
		this.linkMan = linkMan;
		this.idType = idType;
		this.idCode = idCode;
		this.registeredCapital = registeredCapital;
		this.tel = tel;
		this.mobile = mobile;
		this.shortTel = shortTel;
		this.addr = addr;
		this.zipCode = zipCode;
		this.email = email;
		this.state = state;
		this.cancelTime = cancelTime;
		this.operId = operId;
		this.upDateTime = upDateTime;
		this.firRunTime = firRunTime;
		this.placeId = placeId;
		this.hisSeqId = hisSeqId;
		this.systemType = systemType;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
		this.secondNo = secondNo;
		this.secondName = secondName;
		this.exceptionMessage = exceptionMessage;
		this.handleFlag = handleFlag;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", userNo=" + userNo + ", organ=" + organ + ", servicePwd=" + servicePwd
				+ ", userType=" + userType + ", linkMan=" + linkMan + ", idType=" + idType + ", idCode=" + idCode
				+ ", registeredCapital=" + registeredCapital + ", tel=" + tel + ", mobile=" + mobile + ", shortTel="
				+ shortTel + ", addr=" + addr + ", zipCode=" + zipCode + ", email=" + email + ", state=" + state
				+ ", cancelTime=" + cancelTime + ", operId=" + operId + ", upDateTime=" + upDateTime + ", firRunTime="
				+ firRunTime + ", placeId=" + placeId + ", hisSeqId=" + hisSeqId + ", systemType=" + systemType
				+ ", operNo=" + operNo + ", operName=" + operName + ", placeNo=" + placeNo + ", placeName=" + placeName
				+ ", secondNo=" + secondNo+ ", secondName=" + secondName+ ", exceptionMessage=" + exceptionMessage+ ", handleFlag=" + handleFlag
				+ "]";
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

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShortTel() {
		return shortTel;
	}

	public void setShortTel(String shortTel) {
		this.shortTel = shortTel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Date getUpDateTime() {
		return upDateTime;
	}

	public void setUpDateTime(Date upDateTime) {
		this.upDateTime = upDateTime;
	}

	public Date getFirRunTime() {
		return firRunTime;
	}

	public void setFirRunTime(Date firRunTime) {
		this.firRunTime = firRunTime;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
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

	public String getDefaultSerPwd() {
		return defaultSerPwd;
	}

	public void setDefaultSerPwd(String defaultSerPwd) {
		this.defaultSerPwd = defaultSerPwd;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(String handleFlag) {
		this.handleFlag = handleFlag;
	}
	
	
}