package com.hgsoft.timerTask.service;

import java.math.BigDecimal;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.common.Enum.MQTimerEnum;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.AcCancelInfoDao;
import com.hgsoft.macao.dao.AcIssuceInfoDao;
import com.hgsoft.macao.dao.AcIssuedStopInfoDao;
import com.hgsoft.macao.dao.AcLossInfoDao;
import com.hgsoft.macao.dao.AcStopInfoDao;
import com.hgsoft.macao.dao.AcUnLossInfoDao;
import com.hgsoft.macao.dao.ServerChangeInfoDao;
import com.hgsoft.macao.dao.TagMigrateInfoDao;
import com.hgsoft.macao.dao.TagReplaceInfoDao;
import com.hgsoft.macao.dao.TagissuceInfoDao;
import com.hgsoft.macao.dao.VechileChangeInfoDao;
import com.hgsoft.macao.entity.AcCancelInfo;
import com.hgsoft.macao.entity.AcIssuceInfo;
import com.hgsoft.macao.entity.AcIssuedStopInfo;
import com.hgsoft.macao.entity.AcLossInfo;
import com.hgsoft.macao.entity.AcStopInfo;
import com.hgsoft.macao.entity.AcUnLossInfo;
import com.hgsoft.macao.entity.ServerChangeInfo;
import com.hgsoft.macao.entity.TagIssuceInfo;
import com.hgsoft.macao.entity.TagMigrateInfo;
import com.hgsoft.macao.entity.TagReplaceInfo;
import com.hgsoft.macao.entity.VechileChangeInfo;
import com.hgsoft.mq.util.InterruptFlag;
import com.hgsoft.mq.util.MQAnalysis;
import com.hgsoft.mq.util.MQServerObject;
import com.hgsoft.timerTask.serviceinterface.IMQTimerTaskService;
import com.hgsoft.timerTask.vo.MQTimerVo;

/**
 * 澳门通清算接口定时任务
 * @author gaosiling
 * 2016年12月26日 14:02:30
 */
@Service
public class MQTimerTaskService implements IMQTimerTaskService{

	private static Logger logger = Logger.getLogger(MQTimerTaskService.class.getName());

