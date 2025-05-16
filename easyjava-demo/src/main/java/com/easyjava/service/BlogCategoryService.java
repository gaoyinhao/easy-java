package com.easyjava.service;

import com.easyjava.entity.query.BlogCategoryQuery;
import com.easyjava.entity.po.BlogCategory;
import com.easyjava.entity.vo.PaginationResultVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客分类对应的Service
 * @date: 2025/05/16
 */

public interface BlogCategoryService{

	/**
	 * 根据条件查询列表
	 */
	List<BlogCategory>findListByParam(BlogCategoryQuery query);

	/**
	 * 根据条件查询数量
	 */
    Integer findCountByParam(BlogCategoryQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<BlogCategory> findListByPage(BlogCategoryQuery query );

	/**
	 * 新增
	 */
	Integer add(BlogCategory bean);
	/**
	 * 批量新增
	 */
	Integer addBatch(List<BlogCategory> listBean);
	/**
	 * 新增或修改
	 */
	Integer addOrUpdate(BlogCategory bean);
	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<BlogCategory> listBean);
	/**
	 * 根据CategoryId查询
	 */
	 BlogCategory getByCategoryId(Integer categoryId);

	/**
	 * 根据CategoryId查询
	 */
	 Integer updateByCategoryId(BlogCategory bean , Integer categoryId);

	/**
	 * 根据CategoryId删除
	 */
	 Integer deleteByCategoryId(Integer categoryId);


}