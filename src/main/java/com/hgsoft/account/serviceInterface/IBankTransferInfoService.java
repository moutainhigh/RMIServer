package com.hgsoft.account.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
@Service
public interface IBankTransferInfoService {
	public Pager list(Pager pager,Date starTime ,Date endTime,BankTransferInfo bankTransferInfo  ,Customer customer);
	public Pager list(Pager pager,String starTime ,String endTime,BankTransferInfo bankTransferInfo  ,Customer customer);
	//电子标签提货金额登记、账户缴款——收费方式——转账——客户转账列表
	public Pager listForBankTransfer(Pager pager,BankTransferInfo bankTransferInfo);
	public BankTransferInfo findBytId(Long id);
	public boolean save(String type,MainAccountInfo mainAccountInfo,RechargeInfo rechargeInfo,BankTransferInfo bankTransferInfo,BankTransferBussiness bankTransferBussiness,Map<String,Object> params);
	public BankTransferBussiness findBankTransferBussiness(BankTransferBussiness bankTransferBussiness);
	public Pager findBankTransferList(Pager pager,String arrvialStartTime,String arrivalEndTime,String fileName);
	public Map<String, String> deleteBankTransferInfo(Long id);
	public Map<String, String> saveApprBankTransfer(BankTransferInfo bankTransferInfo);
	public Map<String, String> saveBankTransfer(List<BankTransferInfo> bankTransferInfos);
	public Customer findByTypeAndCode(String idType,String idCode);
	/**
	 * 2017-06-05 
	 * 查找银行到账信息记录的操作业务流水
	 * @param pager
	 * @param bankTransferId
	 * @return Pager
	 */
	public Pager listBankTransferBussiness(Pager pager,Long bankTransferId);
}
