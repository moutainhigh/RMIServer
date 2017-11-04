package com.hgsoft.common.Enum;

public enum VehicleTypeEnum {
	
	/**
	 * 1：货车
	 * 2：客车
	 * 3：集装箱车
	 */
	truck("货车","0"),car("客车","2"),containertruck("集装箱车","5");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private VehicleTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (VehicleTypeEnum c : VehicleTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getValue(String name) {
        for (VehicleTypeEnum c : VehicleTypeEnum .values()) {
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

    public void setValue(String index) {
        this.value = index;
    }

	/** Internal constructor */
    VehicleTypeEnum(String value) {
		this.value = value;
	}
	
	public static VehicleTypeEnum getIdTypeEnum(String value) {
		VehicleTypeEnum type = null;
		for (VehicleTypeEnum tempEnum : VehicleTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
