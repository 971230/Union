<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}

String order_id = request.getParameter("order_id");
String workflow_id = request.getParameter("workflow_id");
String instance_id = request.getParameter("instance_id");
String node_index = request.getParameter("node_index");
String type_id = request.getParameter("type_id");
String cat_id = request.getParameter("cat_id");
String goods_id = request.getParameter("goods_id");
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重新选择商品</title>

<style>
.clickClass{
   background:#f7f7f7
}

.table_td_width{
	width: 12.5%;
}

.table_select{
	width: 162px;
	height: 24px;
}

.red_mark {
    color: red;
}

.thstyle {
	width: 150px;
	text-align: right;
	-web-kit-appearance:none;
 		-moz-appearance: none;
 		font-size:1.0em;
	height:1.5em;
	outline:0;
}

.accept{
	width: 400px;
	-web-kit-appearance:none;
 		-moz-appearance: none;
 		font-size:1.2em;
	height:1.5em;
	border-radius:4px;
	border:1px solid #c8cccf;
	color:#6a6f77;
	outline:0;
}

a{
	cursor:pointer;
	text-decoration:none;
}

textarea{
	border:1px solid #c8cccf;
	border-radius:4px;
}

.sl{
	width: 200px;
	appearance:none;
	-web-kit-appearance:none;
 		-moz-appearance: none;
	border:1px solid #c8cccf;
	border-radius:4px;
	padding-right: 14px;
}
</style>

</head>

<body style="background: white;">
	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table style="margin-left: 23px;">
			<tr>
				<th class="thstyle"><span class="red_mark">流转信息：</span></th>
				<td>
					<span id="span_flow_info" class="red_mark"></span>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle"><span class="red_mark">客户联系信息：</span></th>
				<td>
					<span id="span_contact_info" class="red_mark"></span>
				</td>
			</tr>
		</table>
	</div>

	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table>
			<tr>
				<th class="table_td_width condition_type_id">商品大类：</th>
				<td class="table_td_width condition_type_id">
					<select id="re_select_goods_type_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_cat_id">商品小类：</th>
				<td class="table_td_width condition_cat_id">
					<select id="re_select_goods_cat_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_goods_id">商品：</th>
				<td class="table_td_width condition_goods_id">
					<select id="re_select_goods_id" class="table_select">
	            	</select>
				</td>	
				
				<th class="table_td_width"></th>
				<td class="table_td_width ">
					
				</td>	
			</tr>
		</table>
	</div>
	
	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table style="margin-left: 21px;">
			<tr>
				<th class="thstyle">备注：</th>
				<td>
					<textarea rows="5" cols="88" id="web_condition_remark" ></textarea>
				</td>
			</tr>
		</table>
	</div>

	<div class="pop_btn" align="center">
		<a onclick="rollback();" class="blueBtns"><span>回退到上一环节</span></a>
		<!-- <a onclick="updateGoods();" class="blueBtns" ><span>修改商品</span></a> -->
		<a onclick="runNodeManual();" class="blueBtns"><span>提交</span></a>
	</div>
</body>

<script type="text/javascript">
var order_id = "<%=order_id%>";
var workflow_id = "<%=workflow_id%>";
var instance_id = "<%=instance_id%>";
var node_index = "<%=node_index%>";
var type_id = "<%=type_id%>";
var cat_id = "<%=cat_id%>";
var goods_id = "<%=goods_id%>";
var select_index = 1;

var reSelectGoodsChanger;
var remark = "";

$(function(){
	type_id = window.parent.WorkFlowTool.getInputValue(type_id);
	cat_id = window.parent.WorkFlowTool.getInputValue(cat_id);
	goods_id = window.parent.WorkFlowTool.getInputValue(goods_id);
	initSelect();
	
	for(var i=0;i<window.parent.canvas.connects.length;i++){
		var connect = window.parent.canvas.connects[i];
		
		if(node_index == connect.target_node_index){
			var html = "";
			var source_node_index = connect.source_node_index;
			
			var source_node = window.parent.canvas.nodes[source_node_index];
			
			if("1" == source_node.is_done){
				$("#span_contact_info").html(source_node.remark);
				break;
			}
		}
	}
	
	var node = window.parent.canvas.nodes[node_index];
	$("#span_flow_info").html(node.remark);
});

