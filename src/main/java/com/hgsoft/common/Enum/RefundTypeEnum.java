package com.hgsoft.common.Enum;

public enum RefundTypeEnum {
	preCardCancel("储值卡注销","1"),bailRefund("保证金退还","2"),accountRefund("余额退款","3");

	private String value;

	private String name;
	
	private RefundTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (RefundTypeEnum c : RefundTypeEnum .values()) {
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
    RefundTypeEnum(String value) {
		this.value = value;
	}
    

	
	public static RefundTypeEnum getRefundTypeEnum(String value) {
		RefundTypeEnum refundAuditStatusEnum = null;
		for (RefundTypeEnum tempEnum : RefundTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				refundAuditStatusEnum = tempEnum;
				break;
			}
		}
		return refundAuditStatusEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (RefundTypeEnum tempEnum : RefundTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
