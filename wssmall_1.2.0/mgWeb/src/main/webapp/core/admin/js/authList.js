$(function(){
	authList.init();
});

var authList = {
		init:function(){
			
			//authList.bindDlgEvent();
			
			authList.bindBoxEvent();
		},
		// 绑定修改和删除事件
		bindBoxEvent : function() {
			
			var self = this;
			$("[name='editAuth']").unbind("click").bind("click", function() {
				self.modifyAuth($(this).attr("authid"),$(this).attr("authtype"));
				       
					});
			$("[name='delAuth']").unbind("click").bind("click", function() {
						self.deleteAuth($(this).attr("authid"),$(this).attr("authtype"));
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
		modifyAuth : function(authid,authtype) {
			var self = this;
/*			if(authtype=='data'){
				var loadurl = "/core/admin/orgRoleAction!roleAuthList.do?authid="+authid;
				$("#databox").load(loadurl,function(){});
			}else{
				window.location.href =ctx +"/core/admin/auth!edit.do?authid="+authid;
			}*/
			window.location.href =ctx +"/core/admin/auth!edit.do?authid="+authid+"&type="+authtype;
		},
		viewDataAuth : function(authid) {
			var self = this;
			$("#dataAuthPack").load('orgRoleAction!roleAuthList.do?ajax=yes&authid=' + authid,
					function() {
				
			});
			Eop.Dialog.open("dataAuthPack");
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
		deleteAuth : function(authid,type) {

			if (!confirm("确认删除此权限吗?")) {
				return;
			}
			$.ajax({
						url : "auth!delete.do?ajax=yes&authid=" + authid + '&type=' + type,
						type : "POST",
						dataType : 'json',
						success : function(result) {
							if (result.result == 1) {
								$.Loading.hide();
								alert("操作成功");
								window.location.href =ctx+"/core/admin/auth!authList.do";
								//$("#li_" + result.authid).remove();
							} else {
								alert(result.message);
							}
						},
						error : function(e) {
							$.Loading.hide();
							alert("出错啦:(");
						}
					});
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
							alert("操作成功");
							window.location.href =ctx +"/core/admin/auth!authList.do";
							//$("#li_" + result.authid + ">span").html(authname);
						} else {// 添加加入一行
							alert("操作成功")
							window.location.href =ctx +"/core/admin/auth!authList.do";
						}
						
					} else {
						alert(result.message);
					}
				//	Eop.Dialog.close("actionDlg");
				},
				error : function(e) {
					$.Loading.hide();
					alert("出错啦:(");
					//Eop.Dialog.close("actionDlg");
				}
			};
			$("#authForm").ajaxSubmit(options);
		}
};