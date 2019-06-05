package com.chinaedustar.app.vote.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinaedustar.app.common.AppConst;
import com.chinaedustar.app.common.ConfigEnum;
import com.chinaedustar.app.common.Configuration;
import com.chinaedustar.app.common.HessianServiceUtil;
import com.chinaedustar.app.vote.common.logger.ProLogger;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.common.utils.JsonUtils;
import com.chinaedustar.common.vo.PageInfo;
import com.chinaedustar.common.web.BaseAction;
import com.chinaedustar.hessian.factory.HoneyBeeServiceFactory;
import com.chinaedustar.honeybee.domain.SUserBaseInfo;
import com.chinaedustar.honeybee.vo.feed.FeedDto;

/**
 * 项目Action基类
 */
@SuppressWarnings({ "serial", "static-access" })
public class ProBaseAction extends BaseAction {
	protected HoneyBeeServiceFactory honeyBeeService = HessianServiceUtil.getHessianService();

	// 投票的有效截止日期为90天之内
	protected static final int DAY = 90;
	
	protected String[] content;

	protected Vote vote;
	
	protected Voter voter;

	/** 我发起的所有投票 */
	protected PageInfo<Vote> voteList;
	
	/** 我参与的所有投票 */
	protected PageInfo<Vote> voteJoinList;
	
	protected List<VoteItem> voteItemList = new ArrayList<VoteItem>();
	
	protected List<Vote> hotList = new ArrayList<Vote>();
	
	protected List<Vote> historyList = new ArrayList<Vote>();
	
	protected List<Vote> newestList = new ArrayList<Vote>();

	protected ProLogger logger = new ProLogger();

	protected SUserBaseInfo loginUser = null;
	
	protected String ip;
	
	protected String jsonUser;
	
	protected SUserBaseInfo snsUser;
	
	/** 是否已投票（true已投；false未投） */
	protected boolean voted;
	
	/** 已投的字符串 */
	protected String votedString;
	
	/** 使用系统地址 */
	protected String clientUrl;
	
	/** 分页字符串 */
	protected String pager;
	
	/** 分页字符串1 */
	protected String pager1;
	
	/** 分页字符串2 */
	protected String pager2;
	
	/** 选择的页码 */
	protected String currentPageNo = "1";
	
	/** 发起d1/参与d2 */
	protected String who = "d1";
    
	protected String voteEndTime;

	@Autowired
	protected ServiceManager serviceManager;
		
	/** 容器类别 */
	protected String containerType;

	/** 容器Id */
	protected String containerId;

	/** 业务类型 */
	protected String appCode;

	/** 业务Id */
	protected String appId;

	/** 业务名称 */
	protected String appName;
	
	/**
	 * 从SNS获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	protected SUserBaseInfo getUser(String userId) {
        String jsonUser = honeyBeeService.getUserService().queryUserList(userId);
        JSONObject obj = (JSONObject) JSON.parse(jsonUser);
        List<SUserBaseInfo> userList = new ArrayList<SUserBaseInfo>();
        if ("1".equals(obj.get("status"))) {
        	userList = JsonUtils.parseList(obj.get("data").toString(), SUserBaseInfo.class);
        }
        if (0 < userList.size()) {
        	snsUser = userList.get(0);
        }
        return snsUser;
	}

	/**
	 * 验证
	 */
	protected boolean validateVote() {
		// 投票标题不能为空
		if (null == vote.getTitle() || "".equals(vote.getTitle())) {
			//addActionError("投票主题不能为空！");
			addActionMessage("a");
			return false;
		}

		// 投票主题最长不能超过32个字符
		if (32 < vote.getTitle().length()) {
			//addActionError("主题最长不能超过32个字符！");
			addActionMessage("b");
			return false;
		}

		// 截止时间
		if (null == vote.getEndTime() || "".equals(vote.getEndTime())) {
			//addActionError("截止时间不能为空！");
			addActionMessage("c");
			return false;
		}

		// 判断用户选择的日期范围是不是：当前日期 < ? < 90天后的日期
		if (new Date().getTime() > dateAddHourMinute(new Date(vote.getEndTime())).getTime()) {
			//addActionError("不能选择已经过去的时间！");
			addActionMessage("d");
			return false;
		}

		// 判断截止时间不得超过当前日期90天
		if (dateAddHourMinute(new Date(vote.getEndTime())).getTime() > dateAddDay().getTime()) {
			//addActionError("截止时间不得超过当前日期90天！");
			addActionMessage("e");
			return false;
		}
		
//		//同时验证objId和objType，任意一个取不到的话都不行
//		if (null == containerType || "".equals(containerType)) { // || null == containerId || "".equals(containerId) || isNumeric(containerId)) {
//			// addActionError("containerType或containerId不能为空！");
//			addActionMessage("f");
//			return false;
//		}
		
		// 判断投票项不能重复
		if (0 == vote.getVoteId()) {
			if (isDuplicate(content)) {
				addActionMessage("g");
				return false;
			}
		}
		
		return true;
	}
	
