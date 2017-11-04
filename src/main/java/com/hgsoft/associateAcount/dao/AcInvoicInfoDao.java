package com.hgsoft.associateAcount.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.AcInvoicInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

/**
 * @FileName AcInvoicInfoDao.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月4日 下午2:16:18 
*/
@Repository
public class AcInvoicInfoDao extends BaseDao{

	public Pager findByPager(Pager pager, AcInvoicInfo acInvoicInfo) {
		StringBuffer sql=new StringBuffer("select a.*,ROWNUM AS num from csms_ac_invoic_info a where a.mainid="+acInvoicInfo.getMainId());		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(acInvoicInfo.getAccode())){
			params.eq("accode", acInvoicInfo.getAccode());
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by a.setTime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}

//	public List<Map<String, Object>> findByAcCode(String acCode) {
//		String sql = "select * from csms_ac_invoic_info where accode='"+acCode+"'";
//		return jdbcUtil.getJdbcTemplate().queryForList(sql);
//	}

	public List<Map<String, Object>> timeValidByAcCode(AcInvoicInfo acInvoicInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = sdf.format(acInvoicInfo.getStartTime());
		String endTime = sdf.format(acInvoicInfo.getEndTime());
		String sql = "select * from csms_ac_invoic_info where accode=?"
				+ " and ((startTime<=to_date('"+startTime+"','yyyy-MM-dd') and endTime>=to_date('"+startTime
				+ "','yyyy-MM-dd')) or(startTime>=to_date('"+startTime+"','yyyy-MM-dd') and startTime<=to_date('"+endTime+"','yyyy-MM-dd')))";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,acInvoicInfo.getAccode());
	}

	public void save(AcInvoicInfo acInvoicInfo) {
		/*StringBuffer sql=new StringBuffer("insert into csms_ac_invoic_info(");
		sql.append(FieldUtil.getFieldMap(AcInvoicInfo.class,acInvoicInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AcInvoicInfo.class,acInvoicInfo).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AcInvoicInfo.class,acInvoicInfo);
		StringBuffer sql=new StringBuffer("insert into csms_ac_invoic_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public void delete(AcInvoicInfo acInvoicInfo) {
		String sql = "delete csms_ac_invoic_info where id=?";
		delete(sql,acInvoicInfo.getId());
	}

	public AcInvoicInfo findById(Long id) {
		AcInvoicInfo temp = null;
		if (id != null) {
			StringBuffer sql = new StringBuffer(
					"select * from csms_ac_invoic_info where id=?");
			List<Map<String, Object>> list = queryList(sql.toString(),id);
			if (!list.isEmpty()) {
				temp = new AcInvoicInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public AcInvoicInfo find(AcInvoicInfo acInvoicInfo) {
		AcInvoicInfo temp = null;
		if (acInvoicInfo != null) {
			StringBuffer sql = new StringBuffer("select * from csms_ac_invoic_info");
			Map map = FieldUtil.getPreFieldMap(AcInvoicInfo.class,acInvoicInfo);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new AcInvoicInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

}
