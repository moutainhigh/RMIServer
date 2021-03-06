package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagTakeReceipt;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagTakeDetailDao;
import com.hgsoft.obu.dao.TagTakeDetailHisDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoHisDao;
import com.hgsoft.obu.dao.TagTakeInfoDao;
import com.hgsoft.obu.dao.TagTakeInfoHisDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeFeeInfoHis;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.obu.entity.TagTakeInfoHis;
import com.hgsoft.obu.serviceInterface.ITagTakeInfoService;
import com.hgsoft.oms.dao.OmsUtilDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;

import net.sf.json.JSONArray;

@Service
public class TagTakeInfoService implements ITagTakeInfoService {

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private TagTakeFeeInfoDao tagTakeFeeInfoDao;

	@Resource
	private TagTakeInfoDao tagTakeInfoDao;
	@Resource
	private TagTakeInfoHisDao tagTakeInfoHisDao;
	@Resource
	private TagTakeDetailDao tagTakeDetailDao;
	@Resource
	private TagTakeDetailHisDao tagTakeDetailHisDao;
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private TagTakeFeeInfoHisDao tagTakeFeeInfoHisDao; 
	
	@Resource
	private IInventoryService inventoryService;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private OmsUtilDao omsUtilDao;

	private static Logger logger = Logger.getLogger(TagTakeInfoService.class
			.getName());

	public Pager tagTakeInfoList(Pager pager,TagTakeInfo tagTakeInfo,
			String tagNo,String operName,Date starTime,Date endTime) {
		return tagTakeInfoDao.findAllTagTakeInfos(pager,tagTakeInfo, tagNo, operName, starTime, endTime);
	}

	/**
	 * 根据电子标签提货id（tagTakeInfoId）查找关联的明细对象
	 * 
	 * @param mainId
	 * @return
	 */
	public List tagTakeInfoDetailListByMainID(Long mainId) {
		return tagTakeDetailDao.findTagTakeDetailsByMainID(mainId);
	}

	/**
	 * 根据id查找TagTakeInfo对象
	 */
	@Override
	public TagTakeInfo tagTakeInfoDetail(Long id) {
		TagTakeInfo tagTakeInfo = null;
		// List<TagTakeDetail> tagTakeDetails = null;
		try {
			// 获得电子标签提货的详细信息
			tagTakeInfo = tagTakeInfoDao.findById(id);

		} catch (Exception e) {
			logger.error("根据id查询TagTakeInfo对象失败"+e.getMessage());
			e.printStackTrace();
		}
		return tagTakeInfo;
	}

