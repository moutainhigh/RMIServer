package com.hgsoft.daysettle.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.daysettle.entity.DaySetWareDetail;
import com.hgsoft.daysettle.entity.DaySetWareDetailHis;

@Component
public class DaySetWareDetailHisDao extends BaseDao{

	
	public void saveHis(DaySetWareDetailHis daySetWareDetailHis,Long MainId) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_DaySetWare_DetailHis( ID,MainID,SettleDay,ProductType,"
				+ "CurrBalanceNum,CurrRecoverNum,SysCurrBalanceNum,SysCurrRecoverNum,BalanceDiffNum,RecoverDiffNum,DifferenceFlag)  "
				+ "SELECT SEQ_CSMSDaySetWareDetail_NO.Nextval,"+daySetWareDetailHis.getMainID()+",SettleDay,"
				+ "ProductType,CurrBalanceNum,CurrRecoverNum,SysCurrBalanceNum,SysCurrRecoverNum,BalanceDiffNum,RecoverDiffNum,DifferenceFlag"
				+ " FROM CSMS_DaySetWare_Detail WHERE MAINID="+MainId+"");
		save(sql.toString());
	}
}
