package com.hgsoft.macao.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.utils.Pager;

public interface ITagIssueInfoService {
	public Pager getVehicleAndTag(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,String issuetime,MacaoCardCustomer macaoCardCustomer);
	public Map<String,Object> getVehicleAndTagById(String tagId);
	public Map<String, Object> verifyTagInfo(String tagNo);
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,String bankNo);
	public List<Map<String, Object>> getAllVehByCusId(String idCardType,String idCardNumber);
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,Long customerId);
	public TagInfo findByVehicleInfoId(Long id);
	public boolean find(String license, String licenseColor);
	public VehicleInfo findById(Long id);
	public Map<String,Object> save(TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,String bankAccountNumber,MacaoBankAccount macaoBankAccount);
	public Map<String, Object> updateIsWriteObu(Long id);
	public TagInfo findByTagId(Long id);
	public String delete(TagInfo tagInfo, Customer customer,Customer customerForServiceWater);
	public TagInfo findByTagNo(String tagNo);
	public Map<String,Object> getCustomerInfo(String bankAccountNumber,String idCardType,String idCardNumber);
	public void saveClear(Customer customer,Map<String,Object> macaoCardCustomerMap,TagInfo tagInfo,VehicleInfo vehicleInfo);
	public boolean checkDelete(String tagNo);
	public Map<String,Object> findByVehicleInfo(VehicleInfo vehicleInfo);
	public Map<String,Object> findByMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer);
	public boolean checkBankAccountNumber(String bankAccountNumber,String idCardType,String idCardNumber);
	public List<Map<String,Object>> getBankAccountNumberList(String idCardType,String idCardNumber);
	public MacaoBankAccount findByVehicleId(Long id);
}
