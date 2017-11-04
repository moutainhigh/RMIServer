package com.hgsoft.system.serviceInterface;

import com.hgsoft.system.entity.SysAdmin;

import java.util.List;
import java.util.Map;

public interface ISysAdminService {
	public Map<String, String> saveOperateSysAdmin(List<SysAdmin> sysAdminList);
	public List<Map<String, Object>> findSysAdminList();
	public SysAdmin findSysAdminById(Long id);
	public List<SysAdmin> findSysAdminsByCustomPoint(Long CustomPoint);
	public List<SysAdmin> findSysAdminsByCustomPointAndCode(Long CustomPoint,String subuumstemCode);
	public SysAdmin findSysAdminById(String staffno,String subuumstemCode);
	public SysAdmin findAdminBySSU(String staffno,String subuumstemCode,String url);
	public SysAdmin findSysAdminByUserName(String userName);
}
