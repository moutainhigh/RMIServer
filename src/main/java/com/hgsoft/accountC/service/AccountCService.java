package com.hgsoft.accountC.service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.BailDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.dao.PaymentCardBlacklistRecvDao;
import com.hgsoft.clearInterface.dao.StopAcListDao;
import com.hgsoft.clearInterface.dao.StopPayRelieveApplyDao;
import com.hgsoft.clearInterface.entity.ACinfo;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.DBACardFlowCardTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowReadFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowSerTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
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
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.dao.JoinCardNoSectionDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardCanceLossReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardLossReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardReplaceNewcardReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardReplaceReceipt;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountCService implements IAccountCService {

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private IRelieveStopPayService relieveStopPayService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private CardSecondIssuedService cardSecondIssuedService;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;

	@Resource
	private AccountCApplyDao accountCApplyDao;
	/*@Resource
	private ACinfoDao aCinfoDao;
	@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/

	@Resource
	private VehicleInfoDao vehicleInfoDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;

	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	@Resource
	private DarkListDao darkListDao;

	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private StopAcListDao stopAcListDao;

	@Resource
	private ServiceWaterDao serviceWaterDao;

	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;

	@Resource
	private IBlackListService blackListService;

	@Resource
	private StopPayRelieveApplyDao stopPayRelieveApplyDao;

	@Resource
	private JoinCardNoSectionDao joinCardNoSectiondao;

	//铭鸿的黑名单全量表
	@Resource
	private PaymentCardBlacklistRecvDao paymentCardBlacklistRecvDao;
	@Resource
	private BailDao bailDao;
	@Resource
	private ReceiptDao receiptDao;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171017
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171017


	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Override
	public AccountCInfo findByCardNo(String cardNo) {
		return accountCDao.findByCardNo(cardNo);
	}

	@Override
	public boolean checkCardIsMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer, String cardNo) {
		return accountCDao.findByMacaoCardCustomerAndCardNo(macaoCardCustomer, cardNo);
	}

	@Override
	public AccountCInfo find(AccountCInfo accountCInfo) {
		return accountCDao.find(accountCInfo);
	}

	//挂失之后，补领新卡
	@Override
	public String saveReplaceCard(MainAccountInfo mainAccountInfo, AccountCInfo accountCInfo, AccountCInfo newAccountCInfo, AccountCBussiness accountCBussiness, ServiceFlowRecord serviceFlowRecord, String systemType, Map<String, Object> params) {
		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryService.omsInterface(newAccountCInfo.getCardNo(), "1", interfaceRecord, "issue",
					accountCBussiness.getPlaceId(), accountCBussiness.getOperId(),
					accountCBussiness.getOperName(), "1", "customPoint", null, newAccountCInfo.getRealCost(), "35", "");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			
			/*有偿领取（即工本费不为0），保修开始日期则为领取发卡当天，保修截止截止日期为保修开始日期加 营运参数年；无偿领取（即工本费为0），则沿用最近一张的保修开始日期和保修截止日期。
			有效启用时间、有效截止时间应该是取营运系统一发时设置的时间
			发行时间应该是办理业务的当前时间*/

			newAccountCInfo.setStartDate((Date) map.get("startDate"));
			newAccountCInfo.setEndDate((Date) map.get("endDate"));

			Calendar cal = Calendar.getInstance();
			;
			if (newAccountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
				//无偿，沿用原卡的发行时间作为维保起始时间
				cal.setTime(accountCInfo.getIssueTime());
			} else {

			}

			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info(paramMap);
			if (paramMap != null && "成功".equals((String) paramMap.get("message"))) {
				cal.add(Calendar.YEAR, Integer.parseInt((String) paramMap.get("value")));
			} else if (paramMap != null && !"成功".equals((String) paramMap.get("message"))) {
				return "获取营运维保周期参数失败:" + (String) paramMap.get("message");
			} else {
				return "获取营运维保周期参数失败";
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}

			newAccountCInfo.setMaintainTime(cal.getTime());

			//设置id
			newAccountCInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO"));

			//客服原发行操作

			//新增的字段
			mainAccountInfo.setOperName(accountCBussiness.getOperName());
			mainAccountInfo.setOperNo(accountCBussiness.getOperNo());
			mainAccountInfo.setPlaceName(accountCBussiness.getPlaceName());
			mainAccountInfo.setPlaceNo(accountCBussiness.getPlaceNo());

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setNewAccountCInfo(newAccountCInfo);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType("23");
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {// 主账户信息
				unifiedParam.setType("3");
				unifiedParam.setAccountCInfo(accountCInfo);
				unifiedParam.setServiceFlowRecord(serviceFlowRecord);

				//旧记帐卡的车卡标签绑定记录
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
				//车辆
				VehicleInfo vehicle = null;
				if (carObuCardInfo != null) {
					vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
					//carObuCardInfoDao.updateAccountID(newAccountCInfo.getId(), accountCInfo.getId());
				}
				VehicleBussiness vehicleBussiness = null;
				if (vehicle != null) {
					//新增车辆业务记录表CSMS_Vehicle_Bussiness
					vehicleBussiness = new VehicleBussiness();
					BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
					vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
					vehicleBussiness.setCustomerID(vehicle.getCustomerID());
					vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
					vehicleBussiness.setCardNo(newAccountCInfo.getCardNo());//新卡号
					vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
					//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
					vehicleBussiness.setType(VehicleBussinessEnum.accountCReplaceCard.getValue());//记帐卡挂失补卡
					vehicleBussiness.setOperID(accountCBussiness.getOperId());
					vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
					//新增的字段
					vehicleBussiness.setOperName(accountCBussiness.getOperName());
					vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
					vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
					vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

					vehicleBussiness.setCreateTime(new Date());
					vehicleBussiness.setMemo("记帐卡挂失补卡");
				} else {
					//如果vehicle为空，按正常数据，旧卡之前是挂起了再去挂失的，所以没有绑定车辆了
					//这个时候要将新卡的状态改为挂起
					newAccountCInfo.setState(AccountCardStateEnum.disabled.getIndex());
					//重新设置传参newPrepaidC
					unifiedParam.setNewAccountCInfo(newAccountCInfo);
				}

				if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {// 记帐卡
					if (carObuCardInfo != null) {
						carObuCardInfoDao.updateAccountID(newAccountCInfo.getId(), accountCInfo.getId());
					}


					Customer customer = customerDao.findById(accountCBussiness.getUserId());
					AccountCApply accountCApply = accountCApplyDao.findBySubAccountInfoId(accountCInfo.getAccountId());
					//2017-08-21  新卡要继承旧卡的保证金,保证金设置表做相应处理:新增一条旧卡退还保证金、新增一条新卡新增保证金
					//旧卡退还的
					Bail backBail = new Bail();
					BigDecimal seqBack = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
					backBail.setId(Long.parseLong(seqBack.toString()));
					backBail.setUserNo(customer.getUserNo());
					backBail.setCardno(accountCInfo.getCardNo());
					backBail.setTradingType("1");//正常
					backBail.setAccountId(accountCInfo.getAccountId());
					backBail.setBailFee(accountCInfo.getBail().negate());
					backBail.setPayFlag("1");//1退还
					backBail.setUp_Date(vehicleBussiness.getCreateTime());
					backBail.setAppTime(vehicleBussiness.getCreateTime());
					backBail.setBankMember(accountCApply.getAccName());
					backBail.setBankNo(accountCApply.getBankAccount());
					backBail.setBankOpenBranches(accountCApply.getBankName());
					backBail.setDflag("0");
					backBail.setSetTime(vehicleBussiness.getCreateTime());
					backBail.setOperId(vehicleBussiness.getOperID());
					backBail.setPlaceId(vehicleBussiness.getPlaceID());
					backBail.setOperName(vehicleBussiness.getOperName());
					backBail.setOperNo(vehicleBussiness.getOperNo());
					backBail.setPlaceName(vehicleBussiness.getPlaceName());
					backBail.setPlaceNo(vehicleBussiness.getPlaceNo());
					backBail.setApplyTime(vehicleBussiness.getCreateTime());
					backBail.setAppState("3");//营运审批通过；
					backBail.setUpreason("挂失补领");
					bailDao.save(backBail, customer);

					//新卡新增的
					Bail addBail = new Bail();
					BigDecimal seqAdd = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
					addBail.setId(Long.parseLong(seqAdd.toString()));
					addBail.setUserNo(customer.getUserNo());
					addBail.setCardno(newAccountCInfo.getCardNo());
					addBail.setTradingType("1");//正常
					addBail.setAccountId(newAccountCInfo.getAccountId());
					addBail.setBailFee(accountCInfo.getBail());
					addBail.setPayFlag("0");//0新增
					addBail.setUp_Date(vehicleBussiness.getCreateTime());
					addBail.setAppTime(vehicleBussiness.getCreateTime());
					addBail.setBankMember(accountCApply.getAccName());
					addBail.setBankNo(accountCApply.getBankAccount());
					addBail.setBankOpenBranches(accountCApply.getBankName());
					addBail.setDflag("0");
					addBail.setSetTime(vehicleBussiness.getCreateTime());
					addBail.setOperId(vehicleBussiness.getOperID());
					addBail.setPlaceId(vehicleBussiness.getPlaceID());
					addBail.setOperName(vehicleBussiness.getOperName());
					addBail.setOperNo(vehicleBussiness.getOperNo());
					addBail.setPlaceName(vehicleBussiness.getPlaceName());
					addBail.setPlaceNo(vehicleBussiness.getPlaceNo());
					addBail.setApplyTime(vehicleBussiness.getCreateTime());
					addBail.setUpreason("挂失补领");
					bailDao.save(addBail, customer);


					// 记帐卡业务记录
					BigDecimal accountC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
					accountCBussiness.setId(Long.valueOf(accountC_bussiness_NO.toString()));
					//AccountCApply accountCApply = accountCApplyDao.findByCardNo(newAccountCInfo.getCardNo());

					//回执打印用
					accountCBussiness.setBusinessId(newAccountCInfo.getHisSeqId());
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

					accountCBussinessDao.save(accountCBussiness);

					if (vehicleBussiness != null) vehicleBussinessDao.save(vehicleBussiness);

					DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getOldCardNo());

					DbasCardFlow dbasCard = new DbasCardFlow();
					dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
					dbasCard.setNewCardNo(accountCBussiness.getCardNo());
					dbasCard.setOldCardNo(accountCBussiness.getOldCardNo());
					if (dbasCardFlow != null) dbasCard.setCardNo(dbasCardFlow.getCardNo());
					else dbasCard.setCardNo(accountCBussiness.getOldCardNo());
					dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
					dbasCard.setSerType(DBACardFlowSerTypeEnum.lossReplaceCard.getValue());
					dbasCard.setApplyTime(new Date());
					dbasCard.setServiceId(Long.valueOf(accountC_bussiness_NO.toString()));
					dbasCard.setCardAmt(new BigDecimal("0"));
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
					dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
					dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
					dbasCard.setOperId(accountCBussiness.getOperId());
					dbasCard.setOperno(accountCBussiness.getOperNo());
					dbasCard.setOpername(accountCBussiness.getOperName());
					dbasCard.setPlaceId(accountCBussiness.getPlaceId());
					dbasCard.setPlacename(accountCBussiness.getPlaceName());
					dbasCard.setPlaceno(accountCBussiness.getPlaceNo());
					dbasCardFlowDao.save(dbasCard);


					//Customer customer = customerDao.findById(accountCBussiness.getUserId());
					String userType = "";
					if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
						userType = "0";//个人
					} else {
						userType = "1";//单位
					}

					String obuCode = "";
					String obuSeq = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					if (vehicle != null) {//车卡已经绑定
						////原清算数据，没用了
						/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
						userInfoBaseList.setNetNo("4401");
						//userInfoBaseList.setIssuerId("");发行方唯一标识
						//userInfoBaseList.setAgent();发行代理单位编码
						userInfoBaseList.setCardType(2);
						userInfoBaseListDao.save(userInfoBaseList, newAccountCInfo, null);*/


						//写给铭鸿的清算数据：用户状态信息
						// 旧卡注销
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCBussiness.getOldCardNo(),
								"23", Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂失补领后将旧卡注销");

						//写给铭鸿的清算数据：用户状态信息
						//新卡发行
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCBussiness.getCardNo(),
								"23", Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂失后补领新卡");

						//旧卡注销
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
								CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

						//新卡发行
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.nomal.getIndex()),
								CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);
					} else {
						//说明：如果vehicle为null，表示旧卡已经挂起过了，领取新卡也没有绑定车辆，要集成挂起的状态

						//旧卡注销
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
								CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

						//新卡发行
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()),
								CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);
					}


					//清算接口//原清算数据，没用了
					/*saveACinfo(23,accountCInfo,systemType);
					saveACinfo(0,newAccountCInfo,systemType);*/


					//Customer customer = customerDao.findById(accountCBussiness.getUserId());
					//调整的客服流水
					ServiceWater serviceWater = new ServiceWater();
					Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

					serviceWater.setId(serviceWater_id);

					if (customer != null) serviceWater.setCustomerId(customer.getId());
					if (customer != null) serviceWater.setUserNo(customer.getUserNo());
					if (customer != null) serviceWater.setUserName(customer.getOrgan());
					serviceWater.setCardNo(newAccountCInfo.getCardNo());
					serviceWater.setSerType("206");//206记帐卡补领
					if (accountCApply != null)
						serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
					serviceWater.setAmt(newAccountCInfo.getCost());//应收金额
					serviceWater.setAulAmt(newAccountCInfo.getRealCost());//实收金额
					serviceWater.setSaleWate(newAccountCInfo.getIssueFlag());//销售方式
					if (accountCApply != null)
						serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
					serviceWater.setAccountCBussinessId(accountCBussiness.getId());
					if (vehicleBussiness != null)
						serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
					serviceWater.setOperId(accountCBussiness.getOperId());
					serviceWater.setOperName(accountCBussiness.getOperName());
					serviceWater.setOperNo(accountCBussiness.getOperNo());
					serviceWater.setPlaceId(accountCBussiness.getPlaceId());
					serviceWater.setPlaceName(accountCBussiness.getPlaceName());
					serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
					serviceWater.setRemark("自营客服系统：记帐卡补领");
					serviceWater.setOperTime(new Date());

					serviceWaterDao.save(serviceWater);

					//记账卡挂失补领回执
					AccCardReplaceReceipt accCardReplaceReceipt = new AccCardReplaceReceipt();
					accCardReplaceReceipt.setTitle("记账卡挂失补领回执");
					accCardReplaceReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
					AccountCBussiness LostAccountCBussiness = this.accountCBussinessDao.findByCardNoAndState(accountCInfo.getCardNo(), AccountCBussinessTypeEnum.accCardLoss.getValue());    //挂失的buss
					accCardReplaceReceipt.setLostReceiptNo(this.receiptDao.findByBusIdAndPTC(LostAccountCBussiness.getId(), ReceiptParentTypeCodeEnum.accountC.getValue()).getReceiptNo());    //挂失的回执
					accCardReplaceReceipt.setOldAccCardNo(accountCInfo.getCardNo());
					accCardReplaceReceipt.setAccCardNo(newAccountCInfo.getCardNo());
					accCardReplaceReceipt.setAccCardCost(NumberUtil.get2Decimal(newAccountCInfo.getCost().doubleValue() * 0.01));
					Receipt receipt = new Receipt();
					receipt.setTypeCode(AccountCBussinessTypeEnum.accCardReplace.getValue());
					receipt.setTypeChName(AccountCBussinessTypeEnum.accCardReplace.getName());
					receipt.setCardNo(accCardReplaceReceipt.getAccCardNo());
					this.saveReceipt(receipt, accountCBussiness, accCardReplaceReceipt, customer);

					//保存清算数据
					cardSecondIssuedService.saveAccountCard(newAccountCInfo, accountCApply);
