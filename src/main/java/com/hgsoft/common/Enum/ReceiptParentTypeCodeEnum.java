package com.hgsoft.common.Enum;

/**
 * 回执记录表-业务大类
 */
public enum ReceiptParentTypeCodeEnum {
    account("账户信息", "1"),
    prepaidC("储值卡","2"),
    accountC("记帐卡","3"),
    tag("电子标签","4"),
    customer("客户信息","5"),
    dayset("日结","6"),
    vehicle("车辆信息业务","7"),
    other("其他服务","8");

    private String value;

    private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ReceiptParentTypeCodeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String index) {
        this.value = index;
    }

    /**
     * Internal constructor
     */
    ReceiptParentTypeCodeEnum(String value) {
        this.value = value;
    }

    public static ReceiptParentTypeCodeEnum getReceiptParentTypeCodeEnum(String value) {
        ReceiptParentTypeCodeEnum receiptParentTypeCodeEnum = null;
        for (ReceiptParentTypeCodeEnum tempEnum : ReceiptParentTypeCodeEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                receiptParentTypeCodeEnum = tempEnum;
                break;
            }
        }
        return receiptParentTypeCodeEnum;
    }

}
