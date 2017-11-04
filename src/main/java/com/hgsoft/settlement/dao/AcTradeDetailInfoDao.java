package com.hgsoft.settlement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;

@Repository
public class AcTradeDetailInfoDao extends BaseDao {


    public void batchSaveFromCardInSettleDetail() {
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_ac_trade_detailinfo")
                .append(" (ID, userno, tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, passtype, inprovinceflag, toll, realtoll, discountamount, passprovince, balancetime, bankaccount, gentime, payflag)")
                .append(" SELECT seq_csmsactradedetailinfo.NEXTVAL AS id,userno,tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, passtype, inprovinceflag, toll, realtoll, discountamount, passprovince, balancetime, bankaccount, gentime, payflag")
                .append(" FROM (")
                .append("    SELECT (SELECT userno FROM csms_customer cus INNER JOIN csms_prepaidc sc ON sc.customerid=cus.ID AND sc.cardno=cardcode WHERE ROWNUM=1) AS userno,MIN(a.ID) AS tableid,a.detailno,a.settlemonth,a.cardcode AS cardno,a.cardtype,")
                .append("       a.entime AS entrancetime,a.enstationname AS entrancestationname,(SELECT b.roadname FROM oms_road b WHERE b.roadno=a.enroadid) AS entranceroadname,")
                .append("       a.extime AS exittime, a.exstationname AS exitstationname, (SELECT b.roadname FROM oms_road b WHERE b.roadno=a.exroadid) AS exitroadname,a.postbalance*100 AS postbalance,0 AS onlinetradeno, a.trseq AS offlinetradeno,a.squaddate,a.reckonstatus AS dealstatus,")
                .append("       (SELECT b.passtype FROM oms_road b WHERE b.roadno=a.exroadid) AS passtype, 0 AS inprovinceflag, SUM(a.toll)*100 AS toll, SUM(a.realtoll)*100 AS realtoll, (SUM(a.toll)-SUM(a.realtoll))*100 AS discountamount, '广东' AS passprovince,")
                .append("       a.balancetime, (SELECT aa.bankaccount FROM csms_accountc_apply aa INNER JOIN  CSMS_SubAccount_Info c ON aa.ID=c.applyid INNER JOIN csms_accountc_info b on b.accountid=c.ID AND b.cardno=a.cardcode WHERE ROWNUM=1) AS bankaccount, SYSDATE AS gentime,0 AS payflag")
                .append("    FROM csms_cardinsettledetail a")
                .append("    WHERE a.cardtype=23 ")
                .append("    GROUP BY a.detailno,a.settlemonth,a.cardcode,a.cardtype,a.entime,a.enstationname,a.enroadid,a.extime,a.exstationname,a.exroadid,a.postbalance,a.trseq,a.squaddate,a.reckonstatus,a.balancetime")
                .append(" ) t");


        super.update(sqlBuilder.toString());

        super.update("delete from csms_cardinsettledetail a WHERE a.cardtype=23 ");
    }

    public void batchSaveFromCardOutSettleDetail() {
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_ac_trade_detailinfo")
                .append(" (ID, userno, tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, passtype, inprovinceflag, toll, realtoll, discountamount, passprovince, balancetime, bankaccount, gentime, payflag)")
                .append(" SELECT seq_csmsactradedetailinfo.NEXTVAL as id, (SELECT userno FROM csms_customer cus INNER JOIN csms_prepaidc sc ON sc.customerid=cus.ID AND sc.cardno=a.cardcode WHERE ROWNUM=1) AS userno,a.ID AS tableid,a.serviceproviderid||'-'||a.messageid||'-'||a.transid AS detailno,a.settlemonth,a.cardcode AS cardno,a.cardtype,")
                .append("    a.entime AS entrancetime,a.enstationname AS entrancestationname,(SELECT b.roadname FROM oms_road b WHERE b.roadno=a.enroadid) AS entranceroadname,")
                .append("    a.extime AS exittime, a.exstationname AS exitstationname, (SELECT b.roadname FROM oms_road b WHERE b.roadno=a.exroadid) AS exitroadname,a.postbalance*100 AS postbalance,0 AS onlinetradeno, a.trseq AS offlinetradeno,a.squaddate,a.reckonstatus AS dealstatus,")
                .append("    '00' AS passtype, 0 AS inprovinceflag, a.toll*100 as toll, a.realtoll*100 as realtoll, (a.toll-a.realtoll)*100 AS discountamount, NVL((SELECT briefname FROM tb_participant WHERE PROVINCECODE=substr(exnetno,1,2) FETCH FIRST 1 ROWS ONLY), substr(exnetno,1,2)) AS passprovince,")
                .append("    a.balancetime, (SELECT aa.bankaccount FROM csms_accountc_apply aa INNER JOIN CSMS_SubAccount_Info c ON c.applyid=aa.ID INNER JOIN csms_accountc_info b  on b.accountid=c.ID AND b.cardno=a.cardcode WHERE ROWNUM=1) AS bankaccount, SYSDATE AS gentime,0 AS payflag")
                .append(" FROM csms_cardoutsettledetail a")
                .append(" WHERE a.cardtype=23 ");

        super.update(sqlBuilder.toString());

        super.update("delete FROM csms_cardoutsettledetail a WHERE a.cardtype=23 ");
    }

