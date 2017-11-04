package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.httpInterface.entity.OmsMinusBank;
import com.hgsoft.httpInterface.entity.TbBankdetail;

import java.util.List;
import java.util.Map;

public interface IAccountCApplyService {

    public List<Map<String, Object>> findByList(Customer customer);

    public AccountCApply findById(Long id);

    public AccountCApply findByBankAccount(String bankAccount);
    
	public AccountCApply findBySubAccountInfoId(Long bankAccountId);

    public List<Map<String, Object>> findByListByState(Customer customer, AccountCApply accountCApply);

    public AccountCApply find(AccountCApply accountCApply);

    public List<Map<String, Object>> getBankByCustomerId(Long customerId);

    public AccountCApply findByCustomerId(Long customerId);

    public List<Map<String, Object>> findListByCustomerId(Long customerId ,String appState);

    public List<Map<String, Object>> findListByCustomerId(Long customerId);
    //自营保证金管理
    public List<Map<String, Object>> findListByCustomerIdCSMS(Long customerId, String appState);

    public AccountCApply findByCardNo(String cardNo);

    public List findByBankAccountByUserno(String userNo);

    public List<TbBankdetail> listTbBankdetail(TbBankdetail tbBankdetail);

    public List<OmsMinusBank> listOmsMinusBank(OmsMinusBank omsMinusBank);

    /**
     * group by 查询bankname,clearingbankcode
     * @param omsMinusBank
     * @return List<OmsMinusBank>
     */
    public List<OmsMinusBank> listOmsBankNameClearCode(OmsMinusBank omsMinusBank);

}
