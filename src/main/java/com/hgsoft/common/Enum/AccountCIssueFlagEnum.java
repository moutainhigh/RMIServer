package com.hgsoft.common.Enum;

public enum AccountCIssueFlagEnum {
	
	normalSale("正常销售","1"),
	freeSale("优惠销售","2");
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountCIssueFlagEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (AccountCIssueFlagEnum c : AccountCIssueFlagEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
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
    AccountCIssueFlagEnum(String value) {
		this.value = value;
	}
	
	public static AccountCIssueFlagEnum getMigrateStateEnum(String value) {
		AccountCIssueFlagEnum accountNCApplyEnum = null;
		for (AccountCIssueFlagEnum tempEnum : AccountCIssueFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accountNCApplyEnum = tempEnum;
				break;
			}
		}
		return accountNCApplyEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccountCIssueFlagEnum tempEnum : AccountCIssueFlagEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }

}