    public List<AcTradeDetailInfo> findByDetailNoAndStatus(String detailNo, Integer dealStatus) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" select * ")
                .append(" from csms_ac_trade_detailinfo ")
                .append(" where dealstatus=? and detailno=? ");

        return super.queryObjectList(sqlBuilder.toString(), AcTradeDetailInfo.class, dealStatus, detailNo);
    }
    
	public AcTradeDetailInfo findByDetailNoStatus(String detailNo, Integer dealStatus) {
		String sql = "select * from csms_ac_trade_detailinfo where dealstatus=? and detailno=? ";
		List<Map<String, Object>> list = queryList(sql,dealStatus,detailNo);
		AcTradeDetailInfo acTradeDetailInfo = null;
		if (!list.isEmpty()) {
			acTradeDetailInfo = new AcTradeDetailInfo();
			this.convert2Bean(list.get(0), acTradeDetailInfo);
		}

		return acTradeDetailInfo;
	}

    public int updatePayInfo(AcTradeDetailInfo acTradeDetailInfo, Integer oldPayFlag) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" update csms_ac_trade_detailinfo ")
                .append(" set bankaccount=?, bankno=?, comgentime=?,payflag=?, paytime=?, paytype=? ")
                .append(" where payflag=? and detailno=? ");
        return super.update(sqlBuilder.toString(), acTradeDetailInfo.getBankAccount(), acTradeDetailInfo.getBankNo(),
                acTradeDetailInfo.getComGenTime(), acTradeDetailInfo.getPayFlag(), acTradeDetailInfo.getPayTime(),
                acTradeDetailInfo.getPayType(), oldPayFlag, acTradeDetailInfo.getDetailNo());
    }
    /**
     * 批量更新记帐卡交易明细表
     * @param acTradeDetailInfoList
     * @return
     */
	public int[] batchUpdatePayInfo(final List<AcTradeDetailInfo> acTradeDetailInfoList) {
        String sql = "update csms_ac_trade_detailinfo "
        		+ "set bankaccount=?, bankno=?, comgentime=?,payflag=?, paytime=?, paytype=?  "
        		+ "where   detailno=? ";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				AcTradeDetailInfo acTradeDetailInfo = acTradeDetailInfoList.get(i);
				if(acTradeDetailInfo.getBankAccount()==null)ps.setNull(1, Types.VARCHAR);else ps.setString(1, acTradeDetailInfo.getBankAccount());
				if(acTradeDetailInfo.getBankNo() ==null)ps.setNull(2, Types.VARCHAR);else ps.setString(2, acTradeDetailInfo.getBankNo());
				if(acTradeDetailInfo.getComGenTime()==null)ps.setNull(3, Types.DATE);else ps.setDate(3, new java.sql.Date(acTradeDetailInfo.getComGenTime().getTime()));
				if(acTradeDetailInfo.getPayFlag() ==null)ps.setNull(4, Types.INTEGER);else ps.setInt(4, acTradeDetailInfo.getPayFlag());
				if(acTradeDetailInfo.getPayTime()==null)ps.setNull(5, Types.DATE);else ps.setDate(5, new java.sql.Date(acTradeDetailInfo.getPayTime().getTime()));
				if(acTradeDetailInfo.getPayType() ==null)ps.setNull(6, Types.INTEGER);else ps.setInt(6, acTradeDetailInfo.getPayType());
				//if(acTradeDetailInfo.getPayFlag() ==null)ps.setNull(7, Types.INTEGER);else ps.setInt(7, acTradeDetailInfo.getPayFlag());
				if(acTradeDetailInfo.getDetailNo() ==null)ps.setNull(7, Types.VARCHAR);else ps.setString(7, acTradeDetailInfo.getDetailNo());
				
			}
			@Override
			public int getBatchSize() {
				 return acTradeDetailInfoList.size();
			}
		});
    }
	/**
	 * 子账户ID查找记帐卡交易明细对象
	 * @param id
	 * @param startTime
	 * @param nowTime
	 * @return
	 */
	public List<AcTradeDetailInfo> findAcDetail(Long id, String startTime, String nowTime) {
        StringBuilder sqlBuilder = new StringBuilder();
        if (startTime !=null) {
        	 sqlBuilder.append("  SELECT * FROM CSMS_AC_TRADE_DETAILINFO  atd  ")
             .append(" join csms_accountc_info  ac on atd.cardno=ac.cardno and ac.accountid=? ")
             .append(" WHERE  DEALSTATUS=0 AND PAYFLAG=0 ")
        	 .append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>= ?")
        	 .append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')<= ?");
        	 
        	 return super.queryObjectList(sqlBuilder.toString(), AcTradeDetailInfo.class, id,startTime, nowTime);
		}else {
	       	 sqlBuilder.append("  SELECT * FROM CSMS_AC_TRADE_DETAILINFO  atd  ")
	         .append(" join csms_accountc_info  ac on atd.cardno=ac.cardno and ac.accountid=? ")
	         .append(" WHERE  DEALSTATUS=0 AND PAYFLAG=0 ");
	       	 
       	 	return super.queryObjectList(sqlBuilder.toString(), AcTradeDetailInfo.class,id);
		}
	}
	/**
	 * 根据子账户ID统计银行账号下单张记帐卡的所有通行费
	 * @param id
	 * @param startTime
	 * @param nowTime
	 * @return
	 */
	
	public List<Map<String, Object>> findAcCardEtcFee(Long id, String startTime, String nowTime) {
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        if (startTime !=null) {
        	  sql.append("select a.cardNo, realtollAll from (SELECT atd.cardno,sum(REALTOLL) as realtollAll ")
             .append(" FROM CSMS_AC_TRADE_DETAILINFO  atd ")
             .append(" where DEALSTATUS=0 AND PAYFLAG=0  AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>= ?  ")
             .append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')<= ?  group by cardno ) a ")
        	 .append(" join csms_accountc_info  ac on a.cardno=ac.cardno and ac.accountid=? ORDER BY CARDNO DESC ");
        	  
        	  list = queryList(sql.toString(),startTime,nowTime,id);
        	  return list;
		}else {
	      	  sql.append("select a.cardNo, realtollAll from (SELECT atd.cardno,sum(REALTOLL) as realtollAll ")
	          .append(" FROM CSMS_AC_TRADE_DETAILINFO  atd ")
	          .append(" where DEALSTATUS=0 AND PAYFLAG=0 group by cardno ) a  ")
	     	  .append(" join csms_accountc_info  ac on a.cardno=ac.cardno and ac.accountid=? ORDER BY CARDNO DESC ");
	      	  
	     	  list = queryList(sql.toString(),id);
	     	  return list;
		}
	}
	
	/**
	 * 根据子账户ID统计银行账号下某张记帐卡的所有通行费
	 * @param id
	 * @param startTime
	 * @param nowTime
	 * @return
	 */
	
	public List<Map<String, Object>> findAcCardEtcFeeByOne(Long id, String startTime, String nowTime,String cardno) {
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        if (startTime !=null) {
        	  sql.append("select a.cardNo, realtollAll from (SELECT atd.cardno,sum(REALTOLL) as realtollAll ")
             .append(" FROM CSMS_AC_TRADE_DETAILINFO  atd ")
             .append(" where DEALSTATUS=0 AND PAYFLAG=0  AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>= ?  ")
             .append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')<= ?  group by cardno ) a ")
        	 .append(" join csms_accountc_info  ac on a.cardno=ac.cardno and ac.accountid=? and a.cardNo=? ");
        	  
        	  list = queryList(sql.toString(),startTime,nowTime,id,cardno);
        	  return list;
		}else {
	      	  sql.append("select a.cardNo, realtollAll from (SELECT atd.cardno,sum(REALTOLL) as realtollAll ")
	          .append(" FROM CSMS_AC_TRADE_DETAILINFO  atd ")
	          .append(" where DEALSTATUS=0 AND PAYFLAG=0 group by cardno ) a  ")
	     	  .append(" join csms_accountc_info  ac on a.cardno=ac.cardno and ac.accountid=? and a.cardNo=? ");
	      	  
	     	  list = queryList(sql.toString(),id,cardno);
	     	  return list;
		}
	}
}
