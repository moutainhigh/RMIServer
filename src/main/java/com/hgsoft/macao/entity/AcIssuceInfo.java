package com.hgsoft.macao.entity;

import java.math.BigDecimal;



public class AcIssuceInfo implements java.io.Serializable{
	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8279132170328708013L;
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String vehiclePlate;
	private String vehicleColor;
	private String vehicleType;
	private String vehicleWeightLimits;
	private String nscVehicleType;
	private String errorCode;
	private String serviceFlowNo;
	private String reqNo;
	private String placeNo;
	private String operNo;
	private String apliayType;
	private String apliayAcount;
	private BigDecimal amt;
	private BigDecimal realAmt;
	private String saleType;
	private String issuceFlowNo;

	
	public AcIssuceInfo() {
		super();
	}
	
	public AcIssuceInfo(String interCode, String createTime, String code, String vehiclePlate,
			String vehicleColor, String vehicleType, String vehicleWeightLimits, String nscVehicleType,
			String errorCode, String serviceFlowNo) {
		super();
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.vehiclePlate = vehiclePlate;
		this.vehicleColor = vehicleColor;
		this.vehicleType = vehicleType;
		this.vehicleWeightLimits = vehicleWeightLimits;
		this.nscVehicleType = nscVehicleType;
		this.errorCode = errorCode;
		this.serviceFlowNo = serviceFlowNo;
	}
	
	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
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

	public String getApliayType() {
		return apliayType;
	}

	public void setApliayType(String apliayType) {
		this.apliayType = apliayType;
	}

	public String getApliayAcount() {
		return apliayAcount;
	}

	public void setApliayAcount(String apliayAcount) {
		this.apliayAcount = apliayAcount;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getRealAmt() {
		return realAmt;
	}

	public void setRealAmt(BigDecimal realAmt) {
		this.realAmt = realAmt;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getIssuceFlowNo() {
		return issuceFlowNo;
	}

	public void setIssuceFlowNo(String issuceFlowNo) {
		this.issuceFlowNo = issuceFlowNo;
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
	public String getVehiclePlate() {
		return vehiclePlate;
	}
	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}
	public String getVehicleColor() {
		return vehicleColor;
	}
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleWeightLimits() {
		return vehicleWeightLimits;
	}
	public void setVehicleWeightLimits(String vehicleWeightLimits) {
		this.vehicleWeightLimits = vehicleWeightLimits;
	}
	public String getNscVehicleType() {
		return nscVehicleType;
	}
	public void setNscVehicleType(String nscVehicleType) {
		this.nscVehicleType = nscVehicleType;
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
