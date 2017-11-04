
package com.hgsoft.customer.entity;

import java.util.Date;

@SuppressWarnings("serial")
public class VehicleInfo implements java.io.Serializable {

	private Long id;

	private Long customerID;

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

	//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
	private String ownerName;
	private String ownerIdType;
	private String ownerIdNum;
	private String ownerTel;
	private String address;
	private String vehicleDriverType;
	private String vehicleModel;
	private Date registerDate;
	private Date issueDate;
	private String fileNum;
	private Integer approvedCount;
	private Integer totalMass;
	private Integer maintenanceMass;
	private Integer permittedWeight;
	private Integer permittedTowWeight;
	private String testRecord;
	private Integer axleDistance;
	private String axisType;
	private String userNo;
	//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

	@Override
	public String toString() {
		return "VehicleInfo [id=" + id + ", customerID=" + customerID + ", vehiclePlate=" + vehiclePlate
				+ ", vehicleColor=" + vehicleColor + ", usingNature=" + usingNature + ", identificationCode="
				+ identificationCode + ", vehicleType=" + vehicleType + ", vehicleWheels=" + vehicleWheels
				+ ", vehicleAxles=" + vehicleAxles + ", vehicleWheelBases=" + vehicleWheelBases
				+ ", vehicleWeightLimits=" + vehicleWeightLimits + ", vehicleSpecificInformation="
				+ vehicleSpecificInformation + ", vehicleEngineNo=" + vehicleEngineNo + ", vehicleWidth=" + vehicleWidth
				+ ", vehicleLong=" + vehicleLong + ", vehicleHeight=" + vehicleHeight + ", owner=" + owner + ", model="
				+ model + ", operId=" + operId + ", placeId=" + placeId + ", createTime=" + createTime + ", hisSeqId="
				+ hisSeqId + ", isWriteOBU=" + isWriteOBU + ", isWriteCard=" + isWriteCard + ", vehicleUserType="
				+ vehicleUserType + ", vehicleHeadH=" + vehicleHeadH + ", NSCVehicleType=" + NSCVehicleType
				+ ", operNo=" + operNo + ", operName=" + operName + ", placeNo=" + placeNo + ", placeName=" + placeName
				+ "]";
	}

	public VehicleInfo(Long id, Long customerID, String vehiclePlate, String vehicleColor, String usingNature,
	                   String identificationCode, String vehicleType, Long vehicleWheels, Long vehicleAxles,
	                   Long vehicleWheelBases, Long vehicleWeightLimits, String vehicleSpecificInformation, String vehicleEngineNo,
	                   Long vehicleWidth, Long vehicleLong, Long vehicleHeight, String owner, String model, Long operId,
	                   Long placeId, Date createTime, Long hisSeqId, String isWriteOBU, String isWriteCard, String vehicleUserType,
	                   Long vehicleHeadH, String nSCVehicleType, String operNo, String operName, String placeNo,
	                   String placeName) {
		super();
		this.id = id;
		this.customerID = customerID;
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

	public VehicleInfo(Long id, Long customerID, String vehiclePlate, String vehicleColor, String
			usingNature, String identificationCode, String vehicleType, Long vehicleWheels, Long
			                   vehicleAxles, Long vehicleWheelBases, Long vehicleWeightLimits, String
			                   vehicleSpecificInformation, String vehicleEngineNo, Long vehicleWidth, Long
			                   vehicleLong, Long vehicleHeight, String owner, String model, Long operId, Long
			                   placeId, Date createTime, Long hisSeqId, String isWriteOBU, String isWriteCard,
	                   String vehicleUserType, Long vehicleHeadH, String NSCVehicleType, String
			                   operNo, String operName, String placeNo, String placeName, String
			                   ownerName, String ownerIdType, String ownerIdNum, String ownerTel,
	                   String address, String vehicleDriverType, String vehicleModel, Date
			                   registerDate, Date issueDate, String fileNum, Integer
			                   approvedCount, Integer totalMass, Integer maintenanceMass, Integer
			                   permittedWeight, Integer permittedTowWeight, String testRecord,
	                   Integer axleDistance, String axisType, String userNo) {
		this.id = id;
		this.customerID = customerID;
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
		this.NSCVehicleType = NSCVehicleType;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
		this.ownerName = ownerName;
		this.ownerIdType = ownerIdType;
		this.ownerIdNum = ownerIdNum;
		this.ownerTel = ownerTel;
		this.address = address;
		this.vehicleDriverType = vehicleDriverType;
		this.vehicleModel = vehicleModel;
		this.registerDate = registerDate;
		this.issueDate = issueDate;
		this.fileNum = fileNum;
		this.approvedCount = approvedCount;
		this.totalMass = totalMass;
		this.maintenanceMass = maintenanceMass;
		this.permittedWeight = permittedWeight;
		this.permittedTowWeight = permittedTowWeight;
		this.testRecord = testRecord;
		this.axleDistance = axleDistance;
		this.axisType = axisType;
		this.userNo = userNo;
	}


	public VehicleInfo() {
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

	public String getNSCVehicleType() {
		return NSCVehicleType;
	}

	public void setNSCVehicleType(String nSCVehicleType) {
		NSCVehicleType = nSCVehicleType;
	}

	public Long getVehicleHeadH() {
		return vehicleHeadH;
	}

	public void setVehicleHeadH(Long vehicleHeadH) {
		this.vehicleHeadH = vehicleHeadH;
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

	//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerIdType() {
		return ownerIdType;
	}

	public void setOwnerIdType(String ownerIdType) {
		this.ownerIdType = ownerIdType;
	}

	public String getOwnerIdNum() {
		return ownerIdNum;
	}

	public void setOwnerIdNum(String ownerIdNum) {
		this.ownerIdNum = ownerIdNum;
	}

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVehicleDriverType() {
		return vehicleDriverType;
	}

	public void setVehicleDriverType(String vehicleDriverType) {
		this.vehicleDriverType = vehicleDriverType;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public Integer getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(Integer approvedCount) {
		this.approvedCount = approvedCount;
	}

	public Integer getTotalMass() {
		return totalMass;
	}

	public void setTotalMass(Integer totalMass) {
		this.totalMass = totalMass;
	}

	public Integer getMaintenanceMass() {
		return maintenanceMass;
	}

	public void setMaintenanceMass(Integer maintenanceMass) {
		this.maintenanceMass = maintenanceMass;
	}

	public Integer getPermittedWeight() {
		return permittedWeight;
	}

	public void setPermittedWeight(Integer permittedWeight) {
		this.permittedWeight = permittedWeight;
	}

	public Integer getPermittedTowWeight() {
		return permittedTowWeight;
	}

	public void setPermittedTowWeight(Integer permittedTowWeight) {
		this.permittedTowWeight = permittedTowWeight;
	}

	public String getTestRecord() {
		return testRecord;
	}

	public void setTestRecord(String testRecord) {
		this.testRecord = testRecord;
	}

	public Integer getAxleDistance() {
		return axleDistance;
	}

	public void setAxleDistance(Integer axleDistance) {
		this.axleDistance = axleDistance;
	}

	public String getAxisType() {
		return axisType;
	}

	public void setAxisType(String axisType) {
		this.axisType = axisType;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026
}