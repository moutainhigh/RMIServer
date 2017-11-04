package com.hgsoft.httpInterface.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.OMSMinusBank;
import com.hgsoft.utils.StringUtil;

@Repository
public class MinusBankDao extends BaseDao{
	public void add(OMSMinusBank omsMinusBank){
		Map map = FieldUtil.getPreFieldMap(OMSMinusBank.class,omsMinusBank);
		StringBuffer sql=new StringBuffer("insert into CSMS_OMS_MinusBank");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	public void delete(Long id){
		String sql = "delete from CSMS_OMS_MinusBank where id=?";
		delete(sql,id);
	}
	public void update(OMSMinusBank omsMinusBank){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(omsMinusBank.getOperTime());
		StringBuffer sql = new StringBuffer("update CSMS_OMS_MinusBank om set om.operId=?,om.operName=?,om.operTime=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
		
		List<String> list=new ArrayList<String>();
		list.add(String.valueOf(omsMinusBank.getOperId()));
		list.add(omsMinusBank.getOperName());
		list.add(time);
		if(StringUtil.isNotBlank(omsMinusBank.getClearingBankCode())){
			sql.append(",om.clearingBankCode=?");
			list.add(omsMinusBank.getClearingBankCode());
		}
		if(StringUtil.isNotBlank(omsMinusBank.getFocusHandleCode())){
			sql.append(",om.focusHandleCode=?");
			list.add(omsMinusBank.getFocusHandleCode());
		}
		if(StringUtil.isNotBlank(omsMinusBank.getFocusHandleArea())){
			sql.append(",om.focusHandleArea=?");
			list.add(omsMinusBank.getFocusHandleArea());
		}
		if(StringUtil.isNotBlank(omsMinusBank.getBankName())){
			sql.append(",om.bankName=?");
			list.add(omsMinusBank.getBankName());
		}
		if(StringUtil.isNotBlank(omsMinusBank.getMemo())){
			sql.append(",om.memo=?");
			list.add(omsMinusBank.getMemo());
		}
		if(StringUtil.isNotBlank(omsMinusBank.getState())){
			sql.append(",om.state=?");
			list.add(omsMinusBank.getState());
		}
		if(omsMinusBank.getPlaceId()!=null){
			sql.append(",om.placeId=?");
			list.add(String.valueOf(omsMinusBank.getPlaceId()));
		}
		if(StringUtil.isNotBlank(omsMinusBank.getPlaceName())){
			sql.append(",om.placeName=?");
			list.add(omsMinusBank.getPlaceName());
		}
		sql=sql.append(" where om.id=? ");
		list.add(String.valueOf(omsMinusBank.getId()));
		saveOrUpdate(sql.toString(),list.toArray());
		
	}
}
