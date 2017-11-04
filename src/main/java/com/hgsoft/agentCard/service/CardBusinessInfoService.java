package com.hgsoft.agentCard.service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCApplyService;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.agentCard.dao.CardBlacklistDao;
import com.hgsoft.agentCard.dao.CardBusinessInfoDao;
import com.hgsoft.agentCard.entity.CardBlacklistInfo;
import com.hgsoft.agentCard.entity.CardBusinessInfo;
import com.hgsoft.agentCard.serviceInterface.ICardBusinessInfoService;
import com.hgsoft.agentCard.serviceInterface.ICardNoSectionService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.bank.dao.BankInterfaceDao;
import com.hgsoft.bank.serviceInterface.IBankInterfaceAuthService;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.dao.CloseAccountRestartSendDao;
import com.hgsoft.clearInterface.dao.ManualBlackListSendDao;
import com.hgsoft.clearInterface.entity.AccCardUserInfoSend;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.clearInterface.entity.CloseAccountRestartSend;
import com.hgsoft.clearInterface.entity.ManualBlackListSend;
import com.hgsoft.clearInterface.service.CardSecondIssuedService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.clearInterface.serviceInterface.IStopPayRelieveApplyService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardBusinessTypeEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.DBACardFlowCardTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowEndSerTypeEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowReadFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowSerTypeEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.dao.TbBankdetailDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.entity.TbBankdetail;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.serviceInterface.ICancelService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.RegularUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardBusinessInfoService implements ICardBusinessInfoService {
	@Resource
	private CardBusinessInfoDao cardBusinessInfoDao;
	@Resource
	private BankInterfaceDao bankInterfaceDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CardBlacklistDao cardBlacklistDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;

	@Resource
	private AccountCBussinessDao accountCBussinessDao;

	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;

	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	@Resource
	private VehicleInfoDao vehicleInfoDao;

	@Resource
	private VehicleBussinessDao vehicleBussinessDao;

	@Resource
	private TbBankdetailDao tbBankdetailDao;

	@Resource
	private BillGetDao billGetDao;

	@Resource
	private ManualBlackListSendDao manualBlackListSendDao;

	@Resource
	private CloseAccountRestartSendDao closeAccountRestartSendDao;

	@Resource
	private IAccountCApplyService accountCApplyService;

	@Resource
	private IAccountCService accountCService;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;

	@Resource
	private IBlackListService blackListService;

	@Resource
	private ICancelService cancelService;
	@Resource
	private ICardNoSectionService cardNoSectionService;
	@Resource
	private ICardObuService cardObuService;

	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;

	@Resource
	private CardSecondIssuedService cardSecondIssuedService;

	@Resource
	private InterfaceRecordDao interfaceRecordDao;

	@Resource
	private IBankInterfaceAuthService bankInterfaceAuthService;

	@Resource
	private IStopPayRelieveApplyService iStopPayRelieveApplyService;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171018
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	@Resource
	private IVehicleInfoService iVehicleInfoService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171018


	@Resource
	SequenceUtil sequenceUtil;


	private static Logger logger = Logger.getLogger(CardBusinessInfoService.class.getName());


	public Map<String, Object> dataCheck(String bankCode, CardBusinessInfo cardBusinessInfo) {
		try {
			Map<String, Object> map = new HashMap<>();
			String errorMessage = null;
			boolean flag = false;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			map.put("flag", flag);
			String businessTime = format.format(cardBusinessInfo.getBusinessTime());
			String businessType = cardBusinessInfo.getBusinessType();
			//必填校验：	导入每一行数据内容必须填写业务时间、业务类型、粤通卡号、信用卡卡号这四个数据项
			if (!StringUtil.isNotBlank(businessTime) || !StringUtil.isNotBlank(businessType)
					|| !StringUtil.isNotBlank(cardBusinessInfo.getUTCardNo()) || !StringUtil.isNotBlank(cardBusinessInfo.getCreditCardNo())) {
				errorMessage = "必填数据为空，请往下检查！";
				map.put("errorMessage", errorMessage);
				return map;
			}

			//对传入的数据普遍验证
			//业务时间验证
//			if(StringUtil.isNotBlank(businessTime)){
//				if(!RegularUtil.isDate(businessTime)){
//					errorMessage = "业务时间格式有误，请检查";
//					map.put("errorMessage",errorMessage);
//					return map;
//				}
//			}
			if (StringUtil.isNotBlank(businessType)) {
				if (businessType.length() != 2 || (!businessType.equals("01") && !businessType.equals("02") && !businessType.equals("03") &&
						!businessType.equals("04") && !businessType.equals("05") && !businessType.equals("06") && !businessType.equals("07") &&
						!businessType.equals("08") && !businessType.equals("09") && !businessType.equals("10") && !businessType.equals("11") &&
						!businessType.equals("13") && !businessType.equals("14") && !businessType.equals("16"))) {
					errorMessage = "业务类型有误，请检查";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			//判断粤通卡卡号
			if (StringUtil.isNotBlank(cardBusinessInfo.getUTCardNo())) {
				if (cardBusinessInfo.getUTCardNo().length() != 16 && cardBusinessInfo.getUTCardNo().length() != 12) {
					errorMessage = "粤通卡卡号的格式有误，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				} else if (cardBusinessInfo.getUTCardNo().length() == 16 && !cardBusinessInfo.getUTCardNo().trim().substring(4, 6).equals("23")) {
					errorMessage = "粤通卡卡号的格式有误，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				} else if (cardBusinessInfo.getUTCardNo().length() == 12 && (!businessType.equals("04") && !businessType.equals("05") && !businessType.equals("06") && !businessType.equals("07") &&
						!businessType.equals("08") && !businessType.equals("09") && !businessType.equals("10") && !businessType.equals("11") && !businessType.equals("16"))) {
					errorMessage = "选择的业务类型不属于该地标卡可办理的业务，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				//2017/05/09     如果网点没有银行编码，就不用判断卡号段
				if (StringUtil.isNotBlank(bankCode)) {
					boolean numFlag = cardNoSectionService.checkCardNoInNoSection(bankCode, "23", cardBusinessInfo.getUTCardNo().trim());
					if (!numFlag) {
						errorMessage = "粤通卡卡号不属于当前营业点的卡号段，无法办理业务！";
						map.put("errorMessage", errorMessage);
						return map;
					}
				} else {
					errorMessage = "当前营业点无网点编码信息，无法办理业务！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			//判断粤通卡旧卡号
			if (StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo())) {
				if (cardBusinessInfo.getOldUTCardNo().length() != 16 && cardBusinessInfo.getOldUTCardNo().length() != 12) {
					errorMessage = "粤通卡旧卡号的格式有误，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				} else if (cardBusinessInfo.getOldUTCardNo().length() == 16 && !cardBusinessInfo.getOldUTCardNo().trim().substring(4, 6).equals("23")) {
					errorMessage = "粤通卡旧卡号的格式有误，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				} else if (cardBusinessInfo.getOldUTCardNo().length() == 12 && (!businessType.equals("04") && !businessType.equals("05") && !businessType.equals("06") && !businessType.equals("07") &&
						!businessType.equals("08") && !businessType.equals("09") && !businessType.equals("10") && !businessType.equals("11") && !businessType.equals("16"))) {
					errorMessage = "选择的业务类型不属于该旧地标卡可办理的业务，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				//2017/05/09     如果网点没有银行编码，就不用判断卡号段
				if (StringUtil.isNotBlank(bankCode)) {
					boolean numFlag = cardNoSectionService.checkCardNoInNoSection(bankCode, "23", cardBusinessInfo.getOldUTCardNo().trim());
					if (!numFlag) {
						errorMessage = "粤通卡旧卡号不属于当前营业点的卡号段，无法办理业务！";
						map.put("errorMessage", errorMessage);
						return map;
					}
				} else {
					errorMessage = "当前营业点无网点编码信息，无法办理业务！";
					map.put("errorMessage", errorMessage);
					return map;
				}

			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCreditCardNo())) {
				if (cardBusinessInfo.getCreditCardNo().length() > 32) {
					errorMessage = "信用卡号长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
				if (cardBusinessInfo.getOldCreditCardNo().length() > 32) {
					errorMessage = "旧信用卡号长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getOrgan())) {
				if (cardBusinessInfo.getOrgan().length() > 80) {
					errorMessage = "客户名称长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getUserType())) {
				if (cardBusinessInfo.getUserType().length() > 2) {
					errorMessage = "客户类型长度不正确！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			String idType = cardBusinessInfo.getIdType();
			if (StringUtil.isNotBlank(idType)) {
				if (idType.length() > 1) {
					errorMessage = "证件类型类型有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!idType.equals("0") && !idType.equals("1") && !idType.equals("2") && !idType.equals("3") && !idType.equals("4")
						&& !idType.equals("6") && !idType.equals("7")) {
					errorMessage = "证件类型类型有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				//新客服系统开户不允许证件类型为5-其他，历史数据保留。
				if (("01".equals(businessType) || "02".equals(businessType)) && "5".equals(idType)) {
					errorMessage = "证件类型类型有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getIdCode())) {
				if (cardBusinessInfo.getIdCode().length() > 40) {
					errorMessage = "证件号码长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCusTel())) {
				if (cardBusinessInfo.getCusTel().length() > 30) {
					errorMessage = "客户电话长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCusMobile())) {
				if (!RegularUtil.isMobile(cardBusinessInfo.getCusMobile())) {
					errorMessage = "客户手机号格式不正确！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCusAddr())) {
				if (cardBusinessInfo.getCusAddr().length() > 80) {
					errorMessage = "客户地址长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCusZipCode())) {
				if (cardBusinessInfo.getCusZipCode().length() > 6 || !RegularUtil.isZipCode(cardBusinessInfo.getCusZipCode())) {
					errorMessage = "客户邮编格式有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getCusEmail())) {
				if (cardBusinessInfo.getCusEmail().length() > 30 || !RegularUtil.isEmail(cardBusinessInfo.getCusEmail())) {
					errorMessage = "电子邮箱格式有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getLinkMan())) {
				if (cardBusinessInfo.getLinkMan().length() > 8) {
					errorMessage = "联系人姓名长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getLinkTel())) {
				if (cardBusinessInfo.getLinkTel().length() > 30) {
					errorMessage = "联系人电话长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getLinkMobile())) {
				if (cardBusinessInfo.getLinkMobile().length() > 11 || !RegularUtil.isMobile(cardBusinessInfo.getLinkMobile())) {
					errorMessage = "联系人手机格式有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getLinkAddr())) {
				if (cardBusinessInfo.getLinkAddr().length() > 80) {
					errorMessage = "联系人地址长度太长！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			if (StringUtil.isNotBlank(cardBusinessInfo.getLinkZipCode())) {
				if (cardBusinessInfo.getLinkZipCode().length() > 6 || !RegularUtil.isZipCode(cardBusinessInfo.getLinkZipCode())) {
					errorMessage = "联系人邮编格式有误！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}

			//每个业务的列不为空验证
			if (businessType.equals("01")) {
				// 对于业务类型“01-开户申请”，必须填写数据项：客户名称、客户类型、证件类型、证件号码、客户电话、客户手机号、客户地址、
				// 客户邮编。联系人姓名、联系人电话、联系人手机号、联系人地址、联系人邮编为空的情况下，默认等同于客户名称、客户电话、客户手机号、客户地址、客户邮编
				// 如从客户电话开始的必填数据项为空，必须填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOrgan())) {
					errorMessage = "客户名称不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!StringUtil.isNotBlank(cardBusinessInfo.getUserType())) {
					errorMessage = "客户类型不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!StringUtil.isNotBlank(idType)) {
					errorMessage = "证件类型不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!StringUtil.isNotBlank(cardBusinessInfo.getIdCode())) {
					errorMessage = "证件号码不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!StringUtil.isNotBlank(cardBusinessInfo.getCusTel()) && !StringUtil.isNotBlank(cardBusinessInfo.getCusMobile())) {
					errorMessage = "客户电话和客户手机号不能同时为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
//				if(!StringUtil.isNotBlank(cusMobile)){
//					errorMessage = "客户手机号不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
//					map.put("errorMessage",errorMessage);
//					return map;
//				}
				if (!StringUtil.isNotBlank(cardBusinessInfo.getCusAddr())) {
					errorMessage = "客户地址不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (!StringUtil.isNotBlank(cardBusinessInfo.getCusZipCode())) {
					errorMessage = "客户邮编不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
					map.put("errorMessage", errorMessage);
					return map;
				}
//				if(!StringUtil.isNotBlank(cusEmail)){
//					errorMessage = "客户电子邮箱不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
//					map.put("errorMessage",errorMessage);
//					return map;
//				}
//				if(!StringUtil.isNotBlank(cardBusinessInfo.getLinkMan())){
////					errorMessage = "联系人姓名不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
////					map.put("errorMessage",errorMessage);
////					return map;
//					cardBusinessInfo.setLinkMan(cardBusinessInfo.getOrgan());
//				}
//				if(!StringUtil.isNotBlank(cardBusinessInfo.getLinkTel())){
////					errorMessage = "联系人电话不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
////					map.put("errorMessage",errorMessage);
////					return map;
//					cardBusinessInfo.setLinkTel(cardBusinessInfo.getCusTel());
//				}
//				if(!StringUtil.isNotBlank(cardBusinessInfo.getLinkMobile())){
////					errorMessage = "联系人手机号不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
////					map.put("errorMessage",errorMessage);
////					return map;
//					cardBusinessInfo.setLinkMobile(cardBusinessInfo.getLinkMobile());
//				}
//				if(!StringUtil.isNotBlank(cardBusinessInfo.getLinkAddr())){
////					errorMessage = "联系人地址不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
////					map.put("errorMessage",errorMessage);
////					return map;
//					cardBusinessInfo.setLinkAddr(cardBusinessInfo.getCusAddr());
//				}
//				if(!StringUtil.isNotBlank(cardBusinessInfo.getLinkZipCode())){
////					errorMessage = "联系人邮编不能为空，请检查！如需缺省，请填写缺省空值，如文本用”X”，数字用”0”，邮编用”000000”";
////					map.put("errorMessage",errorMessage);
////					return map;
//					cardBusinessInfo.setLinkZipCode(cardBusinessInfo.getCusZipCode());
//				}
			} else if (businessType.equals("02")) {
				//TODO
				//开户确认
			} else if (businessType.equals("03")) {
				// 对于业务类型“03-资料变更”，必须填写数据项：由序号11（客户电话）至序号20（联系人邮编）的共10个数据项。
				// 如从客户电话开始的必填数据项为空，不需要填写缺省值，直接置空即可，这种情况下，代表不对该数据项进行修改。
								/*if (!StringUtil.isNotBlank(cusTel) || !StringUtil.isNotBlank(cusMobile)
										|| !StringUtil.isNotBlank(cusAddr) || !StringUtil.isNotBlank(cusZipCode)
										|| !StringUtil.isNotBlank(cusEmail) || !StringUtil.isNotBlank(linkMan)
										|| !StringUtil.isNotBlank(linkTel) || !StringUtil.isNotBlank(linkMobile)
										|| !StringUtil.isNotBlank(linkAddr) || !StringUtil.isNotBlank(linkZipCode)) {
									message = "第" + line + "行的客户电话、客户手机号、客户地址、客户邮编、客户电子邮箱、联系人姓名、联系人电话、联系人手机号、联系人地址、联系人邮编为空，请检查！";
									outErrorMessage(message);
									return null;
								}*/
			} else if (businessType.equals("04")) {
				//挂失
			} else if (businessType.equals("05")) {
				//换卡申请(正常换卡，信用卡卡号不变，已重新制卡)
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo())) {
					errorMessage = "粤通卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					if (!cardBusinessInfo.getCreditCardNo().equals(cardBusinessInfo.getOldCreditCardNo())) {
						errorMessage = "信用卡旧卡号与信用卡卡号不相等！";
						map.put("errorMessage", errorMessage);
						return map;
					}
				}
			} else if (businessType.equals("06")) {
				//换卡确认
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo())) {
					errorMessage = "粤通卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			} else if (businessType.equals("07")) {
				// 对于业务类型“07-补领申请”和“08-补领确认”，必须填写数据项：粤通卡旧卡号、信用卡旧卡号。
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo()) || !StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					errorMessage = "粤通卡旧卡号、信用卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			} else if (businessType.equals("08")) {
				// 对于业务类型“07-补领申请”和“08-补领确认”，必须填写数据项：粤通卡旧卡号、信用卡旧卡号。
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo()) || !StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					errorMessage = "粤通卡旧卡号、信用卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			} else if (businessType.equals("16")) {
				// 非过户补领 16：必须填写数据项：粤通卡旧卡号、信用卡旧卡号。
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo())) {
					errorMessage = "粤通卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
				if (StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					if (!cardBusinessInfo.getCreditCardNo().equals(cardBusinessInfo.getOldCreditCardNo())) {
						errorMessage = "信用卡旧卡号与信用卡卡号不相等！";
						map.put("errorMessage", errorMessage);
						return map;
					}
				}
			} else if (businessType.equals("17")) {
				// 过户申请 17：必须填写数据项：粤通卡旧卡号、信用卡旧卡号。
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo()) || !StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					errorMessage = "粤通卡旧卡号、信用卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			} else if (businessType.equals("18")) {
				// 过户申请 17：必须填写数据项：粤通卡旧卡号、信用卡旧卡号。
				if (!StringUtil.isNotBlank(cardBusinessInfo.getOldUTCardNo()) || !StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
					errorMessage = "粤通卡旧卡号、信用卡旧卡号不能为空，请检查！";
					map.put("errorMessage", errorMessage);
					return map;
				}
			}
			flag = true;
			map.put("errorMessage", errorMessage);
			map.put("flag", flag);
			return map;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "校验业务信息失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Long findSequence(String sequenceName) {
		return sequenceUtil.getSequenceLong(sequenceName);
	}

	public void saveCardBusinessInfos(List<CardBusinessInfo> cardBusinessInfos) {

		try {
			for (CardBusinessInfo cbi : cardBusinessInfos) {
				Long cardBusinessInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSCardbusinessinfo_NO");
				cbi.setId(cardBusinessInfoId);
				cbi.setSystemType("4");//设置系统类型(1:自营，2：联营卡网点（香港），3：澳门通，4：代理点客服系统)
				cardBusinessInfoDao.saveCardBusinessInfo(cbi);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存业务信息失败");
			throw new ApplicationException();
		}

	}

	public Pager findCardBusinessInfosByNum(Pager pager, Long dataNum) {
		return cardBusinessInfoDao.findCardBusinessInfosByNum(pager, dataNum);
	}

	public Pager findCardBusinessInfosByFileName(Pager pager, String fileName) {
		return cardBusinessInfoDao.findCardBusinessInfosByFileName(pager, fileName);
	}

	public List<CardBusinessInfo> findByFileName(String fileName) {
		return cardBusinessInfoDao.findByFileName(fileName);
	}

	public List<CardBusinessInfo> findByFileNameAndTransact(String fileName) {
		return cardBusinessInfoDao.findByFileNameAndTransact(fileName);
	}

	@Override
	public List<CardBusinessInfo> findByFileNameAndTransactSuccess(String fileName) {
		return cardBusinessInfoDao.findByFileNameAndTransactSuccess(fileName);
	}

	@Override
	public List<CardBusinessInfo> findIssueBusinessRecordBycardNo(String cardNo) {
		return cardBusinessInfoDao.findIssueBusinessRecordBycardNo(cardNo);
	}

	public CardBusinessInfo findById(Long cardBusinessInfoId) {
		return cardBusinessInfoDao.findById(cardBusinessInfoId);
	}

	public List<CardBusinessInfo> findByErrorAndName(String fileName) {
		return cardBusinessInfoDao.findByErrorAndName(fileName);
	}

	public static void main(String[] args) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

//		IInventoryServiceForAgent inventoryServiceForAgent = (IInventoryServiceForAgent)context.getBean("inventoryServiceForAgent");
//		Map<String, Object> map = inventoryServiceForAgent.omsInterface("1711231703172222", "1", null,"issue",
//				null,null,null,"1","",null,null,"31");
		CardBusinessInfo cardBusinessInfo = new CardBusinessInfo();
		cardBusinessInfo.setUTCardNo("201723001122");
		String businessType = "01";
		String errorMessage = null;
		if (cardBusinessInfo.getUTCardNo().length() != 16 && cardBusinessInfo.getUTCardNo().length() != 12) {
			errorMessage = "粤通卡卡号的格式有误，请检查！";
		} else if (cardBusinessInfo.getUTCardNo().length() == 16 && !cardBusinessInfo.getUTCardNo().trim().substring(4, 6).equals("23")) {
			errorMessage = "粤通卡卡号的格式有误，请检查！";
		} else if (cardBusinessInfo.getUTCardNo().length() == 12 && (!businessType.equals("04") && !businessType.equals("05") && !businessType.equals("06") && !businessType.equals("07") &&
				!businessType.equals("08") && !businessType.equals("09") && !businessType.equals("10") && !businessType.equals("11") && !businessType.equals("16"))) {
			errorMessage = "选择的业务类型不属于地标卡可办理的业务，请检查！";
		} else {
			errorMessage = "没问题 ";
		}
		System.out.println(errorMessage);
	}

	/****
	 * 开户申请-01
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 * @return
	 */
	public boolean openAccountApply(CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// *开户申请
		//查询该银行编号对应的银行信息
		TbBankdetail tbBankdetail = new TbBankdetail();
		tbBankdetail.setObaNo(Long.parseLong(bankCode));
		List<TbBankdetail> bankList = tbBankdetailDao.listTbBankdetail(tbBankdetail);
		if (bankList == null || bankList.isEmpty()) {
			cardBusinessInfo.setRemark("009100006：获取银行名称错误");
			message = "009100006：获取银行名称错误";
			dealFalg = false;
			return dealFalg;
		} else {
			tbBankdetail = bankList.get(0);
		}
		// 根据记帐卡号（即粤通卡卡号）查找记帐卡信息表，如果查询不到，既可以申请开户
		AccountCInfo exitAccInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
		//如果exitAccInfo为空，表示数据库中不存在此粤通卡卡号，即可以开户申请
		if (exitAccInfo == null) {
			//新增记帐卡信息
			AccountCInfo accountCInfo = new AccountCInfo();
			Long accountCInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
			accountCInfo.setId(accountCInfoId);
			//调用营运卡片或电子标签初始化校验接口
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> map = inventoryServiceForAgent.omsInterface(cardBusinessInfo.getUTCardNo(), "1", interfaceRecord, "issue",
					serviceFlowRecord.getPlaceID(), serviceFlowRecord.getOperID(), serviceFlowRecord.getOperName(), "1", "", serviceFlowRecord.getPlaceID(), accountCInfo.getRealCost(), "31");

			boolean result = (Boolean) map.get("result");
			if (!result) {
				cardBusinessInfo.setRemark("009100097：粤通卡卡号在记帐卡初始化表中不存在");
				message = "009100097：粤通卡卡号在记帐卡初始化表中不存在";
				dealFalg = false;
				logger.error("009100097：粤通卡卡号在记帐卡初始化表中不存在");
				return dealFalg;
			} else {

				//设置有效起始时间与结束时间
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
					logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
					throw new ApplicationException("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
				} else {
					logger.error("获取营运维保周期参数失败:");
					throw new ApplicationException("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
					//cal.add(Calendar.YEAR, 10);// 十年有效期
				}
				accountCInfo.setMaintainTime(cal.getTime());
				accountCInfo.setIssueTime(new Date());


				// “01-开户申请”，若导入数据（证件类型、证件号码）一致，则将新增卡挂在该客户名下；若无，则新增客户
				Customer newCustomer = new Customer();
//								newCustomer.setOrgan(cardBusinessInfo.getOrgan());					//客户名称
				newCustomer.setIdType(cardBusinessInfo.getIdType());                //证件类型
				newCustomer.setIdCode(cardBusinessInfo.getIdCode());                // 证件号码
//								newCustomer.setLinkMan(cardBusinessInfo.getLinkMan());				//联系人
//								newCustomer.setTel(cardBusinessInfo.getLinkTel());					//联系人电话
				//根据证件类型、证件号码查看客户信息，如果客户存在二级编码则不允许办理

				Customer oldCustomer = customerDao.find(newCustomer);

				// ④增加银行申请信息（记帐卡申请表）如果已经存在该银行账号，不允许办理
				AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
				if (accountCApply != null) {
					cardBusinessInfo.setRemark("009100098：信用卡卡号已经在记帐卡申请表中存在");
					message = "009100098：信用卡卡号已经在记帐卡申请表中存在";
					dealFalg = false;
					return dealFalg;
				}
				if (oldCustomer != null) {
					if (StringUtil.isNotBlank(oldCustomer.getSecondNo())) {
						cardBusinessInfo.setRemark("009100100：该客户信息存在二级编码");
						message = "009100100：该客户信息存在二级编码";
						dealFalg = false;
						return dealFalg;
					} else {
						newCustomer = oldCustomer;
					}
				} else {
					// 如果oldCustomer为null即不存在客户信息,此时增加客户资料信息
					Long customerId = sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_NO");
					newCustomer.setId(customerId);
					newCustomer.setUserNo(StringUtil.generateUserNo());                //客户号
					newCustomer.setOrgan(cardBusinessInfo.getOrgan());                //客户名称
					//服务密码默认为联系手机号码后6位
					newCustomer.setServicePwd(StringUtil.getServicePwd(cardBusinessInfo.getCusMobile()));    //服务密码
					newCustomer.setUserType(cardBusinessInfo.getUserType());        //客户类型
					newCustomer.setLinkMan(cardBusinessInfo.getLinkMan());            //联系人
					newCustomer.setTel(cardBusinessInfo.getCusTel());                //联系电话
					newCustomer.setMobile(cardBusinessInfo.getCusMobile());            //联系手机号码
					// newCustomer.setShortTel("");									//用于开通短信服务的号码？？？？？
					newCustomer.setAddr(cardBusinessInfo.getCusAddr());                //联系地址
					newCustomer.setZipCode(cardBusinessInfo.getCusZipCode());        //邮政编码
					newCustomer.setEmail(cardBusinessInfo.getCusEmail());            //电子邮箱
					newCustomer.setState("1");                                        //状态：1正常
					newCustomer.setOperId(cardBusinessInfo.getImportOper());        //操作员
					newCustomer.setPlaceId(cardBusinessInfo.getImportPlace());        //注册网点
					newCustomer.setOperName(serviceFlowRecord.getOperName());        //操作员名称
					newCustomer.setOperNo(serviceFlowRecord.getOperNo());            //操作员编号
					newCustomer.setPlaceName(serviceFlowRecord.getPlaceName());        //操作网点名称
					newCustomer.setPlaceNo(serviceFlowRecord.getPlaceNo());            //操作网点编号
					newCustomer.setUpDateTime(new Date());                            //更新时间，第一次新建客户的时候set，修改的时候update
					newCustomer.setFirRunTime(new Date());                            //首次使用时间
					/**营改增新增客户信息上传      luningyun  end datetime 2017-10-21 15:19 */
					//缺少字段，待王劲毫 昨晚客户信息管理时再改
					/**营改增新增客户信息上传      luningyun  start datetime 2017-10-21 15:19*/
					customerDao.save(newCustomer);

					// ②增加总账户信息（主账户信息表）【存在客户信息则不用添加】
					MainAccountInfo mainAccountInfo = new MainAccountInfo();
					Long mainAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSMainAccountInfo_NO");
					mainAccountInfo.setId(mainAccountInfoId);                            //序列id
					mainAccountInfo.setMainId(newCustomer.getId());                        //客户ID，客户信息表的序列ID
					mainAccountInfo.setBalance(new BigDecimal("0"));                    //账户余额
					mainAccountInfo.setAvailableBalance(new BigDecimal("0"));            //账户可用余额
					mainAccountInfo.setPreferentialBalance(new BigDecimal("0"));        //优惠余额
					mainAccountInfo.setFrozenBalance(new BigDecimal("0"));                //冻结余额
					mainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));        //可退余额
					mainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));        //提现冻结余额
					mainAccountInfo.setState("1");                                        //状态：1.正常
					mainAccountInfo.setOperId(cardBusinessInfo.getImportOper());        //操作员
					mainAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());        //开户网点
					mainAccountInfo.setOpebAccountDate(new Date());                        //开户时间

					mainAccountInfoDao.save(mainAccountInfo);

					// 新增储值卡子账户信息
					SubAccountInfo subAccountInfo = new SubAccountInfo();
					Long subAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSSubAccountInfo_NO");
					subAccountInfo.setId(subAccountInfoId);
					subAccountInfo.setMainId(mainAccountInfo.getId());// 总账户信息表的序列ID

					subAccountInfo.setSubAccountNo(newCustomer.getUserNo() + "1" + "001");// 账户号
					// :客户号+类型+序号（其中序号为记帐卡账户号的后3位）
					subAccountInfo.setSubAccountType("1");// 记帐卡子账户，1：储值卡子账户 ，2：记帐卡子账户

					subAccountInfo.setOperId(cardBusinessInfo.getImportOper());
					subAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());
					subAccountInfo.setOperNo(serviceFlowRecord.getOperNo());
					subAccountInfo.setOperName(serviceFlowRecord.getOperName());
					subAccountInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
					subAccountInfo.setPlaceName(serviceFlowRecord.getPlaceName());
					subAccountInfo.setOperTime(new Date());

					subAccountInfoDao.save(subAccountInfo);


				}

				if (dealFalg) {
					SubAccountInfo subAccountInfo = null;
					boolean bailFlag = false;    //设置保证金新增标识
					//查询为空，新增记帐卡申请信息
					if (accountCApply == null) {
						accountCApply = new AccountCApply();
						Long accountCApplyId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapply_NO");
						accountCApply.setId(accountCApplyId);
						accountCApply.setCustomerId(newCustomer.getId());                        //对应客户id
						accountCApply.setAccountType("2");                                        //账户类型：0、对公1、储蓄2、信用卡3、跨行划扣4、统一划账5、其他
						accountCApply.setLinkman(cardBusinessInfo.getLinkMan());                //账户联系人
						accountCApply.setTel(cardBusinessInfo.getLinkTel());                    //联系手机
						accountCApply.setValidity(DateUtil.getValidity(new Date()));            //子账户有效期，最大不超过5年
						accountCApply.setBank(tbBankdetail.getBankName());                                                //开户银行
//						accountCApply.setBankSpan("2");											//跨行划扣银行
						accountCApply.setObaNo(bankCode);
						accountCApply.setBankAccount(cardBusinessInfo.getCreditCardNo());        //开户银行帐号(信用卡号？)
//						accountCApply.setBankName("建设银行");											//开户银行名称
						accountCApply.setAccName(cardBusinessInfo.getOrgan());                                            //开户帐户户名
						accountCApply.setInvoiceprn("2");                                        //发票打印方式1、按单卡2、按帐号
						accountCApply.setReqcount(0L);                                            //申请数量
						accountCApply.setResidueCount(0L);                                        //剩余数量
						accountCApply.setNewCardFlag("0");//否
						accountCApply.setVirenum(1);


						//转账次数限制

						//没有车辆就没有单卡保证金
						//accountCApply.setBail(new BigDecimal("0"));// 单卡保证金


						accountCApply.setVirType("1");                                            // 转帐类型-限额转账
						accountCApply.setMaxacr(new BigDecimal("0"));                            // 通行费转帐限额
						// accountCApply.setBankClearNo("");//开户银行清算行号
						// accountCApply.setBankAcceptNo(bankAcceptNo);//开户行集中受理行号
						accountCApply.setAppState("6");                                            // 审核通过
						accountCApply.setAppTime(new Date());
						accountCApply.setOperTime(new Date());
						accountCApply.setOperId(cardBusinessInfo.getImportOper());
						accountCApply.setPlaceId(cardBusinessInfo.getImportPlace());
						accountCApply.setOperNo(serviceFlowRecord.getOperNo());
						accountCApply.setOperName(serviceFlowRecord.getOperName());
						accountCApply.setPlaceNo(serviceFlowRecord.getPlaceNo());
						accountCApply.setPlaceName(serviceFlowRecord.getPlaceName());
						accountCApply.setShutDownStatus("0");
						accountCApply.setDebitCardType(1);                                        //代理点

						// ③增加子账户信息（子账户信息表）
						MainAccountInfo mainAccount = mainAccountInfoDao.findByMainId(newCustomer.getId());
						if (mainAccount == null) {
							cardBusinessInfo.setRemark("009100103：取主账户信息表错误");
							message = "009100103：取主账户信息表错误";
							dealFalg = false;
							return dealFalg;
						}
						subAccountInfo = new SubAccountInfo();
						Long subAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSSubAccountInfo_NO");
						subAccountInfo.setId(subAccountInfoId);
						subAccountInfo.setMainId(mainAccount.getId());// 总账户信息表的序列ID

						SubAccountInfo sub = subAccountInfoDao.findLastDateSub(accountCApply.getCustomerId());
						subAccountInfo.setSubAccountNo(newCustomer.getUserNo() + "2" + StringUtil.getSerailNumber(sub));// 账户号
						// :客户号+类型+序号（其中序号为记帐卡账户号的后3位）
						subAccountInfo.setSubAccountType("2");// 记帐卡子账户，1：储值卡子账户 ，2：记帐卡子账户

						subAccountInfo.setApplyID(accountCApply.getId());// 记帐卡申请id
						subAccountInfo.setOperId(cardBusinessInfo.getImportOper());
						subAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());
						subAccountInfo.setOperNo(serviceFlowRecord.getOperNo());
						subAccountInfo.setOperName(serviceFlowRecord.getOperName());
						subAccountInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
						subAccountInfo.setPlaceName(serviceFlowRecord.getPlaceName());
						subAccountInfo.setOperTime(new Date());
//									    subAccountInfo.setAgentsMay(1L);// 代理点

						// ⑤增加保证金账户信息（保证金账户信息表）【存在保证金则不用添加】
						BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(newCustomer.getId());
						if (bailAccountInfo == null) {
							bailFlag = true;    //修改保证金新增标识
							bailAccountInfo = new BailAccountInfo();
							Long bailAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfo_NO");
							bailAccountInfo.setId(bailAccountInfoId);
							bailAccountInfo.setMainId(newCustomer.getId());                    //客户信息表的序列ID
							bailAccountInfo.setBailFee(new BigDecimal("0"));                //保证金金额
							bailAccountInfo.setOperId(cardBusinessInfo.getImportOper());
							bailAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());
							bailAccountInfo.setOperNo(serviceFlowRecord.getOperNo());
							bailAccountInfo.setOperName(serviceFlowRecord.getOperName());
							bailAccountInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
							bailAccountInfo.setPlaceName(serviceFlowRecord.getPlaceName());
							bailAccountInfo.setOperTime(new Date());
							bailAccountInfoDao.saveBailAccount(bailAccountInfo);
						}

						subAccountInfoDao.save(subAccountInfo);
						//新增了个子帐户号字段
						accountCApply.setSubAccountNo(subAccountInfo.getSubAccountNo());        //子账户号
						accountCApplyDao.saveAccountCApply(accountCApply);


						//保存服务方式登记表信息
						BillGet billGet = new BillGet();
						BigDecimal SEQ_CSMS_bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
						billGet.setId(Long.valueOf(SEQ_CSMS_bill_get_NO.toString()));
						billGet.setMainId(newCustomer.getId());
						billGet.setCardType("2");//卡类型设为“2”，表示记帐卡
						billGet.setCardAccountID(subAccountInfo.getId());
						billGet.setCardBankNo(accountCApply.getBankAccount());
						billGet.setSerItem("4");            //4：月结通知
						billGet.setSerType("2");            //2：长期服务
						//服务类型，服务开始时间，服务结束时间暂无

						billGet.setOperTime(new Date());
						billGet.setOperId(accountCApply.getOperId());//操作员取申请操作员
						billGet.setPlaceId(accountCApply.getPlaceId());//网点取申请网点
						billGet.setOperName(accountCApply.getOperName());
						billGet.setOperNo(accountCApply.getOperNo());
						billGet.setPlaceName(accountCApply.getPlaceName());
						billGet.setPlaceNo(accountCApply.getPlaceNo());

						billGetDao.save(billGet);
					} else {
						subAccountInfo = subAccountInfoDao.findByApplyId(accountCApply.getId());
					}


					// 记帐卡业务记录（发行）
					AccountCBussiness accountCBussiness = new AccountCBussiness();
					BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
					accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
					accountCBussiness.setUserId(newCustomer.getId());
					accountCBussiness.setAccountId(subAccountInfo.getId());
					accountCBussiness.setState("1");//1-发行
					accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
					accountCBussiness.setTradeTime(new Date());
					accountCBussiness.setOperId(accountCApply.getOperId());
					accountCBussiness.setPlaceId(accountCApply.getPlaceId());
					accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
					//新增的字段
					accountCBussiness.setOperName(accountCApply.getOperName());
					accountCBussiness.setOperNo(accountCApply.getOperNo());
					accountCBussiness.setPlaceName(accountCApply.getPlaceName());
					accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());

					//回执打印用
					accountCBussiness.setBusinessId(accountCApply.getHisseqId());
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
					accountCBussiness.setSuit(accountCInfo.getSuit());
					accountCBussinessDao.save(accountCBussiness);
					// 记帐卡业务记录（保证金新增）
					if (bailFlag) {
						AccountCBussiness accountCBussiness4Bail = new AccountCBussiness();
						BigDecimal SEQ_CSMSAccountCbussiness_NO4Bail = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
						accountCBussiness4Bail.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO4Bail.toString()));
						accountCBussiness4Bail.setUserId(newCustomer.getId());
						accountCBussiness4Bail.setAccountId(subAccountInfo.getId());
						accountCBussiness4Bail.setState("2");//2-保证金新增
						accountCBussiness4Bail.setRealPrice(new BigDecimal("0"));// 业务费用
						accountCBussiness4Bail.setTradeTime(new Date());
						accountCBussiness4Bail.setOperId(accountCApply.getOperId());
						accountCBussiness4Bail.setPlaceId(accountCApply.getPlaceId());
						accountCBussiness4Bail.setCardNo(cardBusinessInfo.getUTCardNo());
						//新增的字段
						accountCBussiness4Bail.setOperName(accountCApply.getOperName());
						accountCBussiness4Bail.setOperNo(accountCApply.getOperNo());
						accountCBussiness4Bail.setPlaceName(accountCApply.getPlaceName());
						accountCBussiness4Bail.setPlaceNo(accountCApply.getPlaceNo());

						accountCBussiness4Bail.setBusinessId(accountCApply.getHisseqId());
						accountCBussiness4Bail.setAccountCApplyHisID(accountCApply.getHisseqId());
						accountCBussiness4Bail.setSuit(accountCInfo.getSuit());


						accountCBussinessDao.save(accountCBussiness4Bail);
					}

					//调整的客服流水（新）
					ServiceWater serviceWater = new ServiceWater();
					Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

					serviceWater.setId(serviceWater_id);

					serviceWater.setCustomerId(newCustomer.getId());
					serviceWater.setUserNo(newCustomer.getUserNo());
					serviceWater.setUserName(newCustomer.getOrgan());
					serviceWater.setSerType("107");//107:开户申请
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
					serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
					serviceWater.setAccountCBussinessId(accountCBussiness.getId());
					serviceWater.setOperId(accountCBussiness.getOperId());
					serviceWater.setOperName(accountCBussiness.getOperName());
					serviceWater.setOperNo(accountCBussiness.getOperNo());
					serviceWater.setPlaceId(accountCBussiness.getPlaceId());
					serviceWater.setPlaceName(accountCBussiness.getPlaceName());
					serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
					serviceWater.setRemark("代理客服系统：联名记帐卡开户申请");
					serviceWater.setOperTime(new Date());
					serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
					serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());

					serviceWaterDao.save(serviceWater);


					//客服流水号，新加的字段
					cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());


					// ⑥增加记帐卡发行信息（记帐卡信息表）注：记帐卡状态为4：开户申请
//									AccountCInfo accountCInfo = new AccountCInfo();
//									Long accountCInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
					accountCInfo.setId(accountCInfoId);
					accountCInfo.setCardNo(cardBusinessInfo.getUTCardNo());        //卡号(粤通卡卡号)
					accountCInfo.setCustomerId(newCustomer.getId());            //客户信息表.序列ID
					accountCInfo.setAccountId(subAccountInfo.getId());            //对应账户id
					accountCInfo.setState("0");                        // 0：正常 1：挂失2：注销3：停用（挂起）4：锁定，开户申请
					accountCInfo.setCost(new BigDecimal("0"));                    //成本费
					accountCInfo.setRealCost(new BigDecimal("0"));                //实收成本费
					accountCInfo.setIssueFlag("");                                //销售方式？

					//联名记帐卡发行还未绑定车辆信息
					accountCInfo.setBind("0");// 绑定标志
					accountCInfo.setTradingPwd(newCustomer.getServicePwd());


					accountCInfo.setIssueOperId(cardBusinessInfo.getImportOper());
					accountCInfo.setIssuePlaceId(cardBusinessInfo.getImportPlace());
					accountCInfo.setOperNo(serviceFlowRecord.getOperNo());
					accountCInfo.setOperName(serviceFlowRecord.getOperName());
					accountCInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
					accountCInfo.setPlaceName(serviceFlowRecord.getPlaceName());
					// accountCInfo.setS_con_pwd_flag("");//是否需要交易密码？
					// accountCInfo.setTradingPwd("");//交易密码，MD加密
//									accountCInfo.setAgentsMay(1L);// 代理点
					accountCInfo.setLinkMan(cardBusinessInfo.getLinkMan());
					accountCInfo.setLinkTel(cardBusinessInfo.getLinkTel());
					accountCInfo.setLinkMobile(cardBusinessInfo.getLinkMobile());
					accountCInfo.setLinkAddr(cardBusinessInfo.getLinkAddr());
					accountCInfo.setLinkZipCode(cardBusinessInfo.getLinkZipCode());

					accountCInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
					accountCInfoDao.save(accountCInfo);

					//记帐卡二次发行数据信息至清算系统
					cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);

					//清算系统，记帐卡状态信息-状态为3（挂起）-没用了
//									accountCService.saveACinfo(3, accountCInfo, "4");


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

					//写给铭鸿的清算数据：卡片状态信息
					String userType = "";
					if (UserTypeEnum.person.getValue().equals(newCustomer.getUserType())) {
						userType = "0";//个人
					} else {
						userType = "1";//单位
					}
					cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()),
							CardStateSendSerTypeEnum.acIssue.getValue(), accountCInfo.getCardNo(), "23", userType);

					//记帐卡用户数据（客服->清算）
					AccCardUserInfoSend accCardUserInfoSend = new AccCardUserInfoSend();
					accCardUserInfoSend.setId(sequenceUtil.getSequenceLong("SEQ_ACCCARDUSERINFO_SEND"));
//					String boardListNo = blackListService.getAllBoardListNo("1013");
					accCardUserInfoSend.setOrgan(newCustomer.getOrgan());
					accCardUserInfoSend.setAccountType(new Integer(accountCApply.getAccountType()));
					String accountType = accountCApply.getAccountType();
					if (StringUtils.isNotBlank(accountType) && !accountType.equals("3")) {
						accCardUserInfoSend.setBankNo(Long.valueOf(accountCApply.getObaNo()));
					}
					accCardUserInfoSend.setBankAccount(accountCApply.getBankAccount());
					accCardUserInfoSend.setBankName(accountCApply.getBank());
					if (StringUtils.isNotBlank(accountType) && accountType.equals("3")) {
						//todo 这里开户银行不知道需不需要保存到这个清算表
//						accCardUserInfoSend.set(newAccountCApply.getBankAcceptNo()!=null?newAccountCApply.getBankAcceptNo().toString():null);
						accCardUserInfoSend.setBranchBankno(accountCApply.getBankAcceptNo() != null ? accountCApply.getBankAcceptNo().toString() : null);
						accCardUserInfoSend.setReconBankNo(accountCApply.getBankClearNo() != null ? accountCApply.getBankClearNo().toString() : null);
					}
					accCardUserInfoSend.setSpanBankNo(accountCApply.getObaNo() != null ? Long.valueOf(accountCApply.getObaNo()) : null);
					accCardUserInfoSend.setVirType(new Integer(accountCApply.getVirType()));
					accCardUserInfoSend.setVirCount(accountCApply.getVirenum());
					accCardUserInfoSend.setMaxAcr(accountCApply.getMaxacr());
					accCardUserInfoSend.setSystemType(0);// 0 普通客户   1香港快易通    2澳门通
					accCardUserInfoSend.setReqTime(new Timestamp(accountCApply.getOperTime().getTime()));
					accCardUserInfoSend.setCheckTime(new Timestamp(accountCApply.getAppTime().getTime()));
					accCardUserInfoSend.setReMark("代理客服系统：记帐卡开户申请");
					accCardUserInfoSend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					accCardUserInfoSend.setAccName(accountCApply.getAccName());
//					accCardUserInfoSend.setBoardListNo(new Long(boardListNo));
					Long boardListNo = new Long(String.valueOf((new Date()).getTime()) + "1013");
					accCardUserInfoSend.setBoardListNo(new Long(boardListNo));
					cardSecondIssuedDao.saveAccountCUser(accCardUserInfoSend);


					//发行成功后，更新营运接口调用登记记录的客服状态
					interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
					if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
						interfaceRecord.setCsmsState("1");
						interfaceRecordDao.update(interfaceRecord);
					}


				}
				return dealFalg;

			}
		} else {
			//否则，如果粤通卡号已存在于数据库，备注该粤通卡卡号已存在
			cardBusinessInfo.setRemark("009100096：粤通卡卡号已经在记帐卡表中存在");
			message = "009100096：粤通卡卡号已经在记帐卡表中存在";
			dealFalg = false;
			return dealFalg;
		}
	}

	private List<CardBusinessInfo> findBusinessInfo(String cardBusinessType,
	                                                String cardNo, String creditCardNo, String oldCardNo, String oldCreditCardNo, String businessType, Boolean restartFlag) {
		List<CardBusinessInfo> cLists = null;
		if (CardBusinessTypeEnum.AMS.getIsThis(cardBusinessType)) {
			if (restartFlag) {
				cLists = cardBusinessInfoDao.findAvailableCancelRecord(cardNo, creditCardNo);
			} else {
				cLists = cardBusinessInfoDao.findBycardNoType(cardNo, creditCardNo, oldCardNo, oldCreditCardNo, businessType);
			}

		} else if (CardBusinessTypeEnum.BANK.getIsThis(cardBusinessType)) {
			if (restartFlag) {
				cLists = bankInterfaceDao.findAvailableCancelRecord(cardNo, creditCardNo);
			} else {
				cLists = bankInterfaceDao.findAccountCByCardNoType(cardNo, creditCardNo, oldCardNo, oldCreditCardNo, businessType);
			}

		}
		return cLists;
	}

	/***
	 * 开户确认-02
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param message
	 * @param dealFalg
	 * @param cardBusinessType
	 */
	public boolean openAccountConfirm(boolean cardNocheck,
	                                  CardBusinessInfo cardBusinessInfo,
	                                  ServiceFlowRecord serviceFlowRecord, String bankCode,
	                                  String message, boolean dealFalg, String cardBusinessType) {
		// 判断是否为本代理点(文件导入时已经做校验)
		if (cardNocheck) {
			//是否需要校验销户后重启
			boolean restartCheckFlag = false;
			//判断是否有开户申请记录
			List<CardBusinessInfo> cLists = findBusinessInfo(cardBusinessType,
					cardBusinessInfo.getUTCardNo(), cardBusinessInfo.getCreditCardNo(), null, null, "01", restartCheckFlag);

			if (cLists != null && !cLists.isEmpty()) {
				AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());// 根据记帐卡号（即粤通卡卡号）找
				if (accountCInfo != null) {
					// 开户确认前必须是正常的状态
					if (accountCInfo.getState().equals("0")) {//取消锁定状态，开户申请后卡状态为正常
						//判断粤通卡卡号与信用卡是否对应
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());

						//判断记账卡信息表和银行卡信息表的客户号以及子账户ID是否一致
						if (accountCApply == null || !accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
							cardBusinessInfo.setRemark("009101033:信用卡卡号与记账卡卡号无法匹配");
							message = "009101033:信用卡卡号与记账卡卡号无法匹配";
							dealFalg = false;
							return dealFalg;
						} else {
							SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
							if (subAccountInfo == null) {
								cardBusinessInfo.setRemark("009101041:取子账户信息表错误");
								message = "009101041:取子账户信息表错误";
								dealFalg = false;
								return dealFalg;
							}
							if (!subAccountInfo.getSubAccountNo().equals(accountCApply.getSubAccountNo())) {
								cardBusinessInfo.setRemark("009101033:信用卡卡号与记账卡卡号无法匹配");
								message = "009101033:信用卡卡号与记账卡卡号无法匹配";
								dealFalg = false;
								return dealFalg;
							}

							//发行确认更新发行时间：存在发行确认记录，发行时间以发行确认时间，不存在以申请时间为发行时间
							//涉及管理费的计算
							accountCInfo.setIssueTime(new Date());//发行时间
							accountCInfoDao.update(accountCInfo);
							return dealFalg;

						}

					} else {
						// 没做开户申请，就直接做开户确认；

						cardBusinessInfo.setRemark("009101040:粤通卡卡状态异常");
						message = "009101040:粤通卡卡号无开户申请记录";
						dealFalg = false;
						return dealFalg;
					}
				} else {
					// 没做开户申请，就直接做开户确认；
					cardBusinessInfo.setRemark("009101039:粤通卡卡号非系统卡");
					message = "009101039:粤通卡卡号非系统卡";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				// 没做开户申请，就直接做开户确认；
				cardBusinessInfo.setRemark("009101038:该粤通卡卡号和信用卡号无开户申请记录");
				message = "009101038:该粤通卡卡号和信用卡号无开户申请记录";
				dealFalg = false;
				return dealFalg;
			}


		} else {
			// 如果不是本代理点
			cardBusinessInfo.setRemark("009101002:该卡不是本行联名卡");
			message = "009101002:该卡不是本行联名卡";
			dealFalg = false;
			return dealFalg;
		}
	}


	/***
	 * 资料变更-03
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean dataChange(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		if (cardNocheck) {
			// 资料变更（需求调研说不能修改客户资料，待确认）
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
			if (accountCInfo != null) {
				//挂失卡不能做客户资料变更
				if (!accountCInfo.getState().equals("1")) {
									/*by sxw 20170603  暂时不需要，需求未确认
									Customer oldCustomer = customerDao.findById(accountCInfo.getCustomerId());
									// 资料更改的历史
									CustomerHis customerHis = new CustomerHis();
									Long customerHisId = sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_his_NO");
									customerHis.setId(customerHisId);
									customerHis.setGenTime(new Date());
									customerHis.setGenReason("1");// 1：资料更改；
									customerHis.setUserNo(oldCustomer.getUserNo());
									customerHis.setOrgan(oldCustomer.getOrgan());
									customerHis.setServicePwd(oldCustomer.getServicePwd());
									customerHis.setUserType(oldCustomer.getUserType());
									customerHis.setLinkMan(oldCustomer.getLinkMan());
									customerHis.setIdType(oldCustomer.getIdType());
									customerHis.setIdCode(oldCustomer.getIdCode());
									customerHis.setRegisteredCapital(oldCustomer.getRegisteredCapital());
									customerHis.setTel(oldCustomer.getTel());
									customerHis.setMobile(oldCustomer.getMobile());
									customerHis.setShortTel(oldCustomer.getShortTel());
									customerHis.setAddr(oldCustomer.getAddr());
									customerHis.setZipCode(oldCustomer.getZipCode());
									customerHis.setEmail(oldCustomer.getEmail());
									customerHis.setState(oldCustomer.getState());
									customerHis.setCancelTime(oldCustomer.getCancelTime());
									customerHis.setOperId(oldCustomer.getOperId());
									customerHis.setUpDateTime(oldCustomer.getUpDateTime());
									customerHis.setFirRunTime(oldCustomer.getFirRunTime());
									customerHis.setPlaceId(oldCustomer.getPlaceId());
									customerHis.setHisSeqId(oldCustomer.getHisSeqId());
									customerHis.setOperNo(oldCustomer.getOperNo());
									customerHis.setOperName(oldCustomer.getOperName());
									customerHis.setPlaceNo(oldCustomer.getPlaceNo());
									customerHis.setPlaceName(oldCustomer.getPlaceName());

									customerHisDao.save(customerHis);

									// ①修改客户资料(除客户姓名、证件类型、证件号码外的资料)
									// oldCustomer.setId(customerId);
									// oldCustomer.setUserNo(StringUtil.generateUserNo());//客户号
									// oldCustomer.setOrgan(cardBusinessInfo.getOrgan());
									oldCustomer.setUserType(cardBusinessInfo.getUserType());// 客户类型
									oldCustomer.setLinkMan(cardBusinessInfo.getLinkMan());
									oldCustomer.setTel(cardBusinessInfo.getCusTel());// 联系电话
									oldCustomer.setMobile(cardBusinessInfo.getCusMobile());// 联系手机号码
									oldCustomer.setShortTel("");// 用于开通短信服务的号码
									oldCustomer.setAddr(cardBusinessInfo.getCusAddr());
									oldCustomer.setZipCode(cardBusinessInfo.getCusZipCode());
									oldCustomer.setEmail(cardBusinessInfo.getCusEmail());
									// oldCustomer.setState("1");//1:正常2:销户
									oldCustomer.setOperId(cardBusinessInfo.getImportOper());
									oldCustomer.setPlaceId(cardBusinessInfo.getImportPlace());
									oldCustomer.setOperNo(serviceFlowRecord.getOperNo());
									oldCustomer.setOperName(serviceFlowRecord.getOperName());
									oldCustomer.setPlaceNo(serviceFlowRecord.getPlaceNo());
									oldCustomer.setPlaceName(serviceFlowRecord.getPlaceName());
									// oldCustomer.setFirRunTime(new Date());//
									oldCustomer.setUpDateTime(new Date());
									oldCustomer.setHisSeqId(customerHis.getId());

									customerDao.update(oldCustomer);

										*/
					return dealFalg;
				} else {
					// 如果找出来的accountCInfo的状态不正常
					cardBusinessInfo.setRemark("009102095：该卡为挂失卡");
					message = "009102095：该卡为挂失卡";
					dealFalg = false;
					return dealFalg;
				}


			} else {
				// 如果accountCInfo为null，即证明没做开户申请
				cardBusinessInfo.setRemark("009102091：该卡为非系统卡");
				message = "009102091：该卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果不是本代理点
			cardBusinessInfo.setRemark("009102012：该卡不是本行联名卡");
			message = "009102012：该卡不是本行联名卡";
			dealFalg = false;
			return dealFalg;
		}
	}


	/****
	 * 挂失-04
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean saveLose(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		if (cardNocheck) {
			// ①修改记帐卡信息表状态（记帐卡信息表）注：记帐卡状态为1：挂失
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
			if (accountCInfo != null) {
				Customer customer = customerDao.findById(accountCInfo.getCustomerId());
				if (customer != null) {

					if (accountCInfo.getState().equals("0") || accountCInfo.getState().equals("3")) {
						//生成记帐卡业务记录和客服流水
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
						if (accountCApply != null) {
							if (!accountCInfo.getCustomerId().equals(accountCApply.getCustomerId())) {
								cardBusinessInfo.setRemark("009103100：粤通卡号与信用卡号不匹配");
								message = "009103100：粤通卡号与信用卡号不匹配";
								dealFalg = false;
								return dealFalg;
							} else {
								SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
								if (subAccountInfo != null) {
									if (!subAccountInfo.getSubAccountNo().equals(accountCApply.getSubAccountNo())) {
										cardBusinessInfo.setRemark("009103100：粤通卡号与信用卡号不匹配");
										message = "009103100：粤通卡号与信用卡号不匹配";
										dealFalg = false;
										return dealFalg;
									}
									// 先记录历史记录
									AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
									Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
									accountCInfoHis.setId(accountCInfoHisId);
									accountCInfoHis.setGenReason("5");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正

									accountCInfo.setHisSeqId(accountCInfoHis.getId());//挂失后update历史序列id
									accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

									accountCInfo.setState("1");// 记帐卡状态为挂失
									accountCInfoDao.update(accountCInfo);


									// ③增加黑名单（联名卡黑名单信息表）
									CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
									Long cardBlacklistInfoId = sequenceUtil
											.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
									cardBlacklistInfo.setId(cardBlacklistInfoId);
									cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getUTCardNo());
									cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
									cardBlacklistInfo.setUserNo(customer.getUserNo());
									cardBlacklistInfo.setProduceTime(new Date());
									cardBlacklistInfo.setProduceReason("1");// 产生原因挂失
									cardBlacklistInfo.setRemark("代理客服系统：联名记帐卡挂失");

									cardBlacklistDao.save(cardBlacklistInfo);


									// 记帐卡业务记录
									AccountCBussiness accountCBussiness = new AccountCBussiness();
									BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
									accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
									accountCBussiness.setUserId(customer.getId());
									accountCBussiness.setAccountId(subAccountInfo.getId());
									accountCBussiness.setState("3");        //挂失-3
									accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
									accountCBussiness.setTradeTime(new Date());
									accountCBussiness.setOperId(accountCApply.getOperId());
									accountCBussiness.setPlaceId(accountCApply.getPlaceId());
									accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
									//新增的字段
									accountCBussiness.setOperName(accountCApply.getOperName());
									accountCBussiness.setOperNo(accountCApply.getOperNo());
									accountCBussiness.setPlaceName(accountCApply.getPlaceName());
									accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
									accountCBussiness.setBusinessId(accountCApply.getHisseqId());
									accountCBussinessDao.save(accountCBussiness);

									//卡片状态信息表
									String userType = "";
									if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
										userType = "0";//个人
									} else {
										userType = "1";//单位
									}
									cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.loss.getValue()),
											CardStateSendSerTypeEnum.loss.getValue(), accountCInfo.getCardNo(), "23", userType);

									//记帐卡挂失（原铭鸿下发黑名单）
									blackListService.saveCardLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), cardBlacklistInfo.getProduceTime()
											, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
											cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
											new Date());


									//生成客服流水
									ServiceWater serviceWater = new ServiceWater();
									Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

									serviceWater.setId(serviceWater_id);

									serviceWater.setCustomerId(customer.getId());
									serviceWater.setUserNo(customer.getUserNo());
									serviceWater.setUserName(customer.getOrgan());
									serviceWater.setSerType("209");//209:粤通卡挂失
									serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
									serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
									serviceWater.setAccountCBussinessId(accountCBussiness.getId());
									serviceWater.setOperId(accountCBussiness.getOperId());
									serviceWater.setOperName(accountCBussiness.getOperName());
									serviceWater.setOperNo(accountCBussiness.getOperNo());
									serviceWater.setPlaceId(accountCBussiness.getPlaceId());
									serviceWater.setPlaceName(accountCBussiness.getPlaceName());
									serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
									serviceWater.setRemark("代理客服系统：联名记帐卡挂失");
									serviceWater.setOperTime(new Date());
									serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
									serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());

									serviceWaterDao.save(serviceWater);

									//客服流水号关联业务信息，新加的字段
									cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

									//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171018
									VehicleInfo vehicleInfo = iVehicleInfoService.findByAccountCNo(accountCInfo
											.getCardNo());
									// 调用用户卡信息上传及变更接口
									realTransferService.accountCInfoTransfer(customer,
											accountCInfo, vehicleInfo, CardStatusEmeu.CARD_LOSS.getCode(),
											OperationTypeEmeu.UPDATE.getCode());

									// 调用用户卡黑名单上传及变更接口
									noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
											new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EN_BLACK.getCode());
									//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171018

									return dealFalg;
								} else {
									cardBusinessInfo.setRemark("009103098：取记帐卡子账户信息错误");
									message = "009103098：取记帐卡子账户信息错误";
									dealFalg = false;
									return dealFalg;
								}
							}

						} else {
							cardBusinessInfo.setRemark("009103097：取记帐卡申请信息错误");
							message = "009103097：取记帐卡申请信息错误";
							dealFalg = false;
							return dealFalg;
						}
					} else {
						// 没开户确认
						cardBusinessInfo.setRemark("009103096：该卡的卡状态非正常或挂起");
						message = "009103096：该卡的卡状态非正常或挂起";
						dealFalg = false;
						return dealFalg;
					}
				} else {
					cardBusinessInfo.setRemark("009103099：取客户信息错误");
					message = "009103099：取客户信息错误";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				// 没做开户申请
				cardBusinessInfo.setRemark("009103091：该卡为非系统卡");
				message = "009103091：该卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果不是本代理点
			cardBusinessInfo.setRemark("009103002：该卡不是本行联名卡");
			return dealFalg;
		}
	}

	/***
	 * 换卡申请-05（正常换卡，信用卡卡号不变，已重新制卡）
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean changeCardApply(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		if (cardNocheck) {
			// ①修改旧卡状态（记帐卡信息表）注：记帐卡状态为2：注销
			AccountCInfo oldAccountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getOldUTCardNo());// 这里应该是旧卡卡号
			if (oldAccountCInfo != null) {

				//判断旧卡状态是否非挂失
				if (!oldAccountCInfo.getState().equals("1")) {

					List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getOldUTCardNo());

					//旧卡只要为黑名单不允许办理换卡业务
					if (blackListTempList != null && !blackListTempList.isEmpty()) {
						String blackTypeString = StringUtil.getBlackTypeString(blackListTempList);
						if (!blackTypeString.equals("")) {
							blackTypeString = "(" + blackTypeString + ")";
						}
						cardBusinessInfo.setRemark("009104093：旧粤通卡存在黑名单" + blackTypeString);
						message = "009104093：旧粤通卡存在黑名单" + blackTypeString;
						dealFalg = false;
						return dealFalg;
					} else {
						AccountCInfo newAccountC = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());//要换的这张新粤通卡卡号是否早已被发行了？
						//如果新粤通卡卡号不被发行过，才能做本次换卡业务
						if (newAccountC == null) {
							InterfaceRecord interfaceRecord = null;
							Map<String, Object> map = inventoryServiceForAgent.omsInterface(cardBusinessInfo.getUTCardNo(), "1", interfaceRecord, "issue",
									serviceFlowRecord.getPlaceID(), serviceFlowRecord.getOperID(), serviceFlowRecord.getOperName(), "1", "", serviceFlowRecord.getPlaceID(), oldAccountCInfo.getRealCost(), "38");
							boolean result = (Boolean) map.get("result");
							if (!result) {
								cardBusinessInfo.setRemark("009104094：粤通卡卡号在记帐卡初始化表中不存在");
								message = "009104094：粤通卡卡号在记帐卡初始化表中不存在";
								dealFalg = false;
								return dealFalg;
							} else {
								Customer customer = customerDao.findById(oldAccountCInfo.getCustomerId());
								if (customer != null) {
									//生成记帐卡业务记录和客服流水
									AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
									if (accountCApply != null) {

										//判断记账卡和银行卡的客户号，子账户是否一致
										if (!oldAccountCInfo.getCustomerId().equals(accountCApply.getCustomerId())) {
											cardBusinessInfo.setRemark("009104098：旧粤通卡号与新信用卡号不匹配");
											message = "009104098：旧粤通卡号与新信用卡号不匹配";
											dealFalg = false;
											return dealFalg;
										} else {
											SubAccountInfo subAccountInfo = subAccountInfoDao.findById(oldAccountCInfo.getAccountId());
											if (subAccountInfo != null) {
												if (!subAccountInfo.getSubAccountNo().equals(accountCApply.getSubAccountNo())) {
													cardBusinessInfo.setRemark("009104098：旧粤通卡号与新信用卡号不匹配");
													message = "009104098：旧粤通卡号与新信用卡号不匹配";
													dealFalg = false;
													return dealFalg;
												}

												//系统需校验新信用卡名下是否挂非注销的记帐卡
												List<AccountCInfo> accountCInfos = accountCInfoDao.findNoCancelAccountListBySubAccountNo(accountCApply.getSubAccountNo());
												if (accountCInfos != null && !accountCInfos.isEmpty()) {
													for (AccountCInfo item : accountCInfos) {
														if (item.getCardNo().equals(oldAccountCInfo.getCardNo())) {
															continue;
														} else {
															cardBusinessInfo.setRemark("009104099：信用卡名下存在除了当前旧粤通卡的非注销记账卡");
															message = "009104099：信用卡名下存在除了当前旧粤通卡的非注销记账卡";
															dealFalg = false;
															return dealFalg;
														}
													}
												}

												// 先记录旧卡注销前历史记录
												AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
												Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
												accountCInfoHis.setId(accountCInfoHisId);
												accountCInfoHis.setGenReason("3");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
												oldAccountCInfo.setHisSeqId(accountCInfoHis.getId());
												accountCInfoHisDao.save(oldAccountCInfo, accountCInfoHis);

												oldAccountCInfo.setState("2");// 注销

												accountCInfoDao.update(oldAccountCInfo);

												//旧卡写注销登记表
												Cancel oldCancel = new Cancel();
												Long oldCancelId = sequenceUtil.getSequenceLong("SEQ_CSMS_CANCEL_NO");
												oldCancel.setId(oldCancelId);
												oldCancel.setCustomerId(oldAccountCInfo.getCustomerId());
												oldCancel.setFlag("2");//
												oldCancel.setCode(oldAccountCInfo.getCardNo());
												oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
												oldCancel.setOperId(serviceFlowRecord.getOperID());            //操作员ID
												oldCancel.setPlaceId(serviceFlowRecord.getPlaceID());                        //网点ID
												oldCancel.setCancelTime(new Date());
												oldCancel.setBankNo(accountCApply.getBankAccount());                        //开户银行账号
												oldCancel.setBankMember(accountCApply.getBankName());                        //银行客户名称
												oldCancel.setBankOpenBranches(accountCApply.getBank());                        //银行开户网点
												oldCancel.setOperNo(serviceFlowRecord.getOperNo());                            //操作员编号
												oldCancel.setOperName(serviceFlowRecord.getOperName());                        //操作员名称
												oldCancel.setPlaceNo(serviceFlowRecord.getPlaceNo());                        //网点编号
												oldCancel.setPlaceName(serviceFlowRecord.getPlaceName());                    //网点名称
												oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
												oldCancel.setSource("1");//1：代理

												cancelDao.save(oldCancel);

												// ②增加新卡信息（记帐卡信息表）注：记帐卡状态为5：换卡申请-新卡信息继承旧卡
												AccountCInfo accountCInfo = new AccountCInfo();
												Long accountCInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
												accountCInfo.setId(accountCInfoId);
												accountCInfo.setCardNo(cardBusinessInfo.getUTCardNo());// **卡号(粤通卡卡号？)
												accountCInfo.setCustomerId(oldAccountCInfo.getCustomerId());
												accountCInfo.setAccountId(oldAccountCInfo.getAccountId());// 对应账户id,这里与旧记帐卡一样
												accountCInfo.setState("0");// 0：正常
												// 1：挂失2：注销3：停用4：开户申请5：换卡申请6：补领申请7：非过户补领申请8：过户申请
												accountCInfo.setCost(new BigDecimal("0"));// 成本费
												accountCInfo.setRealCost(new BigDecimal("0"));// 实收成本费
												accountCInfo.setIssueTime(new Date());//发行时间

												accountCInfo.setIssueFlag(oldAccountCInfo.getIssueFlag());// 销售方式
												//设置有效起始时间与结束时间
												Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
												if (m != null) {
													accountCInfo.setStartDate((Date) m.get("startDate"));
													accountCInfo.setEndDate((Date) m.get("endDate"));
												}
												//2017/05/27更新
										/*有偿领取（即工本费不为0），保修开始日期则为领取发卡当天，保修截止截止日期为保修开始日期加营运参数 年；无偿领取（即工本费为0），则沿用最近一张的保修开始日期和保修截止日期。
										卡片的有效启用时间、有效截止时间均从营运系统获取。*/

												Calendar cal = Calendar.getInstance();
												;
												if (accountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
													//无偿，沿用原卡的发行时间作为维保起始时间
													cal.setTime(oldAccountCInfo.getIssueTime());
												} else {

												}

												//获取营运参数：维保周期（key=Maintenance time）
												Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
												logger.info(paramMap);
												if (paramMap != null && "成功".equals((String) paramMap.get("message"))) {
													cal.add(Calendar.YEAR, Integer.parseInt((String) paramMap.get("value")));
												} else if (paramMap != null && !"成功".equals((String) paramMap.get("message"))) {
													logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
													throw new ApplicationException();
												} else {
													logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
													throw new ApplicationException();
												}

												accountCInfo.setMaintainTime(cal.getTime());


												accountCInfo.setBind(oldAccountCInfo.getBind());// 绑定标志


												accountCInfo.setIssueOperId(cardBusinessInfo.getImportOper());
												accountCInfo.setIssuePlaceId(cardBusinessInfo.getImportPlace());
												accountCInfo.setOperNo(serviceFlowRecord.getOperNo());
												accountCInfo.setOperName(serviceFlowRecord.getOperName());
												accountCInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
												accountCInfo.setPlaceName(serviceFlowRecord.getPlaceName());
												accountCInfo.setS_con_pwd_flag(oldAccountCInfo.getS_con_pwd_flag());//是否需要交易密码？
												accountCInfo.setTradingPwd(oldAccountCInfo.getTradingPwd());//交易密码，MD加密
