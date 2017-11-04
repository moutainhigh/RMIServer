package com.hgsoft.common.Enum;

public enum BlackFlagEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
	unblack("未下发黑名单","0"),black("已下发黑名单","1");
	
	private String value;

	private String name;

	/** Internal constructor */
	BlackFlagEnum(String name,String value) {
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
	
	public static BlackFlagEnum getIdTypeEnum(String value) {
		BlackFlagEnum type = null;
		for (BlackFlagEnum tempEnum : BlackFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
