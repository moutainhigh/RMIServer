package com.hgsoft.accountC.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccNameChaReceipt;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountNCApplyDao;
import com.hgsoft.accountC.dao.AccountNCApplyHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.accountC.entity.AccountNCApplyHis;
import com.hgsoft.accountC.serviceInterface.IAccountNCApplyService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 记帐卡名称变更
 * @author gaosiling
 * 2016年2月20日10:19:07
 */
@Service
public class AccountNCApplyService implements IAccountNCApplyService{

	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private AccountNCApplyDao accountNCApplyDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	
	@Resource
	private AccountNCApplyHisDao accountNCApplyHisDao;
	
	@Resource
	ServiceFlowRecordDao serviceFlowRecordDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ReceiptDao receiptDao;
	
	private static Logger logger = Logger.getLogger(AccountNCApplyService.class.getName());
	
	/**
	 * 记帐卡名称变更分页查询
	 * @param  pager
	 * @param  customer
	 * @param  accountCApply
	 * @author gaosiling
	 */
	@Override
	public Pager findByPage(Pager pager,Customer customer,AccountCApply accountCApply,Date startTime,Date endTime) {
		try {
			Pager maps=accountNCApplyDao.findByPage(pager, customer, accountCApply,startTime,endTime);
			return maps;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡名称表更列表失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 保存记帐卡名称变更
	 * @param  accountNCApply
	 * @author gaosiling
	 */
	@Override
	public Map<String, Object> save(AccountNCApply accountNCApply, Long custId, Map<String,Object> params) {
		Long accountNCApplyId = sequenceUtil.getSequenceLong("seq_CSMSAccountNCapply_no");
		accountNCApply.setId(accountNCApplyId);
		
		/*List<Map<String,Object>> list = accountNCApplyDao.find(custId);
		Map<String,Object> map = list.get(0);
		String subAccountNo = (String) map.get("subAccountNo");
		accountCBussiness.setCardNo(subAccountNo);*/
		AccountCBussiness accountCBussiness = new AccountCBussiness();
		Long accountCBussinessId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
		accountCBussiness.setId(accountCBussinessId);
		accountCBussiness.setUserId(custId);
		accountCBussiness.setState(AccountCBussinessTypeEnum.accNameCha.getValue());
		accountCBussiness.setTradeTime(accountNCApply.getOperTime());
		accountCBussiness.setOperId(accountNCApply.getOperId());
		accountCBussiness.setPlaceId(accountNCApply.getPlaceId());
		//新增的字段
		accountCBussiness.setOperName(accountNCApply.getOperName());
		accountCBussiness.setOperNo(accountNCApply.getOperNo());
		accountCBussiness.setPlaceName(accountNCApply.getPlaceName());
		accountCBussiness.setPlaceNo(accountNCApply.getPlaceNo());
		accountCBussiness.setBusinessId(accountNCApply.getId());//是否要记录，不记录找不到这表了？？
		AccountCApply accountCApply=accountCApplyDao.findBySubAccId(accountNCApply.getAccountId());
		/*ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
		BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
		serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
		
		serviceFlowRecord.setServicePTypeCode(2);
		serviceFlowRecord.setServiceTypeCode(10);
		serviceFlowRecordDao.save(serviceFlowRecord);*/
//		
		try {
			
			accountNCApplyDao.save(accountNCApply);
			
			accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
			accountCBussiness.setBusinessId(accountNCApply.getHisSeqId());
			
			accountCBussinessDao.save(accountCBussiness);
			
			
			Customer customer = customerDao.findById(custId);
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("217");//217账户名称变更
			serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			//serviceWater.setAmt(bail.getBailFee());//应收金额
			//serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：账户名称变更");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//银行账户名称变更申请回执
			AccNameChaReceipt accNameChaReceipt = new AccNameChaReceipt();
			accNameChaReceipt.setTitle("银行账户名称变更申请回执");
			accNameChaReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			accNameChaReceipt.setApplyBankAccount(accountCApply.getBankAccount());
			accNameChaReceipt.setApplyBankName(accountCApply.getBank());
			accNameChaReceipt.setOldApplyAccName(accountNCApply.getOldAccName());
			accNameChaReceipt.setApplyAccName(accountNCApply.getNewAccName());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(AccountCBussinessTypeEnum.accNameCha.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accNameCha.getName());
			this.saveReceipt(receipt,accountCBussiness,accNameChaReceipt,customer);
			
			Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("accountNCApplyId", accountNCApplyId);
			
			return resultMap;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"保存记帐卡名称变更失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 删除记帐卡名称变更
	 * @param  id
	 * @author gaosiling
	 */
	@Override
	public void delete(Long id){
		try {
			AccountNCApply accountNCApply = new AccountNCApply();
			accountNCApply.setId(id);
			AccountNCApplyHis accountNCApplyHis = new AccountNCApplyHis();
			Long hisId = sequenceUtil.getSequenceLong("seq_CSMSAccountNCapplyhis_no");
			accountNCApplyHis.setId(hisId);
			accountNCApplyHis.setGenReason("2");
			
			accountNCApplyHisDao.saveHis(accountNCApplyHis, accountNCApply);
			
			accountNCApplyDao.delete(id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"删除记帐卡名称变更失败");
			throw new ApplicationException();
		}
	}
	
	@Override
	public AccountNCApply findById(Long id){
		return accountNCApplyDao.findById(id);
	}

	@Override
	public void update(AccountNCApply accountNCApply) {
		try {
			AccountNCApplyHis accountNCApplyHis = new AccountNCApplyHis();
			Long hisId = sequenceUtil.getSequenceLong("seq_CSMSAccountNCapplyhis_no");
			accountNCApplyHis.setId(hisId);
			accountNCApplyHis.setGenReason("2");
			accountNCApply.setHisSeqId(hisId);
			accountNCApplyHisDao.saveHis(accountNCApplyHis, accountNCApply);
			
			accountNCApplyDao.update(accountNCApply);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"修改记帐卡名称变更失败");
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
}
