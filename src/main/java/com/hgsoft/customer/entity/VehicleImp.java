package com.hgsoft.customer.entity;

import java.util.Date;


public class VehicleImp  implements java.io.Serializable {

	private static final long serialVersionUID = 8241931748768479399L;
	
	private Long id;
	private String vehiclePlate;//车牌号码
	private String vehicleColor;//车牌颜色
	private String model;//品牌型号
	private String vehicleType;//车辆类型
	private Long vehicleWeightLimits;//车辆载重/座位数
	private Long vehicleWidth;//车宽
	private Long vehicleAxles;//车轴数
	private Long vehicleWheels;//车轮数
	private Long vehicleHeight;//车高
	private Long vehicleLong;//车长
	private String vehicleEngineNo;//车辆发动机号
	private String usingNature;//车辆使用性质
	private String identificationCode;//车辆识别代码（车架号）
	private String owner;//所有人（车主）
	private String flag;//标志：0:未使用，1:已使用
	private Date impTime;//导入时间
	private Date updateTime;//更新时间
	private Long operId;
	private String operName;
	private String operNo;
	private Long placeId;
	private String placeName;
	private String placeNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getVehicleWeightLimits() {
		return vehicleWeightLimits;
	}
	public void setVehicleWeightLimits(Long vehicleWeightLimits) {
		this.vehicleWeightLimits = vehicleWeightLimits;
	}
	public Long getVehicleWidth() {
		return vehicleWidth;
	}
	public void setVehicleWidth(Long vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
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
	public Long getVehicleHeight() {
		return vehicleHeight;
	}
	public void setVehicleHeight(Long vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}
	public Long getVehicleLong() {
		return vehicleLong;
	}
	public void setVehicleLong(Long vehicleLong) {
		this.vehicleLong = vehicleLong;
	}
	public String getVehicleEngineNo() {
		return vehicleEngineNo;
	}
	public void setVehicleEngineNo(String vehicleEngineNo) {
		this.vehicleEngineNo = vehicleEngineNo;
	}
	public String getUsingNature() {
		return usingNature;
	}
	public void setUsingNature(String usingNature) {
		this.usingNature = usingNature;
	}
	public String getIdentificationCode() {
		return identificationCode;
	}
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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