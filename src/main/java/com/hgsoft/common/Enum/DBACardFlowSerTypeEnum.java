package com.hgsoft.common.Enum;

public enum DBACardFlowSerTypeEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
/*	prePaidReplace("储值卡补卡","01"),prePaidChange("储值卡换卡","02"),
	prePaidCancel("储值卡无卡注销","03"),accountCReplace("记帐卡补卡","11"),
	accountCChange("记帐卡换卡","12"),accountCCancel("记帐卡无卡注销","13");*/
	
	nocardCannel("无卡注销","01"),
	gainCard("换卡","02"),
	cardCannel("有卡注销","03"),
	lossReplaceCard("挂失补领","11"),
	accountBankCannel("记帐卡账户注销","17"),
	prepaidCCardIssuice("储值卡发行","24"),
	accountCCardIssuice("记帐卡发行","27");
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowSerTypeEnum(String name,String value) {
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
	
	public static DBACardFlowSerTypeEnum getIdTypeEnum(String value) {
		DBACardFlowSerTypeEnum type = null;
		for (DBACardFlowSerTypeEnum tempEnum : DBACardFlowSerTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
