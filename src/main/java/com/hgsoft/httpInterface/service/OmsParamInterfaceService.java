package com.hgsoft.httpInterface.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.httpInterface.entity.ServiceParamSetNew;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.PropertiesUtil;
import com.hgsoft.utils.SequenceUtil;

import net.sf.json.JSONObject;

@Service
public class OmsParamInterfaceService implements IOmsParamInterfaceService {
	
	private static Logger logger = Logger.getLogger(OmsParamInterfaceService.class.getName());
	@Resource
	private OmsParamDao omsParamDao;
	@Resource
	SequenceUtil sequenceUtil;

	/**
	 * @Descriptioqn:
	 * @param omsId
	 * @param omsType
	 * @param paramId
	 * @param paramValue
	 * @param type
	 * @param state
	 * @param operId
	 * @param operNo
	 * @param operName
	 * @param operTime
	 * @param memo
	 * @return
	 * @author lgm
	 * @date 2017年3月6日
	 */
	@Override
	public void add(Long omsId,String omsType,Long paramId,String paramValue,String type,String state,Long operId,String operNo,String operName,Date operTime,String memo) {
		OMSParam omsParam = new OMSParam();
		omsParam.setId(sequenceUtil.getSequenceLong("SEQ_CSMSOMSPARAM_NO"));
		omsParam.setOmsId(omsId);
		omsParam.setOmsType(omsType);
		omsParam.setParamId(paramId);
		/*omsParam.setParamChName(paramChName);*/
		omsParam.setParamValue(paramValue);
		omsParam.setType(type);
		omsParam.setState(state);
		omsParam.setOperId(operId);
		omsParam.setOperNo(operNo);
		omsParam.setOperName(operName);
		omsParam.setOperTime(operTime);
		omsParam.setMemo(memo);
		if(memo.equals("null"))
			omsParam.setMemo("");
		omsParamDao.add(omsParam);
		return;
	}

	/**
	 * @Descriptioqn:
	 * @param omsId
	 * @param omsType
	 * @return
	 * @author lgm
	 * @date 2017年3月6日
	 */
	@Override
	public void delete(Long omsId,String omsType) {
		omsParamDao.delete(omsId,omsType);
		return;
	}

	/**
	 * @Descriptioqn:
	 * @param paramNo
	 * @param paramChName
	 * @param paramValue
	 * @param code
	 * @param type
	 * @param state
	 * @param operId
	 * @param operNo
	 * @param operName
	 * @param operTime
	 * @param memo
	 * @return
	 * @author lgm
	 * @date 2017年3月6日
	 */
	@Override
	public void update(Long omsId,String omsType,Long paramId,String paramValue,String type,String state,Long operId,String operNo,String operName,Date operTime,String memo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(operTime);
		omsParamDao.update(omsId,omsType,paramId,paramValue,type,state,operId,operNo,operName,time,memo);
		return;
	}
	/**
	 * 查询营运系统参数（具体要看接口文档，传入key，获得具体营运参数）
	 */
	@Override
	public Map<String,Object> findOmsParam(String key) {
		Map<String, Object> map = new HashMap<String,Object>();
		ServiceParamSetNew spn = omsParamDao.findOmsParam(key);
		if(spn==null) {
			map.put("flag", "-1");
			map.put("message", "无法找到该键值");
		}else {
			map.put("flag", "0");
			map.put("message", "成功");
			map.put("value", spn.getValue());
			map.put("unit", spn.getUnit());
		}
//		try {
//			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getServiceParam.do";
//			
//			JSONObject json = new JSONObject();
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
//			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
//			json.accumulate("timer", format.format(new Date()));
//			String data = DesEncrypt.getInstance().encrypt(json.toString());
//			
//			System.out.println(url+"?au_token="+data+"&key="+key);
//			map = HttpUtil.callClientByHTTP(url, "au_token="+data+"&key="+key, "POST");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage()+"营运接口，查询参数失败！");
//		}
		return map;
	}
}