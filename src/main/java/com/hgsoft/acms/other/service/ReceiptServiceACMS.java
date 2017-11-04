package com.hgsoft.acms.other.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.StringUtil;
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
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.accountC.entity.Migrate;
import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.accountC.entity.NewCardApply;
import com.hgsoft.accountC.entity.NewCardApplyHis;
import com.hgsoft.accountC.entity.TransferApply;
import com.hgsoft.acms.other.serviceInterface.IReceiptServiceACMS;
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
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagMaintainDao;
import com.hgsoft.obu.dao.TagRecoverDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoDao;
import com.hgsoft.obu.dao.TagTakeInfoDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.entity.TagMainRecordHis;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.prepaidC.dao.AddRegDao;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.utils.Pager;

@Service
public class ReceiptServiceACMS implements IReceiptServiceACMS {
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
	@Override
	public List<Receipt> findAll(Receipt receipt, Date starTime, Date endTime) {
		return receiptDao.findAll(receipt, starTime, endTime);
	}
	@Override
	public Receipt findByCustomerId(Long id){
		return receiptDao.findByCustomerId(id);
	}
	
	@Override
	public Pager findPrintAgainAllByPager(Pager pager,Date starTime, Date endTime) {
		return receiptDao.findPrintAgainAllByPager(pager,starTime, endTime);
	}

