package com.hgsoft.common.Enum;

/**
 * 其他服务业务类型
 * Created by wiki on 2017/9/11.
 */
public enum OtherBussinessTypeEnum {

    chaseMoney("追款管理","1"),
    specialCost("特殊费用收取","2"),
    policeOfficialCard("公安公务卡签管理","3");

    private String name;
    private String value;

    private OtherBussinessTypeEnum(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static String getName(String value){
        for (OtherBussinessTypeEnum temp : OtherBussinessTypeEnum.values()) {
            if (temp.getValue().equals(value)) {
                return temp.name;
            }
        }
        return null;
    }
}
