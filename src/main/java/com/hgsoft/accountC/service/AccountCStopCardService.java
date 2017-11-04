package com.hgsoft.accountC.service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.BailDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IAccountCStopCardService;
import com.hgsoft.accountC.serviceInterface.IBailService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.entity.BlackListWarter;
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
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.dao.CardHolderHisDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.entity.CardHolderHis;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardStopReceipt;
import com.hgsoft.other.vo.receiptContent.acms.JointCardCancelReceipt;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
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
import java.util.Date;
import java.util.Map;

@Service
public class AccountCStopCardService implements IAccountCStopCardService {

	@Resource
	private AccountCDao accountCDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private AccountCBussinessDao accountCBussniessDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private CardHolderDao cardHolderDao;
	@Resource
	private CardHolderHisDao cardHolderHisDao;
	/*@Resource
	private ACinfoDao aCinfoDao;
	@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/

	@Resource
	private IAccountCService accountCService;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private IBailService bailService;
	@Resource
	private BailDao bailDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private BlackListDao blackListDao;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171025
	@Resource
	private NoRealTransferService noRealTransferService;
	@Resource
	private RealTransferService realTransferService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171025

	private static Logger logger = Logger.getLogger(AccountCStopCardService.class.getName());

	@Override
	public Pager findStopCardByCustomer(Pager pager, Customer customer) {
		return accountCDao.findStopCardByCustomer(pager, customer);
	}

	@Override
	public String saveStopCard(ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness, Cancel cancel, Customer customer, AccountCInfo accountCInfo, String systemType, Map<String, Object> params) {
		try {
			/*InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = null;
			if (accountCInfo.getCardNo().length()==16 && AccountCBussinessTypeEnum.accCardStopWithCard.getValue().equals(accountCBussiness.getState())) {
				map = inventoryService.omsInterface(accountCInfo.getCardNo(), "2", interfaceRecord,"",
						accountCBussiness.getPlaceId(),accountCBussiness.getOperId(),accountCBussiness.getOperName(),"1","",null,new BigDecimal("0"),"38");
				boolean result = (Boolean) map.get("result");
				if (!result) {
					return map.get("message").toString();
				}
			}*/

			//记录状态
			String cardState = accountCInfo.getState();

			//回收原业务
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("6");//stop card

//			List<Map<String, Object>> accountList = accountCInfoDao.findAccountCByCustomerID(accountCInfo.getCustomerId(),accountCInfo.getAccountId());


			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// save account bussniess
				BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
				accountCBussiness.setBusinessId(accountCInfo.getId());
				accountCBussniessDao.save(accountCBussiness);

				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				cancelDao.save(cancel);

				SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
				AccountCApply accountCApply = accountCApplyDao.findById(subAccountInfo.getApplyID());
				//记帐卡终止使用时，新增一笔保证金退还申请，保证金退还的金额为当前保证金账户总金额/当前客户下的所有记帐卡数量，并且记录相应的银行账户信息。
				//(在这里直接调用bailService的保证金退还申请方法,传入的参数要处理)
//				if(accountList.size()>0){
					/*

					BigDecimal bailFee = accountCInfo.getBail();
					System.out.println("退还保证金金额+bailFee"+bailFee);
					Bail bail = new Bail();
					bail.setOperId(accountCBussiness.getOperId());
					bail.setPlaceId(accountCBussiness.getPlaceId());
					//新增的字段
					bail.setOperName(accountCBussiness.getOperName());
					bail.setOperNo(accountCBussiness.getOperNo());
					bail.setPlaceName(accountCBussiness.getPlaceName());
					bail.setPlaceNo(accountCBussiness.getPlaceNo());
					bail.setBailFee(bailFee);
					bail.setUpreason("记帐卡终止使用");
					bailService.saveGiveBackBail(bail, customer, accountCApply.getBankAccount(),null);*///先设置为null
//				}

				//解绑车辆信息
				VehicleInfo vehicleInfo = null;
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
				if (carObuCardInfo != null) {
					carObuCardInfo.setAccountCID(null);
					carObuCardInfoDao.update(carObuCardInfo);

					vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				}

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

