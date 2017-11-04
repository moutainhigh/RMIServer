package com.hgsoft.system.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.Admin;
import com.hgsoft.system.entity.Module;
import com.hgsoft.system.entity.Role;
import com.hgsoft.utils.Pager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component("roleDao")
public class RoleDao extends BaseDao{
	
    /**
     * 更新
     * @param role
     */
	public void update(Role role) {
		StringBuffer sql=new StringBuffer("update tb_sys_role set ");//username='123qwer' where username='123ewe'";
		String sqlString="";
		if(role.getName()==null){
			sql.append("name=NULL,");
		}else{sql.append("name='"+role.getName()+"',");}
		
		if(role.getRemark()==null){
			sql.append("remark=NULL,");
		}else{sql.append("remark='"+role.getRemark()+"',");}
		
		if(sql.toString().endsWith(",")){
			sqlString=sql.substring(0, sql.length()-1);
		}
		sqlString += " "+" where id='"+role.getId()+"'";
		update(sqlString);
	}
    /**
     * 根据id查询
     * @param id
     * @return
     */
	public Role findById(int id) {
		String sql = "select * from TB_SYS_ROLE where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		Role role = null;
		if (!list.isEmpty()) {
			role = new Role();
			this.convert2Bean(list.get(0), role);
		}

		return role;
	}
	/**
	 * 分页
	 * @param pager
	 * @param role
	 * @return
	 */
	public Pager findByPage(Pager pager, Role role) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from TB_SYS_ROLE t where 1=1 ");
		if (role != null) {
			sql.append(FieldUtil.getFieldMap(Role.class,role).get("nameAndValueNotNull"));
		}
		sql.append(" order by ID");
		return this.findByPages(sql.toString(), pager,null);
	}
	
	/**
	 * 查询所有Role
	 * @return
	 */
	public List<Role> findAllRole() {
		StringBuffer sql = new StringBuffer("select * from TB_SYS_ROLE t order by id");
		List<Object> list=jdbcUtil.selectForList(sql.toString());
		List<Role> roleList=new ArrayList<Role>();
		for(int i=0;i<list.size();i++){
			Role role=null;
			role = (Role)convert2Bean((Map<String, Object>) list.get(i), new Role());

			roleList.add(role);
		}
		return roleList;
	}
	
   /**
    * 根据名字查Role
    * 判断是否有重名
    * @param name
    * @return
    */
	public List<Role> findRoleByName(String name) {
		String sql = "select * from TB_SYS_ROLE t where t.name='"+name+"' "+" order by t.id desc" ;
		List<Object> list=jdbcUtil.selectForList(sql);
		List<Role> roleList=new ArrayList<Role>();
		for(int i=0;i<list.size();i++){
			Role role=null;
			role = (Role)convert2Bean((Map<String, Object>) list.get(i), new Role());


			roleList.add(role);
		}
		return roleList;
	}
	
	/**
	 * 保存新的角色
	 * @param role
	 */
	public void saveRole(Role role) {
		role.setId(findMaxId());
		StringBuffer sql = new StringBuffer("insert into TB_SYS_ROLE(id,name,remark) values("+role.getId()+",");
		String sqlString = null;
		if(role.getName()==null){
			sql.append("NULL,");
		}else{
			sql.append("'"+role.getName()+"',");
		}
		if(role.getRemark()==null){
			sql.append("NULL)");
		}else{
			sql.append("'"+role.getRemark()+"',");
		}
		if(sql.toString().endsWith(",")){
			sqlString=sql.substring(0, sql.length()-1);
		}
		sqlString+=")";
		save(sqlString);
	}
	/**
	 * 自增
	 * @return
	 */
	public int findMaxId(){
		String sql="select TB_SYS_ROLE_SEQ.Nextval from dual";
		return this.jdbcUtil.select(sql);
	}
	/**
	 * 根据id删除角色
	 * @param id
	 */
	public void deleteRole(Integer id) {
		String sql ="delete from TB_SYS_ROLE where id ="+id; 
		super.delete(sql);
	}
	/**
	 * 删除角色模块数据
	 * @param role
	 * @param module
	 */
	public void deleteRoleModule(Role role, int i) {
		String sql = "delete from TB_ROLE_MODULE where roleid="+role.getId()+"and moduleid ="+i;
		super.delete(sql);
	}
	/**
	 * 查询角色模块数据记录
	 * @param 
	 * @return
	 */
	public int countRoleModule(Role role, int moduleid) {
		String sql = "select count(1) from TB_ROLE_MODULE where roleid=" + role.getId() + "and moduleid="
				+ moduleid;
		return super.count(sql);
	}
	/**
	 * 增加角色模块表数据
	 * @param role
	 * @param module
	 */
	public void saveRoleModule(Role role, int moduleid) {
		String sql = "insert into TB_ROLE_MODULE values(" + role.getId() + "," + moduleid + ")";
		super.save(sql);
	}
	
	public Set<Role> findRolesByAdminId(int id) {
		String sql="select * from tb_sys_role where id in(select a.roleID from tb_admin_role a where a.adminID = "+id+")";
		List<Object> list=jdbcUtil.selectForList(sql);
		Set<Role> roleSet=new HashSet<Role>();
		for(int i=0;i<list.size();i++){
			Role role=null;
			role = (Role)convert2Bean((Map<String, Object>) list.get(i), new Role());

			roleSet.add(role);
		}
		return roleSet;
	}
	
	/**
	 * 添加角色模块记录
	 * @param role
	 * @param module
	 */
	public void saveRoleModule(Role role, Module module) {
		String sql = "insert into TB_ROLE_MODULE values(" + role.getId() + "," + module.getId() + ")";	
		super.save(sql);
	}
	
}
