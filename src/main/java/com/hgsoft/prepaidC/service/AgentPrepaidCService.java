package com.hgsoft.prepaidC.service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.dao.ScreturnreqDao;
import com.hgsoft.clearInterface.dao.ScreturnreqSendDao;
import com.hgsoft.clearInterface.dao.StoreCardRechargeDao;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.*;
import com.hgsoft.customer.entity.*;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.invoice.dao.AddBillDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.prepaidC.dao.*;
import com.hgsoft.prepaidC.entity.*;
import com.hgsoft.prepaidC.serviceInterface.IAgentPrepaidCService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceFundMonitor;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.serviceInterface.IServiceFundMonitorService;
import com.hgsoft.unifiedInterface.service.AgentPrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.SequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentPrepaidCService implements IAgentPrepaidCService{

	private static final Logger logger = LoggerFactory.getLogger(AgentPrepaidCService.class);

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private IPrepaidCBussinessService prepaidCBussinessService;
	@Resource
	private CardSecondIssuedService cardSecondIssuedService;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private ElectronicPurseDao electronicPurseDao;
	@Resource
	private BillGetDao billGetDao;
	@Resource
	private BillGetHisDao billGetHisDao;
	@Resource
	private CustomerDao customerDao;
	/*@Resource
	private SCinfoDao sCinfoDao;*/
	@Resource
	private ReturnFeeDao returnFeeDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/

	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private ScreturnreqSendDao screturnreqSendDao;
	@Resource
	private ScreturnreqDao screturnreqDao;
	/*@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;
	@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;*/
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	@Resource
	private ScAddDao scAddDao;
	
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;
	@Resource
	private IBlackListService blackListService;
	
	
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private AgentPrepaidCUnifiedInterfaceService agentPrepaidCUnifiedInterfaceService;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private IPrepaidCService prepaidCService;
	@Resource
	private AddBillDao addBillDao;
	@Resource
	private StoreCardRechargeDao storeCardRechargeDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private IServiceFundMonitorService serviceFundMonitorService;
	@Resource
	private ICardObuService cardObuService;


	@Resource
	public void setPrepaidCDao(PrepaidCDao prepaidCDao) {
		this.prepaidCDao = prepaidCDao;
	}
	@Override
	public String saveIssue(PrepaidC prepaidC, ElectronicPurse electronicPurse, CarObuCardInfo carObuCardInfo,
			PrepaidCBussiness prepaidCBussiness, MainAccountInfo mainAccountInfo, BillGet billGet) {
		/**
		 * saveGainCard 储值卡发行保存 1、扣减主账户金额 2、储值卡发行记录 3、电子钱包记录 4、车卡标签绑定关系
		 * 5、储值卡业务记录 6、客服流水 7、操作日志
		 */

		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryServiceForAgent.omsInterface(prepaidC.getCardNo(), "1", interfaceRecord,"issue",
					null,null,"",
					"1","",null,prepaidC.getRealCost(),"21");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
			if(m!=null){
				prepaidC.setStartDate((Date)m.get("startDate"));
				prepaidC.setEndDate((Date)m.get("endDate"));
			}
			
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewPrepaidC(prepaidC);
			unifiedParam.setType("11");
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			// 储值卡发行记录
			BigDecimal PrePaidC_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_NO");
			prepaidC.setId(Long.valueOf(PrePaidC_NO.toString()));
			prepaidC.setBlackFlag(BlackFlagEnum.unblack.getValue());
			if("1".equals(prepaidC.getInvoicePrint())){
				prepaidC.setInvoiceChangeFlag(2l);
			}else{
				prepaidC.setInvoiceChangeFlag(0l);
			}
			prepaidCDao.save(prepaidC);
			
			// 储值卡业务记录
			BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
			
			DbasCardFlow dbasCard = new DbasCardFlow();
			dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
			dbasCard.setNewCardNo(prepaidC.getCardNo());
			dbasCard.setOldCardNo(prepaidC.getCardNo());
			dbasCard.setCardNo(prepaidC.getCardNo());
			dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
			dbasCard.setSerType(DBACardFlowSerTypeEnum.prepaidCCardIssuice.getValue());
			dbasCard.setApplyTime(new Date());
			dbasCard.setServiceId(prepaidCBussiness.getId());
			dbasCard.setCardAmt(new BigDecimal("0"));
			dbasCard.setReadFlag(DBACardFlowReadFlagEnum.abledRead.getValue());
			dbasCard.setEndFlag(DBACardFlowEndFlagEnum.arriComplete.getValue());
			dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.arriDispute.getValue());
			dbasCard.setOperId(prepaidCBussiness.getOperid());
			dbasCard.setOperno(prepaidCBussiness.getOperNo());
			dbasCard.setOpername(prepaidCBussiness.getOperName());
			dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
			dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
			dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
			dbasCard.setEndServiceId(prepaidCBussiness.getId());
			dbasCard.setEndSerType(DBACardFlowEndSerTypeEnum.prepaidCCardIssuice.getValue());
			dbasCard.setEndCardAmt(new BigDecimal("0"));
			dbasCardFlowDao.save(dbasCard);
			
			// 电子钱包记录
			BigDecimal electronic_purse_NO = sequenceUtil.getSequence("SEQ_CSMS_electronic_purse_NO");
			electronicPurse.setId(Long.valueOf(electronic_purse_NO.toString()));
			electronicPurse.setPrepaidc(Long.valueOf(PrePaidC_NO.toString()));
			electronicPurseDao.save(electronicPurse);
			
			// 车卡标签关系
			carObuCardInfo.setPrepaidCID(Long.valueOf(PrePaidC_NO.toString()));
			carObuCardInfoDao.update(carObuCardInfo);
			
			if (billGet != null) {
				// 服务方式
				BigDecimal bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				billGet.setId(Long.valueOf(bill_get_NO.toString()));
				billGet.setCardAccountID(Long.valueOf(PrePaidC_NO.toString()));
				billGetDao.save(billGet);
			}
			
			
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			
			//CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			//车辆
			VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicle.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
			vehicleBussiness.setCardNo(prepaidC.getCardNo());//
			vehicleBussiness.setCardType(Constant.PREPAIDTYPE);//储值卡
			//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
			vehicleBussiness.setType(VehicleBussinessEnum.prepaidCIssue.getValue());//
			vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
			vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
			vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
			vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
			vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
			vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆储值卡发行");
			
			vehicleBussinessDao.save(vehicleBussiness);
			
			// 客服给清算   //原清算数据，没用了
			/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
			Customer cus = customerDao.findById(prepaidC.getCustomerID());
			SCinfo scinfo = new SCinfo();
			scinfo.setCardNo(prepaidC.getCardNo());
			scinfo.setState("0");
			scinfo.setUserNo(cus.getUserNo());
			scinfo.setBusinessTime(new Date());
			scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
			sCinfoDao.save(scinfo,cus.getId());*/
			
			//清算接口    //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseList.setCardType(1);
			userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
			
			//给清算数据
			cardSecondIssuedService.savePrepaidCard(prepaidC);
//			CardSecondIssued cardSecondIssued = new CardSecondIssued();
//			cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//			cardSecondIssued.setCardType("22");
//			cardSecondIssued.setId(prepaidCBussiness.getId());
//			cardSecondIssued.setSdate(prepaidC.getSaleTime());
//			cardSecondIssued.setStatus(0);
//			cardSecondIssued.setUpdatetime(new Date());
//			cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
			
			
			// 操作日志
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
			if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
				interfaceRecord.setCsmsState("1");
				interfaceRecordDao.update(interfaceRecord);
				return "true";
			}
			
			return "true";
		} catch (ApplicationException e) {
			logger.error("储值卡发行失败");
			e.printStackTrace();
			throw new ApplicationException("储值卡发行失败");
		}
	}
	
	@Override
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,Integer type,List<ReturnFee> returnFeeList) {
		return agentPrepaidCUnifiedInterfaceService.saveRecharge(prepaidCBussiness, addRegDetail, mainAccountInfo,type,returnFeeList,null);
	}
	
	@Override
	public void updateRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList, Map<String,Object> params) {
		agentPrepaidCUnifiedInterfaceService.updateRecharge(prepaidCBussiness, addRegDetail, mainAccountInfo,returnFeeList,params);
	}
	
	@Override
	public void updateReversal(PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList,Long oldBussinessID, Map<String,Object> params) {
		agentPrepaidCUnifiedInterfaceService.updateReversal(prepaidCBussiness, mainAccountInfo,returnFeeList,oldBussinessID, params);
	}
	@Override
	public boolean saveReversal(MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,PrepaidCBussiness oldPrepaidCBussiness) {
		return agentPrepaidCUnifiedInterfaceService.saveReversal(mainAccountInfo, prepaidCBussiness, oldPrepaidCBussiness);
	}
	@Override
	public String saveReplaceCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord) {
		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryServiceForAgent.omsInterface(newPrepaidC.getCardNo(), "1", interfaceRecord,"issue",
					null,null,"","1","",null,newPrepaidC.getRealCost(),"29");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
			if(m!=null){
				newPrepaidC.setStartDate((Date)m.get("startDate"));
				newPrepaidC.setEndDate((Date)m.get("endDate"));
			}
			
			//客服原发行操作
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setNewPrepaidC(newPrepaidC);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType("22");
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			unifiedParam.setType("3");
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {// 储值卡
				// 电子钱包
				BigDecimal electronic_purse_NO = sequenceUtil.getSequence("SEQ_CSMS_electronic_purse_NO");
				electronicPurse.setId(Long.valueOf(electronic_purse_NO.toString()));
				electronicPurse.setPrepaidc(newPrepaidC.getId());
				electronicPurseDao.save(electronicPurse);
				
				// 储值卡业务记录
				BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
				prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
				prepaidCBussinessDao.save(prepaidCBussiness);
				
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(prepaidCBussiness.getOldCardno());
				
				DbasCardFlow dbasCard = new DbasCardFlow();
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(prepaidCBussiness.getCardno());
				dbasCard.setOldCardNo(prepaidCBussiness.getOldCardno());
				if(dbasCardFlow!=null)dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(prepaidCBussiness.getOldCardno());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				dbasCard.setSerType(DBACardFlowSerTypeEnum.gainCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
				dbasCard.setCardAmt(new BigDecimal("0"));
				dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
				dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
				dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
				dbasCard.setOperId(prepaidCBussiness.getOperid());
				dbasCard.setOperno(prepaidCBussiness.getOperNo());
				dbasCard.setOpername(prepaidCBussiness.getOperName());
				dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
				dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
				dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
				dbasCardFlowDao.save(dbasCard);
				
				//旧储值卡的车卡标签绑定记录
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				//车辆
				VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = new VehicleBussiness();
				BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
				vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
				vehicleBussiness.setCustomerID(vehicle.getCustomerID());
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setCardNo(prepaidC.getCardNo());//
				vehicleBussiness.setCardType(Constant.PREPAIDTYPE);//储值卡
				//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
				vehicleBussiness.setType(VehicleBussinessEnum.prepaidCReplaceCard.getValue());//
				vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
				vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
				vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
				vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
				vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
				vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("储值卡补卡");
				
				vehicleBussinessDao.save(vehicleBussiness);
				
				
				// 车卡标签绑定
				int flag=carObuCardInfoDao.updatePerPaidCID(newPrepaidC.getId(), prepaidC.getId());
				
				//回退金额及转移金额处理
				//returnFeeDao.updateCardno(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				
				
				// 客服给清算     原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				
				//新卡
				SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				SCinfo newSCinfo = new SCinfo();
				newSCinfo.setCardNo(newPrepaidC.getCardNo());
				newSCinfo.setState("0");
				newSCinfo.setUserNo(cus.getUserNo());
				newSCinfo.setBusinessTime(new Date());
				newSCinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(newSCinfo,cus.getId());*/
				
				PrepaidCBussiness oldPrepaidCBussiness = prepaidCBussinessService.findByCardNoAndState(prepaidC.getCardNo(), prepaidC.getState());
				if(oldPrepaidCBussiness != null && oldPrepaidCBussiness.getBalance() != null){//with card and balance
					
					//lock card    //原清算数据，没用了
					/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					SCinfo scinfo_lock = new SCinfo();
					scinfo_lock.setCardNo(newPrepaidC.getCardNo());
					scinfo_lock.setState("5");
					scinfo_lock.setUserNo(cus.getUserNo());
					scinfo_lock.setBusinessTime(new Date());
					scinfo_lock.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo_lock,cus.getId());*/
					
					//with card		//原清算数据，没用了
					/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					SCinfo scinfo_withCard = new SCinfo();
					scinfo_withCard.setCardNo(newPrepaidC.getCardNo());
					scinfo_withCard.setState("21");
					scinfo_withCard.setUserNo(cus.getUserNo());
					scinfo_withCard.setBusinessTime(new Date());
					scinfo_withCard.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo_withCard,cus.getId());*/
					
					//with card   //原清算数据，没用了
					/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					SCinfo scinfo_withBalacne = new SCinfo();
					scinfo_withBalacne.setCardNo(newPrepaidC.getCardNo());
					scinfo_withBalacne.setState("25");
					scinfo_withBalacne.setUserNo(cus.getUserNo());
					scinfo_withBalacne.setBusinessTime(new Date());
					scinfo_withBalacne.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo_withBalacne,cus.getId());*/
					
				}else{//without card
					
					//lock card     //原清算数据，没用了
					/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					SCinfo scinfo_lock = new SCinfo();
					scinfo_lock.setCardNo(newPrepaidC.getCardNo());
					scinfo_lock.setState("5");
					scinfo_lock.setUserNo(cus.getUserNo());
					scinfo_lock.setBusinessTime(new Date());
					scinfo_lock.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo_lock,cus.getId());*/
					
					//without card	//原清算数据，没用了
					/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					SCinfo scinfo_withoutCard = new SCinfo();
					scinfo_withoutCard.setCardNo(newPrepaidC.getCardNo());
					scinfo_withoutCard.setState("23");
					scinfo_withoutCard.setUserNo(cus.getUserNo());
					scinfo_withoutCard.setBusinessTime(new Date());
					scinfo_withoutCard.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo_withoutCard,cus.getId());*/
					
				}
				
				if(flag==1){
					//清算接口		//原清算数据，没用了
					/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
					userInfoBaseList.setNetNo("4401");
					//userInfoBaseList.setIssuerId("");发行方唯一标识
					//userInfoBaseList.setAgent();发行代理单位编码
					userInfoBaseList.setCardType(1);
					userInfoBaseListDao.save(userInfoBaseList, newPrepaidC);*/
				}
				
				//原清算数据
				screturnreqSendDao.saveScreturnreqSend(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				screturnreqDao.saveScreturnreq(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				
				
				//保存清算数据
				cardSecondIssuedService.savePrepaidCard(newPrepaidC);
//				CardSecondIssued cardSecondIssued = new CardSecondIssued();
//				cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//				cardSecondIssued.setCardType("22");
//				cardSecondIssued.setId(prepaidCBussiness.getId());
//				cardSecondIssued.setSdate(prepaidC.getSaleTime());
//				cardSecondIssued.setStatus(0);
//				cardSecondIssued.setUpdatetime(new Date());
//				cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
				
				//DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//
				//saveDarkList(prepaidC,darkList,"10", "1");
				//换卡需要注销旧卡
				blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
						, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
						prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
						new Date());
				//发行成功后，更新营运接口调用登记记录的客服状态
				interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
				/*if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
					interfaceRecord.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecord);
					return "true";
				}*/
				return "true";

			}else{
				throw new ApplicationException("补领新卡失败");//回滚主账户修改的记录
			}
			//return "补领新卡失败";
		} catch (ApplicationException e) {
			logger.error("补领新卡失败");
			e.printStackTrace();
			throw new ApplicationException("补领新卡失败");
		}
	}
	@Override
	public String saveGainCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord,
			PrepaidCBussiness rechargePrepaidCBussiness) {
		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryServiceForAgent.omsInterface(newPrepaidC.getCardNo(), "1", interfaceRecord,"issue",
					null,null,"","1","",null,newPrepaidC.getRealCost(),"211");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
			if(m!=null){
				newPrepaidC.setStartDate((Date)m.get("startDate"));
				newPrepaidC.setEndDate((Date)m.get("endDate"));
			}
		
			
			// 主账户信息
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewPrepaidC(newPrepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("9");
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			unifiedParam.setType("5");
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {// 储值卡
				// 电子钱包
				BigDecimal electronic_purse_NO = sequenceUtil.getSequence("SEQ_CSMS_electronic_purse_NO");
				electronicPurse.setId(Long.valueOf(electronic_purse_NO.toString()));
				electronicPurse.setPrepaidc(newPrepaidC.getId());
				electronicPurseDao.save(electronicPurse);
				
				DbasCardFlow dbasCard = new DbasCardFlow();
				// 储值卡业务记录
				if (rechargePrepaidCBussiness != null) {//有卡
					prepaidCBussiness.setNotCardState("1");
					dbasCard.setCardAmt(rechargePrepaidCBussiness.getRealprice());
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.abledRead.getValue());
				}else{//无卡
					prepaidCBussiness.setNotCardState("0");
					dbasCard.setCardAmt(new BigDecimal("0"));
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
				}

				BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
				prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
				prepaidCBussinessDao.save(prepaidCBussiness);
				
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(prepaidCBussiness.getOldCardno());
				
				
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(prepaidCBussiness.getCardno());
				dbasCard.setOldCardNo(prepaidCBussiness.getOldCardno());
				if(dbasCardFlow!=null)dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(prepaidCBussiness.getOldCardno());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				dbasCard.setSerType(DBACardFlowSerTypeEnum.lossReplaceCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
				dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
				dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
				dbasCard.setOperId(prepaidCBussiness.getOperid());
				dbasCard.setOperno(prepaidCBussiness.getOperNo());
				dbasCard.setOpername(prepaidCBussiness.getOperName());
				dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
				dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
				dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
				dbasCardFlowDao.save(dbasCard);
				
				//旧储值卡的车卡标签绑定记录
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				//车辆
				VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = new VehicleBussiness();
				BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
				vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
				vehicleBussiness.setCustomerID(vehicle.getCustomerID());
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setCardNo(prepaidC.getCardNo());//
				vehicleBussiness.setCardType(Constant.PREPAIDTYPE);//储值卡
				//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
				if (rechargePrepaidCBussiness != null) {//有卡
					vehicleBussiness.setType(VehicleBussinessEnum.prepaidCGainWithCard.getValue());//
				}else{//无卡
					vehicleBussiness.setType(VehicleBussinessEnum.prepaidCGainWithoutCard.getValue());//
				}
				vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
				vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
				vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
				vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
				vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
				vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("储值卡换卡");
				
				vehicleBussinessDao.save(vehicleBussiness);
				
				// 车卡标签绑定
				int flag=carObuCardInfoDao.updatePerPaidCID(newPrepaidC.getId(), prepaidC.getId());

				// 回退金额及转移金额处理
				List<ReturnFee> returnFees = returnFeeDao.findByCardNoStateIgnoreSettleMonth(prepaidC.getCardNo(), ReturnFeeStateEnum.notUse.getValue());
				if (returnFees != null) {
					for (ReturnFee returnFee : returnFees) {
						int ret = returnFeeDao.updateGainCardState(prepaidCBussiness.getId(), prepaidCBussiness.getTradetime(), returnFee.getId());
						if (ret <= 0) {
							continue;
						} else if (ret > 1) {
							logger.error("回退记录[{}]更新多条[{}]，非法", returnFee.getId(), ret);
							throw new RuntimeException("回退金额转移更新失败");
						} else if (ret == 1) {
							returnFee.setId(null);
							returnFee.setCardNo(newPrepaidC.getCardNo());
							returnFee.setInsertTime(prepaidCBussiness.getTradetime());
							returnFeeDao.save(returnFee);
						}
					}
				}
				// 回退金额及转移金额处理
				//returnFeeDao.updateCardno(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				
				// 保存充值的储值卡业务记录
				/*if (rechargePrepaidCBussiness != null) {
					prepaidCBussinessDao.save(rechargePrepaidCBussiness);
				}*/
				
				
				//旧卡     //原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				if(rechargePrepaidCBussiness==null){
					scinfo.setState("22");//无卡
				}else{
					scinfo.setState("25");//有卡
				}
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/
				
				//新卡      //原清算数据，没用了
				/*SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				SCinfo newSCinfo = new SCinfo();
				newSCinfo.setCardNo(newPrepaidC.getCardNo());
				newSCinfo.setState("0");
				newSCinfo.setUserNo(cus.getUserNo());
				newSCinfo.setBusinessTime(new Date());
				newSCinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(newSCinfo,cus.getId());*/
				
				if(flag==1){
					//清算接口    //原清算数据，没用了
					/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
					userInfoBaseList.setNetNo("4401");
					//userInfoBaseList.setIssuerId("");发行方唯一标识
					//userInfoBaseList.setAgent();发行代理单位编码
					userInfoBaseList.setCardType(1);
					userInfoBaseListDao.save(userInfoBaseList, newPrepaidC);*/
				}
				
			    //原清算数据
				screturnreqSendDao.saveScreturnreqSend(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				screturnreqDao.saveScreturnreq(prepaidC.getCardNo(), newPrepaidC.getCardNo());
				
				//保存清算数据
				cardSecondIssuedService.savePrepaidCard(newPrepaidC);
//				CardSecondIssued cardSecondIssued = new CardSecondIssued();
//				cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//				cardSecondIssued.setCardType("22");
//				cardSecondIssued.setId(prepaidCBussiness.getId());
//				cardSecondIssued.setSdate(prepaidC.getSaleTime());
//				cardSecondIssued.setStatus(0);
//				cardSecondIssued.setUpdatetime(new Date());
//				cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
				
				return "true";
				
			}
			return "false";
		} catch (ApplicationException e) {
			logger.error("领取新卡失败");
			e.printStackTrace();
			throw new ApplicationException("领取新卡失败");
		}
	}
	@Override
	public String saveStopCard(ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness prepaidCBussiness, Cancel cancel,
			Customer customer, PrepaidC prepaidC) {

		try {
			// 生成卡片注销客服流水
			serviceFlowRecord.setId(sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO"));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId() + "");
			serviceFlowRecord.setClientID(customer.getId());
			
			Long bussId = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(bussId);

			UnifiedParam unifiedParam = new UnifiedParam();
			String type = "6"; // stop prepaidC
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setType(type);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
			
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			
			//unifiedInterfaceService.savePrepaidCState(unifiedParam);
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				
				//增加资金转移确认
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(prepaidCBussiness.getCardno());
				DbasCardFlow dbasCard = new DbasCardFlow();
				dbasCard.setCardAmt(new BigDecimal("0"));
				dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(prepaidCBussiness.getCardno());
				dbasCard.setOldCardNo(prepaidCBussiness.getCardno());
				if(dbasCardFlow!=null)dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(prepaidCBussiness.getCardno());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(bussId);
				dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
				dbasCard.setOperId(prepaidCBussiness.getOperid());
				dbasCard.setOperno(prepaidCBussiness.getOperNo());
				dbasCard.setOpername(prepaidCBussiness.getOperName());
				dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
				dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
				dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
				dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
				}else{
					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
				}
				dbasCardFlowDao.save(dbasCard);
				
				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				cancelDao.save(cancel);
				
				//增加卡信息历史记录
				PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(),"3",prepaidC);
				prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
				prepaidCDao.saveHis(prepaidCHis);
				
				//解绑车辆信息
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				if(carObuCardInfo!=null){
					carObuCardInfo.setPrepaidCID(null);
					carObuCardInfoDao.update(carObuCardInfo);
				}
				
				if("0".equals(serviceFlowRecord.getIsNeedBlacklist())){
					// 客服给清算       //原清算数据，没用了
					/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					Customer cus = customerDao.findById(prepaidC.getCustomerID());
					SCinfo scinfo = new SCinfo();
					scinfo.setCardNo(prepaidC.getCardNo());
					scinfo.setState("22");
					scinfo.setUserNo(cus.getUserNo());
					scinfo.setBusinessTime(new Date());
					scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo,cus.getId());*/
				}else{
					//原清算数据，没用了
					/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					Customer cus = customerDao.findById(prepaidC.getCustomerID());
					SCinfo scinfo = new SCinfo();
					scinfo.setCardNo(prepaidC.getCardNo());
					if (noCard == 1) {
						prepaidCBussiness.setState("13"); // without card
					} else {
						prepaidCBussiness.setState("12");// with card
					}
					if("13".equals(prepaidCBussiness.getState())){//无卡
						scinfo.setState("23");
					}else{//有卡
						scinfo.setState("22");
					}
					scinfo.setUserNo(cus.getUserNo());
					scinfo.setBusinessTime(new Date());
					scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo,cus.getId());*/
				    //原清算数据，没用了
					/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
							null, " ", null, 10, new Date(), 0, new Date());
					TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
							null, " ", null, 10, new Date(), 0, new Date());
					saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
					
					//
					//DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
					//
					//saveDarkList(prepaidC,darkList,"10", "1");
					//卡片注销
					blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
							, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
							prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
							new Date());
				}
				//清算接口        //原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseList.setCardType(1);
				userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
				return "true";
				// TODO 增加操作日志
				
