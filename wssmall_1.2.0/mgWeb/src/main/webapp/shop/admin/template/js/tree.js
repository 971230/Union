var copyTplTreeVal;
//-------------------------------------节点树BEGIN----------------------------------
function getTemplateTree(index){
	$("#orderTemplateId").val(index);//点击模板时设置模板ID
	var orderTemplateId=$("#orderTemplateId").val();
	var tree_div = $("#tree_div");
	tree_div.empty();
	$.ajax({
		type : "post",
		async : false,
		url : "ordTplAction!qryRootDirectory.do?ajax=yes&orderTemplateId="+orderTemplateId+"&super_id=",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(i, orderTemplateNode) {
					if (orderTemplateNode.super_node_id == null||orderTemplateNode.super_node_id=="") {
						if(orderTemplateNode.node_type=="0"){
							root = "<ul id='tree'  root_dir='root_dir"+orderTemplateNode.node_id+"'><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a tree_right_click='tree_right_click' id='root_id"+orderTemplateNode.node_id+"'  href='javascript:void(0);'><i class='treeic1'></i>"	+ orderTemplateNode.node_name + "</a></li></ul>";
						}else if(orderTemplateNode.node_type=="1"){
							root = "<ul id='tree'  root_dir='root_dir"+orderTemplateNode.node_id+"'><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a tree_right_click='tree_right_click' id='root_id"+orderTemplateNode.node_id+"'  href='javascript:void(0);'><i class='treeic2'></i>"	+ orderTemplateNode.node_name + "</a></li></ul>";
						}else if(orderTemplateNode.node_type=="2"){
							root = "<ul id='tree'  root_dir='root_dir"+orderTemplateNode.node_id+"'><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a tree_right_click='tree_right_click' id='root_id"+orderTemplateNode.node_id+"'  href='javascript:void(0);'><i class='treeic4'></i>"	+ orderTemplateNode.node_name + "</a></li></ul>";
						}else{
							root = "<ul id='tree'  root_dir='root_dir"+orderTemplateNode.node_id+"'><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a tree_right_click='tree_right_click' id='root_id"+orderTemplateNode.node_id+"'  href='javascript:void(0);'><i class='treeic1'></i>"	+ orderTemplateNode.node_name + "</a></li></ul>";
						}
						
						tree_div.append(root);
						$("a[id='root_id" + orderTemplateNode.node_id + "']").bind("contextmenu", function(event) {
							bindrightclick(orderTemplateNode.node_id, "menu0", orderTemplateNode.node_name,orderTemplateNode.order_template_id, event);
							return  false; // 等同于continue
						});
						
						$("a[id='root_id"+orderTemplateNode.node_id+"']").bind("click", function() {
							hideShowClsss(this);
							$("#nodeId").val(orderTemplateNode.node_id);//点击设置当前节点
							$("#nodeName").val(orderTemplateNode.node_name);//点击设置当前节点
							getTemplateChildren(orderTemplateNode.node_id,$("#treemenu"+orderTemplateNode.node_id), $("#tree"));
							var $this = $("ul[root_dir='root_dir"+orderTemplateNode.node_id+"']").children("li").children("ul");
							if($this.length > 0) {
								$this.is(":hidden")? $this.show() : $this.hide() ;
							}
						});
					}
				});
			} else {
				alert("未初始化节点！");
			}
			var $tree = $("#tree");//获取tree
//			$tree.is(":hidden")? $tree.show() : $tree.hide() ;
		}
	});

}

