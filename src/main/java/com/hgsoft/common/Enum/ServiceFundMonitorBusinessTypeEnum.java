package com.hgsoft.common.Enum;

/**
 * Created by yangzhongji on 17/7/25.
 */
public enum ServiceFundMonitorBusinessTypeEnum {
    recharge("充值", "15"),
    scCardSale("储值卡销售", "27"),
    obuIssue("电子标签发行", "40"),
    obuPickUp("电子标签提货", "47");

    private String value;
    private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ServiceFundMonitorBusinessTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get 方法
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    /** Internal constructor */
    ServiceFundMonitorBusinessTypeEnum(String value) {
        this.value = value;
    }

    public static ServiceFundMonitorBusinessTypeEnum getEnumByValue(String value) {
        ServiceFundMonitorBusinessTypeEnum retEnum = null;
        for (ServiceFundMonitorBusinessTypeEnum tempEnum : ServiceFundMonitorBusinessTypeEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                retEnum = tempEnum;
                break;
            }
        }
        return retEnum;
    }

    public static String getNameByValue(String value){
        String name = null;
        for (ServiceFundMonitorBusinessTypeEnum tempEnum : ServiceFundMonitorBusinessTypeEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name=tempEnum.getName();
                break;
            }
        }
        return name;
    }
}
