package com.hgsoft.acms.other.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.account.serviceInterface.IMainAccountInfoService;
import com.hgsoft.account.serviceInterface.IRechargeInfoService;
import com.hgsoft.account.serviceInterface.IRefundInfoService;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.associateAcount.serviceInterface.IDarkListService;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.dao.ScaddSendDao;
import com.hgsoft.clearInterface.dao.ScreturnSendDao;
import com.hgsoft.clearInterface.dao.ScreturnreqDao;
import com.hgsoft.clearInterface.dao.ScreturnreqSendDao;
import com.hgsoft.clearInterface.dao.StoreCardRechargeDao;
import com.hgsoft.clearInterface.dao.TbScreturnSendDao;
import com.hgsoft.clearInterface.dao.UserStateInfoSendDao;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccChangeTypeEnum;
import com.hgsoft.common.Enum.AccountBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.AddRegDetailStateEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.CustomerStateEnum;
import com.hgsoft.common.Enum.DBACardFlowCardTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowEndSerTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowReadFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowSerTypeEnum;
import com.hgsoft.common.Enum.IdTypeACMSEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.InvoicePrintEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.PayMentTypeEnum;
import com.hgsoft.common.Enum.PrepaidCFristRechargeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTradeStateEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardStateEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.ScAddReqPaychannelEnum;
import com.hgsoft.common.Enum.ScAddSureConfirmTypeEnum;
import com.hgsoft.common.Enum.SerItemEnum;
import com.hgsoft.common.Enum.ServiceWaterFlowStateEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.SuitEnum;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.common.Enum.UsingNatureEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.common.Enum.VehicleUsingTypeEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.BillGetHisDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.ICarObuCardInfoService;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.customer.serviceInterface.IMaterialService;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.invoice.dao.AddBillDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.serviceInterface.ICardHolderService;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.acms.InitiativeStopReceipt;
import com.hgsoft.other.vo.receiptContent.acms.UnStopReceipt;
import com.hgsoft.other.vo.receiptContent.other.CardDisabledReceipt;
import com.hgsoft.other.vo.receiptContent.other.CardEnabledReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.AddRegReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardCannelReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardGetNewCardReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardImRegisterCorrectReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardImRegisterReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardIssuceReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardLossReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardMemberRechargeReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardPasswordModifyReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardPasswordResetReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardRechargeCorrectReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardReplaceMentReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardUnLossReceipt;
import com.hgsoft.prepaidC.dao.AddRegDao;
import com.hgsoft.prepaidC.dao.AddRegDetailDao;
import com.hgsoft.prepaidC.dao.AddRegDetailHisDao;
import com.hgsoft.prepaidC.dao.AddRegHisDao;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.dao.ElectronicPurseDao;
import com.hgsoft.prepaidC.dao.InvoiceChangeFlowDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.dao.ReturnFeeDao;
import com.hgsoft.prepaidC.dao.ScAddDao;
import com.hgsoft.prepaidC.entity.AddReg;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.prepaidC.entity.AddRegHis;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.ElectronicPurse;
import com.hgsoft.prepaidC.entity.ElectronicPurseHis;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.prepaidC.serviceInterface.IAddRegDetailService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.system.dao.SaleTypeDetailDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.serviceInterface.IServiceFundMonitorService;
import com.hgsoft.unifiedInterface.service.AgentPrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.ClearSequenceUtil;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 储值卡业务接口类
 * 
 * @author zxy 2016年1月25日14:08:36
 */
@Service
public class PrepaidCUnifiedInterfaceServiceACMS {

	private static final Logger logger = LoggerFactory.getLogger(PrepaidCUnifiedInterfaceServiceACMS.class);
	//private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private AddRegDao addRegDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private ElectronicPurseDao electronicPurseDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private ElectronicPurseDao ElectronicPurseDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private AddRegDetailDao addRegDetailDao;
	@Resource
	private ReturnFeeDao returnFeeDao;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	
	@Resource
	private InterfaceRecordDao interfaceRecordDao;

	@Resource
	private BillGetDao billGetDao;
	@Resource
	private BillGetHisDao billGetHisDao;
	/*@Resource
	private SCinfoDao sCinfoDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private IPrepaidCBussinessService prepaidCBussinessService;
	@Resource
	private IRechargeInfoService rechargeInfoService;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private IVehicleInfoService vehicleInfoService;
	@Resource
	private CardSecondIssuedService cardSecondIssuedService;
	
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	SequenceUtil sequenceUtil;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/
	@Resource
	private ScreturnreqSendDao screturnreqSendDao;
	@Resource
	private ScreturnreqDao screturnreqDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private IMainAccountInfoService mainAccountInfoService;
	
	@Resource
	private RechargeInfoDao rechargeInfoDao;
	
	@Resource
	private ClearSequenceUtil clearSequenceUtil;
	
	@Resource
	private AccountBussinessDao accountBussinessDao;
	
	@Resource
	private ScaddSendDao scaddSendDao;
	@Resource
	private ScreturnSendDao screturnSendDao;
	@Resource
	private TbScreturnSendDao tbScreturnSendDao;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private ScAddDao scAddDao;
	@Resource
	private IBlackListService blackListService;
	
	
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	
	@Resource
	private IPrepaidCService prepaidCService;
	
	@Resource
	private DarkListDao darkListDao;
	
	@Resource
	private InvoiceChangeFlowDao invoiceChangeFlowDao;
	@Resource
	private AddBillDao addBillDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private AddRegHisDao addRegHisDao;
	@Resource
	private AddRegDetailHisDao addRegDetailHisDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private IRefundInfoService refundInfoService;
	@Resource
	private StoreCardRechargeDao storeCardRechargeDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private UserStateInfoSendDao userStateInfoSendDao;
	@Resource
	private IMaterialService materialService;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private IAddRegDetailService addRegDetailService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private IDarkListService darkListService;
	@Resource
	private ICarObuCardInfoService carObuCardInfoService;
	@Resource
	private IServiceFundMonitorService serviceFundMonitorService;
	@Resource
	private AgentPrepaidCUnifiedInterfaceService agentPrepaidCUnifiedInterfaceService;
	@Resource
	private ICardHolderService cardHolderService;
	@Resource
	private IVehicleInfoService vehicleService;
	@Resource
	private SaleTypeDetailDao saleTypeDetailDao;
	@Resource
	private BlackListDao blackListDao;
	private IdTypeACMSEnum[] idTypeACMSEnums = IdTypeACMSEnum.values();
	private VehicleTypeEnum[] vehicleTypeEnums = VehicleTypeEnum.values();
	private VehicleColorEnum[] vehicleColorEnums = VehicleColorEnum.values();
	//车辆用户性质
	private VehicleUsingTypeEnum[] vehicleUsingTypeEnum = VehicleUsingTypeEnum.values();
	//使用性质
	private UsingNatureEnum[] usingNatureEnum = UsingNatureEnum.values();
	//国标收费车型
	private NSCVehicleTypeEnum[] nscVehicleTypeEnum = NSCVehicleTypeEnum.values();
	/**
	 * 旧卡锁定接口(储值卡业务记录，客服流水)
	 * @param prepaidC
	 * @param prepaidCBussiness
	 * @param serviceFlowRecord
	 * @param result
	 * @throws Exception
	 * @author zxy
	 */
	public String saveUnusable(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness,
			ServiceFlowRecord serviceFlowRecord,boolean result) {
		try {
			//System.out.println("进行了旧卡锁定操作？");
			//旧卡锁定不调用营运接口，在领取新卡的时候调用。（guanshaofeng修改）
			/*InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			if (prepaidC.getCardNo().length()==16) {
				map = inventoryService.omsInterface(prepaidC.getCardNo(), "2", interfaceRecord,"");
				boolean result = (boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
			}*/
			//回收原业务
			
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("4");
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 储值卡业务记录
				BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
				prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
				prepaidCBussinessDao.save(prepaidCBussiness);

				// 客服给清算		//原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				scinfo.setState("5");
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/
				if("1".equals(serviceFlowRecord.getIsNeedBlacklist())){
					// 清算系统用的黑色单		//原清算数据，没用了
					/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
							null, " ", null, 10, new Date(), 0, new Date());
					TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
							null, " ", null, 10, new Date(), 0, new Date());
					saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
					
					//
					//DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
					//
					//saveDarkList(prepaidC,darkList,"10", "1");
					//给铭鸿的清算数据
					blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
							, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
							prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
							new Date());
				}

				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}

				//清算接口
				if(result){//不需要下黑名单的
					//有卡锁定，写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), prepaidCBussiness.getCardno(), "22", userType);
				}else{
					//无卡锁定或有卡锁定失败，写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(PrepaidCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), prepaidCBussiness.getCardno(), "22", userType);
				}
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				//serviceWater.setNewCardNo(prepaidCBussiness.getCardno());
				serviceWater.setSerType("510");//储值卡旧卡锁定
				//serviceWater.setAmt(newPrepaidC.getCost_());//应收金额
				//serviceWater.setAulAmt(newPrepaidC.getRealCost());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				//serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark("自营客服系统：储值卡旧卡锁定");
				serviceWaterDao.save(serviceWater);
				
				//保存清算数据    用户状态数据（给铭鸿）
				/*UserStateInfoSend userStateInfoSend = new UserStateInfoSend();
				userStateInfoSend.setBusinessTime(prepaidCBussiness.getTradetime());
				userStateInfoSend.setCardCode(prepaidC.getCardNo());
				userStateInfoSend.setCardType(3L);
				Long id = sequenceUtil.getSequenceLong("SEQ_tbuserstateinfosend_NO");
				userStateInfoSend.setId(id);
				CarObuCardInfo obuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				VehicleInfo vehicleInfo = vehicleInfoDao.findById(obuCardInfo.getVehicleID());
				if(vehicleInfo!=null){
					userStateInfoSend.setLicense(vehicleInfo.getVehiclePlate());
					userStateInfoSend.setVehColor(Long.parseLong(vehicleInfo.getVehicleColor()));
					userStateInfoSend.setVehType(Long.parseLong(vehicleInfo.getVehicleType()));
				}
				if(obuCardInfo!=null && obuCardInfo.getTagID()!=null){
					TagInfo tagInfo = tagInfoDao.findById(obuCardInfo.getTagID());
					if(tagInfo!=null){
						userStateInfoSend.setObuId(tagInfo.getObuSerial());
						userStateInfoSend.setObuIssueTime(tagInfo.getStartTime());
						userStateInfoSend.setObuExpireTime(tagInfo.getEndTime());
					}
				}
				userStateInfoSend.setState(5L);
				userStateInfoSend.setUpdateTime(new Date());
				if(customer.getUserType().equals("8")){
					userStateInfoSend.setUserType(0L);
				}else{
					userStateInfoSend.setUserType(1L);
				}
				userStateInfoSendDao.save(userStateInfoSend);*/
				
				/*if (prepaidC.getCardNo().length()==16) {
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
				return "true";
			}
			return "旧卡锁定失败";
		} catch (ApplicationException e) {
			logger.error("旧卡锁定失败", e);
			throw new ApplicationException("旧卡锁定失败");
		}
	}

	/**
	 * 储值卡发行
	 * 
	 * @param
	 * @throws Exception
	 * @author wxm
	 */
	
	public String saveIssue(PrepaidC prepaidC, ElectronicPurse electronicPurse, CarObuCardInfo carObuCardInfo,
			PrepaidCBussiness prepaidCBussiness, MainAccountInfo mainAccountInfo, BillGet billGet) {
		/**
		 * saveGainCard 储值卡发行保存 1、扣减主账户金额 2、储值卡发行记录 3、电子钱包记录 4、车卡标签绑定关系
		 * 5、储值卡业务记录 6、客服流水 7、操作日志
		 */

		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryService.omsInterface(prepaidC.getCardNo(), "1", interfaceRecord,"issue",
					prepaidCBussiness.getPlaceid(),prepaidCBussiness.getOperid(),prepaidCBussiness.getOperName(),
					"1","customPoint",null,prepaidC.getRealCost(),"21","");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			prepaidC.setStartDate((Date)map.get("startDate"));
			prepaidC.setEndDate((Date)map.get("endDate"));
			
			Calendar cal = Calendar.getInstance();;
			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info("营运参数：{}", paramMap);
			if(paramMap!=null && "成功".equals((String) paramMap.get("message"))){
				cal.add(Calendar.YEAR, Integer.parseInt((String)paramMap.get("value")));
			}else if(paramMap!=null && !"成功".equals((String)paramMap.get("message"))){
				return "获取营运维保周期参数失败:"+(String)paramMap.get("message");
			}else{
				return "获取营运维保周期参数失败";
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}
			prepaidC.setMaintainTime(cal.getTime());
			
			
			
			//客服原发行操作
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
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
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
				
				//todo 储值卡电子钱包表废除掉了
				// 电子钱包记录
				BigDecimal electronic_purse_NO = sequenceUtil.getSequence("SEQ_CSMS_electronic_purse_NO");
				electronicPurse.setId(Long.valueOf(electronic_purse_NO.toString()));
				electronicPurse.setPrepaidc(Long.valueOf(PrePaidC_NO.toString()));
				ElectronicPurseDao.save(electronicPurse);

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

				// 储值卡业务记录
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
				vehicleBussiness.setType(VehicleBussinessEnum.prepaidCIssue.getValue());
				vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
				vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
				vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
				vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
				vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
				vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("车辆储值卡发行");
				
				vehicleBussinessDao.save(vehicleBussiness);

				// 客服给清算   	原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				scinfo.setState("0");
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/
				
				//清算接口		原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseList.setCardType(1);
				userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
				
				
				
				// 操作日志
				
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				serviceWater.setSerType("501");//储值卡发行
				serviceWater.setAmt(prepaidC.getCost_());//应收金额
				serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
				serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark("自营客服系统：储值卡发行");
				serviceWaterDao.save(serviceWater);

				//储值卡发行回执
				PreCardIssuceReceipt preCardIssuceReceipt = new PreCardIssuceReceipt();
				preCardIssuceReceipt.setTitle("储值卡发行回执");
				preCardIssuceReceipt.setHandleWay("凭密码办理");
				preCardIssuceReceipt.setPreCardNo(prepaidC.getCardNo());
				preCardIssuceReceipt.setPreCardFaceValue(NumberUtil.get2Decimal(prepaidC.getFaceValue().doubleValue()*0.01));
				preCardIssuceReceipt.setPreCardCost(NumberUtil.get2Decimal(prepaidC.getCost_().doubleValue()*0.01));
				preCardIssuceReceipt.setPreCardSaleFlag(this.saleTypeDetailDao.findByCode(prepaidC.getSaleFlag()).getName());	//销售方式
				preCardIssuceReceipt.setPreCardSumCost(NumberUtil.get2Decimal(prepaidC.getFaceValue().add(prepaidC.getCost_()).doubleValue()*0.01));
				preCardIssuceReceipt.setPreCardInvoicePrint(InvoicePrintEnum.getNameByValue(prepaidC.getInvoicePrint()));
				preCardIssuceReceipt.setPreCardSuit("1".equals(prepaidC.getBind())?"绑定":("0".equals(prepaidC.getBind())?"未绑定":""));
				preCardIssuceReceipt.setSerItem(this.getSerItemName(billGet.getSerItem()));
				preCardIssuceReceipt.setVehiclePlate(vehicle.getVehiclePlate());
				preCardIssuceReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
				preCardIssuceReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits()+"");
				preCardIssuceReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(vehicle.getNSCVehicleType()));
				preCardIssuceReceipt.setVehicleType(VehicleTypeEnum.getName(vehicle.getVehicleType()));
				preCardIssuceReceipt.setVehicleOwner(vehicle.getOwner());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(prepaidCBussiness.getState());
				receipt.setTypeChName(PrepaidCardBussinessTypeEnum.getNameByValue(prepaidCBussiness.getState()));
				this.saveReceipt(receipt,prepaidCBussiness,preCardIssuceReceipt,customer);

				//保存清算数据   卡片二发
				cardSecondIssuedService.savePrepaidCard(prepaidC);
