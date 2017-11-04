package com.hgsoft.account.service;

import com.google.gson.Gson;
import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.BankTransferInfoDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.serviceInterface.IRechargeInfoService;
import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.account.CorrectReceipt;
import com.hgsoft.other.vo.receiptContent.account.RechargeReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * @author user
 * 
 */
@Service
public class RechargeInfoService implements IRechargeInfoService {
	private static Logger logger = LoggerFactory.getLogger(RechargeInfoService.class);

	@Resource
	private RechargeInfoDao rechargeInfoDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private AccountBussinessDao accountBussinessDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private BankTransferInfoDao bankTransferInfoDao;
	
	@Override
	public Pager list(Pager pager, Date starTime, Date endTime,
			RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo) {
		Pager pagers = rechargeInfoDao.list(pager, starTime, endTime,
				rechargeInfo, customer, cusPointPoJo);
		return pagers;
	}
	@Override
	public Pager list2(Pager pager, String starTime, String endTime,
			RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo) {
		Pager pagers = rechargeInfoDao.list2(pager, starTime, endTime,
				rechargeInfo,customer, cusPointPoJo);
		return pagers;
	}
	/**
	 * 保存账户冲正，交款，修改
	 * type 0冲正; 1缴款;2修改
	 */

	@Override
	public boolean save(String type, MainAccountInfo mainAccountInfo,
			RechargeInfo newRechargeInfo, RechargeInfo oldRechargeInfo,String flag,String pospayjson,Map<String,Object> params) {
		try {
			MainAccountInfo globalMainAccountInfo=mainAccountInfo;
			if(StringUtil.isNotBlank(pospayjson)){
				Gson gson = new Gson();
				PosPayListVO surveyListVO = gson.fromJson(pospayjson,
						 PosPayListVO.class);
				for (int i = 0; i < surveyListVO.getPospaylsit().size(); i++) {
					newRechargeInfo.setPosId(surveyListVO.getPospaylsit().get(i)
					          .getPosId());
					newRechargeInfo.setPayMentNo(surveyListVO.getPospaylsit().get(i)
					          .getPayMentNo());
					newRechargeInfo.setTakeBalance(surveyListVO.getPospaylsit().get(i)
					          .getTakeBalance().multiply(new BigDecimal("100")));
					
					if(!save(type,globalMainAccountInfo,newRechargeInfo,oldRechargeInfo,flag,params)){
						throw new ApplicationException();
					}
				}
			}else{
				if(!"37".equals(flag)&&!"38".equals(flag)){
					newRechargeInfo.setTakeBalance(newRechargeInfo.getTakeBalance().multiply(new BigDecimal("100")));
				}
				return save(type,globalMainAccountInfo,newRechargeInfo,oldRechargeInfo,flag,params);
				
			}

			return true;
		} catch (ApplicationException e) {
			throw new ApplicationException("账户交款失败", e);
		}
	}
	
