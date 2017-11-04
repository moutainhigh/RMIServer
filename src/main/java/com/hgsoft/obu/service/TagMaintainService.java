package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.text.html.HTML;

import com.hgsoft.common.Enum.*;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagRepairReceipt;
import com.hgsoft.other.vo.receiptContent.tag.TagRepairReturnReceipt;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
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
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagMaintainDao;
import com.hgsoft.obu.dao.TagTakeDetailDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.entity.TagMainRecordHis;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.obu.serviceInterface.ITagMaintainService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;

@Service
public class TagMaintainService implements ITagMaintainService {
	@Resource
	private TagMaintainDao tagMaintainDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	@Resource
	private TagTakeDetailDao tagTakeDetailDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;

	@Resource
	private IInventoryService inventoryService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
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
	private OmsParamDao omsParamDao;
	@Resource
	private ReceiptDao receiptDao;

	private static Logger logger = Logger.getLogger(TagMaintainService.class
			.getName());
	
	/**
	 * 左边导航电子标签维修，电子标签维修查询
	 * @param tagNo
	 * @param vehicleColor
	 * @param vehiclePlate
	 * @param idType
	 * @param idCode
	 * @param endSixNo
	 * @return
	 */
	@Override
	public Pager tagMaintainList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo,Long customerId) {
		try {
			return tagMaintainDao.findTagMaintain(pager, tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo, customerId);
		} catch (Exception e) {
			logger.error("查询电子标签维修列表失败！"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 详情
	 * @param tagInfoId
	 * @param tagState
	 * @return
	 */
	@Override
	public Map<String, Object> findtagDetail(Long customerId, Long vehicleId, Long tagInfoId, Long tagMaintainId) {
		try {
			return tagMaintainDao.findTagById(customerId,vehicleId,tagInfoId,tagMaintainId);
		} catch (Exception e) {
			logger.error("查询电子标签维修详情失败！"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> findRegisterNeededInfo(Long tagInfoId) {
//		try {
//			return tagMaintainDao.findRegisterNeededInfo(tagInfoId);
//		} catch (Exception e) {
//			logger.error("查询维修登记页面信息失败！");
//			e.printStackTrace();
//		}
		return null;
	}
	
	public String save(TagMainRecord tagMainRecord,TagInfo backTagInfo,Long productInfoId,Map<String,Object> params){
		try {

			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			//备件标签不为空，则需调用营运接口判断是否可发行
			if (backTagInfo != null && StringUtil.isNotBlank(backTagInfo.getTagNo())) {
				map = inventoryService.omsInterface(backTagInfo.getTagNo(), "6", interfaceRecord,"issue",
						tagMainRecord.getIssueplaceID(),tagMainRecord.getOperID(),tagMainRecord.getOperName(),"2","customPoint",productInfoId,new BigDecimal("0"),"42","");
				boolean result = (Boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
			}
			
			//客服原发行操作

			TagInfo tagInfo = new TagInfo();
			tagInfo = tagInfoDao.findById(tagMainRecord.getTagInfoID());
			//客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
			serviceFlowRecord.setId(Long.parseLong(seq.toString()));
			serviceFlowRecord.setClientID(tagMainRecord.getClientID());
			serviceFlowRecord.setServiceFlowNO(seq.toString());
			serviceFlowRecord.setCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			//serviceFlowRecord.setAfterState("3");
			serviceFlowRecord.setAfterState(TagStateEnum.repair.getValue());
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(6);//维修
			serviceFlowRecord.setOperID((long)tagMainRecord.getOperID());
			serviceFlowRecord.setPlaceID((long)tagMainRecord.getIssueplaceID());
			//新增字段
			serviceFlowRecord.setOperName(tagMainRecord.getOperName());
			serviceFlowRecord.setOperNo(tagMainRecord.getOperNo());
			serviceFlowRecord.setPlaceName(tagMainRecord.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagMainRecord.getPlaceNo());
			
			
			//保存维修记录
			BigDecimal SEQ_CSMSTagMainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
			BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil.getSequence("SEQ_CSMSTaginfo_NO");
			tagMainRecord.setId(Long.valueOf(SEQ_CSMSTagMainRecord_NO.toString()));
			
			Calendar c = Calendar.getInstance();
			Date date=c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 60);
			tagMainRecord.setMaintainType("3");

			tagMainRecord.setIssuetime(date);//操作时间
			
			//故障原因，故障类型
			OMSParam omsParamReason = omsParamDao.findById(tagMainRecord.getReasonId());
			OMSParam omsParamType = omsParamDao.findById(tagMainRecord.getFaultTypeId());
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setReason(omsParamReason.getParamValue());
			
			
			UnifiedParam unifiedParam = new UnifiedParam();
			if(backTagInfo!=null){
				backTagInfo.setId(Long.valueOf(SEQ_CSMSTaginfo_NO.toString()));
				backTagInfo.setWriteBackFlag("0");
				backTagInfo.setInstallmanName(tagInfo.getInstallmanName());
				//存放obuSerial
				if(map.get("obuSerial")!=null){
					backTagInfo.setObuSerial((String)map.get("obuSerial"));
				}
				/*backTagInfo.setIssuetime(date);
				backTagInfo.setMaintenanceTime(date);
				//备件标签截至时间固定60天
				backTagInfo.setStartTime((Date)map.get("startDate"));
				backTagInfo.setEndTime((Date)map.get("endDate"));*/
			}
			unifiedParam.setTagInfo(backTagInfo);
			unifiedParam.setTagMainRecord(tagMainRecord);
			unifiedParam.setType("7");//7 代表维修
			unifiedParam.setCarObuCardInfo(new CarObuCardInfo());
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){	
				// 记录电子标签业务操作记录
				TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setBusinessType("2");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(tagMainRecord.getClientID());
				tagBusinessRecord.setVehicleID(tagMainRecord.getVehicleID());
				tagBusinessRecord.setTagNo(tagMainRecord.getTagNo());
				//tagBusinessRecord.setCurrentTagState("3");//当前电子标签状态  1：正常	 2：停用	3：维修	4：注销
				tagBusinessRecord.setCurrentTagState(TagStateEnum.repair.getValue());
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setFromID(tagMainRecord.getTagInfoID());
				tagBusinessRecord.setMemo("维修登记");
				tagBusinessRecord.setRealPrice(tagMainRecord.getChargeCost());//--业务费用
				tagBusinessRecord.setOperID((long)tagMainRecord.getOperID());//操作员
				tagBusinessRecord.setOperplaceID(tagMainRecord.getIssueplaceID());//网点
				//新增字段
				tagBusinessRecord.setOperName(tagMainRecord.getOperName());
				tagBusinessRecord.setOperNo(tagMainRecord.getOperNo());
				tagBusinessRecord.setPlaceName(tagMainRecord.getPlaceName());
				tagBusinessRecord.setPlaceNo(tagMainRecord.getPlaceNo());
				tagBusinessRecord.setBussinessid(tagMainRecord.getHisSeqID());
				tagBusinessRecord.setReason(tagMainRecord.getReason());
				tagBusinessRecord.setFaultType(tagMainRecord.getFaultType());
				if(backTagInfo!=null && backTagInfo.getTagNo()!=null && backTagInfo.getTagNo()!=""){
					backTagInfo=tagInfoDao.findByTagNo(backTagInfo.getTagNo());
					tagMainRecord.setBackTagHisId(backTagInfo.getHisSeqID());
					tagMainRecord.setObuSerial(backTagInfo.getObuSerial());
				}
				tagMainRecord.setOldObuSerial(tagInfo.getObuSerial());
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
				tagMaintainDao.save(tagMainRecord);
				//维修登记如果有备件标签增加车辆业务记录
				VehicleBussiness vehicleBussiness = null;
				VehicleInfo vehicle = new VehicleInfo();
				vehicle = vehicleInfoDao.findById(tagMainRecord.getVehicleID());
				if(backTagInfo!=null){
					vehicleBussiness = new VehicleBussiness();
					vehicleBussiness.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO").toString()));
					vehicleBussiness.setCustomerID(tagMainRecord.getClientID());
					vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
					vehicleBussiness.setTagNo(tagInfo.getTagNo());
					vehicleBussiness.setType(VehicleBussinessEnum.tagMaintainRegiste.getValue());
					vehicleBussiness.setOperID((long)tagMainRecord.getOperID());
					vehicleBussiness.setPlaceID((long)tagMainRecord.getIssueplaceID());
					//新增字段
					vehicleBussiness.setOperName(tagMainRecord.getOperName());
					vehicleBussiness.setOperNo(tagMainRecord.getOperNo());
					vehicleBussiness.setPlaceName(tagMainRecord.getPlaceName());
					vehicleBussiness.setPlaceNo(tagMainRecord.getPlaceNo());
					
					vehicleBussiness.setCreateTime(new Date());
					vehicleBussiness.setMemo("电子标签维修-登记维修");
					vehicleBussinessDao.save(vehicleBussiness);
				}
				
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//维修登记不需要下发黑名单2017-05-08 zhangzw
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//
				//saveDarkList(tagInfo,darkList,"1", "0");
				
				
				
				Customer customer = customerDao.findById(tagBusinessRecord.getClientID());
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				if(backTagInfo!=null)serviceWater.setNewCardNo(backTagInfo.getTagNo());
				if(backTagInfo!=null)serviceWater.setObuSerial(backTagInfo.getObuSerial());
				serviceWater.setSerType("310");//310电子标签维修登记
				
				//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
				serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness!=null)serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(tagBusinessRecord.getOperID());
				serviceWater.setOperName(tagBusinessRecord.getOperName());
				serviceWater.setOperNo(tagBusinessRecord.getOperNo());
				serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
				serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
				serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
				serviceWater.setRemark("自营客服系统：电子标签维修登记");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);

				//电子标签维修登记回执
				TagRepairReceipt tagRepairReceipt = new TagRepairReceipt();
				Receipt receipt = new Receipt();
				tagRepairReceipt.setTitle("电子标签维修登记回执");
				tagRepairReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				tagRepairReceipt.setTagNo(tagInfo.getTagNo());
				tagRepairReceipt.setTagIssueTime(DateUtil.formatDate(tagInfo.getIssuetime(),"yyyy-MM-dd HH:mm:ss"));
				if(backTagInfo!=null){
					tagRepairReceipt.setBackTagNo(backTagInfo.getTagNo());
					tagRepairReceipt.setBackTagStartTime(DateUtil.formatDate(backTagInfo.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
					tagRepairReceipt.setBackTagEndTime(DateUtil.formatDate(backTagInfo.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
				}
				tagRepairReceipt.setTagMainReceivePlace(tagMainRecord.getReceivePlace());
				tagRepairReceipt.setTagMainContactMan(tagMainRecord.getContactMan());
				tagRepairReceipt.setTagMainContactPhone(tagMainRecord.getContactPhone());
				tagRepairReceipt.setTagMainInvoiceHead(tagMainRecord.getInvoiceHead());
				tagRepairReceipt.setTagMainAddress(tagMainRecord.getAddress());
				tagRepairReceipt.setTagMainPostcode(tagMainRecord.getPostcode());
				tagRepairReceipt.setTagMainRecoverTime(DateUtil.formatDate(tagMainRecord.getRecoverTime(),"yyyy-MM-dd HH:mm:ss"));
				if(vehicle!=null) {
					tagRepairReceipt.setVehiclePlate(vehicle.getVehiclePlate());
					tagRepairReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
					tagRepairReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits() + "");
					tagRepairReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
					tagRepairReceipt.setVehicleModel(vehicle.getModel());
					tagRepairReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
					tagRepairReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
					tagRepairReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
					tagRepairReceipt.setVehicleOwner(vehicle.getOwner());
					tagRepairReceipt.setVehicleLong(vehicle.getVehicleLong() + "");
					tagRepairReceipt.setVehicleWidth(vehicle.getVehicleWidth() + "");
					tagRepairReceipt.setVehicleHeight(vehicle.getVehicleHeight() + "");
					tagRepairReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
					tagRepairReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
					tagRepairReceipt.setVehicleAxles(vehicle.getVehicleAxles() + "");
					tagRepairReceipt.setVehicleWheels(vehicle.getVehicleWheels() + "");

					receipt.setVehicleColor(vehicle.getVehicleColor());
					receipt.setVehiclePlate(vehicle.getVehiclePlate());
				}
				tagRepairReceipt.setInstallMan(tagInfo.getInstallmanName());
				receipt.setTypeCode(TagBussinessTypeEnum.tagRepair.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagRepair.getName());
				receipt.setTagNo(tagRepairReceipt.getTagNo());
				this.saveReceipt(receipt,tagBusinessRecord,tagRepairReceipt,customer);


				
				
				if (backTagInfo != null && StringUtil.isNotBlank(backTagInfo.getTagNo()) && map != null) {
					//发行成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}
				return "true";
				
			}
			
		} catch (ApplicationException e) {
			logger.error("电子标签维修信息保存失败！"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return "电子标签维修信息保存失败！";
	}
	/**
	 *  电子标签维修--》修改--》保存
	 * @param tagMainRecord
	 */
	@Override
	public void updateMaintainInfo(TagMainRecord tagMainRecord) {
		try {
			BigDecimal SEQ_CSMSTagMaintainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecordHis_NO");
			Long hisId = Long.valueOf(SEQ_CSMSTagMaintainRecord_NO.toString());
			//将电子标签维修记录移至历史表，生成原因为“修改维修记录“； 
			tagMaintainDao.saveMaintainHis(tagMainRecord.getId(),hisId);
			//更新电子标签维修记录。
			tagMainRecord.setHisSeqID(hisId);
			
			//故障原因，故障类型
			OMSParam omsParamReason = omsParamDao.findById(tagMainRecord.getReasonId());
			OMSParam omsParamType = omsParamDao.findById(tagMainRecord.getFaultTypeId());
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setReason(omsParamReason.getParamValue());
			
			tagMaintainDao.updateMaintainRecord(tagMainRecord);
		} catch (ApplicationException e) {
			logger.error("电子标签维修信息修改失败！"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	@Override
	public String editReturnCustomer(TagMainRecord temp,Map<String,Object> params) {
		try {
			TagInfo tagInfo = new TagInfo();
			tagInfo = tagInfoDao.findById(temp.getTagInfoID());
			
			InterfaceRecord interfaceRecordRefund = null;
			Map<String, Object> mapRefund = null;
			boolean result = false;
			//判断库存是否可冲正备件电子标签
			if(temp.getBackupTagNo()!=null){
				mapRefund = inventoryService.omsInterface(temp.getBackupTagNo(), "8", interfaceRecordRefund,"",
						temp.getIssueplaceID(),temp.getBackOperID(),temp.getOperName(),"2","",null,new BigDecimal("0"),"10","");
				result = (Boolean) mapRefund.get("result");
				if (!result) {
					return mapRefund.get("message").toString();
				}
			}
			
			
			//------原业务流程-------
			
			//客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
			serviceFlowRecord.setId(Long.parseLong(seq.toString()));
			serviceFlowRecord.setClientID(temp.getClientID());
			serviceFlowRecord.setServiceFlowNO(seq.toString());
			serviceFlowRecord.setCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState("1");
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(6);//维修
			serviceFlowRecord.setOperID((long)temp.getOperID());
			serviceFlowRecord.setPlaceID((long)temp.getIssueplaceID());
			//新增字段
			serviceFlowRecord.setOperName(temp.getOperName());
			serviceFlowRecord.setOperNo(temp.getOperNo());
			serviceFlowRecord.setPlaceName(temp.getPlaceName());
			serviceFlowRecord.setPlaceNo(temp.getPlaceNo());
			
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
			
			//--------------调用接口更新----------------
			UnifiedParam unifiedParam = new UnifiedParam();
			TagInfo tag = tagInfoDao.findById(temp.getTagInfoID());
			unifiedParam.setTagInfo(tag);
			unifiedParam.setTagMainRecord(temp);
			unifiedParam.setType("9");//9代表维修返回客户
			unifiedParam.setCarObuCardInfo(new CarObuCardInfo());
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){	
				//将电子标签维修记录移至历史表，生成原因为“维修返回客户“；
				TagMainRecordHis tmr = new TagMainRecordHis();
				BigDecimal seq3 = sequenceUtil.getSequence("SEQ_CSMSTagMainRecordHis_NO");
				tmr.setId(Long.parseLong(seq3.toString()));
				tmr.setCreateReason("维修返回客户");
				tagMaintainDao.saveTagMainRecordHis(tmr,temp.getId());
				
				//更新电子标签维修记录

				temp.setHisSeqID(tmr.getId());
				tagMaintainDao.updateMaintainBackToCustomerTime(temp);
				
				//记录电子标签业务操作记录，业务类型为“维修”，当前状态为”正常(1)“
//				tagMaintainDao.saveTagBusinessRecord(temp.getTagInfoID(), temp.getTagNo(), temp.getClientID(),
//						temp.getVehicleID(),null,null,1);
				
				//记录电子标签业务操作记录，业务类型为“维修”，当前状态为”正常(1)“
				TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setBusinessType("10");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(temp.getClientID());
				tagBusinessRecord.setVehicleID(temp.getVehicleID());
				tagBusinessRecord.setTagNo(temp.getTagNo());
				tagBusinessRecord.setCurrentTagState("1");//当前电子标签状态  1：正常	 2：停用	3：维修	4：注销
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setMemo("维修返回客户");
				tagBusinessRecord.setFromID(temp.getTagInfoID());
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用0
				tagBusinessRecord.setOperID((long)temp.getOperID());//操作员
				tagBusinessRecord.setOperplaceID(temp.getIssueplaceID());//网点
				//新增字段
				tagBusinessRecord.setOperName(temp.getOperName());
				tagBusinessRecord.setOperNo(temp.getOperNo());
				tagBusinessRecord.setPlaceName(temp.getPlaceName());
				tagBusinessRecord.setPlaceNo(temp.getPlaceNo());
				tagBusinessRecord.setBussinessid(temp.getHisSeqID());
				tagBusinessRecordDao.save(tagBusinessRecord);
				
				//增加车辆业务记录
				VehicleBussiness vehicleBussiness = null;
				VehicleInfo vehicle = new VehicleInfo();
				vehicle = vehicleInfoDao.findById(temp.getVehicleID());
				if(StringUtil.isNotBlank(temp.getBackupTagNo())){//解绑备用标签时
					vehicleBussiness = new VehicleBussiness();
					vehicleBussiness.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO").toString()));
					vehicleBussiness.setCustomerID(temp.getClientID());
					vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
					vehicleBussiness.setTagNo(temp.getBackupTagNo());
					vehicleBussiness.setType(VehicleBussinessEnum.tagMaintainReturnCustomer.getValue());
					vehicleBussiness.setOperID((long)temp.getOperID());
					vehicleBussiness.setPlaceID((long)temp.getIssueplaceID());
					//新增字段
					vehicleBussiness.setOperName(temp.getOperName());
					vehicleBussiness.setOperNo(temp.getOperNo());
					vehicleBussiness.setPlaceName(temp.getPlaceName());
					vehicleBussiness.setPlaceNo(temp.getPlaceNo());
					
					vehicleBussiness.setCreateTime(new Date());
					vehicleBussiness.setMemo("电子标签维修-维修返回客户-解绑备用标签");
					vehicleBussinessDao.save(vehicleBussiness);
				}
				
				
				Customer customer = customerDao.findById(tagBusinessRecord.getClientID());
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				serviceWater.setSerType("311");//311电子标签维修返回客户
				
				//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
				serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
				//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
				serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
				if(vehicleBussiness!=null)serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(tagBusinessRecord.getOperID());
				serviceWater.setOperName(tagBusinessRecord.getOperName());
				serviceWater.setOperNo(tagBusinessRecord.getOperNo());
				serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
				serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
				serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
				serviceWater.setRemark("自营客服系统：电子标签维修返回客户");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);


				TagInfo backupTag = this.tagInfoDao.findByTagNo(temp.getBackupTagNo());
				//电子标签维修返回回执
				TagRepairReturnReceipt tagRepairReturnReceipt = new TagRepairReturnReceipt();
				Receipt receipt = new Receipt();
				tagRepairReturnReceipt.setTitle("电子标签维修返回回执");
				tagRepairReturnReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				tagRepairReturnReceipt.setTagNo(tagInfo.getTagNo());
				tagRepairReturnReceipt.setTagIssueTime(DateUtil.formatDate(tagInfo.getIssuetime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setBackUpTagNo(temp.getBackupTagNo());
				if(backupTag!=null){
					tagRepairReturnReceipt.setBackTagStartTime(DateUtil.formatDate(backupTag.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
					tagRepairReturnReceipt.setBackTagEndTime(DateUtil.formatDate(backupTag.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
				}
				tagRepairReturnReceipt.setTagMainReceivePlace(temp.getReceivePlace());
				tagRepairReturnReceipt.setTagMainContactMan(temp.getContactMan());
				tagRepairReturnReceipt.setTagMainContactPhone(temp.getContactPhone());
				tagRepairReturnReceipt.setTagMainInvoiceHead(temp.getInvoiceHead());
				tagRepairReturnReceipt.setTagMainAddress(temp.getAddress());
				tagRepairReturnReceipt.setTagMainPostcode(temp.getPostcode());
				tagRepairReturnReceipt.setTagMainRecoverTime(DateUtil.formatDate(temp.getRecoverTime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setTagMainSendRepairTime(DateUtil.formatDate(temp.getSendRepairTime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setTagMainRepairBackTime(DateUtil.formatDate(temp.getRepairBackTime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setTagMainNoticeCustomerTime(DateUtil.formatDate(temp.getNoticeCustomerTime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setTagMainBackToCustomerTime(DateUtil.formatDate(temp.getBackToCustomerTime(),"yyyy-MM-dd HH:mm:ss"));
				tagRepairReturnReceipt.setTagMainMemo(temp.getMemo());
				if(vehicle!=null) {
					tagRepairReturnReceipt.setVehiclePlate(vehicle.getVehiclePlate());
					tagRepairReturnReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
					tagRepairReturnReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits() + "");
					tagRepairReturnReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
					tagRepairReturnReceipt.setVehicleModel(vehicle.getModel());
					tagRepairReturnReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
					tagRepairReturnReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
					tagRepairReturnReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
					tagRepairReturnReceipt.setVehicleOwner(vehicle.getOwner());
					tagRepairReturnReceipt.setVehicleLong(vehicle.getVehicleLong() + "");
					tagRepairReturnReceipt.setVehicleWidth(vehicle.getVehicleWidth() + "");
					tagRepairReturnReceipt.setVehicleHeight(vehicle.getVehicleHeight() + "");
					tagRepairReturnReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
					tagRepairReturnReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
					tagRepairReturnReceipt.setVehicleAxles(vehicle.getVehicleAxles() + "");
					tagRepairReturnReceipt.setVehicleWheels(vehicle.getVehicleWheels() + "");

					receipt.setVehicleColor(vehicle.getVehicleColor());
					receipt.setVehiclePlate(vehicle.getVehiclePlate());
				}
				tagRepairReturnReceipt.setInstallMan(tagInfo.getInstallmanName());

				receipt.setTypeCode(TagBussinessTypeEnum.tagRepairReturn.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagRepairReturn.getName());
				receipt.setTagNo(tagRepairReturnReceipt.getTagNo());
				this.saveReceipt(receipt,tagBusinessRecord,tagRepairReturnReceipt,customer);
			}

			//冲正成功后，更新营运接口调用登记记录的客服状态
			if(mapRefund!=null){
				interfaceRecordRefund = (InterfaceRecord) mapRefund.get("interfaceRecord");
				if (interfaceRecordRefund != null&&interfaceRecordRefund.getCardno()!=null) {
					interfaceRecordRefund.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordRefund);
					
					return "true";
				}
			}else{
				return "true";
			}
				
			return "维修返回客户失败";
		} catch (ApplicationException e) {
			logger.error("电子标签维修返回客户失败！"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	@Override
	public Map validateBackTagNo(String backupTagNo) {
		Map<String,Object> m =new HashMap<String,Object>();
		TagInfo tagInfo=new TagInfo();
		//做库存验证
		TagTakeDetail tagTakeDetail = tagTakeDetailDao.findByTagNo(backupTagNo);
		if (tagTakeDetail != null) {//判断是否已经提货
				m.put("success", false);
				m.put("message", "该电子标签已提货");
				return m;
		}
		tagInfo = tagInfoDao.findByTagNo(backupTagNo);
		if (tagInfo != null) {
			// 该电子标签已发行
			m.put("success", false);
			m.put("message", "该电子标签已发行");
			return m;
		}
		tagInfo=new TagInfo();
		tagInfo.setTagNo(backupTagNo);
		Calendar c = Calendar.getInstance();
		Date date=c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 60);
		tagInfo.setIssuetime(date);
		tagInfo.setMaintenanceTime(date);
		tagInfo.setStartTime(date);
		tagInfo.setEndTime(c.getTime());
		m.put("success", true);
		m.put("tagInfo", tagInfo);
		return m;
	}
	@Override
	public TagMainRecord findById(Long id) {
		return tagMaintainDao.findById(id);
	}
	//清算接口      //原清算数据，没用了
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

