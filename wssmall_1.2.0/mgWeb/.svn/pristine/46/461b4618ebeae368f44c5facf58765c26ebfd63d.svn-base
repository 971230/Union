 (function($) {
        $.fn.extend({
            Show: function(widht,height) {
			var TopY=0;//初始化元素距父元素的距离
			$(this).css("width",widht+"px").css("height",height+"px");//设置消息框的大小
			$(this).slideDown(1000);//弹出
			$("#messageTool").css("margin-top",-height);//为内容部分创建高度  溢出
			$("#message_close").click(function() {//当点击关闭按钮的时候
			     if(TopY==0)
				 {
					   $("#message").slideUp(1000);//这里之所以用slideUp是为了兼用Firefox浏览器
				 }
		    	else
				{
					  $("#message").animate({top: TopY+height}, "slow", function() { $("#message").hide(); });//当TopY不等于0时  ie下和Firefox效果一样
				}
	         });
			 $(window).scroll(function() {
	             $("#message").css("top", $(window).scrollTop() + $(window).height() - $("#message").height());//当滚动条滚动的时候始终在屏幕的右下角
				 TopY=$("#message").offset().top;//当滚动条滚动的时候随时设置元素距父原素距离
			  });
			}
		 })}
)(jQuery);