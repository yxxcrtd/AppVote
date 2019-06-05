//点击回复按钮弹出回复框
var z_replyPrevID = null;
function replay(id,url){
	html  = '<dl id="r_'+id+'">';
	html += '<dt><img src="'+url+'" width="50" height="50" /></dt>';
	html += '<dd>';
	html += '<div class="replyArea clearfix">';
	html += '<span class="replyAreaArrow"></span>';
	html += '<span class="replyAreaT"></span>';
	html += '<textarea class="replyAreaC"></textarea>';
    html += '<span class="replyAreaB"></span>';
    html += '<span class="tipsWrap">我要回复</span>';
	html += '</div>';
	html += '<p class="textAreaTips mt10"><a href="javascript:;" class="fr publishBtn">发表</a>还可以输入<span id="replyTextNum">300</span>个字</p>';
	html += '</dd>';
	html += '<span class="replyArrow"></span>';
	html += '</dl>';
    var nowId = "#r"+id;
	$(nowId).after(html);
    replyTips();
    if(z_replyPrevID){
        $("#r_"+z_replyPrevID).remove();
    }
    z_replyPrevID = id;
}
$(function(){
    //赞
	$("a.o2").click(function(){
        $(".operateCont").toggle(100).children(".praise").show();
		var o2Tips = $("a.o2").text();
		if(o2Tips == "赞"){
			$(this).text("已赞").addClass("hover");
		}else{
			$(this).text("赞").removeClass("hover");
		}
		if($(".praise").is(":visible")){
			$(".operateCont ul").hide();
		}
	});
	$(".praise").click(function(){
		$(".operateCont ul").toggle(100);
	});
	//收藏
	$("a.o3").click(function(){
		var o3Tips = $("a.o3").children("b").text();
		$(this).toggleClass("hover");
		if(o3Tips == "收藏"){
			$("span.operateTips").show().children("span").text("收藏成功！").animate({"display":"block"}, 1000,function(){
				$("span.operateTips").hide();
			});
			$(this).children("b").text("取消收藏");
		}else{
			$("span.operateTips").show().children("span").text("取消成功！").animate({"display":"block"}, 1000,function(){
				$("span.operateTips").hide();
			});
			$(this).children("b").text("收藏");
		}
	});
    
    //回复框输入字数
	var len = 0;
	function gbLen(obj,target){
		obj.keyup(function(){
			var str = obj.text();
			var reg = /[^\u4e00-\u9fa5]/;
			if(reg.test(str)){
				len +=2;
			}else{
				len++;
			}
			len2 = parseInt(300-len);
			target.text(len2);
		});
	};
	gbLen($(".textAreaC"),$("#textNum"));
	gbLen($(".replyAreaC"),$("#replyTextNum"));
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
	slide($(".textSlide a"),$(".textWrap"));
	slide($(".videoS1 a"),$(".videoW1"));
	slide($(".videoS2 a"),$(".videoW2"));
    //看过该文章的人轮播
	function slideRead(next,prev,wrap,num){
		var i =0, time =600;
		var distance = wrap.children("ul").width();
		next.click(function(){
			if(i>=num-1){	
				i=-1;	
			}
			i++;
			wrap.css("position","relative").animate({left:-distance*i},time);	
		});
		prev.click(function(){
			i--;
			if(i<0) i=num-1;
			wrap.css("position","relative").animate({left:-distance*i},time);
			
		});
	};
    //新闻轮播
    function slideRead2(next,prev,wrap){
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
    slideRead2("ImgSlideNext","ImgSlidePrev","readThis");
    //右侧点击展开更多
	$(".unFold").click(function(){
		$(this).parent("p").prev("p").css("height","auto");
		$(this).hide().siblings(".unFold1").css("display","block");
	});
	$(".unFold1").click(function(){
		$(this).parent("p").prev("p").removeAttr("style");
		$(this).hide().siblings(".unFold").css("display","block");
	});
	//说明更多
	$(".summaryBtn").click(function(){
		$(this).hide().siblings("a").show().parent().prev().css("height","auto");
	});
	$(".summaryBtn1").click(function(){
		$(this).hide().siblings("a").show().parent().prev().removeAttr("style");
	});
    //举报弹出
    $("a.o0").click(function(){
        report($(".report"),$(".close,.reportCancel,.closeTips"),$(".reportSub"));
    });
});
//评论
$(".textAreaC").replyBox({
    par: ".textArea",
    cur: "textAreaH",
    tLeft: "14px",
    tTop: "2px"
});
//回复,动态添加完后再执行此代码
function replyTips(){
    $(".replyAreaC").replyBox({
        par: ".replyArea",
        cur: "replyAreaH",
        tLeft: "14px",
        tTop: "2px"
    });
};
