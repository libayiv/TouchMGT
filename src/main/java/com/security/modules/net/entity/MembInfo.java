package com.security.modules.net.entity;
/**
 * 
 * @说明 经销商详细信息
 * @author hejun
 * @时间 2018年4月23日 上午10:50:36
 */
public class MembInfo {
	private static final long serialVersionUID = 1L;
	private String memb_id;
	private String memb_name;
	private String company_name;
	private int gender;
	private String memb_birth;
	private String memb_doc_no; 
	private String memb_phone_mobile;
	private String memb_email;

	private String memb_sponsor_name; 
	private String memb_upline_name; 
	private String memb_joindate;
	private String KIV;
	private String shop_id;
	private String memb_photo_status;


	private String memb_photo;
	private String signature; // 个性签名
	private String country;
	private String status;
	private String memb_status;
	private String photoName;
	/*修改字段*/
	private String pwd;
	private String imei;
	private String registerDate;
	private String active;
	private int bindingState;
	private String modifyDate;
	private String isShopOwner;
	private String nickName;
	private String homePhone;
	private String address;
	private String bankType;
	private String accountType;
	private String accountNumber;
	private String city;
	private String memb_code;
	private String memb_user_type;
	private String memb_doc_type;
	private String user_title;
	private String statusStr;




	public String getUser_title() {
		return user_title;
	}
	public void setUser_title(String user_title) {
		this.user_title = user_title;
	}
	public String getMemb_code() {
		return memb_code;
	}
	public void setMemb_code(String memb_code) {
		this.memb_code = memb_code;
	}
	public String getMemb_user_type() {
		return memb_user_type;
	}
	public void setMemb_user_type(String memb_user_type) {
		this.memb_user_type = memb_user_type;
	}
	public String getMemb_doc_type() {
		return memb_doc_type;
	}
	public void setMemb_doc_type(String memb_doc_type) {
		this.memb_doc_type = memb_doc_type;
	}
	public String getMemb_status() {
		return memb_status;
	}
	public void setMemb_status(String memb_status) {
		this.memb_status = memb_status;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIsShopOwner() {
		return isShopOwner;
	}
	public void setIsShopOwner(String isShopOwner) {
		this.isShopOwner = isShopOwner;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getBindingState() {
		return bindingState;
	}
	public void setBindingState(int bindingState) {
		this.bindingState = bindingState;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}


	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getMemb_id() {
		return memb_id;
	}
	public void setMemb_id(String memb_id) {
		this.memb_id = memb_id;
	}
	public String getMemb_name() {
		return memb_name;
	}
	public void setMemb_name(String memb_name) {
		this.memb_name = memb_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getMemb_birth() {
		return memb_birth;
	}
	public void setMemb_birth(String memb_birth) {
		this.memb_birth = memb_birth;
	}
	public String getMemb_doc_no() {
		return memb_doc_no;
	}
	public void setMemb_doc_no(String memb_doc_no) {
		this.memb_doc_no = memb_doc_no;
	}
	public String getMemb_phone_mobile() {
		return memb_phone_mobile;
	}
	public void setMemb_phone_mobile(String memb_phone_mobile) {
		this.memb_phone_mobile = memb_phone_mobile;
	}
	public String getMemb_email() {
		return memb_email;
	}
	public void setMemb_email(String memb_email) {
		this.memb_email = memb_email;
	}
	public String getMemb_sponsor_name() {
		return memb_sponsor_name;
	}
	public void setMemb_sponsor_name(String memb_sponsor_name) {
		this.memb_sponsor_name = memb_sponsor_name;
	}
	public String getMemb_upline_name() {
		return memb_upline_name;
	}
	public void setMemb_upline_name(String memb_upline_name) {
		this.memb_upline_name = memb_upline_name;
	}
	public String getMemb_joindate() {
		return memb_joindate;
	}
	public void setMemb_joindate(String memb_joindate) {
		this.memb_joindate = memb_joindate;
	}
	public String getKIV() {
		return KIV;
	}
	public void setKIV(String kIV) {
		KIV = kIV;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getMemb_photo_status() {
		return memb_photo_status;
	}
	public void setMemb_photo_status(String memb_photo_status) {
		this.memb_photo_status = memb_photo_status;
	}
	public String getMemb_photo() {
		return memb_photo;
	}
	public void setMemb_photo(String memb_photo) {
		this.memb_photo = memb_photo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusStr(){
		//1-未激活(Inactive)、2-已禁止(Banned)、3-已激活(Activated)、4-已删除(Deleted)、5-已冻结(Frozen)
		if(memb_status==null){
			return statusStr;
		}
		switch(memb_status){
		case "1":
			statusStr="Inactive";
			break;
		case "2":
			statusStr="Banned";
			break;
		case "3":
			statusStr="Activated";
			break;
		case "4":
			statusStr="Deleted";
			break;
		case "5":
			statusStr="Frozen";
			break;
		}
		return statusStr;
	}

}
