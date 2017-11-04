package com.hgsoft.httpInterface.serviceInterface;

import com.hgsoft.system.entity.OMSMinusBank;


public interface IMinusBankInterfaceService {
	public boolean add(OMSMinusBank omsMinusBank);
	public void delete(Long id);
	public void update(OMSMinusBank omsMinusBank);
}