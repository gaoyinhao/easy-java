package com.easyjava.controller;

import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.easyjava.service.BlogCategoryService;
import com.easyjava.entity.po.BlogCategory;
import com.easyjava.entity.query.BlogCategoryQuery;
import com.easyjava.entity.vo.ResponseVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客分类的Controller类
 * @date: 2025/05/16
 */

@RestController
@RequestMapping("/blogCategory")
public class BlogCategoryController extends ABaseController{

	@Resource
	private BlogCategoryService blogCategoryService;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(BlogCategoryQuery query) {
		return getSuccessResponseVo(blogCategoryService.findListByPage(query));
	}
	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(BlogCategory bean){
		return getSuccessResponseVo(blogCategoryService.add(bean));
	 }
	/**
	 * 批量新增
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<BlogCategory> listBean){
		return getSuccessResponseVo(blogCategoryService.addBatch(listBean));
	 }
	/**
	 * 新增或者修改
	 */
	@RequestMapping("addOrUpdate")
	public ResponseVO addOrUpdate(BlogCategory bean){
		return getSuccessResponseVo(blogCategoryService.addOrUpdate(bean));
	 }
	/**
	 * 批量新增或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdate(@RequestBody List<BlogCategory> listBean){
		return getSuccessResponseVo(blogCategoryService.addOrUpdateBatch(listBean));
	 }
	/**
	 * 根据CategoryId查询
	 */
	@RequestMapping("getBlogCategoryByCategoryId")
	 public ResponseVO getBlogCategoryByCategoryId(Integer categoryId){
		return getSuccessResponseVo(this.blogCategoryService.getByCategoryId(categoryId));
	 }
	/**
	 * 根据CategoryId更新
	 */
	@RequestMapping("updateBlogCategoryByCategoryId")
	 public ResponseVO updateBlogCategoryByCategoryId(BlogCategory bean,Integer categoryId){
		return getSuccessResponseVo(this.blogCategoryService.updateByCategoryId(bean,categoryId));
	 }
	/**
	 * 根据CategoryId删除
	 */
	@RequestMapping("deleteBlogCategoryByCategoryId")
	 public ResponseVO deleteBlogCategoryByCategoryId(Integer categoryId){
		return getSuccessResponseVo(this.blogCategoryService.deleteByCategoryId(categoryId));
	 }

}