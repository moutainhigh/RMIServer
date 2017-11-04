package com.hgsoft.acms.obu.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.acms.obu.dao.OBUActDetailDaoACMS;
import com.hgsoft.acms.obu.dao.OBUActDetailHisDaoACMS;
import com.hgsoft.acms.obu.dao.OBUActRecordDaoACMS;
import com.hgsoft.acms.obu.dao.OBUActRecordHisDaoACMS;
import com.hgsoft.acms.obu.serviceInterface.IOBUActRecordServiceACMS;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.entity.OBUActDetail;
import com.hgsoft.obu.entity.OBUActDetailHis;
import com.hgsoft.obu.entity.OBUActRecord;
import com.hgsoft.obu.entity.OBUActRecordHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * obu激活卡
 * @author gaosiling
 * 2016年1月21日14:42:42
 */
@Service
public class OBUActRecordServiceACMS implements IOBUActRecordServiceACMS{
	
	private static Logger logger = Logger.getLogger(OBUActRecordServiceACMS.class.getName());
	
	@Resource
	private OBUActRecordDaoACMS oBUActRecordDaoACMS;
	
	@Resource
	private OBUActDetailDaoACMS oBUActDetailDaoACMS;
	
	@Resource
	private OBUActRecordHisDaoACMS oBUActRecordHisDaoACMS;
	
	@Resource
	private OBUActDetailHisDaoACMS oBUActDetailHisDaoACMS;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 * OBU激活卡查询列表
	 * @param  OBUActDetail
	 * @param  OBUActRecord
	 * @author gaosiling
	 */
	@Override
	public Pager obuActRecordList(Pager pager,OBUActDetail obuActDetail, OBUActRecord obuActRecord,Date startTime,Date endTime) {
		try {
			Pager maps=oBUActRecordDaoACMS.findByPager(pager, obuActDetail , obuActRecord,startTime,endTime);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询OBUActRecordService失败");
			throw new ApplicationException("查询OBUActRecordService失败"+e.getMessage());
			
		}
	}
	
	/**
	 * OBU激活卡明细
	 * @param  OBUActDetail
	 * @param  OBUActRecord
	 * @author gaosiling
	 */
	@Override
	public Pager obuActRecordDetail(Pager pager,OBUActDetail obuActDetail, OBUActRecord obuActRecord,Date startTime,Date endTime) {
		try {
			Pager maps=oBUActDetailDaoACMS.findByPager(pager, obuActDetail , obuActRecord,startTime,endTime);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询OBUActRecordService明细失败");
			throw new ApplicationException("查询OBUActRecordService明细失败"+e.getMessage());
			
		}
	}
	
