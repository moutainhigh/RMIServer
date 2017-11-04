package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.CustomerImp;
import com.hgsoft.customer.entity.ServiceFlowRecord;

@Repository
public class CustomerImpDao extends BaseDao {
	
	public CustomerImp findById(Long id){
		String sql = "select * from csms_customer_imp where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		CustomerImp customerImp = null;
		if(list.size()>0){
			customerImp = new CustomerImp();
			convert2Bean(list.get(0), customerImp);
		}
		return customerImp;
	}
	
	public void updateFlag(Long id){
		String sql = "update csms_customer_imp set flag='1' where id=?";
		saveOrUpdate(sql, id);
	}
	
	public List<Map<String,Object>> findByIdTypeAndIdCode(String idType, String idCode){
		String sql = "select * from csms_customer_imp where flag='0' and idType=? and idCode=? ";
		return queryList(sql,idType,idCode);
	}
	
	public CustomerImp findByIdTypeAndIdCode(CustomerImp customerImp) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
//		String sql = "select * from csms_customer_imp where idType=? and idCode=? and organ=? and linkMan=? and mobile=?";
//		List<Map<String,Object>> list = queryList(sql,customerImp.getIdType(),customerImp.getIdCode(),customerImp.getOrgan(),customerImp.getLinkMan(),customerImp.getMobile());
		String sql = "select * from csms_customer_imp where idType=? and idCode=? ";
		List<Map<String,Object>> list = queryList(sql,customerImp.getIdType(),customerImp.getIdCode());
		CustomerImp customer = null;
		if(list.size()>0){
			customer = new CustomerImp();
			convert2Bean(list.get(0), customer);
		}
		return customer;
	}
	
	public int[] batchSaveCustomer(final List<CustomerImp> list,final ServiceFlowRecord serviceFlowRecord) {
		String sql = "insert into CSMS_Customer_Imp(id,organ,invoiceTitle,userType,idType,idCode,linkMan,tel,mobile,shortTel,email,addr,zipCode,flag,impTime,updateTime,operId,operName,operNo,placeId,placeName,placeNo) " +
				"values(SEQ_CSMS_CustomerImp_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,'0',sysdate,sysdate,?,?,?,?,?,?)";
		
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				CustomerImp customerImp = list.get(i);
				ps.setString(1,customerImp.getOrgan());
				ps.setString(2,customerImp.getInvoiceTitle());
				ps.setString(3,customerImp.getUserType());
				ps.setString(4,customerImp.getIdType());
				ps.setString(5,customerImp.getIdCode());
				ps.setString(6,customerImp.getLinkMan());
				ps.setString(7,customerImp.getTel());
				ps.setString(8,customerImp.getMobile());
				ps.setString(9,customerImp.getShortTel());
				ps.setString(10,customerImp.getEmail());
				ps.setString(11,customerImp.getAddr());
				ps.setString(12,customerImp.getZipCode());
				ps.setLong(13,serviceFlowRecord.getOperID());
				ps.setString(14,serviceFlowRecord.getOperName());
				ps.setString(15,serviceFlowRecord.getOperNo());
				ps.setLong(16,serviceFlowRecord.getPlaceID());
				ps.setString(17,serviceFlowRecord.getPlaceName());
				ps.setString(18,serviceFlowRecord.getPlaceNo());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
}
