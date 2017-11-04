package com.hgsoft.ygz.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.entity.NoRealBusinessReq;

@Repository
public class NoRealBusinessReqDao extends BaseDao{

//	private static final String SEQ_BUSINESS_NONREALTIME_REQ="SEQ_BUSINESS_NONREALTIME_REQ";

	@Resource
	SequenceUtil sequenceUtil;
	
	public void save(NoRealBusinessReq noRealBusinessReq){
		StringBuffer sql = new StringBuffer("insert into TB_BUSINESS_NONREALTIME_REQ(ID,BUSINESS_CONTENT,BUSINESS_TYPE,OPERATION,CREATE_TIME) " +
				"VALUES(SEQ_BUSINESS_NONREALTIME_REQ.nextval,?,?,?,?)");
		List<Object> list=new ArrayList<>();
		list.add(noRealBusinessReq.getBusinessContent());
		list.add(noRealBusinessReq.getBusinessType());
		list.add(noRealBusinessReq.getOperation());
		list.add(noRealBusinessReq.getCreateTime());
		saveOrUpdate(sql.toString(), list);
	}
}
