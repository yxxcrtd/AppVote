package com.chinaedustar.app.vote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Vote Object
 */
@Entity
@Table(name = "Vote")
@SuppressWarnings("serial")
public class Vote implements Serializable {

    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VoteId", unique = true, nullable = false)
	private int voteId;

	@Column(name = "VoteCreateUserId", nullable = false)
	private String userId;

	@Column(name = "VoteTitle", nullable = false)
	private String title;

	@Column(name = "VoteDescription")
	private String description;

	@Column(name = "VoteEndTime", nullable = true)
	private Long endTime = 0L;

	@Column(name = "VoteType")
	private boolean type;

	@Column(name = "VoteVisible", nullable = false)
	private boolean visible;

	@Column(name = "VoteStatus", nullable = false)
	private boolean status;

	@Column(name = "VoteUserCounts", nullable = false)
	private int counts;

	@Column(name = "VoteCreateTime", nullable = false)
	private Long createTime;
	
	// 容器类别（group, class, user, 来自com.chinaedustar.honeybee.constants.AppConst）
	@Column(name = "ContainerType", nullable = true)
	private String containerType;

	// 容器Id，为个人工作台时的值为0，其他容器为容器的标识Id
	@Column(name = "ContainerId", nullable = true)
	private int containerId;
	
	// 容器名称
    @Column(name = "ContainerName", nullable = true)
    private String containerName;

	// 业务类型（来自com.chinaedustar.honeybee.constants.AppConst）
	@Column(name = "AppCode", nullable = true)
	private String appCode;

	// 业务Id
	@Column(name = "AppId", nullable = true)
	private int appId = 0;

	// 业务名称
	@Column(name = "AppName", nullable = true)
	private String appName;
	
	// 创建者机构Id
    @Column(name = "UnitId", nullable = true)
    private Integer unitId;
    
	// 创建者机构路径，用来进行统计，
	@Column(name = "UnitPath", nullable = true)
    private String unitPath;
	
	
	/**
	 * default constructor
	 */
	public Vote() {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public boolean getType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public int getContainerId() {
		return containerId;
	}

	public void setContainerId(int containerId) {
		this.containerId = containerId;
	}

	public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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
