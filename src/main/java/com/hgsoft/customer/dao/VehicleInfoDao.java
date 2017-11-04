package com.hgsoft.customer.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleImp;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class VehicleInfoDao extends BaseDao {
	public VehicleInfo findByVehicleImp(VehicleImp vehicleImp) {
		StringBuffer sql = new StringBuffer("select * from csms_vehicle_info where 1=1");
		SqlParamer param = new SqlParamer();
		if (vehicleImp != null) {
			String vehiclePlate = vehicleImp.getVehiclePlate();
			if (StringUtil.isNotBlank(vehiclePlate)) {
				param.eq("vehiclePlate", vehiclePlate);
			}
			String vehicleColor = vehicleImp.getVehicleColor();
			if (StringUtil.isNotBlank(vehicleColor)) {
				param.eq("vehicleColor", vehicleColor);
			}
		}

		sql.append(param.getParam());
		List<Map<String, Object>> list = queryList(sql.toString(), param.getList().toArray());

		VehicleInfo vehicleInfo = null;
		if (list.size() > 0) {
			vehicleInfo = new VehicleInfo();
			convert2Bean(list.get(0), vehicleInfo);
		}
		return vehicleInfo;
	}

	public VehicleInfo loadByPlateAndColor(String plate, String color) {
		String sql = "select v.* from CSMS_Vehicle_Info v where vehiclePlate=? and vehicleColor=? order by v.ID fetch first 1 rows only";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, plate, color);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}


	public Pager findByCustomer(Pager pager, Customer customer) {
		String sql = "select v.*,ROWNUM as num from CSMS_Vehicle_Info v where v.customerId=? order by v.ID";
		return super.findByPages(sql.toString(), pager, new Object[]{customer.getId()});
	}

	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color) {
		StringBuffer sql = new StringBuffer("select v.ID,v.customerID,v.vehiclePlate,v.vehicleColor,v.vehicleUserType,v.UsingNature,v.IdentificationCode,"
				+ "v.VehicleType,v.vehicleWheels,v.vehicleAxles,v.vehicleWheelBases,v.vehicleWeightLimits,v.vehicleSpecificInformation,v.vehicleEngineNo,"
				+ "v.vehicleWidth,v.vehicleLong,v.vehicleHeight,v.vehicleHeadH,v.NSCvehicletype,v.owner,v.Model,v.OperID,v.PlaceID,v.createTime,v.HisSeqID,"
				+ "v.IsWriteOBU,v.IsWriteCard,pre.cardno as precardno,acc.cardno as acccardno,tag.tagno,ROWNUM as num ");
		sql.append(" from CSMS_Vehicle_Info v left join csms_carobucard_info cbd on v.id=cbd.vehicleid left join csms_tag_info tag on tag.id=cbd.tagid ");
		sql.append(" left join csms_accountc_info acc on acc.id=cbd.accountcid left join csms_prepaidc pre on pre.id=cbd.prepaidcid where v.customerid=" + customer.getId());
		SqlParamer sqlPar = new SqlParamer();
		if (StringUtil.isNotBlank(plate)) {
			//sql.append(" and v.vehicleplate='"+plate+"'");
			sqlPar.eq("v.vehicleplate", plate);
		}
		if (StringUtil.isNotBlank(color)) {
			sqlPar.eq("v.vehiclecolor", color);
			//sql.append(" and v.vehiclecolor='"+color+"'");
		}
		sql = sql.append(sqlPar.getParam());
		sql.append(" order by v.ID desc");
		return super.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}

	public Pager findByPlateAndColorForAMMS(Pager pager, Customer customer, String plate, String color, String bankCode) {
		String sql = " select allCard.* from ("
				+ " select (case when coc.prepaidcId is null and coc.accountcId is not null then a.cardNo"
				+ " when coc.prepaidcId is not null and coc.accountcId is null then p.cardNo"
				+ " when coc.prepaidcId is null and coc.accountcId is null then null end) allCardNo,"
				+ " v.vehiclePlate,v.vehicleColor,v.identificationCode,v.vehicleType,v.vehicleWeightLimits,v.id,t.tagNo,p.cardNo precardno,a.cardNo acccardno"
				+ " from csms_vehicle_info v"
				+ " join csms_carobucard_info coc on coc.vehicleId = v.id"
				+ " left join csms_tag_info t on coc.tagId = t.id"
				+ " left join csms_accountc_info a on coc.accountcId = a.id"
				+ " left join csms_prepaidc p on coc.prepaidcId = p.id where v.customerId=?) allCard"
				+ " left join csms_joinCardNoSection j on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
				+ " where (allCard.allCardNo  is null or j.bankno = ?) ";

		List<Object> params = new ArrayList<Object>();
		params.add(customer.getId());
		params.add(bankCode);

		if (StringUtil.isNotBlank(plate)) {
			params.add(plate);
			sql = sql + " and allCard.vehiclePlate=?";
		}
		if (StringUtil.isNotBlank(color)) {
			params.add(color);
			sql = sql + " and allCard.vehicleColor=?";
		}
		sql = sql + " order by allCard.id desc";
		return super.findByPages(sql, pager, params.toArray());
	}

	public VehicleInfo findByPlateAndColor(Customer customer, String plate, String color) {
		String sql = "select * from CSMS_Vehicle_Info v where v.vehiclePlate=? and v.vehicleColor=? and v.customerId=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, plate, color, customer.getId());
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findById(Long id) {
		String sql = "select * from CSMS_Vehicle_Info where id=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, id);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByHisId(Long hisSeqId) {
		String sql = "";
		if (hisSeqId == null) {
			sql = "select * from CSMS_Vehicle_Info where hisSeqId is null";
		} else {
			sql = "select * from CSMS_Vehicle_Info where hisSeqId=" + hisSeqId;
		}

		List<Map<String, Object>> list = queryList(sql);
		VehicleInfo vehicleInfo = null;
		if (!list.isEmpty()) {
			vehicleInfo = new VehicleInfo();
			this.convert2Bean(list.get(0), vehicleInfo);
		}
		return vehicleInfo;
	}

	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo, Long customerId) {
		String sql = "select * from csms_vehicle_info  where "
				+ " vehiclePlate=? and vehicleColor=? and customerId=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, vehicleInfo.getVehiclePlate(), vehicleInfo.getVehicleColor(), customerId);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public void save(VehicleInfo vehicleInfo) {
		vehicleInfo.setHisSeqId(-vehicleInfo.getId());
		Map map = FieldUtil.getPreFieldMap(VehicleInfo.class, vehicleInfo);
		StringBuffer sql = new StringBuffer("insert into CSMS_Vehicle_Info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql = new StringBuffer("insert into CSMS_Vehicle_Info(");
		sql.append(FieldUtil.getFieldMap(VehicleInfo.class,vehicleInfo).get("nameStrNotNull")+") values(");
		sql.append(FieldUtil.getFieldMap(VehicleInfo.class,vehicleInfo).get("valueStrNotNull")+")");
		super.save(sql.toString());*/
	}

	public void update(VehicleInfo vehicleInfo) {

		Map map = FieldUtil.getPreFieldMap(VehicleInfo.class, vehicleInfo);
		StringBuffer sql = new StringBuffer("update CSMS_Vehicle_Info set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), vehicleInfo.getId());
		/*StringBuffer sql = new StringBuffer("update CSMS_Vehicle_Info set ");
		sql.append(FieldUtil.getFieldMap(VehicleInfo.class,vehicleInfo).get("nameAndValue")+" where id="+vehicleInfo.getId());
		super.update(sql.toString());*/
	}

	public void updateNotNull(VehicleInfo vehicleInfo) {
		Map map = FieldUtil.getPreFieldMap(VehicleInfo.class, vehicleInfo);
		StringBuffer sql = new StringBuffer("update CSMS_Vehicle_Info set ");
		sql.append(map.get("updateNameStrNotNull") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), vehicleInfo.getId());
	}

	public void delete(Long id) {
		String sql = "delete from CSMS_Vehicle_Info where id=?";
		super.delete(sql, id);
	}

	public VehicleInfo find(VehicleInfo vehicleInfo) {
		if (vehicleInfo == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(VehicleInfo.class, vehicleInfo);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("updateNotNull查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_Vehicle_Info where 1=1 ");
		sql.append(condition);
		sql.append(" order by id");
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql.toString(), VehicleInfo.class, ((List) map.get("paramNotNull")).toArray());
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public int getVehicleInfoComplete(Long vehicleInfoId) {
		StringBuffer sql = new StringBuffer("select count(id)\n" +
				"  from (select id\n" +
				"          from CSMS_Vehicle_Info vi\n" +
				"         where vi.vehiclePlate is not null\n" +
				"           and vi.vehicleColor is not null\n" +
				"           and vi.owner is not null\n" +
				"           and vi.vehicleUserType is not null\n" +
				"           and vi.Model is not null\n" +
				"           and vi.vehicleLong is not null\n" +
				"           and vi.UsingNature is not null\n" +
				"           and vi.vehicleWidth is not null\n" +
				"           and vi.vehicleHeight is not null\n" +
				"           and vi.vehicleEngineNo is not null\n" +
				"           and vi.vehicleWeightLimits is not null\n" +
				"           and vi.vehicleSpecificInformation is not null\n" +
				"           and vi.NSCvehicletype is not null\n" +
				"           and vi.IdentificationCode is not null\n" +
				"           and vi.VehicleType = 2\n" +
				"           and vi.vehicleAxles is not null\n" +
				"           and vi.vehicleWheels is not null\n" +
				"        union all\n" +
				"        select id\n" +
				"          from CSMS_Vehicle_Info vi\n" +
				"         where vi.vehiclePlate is not null\n" +
				"           and vi.vehicleColor is not null\n" +
				"           and vi.owner is not null\n" +
				"           and vi.vehicleUserType is not null\n" +
				"           and vi.Model is not null\n" +
				"           and vi.vehicleLong is not null\n" +
				"           and vi.UsingNature is not null\n" +
				"           and vi.vehicleWidth is not null\n" +
				"           and vi.vehicleHeight is not null\n" +
				"           and vi.vehicleEngineNo is not null\n" +
				"           and vi.vehicleWeightLimits is not null\n" +
				"           and vi.vehicleSpecificInformation is not null\n" +
				"           and vi.NSCvehicletype is not null\n" +
				"           and vi.IdentificationCode is not null\n" +
				"           and vi.VehicleType = 0\n" +
				"        union all\n" +
				"        select id\n" +
				"          from CSMS_Vehicle_Info vi\n" +
				"         where vi.vehiclePlate is not null\n" +
				"           and vi.vehicleColor is not null\n" +
				"           and vi.owner is not null\n" +
				"           and vi.vehicleUserType is not null\n" +
				"           and vi.Model is not null\n" +
				"           and vi.vehicleLong is not null\n" +
				"           and vi.UsingNature is not null\n" +
				"           and vi.vehicleWidth is not null\n" +
				"           and vi.vehicleHeight is not null\n" +
				"           and vi.vehicleEngineNo is not null\n" +
				"           and vi.vehicleWeightLimits is not null\n" +
				"           and vi.vehicleSpecificInformation is not null\n" +
				"           and vi.NSCvehicletype is not null\n" +
				"           and vi.IdentificationCode is not null\n" +
				"           and vi.VehicleType = 5) where id=? ");
		return queryCount(sql.toString(), vehicleInfoId);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public VehicleInfo findByPlateAndColorAncIdentCode(String vehiclePlate, String vehicleColor, String identificationCode) {
		String sql = "select v.* from CSMS_Vehicle_Info v join csms_customer c on v.customerid=c.id  where v.vehiclePlate=? and v.vehicleColor = ? and  substr(IdentificationCode, -6) = ?  and c.systemtype!=2 ";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, vehiclePlate, vehicleColor, identificationCode);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByPrepaidCNo(String cardNo) {
		String sql = "select distinct vehicle.* from csms_vehicle_info vehicle,csms_prepaidc prepaidc,csms_customer customer"
				+ " where vehicle.customerid = customer.id and customer.id = prepaidc.customerid and prepaidc.cardno=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, cardNo);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByPrepaidCardNo(String cardNo) {
		String sql = "select v.* from csms_prepaidc p join csms_carobucard_info c on p.id=c.prepaidcid join csms_vehicle_info v on v.id=c.vehicleid "
				+ " where p.cardno=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, cardNo);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByAccountCNo(String cardNo) {
		String sql = "select v.* from csms_accountc_info a join csms_carobucard_info c on a.id=c.accountcid join csms_vehicle_info v on v.id=c.vehicleid "
				+ " where a.cardno=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, cardNo);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByTagNo(String tagNo) {
		String sql = "select v.* from csms_tag_info t join csms_carobucard_info c on t.id=c.tagid join csms_vehicle_info v on v.id=c.vehicleid "
				+ " where t.tagno=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, tagNo);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public VehicleInfo findByCustomerId(Long id) {
		String sql = "select * from CSMS_Vehicle_Info where customerId=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, id);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public List<Map<String, Object>> getAllVehByCusId(Long customerId) {
		String sql = "select " + FieldUtil.getFieldMap(VehicleInfo.class, new VehicleInfo()).get("nameStr") + " , " + FieldUtil.getFieldMap(CarObuCardInfo.class, new CarObuCardInfo()).get("nameStr") + " from (select " + FieldUtil.getFieldMap(VehicleInfo.class, new VehicleInfo()).get("nameStr") + " from csms_vehicle_info where customerid=" + customerId + ") v  left join csms_carobucard_info c on v.id=c.vehicleid  ";
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}

	public List<Map<String, Object>> getAllVehByCusIdForAMMS(Long customerId) {
		String sql = "select * from csms_vehicle_info v left join csms_carobucard_info c on v.id = c.vehicleid where v.customerid = ?  and (vehicleplate,vehiclecolor) not in(select LICENSE,LICENSECOLOR from TB_GBISSUEUSERLIST)";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}

	public List<Map<String, Object>> findListNotBindByCID(Long id) {
	/*	String sql = "select * from CSMS_Vehicle_Info v left join csms_carobucard_info caro on caro.vehicleid = v.id "
				+ " where caro.tagid is null and customerId="+id;*/
		String sql = "select v.*,p.cardno as pcardno ,a.cardno as acardno from (select * from CSMS_Vehicle_Info  where customerid=" + id + ") v left join csms_carobucard_info caro on caro.vehicleid = v.id "
				+ "left join CSMS_AccountC_info  a on a.id=caro.accountcid left join csms_prepaidc p on p.id=caro.prepaidcid where caro.tagid is null";
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}

	public List<Map<String, Object>> findListNotBindByCIDForAMMS(Long id) {
		String sql = "select v.*,p.cardno as pcardno ,a.cardno as acardno from (select * from CSMS_Vehicle_Info  where customerid=" + id + ") v left join csms_carobucard_info caro on caro.vehicleid = v.id "
				+ "left join CSMS_AccountC_info  a on a.id=caro.accountcid left join csms_prepaidc p on p.id=caro.prepaidcid where caro.tagid is null and (vehicleplate,vehiclecolor) not in(select LICENSE,LICENSECOLOR from TB_GBISSUEUSERLIST)";
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}

	public List<Map<String, Object>> findListNotBindForMacao(MacaoCardCustomer macaoCardCustomer) {
		StringBuffer sql = new StringBuffer(" select vi.*, p.cardno as pcardno, a.cardno as acardno"
				+ " from csms_vehicle_info vi"
				+ " left join csms_carobucard_info coc"
				+ " on coc.vehicleid = vi.id"
				+ " left join CSMS_AccountC_info a"
				+ " on a.id = coc.accountcid"
				+ " left join csms_prepaidc p"
				+ " on p.id = coc.prepaidcid"
				+ " join csms_cardholder_info ci"
				+ " on ci.typeid = vi.id"
				+ " join csms_macao_bankaccount mb"
				+ " on mb.id = ci.macaobankaccountid"
				+ " join csms_macao_card_customer mcc"
				+ " on mcc.id = mb.mainid"
				+ " where coc.tagid is null and ci.type='1' and mcc.id=? ");
		return queryList(sql.toString(), macaoCardCustomer.getId());
	}

	/**
	 * 没绑定的车牌
	 *
	 * @param customerId
	 * @return
	 * @author yuhang
	 */
	public List<Map<String, Object>> listNotBind(Long customerId) {
		String sql = "select v.* from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id)  where caro.prepaidcid is null and caro.accountcid is null and v.customerid = ?";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}


	//TODO 电子标签发行要只能发行客车不能发行货车，这里可以做判断。


	/**
	 * 没绑定的车牌
	 *
	 * @param customerId
	 * @return
	 * @author yuhang
	 */
	public Pager listNotBindByPager(Pager pager, String vehiclePlate, String vehicleColor, Long customerId) {
		String sql = "select v.* from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id)  where caro.prepaidcid is null and caro.accountcid is null and v.customerid = " + customerId;
//		List<Map<String, Object>> list = queryList(sql);
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(vehicleColor)) {
			params.eq("vehicleColor", vehicleColor);
		}
		if (StringUtil.isNotBlank(vehiclePlate)) {
			params.eq("vehiclePlate", vehiclePlate);
		}
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		pager = findByPages(sql, pager, Objects);
		return pager;
	}

	/**
	 * 没绑定的车牌
	 *
	 * @param customerId
	 * @return
	 * @author yuhang
	 */
	public Pager listNotBindByPagerForTag(Pager pager, String vehiclePlate, String vehicleColor, Long customerId) {
		String sql = "select v.* from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id)  where caro.prepaidcid is null and caro.accountcid is null and caro.tagid is null and v.VehicleType='2' and v.customerid = " + customerId;
//		List<Map<String, Object>> list = queryList(sql);
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(vehicleColor)) {
			params.eq("vehicleColor", vehicleColor);
		}
		if (StringUtil.isNotBlank(vehiclePlate)) {
			params.eq("vehiclePlate", vehiclePlate);
		}
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		pager = findByPages(sql, pager, Objects);
		return pager;
	}


	/**
	 * 没绑定的车牌和标签号
	 *
	 * @param customerId
	 * @return
	 * @author guanshaofeng
	 */
	public List<Map<String, Object>> findVehicleTagNo(Long customerId) {
		String sql = "select v.*,t.tagNo from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id) left join csms_tag_info t on t.id=caro.tagid where caro.prepaidcid is null and caro.accountcid is null and v.customerid = ?";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}

	public List<Map<String, Object>> listNotBind(String cardNo) {
		String sql = " select vi.*"
				+ " from csms_vehicle_info vi"
				+ " join csms_carobucard_info ci"
				+ " on vi.id = ci.vehicleid"
				+ " where vi.id in (select ci1.typeid"
				+ " from csms_cardholder_info ci1"
				+ " where ci1.type = '1'"
				+ " and ci1.macaobankaccountid ="
				+ " (select ci2.macaobankaccountid"
				+ " from csms_cardholder_info ci2"
				+ " join csms_accountc_info ai"
				+ " on ai.id = ci2.typeid"
				+ " where ci2.type = '2'"
				+ " and ai.cardno = ?))"
				+ " and ci.accountcid is null and ci.prepaidcid is null";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		return list;
	}

	public List<Map<String, Object>> listBind(Long customerId) {
		String sql = "select v.*,t.tagNo from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id) where ((caro.prepaidcid is not null) or (caro.accountcid is not null)) and v.customerid =?";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}

	public List<Map<String, Object>> findBindCardVehicleTagNo(Long customerId) {
		String sql = "select v.*,t.tagNo from csms_vehicle_info v left join csms_carobucard_info caro on (caro.vehicleid=v.id) left join csms_tag_info t on t.id=caro.tagid where ((caro.prepaidcid is not null) or (caro.accountcid is not null)) and v.customerid =?";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}

	public List<Map<String, Object>> listBindForMacao(Long customerId) {
		String sql = "select vi.* from csms_vehicle_info vi join csms_carobucard_info coc on vi.id=coc.vehicleid join csms_cardholder_info ci on vi.id=ci.typeid join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid JOIN csms_accountc_info ai ON ai.id=coc.accountcid where ((coc.prepaidcid is not null) or (coc.accountcid is not null)) and mcc.id=? and ai.state='0'";
		List<Map<String, Object>> list = queryList(sql, customerId);
		return list;
	}

	public VehicleInfo findByTagId(Long id) {
		String sql = "select vi.* from csms_vehicle_info vi join csms_carobucard_info ci on vi.id=ci.vehicleid where ci.tagid=?";
		List<VehicleInfo> vehicleInfos = super.queryObjectList(sql, VehicleInfo.class, id);
		if (vehicleInfos == null || vehicleInfos.isEmpty()) {
			return null;
		}
		return vehicleInfos.get(0);
	}

	public boolean ifHasBindCard(String vehiclePlate, String vehicleColor) {
		String perOrAccSQL = "select ci.prepaidcid,ci.AccountCID from " +
				"CSMS_Vehicle_Info vi join CSMS_CarObuCard_info ci on vi.id" +
				"=ci.VehicleID where vi.vehiclePlate=? and vi.vehicleColor=? and rownum=1 ";
		List<Map<String, Object>> perOrAccList = this.queryList(perOrAccSQL,
				vehiclePlate, vehicleColor);
		return perOrAccList != null && !perOrAccList.isEmpty();
	}

	/**
	 * 根据车牌号码和颜色找车
	 *
	 * @param plate
	 * @param color
	 * @return
	 */
	public VehicleInfo findVehicleInfoByPlateAndColor(String plate, String color) {
		String sql = "select " + FieldUtil.getFieldMap(VehicleInfo.class, new VehicleInfo()).get("nameStr") + " from CSMS_Vehicle_Info where vehiclePlate = ? and vehicleColor = ?";
		List<VehicleInfo> list = super.queryObjectList(sql, VehicleInfo.class, plate, color);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return new VehicleInfo();
	}

	/**
	 * 根据客户ID找车牌号码和车牌颜色
	 *
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> findPlateAndColorByCustomerId(Long customerId) {
		return queryList("select vehiclePlate,vehicleColor from csms_vehicle_info where customerId = ?", customerId);
	}

	/**
	 * 查询车辆信息（营改增->香港联营）
	 *
	 * @param pager
	 * 		分页
	 * @param customer
	 * 		客户
	 * @param vehicleInfo
	 * 		车辆信息
	 * @param cardHolder
	 * 		持卡人
	 * @return 分页信息
	 * @author wangjinhao
	 */
	public Pager findByVehicleAndHolderACMS(Pager pager, Customer customer, VehicleInfo vehicleInfo,
	                                        CardHolder cardHolder) {
		StringBuffer sql = new StringBuffer("select v.ID,v.customerID,v.vehiclePlate,v.vehicleColor,v.vehicleUserType,v.UsingNature,v.IdentificationCode,"
				+ "v.VehicleType,v.vehicleWheels,v.vehicleAxles,v.vehicleWheelBases,v.vehicleWeightLimits,v.vehicleSpecificInformation,v.vehicleEngineNo,"
				+ "v.vehicleWidth,v.vehicleLong,v.vehicleHeight,v.vehicleHeadH,v.NSCvehicletype,v.owner,v.Model,v.OperID,v.PlaceID,v.createTime,v.HisSeqID,"
				+ "v.IsWriteOBU,v.IsWriteCard,pre.cardno as precardno,acc.cardno as acccardno,tag" +
				".tagno,ROWNUM as num, ");
		//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171101
		sql.append(" hc.name, hc.idType, hc.idCode, hc.secondNo, hc.secondName ");
		//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171101

		sql.append(" from CSMS_Vehicle_Info v left join csms_carobucard_info cbd on v.id=cbd.vehicleid left join csms_tag_info tag on tag.id=cbd.tagid ");

		//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171101
		sql.append(" left join csms_hk_cardholder hc on hc.userNo = v.userNo ");
		//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171101

		sql.append(" left join csms_accountc_info acc on acc.id=cbd.accountcid left join csms_prepaidc pre on pre.id=cbd.prepaidcid where v.customerid=" + customer.getId());


		SqlParamer sqlPar = new SqlParamer();
		//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171101
		if (null != vehicleInfo) {
			if (StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())) {
				sqlPar.eq("v.vehicleplate", vehicleInfo.getVehiclePlate());
			}
			if (StringUtil.isNotBlank(vehicleInfo.getVehicleColor())) {
				sqlPar.eq("v.vehiclecolor", vehicleInfo.getVehicleColor());
			}
		}

		if (null != cardHolder) {
			if (StringUtils.isNotBlank(cardHolder.getName())) {
				sqlPar.like("hc.name", cardHolder.getName());
			}
			if (StringUtils.isNotBlank(cardHolder.getIdType())) {
				sqlPar.eq("hc.idType", cardHolder.getIdType());
			}
			if (StringUtils.isNotBlank(cardHolder.getIdCode())) {
				sqlPar.eq("hc.idCode", cardHolder.getIdCode());
			}
		}
		//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171101

		sql = sql.append(sqlPar.getParam());
		sql.append(" order by v.ID desc");
		return super.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}

}
