	package com.hgsoft.macao.entity;

/**
 * 记帐卡解除止付名单
 * @author guokunpeng
 *
 */
public class AcIssuedStopInfo implements java.io.Serializable{

	private static final long serialVersionUID = 509559154601436050L;
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String errorCode;
	private String serviceFlowNo;
	
	public AcIssuedStopInfo() {
		super();
	}
	public AcIssuedStopInfo(String interCode, String createTime, String code, String errorCode, String serviceFlowNo) {
		super();
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.errorCode = errorCode;
		this.serviceFlowNo = serviceFlowNo;
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
