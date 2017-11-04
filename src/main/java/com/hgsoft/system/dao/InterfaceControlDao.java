package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.InterfaceControl;
import com.hgsoft.system.entity.ServiceFundMonitor;

@Repository
public class InterfaceControlDao extends BaseDao{
	
	public InterfaceControl findByCode(String code){
		String sql = "select * from CSMS_INTERFACE_CONTROL where code=?";
		List<Map<String, Object>> list = queryList(sql,code);
		InterfaceControl interfaceControl = null;
		if (!list.isEmpty()) {
			interfaceControl = new InterfaceControl();
			this.convert2Bean(list.get(0), interfaceControl);
		}

		return interfaceControl;
	}
	
	public void update(InterfaceControl interfaceControl){
		Map map = FieldUtil.getPreFieldMap(InterfaceControl.class, interfaceControl);
		StringBuffer sql = new StringBuffer("update CSMS_INTERFACE_CONTROL set ");
		sql.append(map.get("updateNameStrNotNull")+" where code = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), interfaceControl.getCode());
		
	}
	
}
