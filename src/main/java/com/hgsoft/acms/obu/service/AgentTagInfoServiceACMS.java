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
import com.hgsoft.acms.obu.dao.TagBusinessRecordDaoACMS;
import com.hgsoft.acms.obu.dao.TagInfoDaoACMS;
import com.hgsoft.acms.obu.dao.TagTakeDetailDaoACMS;
import com.hgsoft.acms.obu.dao.TagTakeInfoDaoACMS;
import com.hgsoft.common.Enum.TagIssueTypeEnum;
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
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.obu.serviceInterface.IAgentTagInfoService;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 电子标签发行
 * 
 * @author qinfu 2016年1月22日14:36:27
 */
@Service
public class AgentTagInfoServiceACMS implements IAgentTagInfoService {

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
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;

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
			VehicleInfo vehicleInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success=false;
		String message="";
//-------------------------------		
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
				tagInfo.setMaintenanceTime(c.getTime());
//				tagInfo.setStartTime(c.getTime());
				c.add(Calendar.YEAR, 1);// 十年有效期，以后改成系统参数
//				tagInfo.setEndTime(c.getTime());
				
				//设置有效起始时间与有效结束时间
				Map initializedOrNotMap = (Map)maptemp.get("initializedOrNotMap");
				Date startDate = (Date)initializedOrNotMap.get("startDate");
				Date endDate = (Date)initializedOrNotMap.get("endDate");
				tagInfo.setStartTime(startDate);
				tagInfo.setEndTime(endDate);
				
				tagInfo.setTagState("1");// 正常
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
					success= false;
					message="发行失败";
				}
			} else {// 先提货后发行
				List list = tagTakeInfoDaoACMS.findByTagNo(tagInfo.getTagNo());
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
			tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setMemo("发行");
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			map.put("success", success);
			map.put("message", message);
			map.put("tagInfo",tagInfo);
			//清算接口
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
			
			customer = customerDao.findById(customer.getId());
			tagInfo = tagInfoDaoACMS.findById(tagInfo.getId());
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
			tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
			tagBusinessRecordDaoACMS.save(tagBusinessRecord);
			return "true";
		} catch (ApplicationException e) {
			logger.error("电子标签删除失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
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

}
