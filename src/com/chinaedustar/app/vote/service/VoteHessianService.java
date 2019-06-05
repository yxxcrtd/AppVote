package com.chinaedustar.app.vote.service;

/**
 * 投票的Hessian接口
 */
public interface VoteHessianService {
	
	/**
	 * 根据对象ID和对象类型获取最新的投票列表、当前用户是否已投、当前投票对应的投票项
	 * 
	 * @param JSON格式("userId":"", "appCode":"", "appId":"", "pageNo":"", "pageSize":"")
	 * @return 返回错误信息：如果返回"login"则没有登录信息；如果返回"error"则表示没有同时匹配的 objId 和 objType 记录
	 * @return 返回某分类下的一条最新投票记录：com.chinaedustar.app.vote.domain.Vote
	 * @return 返回某分类下的最新投票列表：PageInfo<com.chinaedustar.app.vote.domain.Vote>
	 * @return 返回某分类下的当前登录用户是否已投：tag=0没投；已投=返回投过的字符串
	 * @return 返回某分类下的最新投票对应的投票项：List<VoteItem>
	 * @return 返回LinkedHashMap字符串
	 * @throws Exception
	 */
	public String queryNewestVoteByObjIdAndObjType(String values) throws Exception;
	
	/**
	 * 投票
	 * 
	 * @param JSON格式("voteId":"", "userId":"", "voteItemId":"", "ip":"")
	 * @return 返回"error"，表示已投。
	 * @return 返回投票的参与人数。如果大于0，则投票成功；否则投票失败。
	 * @throws Exception
	 */	
	public String vote(String values) throws Exception;
	
	/**
	 * 删除投票
	 * 
	 * @param JSON格式("userId":"", "voteId":"")
	 * @return 返回错误信息：如果返回"login"则没有登录信息；如果返回"0"则表示没有投票ID
	 * @return 返回正确信息：如果返回"1"表示操作成功
	 * @throws Exception
	 */
	public String delete(String values) throws Exception;
	
	/**
	 * 分页查询投票
	 * @param values
	 * ■ containerType：容器类型
	 * ■ containerId：应用Id
	 * ■ appCode：应用编码：如research
	 * ■ appObjId：应用Id
	 * ■ keyword ：关键字，实现搜索功能
	 * ■ userId ： 用户ID
	 * ■ pageNo：当前页，实现分页功能
	 * ■ pageSize：页大小，实现分页功能
	 * ■ orderBy:排序方式，实现排序规则，默认DESC
	 * ■ orderType：排序字段，默认Id字段
	 * @return
	 * 返回PageInfo<Vote>
	 */
	public String queryVotePageInfo(String values);
	
	
	/**
     * 查询最新几条投票
     * @param values 
     * ■ containerType：容器类型
     * ■ containerId：应用Id
     * ■ appCode：应用编码：如research
     * ■ appObjId：应用Id
     * ■ userId：用户Id
     * ■ count ：最多几条
     * @return
     * 返回List<Vote>
     */
	public String queryNewVoteList(String values);
	
	/**
    * <p>投票管理接口</p>
    * <p>■ keyword 查询关键字。只查询投票标题</p>
    * <p>■ isOnlyCurUnit：true/false,是否只查当前机构。</p>
    * <p>■ unitId 当前机构Id</p>
    * <p>■ unitPath 机构路径</p>
    * <p>■ status 投票状态 参见com.chinaedustar.hessian.app.bean.AppContentConst中相应的常量</p>
    * <p>■ pageNo 当前页</p>
    * <p>■ pageSize 每页条数</p>
    * @return 返回PageInfo<Vote>
    */
   public String queryVoteListForManage(String values);
   
   /**
    * <p>投票管理接口：批量删除投票</p>
    * @param json格式参数：{"id", "1,2,8"}
    * 
    * @return
    * 
    * HessianResultVo
    */
   public String batchDeleteVoteForManage(String values);
	
}
