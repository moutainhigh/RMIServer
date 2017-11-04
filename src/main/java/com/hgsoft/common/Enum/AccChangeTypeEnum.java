package com.hgsoft.common.Enum;

public enum AccChangeTypeEnum {
	correct("冲正","0"),
	recharge("缴款","1"),
	modify("修改","2"),
	tranferRecharge("转账缴款","3"),
	refund("退款","4"),
	refundRevoke("退款撤销","5"),
	tagIssue("电子标签发行扣费","6"),
	tagChange("电子标签更换扣费","7"),
	tagDelete("电子标签删除工本费返回","8"),
	preCardGetNewCard("领取新卡扣减储值卡卡费","9"),
	rechargeRegister("充值登记","10"),
	preCardIssue("储值卡发行","11"),
	preCardDelete("删除储值卡发行","12"),
	preCardRecharge("储值卡人工充值","13"),
	preCardRechargeSuccess("储值卡充值成功","14"),
	preCardRechargeCorrect("储值卡充值冲正","15"),
	accCardGetNewCard("领取新卡扣减记帐卡卡费","16"),
	accCardBaidAdd("保证金新增","17"),
	preCardRechargeCorrectSuccess("储值卡充值冲正成功","18"),
	accCardMemberRechargeTollFee("手工缴纳通行费","19"),
	accCardMemberRechargeLateFee("手动解除止付扣除滞纳金","20"),
	accCardIssue("记帐卡发行","21"),
	preCardReplaceMent("补领卡扣减储值卡卡费","22"),
	accCardReplaceMent("补领卡扣减记帐卡卡费","23"),
	tagDaySetIssuce("日结前资金修正-电子标签发行","24"),
	imRechargeHalfSuccess("快速充值半条确认成功","25"),
	imRechargeHalfFail("快速充值半条确认失败","26"),
	memberRechargeHalfSuccess("充值半条确认成功","27"),
	memberRechargeHalfFail("充值半条确认失败","28"),
	rechargeRegisterHalfSuccess("充值冲正半条确认成功","29"),
	rechargeRegisterHalfFail("充值冲正半条确认失败","30"),
	revokeRechargeRegister("撤销充值登记","31"),
	bailRefundReq("保证金退还申请","32"),
	bailRefundReqCancel("保证金退还撤销","33"),
	operationReview("营运退款审核","34"),
	financeRefund("财务退款","35"),
	financeRefundFail("财务退款失败","36"),
	imRecharge("直充充值缴款","37"),
	imCorrect("直充充值冲正","38"),
	tagDaySetChange("日结前资金修正-电子标签更换","39"),
	prePaidSetIssuce("日结前资金修正-储值卡发行","40"),
	prePaidSetChange("日结前资金修正-储值卡换卡","41"),
	prePaidSetReplace("日结前资金修正-储值卡补领","42"),
	accountCSetIssuce("日结前资金修正-记帐卡发行","43"),
	accountCSetChange("日结前资金修正-记帐卡换卡","44"),
	accountCSetReplace("日结前资金修正-记帐卡不领","45"),
	prepaidCStopCard("储值卡终止使用","46"),
	specialCost("特殊费用收取","47"),
	chaseMoney("追款管理收取","48"),
	preCardStopRefund("储值卡终止使用首次退款申请","49"),
	accountCombine("账户合并","50"),
	deletePrepaidCAddReg("充值登记记录删除","51"),
	bailToAvailableBalance("保证金流入主账户可用余额","52"),
	bailRefundOmsAppFail("保证金退款营运审批失败","53");
	
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccChangeTypeEnum(String name, String value) {
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
    AccChangeTypeEnum(String value) {
		this.value = value;
	}
	
	public static AccChangeTypeEnum getAccChangeTypeEnum(String value) {
		AccChangeTypeEnum accChangeTypeEnum = null;
		for (AccChangeTypeEnum tempEnum : AccChangeTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (AccChangeTypeEnum tempEnum : AccChangeTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
