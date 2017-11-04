package com.hgsoft.prepaidC.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

@Repository
public class PrepaidHalfPayDao extends BaseDao {
	
	@SuppressWarnings("rawtypes")
	public Pager findHalfPayByPage(Pager pager,PrepaidCBussiness prepaidCBussiness,Integer type) {
		StringBuffer sql = new StringBuffer("");
		if(type!=null&&type==3){
			 sql = new StringBuffer("select t.*,ROWNUM as num  from csms_prepaidc_bussiness t where State in(3,19,20) and tradeState=1 and 1=1");
		}else{
			 sql = new StringBuffer("select t.*,ROWNUM as num  from csms_prepaidc_bussiness t where State in(2,3,4) and tradeState=1 and 1=1");
		}
		
		if (prepaidCBussiness != null) {
			//sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by tradeTime desc ");
			return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		}else{
			sql.append(" order by tradeTime desc ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public int findHalf(String  cardno){
		StringBuffer sql = new StringBuffer("select count(id)  from csms_prepaidc_bussiness t where State in(2,3,4,19,20,94) and tradeState=1 and cardno=?");
		return this.count(sql.toString(), cardno);
	}

	public int findNewHalf(PrepaidCBussiness prepaidCBussiness){
		StringBuffer sql = new StringBuffer("select count(id)  from csms_prepaidc_bussiness t where State in (2,3,4,19,20,94) and tradeState!=3 and cardno=? and tradeTime > ?");
		return this.count(sql.toString(), prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
	}

	public int findImHalf(String  cardno){
		StringBuffer sql = new StringBuffer("select count(id)  from csms_prepaidc_bussiness t where State in (3) and tradeState=1 and cardno=?");
		return this.count(sql.toString(), cardno);
	}
}
