package com.hgsoft.common.Enum;

public enum ScAddReqPaychannelEnum {
    SELF_OPERATED("自营", "1"),
    AGENT("代理", "2"),
    AIR_RECHARGE("空充系统", "3");

    private String name;
    private String value;


    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    ScAddReqPaychannelEnum(String name, String value) {
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

    public static String getNameByValue(String value) {
        String name = null;
        for (ScAddReqPaychannelEnum tempEnum : ScAddReqPaychannelEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name = tempEnum.getName();
                break;
            }
        }
        return name;
    }
}
