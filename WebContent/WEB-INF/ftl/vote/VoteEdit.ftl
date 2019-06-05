<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="shortcut icon" href="${ContextPath}favicon.ico" type="image/x-icon" />
	    <title><#if (0 == vote.voteId!)>发起<#else>修改</#if>投票</title>
	    <link rel="stylesheet" href="${ContextPath}css/common.css" type="text/css">
	    <link rel="stylesheet" href="${ContextPath}css/poll.css" type="text/css">
	</head>
	<body>
		<#include "VoteHeader.ftl">
		<div class="main container">
			<div class="postWrap border">
				<h3 class="indexT">
					<a href="javascript:history.go(-1);" class="fl backLink"></a>
					<#if (0 == vote.voteId!)>发起<#else>修改</#if>投票
		        </h3>
		        <@s.actionerror cssClass="actionError" />
		        <form id="voteForm">
					<div class="infoSection">
						<ul>
							<li><label class="infoT">投票主题：</label> <input type="text" class="ipt iptW584" placeholder="主题最长32个汉字" maxlength="32" name="vote.title" value="${vote.title!?html}" /></li>
							<li><label class="infoT">投票说明：</label> <textarea class="pollC" placeholder="最长200个汉字" maxlength="200" name="vote.description">${vote.description!?html}</textarea>
							<li><label class="infoT">截止时间：</label>
							<input id="endtime1" type="text" class="ipt Wdate" name="voteEndTime" value="<#if vote.endTime??>${Util.getDateByLong(vote.endTime)?string("yyyy-MM-dd HH:mm")}</#if>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', minDate: '${.now?string("yyyy-MM-dd HH:mm")}', maxDate: '%y-%M-{%d + 90} %H:%m:%s'})" />
							<#--<input id="endtime2" type="hidden" name="vote.endTime" />-->
							</li>
							<li><label class="infoT">选择模式：</label>
								<div class="fl radioItems">
									<span class="radioItem"><input type="radio" class="imgRadio" name="vote.type" value="true" <#if (vote.type)>checked</#if> id="radio1" /><label for="radio1">单选</label></span>
									<span class="radioItem"><input type="radio" class="imgRadio" name="vote.type" value="false" <#if (!vote.type)>checked</#if> id="radio2" /><label for="radio2">多选</label></span>
								</div>
							</li>
							<li><label class="infoT">投票结果：</label>
								<div class="fl radioItems">
									<span class="radioItem"><input type="radio" class="imgRadio" name="vote.visible" value="true" <#if (vote.visible)>checked</#if> id="radio3" /><label for="radio3">所有人可见</label></span>
									<span class="radioItem"><input type="radio" class="imgRadio" name="vote.visible" value="false" <#if (!vote.visible)>checked</#if> id="radio4" /><label for="radio4">投票后可见</label></span>
								</div>
							</li>
							<li>
								<label class="infoT">投票选项：</label>
								<div id="voteitem" class="fl location">
									<#if vote?? && voteItemList?? && 0 < voteItemList.size()>
										<#list voteItemList as vi>
											<p class="clr"><span class="fl lNum">${vi_index + 1}，</span><input type="text" class="ipt iptW435" value="${vi.name!}" disabled="disabled" /></p>
										</#list>
									<#else>
										<p class="clr"><span class="fl lNum">1</span><input type="text" class="ipt iptW435" placeholder="最长20个汉字" maxlength="20" name="content" /></p>
										<p class="clr"><span class="fl lNum">2</span><input type="text" class="ipt iptW435" placeholder="最长20个汉字" maxlength="20" name="content" /><a href="javascript:;" class="del delItem"></a></p>
										<a href="javascript:;" class="addItem" onclick="return addVoteItem('voteitem');">添加选项</a>
									</#if>
								</div>
							</li>
						</ul>
						<div class="btBtn">
							<input type="button" class="blueBtn initiateBtn" id="vote_submit" value="<#if (0 == vote.voteId!)>发起<#else>修改</#if>投票" />
							<input type="button" class="grayBtn" onClick="window.history.go(-1);" value="取消" />
						</div>
					</div>
					<input type="hidden" name="vote.voteId" value="${vote.voteId!'0'}" />
					<input type="hidden" name="vote.userId" value="${vote.userId!}" />
					<input type="hidden" name="vote.counts" value="${vote.counts!}" />
					<input type="hidden" name="vote.createTime" value="${vote.createTime!}" />
					<input type="hidden" name="vote.status" value="${vote.status?string('true', 'false')}" />
					<input type="hidden" name="containerType" value="${containerType!}" />
					<input type="hidden" name="containerId" value="${containerId!}" />
					<input type="hidden" name="appCode" value="${appCode!}" />
					<input type="hidden" name="appId" value="${appId!}" />
					<input type="hidden" name="appName" value="${appName!}" />
				</form>
			</div>
			<div class="imgShadow"><img class="imgShadowSize3" src="images/imgShadow.jpg"></div>
		</div>
		<#include "VoteFooter.ftl">
		<script src="js/common/jquery.js" type="text/javascript"></script> 
		<script src="js/common/common.js" type="text/javascript"></script> 
		<script src="My97DatePicker/WdatePicker.js"></script>
		<script src="js/poll.js" type="text/javascript"></script> 
		<!--[if IE 6]>
		<script src="js/common/DD_belatedPNG.js" type="text/javascript"></script>
		<script type="text/javascript">
			DD_belatedPNG.fix('.pngfix, a.pngfix:hover');
		</script>
		<![endif]-->
		<script type="text/javascript">
		<!--
		$(function() {
			var titleNode = $("input[name='vote.title']");
			var contentNode = $("input[name='content']");
			var endTimeNode = $("#endtime1");
			$("#vote_submit").bind("click", function() {
				if ("" == $.trim(titleNode.val())) {
					errorTip("投票主题不能为空！");
					return false;
				}
				<#if (0 == vote.voteId!)>
					if ("" == $.trim(contentNode.val())) {
						errorTip("第一个投票项不能为空！");
						return false;
					//} else {
						//verifySame(function() {
							//errorTip("不能创建相同的投票项！");
						//});
						//return false;
					}
				</#if>
				if ("" == $.trim(endTimeNode.val())) {
					errorTip("截止时间不能为空！");
					return false;
				}
				//改成Long类型，不需要此行代码了。
				//$("#endtime2").val($("#endtime1").val() + ":00");
				
				// TODO 需要循环判断每个投票项
				// contentNode.each(function() {
				// 	if ("" == $(this).val()) {
				// }
				
				// 提交
				$.ajax({
					type: "POST",
					url: "vote_save.do",
					data: $("#voteForm").serialize(),
					success: function(data) {
						if ("a" == data) {
							errorTip("投票主题不能为空！");
						} else if ("b" == data) {
							errorTip("主题最长不能超过32个字符！");
						} else if ("c" == data) {
							errorTip("截止时间不能为空！");
						} else if ("d" == data) {
							errorTip("截止时间没有设置！");
						} else if ("e" == data) {
							errorTip("截止时间不得超过当前日期90天！");
						} else if ("f" == data) {
							errorTip("容器内型不能为空！");
						} else if ("g" == data) {
							errorTip("投票项不能有重复！");
						} else if ("unknown" == data) {
							errorTip("发生未知错误，请稍后重试！");
						}else if ("100" == data) {
							errorTip("请输入结束日期！");
						}else if ("101" == data) {
							errorTip("日期不能解析！");
						} else {
							succTipBtn("投票<#if (0 == vote.voteId!)>创建<#else>修改</#if>成功！", "vote_show.do?vote.voteId=" + data);
						}
					}
				});
			});
		});
		// 添加投票项
		function addVoteItem(obj) {
			var c = $("input[name='content']").length;
			if (15 == c) {
				errorTip("您只能添加15个投票项！");
			} else {
				// 当文本框中的值不为空的是时候再去验证重复
				if ("" != $("input[name='content']").val()) {
					verifySame(function(obj) {
						if(obj.parent().find(".redTxt").length == 0){
							obj.parent().append('<span class="redTxt">不能创建相同的投票选项</span>');
						}
					});
				}
				c = c + 1;
				var html = '<p class="clr"><span class="fl lNum">' + c + '</span><input type="text" class="ipt iptW435" placeholder="最长20个汉字" maxlength="20" name="content" /><a href="javascript:;" class="del delItem"></a></p>';
				$(".addItem").before(html);
			}
			return false;
		};
		//-->
		</script>
	</body>
</html>
