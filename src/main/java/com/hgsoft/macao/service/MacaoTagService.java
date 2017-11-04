package com.hgsoft.macao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.macao.dao.CarUserCardInfoDao;
import com.hgsoft.macao.dao.CardHolderInfoDao;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.serviceInterface.IMacaoTagService;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoCancelDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagMigrateRecordDao;
import com.hgsoft.obu.dao.TagRecoverDao;
import com.hgsoft.obu.dao.TagReplaceDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
/**
 * 电子标签维护管理模块
 * @author guanshaofeng
 * @date 2016年11月29日
 */
@Service
public class MacaoTagService implements IMacaoTagService{

	private static Logger logger = Logger.getLogger(MacaoTagService.class.getName());
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private IVehicleInfoService vehicleInfoService;
	
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private TagRecoverDao tagRecoverDao;
	@Resource
	private TagReplaceDao tagReplaceDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private AccountFundChangeDao accountFundChangeDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private CarUserCardInfoDao carUserCardInfoDao;
	@Resource
	private TagMigrateRecordDao tagMigrateRecordDao;
	@Resource
	private VehicleInfoHisDao  vehicleInfoHisDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private CardHolderInfoDao cardHolderInfoDao;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private TagInfoCancelDao tagInfoCancelDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	@Resource
	private OmsParamDao omsParamDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	
	
