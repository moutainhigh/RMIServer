package com.hgsoft.ivr.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.ivr.entity.ClientLoginInfo;
import com.hgsoft.ivr.entity.ReqInterfaceFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;


public interface IIVRInterfaceService {
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow);
	public AccountCInfo findACByCardNo(String cardNo,String cardType);
	public PrepaidC findPCByCardNo(String cardNo,String cardType);
	public List<Map<String, Object>> findCardByNOandType(String cardNo,String cardKind);
	public boolean checkAccountNOandCardNo(String accountNo,String cardNo);
	public Map<String, String> findIsExistCardNo(String cardNo,String cardKind);
	public Map<String, String> findVerifyByCardNo(String cardNo,String cardKind,String password);
	public Map<String, String> findIsExistAccountNo(String accountNo);
	public Map<String, String> findVerifyByUserNo(String userNo,String password);
	public Map<String, String> saveChangPwdByCardNo(String cardNo,String cardKind,String oldPwd,String newPwd,SysAdmin sysAdmin,CusPointPoJo cusPointPoJo);
	public Map<String, String> saveChangPwdByUserNo(String userNo,String oldPwd,String newPwd,SysAdmin sysAdmin,CusPointPoJo cusPointPoJo);
	public Map<String, String> saveLostByCardNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType);
	public Map<String, String> saveLostByUserNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType);
	public Map<String, String> saveReleaseByCardNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType);
	public Map<String, String> saveReleaseByUserNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType);
	public Map<String, Object> queryBillByCardNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String year,String month);
	public Map<String, Object> queryBillByAccountNo(String accountNo,String year,String month);
	public Map<String, String> saveChangTradingPwdByCardNo(String cardNo,String cardKind,String oldTradingPwd,String newTradingPwd,SysAdmin sysAdmin,CusPointPoJo cusPointPoJo);
	public Map<String, String> findIsExistUserNo(String userNo);
	public Map<String, String> findVerifyTradingPwdByCardNo(String cardNo,String cardKind,String password);
	public Map<String, String> saveClientLoginInfo(ClientLoginInfo cli);
	public Map<String, Object> verifyClientLoginInfo(String tel);
	public Map<String, String> clearClientLoginInfo(String tel,List<ClientLoginInfo> list);

}
