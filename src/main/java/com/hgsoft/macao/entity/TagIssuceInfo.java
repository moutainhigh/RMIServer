package com.hgsoft.macao.entity;

import java.math.BigDecimal;
import java.util.Date;



public class TagIssuceInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String interCode;
	private String createTime;
	private String tagNo;
	private String vehiclePlate;
	private String vehicleColor;
	private String model;
	private String vehicleType;
	private String vehicleWeightLimits;
	private Long vehicleLong;
	private Long vehicleWidth;
	private Long vehicleHeight;
	private Long vehicleAxles;
	private Long vehicleWheels;
	private String customerName;
	private String owner;
	private String zipCode;
	private String address;
	private String cnName;
	private String tel;
	private String shortMsg;
	private String identificationCode;
	private String usingNature;
	private String vehicleSpecificInformation;
	private String vehicleEngineNo;
	private Date endTime;
	private String tagChipNo;
	private String systemNo;
	private String serMemo;
	private BigDecimal cost;
	private BigDecimal chargeCost;
	private String nscVehicleType;
	private String errorCode;
	private String serviceFlowNo;
	private String saleType;
	private String installman;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private String apliayAcount;
	private String 	authMan;
	private String apliayType;
	
	

	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getInstallman() {
		return installman;
	}
	public void setInstallman(String installman) {
		this.installman = installman;
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
	public String getApliayAcount() {
		return apliayAcount;
	}
	public void setApliayAcount(String apliayAcount) {
		this.apliayAcount = apliayAcount;
	}
	public String getAuthMan() {
		return authMan;
	}
	public void setAuthMan(String authMan) {
		this.authMan = authMan;
	}
	public String getApliayType() {
		return apliayType;
	}
	public void setApliayType(String apliayType) {
		this.apliayType = apliayType;
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
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
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
	public Long getVehicleLong() {
		return vehicleLong;
	}
	public void setVehicleLong(Long vehicleLong) {
		this.vehicleLong = vehicleLong;
	}
	public Long getVehicleWidth() {
		return vehicleWidth;
	}
	public void setVehicleWidth(Long vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}
	public Long getVehicleHeight() {
		return vehicleHeight;
	}
	public void setVehicleHeight(Long vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}
	public Long getVehicleAxles() {
		return vehicleAxles;
	}
	public void setVehicleAxles(Long vehicleAxles) {
		this.vehicleAxles = vehicleAxles;
	}
	public Long getVehicleWheels() {
		return vehicleWheels;
	}
	public void setVehicleWheels(Long vehicleWheels) {
		this.vehicleWheels = vehicleWheels;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getShortMsg() {
		return shortMsg;
	}
	public void setShortMsg(String shortMsg) {
		this.shortMsg = shortMsg;
	}
	public String getIdentificationCode() {
		return identificationCode;
	}
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	public String getUsingNature() {
		return usingNature;
	}
	public void setUsingNature(String usingNature) {
		this.usingNature = usingNature;
	}
	public String getVehicleSpecificInformation() {
		return vehicleSpecificInformation;
	}
	public void setVehicleSpecificInformation(String vehicleSpecificInformation) {
		this.vehicleSpecificInformation = vehicleSpecificInformation;
	}
	public String getVehicleEngineNo() {
		return vehicleEngineNo;
	}
	public void setVehicleEngineNo(String vehicleEngineNo) {
		this.vehicleEngineNo = vehicleEngineNo;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTagChipNo() {
		return tagChipNo;
	}
	public void setTagChipNo(String tagChipNo) {
		this.tagChipNo = tagChipNo;
	}
	public String getSystemNo() {
		return systemNo;
	}
	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}
	public String getSerMemo() {
		return serMemo;
	}
	public void setSerMemo(String serMemo) {
		this.serMemo = serMemo;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getChargeCost() {
		return chargeCost;
	}
	public void setChargeCost(BigDecimal chargeCost) {
		this.chargeCost = chargeCost;
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
