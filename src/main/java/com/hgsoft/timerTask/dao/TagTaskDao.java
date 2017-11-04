package com.hgsoft.timerTask.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagInfo;

/**
 * Title:TagTask
 * Description:电子标签定时任务
 * @author guokunpeng
 * @Date 2016-10-21 上午11:35:46
 */
@Component
public class TagTaskDao extends BaseDao {
	
	public List<TagInfo> findTagInfoByWritebackFlag() { 
		   String sql = "select * from csms_tag_info where WRITEBACKFLAG='0' or WRITEBACKFLAG=''";
		   List<Map<String, Object>> list = this.queryList(sql);
		  
		   List<TagInfo> tagInfoList = null;
			if (!list.isEmpty()) {
				tagInfoList = new ArrayList<TagInfo>();
				for (Map<String,Object> map : list) {
					TagInfo tagInfo = new TagInfo();
					this.convert2Bean(map, tagInfo);
					tagInfoList.add(tagInfo);
				}
			}

		   return tagInfoList;
		}
}
