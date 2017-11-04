package com.hgsoft.accountC.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

/**
 * @FileName IBailService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月19日 上午9:41:08 
*/
public interface IBailService {

	Pager findBailList(Pager pager, Customer customer, String cardNo, String bankAccount);

	List<Map<String, Object>> prepareAddInfo(String userNo, String cardNo, String bankAccount);
	
	public Map<String, Object> saveAddBail(Bail bail, Customer customer, String bankAccount,Long accountcid,Map<String,Object> params);
	
	Bail findById(Long id);

	Map<String,Object> findBailAddDetail(Long id);
	Map<String,Object> findBailBackDetail(Long id);

	Boolean saveGiveBackBail(Bail bail, Customer customer, String bankAccount,String materialIds);
	public Map<String, Object> saveGiveBackBailReturnMap(Bail bail, Customer customer, String bankAccount,Map<String,Object> params,Long bussinessPlaceId);
	public void saveCancelBail(RefundInfo refundInfo,AccountCBussiness accountCBussiness);

	public Pager findByPage(Pager pager, Customer customer,
			AccountCApply accountCApply, AccountCInfo accountCInfo);

	/**
	 * 根据子账户id查找记帐卡列表
	 * @param subAccountInfoId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findAccountCInfosBySubID(Long subAccountInfoId);
}
