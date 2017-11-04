package com.hgsoft.common.Enum;

public enum DBACardFlowEndFlagEnum {
	
	disComplete("未完成","0"),
	arriComplete("已完成","1"),
	waitComplete("待完成（针对充值）","2");
	
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowEndFlagEnum(String name,String value) {
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
	
	public static DBACardFlowEndFlagEnum getIdTypeEnum(String value) {
		DBACardFlowEndFlagEnum type = null;
		for (DBACardFlowEndFlagEnum tempEnum : DBACardFlowEndFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
