package com.hgsoft.accountC.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.TransferDetail;
import com.hgsoft.common.dao.BaseDao;

@Repository
public class TransferDetailDao extends BaseDao{

	public int[] batchSaveTransferDetail(final List<TransferDetail> list) {  
        String sql = "insert into CSMS_transfer_detail(ID,TransferID,cardNo,OperID,PlaceID,OperName,OperNo,PlaceName,PlaceNo,OperTime) values(SEQ_CSMSTransferDetail_NO.nextval,?,?,?,?,?,?,?,?,sysdate)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				TransferDetail TransferDetail = list.get(i);
				ps.setLong(1, TransferDetail.getTransferId());
				ps.setString(2, TransferDetail.getCardNo());
				ps.setLong(3, TransferDetail.getOperId());
				ps.setLong(4, TransferDetail.getPlaceId());
				ps.setString(5, TransferDetail.getOperName());
				ps.setString(6, TransferDetail.getOperNo());
				ps.setString(7, TransferDetail.getPlaceName());
				ps.setString(8, TransferDetail.getPlaceNo());
				
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }

	public List<Map<String, Object>> findCardByTransferID(Long TransferID) {
		String sql = " select  * from csms_transfer_detail where TransferID=?";
		return queryList(sql.toString(),TransferID);
	} 
	public List<Map<String, Object>> findPreCardByTransferID(Long TransferID) {
		String sql = " select  * from Csms_Prepaidctransferdetail where transferid=?";
		return queryList(sql.toString(),TransferID);
	}  
}