//					CardSecondIssued cardSecondIssued = new CardSecondIssued();
//					cardSecondIssued.setCardCode(accountCBussiness.getCardNo());
//					cardSecondIssued.setCardType("23");
//					cardSecondIssued.setBankAccount(accountCApply.getBankAccount());
//					cardSecondIssued.setId(accountCBussiness.getId());
//					cardSecondIssued.setSdate(accountCInfo.getIssueTime());
//					cardSecondIssued.setStatus(0);
//					cardSecondIssued.setUpdatetime(new Date());
//					cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);

					//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
					//
					//saveDarkList(accountCInfo,darkList,"10", "1");

					//要先解除旧卡挂失黑名单
					//保存黑名单流水解除挂失黑名单	给铭鸿
					blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());

					//记帐卡换卡注销旧卡
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());
					//发行成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);

						//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171017
						// 旧卡用户卡信息上传及变更
						realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicle,
								CardStatusEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu.UPDATE
										.getCode());
						// 旧卡黑名单名单上传及变更
						noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
								new Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
										.EN_BLACK.getCode());
						// 新卡用户卡信息上传及变更
						realTransferService.accountCInfoTransfer(customer, newAccountCInfo,
								vehicle, CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.ADD
										.getCode());
						//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171017

						return "true";
					}
				}
			}

			return "领取新卡失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "领取新卡失败");
			e.printStackTrace();
			throw new ApplicationException("领取新卡失败");
		}
	}

	/***
	 * 旧卡锁定
	 * @param accountCInfo
	 * @param accountCBussiness
	 * @param serviceFlowRecord
	 * @param result
	 * @param systemType
	 * @return
	 */
	@Override
	public String saveUnusable(AccountCInfo accountCInfo,
	                           AccountCBussiness accountCBussiness,
	                           ServiceFlowRecord serviceFlowRecord, boolean result, String systemType) {
		try {
			//注：guanshaofeng修改了此处
			//旧卡锁定不调用营运接口进行回收旧卡。（领取新卡时如果是无偿则回收旧卡，如果有偿不回收）
			/*InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			if (accountCInfo.getCardNo().length()==16) {
				map = inventoryService.omsInterface(accountCInfo.getCardNo(), "2", interfaceRecord,"");
				boolean result1 = (boolean) map.get("result");
				if (!result1) {
					return map.get("message").toString();
				}
			}*/

			//记录旧卡的状态
			String cardState = accountCInfo.getState();

			//回收原业务
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("4");
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());
			unifiedParam.setResult(result);
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// 记帐卡业务记录
				BigDecimal accountC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(accountC_bussiness_NO.toString()));
				accountCBussinessDao.save(accountCBussiness);
				Customer customer = customerDao.findById(accountCBussiness.getUserId());
				String userType = "";
				if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCBussiness.getCardNo());
				//清算接口
				if (result) {//不需要下黑名单的
					//原清算数据，没用了
					/*saveACinfo(5,accountCInfo,systemType);*/

					//有卡锁定，写给铭鸿的清算数据：卡片状态信息,用户信息
//					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
//							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCBussiness.getCardNo(), "23", userType);
					cardObuService.saveCardUnusable(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCBussiness.getCardNo(), "23", userType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType());

				} else {//需要下黑名单的
					//原清算数据，没用了
					/*saveACinfo(5,accountCInfo,systemType);
					TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
					TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
					saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/
					//

					//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
					//旧卡锁定
					//saveDarkList(accountCInfo,darkList,"10", "1");

					//如果旧卡为挂失卡，要先解除挂失黑名单
					//保存黑名单流水解除挂失黑名单	给铭鸿
					if (AccountCardStateEnum.loss.getIndex().equals(cardState)) {
						blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
								, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
								accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
								new Date());
					}


					//记帐卡旧卡无卡锁定或有卡锁定失败需下发黑名单
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());

					//无卡锁定或有卡锁定失败，写给铭鸿的清算数据：卡片状态信息
//					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
//							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getCardNo(), "23", userType);
					cardObuService.saveCardUnusable(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getCardNo(), "23", userType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType());
				}


				AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				if (customer != null) serviceWater.setCustomerId(customer.getId());
				if (customer != null) serviceWater.setUserNo(customer.getUserNo());
				if (customer != null) serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("207");//207记帐卡旧卡锁定
				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				//serviceWater.setAmt(newAccountCInfo.getCost());//应收金额
				//serviceWater.setAulAmt(newAccountCInfo.getRealCost());//实收金额
				//serviceWater.setSaleWate(newAccountCInfo.getIssueFlag());//销售方式
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：记帐卡旧卡锁定");
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);
				
				
				/*if (accountCInfo.getCardNo().length()==16) {
					//锁定成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}else {
					
				}
*/
				return "true";
			}
			return "旧卡锁定失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "旧卡锁定失败");
			e.printStackTrace();
			throw new ApplicationException("旧卡锁定失败");
		}
	}


	/***
	 * 领取新卡
	 * @param mainAccountInfo
	 * @param accountCInfo
	 * @param newAccountCInfo
	 * @param accountCBussiness
	 * @param serviceFlowRecord
	 * @param systemType
	 * @return
	 */
	@Override
	public String saveGainCard(MainAccountInfo mainAccountInfo, AccountCInfo accountCInfo, AccountCInfo newAccountCInfo, AccountCBussiness accountCBussiness, ServiceFlowRecord serviceFlowRecord, String systemType, Map<String, Object> params) {
		try {

			//注：guanshaofeng修改此处
			//旧卡锁定不调用营运接口，在领取新卡的时候调用。（有偿不用回收旧卡，无偿要回收旧卡）
			InterfaceRecord interfaceRecordReclaim = null;
			Map<String, Object> mapReclaim = null;
			if (newAccountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
				//2017/05/05 营运的回收接口增加了一个参数 newCardSourceType（产品类型）
				String newCardSourceType = "";
				Map<String, Object> newCardMap = inventoryService.getProductTypeByCode("1", newAccountCInfo.getCardNo());//1是粤通卡
				if ("0".equals((String) newCardMap.get("flag"))) {
					newCardSourceType = (String) newCardMap.get("sourceType");
				} else {
					return (String) newCardMap.get("message");
				}


				if (accountCInfo.getCardNo().length() == 16) {
					mapReclaim = inventoryService.omsInterface(accountCInfo.getCardNo(), "2", interfaceRecordReclaim, "",
							accountCBussiness.getPlaceId(), accountCBussiness.getOperId(), accountCBussiness.getOperName(), "1", "", null, new BigDecimal("0"), "37", newCardSourceType);
					boolean result = (Boolean) mapReclaim.get("result");
					if (!result) {
						return mapReclaim.get("message").toString();
					}
				}
			}

			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryService.omsInterface(newAccountCInfo.getCardNo(), "1", interfaceRecord, "issue",
					accountCBussiness.getPlaceId(), accountCBussiness.getOperId(), accountCBussiness.getOperName(), "1", "customPoint", null, newAccountCInfo.getRealCost(), "38", "");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}

			//设置有效起始时间与有效结束时间
			newAccountCInfo.setStartDate((Date) map.get("startDate"));
			newAccountCInfo.setEndDate((Date) map.get("endDate"));
			//2017/05/27更新
			/*有偿领取（即工本费不为0），保修开始日期则为领取发卡当天，保修截止截止日期为保修开始日期加营运参数 年；无偿领取（即工本费为0），则沿用最近一张的保修开始日期和保修截止日期。
			卡片的有效启用时间、有效截止时间均从营运系统获取。*/

			Calendar cal = Calendar.getInstance();
			;
			if (newAccountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
				//无偿，沿用原卡的发行时间作为维保起始时间
				cal.setTime(accountCInfo.getIssueTime());
			} else {

			}

			//获取营运参数：维保周期（key=Maintenance time）
			Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
			logger.info(paramMap);
			if (paramMap != null && "成功".equals((String) paramMap.get("message"))) {
				cal.add(Calendar.YEAR, Integer.parseInt((String) paramMap.get("value")));
			} else if (paramMap != null && !"成功".equals((String) paramMap.get("message"))) {
				return "获取营运维保周期参数失败:" + (String) paramMap.get("message");
			} else {
				return "获取营运维保周期参数失败";
				//cal.add(Calendar.YEAR, 10);// 十年有效期
			}

			newAccountCInfo.setMaintainTime(cal.getTime());

			//设置id
			newAccountCInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO"));

			//客服原发行操作

			mainAccountInfo.setOperId(accountCBussiness.getOperId());
			mainAccountInfo.setPlaceId(accountCBussiness.getPlaceId());
			mainAccountInfo.setOperName(accountCBussiness.getOperName());
			mainAccountInfo.setOperNo(accountCBussiness.getOperNo());
			mainAccountInfo.setPlaceName(accountCBussiness.getPlaceName());
			mainAccountInfo.setPlaceNo(accountCBussiness.getPlaceNo());

			// 主账户信息
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewAccountCInfo(newAccountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("16");
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());

			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				unifiedParam.setType("5");
				unifiedParam.setAccountCInfo(accountCInfo);
				unifiedParam.setServiceFlowRecord(serviceFlowRecord);
				//旧记帐卡的车卡标签绑定记录
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
				//车辆
				VehicleInfo vehicle = null;
				if (carObuCardInfo != null) {
					vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
					//carObuCardInfoDao.updateAccountID(newAccountCInfo.getId(), accountCInfo.getId());
				}

				VehicleBussiness vehicleBussiness = null;
				AccountCBussiness lockCardBussiness = null;    //锁卡业务记录
				if (vehicle != null) {
					AccountCBussiness tmp = new AccountCBussiness();
					tmp.setCardNo(accountCInfo.getCardNo());
					tmp.setState(AccountCBussinessTypeEnum.accCardLock.getValue());//记帐卡旧卡锁定
					lockCardBussiness = accountCBussinessDao.find(tmp);
					if (lockCardBussiness == null) {
						return "数据异常：无法找到旧卡的锁卡业务记录";
					}
					//新增车辆业务记录表CSMS_Vehicle_Bussiness
					vehicleBussiness = new VehicleBussiness();
					BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
					vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
					vehicleBussiness.setCustomerID(vehicle.getCustomerID());
					vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
					vehicleBussiness.setCardNo(newAccountCInfo.getCardNo());//新卡号
					vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
					//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
					if ("0".equals(lockCardBussiness.getLockType())) {
						//无卡锁定
						vehicleBussiness.setType(VehicleBussinessEnum.accountCGainWithoutCard.getValue());//记帐卡无卡换卡
						vehicleBussiness.setMemo("记帐卡无卡换卡");
					} else if ("1".equals(lockCardBussiness.getLockType())) {
						//有卡锁定
						vehicleBussiness.setType(VehicleBussinessEnum.accountCGainWithCard.getValue());//记帐卡有卡换卡
						vehicleBussiness.setMemo("记帐卡有卡换卡");
					} else {
						return "数据异常：旧卡锁定业务记录的lockType异常";
					}
					vehicleBussiness.setOperID(accountCBussiness.getOperId());
					vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
					//新增的字段
					vehicleBussiness.setOperName(accountCBussiness.getOperName());
					vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
					vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
					vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

					vehicleBussiness.setCreateTime(new Date());
				} else {
					//如果vehicle为空，按正常数据，旧卡之前是挂起了再去旧卡锁定的，所以没有绑定车辆了
					//这个时候要将新卡的状态改为挂起
					newAccountCInfo.setState(AccountCardStateEnum.disabled.getIndex());
					//重新设置传参newPrepaidC
					unifiedParam.setNewAccountCInfo(newAccountCInfo);
				}

				if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
					if (carObuCardInfo != null) {
						carObuCardInfoDao.updateAccountID(newAccountCInfo.getId(), accountCInfo.getId());
					}

					Customer customer = customerDao.findById(accountCBussiness.getUserId());
					AccountCApply accountCApply = accountCApplyDao.findBySubAccountInfoId(accountCInfo.getAccountId());
					//2017-08-21  新卡要继承旧卡的保证金,保证金设置表做相应处理:新增一条旧卡退还保证金、新增一条新卡新增保证金
					//旧卡退还的
					Bail backBail = new Bail();
					BigDecimal seqBack = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
					backBail.setId(Long.parseLong(seqBack.toString()));
					backBail.setUserNo(customer.getUserNo());
					backBail.setCardno(accountCInfo.getCardNo());
					backBail.setTradingType("1");//正常
					backBail.setAccountId(accountCInfo.getAccountId());
					backBail.setBailFee(accountCInfo.getBail().negate());
					backBail.setPayFlag("1");//1退还
					backBail.setUp_Date(accountCBussiness.getTradeTime());
					backBail.setAppTime(accountCBussiness.getTradeTime());
					backBail.setBankMember(accountCApply.getAccName());
					backBail.setBankNo(accountCApply.getBankAccount());
					backBail.setBankOpenBranches(accountCApply.getBankName());
					backBail.setDflag("0");
					backBail.setSetTime(accountCBussiness.getTradeTime());
					backBail.setOperId(accountCBussiness.getOperId());
					backBail.setPlaceId(accountCBussiness.getPlaceId());
					backBail.setOperName(accountCBussiness.getOperName());
					backBail.setOperNo(accountCBussiness.getOperNo());
					backBail.setPlaceName(accountCBussiness.getPlaceName());
					backBail.setPlaceNo(accountCBussiness.getPlaceNo());
					backBail.setApplyTime(accountCBussiness.getTradeTime());
					backBail.setAppState("3");//营运审批通过；
					backBail.setUpreason("领取新卡");//2017年9月5日新增 hzw
					bailDao.save(backBail, customer);

					//新卡新增的
					Bail addBail = new Bail();
					BigDecimal seqAdd = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
					addBail.setId(Long.parseLong(seqAdd.toString()));
					addBail.setUserNo(customer.getUserNo());
					addBail.setCardno(newAccountCInfo.getCardNo());
					addBail.setTradingType("1");//正常
					addBail.setAccountId(newAccountCInfo.getAccountId());
					addBail.setBailFee(accountCInfo.getBail());
					addBail.setPayFlag("0");//0新增
					addBail.setUp_Date(accountCBussiness.getTradeTime());
					addBail.setAppTime(accountCBussiness.getTradeTime());
					addBail.setBankMember(accountCApply.getAccName());
					addBail.setBankNo(accountCApply.getBankAccount());
					addBail.setBankOpenBranches(accountCApply.getBankName());
					addBail.setDflag("0");
					addBail.setSetTime(accountCBussiness.getTradeTime());
					addBail.setOperId(accountCBussiness.getOperId());
					addBail.setPlaceId(accountCBussiness.getPlaceId());
					addBail.setOperName(accountCBussiness.getOperName());
					addBail.setOperNo(accountCBussiness.getOperNo());
					addBail.setPlaceName(accountCBussiness.getPlaceName());
					addBail.setPlaceNo(accountCBussiness.getPlaceNo());
					addBail.setApplyTime(accountCBussiness.getTradeTime());
					addBail.setUpreason("领取新卡");//2017年9月5日新增 hzw
					bailDao.save(addBail, customer);


					//保存业务记录
					if (vehicleBussiness != null) vehicleBussinessDao.save(vehicleBussiness);

					// 记帐卡业务记录
					BigDecimal accountC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
					accountCBussiness.setId(Long.valueOf(accountC_bussiness_NO.toString()));

					//回执打印用
					accountCBussiness.setBusinessId(newAccountCInfo.getHisSeqId());

					accountCBussinessDao.save(accountCBussiness);


					//资金转移确认表
					DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getOldCardNo());

					DbasCardFlow dbasCard = new DbasCardFlow();
					dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
					dbasCard.setNewCardNo(accountCBussiness.getCardNo());
					dbasCard.setOldCardNo(accountCBussiness.getOldCardNo());
					if (dbasCardFlow != null) dbasCard.setCardNo(dbasCardFlow.getCardNo());
					else dbasCard.setCardNo(accountCBussiness.getOldCardNo());
					dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
					dbasCard.setSerType(DBACardFlowSerTypeEnum.gainCard.getValue());
					dbasCard.setApplyTime(new Date());
					dbasCard.setServiceId(Long.valueOf(accountC_bussiness_NO.toString()));
					dbasCard.setCardAmt(new BigDecimal("0"));
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
					dbasCard.setEndFlag(DBACardFlowEndFlagEnum.disComplete.getValue());
					dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.disDispute.getValue());
					dbasCard.setOperId(accountCBussiness.getOperId());
					dbasCard.setOperno(accountCBussiness.getOperNo());
					dbasCard.setOpername(accountCBussiness.getOperName());
					dbasCard.setPlaceId(accountCBussiness.getPlaceId());
					dbasCard.setPlacename(accountCBussiness.getPlaceName());
					dbasCard.setPlaceno(accountCBussiness.getPlaceNo());
					dbasCardFlowDao.save(dbasCard);


					String userType = "";
					if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
						userType = "0";//个人
					} else {
						userType = "1";//单位
					}
					String obuCode = "";
					String obuSeq = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					//车卡标签绑定
					//如果vehicle为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicle != null) {
						//清算接口(原清算数据，没用了)
						/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
						userInfoBaseList.setNetNo("4401");
						//userInfoBaseList.setIssuerId("");发行方唯一标识
						//userInfoBaseList.setAgent();发行代理单位编码
						userInfoBaseList.setCardType(2);
						userInfoBaseListDao.save(userInfoBaseList, newAccountCInfo, null);*/

						//新卡发行
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()),
								CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);

						//写给铭鸿的清算数据：用户状态信息
						//新卡发行
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCBussiness.getCardNo(),
								"23", Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
								null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡领取新卡");
					} else {
						//说明：如果vehicle为null，说明旧卡是挂起的卡去做旧卡锁定，领取的新卡也不会有绑定的车辆，所以领取的新卡为挂起状态

						//新卡发行
						//写给铭鸿的清算数据：卡片状态信息
						cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()),
								CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);
					}

					//调整的客服流水
					ServiceWater serviceWater = new ServiceWater();
					Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

					serviceWater.setId(serviceWater_id);

					if (customer != null) serviceWater.setCustomerId(customer.getId());
					if (customer != null) serviceWater.setUserNo(customer.getUserNo());
					if (customer != null) serviceWater.setUserName(customer.getOrgan());
					serviceWater.setCardNo(newAccountCInfo.getCardNo());
					serviceWater.setSerType("208");//208记帐卡领取新卡
					if (accountCApply != null)
						serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
					serviceWater.setAmt(newAccountCInfo.getCost());//应收金额
					serviceWater.setAulAmt(newAccountCInfo.getRealCost());//实收金额
					serviceWater.setSaleWate(newAccountCInfo.getIssueFlag());//销售方式
					if (accountCApply != null)
						serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
					serviceWater.setAccountCBussinessId(accountCBussiness.getId());
					if (vehicleBussiness != null)
						serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
					serviceWater.setOperId(accountCBussiness.getOperId());
					serviceWater.setOperName(accountCBussiness.getOperName());
					serviceWater.setOperNo(accountCBussiness.getOperNo());
					serviceWater.setPlaceId(accountCBussiness.getPlaceId());
					serviceWater.setPlaceName(accountCBussiness.getPlaceName());
					serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
					serviceWater.setRemark("自营客服系统：记帐卡领取新卡");
					serviceWater.setOperTime(new Date());

					serviceWaterDao.save(serviceWater);

					//记账卡换卡回执
					AccCardReplaceNewcardReceipt accCardReplaceNewcardReceipt = new AccCardReplaceNewcardReceipt();
					accCardReplaceNewcardReceipt.setTitle("记账卡换卡回执");
					accCardReplaceNewcardReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
					accCardReplaceNewcardReceipt.setOldAccCardNo(accountCInfo.getCardNo());
					if (lockCardBussiness != null) {
						accCardReplaceNewcardReceipt.setLockType("0".equals(lockCardBussiness.getLockType()) ? "无卡换卡" : ("1".equals(lockCardBussiness.getLockType()) ? "有卡换卡" : ""));
					}
					accCardReplaceNewcardReceipt.setAccCardNo(newAccountCInfo.getCardNo());
					accCardReplaceNewcardReceipt.setAccCardCost(NumberUtil.get2Decimal(newAccountCInfo.getCost().doubleValue() * 0.01));
					Receipt receipt = new Receipt();
					receipt.setTypeCode(AccountCBussinessTypeEnum.accCardReplaceNewcard.getValue());
					receipt.setTypeChName(AccountCBussinessTypeEnum.accCardReplaceNewcard.getName());
					receipt.setCardNo(accCardReplaceNewcardReceipt.getAccCardNo());
					this.saveReceipt(receipt, accountCBussiness, accCardReplaceNewcardReceipt, customer);


					//原清算数据，没用了
					/*if("1".equals(accountCBussiness.getLockType())){
						saveACinfo(21,accountCInfo,systemType);//有卡
					}else{
						saveACinfo(22,accountCInfo,systemType);//无卡
					}
					saveACinfo(0,newAccountCInfo,systemType);*/

					//保存清算数据，记账卡二发信息
					cardSecondIssuedService.saveAccountCard(newAccountCInfo, accountCApply);
