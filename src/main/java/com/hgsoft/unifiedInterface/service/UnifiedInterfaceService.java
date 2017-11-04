package com.hgsoft.unifiedInterface.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.dao.BailAccountInfoHisDao;
import com.hgsoft.account.dao.BankTransferBussinessDao;
import com.hgsoft.account.dao.BankTransferInfoDao;
import com.hgsoft.account.dao.BankTransferInfoHisDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoHisDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoHisDao;
import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.BailAccountInfoHis;
import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.BankTransferInfoHis;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.MainAccountInfoHis;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCApplyHisDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.NewCardApplyDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.entity.NewCardVehicle;
import com.hgsoft.common.Enum.AccChangeTypeEnum;
import com.hgsoft.common.Enum.AccountCardStateEnum;
import com.hgsoft.common.Enum.BankTransferRechargeTypeEnum;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.PrepaidCardStateEnum;
import com.hgsoft.common.Enum.RefundTypeEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ExceptionCustomerDataDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.SequenceUtil;
/**
 * 业务接口类
 * @author gaosiling
 * 2016年1月21日14:08:36 
 */
@Service
public class UnifiedInterfaceService implements IUnifiedInterface{
	
	private static Logger logger = Logger.getLogger(UnifiedInterfaceService.class.getName());
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private MainAccountInfoHisDao mainAccountInfoDaoHis;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;
	@Resource
	private BankTransferInfoDao bankTransferInfoDao;
	@Resource
	private BankTransferInfoHisDao bankTransferInfoHisDao;
	@Resource
	private BillGetDao billGetDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	@Resource
	private BailAccountInfoHisDao bailAccountInfoHisDao;
	@Resource
	private NewCardApplyDao newCardApplyDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private AccountCApplyHisDao accountCApplyHisDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private AccountFundChangeDao accountFundChangeDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private BankTransferBussinessDao bankTransferBussinessDao;
	@Resource
	private ExceptionCustomerDataDao exceptionCustomerDataDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private SubAccountInfoHisDao subAccountInfoHisDao;
	