				//清算接口
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
					//原清算数据，没用了
					//accountCService.saveACinfo(23,accountCInfo, systemType);

					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
				} else {
					//原清算数据，没用了
					//accountCService.saveACinfo(22,accountCInfo, systemType);

					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
				}
				dbasCardFlowDao.save(dbasCard);

				//如果卡片为挂失卡，则先解除挂失黑名单
				if (AccountCardStateEnum.loss.getIndex().equals(cardState)) {
					//保存黑名单流水解除挂失黑名单	给铭鸿
					blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());
				}

				String userType = "";
				if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				if ("0".equals(serviceFlowRecord.getIsNeedBlacklist())) {//不下发黑名单
					//原清算数据，没用了
					//accountCService.saveACinfo(22,accountCInfo, systemType);


					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					String obuSeq = "";
					String obuCode = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡有卡注销");
					}

				} else {
					/*if(noCard == 1){
						accountCBussiness.setState("9");//无卡
					}else{
						accountCBussiness.setState("8");//有卡
					}*/
					//原清算数据，没用了
					/*TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
					TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
					accountCService.saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/

					//
					//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
					//
					//accountCService.saveDarkList(accountCInfo,darkList,"10", "1");


					//记帐卡停止使用注销卡片（写给铭鸿的清算数据）
					//记帐卡无卡停止使用注销卡片下发黑名单
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());


					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					String obuSeq = "";
					String obuCode = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡无卡注销");
					}
				}
				//原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseList.setCardType(2);
				userInfoBaseListDao.save(userInfoBaseList, accountCInfo, null);*/


				AccountCApply newaccountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				if (customer != null) serviceWater.setCustomerId(customer.getId());
				if (customer != null) serviceWater.setUserNo(customer.getUserNo());
				if (customer != null) serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				if (AccountCBussinessTypeEnum.accCardStopWithCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setSerType("202");//202记帐卡有卡终止使用
				} else if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setSerType("203");//203记帐卡无卡终止使用
				}

				if (newaccountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				//serviceWater.setAmt(newAccountCInfo.getCost());//应收金额
				//serviceWater.setAulAmt(newAccountCInfo.getRealCost());//实收金额
				//serviceWater.setSaleWate(newAccountCInfo.getIssueFlag());//销售方式
				if (newaccountCApply != null)
					serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				if (AccountCBussinessTypeEnum.accCardStopWithCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setRemark("自营客服系统：记帐卡有卡终止使用");
				} else if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setRemark("自营客服系统：记帐卡无卡终止使用");
				}
				serviceWater.setOperTime(new Date());

				serviceWaterDao.save(serviceWater);

				//记账卡终止使用回执
				AccCardStopReceipt accCardStopReceipt = new AccCardStopReceipt();
				accCardStopReceipt.setTitle("记账卡终止使用回执");
				accCardStopReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				accCardStopReceipt.setAccCardNo(accountCInfo.getCardNo());
				accCardStopReceipt.setBussState(AccountCBussinessTypeEnum.getNameByValue(accountCBussiness.getState()));
				Receipt receipt = new Receipt();
				receipt.setTypeCode(accountCBussiness.getState());
				receipt.setTypeChName(AccountCBussinessTypeEnum.getNameByValue(accountCBussiness.getState()));
				receipt.setCardNo(accCardStopReceipt.getAccCardNo());
				this.saveReceipt(receipt, accountCBussiness, accCardStopReceipt, customer);
							
				/*if (accountCInfo.getCardNo().length()==16 && map!=null) {
					//锁定成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null&&interfaceRecord.getCardno()!=null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
						return "true";
					}
				}else {
				}*/

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171025
				Integer cardStatus = null;
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
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171025

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
	public String saveStopJointCard(ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness,
	                                Cancel cancel, Customer customer, AccountCInfo accountCInfo, String systemType) {
		try {
			//回收原业务
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("6");//stop card

			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				// save account bussniess
				BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
				accountCBussiness.setBusinessId(accountCInfo.getId());
				accountCBussniessDao.save(accountCBussiness);

				// 增加注销登记
				BigDecimal PrePaidC_cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
				cancel.setId(Long.valueOf(PrePaidC_cancel_NO.toString()));
				cancelDao.save(cancel);

				//解绑车辆信息
				VehicleInfo vehicleInfo = null;
				CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
				if (carObuCardInfo != null) {
					carObuCardInfo.setAccountCID(null);
					carObuCardInfoDao.update(carObuCardInfo);
					vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				}

				//解除持卡人信息
				CardHolder cardHolder = cardHolderDao.findCardHolderByCardNo(accountCInfo.getCardNo());
				if (cardHolder != null) {
//					cardHolder.setCardNo(null);
//					cardHolderDao.update(cardHolder);
					cardHolderDao.delete(cardHolder.getId());
				} // if

				//清算接口
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
					//原清算数据，没用了
					//accountCService.saveACinfo(23,accountCInfo, systemType);

					dbasCard.setSerType(DBACardFlowSerTypeEnum.nocardCannel.getValue());
				} else {
					//原清算数据，没用了
					//accountCService.saveACinfo(22,accountCInfo, systemType);

					dbasCard.setSerType(DBACardFlowSerTypeEnum.cardCannel.getValue());
				}
				dbasCardFlowDao.save(dbasCard);


				String userType = "";
				if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
					userType = "0";//个人
				} else {
					userType = "1";//单位
				}
				if ("0".equals(serviceFlowRecord.getIsNeedBlacklist())) {//不下发黑名单
					//原清算数据，没用了
					//accountCService.saveACinfo(22,accountCInfo, systemType);


					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
							CardStateSendSerTypeEnum.cancelWithCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					String obuCode = "";
					String obuSeq = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡有卡注销");
					}

				} else {
					//记帐卡停止使用注销卡片（写给铭鸿的清算数据）
					//记帐卡无卡停止使用注销卡片下发黑名单
					blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
							, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
							accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
							new Date());


					//写给铭鸿的清算数据：卡片状态信息
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.cancel.getIndex()),
							CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);

					String obuCode = "";
					String obuSeq = "";
					Date obuIssueTime = null;
					Date obuExpireTime = null;
					//写给铭鸿的清算数据：用户状态信息
					//如果vehicleInfo为null，卡片就是已经挂起的了，不需要写入一次用户状态信息
					if (vehicleInfo != null) {
						cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
								"23", Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
								obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡无卡注销");
					}
				}

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);

				if (customer != null) serviceWater.setCustomerId(customer.getId());
				if (customer != null) serviceWater.setUserNo(customer.getUserNo());
				if (customer != null) serviceWater.setUserName(customer.getOrgan());
				serviceWater.setCardNo(accountCInfo.getCardNo());
				if (AccountCBussinessTypeEnum.accCardStopWithCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setSerType("202");//202记帐卡有卡终止使用
				} else if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {
					serviceWater.setSerType("203");//203记帐卡无卡终止使用
				}
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				if (AccountCBussinessTypeEnum.accCardStopWithCard.equals(accountCBussiness.getState())) {
					serviceWater.setRemark("联营卡客服系统：记帐卡有卡终止使用");
				} else if (AccountCBussinessTypeEnum.accCardStopNotCard.equals(accountCBussiness.getState())) {
					serviceWater.setRemark("联营卡客服系统：记帐卡无卡终止使用");
				}
				serviceWater.setOperTime(new Date());
				serviceWaterDao.save(serviceWater);

				return "true";
			}
			return "终止卡失败";

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "终止卡失败");
			e.printStackTrace();
			throw new ApplicationException("终止卡失败");
		}
	}

	/**
	 * 香港联营卡卡片注销
	 */
	@Override
	public String saveStopJointCard(Customer customer, AccountCInfo accountCInfo, AccountCBussiness accountCBussiness,
	                                Cancel cancel, boolean isNeedBlacklist) {
		try {
			// 记账卡信息历史表
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("3"); // 注销
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

			// 记账卡业务记录表
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			accountCBussniessDao.save(accountCBussiness);

			// 注销登记表
			cancel.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_cancel_NO"));
			cancelDao.save(cancel);

			//解绑车辆信息
			VehicleInfo vehicleInfo = null;
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
//			if(carObuCardInfo!=null) {
//				carObuCardInfo.setAccountCID(null);
//				carObuCardInfoDao.update(carObuCardInfo);
//				vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
//				vehicleInfo.setIsWriteCard("0");
//				vehicleInfoDao.update(vehicleInfo);
//			} // if
			carObuCardInfo.setAccountCID(null);
			carObuCardInfoDao.update(carObuCardInfo);
			vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			vehicleInfo.setIsWriteCard("0");
			vehicleInfoDao.update(vehicleInfo);

			// 车辆业务信息
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVEHICLEINFO_NO"));
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setCardNo(accountCInfo.getCardNo());
			vehicleBussiness.setCardType("23");
			if (isNeedBlacklist) {
				vehicleBussiness.setType(VehicleBussinessEnum.accountCStopWithoutCard.getValue());
			} else {
				vehicleBussiness.setType(VehicleBussinessEnum.accountCStopWithCard.getValue());
			} // if
			vehicleBussiness.setOperID(accountCBussiness.getOperId());
			vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
			vehicleBussiness.setOperName(accountCBussiness.getOperName());
			vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
			vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());
			vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("联营卡客服系统：记账卡挂失");
			vehicleBussinessDao.save(vehicleBussiness);

			//删除持卡人信息
			CardHolder cardHolder = cardHolderDao.findCardHolderByCardNo(accountCInfo.getCardNo());
			CardHolderHis cardHolderHis = new CardHolderHis();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSlianCardInfoHis_NO");
			cardHolderHis.setId(seq);
			cardHolder.setHisSeqId(seq);
			cardHolderHis.setRemark("联营卡注销，持卡人信息删除");
			cardHolderHis.setGenReason("2"); // 持卡人信息删除
			cardHolderHisDao.save(cardHolder, cardHolderHis);
			if (cardHolder != null) {
				cardHolderDao.delete(cardHolder.getId());
			} // if

			// 更新记账卡信息表
			accountCInfo.setState("2");    // 注销
			if (isNeedBlacklist) {
				accountCInfo.setBlackFlag("1");
			} // if
			accountCInfoDao.update(accountCInfo);

			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(sequenceUtil.getSequenceLong("seq_csmsservicewater_no"));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			if (!isNeedBlacklist) {
				serviceWater.setSerType(ServiceWaterSerType.accCancelWithCard.getValue());
			} else {
				serviceWater.setSerType(ServiceWaterSerType.accCancelWithoutCard.getValue());
			} // if
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			if (accountCBussiness.getState().equals(AccountCBussinessTypeEnum.accCardStopWithCard.getValue())) {
				serviceWater.setRemark("联营卡客服系统：记帐卡有卡终止使用");
			} else if (accountCBussiness.getState().equals(AccountCBussinessTypeEnum.accCardStopNotCard.getValue())) {
				serviceWater.setRemark("联营卡客服系统：记帐卡无卡终止使用");
			} // if
			serviceWaterDao.save(serviceWater);

			// 无卡注销下发黑名单
			if (isNeedBlacklist) {
				BlackListWarter blackListWater = new BlackListWarter();
				blackListWater.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBLACKLISTWATER_NO"));
				blackListWater.setCardType("23");
				blackListWater.setCardNo(accountCInfo.getCardNo());
				blackListWater.setGenTime(new Date());
				blackListWater.setGenType("2");
				blackListWater.setStatus(10);
				blackListWater.setFlag("0");
				blackListWater.setOperId(accountCBussiness.getOperId());
				blackListWater.setOperNo(accountCBussiness.getOperNo());
				blackListWater.setOperName(accountCBussiness.getOperName());
				blackListWater.setPlaceId(accountCBussiness.getPlaceId());
				blackListWater.setPlaceNo(accountCBussiness.getPlaceNo());
				blackListWater.setPlaceName(accountCBussiness.getPlaceName());
				blackListWater.setOperTime(new Date());
				blackListDao.saveBlackListWarter(blackListWater);
			} // if

			// 记账卡终止使用回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("3");
			if (isNeedBlacklist) {
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStopNotCard.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStopNotCard.getName());
			} else {
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardStopWithCard.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardStopWithCard.getName());
			} // if
			receipt.setBusinessId(accountCBussiness.getId());
			receipt.setOperId(accountCBussiness.getOperId());
			receipt.setOperNo(accountCBussiness.getOperNo());
			receipt.setOperName(accountCBussiness.getOperName());
			receipt.setPlaceId(accountCBussiness.getPlaceId());
			receipt.setPlaceNo(accountCBussiness.getPlaceNo());
			receipt.setPlaceName(accountCBussiness.getPlaceName());
			receipt.setCreateTime(new Date());
			receipt.setOrgan(customer.getOrgan());
			receipt.setCardNo(accountCInfo.getCardNo());
			JointCardCancelReceipt jointCardCancelReceipt = new JointCardCancelReceipt();
			jointCardCancelReceipt.setTitle("记账卡终止使用回执");
			jointCardCancelReceipt.setCardNo(accountCInfo.getCardNo());
			if (isNeedBlacklist) {
				jointCardCancelReceipt.setStopType("无卡终止使用");
			} else {
				jointCardCancelReceipt.setStopType("有卡终止使用");
			} // if
			jointCardCancelReceipt.setName(cardHolder.getName());
			jointCardCancelReceipt.setLinkTel(cardHolder.getPhoneNum());
			jointCardCancelReceipt.setMobileNum(cardHolder.getMobileNum());
			jointCardCancelReceipt.setLinkMan(cardHolder.getLinkMan());
			jointCardCancelReceipt.setLinkAddr(cardHolder.getLinkAddr());
			receipt.setContent(JSONObject.fromObject(jointCardCancelReceipt).toString());
			receiptDao.saveReceipt(receipt);

			// 卡片状态信息清算数据
			String userType = "";
			if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
				userType = "0";//个人
			} else {
				userType = "1";//单位
			}
			String serType;
			if (isNeedBlacklist) {
				serType = CardStateSendSerTypeEnum.cancelWithOutCard.getValue();
			} else {
				serType = CardStateSendSerTypeEnum.cancelWithCard.getValue();
			} // if
			cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(),
					Integer.parseInt(CardStateSendStateFlag.cancel.getValue()), serType, accountCInfo.getCardNo(), "23",
					userType);

			// 用户信息清算数据
			String obuCode = "";
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(),
					Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(), "23",
					Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(),
					vehicleInfo.getNSCVehicleType(), obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡注销");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171025
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
				noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new Date(),
						CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
								.EN_BLACK.getCode());
			}
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171025

			return "true";
		} catch (Exception e) {
			return "false";
		} // try
	}

	@Override
	public Pager findStopCardByCustomer(Pager pager, Customer customer, AccountCInfo accountCInfo) {
		return accountCDao.findStopCardByCustomer(pager, customer, accountCInfo);
	}
	

	/*//清算接口
	private void saveACinfo(Integer state,AccountCInfo accountCInfo){
		ACinfo newACinfo=new ACinfo();
		newACinfo.setCardNo(accountCInfo.getCardNo());
		newACinfo.setState(state);
		//aCinfo.setOBANo(oBANo);尚未确定
		aCinfoDao.saveACinfo(accountCApplyDao.findByCardNo(newACinfo.getCardNo()), newACinfo);
	}
	//清算接口
	private void saveTollCardBlack(AccountCInfo accountCInfo,TollCardBlackDet tollCardBlackDet,TollCardBlackDetSend tollCardBlackDetSend){
		String license=" ";//车牌号
		VehicleInfo vehicleInfo=vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
		if(vehicleInfo!=null){
			license=vehicleInfo.getVehiclePlate();
		}
		tollCardBlackDet.setLicense(license);
		tollCardBlackDetSend.setLicense(license);
		tollCardBlackDetDao.save(tollCardBlackDet);
		tollCardBlackDetSendDao.save(tollCardBlackDetSend);
	}*/

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

