package com.hgsoft.associateReport.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateReport.entity.ImportException;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class ImportExceptionDao extends BaseDao {
	public void save(ImportException importException){
		Map map = FieldUtil.getPreFieldMap(ImportException.class,importException);
		StringBuffer sql=new StringBuffer("insert into CSMS_IMPORT_EXCEPTION");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	
	public Pager findByPage(Pager pager,ImportException importException,Customer customer,Date startTime,Date endTime) {
		
		String sql ="select ID,mainId,HandleType,CardCode,OperateTime,Password,GenCause,StartDate,EndDate,errorCode,FileName,ROWNUM  as num  "
				+ "from CSMS_IMPORT_EXCEPTION t  where 1=1 ";
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
		if(StringUtil.isNotBlank(importException.getFileName())){
			params.eq("t.FileName",importException.getFileName());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by t.OperateTime desc ");
		return this.findByPages(sql, pager,Objects);
	}
	
}

