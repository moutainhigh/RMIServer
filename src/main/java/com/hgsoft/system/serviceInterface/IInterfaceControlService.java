package com.hgsoft.system.serviceInterface;

import java.util.Map;

import com.hgsoft.system.entity.InterfaceControl;

public interface IInterfaceControlService {
	public InterfaceControl findByCode(String code);
	public Map<String, String> update(InterfaceControl interfaceControl);
}
