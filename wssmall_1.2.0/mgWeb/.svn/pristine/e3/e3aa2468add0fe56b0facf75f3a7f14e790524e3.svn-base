/**
 * 后台界面构建js
 * @author 
 */
var SubMenuUi={
	menu:undefined,
	backUrl:[],
	fchildren:[],
	init:function(menu,fchildren){
		var self =this;
		this.menu =menu;
		this.fchildren =fchildren;
		this.autoHeight();
		
		$(window).resize(function(){self.autoHeight();});
		
	},
	disMenu:function(){
		this.disAppMenu();
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
		//add by wui根据权限控制设置菜单
		if(self.fchildren)
			//load右侧的内容,right_content,点击主菜单时触发,子菜单不走这个支线
			Ztp.AUI.load(window.parent.BackendUi.fhref,1);
			self.disAppChildren(self.fchildren);
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
						if(true){
							var par_obj = $(window.parent.document);
							var bottom_tab_ul = par_obj.find("#bottom_tab_ul");
							var botton_falg = true;
							if(bottom_tab_ul.find("li").length > 8){
								var move_app_id = bottom_tab_ul.find("li").eq(0).attr("app_menu_id");
								bottom_tab_ul.find("li").eq(0).remove();
								//par_obj.find(".main_all_iframe[app_menu_id='"+move_app_id+"']").remove();
							}
							
							bottom_tab_ul.find("li").each(function(){
								if($(this).attr("app_menu_id") == v.id){
									bottom_tab_ul.find("li").removeClass("curr")
									$(this).attr("class","curr");
									botton_falg = false;
								}
							});
							if(botton_falg){
								bottom_tab_ul.find("li").removeClass("curr");
								var bottom_li ='<li id="appmenu" up_id="'+v.up_id+'" href="'+v.url+'" target="'+v.target+'" class="curr" app_menu_id="'+v.id+'"><a href="javascript:void(0);"><span>'+v.text+'</span></a><a href="javascript:void(0);" class="tabClose">关闭</a></li>';
								bottom_tab_ul.append(bottom_li);
							}
							self.menuTabSet();
						}
						self.setSecondTitle($(this).attr("text"));
						$("#leftMenus li a ").removeClass("curr");
						$(this).addClass("curr");
						return false;
					});
				});
			}
			
			
		});
		//add by wui 计算高度,设置左边菜单的高度
		//浏览器窗口栏高度
		var topHeight = window.top.document.documentElement.clientHeight;
		var height;
		var bottomHeight = 0;
		if($(".title_new",window.top.document).length>0){
			bottomHeight = 30;
		}
		//计算顶部菜单高度，不同环境顶部不同
		var top_menu = 0;
		if(theme_source_from == "ECS"){
			top_menu = 95;
		}else{
			top_menu = 140;
		}
		height = topHeight - bottomHeight - top_menu;
		//add "if-else" by liqingyi
		if(!$('.topContent', parent.document).is(":visible")){
			if($('.toolbar-box', parent.document).is(":visible")){
				height=topHeight-($(".toolbar-box", parent.document).height()+$(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}else{
				height=topHeight-($(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}
		}else{
			height=topHeight-bottomHeight-$('.topContent', parent.document).height()-$('.top2', parent.document).height()-$('.topBar', parent.document).height()-$('.gridSper', parent.document).height();
		}
		$("#leftMenuDiv").height(height);
	},
	createLink:function(v){
		var link = $("<a app_menu_id='"+v.id+"'    href='"+ v.url +"'  text='"+v.text +"'>" + v.text + "</a>");
		return link;
	},
	autoHeight:function(){
		var height= $(window).height();
	},
	menuTabSet:function(){//auto_created_frame
			var par_obj = $(window.parent.document);
			var bottom_tab_ul = par_obj.find("#bottom_tab_ul");
			bottom_tab_ul.find("li[id='appmenu']").each(function(){
				var tab_li = $(this);
				tab_li.bind("click",function(){
					var target = $(this).attr("target");
					var app_menu_id = $(this).attr("app_menu_id");
     				var url = $(this).attr("href");
     				var up_id = $(this).attr("up_id");
     				//par_obj.find(".mainContent").find(".main_all_iframe").hide();
     				var obj1 = par_obj.find(".main_all_iframe[app_menu_id='"+up_id+"']");
     				var mainapp = obj1.get(0);
     				var isNone = mainapp.style.display;
     				if("none"==isNone){
     					par_obj.find(".mainContent").find(".main_all_iframe").hide();
     					mainapp.style.display="block";
     				}
     				
     			    /* 为解决打开订单详情后在订单处理列表查询订单导致没有样式，增加app_menu_id=2016121601的main_all_iframe
     			     * 用于展示订单详情。此时如果点击订单详情页左侧带单打开菜单页面，页面会在app_menu_id=2016121601的main_all_iframe中打开，
     			     * 这会使得点击底部标签页却换页面时，使用标签的app_menu_id找不到对应页面。如果找不到，则在app_menu_id=2016121601的main_all_iframe中查找*/
     				var right_content = obj1.contents().find("div#right_content"); 
     				var app = right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']").get(0);
     				if(!app){
     					//var up_id = $(this).attr("up_id");
         				//par_obj.find(".mainContent").find(".main_all_iframe").hide();
         				obj1 = par_obj.find(".main_all_iframe[app_menu_id='2016121601']");
         				var mainapp = obj1.get(0);
         				var isNone = mainapp.style.display;
         				if("none"==isNone){
         					par_obj.find(".mainContent").find(".main_all_iframe").hide();
         					mainapp.style.display="block";
         				}
         				right_content = obj1.contents().find("div#right_content"); 
         				right_content.find(".auto_frame").hide();
         				app = right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']").get(0);
     				}
     				
     				if(app && "none"==app.style.display){
     					var appNone = app.style.display;
     					right_content.find(".auto_frame").hide();
     					app.style.display="block";
     					var leftMenu = obj1.contents().find(".leftMenu");
         				leftMenu.find("a").removeClass("curr")
         				leftMenu.find("a[app_menu_id='"+app_menu_id+"']").attr("class","curr");
    					
     				}
     				tab_li.addClass("curr").siblings("li").removeClass("curr");
					par_obj.find("#appmenu").find("li").removeClass("curr");
					par_obj.find("#appmenu").find("li[app_menu_id='"+app_menu_id+"']").addClass("curr");
     				
				});
				tab_li.find("a[class='tabClose']").each(function(){
					$(this).bind("click",function(){
						var up_menu_id = $(tab_li).attr("up_id");
						var app_menu_id = $(tab_li).attr("app_menu_id");
						var id = up_menu_id?up_menu_id:app_menu_id;
						$(tab_li).prev().trigger("click");
						$(window.top.document).find(".main_all_iframe[app_menu_id='"+id+"']").contents().find("#right_content").find(".auto_frame[app_menu_id='"+app_menu_id+"']").remove();
						tab_li.remove();
					});
				});
			});
	}
     
};

$(function(){
	var bMenu;
	try{bMenu = window.parent.BackendUi.fchildren}catch(e){}
	SubMenuUi.init(parent.menu,bMenu);
	SubMenuUi.disMenu();
})
