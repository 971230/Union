//-------------------------------------------------------------------------
//提示窗口 （基于jquery）
//--------------------------------------------------------------------------
/*
//方法:pop(ops,arg,fn)
----------------------------------------------------------------------------
//参数:
	ops（窗体内容）
----------------------------------------------------------------------------
		  title:	窗口标题
		   text:    具体内容，支持html格式
		  width:	内容宽度
		 height:	内容高度
		buttons:    按钮设置   
		 iframe:	内容是否为iframe true/false
		    url:    iframe页面URL
	  scrolling:    iframe滚动条 no:关闭
	  
	      width:  内容区宽度
	      heigh:  内容区高度
			
-----------------------------------------------------------------------------
	arg（窗体样式）:  0:错误样式 1:成功样式 2:确认样式 3:普通样式   /  fn  :如果入参为fn则为选择窗口
----------------------------------------------------------------------------
	fn（执行方法）:   点击窗体按钮所执行的方法（选择窗口为取消按钮执行函数，非选择窗口为确认按钮执行函数）
 */
 //调用示例:
 //-------------------------------------------------------------------------
 //eg_1: 提示您不能进行此操作，并带返回首页的链接
 //pop({text:'您不能进行此操作！<a href=\'/index.jsp\'>返回首页</a>'},3)
 //eg_2: 注册成功后提示用户成功信息，用户点击确定按钮页面跳转到index.jsp
 //pop({text:'注册成功！',title:'恭喜您！'},1,function(){location.href='/index.jsp'});
 //eg_3: 注册成功后提示用户成功信息，用户点击确定按钮页面跳转到/index.jsp
 //pop({text:'恭喜您，注册成功！'},1,function(){location.href='/index.jsp'})
 //eg_4: 提示确定要删除吗，确定则执行alert('ok')，取消则执行alert('no')
 //pop({text:'您确定要删除吗!'},function(){alert('ok')},function(){alert('no')});
 //eg_5: 提示确定要删除吗，确定则执行alert('ok')，取消则执行alert('no')
 //pop({text:'您确定要删除吗!'},function(){alert('ok')},function(){alert('no')});
 //eg_6: 弹出带有iframe的窗口
 //pop({iframe: true,type: 9,width: 480,height: 320,url:'http://yn.ct10000.com/',title:'云南网厅',buttons:{}});
 //-------------------------------------------------------------------------
