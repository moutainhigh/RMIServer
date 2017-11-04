package com.hgsoft.customer.serviceInterface;

import com.hgsoft.customer.entity.CarObuCardInfo;

public interface ICarObuCardInfoService {

	public CarObuCardInfo findByTagid(Long id);
	public CarObuCardInfo findByTagNo(String tagNo);
	public CarObuCardInfo findByPrepaidCID(Long id);
	public CarObuCardInfo findByVehicleID(Long id);
	public CarObuCardInfo findByAccountCID(Long id);
	public CarObuCardInfo findByPrepaidCNo(String cardNo);

}
