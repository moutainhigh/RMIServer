package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ： 孙晓伟
 *         file : BillInfoDao.java
 *         date : 2017/6/19
 *         time : 11:28
 */
@Repository
public class BillInfoDao extends BaseDao {

    /***
     * 账单查询-按客户号（userNo）
     * @param userNo
     * @param settleMonth
     * @return
     */
    public List<Map<String, Object>> findBillInfoByUserNo(String userNo, String settleMonth){
        StringBuffer sql = new StringBuffer(
                "SELECT cardNo AS \"cardNo\", cardType AS \"cardType\", bankAccount AS \"bankAccount\", latefee AS \"latefee\", Dealfee AS \"Dealfee\" , dealnum AS \"dealnum\", realdealfee AS \"realdealfee\", oncenum AS \"oncenum\", oncefee AS \"oncefee\", otherfee AS \"otherfee\" , otherrealfee AS \"otherrealfee\", othernum AS \"othernum\", rechargeTimes AS \"rechargeTimes\", rechargeFee AS \"rechargeFee\", transferFee AS \"transferFee\" , mustReturnFee AS \"mustReturnFee\", hadReturnFee AS \"hadReturnFee\" FROM ( SELECT sc.cardType, sc.Dealfee, sc.dealnum, sc.realdealfee, nvl(sc.oncenum, 0) AS oncenum , nvl(sc.oncefee, 0) AS oncefee, sc.latefee, sc.OTHERSERVERFEE AS otherserver, sc.otherfee, sc.otherrealfee , sc.othernum, sc.RECHARGENUM AS rechargeTimes, sc.rechargeFee, sc.transferFee, sc.mustReturnFee , sc.hadReturnFee, sc.userNo, sc.settleMonth, sc.cardNo, sc.dealStatus , NULL AS bankAccount FROM CSMS_SC_BILLINFO_MONTHLY sc WHERE sc.dealStatus = 0 UNION ALL SELECT ac.cardType, ac.Dealfee, ac.dealnum, ac.realdealfee, nvl(ac.oncenum, 0) AS oncenum , nvl(ac.oncefee, 0) AS oncefee, ac.serverfee, ac.latefee, ac.otherfee, ac.otherrealfee , ac.othernum, NULL AS rechargeTimes, NULL AS rechargeFee, NULL AS transferFee, NULL AS mustReturnFee , NULL AS hadReturnFee, ac.userNo, ac.settleMonth, ac.cardNo, ac.dealStatus , ac.bankAccount FROM CSMS_AC_BILLINFO_MONTHLY ac WHERE ac.dealStatus = 0 ) WHERE 1 = 1"
        );

        SqlParamer params=new SqlParamer();

        if(StringUtil.isNotBlank(userNo)){
            params.eq("userNo", userNo);
        }
        if(StringUtil.isNotBlank(settleMonth)){
            params.eq("settleMonth", settleMonth);
        }


        sql.append(params.getParam());
        List list = params.getList();
        Object[] Objects= list.toArray();

        List<Map<String, Object>> billInfoList = queryList(sql.toString(),Objects);

        if(!billInfoList.isEmpty()&&billInfoList.size()>0){
            return billInfoList;
        }else{
            return null;
        }
    }

