/**
 * 后台界面构建js
 * @author 
 */
var BackendUi={
	menu:undefined,
	backUrl:[],
	init:function(menu){
		
		var self =this;
		
		Ztp.AUI.init({wrapper:$("#right_content")});
		
		$("#desktop").click(function(){
			
			BackendUi.loadLeftMenuByPid($(this),'2221','日常操作');
			Ztp.AUI.load($(this));
			self.resetBackUrl();
			
			return false;
		});
		
		this.menu =menu;
		this.autoHeight();
		
		$(window).resize(function(){self.autoHeight();});
		
		$("#back_a").bind("click",function(){
			var preLink = self.getPrevUrl();//self.getPrevUrl();
			Ztp.AUI.load(preLink);
			BackendUi.loadLeftMenuByPid($(this),'2221','日常操作','no');
		})
		
		//设置返回处理
		$("#back_his_a").unbind("click").bind("click",function(){
			BackendUi.backHisEvent();
			$(this).hide();
		})
		
	},
	backHisADisplay:function(state){
		state?$("#back_his_a").show():("#back_his_a").hide();
		
	},
	backHisEvent:function(){ //返回事件处理
		var iframeDocument =$("[id='right_content'] iframe").get(0).contentWindow.document;
		$("#iframe_back_a",iframeDocument).trigger("click");
	},
	disMenu:function(){
		this.disSysMenu();
		this.disAppMenu();
	},
	getPrevUrl:function(){
		var prevUrl = this.backUrl.pop();
		return prevUrl;
	},
	resetBackUrl:function(){
		this.backUrl = [];
		$("#back_a").hide();
	},
	addATag:function(atag){ //追加url
		
		this.backUrl.push(atag);
		
		if(this.backUrl.length>0){
		 	$("#back_a").show();
		}else{
			$("#back_a").hide();
		}
		
	},
	/**
	 * 显示系统菜单
	 */
	disSysMenu:function(){
		var self =this;
		var menu = this.menu;
		$.each(menu.sys,function(k,v){
			if(v.text =="浏览网站")
			{
				return true;
			}
			
			var link;
			if(v.text == '应用中心'){
				link = $('<a class="topbtn2" target="' + v.target + '" href="' + v.url + '"><span><i class="allicon appicon"></i>' + v.text + '</span></a>');
				$(link).prependTo($("#sysmenu"));
			}else {
				link = $("<a   class='topbtn2' target='"+v.target+"' href='"+ v.url +"' text='"+v.text +"'  ><span><i class='allicon exiticon'></i>" + v.text + "</span></a>");
				$(link).appendTo($("#sysmenu"));
			}
			 if(v.target!='_blank'){
				link.click(function(){
						
						
						Ztp.AUI.load($(this));
						self.setSecondTitle($(this).attr("text"));
						return false;
				});
			 }
		});		
	},
	//装载侧边栏菜单
	loadLeftMenuByPid:function(curr_a,pid,title,is_add){ //
		
		if(is_add !='no')
			this.addATag(curr_a);
		var self=this;
		var menu = this.menu;
		$.each(menu.app,function(k,v){
			if(pid ==v.id){
				
				var link = $("app_menu_id='"+pid+"'");
				var children = v.children;
				if(children)
					self.disAppChildren(children);
				self.setSecondTitle(title);
				
				return false;
			}
		});
		
		if(this.backUrl.length>0){
		 	$("#back_a").show();
		}else{
			$("#back_a").hide();
		}
		
		
	},
	/**
	 * 显示应用菜单
	 */
	disAppMenu:function(){
		var self=this;
		var menu = this.menu;
		$.each(menu.app,function(k,v){
			
			var a_str ='';
			var li_str ='';
			if(k==0)
				a_str='class="first curr"';
			if(k==menu.app.length-1)
				li_str='class="last"';
			
			var link = $("<a  "+a_str+" target='"+v.target+"' href='"+ v.url +"' text='"+v.text +"'  app_menu_class ='yes'><span>" + v.text + "</span></a>");
			link.appendTo($("<li "+li_str+" app_menu_id='"+v.id+"'></li>").appendTo($("#appmenu>ul")));
			
			var children = v.children;
			 
				link.bind("click",function(){
					//add by wui根据权限控制设置菜单
						
					if(children)
						self.disAppChildren(children);
					
					self.setSecondTitle($(this).attr("text"));
					
					if(v.id !='2221'){
						var p_v = null;
						$.each(children,function(k,v){
								
								if(this.children){
									var childrens = this.children;
									$.each(childrens,function(k,v){
											p_v= v;
											return false;
								
									})
								}
								return false;
						});
						//add by wui第一个选中
						if(p_v)
						{	
							$(this).attr("target",p_v.target);
							$(this).attr("href",p_v.url);
						}
					}
					Ztp.AUI.load($(this));
					$("#appmenu li a ").removeClass("curr");
					$(this).addClass("curr");
					return false;
				});
				if(k==0){ 
					var href= link.attr("href");
					var target=link.attr("target");
					link.attr("href",app_path+"/mbl/admin/dailyOperation!index.do");
					link.removeAttr("target");
					self.setSecondTitle("个人中心");
					link.click();
					link.attr("href",href);
					link.attr("target",target);
				}
		
		});			
	},
	setSecondTitle:function(value){
		$("#second_h3_title").html(value);
	},
	/**
	 * 显示应用的子菜单
	 */
	disAppChildren:function(children){
		var self= this;
		var leftMenu = $("#leftMenus");
		leftMenu.empty();
		$.each(children,function(k,v){
			
			var sub_id ="sub_items"+v['id'];
			leftMenu.append($("<div class='leftTop'><div class='leftArrow'><a href='javascript:void(0)' class='arrowclose'></a></div><h2>"+v.text+"</h2></div><ul class='leftMenu' id="+sub_id+"></ul>"));
			if(this.children){
				var childrens = this.children;
				$.each(childrens,function(k,v){
					link = self.createLink(v);
					var li_str ='';
					if(k==childrens.length-1)
						li_str='class="last"';
					link.appendTo($("<li "+li_str+"></li>").appendTo(leftMenu.find("#"+sub_id)));
					link.click(function(){
						Ztp.AUI.load($(this));
						self.setSecondTitle($(this).attr("text"));
						$("#leftMenus li a ").removeClass("curr");
						$(this).addClass("curr");
						return false;
					});
				});
			}
			
			
		});
		
		//add by wui 计算高度,设置左边菜单的高度
		var clientHeight =screen.availHeight-140;
		var leftMenuHeight = $("#leftMenus").height();
		//alert(leftMenuHeight+":"+clientHeight);
		//alert(screen.availHeight+":"+clientHeight+":"+leftMenuHeight)
		if(leftMenuHeight>clientHeight){
			//alert(clientHeight)
			$("#leftMenuDiv").height(clientHeight);
		}
		//#("#leftMenuDiv").height();
		//$("#leftMenuDiv").height($("#leftMenus").height());
		
		
	},
	createLink:function(v){
		var link = $("<a  target='"+v.target+"' href='"+ v.url +"'  text='"+v.text +"'>" + v.text + "</a>");
		return link;
	},
	autoHeight:function(){
		var height= $(window).height();
		
		//$("#leftMenus").height(height);
		//$("#right_content").height(height);
	}

};

$(function(){
	BackendUi.init(menu);
	BackendUi.disMenu();
})
