package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.*;

import java.util.List;
import java.util.Map;

public interface IAgentPrepaidCService {
	public String saveIssue(PrepaidC prepaidC,ElectronicPurse electronicPurse,CarObuCardInfo carObuCardInfo,PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,BillGet billGet);
	public void updateRecharge(PrepaidCBussiness prepaidCBussiness,AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList, Map<String,Object> params);
	public void updateReversal(PrepaidCBussiness prepaidCBussiness,MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList,Long oldBussinessID, Map<String,Object> params);
	public boolean saveReversal(MainAccountInfo mainAccountInfo,PrepaidCBussiness prepaidCBussiness,PrepaidCBussiness oldPrepaidCBussiness);
	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness,
			AddRegDetail addRegDetail, MainAccountInfo mainAccountInfo,
			Integer type, List<ReturnFee> returnFeeList );

	public String saveReplaceCard(MainAccountInfo mainAccountInfo,
			PrepaidC prepaidC, PrepaidC newPrepaidC,
			ElectronicPurse electronicPurse,PrepaidCBussiness prepaidCBussiness,
			ServiceFlowRecord serviceFlowRecord);
	
	public String saveGainCard(MainAccountInfo mainAccountInfo, PrepaidC prepaidC, PrepaidC newPrepaidC, ElectronicPurse electronicPurse, PrepaidCBussiness prepaidCBussiness, ServiceFlowRecord serviceFlowRecord, PrepaidCBussiness rechargePrepaidCBussiness);

	public String saveStopCard(ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness prepaidCBussiness,Cancel cancel, Customer customer,PrepaidC prepaidC);
	public String delPrepaidC(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,
			PrepaidCHis prepaidCHis, ElectronicPurse electronicPurse,
			ElectronicPurseHis electronicPurseHis,
			MainAccountInfo mainAccountInfo,
			PrepaidCBussiness prepaidCBussiness, BillGet billGet,
			BillGetHis billGetHis);
	public Map<String, Object> saveHalfTrue(PrepaidCBussiness prepaidCBussiness);
	public Map<String, Object> saveHalfFalse(PrepaidCBussiness prepaidCBussiness);
	public Map<String,Object> findCardFeeByOMSInterface();
	public Map<String, Object> findFaceValueByOMSInterface();
	public Map<String,Object> findSingleCardFeeLimit();

	boolean saveRechargeGainCard(PrepaidCBussiness prepaidCBussiness, DbasCardFlow dbasCardFlow);
}