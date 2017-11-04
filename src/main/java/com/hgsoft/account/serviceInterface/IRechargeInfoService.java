package com.hgsoft.account.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.utils.Pager;

public interface IRechargeInfoService {
	public Pager list(Pager pager,Date starTime ,Date endTime,RechargeInfo rechargeInfo,Customer customer,CusPointPoJo cusPointPoJo);		
	public Pager list2(Pager pager,String starTime ,String endTime,RechargeInfo rechargeInfo,Customer customer,CusPointPoJo cusPointPoJo);		
	public boolean save(String type,MainAccountInfo mainAccountInfo,RechargeInfo newRechargeInfo,RechargeInfo oldRechargeInfo,String flag,Map<String,Object> params);
	public boolean save(String type,MainAccountInfo mainAccountInfo,RechargeInfo newRechargeInfo,RechargeInfo oldRechargeInfo,String flag,String pospayjson,Map<String,Object> params);
	public RechargeInfo findById(Long id);
	public RechargeInfo findByCorrectId(Long correctId);
	public void save(RechargeInfo newRechargeInfo);
	public int updateCorrectState(Long id);
	public RechargeInfo findByPrepaidCBussinessId(Long id);
	public Pager findByPageForSumAmt(Pager pager,Date starTime ,Date endTime, RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo);
	
	public List<Receipt> getReceipt(Receipt receipt,String flag);
	/**
	 * 转账方式的缴款修改
	 * @param rechargeInfo 包含BankTransferID、id、TakeBalance、Memo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveEdit4BankTransfer(RechargeInfo rechargeInfo);
}
