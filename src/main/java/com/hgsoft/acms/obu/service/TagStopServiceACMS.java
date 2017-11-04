package com.hgsoft.acms.obu.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.acms.obu.dao.TagBusinessRecordDaoACMS;
import com.hgsoft.acms.obu.dao.TagInfoDaoACMS;
import com.hgsoft.acms.obu.dao.TagInfoHisDaoACMS;
import com.hgsoft.acms.obu.dao.TagStopDaoACMS;
import com.hgsoft.acms.obu.serviceInterface.ITagStopServiceACMS;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.serviceInterface.ICardHolderService;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.TagCannelACMSReceipt;
import com.hgsoft.other.vo.receiptContent.acms.TagStopACMSReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.ObuUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

import net.sf.json.JSONObject;

@Service
public class TagStopServiceACMS implements ITagStopServiceACMS {
	@Resource
	private TagInfoHisDaoACMS tagInfoHisDaoACMS;
	
	@Resource
	private TagInfoDaoACMS infoDaoACMS;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	
	@Resource
	private TagStopDaoACMS tagStopDaoACMS;
	
	@Resource
	private ObuUnifiedInterfaceService obuUnifiedInterfaceService;
	@Resource
	private SequenceUtil sequenceUtil;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private TagBusinessRecordDaoACMS tagBusinessRecordDaoACMS;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private ICardHolderService cardHolderService;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	
	private static Logger logger = Logger.getLogger(TagStopServiceACMS.class
			.getName());

