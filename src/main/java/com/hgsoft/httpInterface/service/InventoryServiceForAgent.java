package com.hgsoft.httpInterface.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 营运接口库存接口
 * @author zxy
 * 2016年3月28日
 */
@Service
public class InventoryServiceForAgent implements IInventoryServiceForAgent{
	
	private static Logger logger = Logger.getLogger(InventoryServiceForAgent.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;

	
	/*
	 * cardnno:卡号，type：（1：发行，2：回收,3:冲正，6：备件发行，7：备件回收，8备件冲正），interfaceRecord：营运接口调用记录,outType：出库类型(传"replace"为更换出库，"issue"为发行出库)
	 * cardType:粤通卡传1，obu传2，obu导入卡激活卡传3
	 */
	@Override
	public Map<String, Object> omsInterface(String cardno, String type, InterfaceRecord interfaceRecord,String outType,Long placeID,Long operID,String operName,String cardType,String flagStr,Long productInfo,BigDecimal price,String serviceType){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		try {
			//营运库存处理
			boolean updateOmsState = true;//是否需要访问营运接口
//			Date operTime = new Date();
//			interfaceRecord = interfaceRecordDao.findByNullCsmsState(cardno,type);
//			map.put("result", false);
//			if (interfaceRecord != null) {
//				if (interfaceRecord.getOmsState() != null) {
//					updateOmsState = false;
//				}else {
//					updateOmsState = true;
//					operTime = interfaceRecord.getOperTime();
//				}
//			} else {
//				//新建营运接口调用登记记录
//				Long id = Long.valueOf(sequenceUtil.getSequence("SEQ_CSMS_InterfaceRecord_NO").toString());
//				interfaceRecord = new InterfaceRecord();
//				interfaceRecord.setCardno(cardno);
//				interfaceRecord.setOperTime(operTime);
//				interfaceRecord.setType(type);
//				interfaceRecord.setId(id);
//				interfaceRecord.setRealPrice(price);
//				interfaceRecord.setSerType(serviceType);
//				int i = interfaceRecordDao.save(interfaceRecord);
//				if(i==0){
//					map.put("message", "发行失败，保存数据异常");
//					return map;
//				}
//				System.out.println(i);
//				updateOmsState = true;
//			}
			
			//如果访问营运接口失败，直接返回失败结果
			if (updateOmsState) {
				//调用营运接口
				String message = "";
				Map<String, Object> initializedOrNotMap = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject json = new JSONObject();
				json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
				json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
				json.accumulate("timer", System.currentTimeMillis());
				String data = DesEncrypt.getInstance().encrypt(json.toString());
				if (type.equals("1")) {
					//outType出库类型	电子标签的有偿更换为发行出库，无偿更换为更换出库。
					if("2".equals(cardType)){
						//电子标签发行
						initializedOrNotMap = initializedOrNot(cardType, cardno, data);
						message = (String)initializedOrNotMap.get("message");
						String flag = (String)initializedOrNotMap.get("flag");
						if(!"-1".equals(flag)){
							Date startDate = format.parse((String)initializedOrNotMap.get("startDate"));
							Date endDate = format.parse((String)initializedOrNotMap.get("endDate"));
							initializedOrNotMap.put("startDate", startDate);
							initializedOrNotMap.put("endDate", endDate);
						}
						map.put("initializedOrNotMap", initializedOrNotMap);
					}else{
						initializedOrNotMap = initializedOrNot(cardType, cardno, data);
						
						message = (String)initializedOrNotMap.get("message");
						String flag = (String)initializedOrNotMap.get("flag");
						if(!"-1".equals(flag)){
							Date startDate = format.parse((String)initializedOrNotMap.get("startDate"));
							Date endDate = format.parse((String)initializedOrNotMap.get("endDate"));
							initializedOrNotMap.put("startDate", startDate);
							initializedOrNotMap.put("endDate", endDate);
						}
						map.put("initializedOrNotMap", initializedOrNotMap);
					}
					
				}
				map.put("message", message);
				System.out.println("message:="+message);
				if (!StringUtil.isNotBlank(message)) {
					return map;
				}else if(!message.equals("产品已正常初始化！")){
					//这里多出来了一个“更换成功”
					return map;
				}
				//访问营运接口成功后，更新营运接口调用登记记录的营运状态
//				interfaceRecord.setOmsState("1");
//				interfaceRecord.setRealPrice(price);
//				interfaceRecordDao.update(interfaceRecord);
				
			}
			map.put("result", true);
//			map.put("interfaceRecord", interfaceRecord);
			logger.info(map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	
	/**
	 * 检测产品是否已经初始化接口
	 */
	@Override
	public Map<String, Object> initializedOrNot(String cardType,String productCode,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_initializedOrNot.do";
		
			System.out.println(url+"?"+"cardType="+cardType+"&productCode="+productCode+"&au_token="+au_token);
			Map<String, Object> map = HttpUtil.callClientByHTTP(url, "cardType="+cardType+"&productCode="+productCode+"&au_token="+au_token, "POST");
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，检测产品是否已经初始化失败！");
		}
		return null;
		
	}
	
	/**
	 * 生成校验位
	 * @param str
	 * @return
	 */
	public static String getCardNo(String str){
		if(str!=null){
			char a[] =  str.toCharArray();
			int sum=0;
			int len = str.length();
			for(int i=0;i<len;i++){
				sum += (a[i]-0x30)*(len-i+1);
			}
			int k = (10-sum%10)==10?0:10-sum%10;
			return str+Integer.toString(k);
		}else{
			return "";
		}
	}


}
