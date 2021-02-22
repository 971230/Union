/**
 * 分屏加载控件对象
 * add by wui
 * 
 * 处理思路：
 * 1.装载clientHeight第一屏页面数据
 * 2.scroll滚动时，延迟300状态滚动的数据，
 * 3.采用同步机制，滚动时，页面不可动，展示增在加载字样
 * 4.使用说明
 * 第6个参数为true时，表示分屏加载
 * 
 * CommonLoad.loadJSP('growth_info_div', 'modules/m108.jsp', {}, true,null,true);
 * 
 */
(function(scope) {
	var SplitScreen = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.jspInsts = [];
			this.waitInsts = [];
			this.canNext = true;
			this.old_scroll_top=0;
			this.init();
		},
		init:function(){
			this.bindScroll();
		},
		loadJspInst:function(jspInst){
			if(this.jspInsts.length ==0)
				this.getScrollObj().scrollTop(0);
			this.addInsts(jspInst);
			if(this.canNext)  //下一步
			{
				var obj_top = this.getOffSetTop(jspInst);
				var ch = this.getClientHeight();
				if(obj_top<ch)
					this.loadJsp(jspInst);
				else
					this.addWait(jspInst);
					
			}else{
				this.addWait(jspInst);
			}
		},
		scrollJspInst:function(scrollJq){ //页面滚动时设置
			var scroll_top = scrollJq.scrollTop();
			var top = this.getClientHeight()+scroll_top;
			var len =this.waitInsts.length;
			if(len>0 && this.canNext){
				var scrollInsts = [];
				for(var i=0;i<this.waitInsts.length;i++){ //获取需要装载的
					var jspInst  =this.waitInsts[i];
					var obj_top = this.getOffSetTop(jspInst);
					if(obj_top<top){
						scrollInsts.push(jspInst);
						jspInst['del'] = true;
					}
				}
				if(scrollInsts.length>0){
					var tempWaitInsts = [];
					for(var i=0;i<this.waitInsts.length;i++){ //获取需要装载的
						var jspInst  =this.waitInsts[i];
						if(jspInst['del'] !=true)
							tempWaitInsts.push(jspInst);
					}
					this.waitInsts = tempWaitInsts;
				}
				if(scrollInsts.length>0){
					for(var j=0;j<scrollInsts.length;j++){
						this.loadJsp(scrollInsts[j]);
					}
				}
			}
		},
		addWait:function(jspInst){ //设置等待池
			var container = false;
			for(var i=0;i<this.waitInsts.length;i++)
			{
				var div_id = jspInst['divId'];
				var curr_div_id = this.waitInsts[i]['divId']
				if(curr_div_id ==div_id){
					container = true;
					break;
				}
			}
			if(!container) //不存在，加入等待池
				this.waitInsts.push(jspInst);
		},
		addInsts:function(jspInst){
			this.jspInsts.push(jspInst);
		},
		getOffSetTop:function(jspInst){
			var jqObj = $("#"+jspInst['divId']);
			var offset = jqObj.offset();
			var top = offset.top;
			return top;
		},
		getScrollObj:function(){
			return $(window);
		},
		getScrollTop:function(p_jqObj){
			var jqObj = p_jqObj || this.getScrollObj();
			var sc_top = jqObj.scrollTop();
			return sc_top;
		},
		loadJspCallBack:function(jspInst){
			if(this.waitInsts.length>0){
				var jspInst = this.waitInsts.splice(0,1);
				this.loadJspInst(jspInst[0]);
			}
		},
		getClientHeight:function(){
			var client_height = document.documentElement.clientHeight;
			return client_height;
		},
		getClientWidth:function(){
			var client_width = document.documentElement.clientWidth;
			return client_width;
		},
		getScrollHeight:function(){
			var scroll_height = document.documentElement.scrollHeight;
			return scroll_height;
		},
		getOffSetHeight:function(){
			var offset_height = document.documentElement.offsetHeight;
			return offset_height;
		},
		loadJsp:function(jspInst){ //渲染jsp页面
			var me =this;
			this.canNext = false;
			this.mask();
			var is_asy =false;
			if(!$.browser.mozilla && jQuery.browser.version.indexOf("6.0")>-1)
				is_asy = true; //ie6设置为异步加载
			is_asy = true;
			window.setTimeout(function(){
				$.ajax({
					type: "POST",
					url: jspInst['url'],
					async:is_asy,
					data: jspInst['param'],
					beforeSend: function(){
					},
					success: function(msg) {
						
						$("#" + jspInst['divId']).html($.trim(msg));
						$("#" + jspInst['divId']).css("height","");//清理掉外层的高度，防止内部内容展示问题
						
						if(jspInst['callBack'])
							jspInst['callBack']();
						me.canNext = true;
						me.loadJspCallBack();
						if(typeof(cmsMgr)!="undefined"){
							cmsMgr.addCmsPanel();
						}
			
					},
					error:function(){
						me.canNext = true;
					},
					complete: function() {
						me.unmask();
					}
				});	
			},0);
		},
		bindScroll:function(){
			var me =this;
			this.getScrollObj().bind("scroll",function(){
				if(me.canNext && me.waitInsts.length>0)
					window.setTimeout(function(){me.scrollJspInst($(this))},150);
				return false
			})
		},
		mask:function(){
			return;
			var maskjQ = $("body").find("#yjz_mask");
			if(maskjQ.length==0){
				maskjQ = $("<div class='loadmask-msg' id='yjz_mask'><div>预加载,请等待.....</div></div>")
				$("body").append(maskjQ);
			}else{
				maskjQ.show();
				
			}
			
			var top = this.getClientHeight()+$(window).scrollTop();
			var left =this.getClientWidth()/2;
			maskjQ.css("top",top-40).css("left",left-50);
		},
		unmask:function(){
			$("body").find("#yjz_mask").hide();
		}
	})
	var JspInst = Base.extend({
		constructor : function(divId, url, param, useMask,callBack) {
			this.version = "1.0";
			this.divId =divId;
			this.url =url;
			this.param =param;
			this.useMask=useMask;
			this.callBack =callBack;
		}
	})
	window.splitScreen = new SplitScreen();
	window.JspInst = JspInst;
	
	var CommonLoad = {
		/**
		 * 公用加载jsp方法
		 * divId:存放登录模块的div的ID
		 * url:要加载的jsp对象
		 * param:参数,对象,如{"SERV_TYPE":"GBI-2","USER_FLAG":"002"}
		 * useMask:是否需要添加mask信息，默认不添加
		 */
		loadJSP:function(divId, url, param, useMask,callBack,lazy){
			if(lazy){
				try{
					var jspInst = new JspInst(divId, url, param, useMask,callBack);
					splitScreen.loadJspInst(jspInst);
				}catch(e){}
			}else{
				$.ajax({
					type: "POST",
					url: url,
					data: param,
					beforeSend: function(){
						if(useMask){
							//$("#" + divId).mask("正在加载...");
						}
					},
					success: function(msg) {
						$("#" + divId).html($.trim(msg));
						if(callBack)
							callBack();
					},
					complete: function() {
						//$("#" + divId).unmask();
					}
				});		
			}
		}
	}
	window.CommonLoad = CommonLoad;
}(window));

