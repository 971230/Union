var j = 0;
var quantofamo = 0;
var index = 0;
jQuery.fn.slideView = function(settings) {
	settings = jQuery.extend({
		easeFunc: "easeInOutExpo",
		easeTime: 750,
		toolTip: false,
		ttOpacity: 0.9
	}, settings);
	return this.each(function(){
		var container = jQuery(this);
		container.addClass("stripViewer");	
		var pictWidth = container.find("img").width();
		var pictHeight = container.find("img").height();
		var pictEls = container.find("li").size();
		var stripViewerWidth = pictWidth*pictEls;
		var time = null;
		var count = 0;
		container.find("ul").css("width" , stripViewerWidth);
		container.css("width" , pictWidth);
		container.css("height" , pictHeight);
		container.each(function(i){
			jQuery(this).after("<div class='stripTransmitter' id='stripTransmitter" + (j) + "'><ul></ul></div>");
			jQuery(this).find("li").each(function(n){
				jQuery("div#stripTransmitter" + j + " ul").append("<li><a title='" + jQuery(this).find("img").attr("alt") + "' href='#'>"+(n+1)+"</a></li>");												
			});
			count = jQuery("div#stripTransmitter" + j + " a").size();
			
			jQuery("div#stripTransmitter" + j + " a").each(function(z) {
				jQuery(this).bind("click", function(){
					//图片按时间间隔自动滚动效果

					if(null != time){
						index = z;
						clearInterval(time);
					}
					jQuery(this).addClass("current").parent().parent().find("a").not(jQuery(this)).removeClass("current");
					var cnt = -(pictWidth*z);
					container.find("ul").animate({ left: cnt}, settings.easeTime, settings.easeFunc);
					//图片按时间间隔自动滚动效果

					if("1" == settings.isAutoShow){
						time = setInterval("showAuto("+count+")", settings.showTime);
					}
					return false;
				});
			});
		
			// next image via image click	14/01/2009
			jQuery("div#stripTransmitter" + j + " a").parent().parent().parent().prev().find("img").each(function(z){
				jQuery(this).bind("click", function(){
					var ui = jQuery(this).parent().parent().parent().next().find("a");
					if(z+1 < pictEls){
						ui.eq(z+1).trigger("click");
					}
					else{
						ui.eq(0).trigger("click");
					}
				});
			});
		
		
			jQuery("div#stripTransmitter" + j).css("width" , pictWidth);
			jQuery("div#stripTransmitter" + j + " a:first").addClass("current");
			jQuery('body').append('<div class="tooltip" style="display:none;"></div>');
		
			// built-in tooltips 17/02/2010
			// original work at stoimen.com/jquery.tooltip.plugin/
			if(settings.toolTip){
				var aref = jQuery("div#stripTransmitter" + j + " a");
				aref.live('mousemove', function(e){
					var att = jQuery(this).attr('title');
					posX=e.pageX+10;
					posY=e.pageY+10;
					jQuery('.tooltip').html(att).css({'position': 'absolute', 'top': posY+'px', 'left': posX+'px', 'display': 'block', 'opacity': settings.ttOpacity});
				});
			
				aref.live('mouseout', function(){
					jQuery('.tooltip').hide();
				});				
			}
		});
		
		//新增图片按时间间隔自动滚动效果

		if("1" == settings.isAutoShow){
			time = setInterval("showAuto("+count+")", settings.showTime);
		}
		
		j++;
	});	
};

function showAuto(maxcount){
	if(undefined == maxcount || "number" != typeof(maxcount))	return;
	index = index < maxcount - 1 ? index+1 : 0;
	$("div.stripTransmitter ul a").eq(index).trigger("click");
}