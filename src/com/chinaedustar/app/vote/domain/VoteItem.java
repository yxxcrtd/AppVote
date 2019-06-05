package com.chinaedustar.app.vote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VoteItem Object
 */
@Entity
@Table(name = "VoteItem")
@SuppressWarnings("serial")
public class VoteItem implements Serializable {

    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VoteItemId", unique = true, nullable = false)
	private int voteItemId;

	@Column(name = "VoteId", nullable = false)
	private int voteId;

	@Column(name = "VoteItemName", nullable = false)
	private String name;

	@Column(name = "VoteUserCounts", nullable = false)
	private int counts;

	@Column(name = "VoteItemIndex", nullable = false)
	private int index;

	/**
	 * Default Constructor
	 */
	public VoteItem() {
		
	}

	public int getVoteItemId() {
		return voteItemId;
	}

	public void setVoteItemId(int voteItemId) {
		this.voteItemId = voteItemId;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}