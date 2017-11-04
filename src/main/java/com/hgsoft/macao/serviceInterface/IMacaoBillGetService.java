package com.hgsoft.macao.serviceInterface;

import java.util.Map;

import com.hgsoft.macao.entity.MacaoBillGet;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;

public interface IMacaoBillGetService {
	public Pager findBillGets(Pager pager,MacaoBillGet macaoBillGet);
	public void saveMacaoBillGet(MacaoBillGet macaoBillGet);
	public MacaoBillGet find(MacaoBillGet macaoBillGet);
	public MacaoBillGet findById(Long id);
	public Map<String, String> delete(Long id);
	public MacaoCardCustomer getMacaoCardCustomerInfo(String cardNo);
	public boolean checkCardNo(String cardNo);
}
