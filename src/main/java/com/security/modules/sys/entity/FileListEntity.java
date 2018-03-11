package com.security.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 文件上传
 * 
 * @author lbx

 */
public class FileListEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String file_id;
	//URL地址
	private String file_path;
	//创建时间
	private String file_type;
	
	private String filename;
	
	private String intro;
	
	private String status;
	
	private Date pubdate;

	
	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	

}
