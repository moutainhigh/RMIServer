package com.hgsoft.macao.entity;

import java.math.BigDecimal;



public class VechileChangeInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String interCode;
	private String createTime;
	private String code;
	private String serType;
	private String vehiclePlate;
	private String vehicleColor;
	private String memo;
	private String systemType;
	private String writeCardFlag;
	private String errorCode;
	private String serviceFlowNo;
	private String placeNo;
	private String operNo;
	private String vehicleType;
	private Long vehicleWeightLimits;
	private String nscVehicleType;
	
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
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getNscVehicleType() {
		return nscVehicleType;
	}
	public void setNscVehicleType(String nscVehicleType) {
		this.nscVehicleType = nscVehicleType;
	}
	public VechileChangeInfo() {
		super();
	}
	public VechileChangeInfo(Long id, String interCode, String createTime,
			String code, String serType, String vehiclePlate,
			String vehicleColor, String memo, String systemType,
			String writeCardFlag, String errorCode, String serviceFlowNo) {
		super();
		this.id = id;
		this.interCode = interCode;
		this.createTime = createTime;
		this.code = code;
		this.serType = serType;
		this.vehiclePlate = vehiclePlate;
		this.vehicleColor = vehicleColor;
		this.memo = memo;
		this.systemType = systemType;
		this.writeCardFlag = writeCardFlag;
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
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getWriteCardFlag() {
		return writeCardFlag;
	}
	public void setWriteCardFlag(String writeCardFlag) {
		this.writeCardFlag = writeCardFlag;
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
	public Long getVehicleWeightLimits() {
		return vehicleWeightLimits;
	}
	public void setVehicleWeightLimits(Long vehicleWeightLimits) {
		this.vehicleWeightLimits = vehicleWeightLimits;
	}
	
	
	
}
