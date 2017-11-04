package com.hgsoft.associateAcount.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.AcInvoicInfo;
import com.hgsoft.associateAcount.entity.AcInvoicInfoHis;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

/**
 * @FileName AcInvoicInfoDao.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月4日 下午2:16:18 
*/
@Repository
public class AcInvoicInfoHisDao extends BaseDao{

	public void save(AcInvoicInfoHis acInvoicInfoHis, AcInvoicInfo acInvoicInfo) {
		String sql = "insert into csms_ac_invoic_infohis(id,mainId,UserNo,StartTime,EndTime,SerType,GetType,"
				+ "AcCode,bAccount,RegisterType,OpId,SetTime,PlaceId,Memo,HisSeqID,CreateDate,CreateReason,lianCardInfoId) "
				+ "select "+acInvoicInfoHis.getId()+",mainId,UserNo,StartTime,EndTime,SerType,GetType,"
				+ "AcCode,bAccount,RegisterType,OpId,SetTime,PlaceId,Memo,HisSeqID,"
				+ "sysdate,'"+acInvoicInfoHis.getCreateReason()+"',lianCardInfoId from csms_ac_invoic_info where id="+acInvoicInfo.getId();
		save(sql);
	}

}