function getTemplateChildren(super_id,liNode,ulNode){
	var orderTemplateId=$("#orderTemplateId").val();
	$.ajax({
		type : "post",
		async : false,
		url : "ordTplAction!qryRootDirectory.do?ajax=yes&orderTemplateId="+orderTemplateId+"&super_id="+super_id,
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(ii, orderTemplateNode) {
					if (orderTemplateNode.super_node_id != null&&orderTemplateNode.super_node_id!="") {
						var is_load = $("ul[root_dir='root_dir"+orderTemplateNode.node_id+"']").attr("is_load");
						if (is_load == "yes") {
							return true;
						}
						if(orderTemplateNode.node_type=="0"){
							dir = "<ul is_load='yes'  id='tree"+orderTemplateNode.node_id+"'  root_dir='root_dir"+orderTemplateNode.node_id+"' ><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a id='root_id"+ orderTemplateNode.node_id +"'  href='javascript:void(0);'><i class='treeic1'></i>"+ orderTemplateNode.node_name +"</a></li></ul>";
						}else if(orderTemplateNode.node_type=="1"){
							dir = "<ul is_load='yes'  id='tree"+orderTemplateNode.node_id+"'  root_dir='root_dir"+orderTemplateNode.node_id+"' ><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a id='root_id"+ orderTemplateNode.node_id +"'  href='javascript:void(0);'><i class='treeic2'></i>"+ orderTemplateNode.node_name +"</a></li></ul>";
						}else if(orderTemplateNode.node_type=="2"){
							dir = "<ul is_load='yes'  id='tree"+orderTemplateNode.node_id+"'  root_dir='root_dir"+orderTemplateNode.node_id+"' ><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a id='root_id"+ orderTemplateNode.node_id +"'  href='javascript:void(0);'><i class='treeic4'></i>"+ orderTemplateNode.node_name +"</a></li></ul>";
						}else{
							dir = "<ul is_load='yes'  id='tree"+orderTemplateNode.node_id+"'  root_dir='root_dir"+orderTemplateNode.node_id+"' ><li id='treemenu"+orderTemplateNode.node_id+"' class='close'><a id='root_id"+ orderTemplateNode.node_id +"'  href='javascript:void(0);'><i class='treeic1'></i>"+ orderTemplateNode.node_name +"</a></li></ul>";
						}
						
						
						liNode.append(dir);
						$("a[id='root_id" + orderTemplateNode.node_id + "']").bind("contextmenu", function(event) {
							bindrightclick(orderTemplateNode.node_id, "menu0", orderTemplateNode.node_name,orderTemplateNode.order_template_id, event);
							return false;
						});
						ulNode.children("li").children("ul").hide();
				
						$("a[id='root_id"+orderTemplateNode.node_id+"']").bind("click", function() {
							hideShowClsss(this);
							$("#nodeId").val(orderTemplateNode.node_id);//点击设置当前节点
							$("#nodeName").val(orderTemplateNode.node_name);//点击设置当前节点
							getTemplateChildren(orderTemplateNode.node_id,$("#treemenu"+orderTemplateNode.node_id), $("#tree"+orderTemplateNode.node_id));
							var $this = $("ul[root_dir='root_dir"+orderTemplateNode.node_id+"']").children("li").children("ul");
							if($this.length > 0) {
								$this.is(":hidden")? $this.show() : $this.hide() ;
							}
						});
					
					}
				});
			}
			
		}
	});
}

//---------------------------------------节点树END-----------------------------------------
//--------------------------------------目录树BEGIN----------------------------------------
function getCatalogueTree(){
	var tree_div = $("#catalogue_div");
	tree_div.empty();
	$.ajax({
		type : "post",
		async : false,
		url : "ordTplAction!qryRootCatalogue.do?ajax=yes&id=&pid=-1",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(i, catalogue) {
					if (catalogue.pid == "-1") {
						root = "<ul id='treeUL'  catalogue_dir='catalogue_dir"+catalogue.id+"'><li id='treeCatalogue"+catalogue.id+"' class='close'><a tree_right_click='tree_right_click' id='root_id"+catalogue.id+"'  root_tree_tag='root_tree_tag'  href='javascript:void(0);'><i class='treeic1'></i>"	+ catalogue.name + "</a></li></ul>";
						tree_div.append(root);
						$("a[id='root_id" + catalogue.id + "']").bind("contextmenu", function(event) {
							bindCatalogueRightclick(catalogue.id, "menu1", catalogue.name, event);
							return  false; // 等同于continue
						});
						
						$("a[id='root_id"+catalogue.id+"']").bind("click", function() {
							hideShowClsss(this);
							//$("#topDiv").load(ctx+"/shop/admin/ordTplAction!getOrdTplList.do?ajax=yes");
							$("#catalogueId").val(catalogue.id);//点击设置当前节点
							$("#catalogueName").val(catalogue.name);//点击设置当前节点
							getCatalogueChildren(catalogue.id,$("#treeCatalogue"+catalogue.id), $("#treeUL"));
							var $this = $("ul[catalogue_dir='catalogue_dir"+catalogue.id+"']").children("li").children("ul");
							if($this.length > 0) {
								$this.is(":hidden")? $this.show() : $this.hide() ;
							}
						});
					}
				});
			} else {
				alert("未初始化节点！");
			}
			var $tree = $("#treeUL");//获取tree
//			$tree.is(":hidden")? $tree.show() : $tree.hide() ;
		}
	});

}

