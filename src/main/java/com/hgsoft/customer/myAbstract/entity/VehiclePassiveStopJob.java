package com.hgsoft.customer.myAbstract.entity;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.CardStateSendSerTypeEnum;
import com.hgsoft.common.Enum.CardStateSendStateFlag;
import com.hgsoft.common.Enum.PrepaidCardStateEnum;
import com.hgsoft.common.Enum.ServiceWaterFlowStateEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.myAbstract.abstractEntity.VehicleJob;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.SequenceUtil;

/**
 * 车辆被动挂起任务
 * @author Administrator
 * @date 2017年8月30日
 */
@Service
public class VehiclePassiveStopJob extends VehicleJob{
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	@Autowired
	private SequenceUtil sequenceUtil;
	
	@Resource
	private  IBlackListService blackListService;
	@Resource
	private ICardObuService cardObuService;

	@Override
	public void saveServiceWater(Customer customer,VehicleBussiness vehicleBussiness,CustomerBussiness customerBussiness) {
		//调整的客服流水
		ServiceWater serviceWater = new ServiceWater();
		Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
		serviceWater.setId(serviceWater_id);
		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());
		serviceWater.setSerType(ServiceWaterSerType.veicleOwnerChange.getValue());//被动挂起
		serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
		serviceWater.setOperId(vehicleBussiness.getOperID());
		serviceWater.setOperName(vehicleBussiness.getOperName());
		serviceWater.setOperNo(vehicleBussiness.getOperNo());
		serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
		serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
		serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
		serviceWater.setOperTime(new Date());
		serviceWater.setRemark("自营客服系统：被动挂起");
		serviceWaterDao.save(serviceWater);
	}

	@Override
	public void saveVehicleBussiness(VehicleBussiness vehicleBussiness) {
		vehicleBussinessDao.save(vehicleBussiness);
	}
	
	/**
	 * 被动挂起之：处理记帐卡信息
	 * @param accountCId
	 * @param vehicleInfo
	 * @param vehicleBussiness
	 * @return void
	 */
	public void dealAccountC4PassiveStop(AccountCInfo accountCInfo,VehicleInfo vehicleInfo,VehicleBussiness vehicleBussiness,Customer customer){
		//记录记帐卡历史
		Long accountCInfoHisId = accountCInfoHisDao.saveAccountCInfoHis(accountCInfo, "14");
		//更新记帐卡状态为挂起
		accountCInfo.setHisSeqId(accountCInfoHisId);
		accountCInfo.setBlackFlag(BlackFlagEnum.black.getValue());
		accountCInfo.setState(AccountCardStateEnum.disabled.getIndex());
		accountCInfo.setBind("0");
		accountCDao.update(accountCInfo);
		//保存黑名单流水挂起表(禁用+生成方式‘人工办理’)	给铭鸿     （注解：解除挂起根据  禁用+‘人工办理’ 就可以判断黑名单是无卡挂起产生的）
		blackListService.saveCardStopUse(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), vehicleBussiness.getCreateTime(), 
				"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
				vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
		//"0":个人，"1"：单位
		String userType = UserTypeEnum.person.getValue().equals(customer.getUserType())?"0":"1";
		//写给铭鸿的清算数据：卡片状态信息
		cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()), 
				CardStateSendSerTypeEnum.stopWithOutCard.getValue(), accountCInfo.getCardNo(), Constant.ACCOUNTCTYPE, userType);
		//写给铭鸿的清算数据：用户状态信息
		String obuSeq = "";
		Date obuIssueTime = null;
		Date obuExpireTime = null;
		cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), accountCInfo.getCardNo(), 
				Constant.ACCOUNTCTYPE, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
				null,obuSeq, obuIssueTime, obuExpireTime, "记帐卡挂起");
	}
	/**
	 * 被动挂起之：处理储值卡信息
	 * @param prepaidCId
	 * @param vehicleInfo
	 * @param vehicleBussiness
	 * @return void
	 */
	public void dealPrepaidC4PassiveStop(PrepaidC prepaidC,VehicleInfo vehicleInfo,VehicleBussiness vehicleBussiness,Customer customer){
		//记录储值卡历史
		Long prepiadCHisId = prepaidCDao.savePrepaidCHis(prepaidC, "14");
		//更新储值卡状态为挂起
		prepaidC.setHisSeqID(prepiadCHisId);
		prepaidC.setBlackFlag(BlackFlagEnum.black.getValue());
		prepaidC.setState(PrepaidCardStateEnum.disabled.getIndex());
		prepaidC.setBind("0");
		prepaidCDao.update(prepaidC);
		
		//保存黑名单流水挂起表(禁用+生成方式‘人工办理’)	给铭鸿     （注解：解除挂起根据  禁用+‘人工办理’ 就可以判断黑名单是无卡挂起产生的）
		blackListService.saveCardStopUse(Constant.PREPAIDTYPE, prepaidC.getCardNo(), vehicleBussiness.getCreateTime(), 
				"2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(), 
				vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), new Date());
		//写给铭鸿的清算数据：卡片状态信息
		//无卡挂起
		//"0":个人，"1"：单位
		String userType = UserTypeEnum.person.getValue().equals(customer.getUserType())?"0":"1";
		cardObuService.saveCardStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(CardStateSendStateFlag.disabled.getValue()), 
				CardStateSendSerTypeEnum.stopWithOutCard.getValue(), prepaidC.getCardNo(), Constant.PREPAIDTYPE, userType);
		//写给铭鸿的清算数据：用户状态信息
		String obuSeq = "";
		Date obuIssueTime = null;
		Date obuExpireTime = null;
		cardObuService.saveUserStateInfo(vehicleBussiness.getCreateTime(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndCard.getValue()), prepaidC.getCardNo(), 
				Constant.PREPAIDTYPE, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
				null,obuSeq, obuIssueTime, obuExpireTime, "储值卡挂起");
	}
	/**
	 * 被动挂起之：处理电子标签信息
	 * @param tagInfoId
	 * @param vehicleInfo
	 * @param vehicleBussiness
	 * @return void
	 */
	public void dealTag4PassiveStop(TagInfo tagInfo,VehicleInfo vehicleInfo,VehicleBussiness vehicleBussiness,Customer customer){
		Date nowDate = vehicleBussiness.getCreateTime();
		
		//保存到历史表
		Long tagInfoHisId = tagInfoHisDao.saveTagInfoHis(tagInfo, "电子标签停用更新");
		//更新电子标签信息
		tagInfo.setCorrectTime(nowDate);//更新时间
		tagInfo.setCorrectOperID(vehicleBussiness.getOperID());
		tagInfo.setCorrectOperName(vehicleBussiness.getOperName());
		tagInfo.setCorrectOperNo(vehicleBussiness.getOperNo());
		tagInfo.setCorrectPlaceID(vehicleBussiness.getPlaceID());
		tagInfo.setCorrectPlaceName(vehicleBussiness.getPlaceName());
		tagInfo.setCorrectPlaceNo(vehicleBussiness.getPlaceNo());
		tagInfo.setTagState(TagStateEnum.stop.getValue());//挂起
		tagInfo.setHisSeqID(tagInfoHisId);
		tagInfo.setBlackFlag(BlackFlagEnum.black.getValue());
		tagInfoDao.update(tagInfo);
		
		//黑名单
		blackListService.saveOBUStopUse(tagInfo.getObuSerial(), nowDate
				, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
				vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(), 
				nowDate);
		
		//写给铭鸿的清算数据：用户状态信息
		String cardNo = "";
		String cardType = "";
		cardObuService.saveUserStateInfo(nowDate, Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
				cardType, Integer.parseInt(vehicleInfo.getVehicleColor()), vehicleInfo.getVehiclePlate(), vehicleInfo.getNSCVehicleType(), 
				tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "车辆被动挂起");
	}
	
	/**
	 * 被动挂起之：删除车辆信息
	 * @param vehicleInfo
	 * @param vehicleBussiness
	 * @return void
	 */
	public void deleteVehicleInfo(VehicleInfo vehicleInfo,VehicleBussiness vehicleBussiness,Customer customer){
		vehicleInfoHisDao.saveVehicleInfoHis(vehicleInfo, "2");// 1表示修改，2表示删除
		vehicleInfoDao.delete(vehicleInfo.getId());
		carObuCardInfoDao.deleteByVehicleId(vehicleInfo.getId());
	}
	
}
