package com.security.modules.net.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.net.dao.GameDao;
import com.security.modules.net.entity.GameInfo;
import com.security.modules.net.service.GameService;



@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameDao gameDao;

	@Override
	public GameInfo queryEntity(String pid) throws Exception {
		return gameDao.queryObject(pid);
	}

	@Override
	public List<GameInfo> queryList(Map<String, Object> map) throws Exception {
		return gameDao.queryList(map);
	}
	
	@Override
	public List<GameInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return gameDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return gameDao.queryTotal(map);
	}

	@Override
	public void save(GameInfo game) throws Exception {
		gameDao.save(game);
	}

	@Override
	public void update(GameInfo game) throws Exception {
		gameDao.update(game);
	}



	@Override
	public void updateStatus(String pids, String status) throws Exception {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		List<String> list = new ArrayList<String>();
		String[] split = pids.split(",");
		for(String pid : split){
			if(pid != null && !"".equals(pid.trim())){
				list.add(pid);
			}
		}
		if(list.size() > 0){
			params.put("list", list);
			gameDao.updateStatus(params);
		}*/
	}

	

}