	public CarObuCardInfo getCarObuCardInfo(String vehiclePlate){
		try {
			return macaoDao.getCarObuCardInfoByVehPla(vehiclePlate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 挂起电子标签
	 */
	@Override
	public void saveTagStopTagInfo(String flag,Customer customer,Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID,Long installmanID, String memo, Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord) {
		try {	
			//标记该标签状态改变之前的状态，给客户流水用
			TagInfo tagInfo = tagInfoDao.findById(tagInfoId);
			
			//调用接口更新
			UnifiedParam unifiedParam = new UnifiedParam();
			
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			//tagInfo.setTagState("2");
			tagInfo.setTagState(TagStateEnum.stop.getValue());
			carObuCardInfo.setVehicleID(vehicleID);
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(vehicleID);
			
			// 2017/4/19修改.  依据澳门通分册 V0.7
			//set这些修正人信息，为了传到obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)来可以获取并update数据库
			tagInfo.setCorrectTime(new Date());//更新时间
			tagInfo.setCorrectOperID(operID);
			tagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			tagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			tagInfo.setCorrectPlaceID(operplaceID);
			tagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			tagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			//黑名单标记,传到updateOrSaveObu
			if("0".equals(flag)){
				//无标签挂起，要下黑名单
				tagInfo.setBlackFlag(BlackFlagEnum.black.getValue());
			}else{
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
			}
			
			
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("3");
			
			/*
			//更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为挂起（‘2’）。
			tagStopDao.updateTagInfo(tagInfoId);
			//更新车卡电子标签绑定表的电子标签为NULL.
			tagStopDao.updateCarObuCardInfo(vehicleID);*/
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){	
				//澳门通电子标签停用，还要考虑   车、持卡人信息、卡、标签 绑定关系表
				/*CarUserCardInfo carUserCardInfo = carUserCardInfoDao.findByVehicleId(vehicleID);
				carUserCardInfo.setTagId(null);
				carUserCardInfoDao.updateCarUserCardInfo(carUserCardInfo);*/
				
				// 记录电子标签业务操作记录，业务类型为“挂起”，当前状态为”挂起“（‘2’）
				/*tagStopDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
						vehicleID,null,null);*/
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagNo);
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID(operID);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operplaceID);
				tagBusinessRecord.setBusinessType("5");//挂起
				tagBusinessRecord.setInstallmanID(installmanID);//安装人员
				//tagBusinessRecord.setCurrentTagState("2");//正常
				tagBusinessRecord.setCurrentTagState(TagStateEnum.stop.getValue());
				tagBusinessRecord.setMemo(memo);
				tagBusinessRecord.setFromID(tagInfoId);
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
				tagBusinessRecord.setBussinessid(vehicleInfo.getHisSeqId());//注销的时候业务记录表记录的是车辆的hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				if("0".equals(flag)){
					//无标签挂起,才下黑名单： 2017/4/19  依据澳门通分册V0.7
					
					//无标签挂起
					blackListService.saveOBUStopUse(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());
				}
				
				String cardNo = "";
				String cardType = "";
				//写给铭鸿的清算数据：用户状态信息
				cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
						cardType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
						tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签挂起");
				
				//获取持卡人信息
				MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByTagInfoId(tagInfoId);
//				MacaoCardCustomer macaoCardCustomer = macaoCardCustomerDao.getMacaoCardCustomerById(tagInfoId);
				
				String serType = "307";
				String remark = "电子标签有卡挂起";
				if("0".equals(flag)){
					serType="309";
					remark = "无标签挂起";
				}
				
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setNewCardNo(tagInfo.getTagNo());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWater.setAmt(tagInfo.getCost());
				serviceWater.setAulAmt(tagInfo.getChargeCost());
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				serviceWater.setOperId(customer.getOperId());
				serviceWater.setOperNo(customer.getOperNo());
				serviceWater.setOperName(customer.getOperName());
				serviceWater.setPlaceId(customer.getPlaceId());
				serviceWater.setPlaceNo(customer.getPlaceNo());
				serviceWater.setPlaceName(customer.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setSerType(serType);
				serviceWater.setRemark(remark);
				serviceWaterDao.save(serviceWater);
			}
			
		} catch (Exception e) {
			logger.error("电子标签挂起失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 电子标签迁移
	 * @param  tagInfo		电子标签信息
	 * @param  clientID		客户ID
	 * @param  vehicleID	车辆ID
	 * @author zhengwenhai
	 */
	@Override
	public Map<String,Object> saveTagInfoMigrate(Customer customer,TagInfo tagInfo, Long clientID,
			Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String replaceType,String recoverReason) {
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			tagInfo = tagInfoDao.findById(tagInfo.getId());
			
			//澳门通不需要迁移审核
			
			/*int count = tagMigrateRecordDao.queryCountByTagNo(tagInfo.getTagNo(), TagMigrateReqAhtuStateEnum.wateAuth.getValue());
			if(count!=0){
				map.put("flag", "hasMigrateAuth");
				return map;
			}*/
			
			String blackFlag=tagInfo.getBlackFlag();
			//客服流水
			//调用接口更新
			VehicleInfo vehicle = vehicleInfoService.findById(vehicleID);
			VehicleInfo Oldvehicle=vehicleInfoDao.findByTagNo(tagInfo.getTagNo());
			
			
			
			VehicleBussiness vehicleBussiness = null;
			String vehicleType = null;
			VehicleInfoHis vehicleInfoHis = null;
			Long oldCustomerId =null;
			Long oldVehicleId =null;
			if(Oldvehicle==null){
				vehicleBussiness = vehicleBussinessDao.findByTagNo(tagInfo.getTagNo());
				if(vehicleBussiness!=null){
					String place = vehicleBussiness.getVehiclePlate();
					String color = vehicleBussiness.getVehicleColor();
					vehicleInfoHis = vehicleInfoHisDao.findByHisVc(place,color);
				}
				if(vehicleInfoHis!=null)vehicleType = vehicleInfoHis.getNSCVehicleType();
				if(vehicleInfoHis!=null)oldCustomerId = vehicleInfoHis.getCustomerID();
				if(vehicleInfoHis!=null)oldVehicleId = vehicleInfoHis.getId();
			}else{
				vehicleType = Oldvehicle.getNSCVehicleType();
				oldCustomerId = Oldvehicle.getCustomerID();
				oldVehicleId = Oldvehicle.getId();
			}
			
			//澳门通不需要迁移审核
			/*if(vehicleType!=null && !vehicle.getNSCVehicleType().equals(vehicleType)){
				
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagInfo.getTagNo());
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID((long)operatorId);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operPlaceId);
				tagBusinessRecord.setBusinessType("6");//发行
//				tagBusinessRecord.setInstallmanID(25255L);//安装人员
				tagBusinessRecord.setMemo("迁移");
				tagBusinessRecord.setFromID(tagInfo.getId());
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
				if(Oldvehicle!=null)tagBusinessRecord.setBussinessid(Oldvehicle.getHisSeqId());//迁移的时候用来记录车辆的Hisid
				tagBusinessRecord.setReason(recoverReason);
				tagBusinessRecord.setFaultType(replaceType);
				TagMigrateRecord tagMigrateRecord = new TagMigrateRecord();
				tagMigrateRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMStagMigraterecord_NO"));
				tagMigrateRecord.setTagNo(tagInfo.getTagNo());
				tagMigrateRecord.setCustomerId(oldCustomerId);
				tagMigrateRecord.setVehicleId(oldVehicleId);
				tagMigrateRecord.setNewcustomerId(clientID);
				tagMigrateRecord.setNewvehicleId(vehicleID);
				tagMigrateRecord.setAuthState(TagMigrateReqAhtuStateEnum.wateAuth.getValue());
				tagMigrateRecord.setReqdate(new Date());
				tagMigrateRecord.setOperId(tagBusinessRecord.getOperID());
				tagMigrateRecord.setOperName(tagBusinessRecord.getOperName());
				tagMigrateRecord.setOperNo(tagBusinessRecord.getOperNo());
				tagMigrateRecord.setPlaceId(tagBusinessRecord.getOperplaceID());
				tagMigrateRecord.setPlaceName(tagBusinessRecord.getPlaceName());
				tagMigrateRecord.setPlaceNo(tagBusinessRecord.getPlaceNo());
				tagMigrateRecord.setReason(recoverReason);
				tagMigrateRecord.setFaultType(replaceType);
				
				Customer oldCustomer = customerDao.findById(oldCustomerId);
				Customer newCustomer = null;
				tagMigrateRecord.setOrgan(oldCustomer.getOrgan());
				if(vehicle.getCustomerID()==oldCustomerId){
					tagMigrateRecord.setNewOrgan(oldCustomer.getOrgan());
				}else{
					newCustomer = customerDao.findById(vehicle.getCustomerID());
					tagMigrateRecord.setNewOrgan(newCustomer.getOrgan());
				}
				
				tagBusinessRecordDao.save(tagBusinessRecord);
				tagMigrateRecordDao.save(tagMigrateRecord);
				map.put("flag", "noWriteCard");
				return map;
			}*/
			
			
			
			
			UnifiedParam unifiedParam = new UnifiedParam();
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			//更新对应车辆信息的写导入卡标志
			
			tagInfo.setClientID(clientID);//可能改变标签对应的用户
			carObuCardInfo.setVehicleID(vehicleID);
			carObuCardInfo.setTagID(tagInfo.getId());
			
			//set这些修正人信息，为了传到obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)来可以获取并update数据库
			tagInfo.setCorrectTime(new Date());//更新时间
			tagInfo.setCorrectOperID(operatorId);
			tagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			tagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			tagInfo.setCorrectPlaceID(operPlaceId);
			tagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			tagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("5");//5 代表迁移
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){	
				//修改澳门通持卡人绑定关系表(csms_cardholder_info)，电子标签迁移到另一辆车，那么电子标签的持卡人可能也改变，所以要update
				/*CardHolderInfo cardHolderInfo = cardHolderInfoDao.findByTagId(tagInfo.getId());
				CardHolderInfo newCardHolderInfo = cardHolderInfoDao.findByVehicle(vehicleID);//新车的持卡人
				if(newCardHolderInfo!=null){
					cardHolderInfo.setMacaoBankAccountId(newCardHolderInfo.getMacaoBankAccountId());
					cardHolderInfoDao.update(cardHolderInfo);
				}*/
				
				// 记录电子标签业务操作记录
				//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagInfo.getTagNo());
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID((long)operatorId);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operPlaceId);
				tagBusinessRecord.setBusinessType("6");//发行
//				tagBusinessRecord.setInstallmanID(25255L);//安装人员
				tagBusinessRecord.setMemo("迁移");
				tagBusinessRecord.setFromID(tagInfo.getId());
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
				
				OMSParam omsParam = omsParamDao.findById(Long.parseLong(replaceType));
				tagBusinessRecord.setFaultType(omsParam.getParamValue());
				tagBusinessRecord.setFaultTypeId(omsParam.getId());
				omsParam = omsParamDao.findById(Long.parseLong(recoverReason));
				tagBusinessRecord.setReason(omsParam.getParamValue());
				tagBusinessRecord.setReasonId(omsParam.getId());
				
				if(Oldvehicle!=null)tagBusinessRecord.setBussinessid(Oldvehicle.getHisSeqId());//迁移的时候用来记录车辆的Hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//增加车辆业务记录
				vehicleBussiness = new VehicleBussiness();
				vehicleBussiness.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO").toString()));
				vehicleBussiness.setCustomerID(clientID);
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setTagNo(tagInfo.getTagNo());
				//vehicleBussiness.setType("65");
				vehicleBussiness.setType(VehicleBussinessEnum.tagMigrate.getValue());
				vehicleBussiness.setOperID((long)operatorId);
				vehicleBussiness.setPlaceID((long)operPlaceId);
				//新增的字段
				vehicleBussiness.setOperName(tagBusinessRecord.getOperName());
				vehicleBussiness.setOperNo(tagBusinessRecord.getOperNo());
				vehicleBussiness.setPlaceName(tagBusinessRecord.getPlaceName());
				vehicleBussiness.setPlaceNo(tagBusinessRecord.getPlaceNo());
				
				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("电子标签迁移");
				vehicleBussinessDao.save(vehicleBussiness);
				
				
				
				carObuCardInfo=carObuCardInfoDao.findByVehicleID(vehicle.getId());
				
				vehicleInfoHis = new VehicleInfoHis();
				BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
				vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
				vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
				vehicleInfoHis.setGenTime(new Date());
				vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicle);
				vehicle.setIsWriteOBU("1");
				vehicle.setHisSeqId(vehicleInfoHis.getId());
				vehicleInfoDao.update(vehicle);
				
				if(carObuCardInfo!=null){
					if("0".equals(vehicle.getIsWriteOBU())){
						carObuCardInfo.setTagID(null);
					}
					if("0".equals(vehicle.getIsWriteCard())){
						if(carObuCardInfo.getAccountCID()!=null){
							 accountCDao.updateBind("0", carObuCardInfo.getAccountCID());
							 carObuCardInfo.setAccountCID(null);
						}
						if(carObuCardInfo.getPrepaidCID()!=null){
							prepaidCDao.updateBind("0", carObuCardInfo.getPrepaidCID());
							carObuCardInfo.setPrepaidCID(null);
						}
					}
					carObuCardInfoDao.update(carObuCardInfo);
				}
				
				if(BlackFlagEnum.black.getValue().equals(blackFlag)){
					//解除标签挂起
					blackListService.saveOBURelieveStopUse(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());
				}
			}
			
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByTagNo(tagInfo.getTagNo());
//			MacaoCardCustomer macaoCardCustomer =macaoCardCustomerDao.getMacaoCardCustomerByTagNo(tagInfo.getTagNo());
			//获取电子标签信息
			tagInfo = macaoDao.getTagInfoByTagNo(tagInfo.getTagNo());
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setNewCardNo(tagInfo.getTagNo());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setAmt(tagInfo.getCost());
			serviceWater.setAulAmt(tagInfo.getChargeCost());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType("306");
			serviceWater.setRemark("电子标签迁移");
			serviceWaterDao.save(serviceWater);
			
			map.put("flag", "success");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoMigrate电子标签迁移失败"+e.getMessage());
			throw new ApplicationException();
		}
	}
	
	/**
	 * 注销电子标签
	 */
	@Override
	public void saveCancelTagInfo(Customer customer,Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag) {
		try {	
			TagInfo tagInfo = new TagInfo();
			tagInfo = tagInfoDao.findById(tagInfoId);
			
			//客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
			serviceFlowRecord.setId(Long.parseLong(seq.toString()));
			serviceFlowRecord.setClientID(clientID);
			serviceFlowRecord.setServiceFlowNO(seq.toString());
			serviceFlowRecord.setCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(TagStateEnum.cancel.getValue());
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(5);//注销
			serviceFlowRecord.setOperID((long)operatorId);
			serviceFlowRecord.setPlaceID((long)operPlaceId);
			//新增的字段
			serviceFlowRecord.setOperName(tagBusinessRecord.getOperName());
			serviceFlowRecord.setOperNo(tagBusinessRecord.getOperNo());
			serviceFlowRecord.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
			//调用接口更新
			UnifiedParam unifiedParam = new UnifiedParam();
			
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setVehicleID(vehicleID);
			
			//set这些修正人信息，为了传到obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)来可以获取并update数据库
			tagInfo.setCorrectTime(new Date());//更新时间
			tagInfo.setCorrectOperID(operatorId);
			tagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			tagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			tagInfo.setCorrectPlaceID(operPlaceId);
			tagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			tagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			
			if("0".equals(hasTag)){
				tagInfo.setBlackFlag(BlackFlagEnum.black.getValue());
			}else{
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
			}
			
			
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("4");
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){	
				VehicleInfo vehicle = vehicleInfoDao.findById(vehicleID);
				
				//澳门通注销电子标签，还要解除CarUserCardInfo的绑定关系
				/*CarUserCardInfo carUserCardInfo = carUserCardInfoDao.findByVehicleId(vehicleID);
				carUserCardInfo.setTagId(null);
				carUserCardInfoDao.updateCarUserCardInfo(carUserCardInfo);*/
				
				// 记录电子标签业务操作记录，业务类型为“注销”，当前状态为”注销“（‘4’）
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagInfo.getTagNo());
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID((long)operatorId);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operPlaceId);
				tagBusinessRecord.setBusinessType("7");//注销
