$(document).ready(function(){
	var tree_div = $("#tree_div");
	$.ajax({
		type : "post",
		async : false,
		url : "ruleManager!qryRootDirectory.do?ajax=yes&id=&pid=0",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(i, directoryVo) {
					if (directoryVo.id == 0) {
						root = "<ul id='tree'><li id='treemenu' class='open'><a id='"+directoryVo.id+"' href='#'><i class='treeic1'></i>"	+ directoryVo.name + "</a></li></ul>"
						tree_div.append(root);
						$("a[id='" + directoryVo.id + "']").bind("contextmenu", function() {
							bindright.bindrightclick(directoryVo.id, "menu0");
						});
					}
					if (directoryVo.id != 0) {
						dir = "<ul id='dir_ul"+i+"'><li id='dir_li"+i+"' class='open'><a id='dir_id"+ directoryVo.id +"' href='#'><i class='treeic1'></i>"+ directoryVo.name +"</a></li></ul>";
						$("#treemenu").append(dir);
						$("a[id='dir_id" + directoryVo.id + "']").bind("contextmenu", function() {
							bindright.bindrightclick(directoryVo.id, "menu1");
						});
						// 加载方案节点
						$.ajax({
							type : "post",
							async : false,
							url : "ruleManager!qrySchemeNode.do?ajax=yes&id=&pid="+directoryVo.id,
							data : {},
							dataType : "json",
							success : function(data) {
								if (data != null && data.length > 0) {
									$.each(data, function(j, schemeNodeVo) {
										schemenode = "<ul id='scheme_ul"+i+j+"'>"+
												 "<li id='scheme_li"+i+j+"' class='open'><a id='scheme_id" 
													+ schemeNodeVo.plan_id + 
													"' href='#'><i class='treeic2'></i>" 
													+ schemeNodeVo.plan_name + "</a>"+
												 "</li></ul>";
										$("#dir_li"+i).append(schemenode);
										$("a[id='scheme_id" + schemeNodeVo.plan_id + "']").bind("contextmenu", function() {
											bindright.bindrightclick(schemeNodeVo.plan_id, "menu2");
										});
										 $.ajax({
											type : "post",
											async : false,
											url : "ruleManager!qryRuleNode.do?ajax=yes&id="+schemeNodeVo.plan_id+"&pid=0",
											data : {},
											dataType : "json",
											success : function(data) {
												if (data != null && data.length > 0) {
													$.each(data, function(k, ruleNodeVo) {
														rulenode = "<ul id='rule_ul"+i+j+k+"'>"+
														 "<li id='rule_li"+i+j+k+"' class='open'><a id='rule_id" 
															+ ruleNodeVo.rule_id + 
															"' href='#'><i class='treeic3'></i>" 
															+ ruleNodeVo.rule_name + "</a>"+
														 "</li></ul>";
														$("#scheme_li"+i+j).append(rulenode);														
														$.ajax({
															type : "post",
															async : false,
															url : "ruleManager!qryRuleChildNode.do?ajax=yes&id="+schemeNodeVo.plan_id+"&pid="+ruleNodeVo.rule_id,
															data : {},
															dataType : "json",
															success : function(data) {
																if (data != null && data.length > 0) {
																	$.each(data, function(n, ruleNodeVo) {
																		rulenodechildrens = "<ul id='rule_ul"+i+j+k+n+"'>"+
																		 "<li id='rule_li"+i+j+k+n+"'><a id='rule_id" 
																			+ ruleNodeVo.rule_id + 
																			"' href='#'><i class='treeic3'></i>" 
																			+ ruleNodeVo.rule_name + "</a>"+
																		 "</li></ul>";
																		$("#rule_li"+i+j+k).append(rulenodechildrens);
																		$("#rule_ul"+i+j+k).children("li").children("ul").hide();
																		$("a[id='rule_id" + ruleNodeVo.rule_id + "']").bind("click", function() {
																			alert("children node!");
																		});
																		var $this = $(this).nextAll("ul");
																		if($this.length > 0){
																			//判断当前菜单下是否有下级菜单
																			$this.is(":hidden") ? $this.show() : $this.hide();
																		}
																	});
																}
															}
														});															
														$("#scheme_ul"+i+j).children("li").children("ul").hide();
														$("a[id='rule_id" + ruleNodeVo.rule_id + "']").bind("click", function() {
																													
															var $this = $(this).nextAll("ul");
															if($this.length > 0){
																//判断当前菜单下是否有下级菜单
																$this.is(":hidden") ? $this.show() : $this.hide();
															}
														});
													});
												}
											}
										 });
										 
										$("#dir_ul"+i).children("li").children("ul").hide();
										$("a[id='scheme_id" + schemeNodeVo.plan_id + "']").bind("click", function() {
											var $this = $(this).nextAll("ul");
											if($this.length > 0){
												//判断当前菜单下是否有下级菜单
												$this.is(":hidden") ? $this.show() : $this.hide();
											}
										});
									});
								}
							}
						});
						$("a[id='dir_id"+directoryVo.id+"']").bind("click", function() {
							var $this = $(this).nextAll("ul");
							if($this.length > 0){
								//判断当前菜单下是否有下级菜单
								$this.is(":hidden") ? $this.show() : $this.hide();
							}
						});
					}
				});
			} else {
				alert("未初始化节点！");
			}			
			var $tree = $("#tree");//获取tree
			var $a_tops = $tree.children("li").children("a");//找到tree下第一层菜单
			$tree.children("li").children("ul").hide();
			$a_tops.bind("click", function(){//为第一层菜单绑定事件
				var $this = $(this).nextAll("ul");
				if($this.length > 0){ //判断当前第一层菜单下是否有第二层菜单
					$this.is(":hidden") ? $this.show() : $this.hide();//根据状态判断是隐藏还是现实第二层菜单
				}
			});			
		}
	});	
});

