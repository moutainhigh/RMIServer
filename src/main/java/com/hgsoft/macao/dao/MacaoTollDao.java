package com.hgsoft.macao.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.macao.entity.MacaoPassageDetail;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 给澳门通的单卡通行明细、清算数据
 * @author gsf
 * @date 2017年4月17日
 */
@Repository
public class MacaoTollDao extends EtcTollingBaseDao {
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	/**
	 * 查询TB_MACAOCARDTRADELIST_TMP铭鸿给李海宁的数据
	 * @param pager
	 * @param macaoPassageDetail
	 * @return Pager
	 */
	public Pager findTradeListByPager(Pager pager, MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		//TB_MACAOCARDTRADELIST_TMP铭鸿给李海宁的清算数据
		String sql = "select id,detailno,cardcode,entry,"
				+ "entime,export,extime,toll,realtoll,dealtype,province,balancetime,0 Isverify"
				+ "  from TB_MACAOCARDTRADELIST_TMP where 1=1 ";
		if (macaoPassageDetail != null && macaoPassageDetail.getBalanceDate() != null) {
			sql += " and to_char(balancetime,'yyyy/MM/dd') = '"+format.format(macaoPassageDetail.getBalanceDate())+"'";
		}
		sql = sql + (" order by balancetime desc ");
		return super.findByPages(sql.toString(), pager, null);
	}
	
