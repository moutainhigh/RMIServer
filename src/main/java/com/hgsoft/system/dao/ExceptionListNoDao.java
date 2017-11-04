package com.hgsoft.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.OmsDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.ExceptionListNo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
@SuppressWarnings("rawtypes")
public class ExceptionListNoDao extends OmsDao{
	
	/**
	 * 追款管理列表分页查询
	 * @param pager
	 * @param exceptionListNo
	 * @return Pager
	 */
	public Pager findByPager(Pager pager, ExceptionListNo exceptionListNo,Long customerId) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select * from ("
				+ "select e.*,a.customerid from OMS_EXCEPTIONLISTNO e "
				+ " join csms_accountc_info a on e.cardno=a.cardno "
				+ " join csms_customer c on c.id=a.customerid "
				+ " union all "
				+ "select e.*,p.customerid from OMS_EXCEPTIONLISTNO e "
				+ " join csms_prepaidc p on e.cardno=p.cardno "
				+ " join csms_customer c on c.id=p.customerid ) where 1=1 "  );
		
		//只查询出来营运已经复核的数据
		params.eq("checkFlag", "2");
		
		if(exceptionListNo != null){
			//params.eq("customerid", chaseMoneyInfo.getCustomerId());
			if(StringUtil.isNotBlank(exceptionListNo.getCardNo())){
				params.eq("cardNo", exceptionListNo.getCardNo());
			}
		}
		if(customerId != null){
			params.eq("customerid", customerId);
		}
		sql.append(params.getParam());
		sql.append(" order by id desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public List<Map<String, Object>> findByCardNo(String cardNo,String type){
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select "+FieldUtil.getFieldMap(ExceptionListNo.class,new ExceptionListNo()).get("nameStr")+" from OMS_EXCEPTIONLISTNO where 1=1 ");
		
		//只查询出来营运已经复核的数据
		params.eq("checkFlag", "2");
		//只查询出来未追款的营运异常流水数据说
		params.eq("finishFlag", "1");
		params.eq("cardType", type);
		
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}
		
		sql.append(params.getParam());
		sql.append(" order by id desc ");
		Object[] Objects= params.getList().toArray();
		return queryList(sql.toString(), Objects);
	}



	public ExceptionListNo findById(Long id) {
		ExceptionListNo temp = null;
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(ExceptionListNo.class,new ExceptionListNo()).get("nameStr")+" from OMS_EXCEPTIONLISTNO where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(),id);
		if (!list.isEmpty()&&list.size()==1) {
			temp = new ExceptionListNo();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public void updateNotNull(ExceptionListNo exceptionListNo) {
		Map map = FieldUtil.getPreFieldMap(ExceptionListNo.class,exceptionListNo);
		StringBuffer sql=new StringBuffer("update OMS_EXCEPTIONLISTNO set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),exceptionListNo.getId());
	}

	public Map<String, Object> findRoadInfo(Long roadId){
		String sql = "select roadno, roadName from oms_road where roadno = ?";
		List<Map<String, Object>> list = queryList(sql, roadId);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> findStationInfo(Long stationId){
		String sql = "select stationno, stationName from oms_station where stationno = ?";
		List<Map<String, Object>> list = queryList(sql, stationId);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> findExceptionType(Long exceptionId){
		String sql = "SELECT * FROM OMS_exDataType WHERE ID = ?";
		List<Map<String, Object>> list = queryList(sql, exceptionId);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
