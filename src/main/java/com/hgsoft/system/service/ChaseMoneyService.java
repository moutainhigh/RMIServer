package com.hgsoft.system.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.*;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.other.ChaseMoneyReceipt;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.clearInterface.serviceInterface.IChaseMoneyInterfaceService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ExceptionListNoDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ChaseMoneyInfo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ExceptionListNo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.system.serviceInterface.IChaseMoneyService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
@Service
public class ChaseMoneyService implements IChaseMoneyService {
	private static Logger logger = Logger.getLogger(ChaseMoneyService.class.getName());
	
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private ExceptionListNoDao exceptionListNoDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private IChaseMoneyInterfaceService chaseMoneyInterfaceService;

	@Override
	public Pager findByPager(Pager pager, ExceptionListNo exceptionListNo,Long customerId) {
		try {
			return exceptionListNoDao.findByPager(pager,exceptionListNo,customerId);
		} catch (ApplicationException e) {
			throw new ApplicationException("查询追款信息列表失败");
		}
	}

	@Override
	public ExceptionListNo find(Long id) {
		return exceptionListNoDao.findById(id);
	}

	@Override
	public void update(ChaseMoneyInfo chaseMoneyInfo) {
		/*ChaseMoneyInfo temp = chaseMoneyDao.findById(chaseMoneyInfo.getId());
		temp.setFinish_flag(chaseMoneyInfo.getFinish_flag());
		temp.setUpdateTime(chaseMoneyInfo.getUpdateTime());
		temp.setProcessMan(chaseMoneyInfo.getProcessMan());
		temp.setRealPayment(chaseMoneyInfo.getRealPayment());
	    
		try {
			chaseMoneyDao.update(temp);
		} catch (ApplicationException e) {
			logger.error("更新追款信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新追款信息失败");
		}*/
		
	}

