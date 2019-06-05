package com.chinaedustar.app.vote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VoteUser Object
 */
@Entity
@Table(name = "VoteUser")
@SuppressWarnings("serial")
public class Voter implements Serializable {

    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VoteUserId", unique = true, nullable = false)
	private int voterId;

	@Column(name = "VoteId", nullable = false)
	private int voteId;

	@Column(name = "UserId", nullable = false)
	private String userId;

	@Column(name = "VoteItemId", nullable = false)
	private String voteItemId;

	@Column(name = "VoteCreateTime", nullable = false)
	private Long createTime;

	@Column(name = "VoteUserIP", nullable = true)
	private String ip;
	
	// 参与者机构Id
    @Column(name = "UnitId", nullable = true)
    private Integer unitId;
    
    // 参与者机构路径，用来进行统计，
    @Column(name = "UnitPath")
    private String unitPath;

	/**
	 * Default Constructor
	 */
	public Voter() {
		
	}

	public int getVoterId() {
		return voterId;
	}

	public void setVoterId(int voterId) {
		this.voterId = voterId;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVoteItemId() {
		return voteItemId;
	}

	public void setVoteItemId(String voteItemId) {
		this.voteItemId = voteItemId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitPath() {
        return unitPath;
    }

    public void setUnitPath(String unitPath) {
        this.unitPath = unitPath;
    }

}