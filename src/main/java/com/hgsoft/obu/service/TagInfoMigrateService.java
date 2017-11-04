package com.hgsoft.obu.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.TagMigrateReqAhtuStateEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UsingNatureEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.common.Enum.VehicleUsingTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.TagMigrateInfoDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.TagMigrateInfo;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagInfoMigrateDao;
import com.hgsoft.obu.dao.TagMigrateRecordDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.serviceInterface.ITagInfoMigrateService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagMirateReceipt;
import com.hgsoft.other.vo.receiptContent.tag.TagTransferReceipt;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;


/**
 * 电子标签发行
 * @author 
 * 2016年1月22日14:36:27
 */
@Service
public class TagInfoMigrateService implements ITagInfoMigrateService{
	
	private static Logger logger = Logger.getLogger(TagInfoMigrateService.class.getName());
	
	@Resource
	private TagInfoMigrateDao tagInfoMigrateDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	@Resource 
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private IVehicleInfoService vehicleInfoService; 
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	
	@Resource
	private DarkListDao darkListDao;
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private TagMigrateRecordDao tagMigrateRecordDao;
	@Resource
	private TagMigrateInfoDao tagMigrateInfoDao;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private OmsParamDao omsParamDao;
	@Resource
	private ReceiptDao receiptDao;
	
