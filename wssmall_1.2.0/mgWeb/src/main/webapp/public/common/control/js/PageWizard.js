/**
 * 业务受理核心模块
 */
(function(scope) {
	var PageWizard = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config ={};
			this.tabMenus =[];
			this.serv_info = {};
		},
		genMenu:function(){// 构造菜单布局
			var me=this;
			this.config.method='GET_MENU_BY_SERV_NO';
			this.config.SERV_NO =this.config.serv_no;
			var parentPanel =this.getParentJq();
			Service.excute("TWB_ACCEPT", false, this.config, function(reply){
				
				var tabMenus = reply['RET_OBJ'];
				me.serv_info =reply['SERV_INFO'];
				me.tabMenus = tabMenus;
//				var len =tabMenus.length;
//				me.setParentJqId(parentPanel,len);
//				var menuArr =[];
//				menuArr.push('<div class="OrderTab">');
//            	menuArr.push('<div class="tab_bg2" name="accept_tab_name">');
//				for(var i=0;i<len;i++){ //创建tab表头
//					var tabMenu = tabMenus[i];
//					menuArr.push("<div class='tab_common' id='"+tabMenu['code']+"_li' div_name ='"+me.config.serv_no+"_"+tabMenu['code']+"_div' litab ='yes'><a href='javascript:void(0)' class='track' >"+tabMenu['title']+"</a></div>")					
//				}
//				menuArr.push('</div>');
//          		menuArr.push('</div>');
//				for(var i=0;i<tabMenus.length;i++){
//					var tabMenu = tabMenus[i];
//					var styleCss =(i==0)?"display:block;":"display:none;";
//					menuArr.push("<div style='"+styleCss+";'  id='"+me.config.serv_no+"_"+tabMenu['code']+"_div' litabcontent='yes'>");
//					menuArr.push("</div>");
//				}
				
//				parentPanel.append( $(menuArr.join("")));
				me.initMenuEvent();
			})
		},
		genMenu2:function(){// 构造菜单布局
			var me=this;
			this.config.method='GET_MENU_BY_SERV_NO';
			this.config.SERV_NO =this.config.serv_no;
			var parentPanel = this.getParentJq()
			Service.asExcute("TWB_ACCEPT", false, this.config, function(reply){
				var tabMenus = reply['RET_OBJ'];
				me.serv_info =reply['SERV_INFO'];
				me.tabMenus = tabMenus;
//				var len =tabMenus.length;
//				me.setParentJqId(parentPanel,len);
//				var menuArr =[];
//				menuArr.push('<div class="navflow" id="phoneTab3">')
//				menuArr.push('<div class="phone3">');
//            	menuArr.push('<div class="tab_bg0" name="accept_tab_name">');
//				for(var i=0;i<len;i++){ //创建tab表头
//					var tabMenu = tabMenus[i];
//					menuArr.push("<div class='tab_common' id='"+tabMenu['code']+"_li' div_name ='"+me.config.serv_no+"_"+tabMenu['code']+"_div' litab ='yes'><a href='javascript:void(0)' class='track' >&nbsp;</a></div>") //tabMenu['title']					
//				}
//				menuArr.push('</div>');
//          		menuArr.push('</div>');
//          		menuArr.push('</div>');
//				for(var i=0;i<tabMenus.length;i++){
//					var tabMenu = tabMenus[i];
//					var styleCss =(i==0)?"display:block;":"display:none;";
//					menuArr.push("<div style='"+styleCss+";'  id='"+me.config.serv_no+"_"+tabMenu['code']+"_div' litabcontent='yes'>");
//					menuArr.push("</div>");
//				}
//				var menuPanel = $(menuArr.join(""));
//				parentPanel.append(menuPanel);
				me.initMenuEvent();
			})
		},
		genBtns:function(){
			btnWizard.genPageBtns();
		},
		setParentJqId:function(parentPanel,len){
			parentPanel.attr("id","ProcessTab"+len)
		},
		getParentJq:function(){
			return $(".OrderBox");
		},
		initMenuEvent:function(){
			var me =this;
			me.getParentJq().find("[litab='yes']").bind("click",function(event,lx){
					if(!lx && btnWizard.is_btn_click =="no") //不是按钮触发的翻页不给予处理
						return;
					var id =$(this).attr("id");
					var li =$(this).get(0);
					if(li.className=="tab_current")//已选中
						return;
					$("[litab='yes']").removeClass("tab_current").addClass("tab_common");
					$(this).addClass("tab_current");
					var i=$(this).index();
					var selAcceptTab =$(this).closest("[name='accept_tab_name']");
					selAcceptTab.removeClass();
					selAcceptTab.addClass("tab_bg"+i);
					me.changeTab($(this));
			})
			btnWizard.is_btn_click ="no"
			me.getParentJq().find("[litab='yes']:eq(0)").trigger("click",['btn']); //默认选中第0条
		},
		changeTab:function(liJq){
			var me =this;
			var li_id = liJq.attr("id");
			var tabMenu =this.getMenuData(li_id);
			var url = tabMenu['url'];
			var is_reload =tabMenu['is_reload'];
			var has_load = liJq.attr("has_load");
			var div_id =liJq.attr("div_name");
			$("[litabcontent='yes']").hide();
			$("[id='"+liJq.attr("div_name")+"']",me.getParentJq()).show();
			if(!is_reload)
				is_reload = liJq.attr("is_reload");
				
			if(is_reload==true || is_reload=="true" || !has_load || has_load=='no'){
				liJq.attr("has_load",'yes');
				this.config.param_div_id = div_id;
				
				//隐藏页面按钮
				btnWizard.hideBtn();
				var callBack = function(){//回调函数处理
					
				}
				
				btnWizard.changeBtn(tabMenu); //控制按钮的可见性 ,暂时从回调移除
				//alert(div_id+":"+url)
				CommonLoad.loadJSP(div_id,url, this.config,false,callBack); //回调后再展示数据
			}else{
				btnWizard.changeBtn(tabMenu); //控制按钮的可见性
				acpSplite.init(div_id); //重新init操作
			}
			
		},
		bindConfigEvent:function(liJq){//绑定自定义回调函数
			var li_id = liJq.attr("id");
			var tabMenu =this.getMenuData(li_id);
			var exec_func=tabMenu['exec_func'];
			if(!exec_func)return;
			var fun = eval(exec_func);
			if(typeof(fun) != "function") return;
			return eval(funName+"("+li_id+")"); //执行自定义函数
		},
		getMenuData:function(li_id){
			for(var i=0;i<this.tabMenus.length;i++){ //创建tab表头
				var tabMenu = this.tabMenus[i];
				var code =tabMenu['code'];
				var curr_code =li_id.substring(0,li_id.length-3);
				if(curr_code ==code)
					return tabMenu;
			}
			return null;
		},
		getTabLength:function(){ 
			return $("[litab ='yes']").length;
		}
	})
	window.pageWizard = new PageWizard();
}(window));