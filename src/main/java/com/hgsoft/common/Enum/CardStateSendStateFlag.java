package com.hgsoft.common.Enum;

public enum CardStateSendStateFlag {
	nomal("正常","0"),loss("挂失","1"),cancel("注销","2"),
	disabled("挂起","3");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CardStateSendStateFlag(String name, String value) {
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
    CardStateSendStateFlag(String value) {
		this.value = value;
	}
	
	public static CardStateSendStateFlag getAccChangeTypeEnum(String value) {
		CardStateSendStateFlag accChangeTypeEnum = null;
		for (CardStateSendStateFlag tempEnum : CardStateSendStateFlag.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (CardStateSendStateFlag tempEnum : CardStateSendStateFlag.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
