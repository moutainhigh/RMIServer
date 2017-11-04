package com.hgsoft.other.service;

import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.agentCard.dao.CardBusinessInfoDao;
import com.hgsoft.agentCard.entity.CardBusinessInfo;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.VechileChangeInfoDao;
import com.hgsoft.macao.entity.VechileChangeInfo;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.other.dao.CardDelayDao;
import com.hgsoft.other.dao.OtherDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.CardDelay;
import com.hgsoft.other.entity.OfficialCardImport;
import com.hgsoft.other.entity.OfficialCardInfo;
import com.hgsoft.other.serviceInterface.IOtherService;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.service.RealTransferService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherService implements IOtherService {
	private static Logger logger = Logger.getLogger(OtherService.class.getName());

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private VechileChangeInfoDao vechileChangeInfoDao;
	@Resource
	private OtherDao otherDao;
	@Resource
	private CardDelayDao cardDelayDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CustomerDao customerDao;

	@Resource
	private NoRealTransferService noRealTreasferService;

	@Resource
	private RealTransferService realTransferService;

	//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171025
	@Resource
	private CardBusinessInfoDao cardBusinessInfoDao;
	//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171025

	@Resource
	private AccountCDao accountCDao;

	@Override
	public void saveStart(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params) {
		try {
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			prepaidCUnifiedInterfaceService.saveStart(prepaidC, carObuCardInfo, vehicleBussiness, params);
			vehicleBussinessDao.save(vehicleBussiness);
			/**@author luningyun  @description 营改增新增判断储值卡有卡挂起/无卡挂起来上传不同的状态名单   @date 2017-10-22 10:56 start**/
			VehicleBussiness vBusiness = vehicleBussinessDao.findStoreBeforeStopVehicleBussiness(prepaidC.getCardNo());
			logger.info("储值卡保存启用数据=========");
			if (vBusiness != null && vBusiness.getType().equals(VehicleBussinessEnum.prepaidCDisabledWithoutCard.getValue())) {//无卡挂起则上传状态名单
				logger.info("无卡启用=========");
				noRealTreasferService.blackListTransfer(prepaidC.getCardNo(), new Date(), CardBlackTypeEmeu.NOCARD_HANGUP.getCode(), OperationTypeEmeu.DEL.getCode());
			}
			VehicleInfo v = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
			realTransferService.prepaidCTransfer(null, prepaidC, v, CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.UPDATE.getCode());//用户卡变更
			logger.info("结束========");
			/**end**/
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage() + "卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
		}
	}

	@Override
	public void saveStop(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params) {
		try {
			prepaidCUnifiedInterfaceService.saveStop(prepaidC, carObuCardInfo, vehicleBussiness, params);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
		}
	}


	@Override
	public void saveStart(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params) {
		try {
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			prepaidCUnifiedInterfaceService.saveStart(accountCInfo, carObuCardInfo, vehicleBussiness, params);
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "卡牌启用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
		}
	}

	/**
	 * @author luningyun
	 * @description 营改增新增判断记账卡有卡挂起/无卡挂起来上传不同的状态名单
	 * @date 2017-10-22 11:29
	 */
	@Override
	public void saveStartAccountc(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params, Integer systemType, AccountCApply accountCApply, VehicleInfo vehicleInfo) {
		logger.info("记账卡保存启用数据开始========");
		try {
			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			prepaidCUnifiedInterfaceService.saveStart(accountCInfo, carObuCardInfo, vehicleBussiness, params);
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
			VehicleBussiness vBusiness = vehicleBussinessDao.findBeforeStopVehicleBussiness(accountCInfo.getCardNo());
			if (vBusiness == null) {//初次记账卡申请 上传待同步卡信息
				logger.info("记账卡保存启用数据开始,初次记账卡申请 上传待同步卡信息========");

				//ygz wangjinhao---------------------------------- start CARDUPLOAD+CARDBLACKLISTUPLOAD20171025
				List<CardBusinessInfo> cardBusinessInfoList = cardBusinessInfoDao.findBusinessRecordBycardNo
						(accountCInfo.getCardNo());
				if (!cardBusinessInfoList.isEmpty()) {
					AccountCInfo oldAccountCInfo = accountCInfoDao.findByCardNo(cardBusinessInfoList.get(0).getOldUTCardNo());
					// 用户卡信息上传及变更
					realTransferService.accountCInfoTransfer(customer, oldAccountCInfo,
							vehicleInfo, CardStatusEmeu.NOCARD_CANCLE.getCode(),
							OperationTypeEmeu.UPDATE.getCode());
					// 旧卡黑名单状态上传及变更
					noRealTreasferService.blackListTransfer(oldAccountCInfo.getCardNo(), new
							Date(), CardBlackTypeEmeu.NOCARD_CANCLE.getCode(), OperationTypeEmeu
							.EN_BLACK.getCode());
				}
				//ygz wangjinhao---------------------------------- end CARDUPLOAD+CARDBLACKLISTUPLOAD20171025

				realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo,
						CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.ADD.getCode());//用户卡新增
			} else {//不为空，则不是开户申请、补换卡等产生的,只要上传卡信息变更信息和卡状态变更信息（无卡）即可
				logger.info("存在挂起记录===");
				if (vBusiness.getType().equals(VehicleBussinessEnum.accountCDisabledWithoutCard.getValue())) {//无卡挂起上传卡状态变更名单
					logger.info("[cardNo]:" + accountCInfo.getCardNo() + "无卡解除挂起新增黑名单删除消息===");
					noRealTreasferService.blackListTransfer(accountCInfo.getCardNo(), new Date(), CardBlackTypeEmeu.NOCARD_HANGUP.getCode(), OperationTypeEmeu.DEL.getCode());
				}
				realTransferService.accountCInfoTransfer(customer, accountCInfo, vehicleInfo,
						CardStatusEmeu.NORMAL.getCode(), OperationTypeEmeu.UPDATE.getCode());//用户卡新增
			}
			logger.info("结束========");
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "卡牌启用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
		}
	}

	@Override
	public void saveStop(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params) {
		try {
			prepaidCUnifiedInterfaceService.saveStop(accountCInfo, carObuCardInfo, vehicleBussiness, params);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "卡牌停用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌停用操作失败，记帐卡卡号:" + accountCInfo.getCardNo());
		}
	}

	/**
	 * 保存挂起时，清算数据
	 */
	public void saveStopClear(String flag, VehicleInfo vehicleInfo) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());

			VechileChangeInfo vechileChangeInfo = new VechileChangeInfo();
			vechileChangeInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVechileChangeInfo_NO"));
			vechileChangeInfo.setInterCode("91005");
			vechileChangeInfo.setCreateTime(createTime);
			vechileChangeInfo.setSerType("2");
			vechileChangeInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileChangeInfo.setVehicleColor(vehicleInfo.getVehicleColor());

			vechileChangeInfo.setVehicleType(vehicleInfo.getVehicleType());
			vechileChangeInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits());
			vechileChangeInfo.setNscVehicleType(vehicleInfo.getNSCVehicleType());

			if ("1".equals(flag))
				vechileChangeInfo.setWriteCardFlag("0");//有卡停用
			else
				vechileChangeInfo.setWriteCardFlag("1");//无卡停用
			vechileChangeInfoDao.save(vechileChangeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存解除挂起时，清算数据
	 */
	public void saveStartClear(VehicleInfo vehicleInfo) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());

			VechileChangeInfo vechileChangeInfo = new VechileChangeInfo();
			vechileChangeInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVechileChangeInfo_NO"));
			vechileChangeInfo.setInterCode("91005");
			vechileChangeInfo.setCreateTime(createTime);
			vechileChangeInfo.setSerType("1");
			vechileChangeInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileChangeInfo.setVehicleColor(vehicleInfo.getVehicleColor());

			vechileChangeInfo.setVehicleType(vehicleInfo.getVehicleType());
			vechileChangeInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits());
			vechileChangeInfo.setNscVehicleType(vehicleInfo.getNSCVehicleType());

			vechileChangeInfo.setWriteCardFlag("0");//有卡停用
			vechileChangeInfoDao.save(vechileChangeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据证件类型，证件号码查询该客户的储值卡与记帐卡
	 */
	@Override
	public List<Map<String, Object>> findCardListByCustomer(String idType, String idCode, String expiredCatdNo) {
		try {
			List<Map<String, Object>> list = otherDao.findCardListByCustomer(idType, idCode, expiredCatdNo);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage() + "卡片延期失败");
			e.printStackTrace();
			throw new ApplicationException("卡片延期失败");
		}
	}

	@Override
	public Map<String, Object> findDetailByCardNo(String cardNo, String cardType) {
		Map<String, Object> m = otherDao.findDelayDetailByCardNo(cardNo, cardType);
		return m;
	}

	@Override
	public void saveCardDelay(CardDelay cardDelay) {
		try {
			cardDelay.setId(sequenceUtil.getSequenceLong("seq_csmscarddelay_no"));
			cardDelayDao.save(cardDelay);
			//更改发行表有效截止时间
			if ("1".equals(cardDelay.getCardType())) {//储值卡
				PrepaidC prepaidC = prepaidCDao.findByPrepaidCNo(cardDelay.getCardNo());
				DateTime dt2 = new DateTime(prepaidC.getEndDate());
				DateTime d = null;
				if (cardDelay.getDelayTime().compareTo("1") < 0) {
					d = dt2.plusMonths(6);
				} else {
					d = dt2.plusYears(Integer.parseInt(cardDelay.getDelayTime()));
				}
				prepaidC.setEndDate(new Date(d.getMillis()));
				prepaidCDao.updateEndDate(prepaidC);
			} else {//记帐卡
				AccountCInfo info = accountCInfoDao.findByCardNo(cardDelay.getCardNo());
				DateTime dt2 = new DateTime(info.getEndDate());
				DateTime d = null;
				if (cardDelay.getDelayTime().compareTo("1") < 0) {
					d = dt2.plusMonths(6);
				} else {
					d = dt2.plusYears(Integer.parseInt(cardDelay.getDelayTime()));
				}
				info.setEndDate(new Date(d.getMillis()));
				accountCInfoDao.update(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "卡片延期失败:" + cardDelay.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡片延期失败:" + cardDelay.getCardNo());
		}
	}

	@Override
	public void updateCardDelay(CardDelay cardDelay) {
		try {
			cardDelayDao.updateById(cardDelay);
			//更改发行表有效截止时间
			if (cardDelay.getCardType().equals("储值卡")) {//更新储值卡信息表
				PrepaidC prepaidC = new PrepaidC();
				prepaidC.setCardNo(cardDelay.getCardNo());
				prepaidC.setEndDate(cardDelay.getBeforeDelayTime());
				prepaidCDao.updateEndDate(prepaidC);
			} else {//更新记帐卡信息表
				AccountCInfo info = new AccountCInfo();
				info.setCardNo(cardDelay.getCardNo());
				info.setEndDate(cardDelay.getBeforeDelayTime());
				accountCInfoDao.updateEndDate(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "卡片延期撤销失败:" + cardDelay.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡片延期撤销失败:" + cardDelay.getCardNo());
		}
	}

	/**
	 * @param cardNo
	 * @param state
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年4月19日
	 */
	@Override
	public AccountCInfoHis findByCardNoAndState(String cardNo, String state) {
		return accountCInfoHisDao.findByCardNoAndState(cardNo, state);
	}

	/**
	 * @param id
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年4月27日
	 */
	@Override
	public CardDelay findCardDelayById(Long id) {
		try {
			return cardDelayDao.findById(id);
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException | InvocationTargetException e) {
		}
		return null;
	}

	/**
	 * @param id
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年4月27日
	 */
	@Override
	public boolean updateFlag(Long id) {
		try {
			cardDelayDao.updateFlag(id);
			return true;
		} catch (Exception e) {
			logger.error("卡片延期标志更新失败" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Map<String, Object> getVehicleByCardNo(String cardNo, Customer customer) {
		//判断卡号是否在公安公务卡导入表里
		OfficialCardImport officialCard = otherDao.findOfficialCardImportByNo(cardNo);

		if (officialCard == null) {
			return null;
		}
		String cardType = officialCard.getCardType();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("officialCard", officialCard);
		VehicleInfo vehicleInfo;
		TagInfo tag = tagInfoDao.findByTagNo(officialCard.getTagNo());
		if (tag != null && tag.getClientID().equals(customer.getId())) {
			map.put("tag", tag);
		}

		if (StringUtil.isEquals(cardType, "22")) {//储值卡
			PrepaidC prepaidC = prepaidCDao.findByPrepaidcNoAndCustomer(cardNo, customer);
			map.put("cardInfo", prepaidC);
			vehicleInfo = vehicleInfoDao.findByPrepaidCardNo(cardNo);
		} else {
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNoAndCustomer(cardNo, customer);
			map.put("cardInfo", accountCInfo);
			vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
		}
		map.put("vehicleInfo", vehicleInfo);
		return map;
	}

	@Override
	public OfficialCardImport getOfficialCard(String cardNo) {
		return otherDao.findOfficialCardImportByNo(cardNo);
	}

	@Override
	public void saveOfficialCard(OfficialCardInfo officialCardInfo, Customer customer, Map<String, Object> params) {
		//保存公安公务卡发行信息
		Long officialCardInfoId = sequenceUtil.getSequenceLong("SEQ_OFFICIALCARD_ISSUE");
		officialCardInfo.setId(officialCardInfoId);
		officialCardInfo.setOrgId(customer.getId());
		otherDao.saveOfficialCard(officialCardInfo);
		//保存客服流水表信息
		ServiceWater serviceWater = new ServiceWater();
		Long serviceWaterId = Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString());
		serviceWater.setId(serviceWaterId);
		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());

		if (StringUtil.isEquals(officialCardInfo.getCardType(), "23")) {//记帐卡
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(officialCardInfo.getCardNo());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setNewCardNo(accountCInfo.getCardNo());
			serviceWater.setAmt(accountCInfo.getCost());
			serviceWater.setAulAmt(accountCInfo.getRealCost());
			serviceWater.setAccountBussinessId(officialCardInfoId);
		} else {
			PrepaidC prepaidC = prepaidCDao.findByCardNoToGain(officialCardInfo.getCardNo());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setNewCardNo(prepaidC.getCardNo());
			serviceWater.setAmt(prepaidC.getCost_());
			serviceWater.setAulAmt(prepaidC.getRealCost());
			serviceWater.setPrepaidCBussinessId(officialCardInfoId);
		}

		serviceWater.setOperId(officialCardInfo.getOperId());
		serviceWater.setOperNo(officialCardInfo.getOperNo());
		serviceWater.setOperName(officialCardInfo.getOperName());
		serviceWater.setPlaceId(officialCardInfo.getPlaceId());
		serviceWater.setPlaceNo(officialCardInfo.getPlaceNo());
		serviceWater.setPlaceName(officialCardInfo.getPlaceName());
		serviceWater.setOperTime(officialCardInfo.getOperTime());
		serviceWater.setSerType("408");
		serviceWater.setRemark("公安公务卡发行");
		serviceWaterDao.save(serviceWater);

		/*
		VehicleInfo vehicleInfo = this.vehicleInfoDao.findVehicleInfoByPlateAndColor(officialCardInfo.getVehiclePlate(),officialCardInfo.getVehicleColor());
		//公安公务卡发行回执
		PoliceOfficialCardReceipt policeOfficialCardReceipt = new PoliceOfficialCardReceipt();
		policeOfficialCardReceipt.setTitle("公安公务卡发行回执");
		policeOfficialCardReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
		policeOfficialCardReceipt.setCustomerSecondNo(customer.getSecondNo());
		policeOfficialCardReceipt.setCustomerSecondName(customer.getSecondName());
		policeOfficialCardReceipt.setCardNo(officialCardInfo.getCardNo());
		policeOfficialCardReceipt.setTagNo(officialCardInfo.getTagNo());
		policeOfficialCardReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
		policeOfficialCardReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
		policeOfficialCardReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
		policeOfficialCardReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
		policeOfficialCardReceipt.setVehicleModel(vehicleInfo.getModel());
		policeOfficialCardReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicleInfo.getVehicleType()).getName());
		policeOfficialCardReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicleInfo.getVehicleUserType()));
		policeOfficialCardReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
		policeOfficialCardReceipt.setVehicleOwner(vehicleInfo.getOwner());
		policeOfficialCardReceipt.setVehicleLong(vehicleInfo.getVehicleLong()+"");
		policeOfficialCardReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth()+"");
		policeOfficialCardReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight()+"");
		policeOfficialCardReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicleInfo.getNSCVehicleType()));
		policeOfficialCardReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
		policeOfficialCardReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles()+"");
		policeOfficialCardReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels()+"");
		policeOfficialCardReceipt.setCustomerNo(customer.getUserNo());
		policeOfficialCardReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		policeOfficialCardReceipt.setCustomerIdCode(customer.getIdCode());
		policeOfficialCardReceipt.setCustomerName(customer.getOrgan());
		Receipt receipt = new Receipt();
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.other.getValue());
		receipt.setTypeCode(OtherBussinessTypeEnum.policeOfficialCard.getValue());
		receipt.setTypeChName(OtherBussinessTypeEnum.policeOfficialCard.getName());
		receipt.setCardNo(policeOfficialCardReceipt.getCardNo());
		receipt.setTagNo(policeOfficialCardReceipt.getTagNo());
		receipt.setVehicleColor(vehicleInfo.getVehicleColor());
		receipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
		receipt.setCreateTime(new Date());
		receipt.setPlaceId(officialCardInfo.getPlaceId());
		receipt.setPlaceNo(officialCardInfo.getPlaceNo());
		receipt.setPlaceName(officialCardInfo.getPlaceName());
		receipt.setOperId(officialCardInfo.getOperId());
		receipt.setOperName(officialCardInfo.getOperName());
		receipt.setOperNo(officialCardInfo.getOperNo());
		receipt.setOrgan(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(policeOfficialCardReceipt).toString());
		this.receiptDao.saveReceipt(receipt);*/
	}

	@Override
	public Map<String, Object> saveWriteSuccess(PrepaidC prepaidC, AccountCInfo accountCInfo, ServiceWater serviceWater) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (prepaidC == null && accountCInfo == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "无法找到卡号信息");
		} else if (prepaidC != null) {
			Customer customer = customerDao.findById(prepaidC.getCustomerID());
			if (customer == null) {
				resultMap.put("result", "false");
				resultMap.put("message", "数据异常：无法找到客户信息");
				return resultMap;
			}
			prepaidCDao.updateWriteCardFlag(prepaidC.getCardNo());
			//调整的客服流水
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setSerType(ServiceWaterSerType.writeCardAfter.getValue());
			serviceWater.setRemark("补写卡");

			serviceWaterDao.save(serviceWater);

			resultMap.put("result", "true");
		} else if (accountCInfo != null) {
			Customer customer = customerDao.findById(accountCInfo.getCustomerId());
			if (customer == null) {
				resultMap.put("result", "false");
				resultMap.put("message", "数据异常：无法找到客户信息");
				return resultMap;
			}
			accountCDao.updateWriteCardFlag(accountCInfo.getCardNo());
			//调整的客服流水
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType(ServiceWaterSerType.writeCardAfter.getValue());
			serviceWater.setRemark("补写卡");

			serviceWaterDao.save(serviceWater);

			resultMap.put("result", "true");
		} else {
			resultMap.put("result", "false");
			resultMap.put("message", "异常的传入prepaidC与accountCInfo");
		}
		return resultMap;
	}
}
