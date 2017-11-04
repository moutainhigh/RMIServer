package com.hgsoft.acms.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签维修返回回执
 * Created by wiki on 2017/8/22.
 */
public class TagRepairReturnReceiptACMS extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;
    /**
     * 标签发行时间
     */
    private String backTagIssueTime;
    /**
     * 备件标签号
     */
    private String mainBackUpTagNo;
    /**
     * 备件启用时间
     */
    private String backTagStartTime;
    /**
     * 备件截止时间
     */
    private String backTagEndTime;
    /**
     * 领取地点
     */
    private String mainReceivePlace;
    /**
     * 联 系 人
     */
    private String mainContactMan;
    /**
     * 联系电话
     */
    private String mainContactPhone;
    /**
     * 发票抬头
     */
    private String mainInvoiceHead;
    /**
     * 邮寄地址
     */
    private String mainAddress;
    /**
     * 邮    编
     */
    private String mainPostcode;
    /**
     * 回收时间
     */
    private String mainRecoverTime;
    /**
     * 产品送修时间
     */
    private String mainSendRepairTime;
    /**
     * 维修后返还时间
     */
    private String mainRepairBackTime;
    /**
     * 通知客户时间
     */
    private String mainNoticeCustomerTime;
    /**
     * 返回客户时间
     */
    private String mainBackToCustomerTime;
    /**
     * 备    注
     */
    private String mainMemo;
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

    public TagRepairReturnReceiptACMS() {
    }

    /**
     *
     * @param tagNo 标 签 号
     * @param backTagIssueTime 标签发行时间
     * @param mainBackUpTagNo 备件标签号
     * @param backTagStartTime 备件启用时间
     * @param backTagEndTime 备件截止时间
     * @param mainReceivePlace 领取地点
     * @param mainContactMan 联 系 人
     * @param mainContactPhone 联系电话
     * @param mainInvoiceHead 发票抬头
     * @param mainAddress 邮寄地址
     * @param mainPostcode 邮    编
     * @param mainRecoverTime 回收时间
     * @param mainSendRepairTime 产品送修时间
     * @param mainRepairBackTime 维修后返还时间
     * @param mainNoticeCustomerTime 通知客户时间
     * @param mainBackToCustomerTime 返回客户时间
     * @param mainMemo 备    注
     *
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
     */
    public TagRepairReturnReceiptACMS(String tagNo, String backTagIssueTime, String mainBackUpTagNo, String backTagStartTime, String backTagEndTime, String mainReceivePlace, String mainContactMan, String mainContactPhone, String mainInvoiceHead, String mainAddress, String mainPostcode, String mainRecoverTime, String mainSendRepairTime, String mainRepairBackTime, String mainNoticeCustomerTime, String mainBackToCustomerTime, String mainMemo, String vehiclePlate, String vehiclePlateColor, String vehicleWeightLimits, String vehicleEngineNo, String vehicleModel, String vehicleType, String vehicleUserType, String vehicleUsingNature, String vehicleOwner, String vehicleLong, String vehicleWidth, String vehicleHeight, String vehicleNSCvehicletype, String vehicleIdentificationCode, String vehicleAxles, String vehicleWheels, String installMan) {
        this.tagNo = tagNo;
        this.backTagIssueTime = backTagIssueTime;
        this.mainBackUpTagNo = mainBackUpTagNo;
        this.backTagStartTime = backTagStartTime;
        this.backTagEndTime = backTagEndTime;
        this.mainReceivePlace = mainReceivePlace;
        this.mainContactMan = mainContactMan;
        this.mainContactPhone = mainContactPhone;
        this.mainInvoiceHead = mainInvoiceHead;
        this.mainAddress = mainAddress;
        this.mainPostcode = mainPostcode;
        this.mainRecoverTime = mainRecoverTime;
        this.mainSendRepairTime = mainSendRepairTime;
        this.mainRepairBackTime = mainRepairBackTime;
        this.mainNoticeCustomerTime = mainNoticeCustomerTime;
        this.mainBackToCustomerTime = mainBackToCustomerTime;
        this.mainMemo = mainMemo;
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
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getBackTagIssueTime() {
        return backTagIssueTime;
    }

    public void setBackTagIssueTime(String backTagIssueTime) {
        this.backTagIssueTime = backTagIssueTime;
    }

    public String getMainBackUpTagNo() {
        return mainBackUpTagNo;
    }

    public void setMainBackUpTagNo(String mainBackUpTagNo) {
        this.mainBackUpTagNo = mainBackUpTagNo;
    }

    public String getBackTagStartTime() {
        return backTagStartTime;
    }

    public void setBackTagStartTime(String backTagStartTime) {
        this.backTagStartTime = backTagStartTime;
    }

    public String getBackTagEndTime() {
        return backTagEndTime;
    }

    public void setBackTagEndTime(String backTagEndTime) {
        this.backTagEndTime = backTagEndTime;
    }

    public String getMainReceivePlace() {
        return mainReceivePlace;
    }

    public void setMainReceivePlace(String mainReceivePlace) {
        this.mainReceivePlace = mainReceivePlace;
    }

    public String getMainContactMan() {
        return mainContactMan;
    }

    public void setMainContactMan(String mainContactMan) {
        this.mainContactMan = mainContactMan;
    }

    public String getMainContactPhone() {
        return mainContactPhone;
    }

    public void setMainContactPhone(String mainContactPhone) {
        this.mainContactPhone = mainContactPhone;
    }

    public String getMainInvoiceHead() {
        return mainInvoiceHead;
    }

    public void setMainInvoiceHead(String mainInvoiceHead) {
        this.mainInvoiceHead = mainInvoiceHead;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getMainPostcode() {
        return mainPostcode;
    }

    public void setMainPostcode(String mainPostcode) {
        this.mainPostcode = mainPostcode;
    }

    public String getMainRecoverTime() {
        return mainRecoverTime;
    }

    public void setMainRecoverTime(String mainRecoverTime) {
        this.mainRecoverTime = mainRecoverTime;
    }

    public String getMainSendRepairTime() {
        return mainSendRepairTime;
    }

    public void setMainSendRepairTime(String mainSendRepairTime) {
        this.mainSendRepairTime = mainSendRepairTime;
    }

    public String getMainRepairBackTime() {
        return mainRepairBackTime;
    }

    public void setMainRepairBackTime(String mainRepairBackTime) {
        this.mainRepairBackTime = mainRepairBackTime;
    }

    public String getMainNoticeCustomerTime() {
        return mainNoticeCustomerTime;
    }

    public void setMainNoticeCustomerTime(String mainNoticeCustomerTime) {
        this.mainNoticeCustomerTime = mainNoticeCustomerTime;
    }

    public String getMainBackToCustomerTime() {
        return mainBackToCustomerTime;
    }

    public void setMainBackToCustomerTime(String mainBackToCustomerTime) {
        this.mainBackToCustomerTime = mainBackToCustomerTime;
    }

    public String getMainMemo() {
        return mainMemo;
    }

    public void setMainMemo(String mainMemo) {
        this.mainMemo = mainMemo;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(super.toString());
        sb.append(",\"tagNo\":\"" ).append(tagNo).append('\"');
        sb.append(",\"backTagIssueTime\":\"" ).append(backTagIssueTime).append('\"');
        sb.append(",\"mainBackUpTagNo\":\"" ).append(mainBackUpTagNo).append('\"');
        sb.append(",\"backTagStartTime\":\"" ).append(backTagStartTime).append('\"');
        sb.append(",\"backTagEndTime\":\"" ).append(backTagEndTime).append('\"');
        sb.append(",\"mainReceivePlace\":\"" ).append(mainReceivePlace).append('\"');
        sb.append(",\"mainContactMan\":\"" ).append(mainContactMan).append('\"');
        sb.append(",\"mainContactPhone\":\"" ).append(mainContactPhone).append('\"');
        sb.append(",\"mainInvoiceHead\":\"" ).append(mainInvoiceHead).append('\"');
        sb.append(",\"mainAddress\":\"" ).append(mainAddress).append('\"');
        sb.append(",\"mainPostcode\":\"" ).append(mainPostcode).append('\"');
        sb.append(",\"mainRecoverTime\":\"" ).append(mainRecoverTime).append('\"');
        sb.append(",\"mainSendRepairTime\":\"" ).append(mainSendRepairTime).append('\"');
        sb.append(",\"mainRepairBackTime\":\"" ).append(mainRepairBackTime).append('\"');
        sb.append(",\"mainNoticeCustomerTime\":\"" ).append(mainNoticeCustomerTime).append('\"');
        sb.append(",\"mainBackToCustomerTime\":\"" ).append(mainBackToCustomerTime).append('\"');
        sb.append(",\"mainMemo\":\"" ).append(mainMemo).append('\"');
        sb.append(",\"vehiclePlate\":\"" ).append(vehiclePlate).append('\"');
        sb.append(",\"vehiclePlateColor\":\"" ).append(vehiclePlateColor).append('\"');
        sb.append(",\"vehicleWeightLimits\":\"" ).append(vehicleWeightLimits).append('\"');
        sb.append(",\"vehicleEngineNo\":\"" ).append(vehicleEngineNo).append('\"');
        sb.append(",\"vehicleModel\":\"" ).append(vehicleModel).append('\"');
        sb.append(",\"vehicleType\":\"" ).append(vehicleType).append('\"');
        sb.append(",\"vehicleUserType\":\"" ).append(vehicleUserType).append('\"');
        sb.append(",\"vehicleUsingNature\":\"" ).append(vehicleUsingNature).append('\"');
        sb.append(",\"vehicleOwner\":\"" ).append(vehicleOwner).append('\"');
        sb.append(",\"vehicleLong\":\"" ).append(vehicleLong).append('\"');
        sb.append(",\"vehicleWidth\":\"" ).append(vehicleWidth).append('\"');
        sb.append(",\"vehicleHeight\":\"" ).append(vehicleHeight).append('\"');
        sb.append(",\"vehicleNSCvehicletype\":\"" ).append(vehicleNSCvehicletype).append('\"');
        sb.append(",\"vehicleIdentificationCode\":\"" ).append(vehicleIdentificationCode).append('\"');
        sb.append(",\"vehicleAxles\":\"" ).append(vehicleAxles).append('\"');
        sb.append(",\"vehicleWheels\":\"" ).append(vehicleWheels).append('\"');
        sb.append(",\"installMan\":\"" ).append(installMan).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