	/**
	 * 根据TB_MACAOCARDTRADELIST_TMP列表数据获得    单卡清算数据
	 * @param macaoPassageDetail
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> findTradeListByBalanceDate(MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String sql = "select Cardcode, count(id) count,sum(toll) toll,sum(realToll) realToll from TB_MACAOCARDTRADELIST_TMP where 1=1  ";
		if(macaoPassageDetail !=null && macaoPassageDetail.getBalanceDate()!=null){
			//sql+=" and BalanceDate =to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')";
			sql += " and to_char(balancetime,'yyyy/MM/dd') = '"+format.format(macaoPassageDetail.getBalanceDate())+"'";
		}
		sql=sql+(" group by Cardcode ");
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}
	
	
	/**
	 * 根据日期查找清算的TB_MACAOCARDTRADELIST_TMP列表数据，准备存到客服的表：CSMS_MACAO_PassageDetail
	 * @param macaoPassageDetail   
	 * @return
	 * @return List
	 */
	public List<Map<String, Object>> findMacaoCardTradeList(MacaoPassageDetail macaoPassageDetail) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		//TB_MACAOCARDTRADELIST_TMP铭鸿给李海宁的清算数据
		String sql = "select id TransId,detailno,cardcode,entry,"
				+ "entime,export,extime,toll,realtoll,dealtype,province ProvinceCode,balancetime BalanceDate"
				+ "  from TB_MACAOCARDTRADELIST_TMP where 1=1 ";
		if (macaoPassageDetail != null && macaoPassageDetail.getBalanceDate() != null) {
			sql += " and to_char(balancetime,'yyyy/MM/dd') = '"+format.format(macaoPassageDetail.getBalanceDate())+"'";
		}
		sql = sql + (" order by balancetime desc ");
		return queryList(sql);
	}
	
	
	/**
	 * 批量把TB_MACAOCARDTRADELIST_TMP 的生成数据导入etctolluser.TB_MACAOCARDTRADELIST
	 * @param list
	 * @param macaoPassageDetail
	 * @param boardlistno
	 * @return int[]
	 */
	public int[] batchSaveCardTradeList(final List<Map<String, Object>> list,final Long boardlistno) {  
        String sql = "insert into TB_MACAOCARDTRADELIST(id,boardlistno,detailno,cardcode,entry,"
				+ "entime,export,extime,toll,realtoll,dealtype,province,balanceTime,balanceDate)  "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				
				ps.setLong(1, sequenceUtil.getSequenceLong("seq_csmsmacaopassagedetail_no"));//要建一个TB_MACAOCARDTRADELIST的序列号
				ps.setLong(2, boardlistno);//
				ps.setString(3,(String)m.get("detailno"));
				ps.setString(4, (String)m.get("Cardcode"));
				//ps.setLong(5, (Long)m.get("TransId"));
				ps.setString(5, (String)m.get("entry"));
				ps.setDate(6, new java.sql.Date(((Date)m.get("entime")).getTime()));
				ps.setString(7, (String)m.get("export"));
				ps.setDate(8, new java.sql.Date(((Date)m.get("extime")).getTime()));
				ps.setBigDecimal(9, (BigDecimal)m.get("toll"));
				ps.setBigDecimal(10, (BigDecimal)m.get("realtoll"));
				ps.setString(11, (String)m.get("dealtype"));
				ps.setString(12, (String)m.get("ProvinceCode"));
				ps.setTimestamp(13, new java.sql.Timestamp(((Timestamp)m.get("BalanceDate")).getTime()));
				ps.setDate(14, new java.sql.Date(((Date)m.get("BalanceDate")).getTime()));
				//ps.setDate(9, new java.sql.Date(macaoPassageDetail.getBalanceDate().getTime()));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	
	/**
	 * 批量保存   澳门通卡单卡清算结果TB_MACAOCARDTRADERESULT
	 * @param list
	 * @param macaoPassageDetail
	 * @param boardlistno
	 * @return int[]
	 */
	public int[] batchSaveCardTradeResult(final List<Map<String, Object>> list,final Long boardlistno,final MacaoPassageDetail macaoPassageDetail) {  
        String sql = "insert into TB_MACAOCARDTRADERESULT(id,boardlistno,Cardcode,DealNum,DealFee,RealDealFee,BalanceDate)  "
				+ "values(?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				
				ps.setLong(1, sequenceUtil.getSequenceLong("seq_csmsmacaopassagedetail_no"));//要建一个TB_MACAOCARDTRADERESULT的序列号
				ps.setLong(2, boardlistno);
				ps.setString(3, (String)m.get("Cardcode"));
				ps.setBigDecimal(4, (BigDecimal)m.get("count"));
				ps.setBigDecimal(5, (BigDecimal)m.get("toll"));
				ps.setBigDecimal(6, (BigDecimal)m.get("realToll"));
				//ps.setDate(7, new java.sql.Date(((Timestamp)m.get("BalanceDate")).getTime()));
				ps.setDate(7, new java.sql.Date(macaoPassageDetail.getBalanceDate().getTime()));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	/**
	 * 批量删除 TB_MACAOCARDTRADELIST_TMP
	 * @param list 
	 * @return int[]
	 */
	public int[] batchDeleteTMP(final List<Map<String, Object>> list) {  
        String sql = "delete from TB_MACAOCARDTRADELIST_TMP where id=?  ";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String, Object> m = list.get(i);
				
				ps.setBigDecimal(1, (BigDecimal)m.get("TransId"));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	/**
	 * 通行费审核的审核合计
	 * @param macaoPassageDetail
	 * @return BigDecimal
	 */
	public BigDecimal getTotalFee(MacaoPassageDetail macaoPassageDetail) {
		//SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer sql = new StringBuffer("select SUM(realtoll) total from TB_MACAOCARDTRADELIST_TMP ");
		List<Map<String, Object>> list = null;
		if(macaoPassageDetail != null && macaoPassageDetail.getBalanceDate()!=null){
			//sql.append(" GROUP BY balancedate HAVING balancedate=to_date('"+format.format(macaoPassageDetail.getBalanceDate())+"','yyyy/MM/dd')");
			sql.append(" GROUP BY balanceTime HAVING to_char(balanceTime,'yyyymmdd')=to_char(?,'yyyymmdd')");
			list = queryList(sql.toString(),macaoPassageDetail.getBalanceDate());
		}else{
			list = queryList(sql.toString());
		}
		
		BigDecimal total = new BigDecimal("0");
		if(list!=null && list.size()>0){
			total = (BigDecimal)list.get(0).get("total");
		}
		return total;
	}
			
}
