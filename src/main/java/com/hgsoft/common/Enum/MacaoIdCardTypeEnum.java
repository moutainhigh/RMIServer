package com.hgsoft.common.Enum;

/**
 * 澳门证件类型
 * @author Administrator
 * @date 2017年9月19日
 */
public enum MacaoIdCardTypeEnum {
	//2017/04/24 澳门通的证件类型修改
	macaoIdCard("澳門永久/非永久居民身份證","13"),hongKong("香港永久/非永久居民身份證","14"),chinaIdCard("中華人民共和國居民身份證","15"),
	passport("护照","16"),companyBusReg("公司商业登记副本（3个月内）","17"),
	companyBusLicM8("公司营业税单M8副本","18"),companyBusLicM1("公司营业税开业/更改M1副本","19");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private MacaoIdCardTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (MacaoIdCardTypeEnum c : MacaoIdCardTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    public static String getValue(String name) {
        for (MacaoIdCardTypeEnum c : MacaoIdCardTypeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.value;
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
    MacaoIdCardTypeEnum(String value) {
		this.value = value;
	}
	
	public static MacaoIdCardTypeEnum getMacaoIdCardTypeEnum(String value) {
		MacaoIdCardTypeEnum macaoIdCardTypeEnum = null;
		for (MacaoIdCardTypeEnum tempEnum : MacaoIdCardTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				macaoIdCardTypeEnum = tempEnum;
				break;
			}
		}
		return macaoIdCardTypeEnum;
	}
}
