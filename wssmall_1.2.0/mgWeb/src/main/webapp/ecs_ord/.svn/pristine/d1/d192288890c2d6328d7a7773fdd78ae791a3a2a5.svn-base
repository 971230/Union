<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义工单配置</title>

<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workCustomCfg.js"></script>

<style type="text/css">

.page-container {
    display: flex;
    width: 100vw;
    justify-content: left;
    flex: 1;
}

.left-container {
    width: 20%;
    border: 1px solid #CCC;
}

.right-container {
    width: 80%;
}

.canvas-main {
    position: relative;
    margin-top: 98px;
}

.canvas {
    height: 550px;
    max-height: 700px;
    border: 1px solid #CCC;
    background-color: white;
    display: flex;
}

.lib_title{
	width: 170px;
	height: 50px;
	border: 1px solid #CCC;
	text-align: center;
	background-color: #bfc1ba;
	font-size: 140%;
	line-height: 50px;
}

.diamond{   
	width: 50px;   
	height: 50px;   
	transform:rotate(45deg);   
	-ms-transform:rotate(45deg); /* Internet Explorer */   
	-moz-transform:rotate(45deg); /* Firefox */   
	-webkit-transform:rotate(45deg); /* Safari 和 Chrome */   
	-o-transform:rotate(45deg); /* Opera */   
	margin:50px auto;/*让菱形浏览器上居中*/  
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.circle {
	width: 100px;
	height: 40px;
	text-align: center;
	border-radius: 50%;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.rectangle {
	width:100px; 
	height:40px; 
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.rectangle_radius {
	width:100px; 
	height:40px; 
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
	border-radius: 20px;
}

.aLabel {
    background-color: white;
    padding: 0.4em;
    font: 12px sans-serif;
    color: #444;
    z-index: 21;
    border: 1px dotted gray;
    opacity: 0.8;
    cursor: pointer;
}

.color_green	{
	background-color: #70e77f;
}

.color_yellow	{
	background-color: yellow;
}

.color_cyan	{
	background-color: cyan;
}

.color_orange{
	background-color: orange;
}

</style>

</head>

<body>
	<div id="base_info" class="searchformDiv" style="background: white;border: 0px;" >
		<table>
			
			<tr>
				<th class="table_td_width">流程名称：</th>
				<td class="table_td_width">
					<input id="add_cfg_name" type="text" class="ipttxt" />
				</td>
				
				<th class="table_td_width"><span class="id_info" style="display: none;">流程编号</span></th>
				<td class="table_td_width" >
					<input id="add_cfg_id" type="text" class="ipttxt id_info" style="display: none;" />
				</td>
				
				<th class="table_td_width" ><span class="id_info" style="display: none;">版本编号</span></th>
				<td class="table_td_width" >
					<input id="add_version_id" type="text" class="ipttxt id_info" style="display: none;" />
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
			</tr>
			
			<tr height="50px;">
		   		<td style="text-align: center;font-size: 140%;" colspan="8">
		   			<h1>匹配信息</h1>
		   		</td>
			</tr>
			
			<tr>
				<th class="table_td_width">配置级别：</th>
				<td class="table_td_width">
					<select id="add_cfg_level" class="table_select">
						<option value="type">商品大类</option>
						<option value="cat">商品小类</option>
						<option value="id">商品</option>
	            	</select>
				</td>	
				
				<th class="table_td_width condition_type_id">商品大类：</th>
				<td class="table_td_width condition_type_id">
					<select id="add_goods_type_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_cat_id">商品小类：</th>
				<td class="table_td_width condition_cat_id">
					<select id="add_goods_cat_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_goods_id">商品：</th>
				<td class="table_td_width condition_goods_id">
					<select id="add_goods_id" class="table_select">
	            	</select>
				</td>	
			</tr>
			
			<tr>
				<th class="table_td_width">订单来源：</th>
				<td class="table_td_width">
					<select id="add_order_from" class="table_select">
	            	</select>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="searchformDiv" style="background: white;border: 0px;">
		<table>
			<tr height="50px;">
		   		<td style="text-align: center;font-size: 140%;width: 1164px;">
		   			<h1>流程图</h1>
		   		</td>
			</tr>
		</table>
	</div>
	
	<div>
		<span style="color: red;margin-left: 80px;font-size: 120%;">双击左侧组件库中的组件来新增组件，右键点击流程图中的组件来编辑组件属性</span>
	</div>
	
	<div class="page-container">
		<div style="width: 170px;">
			<div style="position: relative;margin-top: 10px;">
				
				<div class="lib_title">
					<span>基础组件</span>
				</div>
			
				<!-- 可选的流程节点模板 -->
				<div id="drag_from_div" style="height: 600px;max-height: 900px;border: 1px solid #CCC;background-color: white;display: flex;">
				
					<div class="circle color_orange node_templete" node_type="start" style="top: 70px;left: 40px;" title="流程开始环节，一个流程中只能有一个流程开始环节" >
						<br>
						<strong class="node_name">开始</strong>
					</div>
					
					<div class="diamond color_green node_templete" node_type="back_condition" style="top: 90px;left: 60px;" title="后台选择环节，执行后台代码，根据返回结果选择流程分支并执行流程" >
						<div style="transform:rotate(-45deg);   -ms-transform:rotate(-45deg); /* Internet Explorer */   -moz-transform:rotate(-45deg); /* Firefox */   	-webkit-transform:rotate(-45deg); /* Safari 和 Chrome */   -o-transform:rotate(-45deg); /* Opera */  text-align: center; ">
							<br>
							<strong class="node_name">后台选择环节</strong>
						</div>
					</div>
					
					<div class="diamond color_cyan node_templete" node_type="web_condition" style="top: 180px;left: 60px;" title="前台选择环节，前台页面人工手动选择，根据返回结果选择流程分支并执行流程">
						<div style="transform:rotate(-45deg);   -ms-transform:rotate(-45deg); /* Internet Explorer */   -moz-transform:rotate(-45deg); /* Firefox */   	-webkit-transform:rotate(-45deg); /* Safari 和 Chrome */   -o-transform:rotate(-45deg); /* Opera */  text-align: center; ">
							<br>
							<strong class="node_name">前台选择环节</strong>
						</div>
					</div>
					
					<div class="rectangle color_green node_templete" node_type="auto" style="top: 310px;left: 40px;" title="后台处理环节，根据配置的后台方法自动执行后台代码" >
						<br>
						<strong class="node_name">后台处理环节</strong>
					</div>
					
					<div class="rectangle color_cyan node_templete" node_type="web" style="top: 380px;left: 40px;" title="前台处理环节，前台页面人工处理环节" >
						<br>
						<strong class="node_name">前台处理环节</strong>
					</div>
					
					<div class="rectangle_radius color_green node_templete" node_type="back_wait" style="top: 440px;left: 40px;" title="后台等待环节，在当前环节等待，直到流程显式触发流转" >
						<br>
						<strong class="node_name">后台等待环节</strong>
					</div>
					
					<div class="rectangle_radius color_cyan node_templete" node_type="web_wait" style="top: 500px;left: 40px;" title="前台等待环节，在当前环节等待，直到流程显式触发流转，可以配置等待的前台页面" >
						<br>
						<strong class="node_name">前台等待环节</strong>
					</div>
					
					<div class="circle color_orange node_templete" node_type="end" style="top: 560px;left: 40px;" title="流程结束环节，一个流程中只能有一个流程结束环节">
						<br>
						<strong class="node_name">结束</strong>
					</div>
					
				</div>
			</div>
		</div>
		
		<div style="width: 1000px;">
			<div style=" position: relative;margin-top: 10px;">
				<!-- 流程画板 -->
				<div id="canvas_div" style="position: relative;height: 652px;max-height: 900px;border: 1px solid #CCC;background-color: white;display: flex;">
					
				</div>
			</div>
		</div>
	</div>
	
	<div class="searchformDiv" style="background: white;border: 0px;margin-top: 30px;">
		<table>
			<tr>
				<th></th>
				<td>
			    	<input class="comBtn" type="button" onclick="saveCfg();" value="保存" style="margin-left:450px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="nodeEditDiag"></div>
	<div id="labelEditDiag"></div>
	
</body>

</html>

<script type="text/javascript">
var cfgJsPlumb;
var id_count = 1;
var data = {};
var nodes = {};
var connections = {};

var addGoodsChanger;
var curr_node;
var curr_connect;

var basicType = {
        connector: "StateMachine",
        paintStyle: { stroke: "red", strokeWidth: 4 },
        hoverPaintStyle: { stroke: "blue" },
        overlays: [
            "Arrow"
        ]
    };
    
var connectorPaintStyle = {
        strokeWidth: 2,
        stroke: "#61B7CF",
        joinstyle: "round",
        outlineStroke: "white",
        outlineWidth: 2
    };
    
//this is the paint style for the connecting lines..
var connectorPaintStyle = {
        strokeWidth: 2,
        stroke: "#61B7CF",
        joinstyle: "round",
        outlineStroke: "white",
        outlineWidth: 2
    };
    
// .. and this is the hover style.
var connectorHoverStyle = {
        strokeWidth: 3,
        stroke: "#216477",
        outlineWidth: 5,
        outlineStroke: "white"
    };
    
var endpointHoverStyle = {
        fill: "#216477",
        stroke: "#216477"
    };
    
// the definition of source endpoints (the small blue ones)
var endpointOptions = {
        endpoint: "Dot",
        paintStyle: {
            stroke: "#7AB02C",
            fill: "transparent",
            radius: 7,
            strokeWidth: 1
        },
        isSource: true,
        isTarget: true,
        connector: [ "Flowchart"],
        connectorStyle: connectorPaintStyle,
        hoverPaintStyle: endpointHoverStyle,
        connectorHoverStyle: connectorHoverStyle,
        dragOptions: {},
        overlays: [
            [ "Label", {
                location: 1,
                label: "Drag",
                cssClass: "endpointSourceLabel",
                visible:false
            } ]
        ]
    };



$(function(){
	initSelectNode();
	
	initJsPlumb();
	
	initOrderFrom("add_order_from");
	
	initCfgLevel("add_cfg_level","base_info");
	
	addGoodsChanger = new GoodsChanger("add_goods_type_id","add_goods_cat_id","add_goods_id");
});	

function showNodeEditDiag(id){
	Eop.Dialog.init({id:"nodeEditDiag",modal:false,title:"环节属性",width:"600px"});
	
	var url = ctx+"/shop/admin/WorkCustom!getNodeEditUrl.do?ajax=yes";
	
	Eop.Dialog.open("nodeEditDiag");
	$("#nodeEditDiag").load(url);
	
	$("#dlg_nodeEditDiag").css("opacity","1");
	
	curr_node = id;
}

function showNodeLabelEdit(){
	Eop.Dialog.init({id:"labelEditDiag",modal:false,title:"连接线条件",width:"600px"});
	
	var url = ctx+"/shop/admin/WorkCustom!getLabelEditUrl.do?ajax=yes";
	
	Eop.Dialog.open("labelEditDiag");
	$("#labelEditDiag").load(url);
	
	$("#dlg_labelEditDiag").css("opacity","1");
}

function initSelectNode(){
	//双击在画板中新增节点
	$("#drag_from_div .node_templete").dblclick(function(e){
		var obj;
		
		if($(e.srcElement).hasClass("node_templete")){
			obj = e.srcElement;
		}else{
			
			for(var i=0;i<$(e.srcElement).parents().length;i++){
				if($($(e.srcElement).parents()[i]).hasClass("node_templete")){
					obj = $(e.srcElement).parents()[i];
					break;
				}
			}
		}
		
		if(typeof(obj) != "undefined"){
			//克隆DIV
			var v = $(obj).clone();
			//解除事件绑定
			$(v).unbind();
			//设置DIV的ID
			var id = "node_" + id_count;
			$(v).attr("id",id);
			$(v).attr("node_index",id_count);
			id_count++;
			
			//画板上的初始位置
			$(v).css("top","30px");
			$(v).css("left","30px");
			
			$(v).bind("contextmenu", function(){
				//右键显示流程环节编辑页面
				showNodeEditDiag(id);
			    return false;
			});
			
			//添加到画板上
			$("#canvas_div").append(v);
			
			//增加4个挂载点
			cfgJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Top",uuid: id+"_Top"});
			cfgJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Right",uuid: id+"_Right"});
			cfgJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Bottom",uuid: id+"_Bottom"});
			cfgJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Left",uuid: id+"_Left"});
			
			//DIV可拖动
			cfgJsPlumb.draggable(id);
		}
	});
}

function initJsPlumb(){
	//流程工具初始化
	jsPlumb.ready(function () {
		cfgJsPlumb = jsPlumb.getInstance({
			  //拖动参数
			  DragOptions: { cursor: 'pointer', zIndex: 2000 },
			  //连接样式
			  ConnectionOverlays: [
			      //箭头样式
                  [ "Arrow", {
                      location: 1,
                      visible:true,
                      width:11,
                      length:11,
                      id:"ARROW"
                      /*
                      ,
                      events:{
                          click:function() { alert("you clicked on the arrow overlay")}
                      }
                  	  */
                  } ],
                  //文字样式
                  [ "Label", {
                      location: 0.5,
                      id: "label",
                      cssClass: "aLabel"
                    	  /*
                      ,
                      events:{
                          tap:function() { alert("hey"); }
                      }
                    	  */
                  }]
              ],
              //画板容器
			Container:"canvas_div"
		});
		
		//注册基本的连接样式
		cfgJsPlumb.registerConnectionType("basic", basicType);
		
		cfgJsPlumb.bind("connection", function(info,originalEvent) {
			//连接事件
			
			var connection = info.connection;
			
			//连接线双击事件
			connection.bind("dblclick", function(conn) {
				
				curr_connect = connection;
				
				showNodeLabelEdit();
			});
		});
		
		cfgJsPlumb.bind("connectionDetached", function(info,originalEvent) {
			//断开连接事件
		});
	});
}

function saveCfg(){
	
}

</script>