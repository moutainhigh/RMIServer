package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagRecoverReceipt;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.macao.dao.MacaoBankAccountDao;
import com.hgsoft.macao.dao.MacaoCardCustomerDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagRecoverDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.serviceInterface.ITagRecoverService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class TagRecoverService implements ITagRecoverService {
	@Resource
	private TagRecoverDao tagRecoverDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private MacaoCardCustomerDao macaoCardCustomerDao;
	@Resource
	private MacaoDao macaoDao;
	@Resource
	private MacaoBankAccountDao macaoBankAccountDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private OmsParamDao omsParamDao;
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	SequenceUtil sequenceUtil;

	private static Logger logger = Logger.getLogger(TagRecoverService.class.getName());

	@Override
	public List tagRecoverList(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID) {
		List list = null;
		try {
			list = tagRecoverDao.findTagRecovers(tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerID);
		} catch (Exception e) {
			logger.error("查询电子标签恢复信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return list;
	}
	
	@Override
	public Pager tagRecoverListByPager(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerID) {
		return tagRecoverDao.findTagRecoversByPager(pager,tagNo, vehicleColor,
				vehiclePlate, idType, idCode, endSixNo,customerID);
	}
	
	@Override
	public Pager tagRecoverListByPagerForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerID,String bankCode) {
		return tagRecoverDao.findTagRecoversByPagerForAMMS(pager,tagNo, vehicleColor,
				vehiclePlate, idType, idCode, endSixNo,customerID,bankCode);
	}
	
	/**
	 * 澳门通用
	 * @param pager
	 * @param tagNo
	 * @param vehicleColor
	 * @param vehiclePlate
	 * @param idType
	 * @param idCode
	 * @param endSixNo
	 * @param customerID
	 * @param macaoCardCustomer
	 * @return Pager
	 */
	@Override
	public Pager tagRecoverListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer) {
		try {
			pager = tagRecoverDao.findTagRecoversByPager(pager,tagNo, vehicleColor,
					vehiclePlate, idType, idCode, endSixNo,customerID,macaoCardCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签恢复信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}
	/*
	 * 澳门通用的标签恢复详情
	 * (non-Javadoc)
	 * @see com.hgsoft.obu.serviceInterface.ITagRecoverService#findDetailForMacao(java.lang.Long)
	 */
	@Override
	public Map<String, Object> findDetailForMacao(Long tagInfoId) {
		Map<String, Object> tagRecover = null;
		try {
			tagRecover = tagRecoverDao.findMacaoTag(tagInfoId);
		} catch (Exception e) {
			logger.error("查询单个（电子标签恢复）的详细信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return tagRecover;
	}
	
	public Pager tagRecoverListForLian(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,Customer sessionCustomer,Customer listCustomer){
		try {
			pager = tagRecoverDao.findForLian(pager,tagInfo,vehicleInfo,sessionCustomer,listCustomer);
		} catch (Exception e) {
			logger.error("查询电子标签恢复信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return pager;
	}

	@Override
	public Map<String, Object> tagRecoverDetail(Long tagInfoId) {

		Map<String, Object> tagRecover = null;
		try {
			tagRecover = tagRecoverDao.findTagRecoverById(tagInfoId);
		} catch (Exception e) {
			logger.error("查询单个（电子标签恢复）的详细信息失败！"+e.getMessage());
			e.printStackTrace();
		}

		return tagRecover;
	}

	@Override
	public void saveRecoverTagInfo(BigDecimal chargeCost,Long tagInfoId, String tagNo, Long clientID,Long vehicleID, String faultReason, Long installmanID, String memo, Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord,String faultType,Map<String,Object> params) {
		try {
			
			TagInfo oldTagInfo = tagInfoDao.findById(tagInfoId);
			//保存到历史表
			TagInfoHis tagInfoHis = new TagInfoHis();
			
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
			tagInfoHis.setId(Long.parseLong(seq.toString()));
			tagInfoHis.setCreateReason("电子标签恢复");
			tagInfoHisDao.saveHis(tagInfoHis, oldTagInfo);
			
			//2017/6/2 修改：需要将原电子标签的修正人信息update
			TagInfo updateOldTagInfo = new TagInfo();
			
			updateOldTagInfo.setId(oldTagInfo.getId());
			updateOldTagInfo.setCorrectTime(new Date());//更新时间
			updateOldTagInfo.setCorrectOperID(tagBusinessRecord.getOperID());
			updateOldTagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			updateOldTagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			updateOldTagInfo.setCorrectPlaceID(tagBusinessRecord.getOperplaceID());
			updateOldTagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			updateOldTagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
			
			tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
			
			
			
			OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(faultReason));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(faultType));
			
			// 保存电子标签维护记录，维护类型为“恢复”
			BigDecimal SEQ_CSMSTagMainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
			Long id = Long.valueOf(SEQ_CSMSTagMainRecord_NO.toString());
			
			TagMainRecord tagMainRecord = new TagMainRecord();
			tagMainRecord.setId(id);
			tagMainRecord.setTagInfoID(tagInfoId);
			tagMainRecord.setTagNo(tagNo);
			tagMainRecord.setClientID(clientID);
			tagMainRecord.setVehicleID(vehicleID);
			tagMainRecord.setMaintainType("1");
			tagMainRecord.setInstallman(installmanID+"");//TODO
			tagMainRecord.setChargeCost(chargeCost);
			if(omsParamType!=null)tagMainRecord.setFaultType(omsParamType.getParamValue());
			if(omsParamReason!=null)tagMainRecord.setReason(omsParamReason.getParamValue());
			tagMainRecord.setMemo(memo);
			tagMainRecord.setOperID(tagBusinessRecord.getId());
			tagMainRecord.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			tagMainRecord.setOperNo(tagBusinessRecord.getOperNo());
			tagMainRecord.setOperName(tagBusinessRecord.getOperName());
			tagMainRecord.setPlaceNo(tagBusinessRecord.getPlaceName());
			tagMainRecord.setPlaceName(tagBusinessRecord.getPlaceName());
			tagMainRecord.setIssuetime(new Date());
			tagMainRecord.setFaultTypeId(omsParamType.getId());
			tagMainRecord.setReasonId(omsParamReason.getId());
			tagRecoverDao.saveTagMainRecord(tagMainRecord);
			// 保存电子标签业务操作表，BusinessType业务类型为4（恢复）
/*			tagRecoverDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
					vehicleID, recoverReason, installmanID, memo);*/
			
			//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			tagBusinessRecord.setReasonId(omsParamReason.getId());
			tagBusinessRecord.setClientID(clientID);
			tagBusinessRecord.setTagNo(tagNo);
			tagBusinessRecord.setVehicleID(vehicleID);
			/*tagBusinessRecord.setOperID(operID);*/
			tagBusinessRecord.setOperTime(new Date());
			/*tagBusinessRecord.setOperplaceID(operplaceID);*/
			tagBusinessRecord.setBusinessType("4");//恢复
			tagBusinessRecord.setInstallmanID(installmanID);//安装人员
			//tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());
			tagBusinessRecord.setMemo(memo);
			tagBusinessRecord.setFromID(tagInfoId);
			tagBusinessRecord.setRealPrice(chargeCost);//--业务费用
			tagBusinessRecord.setBussinessid(tagMainRecord.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			
			TagInfo tagInfo = tagInfoDao.findById(tagInfoId);
			Customer customer = customerDao.findById(clientID);
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			if(tagInfo!=null)serviceWater.setCardNo(tagInfo.getTagNo());
			if(tagInfo!=null)serviceWater.setObuSerial(tagInfo.getObuSerial());
			serviceWater.setSerType("304");//304电子标签恢复
			
			//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
			serviceWater.setAulAmt(chargeCost);//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(tagBusinessRecord.getOperID());
			serviceWater.setOperName(tagBusinessRecord.getOperName());
			serviceWater.setOperNo(tagBusinessRecord.getOperNo());
			serviceWater.setPlaceId(tagBusinessRecord.getOperplaceID());
			serviceWater.setPlaceName(tagBusinessRecord.getPlaceName());
			serviceWater.setPlaceNo(tagBusinessRecord.getPlaceNo());
			serviceWater.setRemark("自营客服系统：电子标签恢复");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			VehicleInfo vehicle = this.vehicleInfoDao.findById(vehicleID);
			if(vehicle==null){
				vehicle = new VehicleInfo();
			}
			//电子标签恢复回执
			TagRecoverReceipt tagRecoverReceipt = new TagRecoverReceipt();
			tagRecoverReceipt.setTitle("电子标签恢复回执");
			tagRecoverReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			tagRecoverReceipt.setTagNo(tagInfo.getTagNo());
			tagRecoverReceipt.setVehiclePlate(vehicle.getVehiclePlate());
			if(StringUtil.isNotBlank(vehicle.getVehicleColor())) tagRecoverReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicle.getVehicleColor()));
			tagRecoverReceipt.setVehicleWeightLimits(vehicle.getVehicleWeightLimits()+"");
			tagRecoverReceipt.setVehicleEngineNo(vehicle.getVehicleEngineNo());
			tagRecoverReceipt.setVehicleModel(vehicle.getModel());
			if(StringUtil.isNotBlank(vehicle.getVehicleType())) tagRecoverReceipt.setVehicleType(VehicleTypeEnum.getIdTypeEnum(vehicle.getVehicleType()).getName());
			tagRecoverReceipt.setVehicleUserType(VehicleUsingTypeEnum.getName(vehicle.getVehicleUserType()));
			tagRecoverReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicle.getUsingNature()));
			tagRecoverReceipt.setVehicleOwner(vehicle.getOwner());
			tagRecoverReceipt.setVehicleLong(vehicle.getVehicleLong()+"");
			tagRecoverReceipt.setVehicleWidth(vehicle.getVehicleWidth()+"");
			tagRecoverReceipt.setVehicleHeight(vehicle.getVehicleHeight()+"");
			tagRecoverReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicle.getNSCVehicleType()));
			tagRecoverReceipt.setVehicleIdentificationCode(vehicle.getIdentificationCode());
			tagRecoverReceipt.setVehicleAxles(vehicle.getVehicleAxles()+"");
			tagRecoverReceipt.setVehicleWheels(vehicle.getVehicleWheels()+"");
			tagRecoverReceipt.setInstallMan(tagInfo.getInstallmanName());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(TagBussinessTypeEnum.tagRecover.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagRecover.getName());
			receipt.setTagNo(tagRecoverReceipt.getTagNo());
			receipt.setVehicleColor(vehicle.getVehicleColor());
			receipt.setVehiclePlate(vehicle.getVehiclePlate());
			this.saveReceipt(receipt,tagBusinessRecord,tagRecoverReceipt,customer);
			
		} catch (Exception e) {
			logger.error("电子标签恢复失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 澳门通恢复电子标签
	 */
	@Override
	public void saveRecoverTagInfo(Customer customer,BigDecimal chargeCost,Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID, String recoverReason, Long installmanID, String memo, Long operID,
			Long operplaceID,TagBusinessRecord tagBusinessRecord,String replaceType) {
		try {
			
			
			TagInfo oldTagInfo = tagInfoDao.findById(tagInfoId);
			//保存到历史表
			TagInfoHis tagInfoHis = new TagInfoHis();
			
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
			tagInfoHis.setId(Long.parseLong(seq.toString()));
			tagInfoHis.setCreateReason("电子标签恢复");
			tagInfoHisDao.saveHis(tagInfoHis, oldTagInfo);
			
			//2017/6/2 修改：需要将原电子标签的修正人信息update
			TagInfo updateOldTagInfo = new TagInfo();
			
			updateOldTagInfo.setId(oldTagInfo.getId());
			updateOldTagInfo.setCorrectTime(new Date());//更新时间
			updateOldTagInfo.setCorrectOperID(tagBusinessRecord.getOperID());
			updateOldTagInfo.setCorrectOperName(tagBusinessRecord.getOperName());
			updateOldTagInfo.setCorrectOperNo(tagBusinessRecord.getOperNo());
			updateOldTagInfo.setCorrectPlaceID(tagBusinessRecord.getOperplaceID());
			updateOldTagInfo.setCorrectPlaceName(tagBusinessRecord.getPlaceName());
			updateOldTagInfo.setCorrectPlaceNo(tagBusinessRecord.getPlaceNo());
			updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
			
			tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
			
			
			
			
			
			// 保存电子标签维护记录，维护类型为“恢复”
			BigDecimal SEQ_CSMSTagMainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
			Long id = Long.valueOf(SEQ_CSMSTagMainRecord_NO.toString());
			
			TagMainRecord tagMainRecord = new TagMainRecord();
			tagMainRecord.setId(id);
			tagMainRecord.setTagInfoID(tagInfoId);
			tagMainRecord.setTagNo(tagNo);
			tagMainRecord.setClientID(clientID);
			tagMainRecord.setVehicleID(vehicleID);
			tagMainRecord.setMaintainType("1");
			tagMainRecord.setInstallman(installmanID+"");//TODO
			tagMainRecord.setChargeCost(chargeCost);
			
			//OMSParam omsParamType = omsParamDao.findById(Long.parseLong(replaceType));
			OMSParam omsParamType = omsParamDao.findById(Long.parseLong(replaceType));
			OMSParam omsParamReason = omsParamDao.findById(Long.parseLong(recoverReason));
			tagMainRecord.setFaultType(omsParamType.getParamValue());
			tagMainRecord.setFaultTypeId(omsParamType.getId());
			//omsParam = omsParamDao.findById(Long.parseLong(recoverReason));
			tagMainRecord.setReason(omsParamReason.getParamValue());
			tagMainRecord.setReasonId(omsParamReason.getId());
			
			tagMainRecord.setMemo(memo);
			tagMainRecord.setOperID(tagBusinessRecord.getId());
			tagMainRecord.setIssueplaceID(tagBusinessRecord.getOperplaceID());
			tagMainRecord.setOperNo(tagBusinessRecord.getOperNo());
			tagMainRecord.setOperName(tagBusinessRecord.getOperName());
			tagMainRecord.setPlaceNo(tagBusinessRecord.getPlaceName());
			tagMainRecord.setPlaceName(tagBusinessRecord.getPlaceName());
			tagMainRecord.setIssuetime(new Date());
			tagRecoverDao.saveTagMainRecord(tagMainRecord);
			// 保存电子标签业务操作表，BusinessType业务类型为4（恢复）
/*			tagRecoverDao.saveTagBusinessRecord(tagInfoId, tagNo, clientID,
					vehicleID, recoverReason, installmanID, memo);*/
			
			//TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			
			//omsParam = omsParamDao.findById(Long.parseLong(replaceType));
			tagBusinessRecord.setFaultType(omsParamType.getParamValue());
			tagBusinessRecord.setFaultTypeId(omsParamType.getId());
			//omsParam = omsParamDao.findById(Long.parseLong(recoverReason));
			tagBusinessRecord.setReason(omsParamReason.getParamValue());
			tagBusinessRecord.setReasonId(omsParamReason.getId());
			
			tagBusinessRecord.setReason(recoverReason);
			tagBusinessRecord.setFaultType(replaceType);
			tagBusinessRecord.setClientID(clientID);
			tagBusinessRecord.setTagNo(tagNo);
			tagBusinessRecord.setVehicleID(vehicleID);
			/*tagBusinessRecord.setOperID(operID);*/
			tagBusinessRecord.setOperTime(new Date());
			/*tagBusinessRecord.setOperplaceID(operplaceID);*/
			tagBusinessRecord.setBusinessType("4");//恢复
			tagBusinessRecord.setInstallmanID(installmanID);//安装人员
			//tagBusinessRecord.setCurrentTagState("1");//正常
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());
			tagBusinessRecord.setMemo(memo);
			tagBusinessRecord.setFromID(tagInfoId);
			tagBusinessRecord.setRealPrice(chargeCost);//--业务费用
			tagBusinessRecord.setBussinessid(tagMainRecord.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			//获取持卡人信息
			MacaoBankAccount macaoBankAccount = macaoBankAccountDao.findByTagNo(tagNo);
//			MacaoCardCustomer macaoCardCustomer =macaoCardCustomerDao.getMacaoCardCustomerByTagNo(tagNo);
			//获取电子标签信息
			TagInfo tagInfo = macaoDao.getTagInfoByTagNo(tagNo);
			
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setMacaoCardCustomerId(macaoBankAccount.getMainId());
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setCardNo(tagInfo.getTagNo());
			serviceWater.setNewCardNo(tagInfo.getTagNo());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWater.setAmt(tagInfo.getCost());
			serviceWater.setAulAmt(tagInfo.getChargeCost());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWater.setSerType("304");
			serviceWater.setRemark("电子标签恢复");
			serviceWaterDao.save(serviceWater);
			
		} catch (Exception e) {
			logger.error("电子标签恢复失败"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * @Descriptioqn:
	 * @param systemType
	 * @param omsType
	 * @param type
	 * @return
	 * @author lgm
	 * @date 2017年3月28日
	 */
	@Override
	public List<OMSParam> findFirstLevel(String omsType,String type) {
		try {
			return omsParamDao.findFirstLevel(omsType, type);
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}

	/**
	 * @Descriptioqn:
	 * @param replaceType
	 * @return
	 * @author lgm
	 * @date 2017年3月28日
	 */
	@Override
	public List<OMSParam> findSecondLevel(String replaceType) {
		try {
			OMSParam omsParam = omsParamDao.findByParamValue(replaceType);
			return omsParamDao.findSecondLevel(omsParam.getOmsId(),"2","2");
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @author lgm
	 * @date 2017年3月30日
	 */
	@Override
	public void updateWriteBackFlag(Long id) {
		tagRecoverDao.updateWriteBackFlag(id);
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param tagBusinessRecord 电子标签业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, TagBusinessRecord tagBusinessRecord, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.tag.getValue());
		receipt.setCreateTime(tagBusinessRecord.getOperTime());
		receipt.setPlaceId(tagBusinessRecord.getOperplaceID());
		receipt.setPlaceNo(tagBusinessRecord.getPlaceNo());
		receipt.setPlaceName(tagBusinessRecord.getPlaceName());
		receipt.setOperId(tagBusinessRecord.getOperID());
		receipt.setOperNo(tagBusinessRecord.getOperName());
		receipt.setOperName(tagBusinessRecord.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}
}