	// 利用HashSet中不能有重复记录，但能放入空值
	protected static boolean isDuplicate(Object[] args) {
		// List 允许重复记录
		List<String> list = new ArrayList<String>();
		for (Object obj : args) {
			if (null != obj && !"".equals((String) obj) && 0 < ((String) obj).length()) {
				list.add((String) obj);
			}
		}
		// 生成去空值后的新数组
		args = list.toArray();
		
		// HashSet不允许重复记录
		Set<Object> set = new HashSet<Object>();
		for (int i = 0; i < args.length; i++) {
			set.add(args[i]);
		}
		
		// 最后判断长度是否相同
		if (args.length == set.size()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 获得ActionMessages中的信息
	 * 
	 * @param list
	 * @return
	 */
	protected String getMessage(Collection<String> c) {
		StringBuilder sb = new StringBuilder();
		for (String s : c) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	// 判断是否是数字
	protected static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}

	/**
	 * 将年月日格式的日期加上小时和分钟
	 * 
	 * @param date 日期格式：2013-01-01
	 * @param hour 整型的小时（0-23）
	 * @param minute 整型的分钟（0-59）
	 * @return
	 */
	protected static Date dateAddHourMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTime();
	}
	
	/**
	 * 获取日期中的小时
	 * 
	 * @param date
	 * @return
	 */
	protected static String getHourOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return String.valueOf(c.get(c.HOUR_OF_DAY));
	}
	
	/**
	 * 获取日期中的分钟
	 * 
	 * @param date
	 * @return
	 */
	protected static String getMinuteOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return String.valueOf(c.get(c.MINUTE));
	}

	/**
	 * 获取当前日期之后的90天时间
	 */
	protected static Date dateAddDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, DAY);
		return c.getTime();
	}
	
	/*
	 * 为SNS提供一个feed
	 */
	protected static void send_feed(Map<String, String> map, SUserBaseInfo loginUser, Vote vote, String show) {
		String currentUrl = Configuration.getValue("vote.siteUrl", ConfigEnum.ESI);
		FeedDto feedDto = new FeedDto(
				currentUrl + "/vote_show.do?vote.voteId=" + vote.getVoteId(),
				"投票", 
				vote.getVoteId(), 
				"VOTE", 
				show,
				loginUser.getUserId(), 
				loginUser.getNickName(), 
				vote.getTitle(), 
				map, 
				currentUrl + "/vote_show.do?vote.voteId=" + vote.getVoteId(), 
				Integer.valueOf(vote.getContainerId()), 
				"", 
				vote.getContainerType());
		HessianServiceUtil.getHessianService().getHoneybeeService().pulishFeedForApp(JsonUtils.toJson(feedDto), loginUser.getUserId(), String.valueOf(vote.getContainerId()), vote.getContainerType());
	}

	/* (non-Javadoc)
	 * 
	 * @see com.chinaedustar.common.web.BaseAction#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
		super.setServletRequest(request);
		ip = request.getRemoteAddr();
		HttpSession s = request.getSession(false);
		this.loginUser = null == s ? null : (SUserBaseInfo) s.getAttribute(AppConst.SESSION_USER_KEY);
		 clientUrl = Configuration.getValue("honeybee.serverUrl", ConfigEnum.ESI); 
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public String[] getContent() {
		return content;
	}

	public void setContent(String[] content) {
		this.content = content;
	}

	public Voter getVoter() {
		return voter;
	}

	public void setVoter(Voter voter) {
		this.voter = voter;
	}

	public PageInfo<Vote> getVoteList() {
		return voteList;
	}

	public PageInfo<Vote> getVoteJoinList() {
		return voteJoinList;
	}

	public List<VoteItem> getVoteItemList() {
		return voteItemList;
	}

	public List<Vote> getHotList() {
		return hotList;
	}

	public List<Vote> getHistoryList() {
		return historyList;
	}

	public List<Vote> getNewestList() {
		return newestList;
	}

	public String getJsonUser() {
		return jsonUser;
	}

	public void setJsonUser(String jsonUser) {
		this.jsonUser = jsonUser;
	}

	public SUserBaseInfo getSnsUser() {
		return snsUser;
	}

	public void setSnsUser(SUserBaseInfo snsUser) {
		this.snsUser = snsUser;
	}

	public boolean getVoted() {
		return voted;
	}

	public String getVotedString() {
		return votedString;
	}

	public String getClientUrl() {
		return clientUrl;
	}

	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getPager1() {
		return pager1;
	}

	public void setPager1(String pager1) {
		this.pager1 = pager1;
	}

	public String getPager2() {
		return pager2;
	}

	public void setPager2(String pager2) {
		this.pager2 = pager2;
	}

	public String getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(String currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}
    
    public void setVoteEndTime(String voteEndTime) {
        this.voteEndTime = voteEndTime;
    }

	public String getContainerType() {
		return containerType;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getAppCode() {
		return appCode;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
