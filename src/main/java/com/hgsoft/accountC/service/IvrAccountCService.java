package com.hgsoft.accountC.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.IvrAccountCDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.serviceInterface.IIvrAccountCService;
import com.hgsoft.utils.Pager;

@Service
public class IvrAccountCService implements IIvrAccountCService{
	@Resource
	private IvrAccountCDao ivrAccountCDao;
	
	@Override
	public Pager getCustomerInfo(Pager pager, String ivrValidateCustomer,String artificialValidateCustomer) {
		return ivrAccountCDao.finCustomerInfo(pager,ivrValidateCustomer,artificialValidateCustomer);
	}
	@Override
	public void updateCustomerInfo(String aaid,String cid,String zipCode,String invoicePrn,String name,String fax,String organ,String email,String linkMan,String idType,String idCode,String limitMoney,String tel,String addr){
		ivrAccountCDao.updateCustomer(cid,zipCode, organ, email, linkMan, idType, idCode, tel, addr);
		ivrAccountCDao.updateAccountCApply(aaid,invoicePrn, linkMan, tel);
	}
	@Override
	public Pager getAccountCMessage(Pager pager,String ivrValidateCustomer,String artificialValidateCustomer){
		return ivrAccountCDao.findAccountCMessage(pager, ivrValidateCustomer, artificialValidateCustomer);
	}
	@Override
	public void updateAccountCMessage(String bgid, String seritem) {
		ivrAccountCDao.updateAccountCMessage(bgid, seritem);
	}
	@Override
	public Pager getAccountInfo(Pager pager,String code,String flag){
		return ivrAccountCDao.findAccountCInfo(pager,code,flag);
	}
	public void updateAccountcInfoState(String aiid){
		ivrAccountCDao.updateState(aiid);
	}
	@Override
	public AccountCInfo getAccountCInfoById(String aiid) {
		return ivrAccountCDao.getAccountCInfoById(aiid);
	}
	@Override
	public String getAccountCTotalBail(String userNo) {
		return ivrAccountCDao.getAccountCTotalBail(userNo);
	}
	@Override
	public List<Map<String, Object>> findCustomerByCardNo(String cardno) {
		return ivrAccountCDao.findCustomerByCardNo(cardno);
	}
	
}
