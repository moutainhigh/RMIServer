package com.hgsoft.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.ServiceWater;

@Repository
public class ServiceWaterDao extends BaseDao{

	public void save(ServiceWater serviceWater) {
		Map map = FieldUtil.getPreFieldMap(ServiceWater.class,serviceWater);
		StringBuffer sql=new StringBuffer("insert into CSMS_SERVICE_WATER");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public ServiceWater findByCardNoAndSerType(String cardNo,String serType){
		String sql="select * from CSMS_SERVICE_WATER where cardNo=? and sertype=? order By id Desc";
		List<Map<String, Object>> list = queryList(sql,cardNo,serType);
		ServiceWater serviceWater = null;
		if (!list.isEmpty()) {
			serviceWater = new ServiceWater();
			this.convert2Bean(list.get(0), serviceWater);
		}

		return serviceWater;
	}
	
	public ServiceWater findByAccountBussinessId(Long accountBussinessId){
		String sql="select * from CSMS_SERVICE_WATER where accountBussinessId=? order By id Desc";
		List<Map<String, Object>> list = queryList(sql,accountBussinessId);
		ServiceWater serviceWater = null;
		if (!list.isEmpty()) {
			serviceWater = new ServiceWater();
			this.convert2Bean(list.get(0), serviceWater);
		}

		return serviceWater;
	}
	
	public void update(ServiceWater serviceWater){
		Map map = FieldUtil.getPreFieldMap(ServiceWater.class, serviceWater);
		StringBuffer sql =  new StringBuffer("update CSMS_SERVICE_WATER set ");
		sql.append(map.get("updateNameStrNotNull")+" where id=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), serviceWater.getId());
	}

	public void updateByPrepaidCBusinessId(ServiceWater serviceWater){
		Map map = FieldUtil.getPreFieldMap(ServiceWater.class, serviceWater);
		StringBuffer sql =  new StringBuffer("update CSMS_SERVICE_WATER set ");
		sql.append(map.get("updateNameStrNotNull")+" where prepaidCBussinessId=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), serviceWater.getPrepaidCBussinessId());
	}
}
