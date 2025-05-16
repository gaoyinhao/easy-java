package com.easyjava.controller;

import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.easyjava.service.BlogTeamUserService;
import com.easyjava.entity.po.BlogTeamUser;
import com.easyjava.entity.query.BlogTeamUserQuery;
import com.easyjava.entity.vo.ResponseVO;
import java.util.List;
/**
 * @author 高98
 * @Description: 博客成员的Controller类
 * @date: 2025/05/16
 */

@RestController
@RequestMapping("/blogTeamUser")
public class BlogTeamUserController extends ABaseController{

	@Resource
	private BlogTeamUserService blogTeamUserService;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(BlogTeamUserQuery query) {
		return getSuccessResponseVo(blogTeamUserService.findListByPage(query));
	}
	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(BlogTeamUser bean){
		return getSuccessResponseVo(blogTeamUserService.add(bean));
	 }
	/**
	 * 批量新增
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<BlogTeamUser> listBean){
		return getSuccessResponseVo(blogTeamUserService.addBatch(listBean));
	 }
	/**
	 * 新增或者修改
	 */
	@RequestMapping("addOrUpdate")
	public ResponseVO addOrUpdate(BlogTeamUser bean){
		return getSuccessResponseVo(blogTeamUserService.addOrUpdate(bean));
	 }
	/**
	 * 批量新增或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdate(@RequestBody List<BlogTeamUser> listBean){
		return getSuccessResponseVo(blogTeamUserService.addOrUpdateBatch(listBean));
	 }
	/**
	 * 根据UserId查询
	 */
	@RequestMapping("getBlogTeamUserByUserId")
	 public ResponseVO getBlogTeamUserByUserId(Integer userId){
		return getSuccessResponseVo(this.blogTeamUserService.getByUserId(userId));
	 }
	/**
	 * 根据UserId更新
	 */
	@RequestMapping("updateBlogTeamUserByUserId")
	 public ResponseVO updateBlogTeamUserByUserId(BlogTeamUser bean,Integer userId){
		return getSuccessResponseVo(this.blogTeamUserService.updateByUserId(bean,userId));
	 }
	/**
	 * 根据UserId删除
	 */
	@RequestMapping("deleteBlogTeamUserByUserId")
	 public ResponseVO deleteBlogTeamUserByUserId(Integer userId){
		return getSuccessResponseVo(this.blogTeamUserService.deleteByUserId(userId));
	 }
	/**
	 * 根据Phone查询
	 */
	@RequestMapping("getBlogTeamUserByPhone")
	 public ResponseVO getBlogTeamUserByPhone(String phone){
		return getSuccessResponseVo(this.blogTeamUserService.getByPhone(phone));
	 }
	/**
	 * 根据Phone更新
	 */
	@RequestMapping("updateBlogTeamUserByPhone")
	 public ResponseVO updateBlogTeamUserByPhone(BlogTeamUser bean,String phone){
		return getSuccessResponseVo(this.blogTeamUserService.updateByPhone(bean,phone));
	 }
	/**
	 * 根据Phone删除
	 */
	@RequestMapping("deleteBlogTeamUserByPhone")
	 public ResponseVO deleteBlogTeamUserByPhone(String phone){
		return getSuccessResponseVo(this.blogTeamUserService.deleteByPhone(phone));
	 }

}