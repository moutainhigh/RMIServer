package com.hgsoft.bank.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : 吴锡霖
 * file : BankCardLock.java
 * date : 2017-06-01
 * time : 19:55
 */
public class BankCardLock implements Serializable {

    private static final long serialVersionUID = -421411561105342128L;

    private Long id;
    private String operNo;
    private String terminalNo;
    private Date businessTime;
    private String vehiclePlate;
    private String vehicleColor;
    private String vehicleType;
    private String vehicleFlag;
    private Integer vehicleWeightLimits;
    private String owner;
    private String address;
    private String usingNature;
    private String model;
    private String identificationCode;
    private String vehicleEngineNo;
    private Date registeredDate;
    private Date issueDate;
    private String bankNo;
    private Integer lockDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleFlag() {
        return vehicleFlag;
    }

    public void setVehicleFlag(String vehicleFlag) {
        this.vehicleFlag = vehicleFlag;
    }

    public Integer getVehicleWeightLimits() {
        return vehicleWeightLimits;
    }

    public void setVehicleWeightLimits(Integer vehicleWeightLimits) {
        this.vehicleWeightLimits = vehicleWeightLimits;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsingNature() {
        return usingNature;
    }

    public void setUsingNature(String usingNature) {
        this.usingNature = usingNature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Integer getLockDay() {
        return lockDay;
    }

    public void setLockDay(Integer lockDay) {
        this.lockDay = lockDay;
    }

    public boolean hasExpire() throws ParseException {
        if (businessTime == null || lockDay == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date businessDay = sdf.parse(sdf.format(businessTime));
        DateTime dateTime = new DateTime(businessDay);
        return dateTime.plusDays(lockDay).toDate().before(new Date());
    }
}
