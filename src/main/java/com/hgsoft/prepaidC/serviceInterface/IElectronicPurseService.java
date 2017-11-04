package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.prepaidC.entity.ElectronicPurse;

public interface IElectronicPurseService {
	
	public ElectronicPurse findByPrepaidID(Long prepaidID);

	public void saveTransferMoney();

}
