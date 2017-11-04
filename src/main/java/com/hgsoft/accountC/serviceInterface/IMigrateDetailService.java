package com.hgsoft.accountC.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.utils.Pager;

public interface IMigrateDetailService {

	public Pager findByPager(Pager pager, MigrateDetail migrateDetail);

	public List<Map<String, Object>> find(MigrateDetail migrateDetail);

}
