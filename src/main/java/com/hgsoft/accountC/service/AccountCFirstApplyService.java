package com.hgsoft.accountC.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.utils.ReceiptUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCApplyHisDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.serviceInterface.IAccountCFirstApplyService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCShutDownStatusEnum;
import com.hgsoft.common.Enum.AccountTypeTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.SerItemEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.BillGetHisDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardInitialApplyReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardInitialApplyUpdateReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONObject;

@Service
public class AccountCFirstApplyService implements IAccountCFirstApplyService {
	@Resource
	private AccountCApplyDao accountCApplyDao;
	
	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	
	@Resource
	private AccountCApplyHisDao accountCApplyHisDao;
	
	@Resource
	private BillGetDao billGetDao;
	
	@Resource
	private ReceiptDao receiptDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	@Resource
	private BillGetHisDao billGetHisDao;
	

	@Resource
	private SequenceUtil sequenceUtil;

	private static Logger logger = Logger.getLogger(AccountCFirstApplyService.class.getName());

	public Pager findAccountCFirstApplyList(Pager pager, Customer customer, String bankAccount) {

		pager = accountCApplyDao.findAccountCFirstApplys(pager, customer, bankAccount);

		return pager;
	}

	/**
	 * 记帐卡初次申请信息保存
	 */
	@Override
	public void saveAccountCFirstApply(Customer customer, AccountCApply accountCApply, BillGet billGet) {
		try {
			// 增加记帐卡申请记录
			// 初次申请，新建一条保证金账户信息(首先要判断数据库中是否有该客户的保证金账户信息，如果有那就不用增加)
			BailAccountInfo oldBailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			if(oldBailAccountInfo == null){
				BailAccountInfo bailAccountInfo = new BailAccountInfo();
				BigDecimal SEQ_CSMSBailAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfo_NO");
				bailAccountInfo.setId(Long.valueOf(SEQ_CSMSBailAccountInfo_NO.toString()));
				bailAccountInfo.setMainId(customer.getId());
				bailAccountInfo.setBailFee(new BigDecimal("0"));//保证金金额
				bailAccountInfo.setOperId(accountCApply.getOperId());
				bailAccountInfo.setPlaceId(accountCApply.getPlaceId());
				//新增的字段
				bailAccountInfo.setOperName(accountCApply.getOperName());
				bailAccountInfo.setOperNo(accountCApply.getOperNo());
				bailAccountInfo.setPlaceName(accountCApply.getPlaceName());
				bailAccountInfo.setPlaceNo(accountCApply.getPlaceNo());
				
				bailAccountInfo.setOperTime(new Date());
				bailAccountInfo.setBailFrozenBalance(new BigDecimal("0"));//保证金冻结金额

				bailAccountInfoDao.saveBailAccount(bailAccountInfo);
			}

			// 增加记帐卡申请记录
			BigDecimal SEQ_CSMSAccountCapply_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCapply_NO");
			accountCApply.setId(Long.valueOf(SEQ_CSMSAccountCapply_NO.toString()));
			accountCApply.setBail(new BigDecimal("0")); //客车单卡保证金
			accountCApply.setTruckBail(new BigDecimal("0"));//卡车单卡保证金
			
			accountCApply.setCustomerId(customer.getId());
			// TODO: 2017/4/14 这里的申请数量和剩余数量暂时没用
			accountCApply.setReqcount(0l);
			accountCApply.setResidueCount(0l);
//			accountCApply.setResidueCount(accountCApply.getReqcount());// 剩余数量
			accountCApply.setAppState("0");
			// 这里的新增卡申请标识设置为否是用来判断是否已做新增卡申请
			accountCApply.setNewCardFlag("0");
			accountCApply.setOperTime(new Date());


			// 增加客户记帐卡账户（子账户信息）
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findMainAccountInfoByCustomerID(customer.getId());
			SubAccountInfo subAccountInfo = new SubAccountInfo();
			BigDecimal SEQ_CSMSSubAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSSubAccountInfo_NO");
			subAccountInfo.setId(Long.valueOf(SEQ_CSMSSubAccountInfo_NO.toString()));
			subAccountInfo.setMainId(mainAccountInfo.getId());
			
			SubAccountInfo sub = subAccountInfoDao.findLastDateSub(customer.getId());
			subAccountInfo.setSubAccountNo(customer.getUserNo() + "2" + StringUtil.getSerailNumber(sub));// 账户号=客户号+类型+序号
			subAccountInfo.setSubAccountType("2");
			subAccountInfo.setApplyID(accountCApply.getId());
			subAccountInfo.setOperId(accountCApply.getOperId());
			subAccountInfo.setPlaceId(accountCApply.getPlaceId());
			//新增的字段
			subAccountInfo.setOperName(accountCApply.getOperName());
			subAccountInfo.setOperNo(accountCApply.getOperNo());
			subAccountInfo.setPlaceName(accountCApply.getPlaceName());
			subAccountInfo.setPlaceNo(accountCApply.getPlaceNo());
			subAccountInfo.setOperTime(new Date());
			accountCApply.setSubAccountNo(subAccountInfo.getSubAccountNo());

			subAccountInfo.setBailBalance(BigDecimal.ZERO);
			subAccountInfo.setBailFee(BigDecimal.ZERO);
			subAccountInfo.setBailFrozenBalance(BigDecimal.ZERO);
			//强制结束状态
			accountCApply.setShutDownStatus(AccountCShutDownStatusEnum.start.getValue());

			accountCApplyDao.saveAccountCApply(accountCApply);
			subAccountInfoDao.save(subAccountInfo);
			
			//增加服务登记表信息
			if(billGet!=null){
				BigDecimal SEQ_CSMS_bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				billGet.setId(Long.valueOf(SEQ_CSMS_bill_get_NO.toString()));
				billGet.setMainId(customer.getId());
				billGet.setCardType("2");//卡类型设为“2”，表示记帐卡
				billGet.setCardAccountID(subAccountInfo.getId());
				billGet.setCardBankNo(accountCApply.getBankAccount());
//				billGet.setOperId(accountCApply.getOperId());//操作员取申请操作员
//				billGet.setPlaceId(accountCApply.getPlaceId());//网点取申请网点
				//服务类型，服务开始时间，服务结束时间暂无
				billGetDao.save(billGet);
			}

			// 记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
			accountCBussiness.setUserId(customer.getId());
			accountCBussiness.setAccountId(subAccountInfo.getId());
			accountCBussiness.setState("20");
			accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setOperId(accountCApply.getOperId());
			accountCBussiness.setPlaceId(accountCApply.getPlaceId());
			//新增的字段
			accountCBussiness.setOperName(accountCApply.getOperName());
			accountCBussiness.setOperNo(accountCApply.getOperNo());
			accountCBussiness.setPlaceName(accountCApply.getPlaceName());
			accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
			accountCBussiness.setBusinessId(accountCApply.getHisseqId());
			accountCBussinessDao.save(accountCBussiness);
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("211");//211记帐卡初次申请
			serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡初次申请");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"记帐卡初次申请失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	
	public void updateAccountCFirstApply(Customer customer,AccountCApply accountCApply, BillGet billGet, Map<String,Object> params){
		try {
			//1、将记帐卡申请信息移到历史表
			AccountCapplyHis accountCapplyHis = new AccountCapplyHis();
			BigDecimal SEQ_CSMSAccountCapplyhis_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCapplyhis_NO");
			accountCapplyHis.setId(Long.valueOf(SEQ_CSMSAccountCapplyhis_NO.toString()));
			accountCapplyHis.setGenReason("1");
			accountCApplyHisDao.saveHis(accountCapplyHis, accountCApply);
			//accountCapplyHis.setGenTime(genTime);
			
			//2、修改记帐卡申请信息表，要把历史序列id也update进去
			accountCApply.setHisseqId(accountCapplyHis.getId());
			AccountCApply oldAccountCApply = accountCApplyDao.findById(accountCApply.getId());
			accountCApplyDao.update4NotNullStr(accountCApply);
			
			//判断银行账号是否修改，若修改了，也要修改服务方式登记表的银行账号
			boolean shouldUpdateBillGet = false;
			BillGet oldbillGet = billGetDao.findByCardBankNo(oldAccountCApply.getBankAccount());
			if(!oldAccountCApply.getBankAccount().equals(accountCApply.getBankAccount())){
				if(oldbillGet != null){
					oldbillGet.setCardBankNo(accountCApply.getBankAccount());
					shouldUpdateBillGet = true;
				}
			}
			
			//判断服务方式是否改变了，若改变了，update
			if(billGet != null && oldbillGet != null && StringUtil.isNotBlank(billGet.getSerItem())){
				String[] newSerItem = billGet.getSerItem().split(",");
				String[] oldSerItem = oldbillGet.getSerItem().split(",");
				//判断是否有修改服务方式
				if(!StringUtil.checkDifferentArray(newSerItem, oldSerItem)){
					oldbillGet.setSerItem(billGet.getSerItem());
					shouldUpdateBillGet = true;
				}
			}
			
			if(shouldUpdateBillGet){
				//保存历史表
				Date date = new Date();
				Long seq = sequenceUtil.getSequenceLong("SEQ_CSMS_bill_get_his_NO");
				BillGetHis billGetHis = new BillGetHis(null, date, "1", oldbillGet.getMainId(), oldbillGet.getSerItem(), 
						oldbillGet.getSerType(), oldbillGet.getBegTime(), oldbillGet.getEndTime(), oldbillGet.getOperId(), 
						oldbillGet.getPlaceId(), oldbillGet.getHisSeqId());
				billGetHis.setId(seq);
				billGetHis.setCardType(oldbillGet.getCardType());
				billGetHis.setCardAccountID(oldbillGet.getCardAccountID());
				billGetHis.setCardBankNo(oldbillGet.getCardBankNo());
				billGetHis.setOperTime(oldbillGet.getOperTime());
				billGetHisDao.save(billGetHis);
				
				oldbillGet.setHisSeqId(billGetHis.getId());
				billGetDao.update(oldbillGet);
			}
			
			SubAccountInfo subAccountInfo  = subAccountInfoDao.findByApplyId(accountCApply.getId());
			
			//3、记录业务记录表
			// 记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
			accountCBussiness.setUserId(customer.getId());
			accountCBussiness.setAccountId(subAccountInfo.getId());
			accountCBussiness.setState("26");//TODO 暂时定为记帐卡初次申请修改
			accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setOperId(accountCApply.getOperId());
			accountCBussiness.setPlaceId(accountCApply.getPlaceId());
			//新增的字段
			accountCBussiness.setOperName(accountCApply.getOperName());
			accountCBussiness.setOperNo(accountCApply.getOperNo());
			accountCBussiness.setPlaceName(accountCApply.getPlaceName());
			accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
			accountCBussiness.setBusinessId(accountCApply.getHisseqId());
			//receiptDao.saveByBussiness(null, null, null, null, accountCBussiness);
			accountCBussinessDao.save(accountCBussiness);
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("212");//212记帐卡初次申请修改
			serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			serviceWater.setBankNo(oldAccountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡初次申请修改");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//银行账号修改申请回执
			AccCardInitialApplyUpdateReceipt accCardInitialApplyUpdateReceipt = new AccCardInitialApplyUpdateReceipt();
			accCardInitialApplyUpdateReceipt.setTitle("银行账号修改申请回执");
			accCardInitialApplyUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			accCardInitialApplyUpdateReceipt.setApplyAccountType(AccountTypeTypeEnum.getNameByValue(accountCApply.getAccountType()));
			accCardInitialApplyUpdateReceipt.setApplyLinkman(accountCApply.getLinkman());
			accCardInitialApplyUpdateReceipt.setApplyTel(accountCApply.getTel());
			accCardInitialApplyUpdateReceipt.setApplyAccName(accountCApply.getAccName());
			accCardInitialApplyUpdateReceipt.setApplyBankAccount(accountCApply.getBankAccount());
			accCardInitialApplyUpdateReceipt.setApplyBankName(accountCApply.getBank());
			accCardInitialApplyUpdateReceipt.setSerItem(this.getSerItemName(billGet.getSerItem()));
			Receipt receipt = new Receipt();
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardInitialApplyUpdate.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardInitialApplyUpdate.getName());
			this.saveReceipt(receipt,accountCBussiness,accCardInitialApplyUpdateReceipt,customer);
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"修改记帐卡初次申请信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 判断记帐卡的开户银行帐号是否已经申请过记帐卡，如果申请过，返回true
	 * 
	 * @param accountCApply
	 * @return
	 */
	public boolean sureIsApplied(AccountCApply accountCApply) {
		boolean result = false;

		AccountCApply ACApply = null;
		try {
			ACApply = accountCApplyDao.findByBankAccount(accountCApply.getBankAccount());
		} catch (Exception e) {
			logger.error(e.getMessage()+"断记帐卡的开户银行帐号是否已经申请过记帐卡失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		if (ACApply != null) {
			result = true;
		}

		return result;
	}

	public AccountCApply findAccountCApplyById(Long accountCApplyId){
		try {
			return accountCApplyDao.findById(accountCApplyId);
		} catch (Exception e) {
			logger.error(e.getMessage()+"查找记帐卡申请表信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	public BillGet findBillGetByAccountCApply(AccountCApply accountCApply){
		try {
			return accountCApplyDao.findBillGetByAccountCApply(accountCApply);
		} catch (Exception e) {
			logger.error(e.getMessage()+"查找保证金账户信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	public AccountCApply findAccountCApply(AccountCApply accountCApply){
		try {
			return accountCApplyDao.find(accountCApply);
		} catch (Exception e) {
			logger.error(e.getMessage()+"查找记帐卡表申请信息信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * @author gaosiling
	 * @meno update shut down status,the accountCApply unable
	 * @time 2016-07-23 15:21:06
	 * @param accountCApply
	 */
	@Override
	public void updateShutDownStatus(AccountCApply accountCApply){
		try {
			//1、将记帐卡申请信息移到历史表
			AccountCapplyHis accountCapplyHis = new AccountCapplyHis();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
			accountCapplyHis.setId(seq);
			accountCapplyHis.setGenReason("1");
			accountCApplyHisDao.saveHis(accountCapplyHis, accountCApply);
			accountCApply.setHisseqId(seq);
			accountCApplyDao.updateShutDownStatus(accountCApply);
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"修改记帐卡初次申请信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	@Override
	public void update(AccountCApply accountCApply){
		try {
			//1、将记帐卡申请信息移到历史表
			accountCApplyDao.update(accountCApply);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"修改记帐卡初次申请信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/*
	 * 返回Map的初次申请保存
	 * (non-Javadoc)
	 * @see com.hgsoft.accountC.serviceInterface.IAccountCFirstApplyService#saveAccountCFirstApplyRutrnMap(com.hgsoft.customer.entity.Customer, com.hgsoft.accountC.entity.AccountCApply, com.hgsoft.customer.entity.BillGet)
	 */
	@Override
	public Map<String, Object> saveAccountCFirstApplyReturnMap(Customer customer, AccountCApply accountCApply,BillGet billGet,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			// 增加记帐卡申请记录
			// 初次申请，新建一条保证金账户信息(首先要判断数据库中是否有该客户的保证金账户信息，如果有那就不用增加)
			BailAccountInfo oldBailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			if(oldBailAccountInfo == null){
				BailAccountInfo bailAccountInfo = new BailAccountInfo();
				BigDecimal SEQ_CSMSBailAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfo_NO");
				bailAccountInfo.setId(Long.valueOf(SEQ_CSMSBailAccountInfo_NO.toString()));
				bailAccountInfo.setMainId(customer.getId());
				bailAccountInfo.setBailFee(new BigDecimal("0"));//保证金金额
				bailAccountInfo.setOperId(accountCApply.getOperId());
				bailAccountInfo.setPlaceId(accountCApply.getPlaceId());
				//新增的字段
				bailAccountInfo.setOperName(accountCApply.getOperName());
				bailAccountInfo.setOperNo(accountCApply.getOperNo());
				bailAccountInfo.setPlaceName(accountCApply.getPlaceName());
				bailAccountInfo.setPlaceNo(accountCApply.getPlaceNo());
				
				bailAccountInfo.setOperTime(new Date());
				bailAccountInfo.setBailFrozenBalance(new BigDecimal("0"));//保证金冻结金额

				bailAccountInfoDao.saveBailAccount(bailAccountInfo);
			}

			// 增加记帐卡申请记录
			BigDecimal SEQ_CSMSAccountCapply_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCapply_NO");
			accountCApply.setId(Long.valueOf(SEQ_CSMSAccountCapply_NO.toString()));
			accountCApply.setBail(new BigDecimal("0")); //客车单卡保证金
			accountCApply.setTruckBail(new BigDecimal("0"));//卡车单卡保证金
			
			accountCApply.setCustomerId(customer.getId());
			// TODO: 2017/4/14 这里的申请数量和剩余数量暂时没用
			accountCApply.setReqcount(0l);
			accountCApply.setResidueCount(0l);
//			accountCApply.setResidueCount(accountCApply.getReqcount());// 剩余数量
			accountCApply.setAppState("0");
			// 这里的新增卡申请标识设置为否是用来判断是否已做新增卡申请
			accountCApply.setNewCardFlag("0");
			accountCApply.setOperTime(new Date());
			accountCApply.setApplyTime(new Date());


			// 增加客户记帐卡账户（子账户信息）
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findMainAccountInfoByCustomerID(customer.getId());
			SubAccountInfo subAccountInfo = new SubAccountInfo();
			BigDecimal SEQ_CSMSSubAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSSubAccountInfo_NO");
			subAccountInfo.setId(Long.valueOf(SEQ_CSMSSubAccountInfo_NO.toString()));
			subAccountInfo.setMainId(mainAccountInfo.getId());
			
			SubAccountInfo sub = subAccountInfoDao.findLastDateSub(customer.getId());
			subAccountInfo.setSubAccountNo(customer.getUserNo() + "2" + StringUtil.getSerailNumber(sub));// 账户号=客户号+类型+序号
			subAccountInfo.setSubAccountType("2");
			subAccountInfo.setApplyID(accountCApply.getId());
			subAccountInfo.setOperId(accountCApply.getOperId());
			subAccountInfo.setPlaceId(accountCApply.getPlaceId());
			//新增的字段
			subAccountInfo.setOperName(accountCApply.getOperName());
			subAccountInfo.setOperNo(accountCApply.getOperNo());
			subAccountInfo.setPlaceName(accountCApply.getPlaceName());
			subAccountInfo.setPlaceNo(accountCApply.getPlaceNo());
			
			subAccountInfo.setOperTime(new Date());
			
			accountCApply.setSubAccountNo(subAccountInfo.getSubAccountNo());
			//强制结束状态
			accountCApply.setShutDownStatus(AccountCShutDownStatusEnum.start.getValue());
			accountCApply.setDebitCardType(0);//标识银行账号是自营系统申请的

			subAccountInfo.setBailBalance(BigDecimal.ZERO);
			subAccountInfo.setBailFee(BigDecimal.ZERO);
			subAccountInfo.setBailFrozenBalance(BigDecimal.ZERO);

			accountCApplyDao.saveAccountCApply(accountCApply);
			subAccountInfoDao.save(subAccountInfo);
			
			//增加服务登记表信息
			if(billGet!=null){
				BigDecimal SEQ_CSMS_bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				billGet.setId(Long.valueOf(SEQ_CSMS_bill_get_NO.toString()));
				billGet.setMainId(customer.getId());
				billGet.setCardType("2");//卡类型设为“2”，表示记帐卡
				billGet.setCardAccountID(subAccountInfo.getId());
				billGet.setCardBankNo(accountCApply.getBankAccount());
//				billGet.setOperId(accountCApply.getOperId());//操作员取申请操作员
//				billGet.setPlaceId(accountCApply.getPlaceId());//网点取申请网点
				//服务类型，服务开始时间，服务结束时间暂无
				billGetDao.save(billGet);
			}

			// 记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
			accountCBussiness.setUserId(customer.getId());
			accountCBussiness.setAccountId(subAccountInfo.getId());
			accountCBussiness.setState("20");
			accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setOperId(accountCApply.getOperId());
			accountCBussiness.setPlaceId(accountCApply.getPlaceId());
			//新增的字段
			accountCBussiness.setOperName(accountCApply.getOperName());
			accountCBussiness.setOperNo(accountCApply.getOperNo());
			accountCBussiness.setPlaceName(accountCApply.getPlaceName());
			accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
			accountCBussiness.setBusinessId(accountCApply.getHisseqId());
			accountCBussinessDao.save(accountCBussiness);
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("211");//211记帐卡初次申请
			serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡初次申请");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//银行账号申请回执
			AccCardInitialApplyReceipt accCardInitialApplyReceipt = new AccCardInitialApplyReceipt();
			accCardInitialApplyReceipt.setTitle("银行账号申请回执");
			accCardInitialApplyReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			accCardInitialApplyReceipt.setApplyAccountType(AccountTypeTypeEnum.getNameByValue(accountCApply.getAccountType()));
			accCardInitialApplyReceipt.setApplyLinkman(accountCApply.getLinkman());
			accCardInitialApplyReceipt.setApplyTel(accountCApply.getTel());
			accCardInitialApplyReceipt.setApplyAccName(accountCApply.getAccName());
			accCardInitialApplyReceipt.setApplyBankAccount(accountCApply.getBankAccount());
			accCardInitialApplyReceipt.setApplyBankName(accountCApply.getBank());
			accCardInitialApplyReceipt.setSerItem(this.getSerItemName(billGet.getSerItem()));
			Receipt receipt = new Receipt();
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardInitialApply.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardInitialApply.getName());
			this.saveReceipt(receipt,accountCBussiness,accCardInitialApplyReceipt,customer);

			resultMap.put("result", "true");
			resultMap.put("accountCApplyId", accountCApply.getId());
			
			return resultMap;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"记帐卡初次申请失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param accountCBussiness 记账卡业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, AccountCBussiness accountCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.accountC.getValue());
		receipt.setCreateTime(accountCBussiness.getTradeTime());
		receipt.setPlaceId(accountCBussiness.getPlaceId());
		receipt.setPlaceNo(accountCBussiness.getPlaceNo());
		receipt.setPlaceName(accountCBussiness.getPlaceName());
		receipt.setOperId(accountCBussiness.getOperId());
		receipt.setOperNo(accountCBussiness.getOperName());
		receipt.setOperName(accountCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	/**
	 * 获取服务项目name
	 * @param itemCodes 服务方式Codes
	 * @return
	 */
	private String getSerItemName(String itemCodes){
		String result = "";
		if(itemCodes==null){
		    return result;
        }
		for(String serItemCode: itemCodes.split(",")){
			String temp = org.apache.commons.lang.StringUtils.trim(serItemCode);
			if(!"".equals(temp)){
				result+=SerItemEnum.getName(temp)+"，";
			}
		}
		if(result.length()>0){
			result = result.substring(0,result.length()-1);
		}
		return result;
	}
}
