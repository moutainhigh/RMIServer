package com.hgsoft.acms.other.vo.receiptContent.customer;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 车辆信息修改回执
 * Created by wiki on 2017/8/18.
 */
public class VechileUpdateReceiptACMS extends BaseReceiptContent {
    /**
     * 车主名称
     */
    private String vehicleOwner;
    /**
     * 车牌号码
     */
    private String vehiclePlate;
    /**
     * 车牌颜色
     */
    private String vehiclePlateColor;
    /**
     * 吨位（KG）/座位数（个）
     */
    private String vehicleWeightLimits;
    /**
     * 厂牌型号
     */
    private String vehicleModel;
    /**
     * 车辆类型
     */
    private String vehicleType;
    /**
     * 使用性质
     */
    private String vehicleUsingNature;
    /**
     * 发动机号
     */
    private String vehicleEngineNo;
    /**
     * 车辆识别码
     */
    private String vehicleIdentificationCode;
    /**
     * 国际收费车型
     */
    private String vehicleNSCvehicletype;
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
     * 轴数
     */
    private String vehicleAxles;
    /**
     * 轮数
     */
    private String vehicleWheels;
    /**
     * 旧车主名称
     */
    private String oldVehicleOwner;
    /**
     * 旧车牌号码
     */
    private String oldVehiclePlate;
    /**
     * 旧车牌颜色
     */
    private String oldVehiclePlateColor;
    /**
     * 旧吨位（KG）/座位数（个）
     */
    private String oldVehicleWeightLimits;
    /**
     * 旧厂牌型号
     */
    private String oldVehicleModel;
    /**
     * 旧车辆类型
     */
    private String oldVehicleType;
    /**
     * 旧使用性质
     */
    private String oldVehicleUsingNature;
    /**
     * 旧发动机号
     */
    private String oldVehicleEngineNo;
    /**
     * 旧车辆识别码
     */
    private String oldVehicleIdentificationCode;
    /**
     * 旧国际收费车型
     */
    private String oldVehicleNSCvehicletype;
    /**
     * 旧车长（mm）
     */
    private String oldVehicleLong;
    /**
     * 旧车宽（mm）
     */
    private String oldVehicleWidth;
    /**
     * 旧车高（mm）
     */
    private String oldVehicleHeight;
    /**
     * 旧轴数
     */
    private String oldVehicleAxles;
    /**
     * 旧轮数
     */
    private String oldVehicleWheels;
    /**
     * 联系手机
     */
    private String customerMobile;
    /**
     * 联系电话
     */
    private String customerTel;

    public VechileUpdateReceiptACMS(){

    }

    /**
     *
     * @param vehicleOwner 车主名称
     * @param vehiclePlate 车牌号码
     * @param vehiclePlateColor 车牌颜色
     * @param vehicleWeightLimits 吨位（KG）/座位数（个）
     * @param vehicleModel 厂牌型号
     * @param vehicleType 车辆类型
     * @param vehicleUsingNature 使用性质
     * @param vehicleEngineNo 发动机号
     * @param vehicleIdentificationCode 车辆识别码
     * @param vehicleNSCvehicletype 国际收费车型
     * @param vehicleLong 车长（mm）
     * @param vehicleWidth 车宽（mm）
     * @param vehicleHeight 车高（mm）
     * @param vehicleAxles 轴数
     * @param vehicleWheels 轮数
     *
     * @param oldVehicleOwner 旧车主名称
     * @param oldVehiclePlate 旧车牌号码
     * @param oldVehiclePlateColor 旧车牌颜色
     * @param oldVehicleWeightLimits 旧吨位（KG）/座位数（个）
     * @param oldVehicleModel 旧厂牌型号
     * @param oldVehicleType 旧车辆类型
     * @param oldVehicleUsingNature 旧使用性质
     * @param oldVehicleEngineNo 旧发动机号
     * @param oldVehicleIdentificationCode 旧车辆识别码
     * @param oldVehicleNSCvehicletype 旧国际收费车型
     * @param oldVehicleLong 旧车长（mm）
     * @param oldVehicleWidth 旧车宽（mm）
     * @param oldVehicleHeight 旧车高（mm）
     * @param oldVehicleAxles 旧轴数
     * @param oldVehicleWheels 旧轮数
     *
     * @param customerMobile 联系手机
     * @param customerTel 联系电话
     */
    public VechileUpdateReceiptACMS(String vehicleOwner, String vehiclePlate, String vehiclePlateColor, String vehicleWeightLimits, String vehicleModel, String vehicleType, String vehicleUsingNature, String vehicleEngineNo, String vehicleIdentificationCode, String vehicleNSCvehicletype, String vehicleLong, String vehicleWidth, String vehicleHeight, String vehicleAxles, String vehicleWheels, String oldVehicleOwner, String oldVehiclePlate, String oldVehiclePlateColor, String oldVehicleWeightLimits, String oldVehicleModel, String oldVehicleType, String oldVehicleUsingNature, String oldVehicleEngineNo, String oldVehicleIdentificationCode, String oldVehicleNSCvehicletype, String oldVehicleLong, String oldVehicleWidth, String oldVehicleHeight, String oldVehicleAxles, String oldVehicleWheels, String customerMobile, String customerTel) {
        this.vehicleOwner = vehicleOwner;
        this.vehiclePlate = vehiclePlate;
        this.vehiclePlateColor = vehiclePlateColor;
        this.vehicleWeightLimits = vehicleWeightLimits;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleUsingNature = vehicleUsingNature;
        this.vehicleEngineNo = vehicleEngineNo;
        this.vehicleIdentificationCode = vehicleIdentificationCode;
        this.vehicleNSCvehicletype = vehicleNSCvehicletype;
        this.vehicleLong = vehicleLong;
        this.vehicleWidth = vehicleWidth;
        this.vehicleHeight = vehicleHeight;
        this.vehicleAxles = vehicleAxles;
        this.vehicleWheels = vehicleWheels;
        this.oldVehicleOwner = oldVehicleOwner;
        this.oldVehiclePlate = oldVehiclePlate;
        this.oldVehiclePlateColor = oldVehiclePlateColor;
        this.oldVehicleWeightLimits = oldVehicleWeightLimits;
        this.oldVehicleModel = oldVehicleModel;
        this.oldVehicleType = oldVehicleType;
        this.oldVehicleUsingNature = oldVehicleUsingNature;
        this.oldVehicleEngineNo = oldVehicleEngineNo;
        this.oldVehicleIdentificationCode = oldVehicleIdentificationCode;
        this.oldVehicleNSCvehicletype = oldVehicleNSCvehicletype;
        this.oldVehicleLong = oldVehicleLong;
        this.oldVehicleWidth = oldVehicleWidth;
        this.oldVehicleHeight = oldVehicleHeight;
        this.oldVehicleAxles = oldVehicleAxles;
        this.oldVehicleWheels = oldVehicleWheels;
        this.customerMobile = customerMobile;
        this.customerTel = customerTel;
    }

