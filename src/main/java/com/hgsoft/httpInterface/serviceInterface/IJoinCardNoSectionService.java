package com.hgsoft.httpInterface.serviceInterface;

import com.hgsoft.httpInterface.entity.JoinCardNoSection;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface IJoinCardNoSectionService {

	public Pager findByPager(Pager pager, String cardno,String bankNo,String cardType,String CompoundFlag,String issFlag);
	public Map<String, Object> saveJoinCardNoSection(JoinCardNoSection joinCardNoSection,String startCode,String endCode);
	public JoinCardNoSection findById(Long id);
	public Map<String, Object> updateJoinCardNoSection(JoinCardNoSection joinCardNoSection);
	public Map<String, Object> deleteCardNoSectionService(JoinCardNoSection joinCardNoSection);
	public Map<String, Object> batchApproval(String arrayId, Map operator, String checkFlag);
	public List findList(String cardType,String cardNo);

}
