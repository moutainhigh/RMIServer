package com.hgsoft.accountC.service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.serviceInterface.IAccountCApplyService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.OmsMinusBankDao;
import com.hgsoft.httpInterface.dao.TbBankdetailDao;
import com.hgsoft.httpInterface.entity.OmsMinusBank;
import com.hgsoft.httpInterface.entity.TbBankdetail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 记帐卡申请表
 * @author gaosiling
 * 2016年2月20日10:21:11
 */
@Service
public class AccountCApplyService implements IAccountCApplyService{

	private static Logger logger = Logger.getLogger(AccountCApplyService.class.getName());
	
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private TbBankdetailDao tbBankdetailDao;
	@Resource
	private OmsMinusBankDao omsMinusBankDao;

	/**
	 * 记帐卡申请表查询列表
	 * @param  customer
	 * @author gaosiling
	 */
	@Override
	public List<Map<String, Object>> findByList(Customer customer){
		try {
			return accountCApplyDao.findByList(customer);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表列表失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 记帐卡申请表查询
	 * @param  id
	 * @author gaosiling
	 */
	@Override
	public AccountCApply findById(Long id) {
		try {
			return accountCApplyDao.findById(id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表id为"+id+"失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 记帐卡申请表查询
	 * @param  bankAccount
	 * @author gaosiling
	 */
	@Override
	public AccountCApply findByBankAccount(String bankAccount) {
		try {
			return accountCApplyDao.findByBankAccount(bankAccount);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表银行账户id为"+bankAccount+"失败");
			throw new ApplicationException();
		}
	}

	/**
	 * 记帐卡申请表查询
	 * @param  userNo
	 * @author gaosiling
	 */
	@Override
	public List findByBankAccountByUserno(String userNo) {
		try {
			return accountCApplyDao.findByBankAccountByUserno(userNo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表银行账户客户号为"+userNo+"失败");
			throw new ApplicationException();
		}
	}

	@Override
	public List<TbBankdetail> listTbBankdetail(TbBankdetail tbBankdetail) {
		return tbBankdetailDao.listTbBankdetail(tbBankdetail);
	}
	@Override
	public List<OmsMinusBank> listOmsMinusBank(OmsMinusBank omsMinusBank) {
		return omsMinusBankDao.listOmsMinusBank(omsMinusBank);
	}

	@Override
	public AccountCApply find(AccountCApply accountCApply) {
		try {
			return accountCApplyDao.find(accountCApply);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表银行账户id为失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 记帐卡申请表查询
	 * @param  customer
	 * @param  accountCApply
	 * @author gaosiling
	 */
	@Override
	public List<Map<String, Object>> findByListByState(Customer customer,AccountCApply accountCApply){
		try {
			return accountCApplyDao.findByListByState(customer, accountCApply);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询记帐卡申请表银行账户失败");
			throw new ApplicationException();
		}
	}

	@Override
	public List<Map<String, Object>> getBankByCustomerId(Long customerId) {
		try {
			return accountCApplyDao.getBankByCustomerId(customerId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"根据客户id查询记帐卡申请表列表失败");
			throw new ApplicationException();
		}
	}

	@Override
	public AccountCApply findByCustomerId(Long customerId) {
		
		return accountCApplyDao.findbyCustomerId(customerId);
	}



	@Override
	public List<Map<String, Object>> findListByCustomerId(Long customerId) {
		return accountCApplyDao.findListByCustomerId(customerId);
	}

	@Override
	public List<Map<String, Object>> findListByCustomerId(Long customerId, String appState) {
		return accountCApplyDao.findListByCustomerId(customerId,appState);
	}
	
	@Override
	public List<Map<String, Object>> findListByCustomerIdCSMS(Long customerId, String appState) {
		return accountCApplyDao.findListByCustomerIdCSMS(customerId,appState);
	}

	@Override
	public AccountCApply findByCardNo(String cardNo) {
		// TODO Auto-generated method stub
		return accountCApplyDao.findByCardNo(cardNo);
	}

	
	public AccountCApply findBySubAccountInfoId(Long bankAccountId) {
		return accountCApplyDao.findBySubAccountInfoId(bankAccountId);
	}

	@Override
	public List<OmsMinusBank> listOmsBankNameClearCode(OmsMinusBank omsMinusBank) {
		return omsMinusBankDao.listOmsBankNameClearCode(omsMinusBank);
	}
	
}
