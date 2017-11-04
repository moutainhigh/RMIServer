package com.hgsoft.associateAcount.serviceInterface;

import com.hgsoft.associateAcount.entity.LianCardInfo;

public interface ILianCardInfoService {

	public void save(LianCardInfo lianCardInfo);

	public LianCardInfo find(LianCardInfo lianCardInfo);

	public void update(LianCardInfo lianCardInfo);

}
