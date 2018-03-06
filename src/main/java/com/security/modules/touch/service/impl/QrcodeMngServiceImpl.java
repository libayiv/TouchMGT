package com.security.modules.touch.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.sys.entity.SysUserEntity;
import com.security.modules.touch.dao.QrcodeMngDao;
import com.security.modules.touch.entity.QrcodeMng;
import com.security.modules.touch.service.QrcodeMngService;
import com.security.modules.touch.service.exception.ArgumentNullException;

@Service
public class QrcodeMngServiceImpl implements QrcodeMngService {

	@Autowired
	private QrcodeMngDao qrcodeMngDao;

	@Override
	public QrcodeMng queryEntity(String id) throws Exception {
		return qrcodeMngDao.queryObject(id);
	}

	@Override
	public List<QrcodeMng> queryList(Map<String, Object> map,RowBounds rowBounds) throws Exception {
		return qrcodeMngDao.queryList(map,rowBounds);
	}

	@Override
	public int count(Map<String, Object> map) throws Exception {
		return qrcodeMngDao.queryTotal(map);
	}

	@Override
	public void save(QrcodeMng qrcode) throws Exception {
		SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		if(user==null){
			return;
		}
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		qrcode.setpId(CommonUtils.getRandomId());
		qrcode.setOperationId(user.getUserId().toString());
		qrcode.setOperationTime(formatter.format(currentTime));
		qrcode.setCreateId(user.getUserId().toString());
		qrcode.setCreateTime(formatter.format(currentTime));
		qrcodeMngDao.save(qrcode);
	}

	@Override
	public void update(QrcodeMng qrcode) throws Exception {
		SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		if(user==null){
			return;
		}
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		qrcode.setOperationId(user.getUserId().toString());
		qrcode.setOperationTime(formatter.format(currentTime));
		qrcodeMngDao.update(qrcode);
	}

	@Override
	public void deleteByStatus(String[] pIds) throws Exception {
		qrcodeMngDao.deleteByStatus(pIds);
	}

	

	@Override
	public List<QrcodeMng> queryVisibleList(QrcodeMng qrcode) throws ArgumentNullException  {
		if(qrcode.getPlatform()==null){
			throw new ArgumentNullException("字段:[platform]不能为空！");
		}
		qrcode.setStatus("1");
		return qrcodeMngDao.queryVisibleList(qrcode);
	}


}
