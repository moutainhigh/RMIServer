package com.hgsoft.accountC.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.BailAccountInfoHisDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.BailAccountInfoHis;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.BailDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.accountC.serviceInterface.IBailService;
import com.hgsoft.common.Enum.AccChangeTypeEnum;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.RefundAuditStatusEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.serviceInterface.IMaterialService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardBailAddReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardBailBackReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;

import net.sf.json.JSONObject;

/**
 * @FileName BailService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月19日 上午9:41:41 
 */
@Service
public class BailService implements IBailService {

	private static Logger logger = Logger.getLogger(BailService.class
			.getName());

	@Resource
	BailDao bailDao;
	@Resource
	AccountCDao accountCDao;
	@Resource
	MainAccountInfoDao mainAccountInfoDao;
	@Resource
	BailAccountInfoDao bailAccountInfoDao;
	@Resource
	AccountCBussinessDao accountCBussinessDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private BailAccountInfoHisDao bailAccountInfoHisDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private IMaterialService materialService;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;

	private BailAccountInfo newBailAccountInfo;
	private BailAccountInfo bailAccountInfo;

	@Override
	public Pager findBailList(Pager pager, Customer customer, String cardNo, String bankAccount) {
		try {
			Pager resultPager  = bailDao.bailList(pager, customer, cardNo, bankAccount);
			return resultPager;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"BailService的bailList保证金查询列表失败");
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> prepareAddInfo(String userNo, String cardNo, String bankAccount) {
		try {
			List<Map<String, Object>> resultList = bailDao.prepareAddInfo(userNo, cardNo, bankAccount);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"BailService的bailList保证金查询列表失败");
		}
		return null;
	}

