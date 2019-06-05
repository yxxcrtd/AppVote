package com.chinaedustar.app.common;

/**
 * 表示支持的对象类型。 所有对象类型编码必须使用本对象中定义的值。如用户、群组、文章、相册、页面等。
 * 
 * 编制此类的目的是规范教研系统中在使用对象类型的时候能够使用统一的编码。 在能够为多个类提供服务的服务中，也要通过 ObjectType 来区分是哪个对象。
 * 
 * 注意：typeId必须大于0
 */
public class ObjectType {

	/** 对象类型标识。 */
	private final int typeId;

	/** 对象标准英文名。 */
	private final String typeName;

	/** 用户，代码 1，名称 'user' */
	public static final ObjectType OBJECT_TYPE_USER = new ObjectType(1, "user");
	/** 群组，代码 2，名称 'group' */
	public static final ObjectType OBJECT_TYPE_GROUP = new ObjectType(2, "group");
	/** 博文，代码 3，名称 'article' */
	public static final ObjectType OBJECT_TYPE_ARTICLE = new ObjectType(3, "article");
	/** 模板方案，代码 4，名称 'theme' */
	public static final ObjectType OBJECT_TYPE_THEME = new ObjectType(4, "theme");
	/** 好友，代码 5，名称 'friend' */
	public static final ObjectType OBJECT_TYPE_FRIEND = new ObjectType(5, "friend");
	/** 门户页面，代码 6, 名称 'portal_page' */
	public static final ObjectType OBJECT_TYPE_PAGE = new ObjectType(6, "portal_page");
	/** 门户页面中的内容块，代码 7，名称 'portal_webpart' */
	public static final ObjectType OBJECT_TYPE_WEBPART = new ObjectType(7, "portal_webpart");
	/** 个人博文分类，代码 8，名称 'staple' */
	public static final ObjectType OBJECT_TYPE_STAPLE = new ObjectType(8, "staple");
	/** 标签，代码 9，名称 'tag' */
	public static final ObjectType OBJECT_TYPE_TAG = new ObjectType(9, "tag");
	/** 公告，代码 10，名称 'placard' */
	public static final ObjectType OBJECT_TYPE_PLACARD = new ObjectType(10, "placard");
	/** 相册，代码 11，名称 'photo' */
	public static final ObjectType OBJECT_TYPE_PHOTO = new ObjectType(11, "photo");
	/** 资源，代码 12，名称 'resource' */
	public static final ObjectType OBJECT_TYPE_RESOURCE = new ObjectType(12, "resource");
	/** 课程，代码 13，名称 'course' */
	public static final ObjectType OBJECT_TYPE_COURSE = new ObjectType(13, "course");
	/** 学科，代码 14，名称 'subject' */
	public static final ObjectType OBJECT_TYPE_SUBJECT = new ObjectType(14, "subject");
	/** 集体备课，代码 15，名称 'preparecourse' */
	public static final ObjectType OBJECT_TYPE_PREPARECOURSE = new ObjectType(15, "preparecourse");
	/** 评论，代码 16，名称 'comment' */
	public static final ObjectType OBJECT_TYPE_COMMENT = new ObjectType(16, "comment");
	/** 资源，代码 17，名称 'video' */
	public static final ObjectType OBJECT_TYPE_VIDEO = new ObjectType(17, "video");
	/** 组织机构，代码 18，名称 'unit' */
	public static final ObjectType OBJECT_TYPE_UNIT = new ObjectType(18, "unit");
	/** 自定义频道，代码 19，名称 'channel' */
	public static final ObjectType OBJECT_TYPE_CHANNEL = new ObjectType(19, "channel");
	/** 集体备课个案，代码 20，名称 'preparecoursemember' */
	public static final ObjectType OBJECT_TYPE_PREPARECOURSEMEMBER = new ObjectType(20, "preparecoursemember");

	/** 活动 */
	public static final ObjectType OBJECT_TYPE_ACTIVITY = new ObjectType(21, "activity");

	/** 投票 */
	public static final ObjectType OBJECT_TYPE_VOTE = new ObjectType(22, "vote");

	/** 专题 */
	public static final ObjectType OBJECT_TYPE_SPECIALSUBJECT = new ObjectType(23, "specialsubject");

	/** 系统保留的，代码 100，名称 'system' */
	public static final ObjectType OBJECT_TYPE_SYSTEM = new ObjectType(100, "system");

