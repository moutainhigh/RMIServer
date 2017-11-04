package com.hgsoft.associateAcount.service;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IAccountCStopCardService;
import com.hgsoft.associateAcount.dao.AcInvoicInfoDao;
import com.hgsoft.associateAcount.dao.AcInvoicInfoHisDao;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.dao.DarkListHisDao;
import com.hgsoft.associateAcount.dao.FileImportDao;
import com.hgsoft.associateAcount.dao.LianCardTitleDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.associateAcount.entity.FileImport;
import com.hgsoft.associateAcount.serviceInterface.ILianCardFileUploadService;
import com.hgsoft.associateAcount.serviceInterface.IReqInfoService;
import com.hgsoft.associateReport.dao.ImportInfoDao;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ImportInfo;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件导入业务
 *
 * @author luohuanfa
 *         2016-05-20 11:49:36
 */
@Repository
public class LianCardFileUploadService implements ILianCardFileUploadService {

	private static Logger logger = Logger.getLogger(LianCardFileUploadService.class.getName());

	@Resource
	private SequenceUtil sequenceUtil;

	@Resource
	private AccountCDao accountCDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private IReqInfoService reqInfoService;


	@Resource
	private IAccountCService accountCService;


	@Resource
	private FileImportDao fileImportDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ImportInfoDao importInfoDao;

	@Resource
	private DarkListDao darkListDao;

	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;

	@Resource
	private AccountCInfoDao accountCInfoDao;


	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private AccardBussinessDao accardBussinessDao;

	@Resource
	private AccountCBussinessDao accountCBussinessDao;


	@Resource
	private ServiceAppDao serviceAppDao;

	@Resource
	private DarkListHisDao darkListHisDao;

	@Resource
	private AcInvoicInfoDao acInvoicInfoDao;

	@Resource
	private CustomerDao customerDao;

	@Resource
	private AcInvoicInfoHisDao acInvoicInfoHisDao;

	@Resource
	private ICustomerService customerService;
	private Cancel cancel;
	@Resource
	private IAccountCStopCardService accountCStopCardService;

	@Resource
	private ICardObuService cardObuService;
	/*@Resource
	private ACinfoDao aCinfoDao;*/

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	@Resource
	private CardHolderDao cardHolderDao;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030


