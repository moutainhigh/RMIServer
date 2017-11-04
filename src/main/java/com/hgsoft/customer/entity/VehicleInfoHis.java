
package com.hgsoft.customer.entity;

import java.util.Date;

@SuppressWarnings("serial")
public class VehicleInfoHis  implements java.io.Serializable {

	private Long id;
	
	private Long customerID;
  
	private Date genTime;
    
	private String genReason;
    
	private String vehiclePlate;
    
	private String vehicleColor;
    
	private String usingNature;
    
	private String identificationCode;
    
	private String vehicleType;
    
	private Long vehicleWheels;
    
	private Long vehicleAxles;
    
	private Long vehicleWheelBases;
    
	private Long vehicleWeightLimits;
    
	private String vehicleSpecificInformation;
    
	private String vehicleEngineNo;
    
	private Long vehicleWidth;
    
	private Long vehicleLong;
    
	private Long vehicleHeight;
    
	private String owner;
    
	private String model;
    
	private Long operId;
    
	private Long placeId;
    
	private Date createTime;
    
	private Long hisSeqId;

	private String isWriteOBU;
	
	private String isWriteCard;
	
	private String vehicleUserType;
	private Long vehicleHeadH;
	private String NSCVehicleType;
	
	private String operNo;
	
	private String operName;
	
	private String placeNo;
	
	private String placeName;
	
	public VehicleInfoHis(Long id, Long customerID, Date genTime, String genReason, String vehiclePlate,
			String vehicleColor, String usingNature, String identificationCode, String vehicleType, Long vehicleWheels,
			Long vehicleAxles, Long vehicleWheelBases, Long vehicleWeightLimits, String vehicleSpecificInformation,
			String vehicleEngineNo, Long vehicleWidth, Long vehicleLong, Long vehicleHeight, String owner, String model,
			Long operId, Long placeId, Date createTime, Long hisSeqId, String isWriteOBU, String isWriteCard,
			String vehicleUserType, Long vehicleHeadH, String nSCVehicleType, String operNo, String operName,
			String placeNo, String placeName) {
		super();
		this.id = id;
		this.customerID = customerID;
		this.genTime = genTime;
		this.genReason = genReason;
		this.vehiclePlate = vehiclePlate;
		this.vehicleColor = vehicleColor;
		this.usingNature = usingNature;
		this.identificationCode = identificationCode;
		this.vehicleType = vehicleType;
		this.vehicleWheels = vehicleWheels;
		this.vehicleAxles = vehicleAxles;
		this.vehicleWheelBases = vehicleWheelBases;
		this.vehicleWeightLimits = vehicleWeightLimits;
		this.vehicleSpecificInformation = vehicleSpecificInformation;
		this.vehicleEngineNo = vehicleEngineNo;
		this.vehicleWidth = vehicleWidth;
		this.vehicleLong = vehicleLong;
		this.vehicleHeight = vehicleHeight;
		this.owner = owner;
		this.model = model;
		this.operId = operId;
		this.placeId = placeId;
		this.createTime = createTime;
		this.hisSeqId = hisSeqId;
		this.isWriteOBU = isWriteOBU;
		this.isWriteCard = isWriteCard;
		this.vehicleUserType = vehicleUserType;
		this.vehicleHeadH = vehicleHeadH;
		NSCVehicleType = nSCVehicleType;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
	}

	public VehicleInfoHis() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getGenReason() {
		return genReason;
	}

	public void setGenReason(String genReason) {
		this.genReason = genReason;
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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getVehicleWheels() {
		return vehicleWheels;
	}

	public void setVehicleWheels(Long vehicleWheels) {
		this.vehicleWheels = vehicleWheels;
	}

	public Long getVehicleAxles() {
		return vehicleAxles;
	}

	public void setVehicleAxles(Long vehicleAxles) {
		this.vehicleAxles = vehicleAxles;
	}

	public Long getVehicleWheelBases() {
		return vehicleWheelBases;
	}

	public void setVehicleWheelBases(Long vehicleWheelBases) {
		this.vehicleWheelBases = vehicleWheelBases;
	}

	public Long getVehicleWeightLimits() {
		return vehicleWeightLimits;
	}

	public void setVehicleWeightLimits(Long vehicleWeightLimits) {
		this.vehicleWeightLimits = vehicleWeightLimits;
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

	public Long getVehicleWidth() {
		return vehicleWidth;
	}

	public void setVehicleWidth(Long vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}

	public Long getVehicleLong() {
		return vehicleLong;
	}

	public void setVehicleLong(Long vehicleLong) {
		this.vehicleLong = vehicleLong;
	}

	public Long getVehicleHeight() {
		return vehicleHeight;
	}

	public void setVehicleHeight(Long vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

	public String getIsWriteOBU() {
		return isWriteOBU;
	}

	public void setIsWriteOBU(String isWriteOBU) {
		this.isWriteOBU = isWriteOBU;
	}

	public String getIsWriteCard() {
		return isWriteCard;
	}

	public void setIsWriteCard(String isWriteCard) {
		this.isWriteCard = isWriteCard;
	}

	public String getVehicleUserType() {
		return vehicleUserType;
	}

	public void setVehicleUserType(String vehicleUserType) {
		this.vehicleUserType = vehicleUserType;
	}

	public Long getVehicleHeadH() {
		return vehicleHeadH;
	}

	public void setVehicleHeadH(Long vehicleHeadH) {
		this.vehicleHeadH = vehicleHeadH;
	}

	public String getNSCVehicleType() {
		return NSCVehicleType;
	}

	public void setNSCVehicleType(String nSCVehicleType) {
		NSCVehicleType = nSCVehicleType;
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
	
}