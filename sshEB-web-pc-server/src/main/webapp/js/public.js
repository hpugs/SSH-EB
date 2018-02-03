$(function(){
	scroll();
	initNav();
	keyEnter();
	$(".search-go").click(function(){
		$(this).parent().addClass("open");
	});
	searchCode();
})
/**
 * 返回顶部
 */
function scroll(){
	var win = $(window); //得到窗口对象
	var sc = $(document);//得到document文档对象。
	win.scroll(function(){
		if(sc.scrollTop() >= 600){
			$(".help-core .top").show();
		}else{
			$(".help-core .top").hide();
		}
	});
	$(".help-core .top").click(function(){
		$("html,body").animate({scrollTop:0}, 1000);
	});
}
/**
 * 初始化菜单选中
 */
function initNav(){
	var url = document.location.href;
	var flag = true;
	var item = null;
	var count = $(".head-public .nav li").length;
	for (var i=count-1; i>=0; i--) {
		item = $(".head-public .nav li")[i];
		if(0 < item.className.length && 0 < url.indexOf(item.className)){
			$(item).children("span").removeClass("nine");
			flag = false;
		}
	}
	if(flag) {
		$(item).children("span").removeClass("nine");
	}
}

/**
 * 回车事件监听
 */
function keyEnter(){
	$(".search-key").bind("keydown", function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {
	    	if(0 < $(".search-key").val().length){
	    		alert($(".search-key").val());
	    	}
        }    
	});
}

/**
 * 图片编码查询
 */
function searchCode(){
	$(".search-code-go").bind("click", function(){
    	if(0 < $(".search-code").val().length){
    		alert($(".search-code").val());
    	}
	});
	$(".search-code").bind("keydown", function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {
	    	if(0 < $(".search-code").val().length){
	    		alert($(".search-code").val());
	    	}
        }    
	});
}
/**
 * 弹出层图片
 */
function openZFloat(obj){
	tit = $(obj).attr("tit");
	path = $(obj).attr("ref");
	var html = '<div class="z-float" style="width: 100%;height: 100%;text-align: center;background-color: rgba(0,0,0,.6);position: fixed;top: 0;left: 0;z-index: 19930630;"><div class="z-float-value" style="width: 1200px;height: 100%;overflow-y: auto;display: inline-block;position: relative;">';
	html += '<img alt="'+tit+'" src="'+path+'" style="max-width: 100%;position: absolute;top: 0;left: 0;">';
	html += '</div></div>';
	$("body").append(html);
	$(".z-float").click(function() {
		$(this).remove();
	});
	
	width = $(".z-float-value").width();
	height = $(window).height();
	var child = $(".z-float-value img")[0];
	child_width = child.naturalWidth;
	child_height = child.naturalHeight;
	if(0 < child_width && width > child_width){
		leftInt = (width - child_width)/2;
		$(child).css("left", leftInt);
	}
	if(0 < child_height && height > child_height){
		topInt = (height - child_height)/2;
		$(child).css("top", topInt);
	}
}
/**
 * 弹出层图片--图片长、宽添加最大限制
 */
function openZFloatMax(obj){
	tit = $(obj).attr("tit");
	path = $(obj).attr("ref");
	var html = '<div class="z-float" style="width: 100%;height: 100%;text-align: center;background-color: rgba(0,0,0,.6);position: fixed;top: 0;left: 0;z-index: 19930630;"><div class="z-float-value" style="width: 1200px;height: 100%;text-align: center;display: inline-block;">';
	html += '<img alt="'+tit+'" src="'+path+'" style="max-width: 100%;max-height: 100%;">';
	html += '</div></div>';
	$("body").append(html);
	$(".z-float").click(function() {
		$(this).remove();
	});
	
	height = $(window).height();
	var child = $(".z-float-value img")[0];
	child_height = child.naturalHeight;
	if(0 < child_height && height > child_height){
		topInt = (height - child_height)/2;
		$(child).css("margin-top", topInt);
	}
}