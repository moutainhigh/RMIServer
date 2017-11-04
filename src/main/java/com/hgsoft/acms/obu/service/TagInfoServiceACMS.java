package com.hgsoft.acms.obu.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.acms.obu.dao.TagBusinessRecordDaoACMS;
import com.hgsoft.acms.obu.dao.TagInfoDaoACMS;
import com.hgsoft.acms.obu.dao.TagInfoHisDaoACMS;
import com.hgsoft.acms.obu.dao.TagTakeDetailDaoACMS;
import com.hgsoft.acms.obu.dao.TagTakeInfoDaoACMS;
import com.hgsoft.acms.obu.serviceInterface.ITagInfoServiceACMS;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.IdTypeACMSEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.TagIssueTypeEnum;
import com.hgsoft.common.Enum.TagSalesTypeEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UsingNatureEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.common.Enum.VehicleUsingTypeEnum;
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
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.serviceInterface.ICardHolderService;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.TagIssuceACMSReceipt;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

import net.sf.json.JSONObject;

/**
 * 电子标签发行
 * 
 * @author qinfu 2016年1月22日14:36:27
 */
@Service
public class TagInfoServiceACMS implements ITagInfoServiceACMS {

	private static Logger logger = Logger.getLogger(TagInfoServiceACMS.class
			.getName());

	@Resource
	private TagInfoDaoACMS tagInfoDaoACMS;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private TagTakeDetailDaoACMS tagTakeDetailDaoACMS;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private RechargeInfoDao rechargeInfoDao;
	@Resource
	private TagTakeInfoDaoACMS tagTakeInfoDaoACMS;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private TagBusinessRecordDaoACMS tagBusinessRecordDaoACMS;
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource 
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private TagInfoHisDaoACMS tagInfoHisDaoACMS;
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private ReceiptDao receiptDao;
	private IdTypeACMSEnum[] idTypeACMSEnums = IdTypeACMSEnum.values();
	private VehicleTypeEnum[] vehicleTypeEnums = VehicleTypeEnum.values();
	private VehicleColorEnum[] vehicleColorEnums = VehicleColorEnum.values();
	//车辆用户性质
	private VehicleUsingTypeEnum[] vehicleUsingTypeEnum = VehicleUsingTypeEnum.values();
	//使用性质
	private UsingNatureEnum[] usingNatureEnum = UsingNatureEnum.values();
	//国标收费车型
	private NSCVehicleTypeEnum[] nscVehicleTypeEnum = NSCVehicleTypeEnum.values();
	@Resource
	private ICardHolderService cardHolderService;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	/**
	 * 发行记录查询
	 * 
	 * @param pager
	 *            页码
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param tagInfo
	 *            电子标签信息你
	 * @param customer
	 *            客户信息
	 * @param vehicleInfo
	 *            车辆信息
	 * @author gaosiling
	 */
	@Override
	public Pager obuRecordTagInfoList(Pager pager, Date starTime, Date endTime,
			TagInfo tagInfo, Customer customer, VehicleInfo vehicleInfo) {
		try {
			Pager maps = tagInfoDaoACMS.obuRecordTagInfoList(pager, starTime,
					endTime, tagInfo, customer, vehicleInfo);
			return maps;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询TagInfoService的obuRecordTagInfoList电子标签发行失败"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 发行记录查询
	 * 
	 * @param pager
	 *            页码
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param tagInfo
	 *            电子标签信息你
	 * @param customer
	 *            客户信息
	 * @param vehicleInfo
	 *            车辆信息
	 * @author gaosiling
	 */
	@Override
	public Pager obuRecordTagInfoListForAMMS(Pager pager, Date starTime, Date endTime,
			TagInfo tagInfo, Customer customer, VehicleInfo vehicleInfo,String bankCode) {
		try {
			Pager maps = tagInfoDaoACMS.obuRecordTagInfoListForAMMS(pager, starTime,
					endTime, tagInfo, customer, vehicleInfo,bankCode);
			return maps;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询TagInfoService的obuRecordTagInfoList电子标签发行失败"+e.getMessage());
		}
		return null;
	}

	@Override
	public TagInfo findById(Long id) {
		return tagInfoDaoACMS.findById(id);
	}

	/**
	 * 添加电子标签发行记录验证 验证电子标签信息
	 */
	@Override
	public Map<String, Object> verifyTagInfo(String tagNo) {
		TagTakeDetail tagTakeDetail = tagTakeDetailDaoACMS.findByTagNo(tagNo);
		Map<String, Object> m = new HashMap<String, Object>();
		if (tagTakeDetail != null) {//先提货后发行
			if ("1".equals(tagTakeDetail.getTagState())) {// 已发行
				m.put("success", false);
				m.put("message", "该电子标签已提货发行");
				return m;
			}
			if ("0".equals(tagTakeDetail.getTagState())) {// 未发行----先提货后发行
				if(tagInfoDaoACMS.findByTagNo(tagNo) != null){
					m.put("success", false);
					m.put("message", "该电子标签已发行");
					return m;
				}
				TagTakeInfo tagTakeInfo = tagTakeInfoDaoACMS.findById(tagTakeDetail.getMainID());
				
				m.put("success", true);
				TagInfo tagInfo = new TagInfo();
				tagInfo.setSalesType(TagSalesTypeEnum.normalSale.getValue());// 销售方式 写死，以后又系统参数传入
				tagInfo.setChargeCost(new BigDecimal(0));// 实收成本 写死，以后又系统参数传入
				tagInfo.setIssueType(TagIssueTypeEnum.issue.getValue());
				m.put("tagInfo", tagInfo);
				//如果是先提货后发行，实收金额是电子标签提货的金额
				m.put("chargeCost", tagTakeInfo.getTagPrice());
				return m;
			}
			m.put("success", false);
			m.put("message", "发行失败");
			return m;
		} else {
			// 提货发行一次完成
			TagInfo tagInfo = tagInfoDaoACMS.findByTagNo(tagNo);
			if (tagInfo != null) {
				// 该电子标签已发行
				m.put("success", false);
				m.put("message", "该电子标签已发行");
				return m;
			}
			/*
			 * if(){//B. 判断电子标签是否在该网点库存： m.put("success", false);
			 * m.put("message", "该电子标签不属于本网点库存，不能发行"); return m; } if(){//C.
			 * 判断电子标签是否为可发行出库 ： m.put("success", false); m.put("message",
			 * "该产品不可发行出库"); return m; } return D. 选择销售方式，按需修改工本费并进行主管授权
			 */
			m.put("success", true);
			tagInfo = new TagInfo();
			tagInfo.setIssueType(TagIssueTypeEnum.firstIss.getValue());
			tagInfo.setChargeCost(new BigDecimal("10"));
			m.put("tagInfo", tagInfo);
			return m;
		}

	}

	/**
	 * 用于验证新增电子发行 车辆验证
	 */
	@Override
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,
			Long customerId) {
		return vehicleInfoDao.findByVehicleInfo(vehicleInfo, customerId);
	}

	@Override
	// 新增发行卡校验
	public TagInfo findByVehicleInfoId(Long id) {
		return tagInfoDaoACMS.findByVehicleInfoId(id);
	}

	/**
	 * 保存电子标签发行
	 */
	@Override
	public Map<String,Object> save(TagInfo tagInfo, Customer customer,
			VehicleInfo vehicleInfo,Long productInfoId) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success=false;
		String message="";
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> maptemp = null;
		if ("1".equals(tagInfo.getIssueType())) {
			//判断库存是否可发行电子标签
			maptemp = inventoryService.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord,"issue",
					tagInfo.getIssueplaceID(),tagInfo.getOperID(),tagInfo.getOperName(),"2","customPoint",productInfoId,tagInfo.getChargeCost(),"41","");
			boolean result = (Boolean) maptemp.get("result");
			if (!result) {
				map.put("success", success);
				map.put("message", maptemp.get("message").toString());
				return map;
			}
			//设置obuid
			if(maptemp.get("obuSerial")!=null){
				tagInfo.setObuSerial((String)maptemp.get("obuSerial"));
			}
			//设置有效启用时间     有效截止时间
			tagInfo.setStartTime((Date)maptemp.get("startDate"));
			tagInfo.setEndTime((Date)maptemp.get("endDate"));
		}
		tagInfo.setWriteBackFlag("0");
		
