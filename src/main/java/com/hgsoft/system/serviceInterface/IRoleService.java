package com.hgsoft.system.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.system.entity.Role;
import com.hgsoft.utils.Pager;

public interface IRoleService {
	public void update(Role role);
	public Role findById(int id);
	public Pager findByPagers(Pager pager, Role role);
	public boolean nameIsExists(Role role);
	public void save(Role role);
	public void deleteById(Integer id);
	public List<Role> findAllRole();
}
