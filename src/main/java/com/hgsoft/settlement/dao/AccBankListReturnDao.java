package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangzhongji on 17/9/23.
 */
@Repository
public class AccBankListReturnDao extends BaseDao {

    public List<AccBankListReturn> listCrossBankCommnad(CommandInfo commandInfo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT ID, BOARDLISTNO, GENTIME,BANKNO,ACBACCOUNT,INCOME,STATUS,HDLDATETIME,")
                .append(AccBankListReturn.CommandTypeEnum.CROSS_BANK.getValue()).append(" as COMMANDTYPE ")
                .append(" FROM CSMS_ACCBANKLISTRETURNHK ")
                .append(" WHERE BANKNO=? and ACBACCOUNT=? and GENTIME=? ")
                .append(" ORDER BY GENTIME,ACBACCOUNT,BANKNO DESC ");

        return super.queryObjectList(sqlBuilder.toString(), AccBankListReturn.class,
                commandInfo.getBankNo(), commandInfo.getAcbAccount(), commandInfo.getGentime());
    }

    public List<AccBankListReturn> listSameBankCommnad(CommandInfo commandInfo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT ID, BOARDLISTNO, GENTIME,BANKNO,ACBACCOUNT,INCOME,STATUS,HDLDATETIME,")
                .append(AccBankListReturn.CommandTypeEnum.SAME_BANK.getValue()).append(" as COMMANDTYPE ")
                .append(" FROM CSMS_ACCBANKLISTRETURN ")
                .append(" WHERE BANKNO=? and ACBACCOUNT=? and GENTIME=? ")
                .append(" ORDER BY GENTIME,ACBACCOUNT,BANKNO DESC ");

        return super.queryObjectList(sqlBuilder.toString(), AccBankListReturn.class,
                commandInfo.getBankNo(), commandInfo.getAcbAccount(), commandInfo.getGentime());
    }

    public List<AccBankListReturn> listByCommandInfo(CommandInfo commandInfo) {
        List<AccBankListReturn> accBankListReturns = listSameBankCommnad(commandInfo);
        if (accBankListReturns == null || accBankListReturns.isEmpty()) {
            accBankListReturns = listCrossBankCommnad(commandInfo);
        }
        return accBankListReturns;
    }
}
