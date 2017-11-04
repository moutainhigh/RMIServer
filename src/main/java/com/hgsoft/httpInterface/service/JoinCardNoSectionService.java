package com.hgsoft.httpInterface.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.JoinCardNoSectionDao;
import com.hgsoft.httpInterface.entity.JoinCardNoSection;
import com.hgsoft.httpInterface.serviceInterface.IJoinCardNoSectionService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoinCardNoSectionService implements IJoinCardNoSectionService {
	private static Logger logger = Logger.getLogger(JoinCardNoSectionService.class.getName());
	@Resource
	private JoinCardNoSectionDao joinCardNoSectiondao;
	@Resource
	private SequenceUtil sequenceUtil;

	@Override
	public Pager findByPager(Pager pager, String cardno,String bankNo,String cardType,String CompoundFlag,String issFlag){
		return joinCardNoSectiondao.findByPager(pager, cardno,bankNo,cardType,CompoundFlag,issFlag);
	}

	@Override
	public Map<String, Object> saveJoinCardNoSection(JoinCardNoSection joinCardNoSection, String startCode,
			String endCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int i = joinCardNoSectiondao.findCountByCode(startCode, endCode);
			if(i>0){
				map.put("hasMsg", false);
				map.put("msg", "卡号段中包含已存在卡号！请重新输入卡号段");
				return map;
			}
			joinCardNoSection.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSjoinCardNoSection_NO").toString()));
			joinCardNoSection.setCode(startCode);
			joinCardNoSection.setEndCode(endCode);
			joinCardNoSectiondao.save(joinCardNoSection);
			map.put("hasMsg", true);
			map.put("msg", "保存成功！");
		} catch (Exception e) {
			logger.error(e.getMessage()+"卡号段中包含已存在卡号！请重新输入卡号段");
			e.printStackTrace();
			map.put("hasMsg", false);
			map.put("msg", "卡号段中包含已存在卡号！请重新输入卡号段");
			throw new ApplicationException("卡号段中包含已存在卡号！请重新输入卡号段");
		}
		return map;

	}

	@Override
	public JoinCardNoSection findById(Long id) {
		return joinCardNoSectiondao.findById(id);
	}

	@Override
	public Map<String, Object> updateJoinCardNoSection(JoinCardNoSection joinCardNoSection) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasMsg", true);
		map.put("msg", "修改成功！");
		try {
			JoinCardNoSection oldJoinCardNoSection = joinCardNoSectiondao.findById(joinCardNoSection.getId());
			
			oldJoinCardNoSection.setBankName(joinCardNoSection.getBankName());
			oldJoinCardNoSection.setBankNo(joinCardNoSection.getBankNo());
			oldJoinCardNoSection.setCardType(joinCardNoSection.getCardType());
			oldJoinCardNoSection.setCompoundFlag(joinCardNoSection.getCompoundFlag());
			oldJoinCardNoSection.setRemark(joinCardNoSection.getRemark());
			oldJoinCardNoSection.setCheckFlag("1");
			oldJoinCardNoSection.setIssFlag(joinCardNoSection.getIssFlag());
			oldJoinCardNoSection.setCheckId(null);
			oldJoinCardNoSection.setCheckTime(null);
			oldJoinCardNoSection.setCheckName(null);
			joinCardNoSectiondao.update(oldJoinCardNoSection);
		} catch (Exception e) {
			logger.error(e.getMessage()+"修改失败");
			e.printStackTrace();
			map.put("hasMsg", false);
			map.put("msg", "修改失败");
			throw new ApplicationException("修改失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteCardNoSectionService(JoinCardNoSection joinCardNoSection) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasMsg", true);
		map.put("msg", "删除成功！");
		try {
		
			joinCardNoSectiondao.delete(joinCardNoSection.getId());
		} catch (Exception e) {
			logger.error(e.getMessage()+"删除失败!");
			e.printStackTrace();
			map.put("hasMsg", false);
			map.put("msg", "删除失败!");
			throw new ApplicationException("删除失败!");
		}
		return map;
	}

	@Override
	public Map<String, Object> batchApproval(String arrayId, Map operator, String checkFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasMsg", true);
		map.put("msg", "审核成功！");
		try {
			if(StringUtil.isNotBlank(arrayId)){
				if(arrayId.endsWith(",")){
					arrayId = arrayId.substring(0,arrayId.length()-1);
				}
//				joinCardNoSectiondao.batchSaveJoinCardNoSectionHis(arrayId,operator,"3");
				joinCardNoSectiondao.batchUpdate(arrayId,operator,checkFlag);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"审核失败");
			e.printStackTrace();
			throw new ApplicationException("审核失败");
		}
		return map;
	}

	@Override
	public List findList(String cardType, String cardNo) {
		List list = joinCardNoSectiondao.findList(cardType, cardNo);
		if(list == null){
			return Collections.emptyList();
		}
		return list;
	}

}
