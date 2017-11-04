package com.hgsoft.account.dao;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.MainAccountInfoHis;
import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("mainAccountInfoHisDao")
public class MainAccountInfoHisDao extends BaseDao {

	
	public void saveHis(MainAccountInfoHis mainAccountInfoHis,MainAccountInfo mainAccountInfo) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_MAINACCOUNT_INFOHIS("
				+ "ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,operNo,operName,placeNo,placeName,OPEBACCOUNTDATE,HISSEQID,CREATEDATE,CREATEREASON )  "
				+ "SELECT "+mainAccountInfoHis.getId()+",MAINID,BALANCE-("+mainAccountInfo.getAvailableBalance()+")-("
				+mainAccountInfo.getPreferentialBalance()+")-("
				+mainAccountInfo.getFrozenBalance()+")-("
				+mainAccountInfo.getAvailableRefundBalance()+")-("
				+mainAccountInfo.getRefundApproveBalance()
				+"),AVAILABLEBALANCE-("+mainAccountInfo.getAvailableBalance()+")"
				+",PREFERENTIALBALANCE-("+mainAccountInfo.getPreferentialBalance()+"),"
				+ "FROZENBALANCE-("+mainAccountInfo.getFrozenBalance()+")"
				+",AVAILABLEREFUNDBALANCE-("+mainAccountInfo.getAvailableRefundBalance()+")"
				+",REFUNDAPPROVEBALANCE-("+mainAccountInfo.getRefundApproveBalance()+"),STATE,"
				+ "OPERID,PLACEID,operNo,operName,placeNo,placeName,OPEBACCOUNTDATE,"+mainAccountInfoHis.getHisSeqId()+",sysdate," 
				+ "'"+mainAccountInfoHis.getCreateReason()+"'  FROM CSMS_MAINACCOUNT_INFO WHERE mainid="+mainAccountInfo.getMainId()+"");
		save(sql.toString());
	}
	
	public MainAccountInfoHis findByHisId(Long disId) {
		String sql="select *  from CSMS_MAINACCOUNT_INFOHIS ";
		List list=null;
		if(disId==null){
			sql=sql+" where HISSEQID is null";
			list=queryList(sql);
		}else{
			sql=sql+" where HISSEQID=?";
			list=queryList(sql, disId);
		}
		
		MainAccountInfoHis mainAccountInfoHis = null;
		if(list!=null && list.size()!=0) {
			mainAccountInfoHis = (MainAccountInfoHis) this.convert2Bean((Map<String, Object>) list.get(0), new MainAccountInfoHis());
		}

		return mainAccountInfoHis;
	}
}
