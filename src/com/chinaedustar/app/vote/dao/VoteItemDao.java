package com.chinaedustar.app.vote.dao;

import org.springframework.stereotype.Component;

import com.chinaedustar.app.vote.domain.VoteItem;
import com.chinaedustar.common.dao.BaseDaoHibernate;

@Component("voteItemDao")
public class VoteItemDao extends BaseDaoHibernate<VoteItem, Integer> {

}
