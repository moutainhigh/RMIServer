package com.hgsoft.common.Enum;

/**
 * Created by yangzhongji on 17/7/13.
 */
public enum AddBillStateEnum {
    notPrint("未打印", "0"),
    printed("已打印", "1"),
    rePrinted("已重打", "2");

    private String value;
    private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AddBillStateEnum(String name, String value) {
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
    AddBillStateEnum(String value) {
        this.value = value;
    }

    public static AddBillStateEnum getEnumByValue(String value) {
        AddBillStateEnum retEnum = null;
        for (AddBillStateEnum tempEnum : AddBillStateEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                retEnum = tempEnum;
                break;
            }
        }
        return retEnum;
    }

    public static String getNameByValue(String value){
        String name = null;
        for (AddBillStateEnum tempEnum : AddBillStateEnum.values()) {
            if (tempEnum.getValue().equals(value)) {
                name=tempEnum.getName();
                break;
            }
        }
        return name;
    }

}
