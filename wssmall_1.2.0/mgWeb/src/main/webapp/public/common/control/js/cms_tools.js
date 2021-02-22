/**
 * 
 * 获取所有可编辑的对象，
 */
(function(scope) {

var UeditorProxy = Base.extend({
	constructor : function(config) {
		this.version = "1.0";
		this.config = {};
		this.sel_panel =null;
//		this.isIe=$.browser.msie;
	},
	openDetailEditor:function(btn,panel){
		this.sel_panel = panel;
		this.sel_btn = btn;
		var obj = {"btn":btn,"panel":panel};//按钮对象和div对象
		var sFeatures = "dialogHeight = 500px;dialogWidth = 1000px;help:no;status:yes;scroll:yes";
		var retObj = window.showModalDialog("/admin/cmsedit/edit.jsp",obj,sFeatures);
		if(true)
			window.location.reload();//重新加载
	},
	openFileEditor:function(btn,panel){
		var cms_type = panel.attr("cms_type");
		var cms_value = panel.attr("cms_value");
		var action_type = "E";
		window.showModalDialog("/admin/cmsedit/fileEdit.jsp",{cms_value:cms_value,cms_type:cms_type,action_type:action_type},"help: yes;status: no;dialogWidth:1000px;dialogHeight:550px;");
	},
	openUeditor:function(btn,panel){
		var me = this;
		var file= panel.attr("cms_type");
		if(file=='file'){
			me.openFileEditor(btn,panel);
		}else{//db
			me.openDetailEditor(btn,panel);
		}
	}});
	window.ueditorProxy = new UeditorProxy();
	
	//========================================================================================================================//
	
	//csm Context实体对象
	var CmsContext = Base.extend({
		constructor : function(config) {
			this.onEditPage = false;
			this.staff_no="";
			this.role={};
			this.audit={};
			this.initRole();
		},
		initRole:function(){
			var me = this;
			Service.excuteNoLoading('CMS_INFO',false,{type:'ROLE'},function(reply){
				me.role = reply['FUN_RULE'];
				me.audit = reply['AUDIT_FUN'];
				me.staff_no=reply["STAFF_NO"]
			})
		},
		canEdit:function(fun_id){//可编辑:1
			return this.roleCheck(fun_id,1);
		},
		canAudit:function(fun_id){//可审核：2
			return this.roleCheck(fun_id,2);
		},
		canShow:function(fun_id){//是否显示编辑框
			return this.roleCheck(fun_id,1)||this.roleCheck(fun_id,2);
		},
		roleCheck:function(fun_id,role_type){
			if(!fun_id)return false;
			var role = this.role[""+fun_id];
			if(!role)return false;
			try{
				if(parseInt(role)>=role_type){
					return true;
				}
			}catch(e){
				return false;
			}
			return false;
		},
		hasAudit:function(fun_id){//检查当前项下面是否有待审核信息
			if(!fun_id)return false;
			var flag = this.audit[""+fun_id];
			if(flag && flag=='1')return true;
			else return false;
		}
	});
	window.cmsContext = new CmsContext();	
	
	//========================================================================================================================//
	
	var CmsMgr = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config = {};
			this.sel_panel  = null;
			this.btnsContorls ={'A':'cms_btn_add_li','M':'cms_btn_mod_li','D':'cms_btn_del_li'}
		},
		initDocument:function(time){
			var me = this;
			time = time || 0;
			window.setTimeout(function(){
				$("[cms_edit='yes']").each(function(){
					$(this).hover(function(){
						me.visibleTools($(this));
					},function(){
						$("#cms_tools").hide();
					})
					me.addCmsPanel($(this));
				});
			},time)
			
		},
		visibleTools:function(currObj){
			var me =this;
			var cmsTools = null;
			this.sel_panel = currObj;
			$("#cms_tools").remove();
			var fun_id = currObj.attr("cms_value");
//			alert(cmsContext.canEdit(fun_id)+":"+cmsContext.canAudit(fun_id));
			var temp="<ul style='position:absolute;z-index:9999;' id='cms_tools'>" ;
			if(cmsContext.canEdit(fun_id)){
				temp+=
					"<li style='float:left;margin-right:5px;cursor:hand;' title='新增' id='cms_btn_add_li'><span id='cms_btn_add'><img  src ='/ueditor/img/add.gif' ></span></li>" +
					"<li style='float:left;margin-right:5px;cursor:hand;' title='修改' id='cms_btn_mod_li'><span id='cms_btn_upd'><img  src ='/ueditor/img/mod.gif'  /></span></li>" +
					"<li style='float:left;margin-right:5px;cursor:hand;' title='删除' id='cms_btn_del_li'><span id='cms_btn_del'><img  src ='/ueditor/img/del.gif' /></span></li>"+
					"<li style='float:left;margin-right:5px;cursor:hand;' title='版本' id='history_btn'><span><img  src ='/ueditor/img/history.gif' /></span></li>";
			}
			if(cmsContext.canAudit(fun_id) && cmsContext.hasAudit(fun_id)){
				temp+="<li style='float:left;margin-right:5px;cursor:hand;' title='审核'  id='cms_btn_aud_li'><span id='cms_btn_aud' ><img  src ='/ueditor/img/aud.gif' /></span></li>";
                temp+="<li style='float:left;margin-right:5px;cursor:hand;' title='审核不通过'  id='cms_btn_aud_li'><span id='cms_btn_not_aud' ><img  src ='/ueditor/img/aud.gif' /></span></li>";
            }
			temp+="</ul>";
			cmsTools =$(temp);
			$("body").append(cmsTools);
			cmsTools.find("#cms_btn_upd").bind("click",function(){
				me.cms_upd($(this),me.sel_panel);
			})
			cmsTools.find("#cms_btn_add").bind("click",function(){
				me.cms_add($(this),me.sel_panel);
			})
			cmsTools.find("#cms_btn_del").bind("click",function(){
				me.cms_del($(this),me.sel_panel);
			})
			cmsTools.find("#cms_btn_aud").bind("click",function(){
				var type=me.sel_panel.attr("cms_type");
                if(confirm('是否审核?')){
					if(type=='file'){
	                    var param={'fun_id':fun_id,METHOD:"AUDIT"};
	                    Service.asExcute('CMSCONTENTBO',false,param,function(reply){
	                        var result=reply['RESULT'];
	                        if(result=='true'){
	                            alert('审核成功!');
	                        }else alert('审核出错!');
	                    });
					}else{
						var url='/admin/cmsedit/verifyList.jsp';
		               	var param = "dialogHeight:450px; dialogWidth:650px; dialogLeft:350px;dialogTop:80px;help: no; status: yes;scroll:yes";
		               	window.showModalDialog(url,new Object(),param);
					}
                    window.location.reload();
                }
			})
            cmsTools.find("#cms_btn_not_aud").bind("click",function(){
                if(confirm('是否确认操作?')){
                    var param={'fun_id':fun_id,METHOD:"AUDIT_NOT"};
                    Service.asExcute('CMSCONTENTBO',false,param,function(reply){
                        var result=reply['RESULT'];
                        if(result=='true'){
                            alert('操作成功!');
                        }else alert('操作出错!');
                    });
                    window.location.reload();
                }
            })
            cmsTools.find("#history_btn").bind("click",function(){
                var param = new Object();
                param.fun_id=fun_id;
                var sFeatures = "dialogHeight = 450px; dialogWidth = 600px; help: no; status: yes;scroll:yes";
                var retObj = window.showModalDialog("/admin/cmsedit/showFileHistory.jsp" ,param,sFeatures);
                if(null!=retObj && undefined!=retObj){
                    var items=retObj.split('\,');
                    var features="help: yes;status: no;dialogWidth:820px;dialogHeight:600px;";
                    var param={cms_value:fun_id,cms_type:'file',action_type:'E',cms_file_id:items[0],cms_sequ:items[1],sign:true};
                    window.showModalDialog("/admin/cmsedit/fileEdit.jsp",param,features);
                }
            })
			cmsTools.hover(function(){
				$("#cms_tools").show();
			},function(){
			})
			cmsTools.show();
			//设置位置
			var offset = currObj.offset();
		    var inputTop = offset.top;
		    var inputLeft = offset.left;
		    var currWidth = currObj.outerWidth(true);
		    var cmsWidth = cmsTools.outerWidth(true);
			cmsTools.css({top : inputTop+5, left : (inputLeft+currWidth/2)});
			this.controlBtns(currObj);
		},
		controlBtns:function(currObj){ //控制按钮
			var cms_type  = this.sel_panel.attr("cms_type");
			if(cms_type =="file")
			{
				$("#cms_btn_add").hide();
				$("#cms_btn_del").hide();
			}
			var filter_btns = currObj.attr("filter_btns");
			if(filter_btns)
			{
				var filter_btns_arr  = filter_btns.split(",");
				for(var i=0;i<filter_btns_arr.length;i++){
					var filter_btn = filter_btns_arr[i];
					var btn_id = this.btnsContorls[filter_btn];
					$("#"+btn_id).hide();
				}
			}
		},
		hideTools:function(){
			$("#cms_tools").hide();
		},
        cms_history:function(curr_element,panel,file_id,sequ){//回退历史
            panel.attr("action_type","M");
            ueditorProxy.openUeditor(curr_element,panel);
            $("#cms_tools").hide();
        },
		cms_upd:function(curr_element,panel){
			panel.attr("action_type","M");
			ueditorProxy.openUeditor(curr_element,panel);
			$("#cms_tools").hide();
		},
		cms_del:function(curr_element,panel){ //删除
			panel.attr("action_type","D");
			ueditorProxy.openUeditor(curr_element,panel);
			$("#cms_tools").hide();
		},
		cms_add:function(curr_element,panel){ //添加
			panel.attr("action_type","A");
			ueditorProxy.openUeditor(curr_element,panel);
			$("#cms_tools").hide();
		},
		cms_aud:function(curr_element,panel){ //审核
			panel.attr("action_type","X");
			ueditorProxy.openUeditor(curr_element,panel);
			$("#cms_tools").hide();
		},
		addCmsPanel:function(currObj){
			var me =this;
			//$("[name ='cms_outer_div']").remove();
			$("[cms_edit='yes']").each(function(){
				var fun_id = currObj.attr("cms_value");
				//如果没有权限，直接返回
				if(!cmsContext.canShow(fun_id))return;
				
				var curr =$(this);
				var divjQ = $("<div name='cms_outer_div' style='border:2px dashed #9BCF22;position:absolute;z-index:1;'></div>");
				var offset = $(this).offset();
			    var inputTop = offset.top;
			    var inputLeft = offset.left;
			    var csm_height = $(this).attr("csm_height");
			    var p_height = csm_height || $(this).height();
			   
				divjQ.css({"top":inputTop-3,"left":inputLeft,"height":p_height,"width":$(this).width()});
				
				$("body").append(divjQ);
				//重新绑定事件
				divjQ.hover(function(){
					me.visibleTools(curr);
				},function(){
					
				})
			});
		}
	  });
	 window.cmsMgr = new CmsMgr();
})(window);

$(function(){
	cmsMgr.initDocument(500);
})