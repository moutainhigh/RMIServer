package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.NewCardApply;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface INewCardApplyService {
    public List<Map<String, Object>> listAccountCApplys(Customer customer,String debitCardType);
    public List<Map<String, Object>> findAccountCApplys(Customer customer,String bankname);
    public Map<String, Object> findAccountCApplySomeInfo(Customer customer,String bankAccount);
    public Pager findNewCardApplyListByPager(Pager pager,Customer customer,String bankAccount);
    public AccountCApply findAccountCApplyByBankAccount(String bankAccount);
    public void saveNewCardApply(NewCardApply newCardApply,Customer customer,String newCardVehicleStr,Map<String,Object> params);
    public NewCardApply findNewCardApplyById(Long newCardApplyId);
    public Pager findNewCardVehiclePager(Long newCardApplyId,Pager pager);
    public void updateNewCardApply(NewCardApply newCardApply,Customer customer);
    public boolean canApply(String bankAccount,Long customerId);
    public Map<String, Object> marginPutList();
}
