package com.hgsoft.common.Enum;

public enum AccountCShutDownStatusEnum {
//1：正常2：停用3：维修 4：注销
	start("正常","0"),shutDown("停用","1");
	private String value;

	private String name;

	/** Internal constructor */
	AccountCShutDownStatusEnum(String name,String value) {
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
	
	public static AccountCShutDownStatusEnum getIdTypeEnum(String value) {
		AccountCShutDownStatusEnum type = null;
		for (AccountCShutDownStatusEnum tempEnum : AccountCShutDownStatusEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
