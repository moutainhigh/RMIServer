package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.Admin;
import com.hgsoft.system.entity.Role;
import com.hgsoft.utils.Pager;

@Component("adminDao")
public class AdminDao extends BaseDao{
	
	public Admin findAllByName(String name){
		/*if(name!=null&&!"".equals(name)){
			String sql="select count(*) from sys_admin where name = ?";
		}else{
			findAllAdmin
		}*/
		return new Admin();
	}
	
	
	public boolean checkAdmin(String name){
		String sql="select count(*) from tb_sys_admin where username = ?";
		int count = this.count(sql, name);
		if(count>0){
			return true;
		}
		return false;
	}
	
	public boolean checkDirector(Admin admin){
		String sql="select * from tb_sys_admin where USERID = ? and PASSWORD = ?";
		List list=queryList(sql, admin.getUserid(),admin.getPassword());
		admin = null;
		try {
			if(list.size()!=0)
			admin=(Admin)this.convert2Bean((Map<String, Object>) list.get(0), new Admin());
			if(admin!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 * 分页查询
	 * @param pager
	 * @param admin
	 * @return
	 */
	public Pager findByPagers(Pager pager, Admin admin) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from TB_SYS_ADMIN t where 1=1 ");
		if (admin != null) {
			sql.append(FieldUtil.getFieldMap(Admin.class,admin).get("nameAndValueNotNull"));
		}
		sql.append(" order by ID");
		return this.findByPages(sql.toString(), pager,null);
	}
	
	@SuppressWarnings("rawtypes")
	public Admin findByName(String name){
		String sql="select * from tb_sys_admin where username=? ";
		List list=queryList(sql, name);
		Admin admin=null;
		if(list.size()!=0)admin=(Admin)this.convert2Bean((Map<String, Object>) list.get(0), new Admin());

		return admin;
	}
	
	@SuppressWarnings("rawtypes")
	public List findAllAdmin(String username){
		String sql="select * from tb_sys_admin where 1=1 ";
		if(username!=null&&!"".equals(username)){
			sql+="and username = '"+username+"'";
		}
		List<Object> list=jdbcUtil.selectForList(sql);
		List<Admin> adminList=new ArrayList<Admin>();
		for(int i=0;i<list.size();i++){
			Admin admin=null;
			admin = (Admin)convert2Bean((Map<String, Object>) list.get(i), new Admin());

			adminList.add(admin);
		}
		return adminList;
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(int id){
		String sql="delete from tb_sys_admin where id='"+id+"'";
		super.delete(sql);
	}
	/**
	 * 修改用户
	 * @param admin
	 */
	public void update(Admin admin){
		StringBuffer sql=new StringBuffer("update tb_sys_admin set ");
		String sqlString="";
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*if(admin.getUserid()==null){
			sql.append("userid=NULL,");
		}else{sql.append("userid="+admin.getUserid()+",");}*/
		/*if(admin.getUsername()==null){
			sql.append("username=NULL,");
		}else{sql.append("username='"+admin.getUsername()+"',");}*/
		if(admin.getName()==null){
			sql.append("name=NULL,");
		}else{sql.append("name='"+admin.getName()+"',");}
		if(admin.getPassword()==null||admin.getPassword().isEmpty()){
			//sql.append("password=NULL,");
		}else{sql.append("password='"+admin.getPassword()+"',");}
		if(admin.getType()==null){
			sql.append("type=NULL,");
		}else{sql.append("type='"+admin.getType()+"',");}
		if(admin.getSex()==null){
			sql.append("sex=NULL,");
		}else{sql.append("sex='"+admin.getSex()+"',");}
		if(admin.getEmail()==null){
			sql.append("email=NULL,");
		}else{sql.append("email='"+admin.getEmail()+"',");}
		if(admin.getPhone()==null){
			sql.append("phone=NULL,");
		}else{sql.append("phone='"+admin.getPhone()+"',");}
		/*if(admin.getCreateTime()==null){
			sql.append("createTime=NULL,");
		}else{
			//format.format(admin.getCreateTime());
			sql.append("createTime=to_date('"+format.format(admin.getCreateTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}*/
		/*if(admin.getLastIp()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getLastIp()+"',");}
		if(admin.getLastTime()==null){
			sql.append("NULL,");
		}else{
			sql.append("to_date('"+format.format(admin.getLastTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}*/
		if(admin.getValid()==null){
			sql.append("valid=NULL,");
		}else{sql.append("valid='"+admin.getValid()+"',");}
		/*if(admin.getTheme()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getTheme()+"',");}*/
		
		if(sql.toString().endsWith(",")){
			sqlString=sql.substring(0, sql.length()-1);
		}
		sqlString+=" "+"where id='"+admin.getId()+"'";
		update(sqlString);
	}
	
	/**
	 * 新增用户
	 * @param admin
	 */
	public void save(Admin admin){
		admin.setId(findMaxId());//设置id
		StringBuffer sql=new StringBuffer("insert into tb_sys_admin(id,userid,username,name,password,type,sex,email,phone,createTime,lastip,lasttime,valid,theme) values(");
		String sqlString=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sql.append(admin.getId()+",");
		if(admin.getUserid()==null){
			sql.append("NULL,");
		}else{sql.append(admin.getUserid()+",");}
		if(admin.getUsername()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getUsername()+"',");}
		if(admin.getName()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getName()+"',");}
		if(admin.getPassword()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getPassword()+"',");}
		if(admin.getType()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getType()+"',");}
		if(admin.getSex()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getSex()+"',");}
		if(admin.getEmail()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getEmail()+"',");}
		if(admin.getPhone()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getPhone()+"',");}
		if(admin.getCreateTime()==null){
			sql.append("NULL,");
		}else{
			sql.append("to_date('"+format.format(admin.getCreateTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}
		if(admin.getLastIp()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getLastIp()+"',");}
		if(admin.getLastTime()==null){
			sql.append("NULL,");
		}else{
			sql.append("to_date('"+format.format(admin.getLastTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}
		if(admin.getValid()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getValid()+"',");}
		if(admin.getTheme()==null){
			sql.append("NULL,");
		}else{sql.append("'"+admin.getTheme()+"',");}
		
		if(sql.toString().endsWith(",")){
			sqlString=sql.substring(0, sql.length()-1);
		}
		sqlString+=")";
		save(sqlString);
	}
	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	public Admin findAdminById(Integer id) {
		String sql="select * from tb_sys_admin where id=? ";
		List list=queryList(sql, id);
		Admin admin=null;
		admin=(Admin)this.convert2Bean((Map<String, Object>) list.get(0), new Admin());

		return admin;
	}
	/**
	 * 自增
	 * @return
	 */
	public int findMaxId(){
		String sql="select TB_SYS_ADMIN_SEQ.Nextval from dual";
		return this.jdbcUtil.select(sql);
	}
	/**
	 * 增加用户角色记录
	 * @param admin
	 * @param role
	 */
	public void saveAdminRole(Admin admin, Role role) {
		String sql = "insert into TB_ADMIN_ROLE values(" + admin.getId() + "," + role.getId() + ")";	
		save(sql);
	}
    /**
     * 删除用户角色记录
     * @param admin
     * @param i
     */
  	public void deleteAdminRole(Admin admin, int roleid) {
  		String sql = "delete from TB_ADMIN_ROle where adminid="+admin.getId()+"and roleid ="+roleid;
  		super.delete(sql);
	}
  	/**
	 * 查询用户角色数据记录
	 * @param 
	 * @return
	 */
	public int countAdminRole(Admin admin, int roleid) {
		String sql = "select count(1) from TB_ADMIN_ROLE where adminid=" + admin.getId() + "and roleid="
				+ roleid;
		return super.count(sql);
	}
	/**
     * 增加用户角色记录
     * @param admin
     * @param i
     */
	public void saveAdminRole(Admin admin, int roleid) {
		String sql = "insert into TB_ADMIN_ROLE values(" + admin.getId() + "," + roleid + ")";
		super.save(sql);
	}

}
