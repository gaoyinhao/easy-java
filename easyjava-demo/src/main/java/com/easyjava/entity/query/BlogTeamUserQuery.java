package com.easyjava.entity.query;

import java.util.Date;

/**
 * @author 高98
 * @Description: 博客成员查询对象
 * @date: 2025/05/16
 */
public class BlogTeamUserQuery extends BaseQuery{

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 头像
	 */
	private String avatar;

	private String avatarFuzzy;

	/**
	 * 手机号
	 */
	private String phone;

	private String phoneFuzzy;

	/**
	 * 密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 职业
	 */
	private String profession;

	private String professionFuzzy;

	/**
	 * 简介
	 */
	private String introduction;

	private String introductionFuzzy;

	/**
	 * 0:富文本 1:markdown编辑器
	 */
	private Integer editorType;

	/**
	 * 0:普通用户 1:超级管理员
	 */
	private Integer roleType;

	/**
	 * 0:禁用 1:启用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setEditorType(Integer editorType) {
		this.editorType = editorType;
	}

	public Integer getEditorType() {
		return this.editorType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getRoleType() {
		return this.roleType;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setAvatarFuzzy(String avatarFuzzy) {
		this.avatarFuzzy = avatarFuzzy;
	}

	public String getAvatarFuzzy() {
		return this.avatarFuzzy;
	}

	public void setPhoneFuzzy(String phoneFuzzy) {
		this.phoneFuzzy = phoneFuzzy;
	}

	public String getPhoneFuzzy() {
		return this.phoneFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy) {
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy() {
		return this.passwordFuzzy;
	}

	public void setProfessionFuzzy(String professionFuzzy) {
		this.professionFuzzy = professionFuzzy;
	}

	public String getProfessionFuzzy() {
		return this.professionFuzzy;
	}

	public void setIntroductionFuzzy(String introductionFuzzy) {
		this.introductionFuzzy = introductionFuzzy;
	}

	public String getIntroductionFuzzy() {
		return this.introductionFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart() {
		return this.createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd() {
		return this.createTimeEnd;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart() {
		return this.lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd() {
		return this.lastLoginTimeEnd;
	}

}