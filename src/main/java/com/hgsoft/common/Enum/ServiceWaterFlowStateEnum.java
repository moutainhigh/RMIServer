package com.hgsoft.common.Enum;

/**
 * Created by yangzhongji on 17/7/13.
 */
public enum ServiceWaterFlowStateEnum {
    complete("完成", "1"),
    isReversaled("被冲正", "2"),
    reversal("冲正", "3");

    private String value;
    private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ServiceWaterFlowStateEnum(String name, String value) {
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
    ServiceWaterFlowStateEnum(String value) {
        this.value = value;
    }

    public static ServiceWaterFlowStateEnum getEnumByValue(String value) {
        ServiceWaterFlowStateEnum retEnum = null;
        for (ServiceWaterFlowStateEnum tempEnum : ServiceWaterFlowStateEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                retEnum = tempEnum;
                break;
            }
        }
        return retEnum;
    }

    public static String getNameByValue(String value){
        String name = null;
        for (ServiceWaterFlowStateEnum tempEnum : ServiceWaterFlowStateEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name=tempEnum.getName();
                break;
            }
        }
        return name;
    }

}
