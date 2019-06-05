$(function(){
	$.extend({
	   makePageClik:function(){
		    var url = arguments[0]; //ajax请求url
		    var needAjax = arguments[1];//需要做分页查询的页的容器id
		    var who = arguments[2];
		    var objType = arguments[3];
		    var objId = arguments[4];
		    
		   //直接点击数字
			$('.w_pagination a:not(.w_pre,.w_next)').click(function(){
				doActivityList($(this).text(),url,needAjax, who, objType, objId);
			});
			//点击上一页
			$('.w_pagination a[class=w_pre]').click(function(){
				if(1 < parseInt($('.w_pagination a[class=w_currPage]').text())){
					doActivityList(parseInt($('.w_pagination a[class=w_currPage]').text())-1, url, needAjax, who, objType, objId);
				}
			});
			//点击下一页
			$('.w_pagination a[class=w_next]').click(function(){
				 doActivityList(parseInt($('.w_pagination a[class=w_currPage]').text())+1, url, needAjax, who, objType, objId);
			});
			//点击go
			$('.w_goBtn').click(function(){
				if(!/^\d+$/.test($('#goPage').val())){
					alert("请输入一个正整数");
					return false;
				}
				if(parseInt($('#goPage').val())<1||parseInt($('#goPage').val())>parseInt($('#goPage').attr("maxValue"))){
					alert("请输入一个大于1且小于最大页数的整数!");
					return false;
				}
				doActivityList(parseInt($('#goPage').val()), url, needAjax, who, objType, objId);
			})
		}
	});
	//$.makePageClik();
	$('#goPage').val(1); //刷新页面返回第一页  页码重置为1
})

function doActivityList(){
	var currentPage = arguments[0];
	var requesturl = arguments[1];
	var needAjax = arguments[2];
	var who = arguments[3];
    var objType = arguments[4];
    var objId = arguments[5];
	$.ajax({
		url:requesturl,
		data:{
			"currentPageNo":currentPage,
			"who" : who,
			"objType" : objType,
			"objId" : objId
		},
		type:"POST",
		success:function(msg){ //拿到数字点击事件处理
			$('#'+needAjax+'').empty();
			$('#'+needAjax+'').html(msg);
			$('#goPage').val(currentPage);
	}});
}