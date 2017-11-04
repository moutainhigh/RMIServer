package com.hgsoft.customer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class MaterialDao extends BaseDao{
	public List<Material> findMateria(Material material){
		List<Material> materials = new ArrayList<Material>();
			SqlParamer sqlPar=new SqlParamer();
			StringBuffer sql = new StringBuffer("select ID,CustomerID,VehicleID,Type,PicAddr,UpdateTime,Up_Date,Remark,CODE,bussinessId from CSMS_Material where 1=1 ");
			if(material.getCustomerID() != null){
				//sql.append(" and CustomerID = '"+material.getCustomerID()+"'");
				sqlPar.eq("CustomerID", material.getCustomerID());
			}
			if(material.getVehicleID() != null){
				//sql.append(" and CustomerID = '"+material.getCustomerID()+"'");
				sqlPar.eq("VehicleID", material.getVehicleID());
			}
			if(material.getBussinessId() != null){
				sqlPar.eq("bussinessId", material.getBussinessId());
			}
			if(StringUtil.isNotBlank(material.getType())){
				//sql.append(" and Type = '"+material.getType()+"'");
				sqlPar.eq("Type",material.getType());
			}
			if(StringUtil.isNotBlank(material.getCode())){
				//sql.append(" and code = '"+material.getCode()+"'");
				sqlPar.eq("code", material.getCode());
			}
			sql=sql.append(sqlPar.getParam());
			List<Map<String, Object>> list = queryList(sql.toString(),sqlPar.getList().toArray());
			//封装List<Material>对象
			if(!list.isEmpty()){
				for(Map<String, Object> m:list){
					Material material1 = new Material();
						this.convert2Bean(m, material1);

					materials.add(material1);
				}
			}
		
		return materials;
	}
	
	public void deleteMateria(Long materiaId){
	
		String sql="delete from CSMS_Material where id=?";
		super.delete(sql,materiaId);
		
	}
	
	public void updateMateria(Material material){
		Map map = FieldUtil.getPreFieldMap(Material.class,material);
		StringBuffer sql=new StringBuffer("update CSMS_Material set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),material.getId());
		
		/*StringBuffer sql=new StringBuffer("update CSMS_Material set ");
		sql.append(FieldUtil.getFieldMap(Material.class,material).get("nameAndValue")+" where id="+material.getId());
		update(sql.toString());*/
	}
	
	public void saveMateria(Material material){
		Map map = FieldUtil.getPreFieldMap(Material.class,material);
		StringBuffer sql=new StringBuffer("insert into CSMS_Material");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql=new StringBuffer("insert into CSMS_Material(");
		sql.append(FieldUtil.getFieldMap(Material.class,material).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Material.class,material).get("valueStr")+")");
		save(sql.toString());*/
	}
	
	public Material findById(Long materialID){
		Material material = null;
		String sql = "select * from CSMS_Material where id=?";
		List<Map<String, Object>> list = queryList(sql, materialID);
		if(!list.isEmpty()&&list!=null){
			material = new Material();
			this.convert2Bean(list.get(0), material);
		}
		return material;
	}
	
	/**
	 * 根据客户id和图片类型返回最新的Code
	 * @param customerId
	 * @param type
	 * @return String
	 */
	public String getLastCode(Long customerId,String type){
		Material material = null;
		String sql = "select ID,CustomerID,VehicleID,Type,PicAddr,UpdateTime,Up_Date,Remark,CODE from CSMS_Material where customerid=? and type=? order by id desc";
		List<Map<String, Object>> list = queryList(sql.toString(),customerId,type);
		if(!list.isEmpty()&&list!=null){
			material = new Material();
			try {
				this.convert2Bean(list.get(0), material);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
		if(material != null && StringUtil.isNotBlank(material.getCode())){
			//如果能找到最近一条该客户下的该图片类型的图片资料，就返回其code编码
			return material.getCode();
		}else{
			//否则从0开始算
			return "0";
		}
	}
	
	
	public List<Material> findPreCardRefund(Long refundId){
		List<Material> materials = new ArrayList<Material>();
		
		String sql = "select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where type='22' and bussinessid=? "
				+ "      union all "
				+ "   select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where type='23' and bussinessid=?  ";
		List<Map<String, Object>> list = queryList(sql,refundId,refundId);
		//封装List<Material>对象
		if(!list.isEmpty()){
			for(Map<String, Object> m:list){
				Material material1 = new Material();
					this.convert2Bean(m, material1);

				materials.add(material1);
			}
		}
		return materials;
	}
	/**
	 * 本方法主要用于查询退款相关的图片类型
	 * 根据业务id和图片类型查找图片资料
	 * @param bussinessid 
	 * @param type 当退款类型为储值卡注销退款，type=-1传进来特殊判断
	 * @return List<Material>
	 */
	public List<Material> findRefundMaterial(Long bussinessid, String type){
		List<Material> materials = new ArrayList<Material>();
		List<Map<String, Object>> list = null;
		if("-1".equals(type)){
			//储值卡注销类型的图片，有两种
			String sql = "select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type='22' "
					+ "     union all "
					+ "   select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type='23' ";
			
			list = queryList(sql,bussinessid,bussinessid);
		}else{
			String sql = "select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type=? ";
			list = queryList(sql,bussinessid,type);
		}
		
		//封装List<Material>对象
		if(!list.isEmpty()){
			for(Map<String, Object> m:list){
				Material material1 = new Material();
					this.convert2Bean(m, material1);

				materials.add(material1);
			}
		}
		return materials;
	}
	
	/**
	 * 本方法主要用于查询退款相关的图片类型
	 * 根据业务id和图片类型查找图片资料
	 * @param bussinessid 
	 * @param type 当退款类型为储值卡注销退款，type=-1传进来特殊判断
	 * @return List<Material>
	 */
	public List<Material> findRefundMaterialForAMMS(Long bussinessid){
		List<Material> materials = new ArrayList<Material>();
		List<Map<String, Object>> list = null;
//		if("-1".equals(type)){
			//储值卡注销类型的图片，有两种
		String sql = "select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type='22' "
				+ "     union all "
				+ "   select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type='23' ";
		
		list = queryList(sql,bussinessid,bussinessid);
//		}else{
//			String sql = "select "+FieldUtil.getFieldMap(Material.class,new Material()).get("nameStr")+" from CSMS_Material where bussinessid=? and type=? ";
//			list = queryList(sql,bussinessid,type);
//		}
		
		//封装List<Material>对象
		if(!list.isEmpty()){
			for(Map<String, Object> m:list){
				Material material = new Material();
				this.convert2Bean(m, material);
				
				materials.add(material);
			}
		}
		return materials;
	}
}
