package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.MonthlyReg;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MonthlyRegDao extends BaseDao {

    public List<MonthlyReg> findAllCheckedAndNotGen() {
        String sql = " select ID, startdisparttime, enddisparttime, settlemonth, checkresult, checkopno, checktime, startgenbilltime, genbillflag, genbilltime " +
                " from csms_monthly_reg WHERE checkresult= ? AND genbillflag = ? order by settlemonth";
        return super.queryObjectList(sql, MonthlyReg.class,MonthlyReg.CHECKFLAG_CHECKED,MonthlyReg.GENBILLFLAG_NOT);
    }

    public void updateGenBillFlag(Long id, int genBillFlag) {
        String sql = "update csms_monthly_reg set genbillflag=?,genbilltime=? where id=?";
        super.update(sql, genBillFlag, new Date(), id);
    }

    public MonthlyReg findBySettleMonth(Integer settleMonth){
        MonthlyReg monthlyReg = new MonthlyReg();
        String sql = " select ID, startdisparttime, enddisparttime, settlemonth, checkresult, checkopno, checktime, startgenbilltime, genbillflag, genbilltime " +
                " from csms_monthly_reg WHERE checkresult= ? AND settlemonth = ?";

        //当results为空时，就会抛出EmptyResultDataAccessException异常，Spring这样做的目的是为了防止程序员不对空值进行判断，保证了程序的健壮性。
        try {
            monthlyReg = super.queryRowObject(sql,MonthlyReg.class,MonthlyReg.CHECKFLAG_CHECKED,settleMonth);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return monthlyReg;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MonthlyRegDao monthlyRegDao = (MonthlyRegDao)context.getBean("monthlyRegDao");
        monthlyRegDao.findBySettleMonth(201707);
        System.out.println("1");
    }

}
