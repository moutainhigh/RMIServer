package com.hgsoft.common.Enum;

public enum IdTypeEnum {
/*
	0军官证：officer_certificate
	1身份证：idNo
	2营业执照：business_license
	3其他：other
	4临时身份证:temporary_idNo(===删除===)
	5入境证:entry_permit
	6组织机构代码证:organization_code_certificate
	7.护照:passport
*/
	certificateCard("军官证","0"),idCard("身份证","1"),businessLicence("营业执照","2"),
	other("其他","3"),arrivalCard("入境证","5"),driverLicense("驾驶证","6"),organizationCode("组织机构代码证","7")
	,passport("护照","8"),creditCode("信用代码证","9"),hkAndMacaoPassCard("港澳居民来往内地通行证","10"),
	TaiwanPassCard("台湾居民来往大陆通行证","11"),policeCard("武警警官身份证","12");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private IdTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (IdTypeEnum c : IdTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
    public static String getValue(String name) {
        for (IdTypeEnum c : IdTypeEnum.values()) {
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
    IdTypeEnum(String value) {
		this.value = value;
	}
	
	public static IdTypeEnum getIdTypeEnum(String value) {
		IdTypeEnum idTypeEnum = null;
		for (IdTypeEnum tempEnum : IdTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				idTypeEnum = tempEnum;
				break;
			}
		}
		return idTypeEnum;
	}
}
