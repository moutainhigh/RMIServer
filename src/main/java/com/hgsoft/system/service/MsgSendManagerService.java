package com.hgsoft.system.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.system.dao.MessageManagerDao;
import com.hgsoft.system.entity.MessageCategory;
import com.hgsoft.system.entity.MessageGrade;
import com.hgsoft.system.entity.MessageSend;
import com.hgsoft.system.entity.MessageSendHis;
import com.hgsoft.system.entity.MessageSendOut;
import com.hgsoft.system.entity.SeatMessage;
import com.hgsoft.system.entity.SeatMessageHis;
import com.hgsoft.system.serviceInterface.IMsgSendManagerService;
import com.hgsoft.utils.OMSSequenceUtil;
@Service
public class MsgSendManagerService implements IMsgSendManagerService {

	@Resource
	OMSSequenceUtil oMSSequenceUtil;
	@Resource
	private MessageManagerDao messageManagerDao;
	
	@Override
	public List<MessageGrade> findAll(String state) throws Exception {
		return messageManagerDao.findAll(state);
	}
 
	public OMSSequenceUtil getoMSSequenceUtil() {
		return oMSSequenceUtil;
	}

	public void setoMSSequenceUtil(OMSSequenceUtil oMSSequenceUtil) {
		this.oMSSequenceUtil = oMSSequenceUtil;
	}

	public MessageManagerDao getmessageManagerDao() {
		return messageManagerDao;
	}

	public void setmessageManagerDao(MessageManagerDao messageManagerDao) {
		this.messageManagerDao = messageManagerDao;
	}

	@Override
	public List<MessageCategory> findSecondCategory(MessageGrade messageGrade,
			String useState) throws Exception {
		 
		return messageManagerDao.findSecondCategory(messageGrade,useState);
	}

	@Override
	public List<SeatMessage> findSeatMessageByMsgType(Long msgType) throws Exception {
		 
		return messageManagerDao.findSeatMessageByMsgType(msgType);
	}

	@Override
	public MessageCategory findMsgCategoryById(Long id) throws Exception {
		return messageManagerDao.findMsgCategoryById(id);
	}

	@Override
	public MessageGrade findMsgGradeById(Long id) throws Exception {
		return messageManagerDao.findMsgGradeById(id);
	}

	@Override
	public SeatMessage findBySeatMessageId(Long id) throws Exception {
		return messageManagerDao.findBySeatMessageId(id);
	}

	@Override
	public void saveSeatMessage(SeatMessage seatMessage) {
		try {
			BigDecimal messageCategory_ID = oMSSequenceUtil.getSequence("OMS_SEATMESSAGE_SEQ");
			seatMessage.setId(Long.valueOf(messageCategory_ID.toString()));
			messageManagerDao.saveSeatMessage(seatMessage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("保存失败");
		}
		
	}

	@Override
	public void updateSeatMessage(SeatMessage seatMessage,
			SeatMessageHis seatMessageHis) {
		try {
			BigDecimal messageCategoryHis_ID = oMSSequenceUtil.getSequence("OMS_SEATMESSAGE_HIS_SEQ");
			seatMessageHis.setId(Long.valueOf(messageCategoryHis_ID.toString()));
			messageManagerDao.saveSeatMessageHis(seatMessageHis);
			messageManagerDao.update(seatMessage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("保存失败");
		}
		
	}

	@Override
	public int findSeatMessageByContent(String content) {
		return messageManagerDao.findSeatMessageByContent(content);
	}
	
	@Override
	public MessageSend findMessageSend(Long id) throws Exception {
		return messageManagerDao.findMessageSend(id);
	}
	
	@Override
	public void updateMessageSend(MessageSend messageSend) throws Exception {
		MessageSend temp = findMessageSend(messageSend.getId());
		MessageSendHis msh = new MessageSendHis();
		msh.copyFromMessageSend(temp);
		msh.setGenReason("1");
		msh.setGenTime(new Date());
		messageManagerDao.saveMessageSendHis(msh);
		messageManagerDao.updateMessageSend(messageSend);
		//修改的时候添加或者更新到短信发送表
		MessageSendOut messageSendOut=messageManagerDao.findByMessageImportID(messageSend.getId());
		if(messageSendOut==null){
			messageSendOut=new MessageSendOut(messageSend);
			BigDecimal messageSendOut_ID = oMSSequenceUtil.getSequence("OMS_MESSAGESENDOUT_SEQ");
			messageSendOut.setId(Long.valueOf(messageSendOut_ID.toString()));
			messageManagerDao.saveMessageSendOut(messageSendOut);
		}
		else {
			messageSendOut=new MessageSendOut(messageSend);
			messageManagerDao.updateMessageSendOutByImportMessage(messageSendOut);
		}
	}

	@Override
	public List<SeatMessage> findBySeatMessageIdList(String idList)
			throws Exception {
		return messageManagerDao.findBySeatMessageIdList(idList);
	}

	@Override
	public void importMsgForSend(List<MessageSendOut> msgList) throws Exception {
		 messageManagerDao.importMsgForSend(msgList);
	}

}
