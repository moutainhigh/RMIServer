package com.hgsoft.httpInterface.serviceInterface;

import java.util.Map;

import com.hgsoft.utils.Pager;


public interface ICustomerModifyApplyService {
	public Pager list(Pager pager,String oldOrgan,String newOrgan,String oldIdType,String newIdType,String oldIdCode,String newIdCode,String appState,String approverName,String appTime,String createTime);
	public Map<String,Object> detailList(Long id,String rootPath);
	public boolean approval(Long id,String appState,Long approverId,String approverNo,String approverName,String appTime);
}
