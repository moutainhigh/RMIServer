package com.hgsoft.accountC.service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.BailDao;
import com.hgsoft.accountC.dao.NewCardApplyDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.accountC.entity.NewCardVehicle;
import com.hgsoft.accountC.serviceInterface.IAccountCInfoService;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.dao.StopAcListDao;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.DBACardFlowCardTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowEndSerTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowReadFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowSerTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.ReceiptSuitEnum;
import com.hgsoft.common.Enum.SureVehicleEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardBailAddReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardIssueReceipt;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.system.dao.SaleTypeDetailDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.RealTransferService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountCInfoService implements IAccountCInfoService {
	@Resource
	private AccountCInfoDao accountCInfoDao;

	@Resource
	private MainAccountInfoDao mainAccountInfoDao;

	@Resource(name = "unifiedInterfaceService")
	private IUnifiedInterface unifiedInterfaceService;

	@Resource
	private BailAccountInfoDao bailAccountInfoDao;

	@Resource
	private CardSecondIssuedService cardSecondIssuedService;
	@Resource
	private ICardObuService cardObuService;

	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;

	@Resource
	private AccountCBussinessDao accountCBussinessDao;

	@Resource
	private AccountCApplyDao AccountCApplyDao;

	@Resource
	private SubAccountInfoDao subAccountInfoDao;

	@Resource
	private VehicleInfoDao vehicleInfoDao;

	@Resource
	private IInventoryService inventoryService;

	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;

	@Resource
	private InterfaceRecordDao interfaceRecordDao;

	@Resource
	private CustomerDao customerDao;

	@Resource
	SequenceUtil sequenceUtil;
	
	/*@Resource
	private ACinfoDao aCinfoDao;*/

	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	@Resource
	private BailDao bailDao;

	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */

	@Resource
	private IAccountCService accountCService;
	@Resource
	private StopAcListDao stopAcListDao;

	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;

	@Resource
	private NewCardApplyDao newCardApplyDao;

	@Resource
	private ReceiptDao receiptDao;

	@Resource
	private SaleTypeDetailDao saleTypeDetailDao;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD20171017
	@Resource
	private RealTransferService realTransferService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD20171017

	private static Logger logger = Logger.getLogger(AccountCInfoService.class.getName());

	public Pager findAccountCInfoList(Pager pager, Customer customer, String bankAccount) {
		return accountCInfoDao.findAccountCInfos(pager, customer, bankAccount);
	}


	/**
	 * 判断该卡是否已经发行
	 *
	 * @param cardNo
	 * @return
	 */
	public boolean surePublished(String cardNo) {
		boolean result = true;
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardNo);
		if (accountCInfo == null) {
			//如果该卡没被发行，返回false
			result = false;
		}
		return result;
	}

	/**
	 * 1.客户主账户余额是否大于单卡保证金+工本费
	 * 2.新增卡车辆保证金异常
	 *
	 * @param accountCInfo
	 * @param customer
	 * @param vehicleInfoId
	 * @return
	 */
	public Map<String, Object> sureAvailableBalance(AccountCInfo accountCInfo, AccountCApply accountCApply, Customer customer, Long vehicleInfoId) {
		Map<String, Object> result = new HashMap<>();

		MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
		BigDecimal costAddBail = new BigDecimal("0");

		VehicleInfo vehicleInfo = vehicleInfoDao.findById(vehicleInfoId);
		//判断客车货车
		/*BigDecimal bail = null;
		if(VehicleTypeEnum.car.getValue().equals(vehicleInfo.getVehicleType())){
			bail = accountCApply.getBail();
		}else if(VehicleTypeEnum.truck.getValue().equals(vehicleInfo.getVehicleType())){
			bail = accountCApply.getTruckBail();
		}*/

		NewCardVehicle newCardVehicle = newCardApplyDao.findNewCardVehicleById(vehicleInfoId);
		if (newCardVehicle != null) {
			if (newCardVehicle.getBail() != null) {
				costAddBail = accountCInfo.getCost().add(newCardVehicle.getBail());
				//如果客户主账户余额大于单卡保证金+工本费，返回true
				if (mainAccountInfo.getAvailableBalance().compareTo(costAddBail) > -1) {
//				result.put("vehiclBail",newCardVehicle.getBail());
					result.put("result", 0);//正常
				} else {
//				result.put("vehiclBail",0);
					result.put("result", 1);//账户余额不足(账户可用余额小于工本费+单卡保证金)
				}
			} else {
//			result.put("vehiclBail",0);
				result.put("result", 2);//获取新增卡车辆保证金异常
//			costAddBail = accountCInfo.getCost();
//			return result;
			}
		} else {
			result.put("result", 3);//新增卡被选中车辆的状态显示已使用
		}


		return result;
	}

	public String saveAccountCInfo(AccountCInfo accountCInfo, AccountCApply accountCApply, Customer customer, VehicleInfo vehicleInfo, SubAccountInfo subAccountInfo, String systemType, Map<String, Object> params) {
		try {
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryService.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord, "issue",
					accountCInfo.getIssuePlaceId(), accountCInfo.getIssueOperId(), accountCInfo.getOperName(), "1", "customPoint", null, accountCInfo.getRealCost(), "31", "");
			boolean result = (Boolean) map.get("result");
			if (!result) {
				return map.get("message").toString();
			}
			//设置有效起始时间与有效结束时间
			accountCInfo.setStartDate((Date) map.get("startDate"));
			accountCInfo.setEndDate((Date) map.get("endDate"));

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
			accountCInfo.setIssueTime(new Date());

			//客服原发行操作
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			NewCardVehicle newCardVehicle = newCardApplyDao.findNewCardVehicleById(carObuCardInfo.getVehicleID());
			//车辆
			vehicleInfo = vehicleInfoDao.findById(vehicleInfo.getId());
			accountCApply = AccountCApplyDao.findByBankAccount(accountCApply.getBankAccount());
			if (mainAccountInfo != null) {
				//记帐卡申请历史表（记帐卡发行，记帐卡申请表剩余数量-1，要移历史）
				AccountCapplyHis accountCapplyHis = new AccountCapplyHis();
				BigDecimal SEQ_CSMSAccountCapplyhis_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCapplyhis_NO");
				accountCapplyHis.setId(Long.valueOf(SEQ_CSMSAccountCapplyhis_NO.toString()));
				accountCapplyHis.setGenReason("1");//修改

				UnifiedParam unifiedParam = new UnifiedParam();
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				unifiedParam.setType("21");
				unifiedParam.setAccountCInfo(accountCInfo);
				//unifiedParam.setAccountCApply(AccountCApplyDao.find(accountCApply));
				unifiedParam.setAccountCApply(accountCApply);
				unifiedParam.setBailAccountInfo(bailAccountInfo);
				unifiedParam.setCarObuCardInfo(carObuCardInfo);
				unifiedParam.setSubAccountInfo(subAccountInfo);
				unifiedParam.setOperId(accountCInfo.getIssueOperId());
				unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
				unifiedParam.setOperName(accountCInfo.getOperName());
				unifiedParam.setOperNo(accountCInfo.getOperNo());
				unifiedParam.setPlaceName(accountCInfo.getPlaceName());
				unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());

				unifiedParam.setAccountCapplyHis(accountCapplyHis);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {

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

					//回执打印用
					accountCBussiness.setBusinessId(accountCInfo.getHisSeqId());
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
					accountCBussiness.setSuit(accountCInfo.getSuit());
					accountCBussinessDao.save(accountCBussiness);
					accountCInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
					//原清算数据，没用了
					/*accountCService.saveACinfo(0,accountCInfo,systemType);*/

					//保存资金转移确认数据表
					DbasCardFlow dbasCard = new DbasCardFlow();
					dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
					dbasCard.setNewCardNo(accountCBussiness.getCardNo());
					dbasCard.setOldCardNo(accountCBussiness.getCardNo());
					dbasCard.setCardNo(accountCBussiness.getCardNo());
					dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
					dbasCard.setSerType(DBACardFlowSerTypeEnum.accountCCardIssuice.getValue());
					dbasCard.setApplyTime(new Date());
					dbasCard.setServiceId(accountCBussiness.getId());
					dbasCard.setCardAmt(new BigDecimal("0"));
					dbasCard.setReadFlag(DBACardFlowReadFlagEnum.disabledRead.getValue());
					dbasCard.setEndFlag(DBACardFlowEndFlagEnum.arriComplete.getValue());//已完成
					dbasCard.setExpireFlag(DBACardFlowExpireFlagEnum.arriDispute.getValue());//已过争议期
					dbasCard.setOperId(accountCBussiness.getOperId());
					dbasCard.setOperno(accountCBussiness.getOperNo());
					dbasCard.setOpername(accountCBussiness.getOperName());
					dbasCard.setPlaceId(accountCBussiness.getPlaceId());
					dbasCard.setPlacename(accountCBussiness.getPlaceName());
					dbasCard.setPlaceno(accountCBussiness.getPlaceNo());
					dbasCard.setEndServiceId(accountCBussiness.getId());
					dbasCard.setEndSerType(DBACardFlowEndSerTypeEnum.accountCCardIssuice.getValue());
					dbasCard.setEndCardAmt(new BigDecimal("0"));
					dbasCardFlowDao.save(dbasCard);

					//原清算数据，没用了
					/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
					userInfoBaseList.setNetNo("4401");
					//userInfoBaseList.setIssuerId("");发行方唯一标识
					//userInfoBaseList.setAgent();发行代理单位编码
					userInfoBaseList.setCardType(2);
					userInfoBaseListDao.save(userInfoBaseList, accountCInfo, null);*/


					//保证金资金设置表
					Bail bail = new Bail();
					BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSBail_NO");
					bail.setId(Long.parseLong(seq.toString()));
					bail.setUserNo(customer.getUserNo());
					bail.setCardno(accountCInfo.getCardNo());
					bail.setTradingType("1");
					bail.setAccountId(subAccountInfo.getId());


					BigDecimal bailFee = newCardVehicle.getBail();
					/*if(VehicleTypeEnum.car.getValue().equals(vehicleInfo.getVehicleType())){
						bailFee = accountCApply.getBail();
					}else if(VehicleTypeEnum.truck.getValue().equals(vehicleInfo.getVehicleType())){
						bailFee = accountCApply.getTruckBail();
					}*/
					bail.setBailFee(bailFee);
					bail.setPayFlag("0");
					bail.setUp_Date(new Date());
					bail.setAppTime(new Date());
					bail.setBankMember(accountCApply.getAccName());
					bail.setBankNo(accountCApply.getBankAccount());
					bail.setBankOpenBranches(accountCApply.getBankName());
					bail.setDflag("0");
					bail.setSetTime(new Date());
					bail.setOperId(accountCInfo.getIssueOperId());
					bail.setPlaceId(accountCInfo.getIssuePlaceId());
					bail.setOperName(accountCInfo.getOperName());
					bail.setOperNo(accountCInfo.getOperNo());
					bail.setPlaceName(accountCInfo.getPlaceName());
					bail.setPlaceNo(accountCInfo.getPlaceNo());
					bail.setApplyTime(new Date());
					bail.setUpreason("记帐卡发行");//2017年9月5日新增 hzw
					bailDao.save(bail, customer);

					//子账户保证金与保证金余额新增
					subAccountInfoDao.updateBail(subAccountInfo.getId(), bail.getBailFee());


					//调整的客服流水
					ServiceWater serviceWater = new ServiceWater();
					Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

					serviceWater.setId(serviceWater_id);

					serviceWater.setCustomerId(customer.getId());
					serviceWater.setUserNo(customer.getUserNo());
					serviceWater.setUserName(customer.getOrgan());
					serviceWater.setCardNo(accountCInfo.getCardNo());
					serviceWater.setSerType("201");//201记帐卡发行
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
					serviceWater.setAmt(accountCInfo.getCost());//应收金额
					serviceWater.setAulAmt(accountCInfo.getRealCost());//实收金额
					serviceWater.setSaleWate(accountCInfo.getIssueFlag());//销售方式
					serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
					serviceWater.setAccountCBussinessId(accountCBussiness.getId());
					serviceWater.setOperId(accountCBussiness.getOperId());
					serviceWater.setOperName(accountCBussiness.getOperName());
					serviceWater.setOperNo(accountCBussiness.getOperNo());
					serviceWater.setPlaceId(accountCBussiness.getPlaceId());
					serviceWater.setPlaceName(accountCBussiness.getPlaceName());
					serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
					serviceWater.setRemark("自营客服系统：记帐卡发行");
					serviceWater.setOperTime(new Date());

					serviceWaterDao.save(serviceWater);

					//记帐卡发行回执
					AccCardIssueReceipt accCardIssueReceipt = new AccCardIssueReceipt();
					accCardIssueReceipt.setTitle("记帐卡发行回执");
					accCardIssueReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
					accCardIssueReceipt.setAccCardNo(accountCInfo.getCardNo());
					accCardIssueReceipt.setApplyBankAccount(accountCApply.getBankAccount());
					accCardIssueReceipt.setAccCardCost(NumberUtil.get2Decimal(accountCInfo.getCost().doubleValue() * 0.01));
					accCardIssueReceipt.setAccCardIssueFlag(this.saleTypeDetailDao.findSaleTypeNanmeByCode(accountCInfo.getIssueFlag()));    //销售方式
					accCardIssueReceipt.setAccCardSuit(ReceiptSuitEnum.getNameByValue(accountCInfo.getSuit()) == null ? "无" : ReceiptSuitEnum.getNameByValue(accountCInfo.getSuit()));
					accCardIssueReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
					accCardIssueReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
					accCardIssueReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits() + "");
					accCardIssueReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicleInfo.getNSCVehicleType()));
					accCardIssueReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
					accCardIssueReceipt.setVehicleOwner(vehicleInfo.getOwner());
					Receipt receipt = new Receipt();
					receipt.setTypeCode(AccountCBussinessTypeEnum.accCardIssue.getValue());
					receipt.setTypeChName(AccountCBussinessTypeEnum.accCardIssue.getName());
					receipt.setCardNo(accCardIssueReceipt.getAccCardNo());
					receipt.setVehicleColor(vehicleInfo.getVehicleColor());
					receipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
					this.saveReceipt(receipt, accountCBussiness, accCardIssueReceipt, customer);

					//保证金收款回执
					AccCardBailAddReceipt accCardBailAddReceipt = new AccCardBailAddReceipt();
					accCardBailAddReceipt.setTitle("保证金收款回执");
					accCardBailAddReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
					accCardBailAddReceipt.setAccCardNo(accountCInfo.getCardNo());
					accCardBailAddReceipt.setApplyBankAccount(accountCApply.getBankAccount());
					accCardBailAddReceipt.setBailFee(NumberUtil.get2Decimal(bail.getBailFee().doubleValue() * 0.01));
					bailAccountInfo = bailAccountInfoDao.findByCustomerID(bailAccountInfo.getMainId());
					if (bailAccountInfo != null) {
						accCardBailAddReceipt.setBussBailFee(NumberUtil.get2Decimal(bailAccountInfo.getBailFee().doubleValue() * 0.01));
					}
					accCardBailAddReceipt.setBailTradingType("1".equals(bail.getTradingType()) ? "正常" : ("2".equals(bail.getTradingType()) ? "过户" : ""));
					receipt = new Receipt();
					receipt.setTypeCode(AccountCBussinessTypeEnum.accCardBaidAdd.getValue());
					receipt.setTypeChName(AccountCBussinessTypeEnum.accCardBaidAdd.getName());
					receipt.setCardNo(accCardBailAddReceipt.getAccCardNo());
					this.saveReceipt(receipt, accountCBussiness, accCardBailAddReceipt, customer);

					//保存清算数据
					cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);
