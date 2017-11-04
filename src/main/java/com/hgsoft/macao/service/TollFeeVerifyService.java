package com.hgsoft.macao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.ProviceSendBoardDao;
import com.hgsoft.macao.dao.MacaoPassageDetailDao;
import com.hgsoft.macao.dao.MacaoTollDao;
import com.hgsoft.macao.entity.MacaoPassageDetail;
import com.hgsoft.macao.serviceInterface.ITollFeeVerifyService;
import com.hgsoft.utils.Pager;

@Service
public class TollFeeVerifyService implements ITollFeeVerifyService{

	@Resource
	private MacaoPassageDetailDao macaoPassageDetailDao;
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	@Resource
	private MacaoTollDao macaoTollDao;
	
	/**
	 * 查找清算的表 TB_MACAOCARDTRADELIST_TMP列表
	 */
	@Override
	public Pager findTollFeeList(Pager pager,MacaoPassageDetail macaoPassageDetail) {
		
		//return macaoPassageDetailDao.findByPager(pager, macaoPassageDetail);
		return macaoTollDao.findTradeListByPager(pager, macaoPassageDetail);
	}
	@Override
	public BigDecimal getTotalTollFee(MacaoPassageDetail macaoPassageDetail) {
		//不该查审核后的明细表
		//return macaoPassageDetailDao.getTotalFee(macaoPassageDetail);
		//应该查TMP表
		return macaoTollDao.getTotalFee(macaoPassageDetail);
	}
	@Override
	public void batchSaveTollFee(MacaoPassageDetail macaoPassageDetail) {
		//1.查找     清算的表 TB_MACAOCARDTRADELIST_TMP列表
		List<Map<String, Object>> tradeList = macaoTollDao.findMacaoCardTradeList(macaoPassageDetail);
		List<Map<String, Object>> balanceList = macaoTollDao.findTradeListByBalanceDate(macaoPassageDetail);
		
		//List<Long> boardlistnoDetails = new ArrayList<Long>();
		//List<Long> boardlistnoResults = new ArrayList<Long>();
		Long boardlistnoDetail = Long.parseLong(System.currentTimeMillis()+"0502");//通行明细的
		Long boardlistnoResult = Long.parseLong(System.currentTimeMillis()+"0501");//汇总的
		if(tradeList!=null && tradeList.size()>0){
			//2.生成    澳门通通行明细表（CSMS_MACAO_PassageDetail） 客服报表数据
			macaoPassageDetailDao.batchSavePassDetail(tradeList, macaoPassageDetail,boardlistnoDetail);
		}
		if(balanceList!=null && balanceList.size()>0){
			//3.生成    澳门通单卡清算表(CSMS_MACAO_CardBalance)  客服报表数据
			macaoPassageDetailDao.batchSaveCardBalance(balanceList, macaoPassageDetail,boardlistnoResult);
		}
		
		
		if(tradeList!=null && tradeList.size()>0){
			//4.推送数据到   发送公告信息表etctolluser.TB_PROVICESENDBOARD    要保存明细与汇总的
			proviceSendBoardDao.saveForDetail(tradeList.size(),boardlistnoDetail);
			proviceSendBoardDao.saveForResult(balanceList.size(),boardlistnoResult);
			
			
			//5.推送数据到  etctolluser.TB_MACAOCARDTRADELIST 清算库单卡通行明细
			macaoTollDao.batchSaveCardTradeList(tradeList, boardlistnoDetail);
			//6.推送数据到 etctolluser.TB_MACAOCARDTRADERESULT  清算库单卡清算结果
			macaoTollDao.batchSaveCardTradeResult(balanceList, boardlistnoResult,macaoPassageDetail);
			//7.删除 TB_MACAOCARDTRADELIST_TMP 列表数据
			macaoTollDao.batchDeleteTMP(tradeList);
		}
		
		//List<Map<String, Object>> list = macaoPassageDetailDao.findByBalanceDate(macaoPassageDetail);
		/*Long boardlistno = System.currentTimeMillis()+0501;
		if(list!=null && list.size()>0){
			macaoPassageDetailDao.batchSave(list, macaoPassageDetail,boardlistno);
		}*/
		//发送公告信息表
		//proviceSendBoardDao.save(list.size(),boardlistno);
		//macaoPassageDetailDao.batchUpdateMacaoPassageDetail(list, macaoPassageDetail);
	}
}
