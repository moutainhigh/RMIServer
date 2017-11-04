package com.hgsoft.common.Enum;

public enum PrepaidCardBussinessTradeStateEnum {
	save("保存数据库成功","1"),
	success("完成","2"),
	fail("失败","3");

	private String value;
	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private PrepaidCardBussinessTradeStateEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get 方法
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


	/** Internal constructor */
    PrepaidCardBussinessTradeStateEnum(String value) {
		this.value = value;
	}
	
	public static PrepaidCardBussinessTradeStateEnum getEnumByValue(String value) {
		PrepaidCardBussinessTradeStateEnum retEnum = null;
		for (PrepaidCardBussinessTradeStateEnum tempEnum : PrepaidCardBussinessTradeStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				retEnum = tempEnum;
				break;
			}
		}
		return retEnum;
	}

	public static String getNameByValue(String value){
		  String name = null;
		  for (PrepaidCardBussinessTradeStateEnum tempEnum : PrepaidCardBussinessTradeStateEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
	}
}
