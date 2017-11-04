package com.hgsoft.common.Enum;

public enum VehicleColorEnum {

	blue("蓝牌","0"),yellow("黄牌","1"),blace("黑牌","2"),white("白牌","3"),changeGreen("渐变绿色","4"),yellowGreen("黄绿双拼色","5"),bluewhite("蓝白渐变","9");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private VehicleColorEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (VehicleColorEnum c : VehicleColorEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getValue(String name) {
        for (VehicleColorEnum c : VehicleColorEnum.values()) {
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
    VehicleColorEnum(String value) {
		this.value = value;
	}
	
	public static VehicleColorEnum getIdTypeEnum(String value) {
		VehicleColorEnum colors = null;
		for (VehicleColorEnum tempEnum : VehicleColorEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				colors = tempEnum;
				break;
			}
		}
		return colors;
	}
}