	@Override
	public boolean save(String type, MainAccountInfo mainAccountInfo,
			RechargeInfo newRechargeInfo, RechargeInfo oldRechargeInfo,String flag,Map<String,Object> params){
		try {
			if (oldRechargeInfo != null) {
				BigDecimal seq = sequenceUtil
						.getSequence("SEQ_CSMSRechargeInfo_NO");
				oldRechargeInfo.setId(Long.parseLong(seq.toString()));
			}
			BigDecimal seq = sequenceUtil
					.getSequence("SEQ_CSMSRechargeInfo_NO");
			newRechargeInfo.setId(Long.parseLong(seq.toString()));

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(type);
			unifiedParam.setFlag(flag);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewRechargeInfo(newRechargeInfo);
			unifiedParam.setOldRechargeInfo(oldRechargeInfo);
			unifiedParam.setOperId(newRechargeInfo.getOperId());
			unifiedParam.setPlaceId(newRechargeInfo.getPlaceId());
			unifiedParam.setOperName(newRechargeInfo.getOperName());
			unifiedParam.setOperNo(newRechargeInfo.getOperNo());
			unifiedParam.setPlaceName(newRechargeInfo.getPlaceName());
			unifiedParam.setPlaceNo(newRechargeInfo.getPlaceNo());
			
			if(!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
				return false;
			}
			AccountBussiness accountBussiness = new AccountBussiness();
			accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
			accountBussiness.setRealPrice(newRechargeInfo.getTakeBalance());
			accountBussiness.setState(AccountBussinessTypeEnum.recharge.getValue());
			accountBussiness.setTradeTime(newRechargeInfo.getOperTime());

			mainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());

			newRechargeInfo.setState(mainAccountInfo.getState());
			newRechargeInfo.setMainAccountId(mainAccountInfo.getId());

			if(AccChangeTypeEnum.correct.getValue().equals(type)){	//冲正
				//冲正插入记录业务记录表是负数金额
				newRechargeInfo.setTakeBalance(newRechargeInfo.getTakeBalance().negate());
//				newRechargeInfo.setMemo(AccChangeTypeEnum.correct.getName());

				accountBussiness.setState(AccountBussinessTypeEnum.correct.getValue());
				accountBussiness.setRealPrice(newRechargeInfo.getTakeBalance());

			} else if("2".equals(type)){
				/*newRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance());
				newRechargeInfo.setBalance(globalMainAccountInfo.getBalance());
				newRechargeInfo.setPreferentialBalance(globalMainAccountInfo.getPreferentialBalance());
				newRechargeInfo.setFrozenBalance(globalMainAccountInfo.getFrozenBalance());
				newRechargeInfo.setAvailableRefundBalance(globalMainAccountInfo.getAvailableRefundBalance());
				newRechargeInfo.setRefundApproveBalance(globalMainAccountInfo.getRefundApproveBalance());*/
				newRechargeInfo.setState(mainAccountInfo.getState());
				accountBussiness.setState("3");
			}
			if (oldRechargeInfo != null) {
				oldRechargeInfo.setMainAccountId(mainAccountInfo.getId());
				/*oldRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setBalance(globalMainAccountInfo.getBalance().subtract(newRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setPreferentialBalance(globalMainAccountInfo.getPreferentialBalance());
				oldRechargeInfo.setFrozenBalance(globalMainAccountInfo.getFrozenBalance());
				oldRechargeInfo.setAvailableRefundBalance(globalMainAccountInfo.getAvailableRefundBalance());
				oldRechargeInfo.setRefundApproveBalance(globalMainAccountInfo.getRefundApproveBalance());*/
				oldRechargeInfo.setTakeBalance(new BigDecimal("-"+oldRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
				rechargeInfoDao.save(oldRechargeInfo);
			}
			newRechargeInfo.setBalance(mainAccountInfo.getBalance());
			newRechargeInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());
			newRechargeInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
			newRechargeInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
			newRechargeInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
			newRechargeInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
			rechargeInfoDao.save(newRechargeInfo);

			if(AccChangeTypeEnum.correct.getValue().equals(type)){
				if (updateCorrectState(newRechargeInfo.getCorrectId()) != 1) {
					logger.error("主账户缴款记录[{}]发生变更，不能冲正", newRechargeInfo.getCorrectId());
					throw new ApplicationException("主账户缴款记录发生变更");
				}
			} else if("2".equals(type)){
				updateCorrectState(oldRechargeInfo.getCorrectId());
			}
			
			accountBussiness.setTradeTime(new Date());
			accountBussiness.setUserId(newRechargeInfo.getMainId());
			accountBussiness.setOperId(newRechargeInfo.getOperId());
			accountBussiness.setPlaceId(newRechargeInfo.getPlaceId());
			accountBussiness.setOperName(newRechargeInfo.getOperName());
			accountBussiness.setOperNo(newRechargeInfo.getOperNo());
			accountBussiness.setPlaceName(newRechargeInfo.getPlaceName());
			accountBussiness.setPlaceNo(newRechargeInfo.getPlaceNo());
			accountBussiness.setBussinessId(newRechargeInfo.getId());
			accountBussiness.setHisSeqId(mainAccountInfo.getHisSeqId());
			
			//为了查询金额统计方便，当账户缴款是修改的时候，应该要记录两条账户业务记录（因为修改缴款做了两步：将之前的缴款记录冲正，再新增一条缴款记录）
			AccountBussiness bussinessDelete = new AccountBussiness();
			if("2".equals(type)){
				//交换了id，让业务记录表order看起来顺眼。
				bussinessDelete.setId(accountBussiness.getId());
				accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
				
				bussinessDelete.setState("2");//冲正
				bussinessDelete.setRealPrice(oldRechargeInfo.getTakeBalance());
				
				bussinessDelete.setTradeTime(accountBussiness.getTradeTime());
				bussinessDelete.setUserId(accountBussiness.getUserId());
				bussinessDelete.setOperId(accountBussiness.getOperId());
				bussinessDelete.setPlaceId(accountBussiness.getPlaceId());
				bussinessDelete.setOperName(accountBussiness.getOperName());
				bussinessDelete.setOperNo(accountBussiness.getOperNo());
				bussinessDelete.setPlaceName(accountBussiness.getPlaceName());
				bussinessDelete.setPlaceNo(accountBussiness.getPlaceNo());
				
				bussinessDelete.setBussinessId(oldRechargeInfo.getId());
				bussinessDelete.setHisSeqId(null);//TODO  这个hisSeq用来做什么
				accountBussinessDao.saveRecharge(bussinessDelete);
			}

			accountBussinessDao.saveRecharge(accountBussiness);

			Customer customer = customerDao.findById(accountBussiness.getUserId());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (customer != null) {
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
			}
			//serviceWater.setCardNo(prepaidCBussiness.getCardno());
			if(AccChangeTypeEnum.correct.getValue().equals(type)){
				serviceWater.setSerType("602");//账户冲正
				serviceWater.setRemark("自营客服系统：账户冲正");
				serviceWater.setFlowState("3");//冲正
				//要将之前的缴款的流水状态改为“被冲正”(根据账户业务记录表找)newRechargeInfo.getCorrectId()就是之前的缴款记录id
				AccountBussiness temp = accountBussinessDao.findByBussinessId(newRechargeInfo.getCorrectId());
				if(temp!=null){
					ServiceWater tempWater = serviceWaterDao.findByAccountBussinessId(temp.getId());
					if(tempWater!=null){
						tempWater.setFlowState("2");//被冲正
						serviceWaterDao.update(tempWater);
					}
				}
			}else if("1".equals(type)){
				serviceWater.setSerType("601");//账户缴款
				serviceWater.setRemark("自营客服系统：账户缴款");
				serviceWater.setFlowState("1");//正常
			}else if("2".equals(type)){
				serviceWater.setSerType("603");//账户缴款修改
				serviceWater.setRemark("自营客服系统：账户缴款修改");
				serviceWater.setFlowState("1");//正常
				//账户缴款修改，即新增一条账户缴款记录，把之前的缴款记录冲正。
				//要将之前的缴款的流水状态改为“被冲正”(根据账户业务记录表找)oldRechargeInfo.getCorrectId()就是要被冲正的缴款记录id
				AccountBussiness temp = accountBussinessDao.findByBussinessId(oldRechargeInfo.getCorrectId());
				if(temp!=null){
					ServiceWater tempWater = serviceWaterDao.findByAccountBussinessId(temp.getId());
					if(tempWater!=null){
						tempWater.setFlowState("2");//被冲正
						serviceWaterDao.update(tempWater);
					}
				}
			}
			serviceWater.setAmt(accountBussiness.getRealPrice());
			serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setAccountBussinessId(accountBussiness.getId());
			serviceWater.setOperId(accountBussiness.getOperId());
			serviceWater.setOperNo(accountBussiness.getOperNo());
			serviceWater.setOperName(accountBussiness.getOperName());
			serviceWater.setPlaceId(accountBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountBussiness.getPlaceName());
			serviceWater.setOperTime(accountBussiness.getTradeTime());
			
			//为了查询金额统计方便，当账户缴款是修改的时候，应该要记录两条客服流水记录（因为修改缴款做了两步：将之前的缴款记录冲正，再新增一条缴款记录）
			if("2".equals(type)){
				ServiceWater serviceWater2 = new ServiceWater();
				serviceWater2.setId(serviceWater.getId());
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				
				serviceWater2.setCustomerId(serviceWater.getCustomerId());
				serviceWater2.setUserNo(serviceWater.getUserNo());
				serviceWater2.setUserName(serviceWater.getUserName());
				serviceWater2.setOperId(serviceWater.getOperId());
				serviceWater2.setOperNo(serviceWater.getOperNo());
				serviceWater2.setOperName(serviceWater.getOperName());
				serviceWater2.setPlaceId(serviceWater.getPlaceId());
				serviceWater2.setPlaceNo(serviceWater.getPlaceNo());
				serviceWater2.setPlaceName(serviceWater.getPlaceName());
				serviceWater2.setOperTime(serviceWater.getOperTime());
				
				serviceWater2.setSerType("602");//账户冲正
				serviceWater2.setRemark("自营客服系统：账户冲正");
				serviceWater2.setFlowState("3");//冲正
				serviceWater2.setAulAmt(bussinessDelete.getRealPrice());
				serviceWater2.setAccountBussinessId(bussinessDelete.getId());
				serviceWaterDao.save(serviceWater2);
			}
			
			serviceWaterDao.save(serviceWater);

			//缴款
			if(AccChangeTypeEnum.recharge.getValue().equals(type)) {
				//账户缴款回执(非转账)
				RechargeReceipt rechargeReceipt = new RechargeReceipt();
				rechargeReceipt.setTitle("账户缴款回执");
				rechargeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				rechargeReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
				rechargeReceipt.setCustomerSecondNo(customer.getSecondNo());
				rechargeReceipt.setCustomerSecondName(customer.getSecondName());
				rechargeReceipt.setRechargePayMentTypeCode(newRechargeInfo.getPayMentType());
				rechargeReceipt.setRechargePayMentType(PayMentTypeEnum.getName(newRechargeInfo.getPayMentType()));
				rechargeReceipt.setRechargeTakeBalance(NumberUtil.get2Decimal(newRechargeInfo.getTakeBalance().doubleValue() * 0.01));
				if (!PayMentTypeEnum.tranfer.getValue().equals(rechargeReceipt.getRechargePayMentTypeCode())) {    //非转账
					rechargeReceipt.setRechargeVoucherNo(newRechargeInfo.getVoucherNo());
					rechargeReceipt.setRechargePayMentNo(newRechargeInfo.getPayMentNo());
					rechargeReceipt.setRechargePosId(newRechargeInfo.getPosId() + "");
				}
				rechargeReceipt.setAfterAvailableBalance(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().doubleValue() * 0.01));
				rechargeReceipt.setBeforeAvailableBalance(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()).doubleValue() * 0.01));
				rechargeReceipt.setMemo(newRechargeInfo.getMemo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountBussinessTypeEnum.recharge.getValue());
				receipt.setTypeChName(AccountBussinessTypeEnum.recharge.getName());
				receipt.setBusinessId(accountBussiness.getId());
				this.saveReceipt(receipt, accountBussiness, rechargeReceipt, customer);

