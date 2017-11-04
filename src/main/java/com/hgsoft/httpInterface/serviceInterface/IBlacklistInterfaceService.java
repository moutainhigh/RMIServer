package com.hgsoft.httpInterface.serviceInterface;

import com.hgsoft.utils.Pager;


public interface IBlacklistInterfaceService {

	public Pager blacklistList(Pager pager,String userNo,String organ,String cardNo,String cardType);
	public String requestBlacklist(String param);
	public Pager queryBlacklist(Pager pager,String cardNo,String cardType,String startGenDate,String endGenDate,String state);
	public String removeFromBlacklist(String param);
	
}