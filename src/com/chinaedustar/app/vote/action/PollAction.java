package com.chinaedustar.app.vote.action;

import com.chinaedustar.app.common.HtmlPager;
import com.chinaedustar.app.vote.common.ProBaseAction;
import com.chinaedustar.app.vote.common.exception.ProException;

/**
 * 投票
 */
@SuppressWarnings("serial")
public class PollAction extends ProBaseAction {

	// 外部列表
	public String list() throws ProException {
		if (null != containerType && !"".equals(containerType.trim()) && null != containerId && !"".equals(containerId.trim())) {
			if (null == currentPageNo || 0 == Integer.valueOf(currentPageNo)) {
				currentPageNo = "1";
			}
			voteList = serviceManager.getVoteService().findByTypeAndId(containerType, Integer.valueOf(containerId), Integer.valueOf(currentPageNo), pageSize);
			pager = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			return SUCCESS;
		} else if (null != appCode && !"".equals(appCode.trim()) && null != appId && !"".equals(appId.trim())) {
			if (null == currentPageNo || 0 == Integer.valueOf(currentPageNo)) {
				currentPageNo = "1";
			}
			voteList = serviceManager.getVoteService().findByAppCodeAndId(appCode, Integer.valueOf(appId), Integer.valueOf(currentPageNo), pageSize);
			pager = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
			return SUCCESS;
		} else {
			addActionError("没有提供任何参数！");
			return ERROR;
		}
	}

	public String list_ajax() throws ProException {
		if (null != containerType && !"".equals(containerType.trim()) && null != containerId && !"".equals(containerId.trim())) {
			voteList = serviceManager.getVoteService().findByTypeAndId(containerType, Integer.valueOf(containerId), Integer.valueOf(currentPageNo), pageSize);
			pager = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
		} else if (null != appCode && !"".equals(appCode.trim()) && null != appId && !"".equals(appId.trim())) {
			voteList = serviceManager.getVoteService().findByAppCodeAndId(appCode, Integer.valueOf(appId), Integer.valueOf(currentPageNo), pageSize);
			pager = HtmlPager.render(voteList.getTotalPageCount(), 1, Integer.valueOf(currentPageNo));
		}
		return SUCCESS;
	}
	
}
