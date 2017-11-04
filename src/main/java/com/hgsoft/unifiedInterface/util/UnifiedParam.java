package com.hgsoft.unifiedInterface.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.entity.AcctollcollectList;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;

public class UnifiedParam {
	
//	public static final String CORRECT = "correct";
//	public static final String UPDATE = "update";
//	public static final String RECHARGET = "recharge";
//	public static final String TRANFERRECHARGET = "tranferRecharge";
	
	//充值总金额
	private BigDecimal addCount;

	//类型
	private String type;
	
	//账户
	private MainAccountInfo mainAccountInfo;
	private MainAccountInfo beCombinedMainAccountInfo;//账户合并：被合并的主账户
	
	//缴款
	private RechargeInfo newRechargeInfo;
	
	//冲正
	private RechargeInfo oldRechargeInfo;
	
	//原电子标签
	private TagInfo tagInfo;
	
	//新电子标签
	private TagInfo newTagInfo;
	
	//车卡绑定
	private CarObuCardInfo carObuCardInfo;
	
	private RefundInfo refundInfo;
	
	private BankTransferInfo bankTransferInfo;
	
	//储值卡
	private PrepaidC prepaidC;
	//新储值卡
	private PrepaidC newPrepaidC;
	//客服流水
	private ServiceFlowRecord serviceFlowRecord;
	
	//记帐卡
	private AccountCInfo accountCInfo;
	//新记帐卡
	private AccountCInfo newAccountCInfo;
	//储值卡业务记录
	private PrepaidCBussiness prepaidCBussiness;
	//充值类型
	private Integer rechargeType;//1、人工充值2、快速充值
	
	private TagMainRecord tagMainRecord;
	//保证金账户信息
	private BailAccountInfo bailAccountInfo;
	//新增保证金金额
	private BigDecimal bailFee;
	//手工通行费交款
	private AcctollcollectList acctollcollectList;
	//记帐卡申请
	private AccountCApply accountCApply;
	//资金变动记录
	private AccountFundChange accountFundChange;
	//子账户
	private SubAccountInfo subAccountInfo;
	//变更金额
	private BigDecimal changePrice;
	//网点
	private Long placeId;
	//操作员
	private Long operId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	//标志位
	private String flag;
	//黑名单标志
	private boolean result;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	private List<RefundInfo> refundInfoList;
	
	private AccountCapplyHis accountCapplyHis;
	
