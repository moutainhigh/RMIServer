package com.hgsoft.account.serviceInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.utils.Pager;

public interface IAccountFundChangeService {
	public void saveAccountFundChangeList(List<Map<String,Object>> list);
	public Pager list(Pager pager,Date starTime,Date endTime,AccountFundChange accountFundChange);
	void saveAccountFundChange(Long mainId,BigDecimal balance, String cardNo);
}
