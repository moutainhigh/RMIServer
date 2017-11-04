package com.hgsoft.macao.service;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.hgsoft.exception.ApplicationException;
import org.springframework.stereotype.Service;

import com.hgsoft.macao.dao.MacaoCardBlackListDao;
import com.hgsoft.macao.dao.MacaoReqRecordDao;
import com.hgsoft.macao.dao.ReportDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.serviceInterface.IReportService;
import com.hgsoft.utils.Pager;

@Service
public class ReportService implements IReportService{
	@Resource
	private ReportDao reportDao;
	@Resource
	private MacaoCardBlackListDao macaoCardBlackListDao;
	@Resource
	private MacaoReqRecordDao macaoReqRecordDao;

	@Override
	public Pager getAccountCInfo(Pager pager,String cardNo,MacaoCardCustomer macaoCardCustomer) {
		try {
			return reportDao.getAccountCInfoByCardNo(pager,cardNo,macaoCardCustomer);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getDarkListInfo(Pager pager, String cardNo, String startTime,String endTime,MacaoCardCustomer macaoCardCustomer) {
		try {
			return reportDao.getStopCardBlackList(pager, cardNo, startTime, endTime, macaoCardCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 查询澳门通访问粤通的通讯记录
	 */
	@Override
	public Pager findTranData(Pager pager, String startTime, String endTime, String interfaceFlag) {
		return macaoReqRecordDao.findMacaoReqRecords(pager, startTime, endTime, interfaceFlag);
	}
	
}