	public AccountCapplyHis getAccountCapplyHis() {
		return accountCapplyHis;
	}
	public void setAccountCapplyHis(AccountCapplyHis accountCapplyHis) {
		this.accountCapplyHis = accountCapplyHis;
	}
	public List<RefundInfo> getRefundInfoList() {
		return refundInfoList;
	}
	public void setRefundInfoList(List<RefundInfo> refundInfoList) {
		this.refundInfoList = refundInfoList;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	//滞纳金和通行费
	private BigDecimal lateAndEtcFee;
	
	

	public BigDecimal getLateAndEtcFee() {
		return lateAndEtcFee;
	}

	public void setLateAndEtcFee(BigDecimal lateAndEtcFee) {
		this.lateAndEtcFee = lateAndEtcFee;
	}

	private PrepaidCBussiness newPrepaidCBussiness;
	
	public PrepaidCBussiness getNewPrepaidCBussiness() {
		return newPrepaidCBussiness;
	}

	public void setNewPrepaidCBussiness(PrepaidCBussiness newPrepaidCBussiness) {
		this.newPrepaidCBussiness = newPrepaidCBussiness;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public BigDecimal getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(BigDecimal changePrice) {
		this.changePrice = changePrice;
	}

	public SubAccountInfo getSubAccountInfo() {
		return subAccountInfo;
	}

	public void setSubAccountInfo(SubAccountInfo subAccountInfo) {
		this.subAccountInfo = subAccountInfo;
	}

	public AccountFundChange getAccountFundChange() {
		return accountFundChange;
	}

	public void setAccountFundChange(AccountFundChange accountFundChange) {
		this.accountFundChange = accountFundChange;
	}

	public AccountCApply getAccountCApply() {
		return accountCApply;
	}

	public void setAccountCApply(AccountCApply accountCApply) {
		this.accountCApply = accountCApply;
	}

	public AcctollcollectList getAcctollcollectList() {
		return acctollcollectList;
	}

	public void setAcctollcollectList(AcctollcollectList acctollcollectList) {
		this.acctollcollectList = acctollcollectList;
	}

	public BankTransferInfo getBankTransferInfo() {
		return bankTransferInfo;
	}

	public void setBankTransferInfo(BankTransferInfo bankTransferInfo) {
		this.bankTransferInfo = bankTransferInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MainAccountInfo getMainAccountInfo() {
		return mainAccountInfo;
	}

	public void setMainAccountInfo(MainAccountInfo mainAccountInfo) {
		this.mainAccountInfo = mainAccountInfo;
	}

	public RechargeInfo getNewRechargeInfo() {
		return newRechargeInfo;
	}

	public void setNewRechargeInfo(RechargeInfo newRechargeInfo) {
		this.newRechargeInfo = newRechargeInfo;
	}

	public RechargeInfo getOldRechargeInfo() {
		return oldRechargeInfo;
	}

	public void setOldRechargeInfo(RechargeInfo oldRechargeInfo) {
		this.oldRechargeInfo = oldRechargeInfo;
	}

	public RefundInfo getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(RefundInfo refundInfo) {
		this.refundInfo = refundInfo;
	}

	public TagInfo getTagInfo() {
		return tagInfo;
	}

	public void setTagInfo(TagInfo tagInfo) {
		this.tagInfo = tagInfo;
	}

	public TagInfo getNewTagInfo() {
		return newTagInfo;
	}

	public void setNewTagInfo(TagInfo newTagInfo) {
		this.newTagInfo = newTagInfo;
	}

	public CarObuCardInfo getCarObuCardInfo() {
		return carObuCardInfo;
	}

	public void setCarObuCardInfo(CarObuCardInfo carObuCardInfo) {
		this.carObuCardInfo = carObuCardInfo;
	}

	public PrepaidC getPrepaidC() {
		return prepaidC;
	}

	public void setPrepaidC(PrepaidC prepaidC) {
		this.prepaidC = prepaidC;
	}

	public ServiceFlowRecord getServiceFlowRecord() {
		return serviceFlowRecord;
	}

	public void setServiceFlowRecord(ServiceFlowRecord serviceFlowRecord) {
		this.serviceFlowRecord = serviceFlowRecord;
	}

	public PrepaidC getNewPrepaidC() {
		return newPrepaidC;
	}

	public void setNewPrepaidC(PrepaidC newPrepaidC) {
		this.newPrepaidC = newPrepaidC;
	}

	public BigDecimal getAddCount() {
		return addCount;
	}

	public void setAddCount(BigDecimal addCount) {
		this.addCount = addCount;
	}

	public PrepaidCBussiness getPrepaidCBussiness() {
		return prepaidCBussiness;
	}

	public void setPrepaidCBussiness(PrepaidCBussiness prepaidCBussiness) {
		this.prepaidCBussiness = prepaidCBussiness;
	}

	public TagMainRecord getTagMainRecord() {
		return tagMainRecord;
	}

	public void setTagMainRecord(TagMainRecord tagMainRecord) {
		this.tagMainRecord = tagMainRecord;
	}

	public AccountCInfo getAccountCInfo() {
		return accountCInfo;
	}

	public void setAccountCInfo(AccountCInfo accountCInfo) {
		this.accountCInfo = accountCInfo;
	}

	public AccountCInfo getNewAccountCInfo() {
		return newAccountCInfo;
	}

	public void setNewAccountCInfo(AccountCInfo newAccountCInfo) {
		this.newAccountCInfo = newAccountCInfo;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}

	public BailAccountInfo getBailAccountInfo() {
		return bailAccountInfo;
	}

	public void setBailAccountInfo(BailAccountInfo bailAccountInfo) {
		this.bailAccountInfo = bailAccountInfo;
	}

	public BigDecimal getBailFee() {
		return bailFee;
	}

	public void setBailFee(BigDecimal bailFee) {
		this.bailFee = bailFee;
	}
	public MainAccountInfo getBeCombinedMainAccountInfo() {
		return beCombinedMainAccountInfo;
	}
	public void setBeCombinedMainAccountInfo(MainAccountInfo beCombinedMainAccountInfo) {
		this.beCombinedMainAccountInfo = beCombinedMainAccountInfo;
	}
	
	
	
}
