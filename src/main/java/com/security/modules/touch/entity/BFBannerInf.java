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
public class BFBannerInf extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String id;// 编号
	@NotBlank(message="标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String title;//标题
	


	
	private String pc_valid;// 是否在PC显示 0=PC 1=APP 2=PC+APP
	private String value;// 跳转方式对应的跳转值
	private String status;// 状态  0=不显示 1=显示
	@NotBlank(message="图片不能为空", groups = {AddGroup.class})
	private String coversrc;// banner的封面图片
	private String rank;// 排序编号
	private String is_single;// 是否为单独展示的banner   唯一
	private String type;//跳转方式 app为APP内部跳转  web为webview跳转 

	



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPc_valid() {
		return pc_valid;
	}


	public void setPc_valid(String pc_valid) {
		this.pc_valid = pc_valid;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoversrc() {
		return coversrc;
	}

	public void setCoversrc(String coversrc) {
		this.coversrc = coversrc;
	}


	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}


	public String getIs_single() {
		return is_single;
	}

	public void setIs_single(String is_single) {
		this.is_single = is_single;
	}


	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	@Override
	public String toString() {
		return "BFBannerInf [id=" + id + ", title=" + title + ", pc_valid=" + pc_valid + ", value=" + value
				+ ", status=" + status + ", coversrc=" + coversrc + ", rank=" + rank + ", is_single="
				+ is_single + ", type=" + type + "]";
	}

}