//					CardSecondIssued cardSecondIssued = new CardSecondIssued();
//					cardSecondIssued.setCardCode(accountCBussiness.getCardNo());
//					cardSecondIssued.setCardType("23");
//					cardSecondIssued.setBankAccount(accountCApply.getBankAccount());
//					cardSecondIssued.setId(accountCBussiness.getId());
//					cardSecondIssued.setSdate(accountCInfo.getIssueTime());
//					cardSecondIssued.setStatus(0);
//					cardSecondIssued.setUpdatetime(new Date());
//					cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);
//					
					//有偿不用回收旧卡
					if (newAccountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
						//如果mapReclaim不为空则表示调用营运接口回收旧卡成功，此时要更新营运接口调用登记记录的客服状态
						if (mapReclaim != null) {
							interfaceRecordReclaim = (InterfaceRecord) mapReclaim.get("interfaceRecord");
							if (interfaceRecordReclaim != null && interfaceRecordReclaim.getCardno() != null) {
								interfaceRecordReclaim.setCsmsState("1");
								interfaceRecordDao.update(interfaceRecordReclaim);
							}
						}
					}

					//发行成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);

						//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171017
						// 旧卡用户卡信息上传及变更
						Integer cardType = null;
						if ("1".equals(lockCardBussiness.getLockType())) {
							cardType = CardStatusEmeu.HADCARD_CANCLE.getCode();
						} else {
							cardType = CardStatusEmeu.NOCARD_CANCLE.getCode();
						}

						realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicle,
								cardType, OperationTypeEmeu.UPDATE.getCode());
						// 旧卡黑名单状态上传及变更
						if (!"1".equals(lockCardBussiness.getLockType())) {
							noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new
									Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
									.EN_BLACK.getCode());
						}
						// 新卡用户卡信息上传及变更
						realTransferService.accountCInfoTransfer(customer, newAccountCInfo, vehicle, CardStatusEmeu.NORMAL.getCode(),
								OperationTypeEmeu.ADD.getCode());
						//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171017

						return "true";
					}
				}
			}
			return "领取新卡失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "领取新卡失败");
			e.printStackTrace();
			throw new ApplicationException("领取新卡失败");
		}
	}


	/**
	 * 记帐卡卡号列表
	 *
	 * @param accountCInfo
	 * @author gaosiling
	 */
	@Override
	public List<Map<String, Object>> findByList(AccountCInfo accountCInfo) {
		try {
			return accountCDao.findByList(accountCInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "记帐卡卡号列表查询失败");
			throw new ApplicationException();
		}
	}

	@Override
	public List<Map<String, Object>> getAccountCInfoByBank(Long customerId, Long subAccountInfoId) {
		try {
			return accountCDao.getAccountCInfoByBank(customerId, subAccountInfoId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "记帐卡卡号列表查询失败");
			throw new ApplicationException();
		}
	}

	/**
	 * 记帐卡更改交易密码
	 *
	 * @param accountCInfo
	 * @author zyh
	 */
	@Override
	public boolean updatePwd(AccountCInfo accountCInfo) {
		try {
			//记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			//增加历史表记录
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO"));
			accountCInfoHis.setGenTime(new Date());
			accountCInfoHis.setGenReason("16");//交易密码修改
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

			accountCBussiness.setUserId(accountCInfo.getCustomerId());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setState("13"); //13消费密码修改 14重设
			accountCBussiness.setOperId(accountCInfo.getIssueOperId());
			accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
			accountCBussiness.setOperName(accountCInfo.getOperName());
			accountCBussiness.setOperNo(accountCInfo.getOperNo());
			accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
			accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			//回执打印数据
			AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

			accountCBussinessDao.save(accountCBussiness);

			//MD5 = 卡号+交易密码
			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			//增加历史id
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCDao.update(accountCInfo);

			Customer customer = customerDao.findById(accountCBussiness.getUserId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("204");//204记帐卡密码更改
			//暂时记录新的账户
			if (accountCApply != null)
				serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			//serviceWater.setAmt(bail.getBailFee());//应收金额
			//serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡密码更改");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			return true;
		} catch (NumberFormatException e) {
			logger.error(e.getMessage() + "记帐卡交易密码修改失败，储值卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("记帐卡交易密码修改失败，储值卡id:" + accountCInfo.getId());
		}
	}

	/**
	 * 记帐卡更改交易密码
	 *
	 * @param accountCInfo
	 * @author zyh
	 */
	@Override
	public boolean saveResetPwd(AccountCInfo accountCInfo) {
		try {
			//记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			//增加历史表记录
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO"));
			accountCInfoHis.setGenTime(new Date());
			accountCInfoHis.setGenReason("17");//交易密码重置
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);


			accountCBussiness.setUserId(accountCInfo.getCustomerId());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setState("14"); //13消费密码修改 14重设
			accountCBussiness.setOperId(accountCInfo.getIssueOperId());
			accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
			accountCBussiness.setOperName(accountCInfo.getOperName());
			accountCBussiness.setOperNo(accountCInfo.getOperNo());
			accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
			accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			//回执打印数据
			AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

			accountCBussinessDao.save(accountCBussiness);

			//MD5 = 卡号+交易密码
			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			//增加历史id
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCDao.update(accountCInfo);


			Customer customer = customerDao.findById(accountCBussiness.getUserId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("205");//205记帐卡密码重设
			if (accountCApply != null)
				serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
			//serviceWater.setAmt(bail.getBailFee());//应收金额
			//serviceWater.setAulAmt(bail.getBailFee());//实收金额
			//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
			if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：记帐卡密码重设");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);


			return true;
		} catch (NumberFormatException e) {
			logger.error(e.getMessage() + "记帐卡交易密码修改失败，储值卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("记帐卡交易密码修改失败，储值卡id:" + accountCInfo.getId());
		}
	}

	/**
	 * 记帐卡挂失
	 *
	 * @author zyh
	 */
	@Override
	public boolean saveLock(AccountCInfo accountCInfo, String systemType, Map<String, Object> params) {
		try {
			AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());

			//客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("1");
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// 记帐卡业务记录
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));

				accountCBussiness.setUserId(accountCInfo.getCustomerId());
				accountCBussiness.setOldCardNo(accountCInfo.getCardNo());
				accountCBussiness.setCardNo(accountCInfo.getCardNo());
				accountCBussiness.setState(AccountCBussinessTypeEnum.accCardLoss.getValue());
				accountCBussiness.setOperId(accountCInfo.getIssueOperId());
				accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
				accountCBussiness.setOperName(accountCInfo.getOperName());
				accountCBussiness.setOperNo(accountCInfo.getOperNo());
				accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
				accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
				accountCBussiness.setTradeTime(new Date());
				accountCBussiness.setRealPrice(new BigDecimal("0"));

				//清算接口(//原清算数据，没用了)
				/*saveACinfo(1,accountCInfo,systemType);
				TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,2, new Date(),0, new Date());
				TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,2, new Date(),0, new Date());
				saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/
				//
				//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
				//记帐卡挂失
				//saveDarkList(accountCInfo,darkList,"2", "1");
				blackListService.saveCardLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
						, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
						accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
						new Date());
				//回执打印用
				//TODO
				//accountCBussiness.setBusinessId(accountCInfo.getId());
				if (accountCApply != null)
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

				accountCBussinessDao.save(accountCBussiness);


				Customer customer = customerDao.findById(accountCBussiness.getUserId());
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				if (customer != null) serviceWater.setCustomerId(customer.getId());
				if (customer != null) serviceWater.setUserNo(customer.getUserNo());
				if (customer != null) serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("209");//209记帐卡挂失
				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				//serviceWater.setAmt(bail.getBailFee());//应收金额
				//serviceWater.setAulAmt(bail.getBailFee());//实收金额
				//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark(SystemTypeEnum.getName(systemType) + "：记帐卡挂失");
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);

				//记账卡挂失回执
				AccCardLossReceipt accCardLossReceipt = new AccCardLossReceipt();
				accCardLossReceipt.setTitle("记账卡挂失回执");
				accCardLossReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				accCardLossReceipt.setAccCardNo(accountCInfo.getCardNo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardLoss.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardLoss.getName());
				receipt.setBusinessId(accountCBussiness.getId());
				receipt.setCardNo(accCardLossReceipt.getAccCardNo());
				this.saveReceipt(receipt, accountCBussiness, accCardLossReceipt, customer);

				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.loss.getValue()),
						CardStateSendSerTypeEnum.loss.getValue(), accountCInfo.getCardNo(), "23", userType);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171017
				VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
				// 调用用户卡信息上传及变更接口
				realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo,
						CardStatusEmeu.CARD_LOSS.getCode(), OperationTypeEmeu.UPDATE.getCode());

				// 调用用户卡黑名单上传及变更接口
				noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
						new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EN_BLACK.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171017

				return true;
			}
			return false;
		} catch (NumberFormatException e) {
			logger.error(e.getMessage() + "记帐卡挂失操作失败，记帐卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("记帐卡挂失操作失败，记帐卡id:" + accountCInfo.getId());
		}
	}

	/**
	 * 记帐卡解挂
	 *
	 * @author zyh
	 */
	@Override
	public boolean saveUnLock(AccountCInfo accountCInfo, String systemType, Map<String, Object> params) {
		try {
			AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());

			//客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("2");
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// 记帐卡业务记录
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));

				accountCBussiness.setUserId(accountCInfo.getCustomerId());
				accountCBussiness.setOldCardNo(accountCInfo.getCardNo());
				accountCBussiness.setCardNo(accountCInfo.getCardNo());
				accountCBussiness.setState("4");
				accountCBussiness.setOperId(accountCInfo.getIssueOperId());
				accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
				accountCBussiness.setOperName(accountCInfo.getOperName());
				accountCBussiness.setOperNo(accountCInfo.getOperNo());
				accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
				accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
				accountCBussiness.setTradeTime(new Date());
				accountCBussiness.setRealPrice(new BigDecimal("0"));
				//清算接口 //原清算数据，没用了
				/*saveACinfo(0,accountCInfo,systemType);
				
				TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
				TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
				saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/

				//
				//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
				//记帐卡解挂      解挂的状态应该为正常吧？？？
				//saveDarkList(accountCInfo,darkList,"1", "0");
				//记帐卡解挂
				blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
						, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
						accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
						new Date());

				//回执打印用
				//TODO
				//accountCBussiness.setBusinessId(accountCInfo.getId());
				if (accountCApply != null)
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

				accountCBussinessDao.save(accountCBussiness);

				Customer customer = customerDao.findById(accountCBussiness.getUserId());
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				if (customer != null) serviceWater.setCustomerId(customer.getId());
				if (customer != null) serviceWater.setUserNo(customer.getUserNo());
				if (customer != null) serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("220");//220记帐卡解挂
				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				//serviceWater.setAmt(bail.getBailFee());//应收金额
				//serviceWater.setAulAmt(bail.getBailFee());//实收金额
				//serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：记帐卡解挂");
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);

				//记账卡解挂回执
				AccCardCanceLossReceipt accCardCanceLossReceipt = new AccCardCanceLossReceipt();
				accCardCanceLossReceipt.setTitle("记账卡解挂回执");
				accCardCanceLossReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				AccountCBussiness LostAccountCBussiness = this.accountCBussinessDao.findByCardNoAndState(accountCInfo.getCardNo(), AccountCBussinessTypeEnum.accCardLoss.getValue());    //挂失的buss
				accCardCanceLossReceipt.setLostReceiptNo(this.receiptDao.findByBusIdAndPTC(LostAccountCBussiness.getId(), ReceiptParentTypeCodeEnum.accountC.getValue()).getReceiptNo());    //挂失的回执
				accCardCanceLossReceipt.setAccCardNo(accountCInfo.getCardNo());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardCanceLoss.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardCanceLoss.getName());
				receipt.setCardNo(accCardCanceLossReceipt.getAccCardNo());
				this.saveReceipt(receipt, accountCBussiness, accCardCanceLossReceipt, customer);


				//写给铭鸿的清算数据：卡片状态信息
				String userType = "";
				if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()),
						CardStateSendSerTypeEnum.unloss.getValue(), accountCInfo.getCardNo(), "23", userType);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171017
				VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
				// 调用用户卡信息上传及变更接口
				realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo,
						CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.UPDATE.getCode());

				// 调用用户卡黑名单上传及变更接口
				noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
						new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EX_BLACK.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171017

				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "记帐卡解挂失操作失败，记帐卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("记帐卡解挂操作失败，记帐卡id:" + accountCInfo.getId());
		}
	}

	@Override
	public List<Map<String, Object>> findCardNoByList(AccountCInfo accountCInfo) {
		try {
			List<Map<String, Object>> accountCList = accountCDao.findCardNoByList(accountCInfo);
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			if (!accountCList.isEmpty() && accountCList.size() > 0) {
				//去除联名卡
				for (Map<String, Object> accountc : accountCList) {
					//23表示记帐卡
					List joinCardNoSections = joinCardNoSectiondao.findList(Constant.ACCOUNTCTYPE, (String) accountc.get("CARDNO"));
					if (joinCardNoSections.isEmpty() || joinCardNoSections.size() == 0) {
						returnList.add(accountc);
					}
				}
			}
			return returnList;//
			//不用这个第八位来判断卡片种类
			//return ListUtil.getCardTypeList(accountCDao.findCardNoByList(accountCInfo),"CARDNO",CardTypeEnum.standardCard.getValue());//
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "记帐卡卡号列表查询失败");
			throw new ApplicationException();
		}
	}

	@Override
	public AccountCInfo findByCardNoToGain(String cardNo) {
		try {
			return accountCDao.findByCardNoToGain(cardNo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "根据卡号查询记帐卡");
			throw new ApplicationException();
		}
	}

	@Override
	public AccountCInfo findByCustomerId(Long id) {
		return accountCDao.findByCustomerId(id);
	}

	@Override
	public Pager findByCustomer(Pager pager, Customer customer) {
		return accountCDao.findByCustomer(pager, customer);
	}

	@Override
	public boolean hasMigrateOrTransfer(String cardNo) {
		return accountCDao.hasMigrateOrTransfer(cardNo);
	}

	@Override
	public boolean hasTransfer(String cardNo) {
		return accountCDao.hasTransfer(cardNo);
	}

	@Override
	public AccountCInfo findById(Long id) {
		return accountCDao.findById(id);
	}


	//清算接口（原清算系统，没用了）
	public void saveACinfo(Integer state, AccountCInfo accountCInfo, String systemType) {
		try {
			ACinfo newACinfo = new ACinfo();
			newACinfo.setCardNo(accountCInfo.getCardNo());
			newACinfo.setState(state);
			newACinfo.setSystemType(systemType);
			if (accountCInfo.getIssueTime() != null)
				newACinfo.setSdate(accountCInfo.getIssueTime());
			else newACinfo.setSdate(new Date());
			//aCinfo.setOBANo(oBANo);尚未确定
			/*aCinfoDao.saveACinfo(accountCApplyDao.findByCardNo(newACinfo.getCardNo()), newACinfo);*/
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存清算卡状态数据失败" + accountCInfo.getCardNo());
			throw new ApplicationException("保存清算卡状态数据失败" + accountCInfo.getCardNo());
		}
	}

	//清算接口（原清算系统，没用了）
	public void saveTollCardBlack(AccountCInfo accountCInfo, TollCardBlackDet tollCardBlackDet, TollCardBlackDetSend tollCardBlackDetSend) {
		try {
			String license = " ";//车牌号
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			if (vehicleInfo != null) {
				license = vehicleInfo.getVehiclePlate();
			}
			tollCardBlackDet.setLicense(license);
			tollCardBlackDetSend.setLicense(license);
			/*tollCardBlackDetDao.save(tollCardBlackDet);
			tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/


		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存清算黑名单数据失败" + accountCInfo.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败" + accountCInfo.getCardNo());
		}
	}

	/**
	 * @param accountCInfo
	 * @param darkList
	 * @param genCau
	 * 		产生原因
	 * @param state
	 * 		状态
	 * @Description:TODO
	 */
	public void saveDarkList(AccountCInfo accountCInfo, DarkList darkList, String genCau, String state) {
		//查询客户信息
		Customer customer = customerDao.findById(accountCInfo.getCustomerId());
		try {
			if (darkList == null) {
				darkList = new DarkList();
				darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
				darkList.setCustomerId(accountCInfo.getCustomerId());
				darkList.setCardNo(accountCInfo.getCardNo());
				darkList.setCardType("1");
				darkList.setGenDate(new Date());
				darkList.setGencau(genCau);//产生原因	10—无卡注销。
				darkList.setGenmode("0");//产生方式	系统产生
				darkList.setOperId(accountCInfo.getIssueOperId());
				darkList.setPlaceId(accountCInfo.getIssuePlaceId());
				darkList.setOperNo(accountCInfo.getOperNo());
				darkList.setOperName(accountCInfo.getOperName());
				darkList.setPlaceNo(accountCInfo.getPlaceNo());
				darkList.setPlaceName(accountCInfo.getPlaceName());
				//darkList.setUpdateTime(updateTime);
				if (customer != null) {
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
				}
				//darkList.setRemark(remark);
				darkList.setState(state);
				//黑名单表更改，不需要这个表了
				//darkListDao.save(darkList);

			} else {
				darkList.setCustomerId(accountCInfo.getCustomerId());
				darkList.setUserNo(customer.getUserNo());
				darkList.setUserName(customer.getOrgan());
				darkList.setGencau(genCau);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				//黑名单表更改了，不需要这个表了
//				darkListDao.updateDarkList(darkList);
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存清算黑名单数据失败" + accountCInfo.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败" + accountCInfo.getCardNo());
		}
	}


	@Override
	public Pager findLianCard(Pager pager, Customer customer,
	                          AccountCInfo accountCInfo, Date startTime, Date endTime) {
		return accountCDao.findLianCard(pager, customer, accountCInfo, startTime, endTime);
	}

	/**
	 * 香港联营卡状态查询
	 */
	@Override
	public Pager findJointCardState(Pager pager, Customer customer, AccountCInfo accountCInfo) {
		return accountCDao.findJointCardState(pager, customer, accountCInfo);
	}

	/**
	 * 香港联营卡状态列表（用于状态查询数据导出）
	 */
	@Override
	public List listJointCardState(Customer customer) {
		return accountCDao.listJointCardState(customer);
	}

	@Override
	public List list(AccountCInfo accountCInfo, Customer customer) {
		return accountCDao.list(accountCInfo, customer);
	}

	/**
	 * 修改写卡标志
	 */
	@Override
	public Map<String, Object> updateWriteCardFlag(String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			accountCDao.updateWriteCardFlag(cardNo);
			map.put("result", "1");
			map.put("message", "修改写卡标志成功");
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("修改写卡标志失败");
			throw new ApplicationException();
		}
		return map;
	}

	/**
	 * @param cardNo
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年2月21日
	 */
	@Override
	public AccountCApply findAccountCApplyByCardNo(String cardNo) {
		return accountCApplyDao.findByCardNo(cardNo);
	}

	/**
	 * @param bankAccount
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年2月21日
	 */
	@Override
	public List<Map<String, Object>> getStopAcList(String bankAccount) {
		//废弃  by sxw20171001
//		return stopAcListDao.findByACBAccount(bankAccount);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = paymentCardBlacklistRecvDao.findStateMap(bankAccount);
		if (map != null && !map.isEmpty()) {
			list.add(map);
		}
		return list;
	}

	/**
	 * @param cardNo
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年4月25日
	 */
	@Override
	public Customer findCustomer(String cardNo) {
		Customer customer = null;
		try {
			customer = customerDao.findByCardNo(cardNo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询客户信息失败");
			throw new ApplicationException("查询客户信息失败");
		}
		return customer;
	}

	@Override
	public Customer findCustomer(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		Customer customer = null;
		try {
			customer = customerDao.findCustomer(paymentCardBlacklistRecv);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询客户信息失败");
			throw new ApplicationException("查询客户信息失败");
		}
		return customer;
	}
//	/**
//	 * @Descriptioqn:
//	 * @param ACBAccount
//	 * @return
//	 * @author lgm
//	 * @date 2017年4月25日
//	 */
//	@Override
//	public boolean checkBankAccount(String ACBAccount) {
//		//确认后再做
//		return true;
//	}


	@Override
	public List<Map<String, Object>> findAccountCInfoList(AccountCApply accountCApply) {
		return accountCDao.findAccountCInfoList(accountCApply);
	}

	@Override
	public AccountCBussiness findAccountCBussiness(AccountCBussiness accountCBussiness) {
		return accountCBussinessDao.find(accountCBussiness);
	}

	/**
	 * 保存回执
	 *
	 * @param receipt
	 * 		回执主要信息
	 * @param accountCBussiness
	 * 		记账卡业务
	 * @param baseReceiptContent
	 * 		回执VO
	 * @param customer
	 * 		客户信息
	 */
	private void saveReceipt(Receipt receipt, AccountCBussiness accountCBussiness, BaseReceiptContent baseReceiptContent, Customer customer) {
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
}

