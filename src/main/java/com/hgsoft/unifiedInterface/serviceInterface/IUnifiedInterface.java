package com.hgsoft.unifiedInterface.serviceInterface;

import com.hgsoft.unifiedInterface.util.UnifiedParam;

public interface IUnifiedInterface {

	public boolean saveAccAvailableBalance(UnifiedParam unifiedParam);
	public boolean savePrepaidCState(UnifiedParam unifiedParam);
	public boolean saveAccountCState(UnifiedParam unifiedParam);
}