	/**
	 * 文件导入
	 *
	 * @param fileImport
	 * 		文件导入对象
	 * @param list
	 * 		导入信息  每行为一个数组
	 * 		type 密码重设("04"),挂失("05"),解挂("06"),无卡注销("07"),黑名单添加("09"),黑名单解除("10"),发票抬头("13")
	 * @author gaosiling
	 */
	@Override
	public Map<String, Object> saveUpload(FileImport fileImport, List<String[]> list, AccountCApply accountCApply, SubAccountInfo subAccountInfo) {
		int falseNum = 0;
		int trueNum = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> cardList = new ArrayList<Map<String, String>>();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSIMPORTINFO_NO");
		try {
			if (list == null || list.size() == 0) return null;
			String[] strs = null;

			for (int i = 1; i < list.size(); i++) {
				boolean resultFlag = true;
				strs = list.get(i);
				if ("04".equals(strs[0])) {
					resultFlag = savePassWordService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("05".equals(strs[0])) {
					resultFlag = saveLostService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("06".equals(strs[0])) {
					resultFlag = saveUnLostService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("07".equals(strs[0])) {
					resultFlag = saveLogOutService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("09".equals(strs[0])) {
					resultFlag = saveDarkService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("10".equals(strs[0])) {
					resultFlag = saveRemoveDarkService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("13".equals(strs[0])) {
					resultFlag = saveTitleService(fileImport, strs, accountCApply, subAccountInfo, cardList);
					if (resultFlag) {
						trueNum++;
					} else {
						falseNum++;
					}
				} else if ("01".equals(strs[0]) || "11".equals(strs[0]) || "12".equals(strs[0]) || "13".equals(strs[0])) {
					Map<String, String> mapc = new HashMap<String, String>();
					mapc.put("cardNo", strs[1]);
					mapc.put("meno", "当前功能业务已经暂停提供服务");
					cardList.add(mapc);
					falseNum++;
				} else {
					Map<String, String> mapc = new HashMap<String, String>();
					mapc.put("cardNo", strs[1]);
					mapc.put("meno", "业务类型异常");
					cardList.add(mapc);
					falseNum++;
				}
			}
			Map<String, String> mapc_result = new HashMap<String, String>();
			mapc_result.put("cardNo", strs[1]);
			mapc_result.put("resultMeno", "导入结果：成功" + trueNum + "项，失败" + falseNum + "项");
			cardList.add(mapc_result);
			fileImport.setImportRow(fileImport.getTotalRow() - cardList.size());
			map.put("result", "true");
		} catch (Exception e) {
			logger.error(e.getMessage() + "文件导入失败");
			map.put("result", "false");
			fileImport.setImportRow(0);
		}
		fileImport.setId(seq);
		fileImportDao.save(fileImport);
		map.put("errorCardList", cardList);
		return map;

	}


	/**
	 * 密码重置
	 *
	 * @param fileImport
	 * @param strs
	 * @param accountCApply
	 * @param subAccountInfo
	 * @param cardList
	 */
	private boolean savePassWordService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2, 3)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "04", null, "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		AccountCInfo accountCInfo = accountCService.findByCardNo(strs[1]);
		if (accountCInfo == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "联营卡未办理开通申请");
			saveImportInfo(fileImport, strs[1], strs[2], "04", null, "联营卡未办理开通申请", "1");
			cardList.add(map);
			return false;
		}
		try {
			//记帐卡业务记录
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(seq);

			accountCBussiness.setUserId(accountCInfo.getCustomerId());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setState(accountCInfo.getState());
			//这个值需要修改
			accountCBussiness.setOperId(1L);
			accountCBussiness.setPlaceId(1L);

			accountCBussinessDao.save(accountCBussiness);

			accountCInfo.setTradingPwd(StringUtil.md5(strs[3]));
			accountCDao.update(accountCInfo);
			ServiceApp serviceApp = new ServiceApp();
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("8");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());

			serviceAppDao.save(serviceApp);


			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
			serviceWater.setSerType("101");//301电子标签发行

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 密码重设");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);


			saveImportInfo(fileImport, strs[1], strs[2], "04", null, null, "0");
			return true;

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
			e.printStackTrace();
			saveImportInfo(fileImport, strs[1], strs[2], "04", null, "密码修改异常", "1");
			throw new ApplicationException("联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());

		}

	}


	/**
	 * 解挂
	 *
	 * @param fileImport
	 * @param strs
	 * @param accountCApply
	 * @param subAccountInfo
	 * @param cardList
	 */
	private boolean saveUnLostService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "06", null, "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		try {
			AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
			Map<String, String> map = new HashMap<String, String>();
			if (accountCInfo == null) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通申请");
				saveImportInfo(fileImport, strs[1], strs[2], "06", null, "联营卡未办理开通申请", "1");
				cardList.add(map);
				return false;
			}

			if (!accountCInfo.getState().equals("1")) {
				//卡状态为挂失
				map.put("cardNo", strs[1]);
				map.put("meno", "该联营卡状态不为挂起，无法进行解挂操作");
				saveImportInfo(fileImport, strs[1], strs[2], "06", null, "该联营卡状态不为挂起，无法进行解挂操作", "1");
				cardList.add(map);
				return false;
			}
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
//			serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
//			serviceApp.setPlaceType("7");
//			serviceApp.setSerDate(new Date());
//			serviceApp.setHandleType("2");//解挂
//			serviceApp.setAccode(accountCInfo.getCardNo());
//			//serviceApp.setFlowNo(accountCInfo.getUseNo());
//			serviceAppDao.save(serviceApp);
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			ServiceApp serviceApp = new ServiceApp();
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("2");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());
			serviceAppDao.save(serviceApp);


			//记录联营卡业务记录表，业务类型为解挂
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(accountCInfo.getCustomerId());
			accardBussiness.setCardNo(accountCInfo.getCardNo());
			accardBussiness.setState("6");//解挂
			accardBussiness.setRealPrice(new BigDecimal("0"));
//			accardBussiness.setLastState("0");//卡片最后状态正常
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setMemo("文件导入联营卡解挂");
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

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
			serviceWater.setSerType("220");//301电子标签发行

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 文件导入解挂");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			saveImportInfo(fileImport, strs[1], strs[2], "06", null, null, "0");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			CardHolder cardHolder = cardHolderDao.findByAccountCId(accountCInfo.getId().toString());
			if (null == cardHolder) {
				throw new ApplicationException("持卡人信息为空！");
			}
			Customer newCustomer = new Customer();
			newCustomer.setUserNo(cardHolder.getUserNo());
			newCustomer.setOrgan(cardHolder.getName());
			newCustomer.setAgentName(cardHolder.getAgentName());

			realTransferService.accountCInfoTransfer(newCustomer, accountCInfo, vehicleInfo,
					CardStatusEmeu.NORMAL.getCode(),
					OperationTypeEmeu.UPDATE.getCode());

			// 调用用户卡黑名单上传及变更接口
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
					new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EX_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030

			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "联营卡解挂失败");
			throw new ApplicationException("联营卡解挂失败");
		}
	}


	/**
	 * 挂失
	 *
	 * @author gaosiling
	 */
	private boolean saveLostService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "05", null, "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		try {
			String errorCode = null;
			Map<String, String> map = new HashMap<String, String>();
			AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
			if (accountCInfo == null) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通申请");
				saveImportInfo(fileImport, strs[1], strs[2], "05", null, "联营卡未办理开通申请", "1");
				cardList.add(map);
				return false;
			}
			if (accountCInfo.getState().equals("1")) {
				// 卡状态为挂失
				map.put("cardNo", strs[1]);
				map.put("meno", "当前卡已登记挂失，结束业务");
				saveImportInfo(fileImport, strs[1], strs[2], "05", null, "当前卡已登记挂失，结束业务", "1");
				cardList.add(map);
				return false;
			}

			if (!"0".equals(accountCInfo.getState())) {
				map.put("cardNo", strs[1]);
				map.put("meno", "卡片不为正常卡，无法挂失");
				saveImportInfo(fileImport, strs[1], strs[2], "05", null, "卡片不为正常卡，无法挂失", "1");
				cardList.add(map);
				return false;
			}

			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("5");
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);

			accountCInfo.setState("1");
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);


			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(fileImport.getCustomerId());
			accardBussiness.setCardNo(strs[1]);
			accardBussiness.setState("5");//无卡终止使用
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setLastState("1");
			accardBussiness.setMemo("文件导入挂失");
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setOperId(fileImport.getOperNo());
			accardBussiness.setPlaceId(fileImport.getPlaceNo());
			accardBussinessDao.save(accardBussiness);

			ServiceApp serviceApp = new ServiceApp();
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("1");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());
			//
			//DarkList darkList = darkListDao.findByCardNo(strs[1]);
			//挂失
			//saveDarkList(accountCInfo,darkList,"2", "1");
			blackListService.saveCardLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
					, "2", accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null,
					new Date());
			serviceAppDao.save(serviceApp);

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
			serviceWater.setSerType("209");//挂失

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 文件导入挂失");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			saveImportInfo(fileImport, strs[1], strs[2], "05", null, null, "0");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			CardHolder cardHolder = cardHolderDao.findByAccountCId(accountCInfo.getId().toString());
			if (null == cardHolder) {
				throw new ApplicationException("持卡人信息为空！");
			}
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
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage() + "挂失业务，文件导入异常处理，导入卡号" + strs[1] + "对应数据异常");
			saveImportInfo(fileImport, strs[1], strs[2], "05", "1", null, "1");
			throw new ApplicationException("挂失业务，文件导入异常处理");
		}
	}


	/**
	 * 无卡注销
	 *
	 * @author gaosiling
	 */
	private boolean saveLogOutService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "07", null, "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		try {
			AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
			Map<String, String> map = new HashMap<String, String>();
			if (accountCInfo == null) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通申请");
				saveImportInfo(fileImport, strs[1], strs[2], "05", null, "联营卡未办理开通申请", "1");
				cardList.add(map);
				return false;
			}
			if (accountCInfo.getState().equals("2")) {
				// 卡状态为挂失
				map.put("cardNo", strs[1]);
				map.put("meno", "当前卡已注销，结束业务");
				saveImportInfo(fileImport, strs[1], strs[2], "07", null, "当前卡已注销，结束业务", "1");
				cardList.add(map);
				return false;
			}
			Long customerid = fileImport.getCustomerId();

			Customer customer = customerService.findById(customerid);
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord.setCardTagNO(accountCInfo.getCardNo());
			serviceFlowRecord.setClientID(customer.getId());
			serviceFlowRecord.setServicePTypeCode(2);// 操作记帐卡类
			serviceFlowRecord.setServiceTypeCode(9);// 无卡终止类

			serviceFlowRecord.setOperNo(fileImport.getOperNo() + "");
			serviceFlowRecord.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceFlowRecord.setIsNeedBlacklist("1"); //无卡终止需要下发黑名单
			serviceFlowRecord.setIsDoFlag("1");
			serviceFlowRecord.setDoTime(new Date());
			serviceFlowRecord.setCreateTime(new Date());

			AccardBussiness accardBussiness = new AccardBussiness();

			// 增加业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setCardNo(strs[1]);
			accountCBussiness.setState("9");//无卡
			accountCBussiness.setLastState("2");
			accountCBussiness.setTradeTime(new Date());