function getCatalogueChildren(super_id,liNode,ulNode){
	var orderTemplateId=$("#orderTemplateId").val();
	//加载子目录
	$.ajax({
		type : "post",
		async : false,
		url : "ordTplAction!qryRootCatalogue.do?ajax=yes&id=&pid="+super_id,
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(ii, catalogue) {
					if (catalogue.pid != null&&catalogue.pid!="") {
						var is_load = $("ul[catalogue_dir='catalogue_dir"+catalogue.id+"']").attr("is_load");
						if (is_load == "yes") {
							return true;
						}
						dir = "<ul is_load='yes'  id='treeUL"+catalogue.id+"'  catalogue_dir='catalogue_dir"+catalogue.id+"' ><li id='treeCatalogue"+catalogue.id+"' class='close'><a id='root_id"+ catalogue.id +"'  href='javascript:void(0);'><i class='treeic1'></i>"+ catalogue.name +"</a></li></ul>";
						liNode.append(dir);
						$("a[id='root_id" + catalogue.id + "']").bind("contextmenu", function(event) {
							bindCatalogueRightclick(catalogue.id, "menu1", catalogue.name, event);
							return false;
						});
						ulNode.children("li").children("ul").hide();
				
						$("a[id='root_id"+catalogue.id+"']").die('click').live("click", function() {
							hideShowClsss(this);
							//根据catalogue获取对应的值
							//重新加载对应的DIV值root_tree_tag
							//$("#topDiv").load(ctx+"/shop/admin/ordTplAction!getOrdTplList.do?ajax=yes&ordTplDTO.catalogue_id="+catalogue.id);
							$("#catalogueId").val(catalogue.id);//点击设置当前节点
							$("#catalogueName").val(catalogue.name);//点击设置当前节点
							getCatalogueChildren(catalogue.id,$("#treeCatalogue"+catalogue.id), $("#treeUL"+catalogue.id));
							var $this = $("ul[catalogue_dir='catalogue_dir"+catalogue.id+"']").children("li").children("ul");
							if($this.length > 0) {
								$this.is(":hidden")? $this.show() : $this.hide() ;
							}
						});
					
					}
				});
			}
			
		}
	});
	
	//加载模板列表
	$.ajax({
		type : "post",
		async : false,
		url : "ordTplAction!getTplList.do?ajax=yes&id="+super_id,
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(iii, orderTemplate) {
					if (orderTemplate.order_template_id != null&&orderTemplate.order_template_id!="") {
						var is_load = $("ul[orderTpl_dir='orderTpl_dir"+orderTemplate.order_template_id+"']").attr("is_load");
						if (is_load == "yes") {
							return true;
						}
						dir = "<ul is_load='yes'  id='tplUL"+orderTemplate.order_template_id+"'  orderTpl_dir='orderTpl_dir"+orderTemplate.order_template_id+"' ><li id='treeOrderTpl"+orderTemplate.order_template_id+"' class='close'><a id='tpl_id"+ orderTemplate.order_template_id +"'  href='javascript:void(0);'><i class='treeic4'></i>"+ orderTemplate.order_template_name +"</a></li></ul>";
						liNode.append(dir);
						$("a[id='tpl_id" + orderTemplate.order_template_id + "']").bind("contextmenu", function(event) {
							bindTplRightclick(orderTemplate.order_template_id, "menu3", orderTemplate.order_template_name, event);
							return false;
						});
						ulNode.children("li").children("ul").hide();
				
						$("a[id='tpl_id"+orderTemplate.order_template_id+"']").die('click').live("click", function(event) {
							if(event.button == 2)
								return ;
							$("#orderTemplateId").val(orderTemplate.order_template_id);
							$("#orderTemplateName").val(orderTemplate.order_template_name);
							//getTemplateTree(orderTemplate.order_template_id);   //加载节点树，只要放开注释，在JSP中加DIV即可
							$('span[name="model_switch"]').attr('template_id', orderTemplate.order_template_id);
							getTreeBase(orderTemplate.order_template_id);
						});
					
					}
				});
			}
		}
	});
	
}
//-----------------------------------------目录树END------------------------------------------------
function getIcByRel(rel_type) {
	var ic = "";
	if(rel_type=="0") {
		ic = "<i class='ic_bx'></i>";
	} else if (rel_type=="1") {
		ic = "<i class='ic_yl'></i>";
	} else if (rel_type=="2") {
		ic = "<i class='ic_hc'></i>";
	} else if (rel_type=="3") {
		ic = "<i class='ic_hc'></i>";
	} else {
		ic = "<i class='ic_bx'></i>";
	}
	return ic;
}

	
function hideShowClsss(e) {
	var li_class = $(e).parent().attr("class");
	if (li_class == "open") {
		$(e).parent().attr("class", "close");
		var $ul = $(e).nextAll("ul");
		for (var i=0; i<$ul.length; i++) {
			getNextAllNode($ul[i])
		}
	} else {
		$(e).parent().attr("class", "open");
	}
}