		/*发行时间、更新时间：当前领卡时间
		维保时间：发行时间+维保周期（2年，参数可配置）*/
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
		
		tagInfo.setMaintenanceTime(cal.getTime());
		tagInfo.setIssuetime(new Date());
		
		
		MainAccountInfo mainAccountInfo = new MainAccountInfo();
		mainAccountInfo.setMainId(customer.getId());
		
		ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
		serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
		serviceFlowRecord.setServicePTypeCode(4);
		serviceFlowRecord.setServiceTypeCode(2);
		serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
		serviceFlowRecord.setOperID(tagInfo.getOperID());
		serviceFlowRecord.setPlaceID(tagInfo.getIssueplaceID());
		//新增的字段
		serviceFlowRecord.setOperName(tagInfo.getOperName());
		serviceFlowRecord.setOperNo(tagInfo.getOperNo());
		serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
		serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
		
		serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
		serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeState(null);
		serviceFlowRecord.setCurrState(tagInfo.getTagState());
		serviceFlowRecord.setAfterState(tagInfo.getTagState());
		serviceFlowRecord.setServicePTypeCode(3);
		serviceFlowRecord.setServiceTypeCode(1);
		
		serviceFlowRecord.setIsDoFlag("0");
		
		try {
			Calendar c = Calendar.getInstance();
			if ("1".equals(tagInfo.getIssueType())) {// 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				UnifiedParam unifiedParam = new UnifiedParam();
				unifiedParam.setType("6");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setTagInfo(tagInfo);
				unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
				unifiedParam.setOperId(tagInfo.getOperID());
				unifiedParam.setOperName(tagInfo.getOperName());
				unifiedParam.setOperNo(tagInfo.getOperNo());
				unifiedParam.setPlaceName(tagInfo.getPlaceName());
				unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					tagInfo.setClientID(customer.getId());
					tagInfo.setTagState(TagStateEnum.normal.getValue());// 正常
					BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil
							.getSequence("SEQ_CSMSTaginfo_NO");
					tagInfo.setId(Long.parseLong(SEQ_CSMSTaginfo_NO.toString()));
					CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
					carObuCardInfo.setTagID(tagInfo.getId());
					carObuCardInfo.setVehicleID(vehicleInfo.getId());
					unifiedParam.setType("1");
					unifiedParam.setTagInfo(tagInfo);
					unifiedParam.setCarObuCardInfo(carObuCardInfo);
					unifiedParam.setMainAccountInfo(mainAccountInfoDao.findByMainId(customer.getId()));
					
					if (obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
						serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
						saveSetBind("1",vehicleInfo.getId());
						success= true;
					}else{
						throw new ApplicationException();
						/*success= false;
						message="发行失败";*/
					}
				} else {
					success= false;
					message="账户扣钱失败";
				}
			} else {// 先提货后发行
				List list = tagTakeInfoDaoACMS.findByTagNo(tagInfo.getTagNo());
				if (list.size() > 0) {
					Date t = null;
					t = (Date) ((Map) list.get(0)).get("TAKEDATE");
					String OBUSERIAL = (String)((Map) list.get(0)).get("OBUSERIAL");
					Date startTime = (Date)((Map) list.get(0)).get("startTime");
					Date endTime = (Date)((Map) list.get(0)).get("endTime");
					tagInfo.setObuSerial(OBUSERIAL);
					tagInfo.setStartTime(startTime);
					tagInfo.setEndTime(endTime);
				} else {
					// 查不到提货时间
				}
				tagInfo.setClientID(customer.getId());
				tagInfo.setTagState(TagStateEnum.normal.getValue());// 正常
				BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil
						.getSequence("SEQ_CSMSTaginfo_NO");
				tagInfo.setId(Long.parseLong(SEQ_CSMSTaginfo_NO.toString()));
				
				UnifiedParam unifiedParam = new UnifiedParam();
				CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
				carObuCardInfo.setTagID(tagInfo.getId());
				carObuCardInfo.setVehicleID(vehicleInfo.getId());
				unifiedParam.setType("8");
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				unifiedParam.setTagInfo(tagInfo);
				unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
				unifiedParam.setOperId(tagInfo.getOperID());
				unifiedParam.setOperName(tagInfo.getOperName());
				unifiedParam.setOperNo(tagInfo.getOperNo());
				unifiedParam.setPlaceName(tagInfo.getPlaceName());
				unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
				unifiedParam.setMainAccountInfo(mainAccountInfoDao.findByMainId(customer.getId()));
				
				if (obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
					serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
					saveSetBind("1",vehicleInfo.getId());
					success= true;
				}else{
					success= false;
					message="电子标签发行失败";
				}
			}
			
			
			if(!success){
				map.put("success", success);
				map.put("message", message);
				map.put("tagInfo",tagInfo);
				return map;
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(vehicleInfo.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagInfo.getOperID());
			tagBusinessRecord.setOperplaceID(tagInfo.getIssueplaceID());
			tagBusinessRecord.setOperName(tagInfo.getOperName());
			tagBusinessRecord.setOperNo(tagInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("1");//发行
			tagBusinessRecord.setInstallmanID(tagInfo.getInstallman());//安装人员
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecord.setBussinessid(tagInfo.getId());
			if(tagInfo.getObuSerial()!=null){
				tagBusinessRecord.setObuSerial(tagInfo.getObuSerial());
			}
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			if(maptemp!=null){
				interfaceRecord = (InterfaceRecord) maptemp.get("interfaceRecord");
				if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
					interfaceRecord.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecord);
					success = true;
				}
			}
			
