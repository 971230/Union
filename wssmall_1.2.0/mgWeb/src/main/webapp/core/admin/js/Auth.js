var AuthAction = {

	init : function() {
		var self = this;
		$("#new_action").click(function() {
					self.newAction();
				});
		self.bindBoxEvent();
		Eop.Dialog.init({
					id : "actionDlg",
					title : "权限点"
				});
	},
	newAction : function() {
		var self = this;
		$("#actionDlg").load('auth!add.do?ajax=yes', function() {
					self.createTree();
					$("ul.checktree").checkTree();
					$("#authBtn").click(function() {
								self.saveAuth();
							});
				});
		Eop.Dialog.open("actionDlg");
	},
	/**
	 * 创建菜单树
	 */
	createTree : function() {
		var self = this;
		$.each(menu.app, function(k, v) {
			var li = self.createNode(v);
			var ul = self.createChildren(v.children);
			li.append(ul);
			$("#menubox>.menuTree").append(li);
		});
		$.each(app.all, function(k, v) {
			var li = self.createAppNode(v);
			var ul = self.createAppChildren(v.children);
			li.append(ul);
			$("#menubox>.appTree").append(li);
		});
		$.each(btn.all, function(k, v) {
			var li = self.createBtnNode(v);
			var ul = self.createBtnChildren(v.children);
			li.append(ul);
			$("#menubox>.btnTree").append(li);
		});
	},
	/**
	 * 根据子创建ul结点
	 */
	createChildren : function(menuAr) {
		var ul = $("<ul></ul>");
		var self = this;
		$.each(menuAr, function(k, v) {
					var li = self.createNode(v);

					// 如果有子则递归
					var children = v.children;
					if (children && children.length > 0) {
						li.append(self.createChildren(children));
					}
					ul.append(li);
				});
		return ul;
	},
	createAppChildren : function(menuAr) {
		var ul = $("<ul></ul>");
		var self = this;
		$.each(menuAr, function(k, v) {
					var li = self.createAppNode(v);

					// 如果有子则递归
					var children = v.children;
					if (children && children.length > 0) {
						li.append(self.createAppChildren(children));
					}
					ul.append(li);
				});
		return ul;
	},
	createBtnChildren : function(menuAr) {
		var ul = $("<ul></ul>");
		var self = this;
		$.each(menuAr, function(k, v) {
					var li = self.createBtnNode(v);

					// 如果有子则递归
					var children = v.children;
					if (children && children.length > 0) {
						li.append(self.createBtnChildren(children));
					}
					ul.append(li);
				});
		return ul;
	},
	/**
	 * 根据menu json创建菜单节点
	 */
	createNode : function(v) {
		var li = $("<li><input type=\"checkbox\" name=\"menuids\" value=\""
				+ v.id + "\"><label>" + v.text + "</label></li>");
		return li;
	},
	createAppNode : function(v) {
		var li = $("<li><input type=\"checkbox\" name=\"appIds\" value=\""
				+ v.id + "\"><label>" + v.text + "</label></li>");
		return li;
	},
	createBtnNode : function(v) {
		var li = $("<li><input type=\"checkbox\" name=\"btnIds\" value=\""
				+ v.id + "\"><label>" + v.text + "</label></li>");
		return li;
	},
	/**
	 * 保存权限
	 */
	saveAuth : function() {
		var self = this;
		var authname = $.trim($("#authname").val());
		if (authname == '') {
			alert("请输入权限名称");
			return;
		}
		$.Loading.show('正在保存，请稍侯...');
		var options = {
			url : "auth!save.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {
				if (result.result == 1) {
					$.Loading.hide();
					var isEdit = $("#isEdit").val();
					if (isEdit == 1) {// 修改更换名称
						$("#li_" + result.authid + ">span").html(authname);
					} else {// 添加加入一行
						$("#actbox>ul")
								.append("<li id='li_"
										+ result.authid
										+ "'><input type=\"checkbox\" name=\"acts\" value=\""
										+ result.authid
										+ "\" /><span>"
										+ authname
										+ "</span><img class=\"modify\" src=\"images/transparent.gif\" authid='"
										+ result.authid
										+ "' >&nbsp; <img class=\"delete\" src=\"images/transparent.gif\"  authid='"
										+ result.authid + "'> </li>");
					}
					self.bindBoxEvent();
				} else {
					alert(result.message);
				}
				Eop.Dialog.close("actionDlg");
			},
			error : function(e) {
				$.Loading.hide();
				alert("出错啦:(");
				Eop.Dialog.close("actionDlg");
			}
		};
		$("#authForm").ajaxSubmit(options);
	},

	// 绑定修改和删除事件
	bindBoxEvent : function() {
		var self = this;
		$("#actbox .modify").unbind("click").bind("click", function() {
					self.modifyAuth($(this).attr("authid"));
				});
		$("#actbox .delete").unbind("click").bind("click", function() {
					self.deleteAuth($(this).attr("authid"));
				});
	},

	/**
	 * 绑定对话框打开后各种事件
	 */
	bindDlgEvent : function(type) {
		var self = this;
		self.createTree();
		var menuObjvalue = $("#menuObjvalue").val();
		var appObjvalue = $("#appObjvalue").val();
		var btnObjvalue = $("#btnObjvalue").val();
		if (menuObjvalue) {
			var values = menuObjvalue.split(",");
			for (var i = 0; i < values.length; i++) {
				var v = values[i];
				$("div[tabid='menu']").find("ul.checktree input[value='" + v + "']")
						.attr("checked", true);
			}
		}
		if(appObjvalue){
			var appValues = appObjvalue.split(",");
			for (var i = 0; i < appValues.length; i++) {
				var v = appValues[i];
				$("div[tabid='app']").find("ul.checktree input[value='" + v + "']")
						.attr("checked", true);
			}
		}
		if(btnObjvalue){
			var btnValues = btnObjvalue.split(",");
			for (var i = 0; i < btnValues.length; i++) {
				var v = btnValues[i];
				$("div[tabid='btn']").find("ul.checktree input[value='" + v + "']")
						.attr("checked", true);
			}
		}
		$("ul.checktree").checkTree();
		$("#authBtn").click(function() {
					self.saveAuth();
				});
		if(type =="view"){
			$("#authname").closest("tr").hide();
			$(".checktree").find(".checkbox").hide();
		}
	},
	modifyAuth : function(authid) {
		var self = this;
		$("#actionDlg").load('auth!edit.do?ajax=yes&authid=' + authid,
				function() {
					self.bindDlgEvent();
				});
		Eop.Dialog.open("actionDlg");
	},
	viewAuth : function(roleid) {
		var self = this;
		$("#actionDlg").load('auth!edit.do?ajax=yes&roleid=' + roleid,
				function() {
					$("#authBtn").hide();
					self.bindDlgEvent('view');
				});
		Eop.Dialog.open("actionDlg");
	},
	deleteAuth : function(authid) {

		if (!confirm("确认删除此权限吗?")) {
			return;
		}

		$.ajax({
					url : "auth!delete.do?ajax=yes",
					data : 'authid=' + authid,
					type : "POST",
					dataType : 'json',
					success : function(result) {
						if (result.result == 1) {
							$.Loading.hide();
							$("#li_" + result.authid).remove();
						} else {
							alert(result.message);
						}
					},
					error : function(e) {
						$.Loading.hide();
						alert("出错啦:(");
					}
				});
	}

};

