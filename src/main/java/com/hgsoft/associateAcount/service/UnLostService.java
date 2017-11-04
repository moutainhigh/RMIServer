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
import com.hgsoft.associateAcount.dao.DarkListHisDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.serviceInterface.IUnLostService;
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
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.JointCardUnLossReceipt;
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
import java.util.List;
import java.util.Map;

@Service
public class UnLostService implements IUnLostService {

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
	private DarkListHisDao darkListHisDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private BlackListDao blackListDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CardHolderDao cardHolderDao;
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

	private static Logger logger = Logger.getLogger(UnLostService.class.getName());

	@Override
	public Map<String, Object> saveUnLost(AccountCInfo accountCInfo, ServiceApp serviceApp) {
		try {
			accountCInfo = accountCInfoDao.findById(accountCInfo.getId());
			//将当前联营卡黑名单登记表中数据移除至历史表，生成原因为解挂
//------------------------------------------------------------
			/*DarkList darkList = darkListDao.findByCardNoAndCau(accountCInfo.getCardNo(),"1");
			DarkListHis darkListHis=new DarkListHis();
			darkListHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLISTHIS_NO"));
			darkListHis.setCreateReason("7");
			darkListHisDao.save(darkList,darkListHis);
			
			darkListDao.deleteById(darkList.getId());*/

			//确认解挂后，将对应的记帐卡信息移入历史表，生成原因为“解挂”
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("6");//解挂
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);
			//修改记帐卡信息记录表的状态为“正常”
			accountCInfo.setState("0");//正常
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);

			//记录客户服务表
			serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
			serviceApp.setPlaceType("7");
			serviceApp.setSerDate(new Date());
			serviceApp.setHandleType("2");//解挂
			serviceApp.setAccode(accountCInfo.getCardNo());
			//serviceApp.setFlowNo(accountCInfo.getUseNo());
			serviceAppDao.save(serviceApp);

			//记录联营卡业务记录表，业务类型为解挂
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(accountCInfo.getCustomerId());
			accardBussiness.setCardNo(accountCInfo.getCardNo());
			accardBussiness.setState("6");//解挂
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setLastState("0");//卡片最后状态正常
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setMemo("联营卡解挂");
			accardBussiness.setOperId(serviceApp.getOperId());
			accardBussiness.setPlaceId(serviceApp.getPlaceId());
			accardBussiness.setReceiptPrintTimes(0);
			accardBussinessDao.save(accardBussiness);

			TollCardBlackDet tollCardBlackDet = new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null, 1, new Date(), 0, new Date());
			TollCardBlackDetSend tollCardBlackDetSend = new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null, 1, new Date(), 0, new Date());
			accountCService.saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);
			accountCService.saveACinfo(0, accountCInfo, SystemTypeEnum.ACMS.getValue());

			//
			//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
			//
			//accountCService.saveDarkList(accountCInfo,darkList,"1", "0");
			blackListService.saveCardNoLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), tollCardBlackDet.getBusinessTime()
					, "2", accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null,
					new Date());
			return null;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "联营卡解挂失败");
			throw new ApplicationException("联营卡解挂失败");
		}
	}

	@Override
	public boolean saveUnLost(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness) {
		try {
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("6");
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

			accountCInfo.setState("0");
			accountCInfo.setBlackFlag("0");
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
			blackListWarter.setStatus(-2);
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
			serviceWater.setSerType(ServiceWaterSerType.accUnLost.getValue());
			serviceWater.setAccountCBussinessId(accountCBussiness.getId());
			serviceWater.setOperId(accountCBussiness.getOperId());
			serviceWater.setOperNo(accountCBussiness.getOperNo());
			serviceWater.setOperName(accountCBussiness.getOperName());
			serviceWater.setPlaceId(accountCBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("联营卡客服系统：记账卡解挂");
			serviceWaterDao.save(serviceWater);

			// 记账卡解挂回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("3");
			receipt.setTypeCode(AccountCBussinessTypeEnum.accCardCanceLoss.getValue());
			receipt.setTypeChName(AccountCBussinessTypeEnum.accCardCanceLoss.getName());
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
			JointCardUnLossReceipt jointCardUnLossReceipt = new JointCardUnLossReceipt();
			jointCardUnLossReceipt.setTitle("记账卡解挂回执");
			List<Map<String, Object>> list = receiptDao.findReceiptsByCardNoAndType(accountCInfo.getCardNo(), "3", AccountCBussinessTypeEnum.accCardLoss.getValue());
			String oldReceiptNo = (String) list.get(0).get("oldReceiptNo");
			jointCardUnLossReceipt.setOldReceiptNo(oldReceiptNo);
			jointCardUnLossReceipt.setCardNo(accountCInfo.getCardNo());
			CardHolder cardHolder = cardHolderDao.findCardHolderByCardNo(accountCInfo.getCardNo());
			jointCardUnLossReceipt.setName(cardHolder.getName());
			jointCardUnLossReceipt.setLinkTel(cardHolder.getPhoneNum());
			jointCardUnLossReceipt.setMobileNum(cardHolder.getMobileNum());
			jointCardUnLossReceipt.setLinkMan(cardHolder.getLinkMan());
			jointCardUnLossReceipt.setLinkAddr(cardHolder.getLinkAddr());
			receipt.setContent(JSONObject.fromObject(jointCardUnLossReceipt).toString());
			receiptDao.saveReceipt(receipt);

			String userType = "";
			if (UserTypeEnum.person.getValue().equals(customer.getUserType())) {
				userType = "0";//个人
			} else {
				userType = "1";//单位
			}
			cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(),
					Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), CardStateSendSerTypeEnum.unloss.getValue(),
					accountCInfo.getCardNo(), "23", userType);

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo,
					CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.UPDATE.getCode());

			// 调用用户卡黑名单上传及变更接口
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
					new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EX_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage() + "联营卡解挂失败");
			return false;
		} // try
	}

}
