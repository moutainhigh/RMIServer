package com.hgsoft.accountC.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.accountC.dao.*;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardMigrateReceipt;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.Migrate;
import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.accountC.serviceInterface.IMigrateService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.serviceInterface.IMaterialService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 记帐卡迁移
 * @author gaosiling
 * 2016年2月22日14:47:10
 */
@Service
public class MigrateService implements IMigrateService{

	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private MigrateDao migrateDao;
	
	@Resource
	private MigrateDetailDao migrateDetailDao;
	
	@Resource
	private AccountCApplyDao accountCApplyDao;
	
	@Resource
	private AccountCDao accountCDao;
	
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private TransferApplyDao transferApplyDao;
	@Resource
	private TransferDetailDao transferDetailDao;
	
	@Resource
	ServiceFlowRecordDao serviceFlowRecordDao;
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private IMaterialService materialService;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	
	private static Logger logger = Logger.getLogger(MigrateService.class.getName());
	
	/**
	 * 保存记帐卡迁移数据
	 * migrate 迁移记录
	 * rightsCode 迁移的记帐卡卡号
	 */
	@Override
	public void saveMigrate(Migrate migrate,List<String> rightsCode,Long custId,String materialIds){
		//创建记帐卡信息历史集合
		/*List<AccountCInfoHis> hisList = new ArrayList<AccountCInfoHis>();
		AccountCInfoHis accountCInfoHis = null;*/
		Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMigrate_NO");
		
		/*Long hisId = null;
		//创建记帐卡信息表的列表集合
		List<AccountCInfo> accList = new ArrayList<AccountCInfo>();
		AccountCInfo accountCInfo = null;*/
		
		//设置迁移记录id
		migrate.setId(id);
		
		//创建迁移明细
		List<MigrateDetail> migrateDetailList = new ArrayList<MigrateDetail>();
		MigrateDetail migrateDetail = null;
		for (String cardNo : rightsCode) {
			//TODE 未完成
			migrateDetail = new MigrateDetail();
			migrateDetail.setCardNo(cardNo);
			migrateDetail.setOperId(migrate.getOperId());
			migrateDetail.setPlaceId(migrate.getPlaceId());
			//新增的字段
			migrateDetail.setOperName(migrate.getOperName());
			migrateDetail.setOperNo(migrate.getOperNo());
			migrateDetail.setPlaceName(migrate.getPlaceName());
			migrateDetail.setPlaceNo(migrate.getPlaceNo());
			
			migrateDetail.setMigrateId(id);
			migrateDetailList.add(migrateDetail);
			
			/*//创建记帐卡历史对象
			accountCInfoHis = new AccountCInfoHis();
			hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
			accountCInfoHis.setId(hisId);
			accountCInfoHis.setGenReason("8");
//			accountCInfoHis.setAccountId(migrate.getOldAccountId());
			accountCInfoHis.setCardNo(cardNo);
			
			//创建记帐卡信息对象
			accountCInfo = new AccountCInfo();
			accountCInfo.setAccountId(migrate.getNewAccountId());
			accountCInfo.setCardNo(cardNo);
			accountCInfo.setHisSeqId(hisId);
			accList.add(accountCInfo);
			hisList.add(accountCInfoHis);*/
		}
		
		AccountCBussiness accountCBussiness = new AccountCBussiness();
		id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
		accountCBussiness.setId(id);
		accountCBussiness.setUserId(custId);
		accountCBussiness.setState("18");
		accountCBussiness.setTradeTime(migrate.getOperTime());
		accountCBussiness.setOperId(migrate.getOperId());
		accountCBussiness.setPlaceId(migrate.getPlaceId());
		accountCBussiness.setAccountId(migrate.getNewAccountId());//新子账户id   
		accountCBussiness.setOldAccountId(migrate.getOldAccountId());//旧子账户id
		//新增的字段
		accountCBussiness.setOperName(migrate.getOperName());
		accountCBussiness.setOperNo(migrate.getOperNo());
		accountCBussiness.setPlaceName(migrate.getPlaceName());
		accountCBussiness.setPlaceNo(migrate.getPlaceNo());
		accountCBussiness.setBusinessId(migrate.getId());
		try {
			migrateDao.save(migrate);
			materialService.updateVehicleId(materialIds, id);
			migrateDetailDao.batchSaveMigrateDetail(migrateDetailList);
			/*accountCInfoHisDao.batchSave(hisList);
			accountCDao.batchUpdate(accList);*/
			accountCBussinessDao.save(accountCBussiness);
			
			
			Customer customer = customerDao.findById(custId);
			AccountCApply accountCapply = accountCApplyDao.findBySubAccId(migrate.getNewAccountId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("218");//218记帐卡迁移
			//暂时记录新的账户
			if(accountCapply!=null)serviceWater.setBankAccount(accountCapply.getBankAccount());//银行账号
			//serviceWater.setAmt(bail.getBailFee());//应收金额
			//serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			if(accountCapply!=null)serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡迁移");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存记帐卡迁移失败");
			e.printStackTrace();
			throw new ApplicationException("保存记帐卡迁移失败");
		}
	}

	
	/**
	 * 查询记帐卡迁移数据
	 * @author gaosiling
	 * @param pager
	 * @param starTime
	 * @param endTime
	 * @param migrate
	 * @param customer
	 * @param newSubaccountno
	 * @param oldSubaccountno
	 */
	@Override
	public Pager findByPager(Pager pager,Date starTime ,Date endTime, Migrate migrate,Customer customer,String newSubaccountno,String oldSubaccountno) {
		try {
			return migrateDao.findByPager(pager, starTime, endTime, migrate, customer, newSubaccountno, oldSubaccountno);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"查询记帐卡迁移申请失败");
			e.printStackTrace();
			throw new ApplicationException("查询记帐卡迁移申请失败");
		}
	}


