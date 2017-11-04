package com.hgsoft.sertype.serviceInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by wiki on 2017/6/14.
 */
public interface ISerTypeService {
    /**
     * 查询所有客服类型
     * @return
     */
    public List<Map<String, Object>> findAll();
    public List<Map<String, Object>> findAllByIn();
    public List<Map<String, Object>> findProductTypes();
    public List<Map<String, Object>> findProductName();
    public List<Map<String, Object>> findSupplier();
    public List<Map<String, Object>> findProductNameFind(String type);

    /**
     * 查询产品类型和产品名称
     * @return
     */
    public List<Map<String,Object>> findProductTypeAndInfo();
    
    //盘点库存明细表用到的查询条件
    public List<Map<String, Object>> findAllStockPlaceList();
	public List<Map<String, Object>> findAllProductTypeList();
	public List<Map<String, Object>> findAllProductInfoList();
	public List<Map<String, Object>> findAllByState(String state);
	public List<Map<String, Object>> findAllByPlacenoCode(String placeno);
	public Map<String, Object> findPointById(String id); //通过网点名称id 找网点类型网点区域
	public List<Map<String, Object>> findCusPointTreeByIdTwo();
	public List<Map<String, Object>> findSysAdminListByPointID(String string);
	public List<Map<String, Object>> findProductNameFindByID(String type);
	public List<Map<String, Object>> findStockNameList(String string);
	public List<Map<String, Object>> setFindAllPointByPlaceID(String string);
	public List<Map<String, Object>> setFindCusPointByPlaceID(String string);
	public List<Map<String, Object>> findPointListByTypeIdAndAreaCode(
			String pointId, String type, String area);
	public List<Map<String, Object>> findAgentPointList(String id, String layer);
	public List<Map<String, Object>> findPointByPointIDAndLayer(String layer, String id);

	public List<Map<String, Object>> findSysAdminList();
	public int findLayerByPointId(String pointId);
	public List<Map<String, Object>> findLessLayerByLayerAndPointID(int layer,
			String pointId);
	
	public Map<String, Object> findPointByPointIDAndLayerTO(String layer,
			String pointId);
	public List<Map<String, Object>> setFindCusPointAndParentByPlaceID(
			String string);
}