				this.getReceipt(receipt,"0");	//操作完立即打印
			}else if(AccChangeTypeEnum.correct.getValue().equals(type)){	//冲正
				BankTransferInfo bankTransferInfo = this.bankTransferInfoDao.findBytId(newRechargeInfo.getBankTransferId());
				AccountBussiness rechargeAccountBussiness = this.accountBussinessDao.findByBusinessIdAndState(newRechargeInfo.getCorrectId(),AccountBussinessTypeEnum.recharge.getValue());
				//账户缴款冲正回执
				CorrectReceipt correctReceipt = new CorrectReceipt();
				correctReceipt.setTitle("账户缴款冲正回执");
				correctReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				correctReceipt.setRechargeReciptNo(this.receiptDao.findByBusIdAndPTC(rechargeAccountBussiness.getId(),ReceiptParentTypeCodeEnum.account.getValue()).getReceiptNo());
				correctReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
				correctReceipt.setCustomerSecondNo(customer.getSecondNo());
				correctReceipt.setCustomerSecondName(customer.getSecondName());
				correctReceipt.setRechargePayMentTypeCode(newRechargeInfo.getPayMentType());
				correctReceipt.setRechargePayMentType(PayMentTypeEnum.getName(newRechargeInfo.getPayMentType()));
				correctReceipt.setCorrectMoney(NumberUtil.get2Decimal(Math.abs(newRechargeInfo.getTakeBalance().doubleValue())*0.01));
				if(PayMentTypeEnum.tranfer.getValue().equals(correctReceipt.getRechargePayMentTypeCode())){		//转账冲正
					correctReceipt.setBankNo(bankTransferInfo.getBankNo());
					correctReceipt.setBankPayName(bankTransferInfo.getPayName());
				}else{
					correctReceipt.setRechargePayMentNo(newRechargeInfo.getPayMentNo());
					correctReceipt.setRechargeVoucherNo(newRechargeInfo.getVoucherNo());
					correctReceipt.setRechargePosId(newRechargeInfo.getPosId()+"");
				}
				correctReceipt.setAfterCorrectMoney(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().doubleValue()*0.01));
				correctReceipt.setBeforeCorrectMoney(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()).doubleValue()*0.01));
				correctReceipt.setMemo(newRechargeInfo.getMemo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountBussinessTypeEnum.correct.getValue());
				receipt.setTypeChName(AccountBussinessTypeEnum.correct.getName());
				this.saveReceipt(receipt,accountBussiness,correctReceipt,customer);
			}


			return true;
		} catch (ApplicationException e) {
			throw new ApplicationException("账户交款失败", e);
		}
		
		
	}
	
	List<Receipt> list;
	@Override
	public List<Receipt> getReceipt(Receipt receipt,String flag){
		if(list==null){
			list = new ArrayList<Receipt>();
		}
		if(receipt!=null && flag.equals("0")){
			list.add(receipt);
		}
		if(receipt==null && flag.equals("0")){
			return list;
		}
		if(receipt==null && flag.equals("1")){
			list=null;
		}
		return null;
	}
	/**
	 * 保存账交款款记录
	 */
	
	@Override
	public void save(RechargeInfo newRechargeInfo) {
		try {
			BigDecimal seq = sequenceUtil
					.getSequence("SEQ_CSMSRechargeInfo_NO");
			newRechargeInfo.setId(Long.parseLong(seq.toString()));
			rechargeInfoDao.save(newRechargeInfo);
		} catch (ApplicationException e) {
			logger.error("保存账交款款记录失败", e);
			throw new ApplicationException();
		}
	}

	@Override
	public RechargeInfo findById(Long id) {
		return rechargeInfoDao.findById(id);

	}
	public RechargeInfo findByPrepaidCBussinessId(Long id) {
		return rechargeInfoDao.findByPrepaidCBussinessId(id);
	}
	@Override
	public RechargeInfo findByCorrectId(Long correctId) {
		// TODO Auto-generated method stub
		return rechargeInfoDao.findByCorrectId(correctId);
	}
	/**
	 * 更新冲正状态
	 */
	@Override
	public int updateCorrectState(Long id) {
		try {
			return rechargeInfoDao.updateCorrectState(id);
	} catch (ApplicationException e) {
		throw new ApplicationException("更新冲正状态失败", e);
	}
	}
	@Override
	public Pager findByPageForSumAmt(Pager pager, Date starTime, Date endTime,
			RechargeInfo rechargeInfo, Customer customer,
			CusPointPoJo cusPointPoJo) {
		try {
			return rechargeInfoDao.findByPageForSumAmt(pager, starTime, endTime, rechargeInfo, customer, cusPointPoJo);
		} catch (ApplicationException e) {
			throw new ApplicationException("查询账户缴款记录失败", e);
		}
	}
	
	@Override
	public Map<String, Object> saveEdit4BankTransfer(RechargeInfo rechargeInfo) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		/*try {
			if (oldRechargeInfo != null) {
				BigDecimal seq = sequenceUtil
						.getSequence("SEQ_CSMSRechargeInfo_NO");
				oldRechargeInfo.setId(Long.parseLong(seq.toString()));
			}
			BigDecimal seq = sequenceUtil
					.getSequence("SEQ_CSMSRechargeInfo_NO");
			newRechargeInfo.setId(Long.parseLong(seq.toString()));

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(type);
			unifiedParam.setFlag(flag);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewRechargeInfo(newRechargeInfo);
			unifiedParam.setOldRechargeInfo(oldRechargeInfo);
			unifiedParam.setOperId(newRechargeInfo.getOperId());
			unifiedParam.setPlaceId(newRechargeInfo.getPlaceId());
			unifiedParam.setOperName(newRechargeInfo.getOperName());
			unifiedParam.setOperNo(newRechargeInfo.getOperNo());
			unifiedParam.setPlaceName(newRechargeInfo.getPlaceName());
			unifiedParam.setPlaceNo(newRechargeInfo.getPlaceNo());
			
			if(!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
				return false;
			}
			AccountBussiness accountBussiness = new AccountBussiness();
			accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
			accountBussiness.setRealPrice(newRechargeInfo.getTakeBalance());
			accountBussiness.setState("1");
			
			MainAccountInfo globalMainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
			newRechargeInfo.setMainAccountId(globalMainAccountInfo.getId());
			newRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance());
			newRechargeInfo.setBalance(globalMainAccountInfo.getBalance());
			newRechargeInfo.setPreferentialBalance(globalMainAccountInfo.getPreferentialBalance());
			newRechargeInfo.setFrozenBalance(globalMainAccountInfo.getFrozenBalance());
			newRechargeInfo.setAvailableRefundBalance(globalMainAccountInfo.getAvailableRefundBalance());
			newRechargeInfo.setRefundApproveBalance(globalMainAccountInfo.getRefundApproveBalance());
			mainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
			newRechargeInfo.setState(mainAccountInfo.getState());
			newRechargeInfo.setMainAccountId(mainAccountInfo.getId());
			if("0".equals(type)){
				accountBussiness.setState("2");
				newRechargeInfo.setBalance(globalMainAccountInfo.getBalance());
				newRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance());
				newRechargeInfo.setTakeBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
				//冲正插入记录业务记录表是负数金额
				if(accountBussiness.getRealPrice()!=null)accountBussiness.setRealPrice(accountBussiness.getRealPrice().multiply(new BigDecimal("-1")));
			}
			if("2".equals(type)){
				newRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance());
				newRechargeInfo.setBalance(globalMainAccountInfo.getBalance());
				newRechargeInfo.setPreferentialBalance(globalMainAccountInfo.getPreferentialBalance());
				newRechargeInfo.setFrozenBalance(globalMainAccountInfo.getFrozenBalance());
				newRechargeInfo.setAvailableRefundBalance(globalMainAccountInfo.getAvailableRefundBalance());
				newRechargeInfo.setRefundApproveBalance(globalMainAccountInfo.getRefundApproveBalance());
				newRechargeInfo.setState(mainAccountInfo.getState());
				accountBussiness.setState("3");
			}
			if (oldRechargeInfo != null) {
				oldRechargeInfo.setMainAccountId(mainAccountInfo.getId());
				oldRechargeInfo.setAvailableBalance(globalMainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setBalance(globalMainAccountInfo.getBalance().subtract(newRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setPreferentialBalance(globalMainAccountInfo.getPreferentialBalance());
				oldRechargeInfo.setFrozenBalance(globalMainAccountInfo.getFrozenBalance());
				oldRechargeInfo.setAvailableRefundBalance(globalMainAccountInfo.getAvailableRefundBalance());
				oldRechargeInfo.setRefundApproveBalance(globalMainAccountInfo.getRefundApproveBalance());
				oldRechargeInfo.setTakeBalance(new BigDecimal("-"+oldRechargeInfo.getTakeBalance()));
				oldRechargeInfo.setAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
				rechargeInfoDao.save(oldRechargeInfo);
			}
			rechargeInfoDao.save(newRechargeInfo);
			if("0".equals(type)){
				updateCorrectState(newRechargeInfo.getCorrectId());
			}
			if("2".equals(type)){
				updateCorrectState(oldRechargeInfo.getCorrectId());
			}
			
			accountBussiness.setTradeTime(new Date());
			accountBussiness.setUserId(newRechargeInfo.getMainId());
			accountBussiness.setOperId(newRechargeInfo.getOperId());
			accountBussiness.setPlaceId(newRechargeInfo.getPlaceId());
			accountBussiness.setOperName(newRechargeInfo.getOperName());
			accountBussiness.setOperNo(newRechargeInfo.getOperNo());
			accountBussiness.setPlaceName(newRechargeInfo.getPlaceName());
			accountBussiness.setPlaceNo(newRechargeInfo.getPlaceNo());
			accountBussiness.setBussinessId(newRechargeInfo.getId());
			accountBussiness.setHisSeqId(mainAccountInfo.getHisSeqId());
			
			//为了查询金额统计方便，当账户缴款是修改的时候，应该要记录两条账户业务记录（因为修改缴款做了两步：将之前的缴款记录冲正，再新增一条缴款记录）
			AccountBussiness bussinessDelete = new AccountBussiness();
			if("2".equals(type)){
				//交换了id，让业务记录表order看起来顺眼。
				bussinessDelete.setId(accountBussiness.getId());
				accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
				
				bussinessDelete.setState("2");//冲正
				bussinessDelete.setRealPrice(oldRechargeInfo.getTakeBalance());
				
				bussinessDelete.setTradeTime(accountBussiness.getTradeTime());
				bussinessDelete.setUserId(accountBussiness.getUserId());
				bussinessDelete.setOperId(accountBussiness.getOperId());
				bussinessDelete.setPlaceId(accountBussiness.getPlaceId());
				bussinessDelete.setOperName(accountBussiness.getOperName());
				bussinessDelete.setOperNo(accountBussiness.getOperNo());
				bussinessDelete.setPlaceName(accountBussiness.getPlaceName());
				bussinessDelete.setPlaceNo(accountBussiness.getPlaceNo());
				
				bussinessDelete.setBussinessId(oldRechargeInfo.getId());
				bussinessDelete.setHisSeqId(null);//TODO  这个hisSeq用来做什么
				accountBussinessDao.saveRecharge(bussinessDelete);
			}
			
			
			accountBussinessDao.saveRecharge(accountBussiness);
			
			
			
			//保存回执
			Receipt receipt = receiptDao.saveByBussiness(null, accountBussiness, null, null, null);
			if(("1").equals(accountBussiness.getState())){
				getReceipt(receipt,"0");
			}
			
			
			Customer customer = customerDao.findById(accountBussiness.getUserId());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(prepaidCBussiness.getCardno());
			if("0".equals(type)){
				serviceWater.setSerType("602");//账户冲正
				serviceWater.setRemark("自营客服系统：账户冲正");
				serviceWater.setFlowState("3");//冲正
				//要将之前的缴款的流水状态改为“被冲正”(根据账户业务记录表找)newRechargeInfo.getCorrectId()就是之前的缴款记录id
				AccountBussiness temp = accountBussinessDao.findByBussinessId(newRechargeInfo.getCorrectId());
				if(temp!=null){
					ServiceWater tempWater = serviceWaterDao.findByAccountBussinessId(temp.getId());
					if(tempWater!=null){
						tempWater.setFlowState("2");//被冲正
						serviceWaterDao.update(tempWater);
					}
				}
			}else if("1".equals(type)){
				serviceWater.setSerType("601");//账户缴款
				serviceWater.setRemark("自营客服系统：账户缴款");
				serviceWater.setFlowState("1");//正常
			}else if("2".equals(type)){
				serviceWater.setSerType("603");//账户缴款修改
				serviceWater.setRemark("自营客服系统：账户缴款修改");
				serviceWater.setFlowState("1");//正常
				//账户缴款修改，即新增一条账户缴款记录，把之前的缴款记录冲正。
				//要将之前的缴款的流水状态改为“被冲正”(根据账户业务记录表找)oldRechargeInfo.getCorrectId()就是要被冲正的缴款记录id
				AccountBussiness temp = accountBussinessDao.findByBussinessId(oldRechargeInfo.getCorrectId());
				if(temp!=null){
					ServiceWater tempWater = serviceWaterDao.findByAccountBussinessId(temp.getId());
					if(tempWater!=null){
						tempWater.setFlowState("2");//被冲正
						serviceWaterDao.update(tempWater);
					}
				}
			}
			serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setAccountBussinessId(accountBussiness.getId());
			serviceWater.setOperId(accountBussiness.getOperId());
			serviceWater.setOperNo(accountBussiness.getOperNo());
			serviceWater.setOperName(accountBussiness.getOperName());
			serviceWater.setPlaceId(accountBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			
			//为了查询金额统计方便，当账户缴款是修改的时候，应该要记录两条客服流水记录（因为修改缴款做了两步：将之前的缴款记录冲正，再新增一条缴款记录）
			if("2".equals(type)){
				ServiceWater serviceWater2 = new ServiceWater();
				serviceWater2.setId(serviceWater.getId());
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				
				serviceWater2.setCustomerId(serviceWater.getCustomerId());
				serviceWater2.setUserNo(serviceWater.getUserNo());
				serviceWater2.setUserName(serviceWater.getUserName());
				serviceWater2.setOperId(serviceWater.getOperId());
				serviceWater2.setOperNo(serviceWater.getOperNo());
				serviceWater2.setOperName(serviceWater.getOperName());
				serviceWater2.setPlaceId(serviceWater.getPlaceId());
				serviceWater2.setPlaceNo(serviceWater.getPlaceNo());
				serviceWater2.setPlaceName(serviceWater.getPlaceName());
				serviceWater2.setOperTime(serviceWater.getOperTime());
				
				serviceWater2.setSerType("602");//账户冲正
				serviceWater2.setRemark("自营客服系统：账户冲正");
				serviceWater2.setFlowState("3");//冲正
				serviceWater2.setAulAmt(bussinessDelete.getRealPrice());
				serviceWater2.setAccountBussinessId(bussinessDelete.getId());
				serviceWaterDao.save(serviceWater2);
			}
			
			serviceWaterDao.save(serviceWater);
			
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"账户交款失败");
			e.printStackTrace();
			throw new ApplicationException();
		}*/
		
		return resultMap;
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param accountBussiness 账户业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, AccountBussiness accountBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.account.getValue());
		receipt.setCreateTime(accountBussiness.getTradeTime());
		receipt.setPlaceId(accountBussiness.getPlaceId());
		receipt.setPlaceNo(accountBussiness.getPlaceNo());
		receipt.setPlaceName(accountBussiness.getPlaceName());
		receipt.setOperId(accountBussiness.getOperId());
		receipt.setOperNo(accountBussiness.getOperName());
		receipt.setOperName(accountBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}
}
