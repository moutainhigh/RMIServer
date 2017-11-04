package com.hgsoft.acms.other.entity;

import com.hgsoft.customer.entity.Customer;

import java.util.Date;

public class ReceiptACMS implements java.io.Serializable{

	private static final long serialVersionUID = -5628507523750984503L;
	/**
	 * 主键，唯一标识
	 */
	private Long id ;
	/**
	 * 回执编号
	 */
	private String receiptNo ;
	/**
	 * 业务大类(1.账户信息，2.储值卡，3.记帐卡，4.电子标签，5.客户信息，6.日结，7.车辆信息业务)
	 */
	private String parentTypeCode ;
	/**
	 * 业务小类
	 */
	private String typeCode ;
	/**
	 * 业务小类名称
	 */
	private String typeChName;
	/**
	 * 业务ID(废弃)
	 */
	private Long businessId ;
	/**
	 * 客户ID(废弃)
	 */
	private Long customerId ;
	/**
	 * 客户历史序列ID（废弃）
	 */
	private Long customerHisId;
	/**
	 * 主账户历史序列ID(废弃)
	 */
	private Long mainAccountHisId;
	/**
	 * 车辆历史序列ID(废弃)
	 */
	private Long vehicleHisId;
	/**
	 * 发票抬头（废弃）
	 */
	private String  invoiceTitle;
	/**
	 * 服务项目（废弃）
	 */
	private String seritem;
	/**
	 * 回执打印次数
	 */
	private Integer printNum;
	/**
	 * 客户名称
	 */
	private String organ;
	/**
	 * 绘制内容，json格式。值由对应的回执vo set进来
	 */
	private String content;
	/**
	 * 操作时间
	 */
	private Date createTime ;
	/**
	 * 操作网点ID
	 */
	private Long placeId  ;
	/**
	 * 网点编号
	 */
	private String placeNo;
	/**
	 * 网点名称
	 */
	private String placeName;
	/**
	 * 操作员ID
	 */
	private Long operId ;
	/**
	 * 操作员编号
	 */
	private String operNo;
	/**
	 * 操作员名称
	 */
	private String operName;

	public ReceiptACMS(){

	}

	public ReceiptACMS(Long id, String receiptNo, String parentTypeCode, String typeCode, String typeChName, Integer printNum, String organ,Date createTime, Long placeId, String placeNo, String placeName, Long operId, String operNo, String operName) {
		this.id = id;
		this.receiptNo = receiptNo;
		this.parentTypeCode = parentTypeCode;
		this.typeCode = typeCode;
		this.typeChName = typeChName;
		this.printNum = printNum;
		this.organ = organ;
		this.createTime = createTime;
		this.placeId = placeId;
		this.placeNo = placeNo;
		this.placeName = placeName;
		this.operId = operId;
		this.operNo = operNo;
		this.operName = operName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getParentTypeCode() {
		return parentTypeCode;
	}

	public void setParentTypeCode(String parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeChName() {
		return typeChName;
	}

	public void setTypeChName(String typeChName) {
		this.typeChName = typeChName;
	}

	@Deprecated
	public Long getBusinessId() {
		return businessId;
	}

	@Deprecated
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	@Deprecated
	public Long getCustomerId() {
		return customerId;
	}

	@Deprecated
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Deprecated
	public Long getCustomerHisId() {
		return customerHisId;
	}

	@Deprecated
	public void setCustomerHisId(Long customerHisId) {
		this.customerHisId = customerHisId;
	}

	@Deprecated
	public Long getMainAccountHisId() {
		return mainAccountHisId;
	}

	@Deprecated
	public void setMainAccountHisId(Long mainAccountHisId) {
		this.mainAccountHisId = mainAccountHisId;
	}

	@Deprecated
	public Long getVehicleHisId() {
		return vehicleHisId;
	}

	@Deprecated
	public void setVehicleHisId(Long vehicleHisId) {
		this.vehicleHisId = vehicleHisId;
	}

	@Deprecated
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	@Deprecated
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Deprecated
	public String getSeritem() {
		return seritem;
	}

	@Deprecated
	public void setSeritem(String seritem) {
		this.seritem = seritem;
	}

	public Integer getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
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

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
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

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Receipt{");
		sb.append("id=").append(id);
		sb.append(", receiptNo='").append(receiptNo).append('\'');
		sb.append(", parentTypeCode='").append(parentTypeCode).append('\'');
		sb.append(", typeCode='").append(typeCode).append('\'');
		sb.append(", typeChName='").append(typeChName).append('\'');
		sb.append(", businessId=").append(businessId);
		sb.append(", customerId=").append(customerId);
		sb.append(", customerHisId=").append(customerHisId);
		sb.append(", mainAccountHisId=").append(mainAccountHisId);
		sb.append(", vehicleHisId=").append(vehicleHisId);
		sb.append(", invoiceTitle='").append(invoiceTitle).append('\'');
		sb.append(", seritem='").append(seritem).append('\'');
		sb.append(", printNum=").append(printNum);
		sb.append(", organ='").append(organ).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", placeId=").append(placeId);
		sb.append(", placeNo='").append(placeNo).append('\'');
		sb.append(", placeName='").append(placeName).append('\'');
		sb.append(", operId=").append(operId);
		sb.append(", operNo='").append(operNo).append('\'');
		sb.append(", operName='").append(operName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
