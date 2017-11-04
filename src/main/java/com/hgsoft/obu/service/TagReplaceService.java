package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.TagIssueTypeEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UsingNatureEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.common.Enum.VehicleUsingTypeEnum;
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
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.macao.dao.TagReplaceInfoDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.TagReplaceInfo;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagReplaceDao;
import com.hgsoft.obu.dao.TagReplaceFlowDao;
import com.hgsoft.obu.dao.TagTakeDetailDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.entity.TagReplaceFlow;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.obu.serviceInterface.ITagReplaceService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagChangeReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class TagReplaceService implements ITagReplaceService {
	@Resource
	private SequenceUtil sequenceUtil;

	@Resource
	private MainAccountInfoDao mainAccountInfoDao;

	@Resource
	private TagReplaceDao tagReplaceDao;

	@Resource
	private TagInfoDao tagInfoDao;

	@Resource
	private TagTakeDetailDao tagTakeDetailDao;

	@Resource
	private TagInfoHisDao tagInfoHisDao;

	@Resource
	private IUnifiedInterface unifiedInterfaceService;

	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;

	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;

	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/ 
	
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	
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
	private IInventoryServiceForAgent inventoryServiceForAgent;
	
	@Resource
	private TagReplaceInfoDao tagReplaceInfoDao;
	
	@Resource
	private TagReplaceFlowDao tagReplaceFlowDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	
	@Resource
	private OmsParamDao omsParamDao;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private ReceiptDao receiptDao;

	private static Logger logger = Logger.getLogger(TagReplaceService.class.getName());

	@Override
	public List tagReplaceList(String tagNo, String vehicleColor, String vehiclePlate, String idType, String idCode,
			String endSixNo, Long customerID) {
		List list = null;
		try {
			list = tagReplaceDao.findTagReplaces(tagNo, vehicleColor, vehiclePlate, idType, idCode, endSixNo,
					customerID);
		} catch (Exception e) {
			logger.error("查询电子标签更换信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Pager tagReplaceListByPager(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerID) {
		return tagReplaceDao.findTagReplacesByPager(pager, tagNo, vehicleColor, vehiclePlate, idType, idCode,
				endSixNo, customerID);
	}
	
	@Override
	public Pager tagReplaceListByPagerForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerID,String bankCode) {
		return tagReplaceDao.findTagReplacesByPagerForAMMS(pager, tagNo, vehicleColor, vehiclePlate, idType, idCode,
				endSixNo, customerID,bankCode);
	}
	
	/**
	 * 澳门通用
	 */
	@Override
	public Pager tagReplaceListByPager(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerID,MacaoCardCustomer macaoCardCustomer) {
		try {
			pager = tagReplaceDao.findTagReplacesByPager(pager, tagNo, vehicleColor, vehiclePlate, idType, idCode,
					endSixNo, customerID,macaoCardCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签更换信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}

	public Pager tagReplaceListForLian(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer sessionCustomer,
			Customer listCustomer) {
		try {
			pager = tagReplaceDao.findForLian(pager, tagInfo, vehicleInfo, sessionCustomer, listCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签更换信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}


	@Override
	public Map<String, Object> tagReplaceDetail(Long tagInfoId) {

		Map<String, Object> tagReplace = null;
		try {
			tagReplace = tagReplaceDao.findTagReplaceById(tagInfoId);
		} catch (Exception e) {
			logger.error("查询单个（电子标签更换）的详细信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return tagReplace;
	}

	/**
	 * 更换电子标签
	 */
	@Override
	public Map<String,Object> saveReplaceTagInfo(BigDecimal chargeFee, String newTagNo,Long tagInfoId, Long clientID, Long vehicleID,String faultReason, Long installmanID, String memo, Long operID,
												 Long operplaceID,TagBusinessRecord tagBusinessRecord,Long productInfoId,BigDecimal realCost,String installmanName,String productTypeCode,String faultType,Map<String,Object> params) {
		
		Map<String,Object> map=new HashMap<String,Object>();
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
			
			if(newTagInfo==null){
				map.put("success", false);
				map.put("message", "旧电子标签号未发行");
				return map;
			}

			InterfaceRecord interfaceRecordReclaim = null;
			Map<String, Object> mapReclaim = null;
			boolean result = false;
			
			//boolean updateOldTagTakeDetail = false;//标记旧标签是否为提货标签，需要update为未发行
			boolean updateNewTagTakeDetail = false;//标记新标签是否为提货标签，需要update为已发行
			//String oldTagNo = newTagInfo.getTagNo();
			//判断旧标签是否在提货明细表有
			TagTakeDetail oldTagTakeDetail = tagTakeDetailDao.findByTagNo(newTagInfo.getTagNo());//这个时候的newTagInfo信息还是旧标签信息
			if(oldTagTakeDetail!=null){
				if("0".equals(oldTagTakeDetail.getTagState())){
					map.put("success", false);
					map.put("message", "旧标签未发行");
					return map;
				}
			}else{
				//判断库存是否可回收电子标签。电子标签有偿更换，不需要回收旧标签
				if(chargeFee.compareTo(new BigDecimal("0"))==0){
					
					//2017/05/05 营运的回收接口增加了一个参数 newCardSourceType（产品类型）
					String newCardSourceType = "";
					Map<String, Object> newCardMap = inventoryService.getProductTypeByCode("2", newTagNo);//2是其他
					if("0".equals((String)newCardMap.get("flag"))){
						newCardSourceType = (String)newCardMap.get("sourceType");
					}else{
						map.put("success", false);
						map.put("message", (String)newCardMap.get("message"));
						return map;
					}
					
					//因为可以地标换国标，所以这里不能16位
					//if (newTagInfo.getTagNo().length()==16) {
						//此时获得的newTagInfo.getTagNo()是旧电子标签
						mapReclaim = inventoryService.omsInterface(newTagInfo.getTagNo(), "2", interfaceRecordReclaim,"",
								operplaceID,tagBusinessRecord.getOperID(),tagBusinessRecord.getOperName(),"2","",null,new BigDecimal("0"),"43",newCardSourceType);
						result = (Boolean) mapReclaim.get("result");
						if (!result) {
							map.put("success", false);
							map.put("message", mapReclaim.get("message").toString());
							return map;
						}
					//}
				}
			}
			
			
			
			InterfaceRecord interfaceRecordIssue = null;
			Map<String, Object> mapIssue = null;
			
			//判断新标签是否在提货明细表有
			TagTakeDetail newTagTakeDetail = tagTakeDetailDao.findByTagNo(newTagNo);
			if(newTagTakeDetail!=null){
				if("1".equals(newTagTakeDetail.getTagState())){
					map.put("success", false);
					map.put("message", "新电子标签已提货发行");
					return map;
				}else{
					//先提货后发型的，发行后，要更新提货明细表的状态为已发行
					//tagTakeDetailDao.updateTagStateByTagNo("1", newTagNo);
					updateNewTagTakeDetail = true;
					newTagInfo.setIssueType("2");//先提货后发行
				}
			}else{
				//判断库存是否可发行电子标签
				//有偿更换为发行出库，无偿更换为更换出库。默认为发行出库，更换出库传"replace"
				String outType="";
				if(chargeFee.compareTo(new BigDecimal("0"))==0){
					//无偿更换、更换出库
					outType="replace";
					newTagInfo.setIssueType("4");//更换发行
				}else{
					outType = "issue";
					newTagInfo.setIssueType("1");//提货发行一次完成
				}
				mapIssue = inventoryService.omsInterface(newTagNo, "1", interfaceRecordIssue, outType,
						operplaceID,tagBusinessRecord.getOperID(),tagBusinessRecord.getOperName(),"2","customPoint",productInfoId,newTagInfo.getChargeCost(),"43","");
				result = (Boolean) mapIssue.get("result");
				if (!result) {
					map.put("success", false);
					map.put("message", mapIssue.get("message").toString());
					return map;
				}
			}
			
			if(updateNewTagTakeDetail){
				tagTakeDetailDao.updateTagStateByTagNo("1", newTagNo);
			}
			
			
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
			if(newTagInfo.getIssuetime()!=null)oldTagInfo.setIssuetime(newTagInfo.getIssuetime());
			if(newTagInfo.getStartTime()!=null)oldTagInfo.setStartTime(newTagInfo.getStartTime());
			if(newTagInfo.getEndTime()!=null)oldTagInfo.setEndTime(newTagInfo.getEndTime());
			unifiedParam.setTagInfo(oldTagInfo);//旧电子标签

			newTagInfo.setChargeCost(chargeFee);
			newTagInfo.setTagNo(newTagNo);
			newTagInfo.setId(newTagInfoId);
			//newTagInfo.setTagState("1");// 新标签状态为“正常”
			newTagInfo.setTagState(TagStateEnum.normal.getValue());
			//newTagInfo.setCorrectTime(new Date());//更新时间
			//newTagInfo.setCorrectOperID(operID);
			//newTagInfo.setCorrectPlaceID(operplaceID);
			newTagInfo.setCost(realCost);
			newTagInfo.setInstallmanName(installmanName);
			newTagInfo.setProductType(productTypeCode);
			
			//
//			newTagInfo.setOperName(tagBusinessRecord.getOperName());
//			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
//			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
//			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			
			newTagInfo.setOperID(tagBusinessRecord.getOperID());
			newTagInfo.setOperName(tagBusinessRecord.getOperName());
			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			newTagInfo.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			newTagInfo.setCorrectTime(new Date());//更新时间
			newTagInfo.setCorrectOperID(newTagInfo.getOperID());
			newTagInfo.setCorrectOperName(newTagInfo.getOperName());
			newTagInfo.setCorrectOperNo(newTagInfo.getOperNo());
			newTagInfo.setCorrectPlaceID(newTagInfo.getIssueplaceID());
			newTagInfo.setCorrectPlaceName(newTagInfo.getPlaceName());
			newTagInfo.setCorrectPlaceNo(newTagInfo.getPlaceNo());
			
			
			/*相关时间设置：
			有效启用时间、有效截止时间应该是取营运系统一发时设置的时间
			发行时间应该是办理业务的当前时间
			维保开始时间：如果有偿（成本费不为0）：则使用本次发行时间；如果是无偿（成本费为0），则使用上一张卡的维保开始时间。*/
			if(newTagTakeDetail!=null){
				//若新标签已提货，则从提货明细表获取信息
				//设置obuSerial
				newTagInfo.setObuSerial(newTagTakeDetail.getObuSerial());
				newTagInfo.setStartTime(newTagTakeDetail.getStartTime());
				newTagInfo.setEndTime(newTagTakeDetail.getEndTime());
			}else{
				//否则用调用营运初始化接口获得的信息
				//设置obuSerial
				if(mapIssue.get("obuSerial")!=null){
					newTagInfo.setObuSerial((String)mapIssue.get("obuSerial"));
				}
				newTagInfo.setStartTime((Date)mapIssue.get("startDate"));
				newTagInfo.setEndTime((Date)mapIssue.get("endDate"));
			}
			
			//设置维保时间
			Calendar cal = Calendar.getInstance();;
			if(chargeFee.compareTo(new BigDecimal("0"))==0){
				//无偿，沿用原卡的发行时间作为维保起始时间
				cal.setTime(oldTagInfo.getIssuetime());//此时oldTagInfo的发行时间就是旧的标签的发行时间
			}else{
				
			}
			
			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info(paramMap);
			if(paramMap!=null && "成功".equals((String)paramMap.get("message"))){
				cal.add(Calendar.YEAR, Integer.parseInt((String)paramMap.get("value")));
			}else if(paramMap!=null && !"成功".equals((String)paramMap.get("message"))){
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败:"+(String)paramMap.get("message"));
				return map;
			}else{
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败");
				return map;
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}
			
			newTagInfo.setMaintenanceTime(cal.getTime());
			newTagInfo.setIssuetime(new Date());
			
			
			
			// 更换的收费chargeFee有两种情况,等于0或者大于0
			if (chargeFee.compareTo(new BigDecimal("0"))==0) {
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
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
				AccountFundChange accountFundChange=new AccountFundChange();
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
				accountFundChangeDao.saveChange(accountFundChange);
				
			} else {
				// 如果收费不等于0，要调用更新账户余额的接口
				// 更新账户可用余额=当前账户可用余额-收费金额
				// mainAccountInfo = mainAccountInfoDao.findByMainId(clientID);
				unifiedParam.setNewTagInfo(newTagInfo);// 新电子标签
				unifiedParam.setType("7");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setPlaceId(operplaceID);
				unifiedParam.setOperId(tagBusinessRecord.getOperID());
				unifiedParam.setOperName(tagBusinessRecord.getOperName());
				unifiedParam.setOperNo(tagBusinessRecord.getOperNo());
				unifiedParam.setPlaceName(tagBusinessRecord.getPlaceName());
				unifiedParam.setPlaceNo(tagBusinessRecord.getPlaceNo());
				if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					map.put("success", false);
					map.put("message", "电子标签更换失败，余额不足");
					return map;
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord, "0");
				unifiedParam.setType("2");
				CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(tagInfoId, vehicleID);
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				if (!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
					throw new ApplicationException("电子标签更换失败");
				}
			}
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(newTagInfo.getClientID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setTagNo(newTagInfo.getTagNo());
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
			//String[] reason = replaceReason.split("-");
			OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setReason(omsParamReason.getParamValue());
			tagMainRecord.setFaultTypeId(Long.parseLong(faultType));
			tagMainRecord.setReasonId(Long.parseLong(faultReason));
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
			//tagBusinessRecord.setReason(tagMainRecord.getReason());
			//tagBusinessRecord.setFaultType(tagMainRecord.getFaultType());
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			tagBusinessRecord.setReasonId(omsParamReason.getId());
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
			
			//清算接口    //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, newTagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			//旧标签注销
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					oldTagInfo.getTagNo(),oldTagInfo.getObuSerial(), oldTagInfo.getStartTime(), oldTagInfo.getEndTime(), "标签更换业务的旧标签注销");
			
			//写给铭鸿的清算数据：用户状态信息
			//新标签发行
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					newTagInfo.getTagNo(),newTagInfo.getObuSerial(), newTagInfo.getStartTime(), newTagInfo.getEndTime(), "标签更换业务的新标签发行");
			
			
			
			//原清算数据，没用了
			/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			saveTollCardBlack(oldTagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
			
			//
			//DarkList darkList = darkListDao.findByCardNo(oldTagInfo.getTagNo());
			//
			oldTagInfo.setOperID(operID);
			oldTagInfo.setIssueplaceID(operplaceID);
			//新增字段
			oldTagInfo.setOperName(tagBusinessRecord.getOperName());
			oldTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			oldTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			oldTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			oldTagInfo.setClientID(clientID);
			//saveDarkList(oldTagInfo,darkList,"6", "1");
			//保存电子标签黑名单流水
			blackListService.saveOBUCancel(oldTagInfo.getObuSerial(), new Date()
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
			serviceWater.setCardNo(oldTagInfo.getTagNo());
			serviceWater.setNewCardNo(newTagNo);
			serviceWater.setObuSerial(newTagInfo.getObuSerial());
			serviceWater.setSerType("303");//303电子标签更换
			
			serviceWater.setAmt(newTagInfo.getCost());//应收金额
			serviceWater.setAulAmt(newTagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(newTagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagBusinessRecord.getOperID());
			serviceWater.setOperName(tagBusinessRecord.getOperName());
			serviceWater.setOperNo(tagBusinessRecord.getOperNo());
			serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
			serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
			serviceWater.setRemark("自营客服系统：电子标签更换");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//电子标签更换回执
			TagChangeReceipt tagChangeReceipt = new TagChangeReceipt();
			tagChangeReceipt.setTitle("电子标签更换回执");
			tagChangeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			tagChangeReceipt.setOldTagNo(oldTagInfo.getTagNo());
			tagChangeReceipt.setTagNo(newTagInfo.getTagNo());
			tagChangeReceipt.setTagChargeCost(NumberUtil.get2Decimal(newTagInfo.getCost().doubleValue()*0.01));
			tagChangeReceipt.setVehiclePlate(vehicle.getVehiclePlate());
			tagChangeReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
			tagChangeReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits()+"");
			tagChangeReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
			tagChangeReceipt.setVehicleModel(vehicle.getModel());
			tagChangeReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
			tagChangeReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
			tagChangeReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
			tagChangeReceipt.setVehicleOwner(vehicle.getOwner());
			tagChangeReceipt.setVehicleLong(vehicle.getVehicleLong()+"");
			tagChangeReceipt.setVehicleWidth(vehicle.getVehicleWidth()+"");
			tagChangeReceipt.setVehicleHeight(vehicle.getVehicleHeight()+"");
			tagChangeReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
			tagChangeReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
			tagChangeReceipt.setVehicleAxles(vehicle.getVehicleAxles()+"");
			tagChangeReceipt.setVehicleWheels(vehicle.getVehicleWheels()+"");
			tagChangeReceipt.setInstallMan(installmanName);
			Receipt receipt = new Receipt();
			receipt.setTypeCode(TagBussinessTypeEnum.tagChange.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagChange.getName());
			receipt.setTagNo(tagChangeReceipt.getTagNo());
			receipt.setVehicleColor(vehicle.getVehicleColor());
			receipt.setVehiclePlate(vehicle.getVehiclePlate());
			this.saveReceipt(receipt,tagBusinessRecord,tagChangeReceipt,customer);


			//电子标签有偿更换，不需要回收旧标签
			//if (oldTagInfo.getTagNo().length()==16) {
				//回收成功后，更新营运接口调用登记记录的客服状态
				//电子标签有偿更换，不需要回收旧标签。mapReclaim空时即没有调用营运回收旧标签
				if(mapReclaim!=null){
					interfaceRecordReclaim = (InterfaceRecord) mapReclaim.get("interfaceRecord");
					if (interfaceRecordReclaim != null&&interfaceRecordReclaim.getCardno()!=null) {
						interfaceRecordReclaim.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecordReclaim);
					}
				}
				
			//}
			
			/*//电子标签更换成功后，若tagTakeDetail不为null
			if(tagTakeDetail!=null){
				//tagTakeDetail.setTagState("1");
				tagTakeDetailDao.updateTagStateByTagNo("1", tagTakeDetail.getTagNo());
			}*/
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			if(mapIssue!=null){
				interfaceRecordIssue = (InterfaceRecord) mapIssue.get("interfaceRecord");
				if (interfaceRecordIssue != null&&interfaceRecordIssue.getCardno()!=null) {
					interfaceRecordIssue.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordIssue);
				}
			}
			
			map.put("success", true);
			map.put("message", "电子标签更换成功");
			
		} catch (ApplicationException e) {
			logger.error("电子标签更换失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return map;
	}

	/**
	 * 更换电子标签
	 */
	@Override
	public Map<String,Object> saveReplaceTagInfo_Lian(BigDecimal chargeFee, String newTagNo,
			Long tagInfoId, Long clientID, Long vehicleID,
			String faultReason, Long installmanID, String memo, Long operID, 
			Long operplaceID,TagBusinessRecord tagBusinessRecord,Long productInfoId,
			BigDecimal realCost,String installmanName,String productTypeCode,String faultType) {
		
		Map<String,Object> map=new HashMap<String,Object>();
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
			
			if(newTagInfo==null){
				map.put("success", false);
				map.put("message", "旧电子标签号未发行");
				return map;
			}

			InterfaceRecord interfaceRecordReclaim = null;
			Map<String, Object> mapReclaim = null;
			boolean result = false;
			
			//boolean updateOldTagTakeDetail = false;//标记旧标签是否为提货标签，需要update为未发行
			boolean updateNewTagTakeDetail = false;//标记新标签是否为提货标签，需要update为已发行
			//String oldTagNo = newTagInfo.getTagNo();
			//判断旧标签是否在提货明细表有
//			TagTakeDetail oldTagTakeDetail = tagTakeDetailDao.findByTagNo(newTagInfo.getTagNo());//这个时候的newTagInfo信息还是旧标签信息
//			if(oldTagTakeDetail!=null){
//				if("0".equals(oldTagTakeDetail.getTagState())){
//					map.put("success", false);
//					map.put("message", "旧标签未发行");
//					return map;
//				}
//			}else{
//				//判断库存是否可回收电子标签。电子标签有偿更换，不需要回收旧标签
//				if(chargeFee.compareTo(new BigDecimal("0"))==0){
//					
//					//2017/05/05 营运的回收接口增加了一个参数 newCardSourceType（产品类型）
//					String newCardSourceType = "";
//					Map<String, Object> newCardMap = inventoryService.getProductTypeByCode("2", newTagNo);//2是其他
//					if("0".equals((String)newCardMap.get("flag"))){
//						newCardSourceType = (String)newCardMap.get("sourceType");
//					}else{
//						map.put("success", false);
//						map.put("message", (String)newCardMap.get("message"));
//						return map;
//					}
//					
//					//因为可以地标换国标，所以这里不能16位
//					//if (newTagInfo.getTagNo().length()==16) {
//						//此时获得的newTagInfo.getTagNo()是旧电子标签
//						mapReclaim = inventoryService.omsInterface(newTagInfo.getTagNo(), "2", interfaceRecordReclaim,"",
//								operplaceID,tagBusinessRecord.getOperID(),tagBusinessRecord.getOperName(),"2","",null,new BigDecimal("0"),"43",newCardSourceType);
//						result = (Boolean) mapReclaim.get("result");
//						if (!result) {
//							map.put("success", false);
//							map.put("message", mapReclaim.get("message").toString());
//							return map;
//						}
//					//}
//				}
//			}
			
			
			
			InterfaceRecord interfaceRecordIssue = null;
			Map<String, Object> mapIssue = null;
			
			//判断新标签是否在提货明细表有
//			TagTakeDetail newTagTakeDetail = tagTakeDetailDao.findByTagNo(newTagNo);
//			if(newTagTakeDetail!=null){
//				if("1".equals(newTagTakeDetail.getTagState())){
//					map.put("success", false);
//					map.put("message", "新电子标签已提货发行");
//					return map;
//				}else{
//					//先提货后发型的，发行后，要更新提货明细表的状态为已发行
//					//tagTakeDetailDao.updateTagStateByTagNo("1", newTagNo);
//					updateNewTagTakeDetail = true;
//					newTagInfo.setIssueType("2");//先提货后发行
//				}
//			}else{
				//判断库存是否可发行电子标签
				//有偿更换为发行出库，无偿更换为更换出库。默认为发行出库，更换出库传"replace"
				String outType="";
//				if(chargeFee.compareTo(new BigDecimal("0"))==0){
					//无偿更换、更换出库
					outType="replace";
					newTagInfo.setIssueType("4");//更换发行
//				}else{
//					outType = "issue";
//					newTagInfo.setIssueType("1");//提货发行一次完成
//				}
				mapIssue = inventoryService.omsInterface(newTagNo, "1", interfaceRecordIssue, outType,
						operplaceID,tagBusinessRecord.getOperID(),tagBusinessRecord.getOperName(),"2","customPoint",productInfoId,newTagInfo.getChargeCost(),"43","");
//				result = (Boolean) mapIssue.get("result");
//				if (!result) {
//					map.put("success", false);
//					map.put("message", mapIssue.get("message").toString());
//					return map;
//				}
//			}
			
//			if(updateNewTagTakeDetail){
				tagTakeDetailDao.updateTagStateByTagNo("1", newTagNo);
//			}
			
			
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
			if(newTagInfo.getIssuetime()!=null)oldTagInfo.setIssuetime(newTagInfo.getIssuetime());
			if(newTagInfo.getStartTime()!=null)oldTagInfo.setStartTime(newTagInfo.getStartTime());
			if(newTagInfo.getEndTime()!=null)oldTagInfo.setEndTime(newTagInfo.getEndTime());
			unifiedParam.setTagInfo(oldTagInfo);//旧电子标签

			newTagInfo.setChargeCost(chargeFee);
			newTagInfo.setTagNo(newTagNo);
			newTagInfo.setId(newTagInfoId);
			//newTagInfo.setTagState("1");// 新标签状态为“正常”
			newTagInfo.setTagState(TagStateEnum.normal.getValue());
			//newTagInfo.setCorrectTime(new Date());//更新时间
			//newTagInfo.setCorrectOperID(operID);
			//newTagInfo.setCorrectPlaceID(operplaceID);
			newTagInfo.setCost(realCost);
			newTagInfo.setInstallmanName(installmanName);
			newTagInfo.setProductType(productTypeCode);
			
			//
//			newTagInfo.setOperName(tagBusinessRecord.getOperName());
//			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
//			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
//			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			
			newTagInfo.setOperID(tagBusinessRecord.getOperID());
			newTagInfo.setOperName(tagBusinessRecord.getOperName());
			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			newTagInfo.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			newTagInfo.setCorrectTime(new Date());//更新时间
			newTagInfo.setCorrectOperID(newTagInfo.getOperID());
			newTagInfo.setCorrectOperName(newTagInfo.getOperName());
			newTagInfo.setCorrectOperNo(newTagInfo.getOperNo());
			newTagInfo.setCorrectPlaceID(newTagInfo.getIssueplaceID());
			newTagInfo.setCorrectPlaceName(newTagInfo.getPlaceName());
			newTagInfo.setCorrectPlaceNo(newTagInfo.getPlaceNo());
			
			
			/*相关时间设置：
			有效启用时间、有效截止时间应该是取营运系统一发时设置的时间
			发行时间应该是办理业务的当前时间
			维保开始时间：如果有偿（成本费不为0）：则使用本次发行时间；如果是无偿（成本费为0），则使用上一张卡的维保开始时间。*/
//			if(newTagTakeDetail!=null){
//				//若新标签已提货，则从提货明细表获取信息
//				//设置obuSerial
//				newTagInfo.setObuSerial(newTagTakeDetail.getObuSerial());
//				newTagInfo.setStartTime(newTagTakeDetail.getStartTime());
//				newTagInfo.setEndTime(newTagTakeDetail.getEndTime());
//			}else{
				//否则用调用营运初始化接口获得的信息
				//设置obuSerial
				if(mapIssue.get("obuSerial")!=null){
					newTagInfo.setObuSerial((String)mapIssue.get("obuSerial"));
				}
				newTagInfo.setStartTime((Date)mapIssue.get("startDate"));
				newTagInfo.setEndTime((Date)mapIssue.get("endDate"));
//			}
			
			//设置维保时间
			Calendar cal = Calendar.getInstance();;
			if(chargeFee.compareTo(new BigDecimal("0"))==0){
				//无偿，沿用原卡的发行时间作为维保起始时间
				cal.setTime(oldTagInfo.getIssuetime());//此时oldTagInfo的发行时间就是旧的标签的发行时间
			}else{
				
			}
			
			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info(paramMap);
			if(paramMap!=null && "成功".equals((String)paramMap.get("message"))){
				cal.add(Calendar.YEAR, Integer.parseInt((String)paramMap.get("value")));
			}else if(paramMap!=null && !"成功".equals((String)paramMap.get("message"))){
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败:"+(String)paramMap.get("message"));
				return map;
			}else{
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败");
				return map;
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}
			
			newTagInfo.setMaintenanceTime(cal.getTime());
			newTagInfo.setIssuetime(new Date());
			
			
			
			// 更换的收费chargeFee有两种情况,等于0或者大于0
			if (chargeFee.compareTo(new BigDecimal("0"))==0) {
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
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
				AccountFundChange accountFundChange=new AccountFundChange();
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
				accountFundChangeDao.saveChange(accountFundChange);
				
			} else {
				// 如果收费不等于0，要调用更新账户余额的接口
				// 更新账户可用余额=当前账户可用余额-收费金额
				// mainAccountInfo = mainAccountInfoDao.findByMainId(clientID);
				unifiedParam.setNewTagInfo(newTagInfo);// 新电子标签
				unifiedParam.setType("7");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setPlaceId(operplaceID);
				unifiedParam.setOperId(tagBusinessRecord.getOperID());
				unifiedParam.setOperName(tagBusinessRecord.getOperName());
				unifiedParam.setOperNo(tagBusinessRecord.getOperNo());
				unifiedParam.setPlaceName(tagBusinessRecord.getPlaceName());
				unifiedParam.setPlaceNo(tagBusinessRecord.getPlaceNo());
				if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					map.put("success", false);
					map.put("message", "电子标签更换失败，余额不足");
					return map;
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord, "0");
				unifiedParam.setType("2");
				CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(tagInfoId, vehicleID);
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				if (!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
					throw new ApplicationException("电子标签更换失败");
				}
			}
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(newTagInfo.getClientID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setTagNo(newTagInfo.getTagNo());
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
			//String[] reason = replaceReason.split("-");
			OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setReason(omsParamReason.getParamValue());
			tagMainRecord.setFaultTypeId(Long.parseLong(faultType));
			tagMainRecord.setReasonId(Long.parseLong(faultReason));
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
			//tagBusinessRecord.setReason(tagMainRecord.getReason());
			//tagBusinessRecord.setFaultType(tagMainRecord.getFaultType());
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			tagBusinessRecord.setReasonId(omsParamReason.getId());
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
			
			//清算接口    //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, newTagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			//旧标签注销
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					oldTagInfo.getTagNo(),oldTagInfo.getObuSerial(), oldTagInfo.getStartTime(), oldTagInfo.getEndTime(), "标签更换业务的旧标签注销");
			
			//写给铭鸿的清算数据：用户状态信息
			//新标签发行
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					newTagInfo.getTagNo(),newTagInfo.getObuSerial(), newTagInfo.getStartTime(), newTagInfo.getEndTime(), "标签更换业务的新标签发行");
			
			
			
			//原清算数据，没用了
			/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			saveTollCardBlack(oldTagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
			
			//
			//DarkList darkList = darkListDao.findByCardNo(oldTagInfo.getTagNo());
			//
			oldTagInfo.setOperID(operID);
			oldTagInfo.setIssueplaceID(operplaceID);
			//新增字段
			oldTagInfo.setOperName(tagBusinessRecord.getOperName());
			oldTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			oldTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			oldTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			oldTagInfo.setClientID(clientID);
			//saveDarkList(oldTagInfo,darkList,"6", "1");
			//保存电子标签黑名单流水
			blackListService.saveOBUCancel(oldTagInfo.getObuSerial(), new Date()
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
			serviceWater.setCardNo(oldTagInfo.getTagNo());
			serviceWater.setNewCardNo(newTagNo);
			serviceWater.setObuSerial(newTagInfo.getObuSerial());
			serviceWater.setSerType("303");//303电子标签更换
			
			serviceWater.setAmt(newTagInfo.getCost());//应收金额
			serviceWater.setAulAmt(newTagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(newTagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagBusinessRecord.getOperID());
			serviceWater.setOperName(tagBusinessRecord.getOperName());
			serviceWater.setOperNo(tagBusinessRecord.getOperNo());
			serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
			serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
			serviceWater.setRemark("自营客服系统：电子标签更换");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			

			//电子标签有偿更换，不需要回收旧标签
			//if (oldTagInfo.getTagNo().length()==16) {
				//回收成功后，更新营运接口调用登记记录的客服状态
				//电子标签有偿更换，不需要回收旧标签。mapReclaim空时即没有调用营运回收旧标签
				if(mapReclaim!=null){
					interfaceRecordReclaim = (InterfaceRecord) mapReclaim.get("interfaceRecord");
					if (interfaceRecordReclaim != null&&interfaceRecordReclaim.getCardno()!=null) {
						interfaceRecordReclaim.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecordReclaim);
					}
				}
				
			//}
			
			/*//电子标签更换成功后，若tagTakeDetail不为null
			if(tagTakeDetail!=null){
				//tagTakeDetail.setTagState("1");
				tagTakeDetailDao.updateTagStateByTagNo("1", tagTakeDetail.getTagNo());
			}*/
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			if(mapIssue!=null){
				interfaceRecordIssue = (InterfaceRecord) mapIssue.get("interfaceRecord");
				if (interfaceRecordIssue != null&&interfaceRecordIssue.getCardno()!=null) {
					interfaceRecordIssue.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordIssue);
				}
			}
			
			map.put("success", true);
			map.put("message", "电子标签更换成功");
			
		} catch (ApplicationException e) {
			logger.error("电子标签更换失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return map;
	}
	
	
	/**
	 * 更换电子标签
	 */
	@Override
	public Map<String,Object> saveReplaceTagInfoForAMMS(BigDecimal chargeFee, String newTagNo,
			Long tagInfoId, Long clientID, Long vehicleID,
			String faultReason, Long installmanID, String memo, Long operID,
			Long operplaceID,TagBusinessRecord tagBusinessRecord,Long productInfoId,
			BigDecimal realCost,String installmanName,String productTypeCode,String faultType,Map<String,Object> params) {
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
			
			boolean result = false;
			
			InterfaceRecord interfaceRecordIssue = null;
			Map<String, Object> mapIssue = null;
			//判断库存是否可发行电子标签
			//有偿更换为发行出库，无偿更换为更换出库。默认为发行出库，更换出库传"replace"
			String outType="replace";
			mapIssue = inventoryServiceForAgent.omsInterface(newTagNo, "1", interfaceRecordIssue, outType, null, null, "", "2", "", null, chargeFee, "43");
			result = (Boolean) mapIssue.get("result");
			if (!result) {
				map.put("success", false);
				map.put("message", mapIssue.get("message").toString());
				return map;
			}
			
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
			if(newTagInfo.getIssuetime()!=null)oldTagInfo.setIssuetime(newTagInfo.getIssuetime());
			if(newTagInfo.getStartTime()!=null)oldTagInfo.setStartTime(newTagInfo.getStartTime());
			if(newTagInfo.getEndTime()!=null)oldTagInfo.setEndTime(newTagInfo.getEndTime());
			unifiedParam.setTagInfo(oldTagInfo);//旧电子标签
			
			newTagInfo.setChargeCost(chargeFee);
			newTagInfo.setTagNo(newTagNo);
			newTagInfo.setId(newTagInfoId);
			newTagInfo.setTagState(TagStateEnum.normal.getValue());
			newTagInfo.setIssueType(TagIssueTypeEnum.replaceTagIss.getValue());//更换发行
			newTagInfo.setIssuetime(new Date());// 发行时间
			newTagInfo.setCorrectTime(new Date());//更新时间
			newTagInfo.setCorrectOperID(operID);
			newTagInfo.setCorrectPlaceID(operplaceID);
			newTagInfo.setCost(realCost);
			newTagInfo.setInstallmanName(installmanName);
			newTagInfo.setProductType(productTypeCode);
			
			//
			newTagInfo.setOperName(tagBusinessRecord.getOperName());
			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			Map<String,Object> m = (Map<String,Object>)mapIssue.get("initializedOrNotMap");
			//设置obuSerial
			if(m.get("obuSerial")!=null){
				newTagInfo.setObuSerial((String)m.get("obuSerial"));
			}
			newTagInfo.setStartTime((Date)m.get("startDate"));
			newTagInfo.setEndTime((Date)m.get("endDate"));
			
			//设置维保时间
			Calendar cal = Calendar.getInstance();;
			
			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info(paramMap);
			if(paramMap!=null && "成功".equals((String)paramMap.get("message"))){
				cal.add(Calendar.YEAR, Integer.parseInt((String)paramMap.get("value")));
			}else if(paramMap!=null && !"成功".equals((String)paramMap.get("message"))){
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败:"+(String)paramMap.get("message"));
				return map;
			}else{
				map.put("success", false);
				map.put("message", "获取营运维保周期参数失败");
				return map;
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}
			
			if(chargeFee.compareTo(new BigDecimal("0"))!=0){
				newTagInfo.setMaintenanceTime(cal.getTime());
				//无偿，沿用原卡的发行时间作为维保起始时间
				//cal.setTime(oldTagInfo.getIssuetime());//此时oldTagInfo的发行时间就是旧的标签的发行时间
			}
			
			unifiedParam.setNewTagInfo(newTagInfo);// 新电子标签
			unifiedParam.setType("2");
			// 车卡标签绑定表的电子标签id要改为新的
			CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(tagInfoId, vehicleID);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
				map.put("success", false);
				map.put("message", "电子标签更换失败");
				return map;
				
			}
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
				
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(newTagInfo.getClientID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setTagNo(newTagInfo.getTagNo());
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
//			String[] reason = replaceReason.split("-");
//			tagMainRecord.setFaultType(reason[0]);
//			tagMainRecord.setReason(reason[1]);
			OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setReason(omsParamReason.getParamValue());
			tagMainRecord.setFaultTypeId(Long.parseLong(faultType));
			tagMainRecord.setReasonId(Long.parseLong(faultReason));
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
//			tagBusinessRecord.setReason(tagMainRecord.getReason());
//			tagBusinessRecord.setFaultType(tagMainRecord.getFaultType());
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			tagBusinessRecord.setReasonId(omsParamReason.getId());
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
			
			//清算接口    //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, newTagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			//旧标签注销
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					oldTagInfo.getTagNo(),oldTagInfo.getObuSerial(), oldTagInfo.getStartTime(), oldTagInfo.getEndTime(), "标签更换业务的旧标签注销");
			
			//写给铭鸿的清算数据：用户状态信息
			//新标签发行
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					newTagInfo.getTagNo(),newTagInfo.getObuSerial(), newTagInfo.getStartTime(), newTagInfo.getEndTime(), "标签更换业务的新标签发行");
			
			
			
			//原清算数据，没用了
			/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			saveTollCardBlack(oldTagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
			
			//
			//DarkList darkList = darkListDao.findByCardNo(oldTagInfo.getTagNo());
			//
			oldTagInfo.setOperID(operID);
			oldTagInfo.setIssueplaceID(operplaceID);
			//新增字段
			oldTagInfo.setOperName(tagBusinessRecord.getOperName());
			oldTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			oldTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			oldTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			oldTagInfo.setClientID(clientID);
			//saveDarkList(oldTagInfo,darkList,"6", "1");
			//保存电子标签黑名单流水
			blackListService.saveOBUCancel(oldTagInfo.getObuSerial(), new Date()
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
			serviceWater.setCardNo(oldTagInfo.getTagNo());
			serviceWater.setNewCardNo(newTagNo);
			serviceWater.setObuSerial(newTagInfo.getObuSerial());
			serviceWater.setSerType("303");//303电子标签更换
			
			serviceWater.setAmt(newTagInfo.getCost());//应收金额
			serviceWater.setAulAmt(newTagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(newTagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagBusinessRecord.getOperID());
			serviceWater.setOperName(tagBusinessRecord.getOperName());
			serviceWater.setOperNo(tagBusinessRecord.getOperNo());
			serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
			serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
			serviceWater.setRemark("代理点系统：电子标签更换");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//电子标签更换回执
			TagChangeReceipt tagChangeReceipt = new TagChangeReceipt();
			tagChangeReceipt.setTitle("电子标签更换回执");
			tagChangeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			tagChangeReceipt.setOldTagNo(oldTagInfo.getTagNo());
			tagChangeReceipt.setTagNo(newTagInfo.getTagNo());
			tagChangeReceipt.setTagChargeCost(NumberUtil.get2Decimal(newTagInfo.getCost().doubleValue()*0.01));
			tagChangeReceipt.setVehiclePlate(vehicle.getVehiclePlate());
			tagChangeReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
			tagChangeReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits()+"");
			tagChangeReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
			tagChangeReceipt.setVehicleModel(vehicle.getModel());
			tagChangeReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
			tagChangeReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
			tagChangeReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
			tagChangeReceipt.setVehicleOwner(vehicle.getOwner());
			tagChangeReceipt.setVehicleLong(vehicle.getVehicleLong()+"");
			tagChangeReceipt.setVehicleWidth(vehicle.getVehicleWidth()+"");
			tagChangeReceipt.setVehicleHeight(vehicle.getVehicleHeight()+"");
			tagChangeReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
			tagChangeReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
			tagChangeReceipt.setVehicleAxles(vehicle.getVehicleAxles()+"");
			tagChangeReceipt.setVehicleWheels(vehicle.getVehicleWheels()+"");
			tagChangeReceipt.setInstallMan(installmanName);
			Receipt receipt = new Receipt();
			receipt.setTypeCode(TagBussinessTypeEnum.tagChange.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagChange.getName());
			receipt.setTagNo(tagChangeReceipt.getTagNo());
			receipt.setVehicleColor(vehicle.getVehicleColor());
			receipt.setVehiclePlate(vehicle.getVehiclePlate());
			this.saveReceipt(receipt,tagBusinessRecord,tagChangeReceipt,customer);
			
			map.put("success", true);
			map.put("message", "电子标签更换成功");
			return map;
			
		} catch (ApplicationException e) {
			logger.error("电子标签更换失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	
	/**
	 * 查询并判断该客户的账户余额是否充足 如果不充足（账户可用余额<收费金额），返回false 如果充足，返回true
	 * 
	 * @param mainId
	 * @param chargeFee
	 * @return
	 */
	public boolean checkBalance(Long mainId, BigDecimal chargeFee) {
		MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(mainId);
		// System.out.println(mainAccountInfo.getBalance());
		// System.out.println(mainId);
		if (chargeFee.compareTo(mainAccountInfo.getAvailableBalance()) == -1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 查找CarObuCardInfo表是否已经绑定了更换的电子标签
	 * 
	 * @param newTagInfoId
	 * @param vehicleID
	 * @return
	 */
	public CarObuCardInfo findIsReplaceTag(Long newTagInfoId, Long vehicleID) {

		CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(newTagInfoId, vehicleID);

		return carObuCardInfo;
	}

	public TagInfo findTagInfoByTagNo(String tagNo) {
		return tagInfoDao.findByTagNo(tagNo);
	}

	public TagTakeDetail findTagTakeDetailByTagNo(String tagNo) {
		return tagTakeDetailDao.findByTagNo(tagNo);
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

	@Override
	public Map<String, Object> saveReplaceForLian(BigDecimal chargeFee, String newTagNo,
			Long tagInfoId, Long clientID, Long vehicleID,
			String replaceReason, Long installmanID, String memo, Long operID, 
			Long operplaceID,TagBusinessRecord tagBusinessRecord,Long productInfoId,
			BigDecimal realCost,String installmanName,String productTypeCode) {
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
			
			InterfaceRecord interfaceRecordIssue = null;
			Map<String, Object> mapIssue = null;
			boolean result = false;
			//
			//有偿更换为发行出库，无偿更换为更换出库。默认为发行出库，更换出库传"replace"
			String outType="";
			
			if(chargeFee.compareTo(new BigDecimal("0"))==0){
				//无偿更换、更换出库
				outType="replace";
			}else{
				outType = "issue";
			}
			
			mapIssue = inventoryServiceForAgent.omsInterface(newTagNo, "1", interfaceRecordIssue, outType,
					null,null,"","2","",null,newTagInfo.getChargeCost(),"43");
			result = (Boolean) mapIssue.get("result");
			if (!result) {
				map.put("success", false);
				map.put("message", mapIssue.get("message").toString());
				return map;
			}
			
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

			oldTagInfo.setId(newTagInfo.getId());//旧电子标签id
			oldTagInfo.setTagNo(newTagInfo.getTagNo());//旧电子标签号
			oldTagInfo.setChargeCost(chargeFee);// 要set吗？
			oldTagInfo.setObuSerial(newTagInfo.getObuSerial());
			if(newTagInfo.getStartTime()!=null)oldTagInfo.setStartTime(newTagInfo.getStartTime());
			if(newTagInfo.getEndTime()!=null)oldTagInfo.setEndTime(newTagInfo.getEndTime());
			unifiedParam.setTagInfo(oldTagInfo);//旧电子标签
			newTagInfo.setIssueType("4");//更换发行
			newTagInfo.setChargeCost(chargeFee);
			newTagInfo.setTagNo(newTagNo);
			newTagInfo.setId(newTagInfoId);
			//newTagInfo.setTagState("1");// 新标签状态为“正常”
			newTagInfo.setTagState(TagStateEnum.normal.getValue());
			newTagInfo.setIssuetime(new Date());// 发行时间
			newTagInfo.setCorrectTime(new Date());//更新时间
			newTagInfo.setCorrectOperID(operID);
			newTagInfo.setCorrectPlaceID(operplaceID);
			newTagInfo.setInstallmanName(installmanName);
			newTagInfo.setCost(realCost);
			newTagInfo.setProductType(productTypeCode);
			//
			newTagInfo.setOperName(tagBusinessRecord.getOperName());
			newTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			newTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			newTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());

			
			//设置obuSerial
			Map<String,Object> m = (Map<String,Object>)mapIssue.get("initializedOrNotMap");
			if(m!=null){
				newTagInfo.setObuSerial((String)m.get("obuSerial"));
				//有效启用时间都是用新的，结束时间收费了用新的，没收费用旧的。。。
				newTagInfo.setStartTime((Date)m.get("startDate"));
			}
			
			
			// 更换的收费chargeFee有两种情况,等于0或者大于0
			if (chargeFee.compareTo(new BigDecimal("0"))==0) {
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
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
				AccountFundChange accountFundChange=new AccountFundChange();
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
				accountFundChangeDao.saveChange(accountFundChange);
				
			} else {
				// 如果收费不等于0，要调用更新账户余额的接口
				// 更新账户可用余额=当前账户可用余额-收费金额
				// mainAccountInfo = mainAccountInfoDao.findByMainId(clientID);
				if(m!=null){
					
					newTagInfo.setEndTime((Date)m.get("endDate"));
				}
				
//				newTagInfo.setStartTime(new Date());//有效启用时间
				newTagInfo.setMaintenanceTime(new Date());//维保时间
				newTagInfo.setIssuetime(new Date());//发行时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strStartTime = format.format(newTagInfo.getStartTime());
				String strYear = strStartTime.substring(0, 4);
				String strEndYear = (Integer.parseInt(strYear)+10)+"";//有效截止时间是有效启用时间+10年（参数设置）
				Date endTime = null;
				try {
					endTime = format.parse(strStartTime.replace(strYear, strEndYear));
				} catch (ParseException e) {
					e.printStackTrace();
				}
//				newTagInfo.setEndTime(endTime);
				
				unifiedParam.setNewTagInfo(newTagInfo);// 新电子标签
				unifiedParam.setType("7");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setPlaceId(operplaceID);
				unifiedParam.setOperId(tagBusinessRecord.getOperID());
				unifiedParam.setOperName(tagBusinessRecord.getOperName());
				unifiedParam.setOperNo(tagBusinessRecord.getOperNo());
				unifiedParam.setPlaceName(tagBusinessRecord.getPlaceName());
				unifiedParam.setPlaceNo(tagBusinessRecord.getPlaceNo());
				if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					map.put("success", false);
					map.put("message", "电子标签更换失败，余额不足");
					return map;
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord, "0");
				unifiedParam.setType("2");
				CarObuCardInfo carObuCardInfo = tagReplaceDao.findOldCarObuCardInfo(tagInfoId, vehicleID);
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				if (!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
					throw new ApplicationException("电子标签更换失败");
				}
			}
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(newTagInfo.getClientID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setTagNo(newTagInfo.getTagNo());
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
			tagMainRecord.setInstallman(installmanName);
			tagMainRecord.setChargeCost(new BigDecimal("0"));
			String[] reason = replaceReason.split("-");
			tagMainRecord.setFaultType(reason[0]);
			tagMainRecord.setReason(reason[1]);
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
			tagBusinessRecord.setOperplaceID(operplaceID);
			tagBusinessRecord.setBusinessType("3");// 更换
			tagBusinessRecord.setInstallmanID(Long.parseLong(installmanName));// 安装人员
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
			
			//清算接口   //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, newTagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			//旧标签注销
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					oldTagInfo.getTagNo(),oldTagInfo.getObuSerial(), oldTagInfo.getStartTime(), oldTagInfo.getEndTime(), "标签更换业务的旧标签注销");
			
			//写给铭鸿的清算数据：用户状态信息
			//新标签发行
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					newTagInfo.getTagNo(),newTagInfo.getObuSerial(), newTagInfo.getStartTime(), newTagInfo.getEndTime(), "标签更换业务的新标签发行");
			
			
			
			//原清算数据，没用了
			/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
					null, oldTagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
			saveTollCardBlack(oldTagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
			
			//
			//DarkList darkList = darkListDao.findByCardNo(oldTagInfo.getTagNo());
			//
			oldTagInfo.setOperID(operID);
			oldTagInfo.setIssueplaceID(operplaceID);
			//新增字段
			oldTagInfo.setOperName(tagBusinessRecord.getOperName());
			oldTagInfo.setOperNo(tagBusinessRecord.getOperNo());
			oldTagInfo.setPlaceName(tagBusinessRecord.getPlaceName());
			oldTagInfo.setPlaceNo(tagBusinessRecord.getPlaceNo());
			oldTagInfo.setClientID(clientID);
			//saveDarkList(oldTagInfo,darkList,"6", "1");
			//注销旧电子标签
			blackListService.saveOBUCancel(oldTagInfo.getObuSerial(), new Date()
					, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
					tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
					new Date());
			//电子标签有偿更换，不需要回收旧标签
			
			
			/*//电子标签更换成功后，若tagTakeDetail不为null
			if(tagTakeDetail!=null){
				//tagTakeDetail.setTagState("1");
				tagTakeDetailDao.updateTagStateByTagNo("1", tagTakeDetail.getTagNo());
			}*/
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			if(mapIssue!=null){
				interfaceRecordIssue = (InterfaceRecord) mapIssue.get("interfaceRecord");
				if (interfaceRecordIssue != null&&interfaceRecordIssue.getCardno()!=null) {
					interfaceRecordIssue.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordIssue);
				}
			}
			
			map.put("success", true);
			map.put("message", "电子标签更换成功");
			return map;
			
		} catch (ApplicationException e) {
			logger.error("电子标签更换失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
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
	public void saveClear(Customer customer, String oldTagNo, String newTagNo,Long vehicleID) {
		try {
			//车辆
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(vehicleID);
			
			//持卡人信息
			MacaoCardCustomer macaoCardCustomer = tagReplaceDao.getMacaoCardCustomer(oldTagNo);
			
			//电子标签
			TagInfo tagInfo = tagReplaceDao.getTagInfoByTagNo(newTagNo);
			
			String buitFlag = tagReplaceDao.findBindByTagNo(newTagNo);
			
			//获取原始标签号
			String aulTagNo = null;
			TagReplaceFlow tagReplaceFlow = tagReplaceFlowDao.findByTagNo(oldTagNo);
			TagReplaceFlow newTagReplaceFlow = new TagReplaceFlow();
			
			if(tagReplaceFlow == null)
				aulTagNo = oldTagNo;
			else
				aulTagNo = tagReplaceFlow.getTagNo();
			
			//保存 电子标签更换流水记录表 记录
			newTagReplaceFlow.setId(sequenceUtil.getSequenceLong("SEQ_CSMStagreplaceflow_NO"));
			newTagReplaceFlow.setNewTagNo(newTagNo);
			newTagReplaceFlow.setOldTagNo(oldTagNo);
			newTagReplaceFlow.setReplaceTime(new Date());
			newTagReplaceFlow.setTagNo(aulTagNo);
			tagReplaceFlowDao.save(newTagReplaceFlow);
			
			//清算数据
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());
			
			TagReplaceInfo tagReplaceInfo = new TagReplaceInfo();
			tagReplaceInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSTAGREPLACEINFO_NO"));
			tagReplaceInfo.setInterCode("91008");
			tagReplaceInfo.setCreateTime(createTime);
			tagReplaceInfo.setOldTagNo(oldTagNo);
			tagReplaceInfo.setTagNo(newTagNo);
			tagReplaceInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			tagReplaceInfo.setVehicleColor(vehicleInfo.getVehicleColor());
			tagReplaceInfo.setModel(vehicleInfo.getModel());
			tagReplaceInfo.setVehicleType(vehicleInfo.getVehicleType());
			tagReplaceInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
			tagReplaceInfo.setVehicleLong(vehicleInfo.getVehicleLong());
			tagReplaceInfo.setVehicleWidth(vehicleInfo.getVehicleWidth());
			tagReplaceInfo.setVehicleHeight(vehicleInfo.getVehicleHeight());
			tagReplaceInfo.setVehicleAxles(vehicleInfo.getVehicleAxles());
			tagReplaceInfo.setVehicleWheels(vehicleInfo.getVehicleWheels());
			tagReplaceInfo.setCustomerName(customer.getOperName());
			tagReplaceInfo.setOwner(vehicleInfo.getOwner());
//			tagReplaceInfo.setZipCode("");
			tagReplaceInfo.setAddress(macaoCardCustomer.getAddress());
			tagReplaceInfo.setCnName(macaoCardCustomer.getCnName());
			tagReplaceInfo.setTel(macaoCardCustomer.getTel());
			tagReplaceInfo.setShortMsg(macaoCardCustomer.getShortMsg());
			tagReplaceInfo.setIdentificationCode(vehicleInfo.getIdentificationCode());
			tagReplaceInfo.setUsingNature(vehicleInfo.getUsingNature());
			tagReplaceInfo.setVehicleSpecificInformation(vehicleInfo.getVehicleSpecificInformation());
			tagReplaceInfo.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
//			tagReplaceInfo.setEndTime("");
//			tagReplaceInfo.setTagChipNo("");
//			tagReplaceInfo.setSystemNo("");
//			tagReplaceInfo.setSerMemo("");
			tagReplaceInfo.setCost(tagInfo.getCost());
			tagReplaceInfo.setChargeCost(tagInfo.getChargeCost());
			tagReplaceInfo.setNscVehicletype(vehicleInfo.getNSCVehicleType());
//			tagReplaceInfo.setErrorCode("");
//			tagReplaceInfo.setServiceFlowNo("");
			
			tagReplaceInfo.setAulTagNo(aulTagNo);
			tagReplaceInfo.setBuitFlag(buitFlag);
			tagReplaceInfo.setMainTime(tagInfo.getMaintenanceTime());
			
			tagReplaceInfoDao.save(tagReplaceInfo);
		} catch (Exception e) {
			e.printStackTrace();
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
