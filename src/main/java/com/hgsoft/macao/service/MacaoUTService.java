package com.hgsoft.macao.service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.DBACardFlowCardTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowReadFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowSerTypeEnum;
import com.hgsoft.common.Enum.MacaoIdCardTypeEnum;
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
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.macao.dao.AcCancelInfoDao;
import com.hgsoft.macao.dao.AcIssuceInfoDao;
import com.hgsoft.macao.dao.CarUserCardInfoDao;
import com.hgsoft.macao.dao.CardHolderInfoDao;
import com.hgsoft.macao.dao.MacaoCardSectionDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.entity.AcCancelInfo;
import com.hgsoft.macao.entity.AcIssuceInfo;
import com.hgsoft.macao.entity.Cancel;
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.serviceInterface.IMacaoUTService;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MacaoUTService implements IMacaoUTService {
	private static Logger logger = Logger.getLogger(MacaoService.class.getName());

	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private CardHolderInfoDao cardHolderInfoDao;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;
	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private CarUserCardInfoDao carUserCardInfoDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private CardSecondIssuedService cardSecondIssuedService;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private AcIssuceInfoDao acIssuceInfoDao;
	@Resource
	private AcCancelInfoDao acCancelInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;
	@Resource
	private MacaoCardSectionDao macaoCardSectionDao;
	@Resource
	private BlackListDao blackListDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	@Resource
	private CustomerDao customerDao;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

	@Override
	public String saveAccountCIssue(AccountCInfo accountCInfo, MacaoCardCustomer macaoCardCustomer, VehicleInfo vehicleInfo, Customer customer) {
		try {
			String message = "";
			// 记帐卡发行
			AccountCInfo acInfo = accountCInfoDao.findByCardNo(accountCInfo.getCardNo());// 根据记帐卡号（即粤通卡卡号）找

			if (acInfo == null) {
				InterfaceRecord interfaceRecord = null;
				Map<String, Object> map = inventoryServiceForAgent.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord, "issue",
						null, null, "", "1", "", null, accountCInfo.getRealCost(), "31");
				boolean result = (Boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
				//	设置有效起始时间与结束时间
				Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
				if (m != null) {
					accountCInfo.setStartDate((Date) m.get("startDate"));
					accountCInfo.setEndDate((Date) m.get("endDate"));
				}
				Calendar cal = Calendar.getInstance();
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
				accountCInfo.setMaintainTime(cal.getTime());


				// ①修改记帐卡发行信息（记帐卡信息表）注：记帐卡状态为0：正常
				accountCInfo.setState("0");
				accountCInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());

				accountCInfo.setIssueTime(new Date());//发行时间

				BigDecimal SEQ_CSMSAccountCinfo_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfo_NO");
				accountCInfo.setId(Long.valueOf(SEQ_CSMSAccountCinfo_NO.toString()));

//					accountCInfo.setState("0");
//					accountCInfo.setIssueTime(new Date());//发行时间
				accountCDao.save(accountCInfo);

				// 记帐卡历史表？

				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
				carObuCardInfo.setAccountCID(accountCInfo.getId());
				carObuCardInfoDao.update(carObuCardInfo);
				//废弃
//					CarUserCardInfo carUserCardInfo = carUserCardInfoDao.findByVehicleId(vehicleInfo.getId());
//					carUserCardInfo.setAccountCId(accountCInfo.getId());
//					carUserCardInfoDao.updateCarUserCardInfo(carUserCardInfo);

				CardHolderInfo cardHolderInfo = new CardHolderInfo();
				MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByVehicleId(vehicleInfo.getId());
				if (macaoBankAccount != null) {
					cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
				}
				Long SEQ_CSMSCARDHOLDERINFO_NO = sequenceUtil.getSequenceLong("SEQ_CSMSCARDHOLDERINFO_NO");
				cardHolderInfo.setId(SEQ_CSMSCARDHOLDERINFO_NO);
				cardHolderInfo.setType("2");
				cardHolderInfo.setTypeId(accountCInfo.getId());
				cardHolderInfoDao.save(cardHolderInfo);

				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = new VehicleBussiness();
				BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
				vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
				vehicleBussiness.setCustomerID(customer.getId());
				vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
				vehicleBussiness.setCardNo(accountCInfo.getCardNo());
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
				//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
				//vehicleBussiness.setType("51");
				vehicleBussiness.setType(VehicleBussinessEnum.accountCIssue.getValue());
				vehicleBussiness.setOperID(accountCInfo.getIssueOperId());
				vehicleBussiness.setPlaceID(accountCInfo.getIssuePlaceId());

				//新增的字段
				vehicleBussiness.setOperName(accountCInfo.getOperName());
				vehicleBussiness.setOperNo(accountCInfo.getOperNo());
				vehicleBussiness.setPlaceName(accountCInfo.getPlaceName());
				vehicleBussiness.setPlaceNo(accountCInfo.getPlaceNo());

				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("记帐卡发行");
				vehicleBussinessDao.save(vehicleBussiness);


				//7 增加记帐卡业务记录
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setState("1");
				accountCBussiness.setRealPrice(accountCInfo.getRealCost());// 业务费用
				accountCBussiness.setTradeTime(new Date());
				accountCBussiness.setOperId(accountCInfo.getIssueOperId());
				accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
				accountCBussiness.setCardNo(accountCInfo.getCardNo());
				//新增的字段
				accountCBussiness.setOperName(accountCInfo.getOperName());
				accountCBussiness.setOperNo(accountCInfo.getOperNo());
				accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
				accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());

				//
				accountCBussiness.setBusinessId(accountCInfo.getHisSeqId());
//					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
				accountCBussiness.setSuit(accountCInfo.getSuit());
				accountCBussinessDao.save(accountCBussiness);

				//澳门通清算系统表(MQ)
				Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format.format(accountCBussiness.getTradeTime());
				AcIssuceInfo acIssuceInfo = new AcIssuceInfo("91000", date, accountCBussiness.getCardNo(), vehicleInfo.getVehiclePlate(),
						vehicleInfo.getVehicleColor(), vehicleInfo.getVehicleType(), vehicleInfo.getVehicleWeightLimits().toString(), vehicleInfo.getNSCVehicleType(),
						"", "");
				Long id = sequenceUtil.getSequenceLong("seq_csmsacissuceinfo_no");
				acIssuceInfo.setId(id);
				acIssuceInfo.setAmt(accountCInfo.getCost());//应收金额
				acIssuceInfo.setRealAmt(accountCInfo.getRealCost());//实收金额
				acIssuceInfoDao.save(acIssuceInfo);

				//写给铭鸿清算数据
				AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
				cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);

				//写给铭鸿的清算数据：卡片状态信息
				//TODO 是否用证件类型来判断用户类型
				String userType = "";
				if (MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()),
						CardStateSendSerTypeEnum.acIssue.getValue(), accountCInfo.getCardNo(), "23", userType);

				String obuCode = "";
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;
				//写给铭鸿的清算数据：用户状态信息
				cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCInfo.getCardNo(),
						"23", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
						obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡发行");


				//保存流水记录
				ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), customer.getUserNo(), customer.getOrgan(), accountCBussiness.getCardNo(),
						null, null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(), accountCInfo.getCost(), accountCInfo.getRealCost(),
						accountCInfo.getIssueFlag(), null, null, null, null,
						null, accountCBussiness.getId(), null, null,
						null, null, accountCBussiness.getOperId(), accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(),
						accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
						accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "记帐卡发行", macaoCardCustomer.getId());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setSerType("201");
				serviceWaterDao.save(serviceWater);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				realTransferService.accountCInfoTransfer(customer, accountCInfo,
						vehicleInfo, CardStatusEmeu.NORMAL.getCode(),
						OperationTypeEmeu.ADD.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

				message = "发行成功！";

			} else {
				message = "该卡已发行！";
			}

			return message;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "卡片发行失败");
			e.printStackTrace();
			throw new ApplicationException("卡片发行失败");
		}
	}


	private ServiceFlowRecord getServiceFlowRecord(MainAccountInfo mainAccountInfo, BailAccountInfo bailAccountInfo,
	                                               ServiceFlowRecord serviceFlowRecord) {

		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));

		serviceFlowRecord.setBeforeBailFee(bailAccountInfo.getBailFee());// 修改前保证金账户余额
		serviceFlowRecord.setCurrBailFee(new BigDecimal("0"));// 本次变动保证金账户余额
		serviceFlowRecord.setAfterBailFee(bailAccountInfo.getBailFee());// 修改后保证金账户余额

		return serviceFlowRecord;
	}


	@Override
	public boolean surePublished(String cardNo) {
		boolean result = true;
		try {
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardNo);
			if (accountCInfo == null) {
				//如果该卡没被发行，返回false
				result = false;
			}
			return result;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "验证卡片失败");
			e.printStackTrace();
			throw new ApplicationException("验证卡片失败");
		}
	}

	//挂失之后，补领新卡
	@Override
	public String saveReplaceCard(MainAccountInfo mainAccountInfo,
	                              AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,
	                              AccountCBussiness accountCBussiness,
	                              ServiceFlowRecord serviceFlowRecord, String systemType, MacaoCardCustomer macaoCardCustomer) {
		try {
			InterfaceRecord interfaceRecord = null;

			Map<String, Object> map = inventoryServiceForAgent.omsInterface(newAccountCInfo.getCardNo(), "1", interfaceRecord, "issue",
					null, null, "", "1", "", null, accountCInfo.getRealCost(), "35");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
			if (m != null) {
				newAccountCInfo.setStartDate((Date) m.get("startDate"));
				newAccountCInfo.setEndDate((Date) m.get("endDate"));
			}
			//客服原发行操作

			MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(accountCInfo.getCardNo());
			if (macaoBankAccount == null) {
				return "数据错误：无法找到卡片所属银行账号";
			}

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
			unifiedParam.setType("3");
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);

			//旧记帐卡的车卡标签绑定记录
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			//车辆
			VehicleInfo vehicle = null;
			if (carObuCardInfo != null) {
				vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			}
			if (vehicle != null) {
				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = new VehicleBussiness();
				BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
				vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
				vehicleBussiness.setCustomerID(vehicle.getCustomerID());
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setCardNo(newAccountCInfo.getCardNo());//新卡号
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
				//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
				//vehicleBussiness.setType("58");//记帐卡挂失补卡
				vehicleBussiness.setType(VehicleBussinessEnum.accountCReplaceCard.getValue());
				vehicleBussiness.setOperID(accountCBussiness.getOperId());
				vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
				//新增的字段
				vehicleBussiness.setOperName(accountCBussiness.getOperName());
				vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
				vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
				vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

				vehicleBussiness.setCreateTime(new Date());
				vehicleBussiness.setMemo("记帐卡挂失补卡");

				vehicleBussinessDao.save(vehicleBussiness);

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

				//新增澳门通持卡人绑定关系信息表
				CardHolderInfo cardHolderInfo = new CardHolderInfo();
				cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
				Long SEQ_CSMSCARDHOLDERINFO_NO = sequenceUtil.getSequenceLong("SEQ_CSMSCARDHOLDERINFO_NO");
				cardHolderInfo.setId(SEQ_CSMSCARDHOLDERINFO_NO);
				cardHolderInfo.setType("2");
				cardHolderInfo.setTypeId(newAccountCInfo.getId());
				cardHolderInfoDao.save(cardHolderInfo);

				// 记帐卡业务记录
				BigDecimal accountC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(accountC_bussiness_NO.toString()));
				AccountCApply accountCApply = accountCApplyDao.findByCardNo(newAccountCInfo.getCardNo());

				//回执打印用
				accountCBussiness.setBusinessId(newAccountCInfo.getHisSeqId());
				accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

				accountCBussinessDao.save(accountCBussiness);

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

				//TODO 是否用证件类型来判断用户类型
				String userType = "";
				if (MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}

				String obuCode = "";
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;

				//如果旧卡有绑定车辆，才发送绑定车辆数据给铭鸿
				if (vehicle != null) {
					//增加澳门通清算数据   发行   
					Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String t = format.format(accountCBussiness.getTradeTime());
					AcIssuceInfo acIssuceInfo = new AcIssuceInfo("91000", t, newAccountCInfo.getCardNo(),
							vehicle.getVehiclePlate(), vehicle.getVehicleColor(), vehicle.getVehicleType(),
							vehicle.getVehicleWeightLimits().toString(), vehicle.getNSCVehicleType(), "", "");
					acIssuceInfo.setId(sequenceUtil.getSequenceLong("seq_csmsacissuceinfo_no"));
					acIssuceInfo.setAmt(newAccountCInfo.getCost());//应收金额
					acIssuceInfo.setRealAmt(newAccountCInfo.getRealCost());//实收金额
					acIssuceInfoDao.save(acIssuceInfo);

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
					//旧卡注销
					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

					//新卡发行
					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()),
							CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);
				}

				//保存清算数据
				cardSecondIssuedService.saveAccountCard(newAccountCInfo, accountCApply);

				//挂失补领需要注销旧卡
				blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
						, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
						accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
						new Date());

				//保存流水记录
				MacaoCardCustomer macaoCustomer = macaoDao.findById(macaoCardCustomer.getId());
				ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), null, null, accountCBussiness.getOldCardNo(),
						accountCBussiness.getCardNo(), null, accountCBussiness.getState(), macaoCustomer.getBankAccountNumber(),
						newAccountCInfo.getCost(), newAccountCInfo.getRealCost(), newAccountCInfo.getIssueFlag(),
						null, null, null, null, null,
						accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
						accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
						accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "记帐卡补领", macaoCustomer.getId());

				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWater.setSerType("206");
				serviceWaterDao.save(serviceWater);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(newAccountCInfo.getCardNo());
				Customer customer = new Customer();
				customer.setUserNo(macaoCustomer.getUserNo());
				customer.setOrgan(macaoCustomer.getCnName());
				customer.setAgentName(macaoCustomer.getAgentName());
				realTransferService.accountCInfoTransfer(customer, accountCInfo,
						vehicleInfo, CardStatusEmeu.NOCARD_CANCLE.getCode(),
						OperationTypeEmeu.UPDATE.getCode());
				// 旧卡黑名单名单上传及变更
				noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
						new Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu.EN_BLACK.getCode());
				// 新卡用户卡信息上传及变更
				realTransferService.accountCInfoTransfer(customer, newAccountCInfo, vehicleInfo,
						CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.ADD
								.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

				return "true";
			}
			return "补领失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "补领失败");
			e.printStackTrace();
			throw new ApplicationException("补领失败");
		}
	}

	@Override
	public String saveUnusable(AccountCInfo accountCInfo,
	                           AccountCBussiness accountCBussiness,
	                           ServiceFlowRecord serviceFlowRecord, boolean result, String systemType) {
		try {

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

				//澳门通持卡人信息表
				MacaoCardCustomer macaoCardCustomer = macaoDao.getCustomerByCardNo(accountCBussiness.getCardNo());

				//TODO 是否用证件类型来判断用户类型
				String userType = "";
				if (MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}

				//清算接口
				if (result) {//不需要下黑名单的
					//有卡锁定，写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCBussiness.getCardNo(), "23", userType);
				} else {//需要下黑名单的
					//记帐卡旧卡无卡锁定或有卡锁定失败需下发黑名单
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());

					//无卡锁定或有卡锁定失败，写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getCardNo(), "23", userType);
				}

				//旧卡的注销
				Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String t = format.format(accountCBussiness.getTradeTime());
				AcCancelInfo acCancelInfo = new AcCancelInfo("91003", t, accountCInfo.getCardNo(), "1", "1", "", "", "", "", "");
				acCancelInfo.setId(sequenceUtil.getSequenceLong("seq_csmsaccannelinfo_no"));
				acCancelInfoDao.save(acCancelInfo);

				MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(accountCInfo.getCardNo());
				//保存流水记录
				ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), null, null, accountCBussiness.getCardNo(),
						null, null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(),
						null, null, null,
						null, null, null, null, null,
						accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
						accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
						accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "记帐卡旧卡锁定", macaoCardCustomer.getId());

				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setSerType("207");
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWaterDao.save(serviceWater);


				return "true";
			}
			return "旧卡锁定失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "旧卡锁定失败");
			e.printStackTrace();
			throw new ApplicationException("旧卡锁定失败");
		}
	}

	@Override
	public String saveGainCard(AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,
	                           AccountCBussiness accountCBussiness,
	                           ServiceFlowRecord serviceFlowRecord, String systemType, MacaoCardCustomer macaoCardCustomer) {
		try {

			InterfaceRecord interfaceRecord = null;

			Map<String, Object> map = inventoryServiceForAgent.omsInterface(newAccountCInfo.getCardNo(), "1", interfaceRecord, "issue",
					null, null, "", "1", "", null, accountCInfo.getRealCost(), "37");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与结束时间
			Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
			if (m != null) {
				/*//设置有效起始时间与有效结束时间
				newAccountCInfo.setStartDate((Date)m.get("startDate"));
				//收费了就用新的，不收费就用旧的
				if(newAccountCInfo.getRealCost().compareTo(new BigDecimal("0"))==0){
					newAccountCInfo.setEndDate(accountCInfo.getEndDate());
				}else{
					newAccountCInfo.setEndDate((Date)m.get("endDate"));
				}*/

				//2017/04/12 需求改变。有效启用时间与截至时间都从一发拿，与有偿无偿没有关系
				newAccountCInfo.setStartDate((Date) m.get("startDate"));
				newAccountCInfo.setEndDate((Date) m.get("endDate"));
			}
			//客服原发行操作

			MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(accountCInfo.getCardNo());
			if (macaoBankAccount == null) {
				return "数据错误：无法找到卡片所属银行账号";
			}

			// 主账户信息
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setNewAccountCInfo(newAccountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("16");
			unifiedParam.setOperId(serviceFlowRecord.getOperID());
			unifiedParam.setPlaceId(serviceFlowRecord.getPlaceID());
			unifiedParam.setOperName(serviceFlowRecord.getOperName());
			unifiedParam.setOperNo(serviceFlowRecord.getOperNo());
			unifiedParam.setPlaceName(serviceFlowRecord.getPlaceName());
			unifiedParam.setPlaceNo(serviceFlowRecord.getPlaceNo());
			unifiedParam.setType("5");
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			//旧记帐卡的车卡标签绑定记录
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			//车辆
			VehicleInfo vehicle = null;
			if (carObuCardInfo != null) {
				vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			}
			if (vehicle != null) {
				AccountCBussiness tmp = new AccountCBussiness();
				tmp.setCardNo(accountCInfo.getCardNo());
				tmp.setState(AccountCBussinessTypeEnum.accCardLock.getValue());//记帐卡旧卡锁定
				AccountCBussiness lockCardBussiness = accountCBussinessDao.find(tmp);
				if (lockCardBussiness == null) {
					return "数据异常：无法找到旧卡的锁卡业务记录";
				}
				//新增车辆业务记录表CSMS_Vehicle_Bussiness
				VehicleBussiness vehicleBussiness = new VehicleBussiness();
				BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
				vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
				vehicleBussiness.setCustomerID(vehicle.getCustomerID());
				vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
				vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
				vehicleBussiness.setCardNo(newAccountCInfo.getCardNo());//新卡号
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
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
				//vehicleBussiness.setType("52");//记帐卡换卡
				vehicleBussiness.setOperID(accountCBussiness.getOperId());
				vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
				//新增的字段
				vehicleBussiness.setOperName(accountCBussiness.getOperName());
				vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
				vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
				vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

				vehicleBussiness.setCreateTime(new Date());

				vehicleBussinessDao.save(vehicleBussiness);
			} else {
				//如果vehicle为空，按正常数据，旧卡之前是挂起了再去旧卡锁定的，所以没有绑定车辆了
				//这个时候要将新卡的状态改为挂起
				newAccountCInfo.setState(AccountCardStateEnum.disabled.getIndex());
				//重新设置传参newPrepaidC
				unifiedParam.setNewAccountCInfo(newAccountCInfo);
			}

			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				//若旧卡有绑定车辆，新卡要继承车辆绑定表关系
				if (carObuCardInfo != null) {
					carObuCardInfoDao.updateAccountID(newAccountCInfo.getId(), accountCInfo.getId());
				}
				//新增澳门通持卡人绑定关系信息表
				CardHolderInfo cardHolderInfo = new CardHolderInfo();
				cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
				Long SEQ_CSMSCARDHOLDERINFO_NO = sequenceUtil.getSequenceLong("SEQ_CSMSCARDHOLDERINFO_NO");
				cardHolderInfo.setId(SEQ_CSMSCARDHOLDERINFO_NO);
				cardHolderInfo.setType("2");
				cardHolderInfo.setTypeId(newAccountCInfo.getId());
				cardHolderInfoDao.save(cardHolderInfo);

				// 记帐卡业务记录
				BigDecimal accountC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(accountC_bussiness_NO.toString()));
				//回执打印用
				accountCBussiness.setBusinessId(newAccountCInfo.getHisSeqId());

				accountCBussinessDao.save(accountCBussiness);

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


				//TODO 是否用证件类型来判断用户类型
				String userType = "";
				if (MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				String obuSeq = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;

				//如果旧卡有绑定车辆，才发送绑定车辆数据给铭鸿
				if (vehicle != null) {
					//增加澳门通清算数据   发行   
					Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String t = format.format(accountCBussiness.getTradeTime());
					AcIssuceInfo acIssuceInfo = new AcIssuceInfo("91000", t, newAccountCInfo.getCardNo(),
							vehicle.getVehiclePlate(), vehicle.getVehicleColor(), vehicle.getVehicleType(),
							vehicle.getVehicleWeightLimits().toString(), vehicle.getNSCVehicleType(), "", "");
					acIssuceInfo.setId(sequenceUtil.getSequenceLong("seq_csmsacissuceinfo_no"));
					acIssuceInfo.setAmt(newAccountCInfo.getCost());//应收金额
					acIssuceInfo.setRealAmt(newAccountCInfo.getRealCost());//实收金额
					acIssuceInfoDao.save(acIssuceInfo);

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
					//新卡发行
					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()),
							CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);
				}


				//写给铭鸿记帐卡二发清算数据
				AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
				cardSecondIssuedService.saveAccountCard(newAccountCInfo, accountCApply);

				//保存流水记录
				ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), null, null, accountCBussiness.getOldCardNo(),
						accountCBussiness.getCardNo(), null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(),
						newAccountCInfo.getCost(), newAccountCInfo.getRealCost(), newAccountCInfo.getIssueFlag(),
						null, null, null, null, null,
						accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
						accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
						accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "记帐卡领取新卡", macaoCardCustomer.getId());

				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setSerType("208");
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWaterDao.save(serviceWater);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				AccountCBussiness tmp = new AccountCBussiness();
				tmp.setCardNo(accountCInfo.getCardNo());
				tmp.setState(AccountCBussinessTypeEnum.accCardLock.getValue());//记帐卡旧卡锁定
				AccountCBussiness lcb = accountCBussinessDao.find(tmp);
				Integer cardType = null;
				if ("1".equals(lcb.getLockType())) {
					cardType = CardStatusEmeu.HADCARD_CANCLE.getCode();
				} else {
					cardType = CardStatusEmeu.NOCARD_CANCLE.getCode();
				}
				AccountCApply newAccountCApply = accountCApplyDao.findByCardNo(newAccountCInfo
						.getCardNo());
				Customer macaoCustomer = customerDao.findById(accountCInfo.getCustomerId());
				realTransferService.accountCInfoTransfer(macaoCustomer, accountCInfo, vehicle,
						cardType, OperationTypeEmeu.UPDATE.getCode());
				// 旧卡黑名单状态上传及变更
				if (!"1".equals(lcb.getLockType())) {
					noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new
							Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
							.EN_BLACK.getCode());
				}
				// 新卡用户卡信息上传及变更
				realTransferService.accountCInfoTransfer(macaoCustomer, newAccountCInfo, vehicle,
						CardStatusEmeu.NORMAL.getCode(),
						OperationTypeEmeu.ADD.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

				return "true";
			}
			return "领取新卡失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "领取新卡失败");
			e.printStackTrace();
			throw new ApplicationException("领取新卡失败");
		}
	}

	@Override
	public String saveStopCard(Cancel cancel, ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness,
	                           Customer customer, AccountCInfo accountCInfo, String systemType) {
		try {

			//回收原业务
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("6");//stop card

			//List<Map<String, Object>> accountList = accountCInfoDao.findAccountCByCustomerID(accountCInfo.getCustomerId(),accountCInfo.getAccountId());
			//SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());


			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// save account bussniess
				BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
				accountCBussiness.setBusinessId(accountCInfo.getId());
				accountCBussinessDao.save(accountCBussiness);

				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				macaoDao.save(cancel);

				//解绑车辆信息
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
				VehicleInfo vehicleInfo = null;
				if (carObuCardInfo != null) {
					carObuCardInfo.setAccountCID(null);
					carObuCardInfoDao.update(carObuCardInfo);

					vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				}

				//若卡片之前有绑定车辆，则此时涉及到解绑车辆，需要保存车辆信息业务记录表
				if (vehicleInfo != null) {
					//新增车辆业务记录表CSMS_Vehicle_Bussiness
					VehicleBussiness vehicleBussiness = new VehicleBussiness();
					BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
					vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
					vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
					vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
					vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
					vehicleBussiness.setCardNo(accountCInfo.getCardNo());
					vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
					//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
					if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {//无卡
						vehicleBussiness.setType(VehicleBussinessEnum.accountCStopWithoutCard.getValue());//记帐卡无卡终止使用
						vehicleBussiness.setMemo("记帐卡无卡终止使用");
					} else {
						vehicleBussiness.setType(VehicleBussinessEnum.accountCStopWithCard.getValue());//记帐卡有卡终止使用
						vehicleBussiness.setMemo("记帐卡有卡终止使用");
					}
					vehicleBussiness.setOperID(accountCBussiness.getOperId());
					vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
					//新增的字段
					vehicleBussiness.setOperName(accountCBussiness.getOperName());
					vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
					vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
					vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());
					vehicleBussiness.setCreateTime(new Date());

					vehicleBussinessDao.save(vehicleBussiness);
				}

				//资金转移确认表
				DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getCardNo());

				DbasCardFlow dbasCard = new DbasCardFlow();
				dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
				dbasCard.setNewCardNo(accountCBussiness.getCardNo());
				dbasCard.setOldCardNo(accountCBussiness.getCardNo());
				if (dbasCardFlow != null) dbasCard.setCardNo(dbasCardFlow.getCardNo());
				else dbasCard.setCardNo(accountCBussiness.getCardNo());
				dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
				dbasCard.setApplyTime(new Date());
				dbasCard.setServiceId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
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
				if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {//无卡
					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
				} else {
					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
				}
				dbasCardFlowDao.save(dbasCard);

				MacaoCardCustomer macaoCardCustomer = macaoDao.getCustomerByCardNo(accountCBussiness.getCardNo());

				//TODO 是否用证件类型来判断用户类型
				String userType = "";
				if (MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
						|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				String obuSeq = "";
				String obuCode = "";
				Date obuIssueTime = null;
				Date obuExpireTime = null;
				if ("0".equals(serviceFlowRecord.getIsNeedBlacklist())) {//不下发黑名单
					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡有卡注销");
					}

				} else {

					//如果卡片为挂失卡，则先解除挂失黑名单
					BlackListTemp blackListTemp = new BlackListTemp();
					blackListTemp.setCardType(Constant.ACCOUNTCTYPE);
					blackListTemp.setCardNo(accountCInfo.getCardNo());
					blackListTemp.setStatus(2);//挂失黑名单
					List<Map<String, Object>> list = blackListDao.findByBlackListTemp(blackListTemp);
					if (list.size() > 0) {
						//保存黑名单流水解除挂失黑名单	给铭鸿
						blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
								, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
								accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
								new Date());
					}

					//终止使用
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());


					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡无卡注销");
					}
				}

				//澳门通清算数据
				Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format.format(accountCBussiness.getTradeTime());
				String s = null;
				if (accountCBussiness.getState().equals("8")) {//有卡
					s = "1";
				}
				if (accountCBussiness.getState().equals("9")) {//无卡
					s = "2";
				}
				AcCancelInfo acCancelInfo = new AcCancelInfo("91003", date, accountCInfo.getCardNo(), s,
						"1", cancel.getReason(), cancel.getRemark(), "", "", "");

				Long id = sequenceUtil.getSequenceLong("seq_csmsaccannelinfo_no");
				acCancelInfo.setId(id);
				acCancelInfoDao.save(acCancelInfo);


				MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(accountCInfo.getCardNo());
				//保存流水记录
				ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), customer.getUserNo(), customer.getOrgan(), accountCBussiness.getCardNo(),
						null, null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(),
						null, null, null,
						null, null, null, null, null,
						accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
						accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
						accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "记帐卡终止使用", macaoCardCustomer.getId());

				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				if (accountCBussiness.getState().equals("8")) {//有卡
					serviceWater.setSerType("202");
				}
				if (accountCBussiness.getState().equals("9")) {//无卡
					serviceWater.setSerType("203");
				}
				serviceWaterDao.save(serviceWater);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				Integer cardStatus = null;
				AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
				if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {
					// 无卡
					cardStatus = CardStatusEmeu.NOCARD_CANCLE.getCode();
				} else {
					// 有卡
					cardStatus = CardStatusEmeu.HADCARD_CANCLE.getCode();
				}
				// 用户卡信息上传及变更
				realTransferService.accountCInfoTransfer(customer, accountCInfo,
						vehicleInfo, cardStatus, OperationTypeEmeu.UPDATE.getCode());

				if (CardStatusEmeu.NOCARD_CANCLE.getCode().intValue() == cardStatus.intValue()) {
					// 用户卡黑名单上传及变更
					noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new
							Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
							.EN_BLACK.getCode());
				}
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

				return "true";
			}
			return "终止卡失败";

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "终止卡失败");
			e.printStackTrace();
			throw new ApplicationException("终止卡失败");
		}
	}

	@Override
	public List<Map<String, Object>> findAvailableVehicle(String bankAccountNumber) {
		return macaoDao.findAvailableVehicle(bankAccountNumber);
	}


	/*
	 * 查询发行列表
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoUTService#findCardByPager(com.hgsoft.macao.entity.MacaoCardCustomer, com.hgsoft.accountC.entity.AccountCInfo, com.hgsoft.customer.entity.VehicleInfo)
	 */
	@Override
	public Pager findCardByPager(Pager pager, MacaoCardCustomer macaoCardCustomer, AccountCInfo accountCInfo,
	                             VehicleInfo vehicleInfo) {
		return macaoDao.findCardByPager(pager, macaoCardCustomer, accountCInfo, vehicleInfo);
	}

	/*
	 * 查找卡号发行信息详情
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoUTService#findIssueDetail(java.lang.Long)
	 */
	@Override
	public Map<String, Object> findIssueDetail(Long id) {
		return macaoDao.findIssueDetail(id);
	}


	@Override
	public Map<String, Object> findMacaoBankAccountByCardNo(String cardNo) {
		Map<String, Object> m = macaoDao.MacaoBankAccountByCardNo(cardNo);
		return m;
	}


	@Override
	public Pager findStopCardByCustomer(Pager pager, MacaoCardCustomer macaoCardCustomer, AccountCInfo accountCInfo) {
		pager = macaoDao.findStopCardByCustomer(pager, macaoCardCustomer, accountCInfo);
		return pager;
	}


	@Override
	public Cancel findByCancelId(Long id) {
		Cancel cancel = macaoDao.findByCancelId(id);
		return cancel;
	}


	/*
	 * 判断是否为澳门通卡
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoUTService#checkMacaoCard(java.lang.String)
	 */
	@Override
	public boolean checkMacaoCard(String cardNo) {
		List list = macaoCardSectionDao.findMacaoCardSection(cardNo);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}


	/**
	 * @param cardNo
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年4月17日
	 */
	@Override
	public boolean checkCard(Long id, String cardNo) {
		return macaoDao.checkCard(id, cardNo);
	}

}
