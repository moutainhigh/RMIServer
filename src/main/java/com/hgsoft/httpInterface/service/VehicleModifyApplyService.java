package com.hgsoft.httpInterface.service;

import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.VehicleModifyApplyDao;
import com.hgsoft.httpInterface.entity.VehicleModifyApply;
import com.hgsoft.httpInterface.serviceInterface.IVehicleModifyApplyService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class VehicleModifyApplyService implements IVehicleModifyApplyService {
	
	private static Logger logger = Logger.getLogger(VehicleModifyApplyService.class.getName());
	
	@Resource
	private VehicleModifyApplyDao vehicleModifyApplyDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	private SequenceUtil sequenceUtil;

	@Override
	public Pager list(Pager pager, String organ,String vehiclePlate, String vehicleColor, 
			String appState,String approverName,String appTime,String createTime) {
		// TODO Auto-generated method stub
		try {
			return vehicleModifyApplyDao.list(pager,organ,vehiclePlate, vehicleColor, appState, approverName, appTime, createTime);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，车辆信息修改审核列表查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，车辆息修改审核列表查询失败！");
		}
	}

	@Override
	public boolean approval(Long id, String appState, Long approverId, String approverNo, String approverName,
			String appTime) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = format.parse(appTime);
			vehicleModifyApplyDao.approval(id, appState, approverId, approverNo, approverName, time);
			if("2".equals(appState)){
				VehicleModifyApply vehicleModifyApply = vehicleModifyApplyDao.findById(id);
				VehicleInfo vehicleInfo = vehicleInfoDao.findById(vehicleModifyApply.getVehicleId());
				
				
				//添加车辆信息历史表
				VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
				BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
				vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
				vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
				vehicleInfoHis.setGenTime(new Date());
				vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
				vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
				
				//更新车辆信息表
				vehicleInfo.setVehicleType(vehicleModifyApply.getNewVehicleType());
				vehicleInfo.setVehicleWeightLimits(vehicleModifyApply.getNewVehicleWeightLimits());
				vehicleInfo.setNSCVehicleType(vehicleModifyApply.getNewNSCVehicleType());
				vehicleInfoDao.updateNotNull(vehicleInfo);
			}
			return true;
		} catch (ApplicationException  e) {
			logger.error(e.getMessage()+"营运系统，车辆信息修改审核失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，车辆信息修改审核失败！");
		} catch (ParseException e) {
			logger.error(e.getMessage()+"时间转换失败！");
			e.printStackTrace();
			throw new ApplicationException("时间转换失败！");
		}
	}

	@Override
	public Map<String, Object> detailList(Long id, String rootPath) {
		// TODO Auto-generated method stub
		try {
			return vehicleModifyApplyDao.detailList(id,rootPath);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，车辆信息修改审核详细信息查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，车辆信息修改审核详细信息查询失败！");
		}
	}

}
