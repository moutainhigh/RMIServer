package com.hgsoft.obu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class TagBusinessRecordDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	
	//获取 电子标签业务操作记录表 除发行和发行删除记录之外的数据
	public boolean checkDelete(String tagNo){
		String sql = "select * from CSMS_Tag_BusinessRecord where BusinessType not in  (1,8) and tagNo=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		return list.size()>0;
	}
	
	public void save(TagBusinessRecord tagBusinessRecord){
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_BusinessRecord(");
		sql.append(FieldUtil.getFieldMap(TagBusinessRecord.class,tagBusinessRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TagBusinessRecord.class,tagBusinessRecord).get("valueStr")+")");
		save(sql.toString());
		//receiptDao.saveByBussiness(null, null, tagBusinessRecord, null, null);
	}
	
	/**
	 * 电子标签提货金额登记用
	 * @param tagBusinessRecord
	 * @return void
	 */
	public void saveWithOutReceiptDao(TagBusinessRecord tagBusinessRecord){
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_BusinessRecord(");
		sql.append(FieldUtil.getFieldMap(TagBusinessRecord.class,tagBusinessRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TagBusinessRecord.class,tagBusinessRecord).get("valueStr")+")");
		save(sql.toString());
	}

	public List<Map<String, Object>> findAllByTime(ReceiptPrint receiptPrint) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("SELECT	t.realPrice,t .businessType,t . ID,t.TagNo FROM	CSMS_Tag_BusinessRecord t WHERE	t .OPERTIME BETWEEN(TO_DATE('"+sd.format(receiptPrint.getBeginTime())+"', 'yyyy-MM-dd   HH24:mi:ss')) AND (TO_DATE('"+ sd.format(receiptPrint.getEndTime()) +"', 'yyyy-MM-dd   HH24:mi:ss')) AND T .CLIENTID = "+receiptPrint.getCustomerId());
		return queryList(sql.toString());
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update CSMS_Tag_BusinessRecord set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(OPERTIME,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(OPERTIME,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(),settleDay,startTime,endTime);
	}
	
	public TagBusinessRecord findById(Long id) {
		String sql = "select * from CSMS_Tag_BusinessRecord where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		TagBusinessRecord tagBusinessRecord = null;
		if (!list.isEmpty()&&list.size()==1) {
			tagBusinessRecord = new TagBusinessRecord();
			this.convert2Bean(list.get(0), tagBusinessRecord);
		}

		return tagBusinessRecord;
	}
	
	public TagBusinessRecord findByIdTagNo(String tagNo) {
		String sql = "select * from CSMS_Tag_BusinessRecord where businessType='3' and tagNo="+tagNo+" order by id desc ";
		List<Map<String, Object>> list = queryList(sql);
		TagBusinessRecord tagBusinessRecord = null;
		if (!list.isEmpty()&&list.size()==1) {
			tagBusinessRecord = new TagBusinessRecord();
			this.convert2Bean(list.get(0), tagBusinessRecord);
		}

		return tagBusinessRecord;
	}
	
	public Pager findByAssociate(Pager pager,Date starTime ,Date endTime,String no,String type,Long id) {
		StringBuffer sql=new StringBuffer("select c.*,ROWNUM as num  from("
				+"select id,cardNo as no,state as state,receiptPrintTimes,tradeTime as tradeTime,'联营卡'as name ,'1' as type from CSMS_ACCARD_BUSSINESS where customerID="+id
				+" union all "
				+"select id,TagNo as no,BusinessType as state,receiptPrintTimes,OperTime as tradeTime,'电子标签'as name ,'2' as type from CSMS_TAG_BUSINESSRECORD where ClientID="+id
				+")c left join csms_lian_card_info a on c.no = a.cardno where 1=1 ");		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			params.geDate("c.tradeTime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("c.tradeTime", params.getFormatEnd().format(endTime));
		}
		
		if(StringUtil.isNotBlank(no)){
			params.eq("c.no", no);
		}
		if(StringUtil.isNotBlank(type)){
			params.eq("c.type", type);
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by c.tradeTime DESC ");
		return this.findByPages(sql.toString(), pager,Objects);
		
	}
	
	public void updatePrintTimes(Long id){
		StringBuffer sql=new StringBuffer(" update CSMS_TAG_BUSINESSRECORD set receiptPrintTimes =(CASE  WHEN receiptPrintTimes IS NULL THEN 1 ELSE receiptPrintTimes+1 END) where id = ? ");
		saveOrUpdate(sql.toString(),id);
	}
	
	public int checkBussiness(String tagNo){
		StringBuffer sql=new StringBuffer(" select count(1) from csms_tag_businessrecord" +
				" where tagno=? and BusinessType not in(1,3,8,14,19) ");
		return jdbcUtil.getJdbcTemplate().queryForInt(sql.toString(),tagNo);
	}
	
	
}
