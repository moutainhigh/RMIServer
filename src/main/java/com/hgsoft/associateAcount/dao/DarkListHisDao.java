package com.hgsoft.associateAcount.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.associateAcount.entity.DarkListHis;
import com.hgsoft.common.dao.BaseDao;

/**
 * 
 * @author gsf
 *
 */
@Repository
public class DarkListHisDao extends BaseDao{
	/**
	 * @author gsf
	 * @param darkList
	 * @param darkListHis
	 */
	public void save(DarkList darkList,DarkListHis darkListHis){
		String sql = "insert into csms_dark_listhis(ID,CreateDate,CreateReason,CustomerID,CardNo,CardType,"
				+ "genDate,genCau,genMode,OperId,placeId,Remark,HisSeqID )  "
				+ "SELECT "+darkListHis.getId()+",sysdate,'"+darkListHis.getCreateReason()+"',CustomerID,CardNo,CardType,"
				+ "genDate,genCau,genMode,OperId,placeId,Remark,HisSeqID "
				+ " FROM csms_dark_list WHERE id="+darkList.getId();
		save(sql);
	}
}
