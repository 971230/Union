var rule_sys_flag = 'RULE_ECSORD';		//复用佣金的规则入库方法,联通订单系统标识
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
						root = "<ul id='tree'><li id='treemenu' class='close'><a tree_right_click='tree_right_click' id='root_id"+directoryVo.id+"' dir_tree='dir_tree"+directoryVo.id+"' href='javascript:void(0);'><i class='treeic1'></i>"	+ directoryVo.name + "</a></li></ul>"
						tree_div.append(root);
						$("a[id='root_id" + directoryVo.id + "']").bind("contextmenu", function(event) {
							bindrightclick(directoryVo.id, "menu0", "", event);
							return  false; // 等同于continue
						});
						$("a[id='root_id"+directoryVo.id+"']").bind("click", function() {
							hideShowClsss(this);
							$.ajax({
								type : "post",
								async : false,
								url : "ruleManager!qryRootDirectory.do?ajax=yes&id=&pid=0",
								data : {},
								dataType : "json",
								success : function(data) {
									if (data != null && data.length > 0) {
										$.each(data, function(ii, directoryVo) {
											if (directoryVo.id != 0) {
												var is_load = $("ul[root_dir='root_dir"+directoryVo.id+"']").attr("is_load");
												if (is_load == "yes") {
													return true;
												}
												dir = "<ul is_load='yes' root_dir='root_dir"+directoryVo.id+"' id='dir_ul"+ii+"'><li id='dir_li"+directoryVo.id+"' class='close'><a id='dir_id"+ directoryVo.id +"' dir_tree='dir_tree"+directoryVo.id+"' href='javascript:void(0);'><i class='treeic1'></i>"+ directoryVo.name +"</a></li></ul>";
												$("#treemenu").append(dir);
												$("a[id='dir_id" + directoryVo.id + "']").bind("contextmenu", function(event) {
													bindrightclick(directoryVo.id, "menu1", "", event);
													return false;
												});
												
												$("#tree").children("li").children("ul").hide();
												
												$("a[id='dir_id"+directoryVo.id+"']").bind("click", function() {
													hideShowClsss(this);
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
																	var is_load = $("#schemenode_ul"+schemeNodeVo.plan_id).attr("is_load");
																	if (is_load == "yes") {
																		return true;
																	}
																	schemenode = "<ul is_load='yes' id='schemenode_ul"+schemeNodeVo.plan_id+"' >"+
																			 "<li title='"+schemeNodeVo.plan_name+"' id='schemenode_li"+schemeNodeVo.plan_id+"' class='close'>" +
																			 	"<a id='schemenode_id" 
																				+ schemeNodeVo.plan_id + 
																				"' href='javascript:void(0);'><i class='treeic2'></i>" 
																				+ schemeNodeVo.plan_name + "</a>"+
																			 "</li></ul>";
																	$("#dir_li"+directoryVo.id).append(schemenode);
																	$("a[id='schemenode_id" + schemeNodeVo.plan_id + "']").bind("contextmenu", function(event) {
																		bindrightclick(schemeNodeVo.plan_id, "menu2", "", event);
																		return false;
																	});
																	var $this = $("ul[root_dir='root_dir"+directoryVo.id+"']").children("li").children("ul").hide();
																	$("a[id='schemenode_id" + schemeNodeVo.plan_id + "']").bind("click", function() {
																		hideShowClsss(this);
//																		if ($("#schemenode_ul"+schemeNodeVo.plan_id+" li[title=rule0]").length == 0) {
																		$.ajax({
																		type : "post",
																		async : false,
																		url : "ruleManager!qryRuleNode.do?ajax=yes&id="+schemeNodeVo.plan_id+"&pid=0",
																		data : {},
																		dataType : "json",
																		success : function(data) {
																			if (data != null && data.length > 0) {
																				$.each(data, function(k, ruleNodeVo) {
																					
																					var is_load = $("#rulenode_ul"+ruleNodeVo.rule_id).attr("is_load");
																					if (is_load == "yes") {
																						return true;
																					}
																					var ic = getIcByRel(ruleNodeVo.rel_type);
																					rulenode = "<ul is_load='yes' id='rulenode_ul"+ruleNodeVo.rule_id+"'>"+
																					 "<li priority='"+ruleNodeVo.priority+"' parent='"+schemeNodeVo.plan_id+"' title='"+ruleNodeVo.rule_name+"("+ruleNodeVo.auto_exe+")' id='rulenode_li"+ruleNodeVo.rule_id
																					 +"' class='open'><a id='rulenode_id" 
																						+ ruleNodeVo.rule_id + 
																						"' href='javascript:void(0);' tree_flag='rule' rule_id='" + ruleNodeVo.rule_id + "' >"+ic+"" 
																						+ ruleNodeVo.rule_name+"("+ruleNodeVo.auto_exe+")</a>"+
																					 "</li></ul>";
																					$("#schemenode_li"+schemeNodeVo.plan_id).append(rulenode);
																					$("a[id='rulenode_id" + ruleNodeVo.rule_id + "']").bind("contextmenu", function(event) {
																						bindrightclick(ruleNodeVo.rule_id, "menu3", schemeNodeVo.plan_id, event);
																						return false;
																					});
																					$("#schemenode_ul"+schemeNodeVo.plan_id).children("li").children("ul").hide();
																					$("a[id='rulenode_id" + ruleNodeVo.rule_id + "']").bind("click", function() {
																						$("a[class='curra']").removeClass("curra");
																						$(this).addClass("curra");
//																						if ($("#rulenode_ul"+ruleNodeVo.rule_id+" li[title='ruletitle"+ruleNodeVo.rule_id+"']").length == 0) {
																							childrenNode(schemeNodeVo.plan_id, ruleNodeVo.rule_id, $("#rulenode_li"+ruleNodeVo.rule_id), $("#rulenode_ul"+ruleNodeVo.rule_id));										
//																						}
																						var $this = $("#rulenode_ul"+ruleNodeVo.rule_id).children("li").children("ul");
																						
																						if($this.length > 0) {
																							$this.is(":hidden")? $this.show() : $this.hide() ;
																						}
																						Directory.load_cond_page(ruleNodeVo.rule_id);
																					});
																				});
																			}
																		}
																		});
//																		}
																		var $this = $("#schemenode_ul"+schemeNodeVo.plan_id).children("li").children("ul");
																		if($this.length > 0) {
																			$this.is(":hidden")? $this.show() : $this.hide() ;
																		}
																		Directory.load_plan_cond(schemeNodeVo.plan_id);
																	});
																});
															}
														}
													});
													var $this = $("ul[root_dir='root_dir"+directoryVo.id+"']").children("li").children("ul");
													if($this.length > 0) {
														//判断当前菜单下是否有下级菜单
														$this.is(":hidden")? $this.show() : $this.hide() ;
													}
												});
											
											}
										});
									}
								}
							});
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

