package com.hgsoft.system.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.Admin;
import com.hgsoft.system.entity.Module;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("moduleDao")
public class ModuleDao extends BaseDao{

	@SuppressWarnings("unchecked")
	public Module find(Serializable id) {
		String sql="select * from tb_system_module where id=?  order by priority";
		List list=queryList(sql, id);
		//List list =new ArrayList<Module>();
		//list= jdbcUtil.selectForList(sql);
		Module module = null;
		module = (Module)convert2Bean((Map<String, Object>) list.get(0),new Module());

		return module;
	}
	
	@SuppressWarnings("unchecked")
	public Module saveModule(Module module){
		module.setId(findMaxId());
		StringBuffer sql=new StringBuffer("insert into TB_SYSTEM_MODULE(");
		sql.append(FieldUtil.getFieldMap(Module.class,module).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Module.class,module).get("valueStr")+")");
		save(sql.toString());
		return module;
	}
	
	@SuppressWarnings("unchecked")
	public List<Module> findAll(){
		String sql="select * from tb_system_module order by priority ";
		List<Object> list=jdbcUtil.selectForList(sql);
		List<Module> moduleList=new ArrayList<Module>();
		for(int i=0;i<list.size();i++){
			Module module=null;
			module = (Module)convert2Bean((Map<String, Object>) list.get(i), new Module());

			moduleList.add(module);
		}
		return moduleList;
	}
	
	public int findMaxId(){
		String sql="select TB_SYS_MODULE_SEQ.Nextval from dual";
		return this.jdbcUtil.select(sql);
	}

	public void delete(Integer id) {
		 String sql = "delete from tb_system_module where id="+id;
		 super.delete(sql);
	}

	public void update(Module module) {
		StringBuffer sql=new StringBuffer("update TB_SYSTEM_MODULE set ");
		sql.append(FieldUtil.getFieldMap(Module.class,module).get("nameAndValue")+" where id="+module.getId());
		update(sql.toString());
	}

	public Set<Module> findModulesByRoleId(int id) {
		String sql="select * from tb_system_module where id in(select a.moduleID from tb_role_module a where a.roleID = "+id+")";
		List<Object> list=jdbcUtil.selectForList(sql);
		Set<Module> moduleSet=new HashSet<Module>();
		for(int i=0;i<list.size();i++){
			Module module=null;
			module = (Module)convert2Bean((Map<String, Object>) list.get(i), new Module());

			moduleSet.add(module);
		}
		return moduleSet;
	}

	
}