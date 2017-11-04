package com.hgsoft.system.serviceInterface;

import java.util.List;

import com.hgsoft.system.entity.MessageCategory;
import com.hgsoft.system.entity.MessageGrade;
import com.hgsoft.system.entity.MessageSend;
import com.hgsoft.system.entity.MessageSendOut;
import com.hgsoft.system.entity.SeatMessage;
import com.hgsoft.system.entity.SeatMessageHis;

/**
 * 短信发送管理
 * @2017-6-23 2017-6-23 下午2:44:07
 * @author chendongjing
 *
 *
 */
public interface IMsgSendManagerService {
	public List<MessageGrade> findAll(String state)  throws Exception ;
	public List<MessageCategory> findSecondCategory(MessageGrade messageGrade,String useState)throws Exception;
	public List<SeatMessage> findSeatMessageByMsgType(Long msgType) throws Exception;
	public MessageCategory findMsgCategoryById(Long id)throws Exception;
	public MessageGrade findMsgGradeById(Long id)  throws Exception ;
	public SeatMessage findBySeatMessageId(Long id) throws Exception;
	public void saveSeatMessage(SeatMessage seatMessage);
	public void updateSeatMessage(SeatMessage seatMessage,SeatMessageHis seatMessageHis);
	public int findSeatMessageByContent(String content);
	public void updateMessageSend(MessageSend messageSend) throws Exception;
	public MessageSend findMessageSend(Long id)  throws Exception ;
	public List<SeatMessage> findBySeatMessageIdList(String idList) throws Exception;
	public void importMsgForSend(List<MessageSendOut> msgList) throws Exception;

}
