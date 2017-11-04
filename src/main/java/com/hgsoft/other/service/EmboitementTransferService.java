package com.hgsoft.other.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.BillGetHisDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.other.dao.EmboitementTransferDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.serviceInterface.IEmboitementTransferService;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.prepaidC.entity.PrepaidCTransferDetail;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class EmboitementTransferService implements IEmboitementTransferService {
	
	private static Logger logger = Logger.getLogger(EmboitementTransferService.class.getName());
	
	@Resource
	private ReceiptDao receiptDao;
	
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	
	@Resource
	private EmboitementTransferDao emboitementTransferDao;
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	
	@Resource
	private PrepaidCDao prepaidCDao;
	
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	
	@Resource
	private BillGetDao billGetDao;
	
	@Resource
	private BillGetHisDao billGetHisDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;

	@Override
	public VehicleInfo findById(Long id) {
		return vehicleInfoDao.findById(id);
	}

	@Override
	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color, String cardno, String tagno) {
		return emboitementTransferDao.findByPlateAndColor(pager, customer, plate, color, cardno, tagno);
	}


	@Override
	public boolean saveTransfer(VehicleInfo vehicleInfo, Long customerId,
			Long newCutomerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		boolean flag = true;
		try {
			//保存车辆信息历史表和更新车辆信息表和保存客户信息业务记录表
			CustomerBussiness customerBussiness = new CustomerBussiness();
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
		
			vehicleInfo.setCustomerID(newCutomerId);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			emboitementTransferDao.update(vehicleInfo);
		
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customerId);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setVehicleHisId(vehicleInfoHis.getId());
			customerBussiness.setMigratecustomerId(newCutomerId);
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileMigrate.getValue());//迁移
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getUserName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			//根据车辆ID查询车卡标
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			
			//跟据车卡标查电子标签ID
			Long tagInfoId = carObuCardInfo.getTagID();
			TagInfo tagInfo=tagInfoDao.findById(tagInfoId);
			
			//保存电子标签信息历史表和更新电子标签信息表和保存电子标签信息业务记录表
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			TagInfoHis tagInfoHis = new TagInfoHis();
			tagInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSTaginfoHis_NO"));
			tagInfoHis.setCreateReason("电子标签过户");
			tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
			tagInfo.setClientID(newCutomerId);
			tagInfo.setHisSeqID(tagInfoHis.getId());
			emboitementTransferDao.update(tagInfo);
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setClientID(customerId);
			tagBusinessRecord.setTagNo(tagInfo.getTagNo());
			tagBusinessRecord.setVehicleID(vehicleInfo.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(sysAdmin.getId());
			tagBusinessRecord.setOperplaceID(cusPointPoJo.getCusPoint());
			tagBusinessRecord.setOperName(sysAdmin.getUserName());
			tagBusinessRecord.setOperNo(sysAdmin.getStaffNo());
			tagBusinessRecord.setPlaceName(cusPointPoJo.getCusPointName());
			tagBusinessRecord.setPlaceNo(cusPointPoJo.getCusPointCode());
			
			tagBusinessRecord.setBusinessType(TagBussinessTypeEnum.tagMirate.getValue());//迁移
			tagBusinessRecord.setCurrentTagState(tagInfo.getTagState());//正常
			tagBusinessRecord.setMemo("迁移");
			tagBusinessRecord.setRealPrice(new BigDecimal("0"));//--业务费用
			tagBusinessRecord.setBussinessid(tagInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			
			//跟据车卡标查储值卡ID
			Long prepaidCId = carObuCardInfo.getPrepaidCID();
			PrepaidC prepaidC=prepaidCDao.findById(prepaidCId);
			
			//保存储值卡信息历史表和更新储值卡信息表和保存储值卡信息业务记录表
			PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(),"9",prepaidC);
			BigDecimal PrePaidC_his_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_his_NO");
			prepaidCHis.setId(Long.valueOf(PrePaidC_his_NO.toString()));
			prepaidCDao.saveHis(prepaidCHis);
			prepaidC.setHisSeqID(prepaidCHis.getId());
			prepaidC.setCustomerID(newCutomerId);
			prepaidC.setSuit("1");
			emboitementTransferDao.update(prepaidC);
			prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
			prepaidCBussiness.setUserid(newCutomerId);
			prepaidCBussiness.setUserid(customerId);
			prepaidCBussiness.setCardno(prepaidC.getCardNo());
			prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardTranfer.getValue());//过户
			prepaidCBussiness.setFaceValue(prepaidC.getFaceValue());
			prepaidCBussiness.setTransferSum(prepaidC.getTransferSum());
			prepaidCBussiness.setReturnMoney(prepaidC.getReturnMoney());
			
			prepaidCBussiness.setOperid(sysAdmin.getId());
			prepaidCBussiness.setOperNo(sysAdmin.getStaffNo());
			prepaidCBussiness.setOperName(sysAdmin.getUserName());
			prepaidCBussiness.setPlaceid(cusPointPoJo.getCusPoint());
			prepaidCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			prepaidCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			prepaidCBussiness.setTradetime(new Date());
			prepaidCBussinessDao.save(prepaidCBussiness);
			
			//修改服务方式
			BillGet billGet = billGetDao.findPreCardNo(prepaidC.getCardNo());
			if(billGet!=null){
				seq = sequenceUtil.getSequenceLong("SEQ_CSMS_bill_get_his_NO");
				BillGetHis billGetHis = new BillGetHis();
				billGetHis.setId(seq);
				billGetHis.setGenReason("1");//修改
				billGetHisDao.saveHis(billGetHis, billGet);
				billGet.setMainId(newCutomerId);
				billGet.setHisSeqId(seq);
				emboitementTransferDao.update(billGet);
			}
			
			//保存储值卡过户记录表
			PrepaidCTransfer prepaidCTransfer = new PrepaidCTransfer();
			prepaidCTransfer.setId(sequenceUtil.getSequenceLong("SEQ_CSMSPrepaidCTransfer_NO"));
			prepaidCTransfer.setOldCustomerId(customerId);
			prepaidCTransfer.setNewCustomerId(newCutomerId);
			prepaidCTransfer.setOperNo(sysAdmin.getStaffNo());
			prepaidCTransfer.setOperName(sysAdmin.getUserName());
			prepaidCTransfer.setPlaceNo(cusPointPoJo.getCusPointCode());
			prepaidCTransfer.setPlaceName(cusPointPoJo.getCusPointName());
			prepaidCTransfer.setOperTime(new Date());
			prepaidCDao.saveTransfer(prepaidCTransfer);
			
			//保存储值卡过户明细表
			PrepaidCTransferDetail prepaidCTransferDetail = new PrepaidCTransferDetail();
			prepaidCTransferDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSPrepaidCTransferDt_NO"));
			prepaidCTransferDetail.setCardNo(prepaidC.getCardNo());
			prepaidCTransferDetail.setTransferId(prepaidCTransfer.getId());
			List<PrepaidCTransferDetail> list = new ArrayList<PrepaidCTransferDetail>();
			list.add(prepaidCTransferDetail);
			prepaidCDao.batchSaveTransferDetail(list);
			
			//保存客服流水
			List<ServiceFlowRecord> serviceFlowRecordList = new ArrayList<ServiceFlowRecord>();
			ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
			serviceFlowRecord.setServiceFlowNO(sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO")+"");
			serviceFlowRecord.setServicePTypeCode(1);
			serviceFlowRecord.setServiceTypeCode(9);   //储值卡过户9
			serviceFlowRecord.setClientID(customerId);
			serviceFlowRecord.setCardTagNO(prepaidC.getCardNo());
			serviceFlowRecord.setOperID(prepaidCTransfer.getOperId());
			serviceFlowRecord.setOperNo(prepaidCTransfer.getOperNo());
			serviceFlowRecord.setOperName(prepaidCTransfer.getOperName());
			serviceFlowRecord.setPlaceID(prepaidCTransfer.getPlaceId());
			serviceFlowRecord.setPlaceNo(prepaidCTransfer.getPlaceNo());
			serviceFlowRecord.setPlaceName(prepaidCTransfer.getPlaceName());
			serviceFlowRecordList.add(serviceFlowRecord);
			
			
			receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);
			serviceFlowRecordDao.batchSaveServiceFlowRecord(serviceFlowRecordList);
			
			
			Customer customer = customerDao.findById(customerId);
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(prepaidCBussiness.getCardno());
			serviceWater.setSerType("405");//套装过户
			serviceWater.setRemark("自营客服系统：套装过户");
			//serviceWater.setFlowState("1");//正常
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
			
			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);
			
			
			
		} catch (ApplicationException e) {
			flag=false;
			logger.error(e.getMessage()+"套装过户失败,车牌号码"+vehicleInfo.getVehiclePlate()+"，车牌颜色"+vehicleInfo.getVehicleColor());
			throw new ApplicationException();
		}
		return flag;
	}
	
}
