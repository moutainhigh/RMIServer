package com.hgsoft.prepaidC.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.CardBalanceData;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.ElectronicPurse;
import com.hgsoft.prepaidC.entity.ElectronicPurseHis;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;

@Repository
public class ElectronicPurseDao extends BaseDao{
	
	public void save(ElectronicPurse electronicPurse) {
		/*StringBuffer sql=new StringBuffer("insert into csms_electronic_purse(");
		sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("valueStr")+")");
		save(sql.toString());*/
		electronicPurse.setHisseqid(-electronicPurse.getId());
		Map map = FieldUtil.getPreFieldMap(ElectronicPurse.class,electronicPurse);
		StringBuffer sql=new StringBuffer("insert into csms_electronic_purse");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public void delete(Long id) {
		String sql="delete from csms_electronic_purse where id=?";
		super.delete(sql,id);
	}

	public void update(ElectronicPurse electronicPurse) {
		/*StringBuffer sql=new StringBuffer("update csms_electronic_purse set ");
		sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("nameAndValue")+" where id="+electronicPurse.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(ElectronicPurse.class,electronicPurse);
		StringBuffer sql=new StringBuffer("update csms_electronic_purse set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),electronicPurse.getId());
	}

	public ElectronicPurse findById(Long id) {
		String sql = "select * from csms_electronic_purse where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		ElectronicPurse electronicPurse = null;
		if (!list.isEmpty()) {
			electronicPurse = new ElectronicPurse();
			this.convert2Bean(list.get(0), electronicPurse);
		}

		return electronicPurse;
	}

	public List<Map<String, Object>> findAll(ElectronicPurse electronicPurse) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_electronic_purse where 1=1 ");
		if (electronicPurse != null) {
			//sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ElectronicPurse.class,electronicPurse);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		}else{
			list = queryList(sql.toString());
		}
		
		return list;
	}

	public Pager findByPage(Pager pager,ElectronicPurse electronicPurse) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_electronic_purse t where 1=1");
		if (electronicPurse != null) {
			//sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ElectronicPurse.class,electronicPurse);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		}else{
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,null);
		}
		
	}

	public ElectronicPurse find(ElectronicPurse electronicPurse) {
		ElectronicPurse temp = null;
		if (electronicPurse != null) {
			StringBuffer sql = new StringBuffer("select * from csms_electronic_purse where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(ElectronicPurse.class,electronicPurse).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ElectronicPurse.class,electronicPurse);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new ElectronicPurse();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public ElectronicPurse findByPrepaidID(Long prepaidID) {
		String sql = "select * from csms_electronic_purse where prepaidC=?";
		List<Map<String, Object>> list = queryList(sql,prepaidID);
		ElectronicPurse electronicPurse = null;
		if (!list.isEmpty()) {
			electronicPurse = new ElectronicPurse();
			this.convert2Bean(list.get(0), electronicPurse);
		}

		return electronicPurse;
	}
	
	public void saveHis(ElectronicPurseHis electronicPurseHis) {
		StringBuffer sql=new StringBuffer("insert into CSMS_electronic_Purse_His(");
		sql.append(FieldUtil.getFieldMap(ElectronicPurseHis.class,electronicPurseHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ElectronicPurseHis.class,electronicPurseHis).get("valueStr")+")");
		save(sql.toString());
	}

	public List<DbasCardFlow> findTransMoneyList(String currentDate) {
		String sql = "SELECT * FROM CSMS_DBASCARDFLOW WHERE to_char(EXPIRETIME,'YYYYMMDDHH24MISS')<=? AND BalanceTime IS NULL ORDER BY applytime desc";
		List<DbasCardFlow> dbasCardFlowList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),currentDate);
		if(list.size()>0){
			dbasCardFlowList = new ArrayList<DbasCardFlow>();
			for(Map<String, Object> map:list){
				DbasCardFlow fee = new DbasCardFlow();
				fee = (DbasCardFlow) this.convert2Bean(
						(Map<String, Object>) map, new DbasCardFlow());
				dbasCardFlowList.add(fee);

			}
			
		}
		return dbasCardFlowList;
	}

	public CardBalanceData findCardBalanceByCardNo(String cardNo,String expireTime) {
		String sql = "SELECT * FROM TB_CARDBALANCEDATA_RECV WHERE CARDCODE = ? AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>=?";
		
		List<Map<String, Object>> list = queryList(sql,cardNo,expireTime);
		CardBalanceData cardBalanceData = null;
		if (!list.isEmpty()) {
			cardBalanceData = new CardBalanceData();
			this.convert2Bean(list.get(0), cardBalanceData);
		}

		return cardBalanceData;
	}

	public void updateCardMoney(Long dbasCardFlowId, BigDecimal balance,Date balanceDate,Date updateTime,Integer expireFlag) {
		String sql = "UPDATE CSMS_DBASCARDFLOW SET CARDAMT = "+balance+",BalanceTime = to_date('"+DateUtil.formatDate(balanceDate, "yyyy-MM-dd hh:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'),"
				+ "UPDATETIME = to_date('"+DateUtil.formatDate(updateTime, "yyyy-MM-dd hh:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'),expireFlag = "+expireFlag+" WHERE ID = "+dbasCardFlowId;
		update(sql);
	}
	
}
