package com.hgsoft.other.vo.receiptContent.acms;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签更换回执
 * Created by wiki on 2017/8/22.
 */
public class TagReplaceACMSReceipt extends BaseReceiptContent {

	///////////////////////////// 客户基本信息/////////////////////////////
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 联系电话
	 */
	private String linkTel;
	/**
	 * 手机号
	 */
	private String mobileNum;
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 联系地址
	 */
	private String linkAddr;

	/**
	 * 原 标 签 号
	 */
	private String tagNo_old;
	/**
	 * 新 标 签 号
	 */
	private String tagNo_new;

	/**
     * 工本费
     */
    private String tagChargeCost;
	/////////////////////////// 车辆基本信息/////////////////////////////
	/**
	 * 车牌号码
	 */
	private String vehiclePlate;
	/**
	 * 车牌颜色
	 */
	private String vehicleColor;
	/**
	 * 吨位（KG）/座位数（个）
	 */
	private String weightsOrSeats;
	/**
	 * 发动机号
	 */
	private String vehicleEngineNo;
	/**
	 * 厂牌型号
	 */
	private String vehicleModel;
	/**
	 * 车辆类型
	 */
	private String vehicleType;
	/**
	 * 车辆用户类型
	 */
	private String vehicleUserType;
	/**
	 * 使用性质
	 */
	private String vehicleUsingNature;
	/**
	 * 车主名称
	 */
	private String vehicleOwner;
	/**
	 * 车长（mm）
	 */
	private String vehicleLong;
	/**
	 * 车宽（mm）
	 */
	private String vehicleWidth;
	/**
	 * 车高（mm）
	 */
	private String vehicleHeight;
	/**
	 * 国际收费车型
	 */
	private String vehicleNSCvehicletype;
	/**
	 * 车辆识别码
	 */
	private String vehicleIdentificationCode;
	/**
	 * 轴数
	 */
	private String vehicleAxles;
	/**
	 * 轮数
	 */
	private String vehicleWheels;

	////////////////////// 其他参数//////////////////////

	/**
	 * 安装员
	 */
	private String installMan;

	///////////////////// get&set方法//////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagChargeCost() {
		return tagChargeCost;
	}

	public void setTagChargeCost(String tagChargeCost) {
		this.tagChargeCost = tagChargeCost;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getTagNo_old() {
		return tagNo_old;
	}

	public void setTagNo_old(String tagNo_old) {
		this.tagNo_old = tagNo_old;
	}

	public String getTagNo_new() {
		return tagNo_new;
	}

	public void setTagNo_new(String tagNo_new) {
		this.tagNo_new = tagNo_new;
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

	public String getWeightsOrSeats() {
		return weightsOrSeats;
	}

	public void setWeightsOrSeats(String weightsOrSeats) {
		this.weightsOrSeats = weightsOrSeats;
	}

	public String getVehicleEngineNo() {
		return vehicleEngineNo;
	}

	public void setVehicleEngineNo(String vehicleEngineNo) {
		this.vehicleEngineNo = vehicleEngineNo;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleUserType() {
		return vehicleUserType;
	}

	public void setVehicleUserType(String vehicleUserType) {
		this.vehicleUserType = vehicleUserType;
	}

	public String getVehicleUsingNature() {
		return vehicleUsingNature;
	}

	public void setVehicleUsingNature(String vehicleUsingNature) {
		this.vehicleUsingNature = vehicleUsingNature;
	}

	public String getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}

	public String getVehicleLong() {
		return vehicleLong;
	}

	public void setVehicleLong(String vehicleLong) {
		this.vehicleLong = vehicleLong;
	}

	public String getVehicleWidth() {
		return vehicleWidth;
	}

	public void setVehicleWidth(String vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}

	public String getVehicleHeight() {
		return vehicleHeight;
	}

	public void setVehicleHeight(String vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}

	public String getVehicleNSCvehicletype() {
		return vehicleNSCvehicletype;
	}

	public void setVehicleNSCvehicletype(String vehicleNSCvehicletype) {
		this.vehicleNSCvehicletype = vehicleNSCvehicletype;
	}

	public String getVehicleIdentificationCode() {
		return vehicleIdentificationCode;
	}

	public void setVehicleIdentificationCode(String vehicleIdentificationCode) {
		this.vehicleIdentificationCode = vehicleIdentificationCode;
	}

	public String getVehicleAxles() {
		return vehicleAxles;
	}

	public void setVehicleAxles(String vehicleAxles) {
		this.vehicleAxles = vehicleAxles;
	}

	public String getVehicleWheels() {
		return vehicleWheels;
	}

	public void setVehicleWheels(String vehicleWheels) {
		this.vehicleWheels = vehicleWheels;
	}

	public String getInstallMan() {
		return installMan;
	}

	public void setInstallMan(String installMan) {
		this.installMan = installMan;
	}

}