	/**
	 * 添加（电子标签提货）数据
	 */
	@Override
	public Map<String, Object> saveTagTakeInfo(TagTakeInfo tagTakeInfo,TagTakeFeeInfo tagTakeFeeInfo,Long productInfoId,Map<String,Object> params) {

		try {
			Map<String,Object> map=new HashMap<String,Object>();
			boolean success=false;
			String message="";
			
			//判断库存是否可电子标签提货
			InterfaceRecord interfaceRecord = null;
			Map<String, Object> maptemp = inventoryService.tagInterface(interfaceRecord, tagTakeInfo.getBegin_TagNo(), tagTakeInfo.getEnd_TagNo(),
					tagTakeInfo.getTakeplaceID(), tagTakeInfo.getOperID(), tagTakeInfo.getOperName(),"customPoint",productInfoId, "4",tagTakeInfo.getTotalPrice(),"45");
			boolean result = (Boolean) maptemp.get("result");
			if (!result) {
				map.put("success", success);
				map.put("message", maptemp.get("message").toString());
				return map;
			}
			String arr = (String)maptemp.get("serialIds");
			JSONArray array = JSONArray.fromObject(arr);
			List<Map> list = JSON.parseArray(array.toString(),Map.class);
			//------原业务流程-------
			
			// 更新第一步所选的客户信息对应的提货登记余额=当前提货登记余额-总价。
			tagTakeFeeInfo = tagTakeFeeInfoDao.findById(tagTakeFeeInfo.getId());//Bug修改，同一客户多条提货金额数据的扣费情况

			//对于电子标签提货金额登记的余额扣款，使用时不允许从前端获取当前余额，或者重新查询出来后的余额进行set参数，需要在update的时候从自己本身取值..参考方式：update a set amt = amt + (-100)

			//也就是现在要传入的不是nowTakeBalance而是提货所需要的总价
			BigDecimal changePrice = new BigDecimal("-"+tagTakeInfo.getTotalPrice());
			//先移历史再update
			TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
			BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
			tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
			tagTakeFeeInfoHis.setCreateReason("电子标签提货增加后扣除提货登记余额");
			tagTakeFeeInfoHisDao.save(tagTakeFeeInfo, tagTakeFeeInfoHis);
			//这里是扣钱的，所以传进来要是负数
			tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getId());
			if(tagTakeFeeInfo.getTakeBalance().compareTo(tagTakeInfo.getTotalPrice())==-1){
				message = "扣除提货登记余额失败";
				success = false;
				map.put("success", success);
				map.put("message", message);
			}else{
				tagTakeFeeInfoDao.updateTakeBalanceById(tagTakeFeeInfo,changePrice);
			}
			

			// 保存到主表
			// 添加id
			BigDecimal SEQ_CSMSTagTakeInfo_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeInfo_NO");
			tagTakeInfo.setId(Long.valueOf(SEQ_CSMSTagTakeInfo_NO.toString()));
			tagTakeInfo.setTagtakeid(tagTakeFeeInfo.getId());
			Receipt receipt = new Receipt();
			receipt.setId(this.sequenceUtil.getSequenceLong("SEQ_CSMSRECEIPT_NO"));
			tagTakeInfo.setReceiptId(receipt.getId());
			tagTakeInfoDao.save(tagTakeInfo);

			// 根据电子标签号（批量）保存到明细表CSMS_TagTake_Detail
			long beginTag = Long.valueOf(tagTakeInfo.getBegin_TagNo());
			long endTag = Long.valueOf(tagTakeInfo.getEnd_TagNo());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (long tagNo = beginTag; tagNo <= endTag; tagNo++) {
				TagTakeDetail tagTakeDetail = new TagTakeDetail();
				// tagTakeDetail的id
				BigDecimal SEQ_CSMSTagTakeDetail_NO = sequenceUtil
						.getSequence("SEQ_CSMSTagTakeDetail_NO");
				tagTakeDetail.setId(Long.valueOf(SEQ_CSMSTagTakeDetail_NO
						.toString()));
				// 电子标签提货明细表的属性
				tagTakeDetail.setMainID(tagTakeInfo.getId());
				tagTakeDetail.setTagNo(tagNo + "");
				tagTakeDetail.setTagState("0");//
				Date startDate = null;
				Date endDate = null;
				if(list!=null && list.size()>0){
					String obuSerial = (String)list.get((int)(tagNo-beginTag)).get("OBUSERIAL");
					try {
						startDate = format.parse((String)list.get((int)(tagNo-beginTag)).get("STARTDATE"));
						endDate = format.parse((String)list.get((int)(tagNo-beginTag)).get("ENDDATE"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					tagTakeDetail.setObuSerial(obuSerial);
					tagTakeDetail.setStartTime(startDate);
					tagTakeDetail.setEndTime(endDate);
				}
				tagTakeDetailDao.save(tagTakeDetail);// 保存每一个电子标签号到明细表
			}
			
			//业务记录
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			//tagBusinessRecord.setClientID(customer.getId());
			//tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			//tagBusinessRecord.setVehicleID(vehicleInfo.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeInfo.getOperID());
			tagBusinessRecord.setOperplaceID(tagTakeInfo.getTakeplaceID());
			tagBusinessRecord.setOperName(tagTakeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType(TagBussinessTypeEnum.tagTake.getValue());
			//tagBusinessRecord.setInstallmanID(tagInfo.getInstallman());//安装人员
//			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("电子标签提货新增");
			//tagBusinessRecord.setRealPrice(tagInfo.getChargeCost());//--业务费用
//			if(tagInfo.getObuSerial()!=null){
//				tagBusinessRecord.setObuSerial(tagInfo.getObuSerial());
//			}
			tagBusinessRecord.setBussinessid(tagTakeInfo.getId());//业务id记录提货表id
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			//流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			
			//if(customer!=null)serviceWater.setCustomerId(customer.getId());
			//if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			//if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			//serviceWater.setCardNo(tagInfo.getTagNo());
			//serviceWater.setObuSerial(tagInfo.getObuSerial());
			
			serviceWater.setSerType(ServiceWaterSerType.tagTakeAdd.getValue());
			
			//serviceWater.setAmt(tagInfo.getCost());//应收金额
			//serviceWater.setAulAmt(tagInfo.getChargeCost());//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			//serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(tagTakeInfo.getOperID());
			serviceWater.setOperName(tagTakeInfo.getOperName());
			serviceWater.setOperNo(tagTakeInfo.getOperNo());
			serviceWater.setPlaceId(tagTakeInfo.getTakeplaceID());
			serviceWater.setPlaceName(tagTakeInfo.getPlaceName());
			serviceWater.setPlaceNo(tagTakeInfo.getPlaceNo());
			serviceWater.setRemark("自营客服系统：电子标签提货新增");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//电子标签提货回执
			TagTakeReceipt tagTakeReceipt = new TagTakeReceipt();
			tagTakeReceipt.setTitle("电子标签提货回执");
			tagTakeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			tagTakeReceipt.setTakeBeginTagNo(tagTakeInfo.getBegin_TagNo());
			tagTakeReceipt.setTakeEndTagNo(tagTakeInfo.getEnd_TagNo());
			tagTakeReceipt.setTakeAmount(tagTakeInfo.getTakeAmount()+"");
			tagTakeReceipt.setTakeTagPrice(NumberUtil.get2Decimal(tagTakeInfo.getTagPrice().doubleValue()*0.01));
			tagTakeReceipt.setTaketotalPrice(NumberUtil.get2Decimal(tagTakeInfo.getTotalPrice().doubleValue()*0.01));
			tagTakeReceipt.setTakeBalance(NumberUtil.get2Decimal(tagTakeFeeInfo.getTakeBalance().subtract(tagTakeInfo.getTotalPrice()).doubleValue()*0.01));
			receipt.setTypeCode(TagBussinessTypeEnum.tagTake.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagTake.getName());
			Customer customer = new Customer();
			customer.setOrgan(tagTakeFeeInfo.getClientName());
			customer.setIdType(tagTakeFeeInfo.getCertType());
			customer.setIdCode(tagTakeFeeInfo.getCertNumber());
			this.saveReceipt(receipt,tagBusinessRecord,tagTakeReceipt,customer);

			//提货成功后，更新营运接口调用登记记录的客服状态
			interfaceRecord = (InterfaceRecord) maptemp.get("interfaceRecord");
			if (interfaceRecord != null&&interfaceRecord.getStartCode()!=null&&interfaceRecord.getEndCode()!=null) {
				interfaceRecord.setCsmsState("1");
				interfaceRecordDao.update(interfaceRecord);
				success = true;
			}

			message = "电子标签提货成功";
			map.put("success", success);
			map.put("message", message);
			return map;
		} catch (ApplicationException e) {
			logger.error("保存TagTakeInfo对象失败"+e.getMessage());
			e.printStackTrace();
			throw
			new ApplicationException("保存TagTakeInfo对象失败");
		}

	}

	/**
	 * 根据id删除该条数据
	 */
	@Override
	public String deleteTagTakeInfo(Long id,TagTakeInfo temp,String flagStr,Long productInfo) {

		try {
			TagTakeInfo tagTakeInfo = tagTakeInfoDao.findById(id);
			/*
			 * author:zhangzw
			 * date:2017/08/08 16:24
			 * memo:删除电子标签提货登记前检验该电子标签是否日结，如果日结，则不允许删除
			 */
			if(tagTakeInfo.getIsDaySet()!=null&&StringUtil.isEquals(tagTakeInfo.getIsDaySet(), "1")){
				return "该电子标签已经日结，不能删除";
			}
			InterfaceRecord interfaceRecordRefund = null;
			Map<String, Object> mapRefund = null;
			boolean result = false;
			//判断库存是否可冲正电子标签提货信息
			BigDecimal price = new BigDecimal("0");
			if(tagTakeInfo.getTotalPrice()!=null) price = tagTakeInfo.getTotalPrice().multiply(new BigDecimal("-1"));
			mapRefund = inventoryService.tagInterface(interfaceRecordRefund, tagTakeInfo.getBegin_TagNo(), tagTakeInfo.getEnd_TagNo(),
					temp.getTakeplaceID(), temp.getOperID(), temp.getOperName(), flagStr, productInfo, "5",price,"46");
			result = (Boolean) mapRefund.get("result");
			if (!result) {
				return mapRefund.get("message").toString();
			}
			
			//------原业务流程-------
			
			// 删除后处理(点击确认删除的时候)：
			// A.更新提货登记金额=当前提货登记金额+总价
			// B对电子标签明细表中的每个电子标签号增加入库记录

			TagTakeFeeInfo tagTakeFeeInfo = tagTakeFeeInfoDao.findById(tagTakeInfo.getTagtakeid());
			
			if(tagTakeFeeInfo != null){
				//BigDecimal nowTagTakeBalance = tagTakeFeeInfo.getTakeBalance().add(tagTakeInfo.getTotalPrice());
				
				//tagTakeFeeInfo.setTakeBalance(nowTagTakeBalance);
				//先移历史再update
				TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
				BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
				tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
				tagTakeFeeInfoHis.setCreateReason("电子标签提货删除后增加提货登记余额");
				tagTakeFeeInfoHisDao.save(tagTakeFeeInfo, tagTakeFeeInfoHis);
				//这里是加钱，所以传进来的是正数
				tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getId());
				tagTakeFeeInfoDao.updateTakeBalanceById(tagTakeFeeInfo,tagTakeInfo.getTotalPrice());
			}

			TagTakeInfoHis tagTakeInfoHis = new TagTakeInfoHis();
			//添加历史id
			//SEQ_CSMSTagTakeInfoHis_NO
			BigDecimal SEQ_CSMSTagTakeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeInfoHis_NO");
			tagTakeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeInfoHis_NO.toString()));
			
			tagTakeInfoHis.setCreateReason("删除");
			tagTakeInfoHis.setCreateDate(new Date());
			
			// 删除记录之前:
			// 将明细移入历史表（先根据TagTakeInfo的id找出相关的所有明细表数据，再循环移入明细历史表）
			List<TagTakeDetail> tagTakeDetails = tagTakeDetailDao
					.findTagTakeDetailsByMainID(id);
			for (TagTakeDetail tagTakeDetail : tagTakeDetails) {
				tagTakeDetail.setMainID(tagTakeInfoHis.getId());
				tagTakeDetail.setMemo("删除");// 备注说明移入历史表的原因
				tagTakeDetailHisDao.save(tagTakeDetail);
			}

			// 删除明细记录
			tagTakeDetailDao.delete(id);
			// 将记录移到历史表中
			tagTakeInfoHisDao.save(tagTakeInfo, tagTakeInfoHis);

			tagTakeInfoDao.delete(id);// 最后才删除记录
			
			
			//冲正成功后，更新营运接口调用登记记录的客服状态
			if(mapRefund!=null){
				interfaceRecordRefund = (InterfaceRecord) mapRefund.get("interfaceRecord");
				if (interfaceRecordRefund != null&&interfaceRecordRefund.getStartCode()!=null&&interfaceRecordRefund.getEndCode()!=null) {
					interfaceRecordRefund.setCsmsState("1");
					interfaceRecordDao.update(interfaceRecordRefund);
					
					return "true";
				}
			}
			
			return "删除失败";
		} catch (ApplicationException e) {
			logger.error("删除（电子标签提货）失败"+e.getMessage());// 记录删除日志
			e.printStackTrace();
			throw new ApplicationException("删除TagTakeInfo（电子标签提货）对象失败");
		}

	}

