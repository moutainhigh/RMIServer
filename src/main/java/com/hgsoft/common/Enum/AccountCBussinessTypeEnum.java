package com.hgsoft.common.Enum;

public enum AccountCBussinessTypeEnum {
	accCardIssue("记帐卡发行","1"),
	accCardBaidAdd("保证金收款","2"),
	accCardLoss("记帐卡挂失","3"),
	accCardCanceLoss("记帐卡解挂","4"),
	accCardReplace("记帐卡补领","5"),
	accCardLock("记帐卡旧卡锁定","6"),
	accCardReplaceNewcard("记帐卡换卡","7"),
	accCardStopWithCard("记帐卡有卡终止使用","8"),
	accCardStopNotCard("记帐卡无卡终止使用","9"),
	accCardApplyCancelStopPay("申请解除止付","10"),
	accCardHandCancelStopPay("手工解除止付黑名单","11"),
	accCardMemberRechargeTollFee("手工缴纳通行费","12"),
	accCardEdPassWord("记帐卡消费密码修改","13"),
	accCardNewPassword("记帐卡消费密码重设","14"),
	accCardStop("记帐卡挂起","15"),
	accCardStart("记帐卡解除挂起","16"),
	accNameCha("记帐卡账户名称变更","17"),
	accCardMigrate("扣款账户变更","18"),
	accCardTransfer ("记帐卡过户","19"),
	accCardInitialApply("银行账户申请","20"),
	accNewCardApply("记帐卡申请","21"),
	accCardInitialApplyAudit("初次申请审核","22"),
	accNewCardApplyAudit("新增卡申请审核","23"),
	accNameChange("记帐卡账户名称变更","24"),
	accCardBailBack("保证金退还","25"),
	accCardInitialApplyUpdate("银行账户申请修改","26"),
	accNewCardApplyUpdate("新增卡申请","27"),
	accServicePwdUpdate("服务密码修改","28"),
	accServicePwdReset("服务密码重置","29"),
	accBailBackCancel("保证金退还撤销","30");
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountCBussinessTypeEnum(String name, String value) {
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
    AccountCBussinessTypeEnum(String value) {
		this.value = value;
	}
	
	public static AccountCBussinessTypeEnum getAccountCBussinessTypeEnum(String value) {
		AccountCBussinessTypeEnum accountCBussinessTypeEnum = null;
		for (AccountCBussinessTypeEnum tempEnum : AccountCBussinessTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accountCBussinessTypeEnum = tempEnum;
				break;
			}
		}
		return accountCBussinessTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccountCBussinessTypeEnum tempEnum : AccountCBussinessTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
