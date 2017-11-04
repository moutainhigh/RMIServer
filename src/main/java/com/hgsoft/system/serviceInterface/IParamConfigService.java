package com.hgsoft.system.serviceInterface;

import java.util.List;

import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.system.entity.SpecialCostSubclass;
import com.hgsoft.system.entity.SpecialCostType;
import com.hgsoft.utils.Pager;


public interface IParamConfigService {
	public ParamConfig find(ParamConfig paramConfig);
	public List<ParamConfig> findByparamNo(String paramNo);
	public void save(ParamConfig paramConfig);
	public Pager findByPager(Pager pager, ParamConfig paramConfig);
	public void update(ParamConfig paramConfig);
	public void delete(ParamConfig paramConfig);
	public List<ParamConfig> findByparam(ParamConfig paramConfig);
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,String bankNo);
	public List<SpecialCostType> findAllTypes();
	public List<SpecialCostSubclass> findByFatherId(Long id);
	
	public List<OMSParam> findFirstLevel(String omsType,String type);
	public List<OMSParam> findSecondLevel(String replaceType);
	public List<OMSParam> findSecondLevelCard(String replaceType);
	
	public OMSParam findById(long id);
	public List<OMSParam> findStopReason(String omsType,String type);//查找终止使用原因
}
