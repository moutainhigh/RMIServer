package com.hgsoft.customer.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IBillGetService {
	public BillGet find(BillGet billGet);

	public void billGetUpdate(BillGet billGet);

	public Pager findByPage(Pager pager, BillGet billGet, Customer customer);
	
	public BillGet find(Long id);
	
	public List<Map<String, Object>> findAll(BillGet billGet);
	
	public List<Map<String, Object>> findAllByMainId(Long mainId);
	
	public void updateBillGet(BillGet billGet, String serTypes, String oldSerTypes);

	public void delete(String string);
	
	public void save(List<BillGet> addBillGets);

	public void updateCancelSer(List<BillGet> removeBillGets);
	
	public BillGet findByCardBankNo(String cardBankNo);

	public Pager findByPage(Pager pager, BillGet billGet);
	
	public void updateSerItem(BillGet billGet);
}
