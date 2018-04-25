package com.security.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.security.common.annotation.SysLog;
import com.security.common.utils.FileUtil;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.entity.FileListEntity;
import com.security.modules.sys.service.FileListService;

/*
 * 文件上传
 * 
 */
@RestController
@RequestMapping("/touch/fileupload")
public class FileUploadController {

	private Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private FileListService fileListService;

	@Value("${FILE_URL_PATH}")
	private String fileUrlPath;

	@Value("${FILE_UPLOAD_SIZE}")
	private String fileSize;
	/**
	 * 单张图片上传
	 */
	@RequestMapping("upload")
	public R uploadImg(@RequestParam("file") MultipartFile file, String modularName){
		log.info("文件上传file：{}", file);
		if(file.getSize()>Integer.valueOf(fileSize)){
			return R.error("文件大小不能超过10MB！");
		}
		Map<String,String> fileName = FileUtil.saveImage(file, modularName);
		Map<String, Object> fileInf = new HashMap<String, Object>();
		fileInf.put("fileName", fileName.get("fileName"));
		fileInf.put("uploadName", fileName.get("uploadName"));
		fileInf.put("oriFileName", file.getOriginalFilename());
		fileInf.put("url", fileUrlPath + fileName.get("fileName"));
		fileInf.put("size", file.getSize());
		return R.ok(fileInf);
	}

	@RequestMapping(value = "/mergeFile")
	public R fileUpload(String guid, String md5value, String chunks, String chunk, String id, String name,
			String type, String lastModifiedDate, int size, MultipartFile file) {
		Map<String, Object> fileInf = new HashMap<String, Object>();
		String fileName;
		try {
			int index;
			String uploadFolderPath = FileUtil.getRealPath(null);
			// 文件保存路径  
			String modularName="temp";
			String dirname="/image/" + modularName;

			String mergePath = uploadFolderPath + dirname + "/" + id +"/";  
			//String mergePath = uploadFolderPath + "\\fileDate\\" + id + "\\";
			String ext = name.substring(name.lastIndexOf("."));

			// 判断文件是否分块
			if (chunks != null && chunk != null) {
				index = Integer.parseInt(chunk);
				fileName = String.valueOf(index) + ext;
				// 将文件分块保存到临时文件夹里，便于之后的合并文件
				FileUtil.saveFile(mergePath, fileName, file, null);
				// 验证所有分块是否上传成功，成功的话进行合并
				String mergeName=FileUtil.Uploaded(md5value, guid, chunk, chunks, mergePath, fileName, ext, null);
				fileInf.put("fileName", "/image/bigFile/"+mergeName);
				fileInf.put("uploadName", mergeName);
				fileInf.put("oriFileName", file.getOriginalFilename());
				fileInf.put("url", fileUrlPath + "/image/bigFile/"+mergeName);
			} else {
				// 上传文件没有分块的话就直接保存目标目录
				Map<String,String> fileMap =FileUtil.saveImage(file, "fileupload");
				fileInf.put("fileName", fileMap.get("fileName"));
				fileInf.put("uploadName", fileMap.get("uploadName"));
				fileInf.put("oriFileName", file.getOriginalFilename());
				fileInf.put("url", fileUrlPath + fileMap.get("fileName"));

			}
			fileInf.put("ext",FileUtil.extTrans(ext));

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());	
		}

		return R.ok(fileInf);
	}



	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<FileListEntity> fileList=null;
		int total=0;
		try {
			fileList = fileListService.queryPageList(query,new RowBounds(query.getOffset(), query.getLimit()));
			total = fileListService.queryTotal(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取上传列表失败");
		}
		PageUtils pageUtil = new PageUtils(fileList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	/**
	 * 删除
	 */
	@SysLog("删除上传文件")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody String[] ids){
		fileListService.deleteBatch(ids);

		return R.ok();
	}

	/**
	 * 保存Banner
	 */
	@SysLog("保存上传文件")
	@RequestMapping("/save")
	@RequiresPermissions("sys:oss:all")
	public R save(@RequestBody FileListEntity file){
		log.info("FileListEntity:{}", file);
		ValidatorUtils.validateEntity(file, AddGroup.class);
		log.info("添加上传文件:{}", file);

		try {
			fileListService.save(file);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加上传文件失败");
		}

		return R.ok();
	}

	/**
	 * 修改Banner
	 */
	@SysLog("修改上传文件")
	@RequestMapping("/update")
	@RequiresPermissions("sys:oss:all")
	public R update(@RequestBody FileListEntity file){
		ValidatorUtils.validateEntity(file, UpdateGroup.class);
		try {
			fileListService.update(file);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改上传文件失败");
		}
		return R.ok();
	}
	/**
	 * 上传信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("sys:oss:all")
	public R info(@PathVariable("pid") String pid){
		FileListEntity file = null;
		try {
			file = fileListService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取上传信息异常");
		}
		return R.ok().put("file", file);
	}
	/**
	 * 修改Banner状态
	 */
	@SysLog("修改文件状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("sys:oss:all")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			fileListService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Banner异常");
		}
		return R.ok();
	}
	
	
}