//				tagBusinessRecord.setInstallmanID(25255L);//安装人员
				tagBusinessRecord.setCurrentTagState(TagStateEnum.cancel.getValue());//注销
				tagBusinessRecord.setMemo("注销");
				tagBusinessRecord.setFromID(tagInfoId);
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
				if(vehicle != null) tagBusinessRecord.setBussinessid(vehicle.getHisSeqId());//注销的时候记录的车辆hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				
				//新增车辆信息业务记录
				VehicleBussiness vehicleBussiness = null;
				if(vehicle != null){
					vehicleBussiness = new VehicleBussiness();
					vehicleBussiness.setCustomerID(customer.getId());
					vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
					vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
					vehicleBussiness.setTagNo(tagInfo.getTagNo());
					if("0".equals(hasTag)){
						vehicleBussiness.setType(VehicleBussinessEnum.tagCancelWithoutTag.getValue());
						vehicleBussiness.setMemo("电子标签无标签注销");
					}else{
						vehicleBussiness.setType(VehicleBussinessEnum.tagCancelWithTag.getValue());
						vehicleBussiness.setMemo("电子标签有标签注销");
					}
					vehicleBussiness.setOperID(tagBusinessRecord.getOperID());
					vehicleBussiness.setPlaceID(tagBusinessRecord.getOperplaceID());
					//新增的字段
					vehicleBussiness.setOperName(tagBusinessRecord.getOperName());
					vehicleBussiness.setOperNo(tagBusinessRecord.getOperNo());
					vehicleBussiness.setPlaceName(tagBusinessRecord.getPlaceName());
					vehicleBussiness.setPlaceNo(tagBusinessRecord.getPlaceNo());
					vehicleBussiness.setCreateTime(new Date());
					
					vehicleBussinessDao.save(vehicleBussiness);
				}
				
