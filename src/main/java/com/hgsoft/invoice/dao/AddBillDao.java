package com.hgsoft.invoice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.invoice.entity.AddBill;
@Repository
public class AddBillDao extends BaseDao{
	
	public void save(AddBill addBill) {
		Map map = FieldUtil.getPreFieldMap(AddBill.class,addBill);
		StringBuffer sql=new StringBuffer("insert into csms_add_bill");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
}