//循环收缩节点
function getNextAllNode(ee) {
	var li_class = $(ee).children("li").attr("class");
	if (li_class == "open") {
		$(ee).children("li").attr("class", "close");
	}
	var ul = $(ee).children("li").children("a").nextAll("ul");
	if (ul.length > 0) {
		ul.hide();
		for (var j=0;j<ul.length;j++) {
			getNextAllNode(ul[j]);
		}
	}
}

 function bindrightclick (nodeId, menu, nodeName,orderTemplateId, event) {
	event.preventDefault();
	$("#nodeId").val(nodeId);
	$("#nodeName").val(nodeName);
	$("#orderTemplateId").val(orderTemplateId);
	//创建右键菜单
	var oMenu = document.getElementById(menu);
	var aLi = oMenu.getElementsByTagName("li");
	//菜单鼠标移入/移出样式
	for (i = 0; i < aLi.length; i++) {
		//鼠标移入样式
		aLi[i].onmouseover = function ()
		{
			this.className = "active"	
		};
		//鼠标移出样式
		aLi[i].onmouseout = function ()
		{
			this.className = ""	
		}
	}
	
	//页面点击后自定义菜单消失
	document.onclick = function () {
		oMenu.style.display = "none";
	}
	var event = event;
	var style = oMenu.style;
	style.display = "block";		
	var X = (event.pageX || event.clientX + document.body.scroolLeft)+"px";
	var Y = (event.pageY || event.clientY + document.body.scrollTop)+"px";
	style.top = Y;
	style.left = X;
	
}

 function bindCatalogueRightclick (catalogueId, menu, catalogueName, event) {
	$("#catalogueId").val(catalogueId);
	$("#catalogueName").val(catalogueName);
	//创建右键菜单
	var oMenu = document.getElementById(menu);
	var aLi = oMenu.getElementsByTagName("li");
	//菜单鼠标移入/移出样式
	for (i = 0; i < aLi.length; i++) {
		//鼠标移入样式
		aLi[i].onmouseover = function ()
		{
			this.className = "active"	
		};
		//鼠标移出样式
		aLi[i].onmouseout = function ()
		{
			this.className = ""	
		}
	}
	if ($(oMenu).attr("id")=="menu1") {
		$("#menu3").css("display", "none");
	} else if($(oMenu).attr("id")=="menu3") {
		$("#menu1").css("display", "none");
	}
	//页面点击后自定义菜单消失
	document.onclick = function () {
		oMenu.style.display = "none";
	}
	var event = event;
	var style = oMenu.style;
	style.display = "block";		
	var X = (event.pageX || event.clientX + document.body.scroolLeft)+"px";
	var Y = (event.pageY || event.clientY + document.body.scrollTop)+"px";
	style.top = Y;
	style.left = X;
}

 function bindTplRightclick(orderTemplateId, menu, orderTemplateName, event){
	    $("#orderTemplateId").val(orderTemplateId);
		$("#orderTemplateName").val(orderTemplateName);
		//创建右键菜单
		var oMenu = document.getElementById(menu);
		var aLi = oMenu.getElementsByTagName("li");
		//菜单鼠标移入/移出样式
		for (i = 0; i < aLi.length; i++) {
			//鼠标移入样式
			aLi[i].onmouseover = function ()
			{
				this.className = "active"	
			};
			//鼠标移出样式
			aLi[i].onmouseout = function ()
			{
				this.className = ""	
			}
		}
		if ($(oMenu).attr("id")=="menu1") {
			$("#menu3").css("display", "none");
		} else if($(oMenu).attr("id")=="menu3") {
			$("#menu1").css("display", "none");
		} 
		//页面点击后自定义菜单消失
		document.onclick = function () {
			oMenu.style.display = "none";
		}
		var event = event;
		var style = oMenu.style;
		style.display = "block";		
		var X = (event.pageX || event.clientX + document.body.scroolLeft)+"px";
		var Y = (event.pageY || event.clientY + document.body.scrollTop)+"px";
		style.top = Y;
		style.left = X;
 }

 var Directory=$.extend({},Eop.Grid,{
     dlg_id:'showDlg',
     title:'标题',
     width:'',
 	init:function(){
 		var self = this;
 		//-----------------节点树-----------------
 		$("#viewNode").click(function() {
 			self.title="查看节点";
 			self.width='600px';
 			var id = $("#nodeId").val();
 			if(id==null&&id==""){
 				alert("请选择查看的节点！");
 				return;
 			}
 			var url = "ordTplAction!addTemplateNode.do?ajax=yes&flag=view&id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#addNode").click(function() {
 			self.title="新增子节点";
 			self.width='600px';
 			var id = $("#nodeId").val();
 			var treeDiv=$("#tree_div").innerHTML;
 			if(id==null&&id==""&&treeDiv!=""){
 				alert("请选择父节点！");
 				return;
 			}
 			var nodeName = $("#nodeName").val();
 			var orderTemplateId=$("#orderTemplateId").val();
 			var url = "ordTplAction!addTemplateNode.do?ajax=yes&flag=add&id="+id+"&orderTemplateId="+orderTemplateId;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#addTemplateRootNode").click(function() {
 			self.title="新增节点";
 			self.width='600px';
 			var id = $("#nodeId").val();
 			var nodeName = $("#nodeName").val();
 			var orderTemplateId=$("#orderTemplateId").val();
 			var url = "ordTplAction!addTemplateNode.do?ajax=yes&flag=add&id="+id+"&orderTemplateId="+orderTemplateId;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#modNode").click(function() {
 			self.title="修改节点";
 			self.width='600px';
 			var id = $("#nodeId").val();
 			if(id==null&&id==""){
 				alert("请选择修改的节点！");
 				return;
 			}
 			var url = "ordTplAction!addTemplateNode.do?ajax=yes&flag=edit&id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#delNode").click(function(){
 			var id = $("#nodeId").val();
 			if(id==null&&id==""){
 				alert("请选择删除的节点！");
 				return;
 			}
			var bool = confirm("确认删除模板节点吗？");
			if (bool) {
				$.ajax({
					type : "post",
					async : false,
					url : "ordTplAction!delTemplateNode.do?ajax=yes&id="+id,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.message);
						if (data.result == '0') {
							$("#tree"+id).remove();
						}
					}
				});
			} else {
				return;
			}
		});
 		//------------------------目录树-------------------------
 		$("#addCatalogue").click(function() {
 			self.title="新增子目录";
 			self.width='600px';
 			var id = $("#catalogueId").val();
 			var treeDiv=$("#catalogue_div").innerHTML;
 			if(id==null&&id==""){
 				alert("请选择父节点！");
 				return;
 			}
 			var catalogueName = $("#catalogueName").val();
 			var url = "ordTplAction!addCatalogue.do?ajax=yes&flag=add&id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#modCatalogue").click(function() {
 			self.title="修改目录";
 			self.width='600px';
 			var id = $("#catalogueId").val();
 			var treeDiv=$("#catalogue_div").innerHTML;
 			if(id==null&&id==""){
 				alert("请选择修改的目录！");
 				return;
 			}
 			var catalogueName = $("#catalogueName").val();
 			var url = "ordTplAction!addCatalogue.do?ajax=yes&flag=edit&id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#delCatalogue").click(function(){
 			var id = $("#catalogueId").val();
 			if(id==null&&id==""){
 				alert("请选择删除的节点！");
 				return;
 			}
			var bool = confirm("确认删除目录节点吗？");
			if (bool) {
				
				$.ajax({
					type : "post",
					async : false,
					url : "ordTplAction!delCatalogue.do?ajax=yes&id="+id,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.message);
						if (data.result == '0') {
							$("#treeUL"+id).remove();
						}
					}
				});
			} else {
				return;
			}
		});
 		//----------------------------------模板按钮-----------------------------
 		$("#addOrderTpl").click(function() {
 			self.title="新增模板";
 			self.width='600px';
 			var id = $("#catalogueId").val();
 			if(id==null&&id==""){
 				alert("请选择目录！");
 				return;
 			}
 			var url ="ordTplAction!toTplConfig.do?ajax=yes&ordTplDTO.flag=add&ordTplDTO.catalogue_id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#modOrderTpl").click(function() {
 			self.title="修改模板";
 			self.width='600px';
 			var id = $("#orderTemplateId").val();
 			var orderTemplateName = $("#orderTemplateName").val();
 			var url ="ordTplAction!toTplConfig.do?ajax=yes&ordTplDTO.flag=update&ordTplDTO.order_template_id="+id;
 	    	self.toUrlOpen(url, self.title);
 		});
 		$("#copyOrderTpl").click(function() {
 			var id = $("#orderTemplateId").val();
 			$.ajax({
 				type : "post",
 				async : false,
 				url : "ordTplAction!copyOrderTpl.do?ajax=yes&id="+id,
 				data : {},
 				dataType : "json",
 				beforeSend: function(){
 					document.getElementById("loading").style.display="";
 				},
 				complete: function(){
 					setTimeout('notloading()',1000);
 				},
 				success : function(data) {
 					if(data!=null){
 						copyTplTreeVal=data;
 						//清空粘贴按钮
 						var paste_div=$("#rightMenu1 #menu1 li #pasteOrderTpl");
						if(paste_div!=null){
							paste_div.parent("li").remove();
						}
 						//在目录下生成粘贴按钮
 						paste_div=$("<li><em class=''></em><a id='pasteOrderTpl' href='javascript:void(0);'>粘贴模板</a></li>");
 						$("#rightMenu1 #menu1").append(paste_div);
 						$("#pasteOrderTpl").bind("click",function(){
 							var catalogueId=$("#catalogueId").val();
 							if(catalogueId!=null && catalogueId!=""){
 								//在该目录id下添加模板
 								var url=ctx+"/shop/admin/ordTplAction!saveTplByCatalogueId.do?ajax=yes";
 	 							var param={"copyTplTreeVal":copyTplTreeVal,"catalogueId":catalogueId,"tplId":id};
 	 							$.ajax({
 	 								type:"post",
 	 								dataType:"json",
 	 								url:url,
 	 								data:{"param":JSON.stringify(param)},
 	 								beforeSend:function(){
 	 									$("#loading").css("display","block");
 	 								},
 	 								complete:function(){
 	 									$("#loading").css("display","none");
 	 								},
 	 								success:function(respData){
 	 									if(respData!=null && respData.status=='0'){
 	 	 									if(respData.msg=='repeat'){
 	 	 										alert("该模板在该目录下已存在");
 	 	 										return;
 	 	 									}
 	 	 									$("a[id='root_id"+catalogueId+"']").trigger("click");
 	 	 								}
 	 								}
 	 							});
 							}
 				 		});
 					}
 				}
 			}); 			
 		});
 		
 		$("#delOrderTpl").click(function(){
 			var id = $("#orderTemplateId").val();
 			var orderTemplateName = $("#orderTemplateName").val();
 			if(id==null&&id==""){
 				alert("请选择删除的模板！");
 				return;
 			}
			var bool = confirm("确认删除模板【"+orderTemplateName+"】吗？");
			if (bool) {
				$.ajax({
					type : "post",
					async : false,
					url : "ordTplAction!delTel.do?ordTplDTO.order_template_id="+id+"&ajax=yes",
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
						if (data.status == '0') {
							$("#tplUL"+id).remove();
						}
					}
				});
			} else {
				return;
			}
		});
 		
     	Eop.Dialog.init({id:'showDlg',modal:false,title:self.title,width:600});
 	},	
 	toUrlOpen:function(url, title){
 	   Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
 	   Eop.Dialog.open(this.dlg_id);
 	   $("#dlg_showDlg").children("div").children("div").children("div").text(title);
 	},
 	onAjaxCallBack:function(){//ajax回调函数	    
 		$('input.dateinput').datepicker();
 	},
 	page_close:function(){
 	     Eop.Dialog.close(this.dlg_id);
 	}
 	
 });
 $(function(){
 	Directory.init();
 	getCatalogueTree();
 });

function notloading(){
	document.getElementById("loading").style.display="none";
}

function getTreeBase(id){
	//alert(id);
	$("#topDiv").load(ctx+"/shop/admin/ordTplAction!getOrdTplTree.do?ajax=yes&id="+id);
}

