package com.hgsoft.common.Enum;

public enum PayMentTypeEnum {
	cash("现金","1"),POS("POS","2"),tranfer("转账","3"),apliays("支付宝","4"),wechat("微信","5"),longCorrection("长款修正","6"),voucher("缴款单","7");

	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private PayMentTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (PayMentTypeEnum c : PayMentTypeEnum .values()) {
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
    PayMentTypeEnum(String value) {
		this.value = value;
	}
	
	public static PayMentTypeEnum getPayMentTypeEnum(String value) {
		PayMentTypeEnum payMentTypeEnum = null;
		for (PayMentTypeEnum tempEnum : PayMentTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				payMentTypeEnum = tempEnum;
				break;
			}
		}
		return payMentTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (PayMentTypeEnum tempEnum : PayMentTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
