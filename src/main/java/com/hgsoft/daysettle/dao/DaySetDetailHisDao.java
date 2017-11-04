package com.hgsoft.daysettle.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.daysettle.entity.DaySetDetailHis;
@Component
public class DaySetDetailHisDao extends BaseDao{
	public void saveHis( DaySetDetailHis  daySetDetailHis,Long MainId) {
		
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_DaySet_DetailHis( ID,MainID,settleDay,FeeType,SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag)  "
				+ "SELECT SEQ_CSMSDaySetDetailHis_NO.Nextval,"+daySetDetailHis.getMainID()+",settleDay,FeeType,SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag"
				+ " FROM CSMS_DaySet_Detail WHERE DifferenceFee !=0 and MAINID="+MainId+"");
		save(sql.toString());
	}
}
