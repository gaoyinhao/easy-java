package com.easyjava.service.impl;

import com.easyjava.entity.query.BlogTeamUserQuery;
import com.easyjava.entity.query.SimplePage;
import com.easyjava.entity.po.BlogTeamUser;
import com.easyjava.entity.vo.PaginationResultVO;
import com.easyjava.mappers.BlogTeamUserMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;
import com.easyjava.entity.enums.PageSize;
import com.easyjava.service.BlogTeamUserService;
/**
 * @author 高98
 * @Description: 博客成员对应的ServiceImpl
 * @date: 2025/05/16
 */

@Service("blogTeamUserService")
public class BlogTeamUserServiceImpl implements BlogTeamUserService{

	@Resource
	private BlogTeamUserMapper<BlogTeamUser,BlogTeamUserQuery> blogTeamUserMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<BlogTeamUser>findListByParam(BlogTeamUserQuery query){
		return this.blogTeamUserMapper.selectList(query);
	 }
	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByParam(BlogTeamUserQuery query){
		return this.blogTeamUserMapper.selectCount(query);
	 }
	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVO<BlogTeamUser> findListByPage(BlogTeamUserQuery query ){
		Integer count = this.findCountByParam(query); 
		Integer pageSize=query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page=new SimplePage(query.getPageNo(),count,pageSize);
		query.setSimplePage(page);
		List<BlogTeamUser> list = this.findListByParam(query);
		PaginationResultVO<BlogTeamUser> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	 }
	/**
	 * 新增
	 */
	@Override
	public Integer add(BlogTeamUser bean){
		return this.blogTeamUserMapper.insert(bean);
	 }
	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<BlogTeamUser> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogTeamUserMapper.insertBatch(listBean);
	 }
	/**
	 * 新增或者修改
	 */
	@Override
	public Integer addOrUpdate(BlogTeamUser blogTeamUser){
		return this.blogTeamUserMapper.insertOrUpdate(blogTeamUser);
	 }
	/**
	 * 批量新增或修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<BlogTeamUser> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogTeamUserMapper.insertOrUpdateBatch(listBean);
	 }
	/**
	 * 根据UserId查询
	 */
	@Override
	 public BlogTeamUser getByUserId(Integer userId){
		return this.blogTeamUserMapper.selectByUserId(userId);
	 }
	/**
	 * 根据UserId更新
	 */
	@Override
	 public Integer updateByUserId(BlogTeamUser bean , Integer userId){
		return this.blogTeamUserMapper.updateByUserId(bean,userId);
	 }
	/**
	 * 根据UserId删除
	 */
	@Override
	 public Integer deleteByUserId(Integer userId){
		return this.blogTeamUserMapper.deleteByUserId(userId);
	 }
	/**
	 * 根据Phone查询
	 */
	@Override
	 public BlogTeamUser getByPhone(String phone){
		return this.blogTeamUserMapper.selectByPhone(phone);
	 }
	/**
	 * 根据Phone更新
	 */
	@Override
	 public Integer updateByPhone(BlogTeamUser bean , String phone){
		return this.blogTeamUserMapper.updateByPhone(bean,phone);
	 }
	/**
	 * 根据Phone删除
	 */
	@Override
	 public Integer deleteByPhone(String phone){
		return this.blogTeamUserMapper.deleteByPhone(phone);
	 }

}