package com.hgsoft.acms.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签发行回执
 * Created by wiki on 2017/8/22.
 */
public class TagIssuceReceiptACMS extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;
    /**
     * 销售方式
     */
    private String tagSaleType;
    /**
     * 发行类型
     */
    private String tagIssueType;
    /**
     * 工本费
     */
    private String tagChargeCost;
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
    /**
     * 安装员
     */
    private String installMan;
    /**
     * 特别说明1
     */
    private String explanation1;
    /**
     * 特别说明2
     */
    private String explanation2;

    public TagIssuceReceiptACMS() {
    }

    /**
     *
     * @param tagNo 标 签 号
     * @param tagSaleType 销售方式
     * @param tagIssueType 发行类型
     * @param tagChargeCost 工本费
     * @param vehiclePlate 车牌号码
     * @param vehiclePlateColor 车牌颜色
     * @param vehicleWeightLimits 吨位（KG）/座位数（个）
     * @param vehicleEngineNo 发动机号
     * @param vehicleModel 厂牌型号
     * @param vehicleType 车辆类型
     * @param vehicleUserType 车辆用户类型
     * @param vehicleUsingNature 使用性质
     * @param vehicleOwner 车主名称
     * @param vehicleLong 车长（mm）
     * @param vehicleWidth 车宽（mm）
     * @param vehicleHeight 车高（mm）
     * @param vehicleNSCvehicletype 国际收费车型
     * @param vehicleIdentificationCode 车辆识别码
     * @param vehicleAxles 轴数
     * @param vehicleWheels 轮数
     * @param installMan 安装员
     * @param explanation1 特别说明1
     * @param explanation2 特别说明2
     */
    public TagIssuceReceiptACMS(String tagNo, String tagSaleType, String tagIssueType, String tagChargeCost, String vehiclePlate, String vehiclePlateColor, String vehicleWeightLimits, String vehicleEngineNo, String vehicleModel, String vehicleType, String vehicleUserType, String vehicleUsingNature, String vehicleOwner, String vehicleLong, String vehicleWidth, String vehicleHeight, String vehicleNSCvehicletype, String vehicleIdentificationCode, String vehicleAxles, String vehicleWheels, String installMan, String explanation1, String explanation2) {
        this.tagNo = tagNo;
        this.tagSaleType = tagSaleType;
        this.tagIssueType = tagIssueType;
        this.tagChargeCost = tagChargeCost;
        this.vehiclePlate = vehiclePlate;
        this.vehiclePlateColor = vehiclePlateColor;
        this.vehicleWeightLimits = vehicleWeightLimits;
        this.vehicleEngineNo = vehicleEngineNo;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleUserType = vehicleUserType;
        this.vehicleUsingNature = vehicleUsingNature;
        this.vehicleOwner = vehicleOwner;
        this.vehicleLong = vehicleLong;
        this.vehicleWidth = vehicleWidth;
        this.vehicleHeight = vehicleHeight;
        this.vehicleNSCvehicletype = vehicleNSCvehicletype;
        this.vehicleIdentificationCode = vehicleIdentificationCode;
        this.vehicleAxles = vehicleAxles;
        this.vehicleWheels = vehicleWheels;
        this.installMan = installMan;
        this.explanation1 = explanation1;
        this.explanation2 = explanation2;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getTagSaleType() {
        return tagSaleType;
    }

    public void setTagSaleType(String tagSaleType) {
        this.tagSaleType = tagSaleType;
    }

    public String getTagIssueType() {
        return tagIssueType;
    }

    public void setTagIssueType(String tagIssueType) {
        this.tagIssueType = tagIssueType;
    }

    public String getTagChargeCost() {
        return tagChargeCost;
    }

    public void setTagChargeCost(String tagChargeCost) {
        this.tagChargeCost = tagChargeCost;
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

    public String getExplanation1() {
        return explanation1;
    }

    public void setExplanation1(String explanation1) {
        this.explanation1 = explanation1;
    }

    public String getExplanation2() {
        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(super.toString());
        sb.append(",\"tagNo\":\"").append(tagNo).append('\"');
        sb.append(",\"tagSaleType\":\"").append(tagSaleType).append('\"');
        sb.append(",\"tagIssueType\":\"").append(tagIssueType).append('\"');
        sb.append(",\"tagChargeCost\":\"").append(tagChargeCost).append('\"');
        sb.append(",\"vehiclePlate\":\"").append(vehiclePlate).append('\"');
        sb.append(",\"vehiclePlateColor\":\"").append(vehiclePlateColor).append('\"');
        sb.append(",\"vehicleWeightLimits\":\"").append(vehicleWeightLimits).append('\"');
        sb.append(",\"vehicleEngineNo\":\"").append(vehicleEngineNo).append('\"');
        sb.append(",\"vehicleModel\":\"").append(vehicleModel).append('\"');
        sb.append(",\"vehicleType\":\"").append(vehicleType).append('\"');
        sb.append(",\"vehicleUserType\":\"").append(vehicleUserType).append('\"');
        sb.append(",\"vehicleUsingNature\":\"").append(vehicleUsingNature).append('\"');
        sb.append(",\"vehicleOwner\":\"").append(vehicleOwner).append('\"');
        sb.append(",\"vehicleLong\":\"").append(vehicleLong).append('\"');
        sb.append(",\"vehicleWidth\":\"").append(vehicleWidth).append('\"');
        sb.append(",\"vehicleHeight\":\"").append(vehicleHeight).append('\"');
        sb.append(",\"vehicleNSCvehicletype\":\"").append(vehicleNSCvehicletype).append('\"');
        sb.append(",\"vehicleIdentificationCode\":\"").append(vehicleIdentificationCode).append('\"');
        sb.append(",\"vehicleAxles\":\"").append(vehicleAxles).append('\"');
        sb.append(",\"vehicleWheels\":\"").append(vehicleWheels).append('\"');
        sb.append(",\"installMan\":\"").append(installMan).append('\"');
        sb.append(",\"explanation1\":\"").append(explanation1).append('\"');
        sb.append(",\"explanation2\":\"").append(explanation2).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
