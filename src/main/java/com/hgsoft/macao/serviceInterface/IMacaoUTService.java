package com.hgsoft.macao.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.Cancel;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;

public interface IMacaoUTService {
	
	public String saveAccountCIssue(AccountCInfo accountCInfo,MacaoCardCustomer macaoCardCustomer,VehicleInfo vehicleInfo,Customer customer);
	public boolean surePublished(String cardNo);
	public String saveReplaceCard(MainAccountInfo mainAccountInfo,
			AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,
			AccountCBussiness accountCBussiness,
			ServiceFlowRecord serviceFlowRecord,String systemType,MacaoCardCustomer macaoCardCustomer);
	public String saveUnusable(AccountCInfo accountCInfo,
			AccountCBussiness accountCBussiness,
			ServiceFlowRecord serviceFlowRecord,boolean result,String systemType);
	public String saveGainCard(AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,
			AccountCBussiness accountCBussiness,
			ServiceFlowRecord serviceFlowRecord,String systemType,MacaoCardCustomer macaoCardCustomer);
	public String saveStopCard(Cancel cancel,ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness, 
			Customer customer, AccountCInfo accountCInfo,String systemType);
	public List<Map<String, Object>> findAvailableVehicle(String bankAccountNumber);
	
	public Pager findCardByPager(Pager pager,MacaoCardCustomer macaoCardCustomer,AccountCInfo accountCInfo,VehicleInfo vehicleInfo);
	public Map<String, Object> findIssueDetail(Long id);
	public Map<String, Object> findMacaoBankAccountByCardNo(String cardNo);
	public Pager findStopCardByCustomer(Pager pager, MacaoCardCustomer macaoCardCustomer, AccountCInfo accountCInfo);
	public Cancel findByCancelId(Long id);
	public boolean checkMacaoCard(String cardNo);
	public boolean checkCard(Long id,String cardNo);
}
