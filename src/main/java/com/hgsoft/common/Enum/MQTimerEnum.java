package com.hgsoft.common.Enum;

public enum MQTimerEnum {
	ACISSUCE("记帐卡发行","91000"),
	ACLOSSINFO("记帐卡挂失","91001"),
	ACUNLOSS("记帐卡解挂","91002"),
	ACCANNEL("注销","91003"),
	ACSTOP("下发止付黑名单","91004"),
	ACUNSTOP("解除止付黑名单","91005"),
	VECHILECHANGE("卡片车牌变更","91006"),
	SERVERREGISTER("发票邮寄登记","91007"),
	TAGISSUCE("电子标签二发","91008"),
	TAGREPLACE("电子标签更换","91009"),
	TAGMIGRATE("电子标签迁移","91010"),
	SERVERCANNEL("发票邮寄取消","91011"),
	vehicleCheck("车牌校验","91012");
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private MQTimerEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (MQTimerEnum c : MQTimerEnum .values()) {
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
    MQTimerEnum(String value) {
		this.value = value;
	}
	
	public static MQTimerEnum getMigrateStateEnum(String value) {
		MQTimerEnum accountNCApplyEnum = null;
		for (MQTimerEnum tempEnum : MQTimerEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accountNCApplyEnum = tempEnum;
				break;
			}
		}
		return accountNCApplyEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (MQTimerEnum tempEnum : MQTimerEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }

}
