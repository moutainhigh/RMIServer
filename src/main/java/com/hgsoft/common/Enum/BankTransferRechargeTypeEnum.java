package com.hgsoft.common.Enum;

public enum BankTransferRechargeTypeEnum {
	
	accountTransferRecharge("账户转账缴款","1"),tagTakeFeeTransfer("电子标签提货金额登记转账缴款","2"),
	accountTransferDelete("账户转账缴款冲正","3"),tagTakeFeeTransferDelete("电子标签提货金额登记转账缴款冲正","4"),
	tagTakeFeeTransferUpdate("电子标签提货金额登记转账缴款修改","5");
	
	private String value;

	private String name;

	/** Internal constructor */
	BankTransferRechargeTypeEnum(String name,String value) {
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
	
	public static BankTransferRechargeTypeEnum getIdTypeEnum(String value) {
		BankTransferRechargeTypeEnum type = null;
		for (BankTransferRechargeTypeEnum tempEnum : BankTransferRechargeTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
