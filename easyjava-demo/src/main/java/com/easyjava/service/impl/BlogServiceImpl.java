package com.easyjava.service.impl;

import com.easyjava.entity.query.BlogQuery;
import com.easyjava.entity.query.SimplePage;
import com.easyjava.entity.po.Blog;
import com.easyjava.entity.vo.PaginationResultVO;
import com.easyjava.mappers.BlogMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;
import com.easyjava.entity.enums.PageSize;
import com.easyjava.service.BlogService;
/**
 * @author 高98
 * @Description: 博客对应的ServiceImpl
 * @date: 2025/05/16
 */

@Service("blogService")
public class BlogServiceImpl implements BlogService{

	@Resource
	private BlogMapper<Blog,BlogQuery> blogMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<Blog>findListByParam(BlogQuery query){
		return this.blogMapper.selectList(query);
	 }
	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByParam(BlogQuery query){
		return this.blogMapper.selectCount(query);
	 }
	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVO<Blog> findListByPage(BlogQuery query ){
		Integer count = this.findCountByParam(query); 
		Integer pageSize=query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page=new SimplePage(query.getPageNo(),count,pageSize);
		query.setSimplePage(page);
		List<Blog> list = this.findListByParam(query);
		PaginationResultVO<Blog> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	 }
	/**
	 * 新增
	 */
	@Override
	public Integer add(Blog bean){
		return this.blogMapper.insert(bean);
	 }
	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<Blog> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogMapper.insertBatch(listBean);
	 }
	/**
	 * 新增或者修改
	 */
	@Override
	public Integer addOrUpdate(Blog blog){
		return this.blogMapper.insertOrUpdate(blog);
	 }
	/**
	 * 批量新增或修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<Blog> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogMapper.insertOrUpdateBatch(listBean);
	 }
	/**
	 * 根据BlogId查询
	 */
	@Override
	 public Blog getByBlogId(String blogId){
		return this.blogMapper.selectByBlogId(blogId);
	 }
	/**
	 * 根据BlogId更新
	 */
	@Override
	 public Integer updateByBlogId(Blog bean , String blogId){
		return this.blogMapper.updateByBlogId(bean,blogId);
	 }
	/**
	 * 根据BlogId删除
	 */
	@Override
	 public Integer deleteByBlogId(String blogId){
		return this.blogMapper.deleteByBlogId(blogId);
	 }

}