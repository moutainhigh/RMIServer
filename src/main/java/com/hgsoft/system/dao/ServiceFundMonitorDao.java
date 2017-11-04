package com.hgsoft.system.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hgsoft.utils.SqlParamer;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.ServiceFundMonitor;
import com.hgsoft.system.entity.ServiceFundMonitorHis;

@Repository
public class ServiceFundMonitorDao extends BaseDao{

	public List<ServiceFundMonitor> findAll() {
		String sql = "select * from CSMS_SERVICEFUNDMONITOR order by id ";
		List<Object> list = jdbcUtil.selectForList(sql);
		List<ServiceFundMonitor> fundMonitorList = new ArrayList<ServiceFundMonitor>();
		for (int i = 0; i < list.size(); i++) {
			ServiceFundMonitor serviceFundMonitor = null;
			try {
				serviceFundMonitor = (ServiceFundMonitor) convert2Bean((Map<String, Object>) list.get(i), new ServiceFundMonitor());
			} catch (Exception e) {
				e.printStackTrace();
			}
			fundMonitorList.add(serviceFundMonitor);
		}
		return fundMonitorList;
	}

	public ServiceFundMonitor findByCustomPoint(Long id) {
		String sql = "select * from CSMS_SERVICEFUNDMONITOR where customPoint = "+id;
		List<Map<String, Object>> list = queryList(sql);
		ServiceFundMonitor serviceFundMonitor = null;
		try {
			if (!list.isEmpty()) {
				serviceFundMonitor = new ServiceFundMonitor();
				this.convert2Bean(list.get(0), serviceFundMonitor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceFundMonitor;
		
	}

	public int findCustomPoint(Long customPoint) {
		String sql = "select count(1) from CSMS_SERVICEFUNDMONITOR where customPoint="+customPoint;
		return super.count(sql);
	}

	public void saveFundMonitor(ServiceFundMonitor serviceFundMonitor) {
		Map map = FieldUtil.getPreFieldMap(ServiceFundMonitor.class, serviceFundMonitor);
		StringBuffer sql = new StringBuffer("insert into CSMS_SERVICEFUNDMONITOR");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
		
	}

	public ServiceFundMonitor findByFundId(Long id) {
		String sql = "select * from CSMS_SERVICEFUNDMONITOR where id = "+id;
		List<Map<String, Object>> list = queryList(sql);
		ServiceFundMonitor serviceFundMonitor = null;
		try {
			if (!list.isEmpty()) {
				serviceFundMonitor = new ServiceFundMonitor();
				this.convert2Bean(list.get(0), serviceFundMonitor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceFundMonitor;
	}

	public void saveFundMonitorHis(ServiceFundMonitorHis serviceFundMonitorHis) {
		Map map = FieldUtil.getPreFieldMap(ServiceFundMonitorHis.class, serviceFundMonitorHis);
		StringBuffer sql = new StringBuffer("insert into CSMS_SERVICEFUNDMONITOR_HIS");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
		
	}

	public void updateFundMonitor(ServiceFundMonitor oldServiceFundMonitor) {
		Map map = FieldUtil.getPreFieldMap(ServiceFundMonitor.class, oldServiceFundMonitor);
		StringBuffer sql = new StringBuffer("update CSMS_SERVICEFUNDMONITOR set ");
		sql.append(map.get("updateNameStrNotNull") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), oldServiceFundMonitor.getId());
		
	}

	public int updateFundMonitor(Long customPointId, String businessType, BigDecimal diffMoney) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update CSMS_SERVICEFUNDMONITOR set ");
		SqlParamer sqlPar=new SqlParamer();
		sql.append(" usefund = usefund+ ? ");

		params.add(diffMoney);
		sqlPar.eq(" buinesstype", businessType);
		sqlPar.eq(" custompoint", customPointId);

		sql.append(" where 1=1 ");
		if(diffMoney.compareTo(BigDecimal.ZERO) > 0){
			sql.append(" and fundmax>=usefund+? ");
			params.add(diffMoney);
		}
		sql.append(sqlPar.getParam());

		params.addAll(sqlPar.getList());
		return update(sql.toString(), params.toArray());
	}

	public ServiceFundMonitor findByCustomPoint(Long customPointId, String businessType) {
		String sql = "select * from CSMS_SERVICEFUNDMONITOR where buinesstype=? and custompoint = ?";
		List<ServiceFundMonitor> serviceFundMonitors = queryObjectList(sql.toString(), ServiceFundMonitor.class, businessType, customPointId);
		if (serviceFundMonitors == null || serviceFundMonitors.isEmpty()) {
			return null;
		}
		return serviceFundMonitors.get(0);
	}

	public void delete(Long id) {
		String sql = "delete from CSMS_SERVICEFUNDMONITOR where id = "+ id;
		super.delete(sql);
		
	}

}
