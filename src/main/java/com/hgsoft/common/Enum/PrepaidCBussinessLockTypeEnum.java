package com.hgsoft.common.Enum;

/**
 * Created by yangzhongji on 17/10/20.
 */
public enum PrepaidCBussinessLockTypeEnum {
    NO_CARD_LOCK("无卡锁定","0"), CARD_LOCK("有卡锁定","1");

    private String name;
    private String value;

    PrepaidCBussinessLockTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
