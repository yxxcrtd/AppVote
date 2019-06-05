<#if voteList??>
	<p class="rNum">ta共发起了<#if voteList??>${voteList.totalCount!}</#if>个投票</p>
	<input type="hidden" name="who" value="${who!}" />
	<#if voteList??>
	<#if voteList.data??>
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
											
				<#if (v.userId! = loginUser.userId!)>
					<a href="javascript:;" class="trash"></a>
					<span class="did" style="display: none;">${v.voteId!}</span>
					<a href="vote_edit.do?vote.voteId=${v.voteId!}" class="zEdit" title="修改"></a>
				</#if>
			</dd>
		</dl>
	</#list>
	</#if>
	</#if>
	<div class="w_pagination"> 
		${pager1!}
	</div>
	<script type="text/javascript">
	<!--
	$(function() {
		$.makePageClik("vote_ajax.do", "poll", "d1");
	})
	//-->
	</script>
</#if>
<#if voteJoinList??>
	<p class="rNum">ta共参与了<#if voteJoinList??>${voteJoinList.totalCount!}</#if>个投票</p>
	<input type="hidden" name="who" value="${who!}" />
	<#if voteJoinList.data??>
		<#list voteJoinList.data as v>
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
							<a class="voteBtn viewBtn" href="vote_show.do?vote.voteId=${v.voteId!}" target="_blank">查看</a>
						</#if>
					</div>
				</dd>
			</dl>
		</#list>
	</#if>
	<div class="w_pagination"> 
		${pager2!}
	</div>
	<script type="text/javascript">
	<!--
	$(function() {
		$.makePageClik("vote_ajax.do", "poll2", "d2");
	})
	//-->
	</script>
</#if>