var authSel={ 
		init : function() {
			Eop.Dialog.init({id : "authSelDlg",title : "选择权限"});
			$("#sel_auth").click(function() {
				        var url = ctx+"/core/admin/auth!selAuth.do?ajax=yes";
				        $("#authSelDlg").load(url,function(){
				        	authSel.initFun();
				        });
				        Eop.Dialog.open();
					});
			
		},	
		initFun:function(){
			authSel.checkAuth();
        	authSel.search();
		},
		search:function(){
			$("#searchBtn").unbind("click").bind("click",function(){
				var name = $("#search_auth_name").val();
				var url = ctx+"/core/admin/auth!selAuth.do?ajax=yes";
				Cmp.ajaxSubmit('serchform','authSelDlg',url,{},authSel.initFun);
				
			});
		},
		checkAuth:function(){
			$("#authSelBtn").unbind("click").bind("click",function(){
				var arr = [];
				var arrLength = $("[name='acts']").length;
				
				$("[name='selAuthCheck']:checked").each(function(){
					var count = 0;
					var actid = $(this).val();
					var name = $(this).attr("auth_name");
					var type = $(this).attr("auth_type");
					if(type=="data"){
						type="数据";
					}else if(type=="menu"){
						type="菜单";
					}else if(type=="app"){
						type="应用";
					}else if(type=="btn"){
						type="按钮";
					}
					
					
					   if(arrLength>0){
						    $("[name='acts']").each(function(){
						    	if(actid==$(this).val()){
									count+=1;
								}
						    });
					   }
						
					if(count==0){
						$("#authBody").append(authSel.appendHtmlStr(actid,name,type));
					}
				});
				authSel.removeTr();
				Eop.Dialog.close();
			});
			
		},
		appendHtmlStr:function(actid,name,type){
			var htmlStr =   "<tr id='tr_"+actid+"'>"+
							" <input type='hidden' value='"+actid+"' name=acts>"+
							"<td style='text-align:center'>"+actid+"</td>"+
							"<td style='text-align:center'>"+name+"</td>"+
							"<td style='text-align:center'>"+type+"</td>"+
							"<td style='text-align:center'>"+
							" <a title ='删除' href='javascript:;' class='p_prted' actid='"+actid+"' name=removeAuth>删除</a>"+
							"</td>"+
							"</tr>";
			return htmlStr;
		},
		removeTr:function(){
			 $("[name='removeAuth']").each(function(){
				 $(this).unbind("click").bind("click",function(){
					 var actid = $(this).attr("actid");
					  $("#tr_"+actid).remove();
				 });
			   });
		}
};