//			accountCBussiness.setOperId(fileImport.getOperNo());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
//			accountCBussiness.setPlaceId(fileImport.getPlaceNo());
			accountCBussiness.setOperNo(fileImport.getOperNo() + "");
			accountCBussiness.setPlaceNo(fileImport.getPlaceNo() + "");
			accountCBussiness.setUserId(customer.getId());
			accountCBussiness.setReceiptPrintTimes(0);


			// 增加注销登记
			cancel = new Cancel();
			cancel.setFlag("5");
			cancel.setCode(accountCInfo.getCardNo());
			cancel.setCustomerId(customer.getId());
			cancel.setCancelTime(new Date());
			cancel.setSource("2");
			cancel.setOperNo(fileImport.getOperNo() + "");
			cancel.setPlaceNo(fileImport.getPlaceNo() + "");
			cancel.setReason("");

//			cancel = new Cancel();
//			cancel.setFlag("5");
//			cancel.setCode(accountCInfo.getCardNo());
//			cancel.setCustomerId(customer.getId());
//			cancel.setCancelTime(new Date());
//			cancel.setSource("2");
//			cancel.setOperId(getOperPlaceId());
//			cancel.setPlaceId(getOperPlaceId());
//			cancel.setOperNo(getOperator().getStaffNo());
//			cancel.setOperName(getOperator().getUserName());
//			cancel.setPlaceNo(getOperPlace().getCusPointCode());
//			cancel.setPlaceName(getOperPlace().getCusPointName());


			ServiceApp serviceApp = new ServiceApp();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("8");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());
			//
			//DarkList darkList = darkListDao.findByCardNo(strs[1]);
			//无卡注销
			//saveDarkList(accountCInfo,darkList,"10", "1");
