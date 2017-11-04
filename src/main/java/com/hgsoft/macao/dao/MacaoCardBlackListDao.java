package com.hgsoft.macao.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class MacaoCardBlackListDao extends EtcTollingBaseDao{
	
	public Pager getBlackList(Pager pager,String cardNo,String startTime,String endTime){
		StringBuffer sql = new StringBuffer("select * from TB_MacaoCardBlackList where 1=1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("cardCode", cardNo);
		}
		if(StringUtil.isNotBlank(startTime)){
			sqlp.ge("to_char(gentime,'yyyy/MM/dd')", startTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.le("to_char(gentime,'yyyy/MM/dd')", endTime);
		}
		sql=sql.append(sqlp.getParam());
		sql=sql.append(" order by genTime ");
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	/**
	 * 根据时间获取新一批黑名单流水数据
	 * @param insertTime
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNewBlackList(String insertTime){
		StringBuffer sql = new StringBuffer("select * from TB_MacaoCardBlackList where 1=1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(insertTime)){
			sqlp.gt("to_char(insertTime,'yyyy/MM/dd hh24:mi:ss')", insertTime);
		}
		sql=sql.append(sqlp.getParam());  
		return queryList(sql.toString(),sqlp.getList().toArray());
	}
	
}