//					CardSecondIssued cardSecondIssued = new CardSecondIssued();
//					cardSecondIssued.setCardCode(accountCBussiness.getCardNo());
//					cardSecondIssued.setCardType("23");
//					cardSecondIssued.setBankAccount(accountCApply.getBankAccount());
//					cardSecondIssued.setId(accountCBussiness.getId());
//					cardSecondIssued.setSdate(accountCInfo.getIssueTime());
//					cardSecondIssued.setStatus(0);
//					cardSecondIssued.setUpdatetime(new Date());
//					cardSecondIssuedDao.saveCardSecondIssued(cardSecondIssued);

					//写给铭鸿的清算数据：卡片状态信息
					String userType = "";
					if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
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


					//发行成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);

						//ygz wangjinhao---------------------------------- start CARDUPLOAD20171017
						realTransferService.accountCInfoTransfer(customer, accountCInfo,
								vehicleInfo, CardStatusEmeu.NORMAL.getCode(),
								OperationTypeEmeu.ADD.getCode());
						//ygz wangjinhao---------------------------------- end CARDUPLOAD20171017

						return "true";
					}
				}

			}
			return "记帐卡发行失败";
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "记帐卡发行失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}


	/**
	 * 找出没有被绑定储蓄卡或记帐卡的车辆信息
	 *
	 * @param vehicleInfo
	 * @return
	 */
	public VehicleInfo findVehicle(VehicleInfo vehicleInfo) {
		return accountCInfoDao.find(vehicleInfo);
	}

	/**
	 * 校验车辆信息
	 *
	 * @param vehicleInfo
	 * @return
	 */
	public String sureVehicle(VehicleInfo vehicleInfo) {
		VehicleInfo v = new VehicleInfo();
		v.setVehiclePlate(vehicleInfo.getVehiclePlate());
		v.setVehicleColor(vehicleInfo.getVehicleColor());
		VehicleInfo v2 = vehicleInfoDao.find(v);
		if (v2 == null) {
			//如果该车牌号+车辆颜色不存在，返回0表示该车牌不存在
			return SureVehicleEnum.vehicleNotHere.getValue();
		} else {

			VehicleInfo v3 = accountCInfoDao.find(vehicleInfo);
			if (v3 == null) {
				//表示车辆已经被绑定
				return SureVehicleEnum.vehicleIsBind.getValue();
			} else {
				//表示可以使用该车辆
				return SureVehicleEnum.vehicleNoBind.getValue();
			}
		}
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

	/**
	 * 根据子账户中的记帐卡申请id查找子账户
	 */
	public SubAccountInfo findByApplyId(SubAccountInfo subAccountInfo) {
		return subAccountInfoDao.find(subAccountInfo);
	}


	public Pager findAccountCInfoList(AccountCInfo accountCInfo, SubAccountInfo subAccountInfo, Pager pager) {
		return accountCInfoDao.findAccountCInfosByPager(accountCInfo, subAccountInfo, pager);
	}


	@Override
	public List<Map<String, Object>> findAvailableVehicle(Customer customer, Long accountCApplyId) {
		return accountCInfoDao.findAvailableVehicle(customer, accountCApplyId);
	}

	@Override
	public List<Map<String, Object>> findAvailableVehicle(Customer customer, String vehicleInfoIdstr) {
		return accountCInfoDao.findAvailableVehicle(customer, vehicleInfoIdstr);
	}

	@Override
	public Map<String, Object> getAvailableVehicleCount(Customer customer) {
		return accountCInfoDao.getAvailableVehicleCount(customer);
	}

	@Override
	public List<Map<String, Object>> findBindingVehicle(Customer customer, Long accountCApplyId) {
		return accountCInfoDao.findBindingVehicle(customer, accountCApplyId);
	}

	@Override
	public Map<String, Object> findByCardNo(Long customerid, String cardno) {
		return accountCInfoDao.findByCardNo(customerid, cardno);
	}

	@Override
	public AccountCInfo checkAccountCInfo(Long customerid, String cardno) {
		return accountCInfoDao.checkAccountCInfo(customerid, cardno);
	}

//	/**
//	 * @Descriptioqn:
//	 * @param bankAccount
//	 * @return
//	 * @author lgm
//	 * @date 2017年2月17日
//	 */
//	@Override
//	public List<Map<String, Object>> getStopAcList(String bankAccount) {
//		return stopAcListDao.findByACBAccount(bankAccount);
//	}

	/**
	 * @param bankAccount
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年2月17日
	 */
	@Override
	public boolean getStopCardBlackList(String bankAccount) {
		return accountCInfoDao.getStopCardBlackList(bankAccount);
	}


	/***
	 * 根据客户id查询记帐卡信息
	 * @param customerId
	 * @return
	 */
	@Override
	public AccountCInfo findByCustomerId(Long customerId) {
		return accountCInfoDao.findByCustomerId(customerId);
	}

	public AccountCInfo findByCardNo(String cardNo) {
		return accountCInfoDao.findByCardNo(cardNo);
	}


}

