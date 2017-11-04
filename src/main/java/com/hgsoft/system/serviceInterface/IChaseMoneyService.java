package com.hgsoft.system.serviceInterface;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.system.entity.ChaseMoneyInfo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ExceptionListNo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface IChaseMoneyService {
	public Pager findByPager(Pager pager, ExceptionListNo exceptionListNo,Long customerId);
	public ExceptionListNo find(Long id);
	public void update(ChaseMoneyInfo chaseMoneyInfo);
	public Map<String, Object> saveChaseMoney(ExceptionListNo exceptionListNo,MainAccountInfo mainAccountInfo,CusPointPoJo cusPointPoJo,SysAdmin sysAdmin,Map<String,Object> params);
	public List<Map<String, Object>> findByCardNo(String cardNo,String type);
	public Map<String, Object> findStationAndRoad(ExceptionListNo exceptionListNo);
}