function initSelect(){
	reSelectGoodsChanger = new GoodsChanger("re_select_goods_type_id",
			"re_select_goods_cat_id","re_select_goods_id",
			type_id.split(","),cat_id.split(","),goods_id.split(","));
}

function updateGoods(){
	var goods_id = window.parent.WorkFlowTool.getInputValue($("#re_select_goods_id").val());
	var goods_name = $("#re_select_goods_id option:selected").html();
	goods_name = window.parent.WorkFlowTool.getInputValue(goods_name);
	
	if("" == goods_id){
		alert("请选择商品");
		return false;
	}
	
	var param = {
		"intent.order_id":order_id,
		"intent.goods_id":goods_id,
		"intent.goods_name":goods_name
	};
	
	$.ajax({
     	url:ctx+"/shop/admin/orderIntentAction!updateOrderIntentGoods.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				remark = "修改商品为【"+goods_id+"】"+goods_name;
     				alert("修改商品信息成功");
     			}else{
     				var msg = reply["msg"];
     				alert("修改商品信息失败："+msg);
     			}
     		}else{
     			alert("修改商品信息失败");
     		}
     	},
     	error:function(msg){
     		alert("修改商品信息失败："+msg);
     	}
	});
}

function runNodeManual(){
	if(confirm("请确认是否选择此商品？")){
		if(!checkGoods())
			return false;
		
		var goods_id = window.parent.WorkFlowTool.getInputValue($("#re_select_goods_id").val());
		var goods_name = $("#re_select_goods_id option:selected").html();
		goods_name = window.parent.WorkFlowTool.getInputValue(goods_name);
		
		if("" == goods_id){
			alert("请选择商品");
			return false;
		}
		
		var param = {
			"intent.order_id":order_id,
			"intent.goods_id":goods_id,
			"intent.goods_name":goods_name
		};
		
		$.ajax({
	     	url:ctx+"/shop/admin/orderIntentAction!updateOrderIntentGoods.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				remark = "修改商品为【"+goods_id+"】"+goods_name;
	     				alert("修改商品信息成功");
	     			}else{
	     				var msg = reply["msg"];
	     				alert("修改商品信息失败："+msg);
	     			}
	     		}else{
	     			alert("修改商品信息失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("修改商品信息失败："+msg);
	     		return false;
	     	}
		});
		
		var condition = "";
		
		var v = $("#web_condition_remark").val();
		
		remark = v;
		
		if(window.parent.canvas){
			window.parent.canvas.runNodeManual(instance_id,condition,remark);
		}
	}
}

/**
 * 商品信息校验
 */
