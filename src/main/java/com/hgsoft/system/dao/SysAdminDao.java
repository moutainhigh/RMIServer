package com.hgsoft.system.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Repository
public class SysAdminDao extends BaseDao{
	public Pager findSysAdminList(Pager pager){
		StringBuffer sql = new StringBuffer(
				"SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID ");
		
		return this.findByPages(sql.toString(), pager,null);
	}


	
//	public void save(SysAdmin sysAdmin){
//		Map map = FieldUtil.getPreFieldMap(SysAdmin.class,sysAdmin);
//		StringBuffer sql=new StringBuffer("insert into CSMS_ADMIN ");
//		sql.append(map.get("insertNameStrNotNull"));
//		saveOrUpdate(sql.toString(), (List) map.get("param"));
//	}
	
//	public void update(SysAdmin sysAdmin){
//		Map map = FieldUtil.getPreFieldMap(SysAdmin.class,sysAdmin);
//		StringBuffer sql=new StringBuffer("update CSMS_ADMIN set ");
//		sql.append(map.get("nameAndValueNotNullToUpdate") +" where id = ?");
//		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),sysAdmin.getId());
//	}
	
//	public void delete(SysAdmin sysAdmin){
//		String sql="delete from CSMS_ADMIN where id=?";
//		super.delete(sql,sysAdmin.getId());
//	}
	
	/*public void deleteAll(){
		String sql="delete from CSMS_ADMIN";
		super.delete(sql);
	}*/
	/**
	 * 根据系统编码删除该系统的操作员 -废弃了，现在已经不需要同步了 by关少锋 20171015
	 * @param subuumstemCode
	 * @return void
	 */
	public void deleteBySubuumstemCode(String subuumstemCode){
		String sql="delete from CSMS_ADMIN where SubuumstemCode=? ";
		super.delete(sql,subuumstemCode);
	}
	
	/**
	 * 找所有用户
	 * @return
	 */
	public List<Map<String, Object>> findSysAdmins(){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID where b.USESTATE = '1'";
		return queryList(sql);
	}

