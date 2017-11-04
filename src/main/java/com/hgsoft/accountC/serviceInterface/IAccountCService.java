package com.hgsoft.accountC.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.clearInterface.entity.TollResult;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;

public interface IAccountCService {
	
	public AccountCInfo findByCardNo(String cardNo);
	public AccountCInfo findById(Long id);
	public AccountCInfo find(AccountCInfo accountCInfo);
	public String saveReplaceCard(MainAccountInfo mainAccountInfo,AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,AccountCBussiness accountCBussiness,ServiceFlowRecord serviceFlowRecord,String systemType,Map<String,Object> params);
	public List<Map<String, Object>> findByList(AccountCInfo accountCInfo);
	public String saveUnusable(AccountCInfo accountCInfo,
			AccountCBussiness accountCBussiness,
			ServiceFlowRecord serviceFlowRecord,boolean result,String systemType);
	public String saveGainCard(MainAccountInfo mainAccountInfo,AccountCInfo accountCInfo, AccountCInfo newAccountCInfo,AccountCBussiness accountCBussiness,ServiceFlowRecord serviceFlowRecord,String systemType,Map<String,Object> params);
	public List<Map<String, Object>> getAccountCInfoByBank(Long customerId, Long subAccountInfoId);
	public boolean updatePwd(AccountCInfo accountCInfo);
	public boolean saveResetPwd(AccountCInfo accountCInfo);
	public boolean saveLock(AccountCInfo accountCInfo,String systemType,Map<String,Object> params);
	public boolean saveUnLock(AccountCInfo accountCInfo,String systemType,Map<String,Object> params);
	public List<Map<String, Object>> findCardNoByList(AccountCInfo accountCInfo);
	public AccountCInfo findByCardNoToGain(String cardNo);
	public AccountCInfo findByCustomerId(Long id);
	public Pager findByCustomer(Pager pager, Customer customer);
	public boolean hasMigrateOrTransfer(String cardNo);
	public Pager findLianCard(Pager pager, Customer customer, AccountCInfo accountCInfo,Date startTime,Date endTime);
	public Pager findJointCardState(Pager pager, Customer customer, AccountCInfo accountCInfo);
	public List listJointCardState(Customer customer);
	public List list(AccountCInfo accountCInfo, Customer customer);
	public void saveACinfo(Integer state,AccountCInfo accountCInfo,String systemType);
	public void saveTollCardBlack(AccountCInfo accountCInfo,TollCardBlackDet tollCardBlackDet,TollCardBlackDetSend tollCardBlackDetSend);
	public boolean hasTransfer(String cardNo);
	public void saveDarkList(AccountCInfo accountCInfo,DarkList darkList,String genCau,String state);
	public Map<String, Object> updateWriteCardFlag(String cardNo);
	public boolean checkCardIsMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer,String cardNo);
	public AccountCApply findAccountCApplyByCardNo(String cardNo);
	public List<Map<String,Object>> getStopAcList(String bankAccount);
	public Customer findCustomer(String cardNo);
	public Customer findCustomer(PaymentCardBlacklistRecv paymentCardBlacklistRecv);
//	public boolean checkBankAccount(String ACBAccount);
	public List<Map<String,Object>> findAccountCInfoList(AccountCApply accountCApply);
	/**
	 * accountCBussiness携带参数，查找记帐卡业务记录
	 * @param accountCBussiness
	 * @return AccountCBussiness
	 */
	public AccountCBussiness findAccountCBussiness(AccountCBussiness accountCBussiness);
}