//			blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
//					, "2", accardBussiness.getOperId(), null, null,
//					accardBussiness.getPlaceId(), null, null, 
//					new Date());
			serviceAppDao.save(serviceApp);


			String flag = accountCStopCardService.saveStopJointCard(serviceFlowRecord, accountCBussiness, cancel, customer, accountCInfo, SystemTypeEnum.ACMS.getValue());

			if (!"true".equals(flag)) {
				map.put("cardNo", strs[1]);
				map.put("meno", "卡片注销失败");
				saveImportInfo(fileImport, strs[1], strs[2], "07", null, "卡片注销失败", "1");
				cardList.add(map);
				return false;
			} // if
			saveImportInfo(fileImport, strs[1], strs[2], "07", null, null, "0");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
			VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			CardHolder cardHolder = cardHolderDao.findByAccountCId(accountCInfo.getId().toString());
			Customer newCustomer = new Customer();
			newCustomer.setUserNo(cardHolder.getUserNo());
			newCustomer.setOrgan(cardHolder.getName());
			newCustomer.setAgentName(cardHolder.getAgentName());

			// 用户卡信息上传及变更
			realTransferService.accountCInfoTransfer(newCustomer, accountCInfo,
					vehicleInfo, CardStatusEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu.UPDATE
							.getCode());

			// 用户卡黑名单上传及变更
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new
					Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
					.EN_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030

			return true;
		} catch (Exception e) {
			saveImportInfo(fileImport, strs[1], strs[2], "07", null, "联营卡注销失败,数据回滚", "1");
			throw new ApplicationException("联营卡注销失败");
		}

	}


	/**
	 * 黑名单解除
	 *
	 * @author gaosiling
	 */
	private boolean saveRemoveDarkService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "10", strs[4], "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
		if (accountCInfo == null) {
			map.put("cardNo", strs[1]);
			map.put("meno", "联营卡未办理开通申请");
			saveImportInfo(fileImport, strs[1], strs[2], "10", strs[4], "联营卡未办理开通", "1");
			cardList.add(map);
			return false;
		}
		if ("15".equals(accountCInfo.getState())) {
			map.put("cardNo", strs[1]);
			map.put("meno", "联营卡未办理开通");
			saveImportInfo(fileImport, strs[1], strs[2], "10", strs[4], "联营卡未办理开通", "1");
			cardList.add(map);
			return false;
		}
		try {
//--------------------------------------------------------
			//手工下发黑名单不对卡签状态进行修改
//			AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
//			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
//			accountCInfoHis.setGenReason("14");
//			accountCInfoHisDao.save(accountCInfo,accountCInfoHis);
//			accountCInfo.setState("0");
//			accountCInfo.setHisSeqId(accountCInfoHis.getId());
//			accountCInfoDao.update(accountCInfo);
			
			/*TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
			TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,1, new Date(),0, new Date());
			saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);*/


			//
			//DarkList darkList = darkListDao.findByCardNo(strs[1]);
			//挂失
			//saveDarkList(accountCInfo,darkList,"1", "0");
			blackListService.saveRelieveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
					, "2", fileImport.getOperNo(), null, null,
					fileImport.getPlaceNo(), null, null,
					new Date());
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(fileImport.getCustomerId());
			accardBussiness.setCardNo(strs[1]);
			accardBussiness.setState("9");
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setLastState("13");
			accardBussiness.setMemo("文件导入黑名单解除");
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setOperId(fileImport.getOperNo());
			accardBussiness.setPlaceId(fileImport.getPlaceNo());
			accardBussinessDao.save(accardBussiness);

			ServiceApp serviceApp = new ServiceApp();
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("8");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());

			serviceAppDao.save(serviceApp);
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
//			serviceWater.setSerType("220");//301电子标签发行

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 文件导入手工黑名单解除");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);
			saveImportInfo(fileImport, strs[1], strs[2], "10", null, null, "0");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new Date(),
					CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
							.EX_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage() + "手工解除黑名单导入，文件导入异常处理，导入卡号" + strs[1] + "对应数据异常");
			saveImportInfo(fileImport, strs[1], strs[2], "10", strs[4], null, "1");
			throw new ApplicationException("手工解除黑名单导入，文件导入异常处理");
		}
	}

	/**
	 * 黑名单导入
	 *
	 * @author gaosiling
	 */
	private boolean saveDarkService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2, 4)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "09", strs[4], "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		try {

			Map<String, String> map = new HashMap<String, String>();
			AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
			if (accountCInfo == null) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通申请");
				saveImportInfo(fileImport, strs[1], strs[2], "09", strs[4], "联营卡未办理开通", "1");
				cardList.add(map);
				return false;
			}
