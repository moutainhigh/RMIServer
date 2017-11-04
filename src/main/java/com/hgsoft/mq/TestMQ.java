package com.hgsoft.mq;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;

import com.hgsoft.mq.util.InterruptFlag;
import com.mqplus.MQMessage;
import com.mqplus.MQQueue;
import com.mqplus.MQServer;
import com.mqplus.MQSession;

public class TestMQ {

	/*public static void main(String[] args) {
		MQSession ses = new MQSession();
		ses.set_m_iTimeout(5); // 秒
		ses.set_m_iTryTimes(3);

		MQServer server = new MQServer();
		MQQueue reqQ = new MQQueue(server);
		MQQueue ansQ = new MQQueue(server);
		String mResult = "";

		try {
			server.login("172.20.19.107", 9960, "mqplus", "cwy123");
			ansQ.open("ansGZ", "rw");
			reqQ.open("reqGZ", "rw");

			ses.set_m_pQTo(reqQ);
			ses.set_m_pQFrom(ansQ);

			MQMessage reqMsg = new MQMessage();

			MQMessage resMsg = new MQMessage();
			String str = InterruptFlag.A+"91001"+InterruptFlag.A+"1362200213153212"+InterruptFlag.A+"2"+InterruptFlag.A+"122"+InterruptFlag.A+"440700001"+InterruptFlag.A+"YY1001"+InterruptFlag.A;
			char ch = 0x01;// 440106001
			
			String ss  = ch
			+ "91001"
			+ ch+ "1362200213153212"
			+ ch+ "2"
			+ ch + "122"
			+ ch + "440700001"
			+ ch + "YY1001"
			+ ch;
			
			ses.MQCall(reqMsg, resMsg);
			try {
				reqMsg.setMsg(ss.getBytes("GBK")); // set params
				mResult = new String(resMsg.getMsg(), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			server.logout();
		} catch (JMSException e) {
			e.printStackTrace();
			//logger.error("调用mq", e);
		} finally {
			ansQ.close();
			reqQ.close();
		}
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer ansPost;

		System.out.println("begin:" );
		MQSession ses = new MQSession();

		ses.set_m_iTimeout(30);
		ses.set_m_iTryTimes(2);

		MQServer server = new MQServer();
		MQQueue reqQ = new MQQueue(server);
		MQQueue ansQ = new MQQueue(server);

		try {

			server.login("172.20.19.107", 9960, "mqplus", "cwy123");
			

			reqQ.set_m_pServer(server);
			ansQ.set_m_pServer(server);

			ansQ.open("ansGZ", "rw");
			reqQ.open("reqGZ", "rw");

			ses.set_m_pQTo(reqQ);
			ses.set_m_pQFrom(ansQ);

			MQMessage reqMsg = new MQMessage();

			MQMessage resMsg = new MQMessage();

			System.out.print("11.    \r\n");

			char ch = 0x01;// 440106001
			
			String ss  = ch
					+ "91000"
					+ ch+ "1649230000000371"
					+ ch+ "3C5PEJ0003"
					+ ch + "860222000"
					+ ch + "YY1001"
					+ ch + "5"
					+ ch + ""
					+ ch + "50"
					+ ch + "40"
					+ ch + "0001"
					+ ch + ""
					+ ch + "粤YLE5588"
					+ ch + "1"
					+ ch + "2"
					+ ch + "6"
					+ ch + "1"
					+ ch + "1"
					+ ch + "12"
					+ ch;

			reqMsg.setMsg(ss);
			ses.MQCall(reqMsg, resMsg);

			System.out.println("111:" + resMsg.getMsgByString());

			server.logout();

			System.out.println("finish.    ");

		} catch (JMSException ex) {
			System.out.println("jms err: " + ex.getErrorCode());
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			ansQ.close();
			reqQ.close();
		}
	}

}
