package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.ScBillInfoMonthly;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ScBillInfoMonthlyDao extends BaseDao {

    public void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime) {

        //预处理
        String sql = "update csms_sc_trade_detailinfo set scbillid=0,settlemonth=? where scbillid IS NULL AND ((tradetype IN('00','01','03') AND settlemonth=?) OR (tradetype IN('a1','a2','a3','a5'))) AND balancetime<? AND balancetime>=? ";
        super.update(sql, settleMonth, settleMonth, endDispartTime, startDispartTime);

        //生成储值卡账单数据
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_sc_billinfo_monthly")
                .append("     (ID, settlemonth, userno, cardno, cardtype,dealstatus, dealnum, dealfee, realdealfee, oncenum, oncefee, serverfee, latefee, othernum, otherfee, otherrealfee, otherserverfee, rechargenum, rechargefee, transferfee, mustreturnfee, hadreturnfee)")
                .append(" SELECT seq_csmsacbillinfomonthly.NEXTVAL AS ID,settlemonth, userno, cardno,cardtype,dealstatus,dealnum, dealfee, realdealfee, oncenum, oncefee, serverfee, latefee, othernum, otherfee, otherrealfee, otherserverfee, rechargenum, rechargefee, transferfee, mustreturnfee, hadreturnfee")
                .append(" FROM (")
                .append("     SELECT settlemonth,userno,cardno,cardtype,dealstatus,SUM(dealnum) AS dealnum,SUM(dealfee) AS dealfee,")
                .append("         SUM(realdealfee) AS realdealfee, SUM(oncenum) AS oncenum, SUM(oncefee) AS oncefee,0 AS serverfee,")
                .append("         0 AS latefee, SUM(othernum) AS othernum, SUM(otherfee) AS otherfee, SUM(otherrealfee) AS otherrealfee, 0 AS otherserverfee,")
                .append("         SUM(rechargenum) AS rechargenum, SUM(rechargefee) AS rechargefee, SUM(transferfee) AS transferfee,")
                .append("         SUM(mustreturnfee) AS mustreturnfee,SUM(hadreturnfee) AS hadreturnfee")
                .append("     FROM (")
                .append("         SELECT ").append(settleMonth).append(" AS settlemonth, userno,cardno, cardtype,dealstatus,")
                .append("            CASE tradetype WHEN '00' THEN 1 ELSE 0 END AS dealnum,")
                .append("            CASE tradetype WHEN '00' THEN costamt ELSE 0 END AS dealfee,")
                .append("            CASE tradetype WHEN '00' THEN costamt-passoughtamt ELSE 0 END AS realdealfee,")
                .append("            CASE tradetype WHEN '01' THEN 1 ELSE 0 END AS oncenum,")
                .append("            CASE tradetype WHEN '01' THEN costamt ELSE 0 END AS oncefee,")
                .append("            0 AS serverfee,0 AS latefee,")
                .append("            CASE tradetype WHEN '03' THEN 1 ELSE 0 END AS othernum,")
                .append("            CASE tradetype WHEN '03' THEN costamt ELSE 0 END AS otherfee,")
                .append("            CASE tradetype WHEN '03' THEN costamt ELSE 0 END AS otherrealfee,")
                .append("            0 AS otherserverfee,")
                .append("            CASE WHEN tradetype IN ('a1','a3','a5') THEN 1 WHEN tradetype='a2' THEN -1 ELSE 0 END AS rechargenum,")
                .append("            CASE WHEN tradetype IN ('a1','a2') THEN credited ELSE 0 END AS rechargefee,")
                .append("            CASE tradetype WHEN 'a3' THEN credited ELSE 0 END AS transferfee,")
                .append("            0 AS mustreturnfee,")
                .append("            CASE tradetype WHEN 'a5' THEN credited ELSE 0 END AS hadreturnfee")
                .append("         FROM csms_sc_trade_detailinfo")
                .append("         WHERE scbillid=0 AND balancetime< ? AND balancetime >= ? AND settlemonth=?")
                .append("         UNION ALL")
                .append("         SELECT refee.settlemonth, cus.userno, refee.cardno, 22 AS cardtype, 0 as dealstatus,")
                .append("            0 AS dealnum, 0 AS dealfee, 0 AS realdealfee, 0 AS oncenum, 0 AS oncefee,")
                .append("            0 AS serverfee,0 AS latefee, 0 AS othernum, 0 AS otherfee, 0 AS otherrealfee,")
                .append("            0 AS otherserverfee,0 AS rechargenum, 0 AS rechargefee, 0 AS transferfee,")
                .append("            refee.returnfee AS mustreturnfee, 0 AS hadreturnfee")
                .append("         FROM csms_customer cus")
                .append("            INNER JOIN csms_prepaidc sc ON cus.ID=sc.customerid")
                .append("            INNER JOIN csms_returnfee refee ON sc.cardno=refee.cardno")
                .append("         WHERE refee.settlemonth=?")
                .append("     ) t")
                .append("     GROUP BY settlemonth,userno,cardno,cardtype,dealstatus")
                .append(" ) x");
        super.update(sqlBuilder.toString(), endDispartTime, startDispartTime, settleMonth, settleMonth);

        //更新生成的scbillid
        sql = " UPDATE csms_sc_trade_detailinfo scdetail SET scdetail.scbillid=(SELECT scbill.ID FROM csms_sc_billinfo_monthly scbill WHERE scdetail.cardtype=scbill.cardtype AND scdetail.dealstatus=scbill.dealstatus AND scdetail.cardno=scbill.cardno AND scdetail.settlemonth=scbill.settlemonth AND scdetail.userno=scbill.userno)"
                + " WHERE EXISTS (SELECT 1 FROM csms_sc_billinfo_monthly scbill WHERE scdetail.cardtype=scbill.cardtype AND scdetail.dealstatus=scbill.dealstatus AND scdetail.cardno=scbill.cardno AND scdetail.settlemonth=scbill.settlemonth AND scdetail.userno=scbill.userno)"
                + " AND scdetail.scbillid=0 AND scdetail.balancetime<? AND scdetail.balancetime>=? AND scdetail.settlemonth=?";
        super.update(sql, endDispartTime, startDispartTime, settleMonth);
    }


    public List<ScBillInfoMonthly> findByCardNoSettleMonthDealStatus(String cardNo, String settleMonth, Integer dealStatus) {
        String sql = "select * from csms_sc_billinfo_monthly where dealstatus=? and cardno=? and settlemonth=?";
        return super.queryObjectList(sql, ScBillInfoMonthly.class, dealStatus, cardNo, settleMonth);
    }
}
