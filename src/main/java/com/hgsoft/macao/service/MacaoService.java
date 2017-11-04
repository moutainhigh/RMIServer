package com.hgsoft.macao.service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.MacaoIdCardTypeEnum;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.AcLossInfoDao;
import com.hgsoft.macao.dao.CarUserCardInfoDao;
import com.hgsoft.macao.dao.CardHolderInfoDao;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.dao.MacaoReqRecordDao;
import com.hgsoft.macao.dao.NotifyMCRecordDao;
import com.hgsoft.macao.dao.NotifyMCRecordHisDao;
import com.hgsoft.macao.entity.AcLossInfo;
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoAddCarReq;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCancleReqInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.MacaoCardCustomerHis;
import com.hgsoft.macao.entity.MacaoLostReq;
import com.hgsoft.macao.entity.MacaoReqRecord;
import com.hgsoft.macao.entity.NotifyMCRecord;
import com.hgsoft.macao.entity.NotifyMCRecordHis;
import com.hgsoft.macao.entity.ReqInterfaceFlow;
import com.hgsoft.macao.serviceInterface.IMacaoService;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.unifiedInterface.service.CustomerUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MacaoService implements IMacaoService {
	private static Logger logger = Logger.getLogger(MacaoService.class.getName());
	@Resource
	private IAccountCService accountCService;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private CustomerUnifiedInterfaceService customerUnifiedInterfaceService;
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CarUserCardInfoDao carUserCardInfoDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	private CardHolderInfoDao cardHolderInfoDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AcLossInfoDao acLossInfoDao;
	@Resource
	private NotifyMCRecordDao notifyMCRecordDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;

	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private MacaoReqRecordDao macaoReqRecordDao;
	@Resource
	private NotifyMCRecordHisDao notifyMCRecordHisDao;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
	@Resource
	private RealTransferService realTransferService;
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

	/**
	 * 增加接口请求流水
	 */
	@Override
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow) {
		try {
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSREQINTERFACEFLOW_NO");
			reqInterfaceFlow.setId(seq);
			macaoDao.saveReqInterfaceFlow(reqInterfaceFlow);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "IVR服务：增加接口请求流水记录失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：增加接口请求流水记录失败");
		}
	}

	/*@Override
	public MacaoCardCustomer findByCardAccount(String accountNo) {
		return macaoDao.findByCardAccount(accountNo);
	}*/
	@Override
	public MacaoCardCustomer findMacao(MacaoCardCustomer macaoCard) {
		return macaoDao.findMacao(macaoCard);
	}
	/**
	 * 根据条件查找车用户卡片绑定表对象
	 */
	/*@Override
	public CarUserCardInfo find(CarUserCardInfo carUserCardInfo) {
		return carUserCardInfoDao.find(carUserCardInfo);
	}*/


	/**
	 * 粤通卡开户通知
	 */
	@Override
	public Map<String, String> saveOpenCardNotify(MacaoCardCustomer macaoCardCustomer) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//先判断是否已经有持卡人信息记录
			MacaoCardCustomer temp = new MacaoCardCustomer();
			temp.setIdCardType(macaoCardCustomer.getIdCardType());
			temp.setIdCardNumber(macaoCardCustomer.getIdCardNumber());
			MacaoCardCustomer oldMacaoCustomer = macaoDao.findMacao(temp);
			Long id = sequenceUtil.getSequenceLong("SEQ_MACAO_CARD_CUSTOMER_NO");

			//新增澳门通银行账号信息
			MacaoBankAccount macaoBankAccount = new MacaoBankAccount();
			macaoBankAccount.setBankCode(macaoCardCustomer.getBankCode());
			macaoBankAccount.setBranchId(macaoCardCustomer.getBranchId());
			macaoBankAccount.setBankAccountNumber(macaoCardCustomer.getBankAccountNumber());

			if (oldMacaoCustomer == null) {
				macaoCardCustomer.setId(id);

				macaoCardCustomer.setBankAccountNumber(null);
				macaoCardCustomer.setBankCode(null);
				macaoCardCustomer.setBranchId(null);

				macaoDao.saveMacaoCardCustomer(macaoCardCustomer);
			} else {
				//是否要更新
				//TODO
			}

			//银行账号信息
			macaoBankAccount.setId(sequenceUtil.getSequenceLong("SEQ_CSMSMACAOBANKACCOUNT"));
			if (oldMacaoCustomer == null) macaoBankAccount.setMainId(id);//持卡人id
			else macaoBankAccount.setMainId(oldMacaoCustomer.getId());
			macaoBankAccount.setReqTime(macaoCardCustomer.getReqTime());
			macaoBankAccount.setReqOperTime(macaoCardCustomer.getReqOperTime());
			macaoBankAccount.setReqOperId(macaoCardCustomer.getReqOperId());
			macaoBankAccount.setReqOperNo(macaoCardCustomer.getReqOperNo());
			macaoBankAccount.setReqOperName(macaoCardCustomer.getReqOperName());
			macaoBankAccount.setReqPlaceId(macaoCardCustomer.getReqPlaceId());
			macaoBankAccount.setReqPlaceNo(macaoCardCustomer.getReqPlaceNo());
			macaoBankAccount.setReqPlaceName(macaoCardCustomer.getReqPlaceName());
			macaoBankAccount.setResSN(macaoCardCustomer.getResSN());
			macaoBankAccount.setReqSN(macaoCardCustomer.getReqSN());
			macaoBankAccount.setResTime(macaoCardCustomer.getResTime());

			macaoDao.saveMacaoBankAccount(macaoBankAccount);

			Customer tempCus = new Customer();
			tempCus.setSystemType(SystemTypeEnum.NO.getValue());
			Customer macaoCustomer = customerDao.find(tempCus);
			if (macaoCustomer == null) macaoCustomer = new Customer();
			//保存流水
			ServiceWater serviceWater = new ServiceWater(null, macaoCustomer.getId(), macaoCustomer.getUserNo(), macaoCustomer.getOrgan(), null,
					null, null, null, macaoBankAccount.getBankAccountNumber(), new BigDecimal("0"), new BigDecimal("0"),
					null, null, null, null, null,
					null, null, null, null,
					null, null, macaoCardCustomer.getReqOperId(), macaoCardCustomer.getReqPlaceId(), macaoCardCustomer.getReqOperNo(),
					macaoCardCustomer.getReqOperName(), macaoCardCustomer.getReqPlaceNo(),
					macaoCardCustomer.getReqPlaceName(), macaoCardCustomer.getResTime(), "澳门通开户申请", macaoCardCustomer.getId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("107");//107开户申请
			serviceWaterDao.save(serviceWater);

			resultMap.put("result", "true");
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "澳门通服务：粤通卡开户失败");
			e.printStackTrace();
			throw new ApplicationException("澳门通服务：粤通卡开户失败");
		}
		return resultMap;
	}

	/**
	 * 澳门通挂失通知
	 */
	@Override
	public Map<String, String> saveLossCard(MacaoLostReq macaoLostReq, AccountCInfo accountCInfo, String systemType) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//accountCService.saveLock(accountCInfo, systemType);
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
				accountCBussiness.setState("3");
				accountCBussiness.setOperId(accountCInfo.getIssueOperId());
				accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
				accountCBussiness.setOperName(accountCInfo.getOperName());
				accountCBussiness.setOperNo(accountCInfo.getOperNo());
				accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
				accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
				accountCBussiness.setTradeTime(new Date());
				accountCBussiness.setRealPrice(new BigDecimal("0"));

				//生成粤通响应信息
				Date resTime = new Date();
				//String resSN = "2016112219931111111111";

				//增加澳门通挂失申请表记录
				Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMACAOLOSTREQ");
				macaoLostReq.setId(id);
				macaoLostReq.setRestime(resTime);
				macaoLostReq.setIsNotify("0");

				macaoDao.saveMacaoLostReq(macaoLostReq);

				//记帐卡挂失
				blackListService.saveCardLost(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), accountCBussiness.getTradeTime()
						, "2", accountCBussiness.getOperId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(),
						accountCBussiness.getPlaceId(), accountCBussiness.getPlaceNo(), accountCBussiness.getPlaceName(),
						new Date());

				MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(accountCInfo.getCardNo());

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
				cardObuService.saveCardStateInfo(accountCBussiness.getTradeTime(), Integer.parseInt(CardStateSendStateFlag.loss.getValue()),
						CardStateSendSerTypeEnum.loss.getValue(), accountCInfo.getCardNo(), "23", userType);


				//增加清算数据
				String date = format.format(resTime);
				AcLossInfo acLossInfo = new AcLossInfo("91001", date, accountCInfo.getCardNo(), "", "");
				Long acLossInfIid = sequenceUtil.getSequenceLong("seq_csmsaclossinfo_no");
				acLossInfo.setId(acLossInfIid);
				acLossInfoDao.save(acLossInfo);

				//回执打印用
				//TODO
				//accountCBussiness.setBusinessId(accountCInfo.getId());
				if (accountCApply != null)
					accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

				accountCBussinessDao.save(accountCBussiness);

				Customer macaoCustomer = customerDao.findById(accountCInfo.getCustomerId());
				MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(accountCInfo.getCardNo());
				if (macaoCustomer == null) macaoCustomer = new Customer();
				if (macaoBankAccount == null) macaoBankAccount = new MacaoBankAccount();
				//if(macaoCardCustomer == null) macaoCardCustomer = new MacaoCardCustomer(); 
				//保存流水
				ServiceWater serviceWater = new ServiceWater(null, accountCInfo.getCustomerId(), macaoCustomer.getUserNo(), macaoCustomer.getOrgan(), accountCInfo.getCardNo(),
						null, null, null, macaoBankAccount.getBankAccountNumber(), new BigDecimal("0"), new BigDecimal("0"),
						null, null, null, null, null,
						null, accountCBussiness.getId(), null, null,
						null, null, accountCInfo.getIssueOperId(), accountCInfo.getIssuePlaceId(), accountCInfo.getOperNo(),
						accountCInfo.getOperName(), accountCInfo.getPlaceNo(),
						accountCInfo.getOperName(), new Date(), "澳门通挂失粤通卡", macaoCardCustomer.getId());
				serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
				serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setSerType("209");//209澳门通挂失粤通卡
				serviceWaterDao.save(serviceWater);

				resultMap.put("result", "true");
				//resultMap.put("resSN", resSN);
				resultMap.put("resTime", format.format(resTime));

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
				Customer customer = new Customer();
				customer.setUserNo(macaoCardCustomer.getUserNo());
				customer.setOrgan(macaoCardCustomer.getCnName());
				customer.setAgentName(macaoCardCustomer.getAgentName());
				// 调用用户卡信息上传及变更接口
				realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo, CardStatusEmeu.CARD_LOSS.getCode(), OperationTypeEmeu
						.UPDATE.getCode());

				// 调用用户卡黑名单上传及变更接口
				noRealTransferService.blackListTransfer(accountCInfo.getCardNo(),
						new Date(), CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.EN_BLACK.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019

				return resultMap;
			}
			resultMap.put("result", "false");
			//resultMap.put("resSN", resSN);
			resultMap.put("resTime", format.format(new Date()));
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "澳门通服务：粤通卡挂失失败");
			e.printStackTrace();
			throw new ApplicationException("澳门通服务：粤通卡挂失失败");
		}
		return resultMap;
	}

	/**
	 * 澳门通卡开通列表查询
	 */
	@Override
	public Pager findOpenList(Pager pager, MacaoCardCustomer macaoCardCustomer, String startTime, String endTime) {

		return null;
	}

	/**
	 * 澳门通车辆新增通知
	 */
	@Override
	public Map<String, String> saveCar(MacaoAddCarReq macaoAddCarReq, VehicleInfo vehicleInfo, Customer customer, MacaoBankAccount macaoBankAccount) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//新增车辆
			vehicleInfo.setCustomerID(customer.getId());
			vehicleInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfo_NO"));
			vehicleInfoDao.save(vehicleInfo);

			//新增车辆、用户、卡关联表记录
			/*CarUserCardInfo carUserCardInfo = new CarUserCardInfo();
			carUserCardInfo.setVehicleId(vehicleInfo.getId());
			carUserCardInfo.setMacaoCardCustomerId(macaoCardCustomer.getId());
			carUserCardInfoDao.save(carUserCardInfo);*/

			//已修改为：
			CardHolderInfo cardHolderInfo = new CardHolderInfo();
			Long holderId = sequenceUtil.getSequenceLong("SEQ_CSMSCARDHOLDERINFO_NO");
			cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
			cardHolderInfo.setId(holderId);
			cardHolderInfo.setType("1");//1 车辆
			cardHolderInfo.setTypeId(vehicleInfo.getId());
			cardHolderInfoDao.save(cardHolderInfo);


			//
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setVehicleID(vehicleInfo.getId());
			carObuCardInfoDao.save(carObuCardInfo);

			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			vehicleBussiness.setCustomerID(customer.getId());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			//vehicleBussiness.setType("11");
			vehicleBussiness.setType(VehicleBussinessEnum.addVehicle.getValue());
			vehicleBussiness.setOperID(vehicleInfo.getOperId());
			vehicleBussiness.setOperNo(vehicleInfo.getOperNo());
			vehicleBussiness.setOperName(vehicleInfo.getOperName());
			vehicleBussiness.setPlaceID(vehicleInfo.getPlaceId());
			vehicleBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
			vehicleBussiness.setPlaceName(vehicleInfo.getPlaceName());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆新增");

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);

			//新增澳门通新增车辆请求表记录
			//生成粤通响应信息
			Date resTime = new Date();
			//String resSN = "2016112219931111111111";

			//增加澳门通挂失申请表记录
			Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMACAOADDCARREQ");
			macaoAddCarReq.setId(id);
			macaoAddCarReq.setRestime(resTime);
			//macaoAddCarReq.setResSN(resSN);
			macaoAddCarReq.setIsNotify("0");

			macaoDao.saveMacaoAddCarReq(macaoAddCarReq);

			MacaoCardCustomer macaoCardCustomer = macaoDao.findById(macaoBankAccount.getMainId());
			if (macaoCardCustomer == null) macaoCardCustomer = new MacaoCardCustomer();
			//保存流水
			ServiceWater serviceWater = new ServiceWater(null, vehicleInfo.getCustomerID(), customer.getUserNo(), customer.getOrgan(), null,
					null, null, null, macaoBankAccount.getBankAccountNumber(), new BigDecimal("0"), new BigDecimal("0"),
					null, null, null, null, null,
					null, null, null, null,
					null, null, vehicleInfo.getOperId(), vehicleInfo.getPlaceId(), vehicleInfo.getOperNo(),
					vehicleInfo.getOperName(), vehicleInfo.getPlaceNo(),
					vehicleInfo.getPlaceName(), new Date(), "澳门通新增车辆", macaoCardCustomer.getId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("108");//108新增车辆
			serviceWaterDao.save(serviceWater);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			vehicleInfo.setUserNo(macaoCardCustomer.getUserNo());
			vehicleInfoDao.save(vehicleInfo);
			Customer newCustomer = new Customer();
			newCustomer.setUserNo(macaoCardCustomer.getUserNo());
			newCustomer.setOrgan(macaoCardCustomer.getCnName());
			newCustomer.setAgentName(macaoCardCustomer.getReqOperName());
			realTransferService.vehicleInfoTransfer(vehicleInfo, newCustomer, OperationTypeEmeu.ADD
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

			resultMap.put("result", "true");
			//resultMap.put("resSN", resSN);
			resultMap.put("resTime", format.format(resTime));
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "澳门通服务：粤通卡车辆新增失败");
			e.printStackTrace();
			throw new ApplicationException("澳门通服务：粤通卡车辆新增失败");
		}
		return resultMap;
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public Pager findCustomerByPage(Pager pager, MacaoCardCustomer macaocardcustomer) {
		return macaoDao.findByPageAndId(pager, macaocardcustomer);
	}

	/**
	 * 根据id查询用户
	 */
	@Override
	public MacaoCardCustomer findById(Long id) {
		return macaoDao.findById(id);
	}

	/*
	 * 保存销户申请记录
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoService#saveCancleCardCustomer(com.hgsoft.macao.entity.MacaoCancleReqInfo)
	 */
	@Override
	public Map<String, String> saveCancleCardCustomer(MacaoCancleReqInfo macaoCancleReqInfo) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			macaoCancleReqInfo.setId(sequenceUtil.getSequenceLong("SEQ_MACAOCANCLEREQ_INFO_NO"));

			//生成粤通响应信息
			Date resTime = new Date();
			//String resSN = "2016112219931111111111";

			//macaoCancleReqInfo.setResSN(resSN);
			macaoCancleReqInfo.setRestime(resTime);
			macaoCancleReqInfo.setState("0");
			macaoDao.saveMacaoCancleReqInfo(macaoCancleReqInfo);

			Customer tempCus = new Customer();
			tempCus.setSystemType(SystemTypeEnum.NO.getValue());
			Customer macaoCustomer = customerDao.find(tempCus);
			MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(macaoCancleReqInfo.getEtcCardNumber());
			MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(macaoCancleReqInfo.getEtcCardNumber());
			if (macaoCustomer == null) macaoCustomer = new Customer();
			if (macaoBankAccount == null) macaoBankAccount = new MacaoBankAccount();
			if (macaoCardCustomer == null) macaoCardCustomer = new MacaoCardCustomer();
			//保存流水
			ServiceWater serviceWater = new ServiceWater(null, macaoCustomer.getId(), macaoCustomer.getUserNo(), macaoCustomer.getOrgan(), macaoCancleReqInfo.getEtcCardNumber(),
					null, null, null, macaoBankAccount.getBankAccountNumber(), new BigDecimal("0"), new BigDecimal("0"),
					null, null, null, null, null,
					null, null, null, null,
					null, null, macaoCancleReqInfo.getReqOperId(), macaoCancleReqInfo.getReqPlaceId(), macaoCancleReqInfo.getReqOperNo(),
					macaoCancleReqInfo.getReqOperName(), macaoCancleReqInfo.getReqPlaceNo(),
					macaoCancleReqInfo.getReqOperName(), resTime, "粤通卡销户申请", macaoCardCustomer.getId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("210");//210粤通卡销户申请
			serviceWaterDao.save(serviceWater);

			resultMap.put("result", "true");
			//resultMap.put("resSN", resSN);
			resultMap.put("resTime", format.format(resTime));
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "澳门通服务：粤通卡销户失败");
			e.printStackTrace();
			throw new ApplicationException("澳门通服务：粤通卡销户失败");
		}
		return resultMap;
	}

	/**
	 * 修改客户信息
	 */
	@Override
	public void updateCustomer(MacaoCardCustomer macaoCardCustomer, MacaoCardCustomerHis macaoCardCustomerHis, Customer customer, CustomerBussiness customerBussiness) {
		try {
			//客户历史信息
			Long seq_macao_card_customer_his_no = sequenceUtil.getSequenceLong("seq_macao_card_customer_his_no");
			macaoCardCustomerHis.setId(seq_macao_card_customer_his_no);
			//存旧客户的历史id
			customerBussiness.setOldcustomerId(macaoCardCustomer.getHisSeqId());
			//设置新历史id
			macaoCardCustomer.setHisSeqId(macaoCardCustomerHis.getId());
			//存新客户历史id
			macaoCardCustomerHis.setMacaoCustomerId(macaoCardCustomer.getHisSeqId());
			macaoDao.saveMacaoCardCustomerHis(macaoCardCustomerHis);


			//如果修改客户信息的时候，有修改idCode，图片资料表的code也要修改
//			Customer oldCustomer = customerDao.findById(customer.getId());
//			if(!oldCustomer.getIdCode().equals(customer.getIdCode())){
//				Material material = new Material();
//				material.setCustomerID(customer.getId());
//				List<Material> materials = materialDao.findMateria(material);
//				for(Material m:materials){
//					m.setCode(customer.getIdCode());
//					materialDao.updateMateria(m);
//				}
//				
//			}
			macaoDao.update(macaoCardCustomer);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			//存为大客户ID
			customerBussiness.setCustomerId(customer.getId());
//			customerBussiness.setCustomerId(macaoCardCustomer.getId());

			customerBussiness.setType(CustomerBussinessTypeEnum.customerUpdate.getValue());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			//保存流水记录
			ServiceWater serviceWater = new ServiceWater(null, customerBussiness.getCustomerId(), null, null, null,
					null, null, customerBussiness.getType(), macaoCardCustomer.getBankAccountNumber(), null, null, null,
					null, null, customerBussiness.getId(), null, null,
					null, null, null, null, null, customerBussiness.getOperId(), customerBussiness.getPlaceId(), customerBussiness.getOperNo(),
					customerBussiness.getOperName(), customerBussiness.getPlaceNo(),
					customerBussiness.getPlaceName(), customerBussiness.getCreateTime(), "客户信息修改",
					macaoCardCustomer.getId());

			Long id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(id);
			serviceWater.setSerType("104");
			serviceWaterDao.save(serviceWater);


		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "客户修改失败，客户id:" + macaoCardCustomer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户修改失败，客户id:" + macaoCardCustomer.getId());
		}

	}

	@Override
	public Pager requestVehicleInfo(Pager pager, String organ, String idType, String idCode, String vehicleColor, String vehiclePlate) {
		return macaoDao.getVehicleInfo(pager, organ, idType, idCode, vehicleColor, vehiclePlate);
	}

	@Override
	public VehicleInfo requestVehicleDetailInfo(String viid) {
		VehicleInfo vehicleInfo = null;
		try {
			vehicleInfo = macaoDao.getVehicleDetailInfo(viid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleInfo;
	}

	@Override
	public String requestupdateVehicle(VehicleInfo vehicleInfo, CustomerBussiness customerBussiness) {
		try {
			Map<String, Object> tagAndAccountC = macaoDao.getTagAndAccountC(vehicleInfo.getId() + "");
			if (tagAndAccountC == null) {
				return "车辆不存在";
			}
			if (tagAndAccountC.get("TAGID") != null || tagAndAccountC.get("ACCOUNTCID") != null) {
				return "已绑定卡签，请先办理卡签挂起业务！";
			} else {
//				CustomerBussiness customerBussiness = new CustomerBussiness();
				Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
				VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
				BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
				vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
				vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
				vehicleInfoHis.setGenTime(new Date());
				vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
				customerBussiness.setVehicleHisId(vehicleInfoDao.findById(vehicleInfo.getId()).getHisSeqId());
				vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
				macaoDao.updateVehicleId(vehicleInfo);
				//macaoDao.updateMacaocarcustomerid(vehicleInfo.getId()+"");

				customerBussiness.setId(seq);
				customerBussiness.setVehicleId(vehicleInfo.getId());

//				customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
//				customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
//				customerBussiness.setOperId(vehicleInfo.getOperId());
//				customerBussiness.setOperNo(vehicleInfo.getOperNo());
//				customerBussiness.setOperName(vehicleInfo.getOperName());
//				customerBussiness.setPlaceId(vehicleInfo.getPlaceId());
//				customerBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
//				customerBussiness.setPlaceName(vehicleInfo.getPlaceName());
//				customerBussiness.setCreateTime(new Date());
				customerBussinessDao.save(customerBussiness);

				//保存流水记录
				MacaoCardCustomer macaoCardCustomer = macaoDao.getMacaoCardCustomerByVehicleId(vehicleInfo.getId());
				ServiceWater serviceWater = new ServiceWater(null, customerBussiness.getCustomerId(), null, null, null,
						null, null, customerBussiness.getType(), macaoCardCustomer.getBankAccountNumber(), null, null, null,
						null, null, customerBussiness.getId(), null, null,
						null, null, null, null, null, customerBussiness.getOperId(), customerBussiness.getPlaceId(), customerBussiness.getOperNo(),
						customerBussiness.getOperName(), customerBussiness.getPlaceNo(),
						customerBussiness.getPlaceName(), customerBussiness.getCreateTime(), "车辆信息修改",
						macaoCardCustomer.getId());

				Long id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(id);
				serviceWater.setSerType("103");
				serviceWaterDao.save(serviceWater);

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
				Customer customer = new Customer();
				customer.setUserNo(macaoCardCustomer.getUserNo());
				customer.setOrgan(macaoCardCustomer.getCnName());
				customer.setAgentName(macaoCardCustomer.getAgentName());
				realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu
						.UPDATE.getCode());
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171019
			}
			return "修改成功";
		} catch (Exception e) {
			return "修改失败";
		}
	}

	/**
	 * 根据卡号获取密码，并与前台传入的密码比较
	 */
	@Override
	public boolean oldPassIsTrue(Long id, String oldPass) {
		try {
			String pass = macaoDao.getOldPass(id);
			oldPass = StringUtil.md5(oldPass);
			return oldPass.equals(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据卡号获取 澳门通持卡人信息表 id
	 */
	public String getMccId(String cardNo) {
		String MccId = "";
		try {
			MccId = macaoDao.getMccId(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MccId;
	}

	/**
	 * 服务密码更改
	 *
	 * @return String
	 * @author lgm
	 */
	@Override
	public String updateOldPass(MacaoCardCustomer macaoCardCustomer, String oldPass, String newPass, String confirmNewPass, CustomerBussiness customerBussiness) {
		String message = null;
		try {
			Long macaoCardCustomerId = macaoCardCustomer.getId();
			boolean isTrue = oldPassIsTrue(macaoCardCustomerId, oldPass);
			if (isTrue == false) {
				message = "您输入的旧服务密码不正确，请重新输入！";
				return message;
			}
			if (newPass.equals("") || confirmNewPass.equals("") || !(newPass.equals(confirmNewPass))) {
				message = "新服务密码有误！";
				return message;
			}
//			String MccId = getMccId(cardNo);
			newPass = StringUtil.md5(newPass);
			macaoDao.updatePass(macaoCardCustomerId, newPass);

			//客户信息业务记录
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			customerBussiness.setId(seq);
			customerBussiness.setOldcustomerId(macaoCardCustomerId);//旧客户id用来存持卡人id
			customerBussinessDao.save(customerBussiness);

			//保存流水记录
			ServiceWater serviceWater = new ServiceWater(null, customerBussiness.getCustomerId(), null, null, null,
					null, null, customerBussiness.getType(), macaoCardCustomer.getBankAccountNumber(), null, null, null,
					null, null, customerBussiness.getId(), null, null,
					null, null, null, null, null, customerBussiness.getOperId(), customerBussiness.getPlaceId(), customerBussiness.getOperNo(),
					customerBussiness.getOperName(), customerBussiness.getPlaceNo(),
					customerBussiness.getPlaceName(), customerBussiness.getCreateTime(), "服务密码更改",
					macaoCardCustomer.getId());

			Long id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(id);
			serviceWater.setSerType("102");
			serviceWaterDao.save(serviceWater);
			message = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return message;

	}

	/**
	 * 获取客户信息
	 *
	 * @return MacaoCardCustomer
	 * @author lgm
	 */
	@Override
	public MacaoCardCustomer getCustomerByCardNo(String cardNo) {
		try {
			return macaoDao.getCustomerByCardNo(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 服务密码重设
	 *
	 * @return String
	 * @author lgm
	 */
	@Override
	public String resetPass(MacaoCardCustomer macaoCardCustomer, String newPass, String confirmNewPass, CustomerBussiness customerBussiness) {
		String message = null;
		try {
			Long macaoCardCustomerId = macaoCardCustomer.getId();
			if (newPass.equals("") || confirmNewPass.equals("") || !(newPass.equals(confirmNewPass))) {
				message = "新服务密码有误！";
				return message;
			}
			newPass = StringUtil.md5(newPass);
			macaoDao.updatePass(macaoCardCustomerId, newPass);

			//客户信息业务记录
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			customerBussiness.setId(seq);
			customerBussiness.setOldcustomerId(macaoCardCustomerId);//旧客户id用来存持卡人id
			customerBussinessDao.save(customerBussiness);

			//保存流水记录
			ServiceWater serviceWater = new ServiceWater(null, customerBussiness.getCustomerId(), null, null, null,
					null, null, customerBussiness.getType(), macaoCardCustomer.getBankAccountNumber(), null, null, null,
					null, null, customerBussiness.getId(), null, null,
					null, null, null, null, null, customerBussiness.getOperId(), customerBussiness.getPlaceId(), customerBussiness.getOperNo(),
					customerBussiness.getOperName(), customerBussiness.getPlaceNo(),
					customerBussiness.getPlaceName(), customerBussiness.getCreateTime(), "服务密码重设",
					macaoCardCustomer.getId());

			Long id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(id);
			serviceWater.setSerType("101");
			serviceWaterDao.save(serviceWater);

			message = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "重设密码失败";
			return message;
		}
		return message;
	}

	/**
	 * 找注销申请记录表记录
	 */
	@Override
	public MacaoCancleReqInfo findMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo) {
		return macaoDao.findMacaoCancleReqInfo(macaoCancleReqInfo);
	}

	/**
	 * 卡片旧密码验证
	 *
	 * @return String
	 * @author lgm
	 */
	@Override
	public String cardOldPassIsTrue(String cardNo, String oldPass) {
		String pass = "";
		String message = "";
		try {
			pass = macaoDao.getCardOldPass(cardNo);
			oldPass = StringUtil.md5(oldPass);
			if (oldPass.equals(pass))
				message = "true";
			else
				message = "false";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 卡片旧密码更改
	 *
	 * @return String
	 * @author lgm
	 */
	@Override
	public String updateCardOldPass(String cardNo, String oldPass, String newPass, String confirmNewPass, AccountCBussiness accountCBussiness) {
		String message = null;
		try {
			String isTrue = cardOldPassIsTrue(cardNo, oldPass);
			if (!("false".equals(isTrue)))
				message = "您输入的旧服务密码不正确，请重新输入！";
			if (newPass.equals("") || confirmNewPass.equals("") || !(newPass.equals(confirmNewPass)))
				message = "新服务密码有误！";

			//根据卡号获取记帐卡信息
			String id = getAccountCIdByCardNo(cardNo);
			AccountCInfo accountCInfo = macaoDao.getAccountCInfoById(id);
			//记帐卡业务记录
//			AccountCBussiness accountCBussiness = new AccountCBussiness();
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
//			accountCBussiness.setOperId(accountCInfo.getIssueOperId());
//			accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
//			accountCBussiness.setOperName(accountCInfo.getOperName());
//			accountCBussiness.setOperNo(accountCInfo.getOperNo());
//			accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
//			accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			//回执打印数据
			AccountCApply accountCApply = macaoDao.findByCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

			macaoDao.save(accountCBussiness);

			//MD5 = 卡号+交易密码
			accountCInfo.setTradingPwd(StringUtil.md5(newPass));
			//增加历史id
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			macaoDao.update(accountCInfo);

			//保存流水记录
			MacaoCardCustomer macaoCardCustomer = macaoDao.getCustomerByCardNo(accountCBussiness.getCardNo());
			ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), null, null, accountCBussiness.getCardNo(),
					null, null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(),
					null, null, null,
					null, null, null, null, null,
					accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
					accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
					accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "卡片旧密码更改", macaoCardCustomer.getId());

			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("204");
			serviceWaterDao.save(serviceWater);

			message = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return message;
	}

	/**
	 * 根据卡号获取 记帐卡信息表 id
	 *
	 * @author lgm
	 */
	public String getAccountCIdByCardNo(String cardNo) {
		String id = "";
		try {
			id = macaoDao.getAccountCIdByCardNo(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 重设记帐卡密码
	 *
	 * @author lgm
	 */
	@Override
	public String resetCardPass(String cardNo, String newPass, String confirmNewPass, AccountCBussiness accountCBussiness) {
		String message = null;
		try {
			if (newPass.equals("") || confirmNewPass.equals("") || !(newPass.equals(confirmNewPass)))
				message = "新服务密码有误！";

			//根据卡号获取记帐卡信息
			String id = getAccountCIdByCardNo(cardNo);
			AccountCInfo accountCInfo = macaoDao.getAccountCInfoById(id);
			//记帐卡业务记录
//			AccountCBussiness accountCBussiness = new AccountCBussiness();
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
//			accountCBussiness.setOperId(accountCInfo.getIssueOperId());
//			accountCBussiness.setPlaceId(accountCInfo.getIssuePlaceId());
//			accountCBussiness.setOperName(accountCInfo.getOperName());
//			accountCBussiness.setOperNo(accountCInfo.getOperNo());
//			accountCBussiness.setPlaceName(accountCInfo.getPlaceName());
//			accountCBussiness.setPlaceNo(accountCInfo.getPlaceNo());
			accountCBussiness.setTradeTime(new Date());
			accountCBussiness.setRealPrice(new BigDecimal("0"));
			//回执打印数据
			AccountCApply accountCApply = macaoDao.findByCardNo(accountCInfo.getCardNo());
			accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());

			macaoDao.save(accountCBussiness);

			//MD5 = 卡号+交易密码
			accountCInfo.setTradingPwd(StringUtil.md5(newPass));
			//增加历史id
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			macaoDao.update(accountCInfo);

			//保存流水记录
			MacaoCardCustomer macaoCardCustomer = macaoDao.getCustomerByCardNo(accountCBussiness.getCardNo());
			ServiceWater serviceWater = new ServiceWater(null, accountCBussiness.getUserId(), null, null, accountCBussiness.getCardNo(),
					null, null, accountCBussiness.getState(), macaoCardCustomer.getBankAccountNumber(),
					null, null, null,
					null, null, null, null, null,
					accountCBussiness.getId(), null, null, null, null, accountCBussiness.getOperId(),
					accountCBussiness.getPlaceId(), accountCBussiness.getOperNo(), accountCBussiness.getOperName(), accountCBussiness.getPlaceNo(),
					accountCBussiness.getPlaceName(), accountCBussiness.getTradeTime(), "重设记帐卡密码", macaoCardCustomer.getId());

			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("205");
			serviceWaterDao.save(serviceWater);

			message = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "重设密码失败";
			return message;
		}
		return message;
	}

	/**
	 * @author lgm
	 */
	@Override
	public Pager findByCustomer(Pager pager, Customer customer) {
		return macaoDao.findByCustomer(pager, customer);
	}

	/* (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoService#findByCardNo(java.lang.String)
	 * @author lgm
	 */
	@Override
	public AccountCInfo findByCardNo(String cardNo) {
		return macaoDao.getByCardNo(cardNo);
	}

	@Override
	public MacaoCardCustomer findByIdTypeAndNumber(String idType, String idNumber) {
		return macaoDao.findByIdTypeAndNumber(idType, idNumber);
	}

	@Override
	public MacaoCardCustomer findMacaoCardCustomerByCardNo(String cardNo) {
		return macaoDao.findMacaoCardCustomerByCardNo(cardNo);
	}

	/**
	 * 根据记帐卡id查找持卡人信息绑定关系
	 */
	@Override
	public CardHolderInfo findByCardId(Long id) {
		return cardHolderInfoDao.findByCardId(id);
	}

	@Override
	public MacaoLostReq findMacaoLostReq(MacaoLostReq macaoLostReq) {
		return macaoDao.findMacaoLostReq(macaoLostReq);
	}

	@Override
	public MacaoAddCarReq findMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq) {
		return macaoDao.findMacaoAddCarReq(macaoAddCarReq);
	}

	@Override
	public void updateMacaoLostReq(MacaoLostReq macaoLostReq) {
		macaoDao.updateMacaoLostReq(macaoLostReq);
	}

	@Override
	public void updateMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq) {
		macaoDao.updateMacaoAddCarReq(macaoAddCarReq);
	}

	@Override
	public void updateMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo) {
		macaoDao.updateMacaoCancleReqInfo(macaoCancleReqInfo);
	}

	@Override
	public void updateMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer) {
		macaoDao.updateMacaoCardCustomer(macaoCardCustomer);
	}

	/*
	 * (non-Javadoc)
	 * 保存访问澳门通接口记录
	 * @see com.hgsoft.macao.serviceInterface.IMacaoService#saveNotifyMCRecord(com.hgsoft.macao.entity.NotifyMCRecord, com.hgsoft.macao.entity.NotifyMCRecord)
	 */
	@Override
	public void saveNotifyMCRecord(NotifyMCRecord notifyMCRecord, NotifyMCRecord oldNotifyMCRecord) {
		try {
			Long id = sequenceUtil.getSequenceLong("SEQ_CSMSNOTIFYMCRECORD");
			notifyMCRecord.setId(id);

			//如果oldNotifyMCRecord不为空，表示是重新发送通知澳门通接口功能
			Long hisId = null;
			if (oldNotifyMCRecord != null) {
				hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNOTIFYMCRECORDHIS");

				NotifyMCRecordHis notifyMCRecordHis = new NotifyMCRecordHis();
				notifyMCRecordHis.setId(hisId);
				notifyMCRecordHis.setGenReason("1");//删除

				notifyMCRecordHisDao.save(oldNotifyMCRecord, notifyMCRecordHis);
				notifyMCRecordDao.deleteById(oldNotifyMCRecord.getId());
			}
			if (hisId != null) notifyMCRecord.setHisSeqID(hisId);
			notifyMCRecordDao.save(notifyMCRecord);

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "记录访问澳门通接口失败");
			e.printStackTrace();
			throw new ApplicationException("记录访问澳门通接口失败");
		}
	}

	/**
	 * 根据银行账号查找银行账号信息表记录
	 */
	@Override
	public MacaoBankAccount findByAccountNumber(String bankAccountNumber) {
		return macaoDao.findByAccountNumber(bankAccountNumber);
	}

	@Override
	public Map<String, Object> authenticationCheck(String idCardType,
	                                               String idCardNumber, String servicePwd, String cardNo, String type) {
		return macaoDao.authenticationCheck(idCardType, idCardNumber, servicePwd, cardNo, type);
	}

	/**
	 * 通过mainid查询持卡人的银行账号列表
	 */
	@Override
	public List<Map<String, Object>> findBankAccountNumberByMainId(Long id) {

		return macaoDao.findBankAccountNumberByMainId(id);
	}

	/**
	 * 根据记帐卡号查找澳门通银行信息记录
	 */
	@Override
	public MacaoBankAccount findMacaoBankByCardNo(String cardNo) {
		return macaoDao.findBankAccountByCardNo(cardNo);
	}

	/**
	 * 保存澳门通请求通讯记录
	 */
	@Override
	public void saveMacaoReqRecord(MacaoReqRecord macaoReqRecord) {
		try {
			Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMACAOREQRECORD");
			macaoReqRecord.setId(id);
			macaoReqRecordDao.saveMacaoReqRecord(macaoReqRecord);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "记录访问澳门通接口失败");
			e.printStackTrace();
			throw new ApplicationException("记录访问澳门通接口失败");
		}
	}

	/**
	 * 找到所有没有通知澳门通成功的记录
	 */
	@Override
	public Pager findNotifyMCRecords(Pager pager, String beginTime, String endTime, NotifyMCRecord notifyMCRecord) {
		return notifyMCRecordDao.findNotifyMCRecords(pager, beginTime, endTime, notifyMCRecord);
	}

	@Override
	public NotifyMCRecord findNotifyMCRecord(Long id) {
		return notifyMCRecordDao.findById(id);
	}

	@Override
	public void deleteNotifyMCRecord(Long id) {
		notifyMCRecordDao.deleteById(id);
	}

	/*
	 * 根据车牌号码与车牌颜色查找澳门通持卡人
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoService#findByVehicleInfo(com.hgsoft.customer.entity.VehicleInfo)
	 */
	@Override
	public MacaoCardCustomer findByVehicleInfo(VehicleInfo vehicleInfo) {
		return macaoCardCustomerDao.findByVehicleInfo(vehicleInfo);
	}

	@Override
	public boolean saveVehicleMigrate(String bankAccountNumber, MacaoCardCustomer macaoCardCustomer, VehicleInfo vehicleInfo,
	                                  Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		try {
			vehicleInfoDao.update(vehicleInfo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (carObuCardInfo == null) {
				throw new ApplicationException();
			}
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			//vehicleBussiness.setType("13");
			vehicleBussiness.setType(VehicleBussinessEnum.migrateVehicle.getValue());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆迁移");
			vehicleBussiness.setOperID(sysAdmin.getId());
			vehicleBussiness.setOperNo(sysAdmin.getStaffNo());
			vehicleBussiness.setOperName(sysAdmin.getUserName());
			vehicleBussiness.setPlaceID(cusPointPoJo.getCusPoint());
			vehicleBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			vehicleBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			vehicleBussiness.setCreateTime(new Date());

			//将车辆挂在验证客户的银行账号下
			MacaoBankAccount macaoBankAccount = null;
			try {
				macaoBankAccount = macaoBankAccountDao.findByBankAccountNumber(bankAccountNumber);
			} catch (Exception e) {
				logger.error("查找MacaoBankAccount对象失败");
				return false;
			}
			if (macaoBankAccount == null) {
				logger.info("找不到银行账号信息");
				return false;
			}
			CardHolderInfo cardHolderInfo = cardHolderInfoDao.findByVehicle(vehicleInfo.getId());
			if (cardHolderInfo == null) {
				logger.info("找不到该车辆id对应的cardHolderInfo记录");
				return false;
			} else {
				cardHolderInfo.setMacaoBankAccountId(macaoBankAccount.getId());
			}
			cardHolderInfoDao.update(cardHolderInfo);

			//将车与标签关系解除绑定，并下标签黑名单
			if (carObuCardInfo.getTagID() != null) {
				TagInfo tagInfo = tagInfoDao.findById(carObuCardInfo.getTagID());
				if (tagInfo == null) return false;
				vehicleBussiness.setTagNo(tagInfo.getTagNo());
				//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
				//电子标签下注销黑名单
				//saveDarkList(tagInfo,darkList,"6", "1");
				//车辆迁移
				blackListService.saveOBUCancel(tagInfo.getObuSerial(), new Date()
						, "2", sysAdmin.getId(), sysAdmin.getStaffNo(), sysAdmin.getLoginName(),
						cusPointPoJo.getCusPoint(), cusPointPoJo.getCusPointCode(), cusPointPoJo.getCusPointName(),
						new Date());
			}
			carObuCardInfo.setTagID(null);
			carObuCardInfoDao.update(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customerId);
			customerBussiness.setMigratecustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileMigrate.getValue());
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getLoginName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);


			Customer orignalCustomer = customerDao.findById(customerId);
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (orignalCustomer != null) serviceWater.setCustomerId(orignalCustomer.getId());
			if (orignalCustomer != null) serviceWater.setUserNo(orignalCustomer.getUserNo());
			if (orignalCustomer != null) serviceWater.setUserName(orignalCustomer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoCardCustomer.getId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			//serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("404");//车辆所属人变更
			serviceWater.setRemark("澳门通客服系统：车辆所属人变更");
			//serviceWater.setFlowState("1");//正常
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);


			return true;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "数据错误！车卡绑定表中无此车牌ID！");
			throw new ApplicationException("数据错误！车卡绑定表中无此车牌ID！");
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
	public void saveDarkList(TagInfo tagInfo, DarkList darkList, String genCau, String state) {
		Customer customer = customerDao.findById(tagInfo.getClientID());
		try {
			if (darkList == null) {
				darkList = new DarkList();
				darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
				darkList.setCustomerId(tagInfo.getClientID());
				darkList.setObuSerial(tagInfo.getObuSerial());
				darkList.setCardNo(tagInfo.getTagNo());
				darkList.setCardType("3");
				darkList.setGenDate(new Date());
				darkList.setGencau(genCau);//产生原因	10—无卡注销。
				darkList.setGenmode("0");//产生方式	系统产生
				darkList.setOperId(tagInfo.getOperID());
				darkList.setPlaceId(tagInfo.getIssueplaceID());
				darkList.setOperNo(tagInfo.getOperNo());
				darkList.setOperName(tagInfo.getOperName());
				darkList.setPlaceNo(tagInfo.getPlaceNo());
				darkList.setPlaceName(tagInfo.getPlaceName());
				//darkList.setUpdateTime(updateTime);
				if (customer != null) {
					darkList.setUserNo(customer.getUserNo());
					darkList.setUserName(customer.getOrgan());
				}
				//darkList.setRemark(remark);
				darkList.setState(state);
				darkListDao.save(darkList);

			} else {
				darkList.setCustomerId(customer.getId());
				darkList.setUserNo(customer.getUserNo());
				darkList.setUserName(customer.getOrgan());
				darkList.setGencau(genCau);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("保存清算黑名单数据失败" + tagInfo.getTagNo() + e.getMessage());
			throw new ApplicationException("保存清算数黑名单据失败" + tagInfo.getTagNo());
		}
	}

	/*
	 * 通过铭鸿MQ接口，判断车辆是否可以用来发行卡片
	 * (non-Javadoc)
	 * @see com.hgsoft.macao.serviceInterface.IMacaoService#checkCarOtherPlace(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkCarOtherPlace(String vehiclePlate, String vehicleColor) {

		List<Map<String, Object>> list = macaoDao.checkVehicle(vehiclePlate, vehicleColor);
		if (list != null && !list.isEmpty()) {
			//如果能找到这个车牌，表示车牌已经被用了。
			return true;
		} else {
			return false;
		}
		
		/*boolean checkResult = false;
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.vehicleCheck.getValue());//接口编码
		list.add(vehiclePlate);//车牌号码
		list.add(vehicleColor);//车牌颜色 2:黑色
		logger.info("参数列表："+list);
		try {
			String msg = MQAnalysis.listToString(list);
			MQServerObject mqServerObject = new MQServerObject();
			String result = mqServerObject.setAndGetMsg(msg);
			if(result!=null && !"".equals(result)){
				list = MQAnalysis.analysis(result);
				logger.info("车牌校验结果:"+list);
				if("00".equals(list.get(0))){
					checkResult = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("车牌校验结果失败原因："+e.getMessage());
			return false;
		}
		return checkResult;*/
	}

}
