/*
 * name: 输入框焦点切换效果
 * author: zhangliang
 * date: 2013.9.27
 */ 
(function($){
    $.fn.replyBox = function(options){
        var defaults = {
            par: ".textArea", //输入框的父元素
            cur: "textAreaH", //获取焦点时添加class
            tLeft: "0px", //提示文字距离父元素的左坐标
            tTop: "0px", //右坐标
            fontSize: "14px" //提示文字的大小
        };
        var options = $.extend(defaults, options);
        return this.each(function(){
            var _this = $(this),
                par = options.par, 
                cur = options.cur,
                l = options.tLeft,
                t = options.tTop,
                fs = options.fontSize;
                _this.siblings(".tipsWrap").css({"left":l,"top":t,"font-size":fs});
            //当点击到提示层时显示和获取焦点一样的效果,并让输入框获得焦点
            _this.siblings(".tipsWrap").click(function(){
                _this.parents(par).addClass(cur).end().focus();
                _this.siblings(".tipsWrap").hide();
            });
            //输入框焦点状态切换
            _this.focus(function(){
                _this.parents(par).addClass(cur);
                _this.siblings(".tipsWrap").hide();
            });
            var oldValue = _this.text() ? _this.text():_this.val();
            if(oldValue != ""){
                _this.siblings(".tipsWrap").hide();
            }
            _this.blur(function(){
                var nowValue = _this.text() ? _this.text():_this.val();
                if(nowValue == ''){
                    _this.siblings(".tipsWrap").show();
                    _this.parents(par).removeClass(cur);
                }
            });
        });
    };    
})(jQuery);
