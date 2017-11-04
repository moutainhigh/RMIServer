package com.hgsoft.httpInterface.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.SpecialCostSubclassDao;
import com.hgsoft.httpInterface.entity.SpecialCostSubclass;
import com.hgsoft.httpInterface.serviceInterface.ISpecialCostSubclassService;
import com.hgsoft.utils.SequenceUtil;

@Service
public class SpecialCostSubclassService implements ISpecialCostSubclassService {

	private static Logger logger = Logger.getLogger(SpecialCostSubclassService.class.getName());
	
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private SpecialCostSubclassDao specialCostSubclassDao;

	/**
	 * @Descriptioqn:
	 * @param specialCostType
	 * @param code
	 * @param categoryName
	 * @param charge
	 * @param remark
	 * @param state
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean save(Long omsId,Long specialCostType, String code, String categoryName,BigDecimal charge, String remark, String state,String flag) {
		boolean result = false;
		try {
			SpecialCostSubclass specialCostSubclass = new SpecialCostSubclass();
			Long specialCostSubclassId = Long.valueOf(sequenceUtil.getSequence("seq_csmsspecialCostSubclass_NO").toString());
			specialCostSubclass.setId(specialCostSubclassId);
			specialCostSubclass.setOmsId(omsId);
			specialCostSubclass.setSpecialCostType(specialCostType);
			specialCostSubclass.setCode(code);
			specialCostSubclass.setCategoryName(categoryName);
			specialCostSubclass.setCharge(charge);
			specialCostSubclass.setRemark(remark);
			specialCostSubclass.setState(state);
			specialCostSubclass.setFlag(flag);
			specialCostSubclassDao.save(specialCostSubclass);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用收取子类型新增失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，特殊费用收取子类型新增失败！");
		}
		return result;
	}

	/**
	 * @Descriptioqn:
	 * @param specialCostType
	 * @param code
	 * @param categoryName
	 * @param charge
	 * @param remark
	 * @param state
	 * @param print1
	 * @param print2
	 * @param print3
	 * @param print4
	 * @param print5
	 * @param print6
	 * @param print7
	 * @param print8
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean update(Long omsId,Long specialCostType, String code,String categoryName, BigDecimal charge, String remark, String state,String flag) {
		boolean result = false;
		try {
			specialCostSubclassDao.update(omsId,specialCostType, code, categoryName, charge, remark, state,flag);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用收取子类型更新失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，特殊费用收取子类型更新失败！");
		}
		return result;
	}

	/**
	 * @Descriptioqn:
	 * @param specialCostType
	 * @return
	 * @author lgm
	 * @date 2017年4月1日
	 */
	@Override
	public boolean delete(Long omsId) {
		boolean result = false;
		try {
			specialCostSubclassDao.delete(omsId);
			result = true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，特殊费用收取子类型删除失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，特殊费用收取子类型删除失败！");
		}
		return result;
	}


}