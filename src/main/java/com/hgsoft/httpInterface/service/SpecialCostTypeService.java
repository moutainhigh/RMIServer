package com.hgsoft.httpInterface.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.SpecialCostTypeDao;
import com.hgsoft.httpInterface.entity.SpecialCostType;
import com.hgsoft.httpInterface.serviceInterface.ISpecialCostTypeService;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpecialCostTypeService implements ISpecialCostTypeService {
	
	private static Logger logger = Logger.getLogger(SpecialCostTypeService.class.getName());
	
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private SpecialCostTypeDao specialCostTypeDao;


	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean save(Long omsId,String code,String categoryName,String remark,String state,String flag) {
		boolean result = false;
		try {
			SpecialCostType specialCostType = new SpecialCostType();
			Long specialCostTypeId = Long.valueOf(sequenceUtil.getSequence("SEQ_csmsspecialCostType_NO").toString());
			specialCostType.setId(specialCostTypeId);
			specialCostType.setOmsId(omsId);
			specialCostType.setCode(code);
			specialCostType.setCategoryName(categoryName);
			specialCostType.setRemark(remark);
			specialCostType.setState(state);
			specialCostType.setFlag(flag);
			specialCostTypeDao.save(specialCostType);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用类型添加失败！");
			throw new ApplicationException("营运系统，特殊费用类型添加失败！");
		}
		return result;
	}

	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean update(Long omsId,String code,String categoryName,String remark,String state,String flag) {
		boolean result = false;
		try {
			specialCostTypeDao.update(omsId, code, categoryName, remark, state, flag);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用类型更新失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，特殊费用类型更新失败！");
		}
		return result;
	}

	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean delete(Long omsId) {
		boolean result = false;
		try {
			specialCostTypeDao.delete(omsId);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用类型删除失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，特殊费用类型删除失败！");
		}
		return result;
	}

}