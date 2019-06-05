package com.chinaedustar.app.vote.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.chinaedustar.app.vote.common.BaseService;
import com.chinaedustar.app.vote.common.exception.ProException;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.app.vote.service.VoteService;
import com.chinaedustar.common.vo.PageInfo;
import com.chinaedustar.common.vo.QueryRule;

@Service("voteService")
@SuppressWarnings("unchecked")
public class VoteServiceImpl extends BaseService implements VoteService {

	@Override
	public PageInfo<Vote> getAll(String userId, int pageNo, int pageSize) throws ProException {
		// 1，先更改已过期的投票
		String sql = "FROM Vote WHERE userId = ? AND endTime < ? AND status = 1";
		List<Vote> votes = (List<Vote>) daoManager.getVoteDao().findByHql(sql, new Object[] { userId, new Date().getTime() });
		if (0 < votes.size()) {
			for (Vote vote : votes) {
				updateStatus(vote.getVoteId());
			}
		}

		// 2，获取列表
		String hql = "FROM Vote WHERE userId = ? ORDER BY voteId DESC";
		try {
			return daoManager.getVoteDao().findByHql(hql, pageNo, pageSize, new Object[] { userId });
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - getAll】获取所有投票发生异常！", e);
		}
	}

	@Override
	public PageInfo<Vote> getMyJoin(String userId, int pageNo, int pageSize) throws ProException {
		String hql = "FROM Vote WHERE voteId IN (SELECT voteId FROM Voter WHERE userId = ?) ORDER BY voteId DESC";
		try {
			return daoManager.getVoteDao().findByHql(hql, pageNo, pageSize, new Object[] { userId });
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - getAll】获取我参见的所有投票发生异常！", e);
		}
	}

	@Override
	public PageInfo<Vote> findByTypeAndId(String objType, int objId, int pageNo, int pageSize) throws ProException {
		String hql = "FROM Vote WHERE containerType = ? and containerId = ? ORDER BY VoteId DESC";
		try {
			return daoManager.getVoteDao().findByHql(hql, pageNo, pageSize, new Object[] { objType, objId });
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - findByTypeAndId】获取根据容器内型和容器ID的所有投票发生异常！", e);
		}
	}

	@Override
	public PageInfo<Vote> findByAppCodeAndId(String appCode, int appId, int pageNo, int pageSize) throws ProException {
		String hql = "FROM Vote WHERE appCode = ? and appId = ? ORDER BY VoteId DESC";
		try {
			return daoManager.getVoteDao().findByHql(hql, pageNo, pageSize, new Object[] { appCode, appId });
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - findByAppCodeAndId】获取根据appCode和appId的所有投票发生异常！", e);
		}
	}

	@Override
	public Vote findByVoteId(int id) throws ProException {
		try {
			return daoManager.getVoteDao().get(id);
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - findByVoteId】根据投票ID查找投票对象发生异常！", e);
		}
	}

	@Override
	public Vote findByUserIdAndVoteId(String userId, int id) throws ProException {
		return super.findByUserIdAndVoteId(userId, id);
	}

	@Override
	public int save(Vote vote, Set<VoteItem> items) throws ProException {
		this.daoManager.getVoteDao().save(vote);
		for (VoteItem vi : items) {
			vi.setVoteId(vote.getVoteId());
			daoManager.getVoteItemDao().save(vi);
		}
		return vote.getVoteId();
	}

	@Override
	public void del(String userId, int id) throws ProException {
		super.del(userId, id);
	}

	@Override
	public List<VoteItem> findListByVotoId(int voteId) throws ProException {
		return super.findListByVotoId(voteId);
	}

	@Override
	public List<Voter> findVoterListByVoteId(int voteId) throws ProException {
		return super.findVoterListByVoteId(voteId);
	}

	@Override
	public long saveVoter(Voter voter) throws ProException {
		// 保存
		this.daoManager.getVoterDao().save(voter);
		return super.saveVoter(voter);
	}

	@Override
	public Voter findByVoteIdAndUserId(int voteId, String userId) throws ProException {
		return super.findByVoteIdAndUserId(voteId, userId);
	}

	@Override
	public List<Vote> hotList() throws ProException {
		// 7天之内的数据
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		String hql = "FROM Vote WHERE createTime >? ORDER BY counts DESC";
		try {
			return (List<Vote>) daoManager.getVoteDao().findByHql(hql, new Object[] { calendar.getTimeInMillis() });
		} catch (Exception e) {
			throw new ProException("【VoteServiceImpl - hotList】获取7天之内的数据投票发生异常！", e);
		}
	}

	@Override
	public List<Vote> historyList(int count) throws ProException {
		// 7天之外的数据
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		String sql = "voteCreateTime < " + calendar.getTimeInMillis();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addDescOrder("counts");
		queryRule.addSql(sql);
		return daoManager.getVoteDao().findList(queryRule, 0, count);
	}

	@Override
	public List<Vote> newestList() throws ProException {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addDescOrder("createTime");
		return daoManager.getVoteDao().findList(queryRule, 0, 10);
	}

	@Override
	public boolean updateStatus(int voteId) throws ProException {
		String sql = "UPDATE Vote SET VoteStatus = 0 WHERE voteId = ?";
		try {
			daoManager.getVoteDao().executeSql(sql, new Object[] { voteId });
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println("a=" + calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, 100);
		System.out.println("b=" + calendar.getTimeInMillis());
		calendar = Calendar.getInstance();
		System.out.println("c=" + calendar.getTimeInMillis());
	}

}
