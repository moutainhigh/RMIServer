package com.hgsoft.macao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.BankTransferBussinessDao;
import com.hgsoft.account.dao.BankTransferInfoDao;
import com.hgsoft.account.dao.BankTransferInfoHisDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoHisDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCApplyHisDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.AccountNCApplyDao;
import com.hgsoft.accountC.dao.AccountNCApplyHisDao;
import com.hgsoft.accountC.dao.BailDao;
import com.hgsoft.accountC.dao.MigrateDao;
import com.hgsoft.accountC.dao.MigrateDetailDao;
import com.hgsoft.accountC.dao.NewCardApplyDao;
import com.hgsoft.accountC.dao.NewCardApplyHisDao;
import com.hgsoft.accountC.dao.TransferApplyDao;
import com.hgsoft.accountC.dao.TransferDetailDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.accountC.entity.Migrate;
import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.accountC.entity.NewCardApply;
import com.hgsoft.accountC.entity.NewCardApplyHis;
import com.hgsoft.accountC.entity.TransferApply;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.BillGetHisDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.InvoiceDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.dao.MacaoReceiptDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.MacaoCardCustomerHis;
import com.hgsoft.macao.serviceInterface.IMacaoReceiptService;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagMaintainDao;
import com.hgsoft.obu.dao.TagRecoverDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoDao;
import com.hgsoft.obu.dao.TagTakeInfoDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.entity.TagMainRecordHis;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.obu.service.TagRecoverService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.serviceInterface.IReceiptService;
import com.hgsoft.prepaidC.dao.AddRegDao;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.utils.Pager;

@Service
public class MacaoReceiptService implements IMacaoReceiptService{
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private AccountBussinessDao accountBussinessDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private InvoiceDao invoiceDao;
	@Resource 
	private PrepaidCDao prepaidCDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private RechargeInfoDao rechargeInfoDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;
	@Resource
	private BankTransferInfoDao bankTransferInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private AccountCApplyHisDao accountCApplyHisDao;
	@Resource
	private AddRegDao addRegDao;
	@Resource
	private NewCardApplyDao newCardApplyDao;
	@Resource
	private BillGetDao billGetDao;
	@Resource
	private BillGetHisDao billGetHisDao;
	@Resource
	private TagTakeFeeInfoDao tagTakeFeeInfoDao;
	@Resource
	private TagTakeInfoDao tagTakeInfoDao;
	@Resource
	private BankTransferBussinessDao bankTransferBussinessDao;
	@Resource
	private BankTransferInfoHisDao bankTransferInfoHisDao;
	@Resource
	private MainAccountInfoHisDao mainAccountInfoHisDao;

	@Resource
	private NewCardApplyHisDao newCardApplyHisDao; 
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private BailDao bailDao;
	@Resource
	private AccountNCApplyDao accountNCApplyDao;
	@Resource
	private AccountNCApplyHisDao accountNCApplyHisDao;
	@Resource
	private MigrateDao migrateDao;
	@Resource
	private MigrateDetailDao migrateDetailDao;
	@Resource
	private TransferApplyDao transferApplyDao;
	@Resource
	private TransferDetailDao transferDetailDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private TagRecoverDao tagRecoverDao;
	@Resource
	private TagMaintainDao tagMaintainDao;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private CancelDao cancleDao;
	@Resource
	private MacaoReceiptDao macaoReceiptDao;
	
	@Resource
	private MacaoDao macaoDao;
	@Override
	public List<Receipt> findAll(Receipt receipt, Date starTime, Date endTime) {
		// TODO Auto-generated method stub
		return receiptDao.findAll(receipt, starTime, endTime);
	}
	@Override
	public Receipt findByCustomerId(Long id){
		return receiptDao.findByCustomerId(id);
	}
	
	@Override
	public Pager findPrintAgainAllByPager(Pager pager,Date starTime, Date endTime,Long id) {
		// TODO Auto-generated method stub
		return macaoReceiptDao.findPrintAgainAllByPager(pager,starTime, endTime,id);
	}