	/**
	 * 账户余额更新接口
	 * @param  MainAccountInfo 账户信息、缴款对象
	 * @param  newRechargeInfo 新增缴款记录
	 * @param  oldRechargeInfo 冲正缴款记录
	 * @param  type 0冲正 1 缴款 2修改 3转账缴款 4退款 5转账撤销 6电子标签发行扣费 7电子标签更换扣费 8电子标签删除工本费返回9领取新卡扣减储值卡卡费10充值登记11储值卡发行12删除储值卡发行 
	 * 				13充值14充值成功15充值冲正16领取新卡扣减记帐卡卡费17保证金新增18、充值冲正成功19手工缴纳通行费20手动解除止付扣除滞纳金、
	 * 				21记帐卡发行、22补领卡扣减储值卡卡费23、补领卡扣减记帐卡卡费  24、日结前资金修正25快速充值半条确认成功 26快速充值半条确认失败
	 * 				27人工充值半条确认成功28人工充值半条确认失败29充值冲正半条确认成功30充值冲正半条确认失败 31撤销充值登记32保证金退还申请 33保证金退还撤销
	 * 				34营运中心审核35财务退款完成36财务退款失败46储值卡终止使用47特殊费用收取48追款管理收取49储值卡终止使用首次退款申请50客户账户合并
	 * 				51充值登记记录删除     52保证金流入主账户可用余额（写在营运） 53保证金退款营运审批失败
	 * @throws ApplicationException
	 * @author gaosiling
	 */
	public  boolean saveAccAvailableBalance(UnifiedParam unifiedParam){
		
		try {
			BigDecimal addCount = unifiedParam.getAddCount();
			String type = unifiedParam.getType();
			//old主账户(更新了操作网点等信息)
			MainAccountInfo mainAccountInfo =unifiedParam.getMainAccountInfo();
			//old主账户
			MainAccountInfo newMainAccountInfo  = mainAccountInfoDao.findByMainIdAndBalance(mainAccountInfo.getMainId());
			if(newMainAccountInfo==null){
				return false;
			}
			
			//主账户信息移入历史表
			MainAccountInfoHis mainAccountInfoHis = new MainAccountInfoHis();
			BigDecimal SEQ_CSMSMAINACCOUNTINFOHIS_NO = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
			mainAccountInfoHis.setId(Long.valueOf(SEQ_CSMSMAINACCOUNTINFOHIS_NO.toString()));
			mainAccountInfoHis.setHisSeqId(newMainAccountInfo.getHisSeqId());
			newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
			
			if(AccChangeTypeEnum.correct.getValue().equals(type)){//0冲正
				RechargeInfo newRechargeInfo = unifiedParam.getNewRechargeInfo();
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				mainAccountInfoHis.setCreateReason("冲正修改可用余额");

				/*if("4".equals(newRechargeInfo.getPayMentType())){//4:可退余额返还   冲正
					if(newMainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()).compareTo(new BigDecimal("0"))==-1){
						return false;
					}

					//将当前退款记录移入历史表
					RefundInfo refundInfo = refundInfoDao.findById(newRechargeInfo.getRefundID());
					RefundInfoHis refundInfoHis = new RefundInfoHis();
					BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
					refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
					refundInfoHis.setCreateReason("当前退款方式为账户缴款的缴款记录被冲正后将当前退款记录至于撤销");
					
					//更新退款记录
					refundInfo.setHisSeqId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
					refundInfo.setAuditStatus("6");
					
					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal(""+newRechargeInfo.getTakeBalance()));
					newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
					newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					
					if(unifiedParam.getFlag()!=null){
						accountFundChange.setChangeType(AccChangeTypeEnum.imCorrect.getValue());
					}else{
						accountFundChange.setChangeType(AccChangeTypeEnum.correct.getValue());
					}
					accountFundChange.setMemo("冲正修改可用余额");
					
					accountFundChange.setCurrAvailableBalance(newRechargeInfo.getTakeBalance());
					accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));//缴款金额
					accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
					
					accountFundChange.setCurrAvailableRefundBalance(newRechargeInfo.getTakeBalance());
					accountFundChange.setAfterAvailableRefundBalance(new BigDecimal(""+newRechargeInfo.getTakeBalance()));
					accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal(""+newRechargeInfo.getTakeBalance()));
					
					accountFundChange.setChgOperID(unifiedParam.getOperId());
					accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
					accountFundChange.setOperName(unifiedParam.getOperName());
					accountFundChange.setOperNo(unifiedParam.getOperNo());
					accountFundChange.setPlaceName(unifiedParam.getPlaceName());
					accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
					
					//mainAccountInfoDao.update(newMainAccountInfo);
					if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
						return false;
					}
					mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
					refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
					refundInfoDao.update(refundInfo);
					accountFundChangeDao.saveChange(accountFundChange);
					return true;
				}else*/
				if("1".equals(newRechargeInfo.getPayMentType())
						|| "2".equals(newRechargeInfo.getPayMentType())
						|| "4".equals(newRechargeInfo.getPayMentType())
						|| "5".equals(newRechargeInfo.getPayMentType())) {//1:现金，2:POS,4:支付宝，5：微信  冲正
					if(newMainAccountInfo.getAvailableBalance().compareTo(newRechargeInfo.getTakeBalance()) < 0){
						return false;
					}
					
					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					newMainAccountInfo.setAvailableBalance(newRechargeInfo.getTakeBalance().negate());
					newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
					newMainAccountInfo.setFrozenBalance(BigDecimal.ZERO);
					newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
					newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);
					
					accountFundChange.setCurrAvailableBalance(newRechargeInfo.getTakeBalance().negate());
					accountFundChange.setAfterAvailableBalance(newRechargeInfo.getTakeBalance().negate());
					accountFundChange.setBeforeAvailableBalance(newRechargeInfo.getTakeBalance().negate());

					if (unifiedParam.getFlag()!=null){
						accountFundChange.setChangeType(AccChangeTypeEnum.imCorrect.getValue());
						accountFundChange.setMemo(AccChangeTypeEnum.imCorrect.getName());

					}else{
						accountFundChange.setChangeType(AccChangeTypeEnum.correct.getValue());
						accountFundChange.setMemo(AccChangeTypeEnum.correct.getName());
					}

					accountFundChange.setChgOperID(unifiedParam.getOperId());
					accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
					accountFundChange.setOperName(unifiedParam.getOperName());
					accountFundChange.setOperNo(unifiedParam.getOperNo());
					accountFundChange.setPlaceName(unifiedParam.getPlaceName());
					accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
					
					/*accountFundChange=getAfterAccountFundChange(newMainAccountInfo, accountFundChange);*/
					int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
					if(ret == 0){
						return false;
					} else if (ret != 1) {
						throw new ApplicationException("账户信息数据异常，请检查");
					}
					//mainAccountInfoDao.update(newMainAccountInfo);
					mainAccountInfoDaoHis.saveHis(mainAccountInfoHis, newMainAccountInfo);
					accountFundChangeDao.saveChange(accountFundChange);
					return true;
				}else if("3".equals(newRechargeInfo.getPayMentType())){//3:转账缴款冲正
					if(newMainAccountInfo.getAvailableBalance().subtract(newRechargeInfo.getTakeBalance()).compareTo(new BigDecimal("0"))==-1){
						return false;
					}
					
					//2017/06/05 保存银行到账缴款业务记录表
					Customer tmpCustomer = customerDao.findById(newRechargeInfo.getMainId());
					BankTransferInfo tmpBankTransferInfo = bankTransferInfoDao.findBytId(newRechargeInfo.getBankTransferId());
					BankTransferBussiness bankTransferBussiness = new BankTransferBussiness();
					bankTransferBussiness.setIdType(tmpCustomer.getIdType());
					bankTransferBussiness.setIdCode(tmpCustomer.getIdCode());
					bankTransferBussiness.setClientName(tmpCustomer.getOrgan());
					bankTransferBussiness.setBankTransferId(tmpBankTransferInfo.getId());
					bankTransferBussiness.setPayName(tmpBankTransferInfo.getPayName());
					bankTransferBussiness.setBankNo(tmpBankTransferInfo.getBankNo());
					bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.accountTransferDelete.getValue());
					bankTransferBussiness.setTransferBlanace(tmpBankTransferInfo.getTransferBlanace());//银行到账金额
					bankTransferBussiness.setBlanace(tmpBankTransferInfo.getBlanace().add(newRechargeInfo.getTakeBalance()));//缴款后余额
					bankTransferBussiness.setRechargeCost(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));//缴款金额
					bankTransferBussiness.setOperId(newRechargeInfo.getOperId());
					bankTransferBussiness.setOperDate(new Date());
					bankTransferBussiness.setPlaceId(newRechargeInfo.getPlaceId());
					bankTransferBussiness.setOperName(newRechargeInfo.getOperName());
					bankTransferBussiness.setOperNo(newRechargeInfo.getOperNo());
					bankTransferBussiness.setPlaceName(newRechargeInfo.getPlaceName());
					bankTransferBussiness.setPlaceNo(newRechargeInfo.getPlaceNo());
					bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
					
					
					//转账账户缴款冲正
					BankTransferInfo bankTransferInfo = new BankTransferInfo();
					bankTransferInfo.setId(newRechargeInfo.getBankTransferId());
					
					//将当前银行账户信息移入历史表
					BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
					BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
					bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
					bankTransferInfoHis.setCreateReason("转账账户缴款冲正余额更新");
					//更新银行账户信息余额
					bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
					bankTransferInfo.setBlanace(newRechargeInfo.getTakeBalance());

					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
					newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
					newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
					
					accountFundChange.setChangeType("0");
					accountFundChange.setMemo("冲正修改可用余额");
					accountFundChange.setCurrAvailableBalance(newRechargeInfo.getTakeBalance());
					accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
					accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
					accountFundChange.setChgOperID(unifiedParam.getOperId());
					accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
					accountFundChange.setOperName(unifiedParam.getOperName());
					accountFundChange.setOperNo(unifiedParam.getOperNo());
					accountFundChange.setPlaceName(unifiedParam.getPlaceName());
					accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
					
					//mainAccountInfoDao.update(newMainAccountInfo);
					if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
						return false;
					}
					mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
					bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
					bankTransferInfoDao.update(bankTransferInfo);
					accountFundChangeDao.saveChange(accountFundChange);
					return true;
				}
				
			}else if(AccChangeTypeEnum.recharge.getValue().equals(type)){// 1 账户缴款 
				RechargeInfo newRechargeInfo = unifiedParam.getNewRechargeInfo();
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("缴款修改可用余额");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(newRechargeInfo.getTakeBalance());
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				if(unifiedParam.getFlag()!=null){
					accountFundChange.setChangeType("37");
					accountFundChange.setMemo(AccChangeTypeEnum.imRecharge.getName());
				}else{
					accountFundChange.setChangeType("1");
					accountFundChange.setMemo(AccChangeTypeEnum.recharge.getName());
				}
				//accountFundChange.setMemo("缴款修改可用余额");
				accountFundChange.setCurrAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setAfterAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setBeforeAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis, newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.modify.getValue().equals(type)){// 2修改
				RechargeInfo newRechargeInfo = unifiedParam.getNewRechargeInfo();
				RechargeInfo oldRechargeInfo = unifiedParam.getOldRechargeInfo();
				BigDecimal newAmt = newRechargeInfo.getTakeBalance().subtract(oldRechargeInfo.getTakeBalance());
				if(newMainAccountInfo.getAvailableBalance().add(newAmt).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				mainAccountInfoHis.setCreateReason("修改缴款余额后修改可用余额");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(newAmt);
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				
				serviceFlowRecord.setAfterAvailableBalance(newAmt);
				serviceFlowRecord.setCurrAvailableBalance(newAmt.abs());
				serviceFlowRecord.setBeforeAvailableBalance(newAmt);
				
				accountFundChange.setAfterAvailableBalance(newAmt);
				accountFundChange.setCurrAvailableBalance(newAmt.abs());
				accountFundChange.setBeforeAvailableBalance(newAmt);
				
				if(newAmt.compareTo(new BigDecimal("0"))==-1){
					newAmt = oldRechargeInfo.getTakeBalance().subtract(newRechargeInfo.getTakeBalance());
				}
				
				serviceFlowRecord.setServicePTypeCode(4);
				serviceFlowRecord.setServiceTypeCode(3);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				
				accountFundChange.setChangeType("2");
				accountFundChange.setMemo("修改缴款余额后修改可用余额");
				
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.tranferRecharge.getValue().equals(type)){//3转账缴款
				RechargeInfo newRechargeInfo = unifiedParam.getNewRechargeInfo();
				BankTransferInfo bankTransferInfo = unifiedParam.getBankTransferInfo();
				bankTransferInfo = bankTransferInfoDao.findBytId(bankTransferInfo.getId());
				
				if(bankTransferInfo.getBlanace().subtract(newRechargeInfo.getTakeBalance()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("转账缴款修改可用余额");
				
				//将当前银行账户信息移入历史表
				BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
				BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
				bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
				bankTransferInfoHis.setCreateReason("转账缴款后余额更新");
				//更新银行账户信息余额
				bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
				bankTransferInfo.setBlanace(new BigDecimal("-"+newRechargeInfo.getTakeBalance()));
				
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(newRechargeInfo.getTakeBalance());
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setAfterAvailableBalance(newRechargeInfo.getTakeBalance());
				serviceFlowRecord.setCurrAvailableBalance(newRechargeInfo.getTakeBalance());
				serviceFlowRecord.setBeforeAvailableBalance(newRechargeInfo.getTakeBalance());
				serviceFlowRecord.setServicePTypeCode(4);
				serviceFlowRecord.setServiceTypeCode(1);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				
				accountFundChange.setChangeType("3");
				accountFundChange.setMemo("转账缴款修改可用余额");
				accountFundChange.setCurrAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setAfterAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setBeforeAvailableBalance(newRechargeInfo.getTakeBalance());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
				bankTransferInfoDao.update(bankTransferInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.refund.getValue().equals(type)){// 4退款申请
				RefundInfo refundInfo = unifiedParam.getRefundInfo();
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("退款后账户修改");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				
				if("1".equals(refundInfo.getRefundType())){//退款类型为银行转账 已废弃
					
					newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					newMainAccountInfo.setRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					
					serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("0"));
					
					serviceFlowRecord.setAfterRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setCurrRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setBeforeRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					
					serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					
					
					accountFundChange.setAfterAvailableBalance(new BigDecimal("0"));
					
					
					accountFundChange.setCurrRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setAfterRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setBeforeRefundApproveBalance(refundInfo.getCurrentRefundBalance());
					
					accountFundChange.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					
				}else if("2".equals(refundInfo.getRefundType())){//退款类型为账户转账，要插主账户交款记录表  已废弃
					
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
					
					serviceFlowRecord.setAfterAvailableBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setBeforeAvailableBalance(refundInfo.getCurrentRefundBalance());
					
					serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setBeforeAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					
					accountFundChange.setAfterAvailableBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableBalance(refundInfo.getCurrentRefundBalance());
					
					accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				}else if("3".equals(refundInfo.getRefundType())){//账户退款，从账户余额到可退余额
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
					
					serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					
					serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					serviceFlowRecord.setBeforeAvailableRefundBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					serviceFlowRecord.setOperName(unifiedParam.getOperName());
					serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
					serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
					serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
					serviceFlowRecord.setOperID(unifiedParam.getOperId());
					serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
					
					
					accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
					
					accountFundChange.setAfterAvailableRefundBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal(""+refundInfo.getCurrentRefundBalance()));
					accountFundChange.setOperName(unifiedParam.getOperName());
					accountFundChange.setOperNo(unifiedParam.getOperNo());
					accountFundChange.setPlaceName(unifiedParam.getPlaceName());
					accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
					accountFundChange.setChgOperID(unifiedParam.getOperId());
					accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				}else{
					return false;
				}
				
				/*serviceFlowRecord.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());*/
				serviceFlowRecord.setServicePTypeCode(4);
				serviceFlowRecord.setServiceTypeCode(4);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setChangeType("4");
				accountFundChange.setMemo("退款后账户修改");
				/*accountFundChange.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());*/
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.refundRevoke.getValue().equals(type)){// 5转账撤销   账户转账方式退款没有撤销，只有银行转账方式退款才有撤销
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("转账退款回撤");
			
				RefundInfo refundInfo = unifiedParam.getRefundInfo();
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				/*newMainAccountInfo.setAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));*/
				
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				newMainAccountInfo.setAvailableBalance(refundInfo.getCurrentRefundBalance());
				
				serviceFlowRecord.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				serviceFlowRecord.setBeforeAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				
				serviceFlowRecord.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
				serviceFlowRecord.setAfterAvailableBalance(refundInfo.getCurrentRefundBalance());
				serviceFlowRecord.setBeforeAvailableBalance(refundInfo.getCurrentRefundBalance());
				
				/*serviceFlowRecord.setCurrRefundApproveBalance(refundInfo.getCurrentRefundBalance());
				serviceFlowRecord.setAfterRefundApproveBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				serviceFlowRecord.setBeforeRefundApproveBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));*/
				
				serviceFlowRecord.setServicePTypeCode(4);
				serviceFlowRecord.setServiceTypeCode(5);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				
				accountFundChange.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				
				accountFundChange.setCurrAvailableBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setAfterAvailableBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setBeforeAvailableBalance(refundInfo.getCurrentRefundBalance());
				
				/*accountFundChange.setCurrAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setAfterAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setBeforeAvailableRefundBalance(refundInfo.getCurrentRefundBalance());
				
				accountFundChange.setCurrRefundApproveBalance(refundInfo.getCurrentRefundBalance());
				accountFundChange.setAfterRefundApproveBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));
				accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("-"+refundInfo.getCurrentRefundBalance()));*/
				
				
				
				accountFundChange.setChangeType("5");
				accountFundChange.setMemo("转账退款回撤");
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
				
			}else if(AccChangeTypeEnum.tagIssue.getValue().equals(type)){
				TagInfo tagInfo = unifiedParam.getTagInfo();
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(newMainAccountInfo.getAvailableBalance().subtract(tagInfo.getChargeCost()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				
				mainAccountInfoHis.setCreateReason("电子标签发行工本费扣费修改");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				accountFundChange.setCurrAvailableBalance(tagInfo.getChargeCost());
				accountFundChange.setChangeType("6");
				accountFundChange.setMemo("电子标签发行工本费扣费修改");
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(tagInfo.getOperName());
				accountFundChange.setOperNo(tagInfo.getOperNo());
				accountFundChange.setPlaceName(tagInfo.getPlaceName());
				accountFundChange.setPlaceNo(tagInfo.getPlaceNo());
				accountFundChange.setChgOperID(tagInfo.getOperID());
				accountFundChange.setChgPlaceID(tagInfo.getIssueplaceID());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.tagChange.getValue().equals(type)){
				TagInfo tagInfo = unifiedParam.getTagInfo();
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				
				if(tagInfo.getChargeCost() != null){
					if(newMainAccountInfo.getAvailableBalance().subtract(tagInfo.getChargeCost()).compareTo(new BigDecimal("0"))==-1){
						return false;
					}
				}
				
				mainAccountInfoHis.setCreateReason("电子标签更换工本费扣费修改");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				/*mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);*/
				if(tagInfo.getChargeCost() != null){
					newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				}else{
					newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
				}
				//System.out.println("tagInfo.getChargeCost()"+tagInfo.getChargeCost());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				accountFundChange.setCurrAvailableBalance(tagInfo.getChargeCost());
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+tagInfo.getChargeCost()));
				accountFundChange.setChangeType("7");
				accountFundChange.setMemo("电子标签更换工本费扣费修改");
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
				
			}else if(AccChangeTypeEnum.tagDelete.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("电子标签删除工本费返回");
			
				
				TagInfo tagInfo = unifiedParam.getTagInfo();
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(tagInfo.getChargeCost());
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setAfterAvailableBalance(tagInfo.getChargeCost());
				accountFundChange.setCurrAvailableBalance(tagInfo.getChargeCost());
				accountFundChange.setBeforeAvailableBalance(tagInfo.getChargeCost());
				accountFundChange.setChangeType("8");
				accountFundChange.setMemo("电子标签删除工本费返回");
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.preCardGetNewCard.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getNewPrepaidC().getRealCost()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("领取新卡扣减储值卡卡费");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				accountFundChange.setCurrAvailableBalance(unifiedParam.getNewPrepaidC().getRealCost());
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				accountFundChange.setChangeType("9");
				accountFundChange.setMemo("领取新卡扣减储值卡卡费");
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				//mainAccountInfoDao.update(newMainAccountInfo);
				
				
			}else if(AccChangeTypeEnum.rechargeRegister.getValue().equals(type)){
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(newMainAccountInfo.getAvailableBalance().subtract(addCount).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("充值登记");
				
				serviceFlowRecord.setCurrAvailableBalance(new BigDecimal(""+addCount));
				serviceFlowRecord.setCurrFrozenBalance(new BigDecimal(""+addCount));
				accountFundChange.setCurrAvailableBalance(new BigDecimal(""+addCount));
				accountFundChange.setCurrFrozenBalance(new BigDecimal(""+addCount));
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(addCount.multiply(new BigDecimal("-1")));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(addCount);
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-1").multiply(addCount));
				serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-1").multiply(addCount));
				serviceFlowRecord.setAfterFrozenBalance(addCount);
				serviceFlowRecord.setBeforeFrozenBalance(addCount);
				serviceFlowRecord.setServicePTypeCode(1);
				serviceFlowRecord.setServiceTypeCode(12);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				serviceFlowRecord.setOperName(mainAccountInfo.getOperName());
				serviceFlowRecord.setOperNo(mainAccountInfo.getOperNo());
				serviceFlowRecord.setPlaceName(mainAccountInfo.getPlaceName());
				serviceFlowRecord.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				accountFundChange.setChangeType("10"); 
				accountFundChange.setMemo("充值登记");
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-1").multiply(addCount));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-1").multiply(addCount));
				accountFundChange.setAfterFrozenBalance(addCount);
				accountFundChange.setBeforeFrozenBalance(addCount);
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				
				accountFundChange.setOperName(mainAccountInfo.getOperName());
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
				
				
			}else if (AccChangeTypeEnum.preCardIssue.getValue().equals(type)) {
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				BigDecimal newAmt = unifiedParam.getNewPrepaidC().getRealCost();
				if(newMainAccountInfo.getAvailableBalance().subtract(newAmt).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("储值卡发行工本费");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+newAmt));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setCurrAvailableBalance(newAmt);
				serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+newAmt));
				serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+newAmt));
				serviceFlowRecord.setServicePTypeCode(1);
				serviceFlowRecord.setServiceTypeCode(1);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				accountFundChange.setChangeType("11");
				accountFundChange.setMemo("储值卡发行工本费");
				accountFundChange.setCurrAvailableBalance(newAmt);
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+newAmt));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+newAmt));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if (AccChangeTypeEnum.preCardDelete.getValue().equals(type)) {
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				BigDecimal newAmt = unifiedParam.getNewPrepaidC().getRealCost();
				mainAccountInfoHis.setCreateReason("储值卡发行删除");
			
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(newAmt);
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setCurrAvailableBalance(newAmt);
				serviceFlowRecord.setAfterAvailableBalance(newAmt);
				serviceFlowRecord.setBeforeAvailableBalance(newAmt);
				serviceFlowRecord.setServicePTypeCode(1);
				serviceFlowRecord.setServiceTypeCode(17);
				//新增的字段
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				accountFundChange.setChangeType("12");
				accountFundChange.setMemo("储值卡发行删除");
				accountFundChange.setCurrAvailableBalance(newAmt);
				accountFundChange.setAfterAvailableBalance(newAmt);
				accountFundChange.setBeforeAvailableBalance(newAmt);
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if (AccChangeTypeEnum.preCardRecharge.getValue().equals(type)) {
				if(unifiedParam.getRechargeType() == 1 || unifiedParam.getRechargeType() == 3){//人工充值||直充
					BigDecimal newAmt = newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice());
					if(newAmt.compareTo(BigDecimal.ZERO) < 0){
						return false;
					}
					AccountFundChange accountFundChange=new AccountFundChange();
					accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);

					mainAccountInfoHis.setCreateReason("储值卡充值");

					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
					newMainAccountInfo.setAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
					newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
					newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
					newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
					newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);

					int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
					if(ret == 0){
						return false;
					} else if (ret != 1) {
						throw new ApplicationException("账户信息数据异常，请检查");
					}
					mainAccountInfoDaoHis.saveHis(mainAccountInfoHis, newMainAccountInfo);

					accountFundChange.setChangeType(AccChangeTypeEnum.preCardRecharge.getValue());
					accountFundChange.setBeforeAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
					accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
					accountFundChange.setCurrAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
					accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
					accountFundChange.setAfterAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
					accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());

					accountFundChange.setChgOperID(unifiedParam.getOperId());
					accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
					accountFundChange.setMemo(AccChangeTypeEnum.preCardRecharge.getName());
					accountFundChange.setOperNo(unifiedParam.getOperNo());
					accountFundChange.setOperName(unifiedParam.getOperName());
					accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
					accountFundChange.setPlaceName(unifiedParam.getPlaceName());
					accountFundChangeDao.saveChange(accountFundChange);
				}
			}else if(AccChangeTypeEnum.preCardRechargeSuccess.getValue().equals(type)) {
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.preCardRechargeSuccess.getName());
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(BigDecimal.ZERO);
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);

				accountFundChange.setChangeType(AccChangeTypeEnum.preCardRechargeSuccess.getValue());

				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());

				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setMemo(AccChangeTypeEnum.preCardRechargeSuccess.getName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());

				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if (ret == 0) {
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);

			}else if(AccChangeTypeEnum.preCardRechargeCorrect.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.preCardRechargeCorrect.getName());
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(BigDecimal.ZERO);
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);
				newMainAccountInfo.setState(mainAccountInfo.getState());
				
				accountFundChange.setChangeType(AccChangeTypeEnum.preCardRechargeCorrect.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.preCardRechargeCorrect.getName());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());

				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if(ret == 0){
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis, newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.accCardGetNewCard.getValue().equals(type)){
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getNewAccountCInfo().getRealCost()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("领取新卡扣减记帐卡卡费");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("16");
				accountFundChange.setMemo("领取新卡扣减记帐卡卡费");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getNewAccountCInfo().getRealCost());
			//	accountFundChange.setAfterFrozenBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(mainAccountInfo.getOperName());
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
				
			}else if(AccChangeTypeEnum.accCardBaidAdd.getValue().equals(type)){

				// 1-保证金历史信息表部分处理
				BailAccountInfo bailAccountInfo = unifiedParam.getBailAccountInfo();
				BailAccountInfo newBailAccountInfo = bailAccountInfoDao.findByCustomerID(bailAccountInfo.getMainId());
				if(newBailAccountInfo==null){
					return false;
				}
				//账户余额小于新增保证金金额
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getBailFee()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
				BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
				bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
				//newBailAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				bailAccountInfoHis.setCreateReason("2");
				bailAccountInfoHisDao.saveHis(bailAccountInfoHis,newBailAccountInfo);

				// 2-更新保证金信息表
				newBailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				bailAccountInfoDao.changeBailFee(unifiedParam.getBailFee(), newBailAccountInfo);

				// 3-更新主账户表
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);

				mainAccountInfoHis.setCreateReason("保证金新增");

				// TODO: 2017/4/27 不明白这里为什么要先更新主账户,然后保存历史表再减去余额这样多此一举
				newMainAccountInfo = new MainAccountInfo();//这个是一个假的主账户,只用来存放中间计算数据
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getBailFee()));//可用余额
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));//可退余额
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));//冻结余额
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));//优惠余额
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));//提现冻结余额

				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				// 4-生成主账户历史表
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);

				// 5-保存账户资金变动流水表
				accountFundChange.setChangeType("17");
				accountFundChange.setMemo("保证金新增");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getBailFee());
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getBailFee()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());

				accountFundChangeDao.saveChange(accountFundChange);

				// 6-保存流水表
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				serviceFlowRecord.setServicePTypeCode(2);
				serviceFlowRecord.setServiceTypeCode(2);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				if(unifiedParam.getAccountCInfo()!=null)serviceFlowRecord.setCardTagNO(unifiedParam.getAccountCInfo().getCardNo());
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);

			} else if(AccChangeTypeEnum.preCardRechargeCorrectSuccess.getValue().equals(type)){

				BigDecimal realPrice = unifiedParam.getPrepaidCBussiness().getRealprice().abs();

				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.preCardRechargeCorrectSuccess.getName());

				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				//冲正的business金额为负数，所以冲正成功可用余额要负数金额的负数
				newMainAccountInfo.setAvailableBalance(realPrice);
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(realPrice.negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);
				
				accountFundChange.setChangeType(AccChangeTypeEnum.preCardRechargeCorrectSuccess.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.preCardRechargeCorrectSuccess.getName());
				accountFundChange.setCurrAvailableBalance(realPrice);
				accountFundChange.setAfterAvailableBalance(realPrice);
				accountFundChange.setBeforeAvailableBalance(realPrice);
				accountFundChange.setCurrFrozenBalance(realPrice.negate());
				accountFundChange.setAfterFrozenBalance(realPrice.negate());
				accountFundChange.setBeforeFrozenBalance(realPrice.negate());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if(ret == 0){
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.accCardMemberRechargeTollFee.getValue().equals(type)){//手工缴纳通行费
				AccountFundChange accountFundChange=new AccountFundChange();
				
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getLateAndEtcFee()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("手工缴纳通行费");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("19");
				accountFundChange.setMemo("手工缴纳通行费");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getLateAndEtcFee());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(mainAccountInfo.getOperName());
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
				
			}else if(AccChangeTypeEnum.accCardMemberRechargeLateFee.getValue().equals(type)){
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				//生成主账户历史表
				mainAccountInfoHis.setCreateReason("手工解除止付");
				//判断主账户够不够钱扣
				if(mainAccountInfo.getAvailableBalance().compareTo(unifiedParam.getLateAndEtcFee())!=1)
					return false;
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("20");
				accountFundChange.setMemo("手工解除止付");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getLateAndEtcFee());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getLateAndEtcFee()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(mainAccountInfo.getOperName());//
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.accCardIssue.getValue().equals(type)){
				//System.out.println("进入统一接口了");
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("记帐卡发行工本费与单卡保证金");
				/*mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);*/
				CarObuCardInfo carObuCardInfo = unifiedParam.getCarObuCardInfo();

				NewCardVehicle newCardVehicle = newCardApplyDao.findNewCardVehicleById(carObuCardInfo.getVehicleID());
				AccountCInfo accountCInfo = unifiedParam.getAccountCInfo();
				AccountCApply accountCApply = unifiedParam.getAccountCApply();
				BigDecimal bailFee =newCardVehicle.getBail();
				BigDecimal costAddBail = accountCInfo.getRealCost();
				if(bailFee!=null&&costAddBail!=null){
					costAddBail = bailFee.add(costAddBail);
				}

				/*VehicleInfo vehicleInfo = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());

				//算出账户可用余额的新值
				
				//找出对应记帐卡申请表的记录，判断其NewCardFlag=0则扣除的保证金是初次申请的单卡保证金，判断为NewCardFlag=1则扣除的是对应新增卡的单卡保证金。
				AccountCInfo accountCInfo = unifiedParam.getAccountCInfo();
				AccountCApply accountCApply = unifiedParam.getAccountCApply();
				
				//判断客车货车
				BigDecimal bailFee = null;
				if(VehicleTypeEnum.car.getValue().equals(vehicleInfo.getVehicleType())){
					bailFee = accountCApply.getBail();
				}else if(VehicleTypeEnum.truck.getValue().equals(vehicleInfo.getVehicleType())){
					bailFee = accountCApply.getTruckBail();
				}
				
				BigDecimal costAddBail = new BigDecimal("0");
				if("0".equals(accountCApply.getNewCardFlag())){
					if(bailFee != null){
						costAddBail = accountCInfo.getRealCost().add(bailFee);
					}else{
						costAddBail = accountCInfo.getRealCost();
					}
				}else if("1".equals(accountCApply.getNewCardFlag())){
					//如果NewCardFlag不是0表示有新增卡申请记录，此时单卡保证金是新增卡单卡保证金。
					NewCardApply newCardApply = newCardApplyDao.findLastByApplyId(accountCApply.getId());
					//判断客车货车
					BigDecimal bailFee2 = null;
					if(VehicleTypeEnum.car.getValue().equals(vehicleInfo.getVehicleType())){
						bailFee2 = newCardApply.getBail();
					}else if(VehicleTypeEnum.truck.getValue().equals(vehicleInfo.getVehicleType())){
						bailFee2 = newCardApply.getTruckBail();
					}
					if(bailFee2!=null){
						costAddBail = bailFee2.add(accountCInfo.getRealCost());
					}else{
						costAddBail = accountCInfo.getRealCost();
					}
				}*/
				
				//1 将客户主账户余额扣减单卡保证金+工本费
				BigDecimal newAvBalance = mainAccountInfo.getAvailableBalance().subtract(costAddBail);
				if(newAvBalance.compareTo(new BigDecimal("0"))==-1){
					logger.info("账户余额不足");
					//System.out.println("账户余额不足");
					return false;
				}
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+costAddBail));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));


				//账户资金变动流水表
				accountFundChange.setChangeType("21");
				accountFundChange.setMemo("记帐卡发行工本费与单卡保证金");
				accountFundChange.setCurrAvailableBalance(costAddBail);
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+costAddBail));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+costAddBail));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(accountCInfo.getOperName());
				accountFundChange.setOperNo(accountCInfo.getOperNo());
				accountFundChange.setPlaceName(accountCInfo.getPlaceName());
				accountFundChange.setPlaceNo(accountCInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					logger.info("更新主账户信息表失败");
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
				//2 客户保证金金额增加单卡保证金金额(增加的是记帐卡申请表的或者新增卡申请表的单卡保证金)
				BailAccountInfo bailAccountInfo = unifiedParam.getBailAccountInfo();
				BailAccountInfo newBailAccountInfo = bailAccountInfoDao.findByCustomerID(bailAccountInfo.getMainId());
				newBailAccountInfo.setOperTime(new Date());
				//BigDecimal beforeBailFee = newBailAccountInfo.getBailFee();
				//判断客车货车
				/*BigDecimal bailFee3 = null;
				if(VehicleTypeEnum.car.getValue().equals(vehicleInfo.getVehicleType())){
					bailFee3 = accountCApply.getBail();
				}else if(VehicleTypeEnum.truck.getValue().equals(vehicleInfo.getVehicleType())){
					bailFee3 = accountCApply.getTruckBail();
				}*/

				// TODO: 2017/4/21 初次申请没有保证金
				/*if("0".equals(accountCApply.getNewCardFlag())){
					if(bailFee3 != null){
						//newBailAccountInfo.setBailFee(newBailAccountInfo.getBailFee().add(accountCApply.getBail()));//增加单卡保证金
						accountCInfo.setBail(bailFee3);//保证金为初次申请的保证金
					}
				}else */
				if(bailFee!=null){
					//costAddBail = newCardApply.getBail().add(accountCInfo.getCost());
					accountCInfo.setBail(bailFee);//保证金为新增卡申请的保证金
				}else if(accountCInfo.getBail()==null)
					accountCInfo.setBail(new BigDecimal("0"));
				//BigDecimal afterBailFee = newBailAccountInfo.getBailFee();
				
				//保证金账户历史
				BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
				BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
				bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));		
				bailAccountInfoHis.setCreateReason("1");//1代表增加单卡保证金
				bailAccountInfoHisDao.saveHis(bailAccountInfoHis, newBailAccountInfo);
				
				newBailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				//
				//bailAccountInfoDao.update(newBailAccountInfo);
				bailAccountInfoDao.changeBailFee(accountCInfo.getBail(), newBailAccountInfo);
				
				//3 增加记帐卡信息表(要调用记帐卡状态更新接口？)
				BigDecimal SEQ_CSMSAccountCinfo_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfo_NO");
				accountCInfo.setId(Long.valueOf(SEQ_CSMSAccountCinfo_NO.toString()));
				//accountCInfo.setRealCost(accountCInfo.getCost());//真实扣除的工本费
				accountCInfo.setCustomerId(newMainAccountInfo.getMainId());
				accountCInfo.setAccountId(unifiedParam.getSubAccountInfo().getId());//子账户id
				accountCInfo.setState("0");
				accountCInfo.setIssueTime(new Date());//发行时间
