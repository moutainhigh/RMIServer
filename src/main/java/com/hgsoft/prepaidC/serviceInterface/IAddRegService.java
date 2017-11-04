package com.hgsoft.prepaidC.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.prepaidC.entity.AddReg;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

public interface IAddRegService {
	
	public Pager findAddRegByCustomer(Pager pager,Customer customer);
	public boolean saveAddRegs(List<Map<String, String>> addRegPairList,Customer customer,long operId,long placeId,String flag,Map<String,Object> params);
	public AddReg findById(Long id);
	public boolean updateAddRegs(List<Map<String, String>> regPairUpdateList,List<Map<String, String>> regPairAddList,AddReg addReg,Customer customer,Map<String,Object> params);
	public Map<String,Object> delete(AddReg addReg,PrepaidCBussiness prepaidCBussiness);
	public AddReg findByIdAndCustomerId(Long id,Long customerId);
}