function childrenNode (plan_id, pid, nodeli, nodeul) {
	$.ajax({
		type : "post",
		async : false,
		url : "ruleManager!qryRuleChildNode.do?ajax=yes&id="+plan_id+"&pid="+pid,
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$.each(data, function(n, ruleNodeVo) {
					
					var is_load = $("#rulenode_ul"+ruleNodeVo.rule_id).attr("is_load");
					if (is_load == "yes") {
						return true;
					}
					var ic_child = getIcByRel(ruleNodeVo.rel_type);
					nodes = "<ul is_load='yes' id='rulenode_ul"+ruleNodeVo.rule_id+"'>"+
					 "<li parent='"+plan_id+"' title='"+ruleNodeVo.rule_name+"("+ruleNodeVo.auto_exe+")' id='rulenode_li"+ruleNodeVo.rule_id+"'><a id='rulenode_id" 
						+ ruleNodeVo.rule_id + 
						"' href='javascript:void(0);' tree_flag='rule' rule_id='" + ruleNodeVo.rule_id + "'>"+ic_child+"" 
						+ ruleNodeVo.rule_name+"("+ruleNodeVo.auto_exe+")</a>"+
					 "</li></ul>";
					nodeli.append(nodes);
					$("a[id='rulenode_id" + ruleNodeVo.rule_id + "']").bind("contextmenu", function(event) {
						bindrightclick(ruleNodeVo.rule_id, "menu3", plan_id, event);
						return false;
					});
					
					var $this = nodeul.children("li").children("ul").hide();
					
					$("a[id='rulenode_id" + ruleNodeVo.rule_id + "']").bind("click", function() {
						$("a[class='curra']").removeClass("curra");
						$(this).addClass("curra");
							childrenNode(plan_id, ruleNodeVo.rule_id,
									$("#rulenode_li"+ruleNodeVo.rule_id),
									$("#rulenode_ul"+ruleNodeVo.rule_id));
						var $this = $("#rulenode_ul"+ruleNodeVo.rule_id).children("li").children("ul");
						if($this.length > 0) {
							$this.is(":hidden")? $this.show() : $this.hide() ;
						}
						//打开规则条件配置界面
						Directory.load_cond_page(ruleNodeVo.rule_id);
					});
				});
			}
		}
		});
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
	// 循环收缩节点
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
	
	 function bindrightclick (id, menu, planid, event) {
		$("#planid").val(planid);
		$("#nodeid").val(id);
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
		if ($(oMenu).attr("id")=="menu0") {
			$("#menu1").css("display", "none");
			$("#menu2").css("display", "none");
			$("#menu3").css("display", "none");
		} else if($(oMenu).attr("id")=="menu1") {
			$("#menu0").css("display", "none");
			$("#menu2").css("display", "none");
			$("#menu3").css("display", "none");
		} else if($(oMenu).attr("id")=="menu2") {
			$("#menu1").css("display", "none");
			$("#menu0").css("display", "none");
			$("#menu3").css("display", "none");
		} else if($(oMenu).attr("id")=="menu3") {
			$("#menu1").css("display", "none");
			$("#menu2").css("display", "none");
			$("#menu0").css("display", "none");
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
		$("#chakanmulu").click(function() {
			self.title="查看目录";
			self.width='350px';
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=view&id="+id;
	    	self.toUrlOpen(url, self.title);
		});
		$("#xinzengmulu").click(function() {
			self.title="新增目录";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=save";
			self.toUrlOpen(url, self.title);
		});
		$("#xiugaimulu").click(function() {
			self.title="修改目录";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=edit&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#xinzengfangan").click(function() {
			self.title="新增方案";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=save&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#chakanmulu1").click(function() {
			self.title="查看目录";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=view&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#xiugaimulu1").click(function() {
			self.title="修改目录";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!addEditViewDirectory.do?ajax=yes&flag=edit&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#chakanfangan").click(function() {
			self.title="查看方案";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=view&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#xinzengguize").click(function() {
			self.title="新增规则";
			self.width='450px';
			$("#planid").val($("#nodeid").val());
			var url = "ruleManager!qryRule.do?ajax=yes&flag=save&id=";
			self.toUrlOpen(url, self.title);
		});
		$("#xiugaifangan").click(function() {
			self.title="修改方案";
			self.width='450px';
			var id = $("#nodeid").val();
			var url = "ruleManager!qryScheme.do?ajax=yes&flag=edit&id="+id;
			self.toUrlOpen(url, self.title);
		});
		$("#shanchufangan").click(function(){
			var bool = confirm("确认删除方案吗？");
			if (bool) {
				var id = $("#nodeid").val();
				$.ajax({
					type : "post",
					async : false,
					url : "ruleManager!delScheme.do?ajax=yes&id="+id,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.message);
						if (data.result == '0') {
							$("#schemenode_ul"+id).remove();
						}
					}
				});
			} else {
				return;
			}
		});
		$("#bianjiguize").click(function() {
			self.title="编辑规则";
			self.width='950px';
			var planid = $("#nodeid").val();
			var url = "ruleManager!qryChildrenRule.do?ajax=yes&flag=edit&id=0&plan_id="+planid;
			self.toUrlOpen(url, self.title);
		});
		$("#shanchuguize").click(function() {
			var bool = confirm("确认删除规则吗？");
			if (bool) {
				var id = $("#nodeid").val();
				var plan_id = $("#planid").val();
				
				$.ajax({
					type : "post",
					async : false,
					url : "ruleManager!delRule.do?ajax=yes&id="+id+"&plan_id="+plan_id,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.message);
						if (data.result == '0') {
							$("#rulenode_ul"+id).empty();
						}
					}
				});
			} else {
				return;
			}
		});
		$("#chakanguize").click(function() {
			self.title="查看规则";
			self.width='450px';
			var id = $("#nodeid").val();
			var planid = $("#planid").val();
			var url = "ruleManager!qryRule.do?ajax=yes&flag=view&id="+id+"&planid="+planid;
			self.toUrlOpen(url, self.title);
		});
		$("#xinzengguize0").click(function() {
			self.title="新增规则";
			self.width='450px';
			var id = $("#nodeid").val();
			var planid = $("#planid").val();
			var url = "ruleManager!qryRule.do?ajax=yes&flag=save&id="+id+"&planid="+planid;
			self.toUrlOpen(url, self.title);
		});
		$("#xiugaiguize").click(function() {
			self.title="修改规则"
			self.width='450px';
			var id = $("#nodeid").val();
			var planid = $("#planid").val();
			var url = "ruleManager!qryRule.do?ajax=yes&flag=edit&id="+id+"&planid="+planid+"&rulexiugai=yes";
			self.toUrlOpen(url, self.title);
		});
		$("#bianjiziguize").click(function() {
			self.width = '1050px';
			self.title = '编辑子规则';
			var id = $("#nodeid").val();
			var planid = $("#planid").val();
			var url = "ruleManager!qryChildrenRule.do?ajax=yes&flag=edit&id="+id+"&plan_id="+planid;
			self.toUrlOpen(url, self.title);
		});
    	Eop.Dialog.init({id:'showDlg',modal:false,title:self.title,width:self.width});
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
	},
	load_cond_page : function(rule_id){
		var url = app_path + "/shop/admin/rule!showConfigAdd.do?ajax=yes";
		//默认action 为 1（修改），rule_sys_flag 为系统标识（联通订单系统）
		$("div.treeRightCon").load(url, {'rule_id':rule_id, 'rule_sys_flag':rule_sys_flag, 'action':'1'});
	},
	load_plan_cond : function(cond_plan_id) {
		var url = app_path + "/shop/admin/ruleManager!showPlanCond.do?ajax=yes";
		$("#rule_page_right").load(url, {"cond_plan_id":cond_plan_id});
	}
});
$(function(){
	Directory.init();
});

