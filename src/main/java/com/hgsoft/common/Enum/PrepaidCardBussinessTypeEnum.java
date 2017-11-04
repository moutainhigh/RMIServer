package com.hgsoft.common.Enum;

public enum PrepaidCardBussinessTypeEnum {
	preCardIssuce("储值卡发行","1"),
	preCardMemberRecharge("储值卡人工充值","2"),
	preCardRechargeRegister("储值卡充值登记充值","3"),
	preCardRechargeCorrect("储值卡充值冲正","4"),
	preCardPasswordModify("储值卡消费密码修改","5"),
	preCardPasswordReset("储值卡消费密码重设","6"),
	preCardLoss("储值卡挂失","7"),
	preCardUnLoss("储值卡解挂","8"),
	preCardReplaceMent("储值卡补领","9"),
	preCardLoking("储值卡旧卡锁定","10"),
	preCardGetNewCard("储值卡换卡","11"),
	preCardCannel("储值卡有卡终止使用","12"),
	preCardNoCardCannel("储值卡无卡终止使用","13"),
	preCardDisabled("储值卡挂起","14"),
	preCardAbled("储值卡解除挂起","15"),
	preCardDelete("储值卡发行删除","16"),
	preCardInvoiceTypeChange("储值卡发票类型变更","17"),
	preCardTranfer("储值卡过户","18"),
	preCardImRegister("储值卡直充充值","19"),
	preCardImRegisterCorrect("储值卡直充充值冲正","20"),
	preCardStopRefund("储值卡终止使用首次退款申请","31"),
	addRegWithHand("储值卡手工录入充值登记","32"),
	addRegBatchInput("储值卡批量导入充值登记","33"),
	preCardIssuceUpdate("储值卡发行修改","34"),
	addRegUpdate("储值卡充值登记修改","35"),
	addRegDelete("储值卡充值登记删除","36"),
	airRecharge("空中充值","94");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private PrepaidCardBussinessTypeEnum(String name, String value) {
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
    PrepaidCardBussinessTypeEnum(String value) {
		this.value = value;
	}
	
	public static PrepaidCardBussinessTypeEnum getPrepaidCardBussinessTypeEnum(String value) {
		PrepaidCardBussinessTypeEnum preCardBussinessTypeEnum = null;
		for (PrepaidCardBussinessTypeEnum tempEnum : PrepaidCardBussinessTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				preCardBussinessTypeEnum = tempEnum;
				break;
			}
		}
		return preCardBussinessTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (PrepaidCardBussinessTypeEnum tempEnum : PrepaidCardBussinessTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
	}
}
