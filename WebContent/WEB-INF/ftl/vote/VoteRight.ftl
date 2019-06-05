<#macro topList list>
	<#if list?has_content>
		<#list list as v>
			<li>
				<em class="emDate">${Util.getDateByLong(v.createTime)?string("MM-dd")}</em>
				<a href="vote_show.do?vote.voteId=${v.voteId!}" target="_blank" title="${v.title!}">
					<#if (14 &lt; v.title?length)>${v.title[0..13]!}...<#else>${v.title!}</#if>
				</a>
			</li>
		</#list>
	</#if>
</#macro>
<div class="fr rightBox">
	<div class="hotSlide">
		<h4 class="rTit">热门投票</h4>
		<div class="hotSlideList clearfix">
			<ul class="fl artSection">
				<@topList hotList />
				<@topList historyList />
			</ul>
		</div>
	</div>
	<p class="line"></p>	
	<h4 class="rTit">最新投票</h4>
	<ul class="artSection">
		<@topList newestList />
	</ul>	
</div>