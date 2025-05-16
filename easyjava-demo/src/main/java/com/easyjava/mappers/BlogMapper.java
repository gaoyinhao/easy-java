package com.easyjava.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author 高98
 * @Description: 博客的Mapper类
 * @date: 2025/05/16
 */

@Mapper
public interface BlogMapper<T,P> extends BaseMapper {
	/**
	 * 根据BlogId查询
	 */
	 T selectByBlogId(@Param("blogId") String blogId);

	/**
	 * 根据BlogId更新
	 */
	 Integer updateByBlogId(@Param("bean") T t, @Param("blogId") String blogId);

	/**
	 * 根据BlogId删除
	 */
	 Integer deleteByBlogId(@Param("blogId") String blogId);

}