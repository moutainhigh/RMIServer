package com.hgsoft.common.Enum;

public enum VehicleUsingTypeEnum {
	
	
	nonOfficialCar("非公务车","0"),officialCar("公务车","1");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private VehicleUsingTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (VehicleUsingTypeEnum c : VehicleUsingTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
 // 普通方法
    public static String getValue(String name) {
        for (VehicleUsingTypeEnum c : VehicleUsingTypeEnum .values()) {
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
    VehicleUsingTypeEnum(String value) {
		this.value = value;
	}
	
	public static VehicleUsingTypeEnum getIdTypeEnum(String value) {
		VehicleUsingTypeEnum colors = null;
		for (VehicleUsingTypeEnum tempEnum : VehicleUsingTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				colors = tempEnum;
				break;
			}
		}
		return colors;
	}
}
