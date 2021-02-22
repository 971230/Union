$(document).ready(function(){
	//左侧先关闭快捷导航
	hideOrShow();

	var duilian = $("div.duilian");
	var duilian_close = $("a.duilian_close");
	
	var window_w = $(window).width();
	if(window_w>1000){
		duilian.show();
	}
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop();
		duilian.stop().animate({top:scrollTop+260});
	});
	duilian_close.click(function(){
		$(this).parent().hide();
		return false;
	});
	
	
});