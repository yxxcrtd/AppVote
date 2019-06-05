package com.chinaedustar.app.vote.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.chinaedustar.app.vote.common.BaseService;
import com.chinaedustar.app.vote.common.exception.ProException;
import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.app.vote.service.VoteHessianService;
import com.chinaedustar.common.utils.JsonUtils;
import com.chinaedustar.common.vo.PageInfo;
import com.chinaedustar.common.vo.QueryRule;
import com.chinaedustar.hessian.app.bean.AppContentConst;
import com.chinaedustar.hessian.factory.HessianFactoryUtils;
import com.chinaedustar.honeybee.constants.HessianConst;
import com.chinaedustar.honeybee.domain.SUserBaseInfo;
import com.chinaedustar.honeybee.vo.hessian.HessianResultVo;


@SuppressWarnings("unchecked")
@Service("voteHessianService")
public class VoteHessianServiceImpl extends BaseService implements VoteHessianService {
	private String json_string = "";
	private Map<String, String> map;
	
	@Override
	public String queryNewestVoteByObjIdAndObjType(String values) throws ProException {
		map = (Map<String, String>) JsonUtils.parseMap(values);
		String userId = map.get("userId");
		if (null == userId || "".equals(userId) || 0 > userId.length()) {
			return resultData(HessianConst.STATUS_ERROR, "login");
		}
		
		int pageNo = NumberUtils.toInt(map.get("pageNo"));
		int pageSize = NumberUtils.toInt(map.get("pageSize"));
		
		// 1，获取最新的一条投票记录
		//String hql = "FROM Vote";
		QueryRule queryRule = QueryRule.getInstance();
		if(map.containsKey("containerType")){
		    queryRule.addEqual("containerType", map.get("containerType"));
		}
		if(map.containsKey("containerId")){
		    queryRule.addEqual("containerId", NumberUtils.toInt(map.get("containerId")));
		}
		if(map.containsKey("appCode")){
		    queryRule.addEqual("appCode", map.get("appCode"));
		}
	    //支持2种格式。
		if(map.containsKey("appCodeId")){
		    queryRule.addEqual("appId", NumberUtils.toInt(map.get("appCodeId")));
		}
		if(map.containsKey("appId")){
		    queryRule.addEqual("appId", NumberUtils.toInt(map.get("appId")));
		}
    
		/**
		 * userId只用来验证登录，不能在此使用
		 */
		/*if(userId != null){
		    queryRule.addEqual("userId", userId);
		}*/
		
		queryRule.addDescOrder("createTime");
		
    	//hql = "FROM Vote WHERE containerType = ? AND containerId = ? ORDER BY createTime DESC";
    	List<Vote> votes = (List<Vote>) daoManager.getVoteDao().findList(queryRule, pageNo, pageSize);
    	
    	Vote vote = null;
    	if (0 < votes.size()) {
    		vote = votes.get(0);
    	} else {
			return resultData(HessianConst.STATUS_ERROR, "error");
    	}
    	//System.out.println("1，获取最新的一条投票记录：" + vote.getVoteId() + "," + vote.getTitle());
    	
		// 2，最新的投票列表
    	//PageInfo<Vote> voteList = daoManager.getVoteDao().findByHql(hql, pageNo, pageSize, new Object[] { containerType, containerId});
    	PageInfo<Vote> voteList = daoManager.getVoteDao().find(queryRule, pageNo, pageSize);
    	//System.out.println("2，最新的投票列表：" + voteList.getTotalCount() + " - " + voteList);
    	
    	// 3，当前用户是否已投（返回0就是没有投过；如已投则返回投过的字符串）
        Voter voter = super.findByVoteIdAndUserId(vote.getVoteId(), userId);
        String tag = null == voter ? "0" : voter.getVoteItemId();
    	//System.out.println("3，当前用户是否已投：" + tag);
		
		// 4，当前投票对应的投票项
    	List<VoteItem> voteItemList = super.findListByVotoId(vote.getVoteId());
    	//System.out.println("4，当前投票对应的投票项：" + voteItemList.size() + " - " + voteItemList);
    	
    	// 只有投票列表不为空的时候才有意义
    	if (0 < voteList.getTotalCount()) {
    		Map<String, String> groupMap = new LinkedHashMap<String, String>();
    		groupMap.put("vote", JsonUtils.toJson(vote));
    		groupMap.put("voteList", JsonUtils.toJson(voteList));
    		groupMap.put("tag", tag);
    		groupMap.put("voteItemList", JsonUtils.toJson(voteItemList));
    		json_string = resultData(HessianConst.STATUS_SUCCESS, groupMap);
    	}
        
        // 5，返回
        return json_string;
	}

