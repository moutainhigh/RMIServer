package com.hgsoft.prepaidC.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.clearInterface.dao.CardObuDao;
import com.hgsoft.clearInterface.entity.PrepaidCBalance;
import com.hgsoft.clearInterface.entity.StoreCardRecharge;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.InvoiceChangeFlowStateEnum;
import com.hgsoft.common.Enum.InvoicePrintEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.SerItemEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardInvoiceTypeChangeReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardIssueUpdateReceipt;
import com.hgsoft.prepaidC.dao.AddRegDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.dao.ElectronicPurseDao;
import com.hgsoft.prepaidC.dao.InvoiceChangeFlowDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.ElectronicPurse;
import com.hgsoft.prepaidC.entity.ElectronicPurseHis;
import com.hgsoft.prepaidC.entity.InvoiceChangeFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.prepaidC.entity.PrepaidCTransferDetail;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.PropertiesUtil;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;

@Service
public class PrepaidCService implements IPrepaidCService{
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private ElectronicPurseDao electronicPurseDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private AddRegDao addRegDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private InvoiceChangeFlowDao invoiceChangeFlowDao;
	
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CardObuDao cardObuDao;
	@Resource
	private BillGetDao billGetDao;
	
	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());
	
//	@Resource
//	private ITransFeeService transFeeService;
	
	@Resource
	private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
	@Resource
	public void setPrepaidCDao(PrepaidCDao prepaidCDao) {
		this.prepaidCDao = prepaidCDao;
	}
	
	
	@Override
	public Pager prepaidCList(Pager pager, Customer customer,String cardNo) {
		Pager pagers=prepaidCDao.prepaidCList(pager, customer,cardNo);
		return pagers;
	}
	
	@Override
	public Pager prepaidCListForAMMS(Pager pager, Customer customer,String cardNo,String cardType,String bankNo) {
		Pager pagers=prepaidCDao.prepaidCListForAMMS(pager, customer, cardNo, cardType, bankNo);
		return pagers;
	}


	@Override
	public String saveUnusable(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness,
			ServiceFlowRecord serviceFlowRecord,boolean result) {
		return prepaidCUnifiedInterfaceService.saveUnusable(prepaidC, prepaidCBussiness,serviceFlowRecord,result);
	}
	
	@Override
	public String saveIssue(PrepaidC prepaidC,ElectronicPurse electronicPurse,CarObuCardInfo carObuCardInfo, PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,BillGet billGet,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveIssue(prepaidC, electronicPurse, carObuCardInfo, prepaidCBussiness, mainAccountInfo, billGet, params);
	}
	
	@Override
	public String saveIssueForAMMS(PrepaidC prepaidC,ElectronicPurse electronicPurse,CarObuCardInfo carObuCardInfo, PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,BillGet billGet,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveIssueForAMMS(prepaidC, electronicPurse, carObuCardInfo, prepaidCBussiness, mainAccountInfo, billGet, params);
	}
	
	@Override
	public String delPrepaidC(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, PrepaidCHis prepaidCHis, ElectronicPurse electronicPurse,ElectronicPurseHis electronicPurseHis,MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,BillGet billGet,BillGetHis billGetHis) {
		return prepaidCUnifiedInterfaceService.delPrepaidC(prepaidC, carObuCardInfo, prepaidCHis, electronicPurse, electronicPurseHis, mainAccountInfo, prepaidCBussiness, billGet, billGetHis);
	}
	
	@Override
	public String delPrepaidCForAMMS(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, PrepaidCHis prepaidCHis, ElectronicPurse electronicPurse,ElectronicPurseHis electronicPurseHis,MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,BillGet billGet,BillGetHis billGetHis) {
		return prepaidCUnifiedInterfaceService.delPrepaidCForAMMS(prepaidC, carObuCardInfo, prepaidCHis, electronicPurse, electronicPurseHis, mainAccountInfo, prepaidCBussiness, billGet, billGetHis);
	}
	
	@Override
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,Integer type,List<ReturnFee> returnFeeList) {
		return prepaidCUnifiedInterfaceService.saveRecharge(prepaidCBussiness, addRegDetail, mainAccountInfo, type, returnFeeList);
	}
	
	@Override
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,Integer type,List<ReturnFee> returnFeeList,RechargeInfo rechargeInfo) {
		return prepaidCUnifiedInterfaceService.saveRecharge(prepaidCBussiness, addRegDetail, mainAccountInfo, type, returnFeeList, rechargeInfo);
	}
	
	@Override
	public void updateRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList, Map<String,Object> params) {
		prepaidCUnifiedInterfaceService.updateRecharge(prepaidCBussiness, addRegDetail, mainAccountInfo,returnFeeList,params);
	}
	
	@Override
	public void updateReversal(PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList,Long oldBussinessID, Map<String,Object> params) {
		prepaidCUnifiedInterfaceService.updateReversal(prepaidCBussiness, mainAccountInfo, returnFeeList, oldBussinessID, params);
	}

	@Override
	public PrepaidC findByPrepaidCNo(String cardNo) {
		return prepaidCDao.findByPrepaidCNo(cardNo);
	}


	@Override
	public String saveGainCard(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC, ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness rechargePrepaidCBussiness, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveGainCard(mainAccountInfo, prepaidC, newPrepaidC, electronicPurse, prepaidCBussiness, serviceFlowRecord, rechargePrepaidCBussiness, params);
	}
	
	@Override
	public String saveGainCardForAMMS(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC, ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness rechargePrepaidCBussiness, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveGainCardForAMMS(mainAccountInfo, prepaidC, newPrepaidC, electronicPurse, prepaidCBussiness, serviceFlowRecord, rechargePrepaidCBussiness, params);
	}

	/**
	 * 根据卡号获取客户信息
	 */
	@Override
	public Customer getCustomerByPrepaidCardNo(String cardNo) {
		return customerDao.getCustomerByPrepaidCardNo(cardNo);
	}

	@Override
	public void updatePrepaidC(PrepaidC prepaidC) {
		prepaidCDao.update(prepaidC);
	}
	
	@Override
	public String saveReplaceCard(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC,ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveReplaceCard(mainAccountInfo, prepaidC, newPrepaidC, electronicPurse, prepaidCBussiness, serviceFlowRecord,params);
	}
	
	@Override
	public String saveReplaceCardForAMMS(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC,ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveReplaceCardForAMMS(mainAccountInfo, prepaidC, newPrepaidC, electronicPurse, prepaidCBussiness, serviceFlowRecord, params);
	}
	
	@Override
	public boolean saveReversal(MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,PrepaidCBussiness oldPrepaidCBussiness) {
		return prepaidCUnifiedInterfaceService.saveReversal(mainAccountInfo, prepaidCBussiness, oldPrepaidCBussiness);
	}

	@Override
	public PrepaidC find(PrepaidC prepaidC) {
		return prepaidCDao.find(prepaidC);
	}
	
	@Override
	public void saveLock(PrepaidC prepaidC, Map<String,Object> params) {
		prepaidCUnifiedInterfaceService.saveLock(prepaidC, params);
	}

	@Override
	public void saveLock(PrepaidC prepaidC,String systemType) {
		prepaidCUnifiedInterfaceService.saveLock(prepaidC,systemType);
	}
	
	@Override
	public void unLock(PrepaidC prepaidC, Map<String,Object> params) {
		prepaidCUnifiedInterfaceService.unLock(prepaidC, params);
	}

	@Override
	public boolean updatePwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.updatePwd(prepaidC,prepaidCBussiness,params);
	}
	
	@Override
	public boolean resetPwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness, Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.resetPwd(prepaidC, prepaidCBussiness, params);
	}
	
	@Override
	public boolean isBlack(String cardNo) {
		return prepaidCDao.isBlack(cardNo);
	}

	@Override
	public void changeInvoicePrint(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness,Map<String,Object> params) {
		try{
			Date date = new Date();
			//将卡信息移至历史信息
			PrepaidCHis prepaidCHis = new PrepaidCHis(date,"7",prepaidC);
			prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
			prepaidCDao.saveHis(prepaidCHis);
			
			if(prepaidC.getInvoicePrint().equals("2")){
				//InvoicePrint由定时器处理生效标志
				//prepaidC.setInvoicePrint("1");//设置为充值即领
				prepaidC.setInvoiceChangeFlag(1l);
			}
			prepaidC.setHisSeqID(prepaidCHis.getId());
			prepaidCDao.update(prepaidC);
			prepaidCBussiness.setBusinessId(prepaidC.getHisSeqID());
			prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			InvoiceChangeFlow invoiceChangeFlow = new InvoiceChangeFlow();
			// TODO: 2017/3/29 这里不用设置系统余额吗? 
			invoiceChangeFlow.setId(sequenceUtil.getSequenceLong("SEQ_CSMSInvoiceChangeFlow_NO"));
			invoiceChangeFlow.setCardNo(prepaidC.getCardNo());
			invoiceChangeFlow.setCustomerId(prepaidC.getCustomerID());
			invoiceChangeFlow.setState(InvoiceChangeFlowStateEnum.noeffect.getValue());
			invoiceChangeFlow.setRealDate(date);
			invoiceChangeFlow.setOperId(prepaidCBussiness.getOperid());
			invoiceChangeFlow.setOperNo(prepaidCBussiness.getOperNo());
			invoiceChangeFlow.setOperName(prepaidCBussiness.getOperName());
			invoiceChangeFlow.setPlaceId(prepaidCBussiness.getPlaceid());
			invoiceChangeFlow.setPlaceNo(prepaidCBussiness.getPlaceNo());
			invoiceChangeFlow.setPlaceName(prepaidCBussiness.getPlaceName());
			invoiceChangeFlowDao.save(invoiceChangeFlow);
			
			
			Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidCBussiness.getCardno());
			serviceWater.setSerType("517");//储值卡发票类型变更
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(date);
			serviceWater.setRemark("自营客服系统：储值卡发票类型变更");
			serviceWaterDao.save(serviceWater);

			//储值卡发票类型变更申请回执
			PreCardInvoiceTypeChangeReceipt preCardInvoiceTypeChange = new PreCardInvoiceTypeChangeReceipt();
			preCardInvoiceTypeChange.setTitle("储值卡发票类型变更申请回执");
			preCardInvoiceTypeChange.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			preCardInvoiceTypeChange.setPreCardNo(prepaidCBussiness.getCardno());
			preCardInvoiceTypeChange.setOldPreCardInvoiceprint(InvoicePrintEnum.monthSet.getName());
			preCardInvoiceTypeChange.setPreCardInvoiceprint(InvoicePrintEnum.rechargePrint.getName());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardInvoiceTypeChange.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardInvoiceTypeChange.getName());
			receipt.setCardNo(preCardInvoiceTypeChange.getPreCardNo());
			this.saveReceipt(receipt,prepaidCBussiness,preCardInvoiceTypeChange,customer);

		}catch(Exception e){
			throw new ApplicationException("发票状态变更失败", e);
		}
		
	}

	@Override
	public PrepaidC findByCustomerId(Long id) {
		return prepaidCDao.findByCustomerId(id);
	}
	
	@Override
	public Pager findByCustomer(Pager pager, Customer customer) {
		return prepaidCDao.findByCustomer(pager, customer);
	}

	@Override
	public PrepaidC findByPrepaidCNoToGain(String cardNo) {
		try {
			return prepaidCDao.findByCardNoToGain(cardNo);
		} catch (ApplicationException e) {
			throw new ApplicationException("根据卡号查询记帐卡", e);
		}
	}

	@Override
	public PrepaidC findById(Long id) {
		return prepaidCDao.findById(id);
	}


	@Override
	public boolean saveTransfer(PrepaidCTransfer prepaidCTransfer, String cardNoRights) {
		Customer customer = customerDao.findById(prepaidCTransfer.getOldCustomerId());
		//过户申请--单条
		prepaidCTransfer.setId(sequenceUtil.getSequenceLong("SEQ_CSMSPrepaidCTransfer_NO"));
		//过户明细列表
		List<PrepaidCTransferDetail> transferDetailList = new ArrayList<PrepaidCTransferDetail>();
		//储值卡
		List<PrepaidC> prepaidCList = new ArrayList<PrepaidC>();
		//储值卡历史
		//车卡绑定
		List<String> cardNoList = new ArrayList<String>();
		//储值卡业务记录 
		List<PrepaidCBussiness> prepaidCBussinessList = new ArrayList<PrepaidCBussiness>();
		//流水记录 
		List<ServiceFlowRecord> serviceFlowRecordList = new ArrayList<ServiceFlowRecord>();
		
		List<BillGet> billGetList = new ArrayList<BillGet>();
		
		String[] rigths = cardNoRights.split(",");
		PrepaidC prepaidC = null;
		for (String cardNo : rigths) {
			prepaidC = prepaidCDao.findByCardNoToGain(cardNo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
			if(carObuCardInfo!=null && carObuCardInfo.getVehicleID()!=null){
				return false;
			}
			
			
			//如果（添加）字段需要在DAO语句添加参数
			Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO");
			System.out.println("cardNo:="+cardNo);
			//申请明细--批处理添加
			PrepaidCTransferDetail transferDetail = new PrepaidCTransferDetail();
			transferDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSPrepaidCTransferDt_NO"));
			transferDetail.setTransferId(prepaidCTransfer.getId());
			transferDetail.setCardNo(cardNo);
			transferDetailList.add(transferDetail);
			//储值卡--批处理修改
			prepaidC = new PrepaidC();
			prepaidC.setCardNo(cardNo);
			prepaidC.setCustomerID(prepaidCTransfer.getNewCustomerId());
			prepaidC.setAccountID(prepaidCTransfer.getNewAccountId());
			prepaidC.setBind("0");
			prepaidC.setHisSeqID(hisId);
			prepaidC.setSaleOperId(prepaidCTransfer.getOperId());
			prepaidC.setOperNo(prepaidCTransfer.getOperNo());
			prepaidC.setOperName(prepaidCTransfer.getOperName());
			prepaidC.setSalePlaceId(prepaidCTransfer.getPlaceId());
			prepaidC.setPlaceNo(prepaidCTransfer.getPlaceNo());
			prepaidC.setPlaceName(prepaidCTransfer.getPlaceName());
			prepaidCList.add(prepaidC);
			//卡片车牌关系解绑--根据卡号们批处理修改
			cardNoList.add(cardNo);
			//储值卡业务记录--批处理添加
			PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
			prepaidCBussiness.setUserid(prepaidCTransfer.getOldCustomerId());
			prepaidCBussiness.setCardno(cardNo);
			prepaidCBussiness.setState("18");  //状态18过户
			prepaidCBussiness.setOperid(prepaidCTransfer.getOperId());
			prepaidCBussiness.setOperNo(prepaidCTransfer.getOperNo());
			prepaidCBussiness.setOperName(prepaidCTransfer.getOperName());
			prepaidCBussiness.setPlaceid(prepaidCTransfer.getPlaceId());
			prepaidCBussiness.setPlaceNo(prepaidCTransfer.getPlaceNo());
			prepaidCBussiness.setPlaceName(prepaidCTransfer.getPlaceName());
			prepaidCBussinessList.add(prepaidCBussiness);
			//流水记录--批处理添加
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord.setServiceFlowNO(sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO")+"");
			serviceFlowRecord.setServicePTypeCode(1);
			serviceFlowRecord.setServiceTypeCode(9);   //储值卡过户9
			//serviceFlowRecord.setCreateTime(new Date());  //已嵌入到SQL
			serviceFlowRecord.setClientID(prepaidCTransfer.getOldCustomerId());
			serviceFlowRecord.setCardTagNO(cardNo);
			serviceFlowRecord.setOperID(prepaidCTransfer.getOperId());
			serviceFlowRecord.setOperNo(prepaidCTransfer.getOperNo());
			serviceFlowRecord.setOperName(prepaidCTransfer.getOperName());
			serviceFlowRecord.setPlaceID(prepaidCTransfer.getPlaceId());
			serviceFlowRecord.setPlaceNo(prepaidCTransfer.getPlaceNo());
			serviceFlowRecord.setPlaceName(prepaidCTransfer.getPlaceName());
			serviceFlowRecordList.add(serviceFlowRecord);
			
			BillGet billGet = new BillGet();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMS_bill_get_his_NO");
			billGet.setHisSeqId(seq);
			billGet.setCardBankNo(cardNo);
			billGet.setMainId(prepaidCTransfer.getNewCustomerId());
			billGetList.add(billGet);
			
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidCBussiness.getCardno());
			serviceWater.setSerType("518");//储值卡过户
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("自营客服系统：储值卡过户");
			serviceWaterDao.save(serviceWater);
			
		}
		try {
			prepaidCDao.batchSaveHis(cardNoList);
			prepaidCDao.saveTransfer(prepaidCTransfer);
			prepaidCDao.batchSaveTransferDetail(transferDetailList);
			prepaidCDao.batchUpdatePrepaidC(prepaidCList);
			prepaidCDao.batchSaveBillGetHis(billGetList);
			prepaidCDao.batchUpdateBill(billGetList);
			carObuCardInfoDao.batchUpdateCardObu(cardNoList);
			prepaidCBussinessDao.batchSavePrepaidCBussiness(prepaidCBussinessList);
			PrepaidCBussiness prepaidCBussiness =prepaidCBussinessList.get(0);
			prepaidCBussiness.setId(prepaidCTransfer.getId());
			receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);
			serviceFlowRecordDao.batchSaveServiceFlowRecord(serviceFlowRecordList);
		} catch (ApplicationException e) {
			throw new ApplicationException("保存储值卡过户失败", e);
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> listCardByCustomerId(Long customerId) {
		return prepaidCDao.listCardByCustomerId(customerId);
	}
	
	@Override
	public void updateGainCardRecharge(PrepaidCBussiness prepaidCBussiness) {
		try {
			prepaidCBussinessDao.update(prepaidCBussiness);
			//保存至铭鸿清算系统
			//获取资金转移表里面的转移金额
			DbasCardFlow dbasCard = dbasCardFlowDao.find(prepaidCBussiness.getCardno());
			PrepaidCBalance prepaidCBalance = new PrepaidCBalance(prepaidCBussiness.getOldCardno(),
					dbasCard.getCardAmt(),new BigDecimal(0),
					dbasCard.getApplyTime(),null,null,new Date());
			//旧卡领取余额
			cardObuDao.saveCardBalance(prepaidCBalance);
			//新卡生成充值数据
			StoreCardRecharge storeCardRecharge = new StoreCardRecharge();
			BigDecimal balanceBefore = prepaidCBussiness.getBeforebalance();
			if(balanceBefore==null){
				balanceBefore = new BigDecimal(0);
			}
			storeCardRecharge.setBalReq(balanceBefore.multiply(new BigDecimal("0.01")));//充值前余额
			storeCardRecharge.setBalSur(balanceBefore.add(dbasCard.getCardAmt()).multiply(new BigDecimal("0.01")));//充值后余额
			storeCardRecharge.setCardCode(prepaidCBussiness.getCardno());//储值卡卡号
			storeCardRecharge.setDealtype("03");//交易类型
			storeCardRecharge.setId(null);//
			storeCardRecharge.setLinkReq(prepaidCBussiness.getOnlinetradeno());//充值前联机号
			storeCardRecharge.setLinkSur(prepaidCBussiness.getOfflinetradeno());//充值后联机号
			storeCardRecharge.setMoneySur(prepaidCBussiness.getRealprice().multiply(new BigDecimal("0.01")));//
			if(prepaidCBussiness.getReturnMoney() != null)storeCardRecharge.setReturnMoneySur(prepaidCBussiness.getReturnMoney().multiply(new BigDecimal("0.01")));//
			storeCardRecharge.setTimeReq(prepaidCBussiness.getTradetime());//
			//storeCardRecharge.setTrReq(prepaidCBussiness.getOfflineTradenoReq().toString());//充值前脱机号
			storeCardRecharge.setTrSur(prepaidCBussiness.getOfflinetradeno());//充值后脱机号
			storeCardRecharge.setUpdatetime(new Date());//
			//storeCardRechargeDao.savePrepaidCCharge(storeCardRecharge);//
			
		} catch (ApplicationException e) {
			throw new ApplicationException("有卡换卡储值卡余额转到新卡，业务记录更新失败", e);
		}
		
	}

	/*
	 * 获取转移金额
	 */
	@Override
	public String getTransFee(String cardNo) {
		String result = null;
		/*try {
			 Client proxy = ClientProxy.getClient(transFeeService);
			 HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
			 HTTPClientPolicy policy = new HTTPClientPolicy();
			 policy.setConnectionTimeout(10); //连接超时时间
			 policy.setReceiveTimeout(20);//请求超时时间.
			 conduit.setClient(policy);
			result = transFeeService.getTransFee(cardNo);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("获取转移金额失败");
			//throw new ApplicationException();
		}*/
		return result;
	}

	/*
	 *锁定转移金额 
	 */
	@Override
	public String lockTransFee(String cardNos, String Fees) {
		String result =null;
		/*try {
			Client proxy = ClientProxy.getClient(transFeeService);
			 HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
			 HTTPClientPolicy policy = new HTTPClientPolicy();
			 policy.setConnectionTimeout(10); //连接超时时间
			 policy.setReceiveTimeout(20);//请求超时时间.
			 conduit.setClient(policy);
			result = transFeeService.lockTransFee(cardNos, Fees);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("锁定转移金额失败");
			//throw new ApplicationException();
		}*/
		return result;
		
		
	}

	/*
	 * 充值成功后，通知清算，把状态改为充值成功
	 */
	@Override
	public String notifyTransFee(String cardNos, String Fees) {
		String result = null;
		/*try {
			Client proxy = ClientProxy.getClient(transFeeService);
			HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();
			policy.setConnectionTimeout(10); //连接超时时间
			policy.setReceiveTimeout(20);//请求超时时间.
			conduit.setClient(policy);
			result = transFeeService.notifyTransFee(cardNos, Fees);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("通知清算失败");
			//throw new ApplicationException();
		}*/
		return result;
		
		
	}
	
	public List<Map<String, Object>> findCards(String cardNo){
		DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(cardNo);
		if(null == dbasCardFlow){
			return null;
		}else{
			return dbasCardFlowDao.findCards(dbasCardFlow.getCardNo());
		}
	}

	/**
	 * 修改写卡标志
	 */
	@Override
	public Map<String, Object> updateWriteCardFlag(String cardNo) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			prepaidCDao.updateWriteCardFlag(cardNo);
			map.put("result", "1");
			map.put("message", "修改写卡标志成功");
		} catch (ApplicationException e) {
			throw new ApplicationException("修改写卡标志失败", e);
		}
		return map;
	}


	@Override
	public InvoiceChangeFlow findInvoiceChangeFlowByCardNo(String cardNo) {
		InvoiceChangeFlow invoiceChangeFlow = invoiceChangeFlowDao.findByCardNo(cardNo);
		return invoiceChangeFlow;
	}


	@Override
	public Pager getprepaidCInfo(Pager pager, String code, String idCardString,String flag,String secondno) {
		return prepaidCDao.findPrepaidCInfo(pager, code, idCardString,flag,secondno);
	}


	@Override
	public Map<String, Object> updateServiceType(String cardNo, String seritem, Customer customer, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			BillGet billGet = this.billGetDao.findPreCardNo(cardNo);

			prepaidCDao.updateServiceType(cardNo,seritem);

			//储值卡服务方式修改回执
			PreCardIssueUpdateReceipt preCardIssueUpdateReceipt = new PreCardIssueUpdateReceipt();
			preCardIssueUpdateReceipt.setTitle("储值卡服务方式修改回执");
			preCardIssueUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			preCardIssueUpdateReceipt.setPreCardNo(cardNo);
			preCardIssueUpdateReceipt.setOldSerItem(this.getSerItemName(billGet.getSerItem()));
			preCardIssueUpdateReceipt.setNewSerItem(this.getSerItemName(seritem));
			preCardIssueUpdateReceipt.setCustomerNo(customer.getUserNo());
			preCardIssueUpdateReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
			preCardIssueUpdateReceipt.setCustomerIdCode(customer.getIdCode());
			preCardIssueUpdateReceipt.setCustomerName(customer.getOrgan());
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.prepaidC.getValue());
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardIssuceUpdate.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardIssuceUpdate.getName());
			receipt.setCardNo(preCardIssueUpdateReceipt.getPreCardNo());
			receipt.setCreateTime(new Date());
			receipt.setPlaceId(cusPointPoJo.getCusPoint());
			receipt.setPlaceNo(cusPointPoJo.getCusPointCode());
			receipt.setPlaceName(cusPointPoJo.getCusPointName());
			receipt.setOperId(sysAdmin.getId());
			receipt.setOperNo(sysAdmin.getStaffNo());
			receipt.setOperName(sysAdmin.getUserName());
			receipt.setOrgan(customer.getOrgan());
			receipt.setContent(JSONObject.fromObject(preCardIssueUpdateReceipt).toString());
			this.receiptDao.saveReceipt(receipt);

			map.put("result", "1");
			map.put("message", "修改服务方式成功");
		} catch (ApplicationException e) {
			throw new ApplicationException("修改服务方式失败", e);
		}
		return map;
	}


	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年6月10日
	 */
	@Override
	public String getCardFee(Long operId,String operName) {
		try {
			
			JSONObject json = new JSONObject();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(new Date());
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", format.format(new Date()));
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getServiceParam.do";
//				System.out.println(url);
//				System.out.println("&key=Customer Registration time&operateId="+operateId+"&operateName="+operateName+"operateTime"+(new Date()));
			Map map = HttpUtil.callClientByHTTP(url, "au_token="+data+"&key=Prepaid card issue fee&operateId="+operId+"&operateName="+operName+"&operateTime="+nowTime, "POST");
			BigDecimal cost = null;
			if(map!=null && "0".equals(map.get("flag"))){
				cost = new BigDecimal((String)map.get("value"));
				return cost.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年6月10日
	 */
	@Override
	public BigDecimal getFaceValue(Long operId,String operName) {
		try {
			
			JSONObject json = new JSONObject();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(new Date());
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", format.format(new Date()));
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getServiceParam.do";
//				System.out.println(url);
//				System.out.println("&key=Customer Registration time&operateId="+operateId+"&operateName="+operateName+"operateTime"+(new Date()));
			Map map = HttpUtil.callClientByHTTP(url, "au_token="+data+"&key=StoreCard Face Value&operateId="+operId+"&operateName="+operName+"&operateTime="+nowTime, "POST");
			BigDecimal cost = null;
			if(map!=null && "0".equals(map.get("flag"))){
				cost = new BigDecimal((String)map.get("value"));
				return cost;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List findCustomerList(String code, String idCardString) {
		return prepaidCDao.findCustomerList(code,idCardString);
	}


	@Override
	public DbasCardFlow findPreCancelDbasCardFlow(String cardNo) {
		return dbasCardFlowDao.findPreCancelDbasCardFlow(cardNo);
	}


	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年8月8日
	 */
	@Override
	public List<Map<String, Object>> findAllCusPo() {
		return prepaidCDao.findAllCusPo();
	}


	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年8月10日
	 */
	@Override
	public String findCusPoById(Long id) {
		Map<String,Object> map = prepaidCDao.findCusPoById(id);
		if(map!=null)
			return (String) map.get("NAME");
		return null;
	}
	
	public Map<String,Object> findFirstIssueFaceValue(String cardNo){
		return prepaidCDao.findFirstIssueFaceValue(cardNo);
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param prepaidCBussiness 储值卡业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, PrepaidCBussiness prepaidCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.prepaidC.getValue());
		receipt.setCreateTime(prepaidCBussiness.getTradetime());
		receipt.setPlaceId(prepaidCBussiness.getPlaceid());
		receipt.setPlaceNo(prepaidCBussiness.getPlaceNo());
		receipt.setPlaceName(prepaidCBussiness.getPlaceName());
		receipt.setOperId(prepaidCBussiness.getOperid());
		receipt.setOperNo(prepaidCBussiness.getOperName());
		receipt.setOperName(prepaidCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	@Override
	public void updateFirstRecharge(String firstRecharge, Long id) {
		prepaidCDao.updateFirstRecharge(firstRecharge, id);
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
				result+= SerItemEnum.getName(temp)+"，";
			}
		}
		if(result.length()>0){
			result = result.substring(0,result.length()-1);
		}
		return result;
	}


	@Override
	public BigDecimal findCardSysBalance(String cardNo) {
		// TODO Auto-generated method stub
		Map<String,Object> map = prepaidCDao.findCardSysBalance(cardNo);
		if(map!=null)
			return (BigDecimal) map.get("cardbalance");
		return null;
	}


	/**
	 * @Descriptioqn:
	 * @param type
	 * @return
	 * @author lgm
	 * @date 2017年10月12日
	 */
	@Override
	public List<Map<String, Object>> findSalesType(String type,String salesState,String salesFlag){
		return prepaidCDao.findSalesType(type,salesState,salesFlag);
	}

	@Override
	public Pager findAllCardInfosByUserNo(String userNo, Pager pager) {
		return prepaidCDao.findAllCardInfosByUserNo(userNo, pager);
	}

	@Override
	public boolean saveRechargeGainCard(PrepaidCBussiness prepaidCBussiness, DbasCardFlow dbasCardFlow) {
		return prepaidCUnifiedInterfaceService.saveRechargeGainCard(prepaidCBussiness, dbasCardFlow);
	}

	@Override
	public DbasCardFlow findDisCompleteByOldCardNo(String oldCardNo) {
		return dbasCardFlowDao.findDisCompleteByOldCardNo(oldCardNo);
	}
}

