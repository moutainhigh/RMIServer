package com.hgsoft.timerTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
@Repository
public class InvoiceChangeFlowTaskDao extends BaseDao{
	
	/**
	 * 获得储值卡卡号
	 * @param cardNo
	 * @param invoiceChangeFlow
	 */
	public List<Map<String,Object>> findCardNos(){
		String sql = "select p.cardNo cardNo  from  csms_prepaidc p where p.state='0'";
		return queryList(sql);
	}
}
