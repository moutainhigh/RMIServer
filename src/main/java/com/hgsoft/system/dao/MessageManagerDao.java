package com.hgsoft.system.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.OmsDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.MessageCategory;
import com.hgsoft.system.entity.MessageGrade;
import com.hgsoft.system.entity.MessageGradeHis;
import com.hgsoft.system.entity.MessageSend;
import com.hgsoft.system.entity.MessageSendHis;
import com.hgsoft.system.entity.MessageSendOut;
import com.hgsoft.system.entity.SeatMessage;
import com.hgsoft.system.entity.SeatMessageHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
@Repository
public class MessageManagerDao extends OmsDao {
	/**
	 * 根据状态查询短信级别
	 * 
	 * @param productType
	 * @param useState 0 表示查所有的类型，1表示正常，2表示停用
	 * @return
	 * @throws Exception 
	 */
	public List<MessageGrade> findAll(String state) throws Exception {
		StringBuffer sql=new StringBuffer("select * from OMS_MESSAGEGRADE where 1=1 ");
		SqlParamer params = new SqlParamer();
		if ("1".equals(state) || "2".equals(state)) {
			params.eq(" state",  state);
		}
		Object[] Objects = params.getList().toArray();
		sql.append(params.getParam());
		sql.append(" order by id");
		List<Map<String, Object>> list = queryList(sql.toString(),Objects);
		List<MessageGrade> messageGradeList=new ArrayList<MessageGrade>();
		for(int i=0;i<list.size();i++){
			MessageGrade messageGrade=null;
			messageGrade= (MessageGrade)convert2Bean((Map<String, Object>) list.get(i), new MessageGrade());
			messageGradeList.add(messageGrade);
		}
		return messageGradeList;
	}