//			if("15".equals(accountCInfo.getState())){
//				map.put("cardNo", strs[1]);
//				map.put("meno", "联营卡未办理开通");
//				saveImportInfo(fileImport,strs[1],strs[2],"09",strs[4],"联营卡未办理开通","1");
//				cardList.add(map);
//				return false;
//			}
			if ("1".equals(accountCInfo.getState()) || "2".equals(accountCInfo.getState())) {
				map.put("cardNo", strs[1]);
				map.put("meno", "该联营卡已在黑名单列表里面无需重复加入");
				saveImportInfo(fileImport, strs[1], strs[2], "09", strs[4], "该联营卡已在黑名单列表里面无需重复加入", "1");
				cardList.add(map);
				return false;
			}

			//手工下发黑名单不对卡签状态进行修改
//			AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
//			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
//			accountCInfoHis.setGenReason("13");
//			accountCInfoHisDao.save(accountCInfo,accountCInfoHis);
//			accountCInfo.setState("12");
//			accountCInfo.setHisSeqId(accountCInfoHis.getId());
//			accountCInfoDao.update(accountCInfo);

			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			if (carObuCardInfo != null) {
				carObuCardInfo.setAccountCID(null);
				carObuCardInfoDao.update(carObuCardInfo);
			}


			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(fileImport.getCustomerId());
			accardBussiness.setCardNo(strs[1]);
			//accardBussiness.setState(strs[4]);//无卡终止使用
			// 营改增发现bug
			accardBussiness.setState(strs[0]);//无卡终止使用
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setTradeTime(new Date());
//			accardBussiness.setLastState(strs[4]);
			accardBussiness.setMemo(strs[4]);
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setOperId(fileImport.getOperNo());
			accardBussiness.setPlaceId(fileImport.getPlaceNo());
			accardBussinessDao.save(accardBussiness);

			ServiceApp serviceApp = new ServiceApp();
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("3");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());

			//
