package com.hgsoft.macao.entity;

/**
 * 记帐卡注销
 * @author guokunpeng
 *
 */
public class AcCancelInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5224320484234046795L;
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String cancelType;
	private String cancelFlag;
	private String canceReason;
	private String memo;
	private String systemNo;
	private String errorCode;
	private String serviceFlowNo;
	private String flag;
	private String bankNo;
	private String placeNo;
	private String operNo;
	
	public AcCancelInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AcCancelInfo(String interCode, String createTime, String code, String cancelType, String cancelFlag,
			String canceReason, String memo, String systemNo, String errorCode, String serviceFlowNo) {
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.cancelType = cancelType;
		this.cancelFlag = cancelFlag;
		this.canceReason = canceReason;
		this.memo = memo;
		this.systemNo = systemNo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getCanceReason() {
		return canceReason;
	}

	public void setCanceReason(String canceReason) {
		this.canceReason = canceReason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
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
