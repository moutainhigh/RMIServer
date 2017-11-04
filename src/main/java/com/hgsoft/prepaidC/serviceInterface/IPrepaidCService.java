package com.hgsoft.prepaidC.serviceInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.ElectronicPurse;
import com.hgsoft.prepaidC.entity.ElectronicPurseHis;
import com.hgsoft.prepaidC.entity.InvoiceChangeFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IPrepaidCService {

	public Pager prepaidCList(Pager pager, Customer customer,String cardNo);
	public Pager prepaidCListForAMMS(Pager pager, Customer customer,String cardNo,String cardType,String bankNo);

	public String saveUnusable(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord,boolean result);
	
	public String saveIssue(PrepaidC prepaidC,ElectronicPurse electronicPurse,CarObuCardInfo carObuCardInfo,PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,BillGet billGet,Map<String,Object> params);
	public String saveIssueForAMMS(PrepaidC prepaidC,ElectronicPurse electronicPurse,CarObuCardInfo carObuCardInfo,PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,BillGet billGet,Map<String,Object> params);
	public String delPrepaidC(PrepaidC prepaidC,CarObuCardInfo carObuCardInfo,PrepaidCHis prepaidCHis,ElectronicPurse electronicPurse,ElectronicPurseHis electronicPurseHis,MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,BillGet billGet,BillGetHis billGetHis);
	public String delPrepaidCForAMMS(PrepaidC prepaidC,CarObuCardInfo carObuCardInfo,PrepaidCHis prepaidCHis,ElectronicPurse electronicPurse,ElectronicPurseHis electronicPurseHis,MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,BillGet billGet,BillGetHis billGetHis);

	public PrepaidC findByPrepaidCNo(String cardNo);
	public PrepaidC find(PrepaidC prepaidC);

	public String saveGainCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC, ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness rechargePrepaidCBussiness, Map<String,Object> params);
	public String saveGainCardForAMMS(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC, ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness rechargePrepaidCBussiness, Map<String,Object> params);

	public void updateGainCardRecharge(PrepaidCBussiness prepaidCBussiness);
	public String saveReplaceCard(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC,ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, Map<String,Object> params);
	public String saveReplaceCardForAMMS(MainAccountInfo mainAccountInfo,PrepaidC prepaidC, PrepaidC newPrepaidC,ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,ServiceFlowRecord serviceFlowRecord, Map<String,Object> params);
	public Customer getCustomerByPrepaidCardNo(String cardNo);
	public void updatePrepaidC(PrepaidC prepaidC);
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,Integer type,List<ReturnFee> returnFeeList);
	public void updateRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList, Map<String,Object> params);
	public void updateReversal(PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList,Long oldBussinessID, Map<String,Object> params);
	public boolean saveReversal(MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,PrepaidCBussiness oldPrepaidCBussiness);
	public void saveLock(PrepaidC prepaidC, Map<String,Object> params);
	public void saveLock(PrepaidC prepaidC,String systemType);
	public void unLock(PrepaidC prepaidC, Map<String,Object> params);
	public boolean updatePwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness, Map<String,Object> params);
	public boolean resetPwd(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness, Map<String,Object> params);

    boolean isBlack(String cardNo);

    public void changeInvoicePrint(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness,Map<String,Object> params);
	public PrepaidC findByCustomerId(Long id);
	public Pager findByCustomer(Pager pager, Customer customer);
	public PrepaidC findByPrepaidCNoToGain(String cardNo);
	public PrepaidC findById(Long id);
	public boolean saveTransfer(PrepaidCTransfer prepaidCTransfer, String cardNoRights);
	public List<Map<String, Object>> listCardByCustomerId(Long customerId);
	boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,
			AddRegDetail addRegDetail, MainAccountInfo mainAccountInfo,
			Integer type, List<ReturnFee> returnFeeList,
			RechargeInfo rechargeInfo);
	//public  List<Map<String, Object>>  findByCardNO(String cardNo);
	public String getTransFee(String cardNo);
	public String lockTransFee(String cardNos,String Fees);
	public String notifyTransFee(String cardNos,String Fees);
	
	public List<Map<String, Object>> findCards(String cardNo);
	public Map<String, Object> updateWriteCardFlag(String cardNo);
	public Map<String, Object> updateServiceType(String cardNo, String seritem, Customer customer, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params);

	public InvoiceChangeFlow findInvoiceChangeFlowByCardNo(String cardNo);
	
	public Pager getprepaidCInfo(Pager pager,String code, String idCardString,String flag,String secondno);
	public String getCardFee(Long operId,String operName);
	public BigDecimal getFaceValue(Long operId,String operName);
	public List  findCustomerList(String code, String idCardString);
	
	public DbasCardFlow findPreCancelDbasCardFlow(String cardNo);
	public List<Map<String,Object>> findAllCusPo();
	public String findCusPoById(Long id);
	public Map<String,Object> findFirstIssueFaceValue(String cardNo);

	void updateFirstRecharge(String firstRecharge, Long id);
	
	public BigDecimal findCardSysBalance(String cardNo);
	public List<Map<String,Object>> findSalesType(String type,String salesState,String salesFlag);

	Pager findAllCardInfosByUserNo(String userNo, Pager pager);

	boolean saveRechargeGainCard(PrepaidCBussiness prepaidCBussiness, DbasCardFlow dbasCardFlow);

	public DbasCardFlow findDisCompleteByOldCardNo(String oldCardNo);
}