//				CardSecondIssued cardSecondIssued = new CardSecondIssued();
//				cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//				cardSecondIssued.setCardType("22");
//				cardSecondIssued.setId(prepaidCBussiness.getId());
//				cardSecondIssued.setSdate(prepaidC.getSaleTime());
//				cardSecondIssued.setStatus(0);
//				cardSecondIssued.setUpdatetime(new Date());
//				cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
				
				
				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(prepaidC.getState()), 
						CardStateSendSerTypeEnum.scIssue.getValue(), prepaidC.getCardNo(), "22", userType);
				
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;
				//写给铭鸿的清算数据：用户状态信息
				cardObuService.saveUserStateInfo(prepaidCBussiness.getTradetime(), Integer.valueOf(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), prepaidC.getCardNo(), 
						"22", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
						null,obuSeq, obuIssueTime, obuExpireTime, "储值卡发行");
				
				//发行成功后，更新营运接口调用登记记录的客服状态
				interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
				if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
					interfaceRecord.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecord);
					return "true";
				}
			}
			return "储值卡发行失败，账户余额不足！";
		} catch (ApplicationException e) {
			logger.error("储值卡发行失败", e);
			throw new ApplicationException("储值卡发行失败");
		}
	}

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
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			if (prepaidC.getCardNo().length()==16) {
				BigDecimal price = new BigDecimal("0");
				if(prepaidC.getRealCost()!=null)prepaidC.getRealCost().multiply(new BigDecimal("-1"));
				map = inventoryService.omsInterface(prepaidC.getCardNo(), "3", interfaceRecord,"",
						prepaidCBussiness.getPlaceid(),prepaidCBussiness.getOperid(),prepaidCBussiness.getOperName(),"1","",null,prepaidCBussiness.getRealprice(),"216","");
				boolean result = (Boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
			}
			
			//冲正原业务
			/*
			 * //主账户信息 mainAccountInfoDao.update(mainAccountInfo);
			 */

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewPrepaidC(prepaidC);
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			unifiedParam.setType("12");
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				// 删除卡车对应关系
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
				
				
				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = null;
				if(vehicleInfo != null){
					vehicleBussiness = new VehicleBussiness();
					BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
					vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
					vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
					vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
					vehicleBussiness.setCardNo(prepaidC.getCardNo());//
					vehicleBussiness.setCardType(Constant.PREPAIDTYPE);//储值卡
					//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
					vehicleBussiness.setType(VehicleBussinessEnum.prepaidCDelete.getValue());
					vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
					vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
					vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
					vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
					vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
					vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
					vehicleBussiness.setCreateTime(new Date());
					vehicleBussiness.setMemo("储值卡发行删除");

					vehicleBussinessDao.save(vehicleBussiness);
				}
				
				
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				serviceWater.setSerType("516");//储值卡删除
				//serviceWater.setAmt(prepaidC.getCost_());//应收金额
				serviceWater.setAulAmt(prepaidCBussiness.getRealprice());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				if(vehicleBussiness != null) serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark("自营客服系统：储值卡删除");
				serviceWaterDao.save(serviceWater);
				
				
				//保存清算数据      给铭鸿

				cardSecondIssuedService.deletePrepaidCard(prepaidC,vehicleInfo);
//				CardSecondIssued cardSecondIssued = new CardSecondIssued();
//				cardSecondIssued.setCardCode(prepaidCBussiness.getCardno());
//				cardSecondIssued.setCardType("22");
//				cardSecondIssued.setId(prepaidCBussiness.getId());
//				cardSecondIssued.setSdate(prepaidC.getSaleTime());
//				cardSecondIssued.setStatus(1);
//				cardSecondIssued.setUpdatetime(new Date());
//				cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
				
				if (prepaidC.getCardNo().length()==16) {
					//冲正成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}else {
					return "true";
				}
			}
			return "删除（储值卡发行）失败";
		} catch (ApplicationException e) {
			logger.error("删除（储值卡发行）失败", e);// 记录删除日志
			throw new ApplicationException("删除（储值卡发行）失败");
		}
	}
	
	/**
	 * 作废
	 */
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness, AddRegDetail addRegDetail,
			MainAccountInfo mainAccountInfo, Integer type, List<ReturnFee> returnFeeList) {
		return false;
	}

	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness, AddRegDetail addRegDetail,
								MainAccountInfo mainAccountInfo, Integer type, List<ReturnFee> returnFeeList,RechargeInfo newRechargeInfo) {
		try {
			if (prepaidCBussiness.getRealprice() == null) {
				prepaidCBussiness.setRealprice(BigDecimal.ZERO);
			}
			if (prepaidCBussiness.getTransferSum() == null) {
				prepaidCBussiness.setTransferSum(BigDecimal.ZERO);
			}
			if (prepaidCBussiness.getReturnMoney() == null) {
				prepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
			}

			BigDecimal returnMoney = BigDecimal.ZERO;
			if (returnFeeList != null) {
				for (ReturnFee returnFee : returnFeeList) {
					returnMoney = returnMoney.add(returnFee.getReturnFee());
				}
			}
			//1,请求清算接口（参数：卡号和金额）锁定转移金额（成功走下一步，失败返回）
//-----------------------------------------------------------------------------------
			BigDecimal transfee = BigDecimal.ZERO;
			List<Map<String, Object>> cards = null;//prepaidCService.findCards(prepaidCBussiness.getCardno());
			if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
				cards = prepaidCService.findCards(prepaidCBussiness.getCardno());
				if (cards != null && !cards.isEmpty()) {
					Map<String, Object> map = cards.get(0);
					BigDecimal amt = (BigDecimal)map.get("CARDAMT");
					transfee = transfee.add(amt);
				/*for (Map<String, Object> map : cards) {
					BigDecimal amt = (BigDecimal)map.get("CARDAMT");
					transfee = transfee.add(amt);
				}*/
				}
			}

			//判断页面传过来的金额与查出金额是否一致
			if (prepaidCBussiness.getTransferSum().compareTo(transfee) != 0) {
				logger.warn("系统转移金额[{}]与申请的转移金额[{}]不一致", transfee, prepaidCBussiness.getTransferSum());
				return false;
			}
			if (prepaidCBussiness.getReturnMoney().compareTo(returnMoney) != 0) {
				logger.warn("系统回退金额[{}]与申请的回退金额[{}]不一致", returnMoney, prepaidCBussiness.getReturnMoney());
				return false;
			}
			BigDecimal totalMoney = prepaidCBussiness.getRealprice().add(transfee).add(returnMoney);
			//判断金额是否大于0，总充值金额是否大于0
			if (prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) < 0
					|| transfee.compareTo(BigDecimal.ZERO) < 0
					|| returnMoney.compareTo(BigDecimal.ZERO) < 0
					|| totalMoney.compareTo(BigDecimal.ZERO) <= 0
					|| totalMoney.add(prepaidCBussiness.getBeforebalance()).compareTo(new BigDecimal("3000000")) > 0) {
				logger.error("卡余额[{}],充值金额非法[{}]、[{}]、[{}]", prepaidCBussiness.getBeforebalance(), prepaidCBussiness.getRealprice(), transfee, returnMoney);
				return false;
			}
//-----------------------------------------------------------------------------------------------------
			//免验证快速充值需要添加缴款记录
			if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
				if (newRechargeInfo.getTakeBalance().compareTo(prepaidCBussiness.getRealprice()) != 0) {
					logger.warn("直充缴款单金额[{}]与申请写卡金额[{}]不一致", newRechargeInfo.getTakeBalance(), prepaidCBussiness.getRealprice());
					return false;
				}
				if(!saveRechargeInfo(newRechargeInfo, mainAccountInfo)){
					logger.warn("生成缴款信息失败");
					return false;//缴款失败
				}
			}

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
			unifiedParam.setType(AccChangeTypeEnum.preCardRecharge.getValue());
			unifiedParam.setRechargeType(type);
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());

			// 修改账户金额信息   注意如果更新数不对，要抛出异常，进行回滚
			if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				if (newRechargeInfo!=null) {
					throw new ApplicationException("更新账户金额异常");
				}
				return false;
			}

			//如果是充值登记充值，锁定充值登记记录
			if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) {
				if (addRegDetailService.updateFlagRechargeHalf(addRegDetail) != 1) {
					throw new ApplicationException("充值登记数据发生变更，请重新操作");
				}
			}
			prepaidCBussiness.setBusinessId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
			prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);

			if (StringUtils.isBlank(prepaidCBussiness.getCardno())) {
				logger.error("卡号为空[{}]", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号为空");
			}
			PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
			if (prepaidC == null) {
				logger.error("卡号[{}]未发行", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号未发行");
			} else if (!PrepaidCardStateEnum.nomal.getIndex().equals(prepaidC.getState())) {
				logger.error("卡号[{}]状态非正常状态[{}]", prepaidCBussiness.getCardno(), PrepaidCardStateEnum.getName(prepaidC.getState()));
				throw new ApplicationException("卡状态不是正常的");
			}

			Customer customer = customerService.getCustomerByPrepaidCardNo(prepaidCBussiness.getCardno());
			if (customer == null) {
				logger.error("卡号[{}]没有对应的正常用户", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号没有对应的正常用户");
			}
			MainAccountInfo tempAccount = mainAccountInfoService.findByMainId(customer.getId());
			if (tempAccount == null) {
				logger.error("卡号[{}]对应的用户[{}]没有对应的账户", prepaidCBussiness.getCardno(), customer.getId());
				throw new ApplicationException("卡号对应的用户没有对应的账户");
			} else if (!MainAccountInfo.State.Normal.getValue().equals(mainAccountInfo.getState())) {
				logger.error("卡号[{}]对应的用户[{}]对应的账户[{}]状态非正常[{}]", prepaidCBussiness.getCardno(), customer.getId(), MainAccountInfo.State.getNameByValue(mainAccountInfo.getState()));
				throw new ApplicationException("卡号[{}]对应的账户状态非正常");
			}
			/*DarkList darkList = darkListService.findByCardNo(prepaidC.getCardNo());
			if (darkList != null) {
				logger.error("卡号[{}]为黑名单那记录", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号为黑名单那记录");
			}*/

			logger.debug("账户冻结金额:{},写卡金额:{}", tempAccount.getFrozenBalance(), prepaidCBussiness.getRealprice());
			if(tempAccount.getFrozenBalance().compareTo(prepaidCBussiness.getRealprice()) < 0){
				throw new ApplicationException("账户冻结金额不足");
			}

			//锁定回退资金
			if (returnFeeList != null) {
				for (ReturnFee returnFee : returnFeeList) {
					if (returnFeeDao.updateLockState(prepaidCBussiness.getId(), prepaidCBussiness.getTradetime(), returnFee.getId()) != 1) {
						throw new ApplicationException("回退金额数据发生变更，请重新操作");
					}
				}
			}
			//锁定转移资金
			if (cards != null && !cards.isEmpty() && prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
				Map<String, Object> map = cards.get(0);
				int ret = dbasCardFlowDao.updateLockEndFlagById(prepaidCBussiness.getId(), (BigDecimal) map.get("CARDAMT"), prepaidCBussiness.getTradetime(), Long.parseLong(map.get("ID").toString()));
				if (ret != 1) {
					throw new ApplicationException("转移金额数据发生变更，请重新操作");
				}
				/*for (Map<String, Object> map : cards) {
					int ret = dbasCardFlowDao.updateLockEndFlagById(prepaidCBussiness.getId(), (BigDecimal)map.get("CARDAMT"), prepaidCBussiness.getTradetime(), Long.parseLong(map.get("ID").toString()));
					if (ret != 1) {
						throw new ApplicationException("转移金额数据发生变更，请重新操作");
					}
				}*/
			}
			//给清算数据
			scAddDao.saveScAddReqByBussiness(prepaidCBussiness,"01", ScAddReqPaychannelEnum.SELF_OPERATED.getValue());
			scAddDao.saveScAddSendByBussiness(prepaidCBussiness, "01");//清算数据

			scAddDao.saveScAddSureByBussiness(prepaidCBussiness, "01", ScAddReqPaychannelEnum.SELF_OPERATED.getValue());
			scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, "01");//清算数据

			if (!PrepaidCFristRechargeEnum.NOT_FIRST.getValue().equals(prepaidC.getFirstRecharge())) {
				prepaidCService.updateFirstRecharge(PrepaidCFristRechargeEnum.NOT_FIRST.getValue(), prepaidC.getId());
			}
			return true;
		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	private boolean isToday(Date date) {
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		return (day.format(new Date()).equals(day.format(date)));
	}

	public boolean saveReversal(MainAccountInfo mainAccountInfo, PrepaidCBussiness prepaidCBussiness,
			PrepaidCBussiness oldPrepaidCBussiness) {

		oldPrepaidCBussiness = prepaidCBussinessService.findById(oldPrepaidCBussiness.getId());

		if (oldPrepaidCBussiness == null
				|| PrepaidCardBussinessTypeEnum.airRecharge.getValue().equals(oldPrepaidCBussiness.getState())
				|| PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(oldPrepaidCBussiness.getState())
				|| PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(oldPrepaidCBussiness.getState())
				|| PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBussiness.getState())
				|| !PrepaidCardBussinessTradeStateEnum.success.getValue().equals(oldPrepaidCBussiness.getTradestate())
				|| !oldPrepaidCBussiness.getPlaceNo().equals(prepaidCBussiness.getPlaceNo())) {
			logger.warn("储值卡业务记录[{}]不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		} else if (oldPrepaidCBussiness.getBalance().compareTo(prepaidCBussiness.getBeforebalance()) != 0
				|| !oldPrepaidCBussiness.getOnlinetradeno().equals(prepaidCBussiness.getOnlinetradeno())
				|| !oldPrepaidCBussiness.getOfflinetradeno().equals(prepaidCBussiness.getOfflinetradeno())) {
			logger.warn("卡内信息联机[{}]、脱机[{}]、金额[{}]与储值卡业务记录[{}]不一致，不可以冲正",
					prepaidCBussiness.getOnlinetradeno(), prepaidCBussiness.getOfflinetradeno(),
					prepaidCBussiness.getBeforebalance(),oldPrepaidCBussiness.getId());
			return false;
		} else if (prepaidCBussiness.getRealprice().compareTo(oldPrepaidCBussiness.getRealprice()) != 0) {
			logger.warn("前端冲正金额[{}]与系统可冲正金额[{}]不一致，不可以冲正",
					prepaidCBussiness.getRealprice(), oldPrepaidCBussiness.getRealprice());
			return false;
		} else if (oldPrepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) == 0) {
			logger.warn("储值卡业务记录[{}]可冲正金额为0，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		} else if (!isToday(oldPrepaidCBussiness.getTradetime())) {
			logger.warn("储值卡业务记录[{}]不是当天，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		}

		if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(oldPrepaidCBussiness.getState())) {
			prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue());
		} else {
			prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue());
		}
		prepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
		prepaidCBussiness.setTransferSum(BigDecimal.ZERO);
		prepaidCBussiness.setTradestate(PrepaidCardBussinessTradeStateEnum.save.getValue());
		prepaidCBussiness.setCardno(oldPrepaidCBussiness.getCardno());

		mainAccountInfo = mainAccountInfoService.findByMainId(oldPrepaidCBussiness.getUserid());
		if (mainAccountInfo == null) {
			logger.warn("储值卡业务记录[{}]不存在主账户，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		} else if (!"1".equals(mainAccountInfo.getState())) {
			logger.warn("储值卡业务记录[{}]对应的主账户不是正常状态，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		}

		/**
		 * 1、增加储值卡 业务记录 2、修改充值业务记录发票打印状态 3、增加客服流水 4、增加总账户可用余额
		 */
		try {
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
			unifiedParam.setType(AccChangeTypeEnum.preCardRechargeCorrect.getValue());
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());

			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				PrepaidCBussiness tempBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNo(oldPrepaidCBussiness.getCardno());
				if (!tempBusiness.getId().equals(oldPrepaidCBussiness.getId())) {
					throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]不是最新的充值记录，不可以冲正");
				} else if (!PrepaidCardBussinessTradeStateEnum.success.getValue().equals(tempBusiness.getTradestate())) {
					throw new ApplicationException("储值卡业务记录[" + tempBusiness.getId() + "]交易状态不是充值成功，不可以冲正");
				} else if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(tempBusiness.getState())
						|| PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(tempBusiness.getState())
						|| PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(tempBusiness.getState())
						|| PrepaidCardBussinessTypeEnum.airRecharge.getValue().equals(tempBusiness.getState())
						|| "1".equals(tempBusiness.getIsDaySet())) {
					throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]类型不是可以冲正的");
				}

				if (tempBusiness.getRealprice().compareTo(oldPrepaidCBussiness.getRealprice()) != 0) {
					throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]发生变更，不可以冲正");
				}

				PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(tempBusiness.getCardno());
				if (prepaidC == null) {
					throw new ApplicationException("储值卡["+tempBusiness.getCardno()+"]不存在，不可以冲正");
				} else if (!PrepaidCardStateEnum.nomal.getIndex().equals(prepaidC.getState())) {
					logger.error("卡号[{}]状态非正常状态[{}]", prepaidCBussiness.getCardno(), PrepaidCardStateEnum.getName(prepaidC.getState()));
					throw new ApplicationException("卡状态不是正常的");
				}

				Customer customer = customerService.findById(prepaidC.getCustomerID());
				if (customer == null || !CustomerStateEnum.normal.getValue().equals(customer.getState())) {
					throw new ApplicationException("储值卡["+oldPrepaidCBussiness.getCardno()+"]不存在正常用户，不可以冲正");
				}
				/*DarkList darkList = darkListService.findByCardNo(prepaidC.getCardNo());
				if(darkList!=null){
					throw new ApplicationException("储值卡["+tempBusiness.getCardno()+"]在黑名单，不可以冲正");
				}*/

				if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(tempBusiness.getState())) {
					RechargeInfo rechargeInfoForId = rechargeInfoService.findByPrepaidCBussinessId(tempBusiness.getId()); // 不为空的时候，免登陆快速充值
					if (rechargeInfoForId == null) {
						logger.error("储值卡业务记录[{}]不存在对应的主账户缴款记录", tempBusiness.getId());
						throw new ApplicationException("储值卡业务记录不存在对应的主账户缴款记录");
					} else if ("1".equals(rechargeInfoForId.getIsCorrect())) {
						logger.error("储值卡业务记录[{}]对应的主账户缴款记录[{}]已经冲正", tempBusiness.getId(), rechargeInfoForId.getId());
						throw new ApplicationException("主账户缴款记录已经冲正");
					} else if ("1".equals(rechargeInfoForId.getIsDaySet())) {
						logger.error("储值卡业务记录[{}]对应的主账户缴款记录[{}]已经日结", tempBusiness.getId(), rechargeInfoForId.getId());
						throw new ApplicationException("主账户缴款记录已经日结");
					}
				}

				/* 储值卡业务记录 */
				prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());

				prepaidCBussiness.setBusinessId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
				prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);

				if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(tempBusiness.getState())) {
					if (rechargeInfoDao.updatePrepaidCBussinessId(oldPrepaidCBussiness.getId(), prepaidCBussiness.getId()) != 1) {
						throw new ApplicationException("储值卡业务记录["+tempBusiness.getId()+"]没有对应的主账户缴款记录");
					}
				}
				//给清算数据
				scAddDao.saveScAddReqByBussiness(prepaidCBussiness,"02", ScAddReqPaychannelEnum.SELF_OPERATED.getValue());
				scAddDao.saveScAddSendByBussiness(prepaidCBussiness,"02");
				scAddDao.saveScAddSureByBussiness(prepaidCBussiness, "02", ScAddReqPaychannelEnum.SELF_OPERATED.getValue());
				scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, "02");

				return true;
			}
			return false;
		} catch (Exception e) {
			//logger.error("充值冲正失败", e);
			throw new ApplicationException("充值冲正失败", e);
		}
	}

	public void updateRecharge(PrepaidCBussiness prepaidCBussiness, AddRegDetail addRegDetail,
							   MainAccountInfo mainAccountInfo, List<ReturnFee> returnFeeList) {
		/**
		 * 1、修改储值卡业务记录状态 2、修改快速充值登记记录状态 3、更改总帐户信息冻结金额
		 */
		try {
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
			unifiedParam.setType(AccChangeTypeEnum.preCardRechargeSuccess.getValue());
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			//为了避免多次更新，通过账户更新或快速登记更新锁记录，并且更新成功的基础上，判断业务记录是否已经确认成功，如果是，回滚
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {

				if (StringUtils.isNotBlank(prepaidCBussiness.getTac())) {
					prepaidCBussiness.setTac(agentPrepaidCUnifiedInterfaceService.tenTacToHexTac(prepaidCBussiness.getTac()));
				}

				//完成储值卡业务记录
				if(prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
					throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]数据发生变化");
				}
				//充值登记记录更新
				if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) { //充值登记
					if (addRegDetailService.updateFlagRechargeSuccess(prepaidCBussiness.getId()) != 1) {
						throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]对应的充值登记数据发生变化");
					}
				}

				Date addSureTime = new Date(); //临时存储充值确认时间，后边使用

				//完成回退金额
				if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
					returnFeeDao.updateRechargeSuccessState(addSureTime, prepaidCBussiness.getId());
				}
				//完成转移金额
				if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
					dbasCardFlowDao.updateRechargedEndFlagByEndServiceId(prepaidCBussiness.getId());
				}
