/*package com.hgsoft.clearInterface.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.utils.UrlUtils;
@Component
public class ScInvoiceDetailsDao extends ClearBaseDao{
	@Autowired
	private UrlUtils urlUtils; 
	
	public List<Map<String, Object>> findAll(Long reckonlistno) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("select * from "+urlUtils.getEtctolluser()+".td_scInvoiceDetails where 1=1 and reckonlistno=?");
		list=queryList(sql.toString(),reckonlistno);
		return list;
	}
}
*/