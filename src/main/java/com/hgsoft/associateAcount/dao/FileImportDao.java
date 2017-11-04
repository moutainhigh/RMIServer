package com.hgsoft.associateAcount.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.FileImport;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class FileImportDao extends BaseDao {
	public void save(FileImport fileImport){
		Map map = FieldUtil.getPreFieldMap(FileImport.class,fileImport);
		StringBuffer sql=new StringBuffer("insert into CSMS_FILE_IMPORT");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
