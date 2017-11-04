package com.hgsoft.system.service;

import com.hgsoft.system.dao.AdminDao;
import com.hgsoft.system.dao.ModuleDao;
import com.hgsoft.system.dao.RoleDao;
import com.hgsoft.system.entity.Admin;
import com.hgsoft.system.entity.Module;
import com.hgsoft.system.entity.Role;
import com.hgsoft.system.serviceInterface.IAdminService;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class AdminService implements IAdminService{
	@Resource
	private AdminDao adminDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private ModuleDao moduleDao;
	@SuppressWarnings("unchecked")
	public List<Admin> list(String username){
		return adminDao.findAllAdmin(username);
	}
	
	public Admin check(Admin admin){
		if(admin!=null){
			if(admin.getUsername()!=null&&!"".equals(admin.getUsername().trim())){
				Admin operator=adminDao.findByName(admin.getUsername());
				if(operator==null)return null;
				operator.setRoles(roleDao.findRolesByAdminId(operator.getId()));
					return operator;
			}
		}
		return null;
	}
	
	@Override
	public Pager findByPagers(Pager pager, Admin admin) {
		// TODO Auto-generated method stub
		return adminDao.findByPagers(pager,admin);
	}
	/**
	 * 查询所有的admin
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> findAll(String username) {
		List<Admin> adminlist = adminDao.findAllAdmin(username);
		for(Admin admin : adminlist){
			Set<Role> roles =	roleDao.findRolesByAdminId(admin.getId());
			admin.setRoles(roles);
		}
		return adminlist;
	}
	
	/**
	 * 根据id查询admin
	 */
	public Admin findAdminById(Integer id) {
		Admin admin = adminDao.findAdminById(id);
		admin.setRoles(roleDao.findRolesByAdminId(id));
		return admin;
	}
	
	/**
	 * 判断名字是否已存在
	 */
  public boolean usernameIsExists(Admin admin) {
		if (admin == null) {
			return false;
		}
		String username = admin.getUsername();
		if (username == null || username.isEmpty()) {
			return false;
		}
		List adminList = adminDao.findAllAdmin(username);
		if (adminList != null && !adminList.isEmpty()) {
			return true;
		}
		return false;
	}
  
   /**
    * 增加用户
    */
	public void saveAdmin(Admin admin) {	
		Set<Role> roles=admin.getRoles();
		adminDao.save(admin);
		      if(roles!=null && !roles.isEmpty()){
		    	  for(Role role:roles){
					adminDao.saveAdminRole(admin,role);
		      }
	      }
	}
	
	/**
	 * 更新用户
	 */
	public void updateAdmin(Admin admin) {	
		adminDao.update(admin);	
		List<Integer> newlist = new ArrayList<Integer>();
		List<Integer> oldlist = new ArrayList<Integer>();
		Set<Role> newRoles = admin.getRoles();
		for(Role role:newRoles){
			newlist.add(role.getId());
		}
		Set<Role> oldRoles = roleDao.findRolesByAdminId(admin.getId());
		for(Role role:oldRoles){
			oldlist.add(role.getId());
		}
		//如果新的roles中没有旧的role，就把旧的role删除
		if(oldlist!=null && !oldlist.isEmpty()){
		  for(int i:oldlist){
			 if(!newlist.contains(i)){
				adminDao.deleteAdminRole(admin,i);
			}
		  }
		}
		for(int j : newlist){
			int i = adminDao.countAdminRole(admin,j);
			if (adminDao.countAdminRole(admin,j) == 0) {
				adminDao.saveAdminRole(admin,j);
			}
		}
	}
	

	public String getFunctions(Admin operator) {
			String functions = "";
			Set roles = operator.getRoles();
			if (roles != null && !roles.isEmpty()) {
				Iterator it = roles.iterator();
				while (it.hasNext()) {
					Role role = (Role) it.next();
					role.setModules(moduleDao.findModulesByRoleId(role.getId()));
					Set modules = role.getModules();
					if (!modules.isEmpty()) {
						Iterator mit = modules.iterator();
						while (mit.hasNext()) {
							Module module = (Module) mit.next();
							functions = functions + ";" + module.getUrl() + ";" + module.getFunctions();
						}
					}
				}
			}
			return functions;
		}
	
	@Override
	public boolean checkDirector(Admin admin){
		return adminDao.checkDirector(admin);
	}
	
	public void deleteAdmin(int id) {	
		adminDao.delete(id);	
	}
	public Admin findByname(String name) {
		return adminDao.findByName(name);
	}
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public AdminDao getAdminDao() {
		return adminDao;
	}
	public ModuleDao getModuleDao() {
		return moduleDao;
	}
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

}