	/**
	 * 通过标识得到对应的对象类型对象。
	 * 
	 * @param typeId
	 * @return 如果对应 typeId 不知道，则返回 null
	 */
	public static final ObjectType fromTypeId(int typeId) {
		switch (typeId) {
		case 1:
			return OBJECT_TYPE_USER;
		case 2:
			return OBJECT_TYPE_GROUP;
		case 3:
			return OBJECT_TYPE_ARTICLE;
		case 4:
			return OBJECT_TYPE_THEME;
		case 5:
			return OBJECT_TYPE_FRIEND;
		case 6:
			return OBJECT_TYPE_PAGE;
		case 7:
			return OBJECT_TYPE_WEBPART;
		case 8:
			return OBJECT_TYPE_STAPLE;
		case 9:
			return OBJECT_TYPE_TAG;
		case 10:
			return OBJECT_TYPE_PLACARD;
		case 11:
			return OBJECT_TYPE_PHOTO;
		case 12:
			return OBJECT_TYPE_RESOURCE;
		case 13:
			return OBJECT_TYPE_COURSE;
		case 14:
			return OBJECT_TYPE_SUBJECT;
		case 15:
			return OBJECT_TYPE_PREPARECOURSE;
		case 16:
			return OBJECT_TYPE_COMMENT;
		case 17:
			return OBJECT_TYPE_VIDEO;
		case 18:
			return OBJECT_TYPE_UNIT;
		case 19:
			return OBJECT_TYPE_CHANNEL;
		case 20:
			return OBJECT_TYPE_PREPARECOURSEMEMBER;
		case 21:
			return OBJECT_TYPE_ACTIVITY;
		case 22:
			return OBJECT_TYPE_VOTE;
		case 23:
			return OBJECT_TYPE_SPECIALSUBJECT;
		case 100:
			return OBJECT_TYPE_SYSTEM;
		}
		return null;
	}

	/**
	 * 根据名字得到对应对象类型。
	 * 
	 * @param typeName
	 * @return
	 */
	public static final ObjectType fromTypeName(String typeName) {

		if (typeName == null || typeName.length() == 0)
			return null;
		if ("user".equals(typeName))
			return OBJECT_TYPE_USER;
		else if ("group".equals(typeName))
			return OBJECT_TYPE_GROUP;
		else if ("article".equals(typeName))
			return OBJECT_TYPE_ARTICLE;
		else if ("theme".equals(typeName))
			return OBJECT_TYPE_THEME;
		else if ("friend".equals(typeName))
			return OBJECT_TYPE_FRIEND;
		else if ("portal_page".equals(typeName))
			return OBJECT_TYPE_PAGE;
		else if ("portal_webpart".equals(typeName))
			return OBJECT_TYPE_WEBPART;
		else if ("staple".equals(typeName))
			return OBJECT_TYPE_STAPLE;
		else if ("tag".equals(typeName))
			return OBJECT_TYPE_TAG;
		else if ("placard".equals(typeName))
			return OBJECT_TYPE_PLACARD;
		else if ("photo".equals(typeName))
			return OBJECT_TYPE_PHOTO;
		else if ("resource".equals(typeName))
			return OBJECT_TYPE_RESOURCE;
		else if ("course".equals(typeName))
			return OBJECT_TYPE_COURSE;
		else if ("subject".equals(typeName))
			return OBJECT_TYPE_SUBJECT;
		else if ("preparecourse".equals(typeName))
			return OBJECT_TYPE_PREPARECOURSE;
		else if ("system".equals(typeName))
			return OBJECT_TYPE_SYSTEM;
		else if ("unit".equals(typeName))
			return OBJECT_TYPE_UNIT;
		else if ("comment".equals(typeName))
			return OBJECT_TYPE_COMMENT;
		else if ("video".equals(typeName))
			return OBJECT_TYPE_VIDEO;
		else if ("channel".equals(typeName))
			return OBJECT_TYPE_CHANNEL;
		else if ("preparecoursemember".equals(typeName))
			return OBJECT_TYPE_PREPARECOURSEMEMBER;
		else if ("activity".equals(typeName))
			return OBJECT_TYPE_ACTIVITY;
		else if ("vote".equals(typeName))
			return OBJECT_TYPE_VOTE;
		else if ("specialsubject".equals(typeName))
			return OBJECT_TYPE_SPECIALSUBJECT;
		return null;

	}

	/**
	 * 根据标识得到对应对象汉语名称（用于显示给用户看看）
	 * 
	 * @param typeId
	 * @return
	 */
	public static final String getTypeNameChinese(int typeId) {
		switch (typeId) {
		case 1:
			return "用户";
		case 2:
			return "协作组";
		case 3:
			return "博文";
		case 4:
			return "模板方案";
		case 5:
			return "好友";
		case 6:
			return "门户页面";
		case 7:
			return "门户页面中的内容块";
		case 8:
			return "个人博文分类";
		case 9:
			return "标签";
		case 10:
			return "公告";
		case 11:
			return "相册";
		case 12:
			return "资源";
		case 13:
			return "课程";
		case 14:
			return "学科";
		case 15:
			return "集体备课";
		case 16:
			return "评论";
		case 17:
			return "视频";
		case 18:
			return "单位";
		case 19:
			return "频道";
		case 20:
			return "集备个案";
		case 21:
			return "活动";
		case 22:
			return "投票";
		case 23:
			return "专题";
		case 100:
			return "系统";
		}
		return "";
	}

	/**
	 * @param typeId
	 * @param typeName
	 */
	private ObjectType(int typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	/**
	 * 对象类型标识
	 * 
	 * @return
	 */
	public int getTypeId() {
		return this.typeId;
	}

	/**
	 * 对象标准英文名。
	 * 
	 * @return
	 */
	public String getTypeName() {
		return this.typeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ObjectType{id=" + typeId + ",name=" + typeName + "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof ObjectType))
			return false;
		ObjectType other = (ObjectType) o;
		return (other.typeId == this.typeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return typeId;
	}
	
}
