//弹出层定位
function rule(target){
    bodyWidth = $("body").width(),
    bodyHeight = $("body").height(),
    screenWidth = $(window).width()/2,
    screenHeight = $(window).height()/2,
	screenHall  = $(window).height(),
    thisWidth = target.width()/2,
    thisHeight = target.height()/2,
    scrollHeight = $("body").scrollTop();
	if(bodyHeight<screenHall){
		bodyHeight = screenHall;
	}
}
//滚动条
function scrollBarShow(obj){
    obj.jscroll({ W:"7px"
        ,BgUrl:"url("+ctx+"images/scrollBar.gif)"
        ,Bg:"right 0 repeat-y"
        ,Bar:{Bd:{Out:"#989c9e",Hover:"#a3a8aa"}
        ,Bg:{Out:"-21px 0 repeat-y",Hover:"-28px 0 repeat-y",Focus:"-35px 0 repeat-y"}}
        ,Btn:{btn:true
            ,uBg:{Out:"0 0",Hover:"-7px 0",Focus:"-14px 0"}
            ,dBg:{Out:"0 -8px",Hover:"-7px -8px",Focus:"-14px -8px"}
        }
        ,Fn:function(){}
    });
}
//关闭
function closePop(target){
	$("#layoutBg").remove();
    target.hide();
}
//删除弹出层
function removePop(target){
    $("#layoutBg").remove();
    target.remove();
}
//举报成功提示
function reportSucc(target){
    target.hide();
    $(".reportTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    if($(".reportTips:visible")){
        setTimeout('$(".reportTips").hide();$("#layoutBg").remove();', 1000);
    }
}
//举报
function report(reporT,callbak,argument){
    $("body").append(reporT);
    rule($(".report"));
    $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    var target = $(".report");
    target.show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    window.onresize = function(){
        rule(target);
        target.css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
    };
    target.find("label").live("click",function(){
        $(this).addClass("reportChecked").parent("span").siblings("span").children("label").removeClass("reportChecked").addClass("reportUnCheck");
    });
    $(".close,.reportCancel").live("click",function(){
        removePop(target);
    });
    $(".reportSub").click(function(){
        if(typeof callbak == "function"){
            callbak(argument);
        }
        target.remove();
    });
}
/*
 * 操作成功提示弹出层
 * 参数txt:提示文字
 */
function succTip(txt){
    if(!txt) txt = "操作成功！";
    var succ = '<div class="successTips"><a href="javascript:;" class="sTipsClose"></a><span class="sTipsText"><span class="sTipsIcon"></span><span class="sTipsT">'+txt+'</span></span></div>';
    $("body").append(succ);
    rule($(".successTips"));
    $(".successTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    setTimeout(function(){
        $(".successTips,#layoutBg").remove();
    }, 1000);
	
	$(".sTipsClose").die().live("click",function(){
        removePop($(".successTips"));
    });
}

/*
 * confirm 提示框
 * 参数txt：提示文字
 */
function confirmTip(txt,callbak,argument){
    if(!txt) txt = "确定要进行此操作吗？";
    var con = '<div class="deleteTips"><h3 class="textIn"><a href="javascript:;" class="close"></a>提示</h3><div class="reportCont"><p class="deleteCont"><em class="warning"></em><em class="warningTxt">'+txt+'</em></p></div><div class="reportSubmit"><a href="javascript:;" class="delSub">确定</a> <a href="javascript:;" class="reportCancel">取消</a></div></div>';
    $("body").append(con+'<div id="layoutBg"></div>');
    rule($(".deleteTips"));
    $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    $(".deleteTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    window.onresize = function(){
        rule($(".deleteTips"));
        $(".deleteTips").css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
        $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    };
    $(".close,.reportCancel").die("click").live("click",function(){
        removePop($(".deleteTips"));
    });
    $(".delSub").die("click").live("click",function(){
        removePop($(".deleteTips"));
        if(typeof callbak == "function"){
            callbak(argument);
        }
    });
}
/*
 * 错误提示
 */