//				//获取持卡人信息
				MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByTagInfoId(tagInfoId);
//				MacaoCardCustomer macaoCardCustomer = macaoCardCustomerDao.getMacaoCardCustomerById(tagInfoId);
//				
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setNewCardNo(tagInfo.getTagNo());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWater.setAmt(tagInfo.getCost());
				serviceWater.setAulAmt(tagInfo.getChargeCost());
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness != null)
					serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(customer.getOperId());
				serviceWater.setOperNo(customer.getOperNo());
				serviceWater.setOperName(customer.getOperName());
				serviceWater.setPlaceId(customer.getPlaceId());
				serviceWater.setPlaceNo(customer.getPlaceNo());
				serviceWater.setPlaceName(customer.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setSerType("305");
				serviceWater.setRemark("电子标签注销");
				serviceWaterDao.save(serviceWater);
				
				
				String cardNo = "";
				String cardType = "";
				//写给铭鸿的清算数据：用户状态信息
				//2017-08-18  除了注销的标签，其他状态的都可以注销，所以不一定有车辆id
				if(vehicle != null){
					cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签注销");
				}
				
				if("0".equals(hasTag)){
					blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());
				}
				
			}
			

		} catch (Exception e) {
			logger.error("电子标签注销失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 更换电子标签
	 */
	@Override
	public Map<String, Object> saveReplaceTagInfo(Customer customer,Map<String, Object> tagReplace,BigDecimal chargeFee, String newTagNo, Long tagInfoId, Long clientID,
			Long vehicleID, String replaceReason, Long installmanID, String memo, Long operID, Long operplaceID,
			TagBusinessRecord tagBusinessRecord, Long productInfoId, BigDecimal realCost, String installmanName,
			String productTypeCode) {
		try {
			TagInfo newTagInfo = null;
			TagInfo oldTagInfo = null;
			
			//车辆
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(vehicleID);

			// 生成一条新的电子标签发行记录
			Long newTagInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSTaginfo_NO");

			UnifiedParam unifiedParam = new UnifiedParam();
			oldTagInfo = new TagInfo();
			// 找出旧标签再给予新电子标签的属性（其中维保时间、有效启用时间、有效截止时间与旧电子标签的一致）
			newTagInfo = tagInfoDao.findById(tagInfoId);
			
			Map<String,Object> map=new HashMap<String,Object>();
			
			if(newTagInfo==null){
				map.put("success", false);
				map.put("message", "旧电子标签号未发行");
				return map;
			}

			//InterfaceRecord interfaceRecordReclaim = null;
			//Map<String, Object> mapReclaim = null;
			//boolean result = false;
			//判断库存是否可回收电子标签。电子标签有偿更换，不需要回收旧标签
			/*if(chargeFee.compareTo(new BigDecimal("0"))==0){
				if (newTagInfo.getTagNo().length()==16) {
					//此时获得的newTagInfo.getTagNo()是旧电子标签
					mapReclaim = inventoryService.omsInterface(newTagInfo.getTagNo(), "2", interfaceRecordReclaim,"",
							operplaceID,tagBusinessRecord.getOperID(),tagBusinessRecord.getOperName(),"2","",null,new BigDecimal("0"),"43");
					result = (Boolean) mapReclaim.get("result");
					if (!result) {
						map.put("success", false);
						map.put("message", mapReclaim.get("message").toString());
						return map;
					}
				}
			}*/
			
			InterfaceRecord interfaceRecordIssue = null;
			Map<String, Object> mapIssue = null;
			//43 电子标签更换
			mapIssue = inventoryServiceForAgent.omsInterface(newTagNo, "1", interfaceRecordIssue, "replace", null, null, "", "2", "", null, chargeFee, "43");
			
//			maptemp = inventoryServiceForAgent.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord,"issue",
//					tagInfo.getIssueplaceID(),tagInfo.getOperID(),tagInfo.getOperName(),"2","customPoint",productnullInfoId,tagInfo.getChargeCost(),"41");
			boolean resultIssue = (Boolean) mapIssue.get("result");
			if (!resultIssue) {
				map.put("success", false);
				map.put("message", mapIssue.get("message").toString());
				return map;
			}
			Map<String,Object> m = (Map<String,Object>)mapIssue.get("initializedOrNotMap");
			
			//原电子标签更换业务
			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(clientID);

			mainAccountInfo = mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());

			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo, serviceFlowRecord);
			serviceFlowRecord.setCardTagNO(newTagNo);
			serviceFlowRecord.setOldCardTagNO(newTagInfo.getTagNo());
			serviceFlowRecord.setCurrAvailableBalance(chargeFee);
			serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-" + chargeFee));
			serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-" + chargeFee));
			serviceFlowRecord.setCurrState(newTagInfo.getTagState());
			serviceFlowRecord.setAfterState(newTagInfo.getTagState());
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(2);// 更换
			serviceFlowRecord.setIsDoFlag("0");
			serviceFlowRecord.setOperID(operID);
			serviceFlowRecord.setPlaceID(operplaceID);
			//新增字段
			serviceFlowRecord.setOperName(tagBusinessRecord.getOperName());
			serviceFlowRecord.setOperNo(tagBusinessRecord.getOperNo());
			serviceFlowRecord.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagBusinessRecord.getPlaceNo());

			if(newTagInfo.getId()!=null)oldTagInfo.setId(newTagInfo.getId());//旧电子标签id
			if(StringUtil.isNotBlank(newTagInfo.getTagNo()))oldTagInfo.setTagNo(newTagInfo.getTagNo());//旧电子标签号
			oldTagInfo.setChargeCost(chargeFee);// 要set吗？
			//把旧ObuSerial存进去
			if(newTagInfo.getObuSerial()!=null){
				oldTagInfo.setObuSerial(newTagInfo.getObuSerial());
			}
			//
			//设置obuid
			if(m!=null){
				newTagInfo.setObuSerial((String)m.get("obuSerial"));
			}
			if(newTagInfo.getIssuetime()!=null)oldTagInfo.setIssuetime(newTagInfo.getIssuetime());
			unifiedParam.setTagInfo(oldTagInfo);//旧电子标签

			newTagInfo.setChargeCost(chargeFee);
			newTagInfo.setTagNo(newTagNo);
			newTagInfo.setId(newTagInfoId);
			//newTagInfo.setTagState("1");// 新标签状态为“正常”
			newTagInfo.setTagState(TagStateEnum.normal.getValue());
			//发行时间为当前时间     依据：澳门通分册-20170411-V0.6 的标签更换流程说明
			newTagInfo.setIssuetime(new Date());//发行时间
			//newTagInfo.setMaintenanceTime(new Date());//维保时间   由于newTagInfo是直接根据旧标签id查找出来的，里面的维保时间就是旧标签的维保时间
			newTagInfo.setCost(realCost);
			newTagInfo.setInstallmanName(installmanName);
			newTagInfo.setProductType(productTypeCode);
			//
			newTagInfo.setOperID(tagBusinessRecord.getOperID());
			newTagInfo.setOperName(tagBusinessRecord.getOperName());
			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			newTagInfo.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			newTagInfo.setCorrectTime(newTagInfo.getIssuetime());//更新时间
			newTagInfo.setCorrectOperID(newTagInfo.getOperID());
			newTagInfo.setCorrectOperName(newTagInfo.getOperName());
			newTagInfo.setCorrectOperNo(newTagInfo.getOperNo());
			newTagInfo.setCorrectPlaceID(newTagInfo.getIssueplaceID());
			newTagInfo.setCorrectPlaceName(newTagInfo.getPlaceName());
			newTagInfo.setCorrectPlaceNo(newTagInfo.getPlaceNo());
			
			
			//设置obuSerial
			/*if(mapIssue.get("obuSerial")!=null){
				newTagInfo.setObuSerial((String)mapIssue.get("obuSerial"));
			}*/
			//有效启用时间用新的，有效结束时间是如果收费了就用新的，没收费就用旧的。。。。（废除）
			//有效截止日期、有效启用日期均从营运系统获取新标签的有效截止时间、有效启用时间    依据 澳门通分册-20170411-V0.6 的标签更换功能流程说明
			newTagInfo.setStartTime((Date)m.get("startDate"));
			newTagInfo.setEndTime((Date)m.get("endDate"));
			
			
			unifiedParam.setNewTagInfo(newTagInfo);// 新电子标签
			// 如果收费等于0
			unifiedParam.setType("2");
			// 车卡标签绑定表的电子标签id要改为新的
			CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(tagInfoId, vehicleID);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);

			if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
				map.put("success", false);
				map.put("message", "电子标签更换失败");
				return map;

			}
			
			//增加澳门通持卡人绑定关系表(csms_cardholder_info)
			CardHolderInfo cardHolderInfo=new CardHolderInfo();
			CardHolderInfo newCardHolderInfo = cardHolderInfoDao.findByVehicle(carObuCardInfo.getVehicleID());
			Long holderId = sequenceUtil.getSequenceLong("SEQ_CSMSCARDHOLDERINFO_NO");
			cardHolderInfo.setMacaoBankAccountId(newCardHolderInfo.getMacaoBankAccountId());
			cardHolderInfo.setId(holderId);
			cardHolderInfo.setType("3");//3电子标签
			cardHolderInfo.setTypeId(newTagInfo.getId());
			cardHolderInfoDao.save(cardHolderInfo);
			
			
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
			/*AccountFundChange accountFundChange=new AccountFundChange();
			BigDecimal SEQ_CSMSAccountFundChange_NO = sequenceUtil.getSequence("SEQ_CSMSAccountFundChange_NO");
			accountFundChange.setId(Long.valueOf(SEQ_CSMSAccountFundChange_NO.toString()));
			accountFundChange.setFlowNo(StringUtil.getFlowNo());
			
			accountFundChange.setBeforeAvailableBalance(new BigDecimal("0"));
			accountFundChange.setBeforeFrozenBalance(new BigDecimal("0"));
			accountFundChange.setBeforepreferentialBalance(new BigDecimal("0"));
			accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("0"));
			accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("0"));
			
			accountFundChange.setMainId(mainAccountInfo.getId());
			
			accountFundChange.setCurrAvailableBalance(new BigDecimal("0"));
			accountFundChange.setCurrFrozenBalance(new BigDecimal("0"));
			accountFundChange.setCurrpreferentialBalance(new BigDecimal("0"));
			accountFundChange.setCurrAvailableRefundBalance(new BigDecimal("0"));
			accountFundChange.setCurrRefundApproveBalance(new BigDecimal("0"));
			
			accountFundChange.setAfterAvailableBalance(new BigDecimal("0"));
			accountFundChange.setAfterFrozenBalance(new BigDecimal("0"));
			accountFundChange.setAfterpreferentialBalance(new BigDecimal("0"));
			accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("0"));
			accountFundChange.setAfterRefundApproveBalance(new BigDecimal("0"));
			
			accountFundChange.setChangeType("7");
			accountFundChange.setMemo("电子标签更换工本费扣费修改");
			accountFundChange.setChgOperID(tagBusinessRecord.getOperID());
			accountFundChange.setChgPlaceID(operplaceID);
			accountFundChange.setOperName(tagBusinessRecord.getOperName());
			accountFundChange.setOperNo(tagBusinessRecord.getOperNo());
			accountFundChange.setPlaceName(tagBusinessRecord.getPlaceName());
			accountFundChange.setPlaceNo(tagBusinessRecord.getPlaceNo());
			accountFundChangeDao.saveChange(accountFundChange);*/
				
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(newTagInfo.getClientID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setTagNo(newTagInfo.getTagNo());
			//vehicleBussiness.setType("64");
			vehicleBussiness.setType(VehicleBussinessEnum.tagReplace.getValue());
			vehicleBussiness.setOperID(operID);
			vehicleBussiness.setPlaceID(operplaceID);
			//新增字段
			vehicleBussiness.setOperName(tagBusinessRecord.getOperName());
			vehicleBussiness.setOperNo(tagBusinessRecord.getOperNo());
			vehicleBussiness.setPlaceName(tagBusinessRecord.getPlaceName());
			vehicleBussiness.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("电子标签更换");
			
			vehicleBussinessDao.save(vehicleBussiness);
			
			// 保存电子标签维护记录，维护类型为“更换”
			// 要重新set更换后的新的电子标签id和电子标签号,电子标签id也要为新发行的记录的id
			BigDecimal SEQ_CSMSTagMainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
			Long id = Long.valueOf(SEQ_CSMSTagMainRecord_NO.toString());
			
			TagMainRecord tagMainRecord = new TagMainRecord();
			tagMainRecord.setId(id);
			tagMainRecord.setTagInfoID(newTagInfoId);
			tagMainRecord.setTagNo(oldTagInfo.getTagNo());
			tagMainRecord.setNewTagNo(newTagNo);
			tagMainRecord.setClientID(clientID);
			tagMainRecord.setVehicleID(vehicleID);
			tagMainRecord.setMaintainType("2");
			tagMainRecord.setInstallman(installmanID+"");
			tagMainRecord.setChargeCost(new BigDecimal("0"));
			//设置故障类型和故障原因
			String[] reason = replaceReason.split("-");
			OMSParam omsParam = omsParamDao.findById(Long.parseLong(reason[0]));
			tagMainRecord.setFaultType(omsParam.getParamValue());
			tagMainRecord.setFaultTypeId(omsParam.getId());
			omsParam = omsParamDao.findById(Long.parseLong(reason[1]));
			tagMainRecord.setReason(omsParam.getParamValue());
			tagMainRecord.setReasonId(omsParam.getId());
			
			tagMainRecord.setMemo(memo);
			tagMainRecord.setOperID(tagBusinessRecord.getId());
			tagMainRecord.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			tagMainRecord.setOperNo(tagBusinessRecord.getOperNo());
			tagMainRecord.setOperName(tagBusinessRecord.getOperName());
			tagMainRecord.setPlaceNo(tagBusinessRecord.getPlaceName());
			tagMainRecord.setPlaceName(tagBusinessRecord.getPlaceName());
			tagMainRecord.setIssuetime(new Date());
			
			tagReplaceDao.saveTagMainRecord(tagMainRecord);
			//TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(clientID);
			tagBusinessRecord.setTagNo(newTagNo);
			tagBusinessRecord.setVehicleID(vehicleID);
			tagBusinessRecord.setOperID(operID);
			tagBusinessRecord.setOperTime(new Date());
			tagBusinessRecord.setReason(tagMainRecord.getReason());
			tagBusinessRecord.setFaultType(tagMainRecord.getFaultType());
			tagBusinessRecord.setOperplaceID(operplaceID);
			tagBusinessRecord.setBusinessType("3");// 更换
			tagBusinessRecord.setInstallmanID(installmanID);// 安装人员
			//tagBusinessRecord.setCurrentTagState("1");// 正常
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());
			tagBusinessRecord.setMemo(memo);
			tagBusinessRecord.setFromID(newTagInfoId);
			tagBusinessRecord.setRealPrice(chargeFee);// --业务费用
			tagBusinessRecord.setBussinessid(tagMainRecord.getId());
			//存放ObuSerial
			if(newTagInfo.getObuSerial()!=null){
				tagBusinessRecord.setObuSerial(newTagInfo.getObuSerial());
			}
			if(oldTagInfo.getObuSerial()!=null){
				tagBusinessRecord.setOldObuSerial(oldTagInfo.getObuSerial());
			}
			
			if(StringUtil.isNotBlank(oldTagInfo.getTagNo()))tagBusinessRecord.setOldTagNo(oldTagInfo.getTagNo());
			if(oldTagInfo.getIssuetime()!=null)tagBusinessRecord.setOldTagIssueTime(oldTagInfo.getIssuetime());
			
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			//旧标签注销
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					oldTagInfo.getTagNo(),oldTagInfo.getObuSerial(), oldTagInfo.getStartTime(), oldTagInfo.getEndTime(), "标签更换业务的旧标签注销");
			
			//写给铭鸿的清算数据：用户状态信息
			//新标签发行
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					newTagInfo.getTagNo(),newTagInfo.getObuSerial(), newTagInfo.getStartTime(), newTagInfo.getEndTime(), "标签更换业务的新标签发行");
			
			
			//保存旧电子标签黑名单流水
			blackListService.saveOBUCancel(oldTagInfo.getObuSerial(), new Date()
					, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
					tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
					new Date());
			
			
			oldTagInfo.setOperID(operID);
			oldTagInfo.setIssueplaceID(operplaceID);
			//新增字段
			oldTagInfo.setOperName(tagBusinessRecord.getOperName());
			oldTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			oldTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			oldTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			oldTagInfo.setClientID(clientID);

			//电子标签有偿更换，不需要回收旧标签
			/*if (oldTagInfo.getTagNo().length()==16) {
				//回收成功后，更新营运接口调用登记记录的客服状态
				//电子标签有偿更换，不需要回收旧标签。mapReclaim时即没有调用营运回收旧标签
				if(mapReclaim!=null){
					interfaceRecordReclaim = (InterfaceRecord) mapReclaim.get("interfaceRecord");
					if (interfaceRecordReclaim != null&&interfaceRecordReclaim.getCardno()!=null) {
						interfaceRecordReclaim.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecordReclaim);
					}
				}
				
			}*/
			
			/*//电子标签更换成功后，若tagTakeDetail不为null
			if(tagTakeDetail!=null){
				//tagTakeDetail.setTagState("1");
				tagTakeDetailDao.updateTagStateByTagNo("1", tagTakeDetail.getTagNo());
			}*/
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			/*if(mapIssue!=null){
				interfaceRecordIssue = (InterfaceRecord) mapIssue.get("interfaceRecord");
				if (interfaceRecordIssue != null&&interfaceRecordIssue.getCardno()!=null) {
					interfaceRecordIssue.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordIssue);
				}
			}*/
			//获取电子标签信息
			TagInfo tagInfo = macaoDao.getTagInfoByTagNo(newTagNo);
			
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = null;
			macaoBankAccount = macaoBankAccountDao.findByTagNo(tagInfo.getTagNo());
