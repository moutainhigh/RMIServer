package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class ScTradeDetailInfoDao extends BaseDao {

    public void batchSaveFromScAddSure() {
        //将未处理的更新为正在处理状态
        String sql = "UPDATE csms_sc_add_sure SET genscdetailflag=1 WHERE ((tradetype='01' AND State='3') OR (moneyreq!=0 AND tradetype='02' AND State='4'))  AND genscdetailflag=0 ";
        super.update(sql);

        //对正在处理状态的充值记录开始生成充值记录的清单
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_sc_trade_detailinfo")
                .append(" (ID, userno, tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, beforebalance,postbalance,onlinetradeno,offlinetradeno, squaddate,dealstatus, tradetype, costamt, credited, passoughtamt, passprovince, inprovinceflag, balancetime, gentime) ")
                .append(" SELECT seq_csmssctradedetailinfo.NEXTVAL AS ID,(SELECT userno FROM csms_customer cus inner join csms_prepaidc sc on cus.id=sc.customerid and sc.cardno=t.cardno where rownum=1) AS userno,tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, beforebalance,postbalance,onlinetradeno,offlinetradeno,squaddate, dealstatus, tradetype, costamt, credited, passoughtamt, passprovince, inprovinceflag, balancetime, gentime")
                .append(" FROM (")
                .append("    SELECT ID AS tableid,NULL AS detailno,NULL AS settlemonth, cardno, 22 AS cardtype,")
                .append("    NULL AS entrancetime, NULL entrancestationname, NULL AS entranceroadname,")
                .append("    timereq AS exittime, (SELECT NAME FROM oms_custompoint WHERE code=placenoreq FETCH FIRST 1 ROWS ONLY) AS exitstationname, NULL AS exitroadname, balreq as beforebalance, balsur as postbalance,onlinetradenoreq as onlinetradeno,trim(to_char(nvl(offlinetradenoreq,0), 'XXXXXXXX')) AS offlinetradeno,timereq as squaddate, 0 AS dealstatus,")
                .append("    'a1' AS tradetype, 0 AS costamt, moneyreq AS credited, 0 AS passoughtamt, NULL AS passprovince,")
                .append("    0 AS inprovinceflag, timesur AS balancetime, SYSDATE AS gentime")
                .append("    FROM csms_sc_add_sure WHERE moneyreq!=0 AND tradetype='01' AND State='3' AND genscdetailflag=1")
                .append("    UNION ALL")
                .append("    SELECT ID AS tableid,NULL AS detailno,NULL AS settlemonth, cardno, 22 AS cardtype,")
                .append("    NULL AS entrancetime, NULL entrancestationname, NULL AS entranceroadname,")
                .append("    timereq AS exittime, (SELECT NAME FROM oms_custompoint WHERE code=placenoreq FETCH FIRST 1 ROWS ONLY) AS exitstationname, NULL AS exitroadname, balreq as beforebalance,balsur as postbalance,onlinetradenoreq as onlinetradeno,trim(to_char(nvl(offlinetradenoreq,0), 'XXXXXXXX')) AS offlinetradeno,timereq as squaddate, 0 AS dealstatus,")
                .append("    'a5' AS tradetype, 0 AS costamt, returnmoneyreq AS credited, 0 AS passoughtamt, NULL AS passprovince,")
                .append("    0 AS inprovinceflag, timesur AS balancetime, SYSDATE AS gentime")
                .append("    FROM csms_sc_add_sure WHERE returnmoneyreq!=0 AND tradetype='01' AND State='3' AND genscdetailflag=1")
                .append("    UNION ALL")
                .append("    SELECT ID AS tableid,NULL AS detailno,NULL AS settlemonth, cardno, 22 AS cardtype,")
                .append("    NULL AS entrancetime, NULL entrancestationname, NULL AS entranceroadname,")
                .append("    timereq AS exittime, (SELECT NAME FROM oms_custompoint WHERE code=placenoreq FETCH FIRST 1 ROWS ONLY) AS exitstationname, NULL AS exitroadname, balreq as beforebalance, balsur as postbalance,onlinetradenoreq as onlinetradeno,trim(to_char(nvl(offlinetradenoreq,0), 'XXXXXXXX')) AS offlinetradeno,timereq as squaddate, 0 AS dealstatus,")
                .append("    'a3' AS tradetype, 0 AS costamt, transfersumreq AS credited, 0 AS passoughtamt, NULL AS passprovince,")
                .append("    0 AS inprovinceflag, timesur AS balancetime, SYSDATE AS gentime")
                .append("    FROM csms_sc_add_sure WHERE transfersumreq!=0 AND tradetype='01' AND State='3' AND genscdetailflag=1")
                .append("    UNION ALL")
                .append("    SELECT ID AS tableid,NULL AS detailno,NULL AS settlemonth, cardno, 22 AS cardtype,")
                .append("    NULL AS entrancetime, NULL entrancestationname, NULL AS entranceroadname,")
                .append("    timereq AS exittime, (SELECT NAME FROM oms_custompoint WHERE code=placenoreq FETCH FIRST 1 ROWS ONLY) AS exitstationname, NULL AS exitroadname, balreq as beforebalance, balsur as postbalance,onlinetradenoreq as onlinetradeno,trim(to_char(nvl(offlinetradenoreq,0), 'XXXXXXXX')) AS offlinetradeno,timereq as squaddate, 0 AS dealstatus,")
                .append("    'a2' AS tradetype, 0 AS costamt, moneyreq AS credited, 0 AS passoughtamt, NULL AS passprovince,")
                .append("    0 AS inprovinceflag, timesur AS balancetime, SYSDATE AS gentime")
                .append("    FROM csms_sc_add_sure WHERE moneyreq!=0 AND tradetype='02' AND State='4' AND genscdetailflag=1")
                .append("    ORDER BY tableid")
                .append(" ) t");
        super.update(sqlBuilder.toString());

        //将正在处理状态更新为已处理完
        sql = "UPDATE csms_sc_add_sure SET genscdetailflag=2 WHERE genscdetailflag=1";
        super.update(sql);
    }

    public void batchSaveFromCardInSettleDetail() {
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_sc_trade_detailinfo")
                .append(" (ID, userno, tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, tradetype, costamt, credited, passoughtamt, passprovince, inprovinceflag, balancetime, gentime) ")
                .append(" SELECT seq_csmssctradedetailinfo.NEXTVAL AS id, userno,tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, tradetype, costamt, credited, passoughtamt, passprovince, inprovinceflag, balancetime, gentime")
                .append(" FROM (")
                .append("    SELECT (SELECT userno FROM csms_customer cus INNER JOIN csms_prepaidc sc ON sc.customerid=cus.ID AND sc.cardno=a.cardcode WHERE ROWNUM=1) AS userno,MIN(a.ID) AS tableid,a.detailno,a.settlemonth,a.cardcode AS cardno,a.cardtype,")
                .append("     a.entime AS entrancetime,a.enstationname AS entrancestationname,(SELECT b.roadname FROM oms_road b WHERE b.roadno=a.enroadid) AS entranceroadname,")
                .append("     a.extime AS exittime, a.exstationname AS exitstationname, (SELECT b.roadname FROM oms_road b WHERE b.roadno=a.exroadid) AS exitroadname,a.postbalance*100 AS postbalance,0 AS onlinetradeno, a.trseq AS offlinetradeno,a.squaddate,a.reckonstatus AS dealstatus,")
                .append("     (SELECT b.passtype FROM oms_road b WHERE b.roadno=a.exroadid) AS tradetype, SUM(a.toll)*100 AS costamt, 0 AS credited, (SUM(a.toll)-SUM(a.realtoll))*100 AS passoughtamt, '广东' AS passprovince,")
                .append("     0 AS inprovinceflag, a.balancetime, SYSDATE AS gentime")
                .append("    FROM csms_cardinsettledetail a")
                .append("    WHERE a.cardtype=22 ")
                .append("    GROUP BY a.detailno,a.settlemonth,a.cardcode,a.cardtype,a.entime,a.enstationname,a.enroadid,a.extime,a.exstationname,a.exroadid,a.postbalance,a.trseq,a.squaddate,a.reckonstatus,a.balancetime")
                .append(" ) t");

        super.update(sqlBuilder.toString());

        super.update("delete from csms_cardinsettledetail a where a.cardtype=22");

    }

    public void batchSaveFromCardOutSettleDetail() {
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_sc_trade_detailinfo")
                .append(" (ID, userno, tableid, detailno, settlemonth, cardno, cardtype, entrancetime, entrancestationname, entranceroadname, exittime, exitstationname, exitroadname, postbalance,onlinetradeno,offlinetradeno,squaddate,dealstatus, tradetype, costamt, credited, passoughtamt, passprovince, inprovinceflag, balancetime, gentime) ")
                .append(" SELECT seq_csmssctradedetailinfo.NEXTVAL AS id, (SELECT userno FROM csms_customer cus INNER JOIN csms_prepaidc sc ON sc.customerid=cus.ID AND sc.cardno=cardcode WHERE ROWNUM=1) AS userno,ID AS tableid,serviceproviderid||'-'||messageid||'-'||transid AS detailno,settlemonth,cardcode AS cardno,cardtype,")
                .append("   entime AS entrancetime,enstationname AS entrancestationname,(SELECT roadname FROM oms_road WHERE roadno=enroadid) AS entranceroadname,")
                .append("   extime AS exittime, exstationname AS exitstationname, (SELECT roadname FROM oms_road WHERE roadno=exroadid) AS exitroadname,(postbalance)*100 AS postbalance,0 AS onlinetradeno, trseq AS offlinetradeno,squaddate,reckonstatus AS dealstatus,")
                .append("   '00' AS tradetype, toll*100 AS costamt, 0 AS credited, (toll-realtoll)*100 AS passoughtamt, NVL((SELECT briefname FROM tb_participant WHERE PROVINCECODE=substr(exnetno,1,2) FETCH FIRST 1 ROWS ONLY), substr(exnetno,1,2)) AS passprovince,")
                .append("   1 AS inprovinceflag, balancetime, SYSDATE AS gentime")
                .append(" FROM csms_cardoutsettledetail ")
                .append(" WHERE cardtype=22 ");

        super.update(sqlBuilder.toString());

        super.update("delete from csms_cardoutsettledetail a where a.cardtype=22");
    }

}
