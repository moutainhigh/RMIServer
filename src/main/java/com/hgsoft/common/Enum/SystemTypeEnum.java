package com.hgsoft.common.Enum;

public enum SystemTypeEnum {
	
	CSMS("自营客服系统","1"),ACMS("香港联营卡","2"),NO("澳门通","3"),
	AM("代理点客服系统","4"),ONLINE("网上营业厅","5"),IVR("呼叫中心","6");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private SystemTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String value) {
        for (SystemTypeEnum c : SystemTypeEnum .values()) {
            if (c.getValue().equals(value)) {
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

	/** Internal constructor */
    SystemTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