			map.put("success", success);
			map.put("message", message);
			map.put("tagInfo",tagInfo);
			//清算接口
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签发行");
			
			
			
			VehicleBussiness vehicleBussiness=new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setTagNo(tagInfo.getTagNo());
			vehicleBussiness.setType("61");
			vehicleBussiness.setOperID(tagInfo.getOperID());
			vehicleBussiness.setPlaceID(tagInfo.getIssueplaceID());
			//新增的字段
			vehicleBussiness.setOperName(tagInfo.getOperName());
			vehicleBussiness.setOperNo(tagInfo.getOperNo());
			vehicleBussiness.setPlaceName(tagInfo.getPlaceName());
			vehicleBussiness.setPlaceNo(tagInfo.getPlaceNo());
			
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("联营卡系统：电子标签发行");
			vehicleBussinessDao.save(vehicleBussiness);
			
			customer = customerDao.findById(customer.getId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("301");//301电子标签发行
			
			serviceWater.setAmt(tagInfo.getCost());//应收金额
			serviceWater.setAulAmt(tagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagInfo.getOperID());
			serviceWater.setOperName(tagInfo.getOperName());
			serviceWater.setOperNo(tagInfo.getOperNo());
			serviceWater.setPlaceId(tagInfo.getIssueplaceID());
			serviceWater.setPlaceName(tagInfo.getPlaceName());
			serviceWater.setPlaceNo(tagInfo.getPlaceNo());
			serviceWater.setRemark("联营卡系统：电子标签发行");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
			
			return map;
		} catch (ApplicationException e) {
			logger.error("电子标签发行失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	
	@Override
	public String checkForDelete(TagInfo tagInfo){
		int num = tagBusinessRecordDaoACMS.checkBussiness(tagInfo.getTagNo());
		if(num!=0){
			return "当前电子标签已办理其他业务，无法删除";
		}
		
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> map = null;
		BigDecimal price = new BigDecimal("0");
		if ("1".equals(tagInfo.getIssueType()) || "4".equals(tagInfo.getIssueType())) { // 提货发行一次完成、更换发行
			if (tagInfo.getTagNo().length()!=16) {
				return "电子标签异常，请检查";
			}
		}else{
			return "该电子标签的发行类型异常，请检查";
		}
		return "success";
	}
	
	
	/**
	 * 保存电子标签发行
	 */
	@Override
	public Map<String,Object> saveForAMMS(TagInfo tagInfo, Customer customer,
			VehicleInfo vehicleInfo,Long productInfoId) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success=false;
		String message="";
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> maptemp = null;
		if ("1".equals(tagInfo.getIssueType())) {
			//判断电子标签是否已经初始化
			maptemp = inventoryServiceForAgent.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord, "issue", null, null, "", "2", "", null, tagInfo.getChargeCost(), "41");
			boolean result = (Boolean) maptemp.get("result");
			if (!result) {
				map.put("success", success);
				map.put("message", maptemp.get("message").toString());
				return map;
			}
			Map<String,Object> m = (Map<String,Object>)maptemp.get("initializedOrNotMap");
			//设置obuid
			if(m!=null){
				tagInfo.setObuSerial((String)m.get("obuSerial"));
			}
			//设置有效启用时间     有效截止时间
			tagInfo.setStartTime((Date)m.get("startDate"));
			tagInfo.setEndTime((Date)m.get("endDate"));
		}
		tagInfo.setWriteBackFlag("0");
		
		/*发行时间、更新时间：当前领卡时间
		维保时间：发行时间+维保周期（2年，参数可配置）*/
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
		
		tagInfo.setMaintenanceTime(cal.getTime());
		tagInfo.setIssuetime(new Date());
		
		
		MainAccountInfo mainAccountInfo = new MainAccountInfo();
		mainAccountInfo.setMainId(customer.getId());
		
		ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
		serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
		serviceFlowRecord.setServicePTypeCode(4);
		serviceFlowRecord.setServiceTypeCode(2);
		serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
		serviceFlowRecord.setOperID(tagInfo.getOperID());
		serviceFlowRecord.setPlaceID(tagInfo.getIssueplaceID());
		//新增的字段
		serviceFlowRecord.setOperName(tagInfo.getOperName());
		serviceFlowRecord.setOperNo(tagInfo.getOperNo());
		serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
		serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
		
		serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
		serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeState(null);
		serviceFlowRecord.setCurrState(tagInfo.getTagState());
		serviceFlowRecord.setAfterState(tagInfo.getTagState());
		serviceFlowRecord.setServicePTypeCode(3);
		serviceFlowRecord.setServiceTypeCode(1);
		
		serviceFlowRecord.setIsDoFlag("0");
		
		try {
			Calendar c = Calendar.getInstance();
			if ("1".equals(tagInfo.getIssueType())) {// 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				/*unifiedParam.setType("6");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setTagInfo(tagInfo);
				unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
				unifiedParam.setOperId(tagInfo.getOperID());
				unifiedParam.setOperName(tagInfo.getOperName());
				unifiedParam.setOperNo(tagInfo.getOperNo());
				unifiedParam.setPlaceName(tagInfo.getPlaceName());
				unifiedParam.setPlaceNo(tagInfo.getPlaceNo());*/
				//代理点不用操作主账户
				/*if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {*/
					tagInfo.setClientID(customer.getId());
					tagInfo.setTagState(TagStateEnum.normal.getValue());// 正常
					BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil
							.getSequence("SEQ_CSMSTaginfo_NO");
					tagInfo.setId(Long.parseLong(SEQ_CSMSTaginfo_NO.toString()));
					CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
					carObuCardInfo.setTagID(tagInfo.getId());
					carObuCardInfo.setVehicleID(vehicleInfo.getId());
					
					UnifiedParam unifiedParam = new UnifiedParam();
					unifiedParam.setType("1");
					unifiedParam.setTagInfo(tagInfo);
					unifiedParam.setCarObuCardInfo(carObuCardInfo);
					unifiedParam.setMainAccountInfo(mainAccountInfoDao.findByMainId(customer.getId()));
					
					if (obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
						serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
						saveSetBind("1",vehicleInfo.getId());
						success= true;
					}else{
						throw new ApplicationException();
						/*success= false;
						message="发行失败";*/
					}
				/*} else {
					success= false;
					message="账户扣钱失败";
				}*/
			} else {// 先提货后发行
				List list = tagTakeInfoDaoACMS.findByTagNo(tagInfo.getTagNo());
				if (list.size() > 0) {
					Date t = null;
					t = (Date) ((Map) list.get(0)).get("TAKEDATE");
					String OBUSERIAL = (String)((Map) list.get(0)).get("OBUSERIAL");
					Date startTime = (Date)((Map) list.get(0)).get("startTime");
					Date endTime = (Date)((Map) list.get(0)).get("endTime");
					tagInfo.setObuSerial(OBUSERIAL);
					tagInfo.setStartTime(startTime);
					tagInfo.setEndTime(endTime);
				} else {
					// 查不到提货时间
				}
				tagInfo.setClientID(customer.getId());
				tagInfo.setTagState(TagStateEnum.normal.getValue());// 正常
				BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil
						.getSequence("SEQ_CSMSTaginfo_NO");
				tagInfo.setId(Long.parseLong(SEQ_CSMSTaginfo_NO.toString()));
				
				UnifiedParam unifiedParam = new UnifiedParam();
				CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
				carObuCardInfo.setTagID(tagInfo.getId());
				carObuCardInfo.setVehicleID(vehicleInfo.getId());
				unifiedParam.setType("8");
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				unifiedParam.setTagInfo(tagInfo);
				unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
				unifiedParam.setOperId(tagInfo.getOperID());
				unifiedParam.setOperName(tagInfo.getOperName());
				unifiedParam.setOperNo(tagInfo.getOperNo());
				unifiedParam.setPlaceName(tagInfo.getPlaceName());
				unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
				unifiedParam.setMainAccountInfo(mainAccountInfoDao.findByMainId(customer.getId()));
				
				if (obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
					serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
					saveSetBind("1",vehicleInfo.getId());
					success= true;
				}else{
					success= false;
					message="电子标签发行失败";
				}
			}
			
			
			if(!success){
				map.put("success", success);
				map.put("message", message);
				map.put("tagInfo",tagInfo);
				return map;
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(vehicleInfo.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagInfo.getOperID());
			tagBusinessRecord.setOperplaceID(tagInfo.getIssueplaceID());
			tagBusinessRecord.setOperName(tagInfo.getOperName());
			tagBusinessRecord.setOperNo(tagInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("1");//发行
			tagBusinessRecord.setInstallmanID(tagInfo.getInstallman());//安装人员
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecord.setBussinessid(tagInfo.getId());
			if(tagInfo.getObuSerial()!=null){
				tagBusinessRecord.setObuSerial(tagInfo.getObuSerial());
			}
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			//发行成功后，更新营运接口调用登记记录的客服状态
			/*if(maptemp!=null){
				interfaceRecord = (InterfaceRecord) maptemp.get("interfaceRecord");
				if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
					interfaceRecord.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecord);
				}
			}*/
			success = true;
			
			map.put("success", success);
			map.put("message", message);
			map.put("tagInfo",tagInfo);
			//清算接口
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签发行");
			
			
			
			VehicleBussiness vehicleBussiness=new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setTagNo(tagInfo.getTagNo());
			vehicleBussiness.setType("61");
			vehicleBussiness.setOperID(tagInfo.getOperID());
			vehicleBussiness.setPlaceID(tagInfo.getIssueplaceID());
			//新增的字段
			vehicleBussiness.setOperName(tagInfo.getOperName());
			vehicleBussiness.setOperNo(tagInfo.getOperNo());
			vehicleBussiness.setPlaceName(tagInfo.getPlaceName());
			vehicleBussiness.setPlaceNo(tagInfo.getPlaceNo());
			
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("电子标签发行");
			vehicleBussinessDao.save(vehicleBussiness);
			
			customer = customerDao.findById(customer.getId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("301");//301电子标签发行
			
			serviceWater.setAmt(tagInfo.getCost());//应收金额
			serviceWater.setAulAmt(tagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagInfo.getOperID());
			serviceWater.setOperName(tagInfo.getOperName());
			serviceWater.setOperNo(tagInfo.getOperNo());
			serviceWater.setPlaceId(tagInfo.getIssueplaceID());
			serviceWater.setPlaceName(tagInfo.getPlaceName());
			serviceWater.setPlaceNo(tagInfo.getPlaceNo());
			serviceWater.setRemark("代理点系统：电子标签发行");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
			
			return map;
		} catch (ApplicationException e) {
			logger.error("电子标签发行失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	//state 1为绑定，0未绑定
	public void saveSetBind(String state,Long vehicleInfoId){
		CarObuCardInfo carObuCardInfo=carObuCardInfoDao.findByVehicleID(vehicleInfoId);		
		if(carObuCardInfo.getAccountCID()!=null){
			accountCDao.updateBind(state, carObuCardInfo.getAccountCID());
			}
		if(carObuCardInfo.getPrepaidCID()!=null){
			prepaidCDao.updateBind(state, carObuCardInfo.getPrepaidCID());
			}
	}
	
	@Override
	public Map<String,Object> saveLianCard(TagInfo tagInfo, Customer customer,
			VehicleInfo vehicleInfo,Long productInfoId) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success=false;
		String message="";
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> maptemp = null;
		if ("1".equals(tagInfo.getIssueType())) {
			//判断库存是否可发行电子标签
			maptemp = inventoryServiceForAgent.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord,"issue",
					null,null,"","2","",null,tagInfo.getChargeCost(),"41");
			boolean result = (Boolean) maptemp.get("result");
			if (!result) {
				map.put("success", success);
				map.put("message", maptemp.get("message").toString());
				return map;
			}
			//设置有效启用时间     有效截止时间
			Map<String,Object> m = (Map<String,Object>)maptemp.get("initializedOrNotMap");
			if(m!=null){
				tagInfo.setObuSerial((String)m.get("obuSerial"));
				tagInfo.setStartTime((Date)m.get("startDate"));
				tagInfo.setEndTime((Date)m.get("endDate"));
			}
			
		}
		
		tagInfo.setWriteBackFlag("0");
		
		MainAccountInfo mainAccountInfo = new MainAccountInfo();
		mainAccountInfo.setMainId(customer.getId());
		
		ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
		serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
		serviceFlowRecord.setServicePTypeCode(4);
		serviceFlowRecord.setServiceTypeCode(2);
		serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
		serviceFlowRecord.setOperID(tagInfo.getOperID());
		serviceFlowRecord.setPlaceID(tagInfo.getIssueplaceID());
		//新增的字段
		serviceFlowRecord.setOperName(tagInfo.getOperName());
		serviceFlowRecord.setOperNo(tagInfo.getOperNo());
		serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
		serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
		
		serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
		serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
		serviceFlowRecord.setBeforeState(null);
		serviceFlowRecord.setCurrState(tagInfo.getTagState());
		serviceFlowRecord.setAfterState(tagInfo.getTagState());
		serviceFlowRecord.setServicePTypeCode(3);
		serviceFlowRecord.setServiceTypeCode(1);
		
		serviceFlowRecord.setIsDoFlag("0");
		
		try {
			Calendar c = Calendar.getInstance();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			
			tagInfo.setClientID(customer.getId());
			tagInfo.setIssuetime(c.getTime());
			c.add(Calendar.YEAR, 10);// 十年有效期，以后改成系统参数
			tagInfo.setMaintenanceTime(c.getTime());
//			tagInfo.setStartTime(c.getTime());
			
//			tagInfo.setEndTime(c.getTime());
			tagInfo.setTagState(TagStateEnum.normal.getValue());// 正常
			BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil.getSequence("SEQ_CSMSTaginfo_NO");
			tagInfo.setId(Long.parseLong(SEQ_CSMSTaginfo_NO.toString()));
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setTagID(tagInfo.getId());
			carObuCardInfo.setVehicleID(vehicleInfo.getId());
			unifiedParam.setType("1");
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setMainAccountInfo(mainAccountInfoDao.findByMainId(customer.getId()));
			
			if (obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)) {
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				saveSetBind("1",vehicleInfo.getId());
				success= true;
			}else{
				success= false;
				message="发行失败";
			}
			
			if(!success){
				map.put("success", success);
				map.put("message", message);
				map.put("tagInfo",tagInfo);
				return map;
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(vehicleInfo.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagInfo.getOperID());
			tagBusinessRecord.setOperplaceID(tagInfo.getIssueplaceID());
			tagBusinessRecord.setOperName(tagInfo.getOperName());
			tagBusinessRecord.setOperNo(tagInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("1");//发行
			tagBusinessRecord.setInstallmanID(tagInfo.getInstallman());//安装人员
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecord.setBussinessid(tagInfo.getId());
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			// 标签发行回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("4");
			receipt.setTypeCode(TagBussinessTypeEnum.tagIssuce.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagIssuce.getName());
			receipt.setBusinessId(tagBusinessRecord.getId());
			receipt.setOperId(tagInfo.getOperID());
			receipt.setOperNo(tagInfo.getOperNo());
			receipt.setOperName(tagInfo.getOperName());
			receipt.setPlaceId(tagInfo.getIssueplaceID());
			receipt.setPlaceNo(tagInfo.getPlaceNo());
			receipt.setPlaceName(tagInfo.getPlaceName());
			receipt.setCreateTime(new Date());
			receipt.setOrgan(customer.getOrgan());
			
			TagIssuceACMSReceipt tagissueReceipt = new TagIssuceACMSReceipt(); 
			tagissueReceipt.setTitle("电子标签发行回执");
			tagissueReceipt.setHandleWay("凭资料办理");
			CarObuCardInfo ccobuInfo = carObuCardInfoDao.findByTagNo(tagInfo.getTagNo());
			if (ccobuInfo!=null&&ccobuInfo.getAccountCID()!=null) {
				CardHolder cardHolder = cardHolderService.findCardHolderByCardNo(accountCInfoDao.findById(ccobuInfo.getAccountCID()).getCardNo());
				tagissueReceipt.setName(cardHolder.getName());
				tagissueReceipt.setLinkTel(cardHolder.getPhoneNum());
				tagissueReceipt.setMobileNum(cardHolder.getMobileNum());
				tagissueReceipt.setLinkMan(cardHolder.getLinkMan());
				tagissueReceipt.setLinkAddr(cardHolder.getLinkAddr());
			}
			
			tagissueReceipt.setTagNo(tagInfo.getTagNo());
			tagissueReceipt.setTagSaleType(tagInfo.getIssueType());
			tagissueReceipt.setTagIssueType(tagInfo.getIssueType());
			tagissueReceipt.setTagChargeCost(tagInfo.getChargeCost()+"");
			
			tagissueReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			for (int i = 0; i < vehicleColorEnums.length; i++) {
				if (vehicleColorEnums[i].getValue().equals(vehicleInfo.getVehicleColor())) {
					tagissueReceipt.setVehicleColor((vehicleColorEnums[i].getName()));
				} // if
			} // for
			tagissueReceipt.setWeightsOrSeats(vehicleInfo.getVehicleWeightLimits()+"");
			tagissueReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			tagissueReceipt.setVehicleModel(vehicleInfo.getModel());
			for (int i = 0; i < vehicleTypeEnums.length; i++) {
				if (vehicleTypeEnums[i].getValue().equals(vehicleInfo.getVehicleType())) {
					tagissueReceipt.setVehicleType(vehicleTypeEnums[i].getName());
				} // if
			} // for
			//车辆用户类型
			for (int i = 0; i < vehicleUsingTypeEnum.length; i++) {
				if (vehicleUsingTypeEnum[i].getValue().equals(vehicleInfo.getVehicleUserType())) {
					tagissueReceipt.setVehicleUserType(vehicleUsingTypeEnum[i].getName());
				} // if
			} // for
			//使用性质
			for (int i = 0; i < usingNatureEnum.length; i++) {
				if (usingNatureEnum[i].getValue().equals(vehicleInfo.getUsingNature())) {
					tagissueReceipt.setVehicleUsingNature(usingNatureEnum[i].getName());
				} // if
			} // for
			tagissueReceipt.setVehicleOwner(vehicleInfo.getOwner());
			tagissueReceipt.setVehicleLong(vehicleInfo.getVehicleLong()+"");
			tagissueReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth()+"");
			tagissueReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight()+"");
			for (int i = 0; i < nscVehicleTypeEnum.length; i++) {
				if (nscVehicleTypeEnum[i].getValue().equals(vehicleInfo.getNSCVehicleType())) {
					tagissueReceipt.setVehicleNSCvehicletype(nscVehicleTypeEnum[i].getName());
				} // if
			} // for
			tagissueReceipt.setVehicleIdentificationCode(vehicleInfo.getVehicleSpecificInformation());
			tagissueReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles()+"");
			tagissueReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels()+"");
			tagissueReceipt.setInstallMan(tagInfo.getInstallmanName());
			receipt.setContent(JSONObject.fromObject(tagissueReceipt).toString());
			receiptDao.saveReceipt(receipt);
			
			
			
			map.put("success", success);
			map.put("message", message);
			map.put("tagInfo",tagInfo);
			//清算接口
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
			
			//发行记录 TODO 这个不知道对不对
			TagInfoHis tagInfoHis = new TagInfoHis();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSTaginfoHis_NO");
			tagInfoHis.setId(seq);
			tagInfoHis.setCreateReason("电子标签发行");
			tagInfo.setHisSeqID(seq);
			tagInfoHisDaoACMS.saveHis(tagInfoHis, tagInfo);
			///////////////////////////////////应该可以吧
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签发行");
			
			

			//vehicleBussiness
			VehicleBussiness vehicleBussiness=new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setTagNo(tagInfo.getTagNo());
			vehicleBussiness.setType("61");
			vehicleBussiness.setOperID(tagInfo.getOperID());
			vehicleBussiness.setPlaceID(tagInfo.getIssueplaceID());
			//新增的字段
			vehicleBussiness.setOperName(tagInfo.getOperName());
			vehicleBussiness.setOperNo(tagInfo.getOperNo());
			vehicleBussiness.setPlaceName(tagInfo.getPlaceName());
			vehicleBussiness.setPlaceNo(tagInfo.getPlaceNo());
			
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("联营卡系统：电子标签发行");
			vehicleBussinessDao.save(vehicleBussiness);
			
			customer = customerDao.findById(customer.getId());
			
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("301");//301电子标签发行
			
			serviceWater.setAmt(tagInfo.getCost());//应收金额
			serviceWater.setAulAmt(tagInfo.getChargeCost());//实收金额
			serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagInfo.getOperID());
			serviceWater.setOperName(tagInfo.getOperName());
			serviceWater.setOperNo(tagInfo.getOperNo());
			serviceWater.setPlaceId(tagInfo.getIssueplaceID());
			serviceWater.setPlaceName(tagInfo.getPlaceName());
			serviceWater.setPlaceNo(tagInfo.getPlaceNo());
			serviceWater.setRemark("联营卡系统：电子标签发行");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			return map;
		} catch (ApplicationException e) {
			logger.error("电子标签发行失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 删除电子标签发行记录信息
	 */
	@Override
	public String delete(TagInfo tagInfo, Customer customer) {
		try {
			
			int num = tagBusinessRecordDaoACMS.checkBussiness(tagInfo.getTagNo());
			if(num!=0){
				return "当前电子标签已办理其他业务，无法删除";
			}
			
			InterfaceRecord interfaceRecord = null;
//			Map<String, Object> map = null;
			BigDecimal price = new BigDecimal("0");
			if ("1".equals(tagInfo.getIssueType()) || "4".equals(tagInfo.getIssueType())) { // 提货发行一次完成、更换发行
				if (tagInfo.getTagNo().length()!=16) {
					return "电子标签异常，请检查";
				}
			}else{
				return "该电子标签的发行类型异常，请检查";
			}
			
			UnifiedParam unifiedParam = new UnifiedParam();
			
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			
			//冲正原业务
			customer = customerDao.findById(customer.getId());
			tagInfo = tagInfoDaoACMS.findById(tagInfo.getId());
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByTagid(tagInfo.getId());

			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(customer.getId());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setAfterAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(7);//发行删除
			serviceFlowRecord.setIsDoFlag("0");
			
			serviceFlowRecord.setOperID(tagInfo.getOperID());
			serviceFlowRecord.setPlaceID(tagInfo.getCorrectPlaceID());
			serviceFlowRecord.setOperName(tagInfo.getOperName());
			serviceFlowRecord.setOperNo(tagInfo.getOperNo());
			serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			if ("2".equals(tagInfo.getIssueType())) {// 删除 先提货后发行
				unifiedParam.setType("6");
				unifiedParam.setTagInfo(tagInfo);
				if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
					return "电子标签删除失败";
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");

			}else if ("1".equals(tagInfo.getIssueType()) || "4".equals(tagInfo.getIssueType()) || "3".equals(tagInfo.getIssueType())) {//删除 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				unifiedParam.setType("8");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setTagInfo(tagInfo);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					unifiedParam.setType("6");
					unifiedParam.setTagInfo(tagInfo);
					if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
						throw new ApplicationException();
						//return "电子标签删除失败";
					}
					serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"0");
				}else{
					return "电子标签删除失败";
				}
			}
			
			saveSetBind("0",carObuCardInfo.getVehicleID());//修改绑定标志
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(carObuCardInfo.getVehicleID());
			tagBusinessRecord.setOperTime(new Date());
			if(tagInfo.getChargeCost()!=null)tagBusinessRecord.setRealPrice(tagInfo.getChargeCost().multiply(new BigDecimal("-1")));
			//新增的字段
			tagBusinessRecord.setOperID(unifiedParam.getOperId());	 	
			tagBusinessRecord.setOperplaceID(unifiedParam.getPlaceId());
			tagBusinessRecord.setOperName(unifiedParam.getOperName());
			tagBusinessRecord.setOperNo(unifiedParam.getOperNo());
			tagBusinessRecord.setPlaceName(unifiedParam.getPlaceName());
			tagBusinessRecord.setPlaceNo(unifiedParam.getPlaceNo());

			
			tagBusinessRecord.setBusinessType("8");//发行删除
			//TODO
			tagBusinessRecord.setInstallmanID(25255L);//安装人员
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("删除发行");
			//if(tagInfo.getChargeCost()!=null)tagBusinessRecord.setRealPrice(tagInfo.getChargeCost().multiply(new BigDecimal("-1")));//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("302");//302电子标签删除
			
			//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
			serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(tagInfo.getOperID());
			serviceWater.setOperName(tagInfo.getOperName());
			serviceWater.setOperNo(tagInfo.getOperNo());
			serviceWater.setPlaceId(tagInfo.getIssueplaceID());
			serviceWater.setPlaceName(tagInfo.getPlaceName());
			serviceWater.setPlaceNo(tagInfo.getPlaceNo());
			serviceWater.setRemark("联营卡系统：电子标签删除");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签删除");
			
			
//			if (tagInfo.getTagNo().length()==16 && map!=null) {
//				//冲正成功后，更新营运接口调用登记记录的客服状态
//				interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
//				if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
//					interfaceRecord.setCsmsState("1");
//					interfaceRecordDao.update(interfaceRecord);
//					return "true";
//				}
//			}else {
				return "true";
//			}
//			return "电子标签删除失败";
		} catch (ApplicationException e) {
			logger.error("电子标签删除失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	

	/**
	 * 删除电子标签发行记录信息
	 */
	@Override
	public String delete_ACMS(TagInfo tagInfo, Customer customer) {
		try {
			
			int num = tagBusinessRecordDaoACMS.checkBussiness(tagInfo.getTagNo());
			if(num!=0){
				return "当前电子标签已办理其他业务，无法删除";
			}
			
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			BigDecimal price = new BigDecimal("0");
			if ("1".equals(tagInfo.getIssueType()) || "4".equals(tagInfo.getIssueType())) { // 提货发行一次完成、更换发行
				if (tagInfo.getTagNo().length()!=16) {
					return "电子标签异常，请检查";
				}
			}else{
				return "该电子标签的发行类型异常，请检查";
			}
			
			UnifiedParam unifiedParam = new UnifiedParam();
			
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			
			//冲正原业务
			customer = customerDao.findById(customer.getId());
			tagInfo = tagInfoDaoACMS.findById(tagInfo.getId());
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByTagid(tagInfo.getId());

			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(customer.getId());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setAfterAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(7);//发行删除
			serviceFlowRecord.setIsDoFlag("0");
			
			serviceFlowRecord.setOperID(tagInfo.getOperID());
			serviceFlowRecord.setPlaceID(tagInfo.getCorrectPlaceID());
			serviceFlowRecord.setOperName(tagInfo.getOperName());
			serviceFlowRecord.setOperNo(tagInfo.getOperNo());
			serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			if ("2".equals(tagInfo.getIssueType())) {// 删除 先提货后发行
				unifiedParam.setType("6");
				unifiedParam.setTagInfo(tagInfo);
				if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
					return "电子标签删除失败";
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");

			}else if ("1".equals(tagInfo.getIssueType()) || "4".equals(tagInfo.getIssueType()) || "3".equals(tagInfo.getIssueType())) {//删除 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				unifiedParam.setType("8");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setTagInfo(tagInfo);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					unifiedParam.setType("6");
					unifiedParam.setTagInfo(tagInfo);
					if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
						throw new ApplicationException();
						//return "电子标签删除失败";
					}
					serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"0");
				}else{
					return "电子标签删除失败";
				}
			}
			
			saveSetBind("0",carObuCardInfo.getVehicleID());//修改绑定标志
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(carObuCardInfo.getVehicleID());
			tagBusinessRecord.setOperTime(new Date());
			if(tagInfo.getChargeCost()!=null)tagBusinessRecord.setRealPrice(tagInfo.getChargeCost().multiply(new BigDecimal("-1")));
			//新增的字段
			tagBusinessRecord.setOperID(unifiedParam.getOperId());	 	
			tagBusinessRecord.setOperplaceID(unifiedParam.getPlaceId());
			tagBusinessRecord.setOperName(unifiedParam.getOperName());
			tagBusinessRecord.setOperNo(unifiedParam.getOperNo());
			tagBusinessRecord.setPlaceName(unifiedParam.getPlaceName());
			tagBusinessRecord.setPlaceNo(unifiedParam.getPlaceNo());

			
			tagBusinessRecord.setBusinessType("8");//发行删除
			//TODO
			tagBusinessRecord.setInstallmanID(25255L);//安装人员
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("删除发行");
			//if(tagInfo.getChargeCost()!=null)tagBusinessRecord.setRealPrice(tagInfo.getChargeCost().multiply(new BigDecimal("-1")));//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("302");//302电子标签删除
			
			//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
			serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(tagInfo.getOperID());
			serviceWater.setOperName(tagInfo.getOperName());
			serviceWater.setOperNo(tagInfo.getOperNo());
			serviceWater.setPlaceId(tagInfo.getIssueplaceID());
			serviceWater.setPlaceName(tagInfo.getPlaceName());
			serviceWater.setPlaceNo(tagInfo.getPlaceNo());
			serviceWater.setRemark("联营卡系统：电子标签删除");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签删除");
			
			
//			if (tagInfo.getTagNo().length()==16 && map!=null) {
//				//冲正成功后，更新营运接口调用登记记录的客服状态
//				interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
//				if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
//					interfaceRecord.setCsmsState("1");
//					interfaceRecordDao.update(interfaceRecord);
//					return "true";
//				}
//			}else {
//				return "true";
//			}
//			return "电子标签删除失败";
			return "true";
		} catch (ApplicationException e) {
			logger.error("电子标签删除失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	
	
	
	@Override
	public boolean updateTagWriteBackFlag(Long id,SysAdmin sysadmin,CusPointPoJo cusPointPoJo){
		try {
			TagInfo tagInfo = tagInfoDaoACMS.findById(id);
			
			String blackFlag=tagInfo.getBlackFlag();
			logger.info(blackFlag);
			UnifiedParam unifiedParam = new UnifiedParam();
			
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setType("10");
			
			if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
				return false;
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(tagInfo.getClientID());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setOperTime(new Date());
			//新增的字段
			tagBusinessRecord.setOperID(sysadmin.getId());	 	
			tagBusinessRecord.setOperplaceID(cusPointPoJo.getCusPoint());
			tagBusinessRecord.setOperName(sysadmin.getUserName());
			tagBusinessRecord.setOperNo(sysadmin.getStaffNo());
			tagBusinessRecord.setPlaceName(cusPointPoJo.getCusPointName());
			tagBusinessRecord.setPlaceNo(cusPointPoJo.getCusPointCode());

			
			tagBusinessRecord.setBusinessType(TagBussinessTypeEnum.tagWriteBack.getValue());//回写
			tagBusinessRecord.setCurrentTagState(tagInfo.getTagState());//正常
			tagBusinessRecord.setMemo("电子标签回写");
			tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			/*ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(tagInfo.getClientID());
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(8);//回写
			serviceFlowRecord.setIsDoFlag("0");
			
			serviceFlowRecord.setOperID(tagInfo.getOperID());
			serviceFlowRecord.setPlaceID(tagInfo.getCorrectPlaceID());
			serviceFlowRecord.setOperName(tagInfo.getOperName());
			serviceFlowRecord.setOperNo(tagInfo.getOperNo());
			serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");*/
			
			if(BlackFlagEnum.black.getValue().equals(blackFlag)){
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//标签回写与黑名单没有关系
				/*DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//
				saveDarkList(tagInfo,darkList,"1", "0");*/
				
			}
			
			Customer customer = customerDao.findById(tagBusinessRecord.getClientID());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(tagInfo.getTagNo());
			//serviceWater.setAmt(tagInfo.getCost());
			//serviceWater.setAulAmt(tagInfo.getChargeCost());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(tagBusinessRecord.getOperID());
			serviceWater.setOperNo(tagBusinessRecord.getOperNo());
			serviceWater.setOperName(tagBusinessRecord.getOperName());
			serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
			serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
			serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType(ServiceWaterSerType.tagWriteBack.getValue());
			serviceWater.setRemark("联营卡系统：电子标签回写");
			serviceWaterDao.save(serviceWater);
			
			return true;
		} catch (Exception e) {
			logger.error("电子回写失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 澳门通
	 * @Descriptioqn:
	 * @param customer
	 * @param id
	 * @param sysadmin
	 * @param cusPointPoJo
	 * @return
	 * @author lgm
	 * @date 2017年2月21日
	 */
	@Override
	public boolean updateTagWriteBackFlag(Customer customer,Long id,SysAdmin sysadmin,CusPointPoJo cusPointPoJo){
		try {
			TagInfo tagInfo = tagInfoDaoACMS.findById(id);
			
			String blackFlag=tagInfo.getBlackFlag();
			logger.info(blackFlag);
			UnifiedParam unifiedParam = new UnifiedParam();
			
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setType("10");
			
			if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
				return false;
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(tagInfo.getClientID());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setOperTime(new Date());
			//新增的字段
			tagBusinessRecord.setOperID(sysadmin.getId());	 	
			tagBusinessRecord.setOperplaceID(cusPointPoJo.getCusPoint());
			tagBusinessRecord.setOperName(sysadmin.getUserName());
			tagBusinessRecord.setOperNo(sysadmin.getStaffNo());
			tagBusinessRecord.setPlaceName(cusPointPoJo.getCusPointName());
			tagBusinessRecord.setPlaceNo(cusPointPoJo.getCusPointCode());

			
			tagBusinessRecord.setBusinessType("9");//回写
			tagBusinessRecord.setCurrentTagState(tagInfo.getTagState());//正常
			tagBusinessRecord.setMemo("电子标签回写");
			tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(tagInfo.getClientID());
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(8);//回写
			serviceFlowRecord.setIsDoFlag("0");
			
			serviceFlowRecord.setOperID(tagInfo.getOperID());
			serviceFlowRecord.setPlaceID(tagInfo.getCorrectPlaceID());
			serviceFlowRecord.setOperName(tagInfo.getOperName());
			serviceFlowRecord.setOperNo(tagInfo.getOperNo());
			serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
			
			if(BlackFlagEnum.black.getValue().equals(blackFlag)){
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 1, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//标签回写与黑名单没有关系
				/*DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//
				saveDarkList(tagInfo,darkList,"1", "0");*/
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
			serviceWater.setSerType("308");
			serviceWater.setRemark("电子标签回写");
			serviceWaterDao.save(serviceWater);
			
			return true;
		} catch (Exception e) {
			logger.error("电子回写失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	//清算接口   //原清算数据，没用了
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
				if(darkList.getCustomerId()!=null && !darkList.getCustomerId().equals(customer.getId())){
					darkList.setCustomerId(customer.getId());
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
				}
				darkList.setGencau(genCau);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("保存清算黑名单数据失败"+tagInfo.getTagNo());
			throw new ApplicationException("保存清算数黑名单据失败"+tagInfo.getTagNo());
		}
	}

	@Override
	public TagInfo findByCutomerId(Long customerId) {
		return tagInfoDaoACMS.findByCutomerId(customerId);
	}

	@Override
	public TagInfo findByTagNo(String tagNo) {
		// TODO Auto-generated method stub
		return tagInfoDaoACMS.findByTagNo(tagNo);
	}
	
	
	
	/**
	 * 客户流水更新前
	 * @param newMainAccountInfo
	 * @param serviceFlowRecord
	 * @return
	 */
	private ServiceFlowRecord getBeforeServiceFlowRecord(MainAccountInfo newMainAccountInfo,ServiceFlowRecord serviceFlowRecord){
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
		serviceFlowRecord.setId(Long.parseLong(seq.toString()));
		serviceFlowRecord.setClientID(newMainAccountInfo.getMainId());
		serviceFlowRecord.setServiceFlowNO(seq.toString());
		
		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));
		
		return serviceFlowRecord;
	}

	@Override
	public String deleteLianCard(TagInfo tagInfo, Customer customer) {
		try {
			
			UnifiedParam unifiedParam = new UnifiedParam();
			
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			
			//冲正原业务
			customer = customerDao.findById(customer.getId());
			tagInfo = tagInfoDaoACMS.findById(tagInfo.getId());
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByTagid(tagInfo.getId());

			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(customer.getId());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord = getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setOldCardTagNO(tagInfo.getTagNo());
			serviceFlowRecord.setCurrAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setAfterAvailableBalance(tagInfo.getChargeCost());
			serviceFlowRecord.setBeforeState(tagInfo.getTagState());
			serviceFlowRecord.setCurrState(tagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(7);//发行删除
			serviceFlowRecord.setIsDoFlag("0");
			
			serviceFlowRecord.setOperID(tagInfo.getOperID());
			serviceFlowRecord.setPlaceID(tagInfo.getCorrectPlaceID());
			serviceFlowRecord.setOperName(tagInfo.getOperName());
			serviceFlowRecord.setOperNo(tagInfo.getOperNo());
			serviceFlowRecord.setPlaceName(tagInfo.getPlaceName());
			serviceFlowRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			unifiedParam.setType("6");
			unifiedParam.setTagInfo(tagInfo);
			if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
				return "电子标签删除失败";
				//return "电子标签删除失败";
			}
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"0");
			
			saveSetBind("0",carObuCardInfo.getVehicleID());//修改绑定标志
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(carObuCardInfo.getVehicleID());
			tagBusinessRecord.setOperTime(new Date());
			//新增的字段
			tagBusinessRecord.setOperID(unifiedParam.getOperId());	 	
			tagBusinessRecord.setOperplaceID(unifiedParam.getPlaceId());
			tagBusinessRecord.setOperName(unifiedParam.getOperName());
			tagBusinessRecord.setOperNo(unifiedParam.getOperNo());
			tagBusinessRecord.setPlaceName(unifiedParam.getPlaceName());
			tagBusinessRecord.setPlaceNo(unifiedParam.getPlaceNo());

			
			tagBusinessRecord.setBusinessType("8");//发行删除
			tagBusinessRecord.setInstallmanID(25255L);//安装人员
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("删除发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			
			return "true";
		} catch (ApplicationException e) {
			logger.error("电子标签删除失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 添加电子标签发行记录验证 验证电子标签信息
	 */
	@Override
	public Map<String, Object> verifyTagInfoLianCard(String tagNo) {
		TagTakeDetail tagTakeDetail = tagTakeDetailDaoACMS.findByTagNo(tagNo);
		Map<String, Object> m = new HashMap<String, Object>();
		if (tagTakeDetail != null) {//先提货后发行
			m.put("success", false);
			m.put("message", "该电子标签已发行");
			return m;
		} else {
			// 提货发行一次完成
			TagInfo tagInfo = tagInfoDaoACMS.findByTagNo(tagNo);
			if (tagInfo != null) {
				// 该电子标签已发行
				m.put("success", false);
				m.put("message", "该电子标签已发行");
				return m;
			}
			m.put("success", true);
			tagInfo = new TagInfo();
			tagInfo.setIssueType(TagIssueTypeEnum.firstIss.getValue());
			tagInfo.setChargeCost(new BigDecimal("10"));
			m.put("tagInfo", tagInfo);
			return m;
		}

	}
	
	public  Map<String, Object> verifyTagInfoService(String tagNo){
		return null;
		
	}

	@Override
	public Map<String, Object> updateIsWriteObu(Long id) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		try {
			tagInfoDaoACMS.updateIsWriteObu(id);
		} catch (ApplicationException e) {
			map.put("result", "0");
			logger.error("电子标签更细写导入卡标志失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		map.put("result", "1");
		return map;
	}

	/**
	 * @Descriptioqn:
	 * @param tagNo
	 * @return
	 * @author lgm
	 * @date 2017年6月28日
	 */
	@Override
	public boolean checkTagBusinessRecord(String tagNo) {
		return tagBusinessRecordDaoACMS.checkDelete(tagNo);
	}
}


