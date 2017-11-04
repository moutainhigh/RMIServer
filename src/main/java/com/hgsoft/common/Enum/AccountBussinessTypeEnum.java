package com.hgsoft.common.Enum;

public enum AccountBussinessTypeEnum {
	recharge("账户缴款","1"),
	correct("账户冲正","2"),
	modify("账户缴款修改","3"),
	tranferRecharge("账户转账缴款","4"),
	refund("账户退款","5"),
	refundRevoke("账户退款撤销","6");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountBussinessTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String index) {
        this.value = index;
    }

	/** Internal constructor */
    AccountBussinessTypeEnum(String value) {
		this.value = value;
	}
	
	public static AccountBussinessTypeEnum getAccountBussinessTypeEnum(String value) {
		AccountBussinessTypeEnum accChangeTypeEnum = null;
		for (AccountBussinessTypeEnum tempEnum : AccountBussinessTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccountBussinessTypeEnum tempEnum : AccountBussinessTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
