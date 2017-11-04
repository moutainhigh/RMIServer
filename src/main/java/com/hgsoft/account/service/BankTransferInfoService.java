package com.hgsoft.account.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.*;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.account.RechargeReceipt;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.BankTransferBussinessDao;
import com.hgsoft.account.dao.BankTransferInfoDao;
import com.hgsoft.account.dao.BankTransferInfoHisDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.BankTransferInfoHis;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.serviceInterface.IBankTransferInfoService;
import com.hgsoft.account.serviceInterface.IRechargeInfoService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;

@Service
public class BankTransferInfoService implements IBankTransferInfoService {
	private static Logger logger = Logger.getLogger(BankTransferInfoService.class.getName());
	@Resource
	private BankTransferInfoDao bankTransferInfoDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private IRechargeInfoService rechargeInfoService;
	@Resource
	private BankTransferBussinessDao bankTransferBussinessDao;
	@Resource
	private AccountBussinessDao accountBussinessDao;
	@Resource
	private BankTransferInfoHisDao bankTransferInfoHisDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private ReceiptDao receiptDao;

	@Override
	public Pager list(Pager pager, Date starTime, Date endTime,
			BankTransferInfo bankTransferInfo, Customer customer) {
		return bankTransferInfoDao.list(pager, starTime, endTime,
				bankTransferInfo, customer);

	}
	@Override
	public Pager list(Pager pager, String starTime, String endTime,
			BankTransferInfo bankTransferInfo, Customer customer) {
		return bankTransferInfoDao.list(pager, starTime, endTime,
				bankTransferInfo, customer);
		
	}
	@Override
	//电子标签提货金额登记——收费方式——转账——客户转账列表
	public Pager listForBankTransfer(Pager pager,BankTransferInfo bankTransferInfo){
		return bankTransferInfoDao.listForBankTransfer(pager, bankTransferInfo);
	}

	@Override
	public BankTransferInfo findBytId(Long id) {
		return bankTransferInfoDao.findBytId(id);

	}
	@Override
	public BankTransferBussiness findBankTransferBussiness(BankTransferBussiness bankTransferBussiness){
		
		return bankTransferBussinessDao.find(bankTransferBussiness);
	}
	
	/**
	 * 保存转账账户交款
	 * type=3
	 */
	@Override
	public boolean save(String type, MainAccountInfo mainAccountInfo,
			RechargeInfo rechargeInfo, BankTransferInfo bankTransferInfo,BankTransferBussiness bankTransferBussiness,Map<String,Object> params) {
		try {
			AccountBussiness accountBussiness = new AccountBussiness();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(type);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setBankTransferInfo(bankTransferInfo);
			unifiedParam.setNewRechargeInfo(rechargeInfo);
			unifiedParam.setOperId(rechargeInfo.getOperId());
			unifiedParam.setPlaceId(rechargeInfo.getPlaceId());
			unifiedParam.setOperName(rechargeInfo.getOperName());
			unifiedParam.setOperNo(rechargeInfo.getOperNo());
			unifiedParam.setPlaceName(rechargeInfo.getPlaceName());
			unifiedParam.setPlaceNo(rechargeInfo.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				rechargeInfoService.save(rechargeInfo);
				
				bankTransferBussiness.setBankTransferId(bankTransferInfo.getId());
				bankTransferBussiness.setPayName(bankTransferInfo.getPayName());
				bankTransferBussiness.setBankNo(bankTransferInfo.getBankNo());
				bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.accountTransferRecharge.getValue());
				bankTransferBussiness.setTransferBlanace(bankTransferInfo.getTransferBlanace());//银行到账金额
				bankTransferBussiness.setBlanace(bankTransferInfo.getBlanace().subtract(rechargeInfo.getTakeBalance()));//缴款后余额
				bankTransferBussiness.setRechargeCost(rechargeInfo.getTakeBalance());//缴款金额
				bankTransferBussiness.setOperId(rechargeInfo.getOperId());
				bankTransferBussiness.setOperDate(new Date());
				bankTransferBussiness.setPlaceId(rechargeInfo.getPlaceId());
				bankTransferBussiness.setOperName(rechargeInfo.getOperName());
				bankTransferBussiness.setOperNo(rechargeInfo.getOperNo());
				bankTransferBussiness.setPlaceName(rechargeInfo.getPlaceName());
				bankTransferBussiness.setPlaceNo(rechargeInfo.getPlaceNo());
				bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
				
				
				accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
				accountBussiness.setRealPrice(rechargeInfo.getTakeBalance());
				accountBussiness.setState("4");//转账缴款
				accountBussiness.setTradeTime(new Date());
				accountBussiness.setUserId(rechargeInfo.getMainId());
				accountBussiness.setOperId(rechargeInfo.getOperId());
				accountBussiness.setPlaceId(rechargeInfo.getPlaceId());
				accountBussiness.setOperName(rechargeInfo.getOperName());
				accountBussiness.setOperNo(rechargeInfo.getOperNo());
				accountBussiness.setPlaceName(rechargeInfo.getPlaceName());
				accountBussiness.setPlaceNo(rechargeInfo.getPlaceNo());
				accountBussiness.setBussinessId(rechargeInfo.getId());
				accountBussiness.setHisSeqId(bankTransferInfo.getHisSeqId());
				accountBussinessDao.save(accountBussiness);
				
				
				Customer customer = customerDao.findById(accountBussiness.getUserId());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setAulAmt(accountBussiness.getRealPrice());
				serviceWater.setSerType("604");//账户转账缴款
				serviceWater.setRemark("自营客服系统：账户转账缴款");
				serviceWater.setFlowState("1");//正常
				serviceWater.setAccountBussinessId(accountBussiness.getId());
				serviceWater.setOperId(accountBussiness.getOperId());
				serviceWater.setOperNo(accountBussiness.getOperNo());
				serviceWater.setOperName(accountBussiness.getOperName());
				serviceWater.setPlaceId(accountBussiness.getPlaceId());
				serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
				serviceWater.setPlaceName(accountBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWaterDao.save(serviceWater);

				//账户缴款回执(转账)
				RechargeReceipt rechargeReceipt = new RechargeReceipt();
				rechargeReceipt.setTitle("账户缴款回执");
				rechargeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				rechargeReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
				rechargeReceipt.setCustomerSecondNo(customer.getSecondNo());
				rechargeReceipt.setCustomerSecondName(customer.getSecondName());
				rechargeReceipt.setRechargePayMentTypeCode(rechargeInfo.getPayMentType());
				rechargeReceipt.setRechargePayMentType(PayMentTypeEnum.getName(rechargeInfo.getPayMentType()));
				rechargeReceipt.setRechargeTakeBalance(NumberUtil.get2Decimal(rechargeInfo.getTakeBalance().doubleValue()*0.01));
				if(PayMentTypeEnum.tranfer.getValue().equals(rechargeReceipt.getRechargePayMentTypeCode())){//转账
					rechargeReceipt.setBankNo(bankTransferInfo.getBankNo());
					rechargeReceipt.setBankPayName(bankTransferInfo.getPayName());
					rechargeReceipt.setBankTransferBalance(NumberUtil.get2Decimal(bankTransferInfo.getTransferBlanace().doubleValue()*0.01));
					rechargeReceipt.setBankArrivalTime(DateUtil.formatDate(bankTransferInfo.getArrivalTime(),"yyyy-MM-dd HH:mm:ss"));
				}
				rechargeReceipt.setAfterAvailableBalance(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().doubleValue()*0.01));
				rechargeReceipt.setBeforeAvailableBalance(NumberUtil.get2Decimal(mainAccountInfo.getAvailableBalance().subtract(rechargeInfo.getTakeBalance()).doubleValue()*0.01));
				rechargeReceipt.setMemo(rechargeInfo.getMemo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountBussinessTypeEnum.recharge.getValue());
				receipt.setTypeChName(AccountBussinessTypeEnum.recharge.getName());
				this.saveReceipt(receipt,accountBussiness,rechargeReceipt,customer);
				
				
				return true;
			} else {
				return false;
			}

		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"转账账户交款失败");
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	
	