var bindright = {	
	bindrightclick : function (id, menu) {
		$("#nodeid").val(id);
		//创建右键菜单
		var oMenu = document.getElementById(menu);
		var aLi = oMenu.getElementsByTagName("li");
		//菜单鼠标移入/移出样式
		for (i = 0; i < aLi.length; i++){
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
		
		//自定义菜单
		window.oncontextmenu = function (event){
			var event = event || window.event;
			var style = oMenu.style;
			style.display = "block";
			style.top = event.clientY + "px";
			style.left = event.clientX + "px";
			return false;
		};
		
		//页面点击后自定义菜单消失
		document.onclick = function (){
			oMenu.style.display = "none"	
		}
	}}

var Directory=$.extend({},Eop.Grid,{
    dlg_id:'showDlg',
    title:'添加目录',
	init:function(){
		var self = this;
		$("#chakanmulu").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=view&id="+id;
	    	self.toUrlOpen(url);
		});
		$("#xinzengmulu").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=save";
	    	self.toUrlOpen(url);
		});
		$("#xiugaimulu").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=edit&id="+id;
	    	self.toUrlOpen(url);
		});
		$("#chakanmulu1").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=view&id="+id;
	    	self.toUrlOpen(url);
		});
		$("#xiugaimulu1").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=edit&id="+id;
	    	self.toUrlOpen(url);
		});
		$("#chakanfangan").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=view&id="+id;
	    	self.toUrlOpen(url);
		});
		$("#xinzengfangan").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=save";
	    	self.toUrlOpen(url);
		});
		$("#xiugaifangan").click(function() {
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=edit&id="+id;
	    	self.toUrlOpen(url);
		});
		Eop.Dialog.init({id:'showDlg',modal:false,title:this.title,width:'450px'});
	},	
	toUrlOpen:function(url){
	   Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
	   Eop.Dialog.open(this.dlg_id);
	},
	onAjaxCallBack:function(){//ajax回调函数	    
		$('input.dateinput').datepicker();
	},
	page_close:function(){
	     Eop.Dialog.close(this.dlg_id);
	},
});
$(function(){
	Directory.init();
});

