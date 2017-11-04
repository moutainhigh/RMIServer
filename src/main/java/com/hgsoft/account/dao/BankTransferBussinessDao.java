package com.hgsoft.account.dao;

import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Component
public class BankTransferBussinessDao extends BaseDao {
@Resource
private SequenceUtil sequenceUtil;	
public void saveBailAccount(BankTransferBussiness bankTransferBussiness){
		if(bankTransferBussiness.getId()==null){
			bankTransferBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBankTBussiness_NO"));
		}
		Map map = FieldUtil.getPreFieldMap(BankTransferBussiness.class,bankTransferBussiness);
		StringBuffer sql=new StringBuffer("insert into CSMS_BankTransfer_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public BankTransferBussiness find(BankTransferBussiness bankTransferBussiness) {
		if (bankTransferBussiness == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(BankTransferBussiness.class, bankTransferBussiness);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("BankTransferBussinessDao.find方法查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_BankTransfer_Bussiness where 1=1 ");
		sql.append(condition);
		sql.append(" order by id");
		List<BankTransferBussiness> bankTransferBussinesses = super.queryObjectList(sql.toString(), BankTransferBussiness.class, ((List) map.get("paramNotNull")).toArray());
		if (bankTransferBussiness == null || bankTransferBussinesses.isEmpty()) {
			return null;
		}
		return bankTransferBussinesses.get(0);
	}
	
/**
 * 2017-06-05 
 * 查找银行到账信息记录的操作业务流水
 * @param pager
 * @param bankTransferId
 * @return Pager
 */
	public Pager findBankTransferBussiness(Pager pager,Long bankTransferId) {
		String sql= "select bi.arrivalTime,bb.id,bb.idType,bb.idCode,bb.clientName,bb.payName,bb.transferBlanace,bb.bankNo,"
				+ "bb.operId,bb.placeId,bb.operDate,bb.operNo,bb.operName,bb.placeNo,bb.placeName,bb.rechargeType,bb.blanace,"
				+ "bb.RechargeCost,bb.bankTransferId,ROWNUM as num from CSMS_BankTransfer_Bussiness bb "
				+ " join CSMS_BankTransfer_Info bi on bi.id=bb.bankTransferId where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(bankTransferId!=null){
			params.eq("bankTransferId",bankTransferId);
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by id desc ");
		return this.findByPages(sql, pager,Objects);
	}
	
}
