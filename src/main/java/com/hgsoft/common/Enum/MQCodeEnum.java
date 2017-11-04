package com.hgsoft.common.Enum;

/**
 * 交易码类
 * @author gaosiling
 * 2016年8月20日 14:25:40
 * PAY ATTENTION:
 * 1：英文名字长度切忽过长，需要保存数据库 
 * 2：此为4参枚举
 * 		第一位中文名称
 * 		第二位交易码
 * 		第三位接口名称
 * 		第四位交易类型
 */
public enum MQCodeEnum {
	//名字长度切忽过长 ，此为4参枚举，第一位中文名称   第二位交易码  第三位接口名称 第四位交易类型
	
	RECHARGE_OFFLINE("充值下限查询","9005","rechargeOffline","03"),
    RETURN_MONENY("回退金额","96040","returnMoney","02"),
	RECHARGE_APPLY("充值申请","8999","rechargeApply","04"),
	RECHARGE_CONFIRM("充值确认","9001","rechargeConfirm","05"),
	HALF_CONFIRM("半条确认","8023","halfConfirm","07"),
    CUSTOMER_QUERY("客户信息查询接口","2150","customerQuery","18"),
    PRECARD_QUERY("储值卡查询接口","96024","precardQuery","09"),
    ACCARD_QUERY("记帐卡查询接口","96023","accardQuery","08"),
    IDCARD_QUERY("证件类型、证件号码查询卡接口","96025","idcardQuery","10"),
    ONEDAY_RECONCILIATION_FLOW("一天对账流水查询","96029","onedayReconciliationQuery","14"),
    CARDNO_RECONCILIATION_FLOW("通过卡号查询对账流水","96042","cardNoReconciliationQuery","15");
	//交易码 
	private String value;

	//中文名称
	private String name;
	
	//接口名称
	private String interfaceName;
	
	//交易类型
	private String type;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private MQCodeEnum(String name, String value, String interfaceName, String type) {
        this.name = name;
        this.value = value;
        this.interfaceName = interfaceName;
        this.type = type;
    }

    // 普通方法
    public static String getName(String value) {
        for (MQCodeEnum c : MQCodeEnum.values()) {
            if (c.getValue() .equals(value)) {
                return c.name;
            }
        }
        return "";
    }
    
    public static String getValue(String name) {
        for (MQCodeEnum c : MQCodeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.value;
            }
        }
        return "";
    }
    
    public static String getInterfaceName(String value) {
        for (MQCodeEnum c : MQCodeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.interfaceName;
            }
        }
        return "";
    }
    
    public static String getType(String value) {
        for (MQCodeEnum c : MQCodeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.type;
            }
        }
        return "";
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
    MQCodeEnum(String value) {
		this.value = value;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
