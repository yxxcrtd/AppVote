package com.chinaedustar.app.common;

public interface HoneybeeHessianService {
	public static String STATUS = "status";
	public static String STATUS_SUCCESS = "1";
	public static String STATUS_ERROR = "0";

	public static String MESSAGE = "message";
	public static String MESSAGE_SUCCESS = "ok";
	public static String MESSAGE_ERROR = "error";

	/**
	 * 应用推送SNS feed方法
	 * 
	 * @param feedjson
	 * @param fansIds
	 * @param groupUserIds
	 * @param pulishPersonTag
	 * @return boolean
	 */
	public boolean pulishFeedForApp(String feedjson, String fansIds, String groupUserIds, String pulishPersonTag);

	/**
	 * 获取用户信息
	 * 
	 * @param userids
	 *            用户id
	 * @return json格式的用户信息
	 */
	public String getUserList(String userids);

	/**
	 * 获取用户的好友列表
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public String getFriendList(String userid);

	/**
	 * 获取图片列表
	 * 
	 * @param photoids
	 * @return
	 */
	public String getPhotoList(String photoids);

	/**
	 * 获取文章列表
	 * 
	 * @param articleids
	 * @return
	 */
	public String getArticleList(String articleids);

	/**
	 * 获取资源列表
	 * 
	 * @param resourceids
	 * @return
	 */
	public String getResourceList(String resourceids);

	/**
	 * 获取视频列表
	 * 
	 * @param videoids
	 * @return
	 */
	public String getVideoList(String videoids);

	/**
	 * 添加文章、图片、资源、视频等对象
	 * 
	 * @param values
	 * @return
	 */
	public String addContent(String values);

	/**
	 * 发表评论（含活动、活动文章资源等的评论）
	 * 
	 * @param values
	 * @return
	 */
	public String postComment(String values);

	/**
	 * 分页返回某对象的评论列表:
	 * 
	 * @param values
	 * @return
	 */
	public String getCommentList(String values);

	/**
	 * 删除评论，注意权限的判断
	 * 
	 * @param values
	 * @return
	 */
	public String deleteComment(String values);

	/**
	 * 赞某一个对象
	 * 
	 * @param values
	 * @return
	 */
	public String praise(String values);

	/**
	 * 收藏某一对象
	 * 
	 * @param values
	 * @return
	 */
	public String favorite(String values);

	/**
	 * 转发某对象
	 * 
	 * @param values
	 * @return
	 */
	public String transfer(String values);

	/**
	 * 举报某对象
	 * 
	 * @param values
	 * @return
	 */
	public String report(String values);

	/**
	 * 发送邀请信息
	 * 
	 * @param values
	 * @return
	 */
	public String sendMsg(String values);

	/**
	 * 判断是否是成员
	 * 
	 * @param values
	 * @return
	 */
	public String isMember(String values);

	/**
	 * 获取圈子成员
	 * 
	 * @param values
	 * @return
	 */
	public String getGroupMember(String values);

}
