package com.hgsoft.customer.myAbstract.entity;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.common.Enum.CustomerStateEnum;
import com.hgsoft.customer.dao.CustomerCombineRecordDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.CustomerCombineRecord;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.utils.SequenceUtil;

@Service
public class CombineExceptionCustomerDataJob {
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private CustomerCombineRecordDao customerCombineRecordDao;
	
	/**
	 * 合并客户信息表信息
	 * @param existCustomer
	 * @param thisCustomer
	 * @return void
	 */
	public void combineCustomerInfo(Customer existCustomer, Customer thisCustomer){
		//已存在客户进入历史
		CustomerHis existCustomerHis = new CustomerHis(existCustomer);
		existCustomerHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_his_NO"));
		existCustomerHis.setGenReason("1");//资料更改
		existCustomerHis.setGenTime(new Date());
		customerHisDao.save(existCustomerHis);
		
		existCustomer.setHisSeqId(existCustomerHis.getId());
		existCustomer.setUpDateTime(new Date());
		
		existCustomer.setOrgan(thisCustomer.getOrgan());
		existCustomer.setIdType(thisCustomer.getIdType());
		existCustomer.setIdCode(thisCustomer.getIdCode());
		existCustomer.setSecondNo(thisCustomer.getSecondNo());
		existCustomer.setSecondName(thisCustomer.getSecondName());
		existCustomer.setUserType(thisCustomer.getUserType());
		existCustomer.setLinkMan(thisCustomer.getLinkMan());
		existCustomer.setMobile(thisCustomer.getMobile());
		existCustomer.setRegisteredCapital(thisCustomer.getRegisteredCapital());
		existCustomer.setShortTel(thisCustomer.getShortTel());
		existCustomer.setEmail(thisCustomer.getEmail());
		existCustomer.setTel(thisCustomer.getTel());
		existCustomer.setZipCode(thisCustomer.getZipCode());
		existCustomer.setAddr(thisCustomer.getAddr());
		
		existCustomer.setHandleFlag("1");//数据迁移相关：异常客户数据是否已处理标志。1：已处理
		customerDao.update4NotNullStr(existCustomer);
		
		
		//被合并客户进入历史
		thisCustomer = customerDao.findById(thisCustomer.getId());
		CustomerHis beCombinedCustomerHis = new CustomerHis(thisCustomer);
		beCombinedCustomerHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_his_NO"));
		beCombinedCustomerHis.setGenReason("2");//注销
		beCombinedCustomerHis.setGenTime(new Date());
		customerHisDao.save(beCombinedCustomerHis);
		//被合并客户注销
		Customer beCombinedCustomer = new Customer();
		beCombinedCustomer.setHisSeqId(beCombinedCustomerHis.getId());
		beCombinedCustomer.setId(thisCustomer.getId());
		beCombinedCustomer.setUpDateTime(new Date());
		beCombinedCustomer.setState(CustomerStateEnum.cancellation.getValue());
		beCombinedCustomer.setHandleFlag("1");//数据迁移相关：异常客户数据是否已处理标志。1：已处理
		customerDao.update4NotNullStr(beCombinedCustomer);
	}
	
	public void saveCustomerCombineRecord(Customer existCustomer, Customer thisCustomer,MainAccountInfo existMainAccountInfo,MainAccountInfo thisMainAccountInfo,CustomerBussiness customerBussiness){
		CustomerCombineRecord customerCombineRecord = new CustomerCombineRecord();
		customerCombineRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMSCUSTOMERCOMBINE_NO"));
		customerCombineRecord.setBeforeCustomerId(thisCustomer.getId());
		customerCombineRecord.setAfterCustomerId(existCustomer.getId());
		
		customerCombineRecord.setBeforeOrgan(thisCustomer.getOrgan());
		customerCombineRecord.setBeforeIdType(thisCustomer.getIdType());
		customerCombineRecord.setBeforeIdCode(thisCustomer.getIdCode());
		customerCombineRecord.setBeforeBalance(thisMainAccountInfo.getBalance());
		customerCombineRecord.setBeforeAvailableBalance(thisMainAccountInfo.getAvailableBalance());
		customerCombineRecord.setBeforePreferentialBalance(thisMainAccountInfo.getPreferentialBalance());
		customerCombineRecord.setBeforeAvailableRefundBalance(thisMainAccountInfo.getAvailableRefundBalance());
		customerCombineRecord.setBeforeRefundApproveBalance(thisMainAccountInfo.getRefundApproveBalance());
		customerCombineRecord.setBeforeFrozenBalance(thisMainAccountInfo.getFrozenBalance());
		
		customerCombineRecord.setAfterOrgan(existCustomer.getOrgan());
		customerCombineRecord.setAfterIdType(existCustomer.getIdType());
		customerCombineRecord.setAfterIdCode(existCustomer.getIdCode());
		customerCombineRecord.setAfterBalance(existMainAccountInfo.getBalance());
		customerCombineRecord.setAfterAvailableBalance(existMainAccountInfo.getAvailableBalance());
		customerCombineRecord.setAfterPreferentialBalance(existMainAccountInfo.getPreferentialBalance());
		customerCombineRecord.setAfterAvailableRefundBalance(existMainAccountInfo.getAvailableRefundBalance());
		customerCombineRecord.setAfterRefundApproveBalance(existMainAccountInfo.getRefundApproveBalance());
		customerCombineRecord.setAfterFrozenBalance(existMainAccountInfo.getFrozenBalance());
		
		customerCombineRecord.setOperId(customerBussiness.getOperId());
		customerCombineRecord.setOperNo(customerBussiness.getOperNo());
		customerCombineRecord.setOperName(customerBussiness.getOperName());
		customerCombineRecord.setPlaceId(customerBussiness.getPlaceId());
		customerCombineRecord.setPlaceNo(customerBussiness.getPlaceNo());
		customerCombineRecord.setPlaceName(customerBussiness.getPlaceName());
		customerCombineRecord.setOperTime(customerBussiness.getCreateTime());
		
		customerCombineRecordDao.save(customerCombineRecord);
	}
	
}
