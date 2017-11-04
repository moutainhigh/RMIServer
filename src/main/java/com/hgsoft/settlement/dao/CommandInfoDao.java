package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;
import com.hgsoft.settlement.entity.MonthlyReg;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CommandInfoDao extends BaseDao {

    /**
     * 按照生成时间、银行编号、划扣账户、流水汇总的指定类型的指令数据
     * @param feeType
     * @return
     */
    public List<CommandInfo> findByFeeType(String feeType) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT detailno,acbaccount,bankno,gentime,SUM(realtoll) AS realtoll ")
                .append(" FROM CSMS_COMMANDINFO ")
                .append(" WHERE detailno is not null and feetype=? ")
                .append(" GROUP BY gentime,acbaccount,bankno,detailno ")
                .append(" ORDER BY gentime,acbaccount,bankno,detailno desc ");

        return super.queryObjectList(sqlBuilder.toString(), CommandInfo.class, feeType);
    }

    /**
     * 根据银行返盘数据（生成时间、银行编号、划扣账户）删除指令对照表记录
     * @param accBankListReturn
     * @return
     */
    public int deleteByAccBankReturn(AccBankListReturn accBankListReturn) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" DELETE FROM CSMS_COMMANDINFO ")
                .append(" WHERE bankno=? and acbaccount=? and gentime=?  ");

        return update(sqlBuilder.toString(), accBankListReturn.getBankNo(),
                accBankListReturn.getAcbAccount(), accBankListReturn.getGentime());
    }
}
