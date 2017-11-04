package com.hgsoft.macao.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.macao.dao.MacaoCardBlackListDao;
import com.hgsoft.macao.dao.ReportDao;
import com.hgsoft.macao.entity.SettlementPeriod;
import com.hgsoft.macao.serviceInterface.IMacaoReportService;
import com.hgsoft.macao.serviceInterface.IReportService;
import com.hgsoft.utils.Pager;

@Service
public class MacaoReportService implements IMacaoReportService{
	@Resource
	private ReportDao reportDao;
	@Override
	public List<Map<String, Object>> findSettlementPeriod(String date) {
		
		return reportDao.findSettlementPeriod(date);
	}
	/**
	 * @Descriptioqn:
	 * @return
	 * @author lgm
	 * @date 2017年4月17日
	 */
	@Override
	public List<Map<String, Object>> getAllServiceType() {
		return reportDao.getAllServiceType();
	}
	
	
}
