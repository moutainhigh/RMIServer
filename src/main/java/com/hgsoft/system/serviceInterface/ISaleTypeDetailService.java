package com.hgsoft.system.serviceInterface;

import java.util.List;

import com.hgsoft.system.entity.SaleType;
import com.hgsoft.system.entity.SaleTypeDetail;
import com.hgsoft.utils.Pager;

public interface ISaleTypeDetailService {

	public void update(SaleTypeDetail saleTypeDetail);

	public Pager findByPager(Pager pager, SaleTypeDetail saleTypeDetail);

	public List<SaleTypeDetail> findBySaleTypeDetail(SaleTypeDetail paramConfig);
	
	public List<SaleType> findBySaleType(SaleType saleType);

	public List<SaleTypeDetail> findByparamNo(String paramNo);

	public SaleTypeDetail findById(Long id);

	public SaleTypeDetail find(SaleTypeDetail saleTypeDetail);

	public void save(SaleTypeDetail saleTypeDetail);

}