//			DarkList darkList = darkListDao.findByCardNo(strs[1]);
			//挂失
//			saveDarkList(accountCInfo,darkList,"5", "1");
			// 营改增发现bug
			/*blackListService.saveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
					, strs[4], accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null,
					new Date());*/
			blackListService.saveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
					, "4", accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null,
					new Date());

			serviceAppDao.save(serviceApp);
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
//			serviceWater.setSerType("220");//301电子标签发行

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 文件导入手工黑名单导入");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);
			saveImportInfo(fileImport, strs[1], strs[2], "09", null, null, "0");

			//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171030
			noRealTransferService.blackListTransfer(accountCInfo.getCardNo(), new Date(),
					CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
							.EN_BLACK.getCode());
			//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171030

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage() + "手工添加黑名单导入，文件导入异常处理，导入卡号" + strs[1] + "对应数据异常");
			saveImportInfo(fileImport, strs[1], strs[2], "09", strs[4], "卡片异常", "1");
			throw new ApplicationException("手工添加黑名单导入，文件导入异常处理");
		}
	}

	@Resource
	private LianCardTitleDao lianCardTitleDao;

	/**
	 * 发票抬头
	 *
	 * @author luohuanfa
	 */
	private boolean saveTitleService(FileImport fileImport, String[] strs, AccountCApply accountCApply, SubAccountInfo subAccountInfo, List<Map<String, String>> cardList) {
		//检查输入格式
		if (!checkStrFormat(strs, 1, 2, 7)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", strs[1]);
			map.put("meno", "输入数据格式错误");
			saveImportInfo(fileImport, strs[1], strs[2], "13", strs[4], "输入数据格式错误", "1");
			cardList.add(map);
			return false;
		}
		try {
			//前面已经验证了输入的格式，所以直接用
			//TODO 首先检验卡号段，这个之后再来做
			Map<String, String> map = new HashMap<String, String>();
			AccountCInfo accountCInfo = accountCDao.findByCardNoAndCustomerId(strs[1], fileImport.getCustomerId());
			if (accountCInfo == null) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通申请");
				saveImportInfo(fileImport, strs[1], strs[2], "13", strs[4], "联营卡未办理开通", "1");
				cardList.add(map);
				return false;
			}
			if ("15".equals(accountCInfo.getState())) {
				map.put("cardNo", strs[1]);
				map.put("meno", "联营卡未办理开通");
				saveImportInfo(fileImport, strs[1], strs[2], "13", strs[4], "联营卡未办理开通", "1");
				cardList.add(map);
				return false;
			}
			//假定卡号段符合，那么开始记录就好 strs[1]是卡号 strs[2]是业务处理时间 strs[7]是发票抬头
			lianCardTitleDao.saveTitle(strs[1], strs[2], new Date().toString(), strs[7]);


			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
//			accountCInfoHis.setGenReason("13");
			accountCInfoHisDao.save(accountCInfo, accountCInfoHis);
//			accountCInfo.setState("12");
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);

			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			if (carObuCardInfo != null) {
				carObuCardInfo.setAccountCID(null);
				carObuCardInfoDao.update(carObuCardInfo);
			}