//															accountCInfo.setAgentsMay(1L);// 代理点
												accountCInfo.setLinkMan(cardBusinessInfo.getLinkMan());
												accountCInfo.setLinkTel(cardBusinessInfo.getLinkTel());
												accountCInfo.setLinkMobile(cardBusinessInfo.getLinkMobile());
												accountCInfo.setLinkAddr(cardBusinessInfo.getLinkAddr());
												accountCInfo.setLinkZipCode(cardBusinessInfo.getLinkZipCode());

												accountCInfoDao.save(accountCInfo);


												// 记帐卡业务记录-旧卡锁定
												AccountCBussiness accountCBussiness = new AccountCBussiness();
												BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
												accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
												accountCBussiness.setUserId(customer.getId());
												accountCBussiness.setAccountId(subAccountInfo.getId());
												accountCBussiness.setState("6");        //旧卡锁定-6
												accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
												accountCBussiness.setTradeTime(new Date());
												accountCBussiness.setOperId(accountCApply.getOperId());
												accountCBussiness.setPlaceId(accountCApply.getPlaceId());
												accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
												accountCBussiness.setOldCardNo(cardBusinessInfo.getOldUTCardNo());
												//新增的字段
												accountCBussiness.setOperName(accountCApply.getOperName());
												accountCBussiness.setOperNo(accountCApply.getOperNo());
												accountCBussiness.setPlaceName(accountCApply.getPlaceName());
												accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
												accountCBussiness.setBusinessId(accountCApply.getHisseqId());
												accountCBussinessDao.save(accountCBussiness);

												// 记帐卡业务记录-领取新卡
												accountCBussiness = new AccountCBussiness();
												BigDecimal SEQ_CSMSAccountCbussiness_NO2 = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
												accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO2.toString()));
												accountCBussiness.setUserId(customer.getId());
												accountCBussiness.setAccountId(subAccountInfo.getId());
												accountCBussiness.setState("7");        //领取新卡-7
												accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
												accountCBussiness.setTradeTime(new Date());
												accountCBussiness.setOperId(accountCApply.getOperId());
												accountCBussiness.setPlaceId(accountCApply.getPlaceId());
												accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
												accountCBussiness.setOldCardNo(cardBusinessInfo.getOldUTCardNo());
												//新增的字段
												accountCBussiness.setOperName(accountCApply.getOperName());
												accountCBussiness.setOperNo(accountCApply.getOperNo());
												accountCBussiness.setPlaceName(accountCApply.getPlaceName());
												accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
												accountCBussiness.setBusinessId(accountCApply.getHisseqId());
												accountCBussinessDao.save(accountCBussiness);

												//生成客服流水
												ServiceWater serviceWater = new ServiceWater();
												Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

												serviceWater.setId(serviceWater_id);

												serviceWater.setCustomerId(customer.getId());
												serviceWater.setUserNo(customer.getUserNo());
												serviceWater.setUserName(customer.getOrgan());
												serviceWater.setSerType("705");//换卡申请
												serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
												serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
												serviceWater.setAccountCBussinessId(accountCBussiness.getId());
												serviceWater.setOperId(accountCBussiness.getOperId());
												serviceWater.setOperName(accountCBussiness.getOperName());
												serviceWater.setOperNo(accountCBussiness.getOperNo());
												serviceWater.setPlaceId(accountCBussiness.getPlaceId());
												serviceWater.setPlaceName(accountCBussiness.getPlaceName());
												serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
												serviceWater.setRemark("代理客服系统：联名记帐卡换卡申请");
												serviceWater.setOperTime(new Date());
												serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
												serviceWater.setCardNo(cardBusinessInfo.getOldUTCardNo());

												serviceWaterDao.save(serviceWater);

												//客服流水号关联业务信息，新加的字段
												cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

												// ④增加旧卡黑名单（联名卡黑名单信息表）
												if (!StringUtil.isNotBlank(cardBusinessInfo.getOldCreditCardNo())) {
													SubAccountInfo oldSubAccountInfo = subAccountInfoDao
															.findById(oldAccountCInfo.getAccountId());
													AccountCApply oldAccountCApply = accountCApplyDao
															.findById(oldSubAccountInfo.getApplyID());

													cardBusinessInfo.setOldCreditCardNo(oldAccountCApply.getBankAccount());
												}

