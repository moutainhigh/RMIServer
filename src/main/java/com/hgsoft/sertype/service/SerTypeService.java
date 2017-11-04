package com.hgsoft.sertype.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.sertype.dao.SerTypeDao;
import com.hgsoft.sertype.serviceInterface.ISerTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

/**
 * Created by wiki on 2017/6/14.
 */
@Service
public class SerTypeService implements ISerTypeService {

    private static Logger logger = LoggerFactory.getLogger(SerTypeService.class.getName());

    @Resource
    private SerTypeDao serTypeDao;

    @Override
    public List<Map<String, Object>> findAll() {
        try {
            return this.serTypeDao.findAll();
        } catch (Exception e) {
            logger.error("查找客服类型记录",e);
            e.printStackTrace();
            throw new ApplicationException();
        }
    }

    @Override
    public List<Map<String, Object>> findAllByIn() {
        try {
            return this.serTypeDao.findAllByIn();
        } catch (Exception e) {
            logger.error("查找部门客服类型记录",e);
            e.printStackTrace();
            throw new ApplicationException();
        }
    }

	@Override
	public List<Map<String, Object>> findProductTypes() {
		return this.serTypeDao.findProductTypes();
	}

	@Override
	public List<Map<String, Object>> findProductName() {
		return this.serTypeDao.findProductName();
	}

	@Override
	public List<Map<String, Object>> findSupplier() {
		return this.serTypeDao.findSupplier();
	}

	@Override
	public List<Map<String, Object>> findProductNameFind(String type) {
		return this.serTypeDao.findProductNameFind(type);
	}

    @Override
    public List<Map<String,Object>> findProductTypeAndInfo() {
        List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> productTypes = this.serTypeDao.findProductType();
        for(Map<String,Object> temp1:productTypes){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("oneLevel",temp1);
            List<Map<String, Object>> productInfos = this.serTypeDao.findProductinfoByTypeId(temp1.get("id").toString());
            map.put("twoLevel",productInfos);
            result.add(map);
        }
        return result;
    }

	@Override
	public List<Map<String, Object>> findAllStockPlaceList() {
		return this.serTypeDao.findAllStockPlaceList();
	}

	@Override
	public List<Map<String, Object>> findAllProductTypeList() {
		return this.serTypeDao.findAllProductTypeList();
	}

	@Override
	public List<Map<String, Object>> findAllProductInfoList() {
		return this.serTypeDao.findAllProductInfoList();
	}

	@Override
	public List<Map<String, Object>> findAllByState(String state) {
		return this.serTypeDao.findAllByState(state);
	}

	@Override
	public List<Map<String, Object>> findAllByPlacenoCode(String placeno) {
		return this.serTypeDao.findAllByPlacenoCode(placeno);
	}

	@Override
	public Map<String, Object> findPointById(String id) {
		return this.serTypeDao.findPointById(id);
	}

	@Override
	public List<Map<String, Object>> findCusPointTreeByIdTwo() {
		return this.serTypeDao.findCusPointTreeByIdTwo();
	}

	@Override
	public List<Map<String, Object>> findSysAdminListByPointID(String string) {
		return this.serTypeDao.findSysAdminListByPointID(string);
	}

	@Override
	public List<Map<String, Object>> findProductNameFindByID(String type) {
		return this.serTypeDao.findProductNameFindByID(type);
	}

	@Override
	public List<Map<String, Object>> findStockNameList(String string) {
		return this.serTypeDao.findStockNameList(string);
	}

	@Override
	public List<Map<String, Object>> setFindAllPointByPlaceID(String string) {
		return this.serTypeDao.setFindAllPointByPlaceID(string);
	}

	@Override
	public List<Map<String, Object>> setFindCusPointByPlaceID(String string) {
		return this.serTypeDao.setFindCusPointByPlaceID(string);
	}

	@Override
	public List<Map<String, Object>> findPointListByTypeIdAndAreaCode(
			String pointId, String type, String area) {
		return this.serTypeDao.findPointListByTypeIdAndAreaCode(pointId,type,area);
	}

	@Override
	public List<Map<String, Object>> findAgentPointList(String id, String layer) {
		return this.serTypeDao.findAgentPointList( id,layer);
	}

	@Override
	public List<Map<String, Object>> findPointByPointIDAndLayer(String layer,
			String id) {
		return	this.serTypeDao.findPointByPointIDAndLayer(layer,id);
	}

	@Override
	public List<Map<String, Object>> findSysAdminList() {
		return this.serTypeDao.findSysAdminList();
	}

	@Override
	public int findLayerByPointId(String pointId) {
		return this.serTypeDao.findLayerByPointId(pointId);
	}

	@Override
	public List<Map<String, Object>> findLessLayerByLayerAndPointID(int layer,
			String pointId) {
		return this.serTypeDao.findLessLayerByLayerAndPointID(layer,pointId);
	}

	@Override
	public Map<String, Object> findPointByPointIDAndLayerTO(String layer,
			String pointId) {
		return	this.serTypeDao.findPointByPointIDAndLayerTO(layer,pointId);
	}

	@Override
	public List<Map<String, Object>> setFindCusPointAndParentByPlaceID(
			String string) {
		return	this.serTypeDao.setFindCusPointAndParentByPlaceID(string);
	}

}
