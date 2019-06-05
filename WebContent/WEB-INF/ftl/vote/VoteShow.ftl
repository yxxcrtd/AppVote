<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="shortcut icon" href="${ContextPath}favicon.ico" type="image/x-icon" />
	    <title>投票详情</title>
	    <link rel="stylesheet" href="${ContextPath}css/common.css" type="text/css">
	    <link rel="stylesheet" href="${ContextPath}css/poll.css" type="text/css">
	</head>

	<body>
		<#include "VoteHeader.ftl">
		
		<!--正文 Start-->
		<div class="main container">
			<div class="colsWrap2 border clearfix">
				<div class="fl detailBox">
					<div class="pSection clearfix">	
						<a href="${Config.snsUrl!}/personhome/home/index.do?userId=<#if snsUser??>${snsUser.userId!}</#if>" class="fl headPic"><img width="50" height="50" src="${Config.snsUrl!}/data/upload/avatar/<#if snsUser??>${snsUser.userIcon!}</#if>" onerror="javascript:this.src='${Config.snsUrl!}/images/demoimg/default1.gif'" /></a> 
						<div class="fl pIntro">
							<h3><a href="${Config.snsUrl!}/personhome/home/index.do?userId=<#if snsUser??>${snsUser.userId!}</#if>" target="_blank"><#if snsUser??>${snsUser.nickName!}</#if></a><span>的投票</span></h3>
							<p>
								<#--<a href="${Config.snsUrl!}/personhome/home/index.do?userId=<#if snsUser??>${snsUser.userId!}</#if>" target="_blank">ta的个人主页</a>-->
								<a href="vote.do?vote.userId=${vote.userId!}"><#if (0 == snsUser.gender)>她<#else>他</#if>的投票</a>
								<#if (vote.userId = loginUser.userId)><a href="javascript:;" class="delVote">删除该投票</a></#if>
								<span id="did" style="display: none;">${vote.voteId!}</span>
							</p>
						</div>
						<a href="vote_edit.do?containerType=user" class="fr createBtn">发起投票</a>
					</div>
					<div class="pollCont">
						<div class="topCont">
							<div class="fr peopleNum"><span>参与人数</span><span id="vote_count" class="number">${vote.counts!'0'}</span></div>
							<h2 title="${vote.title!}">${vote.title!}</h2>
							<p class="pollTime"><span class="pollDjs"></span><span><#if (vote.type)>单选<#else>多选</#if></span></p>
						</div>
						<p title="${vote.description!''}">
							<#--if (90 &lt; vote.description?length)>${vote.description[0..89]!}......<#else>${vote.description!''}</#if-->
							${vote.description!?html}
						</p>
						<form>
							<div class="options">
								<ul class="radioItems">
									<#if voteItemList??>
										<#list voteItemList as vi>
											<div>
												<#if (vote.type)>
													<li>
														<span class="fl radioItem">
															<input type="radio" name="content" class="voteRadio" value="${vi.voteItemId!}" id="id${vote.voteId!}${vi_index}">
															<label for="id${vote.voteId!}${vi_index}">${vi.name!}</label>
														</span>
														<p class="fl pollPer">
															<span class="perBox"><i class="perBg perBg${vi_index%4 + 1}" style="width:1%;"></i></span>
															<em class="perNum">${vi.counts!'0'}</em>
															<em class="perN"></em>
															<b class="perVoted"><#if votedString??><#if (votedString == vi.voteItemId?c)>已投</#if></#if></b>
														</p>
													</li>
												<#else>
													<li>
														<span class="fl radioItem">
															<input type="checkbox" name="content" class="voteCheck" value="${vi.voteItemId!}" id="id${vote.voteId!}${vi_index}">
															<label for="id${vote.voteId!}${vi_index}">${vi.name!}</label>
														</span>
														<p class="fl pollPer">
															<span class="perBox"><i class="perBg perBg${vi_index%4 + 1}" style="width:1%;"></i></span>
															<em class="perNum">${vi.counts!'0'}</em>
															<em class="perN"></em>
															<b class="perVoted"><#if votedString??><#if (-1 < votedString?index_of(vi.voteItemId?c))>已投</#if></#if></b>
														</p>
													</li>
												</#if>
											</div>
										</#list>
									</#if>
								</ul>
								<span class="disPollBtn">投票</span>
								<input class="pollBtn none" id="vote_submit" type="button" value="投票">
							</div>
						</form>
					</div>
				</div>
				<#include "VoteRight.ftl">
			</div>
			<div class="imgShadow"><img class="imgShadowSize3" src="images/imgShadow.jpg"></div>
		</div>
		<!--正文 End-->

		<#include "VoteFooter.ftl">
		<script src="js/common/jquery.js" type="text/javascript"></script> 
		<script src="js/common/common.js" type="text/javascript"></script> 
		<script src="js/poll.js" type="text/javascript"></script> 
		<script src="js/jquery.replyBox.js" type="text/javascript"></script>
		<!--[if IE 6]>
		<script src="js/common/DD_belatedPNG.js" type="text/javascript"></script>
		<script type="text/javascript">
			DD_belatedPNG.fix('.pngfix, a.pngfix:hover');
		</script>
		<![endif]-->
		<script type="text/javascript">
		<!--
		// 定义一个投票总人数
		var totalPeople = ${vote.counts!'0'};
		$(function() {
			// 所有人可见
			<#if (voted || vote.visible)>
				var pollBar = $(".options").find(".pollPer");
				pollBar.animate({"width" : "290px"}, 2500);
				
				var nowNum, totalNum = 0;
				pollBar.find(".perNum").each(function() {
					nowNum = parseInt($(this).text());
					//console.info("每个投票项后面的投票数：" + nowNum);
					totalNum += nowNum;
				});
				//console.info("投票总数：" + totalNum);
				//console.info("投票人数：" + totalPeople);
				
				pollBar.find(".perNum").each(function() {
					nowNum = parseInt($(this).text());
					//console.info("当前投票人数：" + nowNum);
					
					if (isNaN(nowNum/totalNum)) {
						$(this).siblings(".perN").text("0%");
						$(this).siblings(".perBox").children("i").css("width", "1%");
					} else {
						// 单选
						<#if (vote.type)>
							$(this).siblings(".perN").text(parseFloat((nowNum / totalNum) * 100).toFixed(1) + "%");
							$(this).siblings(".perBox").children("i").css("width", parseFloat((nowNum / totalNum) * 100).toFixed(1) + "%");
						// 多选
						<#else>
							$(this).siblings(".perN").text(parseFloat((nowNum / totalPeople) * 100).toFixed(1) + "%");
							$(this).siblings(".perBox").children("i").css("width", parseFloat((nowNum / totalPeople) * 100).toFixed(1) + "%");
						</#if>
					}
				});
			</#if>
			
			// 将所有radio和checkbox屏蔽
			djs('${Util.getDateByLong(vote.endTime)?string("MM/dd/yyyy HH:mm:ss")}', function() {
				$(":radio, :checkbox").attr("disabled", "disabled");
			});
			
			// 是否已投
			<#if voted>$(":radio, :checkbox").attr("disabled", "disabled");</#if>
			
			// 提交
			$("#vote_submit").bind("click", function() {
				var This = $(this);
				var a = new Array();
				$("input[name='content']:checked").each(function() {
					a.push($(this).val());
				});
				$.post("voter.do", {
					"voter.voteId" : ${vote.voteId!},
					"voter.voteItemId" : a.join(',')
				}, function(data) {
					if ("error" == data) {
						errorTip("请不要重复投票！");
					} else if ("null" == data) {
						errorTip("没有选择投票项！");
					} else if (0 != data) {
						succTip("投票成功！");
						
						// 根据后台返回值更新页面上的参与人数
						$("#vote_count").html(data);
						
						// 定义一个总投票数
						var totalNum = 0;
						// 遍历获取投票总数
						This.parents(".options").find(".perNum").each(function() {
							// 获取当前的投票数
							var nowNum = parseInt($(this).text());
							// 如果选择了，先将本身加1，再加到投票总数上
							if ($(this).parent(".pollPer").prev().children("input").attr("checked")) {
								nowNum += 1;
							}
							totalNum += nowNum;
						});
						// console.info("投票总数：" + totalNum);
						// 投票成功将投票人数+1
						// totalPeople += 1;
						totalPeople = data;
						// console.info("投票总人数：" + totalPeople);
						
						// 重新遍历将本身加1
						This.parents(".options").find(".perNum").each(function() {
							// 获取当前的投票数
							var nowNum = parseInt($(this).text());
							// 如果选择就将本身加1
							if ($(this).parent(".pollPer").prev().children("input").attr("checked")) {
								nowNum += 1;
								
								// 寻找同级的元素（this = perNum）
								$(this).siblings(".perVoted").html("已投");
							}
							// 单选
							<#if (vote.type)>
								// 设置进度条的显示百分比
								$(this).text(nowNum).next(".perN").text(parseFloat((nowNum / totalNum) * 100).toFixed(1) + "%");
								// 设置进度条的百分比宽度
								$(this).prev(".perBox").children("i").css("width", parseFloat((nowNum / totalNum) * 100).toFixed(1) + "%");
							// 多选
							<#else>
								// 设置进度条的显示百分比
								$(this).text(nowNum).next(".perN").text(parseFloat((nowNum / totalPeople) * 100).toFixed(1) + "%");
								// 设置进度条的百分比宽度
								$(this).prev(".perBox").children("i").css("width", parseFloat((nowNum / totalPeople) * 100).toFixed(1) + "%");
							</#if>
						});
						
						// 先将前一个按钮隐藏，再修改后一个按钮的文字
						This.parents(".options").find(".pollPer").animate({"width":"290px"}, 300, function() {
							This.hide().prev(".disPollBtn").show().text("已投票");
						});
					}
				});
			});
		
			// 消息
			var timeID;
			$(function() {
				resetMessageCount();
			});
			
			window.onfocus = function(){
				timeID = window.setInterval('resetMessageCount()',300000);	
			};
			window.onblur = function(){
				window.clearInterval(timeID);
			};
		});
		-->
		</script>
	</body>
</html>
