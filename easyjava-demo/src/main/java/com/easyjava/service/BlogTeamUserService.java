package com.easyjava.service;

import com.easyjava.entity.query.BlogTeamUserQuery;
import com.easyjava.entity.po.BlogTeamUser;
import com.easyjava.entity.vo.PaginationResultVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客成员对应的Service
 * @date: 2025/05/16
 */

public interface BlogTeamUserService{

	/**
	 * 根据条件查询列表
	 */
	List<BlogTeamUser>findListByParam(BlogTeamUserQuery query);

	/**
	 * 根据条件查询数量
	 */
    Integer findCountByParam(BlogTeamUserQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<BlogTeamUser> findListByPage(BlogTeamUserQuery query );

	/**
	 * 新增
	 */
	Integer add(BlogTeamUser bean);
	/**
	 * 批量新增
	 */
	Integer addBatch(List<BlogTeamUser> listBean);
	/**
	 * 新增或修改
	 */
	Integer addOrUpdate(BlogTeamUser bean);
	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<BlogTeamUser> listBean);
	/**
	 * 根据UserId查询
	 */
	 BlogTeamUser getByUserId(Integer userId);

	/**
	 * 根据UserId查询
	 */
	 Integer updateByUserId(BlogTeamUser bean , Integer userId);

	/**
	 * 根据UserId删除
	 */
	 Integer deleteByUserId(Integer userId);

	/**
	 * 根据Phone查询
	 */
	 BlogTeamUser getByPhone(String phone);

	/**
	 * 根据Phone查询
	 */
	 Integer updateByPhone(BlogTeamUser bean , String phone);

	/**
	 * 根据Phone删除
	 */
	 Integer deleteByPhone(String phone);


}