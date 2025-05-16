package com.easyjava.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author 高98
 * @Description: 博客分类的Mapper类
 * @date: 2025/05/16
 */

@Mapper
public interface BlogCategoryMapper<T,P> extends BaseMapper {
	/**
	 * 根据CategoryId查询
	 */
	 T selectByCategoryId(@Param("categoryId") Integer categoryId);

	/**
	 * 根据CategoryId更新
	 */
	 Integer updateByCategoryId(@Param("bean") T t, @Param("categoryId") Integer categoryId);

	/**
	 * 根据CategoryId删除
	 */
	 Integer deleteByCategoryId(@Param("categoryId") Integer categoryId);

}