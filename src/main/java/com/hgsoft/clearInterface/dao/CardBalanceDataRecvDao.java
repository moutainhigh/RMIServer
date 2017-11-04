package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.hgsoft.common.dao.ClearBaseDao;
@Component
public class CardBalanceDataRecvDao extends ClearBaseDao {
	public List<Map<String,Object>> findAll(){
		String sql = "select * from TB_CARDBALANCEDATA_RECV";
		return queryList(sql);
	}
	
}
