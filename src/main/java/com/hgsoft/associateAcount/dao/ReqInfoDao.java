package com.hgsoft.associateAcount.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class ReqInfoDao extends BaseDao {

	public void save(ReqInfo reqInfo) {
		/*StringBuffer sql = new StringBuffer("insert into CSMS_REQ_INFO(");
		sql.append(FieldUtil.getFieldMap(ReqInfo.class, reqInfo).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(ReqInfo.class, reqInfo)
				.get("valueStr") + ")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(ReqInfo.class,reqInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_REQ_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public Pager findByPager(Pager pager, Date startTime, Date endTime,
			ReqInfo reqInfo, Customer customer) {
		StringBuffer sql = new StringBuffer(
				"select id,customerid,accode,operid,reqtime,"
						+ "settime,placeid,CancelTime,filename,useno,serno,memo,hisseqid,ROWNUM AS num from CSMS_REQ_INFO where 1=1 ");
		SqlParamer params = new SqlParamer();
		if (startTime != null) {
			params.geDate("reqtime", params.getFormat().format(startTime));
		}
		if (endTime != null) {
			params.leDate("reqtime", params.getFormatEnd().format(endTime));
		}
		if (customer.getId() != null) {
			params.eq("customerid", customer.getId());
		}
		if (StringUtil.isNotBlank(reqInfo.getAccode())) {
			params.eq("accode", reqInfo.getAccode());
		}
		sql = sql.append(params.getParam());
		Object[] Objects = params.getList().toArray();
		sql.append(" order by reqtime desc ");
		return this.findByPages(sql.toString(), pager, Objects);

	}

	public ReqInfo find(ReqInfo reqInfo) {
		if (reqInfo != null) {
			/*StringBuffer sql = new StringBuffer(
					"select * from CSMS_REQ_INFO where 1=1 ");
			sql.append(FieldUtil.getFieldMap(ReqInfo.class, reqInfo).get(
					"nameAndValueNotNull"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			
			StringBuffer sql = new StringBuffer("select * from CSMS_REQ_INFO ");
			Map map = FieldUtil.getPreFieldMap(ReqInfo.class,reqInfo);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				reqInfo = new ReqInfo();
				this.convert2Bean(list.get(0), reqInfo);
			}
		}
		return reqInfo;

	}

	public void update(ReqInfo reqInfo) {
		/*StringBuffer sql = new StringBuffer("update CSMS_REQ_INFO set ");
		sql.append(FieldUtil.getFieldMap(ReqInfo.class, reqInfo).get(
				"nameAndValue")
				+ " where id=" + reqInfo.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(ReqInfo.class,reqInfo);
		StringBuffer sql=new StringBuffer("update CSMS_REQ_INFO set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),reqInfo.getId());
	}

	public void deleteById(Long id) {
		String sql = "delete from CSMS_REQ_INFO where id=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, id);
	}

	public Pager list(Pager pager, Date starTime, Date endTime,
			LianCardInfo lianCardInfo,Customer customer) {

		String sql = "select u.*,l.name,l.idtype,l.idcode,row_number() over (order by u.canceltime desc) as num 　from csms_req_info u left join csms_lian_card_info l on u.accode=l.cardno where u.canceltime is not null  ";
		SqlParamer params = new SqlParamer();
		if(starTime !=null){
			params.geDate("u.canceltime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("u.canceltime", params.getFormatEnd().format(endTime));
		}
		
		if (StringUtil.isNotBlank(lianCardInfo.getName())) {
			params.like("l.name", "%" + lianCardInfo.getName() + "%");
		}
		if (StringUtil.isNotBlank(lianCardInfo.getIdType())) {
			params.eq("l.idtype", lianCardInfo.getIdType());
		}
		if (StringUtil.isNotBlank(lianCardInfo.getIdCode())) {
			params.eq("l.idcode", lianCardInfo.getIdCode());
		}
		if (StringUtil.isNotBlank(lianCardInfo.getCardNo())) {
			params.eq("u.AcCode", lianCardInfo.getCardNo());
		}
		if(customer.getId()!=null){
			params.eq("u.CustomerID",customer.getId());
		}
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		return this.findByPages(sql, pager, Objects);
	}
	
	public List list(Date starTime, Date endTime, LianCardInfo lianCardInfo,Customer customer) {
		String sql = "select u.*,l.name,l.idtype,l.idcode,ROWNUM as num 　from csms_req_info u left join csms_lian_card_info l on u.accode=l.cardno where u.canceltime is not null  and u.CustomerID ="+customer.getId();
		SqlParamer params = new SqlParamer();
		if(starTime !=null){
			params.geDate("u.canceltime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("u.canceltime", params.getFormatEnd().format(endTime));
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by u.reqtime desc ");
		List<Map<String, Object>> list = queryList(sql.toString(),Objects);
		return list;
	}
}
