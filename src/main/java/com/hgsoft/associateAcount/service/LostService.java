package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.serviceInterface.ILostService;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.entity.BlackListWarter;
import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.JointCardLossReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Constant;
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
public class LostService implements ILostService {
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccardBussinessDao accardBussinessDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private ServiceAppDao serviceAppDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private BlackListDao blackListDao;
	@Resource
	private CardHolderDao cardHolderDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private ICardObuService cardObuService;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IBlackListService blackListService;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

	private static Logger logger = Logger.getLogger(LostService.class.getName());

	public Map<String, Object> saveLost(AccountCInfo accountCInfo, ServiceApp serviceApp, String cardType) {
		try {
			accountCInfo = accountCInfoDao.findById(accountCInfo.getId());

			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("5");//挂失
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);
			accountCInfo.setState("1");//挂失
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);

			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(accountCInfo.getCustomerId());
			accardBussiness.setCardNo(accountCInfo.getCardNo());
			accardBussiness.setState("5");//挂失
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setLastState("1");//卡片最后状态	
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setMemo("联营卡挂失");
			accardBussiness.setOperId(serviceApp.getOperId());
			accardBussiness.setPlaceId(serviceApp.getPlaceId());
			accardBussiness.setReceiptPrintTimes(0);
			accardBussinessDao.save(accardBussiness);

			serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
			serviceApp.setPlaceType("7");
			serviceApp.setSerDate(new Date());
			serviceApp.setHandleType("1");//挂失
			serviceApp.setAccode(accountCInfo.getCardNo());
			//serviceApp.setFlowNo(accountCInfo.getUseNo());
			serviceAppDao.save(serviceApp);

			TollCardBlackDet tollCardBlackDet = new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null, 2, new Date(), 0, new Date());
			TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null, 2, new Date(), 0, new Date());
			accountCService.saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);
			accountCService.saveACinfo(1, accountCInfo, SystemTypeEnum.ACMS.getValue());

			//
			//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
			//
			//accountCService.saveDarkList(accountCInfo,darkList,"2", "1");
			blackListService.saveCardLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), tollCardBlackDet.getBusinessTime()
					, "2", accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null,
					new Date());
			return null;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "联营卡挂失失败");
			throw new ApplicationException("联营卡挂失失败");
		}
	}

	@Override
	public boolean saveLost(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness) {
		try {
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("5");
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

			accountCInfo.setState("1");
			accountCInfo.setBlackFlag("1");
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);

			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCBUSSINESS_NO"));
			accountCBussinessDao.save(accountCBussiness);

			BlackListWarter blackListWarter = new BlackListWarter();
			blackListWarter.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBLACKLISTWATER_NO"));
			blackListWarter.setCardType("23");
			blackListWarter.setCardNo(accountCInfo.getCardNo());
			blackListWarter.setGenTime(new Date());
			blackListWarter.setGenType("2");
			blackListWarter.setStatus(2);
			blackListWarter.setFlag("0");
			blackListWarter.setOperId(accountCBussiness.getOperId());
			blackListWarter.setOperNo(accountCBussiness.getOperNo());
			blackListWarter.setOperName(accountCBussiness.getOperName());
			blackListWarter.setPlaceId(accountCBussiness.getPlaceId());
			blackListWarter.setPlaceNo(accountCBussiness.getPlaceNo());
			blackListWarter.setPlaceName(accountCBussiness.getPlaceName());
			blackListWarter.setOperTime(new Date());
			blackListDao.saveBlackListWarter(blackListWarter);

			Customer customer = customerDao.findById(accountCBussiness.getUserId());

			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEWATER_NO"));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType(ServiceWaterSerType.utCardLost.getValue());
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("联营卡客服系统：记账卡挂失");
			serviceWaterDao.save(serviceWater);

			// 记账卡挂失回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("3");
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardLoss.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardLoss.getName());
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
			JointCardLossReceipt jointCardLossReceipt = new JointCardLossReceipt();
			jointCardLossReceipt.setTitle("记账卡挂失回执");
			jointCardLossReceipt.setCardNo(accountCInfo.getCardNo());
			CardHolder cardHolder = cardHolderDao.findCardHolderByCardNo(accountCInfo.getCardNo());
			jointCardLossReceipt.setName(cardHolder.getName());
			jointCardLossReceipt.setLinkTel(cardHolder.getPhoneNum());
			jointCardLossReceipt.setMobileNum(cardHolder.getMobileNum());
			jointCardLossReceipt.setLinkMan(cardHolder.getLinkMan());
			jointCardLossReceipt.setLinkAddr(cardHolder.getLinkAddr());
			receipt.setContent(JSONObject.fromObject(jointCardLossReceipt).toString());
			receiptDao.saveReceipt(receipt);

			String userType = "";
			if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
				userType = "0";//个人
			} else {
				userType = "1";//单位
			}
			cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(),
					Integer.parseInt(CardStateSendStateFlag.loss.getValue()), CardStateSendSerTypeEnum.loss.getValue(),
					accountCInfo.getCardNo(), "23", userType);

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			Customer newCustomer = new Customer();
			newCustomer.setUserNo(cardHolder.getUserNo());
			newCustomer.setOrgan(cardHolder.getName());
			newCustomer.setAgentName(cardHolder.getAgentName());
			// 调用用户卡信息上传及变更接口
			realTransferService.accountCInfoTransfer(newCustomer, accountCInfo, vehicleInfo,
					CardStatusEmeu.CARD_LOSS.getCode(), OperationTypeEmeu.UPDATE
							.getCode());

			// 调用用户卡黑名单上传及变更接口
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
					new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EN_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage() + "联营卡挂失失败");
			return false;
		} // try
	}

}
