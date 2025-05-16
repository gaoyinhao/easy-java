package com.easyjava.controller;

import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.easyjava.service.BlogService;
import com.easyjava.entity.po.Blog;
import com.easyjava.entity.query.BlogQuery;
import com.easyjava.entity.vo.ResponseVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客的Controller类
 * @date: 2025/05/16
 */

@RestController
@RequestMapping("/blog")
public class BlogController extends ABaseController{

	@Resource
	private BlogService blogService;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(BlogQuery query) {
		return getSuccessResponseVo(blogService.findListByPage(query));
	}
	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(Blog bean){
		return getSuccessResponseVo(blogService.add(bean));
	 }
	/**
	 * 批量新增
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<Blog> listBean){
		return getSuccessResponseVo(blogService.addBatch(listBean));
	 }
	/**
	 * 新增或者修改
	 */
	@RequestMapping("addOrUpdate")
	public ResponseVO addOrUpdate(Blog bean){
		return getSuccessResponseVo(blogService.addOrUpdate(bean));
	 }
	/**
	 * 批量新增或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdate(@RequestBody List<Blog> listBean){
		return getSuccessResponseVo(blogService.addOrUpdateBatch(listBean));
	 }
	/**
	 * 根据BlogId查询
	 */
	@RequestMapping("getBlogByBlogId")
	 public ResponseVO getBlogByBlogId(String blogId){
		return getSuccessResponseVo(this.blogService.getByBlogId(blogId));
	 }
	/**
	 * 根据BlogId更新
	 */
	@RequestMapping("updateBlogByBlogId")
	 public ResponseVO updateBlogByBlogId(Blog bean,String blogId){
		return getSuccessResponseVo(this.blogService.updateByBlogId(bean,blogId));
	 }
	/**
	 * 根据BlogId删除
	 */
	@RequestMapping("deleteBlogByBlogId")
	 public ResponseVO deleteBlogByBlogId(String blogId){
		return getSuccessResponseVo(this.blogService.deleteByBlogId(blogId));
	 }

}