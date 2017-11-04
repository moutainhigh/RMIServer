package com.hgsoft.common.Enum;

public enum PrepaidCFristRechargeEnum {
    NOT_FIRST("非首次","0"), FIRST("首次","1");


	private String name;

    private String value;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    PrepaidCFristRechargeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


}