    /***
     * 账单查询-按卡号（储值卡）
     * @param cardNo
     * @param settleMonth
     * @return
     */
    public List<Map<String, Object>> findScBillInfoByCardNo(String cardNo, String settleMonth){
        StringBuffer sql = new StringBuffer(
                "SELECT cardNo AS \"cardNo\", cardType AS \"cardType\", bankAccount AS \"bankAccount\", latefee AS \"latefee\", Dealfee AS \"Dealfee\" , dealnum AS \"dealnum\", realdealfee AS \"realdealfee\", oncenum AS \"oncenum\", oncefee AS \"oncefee\", otherfee AS \"otherfee\" , otherrealfee AS \"otherrealfee\", othernum AS \"othernum\", rechargeTimes AS \"rechargeTimes\", rechargeFee AS \"rechargeFee\", transferFee AS \"transferFee\" , mustReturnFee AS \"mustReturnFee\", hadReturnFee AS \"hadReturnFee\" FROM (SELECT sc.cardType, sc.Dealfee, sc.dealnum, sc.realdealfee, nvl(sc.oncenum, 0) AS oncenum , nvl(sc.oncefee, 0) AS oncefee, sc.latefee, sc.OTHERSERVERFEE AS otherserver, sc.otherfee, sc.otherrealfee , sc.othernum, sc.RECHARGENUM AS rechargeTimes, sc.rechargeFee, sc.transferFee, sc.mustReturnFee , sc.hadReturnFee, sc.userNo, sc.settleMonth, sc.cardNo, sc.dealStatus , NULL AS bankAccount FROM CSMS_SC_BILLINFO_MONTHLY sc WHERE sc.dealStatus = 0 ) WHERE 1 = 1"
        );

        SqlParamer params=new SqlParamer();

        if(StringUtil.isNotBlank(cardNo)){
            params.eq("cardNo", cardNo);
        }
        if(StringUtil.isNotBlank(settleMonth)){
            params.eq("settleMonth", settleMonth);
        }


        sql.append(params.getParam());
        List list = params.getList();
        Object[] Objects= list.toArray();

        List<Map<String, Object>> billInfoList = queryList(sql.toString(),Objects);

        if(!billInfoList.isEmpty()&&billInfoList.size()>0){
            return billInfoList;
        }else{
            return null;
        }
    }

    /***
     * 账单查询-按卡号（记帐卡）
     * @param cardNo
     * @param settleMonth
     * @return
     */
    public List<Map<String, Object>> findAcBillInfoByCardNo(String cardNo, String settleMonth){
        StringBuffer sql = new StringBuffer(
                "SELECT cardNo AS \"cardNo\", cardType AS \"cardType\", bankAccount AS \"bankAccount\", latefee AS \"latefee\", Dealfee AS \"Dealfee\" , dealnum AS \"dealnum\", realdealfee AS \"realdealfee\", oncenum AS \"oncenum\", oncefee AS \"oncefee\", otherfee AS \"otherfee\" , otherrealfee AS \"otherrealfee\", othernum AS \"othernum\", rechargeTimes AS \"rechargeTimes\", rechargeFee AS \"rechargeFee\", transferFee AS \"transferFee\" , mustReturnFee AS \"mustReturnFee\", hadReturnFee AS \"hadReturnFee\" FROM (SELECT ac.cardType, ac.Dealfee, ac.dealnum, ac.realdealfee, NVL(ac.oncenum, 0) AS oncenum , NVL(ac.oncefee, 0) AS oncefee, ac.serverfee, ac.latefee, ac.otherfee, ac.otherrealfee , ac.othernum, NULL AS rechargeTimes, NULL AS rechargeFee, NULL AS transferFee, NULL AS mustReturnFee , NULL AS hadReturnFee, ac.userNo, ac.settleMonth, ac.cardNo, ac.dealStatus , ac.bankAccount FROM CSMS_AC_BILLINFO_MONTHLY ac WHERE ac.dealStatus = 0 ) WHERE 1 = 1"
        );

        SqlParamer params=new SqlParamer();

        if(StringUtil.isNotBlank(cardNo)){
            params.eq("cardNo", cardNo);
        }
        if(StringUtil.isNotBlank(settleMonth)){
            params.eq("settleMonth", settleMonth);
        }


        sql.append(params.getParam());
        List list = params.getList();
        Object[] Objects= list.toArray();

        List<Map<String, Object>> billInfoList = queryList(sql.toString(),Objects);

        if(!billInfoList.isEmpty()&&billInfoList.size()>0){
            return billInfoList;
        }else{
            return null;
        }
    }
}
