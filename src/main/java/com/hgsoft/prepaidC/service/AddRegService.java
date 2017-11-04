package com.hgsoft.prepaidC.service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.common.Enum.AccChangeTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.prepaidC.dao.*;
import com.hgsoft.prepaidC.entity.*;
import com.hgsoft.prepaidC.serviceInterface.IAddRegService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddRegService implements IAddRegService{
	private static final Logger logger = LoggerFactory.getLogger(AddRegService.class);
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private AddRegDao addRegDao;
	@Resource
	private AddRegHisDao addRegHisDao;
	@Resource
	private AddRegDetailHisDao addRegDetailHisDao;
	@Resource
	private AddRegDetailDao addRegDetailDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	
	@Resource
	public void setAddRegDao(AddRegDao addRegDao) {
		this.addRegDao = addRegDao;
	}

	@Override
	public Pager findAddRegByCustomer(Pager pager, Customer customer) {
		return addRegDao.findAddRegByCustomer(pager, customer);
	}

	@Override
	public boolean saveAddRegs(List<Map<String, String>> addRegPairList, Customer customer,long operId,long placeId,String flag,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveAddRegs(addRegPairList,customer, operId, placeId,flag,params);
	}
	
	@Override
	public AddReg findById(Long id) {
		return addRegDao.findById(id);
	}

	@Override
	public boolean updateAddRegs(List<Map<String, String>> addRegPairUpdateList,List<Map<String, String>> addRegPairAddList,AddReg addReg,Customer customer,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.updateAddRegs(addRegPairUpdateList,addRegPairAddList, addReg,customer,params);
	}

	/**
	 * @Descriptioqn:
	 * @param addReg
	 * @return
	 * @author lgm
	 * @date 2017年9月7日
	 */
	@Override
	public Map<String, Object> delete(AddReg addReg,PrepaidCBussiness prepaidCBussiness) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			//充值登记时候的冻结金额流回账户可用余额
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(prepaidCBussiness.getUserid());
			//新增的字段（携带到接口，不能用作mainAccountInfo的update）
			mainAccountInfo.setPlaceId(prepaidCBussiness.getPlaceid());
			mainAccountInfo.setOperId(prepaidCBussiness.getOperid());
			mainAccountInfo.setOperName(prepaidCBussiness.getOperName());
			mainAccountInfo.setOperNo(prepaidCBussiness.getOperNo());
			mainAccountInfo.setPlaceName(prepaidCBussiness.getPlaceName());
			mainAccountInfo.setPlaceNo(prepaidCBussiness.getPlaceNo());
			
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setAddCount(addReg.getTotalFee());
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setType(AccChangeTypeEnum.deletePrepaidCAddReg.getValue());
			unifiedParam.setPlaceId(prepaidCBussiness.getPlaceid());
			unifiedParam.setOperId(prepaidCBussiness.getOperid());
			unifiedParam.setOperName(prepaidCBussiness.getOperName());
			unifiedParam.setOperNo(prepaidCBussiness.getOperNo());
			unifiedParam.setPlaceName(prepaidCBussiness.getPlaceName());
			unifiedParam.setPlaceNo(prepaidCBussiness.getPlaceNo());
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				
				//储值卡业务记录表处理
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));
				prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.addRegDelete.getValue());
				prepaidCBussiness.setRealprice(addReg.getTotalFee());
				prepaidCBussiness.setBusinessId(addReg.getId());//充值登记表id
				
				prepaidCBussinessDao.save(prepaidCBussiness);
				
				//客户服务流水
				Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				//serviceWater.setCardNo(prepaidCBussiness.getCardno());
				serviceWater.setSerType(ServiceWaterSerType.deleteAddReg.getValue());
				serviceWater.setRemark(ServiceWaterSerType.getNameByValue(ServiceWaterSerType.deleteAddReg.getValue()));
				serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
				serviceWater.setOperId(prepaidCBussiness.getOperid());
				serviceWater.setOperNo(prepaidCBussiness.getOperNo());
				serviceWater.setOperName(prepaidCBussiness.getOperName());
				serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
				serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
				serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
				serviceWater.setOperTime(prepaidCBussiness.getTradetime());
				serviceWaterDao.save(serviceWater);
				
				
				List<AddRegDetail> addRegDetailList = addRegDetailDao.findAllByAddRegId(addReg.getId());
				
				//将快速充值登记明细移入历史表
				AddRegDetailHis addRegDetailHis = null;
				if(addRegDetailList != null && addRegDetailList.size() > 0){
					for(AddRegDetail addRegDetail : addRegDetailList){
						addRegDetailHis = new AddRegDetailHis(addRegDetail);
						Long addRegDetailHisId = Long.parseLong(sequenceUtil.getSequence("seq_csmsaddRegDetailHis_no").toString());
						addRegDetailHis.setId(addRegDetailHisId);
						addRegDetailHisDao.save(addRegDetailHis);
					}
				}
				
				//将快速充值登记移入历史表
				AddRegHis addRegHis = new AddRegHis(addReg);
				Long addRegHisId = Long.parseLong(sequenceUtil.getSequence("seq_csmsaddreghis_no").toString());
				addRegHis.setId(addRegHisId);
				addRegHisDao.save(addRegHis);
				
				//删除快速充值登记明细
				addRegDetailDao.deleteByAddRegId(addReg.getId());
				//删除快速充值登记
				addRegDao.deleteById(addReg.getId());
				
				map.put("success", true);
				map.put("message", "删除成功");
				return map;
			}
			
			map.put("success", false);
			map.put("message", "删除失败，账户资金变动失败");
			return map;
		} catch (ApplicationException e) {
			logger.error("充值登记记录删除失败", e);
			throw new ApplicationException(e.getMessage()+"充值登记记录删除失败");
		}
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @param customerId
	 * @return
	 * @author lgm
	 * @date 2017年9月8日
	 */
	@Override
	public AddReg findByIdAndCustomerId(Long id, Long customerId) {
		return addRegDao.findByIdAndCustomerId(id,customerId);
	}

}
