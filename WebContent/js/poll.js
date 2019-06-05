$(function(){
	//删除当前选项
	$(".delItem").live("click",function(){
		$(this).parents("p").remove();
        voteQ();
	});
    //添加投票选项
    function voteQ(){
        $(".location").find(".lNum").each(function(i){
            $(this).text(i+1);
        });
    }
    /*$(".addItem").click(function(){
        var optLen = $(".clr").length;
        var voteHtml = '<p class="clr"><span class="fl lNum"></span><input type="text" value="" placeholder="最长20个汉字" class="ipt iptW435"><a class="del delItem" href="javascript:;"></a></p>';
        if(optLen<15){
            $(this).before(voteHtml);
            $(this).prev("p").children(".lNum").text(optLen+1);
        }else{
            errorTip("您只能添加15条记录");
        }

    });*/	
	//轮播
    function slideRead(next,prev,wrap){
        var i = 0, time = 500,
            oldNext = "."+next,
            newNext = next+"_on",
            oldPrev = "."+prev,
            newPrev = prev+"_on",
            wrap = "."+wrap,
            distance = $(wrap).children("ul").width();
		if($(wrap).children("ul").length<2){
			$(oldNext).hide();
			$(oldPrev).hide();
		}
        $("."+newNext).live("click",function(){
            i = 0;
            $(this).removeClass(newNext);
            $(this).siblings("a").addClass(newPrev);
            $(wrap).css("position","relative").animate({left:-distance*i},time);
        });
        $("."+newPrev).live("click",function(){
            i=1;
            $(this).removeClass(newPrev);
            $(this).siblings("a").addClass(newNext);
            $(wrap).css("position","relative").animate({left:-distance*i},time);
        });
    };
    slideRead("ImgSlideNext","ImgSlidePrev","readThis");
	
	//右侧新闻轮播效果
	function slide(obj,wrap){
		var time = 300;
		var distance = wrap.children("ul").width();
		obj.each(function(i) {
            $(this).hover(function(){
				if($(this).hasClass("active")) return false;
				$(this).addClass("active").siblings().removeClass("active");
				wrap.css("position","relative").animate({left:-distance*i},time);
			});
        });
	};
	slide($(".numSlide a"),$(".hotSlideList"));
	
	//图片单选框
	$(".imgRadio").click(function(){
		$(this).parents(".radioItems").find(".imgRadio").removeClass("isChecked");
		$(this).addClass("isChecked");
	});
	
	//鼠标滑过投票区域
	$(".voteBox").live({
        "mouseover": function(){
			$(this).addClass("voteBoxOn").find(".trash, .zEdit").show();
        },
        "mouseout": function(){
			$(this).removeClass("voteBoxOn").find(".trash, .zEdit").hide();
        }
    });

	//删除
	$(".voteBox .trash").live("click", function() {
		var This = $(this);
		var cur = 0;
		cur = parseInt(This.siblings(".did").text());
		// console.info(cur);
		confirmTip("确定要删除吗？", function() {
			$.get("vote_del.do", {"vote.voteId" : cur}, function(data) {
				if ("ok" == data) {
					This.parents(".voteBox").remove();
				} else {
					errorTip("发生未知错误，请稍后重试！");
				}
			});
		});
	});
	
	//删除投票
	$(".delVote").live("click", function() {
		var This = $(this);
		var cur = 0;
		cur = parseInt(This.siblings("#did").text());
		//console.info(cur);
        confirmTip("确定要删除吗？", function() {
			$.get("vote_del.do", {"vote.voteId" : cur}, function(data) {
				if ("ok" == data) {
					succTipBtn("删除成功！", "vote.do");
				} else {
					errorTip("发生未知错误，请稍后重试！");
				}
			});
		});
    });
	
	$(".options .imgRadio, .voteRadio, .voteCheck").click(function(){
		$(this).parents(".options").find(".disPollBtn").hide().end().find(".pollBtn").show();
	});
	
	//投票列表tab
	tabsShow(".pTabNav li",".votingList","tabOn");

	$(".pTabNav li").each(function(index) {
		$(this).click(function() {
			var liNode = $(this);
			$("div.contentin").removeClass("contentin").addClass("content");
			$("div.content").eq(index).removeClass("content").addClass("contentin");
			$(".pTabNav li.tabOn").removeClass("tabOn");
			liNode.addClass("tabOn");

			var currentPage = 1;
			// 获取数据
			$.ajax({
				type : "POST",
				url : "vote_ajax.do",
				data : {
					"currentPageNo" : currentPage,
					"who" : liNode.attr("id"),
					"vote.userId" : liNode.attr("uid")
				},
				success : function(data) {
					$("div.content").empty();
					$("div.contentin").html(data);
				}
			});
		});
	});
});

// 滑动门
function tabsShow(obj,target,curr){
    $(obj).click(function(){
		var index = $(this).index();
		$(this).addClass(curr).siblings().removeClass(curr);
		$(target).hide().eq(index).show();
	});
}

function verifySame(callbak){
	var arr = [];
	var $ipt = $(".location").find(".ipt");
	$ipt.each(function(i){
		var txt = $(this).val();
		if(findSame(txt,arr)){
			callbak && callbak($(this));
		}else{
			arr.push(txt);
			$(this).parent().find(".redTxt").remove();
		}
	});
	function findSame(n,arr){
		for(var i=0;i<arr.length;i++){
			if(n == arr[i]){
				return true;
			}
		}
	}
}
