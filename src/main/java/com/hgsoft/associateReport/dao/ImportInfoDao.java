package com.hgsoft.associateReport.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateReport.entity.ImportInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class ImportInfoDao extends BaseDao {
	public void save(ImportInfo importInfo){
		Map map = FieldUtil.getPreFieldMap(ImportInfo.class,importInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_IMPORT_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	
	public Pager findByPage(Pager pager,ImportInfo importInfo,Customer customer,Date startTime,Date endTime) {
		
		String sql ="select ID,mainId,HandleType,CardCode,OperateTime,Password,GenCause,StartDate,EndDate,errorReason,FileName,ImportTime,ImportResult,ROWNUM  as num  "
				+ "from CSMS_IMPORT_INFO t  where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(customer.getId()!=null){
			params.eq("t.mainId",customer.getId());
		}
		if(startTime !=null){
			params.geDate("t.OperateTime", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("t.OperateTime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(importInfo.getFileName())){
			params.eq("t.FileName",importInfo.getFileName());
		}
		if(StringUtil.isNotBlank(importInfo.getHandleType())){
			params.eq("t.HandleType",importInfo.getHandleType());
		}
//		params.ne("t.ImportTime","");
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by t.ImportTime desc ");
		return this.findByPages(sql, pager,Objects);
	}
	
}

