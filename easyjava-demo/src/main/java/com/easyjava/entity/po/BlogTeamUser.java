package com.easyjava.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjava.entity.enums.DateTimePatternEnum;
import com.easyjava.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author 高98
 * @Description: 博客成员
 * @date: 2025/05/16
 */
public class BlogTeamUser implements Serializable{

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 职业
	 */
	private String profession;

	/**
	 * 简介
	 */
	private String introduction;

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
	@JsonIgnore
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm::ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm::ss")
	private Date createTime;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm::ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm::ss")
	private Date lastLoginTime;

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

	@Override
	public String toString() {
		return "用户ID:" + (userId == null ? "空" : userId) + ",昵称:" + (nickName == null ? "空" : nickName) + ",头像:" + (avatar == null ? "空" : avatar) + ",手机号:" + (phone == null ? "空" : phone) + ",密码:" + (password == null ? "空" : password) + ",职业:" + (profession == null ? "空" : profession) + ",简介:" + (introduction == null ? "空" : introduction) + ",0:富文本 1:markdown编辑器:" + (editorType == null ? "空" : editorType) + ",0:普通用户 1:超级管理员:" + (roleType == null ? "空" : roleType) + ",0:禁用 1:启用:" + (status == null ? "空" : status) + ",创建时间:" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",最后登录时间:" + (lastLoginTime == null ? "空" : DateUtils.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}

}