	@Override
	public Map<String, Object> saveAddBail(Bail bail, Customer customer, String bankAccount,Long accountcid,Map<String,Object> params) {
		Map<String, Object> m=new HashMap<String, Object>();
		try {
			//Long accountID = bailDao.findAccountIDByBankAccount(bankAccount).longValue();
			AccountCApply accountCapply = accountCApplyDao.findByBankAccount(bankAccount);
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByApplyId(accountCapply.getId());
			//根据卡号查出客户id，对应账户？
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findMainAccountInfoByCustomerID(customer.getId());
			BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());

			//调用账户余额更新接口,1扣主账户余额，2加保证金金额
			UnifiedParam unifiedParam = new UnifiedParam();
			//新增的字段
			mainAccountInfo.setOperName(customer.getOperName());
			mainAccountInfo.setOperNo(customer.getOperNo());
			mainAccountInfo.setPlaceName(customer.getPlaceName());
			mainAccountInfo.setPlaceNo(customer.getPlaceNo());

			unifiedParam.setMainAccountInfo(mainAccountInfo);//old主账户(更新了操作网点等信息)
			unifiedParam.setBailAccountInfo(bailAccountInfo);//old保证金账户
//			AccountCInfo accountCInfo = new AccountCInfo();
//			if(StringUtil.isNotBlank(cardNo)){
//				accountCInfo.setCardNo(cardNo);
//				accountCInfo = accountCDao.find(accountCInfo);
//				unifiedParam.setAccountCInfo(accountCInfo);//记帐卡
//			}
			bail.setBailFee(bail.getBailFee().multiply(new BigDecimal("100")));//金额处理为以分为单位

			unifiedParam.setBailFee(bail.getBailFee());//保证金新增金额
			unifiedParam.setType("17");
			unifiedParam.setOperId(bail.getOperId());
			unifiedParam.setPlaceId(bail.getPlaceId());
			unifiedParam.setOperName(bail.getOperName());
			unifiedParam.setOperNo(bail.getOperNo());
			unifiedParam.setPlaceName(bail.getPlaceName());
			unifiedParam.setPlaceNo(bail.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				AccountCInfo accountCInfo = accountCInfoDao.findById(accountcid);
				// 7.增加保证金记录 insert，需要客户id，对应账户等bail CSMS_bail
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
				bail.setId(Long.parseLong(seq.toString()));
				bail.setUserNo(customer.getUserNo());
//				if(StringUtil.isNotBlank(cardNo))
//					bail.setAccountId(accountCInfo.getAccountId());
				bail.setAccountId(subAccountInfo.getId());
				bail.setPayFlag("0");
				bail.setUp_Date(new Date());
				bail.setBankMember(accountCapply.getAccName());
				bail.setBankNo(accountCapply.getBankAccount());
				bail.setBankOpenBranches(accountCapply.getBankName());
				bail.setDflag("0");
				bail.setSetTime(new Date());
				bail.setAppTime(new Date());
				bail.setApplyTime(new Date());
				bail.setCardno(accountCInfo.getCardNo());
//				bail.setOperId(Long.parseLong("1"));
//				bail.setPlaceId(Long.parseLong("1"));
				if(bail.getUpreason() == null) bail.setUpreason("");
				bailDao.save(bail,customer);
				
				//子账户的保证金余额和保证金金额相应的新增
				subAccountInfoDao.updateBail(subAccountInfo.getId(),bail.getBailFee());
				
				// 8.保存记帐卡历史表
				AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
				Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
				accountCInfoHis.setId(accountCInfoHisId);
				accountCInfoHis.setGenReason("20");//保证金新增
				accountCInfoHisDao.save(accountCInfo,accountCInfoHis);

				// 9.更新记帐卡单卡保证金
				accountCInfo.setHisSeqId(accountCInfoHis.getHisSeqId());
				accountCInfoDao.updateBailFee(bail.getBailFee(),accountCInfo);

				/*AccountCBussiness accountCBussiness = new AccountCBussiness();
				BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setAccountId(Long.valueOf(accountCapply.getSubAccountNo()));
				accountCBussiness.setState("2");//保证金新增
				accountCBussiness.setRealPrice(bail.getBailFee());// 业务费用
				accountCBussiness.setTradeTime(new Date());
				accountCBussiness.setOperId(bail.getOperId());
				accountCBussiness.setPlaceId(bail.getPlaceId());
				//新增的字段
				accountCBussiness.setOperName(bail.getOperName());
				accountCBussiness.setOperNo(bail.getOperNo());
				accountCBussiness.setPlaceName(bail.getPlaceName());
				accountCBussiness.setPlaceNo(bail.getPlaceNo());
				accountCBussiness.setBusinessId(accountCInfo.getHisSeqId());
				accountCBussinessDao.save(accountCBussiness);*/


				//增加操作日志，需要一个accountCBussiness对象 CSMS_AccountC_bussiness
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setCardNo(accountCInfo.getCardNo());
				accountCBussiness.setAccountId(subAccountInfo.getId());
				accountCBussiness.setRealPrice(bail.getBailFee());
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setOperId(bail.getOperId());
				accountCBussiness.setPlaceId(bail.getPlaceId());
				//新增的字段
				accountCBussiness.setOperName(bail.getOperName());
				accountCBussiness.setOperNo(bail.getOperNo());
				accountCBussiness.setPlaceName(bail.getPlaceName());
				accountCBussiness.setPlaceNo(bail.getPlaceNo());
				accountCBussiness.setState("2");//类型为：保证金新增
				accountCBussiness.setTradeTime(new Date());

				//回执打印用
				accountCBussiness.setBusinessId(bail.getId());//
				accountCBussiness.setAccountCApplyHisID(accountCapply.getHisseqId());

				bailAccountInfo = bailAccountInfoDao.findByCustomerID(bailAccountInfo.getMainId());
				accountCBussiness.setBailFee(bailAccountInfo.getBailFee());
				accountCBussiness.setBailFrozenBalance(bailAccountInfo.getBailFrozenBalance());

				accountCBussinessDao.save(accountCBussiness);


				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("215");//215保证金新增
				serviceWater.setBankAccount(accountCapply.getBankAccount());//银行账号
				serviceWater.setAmt(bail.getBailFee());//应收金额
				serviceWater.setAulAmt(bail.getBailFee());//实收金额
				//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
				//TODO  是否要记录状态
				serviceWater.setFlowState("1");//1完成  
				serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：保证金新增");
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);

				//保证金收款回执
				AccCardBailAddReceipt accCardBailAddReceipt = new AccCardBailAddReceipt();
				accCardBailAddReceipt.setTitle("保证金收款回执");
				accCardBailAddReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				accCardBailAddReceipt.setAccCardNo(accountCInfo.getCardNo());
				accCardBailAddReceipt.setApplyBankAccount(accountCapply.getBankAccount());
				accCardBailAddReceipt.setBailFee(NumberUtil.get2Decimal(bail.getBailFee().doubleValue()*0.01));
				accCardBailAddReceipt.setBussBailFee(NumberUtil.get2Decimal(accountCBussiness.getBailFee().doubleValue()*0.01));
				accCardBailAddReceipt.setBailTradingType("1".equals(bail.getTradingType())?"正常":("2".equals(bail.getTradingType())?"过户":""));
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardBaidAdd.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardBaidAdd.getName());
				receipt.setCardNo(accCardBailAddReceipt.getAccCardNo());
				this.saveReceipt(receipt,accountCBussiness,accCardBailAddReceipt,customer);