	@Override
	public Receipt find(Receipt receipt) {
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
	public List<Map<String,Object>> findReceiptDetail(List<Receipt> receipts){
		List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
		Receipt receipt=null;
		Map<String,Object> map=null;
		CustomerBussiness customerBussiness=null;
		VehicleBussiness vehicleBussiness = null;
		PrepaidCBussiness prepaidCBussiness=null;
		AccountBussiness accountBussiness=null;
		AccountCBussiness accountCBussiness=null;
		TagBusinessRecord tagBusinessRecord=null;
		if(receipts!=null && receipts.size()>0){
  			for(int i=0;i<receipts.size();i++){
  				receipt=receiptDao.findById(receipts.get(i).getId());
				String type=receipt.getTypeCode();
				if(receipt.getParentTypeCode().equals("5")){//客户信息
					customerBussiness=customerBussinessDao.findById(receipt.getBusinessId());
					if(customerBussiness!=null){
						String cardType=customerBussiness.getCardType();
						map=new HashMap<String,Object>();
						map.put("customerBussiness",customerBussiness);
						map.put("receipt", receipt);
						map.put("customerhis", customerHisDao.findById(receipt.getCustomerHisId()));
						Customer customer = (Customer)receiptDao.findCusByReceipt(receipt).get("customer");
						map.put("customer", customer);
						
						if(type.equals("11")){//客户新增       未测试成功
							map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						}else if(type.equals("12")){//客户信息修改      未测试成功
							
						}else if(type.equals("13")){//客户信息注销   
							map.put("cancle", cancleDao.findByCode(customer.getUserNo()));
						}
						else if(type.equals("15")){//车辆信息新增 
							map.put("vehicleInfo", vehicleInfoDao.findById(customerBussiness.getVehicleId()));
						}else if(type.equals("16")){//车辆信息修改
							//新车辆信息
							map.put("vehicleInfo", vehicleInfoDao.findById(customerBussiness.getVehicleId()));
							//原车辆信息
							map.put("vehicleInfoHis", vehicleInfoHisDao.findByHisId(customerBussiness.getVehicleHisId()));
						}else if(type.equals("17")){//车辆信息迁移
							map.put("newcustomer", customerDao.findById(customerBussiness.getMigratecustomerId()));
							//为什么要查客户的历史表里面去查旧客户啊？？没看懂
//							map.put("oldcustomer", customerHisDao.findByHisId(customerBussiness.getOldcustomerId()));
							map.put("oldcustomer", customerDao.findById(customerBussiness.getCustomerId()));
							map.put("vehicleInfo", vehicleInfoDao.findById(customerBussiness.getVehicleId()));
						}
						else if(type.equals("18")){// 发票信息新增
							map.put("invoicecustomer", customerDao.findById(customerBussiness.getCustomerId()));
							map.put("invoice", invoiceDao.findById(customerBussiness.getInvoiceId()));
							map.put("invoiceList", invoiceDao.findByCustomer(customerBussiness.getCustomerId()));
						}
						else if(type.equals("19")){// 发票信息修改
							map.put("invoicecustomer", customerDao.findById(customerBussiness.getCustomerId()));
							map.put("invoice", invoiceDao.findById(customerBussiness.getInvoiceId()));
						
							map.put("invoicehis", invoiceDao.findInvoiceHisByHisId(customerBussiness.getInvoiceHisId()));
							map.put("invoiceList", invoiceDao.findByCustomer(customerBussiness.getCustomerId()));
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
							map.put("pswcustomer", customerDao.findById(customerBussiness.getCustomerId()));
						}else if(type.equals("23")){// 服务密码重设
							map.put("billcustomer", customerDao.findById(customerBussiness.getCustomerId()));
							map.put("billGet", billGetDao.findById(customerBussiness.getBillId()));
						}
						datas.add(map);
					}
					
					
					
				}else if(receipt.getParentTypeCode().equals("1")){//账户
					accountBussiness=accountBussinessDao.findById(receipt.getBusinessId());
					if(accountBussiness!=null){
						map=new HashMap<String,Object>();
						map.put("accountBussiness",accountBussiness);
						map.put("receipt", receipt);
						map.put("customer", customerDao.findById(accountBussiness.getUserId()));
						if(type.equals("1")){// 缴款
							map.put("rechargeInfo", rechargeInfoDao.findById(accountBussiness.getBussinessId()));
						}else if(type.equals("2")){// 缴款冲正
							RechargeInfo rechargeInfo = rechargeInfoDao.findById(accountBussiness.getBussinessId());
							map.put("rechargeInfo",rechargeInfo);
							if(rechargeInfo.getBankTransferId()!=null){
								map.put("bankTransferInfo",bankTransferInfoDao.findBytId(rechargeInfo.getBankTransferId()));
							}
						}else if(type.equals("3")){// 缴款修改
							map.put("rechargeInfo", rechargeInfoDao.findById(accountBussiness.getBussinessId()));
							map.put("mainAccountHis", mainAccountInfoHisDao.findByHisId(accountBussiness.getHisSeqId()));
						}else if(type.equals("4")){// 转账缴款
							map.put("rechargeInfo", rechargeInfoDao.findById(accountBussiness.getBussinessId()));
							map.put("bankTransferInfoHis", bankTransferInfoHisDao.findById(accountBussiness.getHisSeqId()));
						}else if(type.equals("5")){// 账户退款
							map.put("refundInfo", refundInfoDao.findById(accountBussiness.getBussinessId()));
						}else if(type.equals("6")){// 退款撤销
							map.put("refundInfoHis", refundInfoHisDao.findById(accountBussiness.getBussinessId()));
						}
						datas.add(map);
					}
					
					
					
					
					
					
					
				}else if(receipt.getParentTypeCode().equals("2")){//储值卡
					prepaidCBussiness=prepaidCBussinessDao.findById(receipt.getBusinessId());
					if(prepaidCBussiness!=null||type.equals("18")){//过户时，没有记录bussinessId
						map=new HashMap<String,Object>();
						map.put("prepaidCBussiness",prepaidCBussiness);
						map.put("receipt", receipt);
						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						map.put("mainAccountInfo", receiptDao.findMainAccByReceipt(receipt).get("mainAccountInfo"));
						if(receipt.getSeritem()!=null){
							map.put("billGet", receipt.getSeritem().split(","));
						}
						if(type.equals(PrepaidCardBussinessTypeEnum.preCardIssuce.getValue())){// 1 发行
							map.put("prepaidC",prepaidCDao.findByCardNoToGain(prepaidCBussiness.getCardno()));
							map.put("vehicleInfo", receiptDao.findVehByReceipt(receipt).get("vehicleInfo"));
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue())){//2 人工充值
							
							
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue())){//3 充值登记充值
							
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue())){//4充值冲正
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardPasswordModify.getValue())||type.equals(PrepaidCardBussinessTypeEnum.preCardPasswordReset.getValue())){//5消费密码修改  6 消费密码重设
							
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardLoss.getValue())||
								type.equals(PrepaidCardBussinessTypeEnum.preCardUnLoss.getValue())||
								type.equals(PrepaidCardBussinessTypeEnum.preCardReplaceMent.getValue())){//7挂失 8解挂 //9 补领
							map.put("actionTime",FieldUtil.getActionTime(prepaidCBussiness.getTradetime()) );//挂失或者解挂生效时间
							map.put("prepaidC",prepaidCDao.findByCardNoToGain(prepaidCBussiness.getCardno()));
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardGetNewCard.getValue())){//11 换卡
							if(prepaidCBussiness.getNotCardState().equals("1")){//有卡换卡
								map.put("oldCardBalance",prepaidCBussinessDao.findByCardNoAndState(prepaidCBussiness.getOldCardno(), "10").getBalance());
							}
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardCannel.getValue())||
								type.equals(PrepaidCardBussinessTypeEnum.preCardNoCardCannel.getValue())){//12有卡终止使用      13无卡终止使用
						
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardInvoiceTypeChange.getValue())){//17发票类型变更
							PrepaidC prepaidC=prepaidCDao.findByHisId(prepaidCBussiness.getBusinessId());//发票变更业务记录保存的是hisid
							if(prepaidC!=null){
								map.put("newPrepaidc", prepaidC);
								
							}else{
								map.put("newPrepaidc", prepaidCDao.findFromHisByHisId(prepaidC.getHisSeqID()));
							}
							map.put("oldPrepaidc", prepaidCDao.findFromHisById(prepaidC.getHisSeqID()));
							
							
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardTranfer.getValue())){//18过户
							//同样需要历史表：根据卡号查出新旧客户信息
							PrepaidCTransfer prepaidCTransfer=prepaidCDao.findTransferId(receipt.getBusinessId());
							PrepaidCBussiness prepaidCBussiness1=new PrepaidCBussiness();
							prepaidCBussiness1.setState("18");
							map.put("prepaidCBussiness",prepaidCBussiness1);
							Customer newCustomer=customerDao.findByHisId(prepaidCTransfer.getNewCusHisId());
							if(newCustomer!=null){
								map.put("newCustomer",newCustomer);
								}else{
								map.put("newCustomer", customerHisDao.findByHisId(prepaidCTransfer.getNewCusHisId()));
							}
							map.put("transferDetails", transferDetailDao.findPreCardByTransferID(prepaidCTransfer.getId()));
							map.put("prepaidCTransfer", prepaidCTransfer);
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardImRegister.getValue())){//19快速充值
							
							
						}else if(type.equals(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue())){//20 快速充值冲正
						}
						datas.add(map);
					}
					
					
					
					
					
					
					
					
					
				}else if(receipt.getParentTypeCode().equals("3")){//记帐卡
					accountCBussiness=accountCBussinessDao.findById(receipt.getBusinessId());
					if(accountCBussiness!=null){
						map=new HashMap<String,Object>();
						map.put("accountCBussiness",accountCBussiness);
						map.put("receipt", receipt);
						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
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
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							//BillGet billGet = null;
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
								//billGet = billGetDao.findByCardBankNo(accountCApply.getBankAccount());
							}else{
								AccountCapplyHis accountCapplyHis = accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
								map.put("accountCApply", accountCapplyHis);
								//billGet = billGetDao.findByCardBankNo(accountCapplyHis.getBankAccount());
							}
							AccountCInfo accountCInfo=accountCInfoDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountCInfo!=null){
								map.put("accountCInfo", accountCInfo);
							}else{
								map.put("accountCInfo", accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId()));
							}
							map.put("vehicleInfo", receiptDao.findVehByReceipt(receipt).get("vehicleInfo"));
							
							/*String[] serItems = billGet.getSerItem().split(",");
							for(int j=0;j<serItems.length;j++){
								serItems[j] = serItems[j].trim();
							}
							map.put("serItems", serItems);*/
						}else if(type.equals(AccountCBussinessTypeEnum.accCardBaidAdd.getValue())||type.equals(AccountCBussinessTypeEnum.accCardBailBack.getValue())){// 2保证金新增,25保证金退换
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
							Bail bail=bailDao.findById(accountCBussiness.getBusinessId());
							map.put("bail", bail);
						}else if(type.equals(AccountCBussinessTypeEnum.accCardLoss.getValue())||type.equals(AccountCBussinessTypeEnum.accCardCanceLoss.getValue())){// 3挂失 4解挂
							map.put("actionTime",FieldUtil.getActionTime(accountCBussiness.getTradeTime()) );//挂失或者解挂生效时间
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardReplace.getValue())){// 5补领
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
								map.put("accountCInfo", accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId()));
							}
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardLock.getValue())){// 6旧卡锁定
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardReplaceNewcard.getValue())){// 7补领新卡
							AccountCInfo accountCInfo=accountCInfoDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountCInfo!=null){
								map.put("accountCInfo", accountCInfo);
							}else{
								map.put("accountCInfo", accountCInfoHisDao.findByHisId(accountCBussiness.getBusinessId()));
							}
							
							//有卡换卡/无卡换卡
							AccountCBussiness oldAccountCBussiness = accountCBussinessDao.findLockByCno(accountCBussiness.getOldCardNo());
							map.put("oldAccountCBussiness", oldAccountCBussiness);
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStopWithCard.getValue())||type.equals(AccountCBussinessTypeEnum.accCardStopNotCard.getValue())){// 8有卡终止使用  9无卡终止使用
							//不用记录
						}else if(type.equals(AccountCBussinessTypeEnum.accCardApplyCancelStopPay.getValue())){// 10申请解除止付
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardHandCancelStopPay.getValue())){// 11手工解除止付黑名单
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardMemberRechargeTollFee.getValue())){// 12手工缴纳通行费
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardEdPassWord.getValue())||type.equals(AccountCBussinessTypeEnum.accCardNewPassword.getValue())){// 13消费密码修改  14消费密码重设
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							//AccountCApply accountCApply=accountCApplyDao.findByCardNo(accountCBussiness.getCardNo());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStop.getValue())){// 15停用
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardStart.getValue())){// 16启用
							
						}else if(type.equals(AccountCBussinessTypeEnum.accNameCha.getValue())){// 17账户名称变更
							AccountCApply accountCApply=accountCApplyDao.findByHisId(accountCBussiness.getAccountCApplyHisID());
							if(accountCApply!=null){
								map.put("accountCApply", accountCApply);
							}else{
								map.put("accountCApply", accountCApplyHisDao.findByHisId(accountCBussiness.getAccountCApplyHisID()));
							}
							AccountNCApply accountNCApply = accountNCApplyDao.findByHisId(accountCBussiness.getBusinessId());
							if(accountNCApply!=null){
								map.put("accountNCApply", accountNCApply);
							}else{
								map.put("accountNCApply", accountNCApplyHisDao.findByHisId(accountCBussiness.getBusinessId()));
							}
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardMigrate.getValue())){// 18迁移
							Migrate migrate=migrateDao.findById(accountCBussiness.getBusinessId());
							map.put("newAccountCApply", accountCApplyDao.findBySubAccId(migrate.getNewAccountId()));
							map.put("oldAccountCApply", accountCApplyDao.findBySubAccId(migrate.getOldAccountId()));
							MigrateDetail migrateDetail=new MigrateDetail();
							migrateDetail.setMigrateId(migrate.getId());
							map.put("accCardNos", migrateDetailDao.find(migrateDetail));
							
							
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardTransfer.getValue())){// 19过户
							TransferApply transferApply=transferApplyDao.findById(accountCBussiness.getBusinessId());
							map.put("newTransferApply", accountCApplyDao.findBySubAccId(transferApply.getNewaccountId()));
							map.put("oldTransferApply", accountCApplyDao.findBySubAccId(transferApply.getOldaccountId()));
							map.put("accCardNos", transferDetailDao.findCardByTransferID(transferApply.getId()));
							AccountCApply accountCApply = accountCApplyDao.findBySubAccId(transferApply.getNewaccountId());
							map.put("newCustomer", customerDao.findById(accountCApply.getCustomerId()));
							
						}else if(type.equals(AccountCBussinessTypeEnum.accCardInitialApplyAudit.getValue())){// 22初次申请审核
							
						}else if(type.equals(AccountCBussinessTypeEnum.accNewCardApplyAudit.getValue())){// 23新增卡申请审核
							
						}else if(type.equals(AccountCBussinessTypeEnum.accNameChange.getValue())){// 24账户名称变更
							
						}
						datas.add(map);
					}
					
					
					
					
					
					
					
					
					
					
				}else if(receipt.getParentTypeCode().equals("7")){//7代表车辆信息业务
					vehicleBussiness=vehicleBussinessDao.findById(receipt.getBusinessId());
					if(null !=vehicleBussiness){
						map=new HashMap<String,Object>();
						map.put("vehicleBussiness", vehicleBussiness);
						map.put("receipt", receipt);
						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						if(type.equals(VehicleBussinessEnum.accountCAbled.getValue())){
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("accountCInfo", accountCInfoDao.findByCardNo(vehicleBussiness.getCardNo()));
						}else if(type.equals(VehicleBussinessEnum.accountCDisabledWithCard.getValue()) || type.equals(VehicleBussinessEnum.accountCDisabledWithoutCard.getValue())){
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("accountCInfo", accountCInfoDao.findByCardNo(vehicleBussiness.getCardNo()));
						}else if(type.equals(VehicleBussinessEnum.prepaidCAbled.getValue())){ 
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("prepaidC", prepaidCDao.findByPrepaidCNo(vehicleBussiness.getCardNo()));
						}else if(type.equals(VehicleBussinessEnum.prepaidCDisabledWithCard.getValue()) || type.equals(VehicleBussinessEnum.prepaidCDisabledWithoutCard.getValue())){ 
							map.put("vehicleInfo", vehicleInfoDao.loadByPlateAndColor(vehicleBussiness.getVehiclePlate(),vehicleBussiness.getVehicleColor()));
							map.put("prepaidC", prepaidCDao.findByPrepaidCNo(vehicleBussiness.getCardNo()));
						}
						datas.add(map);
					}
					
				}
				
				
				
				
				
				
				else if(receipt.getParentTypeCode().equals("4")){//电子标签
					tagBusinessRecord=tagBusinessRecordDao.findById(receipt.getBusinessId());
					//if(tagBusinessRecord!=null){
						map=new HashMap<String,Object>();
						map.put("tagBusinessRecord",tagBusinessRecord);
						map.put("receipt", receipt);
						map.put("vehicleInfo", receiptDao.findVehByReceipt(receipt).get("vehicleInfo"));
						map.put("customer", receiptDao.findCusByReceipt(receipt).get("customer"));
						map.put("mainAccountInfo", receiptDao.findMainAccByReceipt(receipt).get("mainAccountInfo"));
						if(type.equals(TagBussinessTypeEnum.tagIssuce.getValue())){//1发行
							TagInfo tagInfo=tagInfoDao.findById(tagBusinessRecord.getBussinessid());
							if(tagInfo!=null){
								map.put("tagInfo", tagInfoDao.findById(tagBusinessRecord.getBussinessid()));
							}else{
								map.put("tagInfo", tagInfoDao.findHisByHisId(tagBusinessRecord.getBussinessid()));
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
								map.put("oldCustomer", customerDao.findById(oldVehicleInfo.getCustomerID()));
							}else{
								VehicleInfoHis oldVehicleInfo1 = vehicleInfoHisDao.findByHisId(tagBusinessRecord.getBussinessid());
								map.put("oldVehicleInfo", oldVehicleInfo1);
								map.put("oldCustomer", customerDao.findById(oldVehicleInfo1.getCustomerID()));
							}
							
						}else if(type.equals(TagBussinessTypeEnum.tagCannel.getValue())){//7注销
							VehicleInfo vehicleInfo=vehicleInfoDao.findByHisId(tagBusinessRecord.getBussinessid());//注销的时候记录的是车辆历史id
							if(vehicleInfo!=null){
								map.put("vehicleInfo", vehicleInfo);
							}else{
								map.put("vehicleInfo", vehicleInfoHisDao.findByHisId(tagBusinessRecord.getBussinessid()));
							}
						}else if(type.equals(TagBussinessTypeEnum.tagtakefeeinfoAdd.getValue())){//20提货金额登记新增
							TagTakeFeeInfo tagTakeFeeInfo=tagTakeFeeInfoDao.findByReceiptId(receipt.getId());
//							TagTakeFeeInfo tagTakeFeeInfo=tagTakeFeeInfoDao.findByHisId(receipt.getBusinessId());
//							if(tagTakeFeeInfo!=null){
//								map.put("tagTakeFeeInfo", tagTakeFeeInfo);
//							}else{
//								map.put("tagTakeFeeInfo", tagTakeFeeInfoDao.findFromHisByHisId(receipt.getBusinessId()));
//							}
							map.put("tagTakeFeeInfo",tagTakeFeeInfo);
							map.put("tagBusinessRecord",null);
						}else if(type.equals(TagBussinessTypeEnum.tagTake.getValue())){//14标签提货新增
//							TagTakeFeeInfo tagTakeFeeInfo=tagTakeFeeInfoDao.findByHisId(receipt.getBusinessId());
//							if(tagTakeFeeInfo!=null){
//								map.put("tagTakeFeeInfo", tagTakeFeeInfo);
//								map.put("customer", customerDao.findByIdNo(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber()));
//							}else{
//								tagTakeFeeInfo = tagTakeFeeInfoDao.findFromHisByHisId(receipt.getBusinessId());
//								map.put("tagTakeFeeInfo", tagTakeFeeInfoDao.findFromHisByHisId(receipt.getBusinessId()));
//								map.put("customer", customerDao.findByIdNo(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber()));
//							}
							TagTakeInfo tagTakeInfo = tagTakeInfoDao.findByReceiptId(receipt.getId());
							TagTakeFeeInfo tagTakeFeeInfo=tagTakeFeeInfoDao.findById(tagTakeInfo.getTagtakeid());
							map.put("tagTakeInfo", tagTakeInfo);
							map.put("tagTakeFeeInfo", tagTakeFeeInfo);
							map.put("tagBusinessRecord",null);
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

	@Override
	public Pager findPrintReceiptsByPager(Pager pager, Date starTime, Date endTime, Customer customer,String bussinessType, String alreadyPrint) {
		Map<String,String> params = new HashMap<String,String>() ;
		params.put("beginTime", DateUtil.formatDate(starTime,"yyyy-MM-dd HH:mm:ss"));
		params.put("endTime",DateUtil.formatDate(endTime,"yyyy-MM-dd HH:mm:ss"));
		params.put("placeId",customer.getPlaceId()+"");
		params.put("alreadyPrint",alreadyPrint);
		if(StringUtil.isNotBlank(bussinessType)) {
			params.put("parentTypeCode", bussinessType.substring(0,1));
			params.put("typeCode",bussinessType.substring(1));
		}
		return receiptDao.findPrintReceiptsByPager(pager, params);
	}

}