;(function($) {
$.fn.extend({

	overlay: function(ops,id){
	 if($.browser.mozilla){ //火狐浏览器
			var ops = $.extend({
					position: 'fixed', top: 0, left: 0,
					width: '100%',height: '100%',
					opacity: 0.2, background: 'black', zIndex: 99
				}, ops),
			id = id || 'overlay';
			if( $.fn.ie6 ) ops = $.extend(ops, {
				position: 'absolute',
				width: Math.max($(window).width(),$(document.body).width()),
				height: Math.max($(window).height(),$(document.body).height()) });
			return $('<div class="overlay" id="'+id+'"/>').appendTo(document.body).css(ops);
	 }else
	 	this.addMask(id);
	 
	},
	addMask:function(id){
	  var maskDivJq = $('<div class="overlay" id="'+id+'"/>');
	  var maskDiv = maskDivJq[0];
	  $("body").append(maskDivJq);
      maskDiv.style.position = "absolute";
      maskDiv.style.zIndex = "99";
      maskDiv.style.left = "0px";
      maskDiv.style.top = "0px";
      maskDiv.style.width = "100%";
      var height ="100%";
      try{var height = document.documentElement.scrollHeight;}catch(e){height = "100%";}
      maskDiv.style.height = height;
      maskDiv.style.background = "#000";
      maskDiv.style.filter = "alpha(opacity=25)";
      maskDiv.style.opacity = "0.25";
      return maskDivJq;
	},
	position: function(ops){
		var ops = $.extend({
				fixx: 0,
				fixy: 0
			}, ops),
			mod = (this.css("position")=="fixed") ? 0 : 1,
			t = $(document).scrollTop()*mod,
			l = $(document).scrollLeft()*mod,
			mt = t;

		l += ($(window).width() - this.width()) / 2;
		t += ($(window).height() - this.height()) / 2;
		
		l += ops.fixx;
		t = Math.max(t, mt)+ops.fixy;
		
		if(t<0) t = 0;
		if(l<0) l = 0;
		
		return this.css({top: t, left: l});
	},

	dragdrop:function(ops,callback) {
        if(typeof(ops)=='function')callback=ops;
		this.css('position','absolute');
        var ops = $.extend({
            }, ops),handle=ops.handle ? $(ops.handle, this) : this,
            flag =false,_o={left:0,top:0},self=this;
         
        function pos(e){
            if (flag) {self.css( {left : e.pageX - _o.left + 'px',top : e.pageY - _o.top + 'px'});}
        }
        handle.mousedown(function(e){
            flag = true;
            self.css('z-index',parseInt(new Date().getTime()/1000));
            var offset = self.offset();
            _o = { left: e.pageX - offset.left, top: e.pageY - offset.top };
            $(document).mousemove(pos);
        }).mouseup(function(e){
            pos(e);
            flag = false;
            $(document).unbind('mousemove');
            if(callback)callback.apply(this,[self]);
        }).css('cursor','move');
        return self;
    },
	
	popup: function(ops,callback){
		var ops = $.extend({
                buttons:{},esc: true, id: 'popup', iframe: false,scrolling:'no',
				overlay: { opacity: 0.2, background: 'black' },
				text: '', title: '提示', type: 1, width: 340, height: 0, zIndex:1
			}, ops),
			self = this;
		
		function close(){
			$('#_overlay').add([document, window]).unbind('._overlay');
			$('#'+ops.id).add([document, window]).unbind('._darg');
			$('#_overlay').remove();
			if(select)select.ie6fix(false);
			$('#'+ops.id).fadeOut('fast',function(){$(this).remove()});
		}

		var o = $('#'+ops.id);

		switch(ops.action){
			case -1://resize
				$('.content', o).animate(callback,function(){o.ie6fix(false);}).find('iframe').css(callback);
				return;
			case 0://retitle
				$('.title', o).text(ops.text);
				return;
			case 1://first btn
				$('.btnpane', o).children('a:nth-child(1)').trigger('click');
				return;
			case 2://second btn
				$('.btnpane', o).children('a:nth-child(2)').trigger('click');
				return;
			case 9://close
				$('.close', o).trigger('click',callback);
				return;
			case 8:
                callback.apply(this, arguments);
                return;
			default:
				(o.length==1) && close(); 
		}
		
		var pHtm;
		if(ops.iframe){
			 if($.browser.mozilla){ //火狐浏览器
				 pHtm = $('<iframe src="'+ops.url+'" marginwidth="0" id="popup_iframe" marginheight="0" frameborder="0" hspace="0" vspace="0" scrolling="'+ops.scrolling+'" />')
					.css({ width: ops.width-16, height: ops.height==0 ? 'auto' : ops.height-54 });				
			 }else{
				 pHtm = $('<iframe src="'+ops.url+'" marginwidth="0" id="popup_iframe" marginheight="0" frameborder="0" hspace="0" vspace="0" scrolling="'+ops.scrolling+'" />')
					.css({ width: ops.width-16, height: ops.height==0 ? 'auto' : ops.height-54 });			
			 }
		}else{
			if(ops.jq_obj){
				pHtm =ops.jq_obj;
			}else{
				pHtm = $('<div>'+ops.text+'</div>');
			}
			
		}
		var pContent = $('<div class="content content_'+ops.type+'"/>').append(pHtm)
			.wrap('<div class="popup p'+ops.type+'"/>')
			.wrap('<div class="container container_'+ops.type+'"/>'),

		pContainer = pContent.parent(),
		
		pClose = $('<div class="titleclose" style="border:0px solid red;"/>')
			.append('<div class="title" style="border:0px solid red;width:93%;height:98%;float:left;">【' + ops.title + '】</div>')
			.append('<a href="javascript:void(0);" class="close"><span>X</span></a>')
			.prependTo(pContainer),
			
		_p = ($.fn.ie6) ? 'absolute' : 'fixed';	
		var sub_height = 0;
		if(ops.need_btn =="no")
			sub_height = 30;
		
		if($.browser.mozilla){ //火狐浏览器
			popup = pContainer.parent()
			.css({
				position: _p, zIndex: 99+ops.zIndex,
				top: ops.iframe?($(window).height()-ops.height) / 2-60:($(window).height()-ops.height) / 2, left:($(window).width()-ops.width)/ 2, width: ops.width, height: ops.height==0 ? 'auto' : (ops.height-sub_height+24), outline: 0
			})
			.attr({ id: ops.id, tabIndex: '-1' }).appendTo(document.body).hide()
			.keydown(function(ev) {
				if (ops.esc) {
					(ev.keyCode && ev.keyCode == 27 && close());
				}
			})
			.dragdrop({handle:'.titleclose'}),
		
			btnPane = $('<div class="btnpane"/>').appendTo(pContainer);
		}else{
			popup = pContainer.parent()
			.css({
				position: _p, zIndex: 99+ops.zIndex,
				top: ops.iframe?($(window).height()-ops.height) / 2-60:($(window).height()-ops.height) / 2, left:($(window).width()-ops.width)/ 2, width: ops.width, height: ops.height==0 ? 'auto' : (ops.height-sub_height+24), outline: 0
			})
			.attr({ id: ops.id, tabIndex: '-1' }).appendTo(document.body).hide()
			.keydown(function(ev) {
				if (ops.esc) {
					(ev.keyCode && ev.keyCode == 27 && close());
				}
			})
			.dragdrop({handle:'.titleclose'}),
		
			btnPane = $('<div class="btnpane"/>').appendTo(pContainer);
		}
		
		
		
		$('.close', pClose)
			.mousedown(function(ev) {
				ev.stopPropagation();
			})
			.click(function(event, callback) {
				var close_func = ops.close_func
				if(close_func){
					var ret = close_func();
					if(!ret)
						return false;
				}
				if(callback) callback.apply(this, arguments);
				close();
				return false;
			});
		
		var hasBtns = false, genBtns = [];
		function newBtns(btns) {
			btnPane.empty().hide();	
			$.each(btns, function() { return !(hasBtns = true); });
			if (hasBtns) {
				btnPane.show();
				$.each(btns, function(name, fn) {
					genBtns.push($('<a href="javascript:void(0);"/>')
						.text(name)
						.appendTo(btnPane)
						.bind('click', function() { fn.apply(this, arguments);return false;return false;}));
				});
			}
		}
		newBtns(ops.buttons);
		
		if(ops.overlay){
			var _overlay = $().overlay( $.extend(ops.overlay, { position: _p }),'_overlay' );
			if (ops.esc) {
				$(document).bind('keydown._overlay', function(e) {
					(e.keyCode && e.keyCode == 27 && close()); 
				});
			}
		}
		popup.position( ops.iframe ? { fixy: -60 } : {} ).show();		
		var select = $('select').ie6fix(true);
		if(window.event){event.returnValue = false;}
		
		//嵌套
		var containter = $("#popup").find(".container");
		var auiOuter  =$("<div class='aui_outer' id='aui_outer'></div>");
		auiOuter.append(containter);
		$("#popup").append(auiOuter);
		
		//iframe遮罩
		var frameId = "frame_pop";
		var iframe = document.getElementById(frameId);
		var ifm_height = containter[0].offsetHeight;
		 if(!$.browser.mozilla && jQuery.browser.version.indexOf("6.0")>-1){ //为ie6.0才处理
			if(!iframe){
				var iframe = document.createElement("<iframe  id='"+frameId+"' frameborder='0' framespacing='0' src='javascript:false' style='position:absolute; visibility:inherit; width:100%; height:"+ifm_height+"px; border:0px solid red;z-index:-1; filter=\"progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\";'></iframe>");
				containter.prepend($(iframe));
			}
		 }
	},
	popup_bank: function(ops,callback){
		var ops = $.extend({
                buttons:{},esc: true, id: 'popup_new', iframe: false,scrolling:'no',
				overlay: { opacity: 0.2, background: 'black' },
				text: '', title: '提示', type: 1, width: 340, height: 0, zIndex:1
			}, ops),
			self = this;
		function close(){
			$('#_overlay').add([document, window]).unbind('._overlay');
			$('#'+ops.id).add([document, window]).unbind('._darg');
			$('#_overlay').remove();
			if(select)select.ie6fix(false);
			$('#'+ops.id).fadeOut('fast',function(){$(this).remove()});
		}
		
		var popup =ops.pop_obj(ops.id);
		_p = ($.fn.ie6) ? 'absolute' : 'fixed';	
		var sub_height = 0;
		if(ops.need_btn =="no")
			sub_height = 30;
			popup.css({
				position: _p, zIndex: 99+ops.zIndex,
				top: ops.iframe?($(window).height()-ops.height) / 2-60:($(window).height()-ops.height) / 2, left:($(window).width()-ops.width)/ 2
			})
			.attr({ id: ops.id, tabIndex: '-1' }).appendTo(document.body).hide()
			.keydown(function(ev) {
				if (ops.esc) {
					(ev.keyCode && ev.keyCode == 27 && close());
				}
			})
			.dragdrop();
		if(ops.overlay){
			var _overlay = $().overlay( $.extend(ops.overlay, { position: _p }),'_overlay' );
			if (ops.esc) {
				$(document).bind('keydown._overlay', function(e) {
					(e.keyCode && e.keyCode == 27 && close()); 
				});
			}
		}
		
		$(".a_close").mousedown(function(ev) {
				ev.stopPropagation();
			})
			.click(function(event, callback) {
				var close_func = ops.close_func
				if(close_func){
					var ret = close_func();
					if(!ret)
						return false;
				}
				if(callback) callback.apply(this, arguments);
				close();
				return false;
		});
		
		popup.position( ops.iframe ? { fixy: -60 } : {} ).show();		
		var select = $('select').ie6fix(true);
		//if(window.event){event.returnValue = false;}
		var containter = $("#"+ops.id).find(".popregbox");
		//iframe遮罩
		var frameId = "frame_pop";
		var iframe = document.getElementById(frameId);
		var ifm_height = containter[0].offsetHeight;
		if(!$.browser.mozilla && jQuery.browser.version.indexOf("6.0")>-1){ //为ie6.0才处理
			if(!iframe){
				var iframe = document.createElement("<iframe  id='"+frameId+"' frameborder='0' framespacing='0' src='javascript:false' style='position:absolute; visibility:inherit; width:100%; height:"+ifm_height+"px; border:0px solid red;z-index:-1; filter=\"progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\";'></iframe>");
				containter.prepend($(iframe));
			}
		}
		
	},
	ie6fix: function(flag){
		return ($.fn.ie6)?this.css('visibility',flag?'hidden':'visible'):this;
	}
});

})(jQuery);

function pop(ops,arg,fn){//ops title='Information',type|fn1,fn2
	var need_btn =ops.need_btn || 'yes';
	if(need_btn =="yes"){
	    if(typeof(arg)=='function'){
	        $(document.body).popup($.extend({
	            buttons: {
	                取消: function(){
	                    $(document.body).popup( { action: 9, id: ops.id }, fn );
	                },
	                确定: function(){
	                    $(document.body).popup( { action: 9, id: ops.id }, arg );
	                }
	            },
	            type: 2
	        },ops));
	    }else{
	        $(document.body).popup( $.extend({
	            buttons: {
	                确定: function(){
	                    $(document.body).popup( { action: 9, id: ops.id }, fn );
	                }
	            },
	            type: arg //0:error 1:success 3:normal 
	        }, ops));
	    }   
	}else if(need_btn =="no"){
		  $(document.body).popup( $.extend({
	            type: arg //0:error 1:success 3:normal 
	      }, ops));
	}
}

//银行弹出界面
function pop_panel(ops,pop_func){
	  ops.pop_obj = pop_func;
	  $(document.body).popup_bank(ops);
}