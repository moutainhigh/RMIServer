package com.hgsoft.obu.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.utils.Pager;

public interface ITagTakeFeeInfoService {
	public List<TagTakeFeeInfo> tagTakeFeeInfoList(TagTakeFeeInfo tagTakeFeeInfo);
	public Pager tagTakeFeeInfoListByPager(Pager pager,String registerName,String modifyName,TagTakeFeeInfo tagTakeFeeInfo,Date modifyStarTime,Date modifyEndTime,Date registStarTime,Date registEndTime);
	public void saveTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo);
	public void updateTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo);
	public TagTakeFeeInfo findById(Long id);
	public void deleteTagTakeFeeInfo(Long id);
	/**
	 * 2017-06-05
	 * 增加银行到账缴款业务记录
	 * @param id
	 * @param bankTransferBussiness
	 * @return void
	 * @throws Exception 
	 */
	public void deleteTagTakeFeeInfo(Long id,BankTransferBussiness bankTransferBussiness) throws Exception;
	public List<Map<String, Object>> findAllCustomers(Customer customer);
	public Long saveAndReturnId(TagTakeFeeInfo tagTakeFeeInfo,BankTransferInfo bankTransferInfo,Map<String,Object> params);
	public Map<String, Object> updateTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo,BankTransferBussiness bankTransferBussiness) ;
}