	public Pager findByPager(Pager pager, MessageGrade messageGrade) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID desc) as num from OMS_MESSAGEGRADE t where 1=1");
		List list = new ArrayList();
		if (messageGrade !=null) {
			Map map = FieldUtil.getPreFieldMap(MessageGrade.class, messageGrade);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list = ((List) map.get("paramNotNull"));
		}
		sql.append("order by t.id desc");
		return this.findByPages(sql.toString(), pager, list.toArray());
	}

	public void saveMessageGrade(MessageGrade messageGrade) {
		Map map = FieldUtil.getPreFieldMap(MessageGrade.class, messageGrade);
		StringBuffer sql = new StringBuffer("insert into OMS_MESSAGEGRADE");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
	}

	public void saveMessageGradeHis(MessageGradeHis messageGradeHis) {
		Map map = FieldUtil.getPreFieldMap(MessageGradeHis.class, messageGradeHis);
		StringBuffer sql = new StringBuffer("insert into OMS_MESSAGEGRADE_HIS");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
	}

	public void update(MessageGrade oldMessageGrade) {
		Map map = FieldUtil.getPreFieldMap(MessageGrade.class, oldMessageGrade);
		StringBuffer sql = new StringBuffer("update OMS_MESSAGEGRADE set ");
		sql.append(map.get("updateNameStr")+" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), oldMessageGrade.getId());
		
	}

	public void delete(Long id) {
		String sql = "delete from OMS_MESSAGEGRADE where id =?";
		delete(sql,id);
	}

	public MessageGrade findById(Long id) throws Exception {
		String sql = "select * from OMS_MESSAGEGRADE where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		MessageGrade messageGrade = null;
		if (!list.isEmpty()) {
			messageGrade = new MessageGrade();
			this.convert2Bean(list.get(0), messageGrade);
		}
		return messageGrade;
	}

	public int findByName(String name) {
		String sql = "select count(1) from OMS_MESSAGEGRADE where name =?";
		return super.count(sql,name);
	}

	public int findByCode(String code) {
		String sql = "select count(1) from OMS_MESSAGEGRADE where code =?";
		return super.count(sql,code);
	}

	public List<MessageCategory> findSecondCategory(MessageGrade messageGrade,
			String useState) {
		String sql="select * from OMS_MESSAGECATEGORY ";
		if (useState != null && useState != "" && !"0".equals(useState)) {
			sql += " where state ='" + useState + "'";
		}
		sql +=" and messageGrade=" + messageGrade.getId() + " order by id ";
		List<Object> list=omsJdbcUtil.selectForList(sql);
		List<MessageCategory> messageCategoryList=new ArrayList<MessageCategory>();
		for(int i=0;i<list.size();i++){
			MessageCategory messageCategory=null;
			messageCategory= (MessageCategory)convert2Bean((Map<String, Object>) list.get(i), new MessageCategory());
			messageCategoryList.add(messageCategory);
		}
		return messageCategoryList;
	}

	public List<SeatMessage> findSeatMessageByMsgType(Long msgType) {
		String sql="select * from OMS_SEATMESSAGE ";
		if (msgType != null) {
			sql += " where messagecategory ='" + msgType + "' order by id, operatetime desc";
		}
		List<Object> list=omsJdbcUtil.selectForList(sql);
		List<SeatMessage> seatMessageList=new ArrayList<SeatMessage>();
		for(int i=0;i<list.size();i++){
			SeatMessage seatMessage=null;
			seatMessage= (SeatMessage)convert2Bean((Map<String, Object>) list.get(i), new SeatMessage());
			seatMessageList.add(seatMessage);
		}
		return seatMessageList;
	}

	public MessageCategory findMsgCategoryById(Long id) {
		String sql = "select * from OMS_MESSAGECATEGORY where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		MessageCategory messageCategory = null;
		if (!list.isEmpty()) {
			messageCategory = new MessageCategory();
			this.convert2Bean(list.get(0), messageCategory);
		}
		return messageCategory;
	}

	public MessageGrade findMsgGradeById(Long id) {
		String sql = "select * from OMS_MESSAGEGRADE where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		MessageGrade messageGrade = null;
		if (!list.isEmpty()) {
			messageGrade = new MessageGrade();
			this.convert2Bean(list.get(0), messageGrade);
		}
		return messageGrade;
	}

	public SeatMessage findBySeatMessageId(Long id) {
		String sql = "select * from OMS_SEATMESSAGE where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		SeatMessage seatMessage = null;
		if (!list.isEmpty()) {
			seatMessage = new SeatMessage();
			this.convert2Bean(list.get(0), seatMessage);
		}
		return seatMessage;
	}

	public void saveSeatMessage(SeatMessage seatMessage) {
		Map map = FieldUtil.getPreFieldMap(SeatMessage.class, seatMessage);
		StringBuffer sql = new StringBuffer("insert into OMS_SEATMESSAGE");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
	}
	
	public void saveSeatMessageHis(SeatMessageHis seatMessageHis) {
		Map map = FieldUtil.getPreFieldMap(SeatMessageHis.class, seatMessageHis);
		StringBuffer sql = new StringBuffer("insert into OMS_SEATMESSAGE_HIS");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List)map.get("param"));
		
	}

	public void update(SeatMessage seatMessage) {
		Map map = FieldUtil.getPreFieldMap(SeatMessage.class, seatMessage);
		StringBuffer sql = new StringBuffer("update OMS_SEATMESSAGE set ");
		sql.append(map.get("updateNameStr")+" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), seatMessage.getId());
	}

	public int findSeatMessageByContent(String content) {
		String sql = "select count(1) from OMS_SEATMESSAGE where content =?";
		return super.count(sql,content);
	}
	
	public void saveMessageSendOut(MessageSendOut messageSendOut) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> argsArry = new ArrayList<Object>();
		StringBuffer insertData = new StringBuffer("");
		Date opertorTime = new Date();
		insertData.append(" insert into Oms_messageSendOut(id,phone,content,PortNo,SendMode,State,type,operateID,operateName,operateTime");				
		 if (messageSendOut.getMessageImportID() != null && !messageSendOut.getMessageImportID().equals("")) {
			 insertData.append(",messageImportID");
		 }
		 if (messageSendOut.getTobeSendTime() != null && !messageSendOut.getTobeSendTime().equals("")) {
			 insertData.append(",TobeSendTime");
		 }
		
		 insertData.append(") values (").append("null,'").append(messageSendOut.getPhone() + "',?,'")
				.append(messageSendOut.getPortNo() + "','")
				.append(messageSendOut.getSendMode() + "','").append(messageSendOut.getState() + "','")
				.append(messageSendOut.getType() + "',").append(messageSendOut.getOperateId() + ",'")
				.append(messageSendOut.getOperateName() + "',")
				.append("to_date('" + format.format(opertorTime) + "','YYYY-MM-DD HH24:MI:SS')");
		if (messageSendOut.getMessageImportID() != null && !messageSendOut.getMessageImportID().equals("")) {
			insertData.append("," + messageSendOut.getMessageImportID());
		}
		if (messageSendOut.getTobeSendTime() != null && !messageSendOut.getTobeSendTime().equals("")) {
			insertData.append(
					",to_date('" + format.format(messageSendOut.getTobeSendTime()) + "','YYYY-MM-DD HH24:MI:SS')");
		}
		insertData.append(") ");
		argsArry.add(messageSendOut.getContent());
		if ("".equals(insertData.toString()) || insertData.toString().length() <= 0) {
			throw new RuntimeException("没有数据保存");
		}
		super.update(insertData.toString(), argsArry.toArray());
	}

	public MessageSend findMessageSend(Long id) {
		String sql="select * from Oms_MessageSend where id=? ";
		List list=queryList(sql, id);
		MessageSend messageSend=null;
		messageSend=(MessageSend)this.convert2Bean((Map<String, Object>) list.get(0), new MessageSend());
		return messageSend;
	}

	public void saveMessageSendHis(MessageSendHis msh) {
		
		//这里用预编译处理，否则短信内容如果有特殊符号会报错。
		if (msh != null) {
			List<Object> argsArry = new ArrayList<Object>();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer sql=new StringBuffer("insert into Oms_messageSend_His(phone,content,sendTime,portNo,State,type,operateId,operateName,operateTime,messageSendId,genTime,genReason)");
			sql.append(" values ('" + msh.getPhone() +"',?");
			if (msh.getSendTime() != null) {
				sql.append(",to_date('"+format.format(msh.getSendTime())+"','YYYY-MM-DD HH24:MI:SS')");
			} else {
				sql.append(",NULL");
			}
			if (msh.getPortNo() != null) {
				sql.append(",'"+ msh.getPortNo() +"'");
			} else {
				sql.append(",NULL");
			}
			sql.append(",'" + msh.getState() + "','" + msh.getType() + "'," + msh.getOperateId() + ",'" + msh.getOperateName() + "',");
			sql.append("to_date('"+format.format(msh.getOperateTime())+"','YYYY-MM-DD HH24:MI:SS')");
			sql.append("," + msh.getMessageSendId() +",");
			sql.append("to_date('"+format.format(msh.getGenTime())+"','YYYY-MM-DD HH24:MI:SS')");
			sql.append(",'" + msh.getGenReason()+"')");
			argsArry.add(msh.getContent());
			update(sql.toString(), argsArry.toArray());
		}
		
	}

	public void updateMessageSend(MessageSend messageSend) {
		//这里用预编译处理，否则短信内容如果有特殊符号会报错。
		if (messageSend != null) {
			List<Object> argsArry = new ArrayList<Object>();
			StringBuffer sql=new StringBuffer("update oms_messagesend set ");
			if (messageSend.getPhone() != null && !"".equals(messageSend.getPhone())) {
				sql.append("phone=?,");
				argsArry.add(messageSend.getPhone());
			}
			if (messageSend.getContent() != null && !"".equals(messageSend.getContent())) {
				sql.append("content=?,");
				argsArry.add(messageSend.getContent());
			}
			if (messageSend.getSendTime() != null) {
				sql.append("sendTime=?,");
				argsArry.add(messageSend.getSendTime());
			}
			if (messageSend.getPortNo() != null && !"".equals(messageSend.getPortNo())) {
				sql.append("portNo=?,");
				argsArry.add(messageSend.getPortNo());
			}
			if (messageSend.getState() != null && !"".equals(messageSend.getState())) {
				sql.append("state=?,");
				argsArry.add(messageSend.getState());
			}
			if (messageSend.getType() != null && !"".equals(messageSend.getType())) {
				sql.append("type=?,");
				argsArry.add(messageSend.getType());
			}
			if (messageSend.getOperateId() != null) {
				sql.append("operateId=?,");
				argsArry.add(messageSend.getOperateId());
			}
			if (messageSend.getOperateName() != null && !"".equals(messageSend.getOperateName())) {
				sql.append("operateName=?,");
				argsArry.add(messageSend.getOperateName());
			}
			if (messageSend.getOperateTime() != null) {
				sql.append("operateTime=?,");
				argsArry.add(messageSend.getOperateTime());
			}
			String updateSql = "";
			if (sql.toString().endsWith(",")) {
				updateSql = sql.substring(0,sql.toString().length()-1);
			}
			if (argsArry.size() > 0) {
				updateSql += " where id="+messageSend.getId();
				update(updateSql,argsArry.toArray());
			}
		}

	}

	public MessageSendOut findByMessageImportID(Long id) {
		String sql = "select * from Oms_MessageSendOut where messageImportID=?";
		List list = queryList(sql, id);
		MessageSendOut messageSendOut = null;
		if (list.size() > 0) {
			messageSendOut = (MessageSendOut) this.convert2Bean((Map<String, Object>) list.get(0),
					new MessageSendOut());
			
		}
		return messageSendOut;
	}

	public void updateMessageSendOutByImportMessage(
			MessageSendOut messageSendOut) {
		// 这里用预编译处理，否则短信内容如果有特殊符号会报错。
				if (messageSendOut != null) {
					List<Object> argsArry = new ArrayList<Object>();
					StringBuffer sql = new StringBuffer("update oms_messagesendOut set ");
					if (messageSendOut.getPhone() != null && !"".equals(messageSendOut.getPhone())) {
						sql.append("phone=?,");
						argsArry.add(messageSendOut.getPhone());
					}
					if (messageSendOut.getContent() != null && !"".equals(messageSendOut.getContent())) {
						sql.append("content=?,");
						argsArry.add(messageSendOut.getContent());
					}
					if (messageSendOut.getTobeSendTime() != null) {
						sql.append("tobesendTime=?,");
						argsArry.add(messageSendOut.getTobeSendTime());
					}
					if (messageSendOut.getPortNo() != null && !"".equals(messageSendOut.getPortNo())) {
						sql.append("portNo=?,");
						argsArry.add(messageSendOut.getPortNo());
					}
					if (messageSendOut.getState() != null && !"".equals(messageSendOut.getState())) {
						sql.append("state=?,");
						argsArry.add(messageSendOut.getState());
					}
					if (messageSendOut.getType() != null && !"".equals(messageSendOut.getType())) {
						sql.append("type=?,");
						argsArry.add(messageSendOut.getType());
					}
					if (messageSendOut.getOperateId() != null) {
						sql.append("operateId=?,");
						argsArry.add(messageSendOut.getOperateId());
					}
					if (messageSendOut.getOperateName() != null && !"".equals(messageSendOut.getOperateName())) {
						sql.append("operateName=?,");
						argsArry.add(messageSendOut.getOperateName());
					}
					if (messageSendOut.getOperateTime() != null) {
						sql.append("operateTime=?,");
						argsArry.add(messageSendOut.getOperateTime());
					}
					String updateSql = "";
					if (sql.toString().endsWith(",")) {
						updateSql = sql.substring(0, sql.toString().length() - 1);
					}
					if (argsArry.size() > 0) {
						updateSql += " where messageImportID=" + messageSendOut.getMessageImportID();
						update(updateSql, argsArry.toArray());
					}
				}
		
	}

	public List<SeatMessage> findBySeatMessageIdList(String idList) {
		 
		String sql="select * from OMS_SEATMESSAGE os where os.id in("+idList+")";
		List<Map<String, Object>> list=queryList(sql);
		SeatMessage seatMessage = null;
		List<SeatMessage> seatMessageList = new ArrayList<SeatMessage>();
		if (!list.isEmpty()) {
			 for (int j = 0; j < list.size(); j++) {
				 seatMessage = new SeatMessage();
					this.convert2Bean(list.get(j), seatMessage);
					seatMessageList.add((SeatMessage)this.convert2Bean(list.get(j), seatMessage));
			}
		}
		return seatMessageList;
	}

	public void importMsgForSend(List<MessageSendOut> msgList) {
		 for (MessageSendOut messageSendOut : msgList) {
			 saveMessageSendOut(messageSendOut);
		}
	}
}
