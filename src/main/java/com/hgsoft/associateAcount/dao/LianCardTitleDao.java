package com.hgsoft.associateAcount.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.LianCardInfoTitle;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class LianCardTitleDao extends BaseDao {
	@Resource
	private SequenceUtil sequenceUtil;
	
	public void saveTitle(String cardNo,String happenedTime,String importTime,String title) {
		LianCardInfoTitle lianCardInfoTitle = new LianCardInfoTitle();
		Long lianCardTitleID =  sequenceUtil.getSequenceLong("SEQ_ACMSLIANCARDTITLE_NO");
		lianCardInfoTitle.setId(lianCardTitleID);
		lianCardInfoTitle.setHappenedTime(happenedTime);
		lianCardInfoTitle.setImportTime(importTime);
		lianCardInfoTitle.setTitle(title);
		lianCardInfoTitle.setCardNo(cardNo);
		Map map = FieldUtil.getPreFieldMap(LianCardInfoTitle.class,lianCardInfoTitle);
		StringBuffer sql=new StringBuffer("insert into acms_LianCard_title");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