//----------------------------------------------------------------------------------------------------
				//添加充值账单
				PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
				agentPrepaidCUnifiedInterfaceService.saveAddBill(prepaidC, prepaidCBussiness, StringUtils.isNotBlank(prepaidCBussiness.getReason()) && prepaidCBussiness.getReason().equals("1"));

				//给清算数据，插入后半部分数据
				ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(prepaidCBussiness, "01", addSureTime, "3", ScAddSureConfirmTypeEnum.normal.getValue());
				//写给原清算的数据
				scAddDao.updateScAddSureSendByBussiness(prepaidCBussiness, "01", addSureTime, "3");

				//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("01", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());

				//给清算数据    充值金额		给铭鸿
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
				if(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())){
					//人工充值
					serviceWater.setSerType(ServiceWaterSerType.manualRecharge.getValue());//储值卡人工充值
					serviceWater.setRemark(ServiceWaterSerType.manualRecharge.getName());
				}else if(PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())){
					//充值登记充值
					serviceWater.setSerType(ServiceWaterSerType.addRegRecharge.getValue());//储值卡充值登记充值
					serviceWater.setRemark(ServiceWaterSerType.addRegRecharge.getName());
				}else if(PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())){
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
				serviceWater.setOperTime(addSureTime);

				serviceWaterDao.save(serviceWater);

				//回执保存
				Receipt receipt = new Receipt();
				if(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())){
					//储值卡充值回执(人工)
					PreCardMemberRechargeReceipt preCardMemberRechargeReceipt = new PreCardMemberRechargeReceipt();
					preCardMemberRechargeReceipt.setTitle("储值卡充值回执");
					preCardMemberRechargeReceipt.setHandleWay("凭密码办理");
					preCardMemberRechargeReceipt.setPreCardNo(prepaidCBussiness.getCardno());
					preCardMemberRechargeReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(prepaidCBussiness.getBeforebalance().doubleValue()*0.01));
					preCardMemberRechargeReceipt.setPreCardRealPrice(NumberUtil.get2Decimal(prepaidCBussiness.getRealprice().doubleValue()*0.01));
					preCardMemberRechargeReceipt.setMainCountPreferentialBalance(NumberUtil.get2Decimal(prepaidCBussiness.getReturnMoney().doubleValue()*0.01));
					preCardMemberRechargeReceipt.setPreCardTransferSum(NumberUtil.get2Decimal(prepaidCBussiness.getTransferSum().doubleValue()*0.01));
					preCardMemberRechargeReceipt.setPreCardBalance(NumberUtil.get2Decimal(prepaidCBussiness.getBalance().doubleValue()*0.01));
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getName());
					receipt.setBusinessId(prepaidCBussiness.getId());
					this.saveReceipt(receipt,prepaidCBussiness,preCardMemberRechargeReceipt,customer);
				}else if(PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())){
					//储值卡充值回执(直充)
					RechargeInfo rechargeInfo = this.rechargeInfoDao.findByPrepaidCBussinessId(prepaidCBussiness.getId());	//缴款记录
					PreCardImRegisterReceipt preCardImRegisterReceipt = new PreCardImRegisterReceipt();
					preCardImRegisterReceipt.setTitle("储值卡充值回执");
					preCardImRegisterReceipt.setHandleWay("");
					preCardImRegisterReceipt.setPreCardNo(prepaidCBussiness.getCardno());
					preCardImRegisterReceipt.setRechargePayMentType(PayMentTypeEnum.getName(rechargeInfo.getPayMentType()));
					preCardImRegisterReceipt.setRechargePayMentNo(rechargeInfo.getPayMentNo());
					preCardImRegisterReceipt.setRechargePosId(rechargeInfo.getPosId()+"");
					preCardImRegisterReceipt.setPreCardNo(prepaidCBussiness.getCardno());
					preCardImRegisterReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(prepaidCBussiness.getBeforebalance().doubleValue()*0.01));
					preCardImRegisterReceipt.setPreCardRealPrice(NumberUtil.get2Decimal(prepaidCBussiness.getRealprice().doubleValue()*0.01));
					preCardImRegisterReceipt.setMainCountPreferentialBalance(NumberUtil.get2Decimal(prepaidCBussiness.getReturnMoney().doubleValue()*0.01));
					preCardImRegisterReceipt.setPreCardTransferSum(NumberUtil.get2Decimal(prepaidCBussiness.getTransferSum().doubleValue()*0.01));
					preCardImRegisterReceipt.setPreCardBalance(NumberUtil.get2Decimal(prepaidCBussiness.getBalance().doubleValue()*0.01));
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardImRegister.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardImRegister.getName());
					receipt.setBusinessId(prepaidCBussiness.getId());
					this.saveReceipt(receipt,prepaidCBussiness,preCardImRegisterReceipt,customer);
				}
			}
		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	public void updateReversal(PrepaidCBussiness prepaidCBussiness, MainAccountInfo mainAccountInfo,
			List<ReturnFee> returnFeeList,Long oldBussinessID) {
		/**
		 * 1、更改总帐户信息冻结金额2、修改储值卡业务记录状态 3、增加客服流水4、操作日志
		 */

		try {
			PrepaidCBussiness oldPrepaidCBusiness = prepaidCBussinessService.findById(oldBussinessID);
			if (oldPrepaidCBusiness == null) {
				logger.error("储值卡业务记录[{}]不存在", oldBussinessID);
				throw new ApplicationException("业务记录不存在");
			} else if (!PrepaidCardBussinessTradeStateEnum.save.getValue().equals(oldPrepaidCBusiness.getTradestate())) {
				logger.error("储值卡业务记录[{}]交易状态不对", oldBussinessID);
				throw new ApplicationException("储值卡业务记录交易状态不对");
			}

			mainAccountInfo = mainAccountInfoService.findByMainId(oldPrepaidCBusiness.getUserid());
			if (mainAccountInfo == null) {
				logger.warn("储值卡业务记录[{}]不存在主账户，不可以冲正", oldBussinessID);
				throw new ApplicationException("不存在主账户");
			}
			oldPrepaidCBusiness.setMac(prepaidCBussiness.getMac());
			oldPrepaidCBusiness.setTac(prepaidCBussiness.getTac());
			oldPrepaidCBusiness.setTermcode(prepaidCBussiness.getTermcode());
			oldPrepaidCBusiness.setTermtradeno(prepaidCBussiness.getTermtradeno());
			oldPrepaidCBusiness.setOnlinetradeno(prepaidCBussiness.getOnlinetradeno());
			oldPrepaidCBusiness.setOfflinetradeno(prepaidCBussiness.getOfflinetradeno());
			oldPrepaidCBusiness.setBalance(prepaidCBussiness.getBalance());

			//prepaidCBussiness = oldPrepaidCBusiness;

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setPrepaidCBussiness(oldPrepaidCBusiness);
			unifiedParam.setPlaceId(oldPrepaidCBusiness.getPlaceid());
			unifiedParam.setOperId(oldPrepaidCBusiness.getOperid());
			unifiedParam.setOperName(oldPrepaidCBusiness.getOperName());
			unifiedParam.setOperNo(oldPrepaidCBusiness.getOperNo());
			unifiedParam.setPlaceName(oldPrepaidCBusiness.getPlaceName());
			unifiedParam.setPlaceNo(oldPrepaidCBusiness.getPlaceNo());
			unifiedParam.setType(AccChangeTypeEnum.preCardRechargeCorrectSuccess.getValue());

			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				if (prepaidCBussinessService.updateTradeStateSuccess(oldPrepaidCBusiness) != 1) {
					logger.error("储值卡业务记录[{}]更新失败", oldBussinessID);
					throw new ApplicationException("业务记录数据发生变化");
				}

				Date reversalSureTime = new Date(); //临时存储充值确认时间，后边使用

				//PrepaidCBussiness oldPrepaidCBussiness=prepaidCBussinessDao.findById(oldBussinessID);
				if(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBusiness.getState())){//免登陆充值冲正
					RechargeInfo rechargeInfoForId = rechargeInfoService.findByPrepaidCBussinessId(oldBussinessID); // 不为空的时候，免登陆快速充值
					if (rechargeInfoForId == null) {
						logger.error("储值卡业务记录[{}]不存在对应的主账户缴款记录", oldBussinessID);
						throw new ApplicationException("储值卡业务记录不存在对应的主账户缴款记录");
					} else if ("1".equals(rechargeInfoForId.getIsCorrect())) {
						logger.error("储值卡业务记录[{}]对应的主账户缴款记录[{}]已经冲正", oldBussinessID, rechargeInfoForId.getId());
						throw new ApplicationException("主账户缴款记录已经冲正");
					}

					//将直充的缴款记录对应的业务id更新为原先的充值记录
					PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
					if (rechargeInfoDao.updatePrepaidCBussinessId(oldPrepaidCBusiness.getId(), preRechargeBusiness.getId()) != 1) {
						throw new ApplicationException("储值卡业务记录["+oldPrepaidCBusiness.getId()+"]没有对应的主账户缴款记录");
					}

					mainAccountInfo = mainAccountInfoService.findByMainId(rechargeInfoForId.getMainId());
					// 冲正
					rechargeInfoForId.setTransactionType("2");
					rechargeInfoForId.setCorrectId(rechargeInfoForId.getId()); // 冲正id
					rechargeInfoForId.setOperTime(reversalSureTime);
					rechargeInfoService.save(AccChangeTypeEnum.correct.getValue(), mainAccountInfo, rechargeInfoForId, null, AccChangeTypeEnum.imCorrect.getValue(), null);

				}

				//给清算数据，插入后半部分数据
				ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBusiness, "02", reversalSureTime, "4", ScAddSureConfirmTypeEnum.normal.getValue());
				//写给清算的数据
				scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBusiness, "02", reversalSureTime, "4");

				//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("02", oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
				//给清算数据	给铭鸿
				agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge4Reversal(oldPrepaidCBusiness, scAddSure);

				//TODO 发票处理

				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				Customer customer = customerService.findById(oldPrepaidCBusiness.getUserid());
				if (customer != null) {
					serviceWater.setCustomerId(customer.getId());
					serviceWater.setUserNo(customer.getUserNo());
					serviceWater.setUserName(customer.getOrgan());
				}
				serviceWater.setCardNo(oldPrepaidCBusiness.getCardno());

				PrepaidCBussiness preRechargeBusiness = new PrepaidCBussiness();	//要冲正的充值业务记录
				if(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(oldPrepaidCBusiness.getState())){
					//储值卡充值冲正
					serviceWater.setSerType(ServiceWaterSerType.manualRechargeReversal.getValue());//储值卡充值冲正
					serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
					serviceWater.setRemark(ServiceWaterSerType.manualRechargeReversal.getName());
					//对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
					preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
					if (preRechargeBusiness != null) {
						ServiceWater oldServiceWater = new ServiceWater();
						oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
						oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
						serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
					}
				}else if(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBusiness.getState())){
					//储值卡直充充值冲正
					serviceWater.setSerType(ServiceWaterSerType.noLoginRechargeReversal.getValue());//储值卡直充充值冲正
					serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
					serviceWater.setRemark(ServiceWaterSerType.noLoginRechargeReversal.getName());

					//对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
					preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
					if (preRechargeBusiness != null) {
						ServiceWater oldServiceWater = new ServiceWater();
						oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
						oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
						serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
					}
				}
				serviceWater.setAmt(oldPrepaidCBusiness.getRealprice());//应收金额
				serviceWater.setAulAmt(oldPrepaidCBusiness.getRealprice());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				serviceWater.setPrepaidCBussinessId(oldPrepaidCBusiness.getId());

				serviceWater.setOperId(oldPrepaidCBusiness.getOperid());
				serviceWater.setOperNo(oldPrepaidCBusiness.getOperNo());
				serviceWater.setOperName(oldPrepaidCBusiness.getOperName());
				serviceWater.setPlaceId(oldPrepaidCBusiness.getPlaceid());
				serviceWater.setPlaceNo(oldPrepaidCBusiness.getPlaceNo());
				serviceWater.setPlaceName(oldPrepaidCBusiness.getPlaceName());
				serviceWater.setOperTime(reversalSureTime);

				serviceWaterDao.save(serviceWater);

				//回执保存
				Receipt receipt = new Receipt();
				if(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(oldPrepaidCBusiness.getState())){
					//储值卡充值冲正回执(人工充值冲正)
					PreCardRechargeCorrectReceipt preCardRechargeCorrectReceipt = new PreCardRechargeCorrectReceipt();
					preCardRechargeCorrectReceipt.setTitle("储值卡充值冲正回执");
					preCardRechargeCorrectReceipt.setHandleWay("凭密码办理");
					preCardRechargeCorrectReceipt.setRechargeReciptNo(this.receiptDao.findByBusIdAndPTC(preRechargeBusiness.getId(),ReceiptParentTypeCodeEnum.prepaidC.getValue()).getReceiptNo());
					preCardRechargeCorrectReceipt.setPreCardNo(oldPrepaidCBusiness.getCardno());
					preCardRechargeCorrectReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBeforebalance().doubleValue()*0.01));
					preCardRechargeCorrectReceipt.setCorrectMoney(NumberUtil.get2Decimal(oldPrepaidCBusiness.getRealprice().doubleValue()*0.01).substring(1));
					preCardRechargeCorrectReceipt.setPreCardBalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBalance().doubleValue()*0.01));
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getName());
					this.saveReceipt(receipt,oldPrepaidCBusiness,preCardRechargeCorrectReceipt,customer);
				}else if(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBusiness.getState())){
					//储值卡充值冲正回执(直充冲正)
					PreCardImRegisterCorrectReceipt preCardImRegisterCorrectReceipt = new PreCardImRegisterCorrectReceipt();
					preCardImRegisterCorrectReceipt.setTitle("储值卡充值冲正回执");
					preCardImRegisterCorrectReceipt.setHandleWay("");
					Receipt rechargeReceipt = this.receiptDao.findByBusIdAndPTC(preRechargeBusiness.getId(),ReceiptParentTypeCodeEnum.prepaidC.getValue());
					Map<String,String> receiptContent = com.alibaba.fastjson.JSONObject.parseObject(rechargeReceipt.getContent(),new TypeReference<Map<String, String>>(){});
					preCardImRegisterCorrectReceipt.setRechargeReciptNo(rechargeReceipt.getReceiptNo());
					preCardImRegisterCorrectReceipt.setPreCardNo(oldPrepaidCBusiness.getCardno());
					preCardImRegisterCorrectReceipt.setRechargePayMentType(receiptContent.get("rechargePayMentType"));
					preCardImRegisterCorrectReceipt.setRechargePayMentNoOrVoucherNo(receiptContent.get("rechargePayMentNo"));
					preCardImRegisterCorrectReceipt.setRechargePosId(receiptContent.get("rechargePosId"));
					preCardImRegisterCorrectReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBeforebalance().doubleValue()*0.01));
					preCardImRegisterCorrectReceipt.setCorrectMoney(NumberUtil.get2Decimal(oldPrepaidCBusiness.getRealprice().doubleValue()*0.01).substring(1));
					preCardImRegisterCorrectReceipt.setPreCardBalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBalance().doubleValue()*0.01));
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getName());
					this.saveReceipt(receipt,oldPrepaidCBusiness,preCardImRegisterCorrectReceipt,customer);
				}

			}
		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	/**
	 * 保存多个充值信息
	 * 
	 * @param addRegPair
	 *            存储卡号与金额
	 * @param customer
	 *            客户信息1
	 * @author Lizhlin
	 */
	public boolean saveAddRegs(List<Map<String, String>> addRegPairList, Customer customer, long operId, long placeId,String flag) {
		try {
			// TODO 等待扣费操作 prepaidc
			BigDecimal addCount = new BigDecimal(0);
			/*for (Map.Entry<String, String> entry : addRegPair.entrySet()) {
				addCount = addCount.add(new BigDecimal(entry.getValue()));
			}*/
			for(Map<String, String> addRegPair:addRegPairList){
				addCount = addCount.add(new BigDecimal(addRegPair.get("fee")));
			}

			// 元转分：
			addCount = addCount.multiply(new BigDecimal("100"));

			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			//新增的字段（携带到接口，不能用作mainAccountInfo的update）
			mainAccountInfo.setPlaceId(customer.getPlaceId());
			mainAccountInfo.setOperId(customer.getOperId());
			mainAccountInfo.setOperName(customer.getOperName());
			mainAccountInfo.setOperNo(customer.getOperNo());
			mainAccountInfo.setPlaceName(customer.getPlaceName());
			mainAccountInfo.setPlaceNo(customer.getPlaceNo());
			
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAddCount(addCount);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType("10");
			unifiedParam.setPlaceId(customer.getPlaceId());
			unifiedParam.setOperId(customer.getOperId());
			unifiedParam.setOperName(customer.getOperName());
			unifiedParam.setOperNo(customer.getOperNo());
			unifiedParam.setPlaceName(customer.getPlaceName());
			unifiedParam.setPlaceNo(customer.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				AddReg addReg = new AddReg();
				BigDecimal Add_Reg_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_NO");
				addReg.setId(Long.valueOf(Add_Reg_NO.toString()));
				addReg.setUserid(customer.getId());
				addReg.setRegister(operId);
				addReg.setRegistrationPlaceId(placeId);// TODO
				//新增的字段
				addReg.setOperName(customer.getOperName());
				addReg.setOperNo(customer.getOperNo());
				addReg.setPlaceName(customer.getPlaceName());
				addReg.setPlaceNo(customer.getPlaceNo());
				addReg.setAddStyle(flag);
				
				addReg.setTotalFee(addCount);
				addReg.setRegistrationTime(new Date());
				addRegDao.save(addReg);
				/*for (Map.Entry<String, String> entry : addRegPair.entrySet()) {
					AddRegDetail addRegDetail = new AddRegDetail();
					BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
					addRegDetail.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
					addRegDetail.setAddRegID(Long.valueOf(addReg.getId()));
					addRegDetail.setCardNo(entry.getKey());
					addRegDetail.setAddStyle(flag);
					addRegDetail.setFee(
							BigDecimal.valueOf(Double.valueOf(entry.getValue())).multiply(new BigDecimal("100")));
					addRegDetail.setFlag(AddRegDetailStateEnum.normal.getValue());
					addRegDetailDao.save(addRegDetail);
				}*/
				for(Map<String, String> addRegPair:addRegPairList){
					AddRegDetail addRegDetail = new AddRegDetail();
					BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
					addRegDetail.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
					addRegDetail.setAddRegID(Long.valueOf(addReg.getId()));
					addRegDetail.setCardNo(addRegPair.get("cardNo"));
					addRegDetail.setAddStyle(flag);
					addRegDetail.setFee(
							BigDecimal.valueOf(Double.valueOf(addRegPair.get("fee"))).multiply(new BigDecimal("100")));
					addRegDetail.setFlag(AddRegDetailStateEnum.normal.getValue());
					addRegDetailDao.save(addRegDetail);
				}
				
				PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
				prepaidCBussiness.setUserid(addReg.getUserid());
				prepaidCBussiness.setOperid(operId);
				prepaidCBussiness.setPlaceid(placeId);
				//1、手工录入 
				if("1".equals(flag)){
					prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.addRegWithHand.getValue());
				}else{
					prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.addRegBatchInput.getValue()); 
				}
				
//				prepaidCBussiness.setPlaceid(addReg.getSalePlaceId());
//				prepaidCBussiness.setOperid(addReg.getSaleOperId());
				prepaidCBussiness.setOperName(addReg.getOperName());
				prepaidCBussiness.setOperNo(addReg.getOperNo());
				prepaidCBussiness.setPlaceName(addReg.getPlaceName());
				prepaidCBussiness.setPlaceNo(addReg.getPlaceNo());
				prepaidCBussiness.setTradetime(new Date());
				prepaidCBussiness.setBusinessId(addReg.getId());//充值登记表id
				
				prepaidCBussinessDao.save(prepaidCBussiness);
				
				//Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidCBussiness.getCardno());
				//1、手工录入 
				if("1".equals(flag)){
					serviceWater.setSerType(ServiceWaterSerType.addRegWithHand.getValue());
					serviceWater.setRemark(ServiceWaterSerType.getNameByValue(ServiceWaterSerType.addRegWithHand.getValue()));
				}else{
					serviceWater.setSerType(ServiceWaterSerType.addRegBatchInput.getValue());
					serviceWater.setRemark(ServiceWaterSerType.getNameByValue(ServiceWaterSerType.addRegBatchInput.getValue()));
				}
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWaterDao.save(serviceWater);

				//储值卡充值登记回执
				AddRegReceipt addRegReceipt = new AddRegReceipt();
				addRegReceipt.setTitle("储值卡充值登记回执");
				addRegReceipt.setHandleWay("凭密码办理");
				addRegReceipt.setCardAndMoneyJsonData(JSONArray.fromObject(addRegPairList).toString());
				Receipt receipt = new Receipt();
				//手工录入
				if("1".equals(flag)){
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.addRegWithHand.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.addRegWithHand.getName());
				}else{
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.addRegBatchInput.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.addRegBatchInput.getName());
				}
				this.saveReceipt(receipt,prepaidCBussiness,addRegReceipt,customer);
				
				
				return true;
			}
			return false;
			// addRegDao.saveAddRegs(addRegPair, customer);
		} catch (ApplicationException e) {
			logger.error("充值登记信息保存失败", e);
			e.printStackTrace();
			throw new ApplicationException("充值登记信息保存失败");
		}
	}

	/**
	 * 领取新卡接口
	 * 
	 * @param 主账户信息，新储值卡，电子钱包，储值卡业务记录，客服流水
	 * @throws Exception
	 * @author zxy
	 */
	
	public String saveGainCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness rechargePrepaidCBussiness) {
		return null;
	}
	
	/**
	 * 领取新卡接口
	 * 
	 * @param 主账户信息，新储值卡，电子钱包，储值卡业务记录，客服流水
	 * @throws Exception
	 * @author zxy
	 */
	
	public String saveGainCardForAMMS(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness rechargePrepaidCBussiness) {
		return null;
	}

	/**
	 * 补领新卡接口
	 * 
	 * @param 主账户信息，旧储值卡，旧储值卡历史，新储值卡，电子钱包，旧电子钱包历史，储值卡业务记录，客服流水
	 * @throws Exception
	 * @author zxy
	 */
	
	public String saveReplaceCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord) {
		return null;
	}

	/**
	 * 终止使用卡接口
	 * 
	 * @param serviceFlowRecord
	 * @param prepaidCBussiness
	 * @param cancel
	 */
	public Map<String, Object> saveStopCard(ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness prepaidCBussiness, Cancel cancel,
			Customer customer, PrepaidC prepaidC) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			/*InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			if (prepaidC.getCardNo().length()==16 && "13".equals(prepaidCBussiness.getState())) {
				map = inventoryService.omsInterface(prepaidC.getCardNo(), "2", interfaceRecord,"",
						prepaidCBussiness.getPlaceid(),prepaidCBussiness.getOperid(),prepaidCBussiness.getOperName(),"1","",null,new BigDecimal("0"),"212");
				boolean result = (Boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
			}*/
			
			// 生成卡片注销客服流水
			BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
			Long bussId = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(bussId);
			serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId() + "");
			serviceFlowRecord.setClientID(customer.getId());

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
				//materialService.updateVehicleId(materialIds, bussId);
				
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(prepaidCBussiness.getCardno());
				DbasCardFlow dbasCard = new DbasCardFlow();
				String readFlag;//卡片是否可读
				//String auditStatus = "0";//csms_refundInfo表auditStatus取值:有卡为1，即申请状态;无卡为0，即处于资金争议期
				BigDecimal carAmt = prepaidCBussiness.getBalance();//卡片实际余额
				if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					readFlag = DBACardFlowReadFlagEnum.abledRead.getValue();
					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
					//auditStatus = "1";
				}else{
					readFlag = DBACardFlowReadFlagEnum.disabledRead.getValue();
					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
					//auditStatus = "0";
				}
				dbasCard.setCardAmt(carAmt);
				dbasCard.setReadFlag(readFlag);
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(prepaidCBussiness.getCardno());
				dbasCard.setOldCardNo(prepaidCBussiness.getCardno());
				if(dbasCardFlow!=null)dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(prepaidCBussiness.getCardno());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(bussId);
				dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
				dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
				dbasCard.setOperId(prepaidCBussiness.getOperid());
				dbasCard.setOperno(prepaidCBussiness.getOperNo());
				dbasCard.setOpername(prepaidCBussiness.getOperName());
				dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
				dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
				dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
				dbasCardFlowDao.save(dbasCard);
				
				MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
				unifiedParam.setType(AccChangeTypeEnum.prepaidCStopCard.getValue());
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setBailFee(carAmt);
				unifiedParam.setChangePrice(prepaidCBussiness.getRealprice());
				unifiedInterfaceService.saveAccAvailableBalance(unifiedParam);
				
				//2017-07-25 储值卡终止使用不生成退款记录，退款步骤另起功能模块
				/*refundInfo.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfo_NO").toString()));
				refundInfo.setMainId(customer.getId());
				refundInfo.setMainAccountId(mainAccountInfo.getId());
				refundInfo.setRefundType("1");//储值卡注销
				refundInfo.setCardNo(prepaidCBussiness.getCardno());
				refundInfo.setMainAccountId(mainAccountInfo.getId());
				refundInfo.setBalance(mainAccountInfo.getBalance());//账户余额
				refundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());//账户可用余额
				refundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());//优惠余额
				refundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());//可退余额
				refundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());//冻结余额
				refundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());//退款审批余额
				refundInfo.setCurrentRefundBalance(carAmt);//本次退款金额————卡片实际余额，无卡则余额为0
				refundInfo.setRefundApplyOper(prepaidCBussiness.getOperid());
				refundInfo.setRefundApplyTime(prepaidCBussiness.getTradetime());
				refundInfo.setAuditStatus(auditStatus);//申请————资金争议期
				refundInfo.setOperId(prepaidCBussiness.getOperid());
				refundInfo.setOperNo(prepaidCBussiness.getOperNo());
				refundInfo.setOperName(prepaidCBussiness.getOperName());
				refundInfo.setPlaceID(prepaidCBussiness.getPlaceid());
				refundInfo.setPlaceNo(prepaidCBussiness.getPlaceNo());
				refundInfo.setPlaceName(prepaidCBussiness.getPlaceName());
				refundInfoDao.save(refundInfo);*/
				
				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				cancelDao.save(cancel);
				
				VehicleInfo vehicleInfo = null;
				//解绑车辆信息
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				if(carObuCardInfo!=null){
					carObuCardInfo.setPrepaidCID(null);
					carObuCardInfoDao.update(carObuCardInfo);
					
					vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
					//如果能找到绑定车辆，就要记录车辆信息业务记录表
					if(vehicleInfo != null){
						VehicleBussiness vehicleBussiness = new VehicleBussiness();
						BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
						vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
						vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
						vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
						vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
						vehicleBussiness.setCardNo(prepaidC.getCardNo());//
						vehicleBussiness.setCardType(Constant.PREPAIDTYPE);//储值卡
						//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
						if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
							vehicleBussiness.setType(VehicleBussinessEnum.prepaidCStopWithoutCard.getValue());//储值无卡终止使用
							vehicleBussiness.setMemo("储值无卡终止使用");
						}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
							vehicleBussiness.setType(VehicleBussinessEnum.prepaidCStopWithCard.getValue());//储值有卡终止使用
							vehicleBussiness.setMemo("储值有卡终止使用");
						}
						vehicleBussiness.setPlaceID(prepaidCBussiness.getPlaceid());
						vehicleBussiness.setOperID(prepaidCBussiness.getOperid());
						vehicleBussiness.setOperName(prepaidCBussiness.getOperName());
						vehicleBussiness.setOperNo(prepaidCBussiness.getOperNo());
						vehicleBussiness.setPlaceName(prepaidCBussiness.getPlaceName());
						vehicleBussiness.setPlaceNo(prepaidCBussiness.getPlaceNo());
						vehicleBussiness.setCreateTime(new Date());

						vehicleBussinessDao.save(vehicleBussiness);
					}
				}
				
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;
				// TODO 增加操作日志
				if("0".equals(serviceFlowRecord.getIsNeedBlacklist())){
					// 客服给清算		//原清算数据，没用了
					/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
					Customer cus = customerDao.findById(prepaidC.getCustomerID());
					SCinfo scinfo = new SCinfo();
					scinfo.setCardNo(prepaidC.getCardNo());
					scinfo.setState("22");
					scinfo.setUserNo(cus.getUserNo());
					scinfo.setBusinessTime(new Date());
					scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
					sCinfoDao.save(scinfo,cus.getId());*/
					
					//写给铭鸿的清算数据：卡片状态信息
					//有卡注销
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()), 
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), prepaidC.getCardNo(), "22", userType);
					
					
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if(vehicleInfo != null){
						cardObuService.saveUserStateInfo(prepaidCBussiness.getTradetime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
								"22", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
								null,obuSeq, obuIssueTime, obuExpireTime, "储值卡有卡注销");
					}
					
				}else{
					//如果卡片为挂失卡，则先解除挂失黑名单
					BlackListTemp blackListTemp = new BlackListTemp();
					blackListTemp.setCardType(Constant.PREPAIDTYPE);
					blackListTemp.setCardNo(prepaidC.getCardNo());
					blackListTemp.setStatus(2);//挂失黑名单
					List<Map<String,Object>> list = blackListDao.findByBlackListTemp(blackListTemp);
					if(list.size()>0){
						//保存黑名单流水解除挂失黑名单	给铭鸿
						blackListService.saveCardNoLost(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
								, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
								prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
								new Date());
					}
					
					
					blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
							, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
							prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
							new Date());
					
					//写给铭鸿的清算数据：卡片状态信息
					//无卡注销
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(PrepaidCardStateEnum.cancel.getIndex()), 
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), prepaidC.getCardNo(), "22", userType);
					
					
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if(vehicleInfo != null){
						cardObuService.saveUserStateInfo(prepaidCBussiness.getTradetime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
								"22", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
								null,obuSeq, obuIssueTime, obuExpireTime, "储值卡无卡注销");
					}
					
				}
				//清算接口		//原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseList.setCardType(1);
				userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
				
				
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setSerType("513");//储值无卡终止使用
				}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setSerType("512");//储值有卡终止使用
				}
				//serviceWater.setAmt(prepaidC.getCost_());//应收金额
				//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				//serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				//serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setRemark("自营客服系统：储值无卡终止使用");
				}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setRemark("自营客服系统：储值有卡终止使用");
				}
				serviceWaterDao.save(serviceWater);


				//储值卡终止使用回执
				Receipt receipt = new Receipt();
				PreCardCannelReceipt preCardCannelReceipt = new PreCardCannelReceipt();
				preCardCannelReceipt.setTitle("储值卡终止使用回执");
				preCardCannelReceipt.setHandleWay("凭密码办理");
				preCardCannelReceipt.setPreCardNo(prepaidCBussiness.getCardno());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					preCardCannelReceipt.setCannelWay("无卡终止使用");
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getName());
				}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					preCardCannelReceipt.setCannelWay("有卡终止使用");
					receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardCannel.getValue());
					receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardCannel.getName());
				}
				preCardCannelReceipt.setPreCardBalance(NumberUtil.get2Decimal(prepaidCBussiness.getBalance().doubleValue()*0.01));
				/*preCardCannelReceipt.setCannelBankNo(cancel.getBankNo());
				preCardCannelReceipt.setCannelBankMember(cancel.getBankMember());
				preCardCannelReceipt.setCannelBankOpenBranches(cancel.getBankOpenBranches());*/
				this.saveReceipt(receipt,prepaidCBussiness,preCardCannelReceipt,customer);


				/*if (prepaidC.getCardNo().length()==16 && map!=null) {
					//锁定成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}else {
				}*/
				resultMap.put("result", "true");
				resultMap.put("cancelId", PrePaidC_cancel_NO.toString());
				return resultMap;
			}
			
			resultMap.put("result", "false");
			resultMap.put("message", "储值卡终止使用失败");
			return resultMap;
			
		} catch (ApplicationException e) {
			logger.error("终止卡失败", e);
			throw new ApplicationException("终止卡失败");
		}
	}
	
	/**
	 * 储值卡终止使用首次退款
	 * @param serviceFlowRecord
	 * @param refundInfo
	 * @param customer
	 * @param prepaidC
	 * @return Map<String,Object>
	 */
	public Map<String, Object> savePrepaidCRefund(PrepaidCBussiness prepaidCBussiness,Cancel cancel, RefundInfo refundInfo,
			Customer customer, PrepaidC prepaidC,Long bussinessPlaceId) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			
			// 生成储值卡终止使用首次退款业务记录
			//BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
			Long bussId = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(bussId);
			
			DbasCardFlow dbasCardFlow = dbasCardFlowDao.findPreCancelDbasCardFlow(prepaidC.getCardNo());
			if(dbasCardFlow == null){
				resultMap.put("result", "false");
				resultMap.put("message", "数据错误：终止使用卡片缺少资金转移记录");
				return resultMap;
			}
			if("1".equals(dbasCardFlow.getEndFlag())){
				resultMap.put("result", "false");
				resultMap.put("message", "数据错误：储值卡终止使用资金转移记录已被发起退款");
				return resultMap;
			}
			
			BigDecimal carAmt = prepaidCBussiness.getBalance();
			//判断页面显示的卡片余额与此时获取的卡片余额是否一致
			if(dbasCardFlow.getCardAmt().compareTo(carAmt) != 0){
				resultMap.put("result", "false");
				resultMap.put("message", "数据错误：操作期间储值卡终止使用资金转移确认记录的卡片余额发生改变");
				return resultMap;
			}
			
			UnifiedParam unifiedParam = new UnifiedParam();
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			//unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setType(AccChangeTypeEnum.preCardStopRefund.getValue());//储值卡终止使用首次退款
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setBailFee(carAmt);
			//unifiedParam.setChangePrice(prepaidCBussiness.getRealprice());
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			unifiedInterfaceService.saveAccAvailableBalance(unifiedParam);
			
			//2017-07-25 储值卡终止使用不生成退款记录，退款步骤另起功能模块
			refundInfo.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfo_NO").toString()));
			refundInfo.setMainId(customer.getId());
			refundInfo.setMainAccountId(mainAccountInfo.getId());
			refundInfo.setRefundType("1");//储值卡注销
			refundInfo.setCardNo(prepaidCBussiness.getCardno());
			refundInfo.setMainAccountId(mainAccountInfo.getId());
			refundInfo.setBalance(mainAccountInfo.getBalance());//账户余额
			refundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());//账户可用余额
			refundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());//优惠余额
			refundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());//可退余额
			refundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());//冻结余额
			refundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());//退款审批余额
			refundInfo.setCurrentRefundBalance(carAmt);//本次退款金额————查看资金转移确认表的卡片余额
			refundInfo.setRefundApplyOper(prepaidCBussiness.getOperid());
			refundInfo.setRefundApplyTime(prepaidCBussiness.getTradetime());
			refundInfo.setAuditStatus("1");//1:申请
			refundInfo.setExpireFlag(dbasCardFlow.getExpireFlag());//是否已过资金争议期
			refundInfo.setOperId(prepaidCBussiness.getOperid());
			refundInfo.setOperNo(prepaidCBussiness.getOperNo());
			refundInfo.setOperName(prepaidCBussiness.getOperName());
			refundInfo.setPlaceID(prepaidCBussiness.getPlaceid());
			refundInfo.setPlaceNo(prepaidCBussiness.getPlaceNo());
			refundInfo.setPlaceName(prepaidCBussiness.getPlaceName());
			if("0".equals(dbasCardFlow.getReadFlag())) refundInfo.setCardSystemAmt(dbasCardFlow.getCardAmt());//如果不可读dbasCardFlow.getCardAmt()作为系统余额
			else refundInfo.setCardAmt(dbasCardFlow.getCardAmt());//如果可读dbasCardFlow.getCardAmt()作为卡片余额
			
			refundInfo.setBussinessPlaceId(bussinessPlaceId);//操作员所属营业部
			
			refundInfoDao.save(refundInfo);
			
			//修改资金转移确认表的完成标志
			DbasCardFlow tmp = new DbasCardFlow();
			tmp.setId(dbasCardFlow.getId());
			tmp.setEndFlag("1");//1已完成
			dbasCardFlowDao.updateNotNull(dbasCardFlow);
			
			//修改cancel状态为已发起首次退款
			if(cancel.getId() != null){
				cancel.setRefundState("1");//1已发起首次退款
				cancelDao.updateNotNull(cancel);
			}
			
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
				serviceWater.setSerType("513");//储值无卡终止使用
			}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
				serviceWater.setSerType("512");//储值有卡终止使用
			}
			//serviceWater.setAmt(prepaidC.getCost_());//应收金额
			//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
			//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
			//serviceWater.setFlowState("1");//正常
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			//serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
				serviceWater.setRemark("自营客服系统：储值无卡终止使用");
			}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
				serviceWater.setRemark("自营客服系统：储值有卡终止使用");
			}
			serviceWaterDao.save(serviceWater);
			
			resultMap.put("result", "true");
			resultMap.put("refundInfoId", refundInfo.getId());
			return resultMap;
		} catch (ApplicationException e) {
			logger.error("终止卡失败", e);
			throw new ApplicationException("终止卡失败");
		}
		
		//resultMap.put("result", "false");
		//resultMap.put("message", "储值卡终止使用首次退款失败");
		//return resultMap;
	}
	
	
	/**
	 * 储值卡终止使用再次退款接口
	 * 
	 * @param serviceFlowRecord
	 * @param refundInfo
	 * @param customer
	 * @param prepaidC
	 * @param materialIds
	 */
	public Map<String, Object> saveStopCardAgain(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer, PrepaidC prepaidC,String materialIds,Long bussinessPlaceId) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			String bankNo = refundInfo.getBankNo();
			String bankMember = refundInfo.getBankMember();
			String bankOpenBranches = refundInfo.getBankOpenBranches();
			
			//保存图片
			//materialService.updateVehicleId(materialIds, prepaidC.getId());
			
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			//由于退款失败后，退款金额从   退款审批余额-->可退余额    所以再次退款 不发生资金变动
			/*
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(AccChangeTypeEnum.prepaidCStopCard.getValue());
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setBailFee(refundInfo.getCurrentRefundBalance().multiply(new BigDecimal("100")));
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedInterfaceService.saveAccAvailableBalance(unifiedParam);*/
			
			
			RefundInfo oldRefundInfo = refundInfoDao.findById(refundInfo.getId());
			//保存历史记录
			RefundInfoHis refundInfoHis = new RefundInfoHis();
			refundInfoHis.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfoHis_NO").toString()));
			refundInfoHis.setCreateReason("1");//修改
			refundInfoHisDao.saveHis(refundInfoHis, oldRefundInfo);
			
			//将旧记录的审核状态改为再次退款
			oldRefundInfo.setAuditStatus("8");
			refundInfoDao.update(oldRefundInfo);
			
			
			//新增退款记录
			RefundInfo newRefundInfo = new RefundInfo();
			newRefundInfo.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfo_NO").toString()));
			newRefundInfo.setMainId(customer.getId());
			newRefundInfo.setMainAccountId(mainAccountInfo.getId());
			newRefundInfo.setRefundType("1");//储值卡注销
			newRefundInfo.setCardNo(serviceFlowRecord.getCardTagNO());
			newRefundInfo.setMainAccountId(mainAccountInfo.getId());
			//相关金额
			newRefundInfo.setBalance(mainAccountInfo.getBalance());
			newRefundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());
			newRefundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
			newRefundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
			newRefundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
			newRefundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
			newRefundInfo.setCurrentRefundBalance(oldRefundInfo.getCurrentRefundBalance());//卡片实际余额，无卡则余额为0
			//银行信息
			newRefundInfo.setBankNo(bankNo);
			newRefundInfo.setBankMember(bankMember);
			newRefundInfo.setBankOpenBranches(bankOpenBranches);
			//审核状态
			newRefundInfo.setAuditStatus("1");
			//操作员信息
			newRefundInfo.setRefundApplyOper(serviceFlowRecord.getOperID());
			newRefundInfo.setRefundApplyTime(serviceFlowRecord.getCreateTime());
			newRefundInfo.setOperId(serviceFlowRecord.getOperID());
			newRefundInfo.setOperNo(serviceFlowRecord.getOperNo());
			newRefundInfo.setOperName(serviceFlowRecord.getOperName());
			newRefundInfo.setPlaceID(serviceFlowRecord.getPlaceID());
			newRefundInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
			newRefundInfo.setPlaceName(serviceFlowRecord.getPlaceName());
			
			//2017-07-27 新增的字段，要将旧的update进去
			newRefundInfo.setTransferAmt(oldRefundInfo.getTransferAmt());
			newRefundInfo.setReturnAmt(oldRefundInfo.getReturnAmt());
			newRefundInfo.setCardAmt(oldRefundInfo.getCardAmt());//
			newRefundInfo.setCardSystemAmt(oldRefundInfo.getCardSystemAmt());
			newRefundInfo.setExpireFlag(oldRefundInfo.getExpireFlag());//是否已过资金争议期
			
			//操作员所属营业部
			newRefundInfo.setBussinessPlaceId(bussinessPlaceId);
			
			refundInfoDao.save(newRefundInfo);
			
			
			resultMap.put("result", "true");
			resultMap.put("refundInfoId", newRefundInfo.getId());
			return resultMap;
			
		} catch (ApplicationException e) {
			logger.error("再次退款失败", e);
			throw new ApplicationException("再次退款失败");
		}
	}

	/**
	 * 终止使用卡接口
	 * 
	 * @param serviceFlowRecord
	 * @param prepaidCBussiness
	 * @param cancel
	 */
	public Map<String, Object> saveStopCardAgainForAMMS(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer, PrepaidC prepaidC,String materialIds) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			String bankNo = refundInfo.getBankNo();
			String bankMember = refundInfo.getBankMember();
			String bankOpenBranches = refundInfo.getBankOpenBranches();
			
			//保存图片
			//materialService.updateVehicleId(materialIds, prepaidC.getId());
			
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());;
			/*UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(AccChangeTypeEnum.prepaidCStopCard.getValue());
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setBailFee(refundInfo.getCurrentRefundBalance().multiply(new BigDecimal("100")));
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedInterfaceService.saveAccAvailableBalance(unifiedParam);*/
			
			
			RefundInfo oldRefundInfo = refundInfoDao.findById(refundInfo.getId());
			//保存历史记录
			RefundInfoHis refundInfoHis = new RefundInfoHis();
			refundInfoHis.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfoHis_NO").toString()));
			refundInfoHis.setCreateReason("1");//修改
			refundInfoHisDao.saveHis(refundInfoHis, oldRefundInfo);
			
			//将旧记录的审核状态改为再次退款
			oldRefundInfo.setAuditStatus("8");
			refundInfoDao.update(oldRefundInfo);
			
			
			//新增退款记录
			RefundInfo newRefundInfo = new RefundInfo();
			newRefundInfo.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSrefundinfo_NO").toString()));
			newRefundInfo.setMainId(customer.getId());
			newRefundInfo.setMainAccountId(mainAccountInfo.getId());
			newRefundInfo.setRefundType("1");//储值卡注销
			newRefundInfo.setCardNo(serviceFlowRecord.getCardTagNO());
			newRefundInfo.setMainAccountId(mainAccountInfo.getId());
			//相关金额
			newRefundInfo.setBalance(mainAccountInfo.getBalance());
			newRefundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());
			newRefundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
			newRefundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
			newRefundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
			newRefundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
			newRefundInfo.setCurrentRefundBalance(oldRefundInfo.getCurrentRefundBalance());//卡片实际余额，无卡则余额为0
			//银行信息
			newRefundInfo.setBankNo(bankNo);
			newRefundInfo.setBankMember(bankMember);
			newRefundInfo.setBankOpenBranches(bankOpenBranches);
			//审核状态
			newRefundInfo.setAuditStatus("1");
			//操作员信息
			newRefundInfo.setRefundApplyOper(serviceFlowRecord.getOperID());
			newRefundInfo.setRefundApplyTime(serviceFlowRecord.getCreateTime());
			newRefundInfo.setOperId(serviceFlowRecord.getOperID());
			newRefundInfo.setOperNo(serviceFlowRecord.getOperNo());
			newRefundInfo.setOperName(serviceFlowRecord.getOperName());
			newRefundInfo.setPlaceID(serviceFlowRecord.getPlaceID());
			newRefundInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
			newRefundInfo.setPlaceName(serviceFlowRecord.getPlaceName());
			refundInfoDao.save(newRefundInfo);
			
			
			resultMap.put("result", "true");
			resultMap.put("refundInfoId", newRefundInfo.getId());
			return resultMap;
			
		} catch (ApplicationException e) {
			logger.error("再次退款失败", e);
			throw new ApplicationException("再次退款失败");
		}
	}

	/**
	 * 终止使用卡接口
	 * 与自营的区别：
	 * 2017/8/7  代理点不用更新csms_mainaccount_info和新增CSMS_AccountFundChange
	 * @param serviceFlowRecord
	 * @param prepaidCBussiness
	 * @param cancel
	 */
	public Map<String, Object> saveStopCardForAMMS(ServiceFlowRecord serviceFlowRecord,
			PrepaidCBussiness prepaidCBussiness, Cancel cancel,Customer customer,PrepaidC prepaidC) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			// 生成卡片注销客服流水
			BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
			Long bussId = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(bussId);
			serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId() + "");
			serviceFlowRecord.setClientID(customer.getId());
			
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
			
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				DbasCardFlow dbasCard = new DbasCardFlow();
				
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(prepaidCBussiness.getCardno());
				if(dbasCardFlow!=null)dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(prepaidCBussiness.getCardno());
				
				if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.abledRead.getValue());
					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
				}else{
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
				}
				dbasCard.setCardAmt(prepaidCBussiness.getBalance());
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(prepaidCBussiness.getCardno());
				dbasCard.setOldCardNo(prepaidCBussiness.getCardno());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(bussId);
				dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
				dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
				dbasCard.setOperId(prepaidCBussiness.getOperid());
				dbasCard.setOperno(prepaidCBussiness.getOperNo());
				dbasCard.setOpername(prepaidCBussiness.getOperName());
				dbasCard.setPlaceId(prepaidCBussiness.getPlaceid());
				dbasCard.setPlacename(prepaidCBussiness.getPlaceName());
				dbasCard.setPlaceno(prepaidCBussiness.getPlaceNo());
				dbasCardFlowDao.save(dbasCard);
				
				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				cancelDao.save(cancel);
				
				VehicleInfo vehicleInfo = null;
				//解绑车辆信息
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
				if(carObuCardInfo!=null){
					carObuCardInfo.setPrepaidCID(null);
					carObuCardInfoDao.update(carObuCardInfo);
					
					vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				}
				
				//如果卡片为挂失卡，则先解除挂失黑名单
				BlackListTemp blackListTemp = new BlackListTemp();
				blackListTemp.setCardType(DBACardFlowCardTypeEnum.prePaidCard.getValue());
				blackListTemp.setCardNo(prepaidC.getCardNo());
				blackListTemp.setStatus(2);//挂失黑名单
				List<Map<String,Object>> list = blackListDao.findByBlackListTemp(blackListTemp);
				if(list.size()>0){
					//保存黑名单流水解除挂失黑名单	给铭鸿
					blackListService.saveCardNoLost(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
							, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
							prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
							new Date());
				}
				
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;
				if("0".equals(serviceFlowRecord.getIsNeedBlacklist())){
					//写给铭鸿的清算数据：卡片状态信息
					//有卡注销
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()), 
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), prepaidC.getCardNo(), "22", userType);
					
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if(vehicleInfo != null){
						cardObuService.saveUserStateInfo(prepaidCBussiness.getTradetime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
								"22", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
								null,obuSeq, obuIssueTime, obuExpireTime, "储值卡有卡注销");
					}
				}else{
					
					blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
							, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
							prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
							new Date());
					
					//写给铭鸿的清算数据：卡片状态信息
					//无卡注销
					cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(PrepaidCardStateEnum.cancel.getIndex()), 
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), prepaidC.getCardNo(), "22", userType);
					
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if(vehicleInfo != null){
						cardObuService.saveUserStateInfo(prepaidCBussiness.getTradetime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
								"22", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
								null,obuSeq, obuIssueTime, obuExpireTime, "储值卡无卡注销");
					}
				}
				
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setSerType("513");//储值无卡终止使用
				}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setSerType("512");//储值有卡终止使用
				}
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				if(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setRemark("代理点系统：储值无卡终止使用");
				}else if(PrepaidCardBussinessTypeEnum.preCardCannel.getValue().equals(prepaidCBussiness.getState())){
					serviceWater.setRemark("代理点系统：储值有卡终止使用");
				}
				serviceWaterDao.save(serviceWater);
				
				resultMap.put("result", "true");
				resultMap.put("cancelId",PrePaidC_cancel_NO.toString());
				return resultMap;
			}
			
			resultMap.put("result", "false");
			resultMap.put("message", "储值卡终止使用失败");
			return resultMap;
			
		} catch (ApplicationException e) {
			logger.error("终止卡失败", e);
			throw new ApplicationException("终止卡失败");
		}
	}
	
	/**
	 * 更改交易密码
	 * 
	 * @author zyh
	 */
	public boolean updatePwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness) {
		try {
			/* 储值卡业务记录 */
			//PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
			//增加历史表记录
			PrepaidC oldPrepaidC = prepaidCDao.findById(prepaidC.getId());
			PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(), "10", oldPrepaidC);//交易密码修改
			prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PREPAIDC_HIS_NO"));
			prepaidCDao.saveHis(prepaidCHis);

			prepaidCBussinessDao.save(prepaidCBussiness);	//保存业务
			// MD5 = 卡号+交易密码
			prepaidC.setTradingPwd(StringUtil.md5(prepaidC.getTradingPwd()));
			prepaidC.setHisSeqID(prepaidCHis.getId());
			prepaidCDao.update(prepaidC);
			
			
			Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setSerType("505");//储值卡消费密码修改
			//serviceWater.setAmt(prepaidC.getCost_());//应收金额
			//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
			//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
			//serviceWater.setFlowState("1");//正常
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("自营客服系统：储值卡消费密码修改");
			serviceWaterDao.save(serviceWater);

			//储值卡消费密码更改回执
			PreCardPasswordModifyReceipt preCardPasswordModify = new PreCardPasswordModifyReceipt();
			preCardPasswordModify.setTitle("储值卡消费密码更改回执");
			preCardPasswordModify.setHandleWay("凭密码办理");
			preCardPasswordModify.setPreCardNo(prepaidCBussiness.getCardno());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardPasswordModify.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardPasswordModify.getName());
			this.saveReceipt(receipt,prepaidCBussiness,preCardPasswordModify,customer);

			return true;
		} catch (ApplicationException e) {
			logger.error("储值卡交易密码修改失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("储值卡交易密码修改失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 重设交易密码
	 * 
	 * @author zyh
	 */
	public boolean resetPwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness) {
		try {
			/* 储值卡业务记录 */
			//PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
			//增加历史表记录
			PrepaidC oldPrepaidC = prepaidCDao.findById(prepaidC.getId());
			PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(), "11", oldPrepaidC);//交易密码重设
			prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PREPAIDC_HIS_NO"));
			prepaidCDao.saveHis(prepaidCHis);

			prepaidCBussinessDao.save(prepaidCBussiness);	//保存业务
			// MD5 = 卡号+交易密码
			prepaidC.setTradingPwd(StringUtil.md5(prepaidC.getTradingPwd()));
			prepaidC.setHisSeqID(prepaidCHis.getId());
			prepaidCDao.update(prepaidC);
			
			
			Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setSerType("506");//储值卡消费密码重设
			//serviceWater.setAmt(prepaidC.getCost_());//应收金额
			//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
			//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
			//serviceWater.setFlowState("1");//正常
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("自营客服系统：储值卡消费密码重设");
			serviceWaterDao.save(serviceWater);

			//储值卡消费密码重设回执
			PreCardPasswordResetReceipt preCardPasswordResetReceipt = new PreCardPasswordResetReceipt();
			preCardPasswordResetReceipt.setTitle("储值卡消费密码重设回执");
			preCardPasswordResetReceipt.setHandleWay("凭密码办理");
			preCardPasswordResetReceipt.setPreCardNo(prepaidCBussiness.getCardno());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardPasswordReset.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardPasswordReset.getName());
			this.saveReceipt(receipt,prepaidCBussiness,preCardPasswordResetReceipt,customer);

			return true;
		} catch (ApplicationException e) {
			logger.error("储值卡交易密码修改失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("储值卡交易密码修改失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 挂失
	 * 
	 * @author zyh
	 */
	public boolean saveLock(PrepaidC prepaidC) {
		try {
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("1");
			unifiedParam.setPlaceId(prepaidC.getSalePlaceId());
			unifiedParam.setOperId(prepaidC.getSaleOperId());
			unifiedParam.setOperName(prepaidC.getOperName());
			unifiedParam.setOperNo(prepaidC.getOperNo());
			unifiedParam.setPlaceName(prepaidC.getPlaceName());
			unifiedParam.setPlaceNo(prepaidC.getPlaceNo());
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 储值卡业务记录
				PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));

				prepaidCBussiness.setUserid(prepaidC.getCustomerID());
				prepaidCBussiness.setOldCardno(prepaidC.getCardNo());
				prepaidCBussiness.setCardno(prepaidC.getCardNo());
				prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardLoss.getValue());
				prepaidCBussiness.setPlaceid(prepaidC.getSalePlaceId());
				prepaidCBussiness.setOperid(prepaidC.getSaleOperId());
				prepaidCBussiness.setOperName(prepaidC.getOperName());
				prepaidCBussiness.setOperNo(prepaidC.getOperNo());
				prepaidCBussiness.setPlaceName(prepaidC.getPlaceName());
				prepaidCBussiness.setPlaceNo(prepaidC.getPlaceNo());
				prepaidCBussiness.setTradetime(new Date());
				prepaidCBussinessDao.save(prepaidCBussiness);

				// 客服给清算		//原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				scinfo.setState("1");
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/

				// 清算系统用的黑色单
				//VehicleInfo vehicleInfo = vehicleInfoService.findByPrepaidCNo(prepaidC.getCardNo());
				/*VehicleInfo vehicleInfo = vehicleInfoDao.findByPrepaidCardNo(prepaidC.getCardNo());
				TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(null, null, prepaidC.getCardNo(),
						null, null, vehicleInfo.getVehiclePlate(), 2, new Date(), 0, new Date());
				tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/
				
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
						null, " ", null, 2, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
						null, " ", null, 2, new Date(), 0, new Date());
				saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//没用了
				/*DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//
				saveDarkList(prepaidC,darkList,"2", "1");*/
				
				
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				serviceWater.setSerType("507");//储值卡挂失
				//serviceWater.setAmt(prepaidC.getCost_());//应收金额
				//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				//serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark("自营客服系统：储值卡挂失");
				serviceWaterDao.save(serviceWater);

				//储值卡挂失回执
				PreCardLossReceipt preCardLossReceipt = new PreCardLossReceipt();
				preCardLossReceipt.setTitle("储值卡挂失回执");
				preCardLossReceipt.setHandleWay("凭密码办理");
				preCardLossReceipt.setPreCardNo(prepaidCBussiness.getCardno());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardLoss.getValue());
				receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardLoss.getName());
				receipt.setBusinessId(prepaidCBussiness.getId());
				this.saveReceipt(receipt,prepaidCBussiness,preCardLossReceipt,customer);

				
				//保存客服挂失黑名单流水信息		给铭鸿
				blackListService.saveCardLost(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
						, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
						prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
						new Date());
				
				
				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.loss.getValue()), 
						CardStateSendSerTypeEnum.loss.getValue(), prepaidC.getCardNo(), "22", userType);
				
				
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error("储值卡挂失操作失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("储值卡挂失操作失败，储值卡id:" + prepaidC.getId());
		}
	}
	/**
	 * 挂失
	 *
	 * @author sxw
	 */
	public boolean saveLock(PrepaidC prepaidC,String systemType) {
		try {
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("1");
			unifiedParam.setPlaceId(prepaidC.getSalePlaceId());
			unifiedParam.setOperId(prepaidC.getSaleOperId());
			unifiedParam.setOperName(prepaidC.getOperName());
			unifiedParam.setOperNo(prepaidC.getOperNo());
			unifiedParam.setPlaceName(prepaidC.getPlaceName());
			unifiedParam.setPlaceNo(prepaidC.getPlaceNo());
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 储值卡业务记录
				PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));

				prepaidCBussiness.setUserid(prepaidC.getCustomerID());
				prepaidCBussiness.setOldCardno(prepaidC.getCardNo());
				prepaidCBussiness.setCardno(prepaidC.getCardNo());
				prepaidCBussiness.setState("7");
				prepaidCBussiness.setPlaceid(prepaidC.getSalePlaceId());
				prepaidCBussiness.setOperid(prepaidC.getSaleOperId());
				prepaidCBussiness.setOperName(prepaidC.getOperName());
				prepaidCBussiness.setOperNo(prepaidC.getOperNo());
				prepaidCBussiness.setPlaceName(prepaidC.getPlaceName());
				prepaidCBussiness.setPlaceNo(prepaidC.getPlaceNo());
				prepaidCBussiness.setTradetime(new Date());

				prepaidCBussinessDao.save(prepaidCBussiness);

				// 客服给清算		//原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				scinfo.setState("1");
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/

				// 清算系统用的黑色单
				//VehicleInfo vehicleInfo = vehicleInfoService.findByPrepaidCNo(prepaidC.getCardNo());
				/*VehicleInfo vehicleInfo = vehicleInfoDao.findByPrepaidCardNo(prepaidC.getCardNo());
				TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(null, null, prepaidC.getCardNo(),
						null, null, vehicleInfo.getVehiclePlate(), 2, new Date(), 0, new Date());
				tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/

				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
						null, " ", null, 2, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
						null, " ", null, 2, new Date(), 0, new Date());
				saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/

				//没用了
				/*DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//
				saveDarkList(prepaidC,darkList,"2", "1");*/


				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				serviceWater.setSerType("507");//储值卡挂失
				//serviceWater.setAmt(prepaidC.getCost_());//应收金额
				//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				//serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark(SystemTypeEnum.getName(systemType)+"：储值卡挂失");
				serviceWaterDao.save(serviceWater);

				//保存客服挂失黑名单流水信息		给铭鸿
				blackListService.saveCardLost(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
						, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
						prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(),
						new Date());


				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.loss.getValue()),
						CardStateSendSerTypeEnum.loss.getValue(), prepaidC.getCardNo(), "22", userType);


				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error("储值卡挂失操作失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("储值卡挂失操作失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 解挂
	 * 
	 * @author zyh
	 */
	public boolean unLock(PrepaidC prepaidC) {
		try {
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("2");
			unifiedParam.setPlaceId(prepaidC.getSalePlaceId());
			unifiedParam.setOperId(prepaidC.getSaleOperId());
			unifiedParam.setOperName(prepaidC.getOperName());
			unifiedParam.setOperNo(prepaidC.getOperNo());
			unifiedParam.setPlaceName(prepaidC.getPlaceName());
			unifiedParam.setPlaceNo(prepaidC.getPlaceNo());
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 储值卡业务记录
				PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
				prepaidCBussiness.setUserid(prepaidC.getCustomerID());
				prepaidCBussiness.setOldCardno(prepaidC.getCardNo());
				prepaidCBussiness.setCardno(prepaidC.getCardNo());
				prepaidCBussiness.setPlaceid(prepaidC.getSalePlaceId());
				prepaidCBussiness.setOperid(prepaidC.getSaleOperId());
				prepaidCBussiness.setOperName(prepaidC.getOperName());
				prepaidCBussiness.setOperNo(prepaidC.getOperNo());
				prepaidCBussiness.setPlaceName(prepaidC.getPlaceName());
				prepaidCBussiness.setPlaceNo(prepaidC.getPlaceNo());
				prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardUnLoss.getValue());
				prepaidCBussiness.setTradetime(new Date());

				prepaidCBussinessDao.save(prepaidCBussiness);
				
				// 客服给清算		//原清算数据，没用了
				/*BigDecimal SEQ_CSMS_SCINFO = sequenceUtil.getSequence("SEQ_CSMS_SCINFO");
				Customer cus = customerDao.findById(prepaidC.getCustomerID());
				SCinfo scinfo = new SCinfo();
				scinfo.setCardNo(prepaidC.getCardNo());
				scinfo.setState("2");
				scinfo.setUserNo(cus.getUserNo());
				scinfo.setBusinessTime(new Date());
				scinfo.setId(Long.valueOf(SEQ_CSMS_SCINFO.toString()));
				sCinfoDao.save(scinfo,cus.getId());*/
				
				// 清算系统用的黑色单		//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
						null, " ", null, 1, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
						null, " ", null, 1, new Date(), 0, new Date());
				saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//保存至黑名单流水表中，原黑名单表取消
//				DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
//				//黑变白的数据状态
//				saveDarkList(prepaidC,darkList,"1", "0");
				//保存黑名单流水解除挂失黑名单	给铭鸿
				blackListService.saveCardNoLost(Constant.PREPAIDTYPE, prepaidC.getCardNo(), prepaidCBussiness.getTradetime()
						, "2", prepaidCBussiness.getOperid(), prepaidCBussiness.getOperNo(), prepaidCBussiness.getOperName(),
						prepaidCBussiness.getPlaceid(), prepaidCBussiness.getPlaceNo(), prepaidCBussiness.getPlaceName(), 
						new Date());
				
				
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(prepaidC.getCardNo());
				serviceWater.setSerType("508");//储值卡解挂
				//serviceWater.setAmt(prepaidC.getCost_());//应收金额
				//serviceWater.setAulAmt(prepaidC.getRealCost());//实收金额
				//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
				//serviceWater.setFlowState("1");//正常
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWater.setRemark("自营客服系统：储值卡解挂");
				serviceWaterDao.save(serviceWater);

				//储值卡解挂回执
				PreCardUnLossReceipt preCardUnLossReceipt = new PreCardUnLossReceipt();
				PrepaidCBussiness preCardLossBusiness = this.prepaidCBussinessDao.findByCardNoAndState_(prepaidCBussiness.getCardno(),PrepaidCardBussinessTypeEnum.preCardLoss.getValue());	//找到最近挂失的记录
				preCardUnLossReceipt.setTitle("储值卡解挂回执");
				preCardUnLossReceipt.setHandleWay("凭密码办理");
				preCardUnLossReceipt.setLostReceiptNo(this.receiptDao.findByBusIdAndPTC(preCardLossBusiness.getId(),ReceiptParentTypeCodeEnum.prepaidC.getValue()).getReceiptNo());	//挂失记录的回执
				preCardUnLossReceipt.setPreCardNo(preCardLossBusiness.getCardno());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardUnLoss.getValue());
				receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardUnLoss.getName());
				this.saveReceipt(receipt,prepaidCBussiness,preCardUnLossReceipt,customer);
				
				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
					userType = "0";//个人
				}else{
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(prepaidCBussiness.getTradetime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), 
						CardStateSendSerTypeEnum.unloss.getValue(), prepaidC.getCardNo(), "22", userType);
				
				
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error("储值卡解挂操作失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("储值卡解挂操作失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 储值卡启用
	 * 
	 * @author zyh
	 */
	public void saveStart(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 1 where id =  "+carObuCardInfo.getVehicleID());

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("7");
			unifiedParam.setPlaceId(prepaidC.getSalePlaceId());
			unifiedParam.setOperId(prepaidC.getSaleOperId());
			unifiedParam.setOperName(prepaidC.getOperName());
			unifiedParam.setOperNo(prepaidC.getOperNo());
			unifiedParam.setPlaceName(prepaidC.getPlaceName());
			unifiedParam.setPlaceNo(prepaidC.getPlaceNo());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			
			//补充储值卡挂起的业务记录
			PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
			
			prepaidCBussiness.setOperid(vehicleBussiness.getOperID());
			prepaidCBussiness.setOperNo(vehicleBussiness.getOperNo());
			prepaidCBussiness.setOperName(vehicleBussiness.getOperName());
			prepaidCBussiness.setPlaceid(vehicleBussiness.getPlaceID());
			prepaidCBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			prepaidCBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			prepaidCBussiness.setTradetime(vehicleBussiness.getCreateTime());
			
			prepaidCBussiness.setUserid(vehicleBussiness.getCustomerID());
			prepaidCBussiness.setCardno(prepaidC.getCardNo());
			prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardAbled.getValue());//储值卡解除挂起
			prepaidCBussiness.setRealprice(new BigDecimal("0"));
			prepaidCBussiness.setTradestate("2");//完成
			prepaidCBussiness.setSuit(prepaidC.getSuit());
			
			prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);
			
			if(BlackFlagEnum.black.getValue().equals(prepaidC.getBlackFlag())){
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
						null, " ", null, 1, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
						null, " ", null, 1, new Date(), 0, new Date());
				saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//
				//DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//启用状态应该是为0吧？？？？
				//saveDarkList(prepaidC,darkList,"1", "0");
			}
			//原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseList.setCardType(1);
			userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
			
			//给铭鸿
			blackListService.saveRelieveStopUseCard(Constant.PREPAIDTYPE, prepaidC.getCardNo(), vehicleBussiness.getCreateTime(), 
					"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
					vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
			
			
			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			//写给铭鸿的清算数据：卡片状态信息
			String userType = "";
			if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), 
					CardStateSendSerTypeEnum.startCard.getValue(), prepaidC.getCardNo(), "22", userType);
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), prepaidC.getCardNo(), 
					"22", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "储值卡解除挂起");


			//储值卡解除挂起回执
			CardEnabledReceipt cardEnabledReceipt = new CardEnabledReceipt();
			cardEnabledReceipt.setTitle("卡片解除挂起回执");
			cardEnabledReceipt.setHandleWay("凭密码办理");
			cardEnabledReceipt.setCardNo(prepaidC.getCardNo());
			cardEnabledReceipt.setCardType("储值卡");
			cardEnabledReceipt.setCardSuit(SuitEnum.getNameByValue(prepaidC.getSuit()));
			cardEnabledReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			cardEnabledReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
			cardEnabledReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
			cardEnabledReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicleInfo.getNSCVehicleType()));
			cardEnabledReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
			cardEnabledReceipt.setVehicleOwner(vehicleInfo.getOwner());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardAbled.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardAbled.getName());
			this.saveReceipt(receipt,prepaidCBussiness,cardEnabledReceipt,customer);

			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setSerType(ServiceWaterSerType.cardStart.getValue());
			serviceWater.setRemark("自营网点系统：储值卡解除挂起");
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			
			serviceWater.setOperTime(new Date());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWaterDao.save(serviceWater);
			
			
		} catch (ApplicationException e) {
			logger.error("卡牌启用操作失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("卡牌启用操作失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 停用
	 * 
	 * @author zyh
	 */
	public void saveStop(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 0 where id =  "+carObuCardInfo.getVehicleID());
			
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setPrepaidC(prepaidC);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("8");
			unifiedParam.setPlaceId(prepaidC.getSalePlaceId());
			unifiedParam.setOperId(prepaidC.getSaleOperId());
			unifiedParam.setOperName(prepaidC.getOperName());
			unifiedParam.setOperNo(prepaidC.getOperNo());
			unifiedParam.setPlaceName(prepaidC.getPlaceName());
			unifiedParam.setPlaceNo(prepaidC.getPlaceNo());
			if (unifiedInterfaceService.savePrepaidCState(unifiedParam)) {
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			//receiptDao.saveByVehicleBussiness(vehicleBussiness);
			
			
			//补充储值卡挂起的业务记录
			PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
			prepaidCBussiness.setId(Long.valueOf(PrePaidC_bussiness_NO.toString()));
			
			prepaidCBussiness.setOperid(vehicleBussiness.getOperID());
			prepaidCBussiness.setOperNo(vehicleBussiness.getOperNo());
			prepaidCBussiness.setOperName(vehicleBussiness.getOperName());
			prepaidCBussiness.setPlaceid(vehicleBussiness.getPlaceID());
			prepaidCBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			prepaidCBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			prepaidCBussiness.setTradetime(vehicleBussiness.getCreateTime());
			
			prepaidCBussiness.setUserid(vehicleBussiness.getCustomerID());
			prepaidCBussiness.setCardno(prepaidC.getCardNo());
			prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardDisabled.getValue());//储值卡挂起
			prepaidCBussiness.setRealprice(new BigDecimal("0"));
			prepaidCBussiness.setTradestate("2");//完成
			prepaidCBussiness.setSuit(prepaidC.getSuit());
			
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			
			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());
			String userType = "";
			if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			if(prepaidC.getState().equals("1")){//下黑名单
				//原清算数据，没用了
				/*TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(0, null, prepaidC.getCardNo(),
						null, " ", null, 5, new Date(), 0, new Date());
				TollCardBlackDet tollCardBlackDet= new TollCardBlackDet(0, null, prepaidC.getCardNo(),
						null, " ", null, 5, new Date(), 0, new Date());
				saveTollCardBlack(prepaidC, tollCardBlackDet, tollCardBlackDetSend);*/
				
				///////   没用了
				/*DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//
				saveDarkList(prepaidC,darkList,"5", "1");*/
				
				//保存黑名单流水挂起表(禁用+生成方式‘人工办理’)	给铭鸿     （注解：解除挂起根据  禁用+‘人工办理’ 就可以判断黑名单是无卡挂起产生的）
				blackListService.saveCardStopUse(Constant.PREPAIDTYPE, prepaidC.getCardNo(), vehicleBussiness.getCreateTime(), 
						"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
				
				//写给铭鸿的清算数据：卡片状态信息
				//无卡挂起
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()), 
						CardStateSendSerTypeEnum.stopWithOutCard.getValue(), prepaidC.getCardNo(), "22", userType);
			}else{
				//写给铭鸿的清算数据：卡片状态信息
				//有卡挂起
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(PrepaidCardStateEnum.disabled.getIndex()), 
						CardStateSendSerTypeEnum.stopWithCard.getValue(), prepaidC.getCardNo(), "22", userType);
				
			}
			
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
					"22", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "储值卡挂起");
			
			//原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseList.setCardType(1);
			userInfoBaseListDao.save(userInfoBaseList, prepaidC);*/
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			if(prepaidC.getState().equals("1")){
				//无卡
				serviceWater.setSerType(ServiceWaterSerType.stopWithoutCard.getValue());//卡片无卡挂起
				serviceWater.setRemark("自营网点系统：储值卡无卡挂起");
			}else{
				//有卡
				serviceWater.setSerType(ServiceWaterSerType.cardStop.getValue());//卡片有卡挂起
				serviceWater.setRemark("自营网点系统：储值卡有卡挂起");
			}
			
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			
			serviceWater.setOperTime(new Date());
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWaterDao.save(serviceWater);

			//储值卡挂起回执
			CardDisabledReceipt cardDisabledReceipt = new CardDisabledReceipt();
			cardDisabledReceipt.setTitle("卡片挂起回执");
			cardDisabledReceipt.setHandleWay("凭密码办理");
			cardDisabledReceipt.setCardNo(prepaidC.getCardNo());
			cardDisabledReceipt.setCardType("储值卡");
			Receipt receipt = new Receipt();
			receipt.setTypeCode(PrepaidCardBussinessTypeEnum.preCardDisabled.getValue());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.preCardDisabled.getName());
			this.saveReceipt(receipt,prepaidCBussiness,cardDisabledReceipt,customer);
			
		} catch (ApplicationException e) {
			logger.error("卡牌停用操作失败，储值卡id:" + prepaidC.getId(), e);
			throw new ApplicationException("卡牌停用操作失败，储值卡id:" + prepaidC.getId());
		}
	}

	/**
	 * 记帐卡启用
	 * 
	 * @author zyh
	 */
	public void saveStart(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
//			Customer customer = customerService.findById(vehicleBussiness.getCustomerID());
//			VehicleInfo vehicleInfo = vehicleInfoService.findByPlateAndColor(customer, vehicleBussiness.getVehiclePlate(), vehicleBussiness.getVehicleColor());
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 1 where id =  "+carObuCardInfo.getVehicleID());

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("7");
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
			
			accountCBussiness.setOperId(vehicleBussiness.getOperID());
			accountCBussiness.setOperNo(vehicleBussiness.getOperNo());
			accountCBussiness.setOperName(vehicleBussiness.getOperName());
			accountCBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			accountCBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			accountCBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			accountCBussiness.setTradeTime(vehicleBussiness.getCreateTime());
			
			accountCBussiness.setUserId(vehicleBussiness.getCustomerID());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountId(accountCInfo.getAccountId());
			accountCBussiness.setState(AccountCBussinessTypeEnum.accCardStart.getValue());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			accountCBussiness.setSuit(accountCInfo.getSuit());
			accountCBussiness.setLastState(AccountCardStateEnum.nomal.getIndex());
		
			accountCBussinessDao.saveWithOutReceipt(accountCBussiness);
			
			CardHolder cardHolder = cardHolderService.findCardHolderByCardNo(accountCInfo.getCardNo());
			// 联营卡停用回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("3");
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStart.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStart.getName());
			receipt.setBusinessId(accountCBussiness.getId());
			receipt.setOperId(accountCInfo.getIssueOperId());
			receipt.setOperNo(accountCInfo.getOperNo());
			receipt.setOperName(accountCInfo.getOperName());
			receipt.setPlaceId(accountCInfo.getIssuePlaceId());
			receipt.setPlaceNo(accountCInfo.getPlaceNo());
			receipt.setPlaceName(accountCInfo.getPlaceName());
			receipt.setCreateTime(new Date());
			receipt.setOrgan(cardHolder.getName());
			UnStopReceipt unStopReceipt = new UnStopReceipt();
			unStopReceipt.setTitle("卡片挂起回执");
			unStopReceipt.setHandleWay("凭资料办理");
			unStopReceipt.setName(cardHolder.getName());
			unStopReceipt.setLinkTel(cardHolder.getPhoneNum());
			unStopReceipt.setMobileNum(cardHolder.getMobileNum());
			unStopReceipt.setLinkMan(cardHolder.getLinkMan());
			unStopReceipt.setLinkAddr(cardHolder.getLinkAddr());
			unStopReceipt.setCardNo(accountCInfo.getCardNo());
			unStopReceipt.setCardType("记账卡");
			
			unStopReceipt.setVehiclePlate(vehicleBussiness.getVehiclePlate());
			for (int i = 0; i < vehicleColorEnums.length; i++) {
				if (vehicleColorEnums[i].getValue().equals(vehicleBussiness.getVehicleColor())) {
					unStopReceipt.setVehicleColor((vehicleColorEnums[i].getName()));
				} // if
			} // for
			if ("1".equals(accountCInfo.getSuit())) {
				unStopReceipt.setSuit("套装");
			} else if ("0".equals(accountCInfo.getSuit())) {
				unStopReceipt.setSuit("单卡");
			} // if
			unStopReceipt.setWeightsOrSeats(vehicleInfo.getVehicleWeightLimits());
			for (int i = 0; i < vehicleTypeEnums.length; i++) {
				if (vehicleTypeEnums[i].getValue().equals(vehicleInfo.getVehicleType())) {
					unStopReceipt.setVehicleType(vehicleTypeEnums[i].getName());
				} // if
			} // for
			unStopReceipt.setOwner(vehicleInfo.getOwner());
			for (int i = 0; i < nscVehicleTypeEnum.length; i++) {
				if (nscVehicleTypeEnum[i].getValue().equals(vehicleInfo.getNSCVehicleType())) {
					unStopReceipt.setNscVehicleType(nscVehicleTypeEnum[i].getName());
				} // if
			} // for
			receipt.setContent(JSONObject.fromObject(unStopReceipt).toString());
			receiptDao.saveReceipt(receipt);
						
