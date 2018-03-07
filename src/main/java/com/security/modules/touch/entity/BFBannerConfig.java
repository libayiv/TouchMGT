package com.security.modules.touch.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.utils.JsonConfigUtils;


/**
 * app首页banner
 * 
 * @author lbx
 */
public class BFBannerConfig extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String id;// 编号
	
	private String name;// APP内部跳转的简称（需于APP端联合设置才能使生效）
	private String status;// 状态  0=不显示 1=显示
	
	private String intro;// 
	private String type;//app:APP端界面  web:H5界面(仅限CMS内部编辑的界面)
	





	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}






	public String getStatus() {
		return status;
	}






	public void setStatus(String status) {
		this.status = status;
	}






	public String getIntro() {
		return intro;
	}






	public void setIntro(String intro) {
		this.intro = intro;
	}






	public String getType() {
		return type;
	}






	public void setType(String type) {
		this.type = type;
	}






	@Override
	public String toString() {
		return "BFBannerConfig [id=" + id + ", type=" + type + ", intro=" + intro + ", status=" + status
				+ ", name=" + name + "]";
	}

}
