package com.hgsoft.customer.entity;

import java.util.Date;


public class CustomerImp  implements java.io.Serializable {

	private static final long serialVersionUID = 8241931748768479399L;
	
	private Long id;
	private String organ;//客户名称
	private String userType;//客户类型
	private String linkMan;//联系人
	private String idType;//证件类型
	private String idCode;//证件号码
	private String tel;//联系电话
	private String mobile;//联系手机
	private String shortTel;//短信服务号码
	private String addr;//地址
	private String zipCode;//邮政编码
	private String email;//电子邮箱
	private String invoiceTitle;//发票抬头
	private String flag;//标志：0:未使用，1:已使用
	private Date impTime;//导入时间
	private Date updateTime;//更新时间
	private Long operId;
	private String operName;
	private String operNo;
	private Long placeId;
	private String placeName;
	private String placeNo;


	/*YGZ RuiHaoZ Add 新增字段*/
	private String organTel;//客户本人手机


	public String getOrganTel() {
		return organTel;
	}

	public void setOrganTel(String organTel) {
		this.organTel = organTel;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getImpTime() {
		return impTime;
	}
	public void setImpTime(Date impTime) {
		this.impTime = impTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
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
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	
	
}