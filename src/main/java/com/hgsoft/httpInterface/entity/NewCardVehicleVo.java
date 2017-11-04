package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by apple on 2017/5/3.
 */
public class NewCardVehicleVo implements Serializable {
    private static final long serialVersionUID = 4693200106063659995L;
    private Long newCardApplyid;
    private String vehiclePlate;
    private String vehicleColor;
    private String bailType;
    private BigDecimal bail;

    public Long getNewCardApplyid() {
        return newCardApplyid;
    }

    public void setNewCardApplyid(Long newCardApplyid) {
        this.newCardApplyid = newCardApplyid;
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

    public String getBailType() {
        return bailType;
    }

    public void setBailType(String bailType) {
        this.bailType = bailType;
    }

    public BigDecimal getBail() {
        return bail;
    }

    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }
}
