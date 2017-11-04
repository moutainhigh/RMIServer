package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoCancelDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagStopDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.serviceInterface.ITagInfoCancelService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagCannelReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class TagInfoCancelService implements ITagInfoCancelService {
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	
	@Resource
	private TagInfoDao infoDao;
	
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private TagStopDao tagStopDao;
	@Resource
	private TagInfoCancelDao tagInfoCancelDao;
	
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private SequenceUtil sequenceUtil;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private ReceiptDao receiptDao;
	
	private static Logger logger = Logger.getLogger(OBUActRecordService.class.getName());


	/**
	 * 注销电子标签
	 */
	@Override
	public void saveCancelTagInfo(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord) {
		try {	
			TagInfo tagInfo = new TagInfo();
			tagInfo = infoDao.findById(tagInfoId);
			
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
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("4");
			
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){		
				// 记录电子标签业务操作记录，业务类型为“注销”，当前状态为”注销“（‘4’）
				/*tagInfoCancelDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
						vehicleID,null,null);*/
				
				//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
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
				tagBusinessRecord.setBussinessid(vehicleInfoDao.findById(vehicleID).getHisSeqId());//注销的时候记录的车辆hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//清算接口    //原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
				
				VehicleInfo vehicle = vehicleInfoDao.findById(vehicleID);
				String cardNo = "";
				String cardType = "";
				//写给铭鸿的清算数据：用户状态信息
				cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
						cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
						tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签注销");
				
				
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//电子标签注销
				//saveDarkList(tagInfo,darkList,"6", "1");
				blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
						, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
						tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
						new Date());
				
				
				Customer customer = customerDao.findById(clientID);
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				serviceWater.setSerType("305");//305电子标签注销
				
				//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
				//serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				serviceWater.setOperId(tagInfo.getOperID());
				serviceWater.setOperName(tagInfo.getOperName());
				serviceWater.setOperNo(tagInfo.getOperNo());
				serviceWater.setPlaceId(tagInfo.getIssueplaceID());
				serviceWater.setPlaceName(tagInfo.getPlaceName());
				serviceWater.setPlaceNo(tagInfo.getPlaceNo());
				serviceWater.setRemark("自营客服系统：电子标签注销");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);
				
			}
			

		} catch (ApplicationException e) {
			logger.error("电子标签注销失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/*
	 * 区分有标签和无标签注销
	 * (non-Javadoc)
	 * @see com.hgsoft.obu.serviceInterface.ITagInfoCancelService#saveCancelTagInfo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, com.hgsoft.obu.entity.TagBusinessRecord, java.lang.String)
	 */
	@Override
	public void saveCancelTagInfo(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag,Map<String,Object> params) {
		try {	
			TagInfo tagInfo = new TagInfo();
			tagInfo = infoDao.findById(tagInfoId);
			
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
				Customer customer = customerDao.findById(clientID);
				// 记录电子标签业务操作记录，业务类型为“注销”，当前状态为”注销“（‘4’）
				/*tagInfoCancelDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
						vehicleID,null,null);*/
				
				//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
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
				//2017-08-18  除了注销的标签，其他状态的都可以注销，所以不一定有车辆id
				if(vehicle != null) tagBusinessRecord.setBussinessid(vehicle.getHisSeqId());//注销的时候记录的车辆hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//清算接口    //原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
				
				
				String cardNo = "";
				String cardType = "";
				//写给铭鸿的清算数据：用户状态信息
				//2017-08-18  除了注销的标签，其他状态的都可以注销，所以不一定有车辆id
				if(vehicle != null){
					cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签注销");
				}
				
				
				//原清算数据，没用了
//				TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
//						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
//				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
//						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
//				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);
				
				//
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//电子标签注销
				//saveDarkList(tagInfo,darkList,"6", "1");
/*				blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
						, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
						tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
						new Date());*/
				
				
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
				
				
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				serviceWater.setSerType("305");//305电子标签注销
				
				//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
				//serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness != null) 
					serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(tagInfo.getOperID());
				serviceWater.setOperName(tagInfo.getOperName());
				serviceWater.setOperNo(tagInfo.getOperNo());
				serviceWater.setPlaceId(tagInfo.getIssueplaceID());
				serviceWater.setPlaceName(tagInfo.getPlaceName());
				serviceWater.setPlaceNo(tagInfo.getPlaceNo());
				serviceWater.setRemark("自营客服系统：电子标签注销");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);
				
				//电子标签注销回执
				TagCannelReceipt tagCannelReceipt = new TagCannelReceipt();
				tagCannelReceipt.setTitle("电子标签注销回执");
				tagCannelReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				tagCannelReceipt.setTagNo(tagInfo.getTagNo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(TagBussinessTypeEnum.tagCannel.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagCannel.getName());
				receipt.setTagNo(tagCannelReceipt.getTagNo());
				this.saveReceipt(receipt,tagBusinessRecord,tagCannelReceipt,customer);
				
				if("0".equals(hasTag)){
					//原清算数据，没用了
					/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
							null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
					TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
							null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
					saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
					
					//
					//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
					//电子标签注销
					//saveDarkList(tagInfo,darkList,"6", "1");
					blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());
				}
				
			}
			
			
		} catch (ApplicationException e) {
			logger.error("电子标签注销失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/*
	 * 区分有标签和无标签注销
	 * (non-Javadoc)
	 * @see com.hgsoft.obu.serviceInterface.ITagInfoCancelService#saveCancelTagInfo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, com.hgsoft.obu.entity.TagBusinessRecord, java.lang.String)
	 */
	@Override
	public void saveCancelTagInfoForAMMS(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag,Map<String,Object> params) {
		try {	
			TagInfo tagInfo = new TagInfo();
			tagInfo = infoDao.findById(tagInfoId);
			
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
				Customer customer = customerDao.findById(clientID);
				// 记录电子标签业务操作记录，业务类型为“注销”，当前状态为”注销“（‘4’）
				/*tagInfoCancelDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
						vehicleID,null,null);*/
				
				//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
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
				//2017-08-18  除了注销的标签，其他状态的都可以注销，所以不一定有车辆id
				if(vehicle != null) tagBusinessRecord.setBussinessid(vehicle.getHisSeqId());//注销的时候记录的车辆hisid
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//清算接口    //原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
				
				
				String cardNo = "";
				String cardType = "";
				//写给铭鸿的清算数据：用户状态信息
				//2017-08-18  除了注销的标签，其他状态的都可以注销，所以不一定有车辆id
				if(vehicle != null){
					cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签注销");
				}
				
				
				//原清算数据，没用了
//				TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
//						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
//				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
//						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
//				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);
				
				//
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//电子标签注销
				//saveDarkList(tagInfo,darkList,"6", "1");
				/*				blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
						, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
						tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
						new Date());*/
				
				
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
				
				
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				serviceWater.setSerType("305");//305电子标签注销
				
				//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
				//serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness != null) 
					serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(tagInfo.getOperID());
				serviceWater.setOperName(tagInfo.getOperName());
				serviceWater.setOperNo(tagInfo.getOperNo());
				serviceWater.setPlaceId(tagInfo.getIssueplaceID());
				serviceWater.setPlaceName(tagInfo.getPlaceName());
				serviceWater.setPlaceNo(tagInfo.getPlaceNo());
				serviceWater.setRemark("代理点系统：电子标签注销");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);
				
				//电子标签注销回执
				TagCannelReceipt tagCannelReceipt = new TagCannelReceipt();
				tagCannelReceipt.setTitle("电子标签注销回执");
				tagCannelReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				tagCannelReceipt.setTagNo(tagInfo.getTagNo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(TagBussinessTypeEnum.tagCannel.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagCannel.getName());
				receipt.setTagNo(tagCannelReceipt.getTagNo());
				this.saveReceipt(receipt,tagBusinessRecord,tagCannelReceipt,customer);
				
				if("0".equals(hasTag)){
					//原清算数据，没用了
					/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
							null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
					TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
							null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
					saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
					
					//
					//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
					//电子标签注销
					//saveDarkList(tagInfo,darkList,"6", "1");
					blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
					, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
					tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
					new Date());
				}
				
			}
			
			
		} catch (ApplicationException e) {
			logger.error("电子标签注销失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}


	@Override
	public Pager tagCancelList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerId) {
		try {
			pager = tagInfoCancelDao.findTagCancels(pager, tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerId);
		} catch (Exception e) {
			logger.error("查询电子标签注销信息失败！"+e.getMessage());
			e.printStackTrace();
		}
		return pager;
	}
	
	@Override
	public Pager tagCancelListForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerId,String bankCode) {
		try {
			pager = tagInfoCancelDao.findTagCancelsForAMMS(pager, tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerId,bankCode);
		} catch (Exception e) {
			logger.error("查询电子标签注销信息失败！"+e.getMessage());
			e.printStackTrace();
		}
		return pager;
	}


	@Override
	public Map<String, Object> tagCancelDetail(Long tagInfoId) {
		Map<String, Object> tagCancel = null;
		try {
			tagCancel = tagInfoCancelDao.findTagCancelById(tagInfoId);
		} catch (Exception e) {
			logger.error("查询单个（电子标签注销）的详细信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return tagCancel;
	}
	
	//清算接口
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
				darkList.setObuSerial(tagInfo.getObuSerial());
				darkList.setCardNo(tagInfo.getTagNo());
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
	 * @Descriptioqn:
	 * @param vehicleId
	 * @return
	 * @author lgm
	 * @date 2017年2月28日
	 */
	@Override
	public CarObuCardInfo getCarObuCardInfo(Long vehicleId) {
		return carObuCardInfoDao.findByVehicleID(vehicleId);
	}


	@Override
	public boolean writeBackImportCard(String tagNo) {
		TagInfo tagInfo = null;
		DarkList darkList = null;
		try {
			tagInfo = infoDao.findByTagNo(tagNo);
			//darkList = darkListDao.findByCardNo(tagNo);
			//saveDarkList(tagInfo,darkList,"6","1");
//			blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
//					, "2", null, null, null,
//					null, null, null, 
//					new Date());
			
			
			//原清算数据，没用了
			/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
					null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
					null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存黑名单数据失败"+tagInfo.getTagNo()+e.getMessage());
			throw new ApplicationException("保存黑名单据失败"+tagInfo.getTagNo());
		}
		return true;
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