	/**
	 * 电子标签查询列表
	 * @param  pager		页码
	 * @param  tagInfo		电子标签信息
	 * @param  customer		客户信息
	 * @param  vehicleInfo  车辆信息
	 * @param  identificationCode6	车辆识别代码后六位
	 * @author zhengwenhai
	 */
	@Override
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6) {
		try {
			if(tagInfo != null)
				tagInfo.setTagState("2");
			Pager maps=tagInfoMigrateDao.tagInfoList(pager, tagInfo, vehicleInfo, customer, identificationCode6);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoList电子标签查询失败"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 电子标签查询列表
	 * @param  pager		页码
	 * @param  tagInfo		电子标签信息
	 * @param  customer		客户信息
	 * @param  vehicleInfo  车辆信息
	 * @param  identificationCode6	车辆识别代码后六位
	 * @author zhengwenhai
	 */
	@Override
	public Pager tagInfoListForAMMS(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6,String bankCode) {
		try {
			if(tagInfo != null)
				tagInfo.setTagState("2");
			Pager maps=tagInfoMigrateDao.tagInfoListForAMMS(pager, tagInfo, vehicleInfo, customer, identificationCode6,bankCode);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoList电子标签查询失败"+e.getMessage());
		}
		return null;
	}
	
	@Override
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6,MacaoCardCustomer macaoCustomer) {
		try {
			if(tagInfo != null)
				tagInfo.setTagState("2");
			Pager maps=tagInfoMigrateDao.tagInfoList(pager, tagInfo, vehicleInfo, customer, identificationCode6,macaoCustomer);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoList电子标签查询失败"+e.getMessage());
		}
		return null;
	}
	

	/**
	 * 电子标签详情
	 * @param  tagInfo		页码
	 * @param  clientID		电子标签信息
	 * @param  vehicleID		电子标签信息
	 * @author zhengwenhai
	 */
	@Override
	public Map<String, Object> tagInfoDetail(TagInfo tagInfo, Long clientID, Long vehicleID) {
		try {
			return tagInfoMigrateDao.tagInfoDetail(tagInfo,clientID,vehicleID);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoDetail电子标签详情请求失败"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 电子标签迁移
	 * @param  tagInfo		电子标签信息
	 * @param  clientID		客户ID
	 * @param  vehicleID	车辆ID
	 * @author zhengwenhai
	 */
	@Override
	public Map<String,Object> saveTagInfoMigrate(TagInfo tagInfo, Long clientID,Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String replaceType,String recoverReason,Map<String,Object> params) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			//blackflag最好从action传进来，action里面判断csms_black_list_status
			String blackFlag=tagInfo.getBlackFlag();
			
			
			tagInfo = tagInfoDao.findById(tagInfo.getId());
			Customer customer = customerDao.findById(clientID);
			//标签迁移不需要审核了，我反正是服咯
			/*int count = tagMigrateRecordDao.queryCountByTagNo(tagInfo.getTagNo(), TagMigrateReqAhtuStateEnum.wateAuth.getValue());
			if(count!=0){
				map.put("flag", "hasMigrateAuth");
				return map;
			}*/
			
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
			
			
//			if(vehicleType!=null && !vehicle.getNSCVehicleType().equals(vehicleType)){
//				
//				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
//				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
//				tagBusinessRecord.setClientID(clientID);
//				tagBusinessRecord.setTagNo(tagInfo.getTagNo());
//				tagBusinessRecord.setVehicleID(vehicleID);
//				tagBusinessRecord.setOperID((long)operatorId);
//				tagBusinessRecord.setOperTime(new Date());
//				tagBusinessRecord.setOperplaceID(operPlaceId);
//				tagBusinessRecord.setBusinessType("6");//迁移
////				tagBusinessRecord.setInstallmanID(25255L);//安装人员
//				tagBusinessRecord.setMemo("迁移");
//				tagBusinessRecord.setFromID(tagInfo.getId());
//				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
//				if(Oldvehicle!=null)tagBusinessRecord.setBussinessid(Oldvehicle.getHisSeqId());//迁移的时候用来记录车辆的Hisid
//				tagBusinessRecord.setReason(recoverReason);
//				tagBusinessRecord.setFaultType(replaceType);
//				TagMigrateRecord tagMigrateRecord = new TagMigrateRecord();
//				tagMigrateRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMStagMigraterecord_NO"));
//				tagMigrateRecord.setTagNo(tagInfo.getTagNo());
//				tagMigrateRecord.setCustomerId(oldCustomerId);
//				tagMigrateRecord.setVehicleId(oldVehicleId);
//				tagMigrateRecord.setNewcustomerId(clientID);
//				tagMigrateRecord.setNewvehicleId(vehicleID);
//				tagMigrateRecord.setAuthState(TagMigrateReqAhtuStateEnum.wateAuth.getValue());
//				tagMigrateRecord.setReqdate(new Date());
//				tagMigrateRecord.setOperId(tagBusinessRecord.getOperID());
//				tagMigrateRecord.setOperName(tagBusinessRecord.getOperName());
//				tagMigrateRecord.setOperNo(tagBusinessRecord.getOperNo());
//				tagMigrateRecord.setPlaceId(tagBusinessRecord.getOperplaceID());
//				tagMigrateRecord.setPlaceName(tagBusinessRecord.getPlaceName());
//				tagMigrateRecord.setPlaceNo(tagBusinessRecord.getPlaceNo());
//				tagMigrateRecord.setReason(recoverReason);
//				tagMigrateRecord.setFaultType(replaceType);
//				
//				Customer oldCustomer = customerDao.findById(oldCustomerId);
//				Customer newCustomer = null;
//				tagMigrateRecord.setOrgan(oldCustomer.getOrgan());
//				if(vehicle.getCustomerID()==oldCustomerId){
//					tagMigrateRecord.setNewOrgan(oldCustomer.getOrgan());
//				}else{
//					newCustomer = customerDao.findById(vehicle.getCustomerID());
//					tagMigrateRecord.setNewOrgan(newCustomer.getOrgan());
//				}
//				
//				tagBusinessRecordDao.save(tagBusinessRecord);
//				tagMigrateRecordDao.save(tagMigrateRecord);
//				
//				
//				//调整的客服流水
//				ServiceWater serviceWater = new ServiceWater();
//				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
//				
//				serviceWater.setId(serviceWater_id);
//				
//				if(customer!=null)serviceWater.setCustomerId(customer.getId());
//				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
//				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
//				serviceWater.setCardNo(tagInfo.getTagNo());
//				serviceWater.setObuSerial(tagInfo.getObuSerial());
//				serviceWater.setSerType("306");//306电子标签迁移
//				
//				//serviceWater.setAmt(tagInfo.getCost());//应收金额
//				serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
//				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
//				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
//				//serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
//				serviceWater.setOperId(tagBusinessRecord.getOperID());
//				serviceWater.setOperName(tagBusinessRecord.getOperName());
//				serviceWater.setOperNo(tagBusinessRecord.getOperNo());
//				serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
//				serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
//				serviceWater.setPlaceNo(tagInfo.getPlaceNo());
//				serviceWater.setRemark("自营客服系统：电子标签迁移");
//				serviceWater.setOperTime(new Date());
//				
//				serviceWaterDao.save(serviceWater);
//				
//				
//				map.put("flag", "noWriteCard");
//				return map;
//			}
			
			
			
			
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
				tagBusinessRecord.setBusinessType("6");//迁移
//				tagBusinessRecord.setInstallmanID(25255L);//安装人员
				tagBusinessRecord.setMemo("迁移");
				tagBusinessRecord.setFromID(tagInfo.getId());
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
//				OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
//				OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
//				tagBusinessRecord.setReason(omsParamReason.getParamValue());
//				tagBusinessRecord.setFaultType(omsParamType.getParamValue());
//				tagBusinessRecord.setFaultTypeId(omsParamType.getId());
//				tagBusinessRecord.setReasonId(omsParamReason.getId());
				if(Oldvehicle!=null)tagBusinessRecord.setBussinessid(Oldvehicle.getHisSeqId());//迁移的时候用来记录车辆的Hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//增加车辆业务记录
				vehicleBussiness = new VehicleBussiness();
				vehicleBussiness.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO").toString()));
				vehicleBussiness.setCustomerID(clientID);
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setTagNo(tagInfo.getTagNo());
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
					//原清算数据，没用了
					/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
							null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
					TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
							null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
					saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
					
					//
					//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
					//
					//saveDarkList(tagInfo,darkList,"1", "0");
					blackListService.saveOBURelieveStopUse(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());
				}
				
				
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				serviceWater.setSerType("306");//306电子标签迁移
				
				//serviceWater.setAmt(tagInfo.getCost());//应收金额
				serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness!=null)serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(tagBusinessRecord.getOperID());
				serviceWater.setOperName(tagBusinessRecord.getOperName());
				serviceWater.setOperNo(tagBusinessRecord.getOperNo());
				serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
				serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
				serviceWater.setPlaceNo(tagInfo.getPlaceNo());
				serviceWater.setRemark("自营客服系统：电子标签迁移");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);

				//电子标签迁移回执
				TagMirateReceipt tagMirateReceipt = new TagMirateReceipt();
				tagMirateReceipt.setTitle("电子标签解除挂起回执");
				tagMirateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				tagMirateReceipt.setTagNo(tagInfo.getTagNo());
				//标签为挂起状态，则原车辆的字段显示为空
				if(!TagStateEnum.stop.getValue().equals(tagInfo.getTagState())){
					tagMirateReceipt.setOldVehiclePlate(Oldvehicle.getVehiclePlate());
					tagMirateReceipt.setOldVehiclePlateColor(VehicleColorEnum.getName(Oldvehicle.getVehicleColor()));
					tagMirateReceipt.setOldVehicleWeightLimits(Oldvehicle.getVehicleWeightLimits()+"");
					tagMirateReceipt.setOldVehicleEngineNo(Oldvehicle.getVehicleEngineNo());
					tagMirateReceipt.setOldVehicleModel(Oldvehicle.getModel());
					tagMirateReceipt.setOldVehicleType(VehicleTypeEnum.getIdTypeEnum(Oldvehicle.getVehicleType()).getName());
					tagMirateReceipt.setOldVehicleUserType(VehicleUsingTypeEnum.getName(Oldvehicle.getVehicleUserType()));
					tagMirateReceipt.setOldVehicleUsingNature(UsingNatureEnum.getName(Oldvehicle.getUsingNature()));
					tagMirateReceipt.setOldVehicleOwner(Oldvehicle.getOwner());
					tagMirateReceipt.setOldVehicleLong(Oldvehicle.getVehicleLong()+"");
					tagMirateReceipt.setOldVehicleWidth(Oldvehicle.getVehicleWidth()+"");
					tagMirateReceipt.setOldVehicleHeight(Oldvehicle.getVehicleHeight()+"");
					tagMirateReceipt.setOldVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(Oldvehicle.getNSCVehicleType()));
					tagMirateReceipt.setOldVehicleIdentificationCode(Oldvehicle.getIdentificationCode());
					tagMirateReceipt.setOldVehicleAxles(Oldvehicle.getVehicleAxles()+"");
					tagMirateReceipt.setOldVehicleWheels(Oldvehicle.getVehicleWheels()+"");
				}
				tagMirateReceipt.setVehiclePlate(vehicle.getVehiclePlate());
				tagMirateReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
				tagMirateReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits()+"");
				tagMirateReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
				tagMirateReceipt.setVehicleModel(vehicle.getModel());
				tagMirateReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
				tagMirateReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
				tagMirateReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
				tagMirateReceipt.setVehicleOwner(vehicle.getOwner());
				tagMirateReceipt.setVehicleLong(vehicle.getVehicleLong()+"");
				tagMirateReceipt.setVehicleWidth(vehicle.getVehicleWidth()+"");
				tagMirateReceipt.setVehicleHeight(vehicle.getVehicleHeight()+"");
				tagMirateReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
				tagMirateReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
				tagMirateReceipt.setVehicleAxles(vehicle.getVehicleAxles()+"");
				tagMirateReceipt.setVehicleWheels(vehicle.getVehicleWheels()+"");
				tagMirateReceipt.setInstallMan(tagBusinessRecord.getInstallmanName());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(TagBussinessTypeEnum.tagMirate.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagMirate.getName());
				receipt.setTagNo(tagMirateReceipt.getTagNo());
				receipt.setVehicleColor(vehicle.getVehicleColor());
				receipt.setVehiclePlate(vehicle.getVehiclePlate());
				this.saveReceipt(receipt,tagBusinessRecord,tagMirateReceipt,customer);

				
			}
			map.put("flag", "success");
			
		}catch(ApplicationException e){
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoMigrate电子标签迁移失败"+e.getMessage());
			throw new ApplicationException();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoMigrate电子标签迁移失败"+e.getMessage());
			throw new ApplicationException();
		}
		
		return map;
	}

	@Override
	public Pager findVehicle(Customer customer,Pager pager) {
		try{
			return tagInfoMigrateDao.findVehicleByOrgan(customer, pager);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询TagInfoMigrateService的tagInfoMigrate电子标签迁移失败"+e.getMessage());
		}
		return null;
	}
	
	//清算接口     //原清算数据，没用了
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
	 * @param tagInfo
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

	@Override
	public boolean checkHasMigrateRequestRecord (TagInfo tagInfo){
		int count = tagMigrateRecordDao.queryCountByTagNo(tagInfo.getTagNo(), TagMigrateReqAhtuStateEnum.wateAuth.getValue());
		if(count!=0){
			return true;
		}
		return false;
	}

	@Override
	public Pager tagInfoMigrateAuthList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer, String identificationCode6) {
		return tagMigrateRecordDao.findByPage(pager, tagInfo, vehicleInfo, customer, identificationCode6);
	}

	@Override
	public Map<String, Object> saveTransfer(Customer customer,
			Customer newCustomer, TagInfo tagInfo) {
		tagInfo = tagInfoDao.findByTagNo(tagInfo.getTagNo());
		Map<String,Object> map = new HashMap<String,Object>();
		if(tagInfo==null){
			map.put("result", false);
			map.put("message", "当前电子标签不存在");
			return map;
		}
		if(!TagStateEnum.stop.getValue().equals(tagInfo.getTagState())){
			map.put("result", false);
			map.put("message", "当前电子标签状态不为挂起状态");
			return map;
		}
		try {
			TagInfoHis tagInfoHis = new TagInfoHis();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSTaginfoHis_NO");
			tagInfoHis.setId(seq);
			tagInfoHis.setCreateReason("电子标签过户");
			tagInfo.setHisSeqID(seq);
			tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
			tagInfoDao.updateTransfer(newCustomer.getId(),tagInfo.getTagNo());
			
			map.put("result", true);
			map.put("message", "过户成功");
			return map;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("电子标签过户失败"+tagInfo.getTagNo()+e.getMessage());
			throw new ApplicationException("电子标签过户失败"+tagInfo.getTagNo()+e.getMessage());
		}
	}

	@Override
	public void saveClear(Customer customer, TagInfo tagInfo,VehicleInfo vehicleInfo) {
		try {
			BigDecimal chargeCost =tagInfo.getChargeCost();
			tagInfo = tagInfoMigrateDao.findTagInfoByTagNo(tagInfo.getTagNo());
			
			//绑卡标志
			String buitFlag = tagInfoMigrateDao.findBindByTagNo(tagInfo.getTagNo());
			
			//车辆
			vehicleInfo = vehicleInfoDao.findById(vehicleInfo.getId());
			
			MacaoCardCustomer macaoCardCustomer = macaoCardCustomerDao.findByTagNo(tagInfo.getTagNo());
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());
			
			TagMigrateInfo tagMigrateInfo = new TagMigrateInfo();
			tagMigrateInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSTAGMIGRATEINFO_NO"));
			tagMigrateInfo.setInterCode("91009");
			tagMigrateInfo.setCreateTime(createTime);
			tagMigrateInfo.setTagNo(tagInfo.getTagNo());
			tagMigrateInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			tagMigrateInfo.setVehicleColor(vehicleInfo.getVehicleColor());
			tagMigrateInfo.setModel(vehicleInfo.getModel());
			tagMigrateInfo.setVehicleType(vehicleInfo.getVehicleType());
			tagMigrateInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
			tagMigrateInfo.setVehicleLong(vehicleInfo.getVehicleLong());
			tagMigrateInfo.setVehicleWidth(vehicleInfo.getVehicleWidth());
			tagMigrateInfo.setVehicleHeight(vehicleInfo.getVehicleHeight());
			tagMigrateInfo.setVehicleAxles(vehicleInfo.getVehicleAxles());
			tagMigrateInfo.setVehicleWheels(vehicleInfo.getVehicleWheels());
			tagMigrateInfo.setCustomerName(customer.getOperName());
			tagMigrateInfo.setOwner(vehicleInfo.getOwner());
//			tagMigrateInfo.setZipCode("");
			tagMigrateInfo.setAddress(macaoCardCustomer.getAddress());
			tagMigrateInfo.setCnName(macaoCardCustomer.getCnName());
			tagMigrateInfo.setTel(macaoCardCustomer.getTel());
			tagMigrateInfo.setShortMsg(macaoCardCustomer.getShortMsg());
			tagMigrateInfo.setIdentificationCode(vehicleInfo.getIdentificationCode());
			tagMigrateInfo.setUsingNature(vehicleInfo.getUsingNature());
			tagMigrateInfo.setVehicleSpecificInformation(vehicleInfo.getVehicleSpecificInformation());
			tagMigrateInfo.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
//			tagMigrateInfo.setEndTime("");
//			tagMigrateInfo.setTagChipNo("");
//			tagMigrateInfo.setSystemNo("");
//			tagMigrateInfo.setSerMemo("");
			tagMigrateInfo.setCost(tagInfo.getCost());
			tagMigrateInfo.setChargeCost(chargeCost);
			tagMigrateInfo.setNscVehicletype(vehicleInfo.getNSCVehicleType());
//			tagMigrateInfo.setErrorCode("");
//			tagMigrateInfo.setServiceFlowNo("");
			
			tagMigrateInfo.setIssuceTime(tagInfo.getIssuetime());
			tagMigrateInfo.setIssuceOperName(customer.getOperName());
			tagMigrateInfo.setIssuceOperNo(customer.getOperNo());
			tagMigrateInfo.setIssucePlaceNme(customer.getPlaceName());
			tagMigrateInfo.setIssucePlaceNo(customer.getPlaceNo());
			tagMigrateInfo.setMainTime(tagInfo.getMaintenanceTime());
			
			tagMigrateInfo.setBuitFlag(buitFlag);
			
			
			tagMigrateInfoDao.save(tagMigrateInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Override
	public boolean checkAccountC(String vehiclePlate, String vehicleColor) {
		try {
			Map<String,Object> map = tagInfoMigrateDao.getCarObuCardInfo(vehiclePlate,vehicleColor);
			if(map!=null && map.get("accountCID")!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Descriptioqn:
	 * @param vehicleInfo
	 * @return
	 * @author lgm
	 * @date 2017年2月15日
	 */
	@Override
	public MacaoCardCustomer getMacaoCardCustomer(VehicleInfo vehicleInfo) {
		return macaoCardCustomerDao.findByVehicleInfo(vehicleInfo);
	}

	/**
	 * @Descriptioqn:
	 * @param tagInfoId
	 * @return
	 * @author lgm
	 * @date 2017年2月28日
	 */
	@Override
	public CarObuCardInfo getCarObuCardInfo(Long tagInfoId) {
		return carObuCardInfoDao.findByTagid(tagInfoId);
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年3月10日
	 */
	@Override
	public VehicleInfo getVehicleInfo(Long id) {
		return vehicleInfoDao.findByTagId(id);
	}

	/**
	 * @Descriptioqn:
	 * @param vehiclePlate
	 * @return
	 * @author lgm
	 * @date 2017年3月10日
	 */
	@Override
	public boolean checkVehicle(String vehiclePlate) {
		try {
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.getCarObuCardInfo(vehiclePlate);
			if(carObuCardInfo!=null&&(carObuCardInfo.getAccountCID()!=null||carObuCardInfo.getPrepaidCID()!=null))
				return true;
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}
	/**
	 * @Descriptioqn:
	 * @param vehiclePlate
	 * @return
	 * @author lgm
	 * @date 2017年3月10日
	 */
	@Override
	public boolean checkVehicle(Long id) {
		try {
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.getCarObuCardInfo(id);
			if(carObuCardInfo!=null&&(carObuCardInfo.getAccountCID()!=null||carObuCardInfo.getPrepaidCID()!=null))
				return true;
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年5月2日
	 */
	@Override
	public boolean isStopTag(Long id) {
		TagInfo tagInfo = tagInfoDao.findById(id);
		return "2".equals(tagInfo.getTagState());
	}

	/*
	 * 根据需求需要添加标签业务记录
	 * (non-Javadoc)
	 * @see com.hgsoft.obu.serviceInterface.ITagInfoMigrateService#saveTransfer(com.hgsoft.customer.entity.Customer, com.hgsoft.customer.entity.Customer, com.hgsoft.obu.entity.TagInfo, com.hgsoft.obu.entity.TagBusinessRecord)
	 */
	@Override
	public Map<String, Object> saveTransfer(Customer customer, Customer newCustomer, TagInfo tagInfo,TagBusinessRecord tagBusinessRecord,Map<String,Object> params) {
		tagInfo = tagInfoDao.findByTagNo(tagInfo.getTagNo());
		Map<String,Object> map = new HashMap<String,Object>();
		if(tagInfo==null){
			map.put("result", false);
			map.put("message", "当前电子标签不存在");
			return map;
		}
		if(!TagStateEnum.stop.getValue().equals(tagInfo.getTagState())){
			map.put("result", false);
			map.put("message", "当前电子标签状态不为挂起状态");
			return map;
		}
		try {
			TagInfoHis tagInfoHis = new TagInfoHis();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSTaginfoHis_NO");
			tagInfoHis.setId(seq);
			tagInfoHis.setCreateReason("电子标签过户");
			tagInfo.setHisSeqID(seq);
			tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
			//tagInfoDao.updateTransfer(newCustomer.getId(),tagInfo.getTagNo());
			
			//2017/6/02 修改：需要将原电子标签的修正人信息update
			TagInfo updateOldTagInfo = new TagInfo();
			updateOldTagInfo.setTagState(TagStateEnum.stop.getValue());//停用
			updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
			updateOldTagInfo.setClientID(newCustomer.getId());
			updateOldTagInfo.setHisSeqID(tagInfoHis.getId());
			
			updateOldTagInfo.setId(tagInfo.getId());
			updateOldTagInfo.setCorrectTime(tagBusinessRecord.getOperTime());//更新时间
			updateOldTagInfo.setCorrectOperID(tagBusinessRecord.getOperID());
			updateOldTagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			updateOldTagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			updateOldTagInfo.setCorrectPlaceID(tagBusinessRecord.getOperplaceID());
			updateOldTagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			updateOldTagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			
			tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
			
			
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setObuSerial(tagInfo.getObuSerial());
			tagBusinessRecord.setBusinessType(TagBussinessTypeEnum.tagTransfer.getValue());//过户
			tagBusinessRecord.setCurrentTagState(tagInfo.getTagState());
			tagBusinessRecord.setMemo("过户");
			//tagBusinessRecord.setFromID(customer.getId());
			tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
			/*OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			tagBusinessRecord.setReasonId(omsParamReason.getId());*/
			tagBusinessRecordDao.save(tagBusinessRecord);

			Customer newCustomer_ = this.customerDao.findById(newCustomer.getId());
			if(newCustomer_ == null){ newCustomer_ = new Customer();}
			//电子标签过户回执
			TagTransferReceipt tagTransferReceipt = new TagTransferReceipt();
			tagTransferReceipt.setTitle("电子标签过户回执");
			tagTransferReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			tagTransferReceipt.setTagNo(tagInfo.getTagNo());
			tagTransferReceipt.setNewCustomerNo(newCustomer_.getUserNo());
			tagTransferReceipt.setNewCustomerIdType(IdTypeEnum.getName(newCustomer_.getIdType()));
			tagTransferReceipt.setNewCustomerIdCode(newCustomer_.getIdCode());
			tagTransferReceipt.setNewCustomerName(newCustomer_.getOrgan());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(TagBussinessTypeEnum.tagTransfer.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagTransfer.getName());
			receipt.setTagNo(tagTransferReceipt.getTagNo());
			this.saveReceipt(receipt,tagBusinessRecord,tagTransferReceipt,customer);
			
			map.put("result", true);
			map.put("message", "过户成功");
			return map;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("电子标签过户失败"+tagInfo.getTagNo()+e.getMessage());
			throw new ApplicationException("电子标签过户失败"+tagInfo.getTagNo()+e.getMessage());
		}
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param tagBusinessRecord 电子标签业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, TagBusinessRecord tagBusinessRecord, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.tag.getValue());
		receipt.setCreateTime(tagBusinessRecord.getOperTime());
		receipt.setPlaceId(tagBusinessRecord.getOperplaceID());
		receipt.setPlaceNo(tagBusinessRecord.getPlaceNo());
		receipt.setPlaceName(tagBusinessRecord.getPlaceName());
		receipt.setOperId(tagBusinessRecord.getOperID());
		receipt.setOperNo(tagBusinessRecord.getOperName());
		receipt.setOperName(tagBusinessRecord.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}
}