	/*
	 * 2017-05-19
	 * 保存记帐卡迁移
	 * migrate 迁移记录
	 * rightsCode 迁移的记帐卡卡号
	 * (non-Javadoc)
	 * @see com.hgsoft.accountC.serviceInterface.IMigrateService#saveMigrateReturnMap(com.hgsoft.accountC.entity.Migrate, java.util.List, java.lang.Long)
	 */
	@Override
	public Map<String, Object> saveMigrateReturnMap(Migrate migrate, List<String> rightsCode, Long custId,AccountCApply oldAccountCApply,AccountCApply newAccountCApply,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//创建记帐卡信息历史集合
		Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMigrate_NO");
		
		//设置迁移记录id
		migrate.setId(id);
		
		//创建迁移明细
		List<MigrateDetail> migrateDetailList = new ArrayList<MigrateDetail>();
		MigrateDetail migrateDetail = null;
		for (String cardNo : rightsCode) {
			migrateDetail = new MigrateDetail();
			migrateDetail.setCardNo(cardNo);
			migrateDetail.setOperId(migrate.getOperId());
			migrateDetail.setPlaceId(migrate.getPlaceId());
			//新增的字段
			migrateDetail.setOperName(migrate.getOperName());
			migrateDetail.setOperNo(migrate.getOperNo());
			migrateDetail.setPlaceName(migrate.getPlaceName());
			migrateDetail.setPlaceNo(migrate.getPlaceNo());
			
			migrateDetail.setMigrateId(id);
			migrateDetailList.add(migrateDetail);
			
			/*//创建记帐卡历史对象
					accountCInfoHis = new AccountCInfoHis();
					hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
					accountCInfoHis.setId(hisId);
					accountCInfoHis.setGenReason("8");
//					accountCInfoHis.setAccountId(migrate.getOldAccountId());
					accountCInfoHis.setCardNo(cardNo);
					
					//创建记帐卡信息对象
					accountCInfo = new AccountCInfo();
					accountCInfo.setAccountId(migrate.getNewAccountId());
					accountCInfo.setCardNo(cardNo);
					accountCInfo.setHisSeqId(hisId);
					accList.add(accountCInfo);
					hisList.add(accountCInfoHis);*/
		}
		
		AccountCBussiness accountCBussiness = new AccountCBussiness();
		id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
		accountCBussiness.setId(id);
		accountCBussiness.setUserId(custId);
		accountCBussiness.setState(AccountCBussinessTypeEnum.accCardMigrate.getValue());
		accountCBussiness.setTradeTime(migrate.getOperTime());
		accountCBussiness.setOperId(migrate.getOperId());
		accountCBussiness.setPlaceId(migrate.getPlaceId());
		accountCBussiness.setAccountId(migrate.getNewAccountId());//新子账户id   
		accountCBussiness.setOldAccountId(migrate.getOldAccountId());//旧子账户id
		//新增的字段
		accountCBussiness.setOperName(migrate.getOperName());
		accountCBussiness.setOperNo(migrate.getOperNo());
		accountCBussiness.setPlaceName(migrate.getPlaceName());
		accountCBussiness.setPlaceNo(migrate.getPlaceNo());
		accountCBussiness.setBusinessId(migrate.getId());
		try {
			migrateDao.save(migrate);
			//materialService.updateVehicleId(materialIds, id);
			migrateDetailDao.batchSaveMigrateDetail(migrateDetailList);
			/*accountCInfoHisDao.batchSave(hisList);
			accountCDao.batchUpdate(accList);*/
			accountCBussinessDao.save(accountCBussiness);
			
			
			Customer customer = customerDao.findById(custId);
			AccountCApply accountCapply = accountCApplyDao.findBySubAccId(migrate.getNewAccountId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("218");//218记帐卡迁移
			//暂时记录新的账户
			if(accountCapply!=null)serviceWater.setBankAccount(accountCapply.getBankAccount());//银行账号
			//serviceWater.setAmt(bail.getBailFee());//应收金额
			//serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			if(accountCapply!=null)serviceWater.setBankNo(accountCapply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡迁移");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//扣款账户变更申请回执
			AccCardMigrateReceipt accCardMigrateReceipt = new AccCardMigrateReceipt();
			accCardMigrateReceipt.setTitle("扣款账户变更申请回执");
			accCardMigrateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			accCardMigrateReceipt.setOldApplyAccName(oldAccountCApply.getAccName());
			accCardMigrateReceipt.setOldApplyBankAccount(oldAccountCApply.getBankAccount());
			accCardMigrateReceipt.setOldApplyBankName(oldAccountCApply.getBank());
			accCardMigrateReceipt.setApplyAccName(newAccountCApply.getAccName());
			accCardMigrateReceipt.setApplyBankAccount(newAccountCApply.getBankAccount());
			accCardMigrateReceipt.setApplyBankName(newAccountCApply.getBank());
			String accCardNos = "";
			for(String str : rightsCode){
				accCardNos+=str+"、";
			}
			accCardMigrateReceipt.setAccCardNumber(rightsCode.size()+"");
			accCardMigrateReceipt.setAccCardNos(accCardNos.length()>0?accCardNos.substring(0,accCardNos.length()-1):"");
			Receipt receipt = new Receipt();
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardMigrate.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardMigrate.getName());
			this.saveReceipt(receipt,accountCBussiness,accCardMigrateReceipt,customer);

			resultMap.put("result", "true");
			resultMap.put("migrateId", migrate.getId());
			return resultMap;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存记帐卡迁移失败");
			e.printStackTrace();
			throw new ApplicationException("保存记帐卡迁移失败");
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
