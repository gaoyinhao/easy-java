package com.easyjava.service;

import com.easyjava.entity.query.BlogQuery;
import com.easyjava.entity.po.Blog;
import com.easyjava.entity.vo.PaginationResultVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客对应的Service
 * @date: 2025/05/16
 */

public interface BlogService{

	/**
	 * 根据条件查询列表
	 */
	List<Blog>findListByParam(BlogQuery query);

	/**
	 * 根据条件查询数量
	 */
    Integer findCountByParam(BlogQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Blog> findListByPage(BlogQuery query );

	/**
	 * 新增
	 */
	Integer add(Blog bean);
	/**
	 * 批量新增
	 */
	Integer addBatch(List<Blog> listBean);
	/**
	 * 新增或修改
	 */
	Integer addOrUpdate(Blog bean);
	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Blog> listBean);
	/**
	 * 根据BlogId查询
	 */
	 Blog getByBlogId(String blogId);

	/**
	 * 根据BlogId查询
	 */
	 Integer updateByBlogId(Blog bean , String blogId);

	/**
	 * 根据BlogId删除
	 */
	 Integer deleteByBlogId(String blogId);


}