				m.put("result", true);
			}else{
				m.put("result", false);
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保证金新增失败");
			e.printStackTrace();
			throw new ApplicationException("保证金新增失败");
		}
		return m;
	}

	@Override
	public Bail findById(Long id) {
		return bailDao.findById(id);
	}

	@Override
	public Map<String,Object> findBailAddDetail(Long id) {
		return bailDao.findBailAddDetail(id);
	}

	@Override
	public Map<String,Object> findBailBackDetail(Long id) {
		return bailDao.findBailBackDetail(id);
	}

	@Override
	public Boolean saveGiveBackBail(Bail bail, Customer customer, String bankAccount,String materialIds) {
		try {
			//Long accountID = bailDao.findAccountIDByBankAccount(bankAccount).longValue();
			AccountCApply accountCapply = accountCApplyDao.findByBankAccount(bankAccount);
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByApplyId(accountCapply.getId());
			//MainAccountInfo mainAccountInfo = mainAccountInfoDao.findMainAccountInfoByCustomerID(customer.getId());
			BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			//调用账户余额更新接口,1加主账户可退余额，2扣减保证金金额
			/*UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);//主账户
			unifiedParam.setBailAccountInfo(bailAccountInfo);//保证金账户
			unifiedParam.setBailFee(bail.getBailFee());//保证金新增数额
			unifiedParam.setType("32");//保证金退还
			unifiedParam.setOperId(bail.getOperId());
			unifiedParam.setPlaceId(bail.getPlaceId());*/
			//if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
			//不调用接口。
			//保证金历史信息表部分处理
			//BailAccountInfo bailAccountInfo = unifiedParam.getBailAccountInfo();
			BailAccountInfo newBailAccountInfo = bailAccountInfoDao.findByCustomerID(bailAccountInfo.getMainId());
			if(newBailAccountInfo==null){
				logger.error("数据异常：无法找到保证金总账户信息");
				return false;
			}
			BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
			BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
			bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
			newBailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
			bailAccountInfoHis.setCreateReason("3");
			bailAccountInfoHisDao.saveHis(bailAccountInfoHis, newBailAccountInfo);

			//更新保证金信息表
			//newBailAccountInfo.setBailFee(newBailAccountInfo.getBailFee().subtract(unifiedParam.getBailFee()));
			//bailAccountInfoDao.update(newBailAccountInfo);
			newBailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
			
			//页面传入的保证金金额从保证金账户中保证金金额扣除相应的金额到保证金冻结金额中
			if(bailAccountInfoDao.updateBailToFrozen(bail.getBailFee(), newBailAccountInfo) == 0){
				logger.error("数据异常：主账户保证金出现变动");
				throw new ApplicationException("数据异常：主账户保证金出现变动");
			}

			//3.增加保证金记录 insert，需要客户id，对应账户等bail CSMS_bail
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
			bail.setId(Long.parseLong(seq.toString()));
			bail.setUserNo(customer.getUserNo());
			bail.setAccountId(subAccountInfo.getId());
			bail.setPayFlag("1");//退还
			bail.setAppState("1");//审批状态为申请
			System.out.println("bail.getBailFee(--"+bail.getBailFee());
			bail.setBailFee(bail.getBailFee().multiply(new BigDecimal("-1")));
			bail.setUp_Date(new Date());
			bail.setDflag("0");
			bail.setSetTime(new Date());
			bail.setCardno("");
			if(bail.getUpreason() == null) bail.setUpreason("");

			//新增银行账户的3个信息项（保证金设置表）
//			SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountID);
			//AccountCApply accountCApply = accountCApplyDao.findByBankAccount(bankAccount);
			/*bail.setBankNo(bankAccount);//银行账号
			bail.setBankMember(customer.getOrgan());//银行客户名称
			bail.setBankOpenBranches(subAccountInfo.getPlaceId().toString());//银行开户网点（在子账户表的一个字段）
			bail.setBankMember(accountCapply.getAccName());
			bail.setBankNo(accountCapply.getBankAccount());
			bail.setBankOpenBranches(accountCapply.getBankName());*/
			bailDao.save(bail,customer);
			
			//子账户保证金金额与保证金余额减少
			if(subAccountInfoDao.updateBail2Frozen(subAccountInfo.getId(),bail.getBailFee().abs()) == 0){
				logger.error("数据异常：子账户保证金出现变动");
				throw new ApplicationException("数据异常：子账户保证金出现变动");
			}
			
			
			//2017-09-29  由于保证金退还不按单卡，所以设计改了，不需要变动单卡保证金
			// 4.保存记帐卡历史表
			/*AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(bail.getCardno());
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
			accountCInfoHis.setId(accountCInfoHisId);
			accountCInfo.setHisSeqId(accountCInfoHisId);
			accountCInfoHis.setGenReason("21");//保证金退还
			accountCInfoHisDao.save(accountCInfo,accountCInfoHis);

			// 5.更新记帐卡单卡保证金
//			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.updateBailFee(bail.getBailFee(),accountCInfo);*/

			// 6.增加操作日志，需要一个accountCBussiness对象 CSMS_AccountC_bussiness
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.parseLong(bid.toString()));
			accountCBussiness.setAccountId(subAccountInfo.getId());
			accountCBussiness.setUserId(customer.getId());
			accountCBussiness.setOperId(bail.getOperId());
			accountCBussiness.setPlaceId(bail.getPlaceId());
			//新增的字段
			accountCBussiness.setOperName(bail.getOperName());
			accountCBussiness.setOperNo(bail.getOperNo());
			accountCBussiness.setPlaceName(bail.getPlaceName());
			accountCBussiness.setPlaceNo(bail.getPlaceNo());

			accountCBussiness.setState("25");//类型为：保证金退还
			accountCBussiness.setTradeTime(new Date());
			//accountCBussiness.setCardNo(bail.getCardno());
			//accountCBussiness.setSuit(accountCInfo.getSuit());

			//回执打印用
			accountCBussiness.setBusinessId(bail.getId());//
			accountCBussiness.setAccountCApplyHisID(accountCapply.getHisseqId());
			//
			newBailAccountInfo = bailAccountInfoDao.findByCustomerID(newBailAccountInfo.getMainId());
			accountCBussiness.setBailFee(newBailAccountInfo.getBailFee());
			accountCBussiness.setBailFrozenBalance(newBailAccountInfo.getBailFrozenBalance());
			//此时bail.getBailFee()已经是负数
			if(bail.getBailFee()!=null)accountCBussiness.setRealPrice(bail.getBailFee());

			accountCBussinessDao.save(accountCBussiness);


			// 7.调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("216");//216保证金退还
			serviceWater.setBankAccount(accountCapply.getBankAccount());//银行账号
			serviceWater.setAmt(bail.getBailFee());//应收金额
			serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			//TODO  是否要记录状态
			serviceWater.setFlowState("1");//1完成  
			serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：保证金退还");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			//8.修改图片资料表业务id
			//materialService.updateVehicleId(materialIds, bail.getId());


			//return true;
			//}
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保证金退还申请失败");
			e.printStackTrace();
			throw new ApplicationException("保证金退还申请失败");
		}

	}

	/**
	 * 撤销
	 * @param bail
	 */
	@Override
	public void saveCancelBail(RefundInfo refundInfo,AccountCBussiness accountCBussiness) {
		try {
			Customer customer = customerDao.findById(refundInfo.getMainId());
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(refundInfo.getBankAccount());
			if(subAccountInfo == null){
				logger.error("数据异常：无法根据开户银行账号找到子账户");
				throw new ApplicationException("数据异常：无法根据开户银行账号找到子账户");
			}
			AccountCApply accountCapply = accountCApplyDao.findById(subAccountInfo.getApplyID());
			if(accountCapply == null){
				logger.error("数据异常：无法找到记帐卡申请表记录");
				throw new ApplicationException("数据异常：无法找到记帐卡申请表记录");
			}
			
			
			//2017-10-14 需求修改、设计修改：保证金退还申请一步生成退还记录
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);//主账户
			unifiedParam.setBailAccountInfo(bailAccountInfo);//保证金账户
			unifiedParam.setSubAccountInfo(subAccountInfo);
			
			unifiedParam.setBailFee(refundInfo.getCurrentRefundBalance());//保证金退还撤销金额
			unifiedParam.setType(AccChangeTypeEnum.bailRefundReqCancel.getValue());//保证金退还申请撤销
			unifiedParam.setOperId(accountCBussiness.getOperId());
			unifiedParam.setPlaceId(accountCBussiness.getPlaceId());
			unifiedParam.setOperNo(accountCBussiness.getOperNo());
			unifiedParam.setOperName(accountCBussiness.getOperName());
			unifiedParam.setPlaceNo(accountCBussiness.getPlaceNo());
			unifiedParam.setPlaceName(accountCBussiness.getPlaceName());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				//将当前退款记录移入历史表
				RefundInfoHis refundInfoHis = new RefundInfoHis();
				BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
				refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
				refundInfoHis.setCreateReason("撤销");
				//更新退款记录
				refundInfo.setHisSeqId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
				refundInfo.setAuditStatus("6");//已撤销
				refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
				refundInfoDao.update(refundInfo);
				
				// 5.增加操作日志，需要一个accountCBussiness对象 CSMS_AccountC_bussiness
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setAccountId(subAccountInfo.getId());
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setOperId(accountCBussiness.getOperId());
				accountCBussiness.setPlaceId(accountCBussiness.getPlaceId());
				//新增的字段
				accountCBussiness.setOperName(accountCBussiness.getOperName());
				accountCBussiness.setOperNo(accountCBussiness.getOperNo());
				accountCBussiness.setPlaceName(accountCBussiness.getPlaceName());
				accountCBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

				accountCBussiness.setState(AccountCBussinessTypeEnum.accBailBackCancel.getValue());//类型为：保证金退还撤销
				//accountCBussiness.setCardNo(bail.getCardno());
				//accountCBussiness.setSuit(accountCInfo.getSuit());

				//回执打印用
				accountCBussiness.setBusinessId(refundInfo.getId());//退款记录表id
				//AccountCBussiness oldaccountCBussiness=accountCBussinessDao.findByBaiBackCardNo(bail.getCardno());
				//accountCBussiness.setAccountCApplyHisID(oldaccountCBussiness.getAccountCApplyHisID());
				//
				newBailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
				accountCBussiness.setBailFee(newBailAccountInfo.getBailFee());
				accountCBussiness.setBailFrozenBalance(newBailAccountInfo.getBailFrozenBalance());
				accountCBussiness.setRealPrice(refundInfo.getCurrentRefundBalance().negate());

				accountCBussinessDao.save(accountCBussiness);
				
				// 7.调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				//serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType(ServiceWaterSerType.bailBackCancel.getValue());//225保证金退还撤销
				serviceWater.setBankAccount(refundInfo.getBankAccount());//银行账号
				serviceWater.setAmt(refundInfo.getCurrentRefundBalance());//应收金额
				serviceWater.setAulAmt(refundInfo.getCurrentRefundBalance());//实收金额
				//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
				//TODO  是否要记录状态
				serviceWater.setFlowState("1");//1完成  
				serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：保证金退还");
				serviceWater.setOperTime(accountCBussiness.getTradeTime());

				serviceWaterDao.save(serviceWater);
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保证金撤销失败");
			e.printStackTrace();
			throw new ApplicationException("保证金撤销失败");
		}
	}

	@Override
	public  Pager findByPage(Pager pager, Customer customer,AccountCApply accountCApply,AccountCInfo accountCInfo) {
		try {
			return bailDao.findByPage(pager, customer, accountCApply, accountCInfo);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"综合信息保证金查询");
			throw new ApplicationException("综合信息保证金查询");
		}
	}

	public BailAccountInfo getBailAccountInfo() {
		return bailAccountInfo;
	}

	public void setBailAccountInfo(BailAccountInfo bailAccountInfo) {
		this.bailAccountInfo = bailAccountInfo;
	}

	public BailAccountInfo getNewBailAccountInfo() {
		return newBailAccountInfo;
	}

	public void setNewBailAccountInfo(BailAccountInfo newBailAccountInfo) {
		this.newBailAccountInfo = newBailAccountInfo;
	}

	/*
	 * 2017-05-19 
	 * 保存保证金退还申请业务及返回map
	 * (non-Javadoc)
	 * @see com.hgsoft.accountC.serviceInterface.IBailService#saveGiveBackBailReturnMap(com.hgsoft.accountC.entity.Bail, com.hgsoft.customer.entity.Customer, java.lang.String)
	 */
	@Override
	public Map<String, Object> saveGiveBackBailReturnMap(Bail bail, Customer customer, String bankAccount,Map<String,Object> params,Long bussinessPlaceId) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			//Long accountID = bailDao.findAccountIDByBankAccount(bankAccount).longValue();
			AccountCApply accountCapply = accountCApplyDao.findByBankAccount(bankAccount);
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByApplyId(accountCapply.getId());
			BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findMainAccountInfoByCustomerID(customer.getId());
			
			
			//2017-10-14 需求修改、设计修改：保证金退还申请一步生成退还记录
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);//主账户
			unifiedParam.setBailAccountInfo(bailAccountInfo);//保证金账户
			unifiedParam.setSubAccountInfo(subAccountInfo);
			
			unifiedParam.setBailFee(bail.getBailFee());//保证金退还金额
			unifiedParam.setType(AccChangeTypeEnum.bailRefundReq.getValue());//保证金退还申请
			unifiedParam.setOperId(bail.getOperId());
			unifiedParam.setPlaceId(bail.getPlaceId());
			unifiedParam.setOperNo(bail.getOperNo());
			unifiedParam.setOperName(bail.getOperName());
			unifiedParam.setPlaceNo(bail.getPlaceNo());
			unifiedParam.setPlaceName(bail.getPlaceName());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				//2017-10-14 需求修改、设计修改：保证金退还申请一步生成退还记录,生成保证金设置表记录已无意义
				//3.增加保证金记录 insert，需要客户id，对应账户等bail CSMS_bail
				/*BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
				bail.setId(Long.parseLong(seq.toString()));
				bail.setUserNo(customer.getUserNo());
				bail.setAccountId(subAccountInfo.getId());
				bail.setPayFlag("1");//退还
				bail.setAppState("1");//审批状态为申请
				System.out.println("bail.getBailFee(--"+bail.getBailFee());
				bail.setBailFee(bail.getBailFee().multiply(new BigDecimal("-1")));
				bail.setUp_Date(new Date());
				bail.setDflag("0");
				bail.setSetTime(new Date());
				bail.setApplyTime(new Date());
				bail.setCardno("");
				bailDao.save(bail,customer);*/
				
				//记录的是资金变动后的主账户信息记录
				MainAccountInfo newMainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				//生成退款记录
				RefundInfo refundInfo = new RefundInfo();
				Long refundInfoID = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfo_NO");
				refundInfo.setId(refundInfoID);
				refundInfo.setMainId(customer.getId());//客户id
				refundInfo.setMainAccountId(newMainAccountInfo.getId());//主账户id
				refundInfo.setRefundType("2");//1：储值卡注销；2：保证金退还；3：账户退款
				refundInfo.setBalance(newMainAccountInfo.getBalance());
				refundInfo.setAvailableBalance(newMainAccountInfo.getAvailableBalance());
				refundInfo.setPreferentialBalance(newMainAccountInfo.getPreferentialBalance());
				refundInfo.setFrozenBalance(newMainAccountInfo.getFrozenBalance());
				refundInfo.setAvailableRefundBalance(newMainAccountInfo.getAvailableRefundBalance());
				refundInfo.setRefundApproveBalance(newMainAccountInfo.getRefundApproveBalance());
				refundInfo.setCurrentRefundBalance(bail.getBailFee());//本次退款金额
				refundInfo.setBankNo(bail.getBankNo());
				refundInfo.setBankMember(bail.getBankMember());
				refundInfo.setBankOpenBranches(bail.getBankOpenBranches());
				refundInfo.setOperId(bail.getOperId());//默认为保证金退款申请的申请人
				refundInfo.setOperNo(bail.getOperNo());
				refundInfo.setOperName(bail.getOperName());
				refundInfo.setPlaceID(bail.getPlaceId());
				refundInfo.setPlaceNo(bail.getPlaceNo());
				refundInfo.setPlaceName(bail.getPlaceName());
				refundInfo.setRefundApplyTime(bail.getSetTime());
				refundInfo.setRefundApplyOper(bail.getPlaceId());
				refundInfo.setAuditStatus(RefundAuditStatusEnum.request.getValue());
				refundInfo.setBussinessPlaceId(bussinessPlaceId);
				refundInfo.setBankAccount(accountCapply.getBankAccount());
				//refundInfo.setCardNo(bail.getCardno());
				refundInfo.setBailBackReason(bail.getUpreason());//保证金退还原因
				refundInfo.setExpireFlag("1");//未过资金争议期
				refundInfoDao.save(refundInfo);
				
				// 6.增加操作日志，需要一个accountCBussiness对象 CSMS_AccountC_bussiness
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setAccountId(subAccountInfo.getId());
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setOperId(bail.getOperId());
				accountCBussiness.setPlaceId(bail.getPlaceId());
				//新增的字段
				accountCBussiness.setOperName(bail.getOperName());
				accountCBussiness.setOperNo(bail.getOperNo());
				accountCBussiness.setPlaceName(bail.getPlaceName());
				accountCBussiness.setPlaceNo(bail.getPlaceNo());

				accountCBussiness.setState("25");//类型为：保证金退还
				accountCBussiness.setTradeTime(new Date());
				//accountCBussiness.setCardNo(bail.getCardno());
				//accountCBussiness.setSuit(accountCInfo.getSuit());

				//回执打印用
				accountCBussiness.setBusinessId(refundInfo.getId());//业务id记退还记录表id
				accountCBussiness.setAccountCApplyHisID(accountCapply.getHisseqId());
				//
				newBailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
				newBailAccountInfo = bailAccountInfoDao.findByCustomerID(newBailAccountInfo.getMainId());
				accountCBussiness.setBailFee(newBailAccountInfo.getBailFee());
				accountCBussiness.setBailFrozenBalance(newBailAccountInfo.getBailFrozenBalance());
				//此时bail.getBailFee()已经是负数
				if(bail.getBailFee()!=null)accountCBussiness.setRealPrice(bail.getBailFee());

				accountCBussinessDao.save(accountCBussiness);


				// 7.调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				//serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("216");//216保证金退还
				serviceWater.setBankAccount(accountCapply.getBankAccount());//银行账号
				serviceWater.setAmt(bail.getBailFee().negate());//应收金额
				serviceWater.setAulAmt(bail.getBailFee().negate());//实收金额
				//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
				//TODO  是否要记录状态
				serviceWater.setFlowState("1");//1完成  
				serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：保证金退还");
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);

				//保证金退还回执
				AccCardBailBackReceipt accCardBailBackReceipt = new AccCardBailBackReceipt();
				accCardBailBackReceipt.setTitle("保证金退还回执");
				accCardBailBackReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				//accCardBailBackReceipt.setAccCardNo(accountCInfo.getCardNo());
				accCardBailBackReceipt.setApplyBankAccount(accountCapply.getBankAccount());
				accCardBailBackReceipt.setBailBankNo(bail.getBankNo());
				accCardBailBackReceipt.setBailBankMember(bail.getBankMember());
				accCardBailBackReceipt.setBailBankOpenBranches(bail.getBankOpenBranches());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardBailBack.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardBailBack.getName());
				this.saveReceipt(receipt,accountCBussiness,accCardBailBackReceipt,customer);

				resultMap.put("result", "true");
				resultMap.put("refundInfoId", refundInfo.getId());
				return resultMap;
			}
			
			resultMap.put("result", "false");
			resultMap.put("bailId", bail.getId());
			return resultMap;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保证金退还申请失败");
			e.printStackTrace();
			throw new ApplicationException("保证金退还申请失败");
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

	@Override
	public List<Map<String, Object>> findAccountCInfosBySubID(Long subAccountInfoId) {
		return accountCDao.findAccountCInfosBySubID(subAccountInfoId);
	}
}
