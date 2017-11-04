package com.hgsoft.common.Enum;

public enum SureVehicleEnum {
//1：正常2：停用3：维修 4：注销
	vehicleNotHere("车辆不存在","0"),vehicleIsBind("车辆已绑定","1"),vehicleNoBind("车辆未绑定","2");
	private String value;

	private String name;

	/** Internal constructor */
	SureVehicleEnum(String name,String value) {
		this.name=name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public String getName() {
		//name = this.toString();
		return name;
	}
	
	public static SureVehicleEnum getIdTypeEnum(String value) {
		SureVehicleEnum type = null;
		for (SureVehicleEnum tempEnum : SureVehicleEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
