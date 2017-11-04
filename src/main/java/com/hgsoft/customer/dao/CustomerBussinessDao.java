package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;

@Repository
public class CustomerBussinessDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	
	public void save(CustomerBussiness customerBussiness) {
		Map map = FieldUtil.getPreFieldMap(CustomerBussiness.class,customerBussiness);
		StringBuffer sql=new StringBuffer("insert into CSMS_Customer_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));

	}
	
	public CustomerBussiness findById(Long id){
		String sql="select * from CSMS_Customer_Bussiness where id="+id;
		Map<String,Object> m=this.jdbcUtil.getJdbcTemplate().queryForMap(sql);
		CustomerBussiness	temp =null;
		if(m!=null){
			temp = new CustomerBussiness();
			this.convert2Bean(m, temp);
		}

		return temp;
	}

}
