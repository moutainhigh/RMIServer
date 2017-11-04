package com.hgsoft.prepaidC.service;

import com.hgsoft.common.Enum.AddRegDetailStateEnum;
import com.hgsoft.prepaidC.dao.AddRegDetailDao;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.prepaidC.serviceInterface.IAddRegDetailService;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AddRegDetailService implements IAddRegDetailService{
	
	@Resource
	private AddRegDetailDao addRegDetailDao;
	@Resource
	private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
	
	@Resource
	public void setAddRegDetailDao(AddRegDetailDao addRegDetailDao) {
		this.addRegDetailDao = addRegDetailDao;
	}
	
	@Override
	public AddRegDetail findByCardNo(String cardNo,String flag) {
		return addRegDetailDao.findByCardNo(cardNo,flag);
	}
	
	@Override
	public Integer getAddRegCount(String cardNo,String flag) {
		return addRegDetailDao.getAddRegCount(cardNo, flag);
	}
	
	@Override
	public AddRegDetail findById(Long id) {
		return addRegDetailDao.findById(id);
	}
	
	@Override
	public Pager findDetailByAddRegID(Pager pager, Long addRegID) {
		return addRegDetailDao.findDetailByAddRegID(pager, addRegID);
	}
	
	@Override
	public List<AddRegDetail> findDetailByRegID(Long addRegID) {
		return addRegDetailDao.findDetailByRegID(addRegID);
	}

	@Override
	public boolean saveCancelAddReg(AddRegDetail addRegDetail) {
		return prepaidCUnifiedInterfaceService.saveCancelAddReg(addRegDetail);
	}

	@Override
	public int updateFlagRechargeHalf(AddRegDetail addRegDetail) {
		addRegDetail.setFlag(AddRegDetailStateEnum.rechargeHalf.getValue());
		return addRegDetailDao.updateFlag(addRegDetail, AddRegDetailStateEnum.normal.getValue());
	}

	@Override
	public int updateFlagRechargeSuccess(Long businessId) {
		return addRegDetailDao.updateFlagRechargeSuccessByBusinessId(AddRegDetailStateEnum.rechargeSuccess.getValue(), AddRegDetailStateEnum.rechargeHalf.getValue(), businessId);
	}

	@Override
	public int updateFlagNotRecharge(Long businessId) {
		return addRegDetailDao.updateFlagNotRechargeByBusinessId(AddRegDetailStateEnum.normal.getValue(), AddRegDetailStateEnum.rechargeHalf.getValue(), businessId);
	}

	/**
	 * @Descriptioqn:根据快速充值登记表id获取快速充值登记明细表已经充值(充值半条/充值成功)的数量
	 * @param addRegId
	 * @return
	 * @author lgm
	 * @date 2017年9月7日
	 */
	@Override
	public Integer findDepositCountByAddRegId(Long addRegId) {
		List<Map<String,Object>> list = addRegDetailDao.findDepositCountByAddRegId(addRegId);
		return list.size();
	}
}
