package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.ClearBaseDao;

@Component
public class TbScreturnSendDao extends ClearBaseDao  {
	
	public List<Map<String, Object>>  findByCardNo(String cardNo){
		String sql="select * from tb_screturn_send where cardNo=? order by returnTime desc";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		return list;
	}
	public void deleteById(Long id){
		String sql="delete from tb_screturn_send where id="+id;
		saveOrUpdate(sql);
	}
}
