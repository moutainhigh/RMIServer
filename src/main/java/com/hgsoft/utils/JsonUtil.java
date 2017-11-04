package com.hgsoft.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	
	public static List<Map> fromListToMap(String json){
		JSONObject object = JSONObject.fromObject(json);
		Object object2 = object.get("list");
		JSONArray array = JSONArray.fromObject(object2);
		List<Map> list = JSON.parseArray(array.toString(),Map.class);
		return list;
	}
}
