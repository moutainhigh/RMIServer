package com.hgsoft.other.vo.receiptContent.acms;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签解除挂起（迁移）回执
 * Created by wiki on 2017/8/22.
 */
public class TagUnStopACMSReceipt extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;

    /**
     * 客户名称
     */
 	private String name;
// 	/**
// 	 * 证件类型
// 	 */
// 	private String idType;
// 	/**
// 	 * 证件号码
// 	 */
// 	private String idCode;
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
///////////////////////////旧车////////////////////////////
/**
* 车牌号码
*/
private String vehiclePlate_old;
/**
* 车牌颜色
*/
private String vehicleColor_old;
/**
* 吨位（KG）/座位数（个）
*/
private String weightsOrSeats_old;
/**
* 发动机号
*/
private String vehicleEngineNo_old;
/**
* 厂牌型号
*/
private String vehicleModel_old;
/**
* 车辆类型
*/
private String vehicleType_old;
/**
* 车辆用户类型
*/
private String vehicleUserType_old;
/**
* 使用性质
*/
private String vehicleUsingNature_old;
/**
* 车主名称
*/
private String vehicleOwner_old;
/**
* 车长（mm）
*/
private String vehicleLong_old;
/**
* 车宽（mm）
*/
private String vehicleWidth_old;
/**
* 车高（mm）
*/
private String vehicleHeight_old;
/**
* 国际收费车型
*/
private String vehicleNSCvehicletype_old;
/**
* 车辆识别码
*/
private String vehicleIdentificationCode_old;
/**
* 轴数
*/
private String vehicleAxles_old;
/**
* 轮数
*/
private String vehicleWheels_old;




/////////////////////新车///////////////////////
/**
 * 车牌号码
 */
private String vehiclePlate_new;
/**
 * 车牌颜色
 */
private String vehicleColor_new;
/**
 * 吨位（KG）/座位数（个）
 */
private String weightsOrSeats_new;
/**
 * 发动机号
 */
private String vehicleEngineNo_new;
/**
 * 厂牌型号
 */
private String vehicleModel_new;
/**
 * 车辆类型
 */
private String vehicleType_new;
/**
 * 车辆用户类型
 */
private String vehicleUserType_new;
/**
 * 使用性质
 */
private String vehicleUsingNature_new;
/**
 * 车主名称
 */
private String vehicleOwner_new;
/**
 * 车长（mm）
 */
private String vehicleLong_new;
/**
 * 车宽（mm）
 */
private String vehicleWidth_new;
/**
 * 车高（mm）
 */
private String vehicleHeight_new;
/**
 * 国际收费车型
 */
private String vehicleNSCvehicletype_new;
/**
 * 车辆识别码
 */
private String vehicleIdentificationCode_new;
/**
 * 轴数
 */
private String vehicleAxles_new;
/**
 * 轮数
 */
private String vehicleWheels_new;