//			黑名单
			if(BlackFlagEnum.black.getValue().equals(accountCInfo.getBlackFlag())){
				//原清算数据，没用了
				/*TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
				TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
				accountCService.saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				
				//
				//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
				//
				//accountCService.saveDarkList(accountCInfo,darkList,"1","0");
				
			}
			
			//给铭鸿
			blackListService.saveRelieveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), vehicleBussiness.getCreateTime(), 
					"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
					vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
			
			//写给铭鸿的清算数据：卡片状态信息
			String userType = "";
			if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), 
					CardStateSendSerTypeEnum.startCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCInfo.getCardNo(), 
					"23", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "记帐卡解除挂起");
			
			
			//原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseList.setCardType(2);
			userInfoBaseListDao.save(userInfoBaseList, accountCInfo, null);*/
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			/*VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicle.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
			vehicleBussiness.setCardNo(accountCInfo.getCardNo());
			vehicleBussiness.setCardType("2");*///记帐卡
			//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
			/*vehicleBussiness.setType("54");//启用
			vehicleBussiness.setPlaceID(accountCInfo.getIssuePlaceId());
			vehicleBussiness.setOperID(accountCInfo.getIssueOperId());
			vehicleBussiness.setOperName(accountCInfo.getOperName());
			vehicleBussiness.setOperNo(accountCInfo.getOperNo());
			vehicleBussiness.setPlaceName(accountCInfo.getPlaceName());
			vehicleBussiness.setPlaceNo(accountCInfo.getPlaceNo());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("记帐卡启用");
			
			vehicleBussinessDao.save(vehicleBussiness);*/

			//记帐卡解除挂起回执
//			CardEnabledReceipt cardEnabledReceipt = new CardEnabledReceipt();
//			cardEnabledReceipt.setTitle("卡片解除挂起回执");
//			cardEnabledReceipt.setHandleWay("凭密码办理");
//			cardEnabledReceipt.setCardNo(accountCInfo.getCardNo());
//			cardEnabledReceipt.setCardType("记帐卡");
//			cardEnabledReceipt.setCardSuit(SuitEnum.getNameByValue(accountCInfo.getSuit()));
//			cardEnabledReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
//			cardEnabledReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
//			cardEnabledReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
//			cardEnabledReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicleInfo.getNSCVehicleType()));
//			cardEnabledReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
//			cardEnabledReceipt.setVehicleOwner(vehicleInfo.getOwner());
//			Receipt receipt = new Receipt();
//			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStart.getValue());
//			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStart.getName());
//			this.saveReceiptForAccCard(receipt,accountCBussiness,cardEnabledReceipt,customer);

			
			//客户服务流水
//			ServiceWater serviceWater = new ServiceWater();
//			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
//			if(customer!=null)serviceWater.setCustomerId(customer.getId());
//			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
//			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
//			serviceWater.setCardNo(accountCInfo.getCardNo());
//			serviceWater.setSerType(ServiceWaterSerType.cardStart.getValue());
//			serviceWater.setRemark("自营网点系统：记帐卡解除挂起");
//			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
//			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
//			serviceWater.setOperTime(new Date());
//			serviceWater.setOperId(accountCBussiness.getOperId());
//			serviceWater.setOperNo(accountCBussiness.getOperNo());
//			serviceWater.setOperName(accountCBussiness.getOperName());
//			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
//			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
//			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
//			serviceWaterDao.save(serviceWater);
			
			
		} catch (ApplicationException e) {
			logger.error("卡牌启用操作失败，记帐卡id:" + accountCInfo.getId(), e);
			throw new ApplicationException("卡牌启用操作失败，记帐卡id:" + accountCInfo.getId());
		}
	}

	/**
	 * 记帐卡停用
	 * 
	 * @author zyh
	 */
	public void saveStop(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 0 where id =  "+carObuCardInfo.getVehicleID());
			
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("8");
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			//receiptDao.saveByVehicleBussiness(vehicleBussiness);
			
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
			accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
			
			accountCBussiness.setOperId(vehicleBussiness.getOperID());
			accountCBussiness.setOperNo(vehicleBussiness.getOperNo());
			accountCBussiness.setOperName(vehicleBussiness.getOperName());
			accountCBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			accountCBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			accountCBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			accountCBussiness.setTradeTime(vehicleBussiness.getCreateTime());
			
			accountCBussiness.setUserId(vehicleBussiness.getCustomerID());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountId(accountCInfo.getAccountId());
			accountCBussiness.setState(AccountCBussinessTypeEnum.accCardStop.getValue());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			accountCBussiness.setSuit(accountCInfo.getSuit());
			accountCBussiness.setLastState(AccountCardStateEnum.disabled.getIndex());
		
			accountCBussinessDao.save(accountCBussiness);
			
			VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			Customer customer = customerDao.findById(accountCInfo.getCustomerId());
			CardHolder cardHolder = cardHolderService.findCardHolderByCardNo(accountCInfo.getCardNo());
			String userType = "";
			if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			
			if(accountCInfo.getState().equals("1")){
				
				//保存黑名单流水挂起表(禁用+生成方式‘人工办理’)	给铭鸿     （注解：解除挂起根据  禁用+‘人工办理’ 就可以判断黑名单是无卡挂起产生的）
				blackListService.saveCardStopUse(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), vehicleBussiness.getCreateTime(), 
						"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
				//
				//accountCService.saveDarkList(accountCInfo,darkList,"5","1");
				
				//写给铭鸿的清算数据：卡片状态信息
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()), 
						CardStateSendSerTypeEnum.stopWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			}else{
				//写给铭鸿的清算数据：卡片状态信息
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()), 
						CardStateSendSerTypeEnum.stopWithCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			}
			
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(), 
					"23", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂起");
			
			
			
			//原清算数据，没用了
			/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
			userInfoBaseList.setNetNo("4401");
			//userInfoBaseList.setIssuerId("");发行方唯一标识
			//userInfoBaseList.setAgent();发行代理单位编码
			userInfoBaseList.setCardType(2);
			userInfoBaseListDao.save(userInfoBaseList, accountCInfo, null);*/

			//记帐卡挂起回执
