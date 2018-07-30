package com.security.modules.net.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.net.dao.ProductDao;
import com.security.modules.net.entity.ProductInfo;
import com.security.modules.net.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	public ProductInfo queryEntity(String pid) throws Exception {
		return productDao.queryObject(pid);
	}

	@Override
	public List<ProductInfo> queryList(Map<String, Object> map) throws Exception {
		return productDao.queryList(map);
	}
	
	@Override
	public List<ProductInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return productDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return productDao.queryTotal(map);
	}

	@Override
	public void save(ProductInfo prod) throws Exception {
		productDao.save(prod);
	}

	@Override
	public void update(ProductInfo prod) throws Exception {
		productDao.update(prod);
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
			productDao.updateStatus(params);
		}*/
	}

	

}
