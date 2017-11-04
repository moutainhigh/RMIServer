package com.hgsoft.common.Enum;

public enum AddRegDetailStateEnum {
//1：正常2：停用3：维修 4：注销
	normal("新录入","0"),rechargeHalf("充值半条","2"),rechargeSuccess("充值成功","3"),refund("已经退款","4");
	private String value;

	private String name;

	/** Internal constructor */
	AddRegDetailStateEnum(String name,String value) {
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
	
	public static AddRegDetailStateEnum getIdTypeEnum(String value) {
		AddRegDetailStateEnum type = null;
		for (AddRegDetailStateEnum tempEnum : AddRegDetailStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
