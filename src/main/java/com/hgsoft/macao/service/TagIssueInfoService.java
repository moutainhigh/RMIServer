package com.hgsoft.macao.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.TagIssueTypeEnum;
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
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.macao.dao.CardHolderInfoDao;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.TagIssueInfoDao;
import com.hgsoft.macao.dao.TagissuceInfoDao;
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.TagIssuceInfo;
import com.hgsoft.macao.serviceInterface.ITagIssueInfoService;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.service.ParamConfigService;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class TagIssueInfoService implements ITagIssueInfoService{
	private static Logger logger = Logger.getLogger(ParamConfigService.class.getName());
	
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private TagIssueInfoDao tagIssueInfoDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource 
	private PrepaidCDao prepaidCDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CardHolderInfoDao cardHolderInfoDao;
	@Resource
	private TagissuceInfoDao tagissuceInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	
	public Map<String,Object> getCustomerInfo(String bankAccountNumber,String idCardType,String idCardNumber){
		try {
			return tagIssueInfoDao.getCustomerInfo(bankAccountNumber,idCardType,idCardNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Pager getVehicleAndTag(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,String issuetime,MacaoCardCustomer macaoCardCustomer) {
		try {
			return tagIssueInfoDao.getVehicleAndTag(pager,tagInfo,vehicleInfo,issuetime,macaoCardCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> getVehicleAndTagById(String tagId) {
		try {
			return tagIssueInfoDao.getVehicleAndTagById(tagId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,
			String bankNo) {
		try {
			return tagIssueInfoDao.findByparamNoAndBankNo(paramNo,bankNo);
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}
	
	/**
	 * 添加电子标签发行记录验证 验证电子标签信息
	 */
	@Override
	public Map<String, Object> verifyTagInfo(String tagNo) {
		TagTakeDetail tagTakeDetail = tagIssueInfoDao.tagTakeDetailFindByTagNo(tagNo);
		Map<String, Object> m = new HashMap<String, Object>();
		if (tagTakeDetail != null) {//先提货后发行
			/*if ("1".equals(tagTakeDetail.getTagState())) {// 已发行
				m.put("success", false);
				m.put("message", "该电子标签已提货发行");
				return m;
			}
			if ("0".equals(tagTakeDetail.getTagState())) {// 未发行----先提货后发行
				if(tagInfoDao.findByTagNo(tagNo) != null){
					m.put("success", false);
					m.put("message", "该电子标签已发行");
					return m;
				}
				m.put("success", true);
				TagInfo tagInfo = new TagInfo();
				tagInfo.setSalesType(TagSalesTypeEnum.normalSale.getValue());// 销售方式 写死，以后又系统参数传入
				tagInfo.setChargeCost(new BigDecimal(0));// 实收成本 写死，以后又系统参数传入
				tagInfo.setIssueType(TagIssueTypeEnum.issue.getValue());
				m.put("tagInfo", tagInfo);
				return m;
			}*/
			m.put("success", false);
			m.put("message", "该电子标签已发行");
			return m;
		} else {
			// 提货发行一次完成
			TagInfo tagInfo = tagIssueInfoDao.tagInfoFindByTagNo(tagNo);
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
	
	@Override
	public List<Map<String, Object>> getAllVehByCusId(String idCardType,String idCardNumber){
		return tagIssueInfoDao.getAllVehByCusId(idCardType,idCardNumber);
	}
	
	/**
	 * 用于验证新增电子发行 车辆验证
	 */
	@Override
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,
			Long customerId) {
		return tagIssueInfoDao.findByVehicleInfoForMacao(vehicleInfo, customerId);
	}
	
	@Override
	// 新增发行卡校验
	public TagInfo findByVehicleInfoId(Long id) {
		return tagIssueInfoDao.findByVehicleInfoId(id);
	}
	
	@Override
	public boolean find(String license, String licenseColor) {
		// TODO Auto-generated method stub
		return tagIssueInfoDao.find(license, licenseColor);
	}
	
	@Override
	public VehicleInfo findById(Long id) {
		return tagIssueInfoDao.findById(id);
	}
	@Override
	public MacaoBankAccount findByVehicleId(Long id){
		try {
			return macaoBankAccountDao.findByVehicleId(id);
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException
				| InvocationTargetException e) {
		}
		return null;
	}
	
	/**
	 * 保存电子标签发行
	 */
	@Override
	public Map<String,Object> save(TagInfo tagInfo, Customer customer,VehicleInfo vehicleInfo,String bankAccountNumber,MacaoBankAccount macaoBankAccount) {
		
		
		
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success=false;
		String message="";
//-------------------------------		
		//判断车辆是否已经绑定了卡片
		Map<String,Object> carObuCardInfoMap = tagIssueInfoDao.getCarObuCardInfoByVehId(vehicleInfo.getId()+"");
		if(carObuCardInfoMap!=null && carObuCardInfoMap.get("accountCID")!=null){
			map.put("success", success);
			map.put("message", "当前车辆已经绑定卡片，请先解除卡片绑定");
			return map;
		}
		
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> maptemp = null;
		if ("1".equals(tagInfo.getIssueType())) {
			//判断电子标签是否已经初始化
			maptemp = inventoryServiceForAgent.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord, "issue", null, null, "", "2", "", null, tagInfo.getChargeCost(), "41");
			
//			maptemp = inventoryServiceForAgent.omsInterface(tagInfo.getTagNo(), "1", interfaceRecord,"issue",
//					tagInfo.getIssueplaceID(),tagInfo.getOperID(),tagInfo.getOperName(),"2","customPoint",productnullInfoId,tagInfo.getChargeCost(),"41");
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
			if ("1".equals(tagInfo.getIssueType())) {// 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				UnifiedParam unifiedParam = new UnifiedParam();
				unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
				unifiedParam.setOperId(tagInfo.getOperID());
				unifiedParam.setOperName(tagInfo.getOperName());
				unifiedParam.setOperNo(tagInfo.getOperNo());
				unifiedParam.setPlaceName(tagInfo.getPlaceName());
				unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
				tagInfo.setClientID(customer.getId());
				tagInfo.setIssuetime(c.getTime());
				tagInfo.setCorrectTime(c.getTime());
				
				//获取营运参数：维保周期（key=Maintenance time）
				Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
				logger.info(paramMap);
				if(paramMap!=null && "成功".equals((String)paramMap.get("message"))){
					c.add(Calendar.YEAR, Integer.parseInt((String)paramMap.get("value")));
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
				tagInfo.setMaintenanceTime(c.getTime());
				
				//设置有效起始时间与有效结束时间
				Map initializedOrNotMap = (Map)maptemp.get("initializedOrNotMap");
				Date startDate = (Date)initializedOrNotMap.get("startDate");
				Date endDate = (Date)initializedOrNotMap.get("endDate");
				tagInfo.setStartTime(startDate);
				tagInfo.setEndTime(endDate);
				
				tagInfo.setTagState("1");// 正常
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
					//tagIssueInfoDao.updateCarObuCardInfo(vehicleInfo.getId()+"",tagInfo.getId()+"");
					CardHolderInfo cardHolderInfo=new CardHolderInfo();
					BigDecimal SEQ_CSMSCARDHOLDERINFO_NO = sequenceUtil.getSequence("SEQ_CSMSCARDHOLDERINFO_NO");
					cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
					cardHolderInfo.setId(Long.parseLong(SEQ_CSMSCARDHOLDERINFO_NO.toString()));
					cardHolderInfo.setType("3");
					cardHolderInfo.setTypeId(tagInfo.getId());
					cardHolderInfoDao.save(cardHolderInfo);
					serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
					saveSetBind("1",vehicleInfo.getId());
					success= true;
				}else{
					success= false;
					message="发行失败";
				}
			} else {// 先提货后发行
				List list = tagIssueInfoDao.findByTagNo(tagInfo.getTagNo());
				if (list.size() > 0) {
					Date t = null;
					t = (Date) ((Map) list.get(0)).get("TAKEDATE");
					tagInfo.setMaintenanceTime(t);
				} else {
					// 查不到提货时间
				}
				tagInfo.setClientID(customer.getId());
				tagInfo.setIssuetime(c.getTime());
				
				tagInfo.setStartTime(c.getTime());
				c.add(Calendar.YEAR, 1);// 十年有效期，以后改成系统参数
				tagInfo.setEndTime(c.getTime());
				tagInfo.setTagState("1");// 正常
				BigDecimal SEQ_CSMSTaginfo_NO = sequenceUtil.getSequence("SEQ_CSMSTaginfo_NO");
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
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecord.setBussinessid(tagInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			map.put("success", success);
			map.put("message", message);
			map.put("tagInfo",tagInfo);
			
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
			serviceWater.setBankAccount(bankAccountNumber);
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
			serviceWater.setSerType("301");
			serviceWater.setRemark("电子标签发行");
			serviceWaterDao.save(serviceWater);
			
			//清算接口     //原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
			VehicleBussiness vehicleBussiness=new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setTagNo(tagInfo.getTagNo());
			//vehicleBussiness.setType("61");
			vehicleBussiness.setType(VehicleBussinessEnum.tagIssue.getValue());
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
			
			
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签发行");
			
			
			return map;
		} catch (ApplicationException e) {
			logger.error("电子标签发行失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
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
	public Map<String, Object> updateIsWriteObu(Long id) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		try {
			tagIssueInfoDao.updateIsWriteObu(id);
		} catch (ApplicationException e) {
			map.put("result", "0");
			logger.error("电子标签更细写导入卡标志失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		map.put("result", "1");
		return map;
	}
	
	@Override
	public TagInfo findByTagId(Long id) {
		return tagIssueInfoDao.findByTagId(id);
	}
	
	/**
	 * 删除电子标签发行记录信息
	 */
	@Override
	public String delete(TagInfo tagInfo, Customer customer,Customer customerForServiceWater) {
		try {
			
			int num = tagBusinessRecordDao.checkBussiness(tagInfo.getTagNo());
			if(num!=0){
				return "当前电子标签已办理其他业务，无法删除";
			}
			
			customer = customerDao.findById(customer.getId());
			tagInfo = tagIssueInfoDao.findByTagId(tagInfo.getId());
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByTagid(tagInfo.getId());
			MainAccountInfo mainAccountInfo = new MainAccountInfo();
			mainAccountInfo.setMainId(customer.getId());
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
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setPlaceId(tagInfo.getCorrectPlaceID());
			unifiedParam.setOperId(tagInfo.getOperID());
			unifiedParam.setOperName(tagInfo.getOperName());
			unifiedParam.setOperNo(tagInfo.getOperNo());
			unifiedParam.setPlaceName(tagInfo.getPlaceName());
			unifiedParam.setPlaceNo(tagInfo.getPlaceNo());
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByTagNo(tagInfo.getTagNo());
			if ("2".equals(tagInfo.getIssueType())) {// 删除 先提货后发行
				unifiedParam.setType("6");
				unifiedParam.setTagInfo(tagInfo);
				if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
					return "false";
				}
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");

			}else if ("1".equals(tagInfo.getIssueType())) {//删除 提货发行一次完成
				mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				unifiedParam.setType("8");
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setTagInfo(tagInfo);
				
				unifiedParam.setType("6");
				unifiedParam.setTagInfo(tagInfo);
				if(!obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){
					return "false";
				}
				//tagIssueInfoDao.updateCarObuCardInfoByTagId(tagInfo.getId()+"");
				tagIssueInfoDao.deleteById(tagInfo.getId());
				serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"0");
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
			//新增的字段
			tagBusinessRecord.setOperID(tagInfo.getOperID());	 	
			tagBusinessRecord.setOperplaceID(tagInfo.getCorrectOperID());
			tagBusinessRecord.setOperName(tagInfo.getOperName());
			tagBusinessRecord.setOperNo(tagInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("8");//发行删除
			tagBusinessRecord.setInstallmanID(25255L);//安装人员
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("删除发行");
			//tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			if(tagInfo.getChargeCost()!=null)tagBusinessRecord.setRealPrice(tagInfo.getChargeCost().multiply(new BigDecimal("-1")));
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			//获取持卡人信息
//			Map<String,Object> macaoCardCustomer = tagIssueInfoDao.getMacaoCardCustomerByTagNo(tagInfo.getTagNo());
			
			VehicleInfo vehicle = vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
			
			//新增车辆信息业务记录
			VehicleBussiness vehicleBussiness=new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
			vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
			vehicleBussiness.setTagNo(tagInfo.getTagNo());
			vehicleBussiness.setType(VehicleBussinessEnum.tagDelete.getValue());
			vehicleBussiness.setOperID(tagBusinessRecord.getOperID());
			vehicleBussiness.setPlaceID(tagBusinessRecord.getOperplaceID());
			//新增的字段
			vehicleBussiness.setOperName(tagBusinessRecord.getOperName());
			vehicleBussiness.setOperNo(tagBusinessRecord.getOperNo());
			vehicleBussiness.setPlaceName(tagBusinessRecord.getPlaceName());
			vehicleBussiness.setPlaceNo(tagBusinessRecord.getPlaceNo());
			
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("电子标签发行删除");
			vehicleBussinessDao.save(vehicleBussiness);
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setNewCardNo(tagInfo.getTagNo());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setAmt(tagInfo.getCost());
			serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(customerForServiceWater.getOperId());
			serviceWater.setOperNo(customerForServiceWater.getOperNo());
			serviceWater.setOperName(customerForServiceWater.getOperName());
			serviceWater.setPlaceId(customerForServiceWater.getPlaceId());
			serviceWater.setPlaceNo(customerForServiceWater.getPlaceNo());
			serviceWater.setPlaceName(customerForServiceWater.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType("302");
			serviceWater.setRemark("电子标签删除");
			serviceWaterDao.save(serviceWater);
			
			
			String cardNo = "";
			String cardType = "";
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
					cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签删除");
			
			return "true";
		} catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
			logger.error("电子标签删除失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		} 

	}
	
	@Override
	public TagInfo findByTagNo(String tagNo) {
		// TODO Auto-generated method stub
		return tagIssueInfoDao.tagInfoFindByTagNo(tagNo);
	}
	
	
	public void saveClear(Customer customer,Map<String,Object> maoCardCustomerMap,TagInfo tagInfo,VehicleInfo vehicleInfo){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());
			
			TagIssuceInfo tagIssuceInfo = new TagIssuceInfo();
			tagIssuceInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSTAGISSUCEINFO_NO"));
			tagIssuceInfo.setInterCode("91008");
			tagIssuceInfo.setCreateTime(createTime);
			tagIssuceInfo.setTagNo(tagInfo.getTagNo());
			tagIssuceInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			tagIssuceInfo.setVehicleColor(vehicleInfo.getVehicleColor());
			tagIssuceInfo.setModel(vehicleInfo.getModel());
			tagIssuceInfo.setVehicleType(vehicleInfo.getVehicleType());
			tagIssuceInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
			tagIssuceInfo.setVehicleLong(vehicleInfo.getVehicleLong());
			tagIssuceInfo.setVehicleWidth(vehicleInfo.getVehicleWidth());
			tagIssuceInfo.setVehicleHeight(vehicleInfo.getVehicleHeight());
			tagIssuceInfo.setVehicleAxles(vehicleInfo.getVehicleAxles());
			tagIssuceInfo.setVehicleWheels(vehicleInfo.getVehicleWheels());
			tagIssuceInfo.setCustomerName(customer.getOperName());
			tagIssuceInfo.setOwner(vehicleInfo.getOwner());
//			tagIssuceInfo.setZipCode("");
			tagIssuceInfo.setAddress(maoCardCustomerMap.get("address")+"");
			tagIssuceInfo.setCnName(maoCardCustomerMap.get("cnName")+"");
			tagIssuceInfo.setTel(maoCardCustomerMap.get("tel")+"");
			tagIssuceInfo.setShortMsg(maoCardCustomerMap.get("shortMsg")+"");
			tagIssuceInfo.setIdentificationCode(vehicleInfo.getIdentificationCode());
			tagIssuceInfo.setUsingNature(vehicleInfo.getUsingNature());
			tagIssuceInfo.setVehicleSpecificInformation(vehicleInfo.getVehicleSpecificInformation());
			tagIssuceInfo.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
//			tagIssuceInfo.setEndTime(new Date());
//			tagIssuceInfo.setTagChipNo("");
//			tagIssuceInfo.setSystemNo("");
//			tagIssuceInfo.setSerMemo("");
			tagIssuceInfo.setCost(tagInfo.getCost());
			tagIssuceInfo.setChargeCost(tagInfo.getChargeCost());
			tagIssuceInfo.setNscVehicleType(vehicleInfo.getNSCVehicleType());
//			tagIssuceInfo.setErrorCode("");
//			tagIssuceInfo.setServiceFlowNo("");
			tagissuceInfoDao.save(tagIssuceInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkDelete(String tagNo) {
		try {
			return tagIssueInfoDao.checkDelete(tagNo);
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
	 * @date 2017年1月20日
	 */
	@Override
	public Map<String, Object> findByVehicleInfo(VehicleInfo vehicleInfo) {
		try {
			if(vehicleInfo!=null)
				return tagIssueInfoDao.getMacaoCustomerInfoByVehicleInfo(vehicleInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Descriptioqn:
	 * @param macaoCardCustomer
	 * @return
	 * @author lgm
	 * @date 2017年1月20日
	 */
	@Override
	public Map<String, Object> findByMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer) {
		try {
			if(macaoCardCustomer!=null)
				return tagIssueInfoDao.getMacaoCustomerInfoByMacaoCardCustomer(macaoCardCustomer);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Descriptioqn:
	 * @param bankAccountNumber
	 * @return
	 * @author lgm
	 * @date 2017年1月20日
	 */
	@Override
	public boolean checkBankAccountNumber(String bankAccountNumber,String idCardType,String idCardNumber) {
		try {
			return tagIssueInfoDao.getMacaoBankAccount(bankAccountNumber,idCardType,idCardNumber);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Descriptioqn:获取银行账号列表
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 * @author lgm
	 * @date 2017年2月17日
	 */
	@Override
	public List<Map<String, Object>> getBankAccountNumberList(String idCardType, String idCardNumber) {
		return macaoBankAccountDao.getBankAccountNumberList(idCardType,idCardNumber);
	}
	
}
