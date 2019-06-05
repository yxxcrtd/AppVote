package com.chinaedustar.app.vote.dao;

import org.springframework.stereotype.Component;

import com.chinaedustar.app.vote.domain.Voter;
import com.chinaedustar.common.dao.BaseDaoHibernate;

@Component("voterDao")
public class VoterDao extends BaseDaoHibernate<Voter, Integer> {

}
