package com.hgsoft.common.Enum;

public enum CardBusinessTypeEnum {
	
	AMS("联名卡客服系统","1"),BANK("银行自动接口","2");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CardBusinessTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String value) {
        for (CardBusinessTypeEnum c : CardBusinessTypeEnum.values()) {
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
    CardBusinessTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public boolean getIsThis(String value) {
        return this.value != null && this.value.equals(value);
    }

}