//			MacaoCardCustomer macaoCardCustomer = macaoCardCustomerDao.getMacaoCardCustomerByTagNo(tagInfo.getTagNo());
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setCardNo(((String[])tagReplace.get("tagNo"))[0]);
			serviceWater.setNewCardNo(newTagNo);
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setAmt(tagInfo.getCost());
			serviceWater.setAulAmt(tagInfo.getChargeCost());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("电子标签更换");
			serviceWater.setSerType("303");
			serviceWaterDao.save(serviceWater);
			
			map.put("success", true);
			map.put("message", "电子标签更换成功");
			return map;
			
		} catch (Exception e) {
			logger.error("电子标签更换失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 客户流水更新前
	 * 
	 * @param newMainAccountInfo
	 * @param serviceFlowRecord
	 * @return
	 */
	private ServiceFlowRecord getBeforeServiceFlowRecord(MainAccountInfo newMainAccountInfo,
			ServiceFlowRecord serviceFlowRecord) {
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
		serviceFlowRecord.setId(Long.parseLong(seq.toString()));
		serviceFlowRecord.setClientID(newMainAccountInfo.getMainId());
		serviceFlowRecord.setServiceFlowNO(seq.toString());

		serviceFlowRecord.setBeforeAvailableBalance(newMainAccountInfo.getAvailableBalance());
		serviceFlowRecord.setBeforeFrozenBalance(newMainAccountInfo.getFrozenBalance());
		serviceFlowRecord.setBeforepreferentialBalance(newMainAccountInfo.getPreferentialBalance());
		serviceFlowRecord.setBeforeAvailableRefundBalance(newMainAccountInfo.getAvailableRefundBalance());
		serviceFlowRecord.setBeforeRefundApproveBalance(newMainAccountInfo.getRefundApproveBalance());
		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));

		return serviceFlowRecord;
	}
	
	//清算接口    //原清算数据，没用了
		/*private void saveTollCardBlack(TagInfo tagInfo,TollCardBlackDet tollCardBlackDet,TollCardBlackDetSend tollCardBlackDetSend){
			try {
				VehicleInfo vehicleInfo=vehicleInfoDao.findByTagNo(tagInfo.getTagNo());
				String vehPlate=" ";
				if(vehicleInfo!=null){
					vehPlate=vehicleInfo.getVehiclePlate();
				}
				tollCardBlackDet.setLicense(vehPlate);
				tollCardBlackDetSend.setLicense(vehPlate);
				tollCardBlackDetDao.save(tollCardBlackDet);
				tollCardBlackDetSendDao.save(tollCardBlackDetSend);
			} catch (ApplicationException e) {
				e.printStackTrace();
				logger.error("保存清算黑名单数据失败"+tagInfo.getTagNo()+e.getMessage());
				throw new ApplicationException("保存清算数黑名单据失败"+tagInfo.getTagNo());
			}
		}*/
		
		/**
		 * @Description:TODO
		 * @param accountCInfo
		 * @param darkList
		 * @param genCau 产生原因
		 * @param state 状态
		 */
		public void saveDarkList(TagInfo tagInfo,DarkList darkList,String genCau,String state){
			Customer customer = customerDao.findById(tagInfo.getClientID());
			try {
				if(darkList == null){
					darkList = new DarkList();
					darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
					darkList.setCustomerId(tagInfo.getClientID());
					darkList.setCardNo(tagInfo.getTagNo());
					darkList.setObuSerial(tagInfo.getObuSerial());
					darkList.setCardType("3");
					darkList.setGenDate(new Date());
					darkList.setGencau(genCau);//产生原因	10—无卡注销。
					darkList.setGenmode("0");//产生方式	系统产生
					darkList.setOperId(tagInfo.getOperID());
					darkList.setPlaceId(tagInfo.getIssueplaceID());
					darkList.setOperNo(tagInfo.getOperNo());
					darkList.setOperName(tagInfo.getOperName());
					darkList.setPlaceNo(tagInfo.getPlaceNo());
					darkList.setPlaceName(tagInfo.getPlaceName());
					//darkList.setUpdateTime(updateTime);
					if(customer!=null){
						darkList.setUserNo(customer.getUserNo());
						darkList.setUserName(customer.getOrgan());
					}
					//darkList.setRemark(remark);
					darkList.setState(state);
					darkListDao.save(darkList);
					
				}else{
					darkList.setCustomerId(customer.getId());
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
					darkList.setGencau(genCau);
					darkList.setUpdateTime(new Date());
					darkList.setState(state);
					darkListDao.updateDarkList(darkList);
				}
				
			} catch (ApplicationException e) {
				e.printStackTrace();
				logger.error("保存清算黑名单数据失败"+tagInfo.getTagNo()+e.getMessage());
				throw new ApplicationException("保存清算数黑名单据失败"+tagInfo.getTagNo());
			}
		}

		/**
		 * 澳门通的电子标签注销列表查询
		 */
		@Override
		public Pager tagCancelList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType,
				String idCode, String endSixNo, Long id, MacaoCardCustomer macaoCardCustomer) {
			return tagInfoCancelDao.findTagCancelsForMacao(pager, tagNo, vehicleColor, vehiclePlate, idType, idCode, endSixNo, id, macaoCardCustomer);
		}
	
}
