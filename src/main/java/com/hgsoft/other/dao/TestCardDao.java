package com.hgsoft.other.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.TestCard;
import com.hgsoft.utils.Pager;
@Repository
public class TestCardDao extends BaseDao {
	
	public Pager findByPager(Pager pager, TestCard testCard) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID desc) as num  from TB_TESTCARD_SEND t where 1=1");
		if (testCard != null) {
//			if(StringUtil.isNotBlank(testCard.getCheckFlag())){
//				sql.append(" and t.CheckFlag ='"+testCard.getCheckFlag()+"'");//list查询功能
//			}
		}
		sql.append(" order by t.id desc");
		return this.findByPages(sql.toString(), pager, null);
	}
	public void save(TestCard testCard) {
		Map map = FieldUtil.getPreFieldMap(TestCard.class, testCard);
		StringBuffer sql = new StringBuffer("insert into TB_TESTCARD_SEND");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
	}
	
	
	public TestCard findById(Long id) {
		String sql = "select * from TB_TESTCARD_SEND where id =? ";
		List<Map<String, Object>> list = queryList(sql,id);
		TestCard testCard = null;
		if (!list.isEmpty()) {
			testCard = new TestCard();
			this.convert2Bean(list.get(0), testCard);
		}

		return testCard;
	}
	
	
	public void update(TestCard testCard) {
		Map map = FieldUtil.getPreFieldMap(TestCard.class, testCard);
		StringBuffer sql = new StringBuffer("update TB_TESTCARD_SEND set ");
		sql.append(map.get("updateNameStr")+" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), testCard.getId());
		
	}
	
/*	public int findByPK(TestCard joinCard) {
		String sql = "select count (1) from  TB_TESTCARD_SEND where "
				+ " projectNo = '"+ joinCard.getProjectNo()+"' and carType = '"+joinCard.getCarType()+"'";
		return super.count(sql);
	}*/
	
	
	
}
