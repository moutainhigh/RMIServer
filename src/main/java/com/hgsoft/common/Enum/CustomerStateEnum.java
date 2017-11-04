package com.hgsoft.common.Enum;

public enum CustomerStateEnum {
/*
	1.正常:normal
	2.销户:Cancellation

*/
	normal("正常","1"),cancellation("销户","2");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CustomerStateEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (CustomerStateEnum c : CustomerStateEnum.values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
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
    CustomerStateEnum(String value) {
		this.value = value;
	}
	
	public static CustomerStateEnum getCustomerStateEnum(String value) {
		CustomerStateEnum customerStateEnum = null;
		for (CustomerStateEnum tempEnum : CustomerStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				customerStateEnum = tempEnum;
				break;
			}
		}
		return customerStateEnum;
	}
}
