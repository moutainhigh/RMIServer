package com.hgsoft.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerCombineRecord;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class CustomerCombineRecordDao extends BaseDao{
	public void save(CustomerCombineRecord customerCombineRecord) {
		StringBuffer sql=new StringBuffer("insert into csms_customer_combine_record(");
		sql.append(FieldUtil.getFieldMap(CustomerCombineRecord.class,customerCombineRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(CustomerCombineRecord.class,customerCombineRecord).get("valueStr")+")");
		save(sql.toString());
	}
	
	public List<Map<String, Object>> findCustomerCombineRecordList(String combineStartTime,String combineEndTime,Customer searchCustomer){
		StringBuffer sql = new StringBuffer(
				"select "+FieldUtil.getFieldMap(CustomerCombineRecord.class,new CustomerCombineRecord()).get("nameStr")+" from csms_customer_combine_record where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(combineStartTime)){
			params.geDate("opertime", combineStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(combineEndTime)){
			params.leDate("opertime", combineEndTime+" 23:59:59");
		}
		if(searchCustomer != null){
			if(StringUtil.isNotBlank(searchCustomer.getOrgan())){
				params.eq("afterOrgan", searchCustomer.getOrgan());
			}
			if(StringUtil.isNotBlank(searchCustomer.getIdType())){
				params.eq("afterIdtype", searchCustomer.getIdType());
			}
			if(StringUtil.isNotBlank(searchCustomer.getIdCode())){
				params.eq("afterIdcode", searchCustomer.getIdCode());
			}
		}
		sql.append(params.getParam());
		sql.append(" order by opertime desc ");
		return queryList(sql.toString(), params.getList().toArray());
	}
}