function checkGoods(){
	var isPass = false;
	
	var param = {
		"order_id":order_id
	};
	
	var curr_goods_id = "";
	
	//查询意向单现有商品
	$.ajax({
     	url:ctx+"/shop/admin/orderIntentAction!getOrderIntentByOrderId.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				intent = reply["intent"];
     				
     				curr_goods_id = intent.goods_id;
     			}else{
     				var msg = reply["msg"];
     				alert("校验商品信息失败："+msg);
     			}
     		}else{
     			alert("校验商品信息失败");
     		}
     	},
     	error:function(msg){
     		alert("校验商品信息失败："+msg);
     	}
	});
	
	if(typeof(curr_goods_id)=="undefined" || null==curr_goods_id || 
			""==curr_goods_id.trim() || "null"==curr_goods_id.trim()){
		alert("意向单中的商品信息为空");
		return false;
	}
	
	if(typeof(goods_id)!="undefined" && goods_id!=null && 
			""!=goods_id.trim() && "null"!=goods_id.trim()){
		//配置了商品编号的，根据商品编号校验
		var goods_arr = goods_id.split(",");
		
		for(var i=0;i<goods_arr.length;i++){
			if(curr_goods_id == goods_arr[i]){
				isPass = true;
				break;
			}
		}
	}else if(typeof(cat_id)!="undefined" && cat_id!=null && 
			""!=cat_id.trim() && "null"!=cat_id.trim()){
		//配置了商品小类，根据商品小类校验
		var param = {
			"goods_id":curr_goods_id
		};
		
		var curr_cat_id = "";
		
		//查询商品信息
		$.ajax({
	     	url:ctx+"/shop/admin/type!getGoodsByGoodsid.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined" 
	     				&& typeof(reply["goods"]) != "undefined"
	     				&& null!=reply["goods"]){
     				var goods = reply["goods"];
     				
     				curr_cat_id = goods[0].cat_id;
	     		}else{
	     			alert("校验商品信息失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("校验商品信息失败："+msg);
	     	}
		});
		
		if(typeof(curr_cat_id)=="undefined" || null==curr_cat_id || 
				""==curr_cat_id.trim() || "null"==curr_cat_id.trim()){
			alert("查询的商品小类信息为空");
			return false;
		}
		
		var cats_arr = cat_id.split(",");
		
		for(var i=0;i<cats_arr.length;i++){
			if(curr_cat_id == cats_arr[i]){
				isPass = true;
				break;
			}
		}
	}else if(typeof(type_id)!="undefined" && type_id!=null && 
			""!=type_id.trim() && "null"!=type_id.trim()){
		//配置了商品大类，根据商品大类校验
		var param = {
			"goods_id":curr_goods_id
		};
		
		var curr_type_id = "";
		
		//查询商品信息
		$.ajax({
	     	url:ctx+"/shop/admin/type!getGoodsByGoodsid.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined" 
	     			&& typeof(reply["goods"]) != "undefined"
     				&& null!=reply["goods"]){
	     			
     				var goods = reply["goods"];
     				
     				curr_type_id = goods[0].type_id;
	     		}else{
	     			alert("校验商品信息失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("校验商品信息失败："+msg);
	     	}
		});
		
		if(typeof(curr_type_id)=="undefined" || null==curr_type_id || 
				""==curr_type_id.trim() || "null"==curr_type_id.trim()){
			alert("查询的商品大类信息为空");
			return false;
		}
		
		var types_arr = type_id.split(",");
		
		for(var i=0;i<types_arr.length;i++){
			if(curr_type_id == types_arr[i]){
				isPass = true;
				break;
			}
		}
	}else{
		isPass = true;
	}
	
	if(!isPass){
		alert("意向单中的商品不是可选商品，请重新选择商品！");
		return false;
	}else{
		return true;
	}
}

