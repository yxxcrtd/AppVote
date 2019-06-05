package com.chinaedustar.app.vote.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.math.NumberUtils;

import com.chinaedustar.app.common.HtmlPager;
import com.chinaedustar.app.vote.common.ProBaseAction;
import com.chinaedustar.app.vote.common.VoteItemComparator;
import com.chinaedustar.app.vote.common.exception.ProException;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.common.utils.JsonUtils;

// 投票
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class VoteAction extends ProBaseAction {

	// 列表
	public String list() throws ProException {
		List l = new ArrayList<>();
		if (null == vote || null == vote.getUserId() || "".equals(vote.getUserId())) {
			l.add(loginUser.getUserId());
			snsUser = getUser(JsonUtils.toJson(l));
			voteList = serviceManager.getVoteService().getAll(loginUser.getUserId(), Integer.valueOf(currentPageNo), pageSize);
		} else {
			l.add(vote.getUserId());
			jsonUser = honeyBeeService.getUserService().queryUserList(JsonUtils.toJson(l));
			snsUser = getUser(JsonUtils.toJson(l));
			voteList = serviceManager.getVoteService().getAll(vote.getUserId(), Integer.valueOf(currentPageNo), pageSize);
		}
		hotList = serviceManager.getVoteService().hotList();
		int count = hotList.size();
		if (10 > count) {
			historyList = serviceManager.getVoteService().historyList(10 - count);
		}
		newestList = serviceManager.getVoteService().newestList();
		pager1 = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
		return SUCCESS;
	}
	
	public String list_ajax() throws ProException {
		if (null == vote || null == vote.getUserId() || "".equals(vote.getUserId())) {
			if ("d1".equals(who)) {
				who = "d1";
				voteList = serviceManager.getVoteService().getAll(loginUser.getUserId(), Integer.valueOf(currentPageNo), pageSize);
				pager1 = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			}
			if ("d2".equals(who)) {
				who = "d2";
				voteJoinList = serviceManager.getVoteService().getMyJoin(loginUser.getUserId(), Integer.valueOf(currentPageNo), pageSize);
				pager2 = HtmlPager.render(voteJoinList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			}
		} else {
			if ("d1".equals(who)) {
				voteList = serviceManager.getVoteService().getAll(vote.getUserId(), Integer.valueOf(currentPageNo), pageSize);
				pager1 = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			}
			if ("d2".equals(who)) {
				List l = new ArrayList<>();
				l.add(vote.getUserId());
				snsUser = getUser(JsonUtils.toJson(l));
				voteJoinList = serviceManager.getVoteService().getMyJoin(snsUser.getUserId(), Integer.valueOf(currentPageNo), pageSize);
				pager2 = HtmlPager.render(voteJoinList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			}
		}
		return SUCCESS;
	}

	// 添加或修改
	public String edit() throws ProException {
		if (null == vote) {
			vote = new Vote();
			vote.setType(true);
			vote.setVisible(true);
			vote.setEndTime(Calendar.getInstance().getTimeInMillis() + DAY * 24 * 60 * 60 * 1000L);
		} else {
			vote = serviceManager.getVoteService().findByUserIdAndVoteId(loginUser.getUserId(), vote.getVoteId());
			if (null != vote) {
				if (!vote.getStatus()) {
					addActionError("不能修改已结束的投票！");
					return ERROR;
				}
				containerType = vote.getContainerType();
				containerId = String.valueOf(vote.getContainerId());
				appCode = vote.getAppCode();
				appId = String.valueOf(vote.getAppId());
				appName = vote.getAppName();
				voteItemList = serviceManager.getVoteService().findListByVotoId(vote.getVoteId());
			} else {
				addActionError("投票不存在或不能修改别人的投票！");
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	// 保存
	public String save() throws ProException {
	    if(null == voteEndTime || 0 == voteEndTime.length()) {
	        renderText("100");
	        return NONE;
	    }
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
            vote.setEndTime(fmt.parse(this.voteEndTime + ":00").getTime());
        } catch (ParseException e) {
            renderText("101");
            return NONE;
        }
	    
		if (null != vote && validateVote()) {
			Map<String, String> map = new HashMap<String, String>();
			VoteItemComparator<VoteItem> voteItemComparator = new VoteItemComparator<VoteItem>();
			Set<VoteItem> items = new TreeSet<VoteItem>(voteItemComparator);
			boolean exit = false;
			boolean isExist = false;
			if (0 == vote.getVoteId()) {
				vote.setUserId(loginUser.getUserId());
				vote.setUnitId(loginUser.getUnitId());
				vote.setUnitPath(loginUser.getUnitPathInfo());
				vote.setCounts(0);
				vote.setStatus(true); // 新建投票的状态是进行中的
				vote.setCreateTime(new Date().getTime());
				for (int i = 0; content.length > i; i++) {
					if (null == content[i] || "".equals(content[i].trim())) {
						continue;
					}
					for (int j = 0; j < i; j++) {
						if (content[j].equals(content[i])) {
							exit = true;
							break;
						}
					}
					if (exit) {
						continue;
					}
					VoteItem voteItem = new VoteItem();
					voteItem.setName(content[i]);
					voteItem.setIndex(i + 1);
					voteItem.setCounts(0);
					items.add(voteItem);
					map.put(String.valueOf(i + 1), new StringBuffer("选项").append(i).append(":").append(content[i]).toString());
				}
				isExist = true;
			}
			if (null != containerType && !"".equals(containerType)) {
				vote.setContainerType(containerType);
				if ("user".equals(containerType)) {
					vote.setContainerId(0);
				} else {
					vote.setContainerId(NumberUtils.toInt(containerId));
				}
			}
			vote.setAppCode(appCode);
			vote.setAppId(NumberUtils.toInt(appId));
			if(appName != null && appName.length() > 32){
			    vote.setAppName(appName.substring(0, 29) + "...");
			}
			else{
			    vote.setAppName(appName);
			}
			// vote.setEndTime(new Timestamp(dateAddHourMinute(vote.getEndTime()).getTime()));
			int i = serviceManager.getVoteService().save(vote, items);
			if (0 < i) {
				// 修改投票不发feed
				if (isExist) {
					send_feed(map, loginUser, vote, "发起了投票");
				}
				renderText(String.valueOf(i));
			} else {
				renderText("unknown");
			}
			return NONE;
		} else {
			renderText(getMessage(getActionMessages()));
			return NONE;
		}
	}
	
	// 删除
	public String delete() throws ProException {
		if (0 < vote.getVoteId()) {
			serviceManager.getVoteService().del(loginUser.getUserId(), vote.getVoteId());
			renderText("ok");
		}
		return NONE;
	}
	
	// 显示投票
	public String show() throws ProException {
		vote = serviceManager.getVoteService().findByVoteId(vote.getVoteId());
		if (null != vote) {
			if (new Date().getTime() > vote.getEndTime() && vote.getStatus()) {
				serviceManager.getVoteService().updateStatus(vote.getVoteId());
			}
			List l = new ArrayList<>();
			l.add(vote.getUserId());
			snsUser = getUser(JsonUtils.toJson(l));
			voter = serviceManager.getVoteService().findByVoteIdAndUserId(vote.getVoteId(), loginUser.getUserId());
			if (null == voter) {
				voted = false;
			} else {
				voted = true;
				votedString = voter.getVoteItemId();
			}
			voteItemList = serviceManager.getVoteService().findListByVotoId(vote.getVoteId());
			hotList = serviceManager.getVoteService().hotList();
			int count = hotList.size();
			if (10 > count) {
				historyList = serviceManager.getVoteService().historyList(10 - count);
			}
			newestList = serviceManager.getVoteService().newestList();
			return SUCCESS;
		} else {
			addActionError("投票不存在！");
			return ERROR;
		}
	}

	// 投票
	public void vote() throws ProException {
		if ("".equals(voter.getVoteItemId()) || null == voter.getVoteItemId()) {
			renderText("null");
		} else {
			if (null != serviceManager.getVoteService().findByVoteIdAndUserId(voter.getVoteId(), loginUser.getUserId())) {
				renderText(ERROR);
			} else {
				voter.setUserId(loginUser.getUserId());
				voter.setUnitId(loginUser.getUnitId());
				voter.setUnitPath(loginUser.getUnitPathInfo());
				voter.setCreateTime(new Date().getTime());
				voter.setIp(ip);
				long counts = serviceManager.getVoteService().saveVoter(voter);
				Map<String, String> map = new HashMap<String, String>();
				voteItemList = serviceManager.getVoteService().findListByVotoId(voter.getVoteId());
				if (null != voteItemList && 0 < voteItemList.size()) {
					for (int i = 0; i < voteItemList.size(); i++) {
						map.put(String.valueOf(i), new StringBuffer("选项").append(i).append(":").append(voteItemList.get(i).getName()).toString());
					}
				}
				send_feed(map, loginUser, serviceManager.getVoteService().findByVoteId(voter.getVoteId()), "参与了投票");
				renderText(String.valueOf(counts));
			}
		}
	}
	
	// 过期
	public void status() throws ProException {
		if (0 < vote.getVoteId()) {
			serviceManager.getVoteService().updateStatus(vote.getVoteId());
		} else {
			System.out.println("设置投票已过期状态失败，没有投票ID！");
		}
	}

}