//			CardDisabledReceipt cardDisabledReceipt = new CardDisabledReceipt();
//			cardDisabledReceipt.setTitle("卡片挂起回执");
//			cardDisabledReceipt.setHandleWay("凭密码办理");
//			cardDisabledReceipt.setCardNo(accountCInfo.getCardNo());
//			cardDisabledReceipt.setCardType("记帐卡");
//			Receipt receipt = new Receipt();
//			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStop.getValue());
//			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStop.getName());
//			this.saveReceiptForAccCard(receipt,accountCBussiness,cardDisabledReceipt,customer);
			
			// 联营卡停用回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("3");
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStop.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStop.getName());
			receipt.setBusinessId(accountCBussiness.getId());
			receipt.setOperId(accountCInfo.getIssueOperId());
			receipt.setOperNo(accountCInfo.getOperNo());
			receipt.setOperName(accountCInfo.getOperName());
			receipt.setPlaceId(accountCInfo.getIssuePlaceId());
			receipt.setPlaceNo(accountCInfo.getPlaceNo());
			receipt.setPlaceName(accountCInfo.getPlaceName());
			receipt.setCreateTime(new Date());
			receipt.setOrgan(cardHolder.getName());
			InitiativeStopReceipt initiativeStopReceipt = new InitiativeStopReceipt();
			initiativeStopReceipt.setTitle("卡片挂起回执");
			initiativeStopReceipt.setHandleWay("凭资料办理");
			initiativeStopReceipt.setName(cardHolder.getName());
			initiativeStopReceipt.setLinkTel(cardHolder.getPhoneNum());
			initiativeStopReceipt.setMobileNum(cardHolder.getMobileNum());
			initiativeStopReceipt.setLinkMan(cardHolder.getLinkMan());
			initiativeStopReceipt.setLinkAddr(cardHolder.getLinkAddr());
			initiativeStopReceipt.setCardNo(accountCInfo.getCardNo());
			initiativeStopReceipt.setCardType("记账卡");
			receipt.setContent(JSONObject.fromObject(initiativeStopReceipt).toString());
			receiptDao.saveReceipt(receipt);
			
			
		} catch (ApplicationException e) {
			logger.error("卡牌停用操作失败，记帐卡id:" + accountCInfo.getId(), e);
			throw new ApplicationException("卡牌停用操作失败，记帐卡id:" + accountCInfo.getId());
		}
	}

	/**
	 * 撤销充值登记 备注：撤销的是充值登记明细，并退回相应的金额
	 * 
	 * @param addRegDetail
	 * @author lizhilin
	 * @return boolean
	 */
	public boolean saveCancelAddReg(AddRegDetail addRegDetail) {

		try {
			// 客服流水 TODO
			UnifiedParam unifiedParam = new UnifiedParam();
			PrepaidC prepaidC = prepaidCDao.findByPrepaidCNo(addRegDetail.getCardNo());

			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(prepaidC.getCustomerID());
			unifiedParam.setOperId(addRegDetail.getAddOperID());
			unifiedParam.setPlaceId(addRegDetail.getAddPlaceID());
			unifiedParam.setChangePrice(addRegDetail.getFee());
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType("31");
			unifiedParam.setPlaceId(addRegDetail.getAddPlaceID());
			unifiedParam.setOperId(addRegDetail.getAddOperID());
			unifiedParam.setOperName(addRegDetail.getOperName());
			unifiedParam.setOperNo(addRegDetail.getOperNo());
			unifiedParam.setPlaceName(addRegDetail.getPlaceName());
			unifiedParam.setPlaceNo(addRegDetail.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				addRegDetail.setFlag(AddRegDetailStateEnum.refund.getValue());
				addRegDetailDao.update(addRegDetail);
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error("撤销充值登记失败", e);
			throw new ApplicationException("撤销充值登记失败");
		}

	}
	//清算接口
	/*private void saveTollCardBlack(PrepaidC prepaidC,TollCardBlackDet tollCardBlackDet,TollCardBlackDetSend tollCardBlackDetSend){
		
		try {
			VehicleInfo vehicleInfo=vehicleInfoDao.findByPrepaidCardNo(prepaidC.getCardNo());
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
			logger.error(e.getMessage()+"保存清算黑名单数据失败"+prepaidC.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败"+prepaidC.getCardNo());
		}
	}*/
	
	/**
	 * @Description:TODO
	 * @param accountCInfo
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
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}
			
		} catch (ApplicationException e) {
			logger.error("保存清算黑名单数据失败"+prepaidC.getCardNo(), e);
			throw new ApplicationException("保存清算数黑名单据失败"+prepaidC.getCardNo());
		}
	}
	
/*	@SuppressWarnings({ "unused", "null"})
	private boolean saveAndDel(List<Map<String, Object>>  returnFees,String type,PrepaidCBussiness prepaidBussiness){
		boolean flag=false;
		if(returnFees !=null || returnFees.size()>0){
			Map<String, Object> m=null;
			for(int i=0;i<returnFees.size();i++){
				m=returnFees.get(i);
				if(m.get("FEETYPE").toString().equals(type)){//1:回退金额					
				//调用webservice验证是否有效，有效就搬到客户系统，并删除	
					if(true){
						tbScreturnSendDao.deleteById(Long.parseLong(m.get("ID").toString()));
						returnFeeDao.saveByMap(m, prepaidBussiness.getId());
					}else{
						flag=true;
						tbScreturnSendDao.deleteById(Long.parseLong(m.get("ID").toString()));
						return flag;
					}
				}
				
			}
		}
		return flag;
	}*/
	
	private boolean saveRechargeInfo(RechargeInfo newRechargeInfo,MainAccountInfo mainAccountInfo){
		BigDecimal seq = sequenceUtil
				.getSequence("SEQ_CSMSRechargeInfo_NO");
		newRechargeInfo.setId(Long.parseLong(seq.toString()));

		UnifiedParam unifiedParam = new UnifiedParam();
		unifiedParam.setType(AccChangeTypeEnum.recharge.getValue());
		unifiedParam.setMainAccountInfo(mainAccountInfo);
		unifiedParam.setNewRechargeInfo(newRechargeInfo);
		unifiedParam.setOperId(newRechargeInfo.getOperId());
		unifiedParam.setPlaceId(newRechargeInfo.getPlaceId());
		unifiedParam.setOperName(newRechargeInfo.getOperName());
		unifiedParam.setOperNo(newRechargeInfo.getOperNo());
		unifiedParam.setPlaceName(newRechargeInfo.getPlaceName());
		unifiedParam.setPlaceNo(newRechargeInfo.getPlaceNo());
		unifiedParam.setFlag(AccChangeTypeEnum.imRecharge.getValue());

		if(!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
			return false;
		}

		MainAccountInfo tempAccount = mainAccountInfoDao.findById(mainAccountInfo.getId());
		newRechargeInfo.setMainAccountId(tempAccount.getId());
		newRechargeInfo.setMainId(tempAccount.getMainId());
		newRechargeInfo.setBalance(tempAccount.getBalance().add(newRechargeInfo.getTakeBalance()));
		newRechargeInfo.setAvailableBalance(tempAccount.getAvailableBalance().add(newRechargeInfo.getTakeBalance()));
		newRechargeInfo.setPreferentialBalance(tempAccount.getPreferentialBalance());
		newRechargeInfo.setFrozenBalance(tempAccount.getFrozenBalance());
		newRechargeInfo.setAvailableRefundBalance(tempAccount.getAvailableRefundBalance());
		newRechargeInfo.setRefundApproveBalance(tempAccount.getRefundApproveBalance());
		newRechargeInfo.setMemo(AccChangeTypeEnum.imRecharge.getName());


		Customer customer = customerService.findById(tempAccount.getMainId());
		newRechargeInfo.setIsCorrect("0");
		newRechargeInfo.setPayMember(customer.getOrgan());

		rechargeInfoDao.save(newRechargeInfo);

		AccountBussiness accountBussiness = new AccountBussiness();
		accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
		accountBussiness.setUserId(newRechargeInfo.getMainId());
		accountBussiness.setState(AccountBussinessTypeEnum.recharge.getValue());
		accountBussiness.setRealPrice(newRechargeInfo.getTakeBalance());
		accountBussiness.setTradeTime(new Date());
		accountBussiness.setOperId(newRechargeInfo.getOperId());
		accountBussiness.setOperNo(newRechargeInfo.getOperNo());
		accountBussiness.setOperName(newRechargeInfo.getOperName());
		accountBussiness.setPlaceId(newRechargeInfo.getPlaceId());
		accountBussiness.setPlaceNo(newRechargeInfo.getPlaceNo());
		accountBussiness.setPlaceName(newRechargeInfo.getPlaceName());
		accountBussiness.setBussinessId(newRechargeInfo.getId());
		accountBussiness.setHisSeqId(tempAccount.getHisSeqId());

		accountBussinessDao.save(accountBussiness);
		return true;
	}

	public boolean updateAddRegs(List<Map<String, String>> regPairUpdateList,List<Map<String, String>> regPairAddList,AddReg addReg,Customer customer) {
		try {
			addReg = addRegDao.findById(addReg.getId());
			// TODO 等待扣费操作 prepaidc
			BigDecimal addCount = new BigDecimal(0);
			BigDecimal total = new BigDecimal(0);
			//遍历修改了或者删除剩下了的明细
			for (Map<String, String> map:regPairUpdateList) {
				addCount = addCount.add(new BigDecimal(map.get("fee")));
				total = total.add(new BigDecimal(map.get("fee")));
			}
			//遍历新增的明细
			for (Map<String, String> map:regPairAddList) {
				addCount = addCount.add(new BigDecimal(map.get("fee")));
				total = total.add(new BigDecimal(map.get("fee")));
			}
			total = total.multiply(new BigDecimal("100"));
			//原来的充值金额
			
			BigDecimal oldCount = new BigDecimal(0);
			//这批旧的明细
			List<AddRegDetail> oldDetailList = addRegDetailDao.findDetailByRegID(addReg.getId());
			if(oldDetailList != null && oldDetailList.size() > 0){
				for (int i = 0; i < oldDetailList.size(); i++) {
					oldCount = oldCount.add(oldDetailList.get(i).getFee());
				}
			}
			
			oldCount = oldCount.multiply(new BigDecimal("0.01"));
			// 元转分：
			addCount = addCount.subtract(oldCount).multiply(new BigDecimal("100"));//可能为负数

			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			//新增的字段（携带到接口，不能用作mainAccountInfo的update）
			mainAccountInfo.setPlaceId(customer.getPlaceId());
			mainAccountInfo.setOperId(customer.getOperId());
			mainAccountInfo.setOperName(customer.getOperName());
			mainAccountInfo.setOperNo(customer.getOperNo());
			mainAccountInfo.setPlaceName(customer.getPlaceName());
			mainAccountInfo.setPlaceNo(customer.getPlaceNo());
			
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAddCount(addCount);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType("10");
			unifiedParam.setPlaceId(customer.getPlaceId());
			unifiedParam.setOperId(customer.getOperId());
			unifiedParam.setOperName(customer.getOperName());
			unifiedParam.setOperNo(customer.getOperNo());
			unifiedParam.setPlaceName(customer.getPlaceName());
			unifiedParam.setPlaceNo(customer.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				//新增历史记录
				AddRegHis addRegHis = new AddRegHis(addReg);
				Long addRegHisId = sequenceUtil.getSequenceLong("SEQ_csmsaddreghis_NO");
				addRegHis.setId(addRegHisId);
				addRegHisDao.save(addRegHis);
				
				addReg.setTotalFee(total);//新登记金额为原来的+改变的
				addReg.setRegistrationTime(new Date());
				addReg.setHisSeqId(addRegHisId);
				//addReg.setAddStyle("1");
				addRegDao.update(addReg);
				
				for(Map<String, String> addMap:regPairAddList){
					//这是新增的明细，可以直接新增
					AddRegDetail addRegDetailNew = new AddRegDetail();
					BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
					addRegDetailNew.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
					addRegDetailNew.setAddRegID(Long.valueOf(addReg.getId()));
					addRegDetailNew.setCardNo(addMap.get("cardNo"));
					addRegDetailNew.setAddStyle("1");
					addRegDetailNew.setFee(
							BigDecimal.valueOf(Double.valueOf(addMap.get("fee"))).multiply(new BigDecimal("100")));
					addRegDetailNew.setFlag(AddRegDetailStateEnum.normal.getValue());
					addRegDetailDao.save(addRegDetailNew);
				}
				boolean isDelete = true;
				if(oldDetailList != null && oldDetailList.size() > 0){
					for(AddRegDetail oldDetail:oldDetailList){
						//是否有删除掉的明细?
						for(Map<String, String> updateMap:regPairUpdateList){
							isDelete = true;
							if(oldDetail.getId().toString().equals(updateMap.get("id"))){
								//如果regPairUpdateList包含了这个id，此明细id就没有被删除。跳出此循环
								isDelete = false;
								//既然没有被删除，就要判断是否有被修改
								if(!updateMap.get("cardNo").equals(oldDetail.getCardNo()) || BigDecimal.valueOf(Double.valueOf(updateMap.get("fee"))).compareTo(oldDetail.getFee().multiply(new BigDecimal("0.01"))) != 0){
									//进来了则有被修改
									//将被修改的明细update为已退款
									oldDetail.setFlag("4");//已被退款
									addRegDetailDao.update(oldDetail);
									//新增一条明细
									AddRegDetail addRegDetailNew = new AddRegDetail();
									BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
									addRegDetailNew.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
									addRegDetailNew.setAddRegID(Long.valueOf(addReg.getId()));
									addRegDetailNew.setCardNo(updateMap.get("cardNo"));
									addRegDetailNew.setAddStyle("1");
									addRegDetailNew.setFee(
											BigDecimal.valueOf(Double.valueOf(updateMap.get("fee"))).multiply(new BigDecimal("100")));
									addRegDetailNew.setFlag(AddRegDetailStateEnum.normal.getValue());
									addRegDetailDao.save(addRegDetailNew);
								}
								break ;
							}
						}
						//这个id是被删除了得明细
						if(isDelete){
							//处理已被删除的明细id
							oldDetail.setFlag("4");//已被退款
							addRegDetailDao.update(oldDetail);
						}
					}
				}

				//修改后的充值登记明细
				List<AddRegDetail> addRegDetails = this.addRegDetailDao.findDetailByRegID(addReg.getId());
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				Map<String,Object> map = null;
				if(addRegDetails!=null){
					for(AddRegDetail temp : addRegDetails){
						map = new HashMap<String,Object>();
						map.put("cardNo",temp.getCardNo());
						map.put("fee",NumberUtil.get2Decimal(temp.getFee().doubleValue()*0.01));
						list.add(map);
					}
				}
				//储值卡充值登记修改回执
				AddRegReceipt addRegReceipt = new AddRegReceipt();
				addRegReceipt.setTitle("储值卡充值登记修改回执");
				addRegReceipt.setHandleWay("凭密码办理");
				addRegReceipt.setCardAndMoneyJsonData(JSONArray.fromObject(list).toString());
				addRegReceipt.setCustomerNo(customer.getUserNo());
				addRegReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
				addRegReceipt.setCustomerIdCode(customer.getIdCode());
				addRegReceipt.setCustomerName(customer.getOrgan());

				Receipt receipt = new Receipt();
				receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.prepaidC.getValue());
				receipt.setTypeCode(PrepaidCardBussinessTypeEnum.addRegUpdate.getValue());
				receipt.setTypeChName(PrepaidCardBussinessTypeEnum.addRegUpdate.getName());
				receipt.setCreateTime(new Date());
				receipt.setPlaceId(customer.getPlaceId());
				receipt.setPlaceNo(customer.getPlaceNo());
				receipt.setPlaceName(customer.getPlaceName());
				receipt.setOperId(customer.getOperId());
				receipt.setOperNo(customer.getOperNo());
				receipt.setOperName(customer.getOperName());
				receipt.setOrgan(customer.getOrgan());
				receipt.setContent(JSONObject.fromObject(addRegReceipt).toString());
				this.receiptDao.saveReceipt(receipt);
				
				/*List<String> cardList = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					cardList.add(list.get(i).getCardNo());
				}
				List<String> cardList1 = new ArrayList<String>();
				cardList1.addAll(cardList);
				List<String> cardList2 = new ArrayList<String>();
				cardList2.addAll(cardList);
				List<String> cardList3 = new ArrayList<String>();
				cardList3.addAll(cardList);
				
				
				Set<String> cardSet = addRegPair.keySet();
				Set<String> cardSet1 = new HashSet<String>();
				cardSet1.addAll(cardSet);
				Set<String> cardSet2 = new HashSet<String>();
				cardSet2.addAll(cardSet);
				Set<String> cardSet3 = new HashSet<String>();
				cardSet3.addAll(cardSet);
				
				
				cardSet1.removeAll(cardList1);
				for (String str : cardSet1) {  
					AddRegDetail addRegDetailNew = new AddRegDetail();
					BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
					addRegDetailNew.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
					addRegDetailNew.setAddRegID(Long.valueOf(addReg.getId()));
					addRegDetailNew.setCardNo(str);
					addRegDetailNew.setAddStyle("1");
					addRegDetailNew.setFee(
							BigDecimal.valueOf(Double.valueOf(addRegPair.get(str))).multiply(new BigDecimal("100")));
					addRegDetailNew.setFlag(AddRegDetailStateEnum.normal.getValue());
					addRegDetailDao.save(addRegDetailNew);
				}
				
				cardList2.removeAll(cardSet2);
				for (String string : cardList2) {
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).getCardNo().equals(string)){
							AddRegDetail detail = list.get(i);
							detail.setFlag("4");
							if (addRegDetailDao.update(detail) != 1) {
								throw new ApplicationException("充值登记记录["+detail.getId()+"]发生变更，处理失败");
							}
						}
					}
				}
				
				cardList3.retainAll(cardSet3);
				for (String str : cardList3) {  
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).getCardNo().equals(str)){
							if((list.get(i).getFee().multiply(new BigDecimal("0.01")).toString()).equals(addRegPair.get(str))){
								break;
							}else{
								AddRegDetail detail = list.get(i);
								detail.setFlag("4");
								if (addRegDetailDao.update(detail) != 1) {
									throw new ApplicationException("充值登记记录["+detail.getId()+"]发生变更，处理失败");
								}
								
								AddRegDetail addRegDetailNew = new AddRegDetail();
								BigDecimal Add_Reg_Detail_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_detail_NO");
								addRegDetailNew.setId(Long.valueOf(Add_Reg_Detail_NO.toString()));
								addRegDetailNew.setAddRegID(Long.valueOf(addReg.getId()));
								addRegDetailNew.setCardNo(str);
								addRegDetailNew.setAddStyle("1");
								addRegDetailNew.setFee(
										BigDecimal.valueOf(Double.valueOf(addRegPair.get(str))).multiply(new BigDecimal("100")));
								addRegDetailNew.setFlag(AddRegDetailStateEnum.normal.getValue());
								addRegDetailDao.save(addRegDetailNew);
							}
						}
					}
				}*/
				
				return true;
			}
		} catch (ApplicationException e) {
			logger.error("充值登记修改失败", e);
			throw new ApplicationException("充值登记修改失败");
		}
		return false;
	}
	
	/**
	 * 储值卡发行
	 * 
	 * @param
	 * @throws Exception
	 * @author wxm
	 */
	
	public String saveIssueForAMMS(PrepaidC prepaidC, ElectronicPurse electronicPurse, CarObuCardInfo carObuCardInfo,
			PrepaidCBussiness prepaidCBussiness, MainAccountInfo mainAccountInfo, BillGet billGet) {return null;}
	

	/**
	 * 保存回执(记帐卡)
	 * @param receipt 回执主要信息
	 * @param accountCBussiness 记帐卡业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceiptForAccCard(Receipt receipt,AccountCBussiness accountCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.accountC.getValue());
		receipt.setCreateTime(accountCBussiness.getTradeTime());
		receipt.setPlaceId(accountCBussiness.getPlaceId());
		receipt.setPlaceNo(accountCBussiness.getPlaceNo());
		receipt.setPlaceName(accountCBussiness.getPlaceName());
		receipt.setOperId(accountCBussiness.getOperId());
		receipt.setOperNo(accountCBussiness.getOperName());
		receipt.setOperName(accountCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param prepaidCBussiness 储值卡业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, PrepaidCBussiness prepaidCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.prepaidC.getValue());
		receipt.setCreateTime(prepaidCBussiness.getTradetime());
		receipt.setPlaceId(prepaidCBussiness.getPlaceid());
		receipt.setPlaceNo(prepaidCBussiness.getPlaceNo());
		receipt.setPlaceName(prepaidCBussiness.getPlaceName());
		receipt.setOperId(prepaidCBussiness.getOperid());
		receipt.setOperNo(prepaidCBussiness.getOperName());
		receipt.setOperName(prepaidCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	/**
	 * 获取服务项目name
	 * @param itemCodes 服务方式Codes
	 * @return
	 */
	private String getSerItemName(String itemCodes){
		String result = "";
		if(itemCodes==null){
		    return result;
        }
		for(String serItemCode: itemCodes.split(",")){
			String temp = org.apache.commons.lang.StringUtils.trim(serItemCode);
			if(!"".equals(temp)){
				result+=SerItemEnum.getName(temp)+"，";
			}
		}
		if(result.length()>0){
			result = result.substring(0,result.length()-1);
		}
		return result;
	}
}


