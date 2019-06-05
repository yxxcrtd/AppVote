package com.chinaedustar.app.vote.service;

import java.util.List;
import java.util.Set;

import com.chinaedustar.app.vote.common.exception.ProException;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.common.vo.PageInfo;

/**
 * Vote Service
 */
public interface VoteService {
    
    public PageInfo<Vote> getAll(String userId, int pageNo, int pageSize) throws ProException;
    
    public PageInfo<Vote> getMyJoin(String userId, int pageNo, int pageSize) throws ProException;
    
    public PageInfo<Vote> findByTypeAndId(String objType, int objId, int pageNo, int pageSize) throws ProException;
    public PageInfo<Vote> findByAppCodeAndId(String appCode, int appId, int pageNo, int pageSize) throws ProException;
    
    public Vote findByVoteId(int id) throws ProException;
    
    public Vote findByUserIdAndVoteId(String userId, int id) throws ProException;
	
    public int save(Vote vote, Set<VoteItem> items) throws ProException;
	
    public void del(String userId, int id) throws ProException;
    
    // 获取当前投票对应的所有投票项
    public List<VoteItem> findListByVotoId(int voteId) throws ProException;
	
    // 投票
    public long saveVoter(Voter voter) throws ProException;
    
    // 根据投票ID和用户ID获取是否有当前用户的投票对象
    public Voter findByVoteIdAndUserId(int voteId, String userId) throws ProException;
    
    // 7天之内的数据
    public List<Vote> hotList() throws ProException;
    
    // 7天之外的数据
    public List<Vote> historyList(int count) throws ProException;
    
    public List<Vote> newestList() throws ProException;
    
    public boolean updateStatus(int id) throws ProException;
    
}