function errorTip(txt){
    if(!txt) txt = "操作出现错误";
    var con = '<div class="deleteTips"><h3 class="textIn"><a href="javascript:;" class="close"></a>提示</h3><div class="reportCont"><p class="deleteCont"><em class="warning"></em><em class="warningTxt">'+txt+'</em></p></div><div class="reportSubmit"><a href="javascript:;" class="delSub1">确定</a></div></div>';
    $("body").append(con+'<div id="layoutBg"></div>');
    rule($(".deleteTips"));
    $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    $(".deleteTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    window.onresize = function(){
        rule($(".deleteTips"));
        $(".deleteTips").css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
        $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    };
    $(".close").live("click",function(){
        removePop($(".deleteTips"));
    });
    $(".delSub1").live("click",function(){
        removePop($(".deleteTips"));
    });
}
function succTipBtn(txt,url){
    if(!txt) txt = "操作成功";
    if(!url) url = "javascript:;";
    var con = '<div class="deleteTips"><h3 class="textIn"><a href="javascript:;" class="close"></a>提示</h3><div class="reportCont"><p class="deleteCont sucCont"><em class="sucIcon"></em><em class="warningTxt sucTxt">'+txt+'</em></p></div><div class="reportSubmit"><a href="'+url+'" class="delSub1">确定</a></div></div>';
    $("body").append(con+'<div id="layoutBg"></div>');
    rule($(".deleteTips"));
    $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    $(".deleteTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    window.onresize = function(){
        rule($(".deleteTips"));
        $(".deleteTips").css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
        $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
    };
    $(".close").live("click",function(){
        removePop($(".deleteTips"));
    });
    $(".delSub1").live("click",function(){
        removePop($(".deleteTips"));
    });
}
$(function(){
    //点击按钮弹出
	function clickShow1(obj,targetObj){
		obj.click(function(event){
			event.stopPropagation();
			targetObj.toggle();
			//隐藏其他弹出框
			$(".topSearchSelect,.loginBox").not(targetObj).hide();
		});		
		targetObj.click(function(event){
			event.stopPropagation();
		});
	}
	clickShow1($(".loginBtn"), $(".loginBox"));
	clickShow1($(".topSearchOption"), $(".topSearchSelect"));
	document.onclick = function(){
		$(".topSearchSelect,.loginBox").hide();
	};
	//搜索下拉赋值及鼠标滑动效果
	var $option = $(".topSearchOption");
	var $select = $(".topSearchSelect");
    $option.hover(function(){
        $(this).find("span").addClass("arrow3");
    },function(){
        $(this).find("span").removeClass("arrow3");
    });
	$select.children("li").children("a").click(function(){
		var value = $(this).text(),
            typename = $(this).attr("type");
		$option.children("em").text(value);
		$select.hide();
        $("#topSearchType").val(typename);
	});
	//登录后状态效果
	var logged = $(".setWrap");
	logged.hover(function(){
		$(this).addClass("setWrapH");
	},function(){
		$(this).removeClass("setWrapH");
	});
    //顶部学科导航
    $(".topSubject").hover(function(){
        $(this).children(".topSubjectBox").show().css("opacity",0.9).end().children(".subjectText").addClass("subjectTextH");
    },function(){
        $(this).children(".topSubjectBox").hide().end().children(".subjectText").removeClass("subjectTextH");
    });    
    //回到顶部
    $("body").append('<div class="returnTop"><a href="javascript:;" id="returnTop"></a></div>');
    $(window).scroll(function(){
        var scrolltop = $(window).scrollTop();
        if(scrolltop >200){
            $(".returnTop").fadeIn(300);
        }else{
            $(".returnTop").fadeOut(300);
        }
    });
    $("#returnTop").click(function(){
        $('html,body').animate({scrollTop:0},300);
    });
    //透明度
    $(".messVideoBg,.personalTopBg,.vImgS1Bg").css("opacity",0.3);
    //分页
    $(".z_pageC").live({"mouseover":function(){
        $(".z_pageList").show();
		//scrollBarShow($(".z_pageWrap"));
    },"mouseout":function(){
        $(".z_pageList").hide();
    }
    });
});

//倒计时方法
function djs(time,callbak){
    var date = new Date(time);
    var now = new Date();
    now = now.getTime();
    var d = parseInt(date.getTime()) - parseInt(now);
    var djs1 = setTimeout('djs("'+time+'",'+callbak+')', 1000);
    //console.log(callbak)
    if(d>0){ 
        d = parseInt(d / 1000);
        var day = parseInt(d / 86400);
        
        d = d % 86400;
        var hour = parseInt(d / 3600);
        d = d % 3600;
        var minute = parseInt(d / 60);
        d = d % 60;
        var second = parseInt(d);
        
        //console.info("剩余天：" + day);
        //console.info("剩余小时：" + hour);
        //console.info("剩余分钟：" + minute);
        if (0 < day) {
        	$(".pollTime").children("span.pollDjs").html("距离投票结束还有：" + day + "天" + hour + "小时" + minute + "分钟");
        } else if (0 < hour) {
        	$(".pollTime").children("span.pollDjs").html("距离投票结束还有：" + hour + "小时" + minute + "分钟" + second + "秒");
        } else {
        	$(".pollTime").children("span.pollDjs").html("距离投票结束还有：" + minute + "分钟" + second + "秒");
        }
    } else {
        clearTimeout(djs1);
        $(".pollTime").children("span.pollDjs").html("投票已结束").css("color", "#F00");
        //console.log(callbak);
        if(typeof callbak == "function"){
            callbak();
        }
    }
}

