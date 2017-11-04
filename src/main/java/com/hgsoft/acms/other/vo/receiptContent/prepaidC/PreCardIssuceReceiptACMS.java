package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡发行回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardIssuceReceiptACMS extends BaseReceiptContent {

    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 面值
     */
    private String preCardFaceValue;
    /**
     * 工本费
     */
    private String preCardCost;
    /**
     * 销售方式
     */
    private String preCardSaleFlag;
    /**
     * 合计（面值+工本费）
     */
    private String preCardSumCost;
    /**
     * 发票打印方式
     */
    private String preCardInvoicePrint;
    /**
     * 服务项目
     */
    private String serItem;
    /**
     * 车牌号码
     */
    private String vehiclePlate;
    /**
     * 车牌颜色
     */
    private String vehiclePlateColor;
    /**
     * 绑定表标志
     */
    private String preCardsuit;
    /**
     * 吨位（KG）/座位数（个）
     */
    private String vehicleWeightLimits;
    /**
     * 国际收费车型
     */
    private String vehicleNSCvehicletype;
    /**
     * 车辆类型
     */
    private String vehicleType;
    /**
     * 车主名称
     */
    private String vehicleOwner;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getPreCardFaceValue() {
        return preCardFaceValue;
    }

    public void setPreCardFaceValue(String preCardFaceValue) {
        this.preCardFaceValue = preCardFaceValue;
    }

    public String getPreCardCost() {
        return preCardCost;
    }

    public void setPreCardCost(String preCardCost) {
        this.preCardCost = preCardCost;
    }

    public String getPreCardSaleFlag() {
        return preCardSaleFlag;
    }

    public void setPreCardSaleFlag(String preCardSaleFlag) {
        this.preCardSaleFlag = preCardSaleFlag;
    }

    public String getPreCardSumCost() {
        return preCardSumCost;
    }

    public void setPreCardSumCost(String preCardSumCost) {
        this.preCardSumCost = preCardSumCost;
    }

    public String getPreCardInvoicePrint() {
        return preCardInvoicePrint;
    }

    public void setPreCardInvoicePrint(String preCardInvoicePrint) {
        this.preCardInvoicePrint = preCardInvoicePrint;
    }

    public String getSerItem() {
        return serItem;
    }

    public void setSerItem(String serItem) {
        this.serItem = serItem;
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

    public String getPreCardsuit() {
        return preCardsuit;
    }

    public void setPreCardsuit(String preCardsuit) {
        this.preCardsuit = preCardsuit;
    }

    public String getVehicleWeightLimits() {
        return vehicleWeightLimits;
    }

    public void setVehicleWeightLimits(String vehicleWeightLimits) {
        this.vehicleWeightLimits = vehicleWeightLimits;
    }

    public String getVehicleNSCvehicletype() {
        return vehicleNSCvehicletype;
    }

    public void setVehicleNSCvehicletype(String vehicleNSCvehicletype) {
        this.vehicleNSCvehicletype = vehicleNSCvehicletype;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleOwner() {
        return vehicleOwner;
    }

    public void setVehicleOwner(String vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }
}
