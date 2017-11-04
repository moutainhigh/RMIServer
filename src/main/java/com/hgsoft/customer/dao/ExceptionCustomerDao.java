package com.hgsoft.customer.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.ExceptionCustomer;
import com.hgsoft.exception.ApplicationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExceptionCustomerDao extends BaseDao{
	/**
	 * 查找验证客户的所有需要提醒的异常数据
	 * @param customerId
	 * @return List<ExceptionCustomer>
	 */
	public List<ExceptionCustomer> findAllByCustomerId(Long customerId){
		if (customerId == null) {
			throw new ApplicationException("customerId为空");
		}
		String sql = "select "+FieldUtil.getFieldMap(ExceptionCustomer.class,new ExceptionCustomer()).get("nameStr")+" from CSMS_ExceptionCustomer where warnFlag='0' and customerId=? " ;
		return super.queryObjectList(sql, ExceptionCustomer.class, customerId);
	}
	
    /**
     * 修改客户的所有异常客户数据
     * @param exceptionCustomer   customerid不为null，warnFlag不为null
     * @return void
     */
	public void updateNotNull(ExceptionCustomer exceptionCustomer){
		Map map = FieldUtil.getPreFieldMap(ExceptionCustomer.class,exceptionCustomer);
		StringBuffer sql=new StringBuffer("update CSMS_ExceptionCustomer set ");
		sql.append(map.get("updateNameStrNotNull") +" where customerId = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),exceptionCustomer.getCustomerId());
	}
}
