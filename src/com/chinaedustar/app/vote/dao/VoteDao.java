package com.chinaedustar.app.vote.dao;

import org.springframework.stereotype.Component;

import com.chinaedustar.app.vote.domain.Vote;
import com.chinaedustar.common.dao.BaseDaoHibernate;

@Component("voteDao")
public class VoteDao extends BaseDaoHibernate<Vote, Integer> {

}
