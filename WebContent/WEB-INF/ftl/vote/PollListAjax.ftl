<#if voteList??>
	<p class="appCount">共发起了<#if voteList??>${voteList.totalCount!}</#if>个投票</p>
	<#if voteList.data??>
		<#list voteList.data as v>
            <dl class="f_voteBox">
                <dt class="fl"><img src="images/vote.jpg"/></dt>
                <dd class="fl">
                    <h3><a href="vote_show.do?vote.voteId=${v.voteId!}" title="${v.title!}" target="_blank">${v.title!}</a></h3>
                    <p class="voteTime">发起时间：<span>${Util.getDateByLong(v.createTime)?string("yyyy-MM-dd HH:mm")}</span></p>
                    <p class="voteTime">结束时间：<span>${Util.getDateByLong(v.endTime)?string("yyyy-MM-dd HH:mm")}</span></p>
                    <div class="appBtns voteBtns">
                        <i class="voteNum"><b>${v.counts!'0'}</b>人投票</i>
						<#if (!v.status)>
							<span class="appBtn hasEnd">已结束</span>
						<#else>
							<a class="appBtn joinBtn" href="vote_show.do?vote.voteId=${v.voteId!}" target="_blank">马上参与</a>
						</#if>
                    </div>
                </dd>
            </dl>
		</#list>
	</#if>
</#if>
<!-- 分页 start-->
<div class="w_pagination">
	${pager!}
</div>
<script type="text/javascript">
<!--
$(function() {
	$.makePageClik("poll_ajax.do", "poll", "", "<#if containerType??>${containerType!}<#elseif (appCode??)>${appCode!}</#if>", "<#if containerId??>${containerId!}<#elseif (appId??)>${appId!}</#if>");
})
//-->
</script>
<!-- 分页 end--> 