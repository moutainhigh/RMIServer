package com.hgsoft.system.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.system.dao.ParamConfigDao;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.system.entity.SpecialCostSubclass;
import com.hgsoft.system.entity.SpecialCostType;
import com.hgsoft.system.serviceInterface.IParamConfigService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * system constant
 * @author gaosiling
 * 2016-07-19 15:45:28
 *
 */
@Service
public class ParamConfigService implements IParamConfigService {
	
	private static Logger logger = Logger.getLogger(ParamConfigService.class.getName());
	
	@Resource
	private ParamConfigDao paramConfigDao;
	
	@Resource
	private OmsParamDao omsParamDao;
	
	@Resource
	private SequenceUtil sequenceUtil;

	@Override
	public ParamConfig find(ParamConfig paramConfig) {
		return paramConfigDao.find(paramConfig);
	}

	@Override
	public List<ParamConfig> findByparamNo(String paramNo) {
		return paramConfigDao.findByparamNo(paramNo);
	}
	
	@Override
	public Pager findByPager(Pager pager,ParamConfig paramConfig) {
		try {
			return paramConfigDao.findByPager(pager,paramConfig);
		} catch (ApplicationException e) {
			throw new ApplicationException("查操系统参数失败");
		}
	}
	
	@Override
	public void save(ParamConfig paramConfig) {
		Long seq = sequenceUtil.getSequenceLong("seq_csmsparamconfig_no");
		paramConfig.setId(seq);
		paramConfig.setCreateTime(new Date());
		try {
			paramConfigDao.save(paramConfig);
		} catch (ApplicationException e) {
			logger.error("保存系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("保存系统参数失败");
		}
	}
	
	@Override
	public void update(ParamConfig paramConfig) {
		ParamConfig temp = paramConfigDao.findById(paramConfig.getId());
		temp.setParamChName(paramConfig.getParamChName());
		temp.setParamName(paramConfig.getParamName());
		temp.setParamValue(paramConfig.getParamValue());
		temp.setParamValueTwice(paramConfig.getParamValueTwice());
		temp.setState(paramConfig.getState());
		temp.setMemo(paramConfig.getMemo());
		try {
			paramConfigDao.update(temp);
		} catch (ApplicationException e) {
			logger.error("更新系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("更新系统参数失败");
		}
	}

	@Override
	public void delete(ParamConfig paramConfig) {
		try {
			paramConfigDao.delete(paramConfig);
		} catch (ApplicationException e) {
			logger.error("删除系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("删除系统参数失败");
		}
	}
	
	@Override
	public List<ParamConfig> findByparam(ParamConfig paramConfig){
		try {
			return paramConfigDao.findByparamNo(paramConfig);
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}

	@Override
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,
			String bankNo) {
		try {
			return paramConfigDao.findByparamNoAndBankNo(paramNo,bankNo);
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}

	@Override
	public List<SpecialCostType> findAllTypes() {
		 return paramConfigDao.findAllTypes();
	}

	@Override
	public List<SpecialCostSubclass> findByFatherId(Long id) {
		return paramConfigDao.findByFatherId(id);
	}

	
	
	/**
	 * @Descriptioqn:
	 * @param omsType
	 * @param type
	 * @return
	 * @author lgm
	 * @date 2017年3月28日
	 */
	@Override
	public List<OMSParam> findFirstLevel(String omsType,String type) {
		try {
			return omsParamDao.findFirstLevel(omsType, type);
		}catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}

	/**
	 * @Descriptioqn:
	 * @param replaceType
	 * @return
	 * @author lgm
	 * @date 2017年3月28日
	 */
	@Override
	public List<OMSParam> findSecondLevel(String replaceType) {
		try {
			OMSParam omsParam = omsParamDao.findByParamValue(replaceType);
			return omsParamDao.findSecondLevel(omsParam.getOmsId(),"2","2");
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}
	
	/**
	 * @Descriptioqn:
	 * @param replaceType
	 * @return
	 * @author hzw
	 * @date 2017-8-15 16:39:30
	 */
	@Override
	public List<OMSParam> findSecondLevelCard(String replaceType) {
		try {
			OMSParam omsParam = omsParamDao.findByParamValue(replaceType);
			return omsParamDao.findSecondLevel(omsParam.getOmsId(),"2","1");//为卡时，type=1
		} catch (ApplicationException e) {
			logger.error("查询系统参数失败");
			e.printStackTrace();
			throw new ApplicationException("查询系统参数失败");
		}
	}
	/**
	 * 根据公共页面传回来的id
	 * 查询故障类型 故障原因 实体 获取具体的名称
	 */
	@Override
	public OMSParam findById(long id) {
		return omsParamDao.findById(id);
	}
	
	/**
	 * 查询更换或注销原因
	 * hzw
	 */
	@Override
	public List<OMSParam> findStopReason(String omsType,String type) {
		return omsParamDao.findStopReason(omsType, type);
	}
}
