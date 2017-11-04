package com.hgsoft.macao.entity;

/**
 * 发票邮寄登记
 * @author guokunpeng
 *
 */
public class ServerChangeInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4217097084105735465L;
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String invoiceTitle;
	private String messagePhone;
	private String mailFlag;
	private String listFlag;
	private String arrearsFlag;
	private String monthSetFlag;
	private String systemType;
	private String errorCode;
	private String serviceFlowNo;
	private String userNo ;
	private String organ;
	private String memo;
	private String operNo;
	private String placeNo;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ServerChangeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServerChangeInfo(String interCode, String createTime, String code, String invoiceTitle,
			String systemType, String errorCode, String serviceFlowNo, String userNo, String organ, String memo,
			String operNo, String placeNo,String flag) {
		super();
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.invoiceTitle = invoiceTitle;
		this.systemType = systemType;
		this.errorCode = errorCode;
		this.serviceFlowNo = serviceFlowNo;
		this.userNo = userNo;
		this.organ = organ;
		this.memo = memo;
		this.operNo = operNo;
		this.placeNo = placeNo;
		this.flag = flag;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterCode() {
		return interCode;
	}
	public void setInterCode(String interCode) {
		this.interCode = interCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getMessagePhone() {
		return messagePhone;
	}
	public void setMessagePhone(String messagePhone) {
		this.messagePhone = messagePhone;
	}
	public String getMailFlag() {
		return mailFlag;
	}
	public void setMailFlag(String mailFlag) {
		this.mailFlag = mailFlag;
	}
	public String getListFlag() {
		return listFlag;
	}
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	public String getArrearsFlag() {
		return arrearsFlag;
	}
	public void setArrearsFlag(String arrearsFlag) {
		this.arrearsFlag = arrearsFlag;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getServiceFlowNo() {
		return serviceFlowNo;
	}
	public void setServiceFlowNo(String serviceFlowNo) {
		this.serviceFlowNo = serviceFlowNo;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public String getMonthSetFlag() {
		return monthSetFlag;
	}
	public void setMonthSetFlag(String monthSetFlag) {
		this.monthSetFlag = monthSetFlag;
	}
	
}