	/***
	 * -废弃了，现在已经不需要同步了 by关少锋 20171015
	 * @param list
	 * @return
     */
	public int[] batchSaveSysAdmin(final List<SysAdmin> list) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "insert into CSMS_ADMIN(id,loginname,username,password,sex,staffno,"
        		+ "email,phone,userstate,usestate,iplimit,logintime,loginip,lastlogintime,lastloginip,remark,company,"
        		+ "department,dename,createid,updateid,createtime,updatetime,custompoint,pointname,admingroup,custompointtype,pointcode,subuumstemCode) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SysAdmin sysAdmin = list.get(i);
				if(sysAdmin.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, sysAdmin.getId());
				if(sysAdmin.getLoginName()==null)ps.setNull(2, Types.VARCHAR);else ps.setString(2, sysAdmin.getLoginName());
				if(sysAdmin.getUserName()==null)ps.setNull(3, Types.VARCHAR);else ps.setString(3, sysAdmin.getUserName());
				if(sysAdmin.getPassword()==null)ps.setNull(4, Types.VARCHAR);else ps.setString(4, sysAdmin.getPassword());
				if(sysAdmin.getSex()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, sysAdmin.getSex());
				if(sysAdmin.getStaffNo()==null)ps.setNull(6, Types.VARCHAR);else ps.setString(6, sysAdmin.getStaffNo());
				if(sysAdmin.getEmail()==null)ps.setNull(7, Types.VARCHAR);else ps.setString(7, sysAdmin.getEmail());
				if(sysAdmin.getPhone()==null)ps.setNull(8, Types.VARCHAR);else ps.setString(8, sysAdmin.getPhone());
				if(sysAdmin.getUserState()==null)ps.setNull(9, Types.VARCHAR);else ps.setString(9, sysAdmin.getUserState());
				if(sysAdmin.getUseState()==null)ps.setNull(10, Types.VARCHAR);else ps.setString(10, sysAdmin.getUseState());
				if(sysAdmin.getIpLimit()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, sysAdmin.getIpLimit());
				if(sysAdmin.getLoginTime()==null)ps.setNull(12, Types.DATE);else ps.setTimestamp(12, new java.sql.Timestamp(sysAdmin.getLoginTime().getTime()));
				if(sysAdmin.getLoginIp()==null)ps.setNull(13, Types.VARCHAR);else ps.setString(13, sysAdmin.getLoginIp());
				if(sysAdmin.getLastLoginTime()==null)ps.setNull(14, Types.DATE);else ps.setTimestamp(14, new java.sql.Timestamp(sysAdmin.getLastLoginTime().getTime()));
				if(sysAdmin.getLoginIp()==null)ps.setNull(15, Types.VARCHAR);else ps.setString(15, sysAdmin.getLastLoginIp());
				if(sysAdmin.getRemark()==null)ps.setNull(16, Types.VARCHAR);else ps.setString(16, sysAdmin.getRemark());
				if(sysAdmin.getCompany()==null)ps.setNull(17, Types.BIGINT);else ps.setLong(17, sysAdmin.getCompany());
				if(sysAdmin.getDepartment()==null)ps.setNull(18, Types.BIGINT);else ps.setLong(18, sysAdmin.getDepartment());
				if(sysAdmin.getDeName()==null)ps.setNull(19, Types.VARCHAR);else ps.setString(19, sysAdmin.getDeName());
				if(sysAdmin.getCreateId()==null)ps.setNull(20, Types.BIGINT);else ps.setLong(20, sysAdmin.getCreateId());
				if(sysAdmin.getUpdateId()==null)ps.setNull(21, Types.BIGINT);else ps.setLong(21, sysAdmin.getUpdateId());
				if(sysAdmin.getCreateTime()==null)ps.setNull(22, Types.DATE);else ps.setTimestamp(22, new java.sql.Timestamp(sysAdmin.getCreateTime().getTime()));
				if(sysAdmin.getUpdateTime()==null)ps.setNull(23, Types.DATE);else ps.setTimestamp(23, new java.sql.Timestamp(sysAdmin.getUpdateTime().getTime()));
				if(sysAdmin.getCustomPoint()==null)ps.setNull(24, Types.BIGINT);else ps.setLong(24, sysAdmin.getCustomPoint());
				if(sysAdmin.getPointName()==null)ps.setNull(25, Types.VARCHAR);else ps.setString(25, sysAdmin.getPointName());
				if(sysAdmin.getAdminGroup()==null)ps.setNull(26, Types.BIGINT);else ps.setLong(26, sysAdmin.getAdminGroup());
				if(sysAdmin.getCustomPointType()==null)ps.setNull(27, Types.VARCHAR);else ps.setString(27, sysAdmin.getCustomPointType());
				if(sysAdmin.getPointCode()==null)ps.setNull(28, Types.VARCHAR);else ps.setString(28, sysAdmin.getPointCode());
				if(sysAdmin.getSubuumstemCode()==null)ps.setNull(29, Types.VARCHAR);else ps.setString(29, sysAdmin.getSubuumstemCode());
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	/**
	 * 双人复核根据stuffno,subuumstemCode查找SysAdmin
	 * @return
	 */
	public SysAdmin findByStaffno(String staffno,String subuumstemCode){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL," +
				" b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, " +
				"b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, " +
				"b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, " +
				"b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, " +
				"b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B " +
				"JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID AND A.subuumstem = ? WHERE b.STAFFNO = ?";
		List list=queryList(sql, subuumstemCode,staffno);
		SysAdmin sysAdmin=null;
		if(list.size()!=0)sysAdmin=(SysAdmin)this.convert2Bean((Map<String, Object>) list.get(0), new SysAdmin());

		return sysAdmin;
	}



	/**
	 * 主管授权和主任授权根据stuffno,subuumstemCode,url查找SysAdmin
	 * @return
	 */
	public SysAdmin findAdminBySSU(String staffno,String subuumstemCode,String url){
		String sql = "SELECT A.ID, A.LOGINNAME, A.USERNAME, A.PASSWORD, A.SEX , A.STAFFNO, " +
				"A.EMAIL, A.PHONE, A.USERSTATE, A.USESTATE , A.IPLIMIT, A.LOGINTIME, A.LOGINIP, " +
				"A.LASTLOGINTIME, A.LASTLOGINIP , A.REMARK, A.COMPANY, A.DEPARTMENT, A.DENAME, A.CREATEID , " +
				"A.UPDATEID, A.CREATETIME, A.UPDATETIME, A.CUSTOMPOINT, A.POINTNAME , NULL AS ADMINGROUP, " +
				"A.CUSTOMPOINTTYPE, A.POINTCODE, B.subuumstem AS subuumstemCode, ba.* , bt.* FROM uums_admin A " +
				"JOIN uums_admin_subuumstem B ON B.ADMIN = A.ID AND A.STAFFNO = ? AND B.subuumstem = ? " +
				"JOIN csms_businessaccredit_admin ba ON ba.ADMIN = A.ID " +
				"JOIN csms_businessaccredit bt ON bt.ID = ba.businessaccredit AND bt.url = ? AND bt.usestate = '2'";
		List list=queryList(sql, staffno,subuumstemCode,url);
		SysAdmin sysAdmin=null;
		if(list.size()!=0)sysAdmin=(SysAdmin)this.convert2Bean((Map<String, Object>) list.get(0), new SysAdmin());

		return sysAdmin;
	}

