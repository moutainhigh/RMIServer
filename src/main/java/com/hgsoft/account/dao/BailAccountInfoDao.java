package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
/**
 * 保证金账户信息
 * @author guanshaofeng
 *
 */
@Component
public class BailAccountInfoDao extends BaseDao{
	public void saveBailAccount(BailAccountInfo bailAccountInfo){
		bailAccountInfo.setHisSeqId(-bailAccountInfo.getId());
		Map map = FieldUtil.getPreFieldMap(BailAccountInfo.class,bailAccountInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_BailAccount_Info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql = new StringBuffer("insert into CSMS_BailAccount_Info(");
		sql.append(FieldUtil.getFieldMap(BailAccountInfo.class,bailAccountInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(BailAccountInfo.class,bailAccountInfo).get("valueStr")+")");
		save(sql.toString());*/
		
		
	}

	public BailAccountInfo findByCustomerID(Long customerId) {
		String sql = "select * from CSMS_BailAccount_Info where MainID=?";
		List<BailAccountInfo> bailAccountInfos = super.queryObjectList(sql, BailAccountInfo.class, customerId);
		if (bailAccountInfos == null || bailAccountInfos.isEmpty()) {
			return null;
		}
		return bailAccountInfos.get(0);
	}

	public BailAccountInfo findByID(Long id) {
		String sql = "select * from CSMS_BailAccount_Info where id=?";
		List<BailAccountInfo> bailAccountInfos = super.queryObjectList(sql, BailAccountInfo.class, id);
		if (bailAccountInfos == null || bailAccountInfos.isEmpty()) {
			return null;
		}
		return bailAccountInfos.get(0);
	}


	public void update(BailAccountInfo bailAccountInfo) {
		Map map = FieldUtil.getPreFieldMap(BailAccountInfo.class,bailAccountInfo);
		StringBuffer sql=new StringBuffer("update CSMS_BailAccount_Info set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),bailAccountInfo.getId());
		/*StringBuffer sql=new StringBuffer("update CSMS_BailAccount_Info set ");
		sql.append(FieldUtil.getFieldMap(BailAccountInfo.class,bailAccountInfo).get("nameAndValue")+" where id="+bailAccountInfo.getId());
		update(sql.toString());*/
	}

	public void updateWithHisByTransfer(Long id, Long customerid, BigDecimal bail, String createReason, String type) {
		String sql = "insert into csms_bailaccount_infohis(CREATEREASON,CREATEDATE,HISSEQID,OPERTIME,PLACEID,OPERID,BAILFEE,MAINID,ID)  "
				+ "SELECT ?,sysdate,HISSEQID,OPERTIME,PLACEID,OPERID,BAILFEE,MAINID,"+id+" FROM csms_bailaccount_info WHERE MainID="+customerid;
		saveOrUpdate(sql,createReason);
		update("update csms_bailaccount_info b set b.hisseqid="+id+" ,b.bailfee=b.bailfee"+type+bail.toString()+" where b.mainid="+customerid);
		
	}
	/**
	 * 若传入的保证金金额是正数，则表示将保证金金额扣除到冻结金额
	 * 若传入的保证金金额是负数，则表示将冻结金额扣除到保证金金额
	 * @param bailFee
	 * @author gsf
	 * 
	 */
	public int updateBailToFrozen(BigDecimal bailFee,BailAccountInfo bailAccountInfo){
		StringBuffer sql = new StringBuffer("");
		if(bailAccountInfo.getBailFrozenBalance() == null){
			sql.append("update CSMS_BailAccount_Info set bailFee=bailFee-?,bailFrozenBalance=0+? ");
		}else{
			sql.append("update CSMS_BailAccount_Info set bailFee=bailFee-?,bailFrozenBalance=bailFrozenBalance+? ");
		}
		if(bailAccountInfo.getHisSeqId()!=null){
			sql.append(",HisSeqID=? where id=? ");
			if(bailFee.compareTo(BigDecimal.ZERO) == 1){
				sql.append(" and bailFee >= ? ");
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId(),bailFee.abs());
			}else if(bailFee.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and bailFrozenBalance >= ? ");
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId(),bailFee.abs());
			}else{
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId());
			}
			//saveOrUpdate(sql.toString(),bailFee,bailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId());
		}else{
			sql.append(" where id=? ");
			if(bailFee.compareTo(BigDecimal.ZERO) == 1){
				sql.append(" and bailFee >= ? ");
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getId(),bailFee.abs());
			}else if(bailFee.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and bailFrozenBalance >= ? ");
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getId(),bailFee.abs());
			}else{
				return this.update(sql.toString(),bailFee,bailFee,bailAccountInfo.getId());
			}
			//saveOrUpdate(sql.toString(),bailFee,bailFee,bailAccountInfo.getId());
		}
	}
	/**
	 * 扣除保证金冻结金额(传入负数就是扣除)
	 * @param bailFrozenBalance
	 * @param bailAccountInfo
	 */
	public int updateFrozen(BigDecimal bailFrozenBalance,BailAccountInfo bailAccountInfo){
		StringBuffer sql = new StringBuffer(
				"update CSMS_BailAccount_Info set BailFrozenBalance=BailFrozenBalance+?");
		if(bailAccountInfo.getHisSeqId()!=null){
			sql.append(",HisSeqID=? where id=?");
			if(bailFrozenBalance.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and BailFrozenBalance >= ?");
				return this.update(sql.toString(),bailFrozenBalance,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId(),bailFrozenBalance.abs());
			}else{
				return this.update(sql.toString(),bailFrozenBalance,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId());
			}
			//saveOrUpdate(sql.toString(),bailFrozenBalance,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId());
		}else{
			sql.append(" where id=? ");
			if(bailFrozenBalance.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and BailFrozenBalance >= ?");
				return this.update(sql.toString(),bailFrozenBalance,bailAccountInfo.getId(),bailFrozenBalance.abs());
			}else{
				return this.update(sql.toString(),bailFrozenBalance,bailAccountInfo.getId());
			}
			//saveOrUpdate(sql.toString(),bailFrozenBalance,bailAccountInfo.getId());
		}
	}
	
	/**
	 * 保证金金额变动(传入正数就是增加，传入负数就是减少)
	 * @param BailFee
	 * @param bailAccountInfo
	 */
	public int changeBailFee(BigDecimal BailFee,BailAccountInfo bailAccountInfo){
		StringBuffer sql = new StringBuffer(
				"update CSMS_BailAccount_Info set BailFee=BailFee+?");
		if(bailAccountInfo.getHisSeqId()!=null){
			sql.append(",HisSeqID=? where id=?");
			if(BailFee.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and BailFee >= ?");
				return this.update(sql.toString(),BailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId(),BailFee.abs());
			}else{
				return this.update(sql.toString(),BailFee,bailAccountInfo.getHisSeqId(),bailAccountInfo.getId());
			}
		}else{
			sql.append(" where id=? ");
			if(BailFee.compareTo(BigDecimal.ZERO) == -1){
				sql.append(" and BailFee >= ?");
				return this.update(sql.toString(),BailFee,bailAccountInfo.getId(),BailFee.abs());
			}else{
				return this.update(sql.toString(),BailFee,bailAccountInfo.getId());
			}
		}
	}
}
