package com.hgsoft.system.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.ServiceFundMonitorBusinessTypeEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceFundMonitorDao;
import com.hgsoft.system.entity.ServiceFundMonitor;
import com.hgsoft.system.entity.ServiceFundMonitorHis;
import com.hgsoft.system.serviceInterface.IServiceFundMonitorService;
import com.hgsoft.utils.SequenceUtil;
@Service
public class ServiceFundMonitorService implements IServiceFundMonitorService {
	@Resource
	private ServiceFundMonitorDao serviceFundMonitorDao;
	@Resource
	SequenceUtil sequenceUtil;
	
	private static Logger logger = Logger.getLogger(ServiceFundMonitorService.class.getName());
	

	@Override
	public List<ServiceFundMonitor> findAll() {
		return serviceFundMonitorDao.findAll();
	}

	@Override
	public ServiceFundMonitor findByCustomPoint(Long id) {
		return serviceFundMonitorDao.findByCustomPoint(id);
	}

	@Override
	public ServiceFundMonitor findFundMonitorRechargeByCustomPoint(Long customPointId) {
		return serviceFundMonitorDao.findByCustomPoint(customPointId, ServiceFundMonitorBusinessTypeEnum.recharge.getValue());
	}

	@Override
	public void saveFundMonitor(ServiceFundMonitor serviceFundMonitor){
		try {
			BigDecimal fundMonitorID = sequenceUtil.getSequence("SEQ_CSMSSERVICEFUNDMONITOR_NO");
			serviceFundMonitor.setId(Long.valueOf(fundMonitorID.toString()));
			serviceFundMonitorDao.saveFundMonitor(serviceFundMonitor);
		}  catch (ApplicationException e) {
			logger.error("保存授权服务点资金监控记录失败", e);
			throw new ApplicationException();
		}
	}

	@Override
	public ServiceFundMonitor findByFundId(Long id) {
		return serviceFundMonitorDao.findByFundId(id);
	}

	@Override
	public void updateFundMonitor(ServiceFundMonitor oldServiceFundMonitor, ServiceFundMonitorHis serviceFundMonitorHis) {
		try {
			BigDecimal fundMonitorHisId = sequenceUtil.getSequence("SEQ_CSMSSERVICEFUNDMONITORHIS");
			serviceFundMonitorHis.setId(Long.valueOf(fundMonitorHisId.toString()));
			serviceFundMonitorDao.updateFundMonitor(oldServiceFundMonitor);
			serviceFundMonitorDao.saveFundMonitorHis(serviceFundMonitorHis);
		} catch (ApplicationException e) {
			logger.error("修改授权服务点资金监控记录失败", e);
			throw new ApplicationException();
		}
	}

	@Override
	public int updateFundMonitorRechargeByCustomPoint(Long customPointId, BigDecimal diffMoney) {
		return serviceFundMonitorDao.updateFundMonitor(customPointId, ServiceFundMonitorBusinessTypeEnum.recharge.getValue(), diffMoney);
	}

	@Override
	public void deleteFundMonitor(Long id,ServiceFundMonitorHis serviceFundMonitorHis){
		try {
			BigDecimal fundMonitorHisId = sequenceUtil.getSequence("SEQ_CSMSSERVICEFUNDMONITORHIS");
			serviceFundMonitorHis.setId(Long.valueOf(fundMonitorHisId.toString()));
			serviceFundMonitorDao.saveFundMonitorHis(serviceFundMonitorHis);
			serviceFundMonitorDao.delete(id);
		} catch (ApplicationException e) {
			logger.error("删除授权服务点资金监控记录失败", e);
			throw new ApplicationException();
		}
	}

	public ServiceFundMonitorDao getServiceFundMonitorDao() {
		return serviceFundMonitorDao;
	}

	public SequenceUtil getSequenceUtil() {
		return sequenceUtil;
	}

	public void setServiceFundMonitorDao(ServiceFundMonitorDao serviceFundMonitorDao) {
		this.serviceFundMonitorDao = serviceFundMonitorDao;
	}

	public void setSequenceUtil(SequenceUtil sequenceUtil) {
		this.sequenceUtil = sequenceUtil;
	}

}
