package com.hgsoft.common.Enum;

public enum UsingNatureEnum {
	
	/**
	 * 1：非营运
	 * 2：营运
	 */
	unOperaction("非运营","1"),operation("营运","2");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private UsingNatureEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (UsingNatureEnum c : UsingNatureEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getValue(String name) {
        for (UsingNatureEnum c : UsingNatureEnum.values()) {
            if (c.getName().equals(name)) {
                return c.value;
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

    public void setValue(String value) {
        this.value = value;
    }

	/** Internal constructor */
    UsingNatureEnum(String value) {
		this.value = value;
	}
	
	public static UsingNatureEnum getIdTypeEnum(String value) {
		UsingNatureEnum type = null;
		for (UsingNatureEnum tempEnum : UsingNatureEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}


}
