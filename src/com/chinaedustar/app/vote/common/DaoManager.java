package com.chinaedustar.app.vote.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinaedustar.app.vote.dao.VoteDao;
import com.chinaedustar.app.vote.dao.VoteItemDao;
import com.chinaedustar.app.vote.dao.VoterDao;

@Component("daoManager")
public class DaoManager {
    
    @Autowired
    private VoteDao voteDao;
    
    @Autowired
    private VoteItemDao voteItemDao;
    
    @Autowired
    private VoterDao voterDao;

    public VoteDao getVoteDao() {
		return voteDao;
	}

    public VoteItemDao getVoteItemDao() {
		return voteItemDao;
	}

	public VoterDao getVoterDao() {
		return voterDao;
	}
	
}
