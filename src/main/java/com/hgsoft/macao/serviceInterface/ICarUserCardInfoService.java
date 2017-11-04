package com.hgsoft.macao.serviceInterface;

import com.hgsoft.macao.entity.CarUserCardInfo;

public interface ICarUserCardInfoService {
	public CarUserCardInfo findByAccountCId(Long accountCId);
}