	@Override
	public List tagStopList(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID) {
		List list = null;
		try {
			list = tagStopDaoACMS.findTagStops(tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerID);
		} catch (Exception e) {
			logger.error("查询电子标签挂起信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return list;
	}
	
	@Override
	public Pager tagStopListByPager(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType,
			String idCode, String endSixNo, Long customerID) {
		return tagStopDaoACMS.findTagStopsByPager(pager,tagNo, vehicleColor,
				vehiclePlate, idType, idCode, endSixNo,customerID);
	}
	
	@Override
	public Pager tagStopListByPagerForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType,
			String idCode, String endSixNo, Long customerID,String bankCode) {
		return tagStopDaoACMS.findTagStopsByPagerForAMMS(pager,tagNo, vehicleColor,
				vehiclePlate, idType, idCode, endSixNo,customerID,bankCode);
	}
	
	/**
	 * 澳门通用
	 */
	@Override
	public Pager tagStopListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer) {
		try {
			pager = tagStopDaoACMS.findTagStopsByPager(pager,tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerID,macaoCardCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签挂起信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}
	/**
	 * 快易通的
	 * @param pager
	 * @param tagInfo
	 * @param vehicleInfo
	 * @param sessionCustomer
	 * @param listCustomer
	 * @return
	 */
	public Pager tagStopListForLian(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer sessionCustomer,
			Customer listCustomer) {
		try {
			pager = tagStopDaoACMS.findForLian(pager, tagInfo, vehicleInfo, sessionCustomer, listCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签挂起信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}

	@Override
	public Map<String, Object> tagStopDetail(Long tagInfoId) {

		Map<String, Object> tagRecover = null;
		try {
			tagRecover = tagStopDaoACMS.findTagStopById(tagInfoId);
		} catch (Exception e) {
			logger.error("查询单个（电子标签挂起）的详细信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return tagRecover;
	}

	/**
	 * 挂起电子标签
	 */
	@Override
	public void saveTtopTagInfo(Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID,Long installmanID, String memo, Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord) {
		try {	
			//标记该标签状态改变之前的状态，给客户流水用
			TagInfo tagInfo = infoDaoACMS.findById(tagInfoId);
			
			//调用接口更新
			UnifiedParam unifiedParam = new UnifiedParam();
			
			
			// 2017/6/2修改.  
			//set这些修正人信息，为了传到obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)来可以获取并update数据库
			tagInfo.setCorrectTime(new Date());//更新时间
			tagInfo.setCorrectOperID(operID);
			tagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			tagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			tagInfo.setCorrectPlaceID(operplaceID);
			tagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			tagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			
			
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			//tagInfo.setTagState("2");
			tagInfo.setTagState(TagStateEnum.stop.getValue());
			carObuCardInfo.setVehicleID(vehicleID);
			VehicleInfo vehicleInfo=vehicleInfoDao.findById(vehicleID);
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("3");
			
			/*
			//更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为挂起（‘2’）。
			tagStopDao.updateTagInfo(tagInfoId);
			//更新车卡电子标签绑定表的电子标签为NULL.
			tagStopDao.updateCarObuCardInfo(vehicleID);*/
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){		
				// 记录电子标签业务操作记录，业务类型为“挂起”，当前状态为”挂起“（‘2’）
				/*tagStopDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
						vehicleID,null,null);*/
				
				//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagNo);
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID(operID);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operplaceID);
				tagBusinessRecord.setBusinessType("5");//停用
				tagBusinessRecord.setInstallmanID(installmanID);//安装人员
				//tagBusinessRecord.setCurrentTagState("2");//正常
				tagBusinessRecord.setCurrentTagState(TagStateEnum.stop.getValue());
				tagBusinessRecord.setMemo(memo);
				tagBusinessRecord.setFromID(tagInfoId);
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
				tagBusinessRecord.setBussinessid(vehicleInfo.getHisSeqId());//注销的时候业务记录表记录的是车辆的hisid
				tagBusinessRecordDaoACMS.save(tagBusinessRecord);
				
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//
				//saveDarkList(tagInfo,darkList,"6", "1");
				/*blackListService.saveOBUDisassemble(tagInfo.getObuSerial(), new Date()
						, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
						tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
						new Date());*/
				//2017-08-21  无标签挂起下的是5禁用？
				blackListService.saveOBUStopUse(tagInfo.getObuSerial(), new Date()
						, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
						tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
						new Date());
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
				cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
						cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
						tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签挂起");
				
				
				
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
				serviceWater.setSerType("307");//307电子标签挂起
				
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
				serviceWater.setRemark("香港联营卡系统：电子标签挂起");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);
				
			}
			
		} catch (ApplicationException e) {
			logger.error("电子标签挂起失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/*
	 * 区分有标签与无标签挂起，flag为0则是无标签，否则为有标签
	 * (non-Javadoc)
	 * @see com.hgsoft.obu.serviceInterface.ITagStopService#saveTagStopInfo(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, com.hgsoft.obu.entity.TagBusinessRecord)
	 */
	@Override
	public void saveTagStopInfo(String flag, Long tagInfoId, String tagNo, Long clientID, Long vehicleID,
			Long installmanID, String memo, Long operID, Long operplaceID, TagBusinessRecord tagBusinessRecord) {
		try {	
			//标记该标签状态改变之前的状态，给客户流水用
			TagInfo tagInfo = infoDaoACMS.findById(tagInfoId);
			
			//调用接口更新
			UnifiedParam unifiedParam = new UnifiedParam();
			
			
			// 2017/6/2修改.  
			//set这些修正人信息，为了传到obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)来可以获取并update数据库
			tagInfo.setCorrectTime(new Date());//更新时间
			tagInfo.setCorrectOperID(operID);
			tagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			tagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			tagInfo.setCorrectPlaceID(operplaceID);
			tagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			tagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			
			//0表示无标签挂起
			if("0".equals(flag)){
				tagInfo.setBlackFlag(BlackFlagEnum.black.getValue());
			}else{
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
			}
			
			
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			//tagInfo.setTagState("2");
			tagInfo.setTagState(TagStateEnum.stop.getValue());
			carObuCardInfo.setVehicleID(vehicleID);
			VehicleInfo vehicleInfo=vehicleInfoDao.findById(vehicleID);
			unifiedParam.setTagInfo(tagInfo);
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setType("3");
			
			
			if(obuUnifiedInterfaceService.updateOrSaveObu(unifiedParam)){		
				BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
				tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
				tagBusinessRecord.setClientID(clientID);
				tagBusinessRecord.setTagNo(tagNo);
				tagBusinessRecord.setVehicleID(vehicleID);
				tagBusinessRecord.setOperID(operID);
				tagBusinessRecord.setOperTime(new Date());
				tagBusinessRecord.setOperplaceID(operplaceID);
				tagBusinessRecord.setBusinessType("5");//停用
				tagBusinessRecord.setInstallmanID(installmanID);//安装人员
				//tagBusinessRecord.setCurrentTagState("2");//正常
				tagBusinessRecord.setCurrentTagState(TagStateEnum.stop.getValue());
				tagBusinessRecord.setMemo(memo);
				tagBusinessRecord.setFromID(tagInfoId);
				tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
				tagBusinessRecord.setBussinessid(vehicleInfo.getHisSeqId());//注销的时候业务记录表记录的是车辆的hisid
				tagBusinessRecordDaoACMS.save(tagBusinessRecord);
				
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, " ",
						null, tagInfo.getObuSerial(), null, 6, new Date(), 0, new Date());
				saveTollCardBlack(tagInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//
				//saveDarkList(tagInfo,darkList,"6", "1");
				if("0".equals(flag)){
					//无标签挂起才有下黑名单
					//2017-08-21  无标签挂起下的是5禁用？
					/*blackListService.saveOBUDisassemble(tagInfo.getObuSerial(), new Date()
							, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
							tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
							new Date());*/
					blackListService.saveOBUStopUse(tagInfo.getObuSerial(), new Date()
					, "2", tagBusinessRecord.getOperID(), tagBusinessRecord.getOperNo(), tagBusinessRecord.getOperName(),
					tagBusinessRecord.getOperplaceID(), tagBusinessRecord.getPlaceNo(), tagBusinessRecord.getPlaceName(), 
					new Date());
				}
				
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
				cardObuService.saveUserStateInfo(tagBusinessRecord.getOperTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
						cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
						tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签挂起");
				
				Customer customer = customerDao.findById(clientID);
				
				
				// 标签挂起回执
				Receipt receipt = new Receipt();
				receipt.setParentTypeCode("4");
				receipt.setTypeCode(TagBussinessTypeEnum.tagStop.getValue());
				receipt.setTypeChName(TagBussinessTypeEnum.tagStop.getName());
				receipt.setBusinessId(tagBusinessRecord.getId());
				receipt.setOperId(tagInfo.getOperID());
				receipt.setOperNo(tagInfo.getOperNo());
				receipt.setOperName(tagInfo.getOperName());
				receipt.setPlaceId(tagInfo.getIssueplaceID());
				receipt.setPlaceNo(tagInfo.getPlaceNo());
				receipt.setPlaceName(tagInfo.getPlaceName());
				receipt.setCreateTime(new Date());
				receipt.setOrgan(customer.getOrgan());
				
				TagStopACMSReceipt tagStopACMSReceipt = new TagStopACMSReceipt(); 
				tagStopACMSReceipt.setTitle("电子标签挂起回执");
				tagStopACMSReceipt.setHandleWay("凭资料办理");
				
				CarObuCardInfo ccobuInfo = carObuCardInfoDao.findByTagNo(tagInfo.getTagNo());
				if (ccobuInfo!=null&&ccobuInfo.getAccountCID()!=null) {
					CardHolder cardHolder = cardHolderService.findCardHolderByCardNo(accountCInfoDao.findById(ccobuInfo.getAccountCID()).getCardNo());
					tagStopACMSReceipt.setName(cardHolder.getName());
					tagStopACMSReceipt.setLinkTel(cardHolder.getPhoneNum());
					tagStopACMSReceipt.setMobileNum(cardHolder.getMobileNum());
					tagStopACMSReceipt.setLinkMan(cardHolder.getLinkMan());
					tagStopACMSReceipt.setLinkAddr(cardHolder.getLinkAddr());
				}
				
				tagStopACMSReceipt.setTagNo(tagInfo.getTagNo());
				receipt.setContent(JSONObject.fromObject(tagStopACMSReceipt).toString());
				receiptDao.saveReceipt(receipt);
				
				
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				
				serviceWater.setId(serviceWater_id);
				
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(tagInfo.getTagNo());
				serviceWater.setObuSerial(tagInfo.getObuSerial());
				if("0".equals(flag)){
					serviceWater.setSerType("309");//309无标签挂起
					serviceWater.setRemark("联营卡系统：无标签挂起");
				}else{
					serviceWater.setSerType("307");//307电子标签挂起
					serviceWater.setRemark("联营卡系统：电子标签挂起");
				}
				
				
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
//				serviceWater.setRemark("联营卡系统：电子标签挂起");
				serviceWater.setOperTime(new Date());
				
				serviceWaterDao.save(serviceWater);
				
			}
			
		} catch (ApplicationException e) {
			logger.error("电子标签挂起失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}
	

	@Override
	public CarObuCardInfo findByVehicleID(Long vehicleID) {
		return carObuCardInfoDao.findByVehicleID(vehicleID);
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

}
