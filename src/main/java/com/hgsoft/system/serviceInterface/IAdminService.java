package com.hgsoft.system.serviceInterface;

import java.util.List;

import com.hgsoft.system.entity.Admin;
import com.hgsoft.utils.Pager;

public interface IAdminService {
	public Admin check(Admin admin);
	public boolean usernameIsExists(Admin admin);
	public void saveAdmin(Admin admin);
	public void deleteAdmin(int id);
	public void updateAdmin(Admin admin);
	public Admin findByname(String name);
	public List<Admin> findAll(String username);
	public Admin findAdminById(Integer id);
	public String getFunctions(Admin operator);
	public boolean checkDirector(Admin admin);
	public Pager findByPagers(Pager pager, Admin admin);
}
