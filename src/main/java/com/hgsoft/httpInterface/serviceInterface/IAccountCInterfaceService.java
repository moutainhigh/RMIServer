package com.hgsoft.httpInterface.serviceInterface;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.httpInterface.entity.NewCardVehicleVo;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface IAccountCInterfaceService {

	public Pager accountCFirstApplyList(Pager pager, Customer customer, String bankAccount, String state,String startTime,String endTime,Long placeId, String type);

	public Map<String, Object> accountCFirstApplyInfo(Long accountCApplyId);

	public Pager accountCNewApplyList(Pager pager, String userNo, String organ,String idCode,String bankAccount, String state,String startTime,String endTime);

	public Map<String, Object> accountCNewApplyInfo(Long newCardId);

	public Pager migrateList(Pager pager, Customer customer,Long migrateId,String bankAccount, String startTime, String endTime, String state);

	public Map<String, Object> migrateInfo(Long id);

	public Pager transferList(Pager pager, Customer customer,Long transferId,String bankAccount, String startTime, String endTime, String state);

	public Map<String, Object> transferInfo(Long id);

	public Pager accountCRenameList(Pager pager, Customer customer, String bankAccount, String state);

	public Map<String, Object> accountCRenameInfo(Long id);

	public boolean updateAccountCFirstApplyState(Long id, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness,String payAgreementNo,String appFailMemo,String validity,String virenum,String maxAcr);

	public boolean updateAccountCNewApplyState(Long id, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness,String validity,String virenum,String maxAcr, List<NewCardVehicleVo> newCardVehicleVoList);

	public boolean updateMigrateState(Long id, String newState, String oldState, String approver,String approverNo,String approverName, String appTime);

	public boolean updateTransferState(Long id, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness);

	public boolean updateAccountCRenameState(Long id, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness);

	public boolean migrateCardIsOk(Long id);

	public boolean transferCardIsOk(Long id);

	public String batchUpdateBillGet(String param,String serItem);

	public Map<String,Object> deleteNewCardVehicle(Long id);

	public String batchUpdateAddressEmail(String param, String type);

	public boolean updateBail(Long id, String state, String carBail,String truckBail, String approver,String approverNo,String approverName, String appTime, String type);

	public boolean updateNewCardBail(Long id, String state, String carBail,String truckBail, String approver,String approverNo,String approverName, String appTime, String type);

	public List<Map<String, Object>> accountCFirstApplyExportList(String state, String startTime, String endTime,String accountType);

	public boolean batchUpdateAccountCApplyState(String param);

	public Map<String, Object> bailInfo(Long accountCApplyId);

	//public boolean updateBail(Long id, String state, String bail, String approver, String appTime);

	public boolean saveBailBackApp(Bail bail,String newAppState,Long bussinessPlaceId);
	public Pager bailBackAppList(Pager pager,String userNo,String organ,String IdCode,String bankNo,String startTime,String endTime,String state);
	public Map<String , Object> bailBackAppInfo(Long bailId);

	public Map<String, Object> findSureIsCancle(String cardNo);

	public Map<String, String> checkCardVehicle(String param);

	public List<Map<String, Object>> findAccountCinfoMap(Long migrateid);

}
