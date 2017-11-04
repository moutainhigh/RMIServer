package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface IAccountCInfoService {
	public Pager findAccountCInfoList(Pager pager,Customer customer,String bankAccount);
	public boolean surePublished(String cardNo);
	public Map<String, Object> sureAvailableBalance(AccountCInfo accountCInfo,AccountCApply accountCApply,Customer customer,Long vehicleInfoId);
	public String saveAccountCInfo(AccountCInfo accountCInfo,AccountCApply accountCApply,Customer customer,VehicleInfo vehicleInfo,SubAccountInfo subAccountInfo,String systemType,Map<String,Object> params);
	public VehicleInfo findVehicle(VehicleInfo vehicleInfo);
	public SubAccountInfo findByApplyId(SubAccountInfo subAccountInfo);
	public Pager findAccountCInfoList(AccountCInfo accountCInfo,SubAccountInfo subAccountInfo,Pager pager);
	public String sureVehicle(VehicleInfo vehicleInfo);
	public List<Map<String, Object>> findAvailableVehicle(Customer customer,Long accountCApplyId);
	public List<Map<String, Object>> findAvailableVehicle(Customer customer,String vehicleInfoIdstr);
	public Map<String, Object> getAvailableVehicleCount(Customer customer);
	public List<Map<String, Object>> findBindingVehicle(Customer customer,Long IAccountCInfoService);
	public Map<String, Object> findByCardNo(Long customerid,String cardno);
	public AccountCInfo checkAccountCInfo(Long customerid,String cardno);
//	public List<Map<String,Object>> getStopAcList(String bankAccount);
	public boolean getStopCardBlackList(String bankAccount);
	public AccountCInfo findByCustomerId(Long customerId);

	public AccountCInfo findByCardNo(String cardNo);
}
