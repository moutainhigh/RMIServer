package com.hgsoft.macao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.MacaoIdCardTypeEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.CarUserCardInfoDao;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.serviceInterface.IMacaoOtherService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.SequenceUtil;
@Service
public class MacaoOtherService implements IMacaoOtherService{
	private static Logger logger = Logger.getLogger(MacaoOtherService.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private IVehicleInfoService vehicleInfoService;
	@Resource
	private IAccountCService accountCService;
	
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private DarkListDao darkListDao;
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao;*/
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CarUserCardInfoDao carUserCardInfoDao;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;
	
	@Override
	public void saveStart(Customer customer,AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness) {
		try {
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 1 where id =  "+carObuCardInfo.getVehicleID());
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			// 更新对应车辆信息的写导入卡标志
			VehicleInfo vehicle = vehicleInfoService.findById(carObuCardInfo.getVehicleID());
			MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(accountCInfo.getCardNo());
			
			VehicleInfo newvehicleInfo = new VehicleInfo();
			newvehicleInfo.setOperId(vehicleBussiness.getOperID());
			newvehicleInfo.setOperNo(vehicleBussiness.getOperNo());
			newvehicleInfo.setOperName(vehicleBussiness.getOperName());
			newvehicleInfo.setPlaceId(vehicleBussiness.getPlaceID());
			newvehicleInfo.setPlaceNo(vehicleBussiness.getPlaceNo());
			newvehicleInfo.setPlaceName(vehicleBussiness.getPlaceName());
			
			vehicle.setIsWriteCard("1");
			vehicleInfoService.updateVehicleForMacaoStartCard(vehicle,newvehicleInfo);

			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("7");
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				//澳门通的卡片停用，还要考虑        车、持卡人信息、卡、标签绑定表关系
				/*CarUserCardInfo carUserCardInfo = carUserCardInfoDao.findByVehicleId(carObuCardInfo.getVehicleID());
				carUserCardInfo.setAccountCId(carObuCardInfo.getAccountCID());
				carUserCardInfoDao.updateCarUserCardInfo(carUserCardInfo);*/
				
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			if(BlackFlagEnum.black.getValue().equals(accountCInfo.getBlackFlag())){
				/*blackListService.saveRelieveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(),new Date(),
						 "2", customer.getOperId(), customer.getOperNo(), customer.getOperName(),
						customer.getPlaceId(), customer.getPlaceNo(), customer.getPlaceName(), 
						new Date());*/
			}
			
			//给铭鸿
			blackListService.saveRelieveStopUseCard(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), vehicleBussiness.getCreateTime(), 
					"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
					vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
			
			//写给铭鸿的清算数据：卡片状态信息
			//TODO 是否用证件类型来判断用户类型
			String userType = "";
			if(MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType()) 
					|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
					|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
					|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.nomal.getValue()), 
					CardStateSendSerTypeEnum.startCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndCard.getValue()), accountCInfo.getCardNo(), 
					"23", Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "记帐卡解除挂起");
			
			
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
			
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByAccountCInfoId(accountCInfo.getId());
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setNewCardNo(accountCInfo.getCardNo());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setAmt(accountCInfo.getCost());
			serviceWater.setAulAmt(accountCInfo.getRealCost());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType("402");
			serviceWater.setRemark("解除挂起");
			serviceWaterDao.save(serviceWater);
			
		} catch (Exception e) {
			logger.error(e.getMessage()+"卡牌启用操作失败，记帐卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，记帐卡id:" + accountCInfo.getId());
		}
		
	}

	@Override
	public void saveStop(String flag,Customer customer,AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness) {
		try {
			vehicleInfoDao.update(" update CSMS_Vehicle_Info set IsWriteCard = 0 where id =  "+carObuCardInfo.getVehicleID());
			// 客服流水
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAccountCInfo(accountCInfo);
			unifiedParam.setServiceFlowRecord(serviceFlowRecord);
			unifiedParam.setType("8");
			unifiedParam.setPlaceId(accountCInfo.getIssuePlaceId());
			unifiedParam.setOperId(accountCInfo.getIssueOperId());
			unifiedParam.setOperName(accountCInfo.getOperName());
			unifiedParam.setOperNo(accountCInfo.getOperNo());
			unifiedParam.setPlaceName(accountCInfo.getPlaceName());
			unifiedParam.setPlaceNo(accountCInfo.getPlaceNo());
			
			unifiedParam.setCarObuCardInfo(carObuCardInfo);
			
			if (unifiedInterfaceService.saveAccountCState(unifiedParam)) {
				//澳门通的卡片停用，还要考虑        车、持卡人信息、卡、标签绑定表关系
				/*CarUserCardInfo carUserCardInfo = carUserCardInfoDao.findByVehicleId(carObuCardInfo.getVehicleID());
				carUserCardInfo.setAccountCId(null);
				carUserCardInfoDao.updateCarUserCardInfo(carUserCardInfo);*/
				
				// 启用--更新车牌对应的卡id
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			VehicleInfo vehicle = vehicleInfoService.findById(carObuCardInfo.getVehicleID());
			MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(accountCInfo.getCardNo());
			
			//TODO 是否用证件类型来判断用户类型
			String userType = "";
			if(MacaoIdCardTypeEnum.macaoIdCard.getValue().equals(macaoCardCustomer.getIdCardType()) 
					|| MacaoIdCardTypeEnum.hongKong.getValue().equals(macaoCardCustomer.getIdCardType())
					|| MacaoIdCardTypeEnum.chinaIdCard.getValue().equals(macaoCardCustomer.getIdCardType())
					|| MacaoIdCardTypeEnum.passport.getValue().equals(macaoCardCustomer.getIdCardType())){
				userType = "0";//个人
			}else{
				userType = "1";//单位
			}
			if(accountCInfo.getState().equals("1")){
				//保存黑名单流水挂起表(禁用+生成方式‘人工办理’)	给铭鸿     （注解：解除挂起根据  禁用+‘人工办理’ 就可以判断黑名单是无卡挂起产生的）
				blackListService.saveCardStopUse(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), vehicleBussiness.getCreateTime(), 
						"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
				
				//写给铭鸿的清算数据：卡片状态信息
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()), 
						CardStateSendSerTypeEnum.stopWithOutCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			}else{
				//写给铭鸿的清算数据：卡片状态信息
				cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(AccountCardStateEnum.disabled.getIndex()), 
						CardStateSendSerTypeEnum.stopWithCard.getValue(), accountCInfo.getCardNo(), "23", userType);
			}
			
			String obuSeq = "";
			Date obuIssueTime = null;
			Date obuExpireTime = null;
			//写给铭鸿的清算数据：用户状态信息
			cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(), 
					"23", Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
					null,obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂起");
			
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
			
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByAccountCInfoId(accountCInfo.getId());
			
			//客户服务流水
			String serType = "401";
			String remark = "挂起";
			if("2".equals(flag)){
				serType="403";
				remark = "无卡挂起";
			}
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setNewCardNo(accountCInfo.getCardNo());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setAmt(accountCInfo.getCost());
			serviceWater.setAulAmt(accountCInfo.getRealCost());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType(serType);
			serviceWater.setRemark(remark);
			serviceWaterDao.save(serviceWater);
			
		} catch (Exception e) {
			logger.error(e.getMessage()+"卡牌停用操作失败，记帐卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("卡牌停用操作失败，记帐卡id:" + accountCInfo.getId());
		}
		
	}

	/**
	 * 澳门通，通过卡号查询该持卡人下面所有没有绑定卡片的车辆
	 */
	@Override
	public List<Map<String, Object>> listNotBind(String cardNo) {
		return macaoDao.listNotBind(cardNo);
	}

}
