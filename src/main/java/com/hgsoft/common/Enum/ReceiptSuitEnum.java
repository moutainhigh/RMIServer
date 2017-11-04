package com.hgsoft.common.Enum;

public enum ReceiptSuitEnum {

    noBindTag("单卡","0"),
    bindTag("套装","1");

    private String value;

    private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ReceiptSuitEnum(String name, String value) {
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

    /** Internal constructor */
    ReceiptSuitEnum(String value) {
        this.value = value;
    }

    public static ReceiptSuitEnum getReceiptSuitEnum(String value) {
        ReceiptSuitEnum receiptSuitEnum = null;
        for (ReceiptSuitEnum tempEnum : ReceiptSuitEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                receiptSuitEnum = tempEnum;
                break;
            }
        }
        return receiptSuitEnum;
    }
    public static String getNameByValue(String value){
        String name = null;
        for (ReceiptSuitEnum tempEnum : ReceiptSuitEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name=tempEnum.getName();
                break;
            }
        }
        return name;
    }
}