	@Resource
	MQServerObject mqServerObject;
	@Resource
	private AcLossInfoDao acLossInfoDao;
	@Resource
	private AcIssuceInfoDao acIssuceInfoDao;
	@Resource
	private AcCancelInfoDao acCancelInfoDao;
	@Resource
	private VechileChangeInfoDao vechileChangeInfoDao;
	@Resource
	private ServerChangeInfoDao serverChangeInfoDao;
	@Resource
	private TagissuceInfoDao tagissuceInfoDao;
	@Resource
	private TagReplaceInfoDao tagReplaceInfoDao;
	@Resource
	private TagMigrateInfoDao tagMigrateInfoDao;
	@Resource
	private AcStopInfoDao acStopInfoDao;
	@Resource
	private AcUnLossInfoDao acUnLossInfoDao;
	@Resource
	private AcIssuedStopInfoDao acIssuedStopInfoDao;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char ch = InterruptFlag.A;// 440106001
		MQTimerTaskService mts=new MQTimerTaskService();
//		mts.saveIssueAgain_test();//记帐卡发行
//		mts.saveLoss_test();//记帐卡挂失
//		mts.saveUnLoss_test();//记帐卡解挂
//		mts.saveCancelCard_test();//记帐卡注销
//		mts.saveStopCard_test();//下发止付黑名单
//		mts.saveRelieveStopCard_test();//解除止付黑名单
//		mts.saveVehicleChange_test();//卡片车牌变更
//		mts.saveServerChange_test();//发票邮寄登记/取消
//		mts.saveTagIssueAgain_test();//电子标签发行
//		mts.saveTagReplace_test();//电子标签更换
//		mts.saveMigrate_test();//电子标签迁移
		
		
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.vehicleCheck.getValue());//接口编码
		list.add("粤A1234");//车牌号码
		list.add("2");//车牌颜色 2:黑色
		logger.info("参数列表："+list);
		try {
			String msg = MQAnalysis.listToString(list);
			MQServerObject mqServerObject = new MQServerObject();
			String result = mqServerObject.setAndGetMsg(msg);
			if(result!=null && !"".equals(result)){
				list = MQAnalysis.analysis(result);
				logger.info("车牌校验结果:"+list);
				if("00".equals(list.get(0))){
					System.out.println("该车牌可用！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("车牌校验结果失败原因："+e.getMessage());
		}

	}
		//挂失
		/**String ss  = ch
		+ "91001"
		+ ch+ "1708230000000309"
		+ ch+ "2"
		+ ch + "122"
		+ ch + "440700001"
		+ ch + "YY1001"
		+ ch;*/

		/**String ss  = ch
				+ "91010"
				+ ch + "4401160128001771"      //标签号
				+ ch + "粤E99022"           //license
				+ ch + "1"                     //color
				+ ch + "Q1"                    //厂牌型号
				+ ch + "1"                     //车辆种类
				+ ch + "7"                     //座位数
				+ ch + "5.11"                  //长
				+ ch + "3.11"                  //宽
				+ ch + "4.11"                  //高
				+ ch + "2"  //轴数
				+ ch + "4"//轮数
				+ ch + "2017/01/11 12:33:33"
				+ ch + "AMT001"
				+ ch + "主任1"
				+ ch + "860223001"
				+ ch + "澳门通股份有限公司"
				+ ch + "2017/01/11 12:33:33"
				+ ch + "0001"//销售方式
				+ ch + "操作员1"//安装员名称
				+ ch + "AMT001"//操作员编码
				+ ch + "主任1"//操作员名称
				+ ch + "860223001"	    //网点编码
				+ ch + "澳门通股份有限公司"//网点名称
				+ ch + "MH06k"//客户名称
				+ ch + "MH06c"	 //车主名称
				+ ch + "512000"	 //邮政编码
				+ ch + "广州大道南368号"	 //联系地址
				+ ch + "mh001"	 //联系人
				+ ch + "02011111111"//联系电话
				+ ch + "13888888888"//联系手机001
				+ ch + "S111111111"//车辆识别代码
				+ ch + "1"//国标收费车型
				+ ch + "0"//使用性质
				+ ch + "1111111111"//车辆特征描述
				+ ch + "F22222222222222"//发动机号
				+ ch + "00"//绑定标志
				+ ch + "20270101"//标签有效日期
				+ ch + "S23233333333333331"//标签芯片号
				+ ch + "17"//系统编号
				+ ch + ""//临时办卡点
				+ ch + "电子标签信息变更（澳门）"//业务说明
				+ ch + "200"//应收金额
				+ ch + "1000"//实收金额
				+ ch + "5"//支付方式
				+ ch + ""//支付账号
				+ ch + "YY1001"//授权操作员
				+ ch;
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(ss);
		System.out.println(result);
		result.lastIndexOf(InterruptFlag.A)
	//	String[] s = result.split(String.valueOf(ch));


		List<String>list = MQAnalysis.analysis(result);
		System.out.println("===================================");
		for (String string : list) {
			System.out.println(string);
		}*/

	//记帐卡发行
	public synchronized void saveIssueAgain_test(){

		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
//			listsetnull(list,18);
		list.add(MQTimerEnum.ACISSUCE.getValue());//接口编码
		//list.add(acIssuceInfo.getCode());//记帐卡编码
		list.add("1542232208233023");//记帐卡编码1707233205000228,1415232000001012,1535232208302550,1708230000000309、1542232208233104、1542232208233049
		list.add(timerVo.getReqNo());//申请编号（固定值：416C0T00CM）、0000000050
		list.add(timerVo.getPlaceNo());//网点编码（固定值：860223001）
		list.add(timerVo.getOperNo());//操作员编码（固定值：AMT001）
		list.add(timerVo.getApliayType());//支付方式（固定值：5）
		list.add(timerVo.getApliayAccount());//支付账号（“”留空）
		list.add("0.00");//应收金额(单位元，保留2位小数)
		list.add("0.00");//实收金额(单位元，保留2位小数)
		list.add(timerVo.getSaleType());//销售方式(固定值：0001)
		list.add("");//发行流水号（“”留空）
		list.add("粤YY9541");//车牌号码(粤ZQ595澳已绑定卡片)粤YY9541、
		list.add("2");//车牌颜色（2：黑色）
		list.add("2");//车辆种类（0：货车 2：客车）
		list.add("4");//座位数
		list.add("1");//国标收费车型
		list.add(timerVo.getAuthMemberNo());//授权人编号(固定值：AMT001)
		list.add(timerVo.getSysNo());//系统编号（固定值：17）
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//记帐卡挂失
	public synchronized void saveLoss_test(){

		MQTimerVo timerVo = new MQTimerVo();
		//创建集合，将要传入的数据按顺序放入
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.ACLOSSINFO.getValue());
		//list.add(acLossInfo.getCode());
		list.add("1542232208233023");//1708230000000309
		list.add(timerVo.getSaveLossFlag());
		list.add(timerVo.getBankNo());
		list.add(timerVo.getPlaceNo());
		list.add(timerVo.getOperNo());
		logger.info("参数列表："+list);
		//将集合转成澳门通消息报文对象
		String msg = MQAnalysis.listToString(list);
		//将消息发送到澳门通消息队列并接收返回值
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//记帐卡解挂
	public synchronized void saveUnLoss_test(){

		MQTimerVo timerVo = new MQTimerVo();
		//创建集合，将要传入的数据按顺序放入
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.ACUNLOSS.getValue());//接口编码
		list.add(timerVo.getUnLossFlag());//标志位
		list.add("1542232208233065");//记帐卡编码
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getOperNo());//操作员编码
		logger.info("参数列表："+list);
		//将集合转成澳门通消息报文对象
		String msg = MQAnalysis.listToString(list);
		//将消息发送到澳门通消息队列并接收返回值
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//记帐卡注销
	public synchronized void saveCancelCard_test(){

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.ACCANNEL.getValue());//接口编码
		list.add("1533232202382574");//记帐卡编码1708230000000309
		list.add(timerVo.getCancelFlag());//标志位
		list.add(timerVo.getBankNo());//银行账号0085328727688
		list.add("2");//注销方式(1-有卡注销 2-无卡注销)
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getOperNo());//操作员编码

		try {
			list.add(format2.format(format.parse("2017-03-25 17:01:00")));//业务办理时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		list.add(timerVo.getCancelAccountFlag());//销户标识
		list.add(timerVo.getCancelReason());//注销原因
		list.add("main方法测试");//备注
		list.add(timerVo.getSysNo());//系统编号
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//下发止付黑名单
	public synchronized void saveStopCard_test(){

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.ACSTOP.getValue());//接口编码
		list.add("1708230000000309");//卡号
		list.add(timerVo.getBankNo());//银行账号
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getOperNo());//操作员编码
		list.add("4");//下方原因
		try {
			list.add(format2.format(format.parse("2017-03-20 17:06:00")));//下发时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//解除止付黑名单
	public synchronized void saveRelieveStopCard_test(){

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.ACUNSTOP.getValue());//接口编码
		list.add("1708230000000309");//卡号
		list.add(timerVo.getBankNo());//银行账号
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getOperNo());//操作员编码
		try {
			list.add(format2.format(format.parse("2017-03-20 17:06:00")));//下发时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//卡片车牌变更
	public synchronized void saveVehicleChange_test(){

		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.VECHILECHANGE.getValue());//接口编码
		list.add("1");//服务类型
		list.add("1542232208233081");//卡号156232202454350
		list.add("粤YY1237");//车牌
		list.add("2");//车牌颜色
		list.add("2");//车辆种类
		list.add("4");//座位数
		list.add("1");//国标收费车型
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getOperNo());//操作员编码
		list.add("main方法测试车辆变更");//备注
		list.add(timerVo.getSysNo());//系统编号
		list.add("1");//写卡标识
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		//		logger.info("===="+msg);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//发票邮寄登记/取消
	public synchronized void saveServerChange_test(){

		MQTimerVo timerVo = new MQTimerVo();

		List<String> list = new ArrayList<String>();
		//		list.add(MQTimerEnum.SERVERREGISTER.getValue());//接口编码
		list.add(MQTimerEnum.SERVERCANNEL.getValue());//接口编码
		list.add(timerVo.getUserNo());//客户号
		list.add("feng");//客户名称
		list.add("1708230000000309");//卡号
		list.add("抬头");//发票抬头
		list.add("main方法测试");//备注
		list.add(timerVo.getSysNo());//系统编号
		list.add(timerVo.getOperNo());//操作员编码
		list.add(timerVo.getPlaceNo());//网点编码
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//电子标签发行
	public synchronized void saveTagIssueAgain_test(){

		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.TAGISSUCE.getValue());//接口编码
		list.add("4401201703070001");//电子标签号
		list.add("粤M30014");//车牌
		list.add("2");//车牌颜色
		list.add("123456");//厂牌型号
		list.add("2");//车辆种类
		list.add("4");//座位数
		BigDecimal bdstr=new BigDecimal("1000");
		list.add(new BigDecimal(Long.valueOf("1111")).divide(bdstr).toString());//车辆长"1111"
		list.add(new BigDecimal(Long.valueOf("1111")).divide(bdstr).toString());//车辆宽
		list.add(new BigDecimal(Long.valueOf("1111")).divide(bdstr).toString());//车辆高
		list.add("2");//轴数
		list.add("4");//轮数
		list.add(timerVo.getSaleType());//销售方式
		list.add(timerVo.getInstallMan());//安装员名称
		list.add(timerVo.getOperNo());//操作员编码
		list.add(timerVo.getOperName());//操作员名称
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getPlaceName());//网点名称
		list.add("feng");//客户名称
		list.add("feng");//车主名称
		list.add("555555");//邮政编码
		list.add("阳江");//联系地址
		list.add("feng");//联系人
		list.add("13333333333");//联系电话
		list.add("13333333333");//联系手机
		list.add("11111122222233333");//车辆识别代码
		list.add("1");//国标收费车型
		list.add("1");//使用性质
		//list.add(tagIssuceInfo.getModel().substring(0, 16));//车辆特征描述
		list.add("123456");//车辆特征描述
		list.add("121212121");//发动机号
		list.add("20200320");//标签有效日期
		list.add("");//标签芯片号
		list.add(timerVo.getSysNo());//系统编号
		list.add("");//临时办卡点
		list.add("电子标签发行（澳门）");//业务说明
		list.add("0.00");
		list.add("0.00");
		list.add(timerVo.getApliayType());//支付方式
		list.add(timerVo.getApliayAccount());//支付账号
		list.add(timerVo.getOperNo());//授权操作员
		list.add(timerVo.getApliayMode());//交易类型
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//电子标签更换
	public synchronized void saveTagReplace_test(){

		MQTimerVo timerVo = new MQTimerVo();
		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.TAGREPLACE.getValue());//接口编码
		list.add("4401201703070003");//旧电子标签号
		list.add("4401201703070003");//原始标签号
		list.add("4401201703070004");//新电子标签号
		list.add("粤M30013");//车牌粤M30065
		list.add("2");//车牌颜色
		list.add("123456");//厂牌型号
		list.add("2");//车辆种类
		list.add("4");//座位数
		BigDecimal bdstr=new BigDecimal("1000");
		list.add(new BigDecimal(Long.valueOf("1110")).divide(bdstr).toString());//车辆长
		list.add(new BigDecimal(Long.valueOf("1110")).divide(bdstr).toString());//车辆宽
		list.add(new BigDecimal(Long.valueOf("1110")).divide(bdstr).toString());//车辆高
		list.add("2");//轴数
		list.add("4");//轮数
		list.add(timerVo.getSaleType());//销售方式
		list.add(timerVo.getInstallMan());//安装员名称
		list.add(timerVo.getOperNo());//操作员编码
		list.add(timerVo.getOperName());//操作员名称
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getPlaceName());//网点名称
		list.add("feng");//客户名称
		list.add("feng");//车主名称
		list.add("555555");//邮政编码
		list.add("阳江");//联系地址
		list.add("feng");//联系人
		list.add("13333333333");//联系电话
		list.add("13333333333");//联系手机
		list.add("11111122222233333");//车辆识别代码
		list.add("1");//国标收费车型
		list.add("1");//使用性质
		list.add("");//空字符串：单标签; 01：套装
		//list.add(tagIssuceInfo.getModel().substring(0, 16));//车辆特征描述
		list.add("123456");//车辆特征描述
		list.add("121212121");//发动机号
		list.add("20200320");//标签有效日期
		list.add("");//标签芯片号
		list.add("2017/03/20 17:06:00");//保修期开始时间
		list.add(timerVo.getSysNo());//系统编号
		list.add(timerVo.getTemporary());//临时办卡点
		list.add("电子标签更换（澳门）");//业务说明
		list.add("0.00");
		list.add("0.00");
		list.add(timerVo.getApliayType());//支付方式
		list.add(timerVo.getApliayAccount());//支付账号
		list.add(timerVo.getOperNo());//授权操作员
		list.add("广州");//维护地点
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);

	}
	//电子标签迁移
	public synchronized void saveMigrate_test(){
		MQTimerVo timerVo = new MQTimerVo();

		List<String> list = new ArrayList<String>();
		list.add(MQTimerEnum.TAGMIGRATE.getValue());//接口编码
		list.add("4401201703070004");//电子标签号4401160128001789
		list.add("粤M30013");//车牌
		list.add("2");//车牌颜色
		list.add("123456789123456789");//厂牌型号
		list.add("2");//车辆种类
		list.add("4");//座位数
		BigDecimal dbstr = new BigDecimal("1000");
		list.add(new BigDecimal("1120").divide(dbstr).toString());//车辆长
		list.add(new BigDecimal("1120").divide(dbstr).toString());//车辆宽
		list.add(new BigDecimal("1120").divide(dbstr).toString());//车辆高
		list.add("2");//轴数
		list.add("4");//轮数
		list.add("2017/03/20 17:06:00");//发行时间
		list.add(timerVo.getIssueOperNo());//发行操作员编码
		list.add(timerVo.getIssueOperName());//发行操作员名称
		list.add(timerVo.getIssuePlaceNo());//发行网点编码
		list.add(timerVo.getIssuePlaceName());//发行网点名称
		list.add("2017/03/20 17:06:00");//保修期开始时间
		list.add(timerVo.getSaleType());//销售方式
		list.add(timerVo.getInstallMan());//安装员名称
		list.add(timerVo.getOperNo());//操作员编码
		list.add(timerVo.getOperName());//操作员名称
		list.add(timerVo.getPlaceNo());//网点编码
		list.add(timerVo.getPlaceName());//网点名称
		list.add("feng");//客户名称
		list.add("feng");//车主名称
		list.add("555555");//邮政编码
		list.add("阳江");//联系地址
		list.add("feng");//联系人
		list.add("13333333333");//联系电话
		list.add("13333333333");//联系手机
		list.add("11111122222233333");//车辆识别代码
		list.add("1");//国标收费车型
		list.add("1");//使用性质
		//list.add(tagIssuceInfo.getModel().substring(0, 16));//车辆特征描述
		list.add("1234567891234567");//车辆特征描述
		list.add("1234567891234567");//发动机号
		list.add("");//空字符串：单标签 ;01：套装
		list.add("20170320");//标签有效日期
		list.add("");//标签芯片号
		list.add(timerVo.getSysNo());//系统编号
		list.add(timerVo.getTemporary());//临时办卡点
		list.add("电子标签信息变更（澳门）");//业务说明
		list.add("0.00");
		list.add("0.00");
		list.add(timerVo.getApliayType());//支付方式
		list.add(timerVo.getApliayAccount());//支付账号
		list.add(timerVo.getOperNo());//授权操作员
		logger.info("参数列表："+list);
		String msg = MQAnalysis.listToString(list);
		MQServerObject mqServerObject = new MQServerObject();
		String result = mqServerObject.setAndGetMsg(msg);
	}


	/********************************华丽分割线***************************/


	/**
	 * 卡片二次（记帐卡发行）
	 */
	public synchronized void saveIssueAgain(){
		try {
			DecimalFormat myformat=new DecimalFormat("0.00");
			//每张表按一条条数据处理
			AcIssuceInfo acIssuceInfo = acIssuceInfoDao.findAcIssuceInfo();
			if(acIssuceInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();

				list.add(MQTimerEnum.ACISSUCE.getValue());//接口编码
				list.add(acIssuceInfo.getCode());//记帐卡编码
				list.add(timerVo.getReqNo());//申请编号
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				list.add(timerVo.getApliayType());//支付方式
				list.add(timerVo.getApliayAccount());//支付账号
				if(acIssuceInfo.getAmt()!=null)list.add(myformat.format((acIssuceInfo.getAmt().divide(new BigDecimal("100")))));//应收金额
				else list.add("0.00");//应收金额
				if(acIssuceInfo.getRealAmt()!=null)list.add(myformat.format((acIssuceInfo.getRealAmt().divide(new BigDecimal("100")))));//实收金额
				else list.add("0.00");//实收金额
				list.add(timerVo.getSaleType());//销售方式
				list.add("");//发行流水号
				list.add(acIssuceInfo.getVehiclePlate());//车牌号码
				list.add(acIssuceInfo.getVehicleColor());//车牌颜色
				list.add(acIssuceInfo.getVehicleType());//车辆种类
				list.add(acIssuceInfo.getVehicleWeightLimits());//座位数
				list.add(acIssuceInfo.getNscVehicleType());//国标收费车型
				list.add(timerVo.getAuthMemberNo());
				list.add(timerVo.getSysNo());
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_ACISSUCE_INFO的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acIssuceInfoDao.delete(acIssuceInfo.getId());
					}else{
						acIssuceInfoDao.update(list.get(0), acIssuceInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡发行异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡发行异常："+e);
		}
	}

	/**
	 * 卡片挂失（记帐卡挂失）
	 */
	public synchronized void saveLoss(){
		try {
			//每张表按一条条数据处理
			AcLossInfo acLossInfo = acLossInfoDao.findAcLossInfo();
			if(acLossInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				//创建集合，将要传入的数据按顺序放入
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.ACLOSSINFO.getValue());
				list.add(acLossInfo.getCode());
				list.add(timerVo.getSaveLossFlag());
				list.add(timerVo.getBankNo());
				list.add(timerVo.getPlaceNo());
				list.add(timerVo.getOperNo());
				logger.info("参数列表："+list);
				//将集合转成澳门通消息报文对象
				String msg = MQAnalysis.listToString(list);
				//将消息发送到澳门通消息队列并接收返回值
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					//将返回值解析为集合，并且将成功的返回结果进行删除，失败的结果记录失败原因
					list = MQAnalysis.analysis(result);
					logger.info("csms_acloss_info的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acLossInfoDao.delete(acLossInfo.getId());
					}else{
						acLossInfoDao.update(list.get(0), acLossInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡挂失异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡挂失异常："+e);
		}
	}

	/**
	 * 卡片解挂（记帐卡解挂）
	 */
	public synchronized void saveUnLoss(){
		try {
			//每张表按一条条数据处理
			AcUnLossInfo acUnLossInfo = acUnLossInfoDao.findAcUnLossInfo();
			if(acUnLossInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				//创建集合，将要传入的数据按顺序放入
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.ACUNLOSS.getValue());//接口编码
				list.add(timerVo.getUnLossFlag());//标志位
				list.add(acUnLossInfo.getCode());//记帐卡编码
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				logger.info("参数列表："+list);
				//将集合转成澳门通消息报文对象
				String msg = MQAnalysis.listToString(list);
				//将消息发送到澳门通消息队列并接收返回值
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					//将返回值解析为集合，并且将成功的返回结果进行删除，失败的结果记录失败原因
					list = MQAnalysis.analysis(result);
					logger.info("csms_acunloss_info的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acUnLossInfoDao.delete(acUnLossInfo.getId());
					}else{
						acUnLossInfoDao.update(list.get(0), acUnLossInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡解挂异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡解挂异常："+e);
		}
	}

	/**
	 * 记帐卡有卡注销\记帐卡无卡注销
	 */
	public synchronized void saveCancelCard(){
		try {
			//每张表按一条条数据处理
			AcCancelInfo acCancelInfoInfo = acCancelInfoDao.findAcCancelInfoInfo();
			if(acCancelInfoInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.ACCANNEL.getValue());//接口编码
				list.add(acCancelInfoInfo.getCode());//记帐卡编码
				list.add(timerVo.getCancelFlag());//标志位
				list.add(timerVo.getBankNo());//银行账号
				list.add(acCancelInfoInfo.getCancelType());//注销方式
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				if(!acCancelInfoInfo.getCreateTime().isEmpty()) list.add(format2.format(format.parse(acCancelInfoInfo.getCreateTime())));//业务办理时间
				else list.add("");
				list.add(timerVo.getCancelAccountFlag());//销户标识
				list.add(timerVo.getCancelReason());//注销原因
				list.add(acCancelInfoInfo.getMemo());//备注
				list.add(timerVo.getSysNo());//系统编号
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("csms_accannel_info的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acCancelInfoDao.delete(acCancelInfoInfo.getId());
					}else{
						acIssuceInfoDao.update(list.get(0), acCancelInfoInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡注销异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡注销异常："+e);
		}
	}

	/**
	 * 记帐卡下方止付黑名单
	 */
	public synchronized void saveStopCard(){
		try {
			//每张表按一条条数据处理
			AcIssuedStopInfo acStopInfo = acIssuedStopInfoDao.findAcIssuedStopInfo();
			if(acStopInfo!=null){
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.ACSTOP.getValue());//接口编码
				list.add(acStopInfo.getCode());//卡号
				list.add(timerVo.getBankNo());//银行账号
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				list.add("4");//下方原因
				if(!acStopInfo.getCreateTime().isEmpty()) list.add(format2.format(format.parse(acStopInfo.getCreateTime())));//下发时间
				else list.add("");
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_ACISSUEDSTOP_INFO的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acIssuedStopInfoDao.delete(acStopInfo.getId());
					}else{
						acIssuedStopInfoDao.update(list.get(0), acStopInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡解除止付黑名单异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡解除止付黑名单异常："+e);
		}
	}

	/**
	 * 记帐卡解除止付黑名单
	 */
	public synchronized void saveRelieveStopCard(){
		try {
			//每张表按一条条数据处理
			AcStopInfo acStopInfo = acStopInfoDao.findAcStopInfo();
			if(acStopInfo!=null){
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.ACUNSTOP.getValue());//接口编码
				list.add(acStopInfo.getCode());//卡号
				list.add(timerVo.getBankNo());//银行账号
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				if(!acStopInfo.getCreateTime().isEmpty()) list.add(format2.format(format.parse(acStopInfo.getCreateTime())));//下发时间
				else list.add("");
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("csms_acstop_info的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						acStopInfoDao.delete(acStopInfo.getId());
					}else{
						acStopInfoDao.update(list.get(0), acStopInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记帐卡解除止付黑名单异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通记帐卡解除止付黑名单异常："+e);
		}
	}

	/**
	 * 卡片车牌变更
	 */
	public synchronized void saveVehicleChange(){
		try {
			//每张表按一条条数据处理
			VechileChangeInfo vechileChangeInfo = vechileChangeInfoDao.findVechileChangeInfo();
			if(vechileChangeInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.VECHILECHANGE.getValue());//接口编码
				list.add(vechileChangeInfo.getSerType());//服务类型
				list.add(vechileChangeInfo.getCode());//卡号
				list.add(vechileChangeInfo.getVehiclePlate());//车牌
				list.add(vechileChangeInfo.getVehicleColor());//车牌颜色
				list.add(vechileChangeInfo.getVehicleType());//车辆种类
				list.add(vechileChangeInfo.getVehicleWeightLimits()!=null?vechileChangeInfo.getVehicleWeightLimits().toString():"");//座位数
				list.add(vechileChangeInfo.getNscVehicleType());//国标收费车型
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getOperNo());//操作员编码
				list.add(vechileChangeInfo.getMemo());//备注
				list.add(timerVo.getSysNo());//系统编号
				list.add(vechileChangeInfo.getWriteCardFlag());//写卡标识
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_VECHILECHANGE_INFO的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						vechileChangeInfoDao.delete(vechileChangeInfo.getId());
					}else{
						vechileChangeInfoDao.update(list.get(0), vechileChangeInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通记卡片车牌变更异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通卡片车牌变更异常："+e);
		}
	}

	/**
	 * 发票邮寄登记/取消
	 */
	// TODO: 2017/3/25  发票邮寄登记/取消该接口暂时没用
	public synchronized void saveServerChange(){
		try {
			//每张表按一条条数据处理
			ServerChangeInfo serverChangeInfo = serverChangeInfoDao.findServerChangeInfo();
			if(serverChangeInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();

				List<String> list = new ArrayList<String>();
				if("1".equals(serverChangeInfo.getFlag())){
					list.add(MQTimerEnum.SERVERREGISTER.getValue());//接口编码
				}else{
					list.add(MQTimerEnum.SERVERCANNEL.getValue());//接口编码
				}
				list.add(timerVo.getUserNo());//客户号
				list.add(serverChangeInfo.getOrgan());//客户名称
				list.add(serverChangeInfo.getCode());//卡号
				list.add(serverChangeInfo.getInvoiceTitle());//发票抬头
				list.add(serverChangeInfo.getMemo());//备注
				list.add(timerVo.getSysNo());//系统编号
				list.add(timerVo.getOperNo());//操作员编码
				list.add(timerVo.getPlaceNo());//网点编码
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("csms_serverchange_info的数据发送结果:"+list);
					if("0".equals(list.get(0))){
						serverChangeInfoDao.delete(serverChangeInfo.getId());
					}else{
						serverChangeInfoDao.update(list.get(0), serverChangeInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通发票邮寄登记/取消异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通发票邮寄登记/取消异常："+e);
		}
	}

	/**
	 * 电子标签二发
	 */
	public synchronized void saveTagIssueAgain(){
		try {
			DecimalFormat myformat=new DecimalFormat("0.00");
			Format format = new SimpleDateFormat("yyyyMMdd");
			//每张表按一条条数据处理
			TagIssuceInfo tagIssuceInfo = tagissuceInfoDao.findTagIssuceInfo();
			if(tagIssuceInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.TAGISSUCE.getValue());//接口编码
				list.add(tagIssuceInfo.getTagNo());//电子标签号
				list.add(tagIssuceInfo.getVehiclePlate());//车牌
				list.add(tagIssuceInfo.getVehicleColor());//车牌颜色
				list.add(tagIssuceInfo.getModel());//厂牌型号
				list.add(tagIssuceInfo.getVehicleType());//车辆种类
				list.add(tagIssuceInfo.getVehicleWeightLimits());//座位数
				BigDecimal bdstr=new BigDecimal("1000");
				if(tagIssuceInfo.getVehicleLong()!=null) list.add(new BigDecimal(tagIssuceInfo.getVehicleLong()).divide(bdstr).toString());//车辆长
				else list.add("0.00");
				if(tagIssuceInfo.getVehicleWidth()!=null) list.add(new BigDecimal(tagIssuceInfo.getVehicleWidth()).divide(bdstr).toString());//车辆宽
				else list.add("0.00");
				if(tagIssuceInfo.getVehicleHeight()!=null) list.add(new BigDecimal(tagIssuceInfo.getVehicleHeight()).divide(bdstr).toString());//车辆高
				else list.add("0.00");
				list.add(tagIssuceInfo.getVehicleAxles()!=null?tagIssuceInfo.getVehicleAxles().toString():"");//轴数
				list.add(tagIssuceInfo.getVehicleWheels()!=null?tagIssuceInfo.getVehicleWheels().toString():"");//轮数
				list.add(timerVo.getSaleType());//销售方式
				list.add(timerVo.getInstallMan());//安装员名称
				list.add(timerVo.getOperNo());//操作员编码
				list.add(timerVo.getOperName());//操作员名称
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getPlaceName());//网点名称
				list.add(tagIssuceInfo.getCustomerName());//客户名称
				list.add(tagIssuceInfo.getOwner());//车主名称
				list.add(tagIssuceInfo.getZipCode());//邮政编码
				list.add(tagIssuceInfo.getAddress());//联系地址
				list.add(tagIssuceInfo.getCnName());//联系人
				list.add(tagIssuceInfo.getTel());//联系电话
				list.add(tagIssuceInfo.getShortMsg());//联系手机
				list.add(tagIssuceInfo.getIdentificationCode());//车辆识别代码
				list.add(tagIssuceInfo.getNscVehicleType());//国标收费车型
				list.add(tagIssuceInfo.getUsingNature());//使用性质
				//list.add(tagIssuceInfo.getModel().substring(0, 16));//车辆特征描述
				list.add(tagIssuceInfo.getVehicleSpecificInformation());//车辆特征描述(截取16位)
				list.add(tagIssuceInfo.getVehicleEngineNo());//发动机号
				if(tagIssuceInfo.getEndTime()!=null) list.add(format.format(tagIssuceInfo.getEndTime()));//标签有效日期
				else list.add("");
				list.add(tagIssuceInfo.getTagChipNo());//标签芯片号
				list.add(timerVo.getSysNo());//系统编号
				list.add("");//临时办卡点
				list.add("电子标签发行（澳门）");//业务说明
				if(tagIssuceInfo.getCost()!=null) list.add(myformat.format(tagIssuceInfo.getCost().divide(new BigDecimal("100"))));//应收金额
				else list.add("0.00");
				if(tagIssuceInfo.getChargeCost()!=null) list.add(myformat.format(tagIssuceInfo.getChargeCost().divide(new BigDecimal("100"))));//实收金额
				else list.add("0.00");
				list.add(timerVo.getApliayType());//支付方式
				list.add(timerVo.getApliayAccount());//支付账号
				list.add(timerVo.getOperNo());//授权操作员
				list.add(timerVo.getApliayMode());//交易类型
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_TAGISSUCE_INFO的数据发送结果:"+list);
					if("00".equals(list.get(0))){
						tagissuceInfoDao.delete(tagIssuceInfo.getId());
					}else{
						tagissuceInfoDao.update(list.get(0), tagIssuceInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通电子标签二发异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通电子标签二发异常："+e);
		}
	}

	/**
	 * 电子标签更换
	 */
	public synchronized void saveTagReplace(){
		try {
			DecimalFormat myformat=new DecimalFormat("0.00");
			Format format = new SimpleDateFormat("yyyyMMdd");
			Format format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//每张表按一条条数据处理
			TagReplaceInfo tagReplaceInfo = tagReplaceInfoDao.findTagReplaceInfo();
			if(tagReplaceInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.TAGREPLACE.getValue());//接口编码
				list.add(tagReplaceInfo.getOldTagNo());//旧电子标签号
				list.add(tagReplaceInfo.getAulTagNo());//原始标签号
				list.add(tagReplaceInfo.getTagNo());//新电子标签号
				list.add(tagReplaceInfo.getVehiclePlate());//车牌
				list.add(tagReplaceInfo.getVehicleColor());//车牌颜色
				list.add(tagReplaceInfo.getModel());//厂牌型号
				list.add(tagReplaceInfo.getVehicleType());//车辆种类
				list.add(tagReplaceInfo.getVehicleWeightLimits());//座位数
				BigDecimal bdstr=new BigDecimal("1000");
				if(tagReplaceInfo.getVehicleLong()!=null) list.add(new BigDecimal(tagReplaceInfo.getVehicleLong()).divide(bdstr).toString());//车辆长
				else list.add("0.00");
				if(tagReplaceInfo.getVehicleWidth()!=null) list.add(new BigDecimal(tagReplaceInfo.getVehicleWidth()).divide(bdstr).toString());//车辆宽
				else list.add("0.00");
				if(tagReplaceInfo.getVehicleHeight()!=null) list.add(new BigDecimal(tagReplaceInfo.getVehicleHeight()).divide(bdstr).toString());//车辆高
				else list.add("0.00");
				list.add(tagReplaceInfo.getVehicleAxles()!=null?tagReplaceInfo.getVehicleAxles().toString():"");//轴数
				list.add(tagReplaceInfo.getVehicleWheels()!=null?tagReplaceInfo.getVehicleWheels().toString():"");//轮数
				list.add(timerVo.getSaleType());//销售方式
				list.add(timerVo.getInstallMan());//安装员名称
				list.add(timerVo.getOperNo());//操作员编码
				list.add(timerVo.getOperName());//操作员名称
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getPlaceName());//网点名称
				list.add(tagReplaceInfo.getCustomerName());//客户名称
				list.add(tagReplaceInfo.getOwner());//车主名称
				list.add(tagReplaceInfo.getZipCode());//邮政编码
				list.add(tagReplaceInfo.getAddress());//联系地址
				list.add(tagReplaceInfo.getCnName());//联系人
				list.add(tagReplaceInfo.getTel());//联系电话
				list.add(tagReplaceInfo.getShortMsg());//联系手机
				list.add(tagReplaceInfo.getIdentificationCode());//车辆识别代码
				list.add(tagReplaceInfo.getNscVehicletype());//国标收费车型
				list.add(tagReplaceInfo.getUsingNature());//使用性质
				list.add(tagReplaceInfo.getBuitFlag());//绑定标识
				//list.add(tagReplaceInfo.getModel().substring(0, 16));//车辆特征描述
				list.add(tagReplaceInfo.getVehicleSpecificInformation());//车辆特征描述
				list.add(tagReplaceInfo.getVehicleEngineNo());//发动机号
				if (tagReplaceInfo.getEndTime()!=null) list.add(format.format(tagReplaceInfo.getEndTime()));//标签有效日期(8位)
				else list.add("");
				list.add(tagReplaceInfo.getTagChipNo());//标签芯片号
				if(tagReplaceInfo.getMainTime()!=null) list.add(format2.format(tagReplaceInfo.getMainTime()));//保修期开始时间
				else list.add("");
				list.add(timerVo.getSysNo());//系统编号
				list.add(timerVo.getTemporary());//临时办卡点
				list.add("电子标签更换（澳门）");//业务说明
				if(tagReplaceInfo.getCost()!=null) list.add(myformat.format(tagReplaceInfo.getCost().divide(new BigDecimal("100"))));//应收金额
				else list.add("0.00");
				if(tagReplaceInfo.getChargeCost()!=null) list.add(myformat.format(tagReplaceInfo.getChargeCost().divide(new BigDecimal("100"))));//实收金额
				else list.add("0.00");
				list.add(timerVo.getApliayType());//支付方式
				list.add(timerVo.getApliayAccount());//支付账号
				list.add(timerVo.getOperNo());//授权操作员
				list.add(tagReplaceInfo.getMainPlace());//维护地点
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_TAGREPLACE_INFO的数据发送结果:"+list);
					if("00".equals(list.get(0))){
						tagReplaceInfoDao.delete(tagReplaceInfo.getId());
					}else{
						tagReplaceInfoDao.update(list.get(0), tagReplaceInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通电子标签更换异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通电子标签更换异常："+e);
		}
	}

	/**
	 * 电子标签迁移
	 */
	public synchronized void saveMigrate(){
		try {
			DecimalFormat myformat=new DecimalFormat("0.00");
			Format format = new SimpleDateFormat("yyyyMMdd");
			Format format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//每张表按一条条数据处理
			TagMigrateInfo tagMigrateInfo = tagMigrateInfoDao.findTagMigrateInfo();
			if(tagMigrateInfo!=null){
				//创建vo对象，澳门通的操作员信息等
				MQTimerVo timerVo = new MQTimerVo();
				List<String> list = new ArrayList<String>();
				list.add(MQTimerEnum.TAGMIGRATE.getValue());//接口编码
				list.add(tagMigrateInfo.getTagNo());//电子标签号
				list.add(tagMigrateInfo.getVehiclePlate());//车牌
				list.add(tagMigrateInfo.getVehicleColor());//车牌颜色
				list.add(tagMigrateInfo.getModel());//厂牌型号
				list.add(tagMigrateInfo.getVehicleType());//车辆种类
				list.add(tagMigrateInfo.getVehicleWeightLimits());//座位数
				BigDecimal bdstr=new BigDecimal("1000");
				if(tagMigrateInfo.getVehicleLong()!=null) list.add(new BigDecimal(tagMigrateInfo.getVehicleLong()).divide(bdstr).toString());//车辆长
				else list.add("0.00");
				if(tagMigrateInfo.getVehicleWidth()!=null) list.add(new BigDecimal(tagMigrateInfo.getVehicleWidth()).divide(bdstr).toString());//车辆宽
				else list.add("0.00");
				if(tagMigrateInfo.getVehicleHeight()!=null) list.add(new BigDecimal(tagMigrateInfo.getVehicleHeight()).divide(bdstr).toString());//车辆高
				else list.add("0.00");
				list.add(tagMigrateInfo.getVehicleAxles()!=null?tagMigrateInfo.getVehicleAxles().toString():"");//轴数
				list.add(tagMigrateInfo.getVehicleWheels()!=null?tagMigrateInfo.getVehicleWheels().toString():"");//轮数
				if(tagMigrateInfo.getIssuceTime()!=null) list.add(format2.format(tagMigrateInfo.getIssuceTime()));//发行时间
				else list.add("");
				list.add(timerVo.getIssueOperNo());//发行操作员编码
				list.add(timerVo.getIssueOperName());//发行操作员名称
				list.add(timerVo.getIssuePlaceNo());//发行网点编码
				list.add(timerVo.getIssuePlaceName());//发行网点名称
				if(tagMigrateInfo.getMainTime()!=null) list.add(format2.format(tagMigrateInfo.getMainTime()));//保修期开始时间
				else list.add("");
				list.add(timerVo.getSaleType());//销售方式
				list.add(timerVo.getInstallMan());//安装员名称
				list.add(timerVo.getOperNo());//操作员编码
				list.add(timerVo.getOperName());//操作员名称
				list.add(timerVo.getPlaceNo());//网点编码
				list.add(timerVo.getPlaceName());//网点名称
				list.add(tagMigrateInfo.getCustomerName());//客户名称
				list.add(tagMigrateInfo.getOwner());//车主名称
				list.add(tagMigrateInfo.getZipCode());//邮政编码
				list.add(tagMigrateInfo.getAddress());//联系地址
				list.add(tagMigrateInfo.getCnName());//联系人
				list.add(tagMigrateInfo.getTel());//联系电话
				list.add(tagMigrateInfo.getShortMsg());//联系手机
				list.add(tagMigrateInfo.getIdentificationCode());//车辆识别代码
				list.add(tagMigrateInfo.getNscVehicletype());//国标收费车型
				list.add(tagMigrateInfo.getUsingNature());//使用性质
				//list.add(tagMigrateInfo.getModel().substring(0, 16));//车辆特征描述
				list.add(tagMigrateInfo.getVehicleSpecificInformation());//车辆特征描述
				list.add(tagMigrateInfo.getVehicleEngineNo());//发动机号
				list.add(tagMigrateInfo.getBuitFlag());//绑定标识
				if (tagMigrateInfo.getEndTime()!=null) list.add(format.format(tagMigrateInfo.getEndTime()));//标签有效日期
				else list.add("");
				list.add(tagMigrateInfo.getTagChipNo());//标签芯片号
				list.add(timerVo.getSysNo());//系统编号
				list.add(timerVo.getTemporary());//临时办卡点
				list.add("电子标签信息变更（澳门）");//业务说明
				if(tagMigrateInfo.getCost()!=null) list.add(myformat.format(tagMigrateInfo.getCost().divide(new BigDecimal("100"))));//应收金额
				else list.add("0.00");
				if(tagMigrateInfo.getChargeCost()!=null) list.add(myformat.format(tagMigrateInfo.getChargeCost().divide(new BigDecimal("100"))));//实收金额
				else list.add("0.00");
				list.add(timerVo.getApliayType());//支付方式
				list.add(timerVo.getApliayAccount());//支付账号
				list.add(timerVo.getOperNo());//授权操作员
				logger.info("参数列表："+list);
				String msg = MQAnalysis.listToString(list);
				String result = mqServerObject.setAndGetMsg(msg);
				if(result!=null && !"".equals(result)){
					list = MQAnalysis.analysis(result);
					logger.info("CSMS_TAGMIGRATE_INFO的数据发送结果:"+list);
					if("00".equals(list.get(0))){
						tagMigrateInfoDao.delete(tagMigrateInfo.getId());
					}else{
						tagMigrateInfoDao.update(list.get(0) , tagMigrateInfo.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("澳门通电子标签迁移异常："+e);
			e.printStackTrace();
			throw new ApplicationException("澳门通电子标签迁移异常："+e);
		}
	}

}