	@Override
	public Receipt find(Receipt receipt) {
		// TODO Auto-generated method stub
		return receiptDao.find(receipt);
	}
	
	public int[] batchUpdateReceipt(final List<Long> receiptIds) { 
		
		return receiptDao.batchUpdateReceipt(receiptIds);
	}
	
	
	
	
	//储值卡：1
	//记帐卡：2
	//电子标签：3
	//客户信息：4
	//账户信息：5
	public List<Map<String,Object>> findReceiptDetail(List<Receipt> receipts,Customer sessionCustomer,AccountCApply sessionAccountCApply){
		List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
		Receipt receipt=null;
		Map<String,Object> map=null;
		CustomerBussiness customerBussiness=null;
		VehicleBussiness vehicleBussiness = null;
		AccountCBussiness accountCBussiness=null;
		TagBusinessRecord tagBusinessRecord=null;
		if(receipts!=null || receipts.size()>0){
  			for(int i=0;i<receipts.size();i++){
  				receipt=receiptDao.findById(receipts.get(i).getId());
				String type=receipt.getTypeCode();
				if(receipt.getParentTypeCode().equals("4")){//客户信息
					customerBussiness=customerBussinessDao.findById(receipt.getBusinessId());
					if(customerBussiness!=null){
						String cardType=customerBussiness.getCardType();
						map=new HashMap<String,Object>();
						map.put("customerBussiness",customerBussiness);
						map.put("receipt", receipt);
						map.put("customerhis", customerHisDao.findById(receipt.getCustomerHisId()));
						Customer customer = (Customer)receiptDao.findCusByReceipt(receipt).get("customer");
						map.put("customer", customer);
						if(type.equals("12")){//客户信息修改     
							MacaoCardCustomerHis macaoCardCustomerHis = macaoDao.findByMacaoHisId(customerBussiness.getOldcustomerId());
							map.put("macaoCardCustomerHis",macaoCardCustomerHis);
							MacaoCardCustomer macaoCardCustomer = macaoDao.findByHisId(macaoCardCustomerHis.getMacaoCustomerId());
							if(macaoCardCustomer==null){
								 map.put("macaoCardCustomer",macaoDao.findByMacaoHisId(macaoCardCustomerHis.getMacaoCustomerId()));
							}else{
								map.put("macaoCardCustomer", macaoCardCustomer);
							}
						}else if(type.equals("15")){//车辆信息新增 
							map.put("vehicleInfo", vehicleInfoDao.findById(customerBussiness.getVehicleId()));
						}else if(type.equals("16")){//车辆信息修改
							//新车辆信息
							map.put("macaoCustomer", macaoDao.getMacaoCardCustomerByVehicleId(customerBussiness.getVehicleId()));
							map.put("vehicleInfo", vehicleInfoDao.findById(customerBussiness.getVehicleId()));
							//原车辆信息
							map.put("vehicleInfoHis", vehicleInfoHisDao.findByHisId(customerBussiness.getVehicleHisId()));
						}
						else if(type.equals("21") && cardType.equals("1")){// 信息服务管理   储值卡
							map.put("billcustomer", customerDao.findById(customerBussiness.getCustomerId()));
							BillGet billGet = billGetDao.findById(customerBussiness.getBillId());
							map.put("billGet", billGet);
							map.put("billGetHis", billGetHisDao.findByHisId(customerBussiness.getBillHisId()));
							
							map.put("prepaidCBusiness", prepaidCBussinessDao.findLastPrepaidCBussinessByCardNo(billGet.getCardBankNo()));
						}else if(type.equals("21") && cardType.equals("2")){// 信息服务管理   记帐卡
							map.put("billcustomer", customerDao.findById(customerBussiness.getCustomerId()));
							BillGet billGet = billGetDao.findById(customerBussiness.getBillId());
							map.put("billGet", billGet);
							if(billGet!=null){
								map.put("accountCApply",accountCApplyDao.findByBankAccount(billGet.getCardBankNo()));
							}
							map.put("billGetHis", billGetHisDao.findByHisId(customerBussiness.getBillHisId()));
						}else if(type.equals("22")){// 服务密码更改
//							map.put("pswcustomer", customerDao.findById(customerBussiness.getCustomerId()));
							map.put("macaoCustomer", macaoDao.findById(customerBussiness.getOldcustomerId()));
						}else if(type.equals("23")){// 服务密码重设
							map.put("macaoCustomer", macaoDao.findById(customerBussiness.getOldcustomerId()));
//							map.put("billcustomer", customerDao.findById(customerBussiness.getCustomerId()));
//							map.put("billGet", billGetDao.findById(customerBussiness.getBillId()));
						}
						datas.add(map);
					}
					
					
				}else if(receipt.getParentTypeCode().equals("2")){//记帐卡
					accountCBussiness=accountCBussinessDao.findById(receipt.getBusinessId());
					if(accountCBussiness!=null){
						map=new HashMap<String,Object>();
						map.put("accountCBussiness",accountCBussiness);
						map.put("receipt", receipt);
						MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(accountCBussiness.getCardNo());
						map.put("macaoCardCustomer", macaoCardCustomer);
//						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						map.put("mainAccountInfo", receiptDao.findMainAccByReceipt(receipt).get("mainAccountInfo"));
						if(type.equals(AccountCBussinessTypeEnum.accCardInitialApply.getValue())||type.equals("26")){// 20初次申请,26修改
							//accountCApply表里面的历史id可能会变很多次，如果能查到，就说明没修改，查不到就去历史表查
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getBusinessId());
							AccountCapplyHis accountCapplyHis = null;
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
								//要显示旧的信息
								AccountCapplyHis oldAccountCApply = accountCApplyHisDao.findById(accountCApply.getHisseqId());
								map.put("oldAccountCApply", oldAccountCApply);
							}else{
								accountCapplyHis = accountCApplyHisDao.findByHisId(accountCBussiness.getBusinessId());
								map.put("accountCApply", accountCapplyHis);
								//要显示旧的信息
								AccountCapplyHis oldAccountCApply = accountCApplyHisDao.findById(accountCapplyHis.getHisseqId());
								map.put("oldAccountCApply", oldAccountCApply);
							}
							//BillGet billGet = billGetDao.findByCardAccountID(accountCBussiness.getAccountId());
							if(receipt.getSeritem()!=null){
								String[] serItems = receipt.getSeritem().split(",");
								for(int j=0;j<serItems.length;j++){
									serItems[j] = serItems[j].trim();
								}
								map.put("serItems", serItems);
							}
							//修改要显示原回执编号
							AccountCBussiness oldAccountCBussiness = null;
							if(accountCApply!=null){
								accountCapplyHis = accountCApplyHisDao.findById(accountCApply.getHisseqId());
								if(accountCapplyHis!=null){
									oldAccountCBussiness = accountCBussinessDao.findByBussinessID(accountCapplyHis.getHisseqId());
								}
							}else{
								if(accountCapplyHis!=null){
									accountCapplyHis = accountCApplyHisDao.findById(accountCapplyHis.getHisseqId());
									if(accountCapplyHis!=null){
										oldAccountCBussiness = accountCBussinessDao.findByBussinessID(accountCapplyHis.getHisseqId());
									}
								}
							}
							if(oldAccountCBussiness!=null){
								Receipt oldReceipt = receiptDao.findByBusIdAndPTC(oldAccountCBussiness.getId(), "2");
								map.put("oldReceipt", oldReceipt);
								if(oldReceipt.getSeritem()!=null){
									String[] serItems = oldReceipt.getSeritem().split(",");
									for(int j=0;j<serItems.length;j++){
										serItems[j] = serItems[j].trim();
									}
									map.put("oldSerItems", serItems);
								}
							}
							
							
						}else if(type.equals(AccountCBussinessTypeEnum.accNewCardApply.getValue())|| type.equals("27")){// 21新增卡申请 27修改
							NewCardApply newCardApply=newCardApplyDao.findByHisId(accountCBussiness.getBusinessId()); 
							NewCardApplyHis newCardApplyHis = null;
							if(newCardApply!=null){
								map.put("newCardApply", newCardApply);
							}else{
								newCardApplyHis = newCardApplyHisDao.findByHisId(accountCBussiness.getBusinessId());
								map.put("newCardApply", newCardApplyHis);
							}
							//修改要显示原回执编号
							AccountCBussiness oldAccountCBussiness = null;
							
							if(newCardApply!=null){
								AccountCApply accountCApply=accountCApplyDao.findById(newCardApply.getApplyId());
								map.put("accountCApply", accountCApply);
								//原业务记录
								newCardApplyHis = newCardApplyHisDao.findById(newCardApply.getHisseqId());
								if(newCardApplyHis!=null){
									oldAccountCBussiness = accountCBussinessDao.findByBussinessID(newCardApplyHis.getHisseqId());
								}
								
							}else{
								if(newCardApplyHis!=null){
									AccountCApply accountCApply=accountCApplyDao.findById(newCardApplyHis.getApplyId());
									map.put("accountCApply", accountCApply);
									//原业务记录
									newCardApplyHis = newCardApplyHisDao.findById(newCardApplyHis.getHisseqId());
									if(newCardApplyHis!=null){
										oldAccountCBussiness = accountCBussinessDao.findByBussinessID(newCardApplyHis.getHisseqId());
									}
								}
							}
							
							if(oldAccountCBussiness!=null){
								Receipt oldReceipt = receiptDao.findByBusIdAndPTC(oldAccountCBussiness.getId(), "2");
								map.put("oldReceipt", oldReceipt);
							}
				
						}else if(type.equals(AccountCBussinessTypeEnum.accCardIssue.getValue())){// 记帐卡发行
//							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
//							//BillGet billGet = null;
//							if(accountCApply!=null){
//								map.put("accountCApply", accountCApply);
//								//billGet = billGetDao.findByCardBankNo(accountCApply.getBankAccount());
//							}else{
//								AccountCapplyHis accountCapplyHis = accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
//								map.put("accountCApply", accountCapplyHis);
//								//billGet = billGetDao.findByCardBankNo(accountCapplyHis.getBankAccount());
//							}
							map.put("accountCApply", sessionAccountCApply);
							AccountCInfo accountCInfo=accountCInfoDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountCInfo!=null){
								map.put("accountCInfo", accountCInfo);
							}else{
								AccountCInfoHis accountCInfoHis = accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId());
								map.put("accountCInfo", accountCInfoHis);
							}
							map.put("vehicleInfo", receiptDao.findVehByReceipt(receipt).get("vehicleInfo"));
							
							/*String[] serItems = billGet.getSerItem().split(",");
							for(int j=0;j<serItems.length;j++){
								serItems[j] = serItems[j].trim();
							}
							map.put("serItems", serItems);*/
						}else if(type.equals(AccountCBussinessTypeEnum.accCardLoss.getValue())||type.equals(AccountCBussinessTypeEnum.accCardCanceLoss.getValue())){// 3挂失 4解挂
							map.put("actionTime",FieldUtil.getActionTime(accountCBussiness.getTradeTime()) );//挂失或者解挂生效时间
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardReplace.getValue())){// 5补领accCardReplaceMent.jsp
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
							AccountCInfo accountCInfo=accountCInfoDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountCInfo!=null){
								map.put("accountCInfo", accountCInfo);
							}else{
								AccountCInfoHis accountCInfoHis = accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId());
								map.put("macaoCardCustomer", macaoCardCustomer);
							}
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardLock.getValue())){// 6旧卡锁定
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardReplaceNewcard.getValue())){// 7补领新卡accCardChangeCard.jsp
							AccountCInfo accountCInfo=accountCInfoDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountCInfo!=null){
								map.put("accountCInfo", accountCInfo);
							}else{
								AccountCInfoHis accountCInfoHis = accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId());
								map.put("accountCInfo",accountCInfoHis);
							}
							
							//有卡换卡/无卡换卡
							AccountCBussiness oldAccountCBussiness = accountCBussinessDao.findLockByCno(accountCBussiness.getOldCardNo());
							map.put("oldAccountCBussiness", oldAccountCBussiness);
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStopWithCard.getValue())||type.equals(AccountCBussinessTypeEnum.accCardStopNotCard.getValue())){// 8有卡终止使用  9无卡终止使用
							//不用记录
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardEdPassWord.getValue())||type.equals(AccountCBussinessTypeEnum.accCardNewPassword.getValue())){// 13消费密码修改  14消费密码重设
//							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							//AccountCApply accountCApply=accountCApplyDao.findByCardNo(accountCBussiness.getCardNo());
//							if(accountCApply!=null){
//								map.put("accountCApply", accountCApply);
//							}else{
//								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
//							}
							map.put("accountCApply", sessionAccountCApply);
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStop.getValue())){// 15停用
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStart.getValue())){// 16启用
							
						}
						datas.add(map);
					}
					
					
				}else if(receipt.getParentTypeCode().equals("6")){
					vehicleBussiness=vehicleBussinessDao.findById(receipt.getBusinessId());
					if(null !=vehicleBussiness){
						map=new HashMap<String,Object>();
						MacaoCardCustomer macaoCardCustomer1 = macaoDao.getCustomerByCardNo(vehicleBussiness.getCardNo());
						map.put("macaoCardCustomer", macaoCardCustomer1);
						map.put("vehicleBussiness", vehicleBussiness);
						map.put("receipt", receipt);
						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						if(type.equals("53")){// 53记帐卡启用
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("accountCInfo", accountCInfoDao.findByCardNo(vehicleBussiness.getCardNo()));
						}else if(type.equals("54")){// 54记帐卡停用
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("accountCInfo", accountCInfoDao.findByCardNo(vehicleBussiness.getCardNo()));
						}
						datas.add(map);
					}
					
				}
				
				
				
				
				
				
				else if(receipt.getParentTypeCode().equals("3")){//电子标签
					tagBusinessRecord=tagBusinessRecordDao.findById(receipt.getBusinessId());
					//if(tagBusinessRecord!=null){
						map=new HashMap<String,Object>();
						map.put("tagBusinessRecord",tagBusinessRecord);
						MacaoCardCustomer macaoCardCustomer1 = macaoDao.findCustomerByTagNo(tagBusinessRecord.getTagNo());
						map.put("macaoCardCustomer", macaoCardCustomer1);
						map.put("receipt", receipt);
						map.put("vehicleInfo", receiptDao.findVehByReceipt(receipt).get("vehicleInfo"));
//						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						map.put("customer", sessionCustomer);
						map.put("mainAccountInfo", receiptDao.findMainAccByReceipt(receipt).get("mainAccountInfo"));
						if(type.equals(TagBussinessTypeEnum.tagIssuce.getValue())){//1发行
							TagInfo tagInfo=tagInfoDao.findById(tagBusinessRecord.getBussinessid());
							if(tagInfo!=null){
								map.put("tagInfo", tagInfoDao.findById(tagBusinessRecord.getBussinessid()));
							}else{
								TagInfoHis tagInfoHis = tagInfoDao.findHisByHisId(tagBusinessRecord.getBussinessid());
								map.put("tagInfo", tagInfoHis);
							}
						}else if(type.equals(TagBussinessTypeEnum.tagRecover.getValue())|| 
								type.equals(TagBussinessTypeEnum.tagChange.getValue())){//4恢复 3更换
							map.put("tagMainRecord", tagRecoverDao.findById(tagBusinessRecord.getBussinessid()));
						}else if(type.equals(TagBussinessTypeEnum.tagStop.getValue())){//5停用挂起
							VehicleInfo vehicleInfo=vehicleInfoDao.findByHisId(tagBusinessRecord.getBussinessid());//停用的时候记录的是车辆历史id
							if(vehicleInfo!=null){
								map.put("vehicleInfo", vehicleInfo);
							}else{
								map.put("vehicleInfo", vehicleInfoHisDao.findByHisId(tagBusinessRecord.getBussinessid()));
							}
						}else if(type.equals(TagBussinessTypeEnum.tagMirate.getValue())){//6迁移
							VehicleInfo oldVehicleInfo=vehicleInfoDao.findByHisId(tagBusinessRecord.getBussinessid());//迁移的时候记录的是旧车辆历史id
							if(oldVehicleInfo!=null){
								map.put("oldVehicleInfo", oldVehicleInfo);
//								map.put("oldCustomer", customerDao.findById(oldVehicleInfo.getCustomerID()));
							}else{
								VehicleInfoHis oldVehicleInfo1 = vehicleInfoHisDao.findByHisId(tagBusinessRecord.getBussinessid());
								map.put("oldVehicleInfo", oldVehicleInfo1);
//								map.put("oldCustomer", customerDao.findById(oldVehicleInfo1.getCustomerID()));
							}
							
						}else if(type.equals(TagBussinessTypeEnum.tagCannel.getValue())){//7注销
							VehicleInfo vehicleInfo=vehicleInfoDao.findByHisId(tagBusinessRecord.getBussinessid());//注销的时候记录的是车辆历史id
							if(vehicleInfo!=null){
								map.put("vehicleInfo", vehicleInfo);
							}else{
								map.put("vehicleInfo", vehicleInfoHisDao.findByHisId(tagBusinessRecord.getBussinessid()));
							}
						}else if(type.equals(TagBussinessTypeEnum.tagRepair.getValue())||
								type.equals(TagBussinessTypeEnum.tagRepairReturn.getValue())){//2维修登记 10维修返回客户
							TagMainRecord tagMainRecord=tagMaintainDao.findByHisId(tagBusinessRecord.getBussinessid());
							if(tagMainRecord!=null){
								map.put("tagMainRecord", tagMainRecord);
								if(tagMainRecord.getBackupTagNo()!=null&&tagMainRecord.getBackupTagNo()!=""){
									TagInfo tagInfo=tagInfoDao.findByHisId(tagMainRecord.getBackTagHisId());
									if(tagInfo!=null){
										map.put("backTagNoInfo", tagInfo);
									}else{
										map.put("backTagNoInfo", tagInfoDao.findHisByHisId(tagMainRecord.getBackTagHisId()));
									}
								}
								map.put("taginfo", tagInfoDao.findById(tagMainRecord.getTagInfoID()));
							}else{
								TagMainRecordHis tagMainRecordHis=tagMaintainDao.findHisByHisId(tagBusinessRecord.getBussinessid());
								map.put("tagMainRecord", tagMainRecordHis);
								if(tagMainRecordHis.getBackupTagNo()!=null&&tagMainRecordHis.getBackupTagNo()!=""){
									TagInfo tagInfo=tagInfoDao.findByHisId(tagMainRecord.getBackTagHisId());
									if(tagInfo!=null){
										map.put("backTagNoInfo", tagInfo);
									}else{
										map.put("backTagNoInfo", tagInfoDao.findHisByHisId(tagMainRecord.getBackTagHisId()));
									}
								}
								map.put("taginfo", tagInfoDao.findById(tagMainRecordHis.getTagInfoID()));
							}
						}
						
						datas.add(map);
					}
				}
				
			//}
			
		}
		return datas;
	}

}
