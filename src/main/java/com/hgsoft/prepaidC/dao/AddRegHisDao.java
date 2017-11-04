package com.hgsoft.prepaidC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.prepaidC.entity.AddReg;
import com.hgsoft.prepaidC.entity.AddRegHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class AddRegHisDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;
	

	public AddReg findById(Long id) {
		String sql = "select * from CSMS_Add_Reg_his where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		AddReg addReg = null;
		if (!list.isEmpty()) {
			addReg = new AddReg();
			this.convert2Bean(list.get(0), addReg);
		}

		return addReg;
	}
	
	public void save(AddRegHis addRegHis) {
		
		Map map = FieldUtil.getPreFieldMap(AddRegHis.class,addRegHis);
		StringBuffer sql=new StringBuffer("insert into csms_add_reg_his");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
