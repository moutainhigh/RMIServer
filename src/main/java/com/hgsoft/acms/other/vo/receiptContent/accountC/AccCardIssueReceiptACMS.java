package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记帐卡发行回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardIssueReceiptACMS extends BaseReceiptContent{

    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 开户银行账号
     */
    private String applyBankAccount;
    /**
     * 工 本 费
     */
    private String accCardCost;
    /**
     * 销售方式
     */
    private String accCardIssueFlag;
    /**
     * 绑定标识
     */
    private String accCardSuit;
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

    public String getAccCardNo() {
        return accCardNo;
    }

    public void setAccCardNo(String accCardNo) {
        this.accCardNo = accCardNo;
    }

    public String getApplyBankAccount() {
        return applyBankAccount;
    }

    public void setApplyBankAccount(String applyBankAccount) {
        this.applyBankAccount = applyBankAccount;
    }

    public String getAccCardCost() {
        return accCardCost;
    }

    public void setAccCardCost(String accCardCost) {
        this.accCardCost = accCardCost;
    }

    public String getAccCardIssueFlag() {
        return accCardIssueFlag;
    }

    public void setAccCardIssueFlag(String accCardIssueFlag) {
        this.accCardIssueFlag = accCardIssueFlag;
    }

    public String getAccCardSuit() {
        return accCardSuit;
    }

    public void setAccCardSuit(String accCardSuit) {
        this.accCardSuit = accCardSuit;
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