/*				if (prepaidC.getCardNo().length()==16) {
					//锁定成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}else {
					return "true";
				}*/
			}

			return "false";
		} catch (ApplicationException e) {
			logger.error("终止卡失败");
			e.printStackTrace();
			throw new ApplicationException("终止卡失败");
		}
	}
	
	@Override
	public String delPrepaidC(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, PrepaidCHis prepaidCHis,
			ElectronicPurse electronicPurse, ElectronicPurseHis electronicPurseHis, MainAccountInfo mainAccountInfo,
			PrepaidCBussiness prepaidCBussiness, BillGet billGet, BillGetHis billGetHis) {
		/**
		 * 删除处理： 1、删除对应卡车对应关系 2、增加电子钱包历史 3、删除电子钱包信息 4、增加储值卡历史记录 5、删除储值卡信息
		 * 6、记录操作日志 7、增加账户金额（工本费）
		 */

		try {
			VehicleInfo vehicleInfo = null;
			if(carObuCardInfo!=null&&carObuCardInfo.getVehicleID()!=null){
				vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			}
			carObuCardInfoDao.update(carObuCardInfo);

			// 增加电子钱包历史表
			BigDecimal Ele_purse_his_NO = sequenceUtil.getSequence("SEQ_CSMS_ele_purse_his_NO");
			electronicPurseHis.setId(Long.valueOf(Ele_purse_his_NO.toString()));
			electronicPurseDao.saveHis(electronicPurseHis);

			// 删除电子钱包
			electronicPurseDao.delete(electronicPurse.getId());

			// 增加储值卡历史记录
			BigDecimal PrePaidC_his_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_his_NO");
			prepaidCHis.setId(Long.valueOf(PrePaidC_his_NO.toString()));
			prepaidCDao.saveHis(prepaidCHis);

			// 删除储值卡发行记录
			prepaidCDao.delete(prepaidC.getId());

			// 增加服务方式历史表
			if (billGetHis != null) {
				BigDecimal bill_get_his_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_his_NO");
				billGetHis.setId(Long.valueOf(bill_get_his_NO.toString()));
				billGetHisDao.save(billGetHis);
			}

			// 删除服务方式
			if (billGet != null) {
				billGetDao.delete(billGet.getId());
			}

			// 操作日志

			// 增加储值卡业务记录
			BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			//保存清算数据
			cardSecondIssuedService.deletePrepaidCard(prepaidC,vehicleInfo);
//			CardSecondIssued cardSecondIssued = new CardSecondIssued();
//			cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//			cardSecondIssued.setCardType("22");
//			cardSecondIssued.setId(prepaidCBussiness.getId());
//			cardSecondIssued.setSdate(prepaidC.getSaleTime());
//			cardSecondIssued.setStatus(1);
//			cardSecondIssued.setUpdatetime(new Date());
//			cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);

			return "true";
		} catch (ApplicationException e) {
			logger.error("删除（储值卡发行）失败");// 记录删除日志
			e.printStackTrace();
			throw new ApplicationException("删除（储值卡发行）失败");
		}
	}
	
	/**
	 * @Description:TODO
	 * @param prepaidC
	 * @param darkList
	 * @param genCau 产生原因
	 * @param state 状态
	 */
	public void saveDarkList(PrepaidC prepaidC,DarkList darkList,String genCau,String state){
		Customer customer = customerDao.findById(prepaidC.getCustomerID());
		try {
			if(darkList == null){
				darkList = new DarkList();
				darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
				darkList.setCustomerId(prepaidC.getCustomerID());
				darkList.setCardNo(prepaidC.getCardNo());
				darkList.setCardType("2");
				darkList.setGenDate(new Date());
				darkList.setGencau(genCau);//产生原因	10—无卡注销。
				darkList.setGenmode("0");//产生方式	系统产生
				darkList.setOperId(prepaidC.getSaleOperId());
				darkList.setPlaceId(prepaidC.getSalePlaceId());
				darkList.setOperNo(prepaidC.getOperNo());
				darkList.setOperName(prepaidC.getOperName());
				darkList.setPlaceNo(prepaidC.getPlaceNo());
				darkList.setPlaceName(prepaidC.getPlaceName());
				//darkList.setUpdateTime(updateTime);
				if(customer!=null){
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
				}
				//darkList.setRemark(remark);
				darkList.setState(state);
				darkListDao.save(darkList);
				
			}else{
				darkList.setCustomerId(prepaidC.getCustomerID());
				darkList.setCardNo(prepaidC.getCardNo());
				darkList.setCardType("2");
				darkList.setGenDate(new Date());
				darkList.setGencau(genCau);//产生原因	10—无卡注销。
				darkList.setGenmode("0");//产生方式	系统产生
				darkList.setOperId(prepaidC.getSaleOperId());
				darkList.setPlaceId(prepaidC.getSalePlaceId());
				darkList.setOperNo(prepaidC.getOperNo());
				darkList.setOperName(prepaidC.getOperName());
				darkList.setPlaceNo(prepaidC.getPlaceNo());
				darkList.setPlaceName(prepaidC.getPlaceName());
				//darkList.setUpdateTime(updateTime);
				if(customer!=null){
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
				}
				//darkList.setRemark(remark);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("保存清算黑名单数据失败"+prepaidC.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败"+prepaidC.getCardNo());
		}
	}

	private boolean isToday(Date date) {
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		return (day.format(new Date()).equals(day.format(date)));
	}

	@Override
	public Map<String, Object> saveHalfTrue(PrepaidCBussiness oldPrepaidCBussiness) {
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
			prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

			prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
			prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
			prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
			prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());

			if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState().trim())) { // 直充
				//完成储值卡业务记录
				if (prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
					throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
				}
				Date addSureTime = new Date(); //临时存储充值确认时间，后边使用

				if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
					//完成回退金额
					returnFeeDao.updateRechargeSuccessState(addSureTime, prepaidCBussiness.getId());
				}

				if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
					//完成转移金额
					dbasCardFlowDao.updateRechargedEndFlagByEndServiceId(prepaidCBussiness.getId());

					DbasCardFlow dbasCardFlow = dbasCardFlowDao.findByEndServiceId(prepaidCBussiness.getId());
					if (dbasCardFlow == null) {
						logger.error("储值卡业务记录[{}]不存在对应的资金转移记录", prepaidCBussiness.getId());
						throw new RuntimeException("不存在对应的资金转移记录");
					}
					String remark = "新卡["+dbasCardFlow.getNewCardNo()+"]充值旧卡金额:" + dbasCardFlow.getCardAmt();
					cardObuService.saveCardBalance(dbasCardFlow.getOldCardNo(), prepaidCBussiness.getTransferSum(),
							BigDecimal.ZERO, addSureTime, null, remark, new Date());
				}

				//添加充值账单
				PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
				agentPrepaidCUnifiedInterfaceService.saveAddBill(prepaidC, prepaidCBussiness, false);

				oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
				oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
				oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
				oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());

				//给清算数据，插入后半部分数据
				ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01", addSureTime, "3", ScAddSureConfirmTypeEnum.frontSure.getValue());
				//写给原清算的数据
				scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01", addSureTime, "3");

				//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("01", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());

				//给清算数据    充值	给铭鸿
				agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge(prepaidCBussiness, scAddSure);

				//给清算数据    资金转移		给铭鸿
				agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge4Transfer(prepaidCBussiness, scAddSure);

				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if (customer != null) {
					serviceWater.setCustomerId(customer.getId());
					serviceWater.setUserNo(customer.getUserNo());
					serviceWater.setUserName(customer.getOrgan());
				}
				serviceWater.setCardNo(prepaidCBussiness.getCardno());

				if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
					//直充
					serviceWater.setSerType(ServiceWaterSerType.noLoginRecharge.getValue());//储值卡直充充值
					serviceWater.setRemark(ServiceWaterSerType.noLoginRecharge.getName());
				}
				serviceWater.setAmt(prepaidCBussiness.getRealprice());//应收金额
				serviceWater.setAulAmt(prepaidCBussiness.getRealprice());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());

				serviceWater.setFlowState(ServiceWaterFlowStateEnum.complete.getValue());//完成

				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(scAddSure.getTimeReq());

				serviceWaterDao.save(serviceWater);

				//保存回执记录
				Receipt receipt = receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);

			} else if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState().trim())) { // 直充冲正

				prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());
				//完成储值卡业务记录
				if (prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
					throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
				}
				//保存回执记录
				Receipt receipt = receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);

				Date reversalSureTime = new Date(); //临时存储充值确认时间，后边使用

				if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState())) {//免登陆充值冲正
					Long operPlaceId = prepaidCBussiness.getPlaceid();
					ServiceFundMonitor fundMonitor = serviceFundMonitorService.findFundMonitorRechargeByCustomPoint(operPlaceId);
					//判断已用金额+充值金额+回退金额+转移金额是否超过充值上限
					if (fundMonitor != null) {
						int ret = serviceFundMonitorService.updateFundMonitorRechargeByCustomPoint(operPlaceId, prepaidCBussiness.getRealprice());
						if (ret != 1) {
							logger.warn("网点[{}]授权资金更新不为1，需注意", operPlaceId);
						}
					}
				}
				oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
				oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
				oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
				oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());
				//给清算数据，插入后半部分数据
				ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "02", reversalSureTime, "4", ScAddSureConfirmTypeEnum.frontSure.getValue());
				//写给清算的数据
				scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "02", reversalSureTime, "4");

				//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("02", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
				//给清算数据	给铭鸿
				agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge4Reversal(prepaidCBussiness, scAddSure);

				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				if (customer != null) {
					serviceWater.setCustomerId(customer.getId());
					serviceWater.setUserNo(customer.getUserNo());
					serviceWater.setUserName(customer.getOrgan());
				}
				serviceWater.setCardNo(prepaidCBussiness.getCardno());
				if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState())) {
					//储值卡直充充值冲正
					serviceWater.setSerType(ServiceWaterSerType.noLoginRechargeReversal.getValue());//储值卡直充充值冲正
					serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
					serviceWater.setRemark(ServiceWaterSerType.noLoginRechargeReversal.getName());

					//对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
					PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
					if (preRechargeBusiness != null) {
						ServiceWater oldServiceWater = new ServiceWater();
						oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
						oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
						serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
					}
				}
				serviceWater.setAmt(prepaidCBussiness.getRealprice());//应收金额
				serviceWater.setAulAmt(prepaidCBussiness.getRealprice());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());

				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(reversalSureTime);

				serviceWaterDao.save(serviceWater);
			}
			m.put("success", true);
			return m;
		} catch(ApplicationException e) {
			throw new ApplicationException("半条确认成功异常", e);
		}
	}

	@Override
	public Map<String, Object> saveHalfFalse(PrepaidCBussiness oldPrepaidCBussiness) {
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
			prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

			//prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
			//prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
			//prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
			prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());

			if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) { //

				//完成储值卡业务记录
				if (prepaidCBussinessService.updateTradeStateFail(prepaidCBussiness) != 1) {
					throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
				}
				Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
				if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
					//完成回退金额
					returnFeeDao.updateNotUseState(prepaidCBussiness.getId());
				}
				if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
					//完成转移金额
					dbasCardFlowDao.updateDisRechargeEndFlagByEndServiceId(prepaidCBussiness.getId());
				}

				oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
				oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
				oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
				oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());
				//插入后半部分数据
				scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01", addSureTime, "1", ScAddSureConfirmTypeEnum.frontSure.getValue());
				//写给清算的数据
				scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01", addSureTime, "1");

				if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
					if (prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) != 0) {
						Long operPlaceId = prepaidCBussiness.getPlaceid();
						ServiceFundMonitor fundMonitor = serviceFundMonitorService.findFundMonitorRechargeByCustomPoint(operPlaceId);
						//判断已用金额+充值金额+回退金额+转移金额是否超过充值上限
						if (fundMonitor != null) {
							int ret = serviceFundMonitorService.updateFundMonitorRechargeByCustomPoint(operPlaceId, prepaidCBussiness.getRealprice().negate());
							if (ret != 1) {
								logger.error("网点[{}]授权资金发生变更，需重试", operPlaceId);
								throw new ApplicationException("授权资金发生变更，需重试");
							}
						}
					}
				}
			} else if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState().trim())) { // 充值冲正

				prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());
				if (prepaidCBussinessService.updateTradeStateFail(prepaidCBussiness) != 1) {
					throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
				}

				Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
				oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
				oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
				oldPrepaidCBussiness.setTransferSum(BigDecimal.ZERO);
				oldPrepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
				//插入后半部分数据
				scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "02", addSureTime, "2", ScAddSureConfirmTypeEnum.frontSure.getValue());
				//写给清算的数据
				scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "02", addSureTime, "2");
			}
			m.put("success", true);
			return m;
		} catch(ApplicationException e) {
			throw new ApplicationException("半条确认失败异常", e);
		}
	}

	@Override
	public Map<String,Object> findCardFeeByOMSInterface() {
		String key = "Prepaid card issue fee";
		Map<String,Object> map = omsParamInterfaceService.findOmsParam(key);
		return map;
	}

	@Override
	public Map<String, Object> findFaceValueByOMSInterface() {
		String key = "StoreCard Face Value";
		Map<String,Object> map = omsParamInterfaceService.findOmsParam(key);
		return map;
	}
	/**
	 * 获取卡内金额上限
	 */
	@Override
	public Map<String,Object> findSingleCardFeeLimit() {
		String key = "StoreCard Face Value";
		Map<String,Object> map = omsParamInterfaceService.findOmsParam(key);
		return map;
	}

	@Override
	public boolean saveRechargeGainCard(PrepaidCBussiness prepaidCBussiness, DbasCardFlow dbasCardFlow) {
		return agentPrepaidCUnifiedInterfaceService.saveRechargeGainCard(prepaidCBussiness, dbasCardFlow);
	}
}
