package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangzhongji on 17/9/28.
 */
public class HandPayment implements Serializable {

    private static final long serialVersionUID = -6888830608760781502L;

    private Long id;
    private String baccount;
    private Date payTime;
    private Date startTime;
    private Date endTime;
    private BigDecimal etcFee;
    private String flag;
    private Long operId;
    private String operNo;
    private String operName;
    private Long placeId;
    private String placeNo;
    private String placeName;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaccount() {
        return baccount;
    }

    public void setBaccount(String baccount) {
        this.baccount = baccount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getEtcFee() {
        return etcFee;
    }

    public void setEtcFee(BigDecimal etcFee) {
        this.etcFee = etcFee;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
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

    public String getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(String placeNo) {
        this.placeNo = placeNo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public enum FlagEnum {
        NORMAL("正常","0"), REFUND("已退费","1");

        private String value;

        private String name;

        /** Internal constructor */
        FlagEnum(String name, String value) {
            this.value = value;
            this.name=name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(String value) {
            String name = null;
            for (FlagEnum tempEnum : FlagEnum.values()) {
                if (tempEnum.getValue().equals(value)) {
                    name = tempEnum.getName();
                    break;
                }
            }
            return name;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
