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
 * 		第四位交易类型 交易类型为交易码最后两位
 */
public enum ServiceFlowEnum {
	operatorLogin("操作员登录","009200","operatorLogin","00"),
	operatorLogout("操作员登出","009201","operatorLogout","01"),
	operatorKeyChange("操作员密钥变更","009202","operatorKeyChange","01"),
	operator3DesChange("3DES密钥变更","009214","operator3DesChange","14"),
	
	//通用业务
	vehicleInfoLock("车辆信息锁定","009203","vehicleInfoLock","03"),
	vehicleInfoUnlock("车辆信息解锁","009204","vehicleInfoUnlock","04"),
	licenseInfoQuery("车牌信息查询","009218","licenseInfoQuery","18"),
	cardLicenseInfoQuery("卡片车牌信息查询","009209","cardLicenseInfoQuery","09"),
	flowQuery("流水查询","009212","flowQuery","12"),
	bingFlagAndTollcar("取绑定标识和国标收费车型","009229","bingFlagAndTollcar","29"),
	writeLicenseApplication("写入车牌申请（制卡用）","009230","writeLicenseApplication","30"),
	writeLicenseConfirm("写入车牌确认（制卡用）","009231","writeLicenseConfirm","31"),
	deleteLicenseApplication("删除车牌申请（制卡用）","009232","deleteLicenseApplication","32"),
	deleteLicenseConfirm("删除车牌确认（制卡用）","009233","deleteLicenseConfirm","33"),
	cardStateQuery("卡片状态查询","009213","cardStateQuery","13"),
	
	accountCNCReq("开户申请","009100","accountCNCReq","01"),
	accountCNCSure("开户确认","009101","accountCNCSure","02"),
	accountCModify("资料变更","009102","accountCModify","03"),
	accountCLoss("挂失","009103","accountCLoss","04"),
	accountCGainCardNoCardReq("无卡换卡申请","009104","accountCGainCardNoCardReq","05"),
	accountCGainCardNoCardSure("无卡换卡确认","009105","accountCGainCardNoCardSure","06"),
	accountCRepCardReq("补领申请","009106","accountCRepCardReq","07"),
	accountCRepCardSure("补领确认","009107","accountCRepCardSure","08"),
	accountCCancle("注销","009108","accountCCancle","09"),
	accountCAddDark("黑名单下发","009109","accountCAddDark","10"),
	accountCRemoveDark("黑名单解除","009110","accountCRemoveDark","11"),
	accountCRelaInit("关联对应初始化","009117","accountCRelaInit","17"),
	accountCRestart("销卡后重启","009111","accountCRestart","13"),
	accountCUnLoss("解挂","009112","accountCUnLoss","14"),
	accountCRepCardNT("非过户补领","009113","accountCRepCardNT","16"),
	accountCMigReq("过户申请","009114","accountCMigReq","14"),
	accountCMigSure("过户确认","009115","accountCMigSure","15"),
	accountCValidateDark("黑名单校验","009119","accountCValidateDark","19");
	
	//交易码 
	private String value;

	//中文名称
	private String name;
	
	//接口名称
	private String interfaceName;
	
	//交易类型
	private String type;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ServiceFlowEnum(String name, String value,String interfaceName,String type) {
        this.name = name;
        this.value = value;
        this.interfaceName = interfaceName;
        this.type = type;
    }

    // 普通方法
    public static String getName(String value) {
        for (ServiceFlowEnum c : ServiceFlowEnum .values()) {
            if (c.getValue() .equals(value)) {
                return c.name;
            }
        }
        return "";
    }
    
    public static String getValue(String name) {
        for (ServiceFlowEnum c : ServiceFlowEnum.values()) {
            if (c.getName().equals(name)) {
                return c.value;
            }
        }
        return "";
    }
    
    public static String getInterfaceName(String value) {
        for (ServiceFlowEnum c : ServiceFlowEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.interfaceName;
            }
        }
        return "";
    }
    
    public static String getType(String value) {
        for (ServiceFlowEnum c : ServiceFlowEnum.values()) {
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
    ServiceFlowEnum(String value) {
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