	@Override
	public String vote(String values) throws ProException {
	   
		map = (Map<String, String>) JsonUtils.parseMap(values);
		int voteId = Integer.valueOf(map.get("voteId"));
		String userId = String.valueOf(map.get("userId"));
		String voteItemId = String.valueOf(map.get("voteItemId"));
		String ip = String.valueOf(map.get("ip"));
		
		// 1，先判断是否已投
		Voter voter = super.findByVoteIdAndUserId(voteId, userId);
		if (null == voter) {
			// 2，保存投票用户对象
		    SUserBaseInfo user = null;
		    voter = new Voter();
		    String json = HessianFactoryUtils.honeyBeeServiceFactory.getUserService().queryUserById(userId);
		    HessianResultVo vo = JsonUtils.parseObject(json, HessianResultVo.class);
		    if(vo.getStatus().equals("1")){
		        user = JsonUtils.parseObject(vo.getData(), SUserBaseInfo.class);
		        if(user != null){
		            voter.setUnitId(user.getUnitId());
		            voter.setUnitPath(user.getUnitPathInfo());
		        }
		    }
		    
			voter.setVoteId(voteId);
			voter.setUserId(userId);
			voter.setVoteItemId(voteItemId);
			voter.setCreateTime(new Date().getTime());
			voter.setIp(ip);
			daoManager.getVoterDao().save(voter);
			
			// 3，其他统计计算
			json_string = resultData(HessianConst.STATUS_SUCCESS, super.saveVoter(voter));
		} else {
			json_string = resultData(HessianConst.STATUS_ERROR, "error");
		}

		// 4，返回
		return json_string;
	}
	
	@Override
	public String delete(String values) throws ProException {
		map = (Map<String, String>) JsonUtils.parseMap(values);
		
		// 1，如果userId为空，则返回login字符串
		String userId = String.valueOf(map.get("userId"));
		if (null == userId || "".equals(userId) || 0 > userId.length()) {
			return resultData(HessianConst.STATUS_ERROR, "login");
		}
		
		// 2，获取投票ID的集合
		String voteId = map.get("voteIds");
		if (0 < voteId.length()) {
			String[] s = voteId.split(",");
			for (String string : s) {
				super.del(userId, Integer.valueOf(string));
			}
			json_string = resultData(HessianConst.STATUS_SUCCESS, "1");
		} else {
			json_string = resultData(HessianConst.STATUS_ERROR, "0");
		}
		
		// 3，返回
		return json_string;
	}
	
	
	
	/**
     * 分页查询投票
     * @param values
     * containerType：容器类型
     * containerId：应用Id
     * appCode：应用编码：如research
     * appObjId：应用Id
     * keyword ：关键字，实现搜索功能
     * userId ： 用户ID
     * pageNo：当前页，实现分页功能
     * pageSize：页大小，实现分页功能
     * orderBy:排序方式，实现排序规则，默认DESC
     * orderType：排序字段，默认Id字段
     * @return
     * 返回PageInfo<Vote>
     */
    public String queryVotePageInfo(String values){
        map = (Map<String, String>) JsonUtils.parseMap(values);
        QueryRule queryRule = QueryRule.getInstance();
        if(map.containsKey("containerType")){
            queryRule.addEqual("containerType", map.get("containerType"));
        }
        if(map.containsKey("containerId")){
            queryRule.addEqual("containerId", NumberUtils.toInt(map.get("containerId")));
        }
        
        if(map.containsKey("appCode")){
            queryRule.addEqual("appCode", map.get("appCode"));
        }
        
        if(map.containsKey("appObjId")){
            queryRule.addEqual("appId", NumberUtils.toInt(map.get("appObjId")));
        }
        
        if(map.containsKey("appId")){
            queryRule.addEqual("appId", NumberUtils.toInt(map.get("appId")));
        }
        
        if(map.containsKey("userId")){
            queryRule.addEqual("userId", map.get("userId"));
        }
        
        if(map.containsKey("keyword")){
            queryRule.addLike("title", "%" + map.get("keyword") + "%");
        }
        queryRule.addEqual("visible", true);
        
        //默认倒序
        queryRule.addDescOrder("voteId");
        int pageNo = NumberUtils.toInt(map.get("pageNo"));
        int pageSize = NumberUtils.toInt(map.get("pageSize"));
        PageInfo<Vote> pageInfo = daoManager.getVoteDao().find(queryRule, pageNo, pageSize);
        HessianResultVo vo = new HessianResultVo();
        vo.setStatus("1");
        vo.setData(JsonUtils.toJson(pageInfo));
        return JsonUtils.toJson(vo); 
    }
    
    
    /**
     * 查询最新几条投票
     * 
     * @param values
     * containerType：容器类型
     * containerId：应用Id
     * appCode：应用编码：如research
     * userId：用户Id
     * appObjId：应用Id
     * count ：最多几条
     * @return
     * 返回List<Vote>
     */
    public String queryNewVoteList(String values){
        map = (Map<String, String>) JsonUtils.parseMap(values);
        QueryRule queryRule = QueryRule.getInstance();
        if(map.containsKey("containerType")){
            queryRule.addEqual("containerType", map.get("containerType"));
        }
        if(map.containsKey("containerId")){
            queryRule.addEqual("containerId", NumberUtils.toInt(map.get("containerId")));
        }
        
        if(map.containsKey("appCode")){
            queryRule.addEqual("appCode", map.get("appCode"));
        }
        if(map.containsKey("appObjId")){
            queryRule.addEqual("appId", NumberUtils.toInt(map.get("appObjId")));
        }        
        if(map.containsKey("appId")){
            queryRule.addEqual("appId", NumberUtils.toInt(map.get("appId")));
        }
        
        if(map.containsKey("userId")){
            queryRule.addEqual("userId", map.get("userId"));
        }
        
        queryRule.addEqual("visible", true);
        
        // 默认倒序
        queryRule.addDescOrder("voteId");
        int count = NumberUtils.toInt(map.get("count"));
        List<Vote> list = daoManager.getVoteDao().findList(queryRule, 0, count);
        HessianResultVo vo = new HessianResultVo();
        vo.setStatus("1");
        vo.setData(JsonUtils.toJson(list));
        return JsonUtils.toJson(vo); 
    }
    
