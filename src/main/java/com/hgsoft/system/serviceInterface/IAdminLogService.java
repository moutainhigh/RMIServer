package com.hgsoft.system.serviceInterface;

import java.util.Date;

import com.hgsoft.system.entity.AdminLog;
import com.hgsoft.utils.Pager;

public interface IAdminLogService {

	public void save(AdminLog adminLog);

	public Pager findByPager(Pager pager,AdminLog adminLog,Date startTime,Date endTime) ;
	
	public AdminLog findById(Long id);
	public void saveRemark(AdminLog adminLog);

	public Pager findServiceByPager(Pager pager, Long operId, String type,
			Date startTime, Date endTime);
}
