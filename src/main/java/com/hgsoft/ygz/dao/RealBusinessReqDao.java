package com.hgsoft.ygz.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.entity.RealBusinessReq;

@Repository
public class RealBusinessReqDao extends BaseDao{
	
//	private static final String SEQ_BUSINESS_REALTIME_REQ="SEQ_BUSINESS_REALTIME_REQ";

	@Resource
	SequenceUtil sequenceUtil;
	
	public void save(RealBusinessReq realBusinessReq){
		StringBuffer sql = new StringBuffer("insert into TB_BUSINESS_REALTIME_REQ(ID,BUSINESS_CONTENT,BUSINESS_TYPE,OPERATION,CREATE_TIME) " +
				"VALUES(SEQ_BUSINESS_REALTIME_REQ.nextval,?,?,?,?)");
		List<Object> list=new ArrayList<>();
		list.add(realBusinessReq.getBusinessContent());
		list.add(realBusinessReq.getBusinessType());
		list.add(realBusinessReq.getOperation());
		list.add(realBusinessReq.getCreateTime());
		saveOrUpdate(sql.toString(), list);
	}
}