//底部固定
function reHeight(){
	$(".container").removeAttr("style");
	var sHeight = $(window).height();
	var cHeight = $(".container").height()+75;
	var fHeight = $(".footer").outerHeight();
	if((cHeight+80) < sHeight){
		if($.browser.msie&&($.browser.version == "6.0")){
			$(".container").css({"height":"auto !important","height":sHeight-fHeight-165});
		}else{
			$(".container").css({"min-height":sHeight-fHeight-162});
		}
	}
}
reHeight();
$(window).resize(function(){
	reHeight();
});
/*
 * author: zhangliang
 * date:   2013.l0.18
 * checkAll：checkBox全选反选插件
 * checkAll使用方法：$(".kListBox").checkAll();
 * $(".kListBox"): 包含全选和单个复选框的父层元素
 */
(function($){
    $.fn.checkAll = function(options){
        var defaults = {
            allBtn: ".checkAll", //全选按钮元素
            boxBtn: ".checkBox", //复选框元素
            isCheck: "isChecked" //选中状态样式
        };
        var options = $.extend(defaults,options);
        return this.each(function(){
            var _this = $(this),//指向当前调用此方法的对象
               _allBtn = _this.find(options.allBtn),//this对象下的选择按钮
               _boxBtn = _this.find(options.boxBtn).not(options.allBtn),//this对象下的选择框
               _isCheck = options.isCheck;
            //判断全选按钮的状态，如果处于选中状态，box全部选中，否则不选中
            _allBtn.unbind("click").bind("click",function(){
                _allBtn.toggleClass(_isCheck);
                if($(this).hasClass(_isCheck)){
                    _boxBtn.addClass(_isCheck);
                }else{
                    _boxBtn.removeClass(_isCheck);
                }
            });
            //判断box的状态，如果有box处于没选择的状态，则全选按钮不选中
            function allChk(){
                var i=0,len = _boxBtn.length;
                _boxBtn.each(function(){
                    if($(this).hasClass(_isCheck)){
                        ++i;
                    }
                });
                if(i==len){
                    _allBtn.addClass(_isCheck);
                }else{
                    _allBtn.removeClass(_isCheck);
                }
            }
            allChk();
            _boxBtn.click(function(){
                $(this).toggleClass(_isCheck);
                //判断checkBox是否有未选中的元素,len为box所有元素的长度，i为选中元素的长度
                allChk();
            });
        });
    }
})(jQuery);


/**
 * messager
 */
(function($){
	//显示窗口
	function show(win,time){
		win.w.show().find('.popOpt a:first').focus();
		fixPosition(win);
		$(window).resize(function(){
			fixPosition(win);
		});
		var timer = null;
		if(time>0){
			timer= setTimeout(function(){
				win.w.hide();
				win.bg.hide();
			}, time);
		}
	}
	//固定窗口位置
	function fixPosition(win){
		win.w.css({left:($(window).width()-win.w.width())/2,top:($(window).height()-win.w.height())/2});
		win.bg.css({"width":$("body").width(),"height":$("body").height()});
	}
	//隐藏窗口
	function hide(win){
		win.hide().remove();
	}
	//创建窗口
	function createDialog(title, content, buttons){
		var win=$('<div class="popLayer"></div>').appendTo('body');
		var layoutBg=$('<div id="layoutBg"></div>').appendTo('body')
		.css({"width":$("body").width(),"height":$("body").height()});//遮罩层
		var title=$('<h3>'+title+'</h3>').appendTo(win);
		$('<a href="javascript:;" class="closePop"></a>').bind('click',function(){hide(win);hide(layoutBg);})
		.appendTo(title);
		win.append('<div class="popMain"><p class="popCont">'+content+'</p></div>');
		if (buttons){
			var tb = $('<div class="popOpt"></div>').appendTo(win);	
			for(var b in buttons){
				$('<a class="popBtn subBtn" href="javascript:;"></a>')
				.text(b).bind('click',eval(buttons[b]))
				.appendTo(tb);
			}
		}
		return {w:win,bg:layoutBg};//包含窗口和遮罩层两部分
	}
	
	$.messager={
		alert:function(title,msg,icon,fn){
			title=(title&&title!='')?title:'提示';
			var iconClass='';
			switch(icon){
				case 'error':iconClass='popIcon errorTxt';
				break;
				case 'info':iconClass='popIcon infoTxt';
				break;
				case 'warning':iconClass='popIcon warnTxt';
				break;
				case 'ok':iconClass='popIcon okTxt';
				break;
			}
			var content = '<span class="'+iconClass+'">' + msg + '<span>';
			var buttons={};
			buttons[$.messager.defaults.ok]=function(){
				hide(win.w);
				hide(win.bg);
				if (fn){
					fn();
				}	
			}
			var win = createDialog(title,content,buttons);
			show(win);
		},
		confirm:function(title,msg,fn){
			title=(title&&title!='')?title:'确认';
			
		}
			
	};
	$.messager.defaults = {
		ok: '确定',
		cancel: '取消'
	};
})(jQuery);	