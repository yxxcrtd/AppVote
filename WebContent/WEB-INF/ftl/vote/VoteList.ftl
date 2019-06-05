<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="shortcut icon" href="${ContextPath}favicon.ico" type="image/x-icon" />
		<title>投票列表</title>
		<link rel="stylesheet" href="css/common.css" type="text/css">
		<link rel="stylesheet" href="css/poll.css" type="text/css">
		<script src="js/common/jquery.js" type="text/javascript"></script>
		<script src="js/pagerUtil.js" type="text/javascript"></script>
	</head>

	<body>
		<#include "VoteHeader.ftl">

		<!--正文 Start-->
		<div class="main container">
			<div class="colsWrap2 border clearfix">
				<div class="fl detailBox">
					<div class="pSection clearfix noneB">
						<a href="${Config.snsUrl!}/personhome/home/index.do?userId=<#if snsUser??>${snsUser.userId!}</#if>" class="fl headPic" target="_blank"><img width="50" height="50" src="${Config.snsUrl!}/data/upload/avatar/${snsUser.userIcon!}" onerror="javascript:this.src='${Config.snsUrl!}/images/demoimg/default1.gif'" /></a> 
						<div class="fl pIntro">
							<h3><a href="${Config.snsUrl!}/personhome/home/index.do?userId=${snsUser.userId!}" target="_blank">${snsUser.nickName!}</a><span>的投票</span></h3>
							<p>
								<#--<a href="${Config.snsUrl!}/personhome/home/index.do?userId=${snsUser.userId!}" target="_blank">ta的个人主页</a>-->
								<a href="vote.do?vote.userId=<#if vote??>${vote.userId!}<#else>${snsUser.userId!}</#if>"><#if (0 == snsUser.gender)>她<#else>他</#if>的投票</a>
							</p>
						</div>
						<a href="vote_edit.do?containerType=user" class="fr createBtn">发起投票</a>
					</div>
					<ul class="pTabNav clearfix">
						<li class="tabOn" id="d1" uid="<#if vote??>${vote.userId!}</#if>"><span class="btArr"></span><a href="javascript:;"><#if vote??><#if (0 == snsUser.gender)>她<#else>他</#if><#else>我</#if>发起的投票</a></li>
						<li id="d2" uid="<#if vote??>${vote.userId!}</#if>"><span class="btArr"></span><a href="javascript:;"><#if vote??><#if (0 == snsUser.gender)>她<#else>他</#if><#else>我</#if>参与的投票</a></li>
					</ul>
					<div class="votingList contentin" id="poll">
						<#if voteList??>
							<#if voteList.data??>
								<p class="rNum">ta共发起了<#if voteList??>${voteList.totalCount!}</#if>个投票</p>
								<#list voteList.data as v>
									<dl class="voteBox">
										<dt class="fl"><img src="images/pollIcon.gif"><em class="nullTag"></em></dt>
										<dd class="fl">
											<h4><a href="vote_show.do?vote.voteId=${v.voteId!}" title="${v.title!}" target="_blank">${v.title!}</a></h4>
											<p class="date">发起时间：<span>${Util.getDateByLong(v.createTime)?string("yyyy-MM-dd HH:mm")}</span></p>
											<p class="date">结束时间：<span>${Util.getDateByLong(v.endTime)?string("yyyy-MM-dd HH:mm")}</span></p>
											<div class="infoBox">
												<p><strong>${v.counts!'0'}</strong> 人投票</p>
												<#if (!v.status)>
													<span class="voteBtn finished">已结束</span>
												<#else>
													<a class="voteBtn partBtn" href="vote_show.do?vote.voteId=${v.voteId!}" target="_blank">马上参与</a>
												</#if>
											</div>
											<#if (v.userId! == loginUser.userId!)>
												<a href="javascript:;" class="trash" title="删除" style="bottom:0;top:0px;left:520px"></a>
												<span class="did" style="display: none;">${v.voteId!}</span>
												<#if (v.status)>
													<a href="vote_edit.do?vote.voteId=${v.voteId!}" class="zEdit" title="修改" style="bottom:0;top:0px;left:490px"></a>
												</#if>
											</#if>
										</dd>
									</dl>
								</#list>
								<div class="w_pagination">
									${pager1!}
								</div>
								<script type="text/javascript">
								<!--
								$(function() {
									$.makePageClik("vote_ajax.do", "poll", "${who!'d1'}");
								})
								//-->
								</script>
							<#else>
								<p class="rNum">ta共发起了<#if voteList??>${voteList.totalCount!}</#if>个投票</p>
							</#if>
						</#if>
					</div>
					<div class="votingList content" id="poll2"></div>
				</div>
				<#include "VoteRight.ftl">
			</div>
			<div class="imgShadow"><img class="imgShadowSize3" src="images/imgShadow.jpg"></div>
		</div>
		<!--正文 End--> 

		<#include "VoteFooter.ftl">
		
		<script src="js/common/common.js" type="text/javascript"></script> 
		<script src="js/poll.js" type="text/javascript"></script> 
		<script src="js/jquery.replyBox.js" type="text/javascript"></script>
		<script src="js/content.js" type="text/javascript"></script>
		<!--[if IE 6]>
		<script src="js/common/DD_belatedPNG.js" type="text/javascript"></script>
		<script type="text/javascript">
			DD_belatedPNG.fix('.pngfix, a.pngfix:hover');
		</script>
		<![endif]-->
	</body>
</html>
