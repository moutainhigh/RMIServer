package com.hgsoft.associateAcount.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.associateAcount.entity.AcInvoicInfo;
import com.hgsoft.utils.Pager;

/**
 * @FileName IAcInvoicInfoService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月4日 下午2:02:50 
*/
public interface IAcInvoicInfoService {

	Pager findByPager(Pager pager, AcInvoicInfo acInvoicInfo);

	List<Map<String, Object>> timeValidByAcCode(AcInvoicInfo acInvoicInfo);
	
	void save(AcInvoicInfo acInvoicInfo);
	
	void saveCancelById(AcInvoicInfo acInvoicInfo);
	
	AcInvoicInfo findById(Long id);
}