private String installMan;
///////////////////////////get and set/////////////////
public String getVehiclePlate_new() {
	return vehiclePlate_new;
}
public void setVehiclePlate_new(String vehiclePlate_new) {
	this.vehiclePlate_new = vehiclePlate_new;
}
public String getVehicleColor_new() {
	return vehicleColor_new;
}
public void setVehicleColor_new(String vehicleColor_new) {
	this.vehicleColor_new = vehicleColor_new;
}
public String getWeightsOrSeats_new() {
	return weightsOrSeats_new;
}
public void setWeightsOrSeats_new(String weightsOrSeats_new) {
	this.weightsOrSeats_new = weightsOrSeats_new;
}
public String getVehicleEngineNo_new() {
	return vehicleEngineNo_new;
}
public void setVehicleEngineNo_new(String vehicleEngineNo_new) {
	this.vehicleEngineNo_new = vehicleEngineNo_new;
}
public String getVehicleModel_new() {
	return vehicleModel_new;
}
public void setVehicleModel_new(String vehicleModel_new) {
	this.vehicleModel_new = vehicleModel_new;
}
public String getVehicleType_new() {
	return vehicleType_new;
}
public void setVehicleType_new(String vehicleType_new) {
	this.vehicleType_new = vehicleType_new;
}
public String getVehicleUserType_new() {
	return vehicleUserType_new;
}
public void setVehicleUserType_new(String vehicleUserType_new) {
	this.vehicleUserType_new = vehicleUserType_new;
}
public String getVehicleUsingNature_new() {
	return vehicleUsingNature_new;
}
public void setVehicleUsingNature_new(String vehicleUsingNature_new) {
	this.vehicleUsingNature_new = vehicleUsingNature_new;
}
public String getVehicleOwner_new() {
	return vehicleOwner_new;
}
public void setVehicleOwner_new(String vehicleOwner_new) {
	this.vehicleOwner_new = vehicleOwner_new;
}
public String getVehicleLong_new() {
	return vehicleLong_new;
}
public void setVehicleLong_new(String vehicleLong_new) {
	this.vehicleLong_new = vehicleLong_new;
}
public String getVehicleWidth_new() {
	return vehicleWidth_new;
}
public void setVehicleWidth_new(String vehicleWidth_new) {
	this.vehicleWidth_new = vehicleWidth_new;
}
public String getVehicleHeight_new() {
	return vehicleHeight_new;
}
public void setVehicleHeight_new(String vehicleHeight_new) {
	this.vehicleHeight_new = vehicleHeight_new;
}
public String getVehicleNSCvehicletype_new() {
	return vehicleNSCvehicletype_new;
}
public void setVehicleNSCvehicletype_new(String vehicleNSCvehicletype_new) {
	this.vehicleNSCvehicletype_new = vehicleNSCvehicletype_new;
}
public String getVehicleIdentificationCode_new() {
	return vehicleIdentificationCode_new;
}
public void setVehicleIdentificationCode_new(String vehicleIdentificationCode_new) {
	this.vehicleIdentificationCode_new = vehicleIdentificationCode_new;
}
public String getVehicleAxles_new() {
	return vehicleAxles_new;
}
public void setVehicleAxles_new(String vehicleAxles_new) {
	this.vehicleAxles_new = vehicleAxles_new;
}
public String getVehicleWheels_new() {
	return vehicleWheels_new;
}
public void setVehicleWheels_new(String vehicleWheels_new) {
	this.vehicleWheels_new = vehicleWheels_new;
}
public String getTagNo() {
	return tagNo;
}
public void setTagNo(String tagNo) {
	this.tagNo = tagNo;
}
public String getVehiclePlate_old() {
	return vehiclePlate_old;
}
public void setVehiclePlate_old(String vehiclePlate_old) {
	this.vehiclePlate_old = vehiclePlate_old;
}
public String getVehicleColor_old() {
	return vehicleColor_old;
}
public void setVehicleColor_old(String vehicleColor_old) {
	this.vehicleColor_old = vehicleColor_old;
}
public String getWeightsOrSeats_old() {
	return weightsOrSeats_old;
}
public void setWeightsOrSeats_old(String weightsOrSeats_old) {
	this.weightsOrSeats_old = weightsOrSeats_old;
}
public String getVehicleEngineNo_old() {
	return vehicleEngineNo_old;
}
public void setVehicleEngineNo_old(String vehicleEngineNo_old) {
	this.vehicleEngineNo_old = vehicleEngineNo_old;
}
public String getVehicleModel_old() {
	return vehicleModel_old;
}
public void setVehicleModel_old(String vehicleModel_old) {
	this.vehicleModel_old = vehicleModel_old;
}
public String getVehicleType_old() {
	return vehicleType_old;
}
public void setVehicleType_old(String vehicleType_old) {
	this.vehicleType_old = vehicleType_old;
}
public String getVehicleUserType_old() {
	return vehicleUserType_old;
}
public void setVehicleUserType_old(String vehicleUserType_old) {
	this.vehicleUserType_old = vehicleUserType_old;
}
public String getVehicleUsingNature_old() {
	return vehicleUsingNature_old;
}
public void setVehicleUsingNature_old(String vehicleUsingNature_old) {
	this.vehicleUsingNature_old = vehicleUsingNature_old;
}
public String getVehicleOwner_old() {
	return vehicleOwner_old;
}
public void setVehicleOwner_old(String vehicleOwner_old) {
	this.vehicleOwner_old = vehicleOwner_old;
}
public String getVehicleLong_old() {
	return vehicleLong_old;
}
public void setVehicleLong_old(String vehicleLong_old) {
	this.vehicleLong_old = vehicleLong_old;
}
public String getVehicleWidth_old() {
	return vehicleWidth_old;
}
public void setVehicleWidth_old(String vehicleWidth_old) {
	this.vehicleWidth_old = vehicleWidth_old;
}
public String getVehicleHeight_old() {
	return vehicleHeight_old;
}
public void setVehicleHeight_old(String vehicleHeight_old) {
	this.vehicleHeight_old = vehicleHeight_old;
}
public String getVehicleNSCvehicletype_old() {
	return vehicleNSCvehicletype_old;
}
public void setVehicleNSCvehicletype_old(String vehicleNSCvehicletype_old) {
	this.vehicleNSCvehicletype_old = vehicleNSCvehicletype_old;
}
public String getVehicleIdentificationCode_old() {
	return vehicleIdentificationCode_old;
}
public void setVehicleIdentificationCode_old(String vehicleIdentificationCode_old) {
	this.vehicleIdentificationCode_old = vehicleIdentificationCode_old;
}
public String getVehicleAxles_old() {
	return vehicleAxles_old;
}
public void setVehicleAxles_old(String vehicleAxles_old) {
	this.vehicleAxles_old = vehicleAxles_old;
}
public String getVehicleWheels_old() {
	return vehicleWheels_old;
}
public void setVehicleWheels_old(String vehicleWheels_old) {
	this.vehicleWheels_old = vehicleWheels_old;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
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
public String getInstallMan() {
	return installMan;
}
public void setInstallMan(String installMan) {
	this.installMan = installMan;
}



}
