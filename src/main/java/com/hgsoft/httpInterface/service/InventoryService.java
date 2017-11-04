package com.hgsoft.httpInterface.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.httpInterface.dao.InterfaceRecordDao;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryService;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.PropertiesUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 营运接口库存接口
 * @author zxy
 * 2016年3月28日
 */
@Service
public class InventoryService implements IInventoryService{
	
	private static Logger logger = Logger.getLogger(InventoryService.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private InterfaceRecordDao interfaceRecordDao;
	
	/**
	 * 
	 * @param productCode
	 * @param customPoint
	 * @param createId
	 * @param createName
	 * @param operTime
	 * @param type
	 * @param cardType 粤通卡传1，obu传2，obu导入卡激活卡传3
	 * @param flagStr 网点发行传“customPoint”,操作员发行传“admin”
	 * @param au_token 
	 * @return
	 */
	@Override
	public String issue(String productCode, String customPoint, String createId, String createName, String operTime, String type,String cardType,String flagStr,String productInfo,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_issue.do";
			/*JSONObject json = new JSONObject();
			json.accumulate("productCode", productCode);
			json.accumulate("customPoint", customPoint);
			json.accumulate("createId", createId);
			json.accumulate("createName", createName);
			json.accumulate("issueDate", operTime);
			
			System.out.println("json:="+json.toString());*/
			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token);
//			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			//需要取到obuid，返回所有数据
			Map<String, Object> map = HttpUtil.callClientByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			StringBuffer buffer = new StringBuffer();
			buffer.append(map.get("message")).append(",").append(map.get("obuSerial")).append(",").
				append(map.get("startDate")).append(",").append(map.get("endDate"));
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，发行查库存失败！");
		}
		return "";
		
	}

	@Override
	public String reclaim(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token,String newCardSourceType) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_reclaim.do";
			/*JSONObject json = new JSONObject();
			json.accumulate("productCode", productCode);
			json.accumulate("customPoint", customPoint);
			json.accumulate("createId", createId);
			json.accumulate("createName", createName);
			json.accumulate("issueDate", operTime);
			
			System.out.println("json:="+json.toString());*/
			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token+"&newCardSourceType="+newCardSourceType);
			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token+"&newCardSourceType="+newCardSourceType, "POST");
			} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，回收查库存失败！");
		}
		return "";
		
	}
	
	@Override
	public String refund(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_refund.do";
			/*JSONObject json = new JSONObject();
			json.accumulate("productCode", productCode);
			json.accumulate("customPoint", customPoint);
			json.accumulate("createId", createId);
			json.accumulate("createName", createName);
			json.accumulate("issueDate", operTime);
			
			System.out.println("json:="+json.toString());*/
			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token);
			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token, "POST");
			} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，回收查库存失败！");
		}
		return "";
		
	}
	//备件发行   type:"replace"(更换出库)、"issue"(发行出库)
	@Override
	public String issueReplacement(String productCode, String customPoint, String createId, String createName, String operTime, String type,String cardType,String flagStr,String productInfo,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_issueReplacement.do";
			
			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token);
