package com.chinaedustar.app.vote.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaedustar.app.vote.common.exception.ProException;
import com.chinaedustar.app.vote.common.logger.ProLogger;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.common.utils.JsonUtils;
import com.chinaedustar.honeybee.constants.HessianConst;

@SuppressWarnings("unchecked")
public class BaseService {
	protected ProLogger logger = new ProLogger();

    @Autowired
    protected DaoManager daoManager;
    
    /**
     * 根据投票ID和用户ID获取是否有当前用户的投票用户对象
     * 
     * @param voteId
     * @param userId
     * @return
     * @throws ProException
     */
    protected Voter findByVoteIdAndUserId(int voteId, String userId) throws ProException {
    	String hql = "FROM Voter WHERE voteId = ? AND userId = ?";
        try {
			List<Voter> voterList = (List<Voter>) daoManager.getVoterDao().findByHql(hql, new Object[] { voteId, userId });
        	return 0 < voterList.size() ? voterList.get(0): null;
        } catch (Exception e) {
        	throw new ProException("【VoteServiceImpl - isVoted】根据用户ID和投票ID查找投票用户对象发生异常！", e);
        }
    }
    
    /**
     * 根据用户ID和投票ID获取投票对象
     * 
     * @param userId
     * @param id
     * @return
     * @throws ProException
     */
    protected Vote findByUserIdAndVoteId(String userId, int id) throws ProException {
    	String hql = "FROM Vote WHERE userId = ? AND voteId = ?";
        try {
        	List<Vote> voteList = (List<Vote>) daoManager.getVoteDao().findByHql(hql, new Object[] { userId, id });
        	return 0 < voteList.size() ? voteList.get(0) : null;
        } catch (Exception e) {
        	throw new ProException("【VoteServiceImpl - findByUserIdAndVoteId】根据用户ID和投票ID查找投票对象发生异常！", e);
        }
    }
    
    /**
     * 获取当前投票对应的所有投票项
     * 
     * @param id - 投票ID
     * @return - 投票项列表
     * @throws ProException
     */    
    protected List<VoteItem> findListByVotoId(int voteId) throws ProException {
		String hql = "FROM VoteItem WHERE voteId = ? ORDER BY index ASC";
		try {
			return (List<VoteItem>) daoManager.getVoteItemDao().findByHql(hql, new Object[] { voteId });
		} catch (Exception e) {
            throw new ProException("【VoteServiceImpl - findListByVotoId】根据投票ID获取投票项发生异常！", e);
        }
	}
    
    /**
     * 投票
     * 
     * @param voter
     * @return
     * @throws ProException
     */
    protected long saveVoter(Voter voter) throws ProException {
        
    	// 1，重新统计投票项
    	String[] s = voter.getVoteItemId().split(",");
    	for (int i = 0; i < s.length; i++) {
    		// 重新计算选择了该投票项的数量。如果效率不高的话，可以换成在原有的统计基础上+1。
    		String hql_count_voteitem = "SELECT COUNT(*) FROM Voter WHERE voteId = ? AND CONCAT(',', " + " voteItemId " + ", ',')" + " LIKE ?";
    		long count_voteitem = daoManager.getVoterDao().getCount(hql_count_voteitem, new Object[] { voter.getVoteId(), "%," + s[i] + ",%" });
        	String hql_count_voteitem_update = "UPDATE VoteItem SET VoteUserCounts = ? WHERE voteItemId = ?";
        	daoManager.getVoteDao().executeSql(hql_count_voteitem_update, new Object[] { count_voteitem, s[i] });
    	}
    	
    	// 2，重新统计投票
    	String hql_count_vote = "SELECT COUNT(*) FROM Voter WHERE voteId = ?";
    	long count_vote = daoManager.getVoteDao().getCount(hql_count_vote, new Object[] { voter.getVoteId() });
    	String hql_count_vote_update = "UPDATE Vote SET VoteUserCounts = ? WHERE voteId = ?";
    	daoManager.getVoteDao().executeSql(hql_count_vote_update, new Object[] { count_vote, voter.getVoteId() });
    	
    	// 3，返回统计值用于页面显示
    	return count_vote;
    }
    
    protected void del(String userId, int id) throws ProException {
    	Vote vote = findByUserIdAndVoteId(userId, id);
        try {
        	if (null != vote) {
        		// 1，删除投票表
        		daoManager.getVoteDao().delete(vote);
        		
        		// 2，删除投票选项表
        		List<VoteItem> voteItemList = findListByVotoId(id);
        		if (0 < voteItemList.size()) {
            		for (VoteItem voteItem : voteItemList) {
            			daoManager.getVoteItemDao().delete(voteItem);
    				}
        		}
        		
        		// 3，删除投票用户表
        		List<Voter> voterList = findVoterListByVoteId(id);
        		if (0 < voterList.size()) {
        			for (Voter voter : voterList) {
        				daoManager.getVoterDao().delete(voter);
        			}
        		}
        	}
        } catch (Exception e) {
            throw new ProException("【BaseService - del】删除投票出现异常！", e);
        }
    }
	
	/**
	 * 根据投票ID获取投票用户列表
	 * 
	 * @param voteId
	 * @return
	 * @throws ProException
	 */
    protected List<Voter> findVoterListByVoteId(int voteId) throws ProException {
		String hql = "FROM Voter WHERE voteId = ?";
		try {
			return (List<Voter>) daoManager.getVoterDao().findByHql(hql, new Object[] { voteId });
		} catch (Exception e) {
            throw new ProException("【VoteServiceImpl - findVoterListByVoteId】根据投票ID获取投票用户列表发生异常！", e);
        }
	}
    
    /**
     * 返回结果数据
     * 
     * @param status
     * @param obj
     */
    protected String resultData(String status, Object obj) {
    	 HessianResultVo resultVo = new HessianResultVo();
         if (StringUtils.isNotEmpty(status)) {
             if (status.equals(HessianConst.STATUS_SUCCESS)) {
                 resultVo.setStatus(status);
                 resultVo.setData(JsonUtils.toJson(obj));
             } else if (status.equals(HessianConst.STATUS_ERROR)) {
                 resultVo.setStatus(status);
                 resultVo.setMessage((String) obj);
             } else {
                 resultVo.setStatus(HessianConst.STATUS_ERROR);
                 resultVo.setMessage("接口返回状态类型有误");
             }
             return JsonUtils.toJson(resultVo);
         } else {
             this.logger.error("【接口服务】接口返回状态类型有误，status=" + status);
             resultVo.setStatus(HessianConst.STATUS_ERROR);
             resultVo.setMessage("接口返回状态类型为NULL");
             return JsonUtils.toJson(resultVo);
         }
    }
    
}