	/**
	 * 找出电子标签提货金额登记表中，提货登记余额>0的记录。
	 */
	@Override
	public List tagTakeInfoListByTakeBalance(TagTakeInfo tagTakeInfo) {

		return tagTakeInfoDao.findTagTakeInfoByTakeBalance(tagTakeInfo);
	}

	@Override
	public boolean checkTakeBalance(TagTakeFeeInfo tagTakeFeeInfo,TagTakeInfo tagTakeInfo) {
		boolean result = false;
		/*TagTakeFeeInfo tagTakeFeeInfo = tagTakeFeeInfoDao
				.findTagTakeInfoByThreeValue(tagTakeInfo);*/
		tagTakeFeeInfo = tagTakeFeeInfoDao.findById(tagTakeFeeInfo.getId());
		//System.out.println(tagTakeInfo.getCertType());
		//System.out.println(tagTakeFeeInfo);
		// 如果总价<=比提货登记余额，返回true
		if ((tagTakeInfo.getTotalPrice()).compareTo(tagTakeFeeInfo
				.getTakeBalance()) <= 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 判断该批电子标签是否已有发行
	 */
	@Override
	public boolean sureDelete(Long tagTakeInfoId) {
		
		boolean result = true;
		
		List<TagTakeDetail> tagTakeDetails = tagTakeDetailDao.findTagTakeDetailsByMainID(tagTakeInfoId);
		
		for(TagTakeDetail tagTakeDetail:tagTakeDetails){
			if(tagTakeDetail.getTagState().equals("1")){
				result = false;
			}
		}
		
		return result;
	}

	@Override
	public Map<String, Object> checkInventory(TagTakeInfo tagTakeInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//根据库存的状态获取结束点击标签
		if(tagTakeDetailDao.findByTagNo(tagTakeInfo.getBegin_TagNo(), tagTakeInfo.getEnd_TagNo())!=null ){
			map.put("result", "1");
			map.put("message", "始末电子标签已被提货");
			return map;//始末电子标签已被提货
		}
		if( tagInfoDao.findByTagNo(tagTakeInfo.getBegin_TagNo(), tagTakeInfo.getEnd_TagNo())!=null ){
			map.put("result", "2");
			map.put("message", "始末电子标签已经发行");
			return map;//始末电子标签已经发行
		}
		//可提货
		map.put("result", "0");
		map.put("message", "可提货");
		return map;
	}

	@Override
	public String getSourceType(String startCode, String endCode) {
		return omsUtilDao.getSourceType(startCode, endCode);
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