	//---营运调用的接口用
	/**
	 * 银行到账信息列表查询接口
	 * @param pager
	 * @param arrivalStartTime  yyyy-MM-dd
	 * @param arrivalEndTime	 yyyy-MM-dd
	 * @param fileName
	 * @return
	 * @return Pager
	 */
	public Pager findBankTransferList(Pager pager,String arrivalStartTime,String arrivalEndTime,String fileName){
		return bankTransferInfoDao.findBankTransferList(pager, arrivalStartTime, arrivalEndTime, fileName);
	}
	
	@Override
	public Map<String, String> deleteBankTransferInfo(Long id) {
		Map<String, String> resultMap = new HashMap<String,String>();
		
		try {
			BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
			bankTransferInfoHis.setCreateReason("2");//删除
			bankTransferInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBANKTRANSFERINFOHIS_NO"));
			
			BankTransferInfo bankTransferInfo = bankTransferInfoDao.findBytId(id);
			
			bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);;
			bankTransferInfoDao.delete(id);
			
			resultMap.put("result", "true");
			resultMap.put("message", "删除成功");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"删除银行到账信息失败 id="+id);
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return resultMap;
	}
	/**
	 * 银行到账信息审批
	 * @param bankTransferInfo 
	 * @return Map<String,String>
	 */
	public Map<String, String> saveApprBankTransfer(BankTransferInfo bankTransferInfo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		
		try {
			
			BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
			bankTransferInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBANKTRANSFERINFOHIS_NO"));
			bankTransferInfoHis.setCreateReason("1");//修改
			
			bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
			
			bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
			bankTransferInfoDao.updateNotNull(bankTransferInfo);
			
			resultMap.put("result", "true");
			resultMap.put("message", "审批成功");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"审批失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return resultMap;
	}
	
	/**
	 * 增加银行到账信息
	 * @param bankTransferInfos
	 * @return Map<String,String>
	 */
	public Map<String, String> saveBankTransfer(List<BankTransferInfo> bankTransferInfos) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			
			if(!bankTransferInfos.isEmpty()&&bankTransferInfos.size()>0){
				for(BankTransferInfo bankTransferInfo:bankTransferInfos){
					bankTransferInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBANKTRANSFERINFO_NO"));
				}
			}
			
			bankTransferInfoDao.batchSave(bankTransferInfos);
			
			resultMap.put("result", "true");
			resultMap.put("message", "增加银行到账信息成功");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"增加银行到账信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return resultMap;
	}
	/**
	 * @Descriptioqn:
	 * @param idType
	 * @param idCode
	 * @return
	 * @author lgm
	 * @date 2017年3月22日
	 */
	@Override
	public Customer findByTypeAndCode(String idType, String idCode) {
		try {
			return customerDao.findByTypeCode(idType, idCode);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"查找客户信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 2017-06-05 
	 * 查找银行到账信息记录的操作业务流水
	 * @param pager
	 * @param bankTransferId
	 * @return Pager
	 */
	@Override
	public Pager listBankTransferBussiness(Pager pager, Long bankTransferId) {
		return bankTransferBussinessDao.findBankTransferBussiness(pager, bankTransferId);
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
