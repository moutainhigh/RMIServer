package com.hgsoft.httpInterface.serviceInterface;

import java.math.BigDecimal;

public interface ISpecialCostSubclassService {
	public boolean save(Long omsId,Long specialCostType,String code,String categoryName,BigDecimal charge,String remark,String state,String flag);
	public boolean update(Long omsId,Long specialCostType,String code,String categoryName,BigDecimal charge,String remark,String state,String flag);
	public boolean delete(Long omsId);
}