	@Override
	public Map<String, Object> saveChaseMoney(ExceptionListNo exceptionListNo,MainAccountInfo mainAccountInfo,CusPointPoJo cusPointPoJo,SysAdmin sysAdmin,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		Customer customer = customerDao.findById(mainAccountInfo.getMainId());
		ExceptionListNo tmpExceptionListNo = exceptionListNoDao.findById(exceptionListNo.getId());
		
		
		UnifiedParam unifiedParam = new UnifiedParam();
		unifiedParam.setType(AccChangeTypeEnum.chaseMoney.getValue());//追款管理收取
		unifiedParam.setChangePrice(exceptionListNo.getProceeds());//实收金额
		unifiedParam.setMainAccountInfo(mainAccountInfo);
		unifiedParam.setOperId(sysAdmin.getId());
		unifiedParam.setPlaceId(cusPointPoJo.getCusPoint());
		unifiedParam.setOperName(sysAdmin.getUserName());
		unifiedParam.setOperNo(sysAdmin.getStaffNo());
		unifiedParam.setPlaceName(cusPointPoJo.getCusPointName());
		unifiedParam.setPlaceNo(cusPointPoJo.getCusPointCode());
		
		if(!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
			resultMap.put("result", "false");
			resultMap.put("message", "账户可用余额不足");
			return resultMap;
		}
		
		//更新追款记录（调用营运数据源，更新营运数据库）
		exceptionListNoDao.updateNotNull(exceptionListNo);
		//发送给铭鸿追款记录
		ExceptionListNo tmpException  = exceptionListNoDao.findById(exceptionListNo.getId());
		chaseMoneyInterfaceService.saveChaseMoney(tmpException);
		//流水
		//调整的客服流水
		ServiceWater serviceWater = new ServiceWater();
		Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
		serviceWater.setId(serviceWater_id);
		
		if(customer!=null)serviceWater.setCustomerId(customer.getId());
		if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
		if(customer!=null)serviceWater.setUserName(customer.getOrgan());
		serviceWater.setCardNo(tmpExceptionListNo.getCardNo());
		//serviceWater.setObuSerial(tagInfo.getObuSerial());
		serviceWater.setSerType(ServiceWaterSerType.specialFee.getValue());
		
		serviceWater.setAmt(tmpExceptionListNo.getReceivable());//应收金额
		serviceWater.setAulAmt(exceptionListNo.getProceeds());//实收金额
		//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
		//serviceWater.setTagInfoBussinessId(tagBusinessRecord.getId());
		//serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
		serviceWater.setOperId(sysAdmin.getId());
		serviceWater.setOperName(sysAdmin.getUserName());
		serviceWater.setOperNo(sysAdmin.getStaffNo());
		serviceWater.setPlaceId(cusPointPoJo.getCusPoint());
		serviceWater.setPlaceName(cusPointPoJo.getCusPointName());
		serviceWater.setPlaceNo(cusPointPoJo.getCusPointCode());
		serviceWater.setRemark("自营客服系统：追款管理收取");
		serviceWater.setOperTime(new Date());
		
		serviceWaterDao.save(serviceWater);

		//追款回执
		ChaseMoneyReceipt chaseMoneyReceipt = new ChaseMoneyReceipt();
		chaseMoneyReceipt.setTitle("追款回执");
		chaseMoneyReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
		chaseMoneyReceipt.setCustomerSecondNo(customer.getSecondNo());
		chaseMoneyReceipt.setCustomerSecondName(customer.getSecondName());
		chaseMoneyReceipt.setProceeds(NumberUtil.get2Decimal(exceptionListNo.getProceeds().doubleValue()*0.01));
		chaseMoneyReceipt.setCustomerNo(customer.getUserNo());
		chaseMoneyReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		chaseMoneyReceipt.setCustomerIdCode(customer.getIdCode());
		chaseMoneyReceipt.setCustomerName(customer.getOrgan());

		Receipt receipt = new Receipt();
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.other.getValue());
		receipt.setTypeCode(OtherBussinessTypeEnum.chaseMoney.getValue());
		receipt.setTypeChName(OtherBussinessTypeEnum.chaseMoney.getName());
		receipt.setCreateTime(new Date());
		receipt.setPlaceId(cusPointPoJo.getCusPoint());
		receipt.setPlaceNo(cusPointPoJo.getCusPointCode());
		receipt.setPlaceName(cusPointPoJo.getCusPointName());
		receipt.setOperId(sysAdmin.getId());
		receipt.setOperNo(sysAdmin.getStaffNo());
		receipt.setOperName(sysAdmin.getUserName());
		receipt.setOrgan(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(chaseMoneyReceipt).toString());
		this.receiptDao.saveReceipt(receipt);
		resultMap.put("result", "true");
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> findByCardNo(String cardNo,String type) {
		return exceptionListNoDao.findByCardNo(cardNo, type);
	}

	@Override
	public Map<String, Object> findStationAndRoad(ExceptionListNo exceptionListNo) {
		Map<String, Object> infoMap = new HashMap<String,Object>();
		Map<String, Object> inRoadMap = exceptionListNoDao.findRoadInfo(exceptionListNo.getInRoadInfo());
		Map<String, Object> outRoadMap = exceptionListNoDao.findRoadInfo(exceptionListNo.getOutRoadInfo());
		Map<String, Object> inStationMap = exceptionListNoDao.findStationInfo(exceptionListNo.getInSiteInfo());
		Map<String, Object> outStationMap = exceptionListNoDao.findStationInfo(exceptionListNo.getOutSiteInfo());
		
		infoMap.put("INROADNAME", inRoadMap.get("ROADNAME"));
		infoMap.put("OUTROADNAME", outRoadMap.get("ROADNAME"));
		infoMap.put("INSTATIONNAME", inStationMap.get("STATIONNAME"));
		infoMap.put("OUTSTATIONNAME", outStationMap.get("STATIONNAME"));
		return infoMap;
	}


}
