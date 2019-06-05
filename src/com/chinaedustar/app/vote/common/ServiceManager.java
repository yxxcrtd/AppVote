package com.chinaedustar.app.vote.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinaedustar.app.vote.service.VoteHessianService;
import com.chinaedustar.app.vote.service.VoteService;

/**
 * Service管理类
 */
@Component("serviceManager")
public class ServiceManager {
    
    @Autowired
    private VoteService voteService;
    
    public VoteService getVoteService() {
		return voteService;
	}
    
    @Autowired
    private VoteHessianService voteHessianService;
    
	public VoteHessianService getVoteHessianService() {
		return voteHessianService;
	}

}