    public String getVehicleOwner() {
        return vehicleOwner;
    }

    public void setVehicleOwner(String vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehiclePlateColor() {
        return vehiclePlateColor;
    }

    public void setVehiclePlateColor(String vehiclePlateColor) {
        this.vehiclePlateColor = vehiclePlateColor;
    }

    public String getVehicleWeightLimits() {
        return vehicleWeightLimits;
    }

    public void setVehicleWeightLimits(String vehicleWeightLimits) {
        this.vehicleWeightLimits = vehicleWeightLimits;
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

    public String getVehicleUsingNature() {
        return vehicleUsingNature;
    }

    public void setVehicleUsingNature(String vehicleUsingNature) {
        this.vehicleUsingNature = vehicleUsingNature;
    }

    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    public String getVehicleIdentificationCode() {
        return vehicleIdentificationCode;
    }

    public void setVehicleIdentificationCode(String vehicleIdentificationCode) {
        this.vehicleIdentificationCode = vehicleIdentificationCode;
    }

    public String getVehicleNSCvehicletype() {
        return vehicleNSCvehicletype;
    }

    public void setVehicleNSCvehicletype(String vehicleNSCvehicletype) {
        this.vehicleNSCvehicletype = vehicleNSCvehicletype;
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

    public String getOldVehicleOwner() {
        return oldVehicleOwner;
    }

    public void setOldVehicleOwner(String oldVehicleOwner) {
        this.oldVehicleOwner = oldVehicleOwner;
    }

    public String getOldVehiclePlate() {
        return oldVehiclePlate;
    }

    public void setOldVehiclePlate(String oldVehiclePlate) {
        this.oldVehiclePlate = oldVehiclePlate;
    }

    public String getOldVehiclePlateColor() {
        return oldVehiclePlateColor;
    }

    public void setOldVehiclePlateColor(String oldVehiclePlateColor) {
        this.oldVehiclePlateColor = oldVehiclePlateColor;
    }

    public String getOldVehicleWeightLimits() {
        return oldVehicleWeightLimits;
    }

    public void setOldVehicleWeightLimits(String oldVehicleWeightLimits) {
        this.oldVehicleWeightLimits = oldVehicleWeightLimits;
    }

    public String getOldVehicleModel() {
        return oldVehicleModel;
    }

    public void setOldVehicleModel(String oldVehicleModel) {
        this.oldVehicleModel = oldVehicleModel;
    }

    public String getOldVehicleType() {
        return oldVehicleType;
    }

    public void setOldVehicleType(String oldVehicleType) {
        this.oldVehicleType = oldVehicleType;
    }

    public String getOldVehicleUsingNature() {
        return oldVehicleUsingNature;
    }

    public void setOldVehicleUsingNature(String oldVehicleUsingNature) {
        this.oldVehicleUsingNature = oldVehicleUsingNature;
    }

    public String getOldVehicleEngineNo() {
        return oldVehicleEngineNo;
    }

    public void setOldVehicleEngineNo(String oldVehicleEngineNo) {
        this.oldVehicleEngineNo = oldVehicleEngineNo;
    }

    public String getOldVehicleIdentificationCode() {
        return oldVehicleIdentificationCode;
    }

    public void setOldVehicleIdentificationCode(String oldVehicleIdentificationCode) {
        this.oldVehicleIdentificationCode = oldVehicleIdentificationCode;
    }

    public String getOldVehicleNSCvehicletype() {
        return oldVehicleNSCvehicletype;
    }

    public void setOldVehicleNSCvehicletype(String oldVehicleNSCvehicletype) {
        this.oldVehicleNSCvehicletype = oldVehicleNSCvehicletype;
    }

    public String getOldVehicleLong() {
        return oldVehicleLong;
    }

    public void setOldVehicleLong(String oldVehicleLong) {
        this.oldVehicleLong = oldVehicleLong;
    }

    public String getOldVehicleWidth() {
        return oldVehicleWidth;
    }

    public void setOldVehicleWidth(String oldVehicleWidth) {
        this.oldVehicleWidth = oldVehicleWidth;
    }

    public String getOldVehicleHeight() {
        return oldVehicleHeight;
    }

    public void setOldVehicleHeight(String oldVehicleHeight) {
        this.oldVehicleHeight = oldVehicleHeight;
    }

    public String getOldVehicleAxles() {
        return oldVehicleAxles;
    }

    public void setOldVehicleAxles(String oldVehicleAxles) {
        this.oldVehicleAxles = oldVehicleAxles;
    }

    public String getOldVehicleWheels() {
        return oldVehicleWheels;
    }

    public void setOldVehicleWheels(String oldVehicleWheels) {
        this.oldVehicleWheels = oldVehicleWheels;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(super.toString());
        sb.append(",\"vehicleOwner\":\"").append(vehicleOwner).append('\"');
        sb.append(",\"vehiclePlate\":\"").append(vehiclePlate).append('\"');
        sb.append(",\"vehiclePlateColor\":\"").append(vehiclePlateColor).append('\"');
        sb.append(",\"vehicleWeightLimits\":\"").append(vehicleWeightLimits).append('\"');
        sb.append(",\"vehicleModel\":\"").append(vehicleModel).append('\"');
        sb.append(",\"vehicleType\":\"").append(vehicleType).append('\"');
        sb.append(",\"vehicleUsingNature\":\"").append(vehicleUsingNature).append('\"');
        sb.append(",\"vehicleEngineNo\":\"").append(vehicleEngineNo).append('\"');
        sb.append(",\"vehicleIdentificationCode\":\"").append(vehicleIdentificationCode).append('\"');
        sb.append(",\"vehicleNSCvehicletype\":\"").append(vehicleNSCvehicletype).append('\"');
        sb.append(",\"vehicleLong\":\"").append(vehicleLong).append('\"');
        sb.append(",\"vehicleWidth\":\"").append(vehicleWidth).append('\"');
        sb.append(",\"vehicleHeight\":\"").append(vehicleHeight).append('\"');
        sb.append(",\"vehicleAxles\":\"").append(vehicleAxles).append('\"');
        sb.append(",\"vehicleWheels\":\"").append(vehicleWheels).append('\"');
        sb.append(",\"oldVehicleOwner\":\"").append(oldVehicleOwner).append('\"');
        sb.append(",\"oldVehiclePlate\":\"").append(oldVehiclePlate).append('\"');
        sb.append(",\"oldVehiclePlateColor\":\"").append(oldVehiclePlateColor).append('\"');
        sb.append(",\"oldVehicleWeightLimits\":\"").append(oldVehicleWeightLimits).append('\"');
        sb.append(",\"oldVehicleModel\":\"").append(oldVehicleModel).append('\"');
        sb.append(",\"oldVehicleType\":\"").append(oldVehicleType).append('\"');
        sb.append(",\"oldVehicleUsingNature\":\"").append(oldVehicleUsingNature).append('\"');
        sb.append(",\"oldVehicleEngineNo\":\"").append(oldVehicleEngineNo).append('\"');
        sb.append(",\"oldVehicleIdentificationCode\":\"").append(oldVehicleIdentificationCode).append('\"');
        sb.append(",\"oldVehicleNSCvehicletype\":\"").append(oldVehicleNSCvehicletype).append('\"');
        sb.append(",\"oldVehicleLong\":\"").append(oldVehicleLong).append('\"');
        sb.append(",\"oldVehicleWidth\":\"").append(oldVehicleWidth).append('\"');
        sb.append(",\"oldVehicleHeight\":\"").append(oldVehicleHeight).append('\"');
        sb.append(",\"oldVehicleAxles\":\"").append(oldVehicleAxles).append('\"');
        sb.append(",\"oldVehicleWheels\":\"").append(oldVehicleWheels).append('\"');
        sb.append(",\"customerMobile\":\"").append(customerMobile).append('\"');
        sb.append(",\"customerTel\":\"").append(customerTel).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
