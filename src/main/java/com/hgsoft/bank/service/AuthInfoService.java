package com.hgsoft.bank.service;

import com.hgsoft.bank.dao.AuthInfoDao;
import com.hgsoft.bank.entity.AuthInfo;
import com.hgsoft.bank.serviceInterface.IAuthInfoService;
import com.hgsoft.common.dao.OmsBaseDao;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.SequenceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 银行接口操作员签到与签退
 * @author gaosiling
 * 2016年11月7日 17:18:48
 */
@Service
public class AuthInfoService implements IAuthInfoService{
	private static Logger logger = Logger.getLogger(AuthInfoService.class.getName());
	
	@Resource
	private AuthInfoDao authInfoDao;
	@Resource
	private OmsBaseDao omsBaseDao;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	private static final int DEFAULT_TIMEOUT_MINUTES = 10;
	private static final String KEY_PREFIX = "bank_timeout_";
	
	private Map<String, Object> findUser(String operNo, String terminalNo) {
		String sql = "select * from uums_admin where loginName=? and pointCode=?";
//		String sql = "select 1 from dual";
		List<Map<String, Object>> user = omsBaseDao.queryList(sql, operNo,
				terminalNo);
		if (user == null || user.isEmpty()) {
			return null;
		}
		return user.get(0);
	}

	/**
	 * 操作员签到
	 */
	@Override
	public Map<String, String> save(AuthInfo authInfo) {
		Map<String, Object> user = findUser(authInfo.getOperNo(), 
				authInfo.getTerminalNo());
		if (user == null) {
			throw new ApplicationException("操作员不存在");
		}
		CusPointPoJo cusPointPoJo = findCusPointPoJoByTerminalNo(authInfo.getTerminalNo());
		if (!cusPointPoJo.getBankNo().equals(authInfo.getBankNo())) {
			throw new ApplicationException("操作员不存在");
		}
		String encryptPassword = DesEncrypt.getInstance().encrypt(authInfo.getPassword());
		String password = (String) user.get("PASSWORD");
//		String password = encryptPassword;
		if (!encryptPassword.equals(password)) {
			throw new ApplicationException("密码错误");
		}
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSauthInfo_NO");
		authInfo.setId(seq);
		authInfo.setLoginDate(new Date());
		authInfo.setState("1");
		try {
			authInfo.setPassword(null);
			authInfoDao.save(authInfo);
			return null;
		} catch (Exception e) {
			logger.error("操作员签到失败", e);
			throw new ApplicationException("操作员签到失败");
		}
	}

	/**
	 * 操作员签退
	 */
	@Override
	public Map<String, String> delete(AuthInfo authInfo) {
		try {
			authInfoDao.delete(authInfo);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作员签退失败", e);
			throw new ApplicationException("操作员签退失败");
		}
	}
	
	/**
	 * 查询签到情况
	 */
	@Override
	public AuthInfo findByAuth(AuthInfo authInfo) {
		try {
			String sql = "select a.*,u.id adminId,u.username adminName" +
					" from CSMS_AUTH_INFO a join uums_admin u on" +
					" a.operNo=u.loginName and a.terminalNo=u.pointCode where operNo=?" +
					" and terminalNo=? and bankNo=?";
			AuthInfo _authInfo;
			try {
				_authInfo = authInfoDao.queryRowObject(sql, AuthInfo.class,
						authInfo.getOperNo(), authInfo.getTerminalNo(),
						authInfo.getBankNo());
				Date loginDate = _authInfo.getLoginDate();
				if (loginDate == null) {
					authInfoDao.deleteById(_authInfo.getId());
					return null;
				}
				DateTime dateTime = new DateTime(loginDate);
				int timeout = findTimeout(authInfo.getBankNo());
				DateTime expireTime = dateTime.plusMinutes(timeout);
				if (expireTime.isBefore(new DateTime())) {
					//登录超时
					logger.debug("{}登录超时");
					authInfoDao.deleteById(_authInfo.getId());
					return null;
				}
				Map<String, Object> user = findUser(authInfo.getOperNo(),
						authInfo.getTerminalNo());
				if (user == null) {
					throw new ApplicationException("操作员不存在");
				}
				_authInfo.setPassword((String) user.get("PASSWORD"));
				return _authInfo;

			} catch (EmptyResultDataAccessException e) {
				return null;
			}
		} catch (Exception e) {
			logger.error("查询签到情况", e);
			throw new ApplicationException("查询签到情况");
		}
	}

	private int findTimeout(String bankNo) {
		String key = KEY_PREFIX + bankNo;
		String sql = "select * from OMS_serviceParamSet_new where key=? and status=1";
		List<Map<String, Object>> params = omsBaseDao.queryList(sql, key);
		if (CollectionUtils.isEmpty(params)) {
			return DEFAULT_TIMEOUT_MINUTES;
		}
		return (int) Integer.valueOf((String) params.get(0).get("value"));
	}

	@Override
	public void updatePassword(AuthInfo authInfo) {
		String sql = "update uums_admin set password=? where loginName=? and pointCode=?";
		authInfoDao.update(sql,
				DesEncrypt.getInstance().encrypt(authInfo.getPassword()),
				authInfo.getOperNo(), authInfo.getTerminalNo());
	}

	@Override
	public CusPointPoJo findCusPointPoJoByTerminalNo(String terminalNo) {
		String sql = "select id cusPoint,code cusPointCode,name cusPointName " +
				",bankCode bankNo from OMS_customPoint where code=? and useState=1";
		try {
			return omsBaseDao.queryObject(sql, CusPointPoJo.class, terminalNo);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateLastLoginTime(Long id) {
		String sql = "update CSMS_AUTH_INFO set loginDate=? where id=?";
		authInfoDao.update(sql, new Date(), id);
	}

	/**
	 *车牌信息查询
	 */
	/*@Override
	public Map<String,Object> getLicenseInfo(String vehiclePlate,String vehicleColor){
		try {
			return authInfoDao.getLicenseInfo(vehiclePlate,vehicleColor);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("车牌信息查询");
			throw new ApplicationException("车牌信息查询");
		}
	}*/
	
}
