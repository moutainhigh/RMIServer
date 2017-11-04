package com.hgsoft.common.Enum;

public enum AccountTypeTypeEnum {
	
	forPublic("对公","0"),
	prepaid("储蓄","1"),
	creditCard("信用卡","2"),
	crossRow("跨行划扣","3"),
	unionPaid("统一划账","4"),
	other("其他","5");
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountTypeTypeEnum(String name, String value) {
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
    AccountTypeTypeEnum(String value) {
		this.value = value;
	}
	
	public static AccountTypeTypeEnum getMigrateStateEnum(String value) {
		AccountTypeTypeEnum accountNCApplyEnum = null;
		for (AccountTypeTypeEnum tempEnum : AccountTypeTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accountNCApplyEnum = tempEnum;
				break;
			}
		}
		return accountNCApplyEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccountTypeTypeEnum tempEnum : AccountTypeTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }

}
