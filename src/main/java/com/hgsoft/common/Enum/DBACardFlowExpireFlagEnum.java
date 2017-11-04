package com.hgsoft.common.Enum;

public enum DBACardFlowExpireFlagEnum {
	
	arriDispute("已过争议期","0"),
	disDispute("没有过争议期","1"),
	handleDisable("处理未完成","2");
	
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowExpireFlagEnum(String name,String value) {
		this.value = value;
		this.name=name;
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
	
	public static DBACardFlowExpireFlagEnum getIdTypeEnum(String value) {
		DBACardFlowExpireFlagEnum type = null;
		for (DBACardFlowExpireFlagEnum tempEnum : DBACardFlowExpireFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
