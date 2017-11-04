package com.hgsoft.system.serviceInterface;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hgsoft.system.entity.Module;

public interface IModuleService{
	public List<Module> getMenus(HashSet<Module> set);
	public Module find(Serializable id);
	public Module save(Module module);
	public List<Module> getMenus();
	public void deleteModule(Integer id);
	public void update(Module module);
	public Module findNewModule();
	public List findAll();
	Set<Module> findModulesByRoleId(Integer id);
}
