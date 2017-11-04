package com.hgsoft.accountC.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.TransferApplyDao;
import com.hgsoft.accountC.dao.TransferDetailDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.TransferApply;
import com.hgsoft.accountC.entity.TransferDetail;
import com.hgsoft.accountC.serviceInterface.ITransferInquiryService;
import com.hgsoft.clearInterface.dao.StopAcListDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * @FileName transferInquiryService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月22日 下午5:02:10 
*/
@Service
public class TransferInquiryService implements ITransferInquiryService {
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private TransferApplyDao transferApplyDao;
	
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private TransferDetailDao transferDetailDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private StopAcListDao stopAcListDao;
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	private static Logger logger = Logger.getLogger(TransferInquiryService.class.getName());
	
	@Override
	public Pager findByPager(Pager pager, Customer customer, String cardNo, String bankAccount, String startTime,
			String endTime) {
		return transferApplyDao.findTransferApplyList(pager,customer,cardNo,bankAccount,startTime,endTime);
	}

	/**
	 * 保存记帐卡过户数据
	 */
	@Override
	public boolean saveTransfer(TransferApply transferApply,
			AccountCBussiness accountCBussinesstemp, String cardNoRights, Long newCustomerId) {
		Date now = new Date();
		//新客户
		Customer customer = customerDao.findById(accountCBussinesstemp.getUserId());
		/*//创建记帐卡信息历史集合
		List<AccountCInfoHis> hisList = new ArrayList<AccountCInfoHis>();
		AccountCInfoHis accountCInfoHis = null;
		Long hisId = null;
		
		//创建记帐卡信息的列表集合
		List<AccountCInfo> accList = new ArrayList<AccountCInfo>();
		AccountCInfo accountCInfo = null;*/
		
		Long id = sequenceUtil.getSequenceLong("SEQ_CSMSTransferApply_NO");;
		//过户记录
		transferApply.setId(id);
		
		//创建过户明细
		List<TransferDetail> transferDetailList = new ArrayList<TransferDetail>();
		TransferDetail transferDetail = null;
		String[] rigths = cardNoRights.split(",");
		//创建记帐卡业务记录集合
		List<AccountCBussiness> bussList = new ArrayList<AccountCBussiness>();
		AccountCBussiness accountCBussiness = null;
		AccountCInfo accountC = null;
		List<ServiceWater> serviceWaters = new ArrayList<ServiceWater>();
		for (String cardNo : rigths) {
			
			accountC = accountCInfoDao.findByCardNo(cardNo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountC.getId());
			if(carObuCardInfo!=null && carObuCardInfo.getVehicleID()!=null){
				return false;
			}
			
			
			System.out.println("cardNo:="+cardNo);
			//TODE 未完成
			transferDetail = new TransferDetail();
			transferDetail.setCardNo(cardNo);
			transferDetail.setTransferId(transferApply.getId());
			transferDetail.setOperTime(now);
			transferDetail.setOperId(transferApply.getOperId());
			transferDetail.setPlaceId(transferApply.getPlaceId());
			//新增的字段
			transferDetail.setOperName(transferApply.getOperName());
			transferDetail.setOperNo(transferApply.getOperNo());
			transferDetail.setPlaceName(transferApply.getPlaceName());
			transferDetail.setPlaceNo(transferApply.getPlaceNo());
			
			transferDetailList.add(transferDetail);
			
			/*//创建记帐卡历史对象
			accountCInfoHis = new AccountCInfoHis();
			hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
			accountCInfoHis.setId(hisId);
			accountCInfoHis.setGenReason("7");
			accountCInfoHis.setCardNo(cardNo);
			hisList.add(accountCInfoHis);
			
			//创建记帐卡信息对象
			accountCInfo = new AccountCInfo();
			accountCInfo.setCustomerId(newCustomerId);
			accountCInfo.setAccountId(transferApply.getNewaccountId());
			accountCInfo.setCardNo(cardNo);
			accountCInfo.setHisSeqId(hisId);
			accList.add(accountCInfo);*/
			
			//记帐卡业务记录
			id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(id);
			accountCBussiness.setCardNo(cardNo);
			accountCBussiness.setUserId(newCustomerId);
			accountCBussiness.setOldUserId(accountCBussinesstemp.getUserId());
			accountCBussiness.setAccountId(transferApply.getNewaccountId());
			accountCBussiness.setOldAccountId(transferApply.getOldaccountId());
			accountCBussiness.setBusinessId(transferApply.getId());
			accountCBussiness.setState("19");
			//---------------------------------------------------------------
			accountCBussiness.setTradeTime(now);
			accountCBussiness.setOperId(transferApply.getOperId());
			accountCBussiness.setPlaceId(transferApply.getPlaceId());
			//新增的字段
			accountCBussiness.setOperName(transferApply.getOperName());
			accountCBussiness.setOperNo(transferApply.getOperNo());
			accountCBussiness.setPlaceName(transferApply.getPlaceName());
			accountCBussiness.setPlaceNo(transferApply.getPlaceNo());
			
			bussList.add(accountCBussiness);
			
			
			//------------新加代码----------
			
			//新账号
			AccountCApply accountCapply = accountCApplyDao.findBySubAccId(accountCBussiness.getAccountId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(cardNo);
			serviceWater.setSerType("219");//219记帐卡过户
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
			serviceWater.setRemark("自营客服系统：记帐卡过户");
			serviceWater.setOperTime(new Date());
			
			serviceWaters.add(serviceWater);
		}
		
		try {
			transferApplyDao.save(transferApply);
			transferDetailDao.batchSaveTransferDetail(transferDetailList);
			/*accountCInfoHisDao.batchSave(hisList);
			accountCDao.batchUpdateWithCustomer(accList);*/
			accountCBussinessDao.batchSave(bussList);
			bussList.get(0).setUserId(accountCBussinesstemp.getUserId());//回执打印根据当前验证用户
			receiptDao.saveByBussiness(null, null, null, null, bussList.get(0));
			
			
			for(ServiceWater serviceWater:serviceWaters){
				serviceWaterDao.save(serviceWater);
			}
			
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存记帐卡过户失败");
			e.printStackTrace();
			throw new ApplicationException("保存记帐卡过户失败");
		}
		return true;
	}

	@Override
	public TransferApply getById(Long id) {
		TransferApply transferApply = transferApplyDao.findById(id);
		return transferApply;
	}

	@Override
	public List<Map<String, Object>> findCardById(Long id) {
		return transferDetailDao.findCardByTransferID(id);
	}

	@Override
	public List<Map<String, Object>> getCustomerByBank(String oldbankaccount) {
		return transferApplyDao.getCustomerByBank(oldbankaccount);
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年2月20日
	 */
	@Override
	public AccountCApply findBySubAccId(Long id) {
		return accountCApplyDao.findBySubAccId(id);
	}

//	/**
//	 * @Descriptioqn:
//	 * @param bankAccount
//	 * @return
//	 * @author lgm
//	 * @date 2017年2月20日
//	 */
//	@Override
//	public List<Map<String, Object>> getStopAcList(String bankAccount) {
//		return stopAcListDao.findByACBAccount(bankAccount);
//	}
	
	
	

}
