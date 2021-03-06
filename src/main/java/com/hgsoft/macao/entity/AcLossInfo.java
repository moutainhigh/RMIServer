package com.hgsoft.macao.entity;

/**
 * 记帐卡挂失
 * @author guokunpeng
 *
 */
public class AcLossInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6156660559074567748L;
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String errorCode;
	private String serviceFlowNo;
	private String flag;
	private String bankNo;
	private String placeNo;
	private String operNo;

	
	public AcLossInfo() {
		super();
	}
	public AcLossInfo(String interCode, String createTime, String code, String errorCode, String serviceFlowNo) {
		super();
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.errorCode = errorCode;
		this.serviceFlowNo = serviceFlowNo;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
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
	
	
	

	
}
