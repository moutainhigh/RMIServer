package com.hgsoft.prepaidC.dao;

import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddReq;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public class ScAddDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	public void  saveScAddReq(ScAddReq scAddReq){
		if(scAddReq.getId()==null){
			scAddReq.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_SC_ADD_REQ(");
		sql.append(FieldUtil.getFieldMap(ScAddReq.class,scAddReq).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScAddReq.class,scAddReq).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void  saveScAddSend(ScaddSend scaddSend){
		if(scaddSend.getId()==null){
			scaddSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDSEND_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_SCADD_SEND(");
		sql.append(FieldUtil.getFieldMap(ScaddSend.class,scaddSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScaddSend.class,scaddSend).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void  saveScAddReqByBussiness(PrepaidCBussiness prepaidCBussiness,String str, String paychannel){
		ScAddReq scAddReq = new ScAddReq();
		scAddReq.setId(prepaidCBussiness.getBusinessId()); //业务纪录表的业务id为充值申请表id
		scAddReq.setCardNo(prepaidCBussiness.getCardno());
		scAddReq.setBalReq(prepaidCBussiness.getBeforebalance());// 充值前余额
		scAddReq.setMoneyReq(prepaidCBussiness.getRealprice());// 正常充值金额
		scAddReq.setReturnMoneyReq(prepaidCBussiness.getReturnMoney());// 回退金额
		scAddReq.setTransfersumReq(prepaidCBussiness.getTransferSum());// 资金转移金额
		scAddReq.setRandomReq("111");// 申请随机数
		scAddReq.setTransverReq("2");// 算法版本申请
		scAddReq.setKeyverReq("1");// 密钥版本申请
		scAddReq.setOnlineTradeNoReq(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scAddReq.setOfflineTradeNoReq(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scAddReq.setTermnoReq(prepaidCBussiness.getTermcode());// 终端机编号
		scAddReq.setPsamnoReq("444");// PSAM卡号
		scAddReq.setTimeReq(prepaidCBussiness.getTradetime());// 充值申请时间
		scAddReq.setDealtimeReq(new Date());// 处理时间
		if("02".equals(str)){
			scAddReq.setTradeType("02");// 交易类型
		}else{
			scAddReq.setTradeType("01");// 交易类型
		}
		scAddReq.setPlaceNo(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scAddReq.setOpCode(prepaidCBussiness.getOperNo());// 操作员编码
		scAddReq.setPayChannel(paychannel);
		saveScAddReq(scAddReq);
	}
	
	public void  saveScAddSendByBussiness(PrepaidCBussiness prepaidCBussiness,String str){
		ScaddSend scaddSend= new ScaddSend();
		scaddSend.setCardNo(prepaidCBussiness.getCardno());
		scaddSend.setBalReq(prepaidCBussiness.getBeforebalance());// 充值前余额
		scaddSend.setMoneyReq(prepaidCBussiness.getRealprice());// 正常充值金额
		scaddSend.setReturnMoneyReq(prepaidCBussiness.getReturnMoney());// 回退金额
		scaddSend.setTransfersumReq(prepaidCBussiness.getTransferSum());// 资金转移金额
		scaddSend.setRandomReq("111");// 申请随机数
		scaddSend.setTransverReq("2");// 算法版本申请
		scaddSend.setKeyverReq("1");// 密钥版本申请
		scaddSend.setOnlineTradeNoReq(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scaddSend.setOfflineTradeNoReq(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scaddSend.setTermnoReq(prepaidCBussiness.getTermcode());// 终端机编号
		scaddSend.setPsamnoReq("444");// PSAM卡号
		scaddSend.setTimeReq(prepaidCBussiness.getTradetime());// 充值申请时间
		scaddSend.setDealtimeReq(new Date());// 处理时间
		if("02".equals(str)){
			scaddSend.setTradeType("02");// 交易类型
		}else{
			scaddSend.setTradeType("01");// 交易类型
		}
		scaddSend.setPlaceNo(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scaddSend.setOpCode(prepaidCBussiness.getOperNo());// 操作员编码
		saveScAddSend(scaddSend);
	}
	
	public void saveScAddSure(ScAddSure scAddSure){
		if(scAddSure.getId()==null){
			scAddSure.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDSURE_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_SC_ADD_SURE(");
		sql.append(FieldUtil.getFieldMap(ScAddSure.class,scAddSure).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScAddSure.class,scAddSure).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void saveScAddSureSend(ScAddSureSend scAddSureSend){
		if(scAddSureSend.getId()==null){
			scAddSureSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDSURESEND_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_SCADDSURE_SEND(");
		sql.append(FieldUtil.getFieldMap(ScAddSureSend.class,scAddSureSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScAddSureSend.class,scAddSureSend).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void saveScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,String str, String paychannel){
		ScAddSure scAddSure = new ScAddSure();
		scAddSure.setCardNo(prepaidCBussiness.getCardno());
		scAddSure.setBalReq(prepaidCBussiness.getBeforebalance());// 充值前余额
		scAddSure.setMoneyReq(prepaidCBussiness.getRealprice());// 正常充值金额
		scAddSure.setReturnMoneyReq(prepaidCBussiness.getReturnMoney());// 回退金额
		scAddSure.setTransferSumReq(prepaidCBussiness.getTransferSum());// 资金转移金额
		scAddSure.setRandomReq("111");// 申请随机数
		scAddSure.setTransverReq("1");// 算法版本申请
		scAddSure.setKeyverReq("3");// 密钥版本申请
		scAddSure.setOnlineTradenoReq(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scAddSure.setOfflineTradenoReq(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scAddSure.setTermnoReq(prepaidCBussiness.getTermcode());// 终端机编号
		scAddSure.setPsamnoReq("444");// PSAM卡号
		scAddSure.setTimeReq(prepaidCBussiness.getTradetime());// 充值申请时间
		scAddSure.setDealtimeReq(new Date());// 处理时间
		if("02".equals(str)){
			scAddSure.setTradeType("02");// 交易类型
		}else{
			scAddSure.setTradeType("01");// 交易类型
		}
		scAddSure.setPlaceNoReq(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scAddSure.setOpcodeReq(prepaidCBussiness.getOperNo());// 操作员编码
		scAddSure.setPayChannel(paychannel);
		saveScAddSure(scAddSure);
	}
	
	public void saveScAddSureSendByBussiness(PrepaidCBussiness prepaidCBussiness,String str){
		ScAddSureSend scAddSureSend = new ScAddSureSend();
		scAddSureSend.setCardNo(prepaidCBussiness.getCardno());
		scAddSureSend.setBalReq(prepaidCBussiness.getBeforebalance());// 充值前余额
		scAddSureSend.setMoneyReq(prepaidCBussiness.getRealprice());// 正常充值金额
		scAddSureSend.setReturnMoneyReq(prepaidCBussiness.getReturnMoney());// 回退金额
		scAddSureSend.setTransferSumReq(prepaidCBussiness.getTransferSum());// 资金转移金额
		scAddSureSend.setRandomReq("111");// 申请随机数
		scAddSureSend.setTransverReq("1");// 算法版本申请
		scAddSureSend.setKeyverReq("3");// 密钥版本申请
		scAddSureSend.setOnlineTradenoReq(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scAddSureSend.setOfflineTradenoReq(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scAddSureSend.setTermnoReq(prepaidCBussiness.getTermcode());// 终端机编号
		scAddSureSend.setPsamnoReq("444");// PSAM卡号
		scAddSureSend.setTimeReq(prepaidCBussiness.getTradetime());// 充值申请时间
		scAddSureSend.setDealtimeReq(new Date());// 处理时间
		if("02".equals(str)){
			scAddSureSend.setTradeType("02");// 交易类型
		}else{
			scAddSureSend.setTradeType("01");// 交易类型
		}
		scAddSureSend.setPlaceNoReq(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scAddSureSend.setOpcodeReq(prepaidCBussiness.getOperNo());// 操作员编码
		saveScAddSureSend(scAddSureSend);
	}

	public ScAddSure updateScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness, String dealType, Date addSureTime, String state, String confirmType) {
		ScAddSure scAddSure = findScAddSureByTypeCardTime(dealType, prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
		scAddSure.setCheckCode(prepaidCBussiness.getCheckcode());
		scAddSure.setMac(prepaidCBussiness.getMac());
		scAddSure.setTac(prepaidCBussiness.getTac());
		scAddSure.setBalSur(prepaidCBussiness.getBalance());
		scAddSure.setMoneySur(prepaidCBussiness.getRealprice());
		scAddSure.setReturnMoneySur(prepaidCBussiness.getReturnMoney());
		scAddSure.setTransferSumSur(prepaidCBussiness.getTransferSum());
		scAddSure.setRandomSur("1");
		scAddSure.setTransverSur("1");
		scAddSure.setKeyverSur("1");
		scAddSure.setOnlineTradenoSur(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));
		scAddSure.setOfflineTradenoSur(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));
		scAddSure.setTermnoSur(prepaidCBussiness.getTermcode());
		scAddSure.setPsamnoReq("1");
		scAddSure.setTimeSur(addSureTime);
		scAddSure.setDealtimeSur(new Date());
		scAddSure.setPlaceNoSur(prepaidCBussiness.getPlaceNo());
		scAddSure.setOpcodeSur(prepaidCBussiness.getOperNo());
		scAddSure.setState(state);
		scAddSure.setConfirmType(confirmType);
		updateScAddSure(scAddSure);
		return scAddSure;
	}

	public void updateScAddSureSendByBussiness(PrepaidCBussiness prepaidCBussiness, String dealType, Date addSureTime, String state) {
		ScAddSureSend scAddSureSend = findScAddSureSendByTypeCardTime(dealType, prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
		scAddSureSend.setCheckCode(prepaidCBussiness.getCheckcode());
		scAddSureSend.setMac(prepaidCBussiness.getMac());
		scAddSureSend.setTac(prepaidCBussiness.getTac());
		scAddSureSend.setBalSur(prepaidCBussiness.getBalance());
		scAddSureSend.setMoneySur(prepaidCBussiness.getRealprice());
		scAddSureSend.setReturnMoneySur(prepaidCBussiness.getReturnMoney());
		scAddSureSend.setTransferSumSur(prepaidCBussiness.getTransferSum());
		scAddSureSend.setRandomSur("1");
		scAddSureSend.setTransverSur("1");
		scAddSureSend.setKeyverSur("1");
		scAddSureSend.setOnlineTradenoSur(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));
		scAddSureSend.setOfflineTradenoSur(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));
		scAddSureSend.setTermnoSur(prepaidCBussiness.getTermcode());
		scAddSureSend.setPsamnoReq("1");
		scAddSureSend.setTimeSur(addSureTime);
		scAddSureSend.setDealtimeSur(new Date());
		scAddSureSend.setPlaceNoSur(prepaidCBussiness.getPlaceNo());
		scAddSureSend.setOpcodeSur(prepaidCBussiness.getOperNo());
		scAddSureSend.setState(state);
		updateScAddSureSend(scAddSureSend);
	}


	@SuppressWarnings("rawtypes")
	public void updateScAddSure(ScAddSure scAddSure) {
		Map map = FieldUtil.getPreFieldMap(ScAddSure.class,scAddSure);
		StringBuffer sql = new StringBuffer("update CSMS_SC_ADD_SURE set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),scAddSure.getId());
	}
	
	public void updateScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,String state) {
		ScAddSure scAddSure = findScAddSureByCardNO(prepaidCBussiness.getCardno());
		scAddSure.setCheckCode(prepaidCBussiness.getCheckcode());
		scAddSure.setMac(prepaidCBussiness.getMac());
		scAddSure.setTac(prepaidCBussiness.getTac());
		scAddSure.setState("3");
		scAddSure.setBalSur(prepaidCBussiness.getBalance());// 充值后余额
		scAddSure.setMoneySur(prepaidCBussiness.getRealprice());// 正常充值金额
		scAddSure.setReturnMoneySur(prepaidCBussiness.getReturnMoney());// 回退金额
		scAddSure.setTransferSumSur(prepaidCBussiness.getTransferSum());// 资金转移金额
		scAddSure.setRandomSur("1");// 申请随机数
		scAddSure.setTransverSur("1");// 算法版本申请
		scAddSure.setKeyverSur("1");// 密钥版本申请
		scAddSure.setOnlineTradenoSur(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scAddSure.setOfflineTradenoSur(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scAddSure.setTermnoSur(prepaidCBussiness.getTermcode());// 终端机编号
		scAddSure.setPsamnoReq("1");// PSAM卡号
		scAddSure.setTimeSur(prepaidCBussiness.getTradetime());// 充值确认时间
		scAddSure.setDealtimeSur(new Date());// 处理时间
		scAddSure.setPlaceNoSur(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scAddSure.setOpcodeSur(prepaidCBussiness.getOperNo());// 操作员编码
		scAddSure.setState(state);
		updateScAddSure(scAddSure);
	}
	
	@SuppressWarnings("rawtypes")
	public void updateScAddSureSend(ScAddSureSend scAddSureSend) {
		Map map = FieldUtil.getPreFieldMap(ScAddSureSend.class,scAddSureSend);
		StringBuffer sql=new StringBuffer("update CSMS_SCADDSURE_SEND set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),scAddSureSend.getId());
	}
	
	public void updateScAddSureSendByBussiness(PrepaidCBussiness prepaidCBussiness,String state) {
		ScAddSureSend scAddSureSend = findScAddSureSendByCardNO(prepaidCBussiness.getCardno());
		scAddSureSend.setCheckCode(prepaidCBussiness.getCheckcode());
		scAddSureSend.setMac(prepaidCBussiness.getMac());
		scAddSureSend.setTac(prepaidCBussiness.getTac());
		scAddSureSend.setState("3");
		scAddSureSend.setBalSur(prepaidCBussiness.getBeforebalance());// 充值前余额
		scAddSureSend.setMoneySur(prepaidCBussiness.getRealprice());// 正常充值金额
		scAddSureSend.setReturnMoneySur(prepaidCBussiness.getReturnMoney());// 回退金额
		scAddSureSend.setTransferSumSur(prepaidCBussiness.getTransferSum());// 资金转移金额
		scAddSureSend.setRandomSur("1");// 申请随机数
		scAddSureSend.setTransverSur("1");// 算法版本申请
		scAddSureSend.setKeyverSur("1");// 密钥版本申请
		scAddSureSend.setOnlineTradenoSur(Long.parseLong(prepaidCBussiness.getOnlinetradeno()));// 联机流水号申请
		scAddSureSend.setOfflineTradenoSur(Long.parseLong(prepaidCBussiness.getOfflinetradeno()));// 脱机流水号申请
		scAddSureSend.setTermnoSur(prepaidCBussiness.getTermcode());// 终端机编号
		scAddSureSend.setPsamnoReq("1");// PSAM卡号
		scAddSureSend.setTimeSur(prepaidCBussiness.getTradetime());// 充值确认时间
		scAddSureSend.setDealtimeSur(new Date());// 处理时间
		scAddSureSend.setPlaceNoSur(prepaidCBussiness.getPlaceNo());// 充值网点编码
		scAddSureSend.setOpcodeSur(prepaidCBussiness.getOperNo());// 操作员编码
		scAddSureSend.setState(state);
		updateScAddSureSend(scAddSureSend);
	}
	
	public ScAddSure findScAddSureByCardNO(String cardNo){
		String sql="select * from CSMS_SC_ADD_SURE where cardNo=? order by timereq desc FETCH FIRST 1 ROWs ONLY";
		List<ScAddSure> scAddSures = super.queryObjectList(sql, ScAddSure.class, cardNo);
		if (scAddSures == null || scAddSures.isEmpty()) {
			return null;
		}
		return scAddSures.get(0);
	}
	
	public ScAddSureSend findScAddSureSendByCardNO(String cardNo){
		String sql="select * from CSMS_SCADDSURE_SEND where cardNo=? order by timereq desc FETCH FIRST 1 ROWs ONLY";
		List<ScAddSureSend> scAddSureSends = super.queryObjectList(sql, ScAddSureSend.class, cardNo);
		if (scAddSureSends == null || scAddSureSends.isEmpty()) {
			return null;
		}
		return scAddSureSends.get(0);
	}

	/**
	 * @Description:用于空充  ：一天对账流水查询
	 * @return ScAddSureSend
	 * termNo、placeno都是用申请的字段？
	 */
	public List<ScAddSure> findScAddSure(String placeNo,String termNo,String reqBeginDate,String reqEndDate
			,String sureBeginDate,String sureEndDate){
		ScAddSure temp = null;
		List<ScAddSure> scAddSureList = null;

		StringBuffer sql=new StringBuffer("select * from CSMS_SC_ADD_SURE where 1=1 ");
		if(StringUtils.isNotBlank(placeNo)){
			sql.append(" and placenoreq='"+placeNo+"'");
		}
		if(StringUtils.isNotBlank(termNo)){
			sql.append(" and termnoreq='"+termNo+"'");
		}
		if(StringUtils.isNotBlank(reqBeginDate)){
			sql.append(" and timereq>=to_date('"+reqBeginDate+"','yyyy-MM-dd HH24:mi:ss')");
		}
		if(StringUtils.isNotBlank(reqEndDate)){
			sql.append(" and timereq<=to_date('"+reqEndDate+"','yyyy-MM-dd HH24:mi:ss')");
		}
		/*if(StringUtils.isNotBlank(sureBeginDate)){
			sql.append(" and timesur>=to_date('"+sureBeginDate+"','yyyy-MM-dd HH24:mi:ss')");
		}
		if(StringUtils.isNotBlank(sureEndDate)){
			sql.append(" and timesur<=to_date('"+sureEndDate+"','yyyy-MM-dd HH24:mi:ss')");
		}*/
		List<Map<String, Object>> list = queryList(sql.toString());

		if(list!=null && list.size()>0){
			scAddSureList = new ArrayList<ScAddSure>();
			for (Map<String, Object> map : list) {
				temp = new ScAddSure();
				this.convert2Bean(map, temp);
				scAddSureList.add(temp);
			}
		}
		return scAddSureList;
	}

	/**
	 * @Description:TODO
	 * @param placeNo
	 * @param termNo
	 * @param reqDate
	 * @param cardNo
	 * @return List<ScAddSure>
	 * termNo、placeno都是用申请的字段？
	 */
	public List<ScAddSure> findScAddSure(String placeNo, String termNo,
			String reqDate, String cardNo) {

		ScAddSure temp = null;
		List<ScAddSure> scAddSureList = null;
		StringBuffer sql=new StringBuffer("select * from CSMS_SC_ADD_SURE where cardno='"+cardNo+"' and placenoreq='"+placeNo+"' and termnoreq='"+termNo+"'");
		if(StringUtils.isNotBlank(reqDate)){

			sql.append(" and to_char(timereq,'yyyy-MM-dd HH24:mi:ss')= '"+reqDate+"'");
		}
		List<Map<String, Object>> list = queryList(sql.toString());

		if(list!=null && list.size()>0){
			scAddSureList = new ArrayList<ScAddSure>();
			for (Map<String, Object> map : list) {
				temp = new ScAddSure();
				this.convert2Bean(map, temp);
				scAddSureList.add(temp);
			}
		}
		return scAddSureList;
	}

	/**
	 * 查半条，即state状态为空
	 * @param termNo
	 * @param cardNo
	 * @return List<ScAddSure>
	 */
	public List<ScAddSure> findScAddSure(String termNo,String cardNo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		ScAddSure temp = null;
		List<ScAddSure> scAddSureList = null;
		StringBuffer sql=new StringBuffer("select * from CSMS_SC_ADD_SURE where state is null ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(termNo)){
			params.eq("TermNoReq", termNo);
		}

		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		List<Map<String, Object>> resultList = queryList(sql.toString(),Objects);

		if(resultList!=null && resultList.size()>0){
			scAddSureList = new ArrayList<ScAddSure>();
			for (Map<String, Object> map : resultList) {
				temp = new ScAddSure();
				this.convert2Bean(map, temp);
				scAddSureList.add(temp);
			}
		}
		return scAddSureList;

	}

	public ScAddSure findScAddSureByCardTime(String cardNo,Date timeReq){
		String sql="select * from CSMS_SC_ADD_SURE where cardNo=? and timereq=? FETCH FIRST 1 ROWs ONLY";
		List<ScAddSure> scAddSures = super.queryObjectList(sql, ScAddSure.class, cardNo, timeReq);
		if (scAddSures == null || scAddSures.isEmpty()) {
			return null;
		}
		return scAddSures.get(0);
	}

	public ScAddSureSend findScAddSureSendByCardTime(String cardNo,Date timeReq){
		String sql="select * from CSMS_SCADDSURE_SEND where cardNo=? and timereq=? FETCH FIRST 1 ROWs ONLY";
		List<ScAddSureSend> scAddSureSends = super.queryObjectList(sql, ScAddSureSend.class, cardNo, timeReq);
		if (scAddSureSends == null || scAddSureSends.isEmpty()) {
			return null;
		}
		return scAddSureSends.get(0);
	}

	public ScAddSure findLastSureByCardNo(String cardNo) {
		String sql = "select a.* from CSMS_SC_ADD_SURE a where a.cardNo=? " +
				"order by a.timereq desc FETCH FIRST 1 ROWs ONLY";
		List<ScAddSure> scAddSures = queryObjectList(sql, ScAddSure.class, cardNo);
		if ((scAddSures == null) || (scAddSures.isEmpty())) {
			return null;
		}
		return scAddSures.get(0);
	}

	public ScAddSure findScAddSureByTypeCardTime(String dealType, String cardNo, Date timeReq) {
		String sql = "select * from CSMS_SC_ADD_SURE where tradetype=? and cardNo=? and timereq=? FETCH FIRST 1 ROWs ONLY";
		List<ScAddSure> scAddSures = queryObjectList(sql, ScAddSure.class,  dealType, cardNo, timeReq);
		if ((scAddSures == null) || (scAddSures.isEmpty())) {
			return null;
		}
		return scAddSures.get(0);
	}

	public ScAddSureSend findScAddSureSendByTypeCardTime(String dealType, String cardNo, Date timeReq) {
		String sql = "select * from CSMS_SCADDSURE_SEND where tradetype=? and cardNo=? and timereq=? FETCH FIRST 1 ROWs ONLY";
		List<ScAddSureSend> scAddSureSends = queryObjectList(sql, ScAddSureSend.class, dealType, cardNo, timeReq);
		if ((scAddSureSends == null) || (scAddSureSends.isEmpty())) {
			return null;
		}
		return scAddSureSends.get(0);
	}

	public int updateScAddReqSendHalfNotSure(Long id) {
		String sql = "update csms_scadd_send set sureflag=0 where id=?";
		return update(sql, id);
	}
}