//				accountCInfo.setBail();
				//accountCInfo.setIssueOperId(1L);
				//accountCInfo.setIssuePlaceId(1L);
				//bind是否有绑定电子标签
				if(carObuCardInfo.getTagID() == null){
					accountCInfo.setBind("0");
				}else{
					accountCInfo.setBind("1");
				}
				
				accountCDao.save(accountCInfo);
		
				//4  修改(即绑定)车卡标签绑定表(CSMS_CarObuCard_info)
				carObuCardInfo.setAccountCID(accountCInfo.getId());
				carObuCardInfoDao.update(carObuCardInfo);

				//5 更新新增卡车辆信息状态
				newCardVehicle.setState("0");
				newCardApplyDao.update(newCardVehicle);
				
				//6 修改记帐卡账户申请数量
				//将记帐卡申请信息移到历史表
				/*AccountCapplyHis accountCapplyHis = new AccountCapplyHis();
				BigDecimal SEQ_CSMSAccountCapplyhis_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCapplyhis_NO");
				accountCapplyHis.setId(Long.valueOf(SEQ_CSMSAccountCapplyhis_NO.toString()));
				accountCapplyHis.setGenReason("1");//修改
				*/
				accountCApplyHisDao.saveHis(unifiedParam.getAccountCapplyHis(), accountCApply);
				
				Long residueCount = accountCApply.getResidueCount()-1;
				AccountCApply newAccountCApply = accountCApplyDao.findById(accountCApply.getId());
				newAccountCApply.setResidueCount(residueCount);;
				accountCApplyDao.update(newAccountCApply);

				
				//7 生成客服流水
				serviceFlowRecord.setCurrState("0");
				serviceFlowRecord.setAfterState("0");
			
				serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+costAddBail));
				serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("-"+costAddBail));
				serviceFlowRecord.setCurrAvailableBalance(costAddBail);
				//TODO(客服流水是不是没有保存保证金？)
				//System.out.println(accountCInfo.getBail());
				logger.info(accountCInfo.getBail());
				serviceFlowRecord.setBeforeBailFee(new BigDecimal("-"+accountCInfo.getBail()));
				serviceFlowRecord.setAfterBailFee(new BigDecimal("-"+accountCInfo.getBail()));
				serviceFlowRecord.setCurrBailFee(accountCInfo.getBail());
				
				//serviceFlowRecord.setAfterAvailableBalance(afterAvailableBalance);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				serviceFlowRecord.setOperName(accountCInfo.getOperName());
				serviceFlowRecord.setOperNo(accountCInfo.getOperNo());
				serviceFlowRecord.setPlaceName(accountCInfo.getPlaceName());
				serviceFlowRecord.setPlaceNo(accountCInfo.getPlaceNo());
				
				serviceFlowRecord.setCardTagNO(accountCInfo.getCardNo());
				serviceFlowRecord.setServicePTypeCode(2);
				serviceFlowRecord.setServiceTypeCode(1);
				serviceFlowRecord.setClientID(newMainAccountInfo.getMainId());
				//serviceFlowRecordDao.save(serviceFlowRecord);
				
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
				
				
			}else if(AccChangeTypeEnum.preCardReplaceMent.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getNewPrepaidC().getRealCost()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("补领卡扣减储值卡卡费");
				
				//mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("22");
				accountFundChange.setMemo("补领卡扣减储值卡卡费");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getNewPrepaidC().getRealCost());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getNewPrepaidC().getRealCost()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.accCardReplaceMent.getValue().equals(type)){
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getNewAccountCInfo().getRealCost()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason("补领扣减记帐卡卡费");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("23");
				accountFundChange.setMemo("补领扣减记帐卡卡费");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getNewAccountCInfo().getRealCost());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-"+unifiedParam.getNewAccountCInfo().getRealCost()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				accountFundChange.setOperName(mainAccountInfo.getOperName());
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.tagDaySetIssuce.getValue().equals(type)){
				System.out.println(newMainAccountInfo.getAvailableBalance().add(unifiedParam.getChangePrice()));
				if(newMainAccountInfo.getAvailableBalance().add(unifiedParam.getChangePrice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("日结前资金修正");
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				System.out.println(unifiedParam.getChangePrice());
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(unifiedParam.getChangePrice());
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChgDate(unifiedParam.getDate());
				
				if(Constant.OBUISSUE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("24");
				}
				if(Constant.OBUREPLACE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("39");
				}
				if(Constant.PAIDISSUE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("40");
				}
				if(Constant.PAIDCHANGE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("41");
				}
				if(Constant.PAIDREPLACE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("42");
				}
				if(Constant.DEBITISSUE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("43");
				}
				if(Constant.DEBITRECHAGE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("44");
				}
				if(Constant.DEBITREPLACE.equals(unifiedParam.getFlag())){
					accountFundChange.setChangeType("45");
				}
				
				accountFundChange.setMemo("日结前资金修正");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getChangePrice().abs());
				accountFundChange.setAfterAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setBeforeAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				serviceFlowRecord.setServicePTypeCode(4);
				serviceFlowRecord.setServiceTypeCode(7);
				serviceFlowRecord.setCurrAvailableBalance(unifiedParam.getChangePrice().abs());
				serviceFlowRecord.setAfterAvailableBalance(unifiedParam.getChangePrice());
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChangeDaySet(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.imRechargeHalfSuccess.getValue().equals(type)){//25 快速充值半条确认成功 

				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.imRechargeHalfSuccess.getName());
				if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType(AccChangeTypeEnum.imRechargeHalfSuccess.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.imRechargeHalfSuccess.getName());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.imRechargeHalfFail.getValue().equals(type)){//26快速充值半条确认失败
				//nothing to do
			}else if(AccChangeTypeEnum.memberRechargeHalfSuccess.getValue().equals(type)){//27人工充值半条确认成功  

				if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice()).compareTo(BigDecimal.ZERO) < 0){
					return false;
				}

				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.memberRechargeHalfSuccess.getName());

				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(BigDecimal.ZERO);
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);
				
				accountFundChange.setChangeType(AccChangeTypeEnum.memberRechargeHalfSuccess.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.memberRechargeHalfSuccess.getName());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if (ret == 0) {
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.memberRechargeHalfFail.getValue().equals(type)){//28人工充值半条确认失败
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.memberRechargeHalfFail.getName());
				if (newMainAccountInfo.getFrozenBalance().compareTo(unifiedParam.getPrepaidCBussiness().getRealprice()) < 0) {
					return false;
				}
				/*if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}*/
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);

				accountFundChange.setChangeType(AccChangeTypeEnum.memberRechargeHalfFail.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.memberRechargeHalfFail.getName());
				accountFundChange.setBeforeAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setCurrAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if (ret == 0) {
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);

			}else if(AccChangeTypeEnum.rechargeRegisterHalfSuccess.getValue().equals(type)){//29充值冲正半条确认成功
				
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.rechargeRegisterHalfSuccess.getName());
				if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);

				accountFundChange.setChangeType(AccChangeTypeEnum.rechargeRegisterHalfSuccess.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.rechargeRegisterHalfSuccess.getName());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setCurrAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setBeforeAvailableBalance(unifiedParam.getPrepaidCBussiness().getRealprice());

				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());

				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if (ret == 0) {
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.rechargeRegisterHalfFail.getValue().equals(type)){//30充值冲正半条确认失败
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				mainAccountInfoHis.setCreateReason(AccChangeTypeEnum.rechargeRegisterHalfFail.getName());
				if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getPrepaidCBussiness().getRealprice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(BigDecimal.ZERO);
				newMainAccountInfo.setAvailableRefundBalance(BigDecimal.ZERO);
				newMainAccountInfo.setFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				newMainAccountInfo.setPreferentialBalance(BigDecimal.ZERO);
				newMainAccountInfo.setRefundApproveBalance(BigDecimal.ZERO);
				
				accountFundChange.setChangeType(AccChangeTypeEnum.rechargeRegisterHalfFail.getValue());
				accountFundChange.setMemo(AccChangeTypeEnum.rechargeRegisterHalfFail.getName());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setAfterFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setBeforeFrozenBalance(unifiedParam.getPrepaidCBussiness().getRealprice().negate());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				int ret = mainAccountInfoDao.updateBackInt(newMainAccountInfo);
				if (ret == 0) {
					return false;
				} else if (ret != 1) {
					throw new ApplicationException("账户信息数据异常，请检查");
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);

			}else if(AccChangeTypeEnum.revokeRechargeRegister.getValue().equals(type)){// 31、撤销充值登记
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(newMainAccountInfo.getFrozenBalance().subtract(unifiedParam.getChangePrice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("撤销充值登记");
				
				serviceFlowRecord.setCurrAvailableBalance(unifiedParam.getChangePrice());
				serviceFlowRecord.setCurrFrozenBalance(unifiedParam.getChangePrice());
				
				accountFundChange.setCurrAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setCurrFrozenBalance(unifiedParam.getChangePrice());
				
				newMainAccountInfo.setAvailableBalance(unifiedParam.getChangePrice());
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setAfterAvailableBalance(unifiedParam.getChangePrice());
				serviceFlowRecord.setAfterFrozenBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));
				serviceFlowRecord.setBeforeAvailableBalance(unifiedParam.getChangePrice());
				serviceFlowRecord.setBeforeFrozenBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));
				serviceFlowRecord.setServicePTypeCode(1);
				serviceFlowRecord.setServiceTypeCode(12);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setChangeType("31"); 
				accountFundChange.setMemo("充值登记");
				accountFundChange.setAfterAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setAfterFrozenBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));
				accountFundChange.setBeforeAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setBeforeFrozenBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				serviceFlowRecord.setOperName(unifiedParam.getOperName());
				serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
				serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
				serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.bailRefundReq.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				//生成主账户历史表
				mainAccountInfoHis.setCreateReason("保证金退还申请");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(unifiedParam.getBailFee());//主账户增加可退余额
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("32");
				accountFundChange.setMemo("保证金退还申请");
				accountFundChange.setCurrAvailableRefundBalance(unifiedParam.getBailFee());//主账户增加可退余额
				accountFundChange.setBeforeAvailableRefundBalance(unifiedParam.getBailFee());//主账户增加可退余额
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//主账户信息的处理
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				
				//保证金历史信息表部分处理
				BailAccountInfo bailAccountInfo = unifiedParam.getBailAccountInfo();
				SubAccountInfo subAccountInfo = unifiedParam.getSubAccountInfo();
				//保证金的处理
				BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
				BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
				bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
				bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				bailAccountInfoHis.setCreateReason("3");
				bailAccountInfoHisDao.saveHis(bailAccountInfoHis, bailAccountInfo);

				//更新保证金信息表
				bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				
				//保证金主账户减少保证金
				if(bailAccountInfoDao.changeBailFee(unifiedParam.getBailFee().negate(), bailAccountInfo) == 0){
					logger.error("数据异常：保证金主账户保证金不足");
					throw new ApplicationException("数据异常：保证金主账户保证金不足");
				}
				//子账户减少保证金
				if(subAccountInfoDao.updateBail(subAccountInfo.getId(), unifiedParam.getBailFee().negate()) == 0){
					logger.error("数据异常：子账户保证金不足");
					throw new ApplicationException("数据异常：子账户保证金不足");
				}
				
				/*//页面传入的保证金金额从保证金账户中保证金金额扣除相应的金额到保证金冻结金额中
				if(bailAccountInfoDao.updateBailToFrozen(unifiedParam.getBailFee(), bailAccountInfo) == 0){
					logger.error("数据异常：主账户保证金出现变动");
					throw new ApplicationException("数据异常：主账户保证金出现变动");
				}
				
				//子账户保证金金额与保证金余额减少
				if(subAccountInfoDao.updateBail2Frozen(subAccountInfo.getId(),unifiedParam.getBailFee().abs()) == 0){
					logger.error("数据异常：子账户保证金出现变动");
					throw new ApplicationException("数据异常：子账户保证金出现变动");
				}*/
				
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.bailRefundReqCancel.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				//生成主账户历史表
				mainAccountInfoHis.setCreateReason("保证金退还撤销");
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(unifiedParam.getBailFee().negate());//主账户减少可退余额
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("33");
				accountFundChange.setMemo("保证金退还撤销");
				accountFundChange.setCurrAvailableRefundBalance(unifiedParam.getBailFee().negate());//主账户减少可退余额
				accountFundChange.setBeforeAvailableRefundBalance(unifiedParam.getBailFee().negate());//主账户减少可退余额
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//主账户信息的处理
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				
				//保证金历史信息表部分处理
				//保证金历史信息表部分处理
				BailAccountInfo bailAccountInfo = unifiedParam.getBailAccountInfo();
				SubAccountInfo subAccountInfo = unifiedParam.getSubAccountInfo();
				
				BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
				BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
				bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
				bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				bailAccountInfoHis.setCreateReason("8");//原因:"撤销"
				bailAccountInfoHisDao.saveHis(bailAccountInfoHis, bailAccountInfo);
				
				//更新保证金信息表
				bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				
				//保证金主账户增加保证金
				if(bailAccountInfoDao.changeBailFee(unifiedParam.getBailFee(), bailAccountInfo) == 0){
					logger.error("数据异常：保证金主账户保证金增加失败");
					throw new ApplicationException("数据异常：保证金主账户保证金增加失败");
				}
				//子账户增加保证金
				if(subAccountInfoDao.updateBail(subAccountInfo.getId(), unifiedParam.getBailFee()) == 0){
					logger.error("数据异常：子账户保证金增加失败");
					throw new ApplicationException("数据异常：子账户保证金增加失败");
				}
				
				
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				//新增账户资金变动流水
				accountFundChangeDao.saveChange(accountFundChange);
				
			}else if(AccChangeTypeEnum.operationReview.getValue().equals(type)){
				List<RefundInfo> refundInfoList = unifiedParam.getRefundInfoList();
				Long id = null;
				for(RefundInfo refund: refundInfoList){
					//his
					RefundInfoHis refundInfoHis = new RefundInfoHis();
					id = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
					refundInfoHis.setId(id);
					//refundInfoHis.setCreateDate(new Date());
					refundInfoHis.setCreateReason("修改");//1:修改
					refundInfoHisDao.saveHis(refundInfoHis, refund);
					//update -> refundInfoList（参数值已经设置，直接update）
					refund.setHisSeqId(refundInfoHis.getId());
					refundInfoDao.updateForRefundInterface(refund);
					
					//账户等信息的更改
					AccountFundChange accountFundChange=new AccountFundChange();
					MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
					accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
					//生成主账户历史表
					MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
					BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
					mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
					mainAccountHis.setCreateReason("营运中心审核");
					mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
					
					accountFundChange.setChangeType("34");//对应资金变动业务操作类型即type
					accountFundChange.setChgOperID(refund.getAuditId());
					//accountFundChange.setChgPlaceID(refund.getPlaceID());
					accountFundChange.setOperName(refund.getAuditName());
					accountFundChange.setOperNo(refund.getAuditNo());
					//accountFundChange.setPlaceName(refund.getPlaceName());
					//accountFundChange.setPlaceNo(refund.getPlaceNo());
					
					//审批状态为 ： 营运中心审核通过
					//相应的把每笔退款记录中的本次退款金额，根据当前记录的账户ID从账户中扣除可退余额（这笔本次退款金额）到退款审批余额中
					//mainAccount.setHisSeqId(mainAccountHis.getId());
					//mainAccountInfoDao.updateAvRefundAndRefundAppByCurrent(mainAccount.getId(), refund.getCurrentRefundBalance());
					
					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccount.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));//扣除可退余额
					newMainAccountInfo.setRefundApproveBalance(refund.getCurrentRefundBalance());//增加退款审批余额
					newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
					newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
					if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
						return false;
					}
					//his
					mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
					
					accountFundChange.setMemo("退款审批: 营运中心审核");
					accountFundChange.setCurrAvailableRefundBalance(refund.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));
					accountFundChange.setCurrRefundApproveBalance(refund.getCurrentRefundBalance());
					accountFundChange.setBeforeRefundApproveBalance(refund.getCurrentRefundBalance());
					//新增账户资金变动流水
					accountFundChangeDao.saveChange(accountFundChange);
				}
			}else if(AccChangeTypeEnum.financeRefund.getValue().equals(type)){
				List<RefundInfo> refundInfoList = unifiedParam.getRefundInfoList();
				Long id = null;
				for(RefundInfo refund: refundInfoList){
					//his
					RefundInfoHis refundInfoHis = new RefundInfoHis();
					id = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
					refundInfoHis.setId(id);
					//refundInfoHis.setCreateDate(new Date());
					refundInfoHis.setCreateReason("修改");//1:修改
					refundInfoHisDao.saveHis(refundInfoHis, refund);
					//update -> refundInfoList（参数值已经设置，直接update）
					refund.setHisSeqId(refundInfoHis.getId());
					refundInfoDao.updateForRefundInterface(refund);
					
					//账户等信息的更改
					AccountFundChange accountFundChange=new AccountFundChange();
					MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
					accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
					//生成主账户历史表
					MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
					BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
					mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
					mainAccountHis.setCreateReason("财务退款完成");
					mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
					
					accountFundChange.setChangeType("35");//对应资金变动业务操作类型即type
					accountFundChange.setChgOperID(refund.getAuditId());
					//accountFundChange.setChgPlaceID(refund.getPlaceID());
					accountFundChange.setOperName(refund.getAuditName());
					accountFundChange.setOperNo(refund.getAuditNo());
					//accountFundChange.setPlaceName(refund.getPlaceName());
					//accountFundChange.setPlaceNo(refund.getPlaceNo());
					
					//审批状态为 ： 财务退款完成
					//根据当前记录的账户ID从账户中扣除退款审批余额（这笔本次退款金额，直接扣除）
					//mainAccount.setHisSeqId(mainAccountHis.getId());
					//mainAccountInfoDao.updateRefundAppBalanceByCurrent(mainAccount.getId(), refund.getCurrentRefundBalance());
					
					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccount.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));//直接扣除退款审批余额
					newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
					newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
					if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
						return false;
					}
					//his
					mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
					
					accountFundChange.setMemo("退款审批: 财务退款完成");
					accountFundChange.setCurrRefundApproveBalance(refund.getCurrentRefundBalance());
					accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));
					//新增账户资金变动流水
					accountFundChangeDao.saveChange(accountFundChange);
				}
			}else if(AccChangeTypeEnum.financeRefundFail.getValue().equals(type)){
				List<RefundInfo> refundInfoList = unifiedParam.getRefundInfoList();
				Long id = null;
				for(RefundInfo refund: refundInfoList){
					//his
					RefundInfoHis refundInfoHis = new RefundInfoHis();
					id = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
					refundInfoHis.setId(id);
					//refundInfoHis.setCreateDate(new Date());
					refundInfoHis.setCreateReason("修改");//1:修改
					refundInfoHisDao.saveHis(refundInfoHis, refund);
					//update -> refundInfoList（参数值已经设置，直接update）
					refund.setHisSeqId(refundInfoHis.getId());
					refundInfoDao.updateForRefundInterface(refund);
					
					//账户等信息的更改
					AccountFundChange accountFundChange=new AccountFundChange();
					
					
					//2017-10-15 需求变动，设计修改：保证金退还退款、账户余额退款，财务退款将提现冻结金额退回原点
					//所谓退回原点：1，保证金退还就要退到保证金账户；2，账户余额退款就要退到账户可用余额
					if(RefundTypeEnum.accountRefund.getValue().equals(refund.getRefundType())){
						//账户余额退款
						MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
						accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
						//生成主账户历史表
						MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
						BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
						mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
						mainAccountHis.setCreateReason("账户余额退款审批：财务退款失败");
						mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
						
						accountFundChange.setChangeType("36");//对应资金变动业务操作类型即type
						accountFundChange.setChgOperID(refund.getAuditId());
						//accountFundChange.setChgPlaceID(refund.getPlaceID());
						accountFundChange.setOperName(refund.getAuditName());
						accountFundChange.setOperNo(refund.getAuditNo());
						//accountFundChange.setPlaceName(refund.getPlaceName());
						//accountFundChange.setPlaceNo(refund.getPlaceNo());
						
						newMainAccountInfo = new MainAccountInfo();
						newMainAccountInfo.setMainId(mainAccount.getMainId());
						newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
						newMainAccountInfo.setAvailableBalance(refund.getCurrentRefundBalance());//增加可用余额
						newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
						newMainAccountInfo.setRefundApproveBalance(refund.getCurrentRefundBalance().negate());//扣除退款审批余额
						newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
						newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
						if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
							return false;
						}
						
						//his
						mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
						
						accountFundChange.setMemo("账户余额退款审批：财务退款失败");
						accountFundChange.setCurrAvailableBalance(refund.getCurrentRefundBalance());
						accountFundChange.setBeforeAvailableBalance(refund.getCurrentRefundBalance());
						accountFundChange.setCurrRefundApproveBalance(refund.getCurrentRefundBalance());
						accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));
						//新增账户资金变动流水
						accountFundChangeDao.saveChange(accountFundChange);
						
					}else if(RefundTypeEnum.bailRefund.getValue().equals(refund.getRefundType())){
						//保证金退款
						//账户余额退款
						MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
						accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
						//生成主账户历史表
						MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
						BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
						mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
						mainAccountHis.setCreateReason("保证金退还退款审批：财务退款失败");
						mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
						
						accountFundChange.setChangeType("36");//对应资金变动业务操作类型即type
						accountFundChange.setChgOperID(refund.getAuditId());
						//accountFundChange.setChgPlaceID(refund.getPlaceID());
						accountFundChange.setOperName(refund.getAuditName());
						accountFundChange.setOperNo(refund.getAuditNo());
						//accountFundChange.setPlaceName(refund.getPlaceName());
						//accountFundChange.setPlaceNo(refund.getPlaceNo());
						
						newMainAccountInfo = new MainAccountInfo();
						newMainAccountInfo.setMainId(mainAccount.getMainId());
						newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
						newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
						newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
						newMainAccountInfo.setRefundApproveBalance(refund.getCurrentRefundBalance().negate());//扣除退款审批余额
						newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
						newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
						if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
							return false;
						}
						
						//his
						mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
						
						accountFundChange.setMemo("保证金退还退款审批：财务退款失败");
						accountFundChange.setCurrRefundApproveBalance(refund.getCurrentRefundBalance());
						accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));
						//新增账户资金变动流水
						accountFundChangeDao.saveChange(accountFundChange);
						
						//对于保证金退款：财务退款失败将   退款审批余额(提现冻结金额退到保证金账户)
						//保证金历史信息表部分处理
						BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(refund.getMainId());
						if(bailAccountInfo == null){
							logger.error("数据异常：保证金主账户不存在");
							throw new ApplicationException("数据异常：保证金主账户不存在");
						}
						SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(refund.getBankAccount());
						if(subAccountInfo == null){
							logger.error("数据异常：无法根据银行账号找到子账户");
							throw new ApplicationException("数据异常：无法根据银行账号找到子账户");
						}
						//保证金的处理
						BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
						BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
						bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
						bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
						bailAccountInfoHis.setCreateReason("7");//7.保证金退还审批失败
						bailAccountInfoHisDao.saveHis(bailAccountInfoHis, bailAccountInfo);

						//更新保证金信息表
						bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
						
						//保证金主账户增加保证金
						if(bailAccountInfoDao.changeBailFee(refund.getCurrentRefundBalance(), bailAccountInfo) == 0){
							logger.error("数据异常：保证金主账户保证金不足");
							throw new ApplicationException("数据异常：保证金主账户保证金不足");
						}
						//子账户增加保证金
						if(subAccountInfoDao.updateBail(subAccountInfo.getId(), refund.getCurrentRefundBalance()) == 0){
							logger.error("数据异常：子账户保证金不足");
							throw new ApplicationException("数据异常：子账户保证金不足");
						}
						
					}else{
						//储值卡退款
						MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
						accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
						//生成主账户历史表
						MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
						BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
						mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
						mainAccountHis.setCreateReason("储值卡终止使用退款审批：财务退款失败");
						mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
						
						accountFundChange.setChangeType("36");//对应资金变动业务操作类型即type
						accountFundChange.setChgOperID(refund.getAuditId());
						//accountFundChange.setChgPlaceID(refund.getPlaceID());
						accountFundChange.setOperName(refund.getAuditName());
						accountFundChange.setOperNo(refund.getAuditNo());
						//accountFundChange.setPlaceName(refund.getPlaceName());
						//accountFundChange.setPlaceNo(refund.getPlaceNo());
						
						newMainAccountInfo = new MainAccountInfo();
						newMainAccountInfo.setMainId(mainAccount.getMainId());
						newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
						newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
						newMainAccountInfo.setAvailableRefundBalance(refund.getCurrentRefundBalance());//增加可退余额
						newMainAccountInfo.setRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));//扣除退款审批余额
						newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
						newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
						if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
							return false;
						}
						
						//his
						mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
						
						accountFundChange.setMemo("储值卡终止使用退款审批：财务退款失败");
						accountFundChange.setCurrAvailableRefundBalance(refund.getCurrentRefundBalance());
						accountFundChange.setBeforeAvailableRefundBalance(refund.getCurrentRefundBalance());
						accountFundChange.setCurrRefundApproveBalance(refund.getCurrentRefundBalance());
						accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));
						//新增账户资金变动流水
						accountFundChangeDao.saveChange(accountFundChange);
					}
					
				}
			} else if(AccChangeTypeEnum.prepaidCStopCard.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));//地标卡终止使用收取工本费（因赠与余额未用完）
				//2017-07-25 储值卡终止使用不生成退款记录，退款步骤另起功能模块
				//newMainAccountInfo.setAvailableRefundBalance(unifiedParam.getBailFee());//可退余额
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));//可退余额
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("46");//对应资金变动业务操作类型即type
				accountFundChange.setMemo("储值卡终止使用");
				accountFundChange.setCurrAvailableBalance(new BigDecimal("-"+unifiedParam.getChangePrice()));//地标卡终止使用收取工本费（因赠与余额未用完）
				//accountFundChange.setCurrAvailableRefundBalance(unifiedParam.getBailFee());
				//accountFundChange.setBeforeAvailableRefundBalance(unifiedParam.getBailFee());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());

				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				//新增账户资金变动流水
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.chaseMoney.getValue().equals(type)){
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				mainAccountInfoHis.setCreateReason("追款管理收取");
				
				
				if(newMainAccountInfo.getAvailableBalance().subtract(unifiedParam.getChangePrice()).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(new BigDecimal("-" + unifiedParam.getChangePrice()));
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				
				accountFundChange.setChangeType("48");
				accountFundChange.setMemo("追款管理收取");
				accountFundChange.setCurrAvailableBalance(unifiedParam.getChangePrice());
				accountFundChange.setAfterAvailableBalance(new BigDecimal("-" + unifiedParam.getChangePrice()));
				accountFundChange.setBeforeAvailableBalance(new BigDecimal("-" + unifiedParam.getChangePrice()));
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				
				
				return true;
			}else if(AccChangeTypeEnum.preCardStopRefund.getValue().equals(type)){
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				
				newMainAccountInfo = new MainAccountInfo();
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				//2017-07-26  储值卡终止使用首次退款 增加可退余额
				newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
				newMainAccountInfo.setAvailableRefundBalance(unifiedParam.getBailFee());//可退余额
				newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				accountFundChange.setChangeType("49");//对应资金变动业务操作类型即type
				accountFundChange.setMemo("储值卡终止使用首次退款申请");
				accountFundChange.setCurrAvailableRefundBalance(unifiedParam.getBailFee());
				accountFundChange.setBeforeAvailableRefundBalance(unifiedParam.getBailFee());
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				accountFundChange.setOperName(unifiedParam.getOperName());
				accountFundChange.setOperNo(unifiedParam.getOperNo());
				accountFundChange.setPlaceName(unifiedParam.getPlaceName());
				accountFundChange.setPlaceNo(unifiedParam.getPlaceNo());

				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				//新增账户资金变动流水
				accountFundChangeDao.saveChange(accountFundChange);
			}else if(AccChangeTypeEnum.accountCombine.getValue().equals(type)){
				//被合并账户
				MainAccountInfo beCombinedMainAccountInfo = unifiedParam.getBeCombinedMainAccountInfo();
				//被合并保证金账户
				BailAccountInfo beCombinedBailAccountInfo = bailAccountInfoDao.findByCustomerID(beCombinedMainAccountInfo.getMainId());
				//已存在保证金账户
				BailAccountInfo existBailAccountInfo = bailAccountInfoDao.findByCustomerID(newMainAccountInfo.getMainId());
				
				//已存在账户
				AccountFundChange existAccountFundChange=new AccountFundChange();
				existAccountFundChange = getBeforeAccountFundChange(newMainAccountInfo, existAccountFundChange);
				MainAccountInfo existMainAccountInfo = new MainAccountInfo();
				existMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				existMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				existMainAccountInfo.setAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance());
				existMainAccountInfo.setAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance());//可退余额
				existMainAccountInfo.setFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance());
				existMainAccountInfo.setPreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance());
				existMainAccountInfo.setRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance());
				
				existAccountFundChange.setChangeType(AccChangeTypeEnum.accountCombine.getValue());//对应资金变动业务操作类型即type
				existAccountFundChange.setMemo("客户账户合并(已存在账户的资金流入)");
				
				existAccountFundChange.setCurrAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance());
				existAccountFundChange.setBeforeAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance());
				
				existAccountFundChange.setCurrFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance());
				existAccountFundChange.setBeforeFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance());
				
				existAccountFundChange.setCurrpreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance());
				existAccountFundChange.setBeforepreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance());
				
				existAccountFundChange.setCurrAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance());
				existAccountFundChange.setBeforeAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance());
				
				existAccountFundChange.setCurrRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance());
				existAccountFundChange.setBeforeRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance());
				
				existAccountFundChange.setChgOperID(unifiedParam.getOperId());
				existAccountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				existAccountFundChange.setOperName(unifiedParam.getOperName());
				existAccountFundChange.setOperNo(unifiedParam.getOperNo());
				existAccountFundChange.setPlaceName(unifiedParam.getPlaceName());
				existAccountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				//已存在账户的资金流入
				if(mainAccountInfoDao.updateBackInt(existMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				//已存在账户的资金流入
				accountFundChangeDao.saveChange(existAccountFundChange);
				
				//被合并账户
				MainAccountInfoHis beCombinedMainAccountInfoHis = new MainAccountInfoHis();
				BigDecimal beCombinedMainAccountInfoHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
				beCombinedMainAccountInfoHis.setId(Long.valueOf(beCombinedMainAccountInfoHisId.toString()));
				beCombinedMainAccountInfoHis.setHisSeqId(beCombinedMainAccountInfo.getHisSeqId());
				
				AccountFundChange beCombinedAccountFundChange = new AccountFundChange();
				beCombinedAccountFundChange = getBeforeAccountFundChange(beCombinedMainAccountInfo, beCombinedAccountFundChange);
				
				Long beCombinedMainId = beCombinedMainAccountInfo.getMainId();
				Long beCombinedHisSeqId = beCombinedMainAccountInfo.getHisSeqId();
				MainAccountInfo beCombined4Save = new MainAccountInfo();
				beCombined4Save.setMainId(beCombinedMainId);
				beCombined4Save.setHisSeqId(beCombinedHisSeqId);
				
				beCombined4Save.setAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance().negate());
				beCombined4Save.setAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance().negate());//可退余额
				beCombined4Save.setFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance().negate());
				beCombined4Save.setPreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance().negate());
				beCombined4Save.setRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance().negate());
				
				beCombinedAccountFundChange.setChangeType(AccChangeTypeEnum.accountCombine.getValue());//对应资金变动业务操作类型即type
				beCombinedAccountFundChange.setMemo("客户账户合并(被合并账户的资金流出)");
				
				beCombinedAccountFundChange.setCurrAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance().negate());
				beCombinedAccountFundChange.setBeforeAvailableBalance(beCombinedMainAccountInfo.getAvailableBalance().negate());
				
				beCombinedAccountFundChange.setCurrFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance().negate());
				beCombinedAccountFundChange.setBeforeFrozenBalance(beCombinedMainAccountInfo.getFrozenBalance().negate());
				
				beCombinedAccountFundChange.setCurrpreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance().negate());
				beCombinedAccountFundChange.setBeforepreferentialBalance(beCombinedMainAccountInfo.getPreferentialBalance().negate());
				
				beCombinedAccountFundChange.setCurrAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance().negate());
				beCombinedAccountFundChange.setBeforeAvailableRefundBalance(beCombinedMainAccountInfo.getAvailableRefundBalance().negate());
				
				beCombinedAccountFundChange.setCurrRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance().negate());
				beCombinedAccountFundChange.setBeforeRefundApproveBalance(beCombinedMainAccountInfo.getRefundApproveBalance().negate());
				
				beCombinedAccountFundChange.setChgOperID(unifiedParam.getOperId());
				beCombinedAccountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				beCombinedAccountFundChange.setOperName(unifiedParam.getOperName());
				beCombinedAccountFundChange.setOperNo(unifiedParam.getOperNo());
				beCombinedAccountFundChange.setPlaceName(unifiedParam.getPlaceName());
				beCombinedAccountFundChange.setPlaceNo(unifiedParam.getPlaceNo());
				//被合并账户的资金流出
				if(mainAccountInfoDao.updateBackInt(beCombined4Save)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(beCombinedMainAccountInfoHis,beCombinedMainAccountInfo);
				//被合并账户的资金流出
				accountFundChangeDao.saveChange(beCombinedAccountFundChange);
				
				if(existBailAccountInfo != null && beCombinedBailAccountInfo != null){
					//已存在保证金账户的资金流入
					BailAccountInfoHis existBailAccountInfoHis = new BailAccountInfoHis();
					existBailAccountInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfoHis_NO"));
					existBailAccountInfoHis.setCreateReason("9");//客户账户合并：已存在保证金账户的资金流入
					bailAccountInfoHisDao.saveHis(existBailAccountInfoHis, existBailAccountInfo);
					
					existBailAccountInfo.setHisSeqId(existBailAccountInfoHis.getId());
					existBailAccountInfo.setBailFee(beCombinedBailAccountInfo.getBailFee());
					existBailAccountInfo.setBailFrozenBalance(beCombinedBailAccountInfo.getBailFrozenBalance());
					bailAccountInfoDao.update(existBailAccountInfo);
					
					//被合并保证金账户的资金流出
					BailAccountInfoHis beCombinedBailAccountInfoHis = new BailAccountInfoHis();
					beCombinedBailAccountInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfoHis_NO"));
					beCombinedBailAccountInfoHis.setCreateReason("10");//客户账户合并：被合并保证金账户的资金流出
					bailAccountInfoHisDao.saveHis(beCombinedBailAccountInfoHis, beCombinedBailAccountInfo);
					
					beCombinedBailAccountInfo.setHisSeqId(beCombinedBailAccountInfoHis.getId());
					beCombinedBailAccountInfo.setBailFee(BigDecimal.ZERO);
					beCombinedBailAccountInfo.setBailFrozenBalance(BigDecimal.ZERO);
					bailAccountInfoDao.update(beCombinedBailAccountInfo);
				}else if(existBailAccountInfo == null && beCombinedBailAccountInfo != null){
					//因为被合并的有保证金账户而已存在的没有保证金账户，此时就要新建一个保证金账户给已存在客户
					BailAccountInfo bailAccountInfo = new BailAccountInfo();
					BigDecimal SEQ_CSMSBailAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfo_NO");
					bailAccountInfo.setId(Long.valueOf(SEQ_CSMSBailAccountInfo_NO.toString()));
					bailAccountInfo.setMainId(newMainAccountInfo.getMainId());
					bailAccountInfo.setBailFee(beCombinedBailAccountInfo.getBailFee());//保证金金额
					bailAccountInfo.setBailFrozenBalance(beCombinedBailAccountInfo.getBailFrozenBalance());
					bailAccountInfo.setOperId(unifiedParam.getOperId());
					bailAccountInfo.setPlaceId(unifiedParam.getPlaceId());
					//新增的字段
					bailAccountInfo.setOperName(unifiedParam.getOperName());
					bailAccountInfo.setOperNo(unifiedParam.getOperNo());
					bailAccountInfo.setPlaceName(unifiedParam.getPlaceName());
					bailAccountInfo.setPlaceNo(unifiedParam.getPlaceNo());
					bailAccountInfo.setOperTime(new Date());

					bailAccountInfoDao.saveBailAccount(bailAccountInfo);
				}
				
				
			} else if(AccChangeTypeEnum.deletePrepaidCAddReg.getValue().equals(type)){
				
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord = getBeforeServiceFlowRecord(newMainAccountInfo,serviceFlowRecord);
				AccountFundChange accountFundChange=new AccountFundChange();
				accountFundChange = getBeforeAccountFundChange(newMainAccountInfo, accountFundChange);
				if(addCount == null){
					addCount = BigDecimal.ZERO;
				}
				if(newMainAccountInfo.getFrozenBalance().subtract(addCount).compareTo(new BigDecimal("0"))==-1){
					return false;
				}
				mainAccountInfoHis.setCreateReason("充值登记记录删除");
				
				serviceFlowRecord.setCurrAvailableBalance(new BigDecimal(""+addCount));
				serviceFlowRecord.setCurrFrozenBalance(new BigDecimal(""+addCount));
				
				newMainAccountInfo.setMainId(mainAccountInfo.getMainId());
				newMainAccountInfo.setHisSeqId(mainAccountInfoHis.getId());
				newMainAccountInfo.setAvailableBalance(addCount);
				newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("0"));
				newMainAccountInfo.setFrozenBalance(addCount.negate());
				newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
				newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
				
				serviceFlowRecord.setAfterAvailableBalance(addCount);
				serviceFlowRecord.setBeforeAvailableBalance(addCount);
				serviceFlowRecord.setAfterFrozenBalance(addCount.negate());
				serviceFlowRecord.setBeforeFrozenBalance(addCount.negate());
				serviceFlowRecord.setServicePTypeCode(1);
				serviceFlowRecord.setServiceTypeCode(12);
				serviceFlowRecord.setOperID(unifiedParam.getOperId());
				serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				serviceFlowRecord.setOperName(mainAccountInfo.getOperName());
				serviceFlowRecord.setOperNo(mainAccountInfo.getOperNo());
				serviceFlowRecord.setPlaceName(mainAccountInfo.getPlaceName());
				serviceFlowRecord.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				accountFundChange.setChangeType(AccChangeTypeEnum.deletePrepaidCAddReg.getValue()); 
				accountFundChange.setMemo("充值登记记录删除");
				accountFundChange.setCurrAvailableBalance(addCount);
				accountFundChange.setCurrFrozenBalance(addCount);
				accountFundChange.setAfterAvailableBalance(addCount);
				accountFundChange.setBeforeAvailableBalance(addCount);
				accountFundChange.setAfterFrozenBalance(addCount.negate());
				accountFundChange.setBeforeFrozenBalance(addCount.negate());
				
				accountFundChange.setChgOperID(unifiedParam.getOperId());
				accountFundChange.setChgPlaceID(unifiedParam.getPlaceId());
				//新增的字段
				
				accountFundChange.setOperName(mainAccountInfo.getOperName());
				accountFundChange.setOperNo(mainAccountInfo.getOperNo());
				accountFundChange.setPlaceName(mainAccountInfo.getPlaceName());
				accountFundChange.setPlaceNo(mainAccountInfo.getPlaceNo());
				
				//mainAccountInfoDao.update(newMainAccountInfo);
				if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
					return false;
				}
				mainAccountInfoDaoHis.saveHis(mainAccountInfoHis,newMainAccountInfo);
				accountFundChangeDao.saveChange(accountFundChange);
				serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);
				
			}else if(AccChangeTypeEnum.bailRefundOmsAppFail.getValue().equals(type)){
				
				List<RefundInfo> refundInfoList = unifiedParam.getRefundInfoList();
				Long id = null;
				for(RefundInfo refund: refundInfoList){
					//his
					RefundInfoHis refundInfoHis = new RefundInfoHis();
					id = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
					refundInfoHis.setId(id);
					//refundInfoHis.setCreateDate(new Date());
					refundInfoHis.setCreateReason("保证金退款的营运审批失败");//1:修改
					refundInfoHisDao.saveHis(refundInfoHis, refund);
					//update -> refundInfoList（参数值已经设置，直接update）
					refund.setHisSeqId(refundInfoHis.getId());
					refundInfoDao.updateForRefundInterface(refund);
					
					//账户等信息的更改
					AccountFundChange accountFundChange=new AccountFundChange();
					MainAccountInfo mainAccount = mainAccountInfoDao.findById(refund.getMainAccountId());
					accountFundChange = getBeforeAccountFundChange(mainAccount, accountFundChange);
					//生成主账户历史表
					MainAccountInfoHis mainAccountHis = new MainAccountInfoHis();
					BigDecimal mainAccountHisId = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
					mainAccountHis.setId(Long.valueOf(mainAccountHisId.toString()));
					mainAccountHis.setCreateReason("保证金退款的营运审批失败");
					mainAccountHis.setHisSeqId(mainAccount.getHisSeqId());
					
					accountFundChange.setChangeType("53");//对应资金变动业务操作类型即type
					accountFundChange.setChgOperID(refund.getAuditId());
					//accountFundChange.setChgPlaceID(refund.getPlaceID());
					accountFundChange.setOperName(refund.getAuditName());
					accountFundChange.setOperNo(refund.getAuditNo());
					//accountFundChange.setPlaceName(refund.getPlaceName());
					//accountFundChange.setPlaceNo(refund.getPlaceNo());
					
					//审批状态为 ： 营运中心审核通过
					//相应的把每笔退款记录中的本次退款金额，根据当前记录的账户ID从账户中扣除可退余额（这笔本次退款金额）到退款审批余额中
					//mainAccount.setHisSeqId(mainAccountHis.getId());
					//mainAccountInfoDao.updateAvRefundAndRefundAppByCurrent(mainAccount.getId(), refund.getCurrentRefundBalance());
					
					newMainAccountInfo = new MainAccountInfo();
					newMainAccountInfo.setMainId(mainAccount.getMainId());
					newMainAccountInfo.setHisSeqId(mainAccountHis.getId());
					newMainAccountInfo.setAvailableBalance(new BigDecimal("0"));
					newMainAccountInfo.setAvailableRefundBalance(new BigDecimal("-"+refund.getCurrentRefundBalance()));//扣除可退余额
					newMainAccountInfo.setRefundApproveBalance(new BigDecimal("0"));
					newMainAccountInfo.setFrozenBalance(new BigDecimal("0"));
					newMainAccountInfo.setPreferentialBalance(new BigDecimal("0"));
					if(mainAccountInfoDao.updateBackInt(newMainAccountInfo)==0){
						return false;
					}
					//his
					mainAccountInfoDaoHis.saveHis(mainAccountHis,newMainAccountInfo);
					
					accountFundChange.setMemo("保证金退款的营运审批失败");
					accountFundChange.setCurrAvailableRefundBalance(refund.getCurrentRefundBalance());
					accountFundChange.setBeforeAvailableRefundBalance(refund.getCurrentRefundBalance().negate());
					//新增账户资金变动流水
					accountFundChangeDao.saveChange(accountFundChange);
					
					SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(refund.getBankAccount());
					if(subAccountInfo == null){
						logger.error("数据异常：无法根据保证金退款记录找到子账户信息");
						throw new ApplicationException("数据异常：无法根据保证金退款记录找到子账户信息");
					}
					BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(refund.getMainId());
					if(bailAccountInfo == null){
						logger.error("数据异常：无法找到保证金总账户，客户信息id为："+refund.getMainId());
						throw new ApplicationException("数据异常：无法找到保证金总账户，客户信息id为："+refund.getMainId());
					}
					
					//记录子账户历史表
					Long subAccountInfoHisId = sequenceUtil.getSequenceLong("SEQ_SUBACCOUNTINFOHIS_NO");
					subAccountInfoHisDao.saveSubAccountInfoHis(subAccountInfoHisId, "3", subAccountInfo.getId());
					
					//增加子账户保证金
					if(subAccountInfoDao.updateBail(subAccountInfo.getId(), refund.getCurrentRefundBalance()) == 0){
						logger.error("保证金退款的营运审批失败：子账户保证金增加失败");
						throw new ApplicationException("保证金退款的营运审批失败：子账户保证金增加失败");
					}
					
					//记录主账户保证金历史表
					Long bailAccountInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfoHis_NO");
					BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
					bailAccountInfoHis.setId(bailAccountInfoHisId);
					bailAccountInfoHis.setCreateReason("7");//7.保证金退还审批失败
					bailAccountInfoHisDao.saveHis(bailAccountInfoHis, bailAccountInfo);
					
					bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
					if(bailAccountInfoDao.changeBailFee(refund.getCurrentRefundBalance(), bailAccountInfo) == 0){
						logger.error("保证金总账户的保证金变动失败，保证金总账户id："+bailAccountInfo.getId());
						throw new ApplicationException("保证金总账户的保证金变动失败，保证金总账户id："+bailAccountInfo.getId());
					}
				}
				
			}else{
				return false;
			}
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"账户余额更新失败");
			e.printStackTrace();
			throw new ApplicationException("账户余额更新失败");
		}
	}
	
	
	
	/**
	 * 更新账户余额方法
	 * @param mainAccountInfo
	 * @return
	 */
	private BigDecimal changeBalance(MainAccountInfo mainAccountInfo){
		return (
		mainAccountInfo.getAvailableBalance()
		.add(mainAccountInfo.getFrozenBalance())
		.add(mainAccountInfo.getAvailableRefundBalance())
		.add(mainAccountInfo.getRefundApproveBalance())
		.add(mainAccountInfo.getPreferentialBalance()));
	}
	
	/**
	 * 客户流水更新前
	 * @param newMainAccountInfo
	 * @param serviceFlowRecord
	 * @return
	 */
	private ServiceFlowRecord getBeforeServiceFlowRecord(MainAccountInfo newMainAccountInfo,ServiceFlowRecord serviceFlowRecord){
		if(serviceFlowRecord.getId()==null){
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
			serviceFlowRecord.setId(Long.parseLong(seq.toString()));
		}
		serviceFlowRecord.setClientID(newMainAccountInfo.getMainId());
		serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId().toString());
		
		serviceFlowRecord.setBeforeAvailableBalance(newMainAccountInfo.getAvailableBalance());
		serviceFlowRecord.setBeforeFrozenBalance(newMainAccountInfo.getFrozenBalance());
		serviceFlowRecord.setBeforepreferentialBalance(newMainAccountInfo.getPreferentialBalance());
		serviceFlowRecord.setBeforeAvailableRefundBalance(newMainAccountInfo.getAvailableRefundBalance());
		serviceFlowRecord.setBeforeRefundApproveBalance(newMainAccountInfo.getRefundApproveBalance());
		serviceFlowRecord.setBeforeFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setBeforepreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setBeforeAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setBeforeRefundApproveBalance(new BigDecimal("0"));
		serviceFlowRecord.setBeforeAvailableBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableBalance(new BigDecimal("0"));
		serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("0"));
		serviceFlowRecord.setAfterFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setAfterpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setAfterRefundApproveBalance(new BigDecimal("0"));
		serviceFlowRecord.setIsDoFlag("0");
		serviceFlowRecord.setCreateTime(new Date());
		return serviceFlowRecord;
	}
	
	
	
	/**
	 * 账户资金变动更新前
	 * @param newMainAccountInfo
	 * @param AccountFundChange
	 * @return
	 */
	private AccountFundChange getBeforeAccountFundChange(MainAccountInfo newMainAccountInfo,AccountFundChange accountFundChange){
		BigDecimal SEQ_CSMSAccountFundChange_NO = sequenceUtil.getSequence("SEQ_CSMSAccountFundChange_NO");
		accountFundChange.setId(Long.valueOf(SEQ_CSMSAccountFundChange_NO.toString()));
		accountFundChange.setFlowNo(getFlowNo());
		
		accountFundChange.setBeforeAvailableBalance(new BigDecimal("0"));
		accountFundChange.setBeforeFrozenBalance(new BigDecimal("0"));
		accountFundChange.setBeforepreferentialBalance(new BigDecimal("0"));
		accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setMainId(newMainAccountInfo.getId());
		
		accountFundChange.setCurrAvailableBalance(new BigDecimal("0"));
		accountFundChange.setCurrFrozenBalance(new BigDecimal("0"));
		accountFundChange.setCurrpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setCurrAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setCurrRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setAfterAvailableBalance(new BigDecimal("0"));
		accountFundChange.setAfterFrozenBalance(new BigDecimal("0"));
		accountFundChange.setAfterpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setAfterRefundApproveBalance(new BigDecimal("0"));

		return accountFundChange;
	}
	

	/**
	 * 储值卡状态修改接口
	 * @param  prepaidC、prepaidCHis、serviceFlowRecord
	 * @param  type 0发行 1挂失 2 解挂 3补领 4锁定 5领取新卡 6终止卡 7启用 8停用
	 * @throws ApplicationException
	 * @author zxy
	 */
	public  boolean savePrepaidCState(UnifiedParam unifiedParam) {
		try {
			Date now = new Date();
			String type = unifiedParam.getType();
			CarObuCardInfo carObuCardInfo = unifiedParam.getCarObuCardInfo();
			PrepaidC prepaidC =unifiedParam.getPrepaidC();
			ServiceFlowRecord serviceFlowRecord = unifiedParam.getServiceFlowRecord();
			
			PrepaidC tempPrepaidC  = prepaidCDao.findByPrepaidCNo(prepaidC.getCardNo());
			if(tempPrepaidC==null){
				logger.error("储值卡不存在，状态更新失败");
				throw new ApplicationException("储值卡不存在，状态更新失败");
			}
			//储值卡历史
			PrepaidCHis prepaidCHis = new PrepaidCHis(now, "", tempPrepaidC);
			prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
			//储值卡信息
			tempPrepaidC.setHisSeqID(prepaidCHis.getId());
			//客服流水
			serviceFlowRecord.setId(sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO"));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
			serviceFlowRecord.setBeforeState(tempPrepaidC.getState());
			serviceFlowRecord.setClientID(tempPrepaidC.getCustomerID());
			serviceFlowRecord.setCardTagNO(tempPrepaidC.getCardNo());
			serviceFlowRecord.setOperName(unifiedParam.getOperName());
			serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
			serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
			serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
			serviceFlowRecord.setCreateTime(now);
			serviceFlowRecord.setServicePTypeCode(1);
			serviceFlowRecord.setOperID(unifiedParam.getOperId());
			serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
			if("1".equals(type)){//挂失
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(2);
				 serviceFlowRecord.setCurrState("1");
				 serviceFlowRecord.setAfterState("1");
				 serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				 //储值卡历史
				 prepaidCHis.setGenreason("5");
				 prepaidCDao.saveHis(prepaidCHis);
				 //储值卡
				 tempPrepaidC.setBlackFlag(BlackFlagEnum.black.getValue());
				 tempPrepaidC.setState("1");
				 prepaidCDao.update(tempPrepaidC);
			}else if("2".equals(type)){//解挂
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(3);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				 //储值卡历史
				 prepaidCHis.setGenreason("6");
				 prepaidCDao.saveHis(prepaidCHis);
				 //储值卡
				 carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(tempPrepaidC.getId());
				 if(carObuCardInfo == null)
					 tempPrepaidC.setState("4");
				 else
					 tempPrepaidC.setState("0");
				 tempPrepaidC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 prepaidCDao.update(tempPrepaidC);
			}else if("3".equals(type)){//补领
				//新储值卡
				 PrepaidC newPrepaidC = unifiedParam.getNewPrepaidC();
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(4);
				 serviceFlowRecord.setBeforeState(null);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecord.setCardTagNO(newPrepaidC.getCardNo());
				 serviceFlowRecord.setOldCardTagNO(tempPrepaidC.getCardNo());
				 serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				 //储值卡历史
				 prepaidCHis.setGenreason("1");
				 prepaidCDao.saveHis(prepaidCHis);
				 //储值卡
				 tempPrepaidC.setHisSeqID(prepaidCHis.getId());
				 tempPrepaidC.setState("2");
				 tempPrepaidC.setBind("0");
				 prepaidCDao.update(tempPrepaidC);
				 
				 newPrepaidC.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_NO"));
				 newPrepaidC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 prepaidCDao.save(newPrepaidC);
				 
				 BillGet billGet = billGetDao.findByCardBankNo(tempPrepaidC.getCardNo());
				 if (billGet != null) {
					// 服务方式
					BigDecimal bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
					billGet.setId(Long.valueOf(bill_get_NO.toString()));
					billGet.setCardAccountID(newPrepaidC.getId());
					billGet.setCardBankNo(newPrepaidC.getCardNo());
					billGet.setOperId(newPrepaidC.getSaleOperId());
					billGet.setOperName(newPrepaidC.getOperName());
					billGet.setOperNo(newPrepaidC.getOperNo());
					billGet.setOperTime(new Date());
					billGet.setPlaceId(newPrepaidC.getSalePlaceId());
					billGet.setPlaceName(newPrepaidC.getPlaceName());
					billGet.setPlaceNo(newPrepaidC.getPlaceNo());
					billGetDao.save(billGet);
				 }
				 
			}else if("4".equals(type)){//锁定
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(5);
				 serviceFlowRecord.setCurrState("5");
				 serviceFlowRecord.setAfterState("5");
				 serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				 //储值卡历史
				 prepaidCHis.setGenreason("2");
				 prepaidCDao.saveHis(prepaidCHis);
				 //储值卡
				 if("1".equals(serviceFlowRecord.getIsNeedBlacklist())){
					 tempPrepaidC.setBlackFlag(BlackFlagEnum.black.getValue());
				 }
				 //2017-07-06  说没有锁定的状态，只有注销
				 tempPrepaidC.setState(PrepaidCardStateEnum.cancel.getIndex());
				 //tempPrepaidC.setState("5");
				 prepaidCDao.update(tempPrepaidC);
			}else if("5".equals(type)){//领取新卡
				 //新储值卡
				 PrepaidC newPrepaidC = unifiedParam.getNewPrepaidC();
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(6);
				 serviceFlowRecord.setBeforeState(null);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecord.setCardTagNO(newPrepaidC.getCardNo());
				 serviceFlowRecord.setOldCardTagNO(tempPrepaidC.getCardNo());
				 serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				 
				 //如果领取新卡的时候，新卡状态是正常的，那么说明旧卡之前是正常状态的卡片做旧卡锁定，而不是挂起的，这时就要将绑定标志改为0
				 if(newPrepaidC.getState().equals(PrepaidCardStateEnum.nomal.getIndex())){
					//储值卡历史
					 prepaidCHis.setGenreason("2");
					 prepaidCDao.saveHis(prepaidCHis);
					 //储值卡
					 tempPrepaidC.setHisSeqID(prepaidCHis.getId());
					 //tempPrepaidC.setState("2");
					 tempPrepaidC.setBind("0");
					 prepaidCDao.update(tempPrepaidC);
				 }
				 
				 //新储值卡
				 newPrepaidC.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_NO"));
				 newPrepaidC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 prepaidCDao.save(newPrepaidC);
				 
				 
				 BillGet billGet = billGetDao.findByCardBankNo(tempPrepaidC.getCardNo());
				 if (billGet != null) {
					// 服务方式
					BigDecimal bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
					billGet.setId(Long.valueOf(bill_get_NO.toString()));
					billGet.setCardAccountID(newPrepaidC.getId());
					billGet.setCardBankNo(newPrepaidC.getCardNo());
					billGet.setOperId(newPrepaidC.getSaleOperId());
					billGet.setOperName(newPrepaidC.getOperName());
					billGet.setOperNo(newPrepaidC.getOperNo());
					billGet.setOperTime(new Date());
					billGet.setPlaceId(newPrepaidC.getSalePlaceId());
					billGet.setPlaceName(newPrepaidC.getPlaceName());
					billGet.setPlaceNo(newPrepaidC.getPlaceNo());
					billGetDao.save(billGet);
				 }
				 
				 
			}else if("6".equals(type)){
				serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				PrepaidCBussiness prepaidCBussiness = unifiedParam.getPrepaidCBussiness();
				prepaidCBussinessDao.save(prepaidCBussiness);
				
				//增加卡信息历史记录
				prepaidCHis.setGenreason("3");
				prepaidCDao.saveHis(prepaidCHis);
				
				// 更改储值卡状态
				tempPrepaidC.setHisSeqID(prepaidCHis.getId());
				tempPrepaidC.setState("2");// 更新状态为注销
				tempPrepaidC.setBind("0");
				if(!"0".equals(serviceFlowRecord.getIsNeedBlacklist())){
					tempPrepaidC.setBlackFlag(BlackFlagEnum.black.getValue());
				}
				prepaidCDao.update(tempPrepaidC);
			}else if("7".equals(type)){  //启用
				//客服流水
				serviceFlowRecord.setServiceTypeCode(15);  //储值卡启用
				serviceFlowRecord.setAfterState("0");
				serviceFlowRecord.setCurrState("0");
				serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				//储值卡历史
				 prepaidCHis.setGenreason("15");
				 prepaidCDao.saveHis(prepaidCHis);
				//储值卡
				tempPrepaidC.setState("0");
				//这里没有判断车辆是否已经绑定电子标签，就更新为“已绑定”是不正确的。所以加上了
				if(carObuCardInfo!=null && carObuCardInfo.getTagID()!=null){
					tempPrepaidC.setBind("1");
					tempPrepaidC.setSuit("1");
				}
				tempPrepaidC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				/*//查询上一次停用历史
				PrepaidCHis his = prepaidCDao.findByCardNoAndState(tempPrepaidC.getCardNo(), "14");
				//设置有效截止时间
				tempPrepaidC.setEndDate(his.getEndDate());*/
				/*挂起是不需要改数据库的有效截至时间，只需要把卡片里面的有效截至时间改成有效启用时间就可以。
				所以以下的设置有效截至时间的代码不需要了。*/
				prepaidCDao.update(tempPrepaidC);
			}else if("8".equals(type)){  //停用
				//客服流水
				serviceFlowRecord.setServiceTypeCode(14);  //储值卡停用
				serviceFlowRecord.setAfterState("4");
				serviceFlowRecord.setCurrState("4");
				serviceFlowRecord.setIsNeedBlacklist(prepaidC.getState());  //Action传过来是否下黑名单
				serviceFlowRecordDao.savePrepaidServiceFlow(serviceFlowRecord,tempPrepaidC);
				//储值卡历史
				 prepaidCHis.setGenreason("14");
				 prepaidCDao.saveHis(prepaidCHis);
				if(prepaidC.getState().equals("1")){//下黑名单
					//下了黑名单证明是无卡挂起，无卡挂起就证明不能修改套装标志
					tempPrepaidC.setBlackFlag(BlackFlagEnum.black.getValue());
				}else{
					tempPrepaidC.setSuit("0");
				}
				//储值卡
				tempPrepaidC.setState("4");
				tempPrepaidC.setBind("0");
				/*//设置卡片有效截止时间为有效启用时间
				tempPrepaidC.setEndDate(tempPrepaidC.getStartDate());*/
				/*挂起是不需要改数据库的有效截至时间，只需要把卡片里面的有效截至时间改成有效启用时间就可以。
				所以以下的设置有效截至时间的代码不需要了。*/
				prepaidCDao.update(tempPrepaidC);
			}else {
				logger.error("类型不存在，储值卡状态更新失败");
				throw new ApplicationException("类型不存在，储值卡状态更新失败");
			}
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"储值卡状态更新失败");
			e.printStackTrace();
			throw new ApplicationException("储值卡状态更新失败");
		}
	}

	/**
	 * 记帐卡状态修改接口
	 * 保存客服流水（旧）+记帐卡信息+记帐卡历史信息
	 * @param  accountC、accountCHis、serviceFlowRecord
	 * @param  type 0发行 1挂失 2 解挂 3补领 4锁定 5领取新卡 6终止卡 7启用 8停用 9手工缴纳通行费
	 * @throws ApplicationException
	 * @author zxy
	 */
	@Override
	public boolean saveAccountCState(UnifiedParam unifiedParam) {
		try {
			Date now = new Date();
			String type = unifiedParam.getType();
			CarObuCardInfo carObuCardInfo = unifiedParam.getCarObuCardInfo();
			AccountCInfo accountCInfo =unifiedParam.getAccountCInfo();
			ServiceFlowRecord serviceFlowRecord = unifiedParam.getServiceFlowRecord();
			
			AccountCInfo tempAccountC  = accountCDao.findByCardNo(accountCInfo.getCardNo());
			if(tempAccountC==null){
				logger.error("记帐卡不存在，状态更新失败");
				throw new ApplicationException("记帐卡不存在，状态更新失败");
			}
			//记帐卡历史
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis(now, "", tempAccountC);
			BigDecimal PrePaidC_his_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfohis_NO");
			accountCInfoHis.setId(Long.valueOf(PrePaidC_his_NO.toString()));
			//记帐卡信息
			tempAccountC.setHisSeqId(accountCInfoHis.getId());
			//客服流水
			BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
			serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
			serviceFlowRecord.setBeforeState(tempAccountC.getState());
			serviceFlowRecord.setClientID(tempAccountC.getCustomerId());
			serviceFlowRecord.setCardTagNO(tempAccountC.getCardNo());
			serviceFlowRecord.setOperName(unifiedParam.getOperName());
			serviceFlowRecord.setOperNo(unifiedParam.getOperNo());
			serviceFlowRecord.setPlaceName(unifiedParam.getPlaceName());
			serviceFlowRecord.setPlaceNo(unifiedParam.getPlaceNo());
			serviceFlowRecord.setCreateTime(now);
			serviceFlowRecord.setServicePTypeCode(2);
			serviceFlowRecord.setOperID(unifiedParam.getOperId());
			serviceFlowRecord.setPlaceID(unifiedParam.getPlaceId());
			if("1".equals(type)){//挂失
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(2);
				 serviceFlowRecord.setCurrState("1");
				 serviceFlowRecord.setAfterState("1");
				 serviceFlowRecord.setCardTagNO(tempAccountC.getCardNo());
				 serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				 //记帐卡历史
				 accountCInfoHis.setGenReason("5");
				 accountCDao.saveHis(accountCInfoHis);
				 //记帐卡
				 tempAccountC.setState("1");
				 tempAccountC.setBlackFlag(BlackFlagEnum.black.getValue());
				 accountCDao.update(tempAccountC);
			}else if("2".equals(type)){//解挂
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(3);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecord.setCardTagNO(tempAccountC.getCardNo());
				 serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				 //记帐卡历史
				 accountCInfoHis.setGenReason("6");
				 accountCDao.saveHis(accountCInfoHis);
				 //记帐卡
				 carObuCardInfo = carObuCardInfoDao.findByAccountCID(tempAccountC.getId());
				 if(carObuCardInfo == null)
					 tempAccountC.setState(AccountCardStateEnum.disabled.getIndex());
				 else
					 tempAccountC.setState("0");
				 tempAccountC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 accountCDao.update(tempAccountC);
			}else if("3".equals(type)){//补领
				//新记帐卡
				AccountCInfo newAccountCInfo = unifiedParam.getNewAccountCInfo();
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(5);
				 serviceFlowRecord.setBeforeState(null);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecord.setCardTagNO(newAccountCInfo.getCardNo());
				 serviceFlowRecord.setOldCardTagNO(tempAccountC.getCardNo());
				 serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				 
				 //2017-08-21  新卡要继承旧卡的保证金
				 newAccountCInfo.setBail(tempAccountC.getBail());
				 
				 //记帐卡历史
				 accountCInfoHis.setGenReason("1");
				 accountCDao.saveHis(accountCInfoHis);
				 //记帐卡
				 tempAccountC.setHisSeqId(accountCInfoHis.getId());
				 tempAccountC.setState("2");
				 tempAccountC.setBail(BigDecimal.ZERO); //2017-08-21  新卡要继承旧卡的保证金
				 accountCDao.update(tempAccountC);
				 
				 //新的记帐卡
				 BigDecimal accountC_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfo_NO");
				 newAccountCInfo.setId(Long.valueOf(accountC_NO.toString()));
				 newAccountCInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 accountCDao.save(newAccountCInfo);
			}else if("4".equals(type)){//锁定(state:4)
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(6);
				 serviceFlowRecord.setCurrState("4");
				 serviceFlowRecord.setAfterState("4");
				 serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				 //记帐卡历史
				 accountCInfoHis.setGenReason("2");
				 accountCDao.saveHis(accountCInfoHis);
				 //记帐卡
				 if(!unifiedParam.isResult()){
					 tempAccountC.setBlackFlag(BlackFlagEnum.black.getValue());
				 }
				 //2017-07-06  说没有锁定的状态，只有注销
				 tempAccountC.setState(AccountCardStateEnum.cancel.getIndex());
				 //tempAccountC.setState("4");
				 accountCDao.update(tempAccountC);
			}else if("5".equals(type)){//领取新卡
				 //新记帐卡
				AccountCInfo newAccountCInfo = unifiedParam.getNewAccountCInfo();
				 //客服流水
				 serviceFlowRecord.setServiceTypeCode(7);
				 serviceFlowRecord.setBeforeState(null);
				 serviceFlowRecord.setCurrState("0");
				 serviceFlowRecord.setAfterState("0");
				 serviceFlowRecord.setCardTagNO(newAccountCInfo.getCardNo());
				 serviceFlowRecord.setOldCardTagNO(tempAccountC.getCardNo());
				 serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				 
				 //2017-08-21  新卡要继承旧卡的保证金
				 newAccountCInfo.setBail(tempAccountC.getBail());
				 
				 //记帐卡历史
				 //如果领取新卡的时候，新卡状态是正常的，那么说明旧卡之前是正常状态的卡片做旧卡锁定，而不是挂起的，这时就要将绑定标志改为0.旧卡锁定已是注销
				 if(newAccountCInfo.getState().equals(AccountCardStateEnum.nomal.getIndex())){
					 tempAccountC.setBind("0");
				 }
				 accountCInfoHis.setGenReason("2");
				 accountCDao.saveHis(accountCInfoHis);
				 //记帐卡
				 tempAccountC.setHisSeqId(accountCInfoHis.getId());
				 tempAccountC.setBail(BigDecimal.ZERO);//2017-08-21  新卡要继承旧卡的保证金
				 accountCDao.update(tempAccountC);
				 
				 //新记帐卡
				 BigDecimal accountC_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfo_NO");
				 newAccountCInfo.setId(Long.valueOf(accountC_NO.toString()));
				 newAccountCInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				 accountCDao.save(newAccountCInfo);
			}else if("6".equals(type)){
				serviceFlowRecord.setBeforeState(accountCInfo.getState());
				serviceFlowRecord.setCurrState("3");//本次修改状态为注销
				serviceFlowRecord.setAfterState("3");//修改后状态为注销
				serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				accountCInfoHis.setGenReason("3");
				accountCDao.saveHis(accountCInfoHis);
				
				tempAccountC.setHisSeqId(accountCInfoHis.getId());
				tempAccountC.setState("2"); //注销
				if(!"0".equals(serviceFlowRecord.getIsNeedBlacklist())){//不下发黑名单 
					tempAccountC.setBlackFlag(BlackFlagEnum.black.getValue());
				}
				accountCDao.update(tempAccountC);
			}else if("7".equals(type)){  //启用
				//客服流水
				serviceFlowRecord.setServiceTypeCode(14);  //记帐卡启用
				serviceFlowRecord.setAfterState("0");
				serviceFlowRecord.setCurrState("0");
				serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				accountCInfoHis.setGenReason("13");
				accountCDao.saveHis(accountCInfoHis);
				//储值卡
				tempAccountC.setState("0");
				//这里没有判断车辆是否已经绑定电子标签，就更新为“已绑定”是不正确的。所以加上了
				if(carObuCardInfo!=null && carObuCardInfo.getTagID()!=null){
					tempAccountC.setBind("1");
					tempAccountC.setSuit("1");
				}
				tempAccountC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				
				/*挂起是不需要改数据库的有效截至时间，只需要把卡片里面的有效截至时间改成有效启用时间就可以。
				所以以下的设置有效截至时间的代码不需要了。*/
				/*//查询上一次挂起的历史记录
				AccountCInfoHis his = accountCInfoHisDao.findByCardNoAndState(tempAccountC.getCardNo(), "14");
				//设置有效截止时间
				tempAccountC.setEndDate(his.getEndDate());*/
				
				accountCDao.update(tempAccountC);
			}else if("8".equals(type)){  //停用
				//客服流水
				serviceFlowRecord.setServiceTypeCode(13);  //记帐卡停用
				serviceFlowRecord.setAfterState("3");
				serviceFlowRecord.setCurrState("3");
				serviceFlowRecord.setIsNeedBlacklist(accountCInfo.getState());  //Action传过来是否下黑名单
				serviceFlowRecordDao.saveAccountServiceFlow(serviceFlowRecord,tempAccountC);
				accountCInfoHis.setGenReason("14");
				accountCDao.saveHis(accountCInfoHis);
				//记帐卡
				if(accountCInfo.getState().equals("1")){
					tempAccountC.setBlackFlag(BlackFlagEnum.black.getValue());
				}else{
					tempAccountC.setSuit("0");
				}
				tempAccountC.setState("3");
				tempAccountC.setBind("0");
				//挂起是不需要改数据库的有效截至时间，只需要把卡片里面的有效截至时间改成有效启用时间就可以。
				//tempAccountC.setEndDate(tempAccountC.getStartDate());;
				accountCDao.update(tempAccountC);
			}else if("9".equals(type)){  //手工缴纳通行费
				accountCInfoHis.setGenReason("15");
				accountCDao.saveHis(accountCInfoHis);
				tempAccountC.setBlackFlag(BlackFlagEnum.unblack.getValue());
				accountCDao.update(tempAccountC);
			}else {
				logger.error("类型不存在，记帐卡状态更新失败");
				throw new ApplicationException("类型不存在，记帐卡状态更新失败");
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage()+"记帐卡状态更新失败");
			e.printStackTrace();
			throw new ApplicationException("记帐卡状态更新失败");
		}
	}
	private String getFlowNo(){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		int randomNum1 = (int)((Math.random()*9+1)*10000000);
		int randomNum2 = (int)((Math.random()*9+1)*10000000);
		String flowNo=fomat.format(date)+randomNum1+randomNum2;
		return flowNo;
	}

}
