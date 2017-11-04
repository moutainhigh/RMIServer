package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.AcBillInfoMonthly;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AcBillInfoMonthlyDao extends BaseDao {

    public void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime) {

        //预处理
        String sql = "update csms_ac_trade_detailinfo set acbillid=0 where acbillid IS NULL AND balancetime<? AND balancetime>=? AND settlemonth=?";
        super.update(sql, endDispartTime, startDispartTime, settleMonth);

        //生成账单
        StringBuilder sqlBuilder = new StringBuilder()
                .append(" INSERT INTO csms_ac_billinfo_monthly")
                .append("    (ID, settlemonth, userno, bankaccount, cardno,cardtype, dealstatus, dealnum, dealfee, realdealfee, oncenum, oncefee, serverfee, latefee, othernum, otherfee, otherrealfee, otherserverfee)")
                .append(" SELECT seq_csmsacbillinfomonthly.NEXTVAL AS id, ").append(settleMonth).append(" as settlemonth, userno, bankaccount, cardno,cardtype, dealstatus,dealnum, dealfee, realdealfee, oncenum, oncefee, serverfee, latefee, othernum, otherfee, otherrealfee, otherserverfee")
                .append(" FROM (")
                .append("    SELECT settlemonth,userno,bankaccount,cardno,cardtype,dealstatus,SUM(dealnum) AS dealnum,SUM(dealfee) AS dealfee,")
                .append("       SUM(realdealfee) AS realdealfee, SUM(oncenum) AS oncenum, SUM(oncefee) AS oncefee,0 AS serverfee,")
                .append("       SUM(latefee) AS latefee, SUM(othernum) AS othernum, SUM(otherfee) AS otherfee, SUM(otherrealfee) AS otherrealfee, 0 AS otherserverfee")
                .append("    FROM (")
                .append("       SELECT settlemonth, userno, bankaccount,cardno,cardtype,dealstatus,")
                .append("         CASE passtype WHEN '00' THEN 1 ELSE 0 END AS dealnum,")
                .append("         CASE passtype WHEN '00' THEN toll ELSE 0 END AS dealfee,")
                .append("         CASE passtype WHEN '00' THEN realtoll ELSE 0 END AS realdealfee,")
                .append("         CASE passtype WHEN '01' THEN 1 ELSE 0 END AS oncenum,")
                .append("         CASE passtype WHEN '01' THEN toll ELSE 0 END AS oncefee,")
                .append("         0 AS serverfee,0 AS latefee,")
                .append("         CASE passtype WHEN '03' THEN 1 ELSE 0 END AS othernum,")
                .append("         CASE passtype WHEN '03' THEN toll ELSE 0 END AS otherfee,")
                .append("         CASE passtype WHEN '03' THEN realtoll ELSE 0 END AS otherrealfee,")
                .append("         0 AS otherserverfee")
                .append("       FROM csms_ac_trade_detailinfo")
                .append("       WHERE balancetime<? AND balancetime>=? AND acbillid=0 AND settlemonth=?")
                .append("       UNION ALL")
                .append("       SELECT ").append(settleMonth).append(" AS settlemonth, cus.userno, acapply.bankaccount, restop.cardno,23 AS cardtype,0 as dealstatus,")
                .append("         0 AS dealnum, 0 AS dealfee, 0 AS realdealfee, 0 AS oncenum, 0 AS oncefee,")
                .append("         0 AS serverfee,restop.latefee AS latefee, 0 AS othernum, 0 AS otherfee, 0 AS otherrealfee,0 AS otherserverfee")
                .append("       FROM csms_accountc_info acinfo")
                .append("         INNER JOIN csms_accountc_apply acapply ON acapply.ID=acinfo.accountid")
                .append("         INNER JOIN csms_customer cus ON cus.ID=acinfo.customerid")
                .append("         INNER JOIN csms_relieve_stoppay restop ON restop.cardno=acinfo.cardno")
                .append("       WHERE restop.feeendtime<? AND restop.feeendtime>=?")
                .append("    ) t")
                .append("    GROUP BY settlemonth,userno,bankaccount,cardno,cardtype,dealstatus")
                .append("    ORDER BY userno,bankaccount,cardno,dealstatus")
                .append(" ) x");
        super.update(sqlBuilder.toString(), endDispartTime, startDispartTime, settleMonth, endDispartTime, startDispartTime);

        //更新生成的acbillid
        sql = " UPDATE csms_ac_trade_detailinfo acdetail SET acdetail.acbillid=(SELECT acbill.ID FROM csms_ac_billinfo_monthly acbill WHERE acdetail.cardtype=acbill.cardtype AND acdetail.settlemonth=acbill.settlemonth AND acdetail.cardno=acbill.cardno AND acdetail.userno=acbill.userno AND acdetail.bankaccount=acbill.bankaccount)"
            + " WHERE EXISTS (SELECT 1 FROM csms_ac_billinfo_monthly acbill WHERE acdetail.cardtype=acbill.cardtype AND acdetail.dealstatus=acbill.dealstatus AND acdetail.cardno=acbill.cardno AND acdetail.settlemonth=acbill.settlemonth AND acdetail.userno=acbill.userno AND acdetail.bankaccount=acbill.bankaccount)"
            + "  AND acdetail.acbillid=0 AND acdetail.balancetime<? AND acdetail.balancetime>=? AND acdetail.settlemonth=? ";
        super.update(sql, endDispartTime, startDispartTime, settleMonth);
    }

    public List<AcBillInfoMonthly> findByCardNoSettleMonthDealStatus(String cardNo, String settleMonth, Integer dealStatus) {
        String sql = "select * from csms_ac_billinfo_monthly where dealstatus=? and cardno=? and settlemonth=?";
        return super.queryObjectList(sql, AcBillInfoMonthly.class, dealStatus, cardNo, settleMonth);
    }

    public List<AcBillInfoMonthly> findByBankAccountSettleMonth(String bankAccount, String settleMonth, Integer dealStatus) {

        StringBuilder sqlBuilder = new StringBuilder()
                .append(" select sum(nvl(dealnum, 0)) as dealnum, sum(nvl(dealfee,0)) as dealfee, sum(nvl(realdealfee,0)) as realdealfee, ")
                .append("    sum(nvl(oncenum, 0)) as oncenum, sum(nvl(oncefee,0)) as oncefee, sum(nvl(serverfee,0)) as serverfee, ")
                .append("    sum(nvl(latefee, 0)) as latefee, sum(nvl(othernum, 0)) as othernum, sum(nvl(otherfee,0)) as otherfee, ")
                .append("    sum(nvl(otherrealfee,0)) as otherrealfee, sum(nvl(otherserverfee,0)) as otherserverfee ")
                .append(" from csms_ac_billinfo_monthly where dealstatus=? and bankaccount=? and settlemonth=? ");
        return super.queryObjectList(sqlBuilder.toString(), AcBillInfoMonthly.class, dealStatus, bankAccount, settleMonth);
    }
}
