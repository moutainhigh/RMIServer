package com.hgsoft.prepaidC.serviceInterface;

import java.util.List;

import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.utils.Pager;

public interface IAddRegDetailService {
	
	public AddRegDetail findByCardNo(String cardNo,String flag);
	public Integer getAddRegCount(String cardNo,String flag);
	public AddRegDetail findById(Long id);
	public Pager findDetailByAddRegID(Pager pager,Long addRegID);
	public boolean saveCancelAddReg(AddRegDetail addRegDetail);
	public List<AddRegDetail> findDetailByRegID(Long addRegID);
	public int updateFlagRechargeHalf(AddRegDetail addRegDetail);
	public int updateFlagRechargeSuccess(Long businessId);
	public int updateFlagNotRecharge(Long businessId);
	public Integer findDepositCountByAddRegId(Long addRegId);
}