function GoodsChanger(type_select,cat_select,goods_select,limit_types,limit_cats,limit_goods){
	this.types = {};
	this.cats = {};
	this.goods = {};
	
	this.type_select = type_select;
	this.cat_select = cat_select;
	this.goods_select = goods_select;
	
	this.limit_types = limit_types;
	this.limit_cats = limit_cats;
	this.limit_goods = limit_goods;
	
	$.ajax({
     	url:ctx+"/shop/admin/type!getAllGoodsTypes.do?ajax=yes",
     	dataType:"json",
     	data:{},
     	async:false,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			types = reply["types"];
     			
     			if(typeof(limit_types)!="undefined" && limit_types!=null &&
     					limit_types.length>0 && typeof(limit_types[0])!="undefined" &&
     					limit_types[0]!=null && "null"!=limit_types[0]){
     				var temp_types = [];
         			
         			for(var i=0;i<types.length;i++){
         				var type = types[i];
         				
         				for(var j = 0; j < limit_types.length; j++) {
         					var type_id = limit_types[j];
         					
         					if(type_id == types[i].type_id){
         						temp_types.push(types[i]);
         						break;
         					}
         				}
         			}
         			
         			types = temp_types;
     			}
  
     			$("#"+type_select).empty();
     			
				$("#"+type_select).append("<option value=''>请选择</option>");
     			
     			for(var i=0;i<types.length;i++){
     				var option = "<option value="+types[i].type_id+">"+types[i].name+"</option>";
     				
     				$("#"+type_select).append(option);
     			}
     		}else{
     			alert("获取商品大类信息为空");
     		}
     	},
     	error:function(msg){
     		alert("初始化商品信息失败："+msg);
     	}
	});
	
	this.doGoodsTypeChange = function(typeid,catid){
		$("#"+type_select).val(typeid);
		$("#"+type_select).change();
		
		$("#"+cat_select).val(catid);
		$("#"+cat_select).change();
	};
	
	this.doGoodCatChange = function(catid,goodsid){
		$("#"+cat_select).val(catid);
		$("#"+cat_select).change();
		
		$("#"+goods_select).val(goodsid);
		$("#"+goods_select).change();
	};
	
	$("#"+type_select).change(function(){
		var typeid = $("#"+type_select).val();
		
		var param = {
				type_id:typeid
		};
		
		$("#"+cat_select).empty();
			
		$("#"+cat_select).append("<option value=''>请选择</option>");
		
		$.ajax({
	     	url:ctx+"/shop/admin/type!getGoodsCatsByTypeid.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			cats = reply["cats"];
	     			
	     			if(typeof(limit_cats)!="undefined" && limit_cats!=null &&
	     					limit_cats.length>0 && typeof(limit_cats[0])!="undefined" &&
	     					limit_cats[0]!=null && "null"!=limit_cats[0]){
	     				var temp_cats = [];
		     			
		     			for(var i=0;i<cats.length;i++){
		     				var cat = cats[i];
		     				
		     				for(var j = 0; j < limit_cats.length; j++) {
		     					var cat_id = limit_cats[j];
		     					
		     					if(cat_id == cats[i].cat_id){
		     						temp_cats.push(cats[i]);
		     						break;
		     					}
		     				}
		     			}
		     			
		     			cats = temp_cats;
	     			}

	     			for(var i=0;i<cats.length;i++){
	     				var cat_id = cats[i]["cat_id"];
	     				var name = cats[i]["name"];
	     				
     					var option = "<option value="+cat_id+">"+name+"</option>";
     				
     					$("#"+cat_select).append(option);
	     			}
	     		}else{
	     			alert("获取商品小类信息为空");
	     		}
	     	},
	     	error:function(msg){
	     		alert("获取商品小类信息失败："+msg);
	     	}
		});
	});
	
	$("#"+cat_select).change(function(){
		var catid = $("#"+cat_select).val();
		
		var param = {
				cat_id:catid
		};
		
		$("#"+goods_select).empty();
		$("#"+goods_select).append("<option value=''>请选择</option>");
		
		$.ajax({
	     	url:ctx+"/shop/admin/type!getGoodsByCatid.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			goods = reply["goods"];
	     			
	     			if(typeof(limit_goods)!="undefined" && limit_goods!=null &&
	     					limit_goods.length>0 && typeof(limit_goods[0])!="undefined" &&
	     					limit_goods[0]!=null && "null"!=limit_goods[0]){
	     				
	     				var temp_goods = [];
		     			
		     			for(var i=0;i<goods.length;i++){
		     				for(var j = 0; j < limit_goods.length; j++) {
		     					var goods_id = limit_goods[j];
		     					
		     					if(goods_id == goods[i].goods_id){
		     						temp_goods.push(goods[i]);
		     						break;
		     					}
		     				}
		     			}
		     			
		     			goods = temp_goods;
	     			}

	     			for(var i=0;i<goods.length;i++){
	     				var goods_id = goods[i]["goods_id"];
	     				var name = goods[i]["name"];
	     				
     					var option = "<option value="+goods_id+">"+name+"</option>";
     				
     					$("#"+goods_select).append(option);
	     			}
	     		}else{
	     			alert("获取商品信息为空");
	     		}
	     	},
	     	error:function(msg){
	     		alert("获取商品信息失败："+msg);
	     	}
		});
	});
}

function rollback(){
	var canvasInstance;
	
	if(window.parent.canvas)
		canvasInstance = window.parent.canvas;
	else{
		alert("流程画板JS对象为空");
		return false;
	}
	
	var remark = $("#web_condition_remark").val();
	
	if(typeof(remark)=="undefined" || remark==null || ""==remark.trim()){
		alert("请填写备注信息！");
		return false;
	}
	
	canvasInstance.rollback(instance_id,remark);
}
</script>

