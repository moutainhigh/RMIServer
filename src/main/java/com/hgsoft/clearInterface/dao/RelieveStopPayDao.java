package com.hgsoft.clearInterface.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.clearInterface.entity.RelieveStopPayDetail;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;

@Repository
public class RelieveStopPayDao extends BaseDao {
	
	public void save(RelieveStopPay relieveStopPay) {
		StringBuffer sql=new StringBuffer("insert into CSMS_RELIEVE_STOPPAY(");
		sql.append(FieldUtil.getFieldMap(RelieveStopPay.class,relieveStopPay).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(RelieveStopPay.class,relieveStopPay).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void saveDetail(RelieveStopPayDetail relieveStopPayDetail) {
		StringBuffer sql=new StringBuffer("insert into CSMS_RELIEVE_STOPPAY_DETAIL(");
		sql.append(FieldUtil.getFieldMap(RelieveStopPayDetail.class,relieveStopPayDetail).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(RelieveStopPayDetail.class,relieveStopPayDetail).get("valueStr")+")");
		save(sql.toString());
	}
	
	/**
	 * hzw 2017年9月28日10:42:01
	 * @param paymentCardBlacklistRecv
	 * @return
	 * and flag ='"+stopPayFlag+"'
	 */
	public Map<String,Object> getState(String bankAccount,String stopPayFlag){
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		if(bankAccount!=null){
			sql.append("select state from CSMS_RELIEVE_STOPPAY where BACCOUNT =? and state = 0 ");
			list = queryList(sql.toString(),bankAccount);
		}
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> whetherPayToll(PaymentCardBlacklistRecv paymentCardBlacklistRecv, String timestr){
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		if(paymentCardBlacklistRecv!=null){
			if (StringUtil.isNotBlank(paymentCardBlacklistRecv.getAcbAccount())){
				sql.append("select state from CSMS_RELIEVE_STOPPAY where baccount=? " +
						"and to_char(ETCFEESTARTTIME,'YYYYMMDDHH24MISS')=? and flag=2 and state=2 "+
						"order by FEEENDTIME desc");
				list = queryList(sql.toString(),paymentCardBlacklistRecv.getAcbAccount(),timestr);
			}else if (StringUtil.isNotBlank(paymentCardBlacklistRecv.getCardCode())){
				sql.append("select state from CSMS_RELIEVE_STOPPAY where cardno=?  " +
						"and to_char(ETCFEESTARTTIME,'YYYYMMDDHH24MISS')=? and flag=2 and state=2 " +
						"order by FEEENDTIME desc");
				list = queryList(sql.toString(),paymentCardBlacklistRecv.getCardCode(),timestr);
			}
		}
		if(list.size()>0){
			return list.get(0);
		}

		return null;
	}

	/**
	 * 申请解除止付列表
	 * @param pager
	 * @param paymentCardBlacklistRecv
     * @return
     */
	public Pager findApplyRelieveStoppayList(Pager pager, String bankAccount){
		StringBuffer sql = new StringBuffer();
		if(bankAccount!=null){
			sql.append("select cr.userNo,rs.id,rs.BACCOUNT,rs.latefeestarttime," +
					"rs.etcfeestarttime,rs.feeendtime,rs.etcfee,rs.latefee,rs.remark,rs.state,rs.opername,rs.placeName from csms_relieve_stoppay rs " +
					"join CSMS_AccountC_apply aa "+ 
					"on aa.bankAccount=rs.baccount join CSMS_Customer cr on cr.id=aa.CustomerID " +
					"where flag=0 and rs.BACCOUNT = '"+bankAccount+"'order by rs.applyTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}else {
			sql.append("select cr.userNo,rs.id,rs.BACCOUNT,rs.latefeestarttime," +
					"rs.etcfeestarttime,rs.feeendtime,rs.etcfee,rs.latefee,rs.remark,rs.state,rs.opername,rs.placeName from csms_relieve_stoppay rs  " +
					"join CSMS_AccountC_apply aa "+ 
					"on aa.bankAccount=rs.baccount join CSMS_Customer cr on cr.id=aa.CustomerID " +
					"where flag=0 order by rs.applyTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}
		return pager;
	}

	/**
	 * 手工解除止付列表
	 * @param pager
	 * @param paymentCardBlacklistRecv
     * @return
     */
	public Pager findManualRelieveStoppayList(Pager pager, String bankAccount){
		StringBuffer sql = new StringBuffer();
		if(bankAccount!=null){
			sql.append("select cr.userNo,rs.id,rs.BACCOUNT,rs.latefeestarttime," +
					"rs.etcfeestarttime,rs.feeendtime,rs.etcfee,rs.latefee,rs.remark,rs.state,rs.opername,rs.placeName from csms_relieve_stoppay rs  " +
					" join CSMS_AccountC_apply aa "+ 
					"on aa.bankAccount=rs.baccount join CSMS_Customer cr on cr.id=aa.CustomerID " +
					"where rs.flag=1 and rs.BACCOUNT = '"+bankAccount+"'order by rs.applyTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}else {
			sql.append("select cr.userNo,rs.id,rs.BACCOUNT,rs.latefeestarttime," +
					"rs.etcfeestarttime,rs.feeendtime,rs.etcfee,rs.latefee,rs.remark,rs.state,rs.opername,rs.placeName from csms_relieve_stoppay rs  " +
					"join CSMS_AccountC_apply aa "+ 
					"on aa.bankAccount=rs.baccount join CSMS_Customer cr on cr.id=aa.CustomerID " +
					"where rs.flag=1 order by rs.applyTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}
		return pager;
	}

	
	public int[] batchSaveDetail(final List<RelieveStopPayDetail> reDetailList) {
        String sql = "insert into CSMS_RELIEVE_STOPPAY_DETAIL(id,RELIEVESTOPPAYID,bacCount,applyTime,cardNo,lateFeeStartTime,"
        		+ "etcFeeStartTime,feeEndTime,etcFee,lateFee,remark,createTime) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				RelieveStopPayDetail relieveStopPayDetail = reDetailList.get(i);
				if(relieveStopPayDetail.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, relieveStopPayDetail.getId());
				if(relieveStopPayDetail.getRelieveStopPayId()==null)ps.setNull(2, Types.BIGINT);else ps.setLong(2, relieveStopPayDetail.getRelieveStopPayId());
				if(relieveStopPayDetail.getBAccount() ==null)ps.setNull(3, Types.LONGVARCHAR);else ps.setString(3, relieveStopPayDetail.getBAccount());
				if(relieveStopPayDetail.getApplyTime()==null)ps.setNull(4, Types.DATE);else ps.setDate(4, new java.sql.Date(relieveStopPayDetail.getApplyTime().getTime()));
				if(relieveStopPayDetail.getCardNo()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, relieveStopPayDetail.getCardNo());
				if(relieveStopPayDetail.getLateFeeStartTime()==null)ps.setNull(6, Types.DATE);else ps.setTimestamp(6, new java.sql.Timestamp(relieveStopPayDetail.getLateFeeStartTime().getTime()));
				if(relieveStopPayDetail.getEtcFeeStartTime()==null)ps.setNull(7, Types.DATE);else ps.setTimestamp(7, new java.sql.Timestamp(relieveStopPayDetail.getEtcFeeStartTime().getTime()));
				if(relieveStopPayDetail.getFeeEndTime()==null)ps.setNull(8, Types.DATE);else ps.setTimestamp(8, new java.sql.Timestamp(relieveStopPayDetail.getFeeEndTime().getTime()));
				if(relieveStopPayDetail.getEtcFee()==null)ps.setNull(9, Types.DECIMAL);else ps.setBigDecimal(9, relieveStopPayDetail.getEtcFee());
				if(relieveStopPayDetail.getLateFee()==null)ps.setNull(10, Types.DECIMAL);else ps.setBigDecimal(10, relieveStopPayDetail.getLateFee());
				if(relieveStopPayDetail.getRemark()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, relieveStopPayDetail.getRemark());
				if(relieveStopPayDetail.getCreateTime()==null)ps.setNull(12, Types.DATE);else ps.setDate(12, new java.sql.Date(relieveStopPayDetail.getCreateTime().getTime()));
				
			}
			@Override
			public int getBatchSize() {
				 return reDetailList.size();
			}
		});
    }

}
