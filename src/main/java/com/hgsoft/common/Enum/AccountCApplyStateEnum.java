package com.hgsoft.common.Enum;

public enum AccountCApplyStateEnum {
	
	noAppState("未审批","0"),
	bankAppState("银行审批中","1"),
	bankAppStatePass("银行审批通过","2"),
	bankAppStateNoPass("银行审批不通过","3"),
	operAppStateNoPass("营运审批不通过","5"),
	operCheckStatePass("营运审批通过","6");
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountCApplyStateEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (AccountCApplyStateEnum c : AccountCApplyStateEnum .values()) {
            if (c.getValue() == index) {
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
    AccountCApplyStateEnum(String value) {
		this.value = value;
	}
	
	public static AccountCApplyStateEnum getMigrateStateEnum(String value) {
		AccountCApplyStateEnum accountNCApplyEnum = null;
		for (AccountCApplyStateEnum tempEnum : AccountCApplyStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accountNCApplyEnum = tempEnum;
				break;
			}
		}
		return accountNCApplyEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccountCApplyStateEnum tempEnum : AccountCApplyStateEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }

}
