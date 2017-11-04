package com.hgsoft.acms.other.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.CardDelay;

@Repository
public class CardDelayDaoACMS extends BaseDao {
	
	public void updateFlag(Long id){
		String sql = "update csms_card_delay set flag='1' where id='"+id+"'";
		update(sql);
	}
	
	public CardDelay findById(Long id) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_card_delay where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		CardDelay cardDelay = null;
		if(list.size()>0){
			cardDelay = new CardDelay();
			convert2Bean(list.get(0), cardDelay);
		}
		return cardDelay;
	}
	
	public void save(CardDelay cardDelay){
		Map map = FieldUtil.getPreFieldMap(CardDelay.class,cardDelay);
		StringBuffer sql=new StringBuffer("insert into csms_card_delay");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public void updateById(CardDelay cardDelay) {
		String sql = "update csms_card_delay set flag='2' where id="+cardDelay.getId();
		update(sql);
	}
}
