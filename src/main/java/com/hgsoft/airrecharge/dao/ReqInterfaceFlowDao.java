package com.hgsoft.airrecharge.dao;

import com.hgsoft.airrecharge.entity.ReqInterfaceFlow;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口请求流水表
 * @author gaosiling
 * 2016年8月21日 16:37:25
 */
@Repository
public class ReqInterfaceFlowDao extends BaseDao {
	
	public void save(ReqInterfaceFlow reqInterfaceFlow){
		Map map = FieldUtil.getPreFieldMap(ReqInterfaceFlow.class,reqInterfaceFlow);
		StringBuffer sql=new StringBuffer("insert into CSMS_REQINTERFACE_FLOW");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