//													CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
//													Long cardBlacklistInfoId = sequenceUtil
//															.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
//													cardBlacklistInfo.setId(cardBlacklistInfoId);
//													cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getOldUTCardNo());// 旧卡
//													cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getOldCreditCardNo());
//													cardBlacklistInfo.setUserNo(customer.getUserNo());
//													cardBlacklistInfo.setProduceTime(new Date());
//													cardBlacklistInfo.setProduceReason("3");// 产生原因3：换卡销卡
//													// cardBlacklistInfo.setRemark("");
//
//													cardBlacklistDao.save(cardBlacklistInfo);


												//清算系统，记帐卡状态信息（无卡注销）
//											accountCService.saveACinfo(0, accountCInfo, "4");//新卡
//											accountCService.saveACinfo(2, oldAccountCInfo, "4");//旧卡


												//资金转移确认表
												DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getOldCardNo());

												DbasCardFlow dbasCard = new DbasCardFlow();
												dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
												dbasCard.setNewCardNo(accountCBussiness.getCardNo());
												dbasCard.setOldCardNo(accountCBussiness.getOldCardNo());
												if (dbasCardFlow != null)
													dbasCard.setCardNo(dbasCardFlow.getCardNo());
												else
													dbasCard.setCardNo(accountCBussiness.getOldCardNo());
												dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
												dbasCard.setSerType(DBACardFlowSerTypeEnum.gainCard.getValue());
												dbasCard.setApplyTime(new Date());
												dbasCard.setServiceId(accountCBussiness.getId());
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

												//记帐卡二次发行数据（客服->清算）
												cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);

												//记帐卡状态信息表（客服->清算）
												String userType = "";
												if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
													userType = "0";//个人
												} else {
													userType = "1";//单位
												}

												//旧记帐卡的车卡标签绑定记录
												CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(oldAccountCInfo.getId());
												if (carObuCardInfo != null) {
													//车辆
													VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
													if (vehicle != null) {
														//新增车辆业务记录表CSMS_Vehicle_Bussiness
														VehicleBussiness vehicleBussiness = new VehicleBussiness();
														BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
														vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
														vehicleBussiness.setCustomerID(vehicle.getCustomerID());
														vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
														vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
														vehicleBussiness.setCardNo(accountCInfo.getCardNo());//新卡号
														vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);//记帐卡
														//vehicleBussiness.setTagNo(newTagInfo.getTagNo());
														vehicleBussiness.setType(VehicleBussinessEnum.accountCGainWithoutCard.getValue());//记帐卡换卡(当作无卡换卡)
														vehicleBussiness.setOperID(accountCBussiness.getOperId());
														vehicleBussiness.setPlaceID(accountCBussiness.getPlaceId());
														//新增的字段
														vehicleBussiness.setOperName(accountCBussiness.getOperName());
														vehicleBussiness.setOperNo(accountCBussiness.getOperNo());
														vehicleBussiness.setPlaceName(accountCBussiness.getPlaceName());
														vehicleBussiness.setPlaceNo(accountCBussiness.getPlaceNo());

														vehicleBussiness.setCreateTime(new Date());
														vehicleBussiness.setMemo("记帐卡无卡换卡");

														vehicleBussinessDao.save(vehicleBussiness);
														String obuSeq = "";
														Date obuIssueTime = null;
														Date obuExpireTime = null;
														//车卡标签绑定
														if (carObuCardInfoDao.updateAccountID(accountCInfo.getId(), oldAccountCInfo.getId()) == 1) {
															//清算接口(原清算数据，没用了)
													/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
													userInfoBaseList.setNetNo("4401");
													//userInfoBaseList.setIssuerId("");发行方唯一标识
													//userInfoBaseList.setAgent();发行代理单位编码
													userInfoBaseList.setCardType(2);
													userInfoBaseListDao.save(userInfoBaseList, newAccountCInfo, null);*/

															//TODO
															//写给铭鸿的清算数据：用户状态信息
															// 旧卡注销
															cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCBussiness.getOldCardNo(),
																	"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
																	null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡领取新卡后将旧卡注销");

															//写给铭鸿的清算数据：用户状态信息
															//新卡发行
															cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCBussiness.getCardNo(),
																	"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
																	null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡领取新卡");
														}

													}
												}
												//旧卡注销
												//写给铭鸿的清算数据：卡片状态信息
												cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
														CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

												//新卡发行
												//写给铭鸿的清算数据：卡片状态信息
												cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.nomal.getIndex()),
														CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);


												//记帐卡换卡需要注销旧卡
												blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, oldAccountCInfo.getCardNo(), new Date()
														, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
														cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
														new Date());


												//发行成功后，更新营运接口调用登记记录的客服状态
												interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
												if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
													interfaceRecord.setCsmsState("1");
													interfaceRecordDao.update(interfaceRecord);
												}

												return dealFalg;
											} else {
												cardBusinessInfo.setRemark("009104096：取记帐卡子账户信息错误");
												message = "009104096：取记帐卡子账户信息错误";
												dealFalg = false;
												return dealFalg;
											}
										}
									} else {
										cardBusinessInfo.setRemark("009104095：取记帐卡申请信息错误");
										message = "009104095：取记帐卡申请信息错误";
										dealFalg = false;
										return dealFalg;
									}
								} else {
									cardBusinessInfo.setRemark("009104097：取客户信息错误");
									message = "009104097：取客户信息错误";
									dealFalg = false;
									return dealFalg;
								}
							}
						} else {
							cardBusinessInfo.setRemark("009104087：新粤通卡卡号已经在记帐卡表中存在");
							message = "009104087：新粤通卡卡号已经在记帐卡表中存在";
							dealFalg = false;
							return dealFalg;
						}

					}
				} else {
					//
					cardBusinessInfo.setRemark("009104072：旧粤通卡为挂失卡");
					message = "009104072：旧粤通卡为挂失卡";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				// 没有开户申请
				cardBusinessInfo.setRemark("009104088：旧粤通卡为非系统卡");
				message = "009104088：旧粤通卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果不是本代理点
			cardBusinessInfo.setRemark("009104091：旧粤通卡不为本行联名卡");
			return dealFalg;
		}
	}

	/***
	 * 换卡确认-06（信用卡卡号不变，粤通卡卡号变）
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 * @param cardBusinessType
	 */
	public boolean changeCardConfirm(boolean cardNocheck,
	                                 CardBusinessInfo cardBusinessInfo,
	                                 ServiceFlowRecord serviceFlowRecord, String bankCode,
	                                 String message, boolean dealFalg, String cardBusinessType) {
		if (cardNocheck) {
			//是否需要校验销户后重启
			boolean restartCheckFlag = false;
			// 判断是否存在换卡申请记录，粤通卡号+信用卡号+粤通卡旧卡号
			List<CardBusinessInfo> cLists = findBusinessInfo(cardBusinessType,
					cardBusinessInfo.getUTCardNo(), cardBusinessInfo.getCreditCardNo(), cardBusinessInfo.getOldUTCardNo(), null, "05", restartCheckFlag);
			if (cLists != null && !cLists.isEmpty()) {
				// 换卡确认
				// ①修改新卡信息（记帐卡信息表）注：记帐卡状态为0：正常
				AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
				if (accountCInfo != null) {
					if (accountCInfo.getState().equals("0")) {
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
						if (accountCApply == null || !accountCInfo.getCustomerId().equals(accountCApply.getCustomerId())) {
							cardBusinessInfo.setRemark("009105089：粤通卡号与信用卡号不匹配");
							message = "009105089：粤通卡号与信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						}
						SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
						if (subAccountInfo == null) {
							cardBusinessInfo.setRemark("009105090：取子账户信息错误");
							message = "009105089：取子账户信息错误";
							dealFalg = false;
							return dealFalg;
						}
						if (!subAccountInfo.getSubAccountNo().equals(accountCApply.getSubAccountNo())) {
							cardBusinessInfo.setRemark("009105089：粤通卡号与信用卡号不匹配");
							message = "009105089：粤通卡号与信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						}
						//更新新卡的的发行时间，涉及管理费
						accountCInfo.setIssueTime(new Date());//发行时间
						accountCInfoDao.update(accountCInfo);

						return dealFalg;


								/*
								InterfaceRecord interfaceRecord = null;
								Map<String, Object> map = inventoryServiceForAgent.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord,"issue",
										null,null,"","1","",null,accountCInfo.getRealCost(),"35");
								boolean result = (Boolean) map.get("result");
								if (!result) {
									return map.get("message").toString();
								}
								//设置有效起始时间与结束时间
								Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
								if(m!=null){
									accountCInfo.setStartDate((Date)m.get("startDate"));
									accountCInfo.setEndDate((Date)m.get("endDate"));
								}



								Customer customer = customerDao.findById(accountCInfo.getCustomerId());
								// 先记录换卡的历史记录
								AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
								Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
								accountCInfoHis.setId(accountCInfoHisId);
								accountCInfoHis.setGenReason("2");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
								accountCInfo.setHisSeqId(accountCInfoHis.getId());
								accountCInfoHisDao.save(accountCInfo,accountCInfoHis);

								accountCInfo.setState("0");
								accountCInfo.setIssueTime(new Date());//发行时间
//									accountCInfo.setStartDate(new Date());//启用时间
								accountCInfoDao.update(accountCInfo);

								// ②增加客服流水信息（客服流水表）注：补领新卡客服流水(小类为7)
								BailAccountInfo bailAccountInfo = bailAccountInfoDao
										.findByCustomerID(customer.getId());
								MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());

								//ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
								Long serviceFlowRecordId = sequenceUtil
										.getSequenceLong("SEQ_CSMSServiceFlowRecord_NO");
								serviceFlowRecord.setId(serviceFlowRecordId);
								serviceFlowRecord.setClientID(accountCInfo.getCustomerId());
								serviceFlowRecord.setCardTagNO(cardBusinessInfo.getUTCardNo());
								serviceFlowRecord.setOldCardTagNO(cardBusinessInfo.getOldUTCardNo());
								serviceFlowRecord.setServiceFlowNO(serviceFlowRecordId.toString());// 流水号生成规则待定

								serviceFlowRecord.setServicePTypeCode(2);// 客服类型大类编码2
								serviceFlowRecord.setServiceTypeCode(7);// 记帐卡小类7补领新卡
								serviceFlowRecord.setBeforeState("5"); // **记帐卡原来状态为5换卡申请
								serviceFlowRecord.setCurrState("0");// **现在状态为0正常

								serviceFlowRecord = getServiceFlowRecord(mainAccountInfo, bailAccountInfo,
										serviceFlowRecord);

								//serviceFlowRecord.setOperID(cardBusinessInfo.getImportOper());
								//serviceFlowRecord.setPlaceID(cardBusinessInfo.getImportPlace());
								serviceFlowRecord.setCreateTime(new Date());
								serviceFlowRecord.setIsNeedBlacklist("0");// 不下发黑名单
								serviceFlowRecord.setIsDoFlag("0");// 未处理

								serviceFlowRecordDao.save(serviceFlowRecord);

								//客服流水号，新加的字段
								cardBusinessInfo.setServiceFlowNO(serviceFlowRecord.getServiceFlowNO());

								//清算系统，记帐卡状态信息
								accountCService.saveACinfo(0, accountCInfo, "4");
								*/


					} else {
						// 没做换卡申请
						cardBusinessInfo.setRemark("009105087：新卡状态非正常");
						message = "009105087：新卡状态非正常";
						dealFalg = false;
						return dealFalg;
					}

				} else {
					// 没开户申请
					cardBusinessInfo.setRemark("009105086：新卡不是系统卡");
					message = "009105086：新卡不是系统卡";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				// 没开户申请记录
				cardBusinessInfo.setRemark("009105088：不存在换卡申请记录");
				message = "009105088：不存在换卡申请记录";
				dealFalg = false;
				return dealFalg;
			}


		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009105085：该卡不是本行联名卡");
			return dealFalg;
		}
	}


	/***
	 * 补领申请(信用卡号和粤通卡卡号都变)，新信用卡必须是全新的
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean replaceCardApply(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		AccountCInfo oldAccountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getOldUTCardNo());// 根据旧粤通卡找记帐卡信息
		if (cardNocheck) {
			//查询该银行编号对应的银行信息
			TbBankdetail tbBankdetail = new TbBankdetail();
			tbBankdetail.setObaNo(Long.parseLong(bankCode));
			List<TbBankdetail> bankList = tbBankdetailDao.listTbBankdetail(tbBankdetail);
			if (bankList == null || bankList.isEmpty()) {
				cardBusinessInfo.setRemark("009106053：获取银行名称错误");
				message = "009106053：获取银行名称错误";
				dealFalg = false;
				return dealFalg;
			} else {
				tbBankdetail = bankList.get(0);
			}
			if (oldAccountCInfo != null) {
				//旧卡的状态为挂失，且非挂失黑名单
				if (oldAccountCInfo.getState().equals("1")) {
					SubAccountInfo oldSubAccountInfo = null;
					AccountCApply oldAccountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getOldCreditCardNo());
					if (oldAccountCApply == null || !oldAccountCInfo.getCustomerId().equals(oldAccountCApply.getCustomerId())) {
						cardBusinessInfo.setRemark("009106050：旧粤通卡卡号与旧信用卡号不匹配");
						message = "009106050：旧粤通卡卡号与旧信用卡号不匹配";
						dealFalg = false;
						return dealFalg;
					} else {
						oldSubAccountInfo = subAccountInfoDao.findById(oldAccountCInfo.getAccountId());
						if (oldSubAccountInfo == null || !oldSubAccountInfo.getApplyID().equals(oldAccountCApply.getId())) {
							cardBusinessInfo.setRemark("009106050：旧粤通卡卡号与旧信用卡号不匹配");
							message = "009106050：旧粤通卡卡号与旧信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						} else {
							//旧粤通卡是非挂失黑名单
							List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getOldUTCardNo());
							if (blackListTempList != null && !blackListTempList.isEmpty()) {
								for (BlackListTemp item : blackListTempList) {
									if (item.getStatus() != 2) {
										dealFalg = false;
										break;
									}
								}
							}
							if (!dealFalg) {
								cardBusinessInfo.setRemark("009106048：旧粤通卡卡号是是黑名单（非挂失）");
								message = "009106008：旧粤通卡卡号是是黑名单（非挂失）";
								dealFalg = false;
								return dealFalg;

							} else {
								//新粤通卡不允许存在记账卡信息表
								AccountCInfo newAccountC = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
								if (newAccountC == null) {

									InterfaceRecord interfaceRecord = null;
									Map<String, Object> map = inventoryServiceForAgent.omsInterface(cardBusinessInfo.getUTCardNo(), "1", interfaceRecord, "issue",
											serviceFlowRecord.getPlaceID(), serviceFlowRecord.getOperID(), serviceFlowRecord.getOperName(), "1", serviceFlowRecord.getOperName(), null, oldAccountCInfo.getRealCost(), "35");
									boolean result = (Boolean) map.get("result");
									if (!result) {
										cardBusinessInfo.setRemark("009106049：粤通卡卡号在记帐卡初始化表中不存在");
										message = "009106049：粤通卡卡号在记帐卡初始化表中不存在";
										dealFalg = false;
										return dealFalg;
									} else {
//												Customer customer = customerDao.findById(oldAccountCInfo.getCustomerId());

										// ①增加客户资料信息（客户信息表）【存在客户信息则不用添加】(唯一标识=客户唯一性的5要素：证件类型、证件号码、客户名称、联系人、联系电话一致则为同一客户。) 7，9，10
//												Customer newCustomer = new Customer();
//												newCustomer.setOrgan(cardBusinessInfo.getOrgan());					//客户名称
//												newCustomer.setIdType(cardBusinessInfo.getIdType());				//证件类型
//												newCustomer.setIdCode(cardBusinessInfo.getIdCode());				// 证件号码
//												newCustomer.setLinkMan(cardBusinessInfo.getLinkMan());				//联系人
//												newCustomer.setTel(cardBusinessInfo.getLinkTel());					//联系人电话
//
//												Customer customer = customerDao.find(newCustomer);
										Customer customer = customerDao.findById(oldAccountCInfo.getCustomerId());


										if (customer == null) {
											cardBusinessInfo.setRemark("009106052：取记帐卡客户信息不存在");
											message = "009106052：取记帐卡客户信息不存在";
											dealFalg = false;
											return dealFalg;
										} else {
											AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
											if (accountCApply != null) {
												cardBusinessInfo.setRemark("009106018：新信用卡卡号已经在记帐卡用户表中存在");
												message = "009106018：新信用卡卡号已经在记帐卡用户表中存在";
												dealFalg = false;
												return dealFalg;
											}


											SubAccountInfo subAccountInfo = new SubAccountInfo();


											// ②增加银行申请信息（记帐卡申请表）
											accountCApply = new AccountCApply();
											Long accountCApplyId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapply_NO");
											accountCApply.setId(accountCApplyId);
											accountCApply.setCustomerId(oldAccountCInfo.getCustomerId());// 对应客户id
											accountCApply.setAccountType("2");// 0、对公1、储蓄2、信用卡3、跨行划扣4、统一划账5、其他
											accountCApply.setLinkman(cardBusinessInfo.getLinkMan());
											accountCApply.setTel(cardBusinessInfo.getLinkTel());
											accountCApply.setValidity(DateUtil.getValidity(new Date()));// 子账户有效期，最大不超过5年
											accountCApply.setBank(tbBankdetail.getBankName());// 开户银行
//												accountCApply.setBankSpan("");// 跨行划扣银行
											accountCApply.setBankAccount(cardBusinessInfo.getCreditCardNo());// 开户银行帐号(信用卡号？)
//												accountCApply.setBankName();// 开户银行名称
											accountCApply.setObaNo(bankCode);
											accountCApply.setAccName(cardBusinessInfo.getOrgan());// 开户帐户户名
											accountCApply.setInvoiceprn("2");// 发票打印方式1、按单卡2、按帐号
											// accountCApply.setReqcount();
											// accountCApply.setResidueCount();

											//没有车辆就没有单卡保证金
											//accountCApply.setBail(new BigDecimal("0"));// 单卡保证金

											accountCApply.setVirenum(1);    //转账次数限制
											accountCApply.setVirType("1");// 转帐类型
											accountCApply.setMaxacr(new BigDecimal("0"));// 通行费转帐限额
											// accountCApply.setBankClearNo("");//开户银行清算行号
											// accountCApply.setBankAcceptNo(bankAcceptNo);//开户行集中受理行号
											accountCApply.setAppState("6");// 审核通过
											accountCApply.setOperId(cardBusinessInfo.getImportOper());
											accountCApply.setPlaceId(cardBusinessInfo.getImportPlace());
											accountCApply.setOperNo(serviceFlowRecord.getOperNo());
											accountCApply.setOperName(serviceFlowRecord.getOperName());
											accountCApply.setPlaceNo(serviceFlowRecord.getPlaceNo());
											accountCApply.setPlaceName(serviceFlowRecord.getPlaceName());
											accountCApply.setDebitCardType(1);

											// ①增加子账户信息（子账户信息表）
											MainAccountInfo mainAccount = mainAccountInfoDao
													.findByMainId(oldAccountCInfo.getCustomerId());
											Long subAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSSubAccountInfo_NO");
											subAccountInfo.setId(subAccountInfoId);
											subAccountInfo.setMainId(mainAccount.getId());// 总账户id

											SubAccountInfo sub = subAccountInfoDao.findLastDateSub(accountCApply.getCustomerId());
											subAccountInfo
													.setSubAccountNo(customer.getUserNo() + "2" + StringUtil.getSerailNumber(sub));// 账户号
											// :客户号+类型+序号（其中序号为记帐卡账户号的后3位）
											subAccountInfo.setSubAccountType("2");// 记帐卡子账户
											subAccountInfo.setApplyID(accountCApply.getId());// 记帐卡申请id
											subAccountInfo.setOperId(cardBusinessInfo.getImportOper());
											subAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());
											subAccountInfo.setOperNo(serviceFlowRecord.getOperNo());
											subAccountInfo.setOperName(serviceFlowRecord.getOperName());
											subAccountInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
											subAccountInfo.setPlaceName(serviceFlowRecord.getPlaceName());
											subAccountInfo.setOperTime(new Date());
//													subAccountInfo.setAgentsMay(1L);// 代理点

											subAccountInfoDao.save(subAccountInfo);

											accountCApply.setSubAccountNo(subAccountInfo.getSubAccountNo());
											accountCApplyDao.saveAccountCApply(accountCApply);

											//保存服务方式登记表信息
											BillGet billGet = new BillGet();
											BigDecimal SEQ_CSMS_bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
											billGet.setId(Long.valueOf(SEQ_CSMS_bill_get_NO.toString()));
											billGet.setMainId(customer.getId());
											billGet.setCardType("2");//卡类型设为“2”，表示记帐卡
											billGet.setCardAccountID(subAccountInfo.getId());
											billGet.setCardBankNo(accountCApply.getBankAccount());
											billGet.setSerItem("4");            //4：月结通知
											billGet.setSerType("2");            //2：长期服务
											//服务类型，服务开始时间，服务结束时间暂无

											billGet.setOperTime(new Date());
											billGet.setOperId(accountCApply.getOperId());//操作员取申请操作员
											billGet.setPlaceId(accountCApply.getPlaceId());//网点取申请网点
											billGet.setOperName(accountCApply.getOperName());
											billGet.setOperNo(accountCApply.getOperNo());
											billGet.setPlaceName(accountCApply.getPlaceName());
											billGet.setPlaceNo(accountCApply.getPlaceNo());

											billGetDao.save(billGet);


											// ③增加记帐卡信息（记帐卡信息表）注：记帐卡状态为0 正常
											AccountCInfo accountCInfo = new AccountCInfo();
											Long accountCInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
											accountCInfo.setId(accountCInfoId);
											accountCInfo.setCardNo(cardBusinessInfo.getUTCardNo());// **卡号(粤通卡卡号？)
											accountCInfo.setCustomerId(customer.getId());
											accountCInfo.setAccountId(subAccountInfo.getId());// 对应账户id

											accountCInfo.setState("0");// 0：正常
											// 1：挂失2：注销3：停用4：开户申请5：换卡申请6：补领申请7：非过户补领申请8：过户申请
											accountCInfo.setCost(new BigDecimal("0"));// 成本费
											accountCInfo.setRealCost(new BigDecimal("0"));// 实收成本费
											accountCInfo.setIssueTime(new Date());//发行时间
											//设置有效起始时间与结束时间
											Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
											if (m != null) {
												accountCInfo.setStartDate((Date) m.get("startDate"));
												accountCInfo.setEndDate((Date) m.get("endDate"));
											}
											Calendar cal = Calendar.getInstance();
											if (accountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
												//无偿，沿用原卡的发行时间作为维保起始时间
												cal.setTime(oldAccountCInfo.getIssueTime());
											} else {

											}

											//获取营运参数：维保周期（key=Maintenance time）
											Map<String, Object> paramMap = omsParamInterfaceService.findOmsParam("Maintenance time");
											logger.info(paramMap);
											if (paramMap != null && "成功".equals((String) paramMap.get("message"))) {
												cal.add(Calendar.YEAR, Integer.parseInt((String) paramMap.get("value")));
											} else if (paramMap != null && !"成功".equals((String) paramMap.get("message"))) {
												logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
												throw new ApplicationException();
											} else {
												logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
												throw new ApplicationException();
												//cal.add(Calendar.YEAR, 10);// 十年有效期
											}

											accountCInfo.setMaintainTime(cal.getTime());
											accountCInfo.setIssueFlag(oldAccountCInfo.getIssueFlag());// 销售方式
											accountCInfo.setBind(oldAccountCInfo.getBind());// 绑定标志


											accountCInfo.setIssueOperId(cardBusinessInfo.getImportOper());
											accountCInfo.setIssuePlaceId(cardBusinessInfo.getImportPlace());
											accountCInfo.setOperNo(serviceFlowRecord.getOperNo());
											accountCInfo.setOperName(serviceFlowRecord.getOperName());
											accountCInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
											accountCInfo.setPlaceName(serviceFlowRecord.getPlaceName());
											accountCInfo.setS_con_pwd_flag(oldAccountCInfo.getS_con_pwd_flag());//是否需要交易密码？
											accountCInfo.setTradingPwd(oldAccountCInfo.getTradingPwd());//交易密码，MD加密
//													accountCInfo.setAgentsMay(1L);// 代理点
											accountCInfo.setLinkMan(cardBusinessInfo.getLinkMan());
											accountCInfo.setLinkTel(cardBusinessInfo.getLinkTel());
											accountCInfo.setLinkMobile(cardBusinessInfo.getLinkMobile());
											accountCInfo.setLinkAddr(cardBusinessInfo.getLinkAddr());
											accountCInfo.setLinkZipCode(cardBusinessInfo.getLinkZipCode());

											accountCInfoDao.save(accountCInfo);

											// ④修改旧记帐卡状态（记帐卡信息表）注：记帐卡状态为2：注销

											// 先记录注销的历史记录
											AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
											Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
											accountCInfoHis.setId(accountCInfoHisId);
											accountCInfoHis.setGenReason("3");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
											oldAccountCInfo.setHisSeqId(accountCInfoHis.getId());
											accountCInfoHisDao.save(oldAccountCInfo, accountCInfoHis);

											oldAccountCInfo.setState("2");// 注销

											accountCInfoDao.update(oldAccountCInfo);


											//旧卡写注销登记表
											Cancel oldCancel = new Cancel();
											Long oldCancelId = sequenceUtil.getSequenceLong("SEQ_CSMS_CANCEL_NO");
											oldCancel.setId(oldCancelId);
											oldCancel.setCustomerId(oldAccountCInfo.getCustomerId());
											oldCancel.setFlag("2");//
											oldCancel.setCode(oldAccountCInfo.getCardNo());
											oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
											oldCancel.setOperId(serviceFlowRecord.getOperID());            //操作员ID
											oldCancel.setPlaceId(serviceFlowRecord.getPlaceID());                        //网点ID
											oldCancel.setCancelTime(new Date());
											oldCancel.setBankNo(accountCApply.getBankAccount());                        //开户银行账号
											oldCancel.setBankMember(accountCApply.getBankName());                        //银行客户名称
											oldCancel.setBankOpenBranches(accountCApply.getBank());                        //银行开户网点
											oldCancel.setOperNo(serviceFlowRecord.getOperNo());                            //操作员编号
											oldCancel.setOperName(serviceFlowRecord.getOperName());                        //操作员名称
											oldCancel.setPlaceNo(serviceFlowRecord.getPlaceNo());                        //网点编号
											oldCancel.setPlaceName(serviceFlowRecord.getPlaceName());                    //网点名称
											oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
											oldCancel.setSource("1");//1：代理

											cancelDao.save(oldCancel);


											//清算系统，记帐卡状态信息 -注销
//									accountCService.saveACinfo(2, oldAccountCInfo, "4");		//旧卡
//									accountCService.saveACinfo(0, accountCInfo, "4");			//新卡


											//保存联名卡黑名单信息
											CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
											Long cardBlacklistInfoId = sequenceUtil
													.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
											cardBlacklistInfo.setId(cardBlacklistInfoId);
											cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getOldUTCardNo());// 旧卡
											cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getOldCreditCardNo());
											cardBlacklistInfo.setUserNo(customer.getUserNo());
											cardBlacklistInfo.setProduceTime(new Date());
											cardBlacklistInfo.setProduceReason("2");// 产生原因2：销卡
											// cardBlacklistInfo.setRemark("");
											cardBlacklistDao.save(cardBlacklistInfo);


											//记帐卡挂失补领需要注销旧卡（下发黑名单给铭鸿）
											blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, oldAccountCInfo.getCardNo(), new Date()
													, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
													cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
													new Date());

											// 记帐卡业务记录-补领
											AccountCBussiness accountCBussiness = new AccountCBussiness();
											BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
											accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
											accountCBussiness.setUserId(customer.getId());
											accountCBussiness.setAccountId(subAccountInfo.getId());
											accountCBussiness.setOldAccountId(oldSubAccountInfo.getId());
											accountCBussiness.setState("5");        //补领-5
											accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
											accountCBussiness.setTradeTime(new Date());
											accountCBussiness.setOperId(accountCApply.getOperId());
											accountCBussiness.setPlaceId(accountCApply.getPlaceId());
											accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
											accountCBussiness.setOldCardNo(cardBusinessInfo.getOldUTCardNo());
											//新增的字段
											accountCBussiness.setOperName(accountCApply.getOperName());
											accountCBussiness.setOperNo(accountCApply.getOperNo());
											accountCBussiness.setPlaceName(accountCApply.getPlaceName());
											accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
											accountCBussiness.setBusinessId(accountCApply.getHisseqId());
											accountCBussinessDao.save(accountCBussiness);

											//生成客服流水
											ServiceWater serviceWater = new ServiceWater();
											Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

											serviceWater.setId(serviceWater_id);

											serviceWater.setCustomerId(customer.getId());
											serviceWater.setUserNo(customer.getUserNo());
											serviceWater.setUserName(customer.getOrgan());
											serviceWater.setSerType("206");//记帐卡补领
											serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
											serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
											serviceWater.setAccountCBussinessId(accountCBussiness.getId());
											serviceWater.setOperId(accountCBussiness.getOperId());
											serviceWater.setOperName(accountCBussiness.getOperName());
											serviceWater.setOperNo(accountCBussiness.getOperNo());
											serviceWater.setPlaceId(accountCBussiness.getPlaceId());
											serviceWater.setPlaceName(accountCBussiness.getPlaceName());
											serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
											serviceWater.setRemark("代理客服系统：联名记帐卡补领申请");
											serviceWater.setOperTime(new Date());
											serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
											serviceWater.setCardNo(cardBusinessInfo.getOldUTCardNo());

											serviceWaterDao.save(serviceWater);
											//客服流水号关联业务信息，新加的字段
											cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

											//资金转移确认表
											DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getOldCardNo());

											DbasCardFlow dbasCard = new DbasCardFlow();
											dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
											dbasCard.setNewCardNo(accountCBussiness.getCardNo());
											dbasCard.setOldCardNo(accountCBussiness.getOldCardNo());
											if (dbasCardFlow != null)
												dbasCard.setCardNo(dbasCardFlow.getCardNo());
											else
												dbasCard.setCardNo(accountCBussiness.getOldCardNo());
											dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
											dbasCard.setSerType(DBACardFlowSerTypeEnum.lossReplaceCard.getValue());
											dbasCard.setApplyTime(new Date());
											dbasCard.setServiceId(accountCBussiness.getId());
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


											//旧记帐卡的车卡标签绑定记录
											CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(oldAccountCInfo.getId());
											if (carObuCardInfo != null) {
												//车辆
												VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
												VehicleBussiness vehicleBussiness = null;
												String obuSeq = "";
												Date obuIssueTime = null;
												Date obuExpireTime = null;
												if (vehicle != null && carObuCardInfoDao.updateAccountID(accountCInfo.getId(), oldAccountCInfo.getId()) == 1) {//车卡已经绑定

													//新增车辆业务记录表CSMS_Vehicle_Bussiness
													vehicleBussiness = new VehicleBussiness();
													BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
													vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
													vehicleBussiness.setCustomerID(vehicle.getCustomerID());
													vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
													vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
													vehicleBussiness.setCardNo(accountCInfo.getCardNo());//新卡号
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

													vehicleBussinessDao.save(vehicleBussiness);


													//写给铭鸿的清算数据：用户状态信息
													// 旧卡注销
													cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCBussiness.getOldCardNo(),
															"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
															null, obuSeq, obuIssueTime, obuExpireTime, "代理客服系统：记帐卡挂失补领后将旧卡注销");

													//写给铭鸿的清算数据：用户状态信息
													//新卡发行
													cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCBussiness.getCardNo(),
															"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
															null, obuSeq, obuIssueTime, obuExpireTime, "代理客服系统：记帐卡挂失后补领新卡");

												}
											}


											//保存记帐卡二次发行数据（客服->清算）
											cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);

											//保存记帐卡状态信息、用户状态信息（客服->清算）
											String userType = "";
											if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
												userType = "0";//个人
											} else {
												userType = "1";//单位
											}

											String obuSeq = "";


											//旧卡注销
											//写给铭鸿的清算数据：卡片状态信息
											cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
													CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

											//新卡发行
											//写给铭鸿的清算数据：卡片状态信息
											cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.nomal.getIndex()),
													CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);

											//发行成功后，更新营运接口调用登记记录的客服状态
											interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
											if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
												interfaceRecord.setCsmsState("1");
												interfaceRecordDao.update(interfaceRecord);
											}
											return dealFalg;
										}

									}

								} else {
									//新粤通卡已被发行过
									cardBusinessInfo.setRemark("009106013：新粤通卡卡号已经在记帐卡表中存在");
									message = "009106013：新粤通卡卡号已经在记帐卡表中存在";
									dealFalg = false;
									return dealFalg;
								}
							}
						}

					}


				} else {
					cardBusinessInfo.setRemark("009106009：旧粤通卡卡号不是挂失状态");
					message = "009106009：旧粤通卡卡号不是挂失状态";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				//
				cardBusinessInfo.setRemark("009106004：旧卡为非系统卡");
				message = "009106004：旧卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009106003：旧卡不是本行联名卡");
			dealFalg = false;
			return dealFalg;
		}
	}

	/***
	 * 补领确认
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 * @param cardBusinessType
	 */
	public boolean replaceCardConfirm(boolean cardNocheck,
	                                  CardBusinessInfo cardBusinessInfo,
	                                  ServiceFlowRecord serviceFlowRecord, String bankCode,
	                                  String message, boolean dealFalg, String cardBusinessType) {
		// ①修改新记帐卡状态（记帐卡信息表）注：记帐卡状态为0：正常

		if (cardNocheck) {
			//是否需要校验销户后重启
			boolean restartCheckFlag = false;
			//判断是否存在补领申请记录，粤通卡号+信用卡号+粤通卡旧卡号+旧信用卡号
			List<CardBusinessInfo> cLists = findBusinessInfo(cardBusinessType,
					cardBusinessInfo.getUTCardNo(), cardBusinessInfo.getCreditCardNo(), cardBusinessInfo.getOldUTCardNo(), cardBusinessInfo.getOldCreditCardNo(), "07", restartCheckFlag);
			if (cLists != null && !cLists.isEmpty()) {
				AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
				if (accountCInfo != null) {
					// 记帐卡补领申请状态：0 正常
					if (accountCInfo.getState().equals("0")) {
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
						//判断记账卡卡号与银行卡号是否匹配
						if (accountCApply == null || !accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
							cardBusinessInfo.setRemark("009107014：粤通卡卡号与信用卡号不匹配");
							message = "009107014：粤通卡卡号与信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						} else {
							SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
							if (accountCInfo == null || !subAccountInfo.getApplyID().equals(accountCApply.getId())) {
								cardBusinessInfo.setRemark("009107014：粤通卡卡号与信用卡号不匹配");
								message = "009107014：粤通卡卡号与信用卡号不匹配";
								dealFalg = false;
								return dealFalg;
							}
						}
						//对旧数据的新卡止付黑名单进行解除
						List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getUTCardNo());
						if (blackListTempList != null && !blackListTempList.isEmpty()) {
							for (BlackListTemp item : blackListTempList) {
								if (item.getStatus() == 4) {
									// 记帐卡没有黑名单的状态
									List<CardBlacklistInfo> cardBlacklistInfos = cardBlacklistDao.findByUTCardNoType(cardBusinessInfo.getUTCardNo(), "5");//
									if (cardBlacklistInfos != null && !cardBlacklistInfos.isEmpty()) {
										for (CardBlacklistInfo c : cardBlacklistInfos) {
											//解除记帐卡黑名单信息表的止付黑名单
											cardBlacklistDao.delete(c);
										}
									}
									//代理网点解除止付黑名单（铭鸿）
									blackListService.saveRelieveStopPayCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
											, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
											cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
											new Date());
								}
							}

						}
									/*by sxw 20170603
									InterfaceRecord interfaceRecord = null;
									Map<String, Object> map = inventoryServiceForAgent.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord,"issue",
											null,null,"","1","",null,accountCInfo.getRealCost(),"37");
									boolean result = (Boolean) map.get("result");
									if (!result) {
										return map.get("message").toString();
									}
									//设置有效起始时间与结束时间
									Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
									if(m!=null){
										accountCInfo.setStartDate((Date)m.get("startDate"));
										accountCInfo.setEndDate((Date)m.get("endDate"));
									}

									Customer customer = customerDao.findById(accountCInfo.getCustomerId());
									// 先记录补领的历史记录
									AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
									Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
									accountCInfoHis.setId(accountCInfoHisId);
									accountCInfoHis.setGenReason("1");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
									accountCInfo.setHisSeqId(accountCInfoHis.getId());
									accountCInfoHisDao.save(accountCInfo,accountCInfoHis);

									accountCInfo.setState("0");
									accountCInfo.setIssueTime(new Date());//发行时间
//									accountCInfo.setStartDate(new Date());//启用时间
									accountCInfoDao.update(accountCInfo);

									// ②增加客服流水（客服流水表）注：补领客服流水（补领：5）
									BailAccountInfo bailAccountInfo = bailAccountInfoDao
											.findByCustomerID(customer.getId());
									MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());

									//ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
									Long serviceFlowRecordId = sequenceUtil
											.getSequenceLong("SEQ_CSMSServiceFlowRecord_NO");
									serviceFlowRecord.setId(serviceFlowRecordId);
									serviceFlowRecord.setClientID(customer.getId());
									serviceFlowRecord.setCardTagNO(cardBusinessInfo.getUTCardNo());
									serviceFlowRecord.setOldCardTagNO(cardBusinessInfo.getOldUTCardNo());
									serviceFlowRecord.setServiceFlowNO(serviceFlowRecordId.toString());// 流水号生成规则待定

									serviceFlowRecord.setServicePTypeCode(2);// 客服类型大类编码2
									serviceFlowRecord.setServiceTypeCode(5);// 记帐卡小类1记帐卡发行
									serviceFlowRecord.setBeforeState("6"); // **记帐卡原来状态为6补领申请
									serviceFlowRecord.setCurrState("0");// **现在状态为0正常

									serviceFlowRecord = getServiceFlowRecord(mainAccountInfo, bailAccountInfo,
											serviceFlowRecord);

									//serviceFlowRecord.setOperID(cardBusinessInfo.getImportOper());
									//serviceFlowRecord.setPlaceID(cardBusinessInfo.getImportPlace());
									serviceFlowRecord.setCreateTime(new Date());
									serviceFlowRecord.setIsNeedBlacklist("0");// 不下发黑名单
									serviceFlowRecord.setIsDoFlag("0");// 未处理

									serviceFlowRecordDao.save(serviceFlowRecord);

									//客服流水号，新加的字段
									cardBusinessInfo.setServiceFlowNO(serviceFlowRecord.getServiceFlowNO());


									//清算系统，记帐卡状态信息（无卡注销）
									accountCService.saveACinfo(0, accountCInfo, "4");
									*/

						//更新发行时间
						accountCInfo.setIssueTime(new Date());//发行时间
						accountCInfoDao.update(accountCInfo);

						return dealFalg;

					} else {
						cardBusinessInfo.setRemark("009107013：新卡状态非正常");
						message = "009107013：新卡状态非正常";
						dealFalg = false;
						return dealFalg;
					}

				} else {
					cardBusinessInfo.setRemark("009107012：新卡非系统卡");
					message = "009107012：新卡非系统卡";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				cardBusinessInfo.setRemark("009107002：不存在补领申请记录");
				message = "009107002：不存在补领申请记录";
				dealFalg = false;
				return dealFalg;
			}


		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009107011：新卡不是本行联名卡");
			return dealFalg;
		}
	}


	/***
	 * 销卡-09
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean cancelCard(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// ①修改卡片状态（记帐卡信息表）注：记帐卡状态为2：注销
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());

		if (cardNocheck) {
			//判断记帐卡信息是否存在
			if (accountCInfo != null) {
				//判断记帐卡状态是否为挂失状态，非挂失状态才能做注销
				if (!accountCInfo.getState().equals("1")) {

					//判断是否存在黑名单记录，存在黑名单记录不允许办理销卡
					List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getUTCardNo());
					if (blackListTempList == null || blackListTempList.isEmpty()) {
						Customer customer = customerDao.findById(accountCInfo.getCustomerId());
						if (customer != null) {
							//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：注销客服流水
							AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
							if (accountCApply != null) {
								//信用卡与记帐卡是否匹配
								if (!accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
									cardBusinessInfo.setRemark("009108028：粤通卡卡号与信用卡号不匹配");
									message = "009108028：粤通卡卡号与信用卡号不匹配";
									dealFalg = false;
									return dealFalg;
								} else {
									SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
									if (subAccountInfo != null) {
										//信用卡与记帐卡是否匹配
										if (subAccountInfo == null || !subAccountInfo.getApplyID().equals(accountCApply.getId())) {
											cardBusinessInfo.setRemark("009108028：粤通卡卡号与信用卡号不匹配");
											message = "009108028：粤通卡卡号与信用卡号不匹配";
											dealFalg = false;
											return dealFalg;
										}

										//校验该银行账户是否存在欠费状态
										//根据银行账号获取最近的转账状态
										String status = iStopPayRelieveApplyService.findMaxAccBankListRecvByBankAccount(cardBusinessInfo.getCreditCardNo());
										if (status != null && !"0".equals(status)) {
											cardBusinessInfo.setRemark("009108009：信用卡欠款未缴清，不能销卡");
											message = "009108009：信用卡欠款未缴清，不能销卡";
											dealFalg = false;
											return dealFalg;
										}

										// 先记录注销的历史记录
										AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
										Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
										accountCInfoHis.setId(accountCInfoHisId);
										accountCInfoHis.setGenReason("3");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
										accountCInfo.setHisSeqId(accountCInfoHis.getId());
										accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

										accountCInfo.setState("2");

										accountCInfoDao.update(accountCInfo);

										//旧卡写注销登记表
										Cancel oldCancel = new Cancel();
										Long oldCancelId = sequenceUtil.getSequenceLong("SEQ_CSMS_CANCEL_NO");
										oldCancel.setId(oldCancelId);
										oldCancel.setCustomerId(accountCInfo.getCustomerId());
										oldCancel.setFlag("2");
										oldCancel.setCode(accountCInfo.getCardNo());
										oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
										oldCancel.setOperId(serviceFlowRecord.getOperID());            //操作员ID
										oldCancel.setPlaceId(serviceFlowRecord.getPlaceID());                        //网点ID
										oldCancel.setCancelTime(new Date());
										oldCancel.setBankNo(accountCApply.getBankAccount());                        //开户银行账号
										oldCancel.setBankMember(accountCApply.getBankName());                        //银行客户名称
										oldCancel.setBankOpenBranches(accountCApply.getBank());                        //银行开户网点
										oldCancel.setOperNo(serviceFlowRecord.getOperNo());                            //操作员编号
										oldCancel.setOperName(serviceFlowRecord.getOperName());                        //操作员名称
										oldCancel.setPlaceNo(serviceFlowRecord.getPlaceNo());                        //网点编号
										oldCancel.setPlaceName(serviceFlowRecord.getPlaceName());                    //网点名称
										oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
										oldCancel.setSource("1");//1：代理
										cancelDao.save(oldCancel);


										// ③增加黑名单（联名卡黑名单信息表）
										CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
										Long cardBlacklistInfoId = sequenceUtil
												.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
										cardBlacklistInfo.setId(cardBlacklistInfoId);
										cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getUTCardNo());
										cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
										cardBlacklistInfo.setUserNo(customer.getUserNo());
										cardBlacklistInfo.setProduceTime(new Date());
										cardBlacklistInfo.setProduceReason("2");// 产生原因2：销卡
										// cardBlacklistInfo.setRemark("");

										cardBlacklistDao.save(cardBlacklistInfo);

										//清算系统，记帐卡状态信息（注销）
//											accountCService.saveACinfo(2, accountCInfo, "4");

										//记帐卡注销
										blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
												, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
												cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
												new Date());


										// 记帐卡业务记录
										AccountCBussiness accountCBussiness = new AccountCBussiness();
										BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
										accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
										accountCBussiness.setUserId(customer.getId());
										accountCBussiness.setAccountId(subAccountInfo.getId());
										accountCBussiness.setState("9");        //无卡终止使用-9
										accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
										accountCBussiness.setTradeTime(new Date());
										accountCBussiness.setOperId(accountCApply.getOperId());
										accountCBussiness.setPlaceId(accountCApply.getPlaceId());
										accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
										accountCBussiness.setOldCardNo(cardBusinessInfo.getUTCardNo());
										//新增的字段
										accountCBussiness.setOperName(accountCApply.getOperName());
										accountCBussiness.setOperNo(accountCApply.getOperNo());
										accountCBussiness.setPlaceName(accountCApply.getPlaceName());
										accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
										accountCBussiness.setBusinessId(accountCApply.getHisseqId());
										accountCBussinessDao.save(accountCBussiness);

										//生成客服流水
										ServiceWater serviceWater = new ServiceWater();
										Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

										serviceWater.setId(serviceWater_id);

										serviceWater.setCustomerId(customer.getId());
										serviceWater.setUserNo(customer.getUserNo());
										serviceWater.setUserName(customer.getOrgan());
										serviceWater.setSerType("203");//203	无卡终止使用
										serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
										serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
										serviceWater.setAccountCBussinessId(accountCBussiness.getId());
										serviceWater.setOperId(accountCBussiness.getOperId());
										serviceWater.setOperName(accountCBussiness.getOperName());
										serviceWater.setOperNo(accountCBussiness.getOperNo());
										serviceWater.setPlaceId(accountCBussiness.getPlaceId());
										serviceWater.setPlaceName(accountCBussiness.getPlaceName());
										serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
										serviceWater.setRemark("代理客服系统：联名记帐卡销卡");
										serviceWater.setOperTime(new Date());
										serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
										serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());

										serviceWaterDao.save(serviceWater);

										//客服流水号关联业务信息，新加的字段
										cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());


										//资金转移确认表（客服->清算）
										DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getCardNo());

										DbasCardFlow dbasCard = new DbasCardFlow();
										dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
										dbasCard.setNewCardNo(accountCBussiness.getCardNo());
										dbasCard.setOldCardNo(accountCBussiness.getCardNo());
										if (dbasCardFlow != null)
											dbasCard.setCardNo(dbasCardFlow.getCardNo());
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

										//解绑车辆信息
										CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
										if (carObuCardInfo != null) {
											carObuCardInfo.setAccountCID(null);
											carObuCardInfoDao.update(carObuCardInfo);

											VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
											if (vehicleInfo != null) {

												String obuSeq = "";
												Date obuIssueTime = null;
												Date obuExpireTime = null;
												//写给铭鸿的清算数据：用户状态信息
												cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(),
														"23", Integer.valueOf(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(),
														null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡无卡注销");
											}
										}

										//保存卡片状态信息（客服--> 清算）
										String userType = "";
										if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
											userType = "0";//个人
										} else {
											userType = "1";//单位
										}
										//写给铭鸿的清算数据：卡片状态信息
										cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
												CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);

										//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171018
										Integer cardStatus = null;
										VehicleInfo vehicleInfo = iVehicleInfoService.findByAccountCNo(accountCInfo
												.getCardNo());
										if (AccountCBussinessTypeEnum.accCardStopNotCard.getValue().equals(accountCBussiness.getState())) {
											// 无卡
											cardStatus = CardStatusEmeu.NOCARD_CANCLE.getCode();
										} else {
											// 有卡
											cardStatus = CardStatusEmeu.HADCARD_CANCLE.getCode();
										}
										// 用户卡信息上传及变更
										realTransferService.accountCInfoTransfer(customer, accountCInfo,
												vehicleInfo, cardStatus, OperationTypeEmeu.UPDATE
														.getCode());

										if (CardStatusEmeu.NOCARD_CANCLE.getCode().intValue() ==
												cardStatus.intValue()) {
											noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new
													Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
													.EN_BLACK.getCode());
										}
										//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171018

										return dealFalg;

									} else {
										cardBusinessInfo.setRemark("009108027：取记帐卡子账户信息错误");
										message = "009108027：取记帐卡子账户信息错误";
										dealFalg = false;
										return dealFalg;
									}
								}

							} else {
								cardBusinessInfo.setRemark("009108026：取记帐卡申请信息错误");
								message = "009108026：取记帐卡申请信息错误";
								dealFalg = false;
								return dealFalg;
							}
						} else {
							cardBusinessInfo.setRemark("009108029：取客户信息错误");
							message = "009108029：取记帐卡申请信息错误";
							dealFalg = false;
							return dealFalg;
						}
					} else {
						String blackTypeString = StringUtil.getBlackTypeString(blackListTempList);
						if (!blackTypeString.equals("")) {
							blackTypeString = "(" + blackTypeString + ")";
						}
						cardBusinessInfo.setRemark("009108024：该卡为黑名单卡" + blackTypeString);
						message = "009108024：该卡为黑名单卡" + blackTypeString;
						dealFalg = false;
						return dealFalg;
					}

				} else {
					cardBusinessInfo.setRemark("009108007：该卡是挂失状态");
					message = "009108007：该卡是挂失状态";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("009108003：该卡为非系统卡");
				message = "009108003：该卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}

		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009108002：该卡不是本行联名卡");
			return dealFalg;
		}
	}

	/**
	 * 止付黑名单下发-10
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean stopPayBlackListIssued(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// ①增加黑名单（联名卡黑名单信息表）
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());

		if (cardNocheck) {
			//粤通卡只要在系统中已经建立对应账户下止付黑名单
			if (accountCInfo != null) {
				if ("2".equals(accountCInfo.getState())) {
					cardBusinessInfo.setRemark("009109091：该粤通卡号已注销");
					message = "009109091：该粤通卡号已注销";
					dealFalg = false;
					return dealFalg;
				}
				Customer customer = customerDao.findById(accountCInfo.getCustomerId());
				Customer customerCredit = null;
				if (customer != null) {
					//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：止付黑名单下发客服流水
					AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
					if (accountCApply != null) {
						//信用卡与记帐卡是否匹配
						if (!accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
							cardBusinessInfo.setRemark("009109090：粤通卡卡号与信用卡号不匹配");
							message = "009109090：粤通卡卡号与信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						} else {
							SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
							if (subAccountInfo != null) {
								if (!subAccountInfo.getApplyID().equals(accountCApply.getId())) {
									cardBusinessInfo.setRemark("009109090：粤通卡卡号与信用卡号不匹配");
									message = "009109090：粤通卡卡号与信用卡号不匹配";
									dealFalg = false;
									return dealFalg;
								}
								CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
								Long cardBlacklistInfoId = sequenceUtil
										.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
								cardBlacklistInfo.setId(cardBlacklistInfoId);
								cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getUTCardNo());
								cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
								cardBlacklistInfo.setUserNo(customer.getUserNo());
								cardBlacklistInfo.setProduceTime(new Date());
								cardBlacklistInfo.setProduceReason("5");// 产生原因5：止付黑名单
								// cardBlacklistInfo.setRemark("");

								cardBlacklistDao.save(cardBlacklistInfo);


								//代理网点下发禁用黑名单-5
								blackListService.saveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), cardBlacklistInfo.getProduceTime()
										, "4", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
										cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
										new Date());

								// 记帐卡业务记录
								AccountCBussiness accountCBussiness = new AccountCBussiness();
								BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
								accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
								accountCBussiness.setUserId(customer.getId());
								accountCBussiness.setAccountId(subAccountInfo.getId());
								accountCBussiness.setState("50");        //止付黑名单下发-50
								accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
								accountCBussiness.setTradeTime(new Date());
								accountCBussiness.setOperId(accountCApply.getOperId());
								accountCBussiness.setPlaceId(accountCApply.getPlaceId());
								accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
								accountCBussiness.setOldCardNo(cardBusinessInfo.getUTCardNo());
								//新增的字段
								accountCBussiness.setOperName(accountCApply.getOperName());
								accountCBussiness.setOperNo(accountCApply.getOperNo());
								accountCBussiness.setPlaceName(accountCApply.getPlaceName());
								accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
								accountCBussiness.setBusinessId(accountCApply.getHisseqId());
								accountCBussinessDao.save(accountCBussiness);

								//生成手工黑名单数据
								Timestamp reqTime = new Timestamp(System.currentTimeMillis());
								ManualBlackListSend blackListSend = new ManualBlackListSend();
								blackListSend.setCardCode(cardBusinessInfo.getUTCardNo());
								blackListSend.setCardType("23");            //储值卡：22 记账卡：23 电子标签：3
								blackListSend.setSrtType("B2");                //B2：下发手工止付黑名单（联名卡）
								blackListSend.setDarkReason("4");            //4：止付卡进入
								blackListSend.setReqTime(reqTime);
								blackListSend.setUpdateTime(reqTime);
								blackListSend.setBoardListNo(Long.parseLong(System.currentTimeMillis() + "1027"));
								manualBlackListSendDao.save(blackListSend);


								//生成客服流水
								ServiceWater serviceWater = new ServiceWater();
								Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

								serviceWater.setId(serviceWater_id);

								serviceWater.setCustomerId(customer.getId());
								serviceWater.setUserNo(customer.getUserNo());
								serviceWater.setUserName(customer.getOrgan());
								serviceWater.setSerType("701");//701	止付黑名单下发
								serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
								serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
								serviceWater.setAccountCBussinessId(accountCBussiness.getId());
								serviceWater.setOperId(accountCBussiness.getOperId());
								serviceWater.setOperName(accountCBussiness.getOperName());
								serviceWater.setOperNo(accountCBussiness.getOperNo());
								serviceWater.setPlaceId(accountCBussiness.getPlaceId());
								serviceWater.setPlaceName(accountCBussiness.getPlaceName());
								serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
								serviceWater.setRemark("代理客服系统：联名记帐卡止付黑名单下发");
								serviceWater.setOperTime(new Date());
								serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
								serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());

								serviceWaterDao.save(serviceWater);

								//客服流水号关联业务信息，新加的字段
								cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());
								return dealFalg;
							} else {
								cardBusinessInfo.setRemark("009109089：取记帐卡子账户信息错误");
								message = "009109089：取记帐卡子账户信息错误";
								dealFalg = false;
								return dealFalg;
							}
						}

					} else {
						cardBusinessInfo.setRemark("009109088：取记帐卡申请信息错误");
						message = "009109088：取记帐卡申请信息错误";
						dealFalg = false;
						return dealFalg;
					}
				} else {
					cardBusinessInfo.setRemark("009109087:取客户信息错误");
					message = "009109087:取客户信息错误";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("009109086:该卡非系统卡");
				message = "009109086:该卡非系统卡";
				dealFalg = false;
				return dealFalg;
			}

		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009109002:该卡不是本行联名卡");
			return dealFalg;
		}
	}

	/***
	 * 止付黑名单解除-11
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean stopPayBlackListRelieve(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// ①移除黑名单（联名卡黑名单信息表）
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
		if (cardNocheck) {
			if (accountCInfo != null) {
				Customer customer = customerDao.findById(accountCInfo.getCustomerId());
				if (customer != null) {
					// 记帐卡没有黑名单的状态
					List<CardBlacklistInfo> cardBlacklistInfos = cardBlacklistDao.findByUTCardNoType(cardBusinessInfo.getUTCardNo(), "5");//
					boolean blackFlag = false;
					//判断该卡是否存在止付黑名单
					//如果根据粤通卡找到的黑名单对象不是空，即该粤通卡状态是黑名单，此时才能移除黑名单
					List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getUTCardNo());
					if (blackListTempList != null && !blackListTempList.isEmpty()) {
						for (BlackListTemp item : blackListTempList) {
							if (item.getStatus() == 5 && "4".equals(item.getGenMode())) {
								blackFlag = true;
								break;
							}
						}
					}
//					if(cardBlacklistInfos!=null && !cardBlacklistInfos.isEmpty()){
//						blackFlag = true;
//					}
					if (!blackFlag) {
						cardBusinessInfo.setRemark("009110007：该卡已经解除黑名单");
						message = "009110007：该卡已经解除黑名单";
						dealFalg = false;
						return dealFalg;
					} else {
						// ②增加客服流水（客服流水表）注：止付黑名单解除客服流水
						//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：止付黑名单解除客服流水
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
						if (accountCApply != null) {
							//信用卡与记帐卡是否匹配
							if (!accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
								cardBusinessInfo.setRemark("009110090：粤通卡卡号与信用卡号不匹配");
								message = "009110090：粤通卡卡号与信用卡号不匹配";
								dealFalg = false;
								return dealFalg;
							} else {
								SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
								if (subAccountInfo != null) {
									if (!subAccountInfo.getApplyID().equals(accountCApply.getId())) {
										cardBusinessInfo.setRemark("009110090：粤通卡卡号与信用卡号不匹配");
										message = "009110090：粤通卡卡号与信用卡号不匹配";
										dealFalg = false;
										return dealFalg;
									}
									if (cardBlacklistInfos != null && !cardBlacklistInfos.isEmpty()) {
										for (CardBlacklistInfo c : cardBlacklistInfos) {
											//解除记帐卡黑名单信息表的止付黑名单
											cardBlacklistDao.delete(c);
										}
									}


									//代理网点解除禁用黑名单（铭鸿），-5
									blackListService.saveRelieveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
											, "4", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
											cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
											new Date());
									// 记帐卡业务记录
									AccountCBussiness accountCBussiness = new AccountCBussiness();
									BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
									accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
									accountCBussiness.setUserId(customer.getId());
									accountCBussiness.setAccountId(subAccountInfo.getId());
									accountCBussiness.setState("51");        //51-止付黑名单解除（代理点)
									accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
									accountCBussiness.setTradeTime(new Date());
									accountCBussiness.setOperId(accountCApply.getOperId());
									accountCBussiness.setPlaceId(accountCApply.getPlaceId());
									accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
									accountCBussiness.setOldCardNo(cardBusinessInfo.getUTCardNo());
									//新增的字段
									accountCBussiness.setOperName(accountCApply.getOperName());
									accountCBussiness.setOperNo(accountCApply.getOperNo());
									accountCBussiness.setPlaceName(accountCApply.getPlaceName());
									accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
									accountCBussiness.setBusinessId(accountCApply.getHisseqId());
									accountCBussinessDao.save(accountCBussiness);


									//生成手工黑名单数据
									Timestamp reqTime = new Timestamp(System.currentTimeMillis());
									ManualBlackListSend blackListSend = new ManualBlackListSend();
									blackListSend.setCardCode(cardBusinessInfo.getUTCardNo());
									blackListSend.setCardType("23");            //储值卡：22 记账卡：23 电子标签：3
									blackListSend.setSrtType("B4");                //B4：解除手工止付黑名单（联名卡）
									blackListSend.setDarkReason("4");            //4：止付卡进入
									blackListSend.setReqTime(reqTime);
									blackListSend.setUpdateTime(reqTime);
									blackListSend.setBoardListNo(Long.parseLong(System.currentTimeMillis() + "1027"));
									manualBlackListSendDao.save(blackListSend);

									//生成客服流水
									ServiceWater serviceWater = new ServiceWater();
									Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

									serviceWater.setId(serviceWater_id);

									serviceWater.setCustomerId(customer.getId());
									serviceWater.setUserNo(customer.getUserNo());
									serviceWater.setUserName(customer.getOrgan());
									serviceWater.setSerType("702");//702：止付黑名单解除
									serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
									serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
									serviceWater.setAccountCBussinessId(accountCBussiness.getId());
									serviceWater.setOperId(accountCBussiness.getOperId());
									serviceWater.setOperName(accountCBussiness.getOperName());
									serviceWater.setOperNo(accountCBussiness.getOperNo());
									serviceWater.setPlaceId(accountCBussiness.getPlaceId());
									serviceWater.setPlaceName(accountCBussiness.getPlaceName());
									serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
									serviceWater.setRemark("代理客服系统：联名记帐卡止付黑名单解除");
									serviceWater.setOperTime(new Date());
									serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
									serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
									serviceWaterDao.save(serviceWater);

									//客服流水号关联业务信息，新加的字段
									cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());
									return dealFalg;

								} else {
									cardBusinessInfo.setRemark("009110089：取记帐卡子账户信息错误");
									message = "009109089：取记帐卡子账户信息错误";
									dealFalg = false;
									return dealFalg;
								}

							}


						} else {
							cardBusinessInfo.setRemark("009110088：取记帐卡申请信息错误");
							message = "009109088：取记帐卡申请信息错误";
							dealFalg = false;
							return dealFalg;
						}
					}
				} else {
					cardBusinessInfo.setRemark("009110087:取客户信息错误");
					message = "009110087:取客户信息错误";
					dealFalg = false;
					return dealFalg;
				}
			} else {
				cardBusinessInfo.setRemark("009110086:该卡非系统卡");
				message = "009110086:该卡非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			cardBusinessInfo.setRemark("009110002:该卡不是本行联名卡");
			return dealFalg;
		}
	}

	/***
	 * 销户后重启
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 * @param cardBusinessType
	 */
	public boolean restartCard(boolean cardNocheck,
	                           CardBusinessInfo cardBusinessInfo,
	                           ServiceFlowRecord serviceFlowRecord, String bankCode,
	                           String message, boolean dealFalg, String cardBusinessType) {
		//是否需要校验销户后重启
		boolean restartCheckFlag = true;
		// ①修改卡片状态（记帐卡信息表）注：记帐卡状态为0：正常
		//1）查找是否有对应的销卡（09）客户记录，是否存在对应09-销卡，且需校对业务类型+粤通卡号+信用卡卡号是否一致
		//2）且该卡号不能做过换卡、补领、非过户补领业务
		List<CardBusinessInfo> cardLists = findBusinessInfo(cardBusinessType,
				cardBusinessInfo.getUTCardNo(), cardBusinessInfo.getCreditCardNo(), null, null, "09", restartCheckFlag);
		if (cardLists != null && !cardLists.isEmpty()) {
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
			if (cardNocheck) {
				if (accountCInfo != null) {
					// 记帐卡的销卡状态即为注销：2
					if (accountCInfo.getState().equals("2")) {
						//判断该卡是否有注销黑名单
						Customer customer = customerDao.findById(accountCInfo.getCustomerId());
						if (customer != null) {
							//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：销户后重启客服流水
							AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
							if (accountCApply != null) {

								//信用卡与记帐卡是否匹配
								if (!accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
									cardBusinessInfo.setRemark("009111017：粤通卡卡号与信用卡号不匹配");
									message = "009111017：粤通卡卡号与信用卡号不匹配";
									dealFalg = false;
									return dealFalg;
								} else {
									SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
									if (subAccountInfo != null) {
										if (!subAccountInfo.getApplyID().equals(accountCApply.getId())) {
											cardBusinessInfo.setRemark("009111017：粤通卡卡号与信用卡号不匹配");
											message = "009111017：粤通卡卡号与信用卡号不匹配";
											dealFalg = false;
											return dealFalg;
										}
										// 记帐卡没有黑名单的状态(2-销卡)
										List<CardBlacklistInfo> cardBlacklistInfos = cardBlacklistDao.findByUTCardNoType(cardBusinessInfo.getUTCardNo(), "2");//

										//如果根据粤通卡找到的黑名单对象不是空，即该粤通卡状态是黑名单，此时才能移除黑名单
//											List<BlackListTemp> BlackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getOldUTCardNo());

										// 先记录注销的历史记录
										AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
										Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
										accountCInfoHis.setId(accountCInfoHisId);
										accountCInfoHis.setGenReason("50");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正 50：销户后重启
										accountCInfo.setHisSeqId(accountCInfoHis.getId());
										accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

										accountCInfo.setState("0");

										accountCInfoDao.update(accountCInfo);

										//移出注销登记表的注销记录
										List<Cancel> cancels = new ArrayList<Cancel>();
										//1)查找注销登记表注销记录
										cancels = cancelService.findCancelLists(String.valueOf(accountCInfo.getCustomerId()), "5", accountCInfo.getCardNo(), cardBusinessInfo.getCreditCardNo(), bankCode, "1");
										if (cancels != null && !cancels.isEmpty()) {
											for (Cancel c : cancels) {
												//2）删除注销登记表记录
												cancelService.deleteById(c.getId());
											}
										}

										//清算系统，记帐卡状态信息
//											accountCService.saveACinfo(0, accountCInfo, "4");

										if (cardBlacklistInfos != null && !cardBlacklistInfos.isEmpty()) {
											// ③移除黑名单（联名卡黑名单信息表）
											for (CardBlacklistInfo c : cardBlacklistInfos) {
												cardBlacklistDao.delete(c);
											}

										}

										//代理网点注销记帐卡后重启
										blackListService.saveCardRelieveCancel(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
												, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
												cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
												new Date());
										// 记帐卡业务记录
										AccountCBussiness accountCBussiness = new AccountCBussiness();
										BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
										accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
										accountCBussiness.setUserId(customer.getId());
										accountCBussiness.setAccountId(subAccountInfo.getId());
										accountCBussiness.setState("52");        //52. 销户后重启（代理点）
										accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
										accountCBussiness.setTradeTime(new Date());
										accountCBussiness.setOperId(accountCApply.getOperId());
										accountCBussiness.setPlaceId(accountCApply.getPlaceId());
										accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
										accountCBussiness.setOldCardNo(cardBusinessInfo.getUTCardNo());
										//新增的字段
										accountCBussiness.setOperName(accountCApply.getOperName());
										accountCBussiness.setOperNo(accountCApply.getOperNo());
										accountCBussiness.setPlaceName(accountCApply.getPlaceName());
										accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
										accountCBussiness.setBusinessId(accountCApply.getHisseqId());
										accountCBussinessDao.save(accountCBussiness);

										//生成客服流水
										ServiceWater serviceWater = new ServiceWater();
										Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

										serviceWater.setId(serviceWater_id);

										serviceWater.setCustomerId(customer.getId());
										serviceWater.setUserNo(customer.getUserNo());
										serviceWater.setUserName(customer.getOrgan());
										serviceWater.setSerType("703");//703：销户后重启
										serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
										serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
										serviceWater.setAccountCBussinessId(accountCBussiness.getId());
										serviceWater.setOperId(accountCBussiness.getOperId());
										serviceWater.setOperName(accountCBussiness.getOperName());
										serviceWater.setOperNo(accountCBussiness.getOperNo());
										serviceWater.setPlaceId(accountCBussiness.getPlaceId());
										serviceWater.setPlaceName(accountCBussiness.getPlaceName());
										serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
										serviceWater.setRemark("代理客服系统：联名记帐卡销户后重启");
										serviceWater.setOperTime(new Date());
										serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
										serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
										serviceWaterDao.save(serviceWater);

										//客服流水号关联业务信息，新加的字段
										cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

//										//写给铭鸿的清算数据：卡片状态信息
//
//										String userType = "";
//										if(UserTypeEnum.person.getValue().equals(customer.getUserType())){
//											userType = "0";//个人
//										}else{
//											userType = "1";//单位
//										}
//										cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()),
//												CardStateSendSerTypeEnum.acIssue.getValue(), accountCInfo.getCardNo(), "23", userType);

										//生成联名记账卡销户后重启数据
										Timestamp reqTime = new Timestamp(System.currentTimeMillis());
										CloseAccountRestartSend closeAccountRestartSend = new CloseAccountRestartSend();
										closeAccountRestartSend.setAccountId(cardBusinessInfo.getCreditCardNo());
										closeAccountRestartSend.setCardCode(cardBusinessInfo.getUTCardNo());
										closeAccountRestartSend.setReqTime(reqTime);
										closeAccountRestartSend.setUpdateTime(reqTime);
										closeAccountRestartSend.setBoardListNo(Long.parseLong(System.currentTimeMillis() + "1028"));
										closeAccountRestartSendDao.save(closeAccountRestartSend);

										return dealFalg;
									} else {
										cardBusinessInfo.setRemark("009111016：取记帐卡子账户信息错误");
										message = "009111016：取记帐卡子账户信息错误";
										dealFalg = false;
										return dealFalg;
									}
								}


							} else {
								cardBusinessInfo.setRemark("009111015：取记帐卡申请信息错误");
								message = "009111015：取记帐卡申请信息错误";
								dealFalg = false;
								return dealFalg;
							}
						} else {
							cardBusinessInfo.setRemark("009111014：取客户信息错误");
							message = "009111014：取客户信息错误";
							dealFalg = false;
							return dealFalg;
						}

					} else {
						cardBusinessInfo.setRemark("009111004：该卡不是注销卡");
						message = "009111004：该卡不是注销卡";
						dealFalg = false;
						return dealFalg;
					}

				} else {
					cardBusinessInfo.setRemark("009111002：该卡为非系统卡");
					message = "009111002：该卡为非系统卡";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				// 如果非本代理点
				cardBusinessInfo.setRemark("009111013：该卡不是本行联名卡");
				return dealFalg;
			}
		} else {
			cardBusinessInfo.setRemark("009111018：没有找到该粤通卡卡号和信用卡的销卡记录或该粤通卡已经办理换卡、补领业务");
			message = "009111018：没有找到该粤通卡卡号和信用卡的销卡记录或该粤通卡已经办理换卡、补领业务";
			dealFalg = false;
			return dealFalg;
		}
	}

	/***
	 * 解挂
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean relieveLose(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// ①修改卡片状态（记帐卡信息表）注：记帐卡状态为0：正常
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
		if (cardNocheck) {
			if (accountCInfo != null) {
				//粤通卡账户为挂失状态
				String beforeState = accountCInfo.getState();// 该记帐卡原来的状态
				if (accountCInfo.getState().equals("1")) {


					Customer customer = customerDao.findById(accountCInfo.getCustomerId());
					if (customer != null) {
						//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：解挂客服流水（小类是4）
						AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
						if (accountCApply != null) {
							//信用卡与记帐卡是否匹配
							if (!accountCApply.getCustomerId().equals(accountCInfo.getCustomerId())) {
								cardBusinessInfo.setRemark("009112019：粤通卡卡号与信用卡号不匹配");
								message = "009112019：粤通卡卡号与信用卡号不匹配";
								dealFalg = false;
								return dealFalg;
							} else {
								SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
								if (subAccountInfo != null) {
									if (!subAccountInfo.getApplyID().equals(accountCApply.getId())) {
										cardBusinessInfo.setRemark("009112019：粤通卡卡号与信用卡号不匹配");
										message = "009112019：粤通卡卡号与信用卡号不匹配";
										dealFalg = false;
										return dealFalg;
									}
									// 先记录解挂的历史记录
									AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
									Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
									accountCInfoHis.setId(accountCInfoHisId);
									accountCInfoHis.setGenReason("6");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
									accountCInfo.setHisSeqId(accountCInfoHis.getId());
									accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

									accountCInfo.setState("0");

									accountCInfoDao.update(accountCInfo);


									//清算系统，记帐卡状态信息
									List<CardBlacklistInfo> cardBlacklistInfos = cardBlacklistDao.findByUTCardNoType(cardBusinessInfo.getUTCardNo(), "1");

									// ③移除黑名单（联名卡黑名单信息表）
									if (cardBlacklistInfos != null && !cardBlacklistInfos.isEmpty()) {
										for (CardBlacklistInfo c : cardBlacklistInfos) {
											cardBlacklistDao.delete(c);
										}

									}


									//记帐卡解挂
									blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
											, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
											cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
											new Date());
									// 记帐卡业务记录
									AccountCBussiness accountCBussiness = new AccountCBussiness();
									BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
									accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
									accountCBussiness.setUserId(customer.getId());
									accountCBussiness.setAccountId(subAccountInfo.getId());
									accountCBussiness.setState("4");        //4 解挂
									accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
									accountCBussiness.setTradeTime(new Date());
									accountCBussiness.setOperId(accountCApply.getOperId());
									accountCBussiness.setPlaceId(accountCApply.getPlaceId());
									accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
									accountCBussiness.setOldCardNo(cardBusinessInfo.getUTCardNo());
									//新增的字段
									accountCBussiness.setOperName(accountCApply.getOperName());
									accountCBussiness.setOperNo(accountCApply.getOperNo());
									accountCBussiness.setPlaceName(accountCApply.getPlaceName());
									accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
									accountCBussiness.setBusinessId(accountCApply.getHisseqId());
									accountCBussinessDao.save(accountCBussiness);

									//写给铭鸿的清算数据：卡片状态信息
									String userType = "";
									if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
										userType = "0";//个人
									} else {
										userType = "1";//单位
									}
									cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()),
											CardStateSendSerTypeEnum.unloss.getValue(), accountCInfo.getCardNo(), "23", userType);

									//生成客服流水
									ServiceWater serviceWater = new ServiceWater();
									Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

									serviceWater.setId(serviceWater_id);

									serviceWater.setCustomerId(customer.getId());
									serviceWater.setUserNo(customer.getUserNo());
									serviceWater.setUserName(customer.getOrgan());
									serviceWater.setSerType("220");//220：记帐卡解挂
									serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
									serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
									serviceWater.setAccountCBussinessId(accountCBussiness.getId());
									serviceWater.setOperId(accountCBussiness.getOperId());
									serviceWater.setOperName(accountCBussiness.getOperName());
									serviceWater.setOperNo(accountCBussiness.getOperNo());
									serviceWater.setPlaceId(accountCBussiness.getPlaceId());
									serviceWater.setPlaceName(accountCBussiness.getPlaceName());
									serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
									serviceWater.setRemark("代理客服系统：联名记帐卡解挂");
									serviceWater.setOperTime(new Date());
									serviceWater.setCardNo(cardBusinessInfo.getUTCardNo());
									serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
									serviceWaterDao.save(serviceWater);

									//客服流水号关联业务信息，新加的字段
									cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

									//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171018
									VehicleInfo vehicleInfo = iVehicleInfoService.findByAccountCNo(accountCInfo
											.getCardNo());
									realTransferService.accountCInfoTransfer(customer,
											accountCInfo, vehicleInfo, CardStatusEmeu.NORMAL.getCode(),
											OperationTypeEmeu.UPDATE.getCode());

									// 调用用户卡黑名单上传及变更接口
									noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
											new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EX_BLACK.getCode());
									//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171018

									return dealFalg;
								} else {
									cardBusinessInfo.setRemark("009112018：取记帐卡子账户信息错误");
									message = "009112018：取记帐卡子账户信息错误";
									dealFalg = false;
									return dealFalg;
								}
							}

						} else {
							cardBusinessInfo.setRemark("009112017：取记帐卡申请信息错误");
							message = "009112017：取记帐卡申请信息错误";
							dealFalg = false;
							return dealFalg;
						}
					} else {
						cardBusinessInfo.setRemark("009112016：取客户信息错误");
						message = "009112016：取客户信息错误";
						dealFalg = false;
						return dealFalg;
					}
				} else {
					cardBusinessInfo.setRemark("009112007：该卡不是挂失状态");
					message = "009112007：该卡不是挂失状态";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("009112003：该卡为非系统卡");
				message = "009112003：该卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009112002：该卡不是本行联名卡");
			return dealFalg;
		}
	}


	/**
	 * 非过户补领( 针对非复合卡，信用卡卡号不变，粤通卡卡号变)
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean noneReplaceCard(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		AccountCInfo oldAccountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getOldUTCardNo());// 旧卡
		if (cardNocheck) {
			if (oldAccountCInfo != null) {
				//判断旧卡是否挂失
				if (oldAccountCInfo.getState().equals("1")) {
					Customer customer = customerDao.findById(oldAccountCInfo.getCustomerId());
					if (customer == null) {
						cardBusinessInfo.setRemark("009113033：取客户信息错误");
						message = "009113033：取客户信息错误";
						dealFalg = false;
						return dealFalg;
					}
					AccountCApply accountCApply = accountCApplyDao.findByBankAccount(cardBusinessInfo.getCreditCardNo());
					//信用卡与记帐卡是否匹配
					if (accountCApply == null) {
						cardBusinessInfo.setRemark("009113034：取记帐卡申请信息错误");
						message = "009113034：取记帐卡申请信息错误";
						dealFalg = false;
						return dealFalg;
					}
					if (!accountCApply.getCustomerId().equals(oldAccountCInfo.getCustomerId())) {
						cardBusinessInfo.setRemark("009113036：旧粤通卡卡号与旧信用卡号不匹配");
						message = "009113036：旧粤通卡卡号与旧信用卡号不匹配";
						dealFalg = false;
						return dealFalg;
					}

					//取旧记账卡子账户信息
					SubAccountInfo subAccountInfo = subAccountInfoDao.findById(oldAccountCInfo.getAccountId());
					if (subAccountInfo != null) {
						if (!subAccountInfo.getSubAccountNo().equals(accountCApply.getSubAccountNo())) {
							cardBusinessInfo.setRemark("009113036：旧粤通卡卡号与旧信用卡号不匹配");
							message = "009113036：旧粤通卡卡号与旧信用卡号不匹配";
							dealFalg = false;
							return dealFalg;
						}
					} else {
						cardBusinessInfo.setRemark("009113035：取记帐卡子账户信息错误");
						message = "009113035：取记帐卡子账户信息错误";
						dealFalg = false;
						return dealFalg;
					}


					//判断旧卡是否止付黑名单
					List<BlackListTemp> blackListTempList = blackListService.findBlackListByCardNo4AgentCard(cardBusinessInfo.getOldUTCardNo());
					if (blackListTempList != null && !blackListTempList.isEmpty()) {
						for (BlackListTemp item : blackListTempList) {
							if (item.getStatus() == 4) {
								dealFalg = false;
								break;
							}
						}
					}
					if (!dealFalg) {
						cardBusinessInfo.setRemark("009113031：旧粤通卡卡号是是止付黑名单");
						message = "009113031：旧粤通卡卡号是是止付黑名单";
						return dealFalg;
					} else {
						AccountCInfo newAccountC = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());
						//新粤通卡卡号不存在于数据库，才能做本次业务
						if (newAccountC == null) {
							InterfaceRecord interfaceRecord = null;
							Map<String, Object> map = inventoryServiceForAgent.omsInterface(cardBusinessInfo.getUTCardNo(), "1", interfaceRecord, "issue",
									serviceFlowRecord.getPlaceID(), serviceFlowRecord.getOperID(), serviceFlowRecord.getOperName(), "1", serviceFlowRecord.getPlaceNo(), null, oldAccountCInfo.getRealCost(), "35");
							boolean result = (Boolean) map.get("result");
							if (!result) {
								cardBusinessInfo.setRemark("009113032：粤通卡卡号在记帐卡初始化表中不存在");
								message = "009113032：粤通卡卡号在记帐卡初始化表中不存在";
								dealFalg = false;
								return dealFalg;
							} else {

								//生成记帐卡业务记录和客服流水，②增加客服流水（客服流水表）注：非过户补领

								//系统需校验新信用卡名下是否挂非注销的记帐卡
								List<AccountCInfo> accountCInfos = accountCInfoDao.findNoCancelAccountListBySubAccountNo(accountCApply.getSubAccountNo());
								if (accountCInfos != null && !accountCInfos.isEmpty()) {
									for (AccountCInfo item : accountCInfos) {
										if (item.getCardNo().equals(oldAccountCInfo.getCardNo())) {
											continue;
										} else {
											cardBusinessInfo.setRemark("009113037：信用卡名下存在非注销记帐卡");
											message = "009113037：信用卡名下存在非注销记帐卡";
											dealFalg = false;
											return dealFalg;
										}
									}
								}


								// 先记录补领的历史记录
								AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
								Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
								accountCInfoHis.setId(accountCInfoHisId);
								accountCInfoHis.setGenReason("3");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
								oldAccountCInfo.setHisSeqId(accountCInfoHis.getId());
								accountCInfoHisDao.save(oldAccountCInfo, accountCInfoHis);

								// ①修改旧卡状态（记帐卡信息表）注：记帐卡状态为2：注销
								oldAccountCInfo.setState("2");

								//旧卡写注销登记表
								Cancel oldCancel = new Cancel();
								Long oldCancelId = sequenceUtil.getSequenceLong("SEQ_CSMS_CANCEL_NO");
								oldCancel.setId(oldCancelId);
								oldCancel.setCustomerId(oldAccountCInfo.getCustomerId());
								oldCancel.setFlag("2");//
								oldCancel.setCode(oldAccountCInfo.getCardNo());
								oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
								oldCancel.setOperId(serviceFlowRecord.getOperID());            //操作员ID
								oldCancel.setPlaceId(serviceFlowRecord.getPlaceID());                        //网点ID
								oldCancel.setCancelTime(new Date());
								oldCancel.setBankNo(accountCApply.getBankAccount());                        //开户银行账号
								oldCancel.setBankMember(accountCApply.getBankName());                        //银行客户名称
								oldCancel.setBankOpenBranches(accountCApply.getBank());                        //银行开户网点
								oldCancel.setOperNo(serviceFlowRecord.getOperNo());                            //操作员编号
								oldCancel.setOperName(serviceFlowRecord.getOperName());                        //操作员名称
								oldCancel.setPlaceNo(serviceFlowRecord.getPlaceNo());                        //网点编号
								oldCancel.setPlaceName(serviceFlowRecord.getPlaceName());                    //网点名称
								oldCancel.setCreditCardNo(cardBusinessInfo.getCreditCardNo());
								oldCancel.setSource("1");//1：代理
								cancelDao.save(oldCancel);
								accountCInfoDao.update(oldAccountCInfo);

								// Customer customer =
								// getCustomer(cardBusinessInfo.getOrgan(),
								// cardBusinessInfo.getIdType(),
								// cardBusinessInfo.getIdCode());

								// ②增加新卡信息（记帐卡信息表）注：记帐卡状态为0 正常
								AccountCInfo accountCInfo = new AccountCInfo();
								Long accountCInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
								accountCInfo.setId(accountCInfoId);
								accountCInfo.setCardNo(cardBusinessInfo.getUTCardNo());// **卡号(粤通卡卡号？)
								accountCInfo.setCustomerId(oldAccountCInfo.getCustomerId());
								accountCInfo.setAccountId(oldAccountCInfo.getAccountId());// 对应账户id
								accountCInfo.setState("0");// 0：正常
								// 1：挂失2：注销3：停用4：开户申请5：换卡申请6：补领申请	8：过户申请
								accountCInfo.setCost(new BigDecimal("0"));// 成本费
								accountCInfo.setRealCost(new BigDecimal("0"));// 实收成本费
								accountCInfo.setIssueTime(new Date());//发行时间
								accountCInfo.setIssueFlag(oldAccountCInfo.getIssueFlag());// 销售方式？
								//设置有效起始时间与结束时间
								Map<String, Object> m = (Map<String, Object>) map.get("initializedOrNotMap");
								if (m != null) {
									accountCInfo.setStartDate((Date) m.get("startDate"));
									accountCInfo.setEndDate((Date) m.get("endDate"));
								}

								Calendar cal = Calendar.getInstance();
								;
								if (accountCInfo.getRealCost().compareTo(new BigDecimal("0")) == 0) {
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
									logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
									throw new ApplicationException();
								} else {
									logger.error("获取营运维保周期参数失败:" + (String) paramMap.get("message"));
									throw new ApplicationException();
									//cal.add(Calendar.YEAR, 10);// 十年有效期
								}

								accountCInfo.setMaintainTime(cal.getTime());


								accountCInfo.setBind(oldAccountCInfo.getBind());// 绑定标志?


								accountCInfo.setIssueOperId(cardBusinessInfo.getImportOper());
								accountCInfo.setIssuePlaceId(cardBusinessInfo.getImportPlace());
								accountCInfo.setOperNo(serviceFlowRecord.getOperNo());
								accountCInfo.setOperName(serviceFlowRecord.getOperName());
								accountCInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
								accountCInfo.setPlaceName(serviceFlowRecord.getPlaceName());
								accountCInfo.setS_con_pwd_flag(oldAccountCInfo.getS_con_pwd_flag());//是否需要交易密码？
								accountCInfo.setTradingPwd(oldAccountCInfo.getTradingPwd());//交易密码，MD加密
//															accountCInfo.setAgentsMay(1L);// 代理点
								accountCInfo.setLinkMan(cardBusinessInfo.getLinkMan());
								accountCInfo.setLinkTel(cardBusinessInfo.getLinkTel());
								accountCInfo.setLinkMobile(cardBusinessInfo.getLinkMobile());
								accountCInfo.setLinkAddr(cardBusinessInfo.getLinkAddr());
								accountCInfo.setLinkZipCode(cardBusinessInfo.getLinkZipCode());

								accountCInfoDao.save(accountCInfo);


//											//清算系统，记帐卡状态信息
//											accountCService.saveACinfo(0, accountCInfo, "4");
//
//											//清算系统，记帐卡状态信息 (旧卡无卡注销)
//											accountCService.saveACinfo(2, oldAccountCInfo, "4");


								//保存联名卡黑名单信息
								CardBlacklistInfo cardBlacklistInfo = new CardBlacklistInfo();
								Long cardBlacklistInfoId = sequenceUtil
										.getSequenceLong("SEQ_CSMSCardblacklistinfo_NO");
								cardBlacklistInfo.setId(cardBlacklistInfoId);
								cardBlacklistInfo.setUTCardNo(cardBusinessInfo.getOldUTCardNo());// 旧卡
								cardBlacklistInfo.setCreditCardNo(cardBusinessInfo.getOldCreditCardNo());
								cardBlacklistInfo.setUserNo(customer.getUserNo());
								cardBlacklistInfo.setProduceTime(new Date());
								cardBlacklistInfo.setProduceReason("2");// 产生原因2：销卡
								// cardBlacklistInfo.setRemark("");
								cardBlacklistDao.save(cardBlacklistInfo);


								//记帐卡挂失补领需要注销旧卡（下发黑名单给铭鸿）
								blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, oldAccountCInfo.getCardNo(), new Date()
										, "2", cardBusinessInfo.getOperId(), cardBusinessInfo.getOperNo(), cardBusinessInfo.getOperName(),
										cardBusinessInfo.getPlaceId(), cardBusinessInfo.getPlaceNo(), cardBusinessInfo.getPlaceName(),
										new Date());

								// 记帐卡业务记录
								AccountCBussiness accountCBussiness = new AccountCBussiness();
								BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
								accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
								accountCBussiness.setUserId(customer.getId());
								accountCBussiness.setAccountId(subAccountInfo.getId());
								accountCBussiness.setOldAccountId(subAccountInfo.getId());
								accountCBussiness.setState("53");        //53 非过户补领（代理点）
								accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
								accountCBussiness.setTradeTime(new Date());
								accountCBussiness.setOperId(accountCApply.getOperId());
								accountCBussiness.setPlaceId(accountCApply.getPlaceId());
								accountCBussiness.setCardNo(cardBusinessInfo.getUTCardNo());
								accountCBussiness.setOldCardNo(cardBusinessInfo.getOldUTCardNo());
								//新增的字段
								accountCBussiness.setOperName(accountCApply.getOperName());
								accountCBussiness.setOperNo(accountCApply.getOperNo());
								accountCBussiness.setPlaceName(accountCApply.getPlaceName());
								accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
								accountCBussiness.setBusinessId(accountCApply.getHisseqId());
								//回执打印用
								accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
								accountCBussinessDao.save(accountCBussiness);

								//资金转移确认表
								DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(accountCBussiness.getOldCardNo());

								DbasCardFlow dbasCard = new DbasCardFlow();
								dbasCard.setId(sequenceUtil.getSequenceLong("seq_csmsdbascardflow_no"));
								dbasCard.setNewCardNo(accountCBussiness.getCardNo());
								dbasCard.setOldCardNo(accountCBussiness.getOldCardNo());
								if (dbasCardFlow != null)
									dbasCard.setCardNo(dbasCardFlow.getCardNo());
								else dbasCard.setCardNo(accountCBussiness.getOldCardNo());
								dbasCard.setCardType(DBACardFlowCardTypeEnum.accountCard.getValue());
								dbasCard.setSerType(DBACardFlowSerTypeEnum.lossReplaceCard.getValue());
								dbasCard.setApplyTime(new Date());
								dbasCard.setServiceId(accountCBussiness.getId());
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


								//旧记帐卡的车卡标签绑定记录
								CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(oldAccountCInfo.getId());
								//车辆
								VehicleInfo vehicle = null;
								if (carObuCardInfo != null)
									vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
								VehicleBussiness vehicleBussiness = null;


								String userType = "";
								if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
									userType = "0";//个人
								} else {
									userType = "1";//单位
								}

								String obuSeq = "";
								Date obuIssueTime = null;
								Date obuExpireTime = null;
								if (vehicle != null && carObuCardInfoDao.updateAccountID(accountCInfo.getId(), oldAccountCInfo.getId()) == 1) {//车卡已经绑定

									//新增车辆业务记录表CSMS_Vehicle_Bussiness
									vehicleBussiness = new VehicleBussiness();
									BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
									vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
									vehicleBussiness.setCustomerID(vehicle.getCustomerID());
									vehicleBussiness.setVehiclePlate(vehicle.getVehiclePlate());
									vehicleBussiness.setVehicleColor(vehicle.getVehicleColor());
									vehicleBussiness.setCardNo(accountCInfo.getCardNo());//新卡号
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

									vehicleBussinessDao.save(vehicleBussiness);


									//写给铭鸿的清算数据：用户状态信息
									// 旧卡注销
									cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCBussiness.getOldCardNo(),
											"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
											null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂失补领后将旧卡注销");

									//写给铭鸿的清算数据：用户状态信息
									//新卡发行
									cardObuService.saveUserStateInfo(accountCBussiness.getTradeTime(), Integer.valueOf(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCBussiness.getCardNo(),
											"23", Integer.valueOf(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(),
											null, obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂失后补领新卡");

								}

								//旧卡注销
								//写给铭鸿的清算数据：卡片状态信息
								cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.cancel.getValue()),
										CardStateSendSerTypeEnum.cancelWithOutCard.getValue(), accountCBussiness.getOldCardNo(), "23", userType);

								//新卡发行
								//写给铭鸿的清算数据：卡片状态信息
								cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(AccountCardStateEnum.nomal.getIndex()),
										CardStateSendSerTypeEnum.acIssue.getValue(), accountCBussiness.getCardNo(), "23", userType);


								//保存清算数据
								cardSecondIssuedService.saveAccountCard(accountCInfo, accountCApply);
								//生成客服流水
								ServiceWater serviceWater = new ServiceWater();
								Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

								serviceWater.setId(serviceWater_id);

								serviceWater.setCustomerId(customer.getId());
								serviceWater.setUserNo(customer.getUserNo());
								serviceWater.setUserName(customer.getOrgan());
								serviceWater.setSerType("704");//704 非过户补领
								serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
								serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
								serviceWater.setAccountCBussinessId(accountCBussiness.getId());
								serviceWater.setOperId(accountCBussiness.getOperId());
								serviceWater.setOperName(accountCBussiness.getOperName());
								serviceWater.setOperNo(accountCBussiness.getOperNo());
								serviceWater.setPlaceId(accountCBussiness.getPlaceId());
								serviceWater.setPlaceName(accountCBussiness.getPlaceName());
								serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
								serviceWater.setRemark("代理客服系统：联名记帐卡非过户补领");
								serviceWater.setOperTime(new Date());
								serviceWater.setNewCardNo(cardBusinessInfo.getUTCardNo());
								serviceWater.setCardNo(cardBusinessInfo.getOldUTCardNo());

								serviceWaterDao.save(serviceWater);

								//客服流水号关联业务信息，新加的字段
								cardBusinessInfo.setServiceFlowNO(serviceWater.getId().toString());

								//发行成功后，更新营运接口调用登记记录的客服状态
								interfaceRecord = (InterfaceRecord) map.get("interfaceRecord");
								if (interfaceRecord != null && interfaceRecord.getCardno() != null) {
									interfaceRecord.setCsmsState("1");
									interfaceRecordDao.update(interfaceRecord);
								}
								return dealFalg;

							}
						} else {
							cardBusinessInfo.setRemark("009113012：新卡已经发行");
							message = "009113012：新卡已经发行";
							dealFalg = false;
							return dealFalg;
						}
					}

				} else {
					cardBusinessInfo.setRemark("009113009：旧粤通卡卡号不是挂失状态");
					message = "009113009：旧粤通卡卡号不是挂失状态";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("009113004：旧卡为非系统卡");
				message = "009113004：旧卡为非系统卡";
				dealFalg = false;
				return dealFalg;
			}

		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("009113002 ：新卡不是本行联名卡");
			return dealFalg;
		}
	}

	/***
	 * 过户补领申请
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean replaceCardTransApply(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());

		if (true) {
			if (accountCInfo != null) {
				if (accountCInfo.getState().equals("0")) {
					Customer customer = customerDao.findById(accountCInfo.getCustomerId());
					// ②增加银行申请信息（记帐卡申请表）
					AccountCApply accountCApply = new AccountCApply();
					Long accountCApplyId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapply_NO");
					accountCApply.setId(accountCApplyId);
					accountCApply.setCustomerId(accountCInfo.getCustomerId());// 对应客户id
					accountCApply.setAccountType("2");// 0、对公1、储蓄2、信用卡3、跨行划扣4、统一划账5、其他
					accountCApply.setLinkman(cardBusinessInfo.getLinkMan());
					accountCApply.setTel(cardBusinessInfo.getLinkTel());
					accountCApply.setValidity(DateUtil.getValidity(new Date()));// 子账户有效期，最大不超过5年
					accountCApply.setBank("");// 开户银行
					accountCApply.setBankSpan("");// 跨行划扣银行
					accountCApply.setBankAccount(cardBusinessInfo.getCreditCardNo());// 开户银行帐号(信用卡号？)
					accountCApply.setBankName("");// 开户银行名称
					accountCApply.setAccName("");// 开户帐户户名
					accountCApply.setInvoiceprn("");// 发票打印方式1、按单卡2、按帐号
					// accountCApply.setReqcount();
					// accountCApply.setResidueCount();
					//没有车辆就没有单卡保证金
					//accountCApply.setBail(new BigDecimal("0"));// 单卡保证金


					accountCApply.setVirType("");// 转帐类型
					accountCApply.setMaxacr(new BigDecimal("0"));// 通行费转帐限额
					// accountCApply.setBankClearNo("");//开户银行清算行号
					// accountCApply.setBankAcceptNo(bankAcceptNo);//开户行集中受理行号
					accountCApply.setAppState("1");// 审批中
					accountCApply.setOperId(cardBusinessInfo.getImportOper());
					accountCApply.setPlaceId(cardBusinessInfo.getImportPlace());
					accountCApply.setOperNo(serviceFlowRecord.getOperNo());
					accountCApply.setOperName(serviceFlowRecord.getOperName());
					accountCApply.setPlaceNo(serviceFlowRecord.getPlaceNo());
					accountCApply.setPlaceName(serviceFlowRecord.getPlaceName());

					accountCApplyDao.saveAccountCApply(accountCApply);

					// ①增加子账户信息（子账户信息表）
					MainAccountInfo mainAccount = mainAccountInfoDao
							.findByMainId(accountCInfo.getCustomerId());
					SubAccountInfo subAccountInfo = new SubAccountInfo();
					Long subAccountInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSSubAccountInfo_NO");
					subAccountInfo.setId(subAccountInfoId);
					subAccountInfo.setMainId(mainAccount.getId());// 总账户id

					SubAccountInfo sub = subAccountInfoDao.findLastDateSub(accountCApply.getCustomerId());
					subAccountInfo
							.setSubAccountNo(customer.getUserNo() + "2" + StringUtil.getSerailNumber(sub));// 账户号
					// :客户号+类型+序号（其中序号为记帐卡账户号的后3位）
					subAccountInfo.setSubAccountType("2");// 记帐卡子账户
					subAccountInfo.setApplyID(accountCApply.getId());// 记帐卡申请id
					subAccountInfo.setOperId(cardBusinessInfo.getImportOper());
					subAccountInfo.setPlaceId(cardBusinessInfo.getImportPlace());
					subAccountInfo.setOperNo(serviceFlowRecord.getOperNo());
					subAccountInfo.setOperName(serviceFlowRecord.getOperName());
					subAccountInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
					subAccountInfo.setPlaceName(serviceFlowRecord.getPlaceName());
					subAccountInfo.setOperTime(new Date());
					subAccountInfo.setAgentsMay(1L);// 代理点

					subAccountInfoDao.save(subAccountInfo);

					// ③修改记帐卡发行信息（记帐卡信息表）注：记帐卡状态为8：过户申请,,并将记帐卡挂到新的子账户下(这里是否要添加到历史表？)
					accountCInfo.setState("8");
					accountCInfo.setAccountId(subAccountInfo.getId());// 将记帐卡挂到新的子账户下

					accountCInfoDao.update(accountCInfo);
					return dealFalg;
				} else {
					cardBusinessInfo.setRemark("该粤通卡的状态不是正常的");
					message = "该粤通卡的状态不是正常的";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("该粤通卡不存在");
				message = "该粤通卡不存在";
				dealFalg = false;
				return dealFalg;
			}
		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("非本行联名卡");
			return dealFalg;
		}
	}


	/**
	 * 过户补领确认
	 *
	 * @param cardNocheck
	 * @param cardBusinessInfo
	 * @param serviceFlowRecord
	 * @param bankCode
	 * @param message
	 * @param dealFalg
	 */
	public boolean replaceCardTransConfirm(boolean cardNocheck, CardBusinessInfo cardBusinessInfo, ServiceFlowRecord serviceFlowRecord, String bankCode, String message, boolean dealFalg) {
		// ①修改记帐卡发行信息（记帐卡信息表）注：记帐卡状态为0：正常
		AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfo.getUTCardNo());

		if (true) {
			if (accountCInfo != null) {
				String beforeState = accountCInfo.getState();
				//记帐卡过户申请状态为：8
				if (accountCInfo.getState().equals("8")) {
					// 先记录过户的历史记录
					AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
					Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
					accountCInfoHis.setId(accountCInfoHisId);
					accountCInfoHis.setGenReason("8");// 1：补领2：换卡；3：注销；4：删除5：挂失6：解挂7：过户8：迁移9：日结前实收成本费修正
					accountCInfo.setHisSeqId(accountCInfoHis.getId());
					accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

					accountCInfo.setState("0");//这里的发行时间应该是原本的吧？
					//accountCInfo.setIssueTime(new Date());//发行时间
					accountCInfo.setStartDate(new Date());//启用时间
					accountCInfoDao.update(accountCInfo);

					// ②增加客服流水信息（客服流水表）注：迁移客服流水(迁移：15)
					BailAccountInfo bailAccountInfo = bailAccountInfoDao
							.findByCustomerID(accountCInfo.getCustomerId());
					MainAccountInfo mainAccountInfo = mainAccountInfoDao
							.findByMainId(accountCInfo.getCustomerId());

					//ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
					Long serviceFlowRecordId = sequenceUtil
							.getSequenceLong("SEQ_CSMSServiceFlowRecord_NO");
					serviceFlowRecord.setId(serviceFlowRecordId);
					serviceFlowRecord.setClientID(accountCInfo.getCustomerId());
					serviceFlowRecord.setCardTagNO(cardBusinessInfo.getUTCardNo());
					serviceFlowRecord.setOldCardTagNO(cardBusinessInfo.getOldUTCardNo());
					serviceFlowRecord.setServiceFlowNO(serviceFlowRecordId.toString());// 流水号生成规则待定

					serviceFlowRecord.setServicePTypeCode(2);// 客服类型大类编码2
					serviceFlowRecord.setServiceTypeCode(15);// 记帐卡小类15迁移
					serviceFlowRecord.setBeforeState(beforeState); // **记帐卡原来状态为8过户申请
					serviceFlowRecord.setCurrState("0");// **现在状态为0正常

					serviceFlowRecord = getServiceFlowRecord(mainAccountInfo, bailAccountInfo,
							serviceFlowRecord);

					//serviceFlowRecord.setOperID(cardBusinessInfo.getImportOper());
					//serviceFlowRecord.setPlaceID(cardBusinessInfo.getImportPlace());
					serviceFlowRecord.setCreateTime(new Date());
					serviceFlowRecord.setIsNeedBlacklist("0");// 不下发黑名单
					serviceFlowRecord.setIsDoFlag("0");// 未处理

					serviceFlowRecordDao.save(serviceFlowRecord);

					//客服流水号，新加的字段
					cardBusinessInfo.setServiceFlowNO(serviceFlowRecord.getServiceFlowNO());


					//清算系统，记帐卡状态信息
					accountCService.saveACinfo(0, accountCInfo, "4");

					return dealFalg;

				} else {
					cardBusinessInfo.setRemark("没过户申请");
					message = "没过户申请";
					dealFalg = false;
					return dealFalg;
				}

			} else {
				cardBusinessInfo.setRemark("该粤通卡不存在");
				message = "该粤通卡不存在";
				dealFalg = false;
				return dealFalg;
			}

		} else {
			// 如果非本代理点
			cardBusinessInfo.setRemark("非本行联名卡");
			return dealFalg;
		}
	}

	/**
	 * boolean cardNocheck,CardBusinessInfo cardBusinessInfo,ServiceFlowRecord serviceFlowRecord,String bankCode,String message,boolean dealFalg
	 * 业务处理
	 *
	 * @param cardBusinessInfos
	 * 		多行数据
	 */
	public String saveBusiness(List<CardBusinessInfo> cardBusinessInfos, ServiceFlowRecord serviceFlowRecord, String bankCode) {
		try {
			String message = "";
			// 执行多条业务
			if (cardBusinessInfos.size() > 0) {
				for (CardBusinessInfo cardBusinessInfo : cardBusinessInfos) {
					message = dealBusiness(cardBusinessInfo, serviceFlowRecord,
							bankCode, CardBusinessTypeEnum.AMS.getValue());
				}
			}
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage() + "业务处理失败！");
			throw new ApplicationException();

		}
	}

	@Override
	public Map<String, Object> saveBankBusiness(
			CardBusinessInfo cardBusinessInfo,
			ServiceFlowRecord serviceFlowRecord, String bankCode) {
		try {
			String message = dealBusiness(cardBusinessInfo, serviceFlowRecord,
					bankCode, CardBusinessTypeEnum.BANK.getValue());
			Map<String, Object> result = new HashMap<>();
			result.put("message", message);
			result.put("cardBusinessInfo", cardBusinessInfo);
			return result;
		} catch (Exception e) {
			logger.error("业务处理失败！", e);
			throw new ApplicationException("业务处理失败！", e);

		}
	}


	private String dealBusiness(CardBusinessInfo cardBusinessInfo,
	                            ServiceFlowRecord serviceFlowRecord, String bankCode,
	                            String cardBusinessType) {
		boolean dealFalg = true;
		boolean cardNocheck = true;
		boolean bankCheckFlag = false;
		String message = "";
		String businessType = cardBusinessInfo.getBusinessType();

		//银行接口权限校验
		bankCheckFlag = bankInterfaceAuthService.findBankAuthInfo(bankCode, "", businessType, "1");
		if (!bankCheckFlag) {
			cardBusinessInfo.setIsTransact("2");//如果没有备注信息，则已办理失败
			cardBusinessInfo.setRemark("999999999：该代理点所属银行无此业务权限");
			cardBusinessInfo.setErrorCode("999999999");//设置业务编码
			message = cardBusinessInfo.getRemark();
			cardBusinessInfoDao.update(cardBusinessInfo);
			return message;
		}


		if (businessType.equals("01")) {

			//开户申请-01
			dealFalg = openAccountApply(cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("02")) {

			//开户确认-02(客户激活信用卡)
			dealFalg = openAccountConfirm(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg, cardBusinessType);


		} else if (businessType.equals("03")) {

			//资料变更-03
			dealFalg = dataChange(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);


		} else if (businessType.equals("04")) {
			// 挂失-04
			dealFalg = saveLose(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("05")) {
			// 换卡申请-05
			dealFalg = changeCardApply(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("06")) {
			//换卡确认-06

			dealFalg = changeCardConfirm(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg, cardBusinessType);

		} else if (businessType.equals("07")) {
			// 补领申请（挂失后重新制卡，补领新卡）
			dealFalg = replaceCardApply(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("08")) {
			// 补领确认
			dealFalg = replaceCardConfirm(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg, cardBusinessType);

		} else if (businessType.equals("09")) {
			// 销卡（只针对一张卡）
			dealFalg = cancelCard(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("10")) {
			// 止付黑名单下发
			dealFalg = stopPayBlackListIssued(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("11")) {
			// 止付黑名单解除
			dealFalg = stopPayBlackListRelieve(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("13")) {
			// 销户后重启
			dealFalg = restartCard(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg, cardBusinessType);


		} else if (businessType.equals("14")) {
			// 解挂
			dealFalg = relieveLose(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("16")) {
			// 非过户补领（即不用新增子账户的补领）
			dealFalg = noneReplaceCard(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("17")) {
			// 过户申请(将该客户的记帐卡挂到另一个新的子账户上)

			dealFalg = replaceCardTransApply(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		} else if (businessType.equals("18")) {
			// 过户确认
			dealFalg = replaceCardTransConfirm(cardNocheck, cardBusinessInfo, serviceFlowRecord, bankCode, message, dealFalg);

		}

		//处理完，修改状态在CSMS_Card_business_info
		//				if(!StringUtil.isNotBlank(cardBusinessInfo.getRemark())){
		if (dealFalg) {
			//如果该条数据的备注是空，那么表示该条业务已处理
			cardBusinessInfo.setIsTransact("1");//如果没有备注信息，则已办理成功
			cardBusinessInfo.setDealTime(new Date());
			cardBusinessInfo.setOperId(serviceFlowRecord.getOperID());
			cardBusinessInfo.setPlaceId(serviceFlowRecord.getPlaceID());
			cardBusinessInfo.setOperNo(serviceFlowRecord.getOperNo());
			cardBusinessInfo.setPlaceNo(serviceFlowRecord.getPlaceNo());
			cardBusinessInfo.setOperName(serviceFlowRecord.getOperName());
			cardBusinessInfo.setPlaceName(serviceFlowRecord.getPlaceName());
			//银行接口的话cardBusinessInfo的id并不存在，所以应该save
			if (cardBusinessInfo.getId() == null) {
				Long cardBusinessInfoId = sequenceUtil.getSequenceLong("SEQ_CSMSCardbusinessinfo_NO");
				cardBusinessInfo.setId(cardBusinessInfoId);
				cardBusinessInfoDao.saveCardBusinessInfo(cardBusinessInfo);
				message = "业务处理成功";
			} else {
				cardBusinessInfoDao.update(cardBusinessInfo);
			}
		} else {
			cardBusinessInfo.setIsTransact("2");//如果没有备注信息，则已办理失败
			String errorCode = cardBusinessInfo.getRemark();
			cardBusinessInfo.setErrorCode(errorCode.substring(0, 9));//设置业务编码
			message = cardBusinessInfo.getRemark();
			cardBusinessInfoDao.update(cardBusinessInfo);
		}
		return message;
	}


	private ServiceFlowRecord getServiceFlowRecord(MainAccountInfo mainAccountInfo, BailAccountInfo bailAccountInfo,
	                                               ServiceFlowRecord serviceFlowRecord) {
		/*
		 * serviceFlowRecord.setBeforeAvailableBalance(mainAccountInfo.
		 * getAvailableBalance());
		 * serviceFlowRecord.setBeforeFrozenBalance(mainAccountInfo.
		 * getFrozenBalance());
		 * serviceFlowRecord.setBeforepreferentialBalance(mainAccountInfo.
		 * getPreferentialBalance());
		 * serviceFlowRecord.setBeforeAvailableRefundBalance(mainAccountInfo.
		 * getAvailableRefundBalance());
		 * serviceFlowRecord.setBeforeRefundApproveBalance(mainAccountInfo.
		 * getRefundApproveBalance());
		 */

		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));

		/*
		 * serviceFlowRecord.setAfterAvailableBalance(mainAccountInfo.
		 * getAvailableBalance());
		 * serviceFlowRecord.setAfterFrozenBalance(mainAccountInfo.
		 * getFrozenBalance());
		 * serviceFlowRecord.setAfterpreferentialBalance(mainAccountInfo.
		 * getPreferentialBalance());
		 * serviceFlowRecord.setAfterAvailableRefundBalance(mainAccountInfo.
		 * getAvailableRefundBalance());
		 * serviceFlowRecord.setAfterRefundApproveBalance(mainAccountInfo.
		 * getRefundApproveBalance());
		 */

		serviceFlowRecord.setBeforeBailFee(bailAccountInfo.getBailFee());// 修改前保证金账户余额
		serviceFlowRecord.setCurrBailFee(new BigDecimal("0"));// 本次变动保证金账户余额
		serviceFlowRecord.setAfterBailFee(bailAccountInfo.getBailFee());// 修改后保证金账户余额

		return serviceFlowRecord;
	}

//	public String saveExportFile(HttpServletResponse response,List<CardBusinessInfo> cardBusinessInfos,String exportType){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String message = "";
//		BufferedWriter bufferedWriter = null;
//		try {
//			File testDir = new File("D:"+File.separator+"test");
//			if(cardBusinessInfos.size()==0){
//				message = "导出的数据为空!";
//				return message;
//			}else{
//				String fileName = cardBusinessInfos.get(0).getFileNum().substring(0, cardBusinessInfos.get(0).getFileNum().indexOf("."));
//				String filePath = ("D:"+File.separator+"test"+File.separator+""+fileName);
//				// 0:导出全部数据    1：导出错误数据
//				if("0".equals(exportType)){
//					fileName = fileName + "_all";
//					message = "您在D://test文件夹共导出" + cardBusinessInfos.size() + "条数据";
//				}else if("1".equals(exportType)){
//					fileName = fileName + "_error";
//					message = "您在D://test文件夹共导出" + cardBusinessInfos.size() + "条错误数据";
//				}
//				StringBuffer data = null;
//				String bussinessTime = null;
//				String bussinessType = null;
//				String UTCardNo = null;
//				String creditCardNo = null;
//				String oldUTCardNo = null;
//				String oldCreditCardNo = null;
//				String organ = null;
//				String userType = null;
//				String idType = null;
//				String idCode = null;
//				String cusTel = null;
//				String cusMobile = null;
//				String cusAddr = null;
//				String cusZipCode = null;
//				String cusEmail = null;
//				String linkMan = null;
//				String linkTel = null;
//				String linkMobile = null;
//				String linkAddr = null;
//				String linkZipCode = null;
//				String remark = null;
//
//				for(CardBusinessInfo c:cardBusinessInfos){
//					bussinessTime = simpleDateFormat.format(c.getBusinessTime());
//					if(c.getBusinessType() == null){
//						bussinessType = "";
//					}else{
//						bussinessType = c.getBusinessType().trim();
//					}
//					if(c.getUTCardNo() == null){
//						UTCardNo = "";
//					}else{
//						UTCardNo = c.getUTCardNo().trim();
//					}
//					if(c.getCreditCardNo() == null){
//						creditCardNo = "";
//					}else{
//						creditCardNo = c.getCreditCardNo().trim();
//					}
//					if(c.getOldUTCardNo() == null){
//						oldUTCardNo = "";
//					}else{
//						oldUTCardNo = c.getOldUTCardNo().trim();
//					}
//					if(c.getOldCreditCardNo() == null){
//						oldCreditCardNo = "";
//					}else{
//						oldCreditCardNo = c.getOldCreditCardNo().trim();
//					}
//					if(c.getOrgan() == null){
//						organ = "";
//					}else{
//						organ = c.getOrgan().trim();
//					}
//					if(c.getUserType() == null){
//						userType = "";
//					}else{
//						userType = c.getUserType().trim();
//					}
//					if(c.getIdType() == null){
//						idType = "";
//					}else{
//						idType = c.getIdType().trim();
//					}
//					if(c.getIdCode() == null){
//						idCode = "";
//					}else{
//						idCode = c.getIdCode().trim();
//					}
//					if(c.getCusTel() == null){
//						cusTel = "";
//					}else{
//						cusTel = c.getCusTel().trim();
//					}
//					if(c.getCusMobile() == null){
//						cusMobile = "";
//					}else{
//						cusMobile = c.getCusMobile().trim();
//					}
//					if(c.getCusAddr() == null){
//						cusAddr = "";
//					}else{
//						cusAddr = c.getCusAddr().trim();
//					}
//					if(c.getCusZipCode() == null){
//						cusZipCode = "";
//					}else{
//						cusZipCode = c.getCusZipCode().trim();
//					}
//					if(c.getCusEmail() == null){
//						cusEmail = "";
//					}else{
//						cusEmail = c.getCusEmail().trim();
//					}
//					if(c.getLinkMan() == null){
//						linkMan = "";
//					}else{
//						linkMan = c.getLinkMan().trim();
//					}
//					if(c.getLinkTel() == null){
//						linkTel = "";
//					}else{
//						linkTel = c.getLinkTel().trim();
//					}
//					if(c.getLinkMobile() == null){
//						linkMobile = "";
//					}else{
//						linkMobile = c.getLinkMobile().trim();
//					}
//					if(c.getLinkAddr() == null){
//						linkAddr = "";
//					}else{
//						linkAddr = c.getLinkAddr().trim();
//					}
//					if(c.getLinkZipCode() == null){
//						linkZipCode = "";
//					}else{
//						linkZipCode = c.getLinkZipCode().trim();
//					}
//
//					if(c.getRemark()==null){
//						remark = "";
//					}else{
//						remark = c.getRemark().trim();
//					}
//					data.append(bussinessTime).append("\t").append(bussinessType).append("\t").append(UTCardNo).append("\t").append(creditCardNo).append("\t").append(
//							oldUTCardNo).append("\t").append(oldCreditCardNo).append("\t").append(organ).append("\t").append(userType).append("\t").append(idType).append("\t").append(
//							idCode).append("\t").append(cusTel).append("\t").append(cusMobile).append("\t").append(cusAddr).append("\t").append(cusZipCode).append("\t").append(
//							cusEmail).append("\t").append(linkMan).append("\t").append(linkTel).append("\t").append(linkMobile).append("\t").append(linkAddr).append("\t").append(linkZipCode).append("\t").append(remark);
//					data.append("\r\n");
//				}
//				//生成文件保存到服务器本地
//				FileUtils.nioSaveFile(data.toString(),filePath);
//
//				//文件下载
//				FileUtil.exportFile(response,filePath,fileName);
//
//				//删除文件
//				File f = new File(filePath);
//
//				if(f.exists()){
//					f.delete();
//				}
//
//			}
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage()+"数据导出失败！");
//			throw new ApplicationException();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			if(bufferedWriter!=null){
//				try {
//					bufferedWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//		}
//		return message;
//	}

	private Customer getCustomer(String organ, String idType, String idCode) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Customer customer = new Customer();
		customer.setOrgan(organ);
		customer.setIdType(idType);
		customer.setIdCode(idCode);// 证件类型、证件号码唯一确认客户
		Customer oldCustomer = customerDao.find(customer);

		return oldCustomer;
	}

}
