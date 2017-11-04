package com.hgsoft.associateReport.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class ServiceAppDao extends BaseDao{

	public void save(ServiceApp serviceApp) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_SERVICE_APP(");
		serviceApp.setFlowNo(getFlowNo());
		sql.append(FieldUtil.getFieldMap(ServiceApp.class,serviceApp).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ServiceApp.class,serviceApp).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(ServiceApp.class,serviceApp);
		StringBuffer sql=new StringBuffer("insert into CSMS_SERVICE_APP");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	private String getFlowNo(){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		int randomNum1 = (int)((Math.random()*9+1)*10000000);
		int randomNum2 = (int)((Math.random()*9+1)*10000000);
		String flowNo=fomat.format(date)+randomNum1+randomNum2;
		return flowNo;
	}
}
