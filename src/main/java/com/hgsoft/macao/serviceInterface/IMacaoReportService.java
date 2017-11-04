package com.hgsoft.macao.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.macao.entity.SettlementPeriod;


public interface IMacaoReportService {
	public List<Map<String, Object>> findSettlementPeriod(String date);
	public List<Map<String,Object>> getAllServiceType();
}
