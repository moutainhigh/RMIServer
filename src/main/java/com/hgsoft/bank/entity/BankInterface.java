package com.hgsoft.bank.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : 吴锡霖
 * file : BankInterface.java
 * date : 2017-07-10
 * time : 13:49
 */
public class BankInterface implements Serializable {
    
    private static final long serialVersionUID = -7525533504928890019L;
    
    private Long id;
    private String code;
    private Long operId;
    private String operNo;
    private String operName;
    private Long placeId;
    private String placeNo;
    private String placeName;
    private Date businessTime;
    private String businessType;
    private String serviceFlowNo;
    private String utCardNo;
    private String oldUtCardNo;
    private String creditCardNo;
    private String oldCreditCardNo;
    private String bankCode;
    private String vehiclePlate;
    private String vehicleColor;
    private String cardBusinessInfo;
    private String errorCode;
    private String message;
    private Date dealTime;
    private String isSuccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperNo() {
        return operNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getServiceFlowNo() {
        return serviceFlowNo;
    }

    public void setServiceFlowNo(String serviceFlowNo) {
        this.serviceFlowNo = serviceFlowNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(String placeNo) {
        this.placeNo = placeNo;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public String getOldUtCardNo() {
        return oldUtCardNo;
    }

    public void setOldUtCardNo(String oldUtCardNo) {
        this.oldUtCardNo = oldUtCardNo;
    }

    public String getOldCreditCardNo() {
        return oldCreditCardNo;
    }

    public void setOldCreditCardNo(String oldCreditCardNo) {
        this.oldCreditCardNo = oldCreditCardNo;
    }

    public String getUtCardNo() {
        return utCardNo;
    }

    public void setUtCardNo(String utCardNo) {
        this.utCardNo = utCardNo;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getCardBusinessInfo() {
        return cardBusinessInfo;
    }

    public void setCardBusinessInfo(String cardBusinessInfo) {
        this.cardBusinessInfo = cardBusinessInfo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
}