	/**
	 * OBU激活卡详情查询
	 * @param  id
	 * @author gaosiling
	 */
	public Map<String, Object> obuActRecordDetail(Long id) {
		try {
			List<Map<String, Object>> maps=oBUActDetailDaoACMS.obuActRecordDetail(id);
			return maps.get(0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error("查询OBUActRecordService详情失败");
			throw new ApplicationException("查询OBUActRecordService详情失败");
		}
	}
	
	
	/**
	 * OBU激活卡记录修改
	 * @param  OBUActDetail
	 * @param  OBUActRecord
	 * @author gaosiling
	 */
	public Long updateObuRecord(List<OBUActDetail> obuActDetailList, OBUActRecord obuActRecord) {
		try {
			BigDecimal SEQ_CSMSOBUActRecordHis_NO = sequenceUtil.getSequence("SEQ_CSMSOBUActRecordHis_NO");
			
			OBUActRecordHis obuActRecordHis = new OBUActRecordHis();
			OBUActDetailHis obuActDetailHis = new OBUActDetailHis();
			
			obuActRecordHis.setId(Long.valueOf(SEQ_CSMSOBUActRecordHis_NO.toString()));
			obuActRecordHis.setCreateReason("'OBU激活卡回写'");
			
			obuActDetailHis.setMainID(obuActRecordHis.getId());
			
			oBUActRecordHisDaoACMS.saveHis(obuActRecordHis,obuActRecord);
			oBUActDetailHisDaoACMS.saveHis(obuActDetailHis,obuActRecord);
			return Long.valueOf(SEQ_CSMSOBUActRecordHis_NO.toString());
		} catch (ApplicationException e) {
			logger.error("OBU激活卡记录移入历史表失败");
			e.printStackTrace();
			throw new ApplicationException("OBU激活卡记录移入历史表失败"+e.getMessage());
		}
	}
	
	/**
	 * OBU激活卡查询
	 * @param  Id
	 * @author gaosiling
	 */
	public OBUActRecord findById(Long Id){
		return oBUActRecordDaoACMS.findById(Id);
	}
	
	/**
	 * OBU激活卡回写
	 * @param  obuActDetail
	 * @author gaosiling
	 */
	public void updateObuBackWrite(OBUActRecord obuActRecord, List<OBUActDetail> obuActDetailList){
		try {
			
			Long id = updateObuRecord(obuActDetailList, obuActRecord);
			
			obuActRecord.setHisSeqID(id);
			oBUActRecordDaoACMS.update(obuActRecord);
			oBUActDetailDaoACMS.batchUpdateOBUActDetail(obuActDetailList,obuActRecord);
		} catch (ApplicationException e) {
			logger.error("OBU激活卡回写失败");
			e.printStackTrace();
			throw new ApplicationException("OBU激活卡回写失败"+e.getMessage());
		}
	}
	
	
	public OBUActRecord findByActivateCardNo(String activateCardNo,OBUActRecord temp) {
		return oBUActRecordDaoACMS.findByActivateCardNo(activateCardNo,temp);
	}
	
	/**
	 * OBU激活卡记录制作
	 * @param  activateCardNo
	 * @author gaosiling
	 */
	public void saveObuCreate(OBUActRecord obuActRecord, List<String> obuList){
		try {
			
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSOBUActRecord_NO");
			
			List<OBUActDetail> newDetaillist = new ArrayList<OBUActDetail>();
			OBUActDetail obuActDetail = null;
			
			obuActRecord.setId(Long.parseLong(seq.toString()));
			
			String obu = null;
			String[] obuArr = null;
			for (int i = 0; i < obuList.size(); i++) {
				obu = obuList.get(i);
				obuActDetail=new OBUActDetail();
				obuArr = obu.split("-");
				obuActDetail.setVehiclePlate(obuArr[0]);
				obuActDetail.setMainID(Long.parseLong(seq.toString()));
				obuActDetail.setTagNo(obuArr[1]);
				/*obuActDetail.setImportState("1");*/
				newDetaillist.add(obuActDetail);
			}
			oBUActRecordDaoACMS.save(obuActRecord);
			oBUActDetailDaoACMS.batchSaveOBUActDetail(newDetaillist);
			
		} catch (ApplicationException e) {
			logger.error("OBU激活卡记录制作失败");
			e.printStackTrace();
			throw new ApplicationException("OBU激活卡记录制作失败"+e.getMessage());
		}
	}

	/**
	 * OBU激活卡记录制作
	 * @param  notIn
	 * @param  newDetaillist
	 * @author gaosiling
	 */
	@Override
	public void saveObuCreate(List<OBUActDetail> notIn, List<OBUActDetail> newDetaillist) {
		StringBuffer param = new StringBuffer();
		OBUActDetail obuActDetail = null;
		for (int i = 0; i < notIn.size(); i++) {
			obuActDetail = notIn.get(i);
			if(i!=notIn.size()-1){
				param.append(" tagno = '"+obuActDetail.getTagNo()+"' or ");
			}else{
				param.append(" tagno = '"+obuActDetail.getTagNo()+"' ");
			}
		}
		try {
			if(notIn.size()!=0){
				String sql = "delete from CSMS_OBUAct_Detail where "+param.toString();
				System.out.println(sql);
				oBUActDetailDaoACMS.delete(sql);
			}
			if(newDetaillist.size()!=0)oBUActDetailDaoACMS.batchSaveOBUActDetail(newDetaillist);
		} catch (ApplicationException e) {
			logger.error("OBU激活卡记录制作失败");
			e.printStackTrace();
			throw new ApplicationException("OBU激活卡记录制作失败"+e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> getTagNoPlate(
			List<Map<String, String>> cardInfos) {
		return oBUActRecordDaoACMS.getTagNoPlate(cardInfos);
	}
	
	@Override
	public List<Map<String, Object>> obuActRecordList(OBUActRecord obuActRecord) {
		return oBUActRecordDaoACMS.obuActRecordList(obuActRecord);
	}
	
	
	
}
