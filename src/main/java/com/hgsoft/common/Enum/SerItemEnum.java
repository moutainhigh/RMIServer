package com.hgsoft.common.Enum;

public enum SerItemEnum {
/*
	1：邮寄清单服务
	2： 短信服务
	3：E_mail服务
	4：邮寄发票帐单服务
*/
	invoiceBillService("普通邮寄账单服务","1"),emailService("电子清单服务","2"),arrearsNotify("欠费通知","3"),
	monthSetlleNotify("月结通知","4"),debifNotify("扣款通知","5"),rechargeNotify("充值通知","6"),invoiceBillGetAmtService("邮寄到付账单服务","7");
	
	private String value;

	private String name;
	
	private SerItemEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (SerItemEnum c : SerItemEnum .values()) {
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
    SerItemEnum(String value) {
		this.value = value;
	}
	
	public static SerItemEnum getSerItemEnum(String value) {
		SerItemEnum serItemEnum = null;
		for (SerItemEnum tempEnum : SerItemEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				serItemEnum = tempEnum;
				break;
			}
		}
		return serItemEnum;
	}
}
