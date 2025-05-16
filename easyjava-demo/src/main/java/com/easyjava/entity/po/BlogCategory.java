package com.easyjava.entity.po;

import java.io.Serializable;

/**
 * @author 高98
 * @Description: 博客分类
 * @date: 2025/05/16
 */
public class BlogCategory implements Serializable{

	/**
	 * 分类ID
	 */
	private Integer categoryId;

	/**
	 * 封面
	 */
	private String cover;

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 分类描述
	 */
	private String categoryDesc;

	/**
	 * 0:博客分类  1:专题
	 */
	private Integer categoryType;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 排序
	 */
	private Integer sort;

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCover() {
		return this.cover;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Integer getCategoryType() {
		return this.categoryType;
	}

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

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	@Override
	public String toString() {
		return "分类ID:" + (categoryId == null ? "空" : categoryId) + ",封面:" + (cover == null ? "空" : cover) + ",分类名称:" + (categoryName == null ? "空" : categoryName) + ",分类描述:" + (categoryDesc == null ? "空" : categoryDesc) + ",0:博客分类  1:专题:" + (categoryType == null ? "空" : categoryType) + ",用户ID:" + (userId == null ? "空" : userId) + ",昵称:" + (nickName == null ? "空" : nickName) + ",排序:" + (sort == null ? "空" : sort);
	}

}