    /**
    * 投票管理接口
    * 
    * keyword 查询关键字。只查询投票标题
    * isOnlyCurUnit：true/false,是否只查当前机构。
    * unitId 当前机构Id
    * unitPath 机构路径
    * status 投票状态 参见com.chinaedustar.hessian.app.bean.AppContentConst中相应的常量
    * pageNo 当前页
    * pageSize 每页条数
    * @return 返回PageInfo<Vote>
    */
   public String queryVoteListForManage(String values){
       HessianResultVo vo =  new HessianResultVo(); 
       try{
           map = (Map<String, String>) JsonUtils.parseMap(values);
           QueryRule queryRule = QueryRule.getInstance();
           if(map.containsKey("isOnlyCurUnit")){
               if(map.get("isOnlyCurUnit").equals("true")){
                   queryRule.addEqual("unitId", NumberUtils.toInt(map.get("unitId")));
               }else{
                   queryRule.addLike("unitPath", map.get("unitPath") + "%");
               }           
           }
           
           if(map.containsKey("keyword")){
               queryRule.addLike("title", "%" + map.get("keyword") + "%");
           }
           
           if(map.containsKey("status")){
               Long currentTime = System.currentTimeMillis();
               if(map.get("status").equals(AppContentConst.VOTE_STATUS_DOING)){
                   queryRule.addGreaterThan("endTime", currentTime);
               }
               if(map.get("status").equals(AppContentConst.VOTE_STATUS_ENDED)){
                   queryRule.addLessThan("endTime", currentTime);
               }
           }
           int pageNo = NumberUtils.toInt(map.get("pageNo"));
           int pageSize = NumberUtils.toInt(map.get("pageSize"));
           PageInfo<Vote> pageInfo = daoManager.getVoteDao().find(queryRule, pageNo, pageSize);
           vo.setMessage("");
           vo.setStatus("1");
           vo.setData(JsonUtils.toJson(pageInfo));
       }
       catch(Exception e){
           this.logger.error("管理平台查询投票出错。", e);
           vo.setMessage("管理平台查询投票出错。");
           vo.setStatus("0");
       }
       return JsonUtils.toJson(vo);           
    }   
   
   /**
    * 投票管理接口：批量删除投票
    * @param values 以,分隔的id。例如：1,6,8
    * 
    * @return HessianResultVo
    */
   public String batchDeleteVote(String values){
       HessianResultVo vo =  new HessianResultVo(); 
       try{
           map = (Map<String, String>) JsonUtils.parseMap(values);
           if(map.containsKey("id") && map.get("id") != null){
               String[] ids = map.get("id").toString().split(",");
               for(String id : ids){
                   int voteId = NumberUtils.toInt(id.trim());
                   Vote vote = daoManager.getVoteDao().get(voteId);
                   if(vote != null){
                       String hql = "DELETE FROM Voter WHERE voteId=?";
                       daoManager.getVoteDao().executeHql(hql, new Object[]{vote.getVoteId()});
                       hql = "DELETE FROM VoteItem WHERE voteId=?";
                       daoManager.getVoteDao().executeHql(hql, new Object[]{vote.getVoteId()});
                       daoManager.getVoteDao().delete(vote);
                   }
               }
           }
           vo.setMessage("删除成功。");
           vo.setStatus("1");
           vo.setData("");
       }
       catch(Exception e){
           this.logger.error("批量删除投票出错。", e);
           vo.setMessage("批量删除投票出错。");
           vo.setStatus("0");
       }
       return JsonUtils.toJson(vo);      
   }
   
}
