package com.chinaedustar.app.common;

import java.util.Date;

public class SnsUser {
	private String userId;
	private String loginName;
	private String trueName;
	private String nickName;
	private String email;
	private Date createDate;
	private short gender;
	private String userType;
	private String userTypeName;
	private int unitId;
	private String unitName;
	private int userStatus;
	private String userIcon;
	private String userPassword;
	private int usn;
	private short isDead;
	private short visitLimited;
	private short commentLimited;
	private short bannedStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public short getGender() {
		return gender;
	}

	public void setGender(short gender) {
		this.gender = gender;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUsn() {
		return usn;
	}

	public void setUsn(int usn) {
		this.usn = usn;
	}

	public short getIsDead() {
		return isDead;
	}

	public void setIsDead(short isDead) {
		this.isDead = isDead;
	}

	public short getVisitLimited() {
		return visitLimited;
	}

	public void setVisitLimited(short visitLimited) {
		this.visitLimited = visitLimited;
	}

	public short getCommentLimited() {
		return commentLimited;
	}

	public void setCommentLimited(short commentLimited) {
		this.commentLimited = commentLimited;
	}

	public short getBannedStatus() {
		return bannedStatus;
	}

	public void setBannedStatus(short bannedStatus) {
		this.bannedStatus = bannedStatus;
	}

	@Override
	public String toString() {
		return "SnsUser [userId=" + userId + ", loginName=" + loginName + ", trueName=" + trueName + ", nickName=" + nickName + ", email=" + email + ", createDate=" + createDate
				+ ", gender=" + gender + ", userType=" + userType + ", userTypeName=" + userTypeName + ", unitId=" + unitId + ", unitName=" + unitName + ", userStatus="
				+ userStatus + ", userIcon=" + userIcon + ", userPassword=" + userPassword + ", usn=" + usn + ", isDead=" + isDead + ", visitLimited=" + visitLimited
				+ ", commentLimited=" + commentLimited + ", bannedStatus=" + bannedStatus + "]";
	}

}
