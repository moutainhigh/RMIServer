package com.hgsoft.jointCard.service;

import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.entity.AccountCSecondIssued;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.IdTypeACMSEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.InvoiceDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.dao.JointCardIssueDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.entity.JointCardIssueQuery;
import com.hgsoft.jointCard.serviceInterface.IJointCardIssueService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.JointCardIssueReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class JointCardIssueService implements IJointCardIssueService {

	@Resource
	private JointCardIssueDao jointCardIssueDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private CardHolderDao cardHolderDao;
	@Resource
	private InvoiceDao invoiceDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

	private IdTypeACMSEnum[] idTypeACMSEnums = IdTypeACMSEnum.values();
	private VehicleColorEnum[] vehicleColorEnums = VehicleColorEnum.values();
	private NSCVehicleTypeEnum[] nscVehicleTypeEnums = NSCVehicleTypeEnum.values();
	private VehicleTypeEnum[] vehicleTypeEnums = VehicleTypeEnum.values();

	@Override
	public Pager findJointCardIssueInfo(Pager pager, Customer customer, JointCardIssueQuery jointCardIssueQuery) {
		if (jointCardIssueQuery.getEndTime() != null) {
			Date newEndTime = new Date(jointCardIssueQuery.getEndTime().getTime() + (24 * 60 * 60 - 1) * 1000);
			jointCardIssueQuery.setEndTime(newEndTime);
		} // if
		return jointCardIssueDao.findJointCardIssueInfo(pager, customer, jointCardIssueQuery);
	}

	@Override
	public AccountCInfo saveAccountCInfo(Customer customer, AccountCInfo accountCInfo, VehicleInfo vehicleInfo, CardHolder cardHolder, CarObuCardInfo carObuCardInfo) {
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> map = inventoryServiceForAgent.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord,
				"issue", accountCInfo.getIssuePlaceId(), accountCInfo.getIssueOperId(), accountCInfo.getOperName(), "1",
				"customPoint", null, accountCInfo.getRealCost(), "31"); // 31代表记账卡发行
		boolean result = (Boolean) map.get("result");
		if (!result) {
			return null;
		} // if
		Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
		if (m != null) {
			accountCInfo.setStartDate((Date) m.get("startDate"));
			accountCInfo.setEndDate((Date) m.get("endDate"));
		} // if
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
		accountCInfo.setId(seq);
		accountCInfo.setAccountId((long) -1);
		accountCInfoDao.save(accountCInfo);

		if (carObuCardInfo == null) {
			carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setAccountCID(accountCInfo.getId());
			carObuCardInfo.setVehicleID(vehicleInfo.getId());
			carObuCardInfoDao.save(carObuCardInfo);
		} else {
			carObuCardInfo.setAccountCID(accountCInfo.getId());
			carObuCardInfo.setVehicleID(vehicleInfo.getId());
			carObuCardInfoDao.update(carObuCardInfo);
		} // if

		if (cardHolder == null) {
			cardHolder = new CardHolder();
		} // if
		seq = sequenceUtil.getSequenceLong("SEQ_CSMSHKCARDHOLDER_NO");
		cardHolder.setId(seq);
		cardHolder.setCardNo(accountCInfo.getCardNo());
		cardHolder.setAccountCId(accountCInfo.getId());
		cardHolder.setOperId(accountCInfo.getIssueOperId());
		cardHolder.setOperCode(accountCInfo.getOperNo());
		cardHolder.setOperName(accountCInfo.getOperName());
		cardHolder.setPlaceId(accountCInfo.getIssuePlaceId());
		cardHolder.setPlaceCode(accountCInfo.getPlaceNo());
		cardHolder.setPlaceName(accountCInfo.getPlaceName());
		cardHolderDao.save(cardHolder);

		AccountCBussiness accountCBussiness = new AccountCBussiness();
		accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCBUSSINESS_NO"));
		accountCBussiness.setUserId(customer.getId());
		accountCBussiness.setCardNo(accountCInfo.getCardNo());
		accountCBussiness.setState(AccountCBussinessTypeEnum.accCardIssue.getValue());
		accountCBussiness.setSuit(accountCInfo.getSuit());
		accountCBussiness.setTradeTime(new Date());
		accountCBussiness.setOperId(accountCInfo.getIssueOperId());
		accountCBussiness.setOperNo(accountCInfo.getOperNo());
		accountCBussiness.setOperName(accountCInfo.getOperName());
		accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
		accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
		accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
		accountCBussinessDao.save(accountCBussiness);

		VehicleBussiness vehicleBussiness = new VehicleBussiness();
		BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
		vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
		vehicleBussiness.setCustomerID(customer.getId());
		vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
		vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
		vehicleBussiness.setCardNo(accountCInfo.getCardNo());
		vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
		vehicleBussiness.setType(VehicleBussinessEnum.accountCIssue.getValue());
		vehicleBussiness.setOperID(accountCInfo.getIssueOperId());
		vehicleBussiness.setPlaceID(accountCInfo.getIssuePlaceId());
		vehicleBussiness.setOperName(accountCInfo.getOperName());
		vehicleBussiness.setOperNo(accountCInfo.getOperNo());
		vehicleBussiness.setPlaceName(accountCInfo.getPlaceName());
		vehicleBussiness.setPlaceNo(accountCInfo.getPlaceNo());
		vehicleBussiness.setCreateTime(new Date());
		vehicleBussiness.setMemo("记帐卡发行");
		vehicleBussinessDao.save(vehicleBussiness);

		vehicleInfo.setIsWriteCard("1");
		vehicleInfoDao.update(vehicleInfo);

		ServiceWater serviceWater = new ServiceWater();
		serviceWater.setId(sequenceUtil.getSequenceLong("seq_csmsservicewater_no"));
		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());
		serviceWater.setCardNo(accountCInfo.getCardNo());
		serviceWater.setSerType(ServiceWaterSerType.accIssue.getValue());
		serviceWater.setAccountCBussinessId(accountCBussiness.getId());
		serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
		serviceWater.setOperId(accountCInfo.getIssueOperId());
		serviceWater.setOperNo(accountCInfo.getOperNo());
		serviceWater.setOperName(accountCInfo.getOperName());
		serviceWater.setPlaceId(accountCInfo.getIssuePlaceId());
		serviceWater.setPlaceNo(accountCInfo.getPlaceNo());
		serviceWater.setPlaceName(accountCInfo.getPlaceName());
		serviceWater.setOperTime(new Date());
		serviceWater.setRemark("联营卡客服系统：记账卡发行");
		serviceWaterDao.save(serviceWater);

		// 记账卡发行回执
		Receipt receipt = new Receipt();
		receipt.setParentTypeCode("3");
		receipt.setTypeCode(AccountCBussinessTypeEnum.accCardIssue.getValue());
		receipt.setTypeChName(AccountCBussinessTypeEnum.accCardIssue.getName());
		receipt.setBusinessId(accountCBussiness.getId());
		receipt.setOperId(accountCInfo.getIssueOperId());
		receipt.setOperNo(accountCInfo.getOperNo());
		receipt.setOperName(accountCInfo.getOperName());
		receipt.setPlaceId(accountCInfo.getIssuePlaceId());
		receipt.setPlaceNo(accountCInfo.getPlaceNo());
		receipt.setPlaceName(accountCInfo.getPlaceName());
		receipt.setCreateTime(new Date());
		receipt.setOrgan(customer.getOrgan());
		receipt.setCardNo(accountCInfo.getCardNo());
		JointCardIssueReceipt jointCardIssueReceipt = new JointCardIssueReceipt();
		jointCardIssueReceipt.setTitle("记账卡发行回执");
		jointCardIssueReceipt.setHandleWay("凭资料办理");
		jointCardIssueReceipt.setName(cardHolder.getName());
		for (int i = 0; i < idTypeACMSEnums.length; i++) {
			if (idTypeACMSEnums[i].getValue().equals(cardHolder.getIdType())) {
				jointCardIssueReceipt.setIdType(idTypeACMSEnums[i].getName());
			} // if
		} // for
		jointCardIssueReceipt.setIdCode(cardHolder.getIdCode());
		jointCardIssueReceipt.setLinkTel(cardHolder.getPhoneNum());
		jointCardIssueReceipt.setMobileNum(cardHolder.getMobileNum());
		jointCardIssueReceipt.setLinkMan(cardHolder.getLinkMan());
		jointCardIssueReceipt.setLinkAddr(cardHolder.getLinkAddr());
		jointCardIssueReceipt.setInvoiceTitle(cardHolder.getInvoiceTitle());
		jointCardIssueReceipt.setCardNo(accountCInfo.getCardNo());
		jointCardIssueReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
		for (int i = 0; i < vehicleColorEnums.length; i++) {
			if (vehicleColorEnums[i].getValue().equals(vehicleInfo.getVehicleColor())) {
				jointCardIssueReceipt.setVehicleColor(vehicleColorEnums[i].getName());
			} // if
		} // for
		if ("1".equals(accountCInfo.getSuit())) {
			jointCardIssueReceipt.setSuit("套装");
		} else if ("0".equals(accountCInfo.getSuit())) {
			jointCardIssueReceipt.setSuit("单卡");
		} // if
		jointCardIssueReceipt.setWeightsOrSeats(vehicleInfo.getVehicleWeightLimits());
		for (int i = 0; i < nscVehicleTypeEnums.length; i++) {
			if (nscVehicleTypeEnums[i].getValue().equals(vehicleInfo.getNSCVehicleType())) {
				jointCardIssueReceipt.setNscVehicleType(nscVehicleTypeEnums[i].getName());
			} // if
		} // for
		for (int i = 0; i < vehicleTypeEnums.length; i++) {
			if (vehicleTypeEnums[i].getValue().equals(vehicleInfo.getVehicleType())) {
				jointCardIssueReceipt.setVehicleType(vehicleTypeEnums[i].getName());
			} // if
		} // for
		jointCardIssueReceipt.setOwner(vehicleInfo.getOwner());
		receipt.setContent(JSONObject.fromObject(jointCardIssueReceipt).toString());
		receiptDao.saveReceipt(receipt);

		// 记账卡二次发行清算数据
		AccountCSecondIssued accountCSecondIssued = new AccountCSecondIssued();
		accountCSecondIssued.setCardCode(accountCInfo.getCardNo());
		accountCSecondIssued.setCardType("23");
		accountCSecondIssued.setSdate(accountCInfo.getIssueTime());
		accountCSecondIssued.setStatus(0);
		accountCSecondIssued.setBankAccount("0085226278880"); // 快易通开户银行账号
		accountCSecondIssued.setBankNo("99999"); // 快易通银行编码
		accountCSecondIssued.setUpdatetime(new Date());
		cardSecondIssuedDao.saveAccountCCardIssued(accountCSecondIssued);

		// 卡片状态信息清算数据
		String userType = "";
		if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
			userType = "0";//个人
		} else {
			userType = "1";//单位
		}
		cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(),
				Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), CardStateSendSerTypeEnum.acIssue.getValue(),
				accountCInfo.getCardNo(), "23", userType);

		// 用户信息清算数据
		String obuCode = "";
		String obuSeq = "";
		Date obuIssueTime = null;
		Date obuExpireTime = null;
		cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(),
				Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCInfo.getCardNo(), "23",
				Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(),
				vehicleInfo.getNSCVehicleType(), obuCode, obuSeq, obuIssueTime, obuExpireTime, "记帐卡发行");

		//发行成功后，更新营运接口调用登记记录的客服状态
		interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
		if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
			interfaceRecord.setCsmsState("1");
			interfaceRecordDao.update(interfaceRecord);
		} // if

		//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
		realTransferService.accountCInfoTransfer(customer, accountCInfo,
				vehicleInfo, CardStatusEmeu.NORMAL.getCode(),
				OperationTypeEmeu.ADD.getCode());
		//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

		return accountCInfo;
	}

}