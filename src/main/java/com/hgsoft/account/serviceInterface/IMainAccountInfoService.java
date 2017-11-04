package com.hgsoft.account.serviceInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SpecialCostSubclass;
import com.hgsoft.system.entity.SpecialCostType;
import com.hgsoft.system.entity.SysAdmin;

public interface IMainAccountInfoService {
	
	public List<MainAccountInfo> list();
	public void updateMainAccountInfo(MainAccountInfo mainAccountInfo);
	public MainAccountInfo findByMainId(Long id);
	/**
	 * 保存特殊费用收取
	 */
	public void specialCostSave(MainAccountInfo mainAccountInfo, BigDecimal beforBalance, BigDecimal  beforAvailableBalance, SysAdmin sysAdmin, Customer customer, CusPointPoJo cusPointPoJo, SpecialCostType specialCostType, SpecialCostSubclass specialCostSubclass, String charge, Map<String,Object> params);

}
