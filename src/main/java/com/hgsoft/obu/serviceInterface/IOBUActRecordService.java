package com.hgsoft.obu.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.obu.entity.OBUActDetail;
import com.hgsoft.obu.entity.OBUActRecord;
import com.hgsoft.utils.Pager;

public interface IOBUActRecordService {

	public Pager obuActRecordList(Pager pager,OBUActDetail obuActDetail,OBUActRecord obuActRecord,Date startTime,Date endTime);
	public Map<String, Object> obuActRecordDetail(Long id);
	public void updateObuBackWrite(OBUActRecord obuActRecord, List<OBUActDetail> obuActDetailList);
	public void saveObuCreate(OBUActRecord obuActRecord, List<String> obuList);
	public OBUActRecord findById(Long Id);
	public OBUActRecord findByActivateCardNo(String activateCardNo,OBUActRecord temp);
	public void saveObuCreate(List<OBUActDetail> notIn, List<OBUActDetail> newDetaillist);
	public List<Map<String,Object>> getTagNoPlate(List<Map<String,String>> cardInfos);
	public List<Map<String, Object>> obuActRecordList(OBUActRecord obuActRecord);
	public Pager obuActRecordDetail(Pager pager, OBUActDetail obuActDetail,
			OBUActRecord obuActRecord, Date startTime, Date endTime);
}
