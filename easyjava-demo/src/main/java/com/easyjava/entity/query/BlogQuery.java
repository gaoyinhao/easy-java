package com.easyjava.entity.query;

import java.util.Date;

/**
 * @author 高98
 * @Description: 博客查询对象
 * @date: 2025/05/16
 */
public class BlogQuery extends BaseQuery{

	/**
	 * 博客ID
	 */
	private String blogId;

	private String blogIdFuzzy;

	/**
	 * 父ID
	 */
	private String pBlogId;

	private String pBlogIdFuzzy;

	/**
	 * 标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 分类ID
	 */
	private Integer categoryId;

	/**
	 * 分类名称
	 */
	private String categoryName;

	private String categoryNameFuzzy;

	/**
	 * 封面
	 */
	private String cover;

	private String coverFuzzy;

	/**
	 * 摘要
	 */
	private String summary;

	private String summaryFuzzy;

	/**
	 * 内容
	 */
	private String content;

	private String contentFuzzy;

	/**
	 * markdown编辑内容
	 */
	private String markdownContent;

	private String markdownContentFuzzy;

	/**
	 * 0:富文本 1:markdown编辑器
	 */
	private Integer editorType;

	/**
	 * 标签
	 */
	private String tag;

	private String tagFuzzy;

	/**
	 * 0:原创 1:转载
	 */
	private Integer type;

	/**
	 * 转载地址
	 */
	private String reprintUrl;

	private String reprintUrlFuzzy;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 0:不允许评论 1:允许评论
	 */
	private Integer allowComment;

	/**
	 * 0:草稿 1:已发布
	 */
	private Integer status;

	/**
	 * 0:删除 1:正常
	 */
	private Integer delType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	private String lastUpdateTimeStart;

	private String lastUpdateTimeEnd;

	/**
	 * 0:博客  1:专题
	 */
	private Integer blogType;

	/**
	 * 排序
	 */
	private Integer sort;

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getBlogId() {
		return this.blogId;
	}

	public void setPBlogId(String pBlogId) {
		this.pBlogId = pBlogId;
	}

	public String getPBlogId() {
		return this.pBlogId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCover() {
		return this.cover;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setMarkdownContent(String markdownContent) {
		this.markdownContent = markdownContent;
	}

	public String getMarkdownContent() {
		return this.markdownContent;
	}

	public void setEditorType(Integer editorType) {
		this.editorType = editorType;
	}

	public Integer getEditorType() {
		return this.editorType;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return this.tag;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setReprintUrl(String reprintUrl) {
		this.reprintUrl = reprintUrl;
	}

	public String getReprintUrl() {
		return this.reprintUrl;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	public Integer getAllowComment() {
		return this.allowComment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setDelType(Integer delType) {
		this.delType = delType;
	}

	public Integer getDelType() {
		return this.delType;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setBlogType(Integer blogType) {
		this.blogType = blogType;
	}

	public Integer getBlogType() {
		return this.blogType;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setBlogIdFuzzy(String blogIdFuzzy) {
		this.blogIdFuzzy = blogIdFuzzy;
	}

	public String getBlogIdFuzzy() {
		return this.blogIdFuzzy;
	}

	public void setPBlogIdFuzzy(String pBlogIdFuzzy) {
		this.pBlogIdFuzzy = pBlogIdFuzzy;
	}

	public String getPBlogIdFuzzy() {
		return this.pBlogIdFuzzy;
	}

	public void setTitleFuzzy(String titleFuzzy) {
		this.titleFuzzy = titleFuzzy;
	}

	public String getTitleFuzzy() {
		return this.titleFuzzy;
	}

	public void setCategoryNameFuzzy(String categoryNameFuzzy) {
		this.categoryNameFuzzy = categoryNameFuzzy;
	}

	public String getCategoryNameFuzzy() {
		return this.categoryNameFuzzy;
	}

	public void setCoverFuzzy(String coverFuzzy) {
		this.coverFuzzy = coverFuzzy;
	}

	public String getCoverFuzzy() {
		return this.coverFuzzy;
	}

	public void setSummaryFuzzy(String summaryFuzzy) {
		this.summaryFuzzy = summaryFuzzy;
	}

	public String getSummaryFuzzy() {
		return this.summaryFuzzy;
	}

	public void setContentFuzzy(String contentFuzzy) {
		this.contentFuzzy = contentFuzzy;
	}

	public String getContentFuzzy() {
		return this.contentFuzzy;
	}

	public void setMarkdownContentFuzzy(String markdownContentFuzzy) {
		this.markdownContentFuzzy = markdownContentFuzzy;
	}

	public String getMarkdownContentFuzzy() {
		return this.markdownContentFuzzy;
	}

	public void setTagFuzzy(String tagFuzzy) {
		this.tagFuzzy = tagFuzzy;
	}

	public String getTagFuzzy() {
		return this.tagFuzzy;
	}

	public void setReprintUrlFuzzy(String reprintUrlFuzzy) {
		this.reprintUrlFuzzy = reprintUrlFuzzy;
	}

	public String getReprintUrlFuzzy() {
		return this.reprintUrlFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart() {
		return this.createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd() {
		return this.createTimeEnd;
	}

	public void setLastUpdateTimeStart(String lastUpdateTimeStart) {
		this.lastUpdateTimeStart = lastUpdateTimeStart;
	}

	public String getLastUpdateTimeStart() {
		return this.lastUpdateTimeStart;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}

	public String getLastUpdateTimeEnd() {
		return this.lastUpdateTimeEnd;
	}

}