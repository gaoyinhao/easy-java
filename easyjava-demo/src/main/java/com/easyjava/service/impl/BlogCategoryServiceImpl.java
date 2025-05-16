package com.easyjava.service.impl;

import com.easyjava.entity.query.BlogCategoryQuery;
import com.easyjava.entity.query.SimplePage;
import com.easyjava.entity.po.BlogCategory;
import com.easyjava.entity.vo.PaginationResultVO;
import com.easyjava.mappers.BlogCategoryMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;
import com.easyjava.entity.enums.PageSize;
import com.easyjava.service.BlogCategoryService;
/**
 * @author 高98
 * @Description: 博客分类对应的ServiceImpl
 * @date: 2025/05/16
 */

@Service("blogCategoryService")
public class BlogCategoryServiceImpl implements BlogCategoryService{

	@Resource
	private BlogCategoryMapper<BlogCategory,BlogCategoryQuery> blogCategoryMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<BlogCategory>findListByParam(BlogCategoryQuery query){
		return this.blogCategoryMapper.selectList(query);
	 }
	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByParam(BlogCategoryQuery query){
		return this.blogCategoryMapper.selectCount(query);
	 }
	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVO<BlogCategory> findListByPage(BlogCategoryQuery query ){
		Integer count = this.findCountByParam(query); 
		Integer pageSize=query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page=new SimplePage(query.getPageNo(),count,pageSize);
		query.setSimplePage(page);
		List<BlogCategory> list = this.findListByParam(query);
		PaginationResultVO<BlogCategory> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	 }
	/**
	 * 新增
	 */
	@Override
	public Integer add(BlogCategory bean){
		return this.blogCategoryMapper.insert(bean);
	 }
	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<BlogCategory> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogCategoryMapper.insertBatch(listBean);
	 }
	/**
	 * 新增或者修改
	 */
	@Override
	public Integer addOrUpdate(BlogCategory blogCategory){
		return this.blogCategoryMapper.insertOrUpdate(blogCategory);
	 }
	/**
	 * 批量新增或修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<BlogCategory> listBean){
		if(listBean==null || listBean.isEmpty()){
			return 0;
		}
		return this.blogCategoryMapper.insertOrUpdateBatch(listBean);
	 }
	/**
	 * 根据CategoryId查询
	 */
	@Override
	 public BlogCategory getByCategoryId(Integer categoryId){
		return this.blogCategoryMapper.selectByCategoryId(categoryId);
	 }
	/**
	 * 根据CategoryId更新
	 */
	@Override
	 public Integer updateByCategoryId(BlogCategory bean , Integer categoryId){
		return this.blogCategoryMapper.updateByCategoryId(bean,categoryId);
	 }
	/**
	 * 根据CategoryId删除
	 */
	@Override
	 public Integer deleteByCategoryId(Integer categoryId){
		return this.blogCategoryMapper.deleteByCategoryId(categoryId);
	 }

}