//			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			
			Map<String, Object> map = HttpUtil.callClientByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&type="+type+"&cardType="+cardType+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			StringBuffer buffer = new StringBuffer();
			buffer.append(map.get("message")).append(",").append(map.get("obuSerial")).append(",").
			append(map.get("startDate")).append(",").append(map.get("endDate"));
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，备件发行查库存失败！");
		}
		return "";
		
	}
	//备件回收
	@Override
	public String reclaimReplacement(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_reclaimReplacement.do";

			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token);
			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token, "POST");
			} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，回收查库存失败！");
		}
		return "";
		
	}
	//备件冲正
	@Override
	public String refundReplacement(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_refundReplacement.do";
		
			System.out.println(url+"?"+"productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token);
			return HttpUtil.callByHTTP(url, "productCode="+productCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&cardType="+cardType+"&au_token="+au_token, "POST");
			} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，回收查库存失败！");
		}
		return "";
		
	}
	
	/*
	 * cardnno:卡号，type：（1：发行，2：回收,3:冲正，6：备件发行，7：备件回收，8备件冲正），interfaceRecord：营运接口调用记录,outType：出库类型(传"replace"为更换出库，"issue"为发行出库)
	 * cardType:粤通卡传1，obu传2，obu导入卡激活卡传3
	 * newCardSourceType:新卡的产品来源产品来源，只有回收的时候传入
	 */
	@Override
	public Map<String, Object> omsInterface(String cardno, String type, InterfaceRecord interfaceRecord,String outType,Long placeID,Long operID,String operName,String cardType,String flagStr,Long productInfo,BigDecimal price,String serviceType,String newCardSourceType){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//营运库存处理
			boolean updateOmsState = true;//是否需要访问营运接口
			Date operTime = new Date();
			interfaceRecord = interfaceRecordDao.findByNullCsmsState(cardno,type);
			map.put("result", false);
			if (interfaceRecord != null) {
				if (interfaceRecord.getOmsState() != null) {
					updateOmsState = false;
				}else {
					updateOmsState = true;
					operTime = interfaceRecord.getOperTime();
				}
			} else {
				//新建营运接口调用登记记录
				Long id = Long.valueOf(sequenceUtil.getSequence("SEQ_CSMS_InterfaceRecord_NO").toString());
				interfaceRecord = new InterfaceRecord();
				interfaceRecord.setCardno(cardno);
				interfaceRecord.setOperTime(operTime);
				interfaceRecord.setType(type);
				interfaceRecord.setId(id);
				interfaceRecord.setRealPrice(price);
				interfaceRecord.setSerType(serviceType);
				int i = interfaceRecordDao.save(interfaceRecord);
				if(i==0){
					map.put("message", "发行失败，保存数据异常");
					return map;
				}
				System.out.println(i);
				updateOmsState = true;
			}
			
			//如果访问营运接口失败，直接返回失败结果
			if (updateOmsState) {
				//调用营运接口
				String message = "";
				String resultTem = "";
				String obuSerial ="";
				Date startDate = null;
				Date endDate = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject json = new JSONObject();
				json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
				json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
				//json.accumulate("timer", System.currentTimeMillis());
				json.accumulate("timer", format.format(new Date()));
				String data = DesEncrypt.getInstance().encrypt(json.toString());
				if (type.equals("1")) {
					//outType出库类型	电子标签的有偿更换为发行出库，无偿更换为更换出库。
					if("2".equals(cardType)){
						//电子标签发行
						resultTem = issue(cardno, placeID!=null?placeID.toString():"",operID!=null?operID.toString():"", operName,operTime!=null?format.format(operTime):"",outType,cardType,flagStr,productInfo!=null?productInfo.toString():"",data);
						String[] arr = resultTem.split(",");
						message = arr[0];
						if(arr.length>1){
							obuSerial = arr[1];
							if(!arr[2].equals("null")){
								startDate = format.parse(arr[2]);
							}
							if(!arr[3].equals("null")){
								endDate = format.parse(arr[3]);
							}
						}
						map.put("obuSerial", obuSerial);
						map.put("startDate", startDate);
						map.put("endDate", endDate);
					}else{
						resultTem = issue(cardno, placeID.toString(),operID.toString(), operName,format.format(operTime),outType,cardType,flagStr,"",data);
						String[] arr = resultTem.split(",");
						message = arr[0];
						if(!arr[2].equals("null")){
							startDate = format.parse(arr[2]);
						}
						if(!arr[3].equals("null")){
							endDate = format.parse(arr[3]);
						}
						map.put("startDate", startDate);
						map.put("endDate", endDate);
					}
					
				}else if (type.equals("2")){
					//2017/05/05营运接口增加了newCardSourceType新卡的产品来源
					message = reclaim(cardno, placeID.toString(),operID.toString(), operName,format.format(operTime),cardType,data,newCardSourceType);
				}else if (type.equals("3")){
					message = refund(cardno, placeID.toString(),operID.toString(), operName,format.format(operTime),cardType,data);
				}else if (type.equals("6")){
					//备件发行
					if("2".equals(cardType)){
						resultTem = issueReplacement(cardno, placeID.toString(),operID.toString(), operName, format.format(operTime), outType,cardType,flagStr,productInfo.toString(), data);
					
						String[] arr = resultTem.split(",");
						message = arr[0];
						if(arr.length>1){
							obuSerial = arr[1];
							if(!arr[2].equals("null")){
								startDate = format.parse(arr[2]);
							}
							if(!arr[3].equals("null")){
								endDate = format.parse(arr[3]);
							}
						}
						map.put("obuSerial", obuSerial);
						map.put("startDate", startDate);
						map.put("endDate", endDate);
					}else{
						resultTem = issueReplacement(cardno, placeID.toString(),operID.toString(), operName, format.format(operTime), outType,cardType,flagStr,"", data);
						String[] arr = resultTem.split(",");
						message = arr[0];
						if(!arr[2].equals("null")){
							startDate = format.parse(arr[2]);
						}
						if(!arr[3].equals("null")){
							endDate = format.parse(arr[3]);
						}
						map.put("startDate", startDate);
						map.put("endDate", endDate);
					}
					
				}else if (type.equals("7")){
					//备件回收
					message = reclaimReplacement(cardno, placeID.toString(),operID.toString(), operName,format.format(operTime),cardType,data);
				}else if (type.equals("8")){
					//备件冲正
					message = refundReplacement(cardno, placeID.toString(),operID.toString(), operName,format.format(operTime),cardType,data);
				}
				map.put("message", message);
				System.out.println("message:="+message);
				if (!StringUtil.isNotBlank(message)) {
					return map;
				}else if(!message.equals("发行成功")&&!message.equals("更换成功")&&!message.equals("回收成功")&&!message.equals("冲正成功")){
					//这里多出来了一个“更换成功”
					return map;
				}
				
				//2017/05/06  如果访问营运发行接口成功，但是执行客服系统业务失败，下次发行这个卡号就不会调用营运的发行接口，所以要加上这两个字段。
				interfaceRecord.setStartDate(startDate);
				interfaceRecord.setEndDate(endDate);
				interfaceRecord.setObuSerial(obuSerial);
				
				//访问营运接口成功后，更新营运接口调用登记记录的营运状态
				interfaceRecord.setOmsState("1");
				interfaceRecord.setRealPrice(price);
				interfaceRecordDao.update(interfaceRecord);
				
			}else{
				//不用调用营运接口的情况,就要从interfaceRecord获得有效启用时间与有效截至时间。
				map.put("startDate", interfaceRecord.getStartDate());
				map.put("endDate", interfaceRecord.getEndDate());
				map.put("obuSerial", interfaceRecord.getObuSerial());
			}
			
			map.put("result", true);
			map.put("interfaceRecord", interfaceRecord);
			logger.info(map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 
	 * @param interfaceRecord 营运接口调用记录
	 * @param startCode 电子标签起始编码
	 * @param endCode 电子标签结束编码
	 * @param placeId 营业网点id
	 * @param operID 用户Id
	 * @param operName 用户名称
	 * @param type 业务类型(4.电子标签提货、5.电子标签冲正)
	 * @return
	 */
	@Override
	public Map<String, Object> tagInterface(InterfaceRecord interfaceRecord,String startCode,String endCode,Long placeID,Long operID,String operName,String flagStr,Long productInfo,String type,BigDecimal price,String serviceType){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//营运库存处理
			boolean updateOmsState = true;//是否需要访问营运接口
			Date operTime = new Date();
			interfaceRecord = interfaceRecordDao.findByStartEndType(startCode, endCode, type);
			map.put("result", false);
			if (interfaceRecord != null) {
				if (interfaceRecord.getOmsState() != null) {
					updateOmsState = false;
				}else {
					updateOmsState = true;
					operTime = interfaceRecord.getOperTime();
				}
			} else {
				//新建营运接口调用登记记录
				Long id = Long.valueOf(sequenceUtil.getSequence("SEQ_CSMS_InterfaceRecord_NO").toString());
				interfaceRecord = new InterfaceRecord();
				interfaceRecord.setCardno("0");
				interfaceRecord.setStartCode(startCode);
				interfaceRecord.setEndCode(endCode);
				interfaceRecord.setOperTime(operTime);
				interfaceRecord.setType(type);
				interfaceRecord.setId(id);
				interfaceRecord.setRealPrice(price);
				interfaceRecord.setSerType(serviceType);
				interfaceRecordDao.save(interfaceRecord);
				
				updateOmsState = true;
			}
			
			//如果访问营运接口失败，直接返回失败结果
			if (updateOmsState) {
				//调用营运接口
				String message = "";
				String resultTem = "";
				String serialIds = "";
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject json = new JSONObject();
				json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
				json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
				json.accumulate("timer", System.currentTimeMillis());
				String data = DesEncrypt.getInstance().encrypt(json.toString());
				if (type.equals("4")) {
					//电子标签提货接口
					resultTem = pickup(startCode,endCode, placeID.toString(),operID.toString(), operName,format.format(operTime),"customPoint",productInfo.toString(),data);
					String[] arr = resultTem.split(";");
					message = arr[0];
					if(arr.length>1){
						serialIds = arr[1];
					}
				
				}else if (type.equals("5")){
					//电子标签冲正
					message = refundPickup(startCode,endCode, placeID.toString(),operID.toString(), operName,format.format(operTime),"customPoint","",data);
				}
				map.put("serialIds", serialIds);
				map.put("message", message);
				System.out.println("message:="+message);
				if (!StringUtil.isNotBlank(message)) {
					return map;
				}else if(!message.equals("提货成功")&&!message.equals("冲正提货成功")){
					return map;
				}
				//访问营运接口成功后，更新营运接口调用登记记录的营运状态
				interfaceRecord.setRealPrice(price);
				interfaceRecord.setOmsState("1");
				interfaceRecordDao.update(interfaceRecord);
				
			}
			map.put("result", true);
			map.put("interfaceRecord", interfaceRecord);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 电子标签提货接口
	 */
	@Override
	public String pickup(String startCode,String endCode, String customPoint, String createId, String createName, String operTime,String flagStr,String productInfo,String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_pickup.do";
			/*JSONObject json = new JSONObject();
			json.accumulate("productCode", productCode);
			json.accumulate("customPoint", customPoint);
			json.accumulate("createId", createId);
			json.accumulate("createName", createName);
			json.accumulate("issueDate", operTime);
			
			System.out.println("json:="+json.toString());*/
			System.out.println(url+"?"+"startCode="+startCode+"&endCode="+endCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token);
//			return HttpUtil.callByHTTP(url, "startCode="+startCode+"&endCode="+endCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			Map<String, Object> map = HttpUtil.callClientByHTTP(url, "startCode="+startCode+"&endCode="+endCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
			StringBuffer buffer = new StringBuffer();
			buffer.append(map.get("message")).append(";").append(map.get("obuSerialList"));
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，电子标签提货查库存失败！");
		}
		return "";
		
	}
	/**
	 * 电子标签提货冲正接口
	 */
	@Override
	public String refundPickup(String startCode, String endCode, String customPoint, String createId, String createName,
			String operTime, String flagStr, String productInfo, String au_token) {
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_refundPickup.do";
			/*JSONObject json = new JSONObject();
			json.accumulate("productCode", productCode);
			json.accumulate("customPoint", customPoint);
			json.accumulate("createId", createId);
			json.accumulate("createName", createName);
			json.accumulate("issueDate", operTime);
			
			System.out.println("json:="+json.toString());*/
			System.out.println(url+"?"+"startCode="+startCode+"&endCode="+endCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token);
			return HttpUtil.callByHTTP(url, "startCode="+startCode+"&endCode="+endCode+"&customPoint="+customPoint+"&createId="+createId+"&createName="+createName+"&operTime="+operTime+"&flagStr="+flagStr+"&productInfo="+productInfo+"&au_token="+au_token, "POST");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"营运接口，电子标签提货冲正查库存失败！");
		}
		return "";
	}
	
	/**
	 * 获得营运的电子标签产品列表
	 * @return
	 */
	@Override
	public Map<String, Object> getProductInfo(){
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"interface/customPointBiz_findObuProducts.do";
			JSONObject json = new JSONObject();
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", System.currentTimeMillis());
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			return HttpUtil.callClientByHTTP(url, "&au_token="+data, "POST");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过电子标签号获得营运的电子标签产品类型
	 * @return
	 */
	@Override
	public Map<String, Object> getProductTypeByCode(String cardType,String productCode){
		try {
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getProductTypeByCode.do";
			JSONObject json = new JSONObject();
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", System.currentTimeMillis());
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			return HttpUtil.callClientByHTTP(url, "cardType="+cardType+"&productCode="+productCode+"&au_token="+data, "POST");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
