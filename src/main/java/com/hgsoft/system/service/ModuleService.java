package com.hgsoft.system.service;

import com.hgsoft.system.dao.ModuleDao;
import com.hgsoft.system.entity.Module;
import com.hgsoft.system.serviceInterface.IModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@Service
public class ModuleService implements IModuleService{
	
	private ModuleDao moduleDao;
	@Resource
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
	/**
	 * 根据用户拥有的模块生成的菜单
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public List<Module> getMenus(HashSet<Module> set) {
		List<Module> list = new ArrayList();
		HashSet<Integer> ids=new HashSet<Integer>();
		if (set != null && !set.isEmpty()) {
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Module module = (Module) it.next();
				ids.add(module.getId());
			}
		}
		if(ids!= null && !ids.isEmpty()){
			Iterator its = ids.iterator();
			while (its.hasNext()) {
				int moduleid = (Integer) its.next();
				list.add(moduleDao.find(moduleid));
			}
		}
		return list;
	}

	/**
	 * 获取所有模块生成的菜单
	 */
	public List<Module> getMenus() {
		return moduleDao.findAll();
	}

	public String getCurrentPosition(Integer id) {
		String position = "";
		Module module = moduleDao.find(id);
		if (module != null) {
			position = module.getName();
			while (module.getParent() != null) {
				module = moduleDao.find(module.getParent());
				if (module != null) {
					position = module.getName() + " > " + position;
				}
			}
		}
		return position;
	}

	/**
	 * 根据roleId返回其modules
	 */
	public Set<Module> findModulesByRoleId(Integer roleId) {
		return moduleDao.findModulesByRoleId(roleId);
	}
	
	public Module save(Module module) {
		 return moduleDao.saveModule(module);
	}
	
	@Override
	public Module find(Serializable id) {
		// TODO Auto-generated method stub
		return moduleDao.find(id);
	}


	@Override
	public void deleteModule(Integer id) {
		// TODO Auto-generated method stub
		moduleDao.delete(id);
	}

	@Override
	public void update(Module module) {
		// TODO Auto-generated method stub
		moduleDao.update(module);
	}

	@Override
	public Module findNewModule() {
		// TODO Auto-generated method stub
		return this.find(moduleDao.findMaxId());
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return moduleDao.findAll();
	}
}
