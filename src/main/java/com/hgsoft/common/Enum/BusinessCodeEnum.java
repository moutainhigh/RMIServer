package com.hgsoft.common.Enum;

/**
 * Created by 孙晓伟
 * file : BusinessCodeEnum.java
 * date : 2017/7/27
 * time : 10:16
 */

/**
 * 记账卡业务办理-业务类型枚举类
 */
public enum BusinessCodeEnum {
    openAccountApply("开户申请","01"),
    openAccountConfirm("开户确认","02"),
    dataChange("资料变更","03"),
    saveLose("挂失","04"),
    changeCardApply("换卡申请","05"),
    changeCardConfirm("换卡确认","06"),
    replaceCardApply("补领申请","07"),
    replaceCardConfirm("补领确认","08"),
    cancelCard("销卡","09"),
    stopPayBlackListIssued("止付黑名单下发","10"),
    stopPayBlackListRelieve("止付黑名单解除","11"),
    restartCard("销户后重启","13"),
    relieveLose("解挂","14"),
    noneReplaceCard("非过户补领","16"),
    replaceCardTransApply("过户补领申请","17"),
    replaceCardTransConfirm("过户补领确认","18");

    private String value;

    private String name;

    private BusinessCodeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BusinessCodeEnum getBusinessCodeEnum(String value) {
        BusinessCodeEnum businessCodeEnum = null;
        for (BusinessCodeEnum tempEnum : BusinessCodeEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                businessCodeEnum = tempEnum;
                break;
            }
        }
        return businessCodeEnum;
    }
    public static String getNameByValue(String value){
        String name = null;
        for (BusinessCodeEnum tempEnum : BusinessCodeEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name=tempEnum.getName();
                break;
            }
        }
        return name;
    }
}
