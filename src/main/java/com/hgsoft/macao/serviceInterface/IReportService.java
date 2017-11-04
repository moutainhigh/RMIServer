package com.hgsoft.macao.serviceInterface;

import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;

public interface IReportService {
	public Pager getAccountCInfo(Pager pager,String cardNo,MacaoCardCustomer macaoCardCustomer);
	public Pager getDarkListInfo(Pager pager,String cardNo,String genTime,String endTime,MacaoCardCustomer macaoCardCustomer);
	public Pager findTranData(Pager pager,String startTime,String endTime,String interfaceFlag);
}
