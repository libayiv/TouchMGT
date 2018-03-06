package com.security.modules.job.dao;

import java.util.Map;

import com.security.modules.job.entity.ScheduleJobEntity;
import com.security.modules.sys.dao.BaseDao;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:29:57
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