	/**
	 * 根据username查找SysAdmin
	 * @return
	 */
	public SysAdmin findByUserName(String userName){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID AND B.username = ?";
		List list=queryList(sql, userName);
		SysAdmin sysAdmin=null;
		if(list.size()!=0)sysAdmin=(SysAdmin)this.convert2Bean((Map<String, Object>) list.get(0), new SysAdmin());

		return sysAdmin;
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SysAdminDao sysAdminDao = (SysAdminDao)context.getBean("sysAdminDao");
//		SysAdmin sysAdmin = sysAdminDao.findAdminBySSU("wdw01","S000004","/tagStopNoTag/tagStopNoTagAction_isDirector.do");
//		List<SysAdmin>  sysAdminList = sysAdminDao.findSysAdminsByCustomPoint(17253l);
		List<SysAdmin>  sysAdminList = sysAdminDao.findSysAdminsByCustomPointAndCode(17253l,"S000003");
		System.out.println("sysAdminDao");
	}

	/**
	 * 根据id查找SysAdmin
	 * @return
	 */
	public SysAdmin findSysAdminById(Long id){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID AND B.ID = ?";
		List list=queryList(sql, String.valueOf(id));
		SysAdmin sysAdmin=null;
		if(list.size()!=0)sysAdmin=(SysAdmin)this.convert2Bean((Map<String, Object>) list.get(0), new SysAdmin());

		return sysAdmin;
	}
	
	
	/**
	 * 根据层级查找SysAdmin
	 * @return
	 */
	public List<SysAdmin> findSysAdminsByCustomPoint(Long CustomPoint){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID AND B.CUSTOMPOINT = ?";
		List list=queryList(sql, CustomPoint);
		List<SysAdmin> sysAdminList= null;
		if(list!=null && list.size()>0){
			sysAdminList=new ArrayList<SysAdmin>();
			for (int i=0;i<list.size();i++) {
				SysAdmin sysAdmin=null;
				sysAdmin = (SysAdmin)convert2Bean((Map<String, Object>) list.get(i), new SysAdmin());

				sysAdminList.add(sysAdmin);
		}
		}
		
		return sysAdminList;
	}
	
	
	/**
	 * 根据层级和系统编码查找SysAdmin
	 * @return
	 */
	public List<SysAdmin> findSysAdminsByCustomPointAndCode(Long CustomPoint,String subuumstemCode){
		String sql = "SELECT b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX , b.STAFFNO, b.EMAIL, b.PHONE, " +
				"b.USERSTATE, b.USESTATE , b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP , " +
				"b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID , b.UPDATEID, b.CREATETIME, b.UPDATETIME, " +
				"b.CUSTOMPOINT, b.POINTNAME , NULL AS ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, " +
				"A.subuumstem AS subuumstemCode FROM uums_admin B JOIN uums_admin_subuumstem A ON A.ADMIN = b.ID " +
				"AND B.CustomPoint = ? AND A.subuumstem = ?";
		List list=queryList(sql, CustomPoint,subuumstemCode);
		List<SysAdmin> sysAdminList= null;
		if(list!=null && list.size()>0){
			sysAdminList=new ArrayList<SysAdmin>();
			for (int i=0;i<list.size();i++) {
				SysAdmin sysAdmin=null;
				sysAdmin = (SysAdmin)convert2Bean((Map<String, Object>) list.get(i), new SysAdmin());

				sysAdminList.add(sysAdmin);
		}
		}
		
		return sysAdminList;
	}
	
	
}
