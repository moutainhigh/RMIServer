package com.hgsoft.common.Enum;

public enum DBACardFlowReadFlagEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
	disabledRead("不可读","0"),abledRead("可读","1");
	
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowReadFlagEnum(String name,String value) {
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
	
	public static DBACardFlowReadFlagEnum getIdTypeEnum(String value) {
		DBACardFlowReadFlagEnum type = null;
		for (DBACardFlowReadFlagEnum tempEnum : DBACardFlowReadFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
