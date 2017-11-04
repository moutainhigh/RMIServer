package com.hgsoft.httpInterface.serviceInterface;

public interface ISpecialCostTypeService {
	public boolean save(Long omsId,String code,String categoryName,String remark,String state,String flag);
	public boolean update(Long omsId,String code,String categoryName,String remark,String state,String flag);
	public boolean delete(Long omsId);
}
