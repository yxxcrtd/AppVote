<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
	    <title>信息提示</title>
	    <link rel="stylesheet" href="${ContextPath}css/common.css" type="text/css">
	    <link rel="stylesheet" href="${ContextPath}css/poll.css" type="text/css">
	</head>

	<body>
		<#include "VoteHeader.ftl">

		<#--正文 Start-->
		<div class="main container">
			<div class="postWrap border">
				<h3 class="indexT">
					<a href="javascript:history.go(-1);" class="fl backLink"></a>
					信息提示
		        </h3>
		        <br />
		        <br />
		        <@s.actionerror cssClass="actionError" />
			</div>
			<div class="imgShadow"><img class="imgShadowSize3" src="images/imgShadow.jpg"></div>
		</div>
		<#--正文 End-->
		
		<#include "VoteFooter.ftl">
	</body>
</html>