//			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
//			AccardBussiness accardBussiness = new AccardBussiness();
//			accardBussiness.setId(seq);
//			accardBussiness.setCustomerId(fileImport.getCustomerId());
//			accardBussiness.setCardNo(strs[1]);
//			accardBussiness.setState("10");//无卡终止使用
//			accardBussiness.setRealPrice(new BigDecimal("0"));
//			accardBussiness.setTradeTime(new Date());
////			accardBussiness.setLastState("12");
//			accardBussiness.setMemo(strs[4]);
//			accardBussiness.setReceiptPrintTimes(0);
//			accardBussiness.setOperId(fileImport.getOperNo());
//			accardBussiness.setPlaceId(fileImport.getPlaceNo());
//			accardBussinessDao.save(accardBussiness);

			ServiceApp serviceApp = new ServiceApp();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
			serviceApp.setId(seq);
			serviceApp.setPlaceType("7");
			serviceApp.setHandleType("3");
			serviceApp.setAccode(strs[1]);
			serviceApp.setBaccount(accountCApply.getBankAccount());
			serviceApp.setBankName(accountCApply.getBankName());
			serviceApp.setPlaceId(fileImport.getPlaceNo());
			serviceApp.setBalance(new BigDecimal("0"));
			serviceApp.setSerDate(new Date());
			serviceApp.setOperId(fileImport.getOperNo());


			serviceAppDao.save(serviceApp);
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);
			Long customerid = fileImport.getCustomerId();
			Customer customer = customerService.findById(customerid);
			if (customer != null) serviceWater.setCustomerId(customer.getId());
			if (customer != null) serviceWater.setUserNo(customer.getUserNo());
			if (customer != null) serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(strs[1]);
			serviceWater.setSerType("220");//301电子标签发行

			serviceWater.setOperNo(fileImport.getOperNo() + "");
			serviceWater.setPlaceId(fileImport.getPlaceNo());
			serviceWater.setPlaceNo(fileImport.getPlaceNo() + "");
			serviceWater.setRemark("联营卡系统: 文件导入发票抬头修改");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);
			saveImportInfo(fileImport, strs[1], strs[2], "13", null, null, "0");
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage() + "更改发票抬头失败" + strs[1] + "对应数据异常");
			saveImportInfo(fileImport, strs[1], strs[2], "13", strs[4], "卡片异常", "1");
			throw new ApplicationException("更改发票抬头失败");
		}
	}


	public void saveImportInfo(FileImport fileImport, String cardNo, String dealTime, String handleType, String genGau, String errorReason, String importResult) {
		//不进行回滚
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dealDate = null;
		try {
			dealDate = sdf.parse(dealTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ImportInfo importInfo = new ImportInfo();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSIMPORTINFO_NO");
		importInfo.setId(seq);
		importInfo.setMainId(fileImport.getCustomerId());
		importInfo.setOperId(fileImport.getOperNo());
		importInfo.setPlaceId(fileImport.getPlaceNo());
		importInfo.setHandleType(handleType);
		importInfo.setOperateTime(dealDate);
		importInfo.setImportTime(new Date());
		importInfo.setImportResult(importResult);
		importInfo.setCardCode(cardNo);
		importInfo.setErrorReason(errorReason);
		importInfo.setGenCause(genGau);
		if (fileImport.getFileName().contains("\\")) {
			String[] fileMsgArray = fileImport.getFileName().split("\\\\");
			importInfo.setFileName(fileMsgArray[fileMsgArray.length - 1]);
		} else {
			importInfo.setFileName(fileImport.getFileName());
		}
		importInfoDao.save(importInfo);
	}


	/**
	 * 文件导入异常查询
	 *
	 * @param pager
	 * @param importException
	 * @param customer
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author gaosiling
	 */
	@Override
	public Pager findByPage(Pager pager, ImportInfo importInfo, Customer customer, Date startTime, Date endTime) {
		return importInfoDao.findByPage(pager, importInfo, customer, startTime, endTime);
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
				darkListDao.save(darkList);

			} else {
				darkList.setCustomerId(accountCInfo.getCustomerId());
				darkList.setUserNo(customer.getUserNo());
				darkList.setUserName(customer.getOrgan());
				darkList.setGencau(genCau);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存清算黑名单数据失败" + accountCInfo.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败" + accountCInfo.getCardNo());
		}
	}


	public boolean checkStrFormat(String[] strs, int... nums) {
		for (int i = 0; i < nums.length; i++) {
			if (!oneStrFormat(strs, i)) {
				return false;
			}
		}
		return true;
	}

	//密码重设("04"),挂失("05"),解挂("06"),无卡注销("07"),黑名单添加("09"),黑名单解除("10"),发票抬头("13")
	//业务类型	 联营卡卡号	业务处理时间 重设密码	黑名单产生原因	发票邮寄开始日期	发票邮寄结束日期	联营卡帐号（发票抬头修改）	旧卡卡号
	public boolean oneStrFormat(String[] strs, int no) {
		switch (no) {
			case 0:
				if (strs[0].matches("")) {
					return true;
				}
			case 1:
				if (strs[1].matches("^[0-9]{16}$")) {
					return true;
				}
				break;
			case 2:
				if (strs[2].matches("\\d{4}-\\d{2}-\\d{2}\\s\\d+:\\d+:\\d+")) {
					return true;
				}
				break;
			case 3:
				if (true || strs[3].matches("")) {
					return true;
				}
				break;
			case 4:
				if (true || strs[4].matches("")) {
					return true;
				}
				break;
			case 5:
				if (strs[5].matches("\\d{4}-\\d{2}-\\d{2}\\s\\d+:\\d+:\\d+")) {
					return true;
				}
				break;
			case 6:
				if (strs[6].matches("\\d{4}-\\d{2}-\\d{2}\\s\\d+:\\d+:\\d+")) {
					return true;
				}
				break;
			case 7:
				if (true || strs[6].matches("")) {
					return true;
				}
				break;
			case 8:
				if (true || strs[6].matches("")) {
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}


}
