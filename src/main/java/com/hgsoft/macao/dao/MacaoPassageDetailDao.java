package com.hgsoft.macao.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.macao.entity.MacaoPassageDetail;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class MacaoPassageDetailDao extends BaseDao{
	@Resource
	private SequenceUtil sequenceUtil;
	
	/*public Pager findByPager(Pager pager, MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String sql = "select * from CSMS_MACAO_PassageDetail where 1=1";
		if(macaoPassageDetail !=null && macaoPassageDetail.getBalanceDate()!=null){
			sql+=" and BalanceDate =to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')";
		}
		sql=sql+(" order by BalanceDate desc ");
		return super.findByPages(sql.toString(), pager, null);
	}*/
	
	/*public List<Map<String,Object>> findByBalanceDate(MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String sql = "select Cardcode, count(id) count,sum(toll) toll,sum(realToll) realToll from CSMS_MACAO_PassageDetail where 1=1 and ISVERIFY = 0 ";
		if(macaoPassageDetail !=null && macaoPassageDetail.getBalanceDate()!=null){
			sql+=" and BalanceDate =to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')";
		}
		sql=sql+(" group by Cardcode ");
		List<Map<String, Object>> list = queryList(sql, null);
		return list;
	}*/

	public BigDecimal getTotalFee(MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer sql = new StringBuffer("select SUM(realtoll) total from CSMS_MACAO_PassageDetail");
		
		if(macaoPassageDetail != null && macaoPassageDetail.getBalanceDate()!=null){
			sql.append(" GROUP BY balancedate HAVING balancedate=to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')");
		}
		List<Map<String, Object>> list = queryList(sql.toString(), null);
		BigDecimal total = new BigDecimal("0");
		if(list!=null && list.size()>0){
			total = (BigDecimal)list.get(0).get("total");
		}
		return total;
	}

	/**
	 * 批量保存   澳门通单卡清算表(CSMS_MACAO_CardBalance)
	 * @param list
	 * @param macaoPassageDetail
	 * @param boardlistno
	 * @return int[]
	 */
	public int[] batchSaveCardBalance(final List<Map<String, Object>> list,final MacaoPassageDetail macaoPassageDetail,final Long boardlistno) {  
        String sql = "insert into CSMS_MACAO_CardBalance(id,boardlistno,Cardcode,TransId,DealNum,DealFee,RealDealFee,"
        		+ "Total,BalanceDate,isSet)  "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				ps.setLong(1, sequenceUtil.getSequenceLong("seq_csmsmacaopassagedetail_no"));
				ps.setLong(2, boardlistno);
				ps.setString(3, (String)m.get("Cardcode"));
				ps.setLong(4, 11L);
				ps.setBigDecimal(5, (BigDecimal)m.get("count"));
				ps.setBigDecimal(6, (BigDecimal)m.get("toll"));
				ps.setBigDecimal(7, (BigDecimal)m.get("realToll"));
				ps.setBigDecimal(8, (BigDecimal)m.get("realToll"));
				ps.setDate(9, new java.sql.Date(macaoPassageDetail.getBalanceDate().getTime()));
				ps.setString(10, "1");
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	/*public int[] batchUpdateMacaoPassageDetail(final List<Map<String, Object>> list,final MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String sql = "update csms_macao_passagedetail set Isverify = '1' where cardcode=? and "
        		+ "BalanceDate =to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				ps.setString(1, (String)m.get("cardCode"));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }*/
	
	
	/**
	 * 批量把TB_MACAOCARDTRADELIST_TMP 列表导入csms_macao_passagedetail（客服粤通卡通行明细表）
	 * @param list
	 * @param macaoPassageDetail
	 * @param boardlistno
	 * @return int[]
	 */
	public int[] batchSavePassDetail(final List<Map<String, Object>> list,final MacaoPassageDetail macaoPassageDetail,final Long boardlistno) {  
		//final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH24:mi:ss");
		String sql = "insert into csms_macao_passagedetail(id,boardlistno,detailno,cardcode,TransId,entry,"
				+ "entime,export,extime,toll,realtoll,dealtype,ProvinceCode,BalanceDate,isSet,Isverify)  "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				ps.setLong(1, sequenceUtil.getSequenceLong("seq_csmsmacaopassagedetail_no"));
				System.out.println("boardlistno:"+boardlistno);
				ps.setLong(2, boardlistno);//
				ps.setString(3,(String)m.get("detailno"));
				ps.setString(4, (String)m.get("Cardcode"));
				ps.setBigDecimal(5, (BigDecimal)m.get("TransId"));
				ps.setString(6, (String)m.get("entry"));
				//System.out.println((Date)m.get("entime"));
				//System.out.println(new java.sql.Date(((Date)m.get("entime")).getTime()));
				Date enTime = new Date(((Date)m.get("entime")).getTime());
				Date exTime = new Date(((Date)m.get("extime")).getTime());
				Date bTime = new Date(((Date)m.get("BalanceDate")).getTime());
				System.out.println(enTime);
				ps.setDate(7, new java.sql.Date(enTime.getTime()));
				ps.setString(8, (String)m.get("export"));
				ps.setDate(9, new java.sql.Date(exTime.getTime()));
				ps.setBigDecimal(10, (BigDecimal)m.get("toll"));
				ps.setBigDecimal(11, (BigDecimal)m.get("realtoll"));
				ps.setString(12, (String)m.get("dealtype"));
				ps.setString(13, (String)m.get("ProvinceCode"));
				ps.setDate(14, new java.sql.Date(bTime.getTime()));
				ps.setString(15, "1");//已发送
				ps.setString(16, "1");//已审核
				//ps.setDate(9, new java.sql.Date(macaoPassageDetail.getBalanceDate().getTime()));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
}
