package com.hgsoft.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.system.dao.ModuleDao;
import com.hgsoft.system.dao.RoleDao;
import com.hgsoft.system.entity.Module;
import com.hgsoft.system.entity.Role;
import com.hgsoft.system.serviceInterface.IRoleService;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
	@Resource
	private RoleDao roleDao;
	@Resource
	private ModuleDao moduleDao;
	Set<Module> modules;

	
	public Role findById(int id) {
		Role role = roleDao.findById(id);
		modules = moduleDao.findModulesByRoleId(id);
		role.setModules(modules);
		return role;
	}

	@SuppressWarnings("null")
	public void update(Role role) {
		// TODO Auto-generated method stub
		roleDao.update(role);
		Set<Module> newmodules = role.getModules();
		List<Integer> newlist = new ArrayList<Integer>();
		List<Integer> oldlist = new ArrayList<Integer>();
		
		for(Module module:newmodules){
			newlist.add(module.getId());
		}
		Set<Module> oldModules = moduleDao.findModulesByRoleId(role.getId());
		for(Module module:oldModules){
			oldlist.add(module.getId());
		}
		//如果新的modules中没有旧的，就把旧的module删除
		if(oldlist!=null && !oldlist.isEmpty()){
		  for(int i:oldlist){
			 if(!newlist.contains(i)){
				roleDao.deleteRoleModule(role,i);
			}
		  }
		}
		if(newlist!=null && !newlist.isEmpty()){
			for(int j:newlist){
				if (roleDao.countRoleModule(role,j)==0) {
					roleDao.saveRoleModule(role,j);
				}
			}
		}
	}

	/**
	 * 分页查询
	 */
	public Pager findByPagers(Pager pager, Role role) {
		return roleDao.findByPage(pager, role);
	}
	
    /**
     * 查询所有Role
     */
	public List<Role> findAllRole() {
		List<Role> rolelist = roleDao.findAllRole();
		for(Role role :rolelist){
			role.setModules(moduleDao.findModulesByRoleId(role.getId()));
		}
		return rolelist;
	}
	
	
	public void save(Role role) {
	  //先保存新角色
	  modules=role.getModules();
      roleDao.saveRole(role);
      //再保存新角色的菜单模块
	      if(modules!=null && !modules.isEmpty()){
	    	  for(Module module : modules){
	    	  String sql = "insert into TB_ROLE_MODULE values(" + role.getId() + "," + module.getId() + ")";
				roleDao.save(sql);
	      }
      }
	}
	/**
	 * 判断是否重名
	 */
	public boolean nameIsExists(Role role) {
		if (role == null) {
			return false;
		}
		String name = role.getName();

		if (name == null || name.isEmpty()) {
			return false;
		}
		List<Role> roleList = roleDao.findRoleByName(role.getName());
		Integer id = role.getId();
			if (roleList != null && !roleList.isEmpty()) {
					if(id != null){
						for (Role temp : roleList) {
							if (id == temp.getId().intValue()) {
								return false;
							}
						}
					//id不同返回true
					return true;
				    }
			   return true;
			}
		return false;
	}
	
	
    /**
     * 根据id删除role
     */
	public void deleteById(Integer id) {
		roleDao.deleteRole(id);
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public ModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
}
