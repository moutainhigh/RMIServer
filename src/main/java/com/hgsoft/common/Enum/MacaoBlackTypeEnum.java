package com.hgsoft.common.Enum;

public enum MacaoBlackTypeEnum {
//1：解除止付   4：下发止付
	
	relieveStop("解除止付黑名单","1"),issueStop("下发止付黑名单","4");
	
	private String value;

	private String name;

	/** Internal constructor */
	MacaoBlackTypeEnum(String name,String value) {
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
	
	public static MacaoBlackTypeEnum getIdTypeEnum(String value) {
		MacaoBlackTypeEnum type = null;
		for (MacaoBlackTypeEnum tempEnum : MacaoBlackTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
