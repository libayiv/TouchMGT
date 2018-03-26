package com.security.modules.touch.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.touch.dao.BFProductDao;
import com.security.modules.touch.entity.BFProductInf;
import com.security.modules.touch.service.BFProductInfService;

@Service
public class BFProductInfServiceImpl implements BFProductInfService {

	@Autowired
	BFProductDao bfProductDao;
	
	@Override
	public List<BFProductInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) {
		return bfProductDao.queryList(map, rowBounds);
	}

	@Override
	public int count(Map<String, Object> map) {
		return bfProductDao.queryTotal(map);
	}

	@Override
	public void update(BFProductInf product) {
		bfProductDao.update(product);
	}



	@Override
	public List<BFProductInf> queryImageList(BFProductInf product) {
		return bfProductDao.queryImageList(product);
	}

	@Override
	public int updateStar(BFProductInf product) {
		return bfProductDao.updateStar(product);
	}


	@Override
	public BFProductInf queryEditProduct(BFProductInf product) {
		return bfProductDao.queryEditProduct(product);
	}

	@Override
	public int queryDetail(BFProductInf product) {
		return bfProductDao.queryDetail(product);
	}

	@Override
	public void insert(BFProductInf product) {
		bfProductDao.save(product);		
	}

	@Override
	public void updateDetail(BFProductInf product) {
		bfProductDao.updateDetail(product);
		
	}

	@Override
	public void updateImage(BFProductInf product) {
		if("1".equals(product.getIs_coversrc())){
			BFProductInf newProduct=new BFProductInf();
			newProduct.setProduct_code(product.getProduct_code());
			newProduct.setProduct_id(product.getProduct_id());
			newProduct.setIs_coversrc("0");
			//先将所有的封面设为0，再重新设为1
			bfProductDao.updateImage(newProduct);	
		}
		bfProductDao.updateImage(product);	
	}

	@Override
	public void saveImage(BFProductInf product) {
		String[] imgs=product.getProduct_photo().split(",");
		for (String string : imgs) {
			BFProductInf proModel=new BFProductInf();
			proModel.setProduct_code(product.getProduct_code());
			proModel.setProduct_id(product.getProduct_id());
			proModel.setStatus("1");
			proModel.setIs_coversrc("0");
			proModel.setImage_id(UUID.randomUUID().toString().toUpperCase());
			proModel.setProduct_photo(string);
			proModel.setRank(Integer.valueOf(bfProductDao.queryMaxRank(product)+1).toString());
			bfProductDao.saveImage(proModel);
		}
	}
	
}
