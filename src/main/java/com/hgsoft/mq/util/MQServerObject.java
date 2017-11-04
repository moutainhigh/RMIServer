package com.hgsoft.mq.util;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import com.mqplus.MQMessage;
import com.mqplus.MQQueue;
import com.mqplus.MQServer;
import com.mqplus.MQSession;


public class MQServerObject {

	private static Logger logger = Logger.getLogger(MQServerObject.class.getName());
	
	public static MQServer server = null;

	public static MQSession ses = new MQSession();

	public static MQMessage reqMsg = new MQMessage();

	public static MQMessage resMsg = new MQMessage();

	public MQServerObject() {

	}

	/*public void getInstance() throws JMSException {
		System.out.println(ip);
		if (server == null) {
			synchronized (this) {
				server = new MQServer();
				System.out.println("閻ц缍嶅☉鍫熶紖闂冪喎鍨�);
				logger.info("閻ц缍嶅☉鍫熶紖闂冪喎鍨�);
				
				
				
				ses.set_m_iTimeout(1); // 缁夛拷
				ses.set_m_iTryTimes(1);
				MQQueue reqQ = new MQQueue(server);
				MQQueue ansQ = new MQQueue(server);

				try {
					server.login(ip, port, user, password);
					ansQ.open("ansGZ", "rw");
					reqQ.open("reqGZ", "rw");

					ses.set_m_pQTo(reqQ);
					ses.set_m_pQFrom(ansQ);
				} catch (JMSException e) {
					e.printStackTrace();
					server.logout();
					ansQ.close();
					reqQ.close();
					server = null;
					logger.info("閻ц鍤☉鍫熶紖闂冪喎鍨�);
				} finally {
				}
			}
		}
	}*/

	public String setAndGetMsg(String msg) {
		String mResult = "";
		
		//System.out.println("begin:" );
		MQSession ses = new MQSession();

		ses.set_m_iTimeout(30);
		ses.set_m_iTryTimes(2);

		MQServer server = new MQServer();
		MQQueue reqQ = new MQQueue(server);
		MQQueue ansQ = new MQQueue(server);
		try {

			//server.login("172.20.19.107", 9960, "mqplus", "cwy123");
			//server.login("10.44.1.197", 9960, "mqplus", "cwy123");
			server.login("10.44.1.196", 9900, "mqplus", "cwy123");
//			server.login(ip, port, user, password);

			reqQ.set_m_pServer(server);
			ansQ.set_m_pServer(server);

//			ansQ.open("ansGZ", "rw");
//			reqQ.open("reqGZ", "rw");
//			ansQ.open("ansIVR", "rw");
//			reqQ.open("reqIVR", "rw");
			ansQ.open("ansZSJ_YZ", "rw");
			reqQ.open("reqZSJ_YZ", "rw");

			ses.set_m_pQTo(reqQ);
			ses.set_m_pQFrom(ansQ);

			MQMessage reqMsg = new MQMessage();

			MQMessage resMsg = new MQMessage();

		//	System.out.print("11.    \r\n");

			

			reqMsg.setMsg(msg.getBytes("GBK"));
			ses.MQCall(reqMsg, resMsg);
			//mResult = resMsg.getMsgByString();
			mResult = new String(resMsg.getMsg(),"GBK");
			//System.out.println("111:" + resMsg.getMsgByString());

			server.logout();

		//	System.out.println("finish.    ");

		} catch (JMSException ex) {
			logger.error("jms err: " + ex.getErrorCode());
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			server.logout();
			ansQ.close();
			reqQ.close();
		}
		
		logger.debug("mq返回的结果："+mResult);
		return mResult;
	}
	
	/*public String putAndGetMsg(String mResult) {
		MQSession ses = new MQSession();
		ses.set_m_iTimeout(5); // 缁夛拷
		ses.set_m_iTryTimes(3);

		MQServer server = new MQServer();
		MQQueue reqQ = new MQQueue(server);
		MQQueue ansQ = new MQQueue(server);

		try {
			server.login(ip, port, user, password);
			ansQ.open("ansGZ", "rw");
			reqQ.open("reqGZ", "rw");

			ses.set_m_pQTo(reqQ);
			ses.set_m_pQFrom(ansQ);

			MQMessage reqMsg = new MQMessage();

			MQMessage resMsg = new MQMessage();

			ses.MQCall(reqMsg, resMsg);
			try {
				reqMsg.setMsg(mResult.getBytes("GBK")); // set params
				mResult = new String(resMsg.getMsg(), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("閻ц缍嶆径杈Е", e);
		} finally {
			server.logout();
			ansQ.close();
			reqQ.close();
		}
		return mResult;
	}*/
	

	private String ip;

	private int port;

	private String user;

	private String password;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
