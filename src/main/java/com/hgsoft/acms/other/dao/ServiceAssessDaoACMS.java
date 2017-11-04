package com.hgsoft.acms.other.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.ServiceAssess;
import com.hgsoft.utils.Pager;

@Repository
public class ServiceAssessDaoACMS extends BaseDao{
	
	public void save(ServiceAssess serviceAssess) {
		StringBuffer sql=new StringBuffer("insert into CSMS_ServiceAssess");
		Map map = FieldUtil.getPreFieldMap(ServiceAssess.class,serviceAssess);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("nameStr")+") values(");
		//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("valueStr")+")");
		//save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from CSMS_ServiceAssess where id=?";
		super.delete(sql,id);
	}

	public void update(ServiceAssess serviceAssess) {
		StringBuffer sql=new StringBuffer("update CSMS_ServiceAssess set ");
		Map map = FieldUtil.getPreFieldMap(ServiceAssess.class,serviceAssess);
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),serviceAssess.getId());
		//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("nameAndValue")+" where id="+serviceAssess.getId());
		//update(sql.toString());
	}

	public ServiceAssess findById(Long id) {
		String sql = "select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		ServiceAssess serviceAssess = null;
		if (!list.isEmpty()) {
			serviceAssess = new ServiceAssess();
			this.convert2Bean(list.get(0), serviceAssess);
		}

		return serviceAssess;
	}

	public List<Map<String, Object>> findAll(ServiceAssess serviceAssess) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where 1=1 ");
		if (serviceAssess != null) {
			//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ServiceAssess.class,serviceAssess);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		} else{
			return queryList(sql.toString());
		}
	}

	public Pager findByPage(Pager pager,ServiceAssess serviceAssess) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_ServiceAssess t where 1=1");
		if (serviceAssess != null) {
			//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ServiceAssess.class, serviceAssess);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by t.ID ");
			return findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else {
			sql.append(" order by t.ID ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public ServiceAssess find(ServiceAssess serviceAssess) {
		ServiceAssess temp = null;
		if (serviceAssess != null) {
			StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(ServiceAssess.class,serviceAssess).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ServiceAssess.class,serviceAssess);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new ServiceAssess();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public ServiceAssess findByCustomer(Long customerID) {
		ServiceAssess temp = null;
		if (customerID != null) {
			StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where 1=1 and CustomerID="+customerID+" and BeginTime is not null and EndTime is null");
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new ServiceAssess();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public ServiceAssess findByCustomerIdAndTime(Customer customer){
		String sql = "select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where customerID="+customer.getId()+ " and beginTime is not null and endTime is null";
		List<Map<String, Object>> list = queryList(sql);
		ServiceAssess serviceAssess = null;
		if (!list.isEmpty()) {
			serviceAssess = new ServiceAssess();
			this.convert2Bean(list.get(0), serviceAssess);
		}

		return serviceAssess;
	}
	
	public ServiceAssess findByCustomerIdAndBeginTime(Customer customer){
		String sql = "select ID,customerID,beginTime,endTime,assessLevel,assessTime,memo from CSMS_ServiceAssess where customerID="+customer.getId()+ " and beginTime is not null";
		List<Map<String, Object>> list = queryList(sql);
		ServiceAssess serviceAssess = null;
		if (!list.isEmpty()) {
			serviceAssess = new ServiceAssess();
			this.convert2Bean(list.get(0), serviceAssess);
		}

		return serviceAssess;
	}
	
}
