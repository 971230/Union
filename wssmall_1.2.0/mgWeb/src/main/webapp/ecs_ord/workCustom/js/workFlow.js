/**
 *Author:zhao chen
 * 
 * 自定义流程JS
 * 依赖的文件
 * 1、jsplumb.js    绘制流程开源框架JS文件
 * 2、workCustom.css		流程环节模板样式
 */
  
/********************* 流程绘制工具jsPlumb样式 开始  *************************/


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
    
var connectorPaintStyle = {
        strokeWidth: 2,
        stroke: "#61B7CF",
        joinstyle: "round",
        outlineStroke: "white",
        outlineWidth: 2
    };
    
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

/********************* 流程绘制工具jsPlumb样式 结束  *************************/


/**
 * 流程画板JS对象
 */
function WorkFlowCanvas(container_id){
	this.container_id = container_id;
	this.out_container_id = "workflow_container_div";
	this.canvas_id = "";
	this.baseic_lib_id = "";
	this.lib_container_id = "";
	this.canvas_container_id = "";
	this.order_id = "";
	
	this.workFlowJsPlumb = {};
	//config_new-新增流程,config_edit-修改流程,config_his-查看流程历史配置,config_dealer-处理人配置,show_workflow--展示流程
	this.model = "show_workflow";
	this.cfg = {};
	this.nodes = {};
	this.connects = {};
	this.user = {};
	this.org = {};
	this.order_type = "";
	this.lock_id = "";
	this.lock_name = "";
	this.lock_type = "";
	this.id_count = 1;
	this.cfg_version = "";
	this.curr_node = "";
	this.curr_connect = "";
	
	this.nodeAttrDiv = "nodeEditDiag";
	this.workFlowCfgDiv = "EditDiag";
	
	this.container_style = "display: block;float:left; width: 100%;";
	this.templete_container_style = "width: 170px;display: inline;float:left;margin-left: 30px;";
	this.libStyle = "height: 600px;max-height: 900px;border: 1px solid #CCC;background-color: white;display: flex;";
	this.canvasStyle = "position: relative;width: 2000px;height: 5000px;border: 1px solid #CCC;background-color: white;";
	
	this.loadFlowDataUrl = "/shop/admin/WorkCustom!loadFlowData.do?ajax=yes";
	this.nodeAttrUrl = "/shop/admin/WorkCustom!getNodeEditUrl.do?ajax=yes";
	this.addWorkFlowCfgUrl = "/shop/admin/WorkCustom!addWorkCustomCfg.do?ajax=yes";
	this.updateWorkFlowCfgUrl = "/shop/admin/WorkCustom!updateWorkCustomCfg.do?ajax=yes";
	this.getWorkFlowCfgUrl = "/shop/admin/WorkCustom!getWorkCustomCfgByVersion.do?ajax=yes";
	
	this.nestWebDiv = "nest_web_div";
	this.nestWebFrame = "nest_web_frame";
	this.nestWebTitle = "nest_web_title";
	this.hideWorkflowRadion = "radio_hide_workflow";
	
	this.initShowFlow = false;
	
	this.after_load_event;
	
	this.init = function(){
		if(typeof(this.container_id)=="undefined" || this.container_id==null)
			throw new Error("流程画板容器为空");
		
		$("#"+this.container_id).removeAttr("style");
		$("#"+this.container_id).attr("style",this.container_style);
		
		this.canvas_id = this.container_id + "_canvas";
		this.baseic_lib_id = this.container_id + "_basicLib";
		this.lib_container_id = this.container_id + "_lib_container";
		this.canvas_container_id = this.container_id + "_canvas_container";
		
		this.getFlowCfgInfoDiv();
		this.getTempleteContainer();
		this.getCanvasContainer();
		
		this.initDragEvent();
		
		this.initReadOnly();
		
		this.getJsPlumb();
	};
	
	this.getTempleteContainer = function(){
		
		var templeteContainer = "<div id='"+this.lib_container_id+"' style='"+this.templete_container_style+"'></div>";
		
		var subContainer = "<div id='"+this.container_id+"_sub_lib_container' style='position: relative;margin-top: 10px;'></div>";
		var basicLibTitle = "<div class='lib_title'><span>基础组件</span></div>";
		var baseicLib = "<div id='"+this.baseic_lib_id+"' style='"+this.libStyle+"'></div>";
		
		var startNode = "<div id='templete_start' class='circle color_orange node_templete' node_type='start' style='top: 70px;left: 40px;' title='流程开始环节，一个流程中只能有一个流程开始环节'"+ 
				"draggable='true'  > <br> <strong class='node_name'>开始</strong></div>";
		
		var backConditionNode = "<div id='templete_back_condition' class='diamond color_green node_templete' node_type='back_condition' style='top: 90px;left: 60px;' title='后台选择环节，执行后台代码，根据返回结果选择流程分支并执行流程'" + 
				"draggable='true'  ><div style='transform:rotate(-45deg);   -ms-transform:rotate(-45deg); /* Internet Explorer */ "+
				"  -moz-transform:rotate(-45deg); /* Firefox */   	-webkit-transform:rotate(-45deg); /* Safari 和 Chrome */   -o-transform:rotate(-45deg); /* Opera */  text-align: center; '>" +
				" <br> <strong class='node_name'>后台选择环节</strong></div></div>";
		
		var webConditionNode = "<div id='templete_web_condition' class='diamond color_cyan node_templete' node_type='web_condition' style='top: 180px;left: 60px;'"+
				" title='前台选择环节，前台页面人工手动选择，根据返回结果选择流程分支并执行流程' draggable='true'  >"+
				"<div style='transform:rotate(-45deg);   -ms-transform:rotate(-45deg); /* Internet Explorer */   -moz-transform:rotate(-45deg); /* Firefox */   "+
				"	-webkit-transform:rotate(-45deg); /* Safari 和 Chrome */   -o-transform:rotate(-45deg); /* Opera */  text-align: center; '>" +
				"	<br> <strong class='node_name'>前台选择环节</strong></div></div>";
		
		var autoNode = "<div id='templete_auto' class='rectangle color_green node_templete' node_type='auto' style='top: 310px;left: 40px;' title='后台处理环节，根据配置的后台方法自动执行后台代码' "+
				"draggable='true'  ><br><strong class='node_name'>后台处理环节</strong></div>";
		
		var webNode = "<div id='templete_web' class='rectangle color_cyan node_templete' node_type='web' style='top: 380px;left: 40px;' title='前台处理环节，前台页面人工处理环节' "+
				"draggable='true'  ><br><strong class='node_name'>前台处理环节</strong></div>";
		
		var backWaitNode = "<div id='templete_back_wait' class='rectangle color_yellow node_templete' node_type='back_wait' style='top: 440px;left: 40px;' title='后台等待环节，在当前环节等待，直到流程显式触发流转'"+ 
				"draggable='true'  ><br><strong class='node_name'>后台等待环节</strong></div>";
		
		var endNode = "<div id='templete_end' class='circle color_orange node_templete' node_type='end' style='top: 500px;left: 40px;' title='流程结束环节，一个流程中只能有一个流程结束环节'"+
				"draggable='true'  ><br><strong class='node_name'>结束</strong></div>";
		
		$("#"+this.container_id).append(templeteContainer);
		
		$("#"+this.lib_container_id).append(subContainer);
		
		$("#"+this.container_id+"_sub_lib_container").append(basicLibTitle);
		$("#"+this.container_id+"_sub_lib_container").append(baseicLib);
		
		$("#"+this.baseic_lib_id).append(startNode);
		$("#"+this.baseic_lib_id).append(backConditionNode);
		$("#"+this.baseic_lib_id).append(webConditionNode);
		$("#"+this.baseic_lib_id).append(autoNode);
		$("#"+this.baseic_lib_id).append(webNode);
		$("#"+this.baseic_lib_id).append(backWaitNode);
		$("#"+this.baseic_lib_id).append(endNode);
	};
	
	this.getCanvasContainer = function(){
		var canvasContainer = "<div id='"+this.canvas_container_id+"' style='width: 850px;display: inline;float:left;'>"+
			"<div style=' position: relative;overflow:scroll; width:100%; height:664px;margin-top: 10px;'><div id='"+this.canvas_id+"' style='"+this.canvasStyle+"'"+
			" ></div></div></div>";
		
		
		$("#"+this.container_id).append(canvasContainer);
	};
	
	this.getFlowCfgInfoDiv = function(){
		var cfgInfo = "<div id='"+this.container_id+"_cfg_info' class='searchformDiv' style='background: white;border-bottom:0px;border-right:0px;border-top:0px;' ></div>";
		
		var cfgTable = "<table id='"+this.container_id+"_cfg_table'></table>";
		
		var tr_1 = "<tr><th class='table_td_width'>流程配置名称：</th><td class='table_td_width'>"+
			"<input id='cfgInfo_cfg_name' type='text' class='ipttxt' disabled='disabled' /></td>"+
			"<th class='table_td_width'>流程配置编号：</span></th><td class='table_td_width' >"+
			"<input id='cfgInfo_cfg_id' type='text' class='ipttxt' disabled='disabled' /></td>"+
			"<th class='table_td_width' >配置版本号：</span></th><td class='table_td_width' >"+
			"<input id='cfgInfo_version_id' type='text' class='ipttxt' disabled='disabled' /></td>"+
			"<th class='table_td_width'>流程类型：</th><td class='table_td_width'>"+
			"<select id='cfgInfo_cfg_type' class='table_select' disabled='disabled' ><option value='busi'>业务流程</option>"+
			"<option value='cancel'>退单流程</option></select></td></tr>";
		
		var tr_2 = "<tr><th class='table_td_width'>配置级别：</th><td class='table_td_width'>"+
			"<select id='cfgInfo_cfg_level' class='table_select' disabled='disabled'><option value='type'>"+
			"商品大类</option><option value='cat'>商品小类</option><option value='id'>商品</option>"+
			" </select></td><th class='table_td_width condition_type_id'>商品大类：</th>"+
			"<td class='table_td_width condition_type_id'><select id='cfgInfo_goods_type_id' class='table_select' disabled='disabled'>"+
			"</select></td><th class='table_td_width condition_cat_id'>商品小类：</th><td class='table_td_width condition_cat_id'>"+
			"<select id='cfgInfo_goods_cat_id' class='table_select' disabled='disabled'> </select></td>"+
			"<th class='table_td_width condition_goods_id'>商品：</th><td class='table_td_width condition_goods_id'>"+
			"<select id='cfgInfo_goods_id' class='table_select' disabled='disabled'> </select></td></tr>";
		
		var tr_3 = "<tr><th class='table_td_width'>订单来源：</th><td class='table_td_width'>"
			+"<select id='cfgInfo_order_from' class='table_select' disabled='disabled'></select></td>"
			+"<th class='table_td_width cfgInfo_order_deal_method'>受理方式：</th><td class='table_td_width'>"
			+"<select id='cfgInfo_order_deal_method' class='table_select cfgInfo_order_deal_method' disabled='disabled'>"
			+"<option value=''>不限制</option><option value='1'>线上</option><option value='2'>线下</option></select></td>"
			+"</tr>";
		
		$("#"+this.container_id).append(cfgInfo);
		$("#"+this.container_id+"_cfg_info").append(cfgTable);
		
		$("#"+this.container_id+"_cfg_table").append(tr_1);
		$("#"+this.container_id+"_cfg_table").append(tr_2);
		$("#"+this.container_id+"_cfg_table").append(tr_3);
	};
	
	this.initDragEvent = function(){
		if("config_new" == this.model || "config_edit" == this.model){
			for(var i=0;i<$("#"+this.lib_container_id).find(".node_templete").length;i++){
				
				var id = $($("#"+this.lib_container_id).find(".node_templete")[i]).attr("id");
				
				document.getElementById(id).addEventListener("dragstart",function(e){
					e.dataTransfer.setData("templete_id",e.target.id);
				});
			}
			
			document.getElementById(this.canvas_id).addEventListener("dragover",function(e){
				e.preventDefault();
			});
			
			var canvasInstance = this;
			
			document.getElementById(this.canvas_id).addEventListener("drop",function(e){
				e.preventDefault();
				var templete_id = e.dataTransfer.getData("templete_id");
				
				var templete = $("#"+templete_id);
				
				canvasInstance.addNode2Canvas(templete,null,e);
			});
		}
	};
	
	this.getJsPlumb = function(){
		var canvasInstance = this;
		
		jsPlumb.ready(function(){
			canvasInstance.workFlowJsPlumb = jsPlumb.getInstance({
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
				Container:canvasInstance.canvas_id
			});
			
			//注册基本的连接样式
			canvasInstance.workFlowJsPlumb.registerConnectionType("basic", basicType);
			
			canvasInstance.workFlowJsPlumb.bind("connection", function(info,originalEvent) {
				//连接事件
				var connection = info.connection;
				
				//连接线双击事件
				connection.bind("dblclick", function(conn,event) {
					canvasInstance.curr_connect = connection;
					
					canvasInstance.showConnectEdit();
				});
			});
			
			if(typeof(canvasInstance.order_id)!="undefined" && ""!=canvasInstance.order_id){
				canvasInstance.loadWorkFlow(canvasInstance.order_id);
			}
		});
	};
	
	this.showConnectEdit = function(){
		if("config_dealer" == this.model || "show_workflow" == this.model
				|| "config_his" == this.model){
			return true;
		}
		
		Eop.Dialog.init({id:"labelEditDiag",modal:false,title:"连接线条件",width:"400px"});
		
		var url = ctx+"/shop/admin/WorkCustom!getLabelEditUrl.do?ajax=yes";
		
		Eop.Dialog.open("labelEditDiag");
		$("#labelEditDiag").load(url);
		
		this.moveDlg2Mid("dlg_labelEditDiag");
	};
	
	this.setCurrConnectLabel = function(label){		
		this.curr_connect.getOverlay("label").setLabel(label);
	};

	this.deleteCurrConnect = function(){
		this.workFlowJsPlumb.deleteConnection(this.curr_connect);
	};
	
	this.loadWorkFlow = function(order_id){
		var orderId = "";
		
		if(typeof(order_id)!="undefined" && ""!=order_id.trim()){
			orderId = order_id;
			this.order_id = order_id;
		}else{
			orderId = this.order_id;
		}
		
		this.clearCanvas();

		var retCfg;
		var retNodes;
		var retCons;
		var currUser;
		var currOrg;
		var retOrderType;
		var ret_lock_id;
		var ret_lock_name;
		var ret_lock_type;
		
		var param = {
				"ins.order_id":orderId
		};
		
		$.ajax({
	     	url:ctx+this.loadFlowDataUrl,
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				retCfg = reply["cfg"];
	     				retNodes = reply["inses"];
	     				retCons = reply["connects"];
	     				currUser = reply["user"];
	     				currOrg = reply["org"];
	     				retOrderType = reply["order_type"];
	     				ret_lock_id = reply["lock_id"];
	     				ret_lock_name = reply["lock_name"];
	     				ret_lock_type = reply["lock_type"];
	     			}else{
	     				var msg = reply["msg"];
	     				alert("查询流程信息失败"+msg);
	     			}
	     		}else{
	     			alert("查询流程信息失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("查询流程信息失败："+msg);
	     	}
		});
		
		if(typeof(retCfg)=="undefined" || retCfg==null){
			alert("查询到的流程配置信息为空");
			
			return false;
		}
		
		if(typeof(retNodes)=="undefined" || retNodes==null){
			alert("查询到的流程节点为空");
			
			return false;
		}
		
		if(typeof(retCons)=="undefined" || retCons==null){
			alert("查询到的流程连接为空");
			
			return false;
		}
		
		this.cfg = retCfg;
		this.connects = retCons;
		this.user = currUser;
		this.org = currOrg;
		this.order_type = retOrderType;
		this.lock_id = ret_lock_id;
		this.lock_name = ret_lock_name;
		this.lock_type = ret_lock_type;
		
		this.showFlowCfgInfo();
		
		var canvasInstance = this;
		
		this.workFlowJsPlumb.batch(function(){
			canvasInstance.loadNodes(retNodes);
			
			canvasInstance.loadConnections(retCons);
			
			canvasInstance.showCurrNode();
		});
		
		//不是已完成的流程，初始化时不展示流程的，隐藏
		var is_his = false;
		
		for(var node_index in retNodes){
			if("2" == retNodes[node_index].state){
				is_his = true;
				break;
			}
		}
		
		if(!canvasInstance.initShowFlow && !is_his)
			$("#"+canvasInstance.out_container_id).hide();
		
		//状态为历史的流程配置，重新绑定连接事件
		this.workFlowJsPlumb.unbind("connection");
		
		this.workFlowJsPlumb.bind("connection", function(info,originalEvent) {
			//连接事件
			var connection = info.connection;
			//删除连接
			this.workFlowJsPlumb.deleteConnection(connection);
		});
		
		if(this.after_load_event){
			this.after_load_event();
		}
	};
	
	this.showFlowCfgInfo = function(){
		if("show_workflow" == this.model){
			$("#"+this.container_id+"_cfg_info").show();
			
			WorkFlowTool.canvas = this;
			
			//初始化订单来源
			WorkFlowTool.initOrderFrom("cfgInfo_order_from");
			
			//初始化配置级别
			WorkFlowTool.initCfgLevel("cfgInfo_cfg_level","flow_cfg_info");
			
			//初始化商品大类、商品小类、商品联动
			WorkFlowTool.addGoodsChanger = new GoodsChanger("cfgInfo_goods_type_id","cfgInfo_goods_cat_id","cfgInfo_goods_id");
			
			$("#cfgInfo_cfg_name").val(this.cfg.cfg_name);
			$("#cfgInfo_cfg_id").val(this.cfg.cfg_id);
			$("#cfgInfo_version_id").val(this.cfg.version_id);
			
			$("#cfgInfo_cfg_level").val(this.cfg.cfg_level);
			$("#cfgInfo_cfg_level").change();
			$("#cfgInfo_cfg_type").val(this.cfg.cfg_type);
			
			var goods_type_id = WorkFlowTool.getInputValue(this.cfg.goods_type_id);
			var goods_cat_id = WorkFlowTool.getInputValue(this.cfg.goods_cat_id);
			var goods_id = WorkFlowTool.getInputValue(this.cfg.goods_id);
			
			WorkFlowTool.addGoodsChanger.doGoodsTypeChange(goods_type_id,goods_cat_id);
			WorkFlowTool.addGoodsChanger.doGoodCatChange(goods_cat_id,goods_id);
			
			var order_from = WorkFlowTool.getInputValue(this.cfg.order_from);
			$("#cfgInfo_order_from").val(order_from);
			
			if("intent" == this.cfg.cfg_type){
				$(".cfgInfo_order_deal_method").hide();
			}else{
				var order_deal_method = WorkFlowTool.getInputValue(this.cfg.order_deal_method);
				
				$("#cfgInfo_order_deal_method").val(order_deal_method);
			}
		}else{
			$("#"+this.container_id+"_cfg_info").hide();
		}
	};
	
	this.loadNodes = function(retNodes){
		var max_index = 0;
		
		for(var i=0;i<retNodes.length;i++){
			var node_index = retNodes[i].node_index;
			node_index = parseInt(node_index);
			
			if(node_index > max_index)
				max_index = node_index;
			
			this.nodes[node_index] = retNodes[i];
			
			var node_type = this.nodes[node_index].node_type;
			
			var libId = this.container_id+"_basicLib";
			
			var templetes = $("#"+libId).find("[node_type='"+node_type+"']");
			
			if(typeof(templetes)!="undefined" && templetes!=null && templetes.length>0){
				var templete = templetes[0];
				
				this.addNode2Canvas(templete,this.nodes[node_index]);
				
				this.showNestWebFrame(this.nodes[node_index]);
			}
		}
		
		this.id_count = max_index + 1;
	};
	
	this.showNestWebFrame = function(node){
		//展示当前环节页面
		if("show_workflow" == this.model && typeof(node)!="undefined" && node!=null && 
				"1"==node.is_curr_step){
			
			//设置抬头
			if("web"==node.node_type || "web_condition"==node.node_type)
				$("#"+this.nestWebTitle).html(node.node_name);
			else
				$("#"+this.nestWebTitle).html(node.node_name);
			
			if(typeof(node.deal_url)=="undefined" || ""==node.deal_url.trim()){
				//未配置处理页面
				var html = "<div class='pop_btn' align='center' style='margin-bottom: 20px;margin-top: 40px;'>";
				html += "<span style='font-size: 18px;color: red'>当前环节未配置处理页面</span>";
				html += "</div>";
				
				$("#"+this.nestWebDiv).append(html);
			}else if("1"==node.is_done){
				//已完成，提示已完成
				var html = "<div class='pop_btn' align='center' style='margin-bottom: 20px;margin-top: 40px;'>";
				html += "<span style='font-size: 18px;color: red'>当前环节已处理完成</span>";
				html += "</div>";
			}else if(this.lock_id==this.user.userid 
					|| "1"==this.user.userid
					){
				//是当前处理人或管理员
				var url = ctx + node.deal_url;
				
				url += "&order_id="+node.order_id;
				url += "&workflow_id="+node.workflow_id;
				url += "&instance_id="+node.instance_id;
				url += "&node_index="+node.node_index;
				
				$("#"+this.nestWebFrame).attr("src",url);
			}else if("person"==this.lock_type){
				//不是当前处理人或管理员
				var html = "<div class='pop_btn' align='center' style='margin-bottom: 20px;margin-top: 40px;'>";
				html += "<span style='font-size: 18px;color: red'>您不是当前环节处理人员，不能处理订单</span>";
				html += "</div>";
				
				$("#"+this.nestWebDiv).append(html);
			}else{
				//订单未领取
				var html = "<div class='pop_btn' align='center' style='margin-bottom: 20px;margin-top: 40px;'>";
				html += "<span style='font-size: 18px;color: red'>订单未领取，请先领取</span>";
				html += "</div>";
				
				$("#"+this.nestWebDiv).append(html);
			}
			
			$("#"+this.nestWebDiv).show();
		}
	};
	
	this.addNode2Canvas = function(templete,node,ev){
		//克隆DIV
		var v = $(templete).clone();
		//解除事件绑定
		$(v).unbind();
		//设置DIV的ID
		var id;
		var top = "30px";
		var left = "30px";
		
		if(typeof(node)!="undefined" && node!=null){
			id = "node_" + node.node_index;
			top = node.top_px+"px";
			left = node.left_px+"px";
			
			$(v).attr("id",id);
			$(v).attr("node_index",node.node_index);
			
			var node_name = node.node_name;
			
			$(v).find(".node_name").html(node_name);
			
			if(typeof(node.instance_id)!="undefined" && node.instance_id!=null){
				$(v).attr("instance_id",node.instance_id);
			}
		}else{
			id = "node_" + this.id_count;
			$(v).attr("id",id);
			$(v).attr("node_index",this.id_count);
			this.id_count++;
			
			var w = 45;
			var h = 15;
			
			if($(v).attr("node_type") == "back_condition" ||
					$(v).attr("node_type") == "web_condition"){
				w = 10;
				h = 40;
			}
			
			if(typeof(ev)!="undefined" && ev!=null){
				top = (ev.offsetY-h) + "px";
				left = (ev.offsetX-w) + "px";
			}
		}
		
		//画板上的初始位置
		$(v).css("top",top);
		$(v).css("left",left);
		
		var canvasInstance = this;
		
		$(v).bind("contextmenu", function(){
			//右键显示流程环节编辑页面
			canvasInstance.showNode(id);
		    return false;
		});
		
		//添加到画板上
		$("#"+this.canvas_id).append(v);
		
		//增加4个挂载点
		this.workFlowJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Top",uuid: id+"_Top"});
		this.workFlowJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Right",uuid: id+"_Right"});
		this.workFlowJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Bottom",uuid: id+"_Bottom"});
		this.workFlowJsPlumb.addEndpoint(id, endpointOptions, { anchor:"Left",uuid: id+"_Left"});
		
		//DIV可拖动
		if("config_new" == this.model || "config_edit" == this.model)
			this.workFlowJsPlumb.draggable(id);
	};
	
	this.moveDlg2Mid = function(id){
		var w = $("#"+id).width();
		var c_width = $($(".jqmOverlay")[0]).width();
		var left = (c_width - w)/2;
		
		if(left > 0){
			$("#"+id).css("left",left+"px");
		}
		
		$("#"+id).css("opacity","1");
	};
	
	this.showNode = function(id){
		Eop.Dialog.init({id:this.nodeAttrDiv,modal:false,title:"环节属性",width:"600px"});
		
		var url = ctx + this.nodeAttrUrl;
		
		Eop.Dialog.open(this.nodeAttrDiv);
		$("#"+this.nodeAttrDiv).load(url);
		
		this.curr_node = id;
		
		this.moveDlg2Mid("dlg_"+this.nodeAttrDiv);
	};
	
	this.loadConnections = function(retCons){
		for(var i=0;i<retCons.length;i++){
			var source_uuid = retCons[i].source_uuid;
			var target_uuid = retCons[i].target_uuid;
			var connect_label = WorkFlowTool.getInputValue(retCons[i].connect_label);
			
			try{
				var connnect = this.workFlowJsPlumb.connect({uuids: [source_uuid, target_uuid], editable: true});
				connnect.getOverlay("label").setLabel(connect_label);
			}catch(e){
				
			}
		}
	};
	
	this.showCurrNode = function (){
		for(var index in this.nodes){
			var node = this.nodes[index];
			
			var node_index = node.node_index;
			var id = "node_"+node_index;
			var is_done = node.is_done;
			var is_curr_step = node.is_curr_step;
			var state_code = node.state_code;
			var remark = node.remark;
			
			if("0"==is_done && "0" == is_curr_step){
				$("#"+id).addClass("color_gray");
			}
			
			if("1" == is_curr_step){
				if("error" == state_code){
					$("#"+id).addClass("node_error");
					$("#"+id).attr("title",remark);
				}else{
					$("#"+id).addClass("node_curr");
				}
			}
		}
	};
	
	this.initReadOnly = function(){
		if("config_dealer" == this.model || "show_workflow" == this.model
				|| "config_his" == this.model){
			$("#base_info").find("input").attr("disabled","disabled");
			$("#base_info").find("select").attr("disabled","disabled");
			
			$("#"+this.lib_container_id).hide();
			
			$("#comments_div").hide();
			$("#"+this.canvas_container_id).css({
				"width": "100%",
			    "display": "inline",
			    "float": "left"
			});
			
			$("#saveCfgBtn").remove();
		}
	};
	this.showNodeAttr = function(){
		//curr_node为是单前选择的环节
		var node_name = $("#"+this.curr_node+" .node_name").html();
		var node_type = $("#"+this.curr_node).attr("node_type");
		var node_index = $("#"+this.curr_node).attr("node_index");
		
		$("#node_name").val(node_name);
		$("#node_type").val(node_type);
		
		if("web_condition" == node_type || "web" == node_type){
			$("#deal_type").append("<option value='web'>前台页面</option>");
		}else if("auto" == node_type || "start" == node_type || "end" == node_type){
			$("#deal_type").append("<option value='rule'>规则</option>");
			$("#deal_type").append("<option value='bean'>BEAN</option>");
		}else{
			$("#deal_type").append("<option value='bean'>BEAN</option>");
			$("#deal_type").append("<option value='js'>JS</option>");
		}
		
		$("#deal_type").change(function(){
			var deal_type = $("#deal_type").val();
			
			if("web" == deal_type){
				$("#deal_url").removeAttr("disabled");
				$("#th_deal_url").html("页面地址：");
				$("#ruleSearchBtn").hide();
				$(".tr_deal_param").hide();
				$("#tr_deal_type").hide();
				$("#tr_deal_bean").hide();
			}else if("rule" == deal_type){
				$("#th_deal_url").html("异常处理页面：");
				$("#th_deal_bean").html("规则：");
				$("#deal_bean").css("width","340px");
				$("#deal_bean").attr("disabled","disabled");
				$("#ruleSearchBtn").show();
				$(".tr_deal_param").hide();
				$("#tr_deal_type").show();
				$("#tr_deal_bean").show();
			}else if("js" == deal_type){
				$("#th_deal_url").html("异常处理页面：");
				$(".tr_deal_param").show();
				$("#tr_deal_type").show();
				$("#tr_deal_bean").hide();
			}else{
				$("#th_deal_url").html("异常处理页面：");
				$("#th_deal_bean").html("BEAN：");
				$("#deal_bean").css("width","400px");
				$("#deal_bean").removeAttr("disabled");
				$("#ruleSearchBtn").hide();
				$(".tr_deal_param").hide();
				$("#tr_deal_type").show();
				$("#tr_deal_bean").show();
			}
		});
		
		if(typeof(this.nodes[node_index])!="undefined" && this.nodes[node_index]!=null){
			var node = this.nodes[node_index];
			
			$("#node_name").val(node["node_name"]);
			$("#node_code").val(node["node_code"]);
			$("#old_node_code").val(node["old_node_code"]);
			$("#out_node_code").val(node["out_node_code"]);
			$("#deal_type").val(node["deal_type"]);
			$("#deal_url").val(node["deal_url"]);
			$("#deal_bean").val(node["deal_bean"]);
			$("#deal_param").val(node["deal_param"]);
			$("#remark").val(node["remark"]);
			
			if(typeof(node["dealer_type"])!="undefined" && ""!=node["dealer_type"]){
				$(".tr_nodeattr_dealer").show();
				
				$("#nodeattr_dealer_type").val(WorkFlowTool.getInputValue(node["dealer_type"]));
				$("#nodeattr_dealer_code").val(WorkFlowTool.getInputValue(node["dealer_code"]));
				$("#nodeattr_dealer_name").val(WorkFlowTool.getInputValue(node["dealer_name"]));
				$("#nodeattr_curr_user_id").val(WorkFlowTool.getInputValue(node["curr_user_id"]));
				$("#nodeattr_curr_user_name").val(WorkFlowTool.getInputValue(node["curr_user_name"]));
			}else{
				$(".tr_nodeattr_dealer").hide();
			}
		}
		
		$("#deal_type").change();
		
		if("config_dealer" == this.model || "show_workflow" == this.model
				|| "config_his" == this.model){
			$("#node_edit_div input").attr("disabled","disabled");
			$("#node_edit_div select").attr("disabled","disabled");
			$("#node_edit_div textarea").attr("disabled","disabled");
			$("#ruleSearchBtn").hide();
			$("#tr_edit_btn").hide();
			
			if("show_workflow" == this.model){
				if("1" == this.user.userid){
					$(".tr_flow_btn").show();
					$(".tr_flow_btn input").removeAttr("disabled");
					$(".tr_flow_btn textarea").removeAttr("disabled");
				}else if(this.user.userid == node.curr_user_id){
					$(".tr_rollback").show();
					$(".tr_rollback input").removeAttr("disabled");
					$(".tr_rollback textarea").removeAttr("disabled");
				}
			}else{
				$("#tr_nodeattr_condition").hide();
				$("#tr_flow_btn").hide();
			}
		}else{
			$("#tr_flow_btn").hide();
			
			SelectRuleTool.init();
		}
	};
	
	this.getNodes = function(){
		//节点信息
		var nodeArr = [];
		var count = 0;
		
		for(var i=0;i<$("#"+this.canvas_id+" .node_templete").length;i++){
			var node_div = $("#"+this.canvas_id+" .node_templete")[i];
			
			var node_index = WorkFlowTool.getInputValue($(node_div).attr("node_index"));
			var node_name = WorkFlowTool.getInputValue($(node_div).find(".node_name").html());
			var node_type = WorkFlowTool.getInputValue($(node_div).attr("node_type"));
			
			var top_px = WorkFlowTool.getInputValue($(node_div).css("top"));
			var left_px = WorkFlowTool.getInputValue($(node_div).css("left"));
			
			top_px = top_px.replace('px','');
			left_px = left_px.replace('px','');
			
			var node = this.nodes[node_index];
			if(typeof(node)=="undefined" || node==null){
				node = {};
			}

			node["node_index"] = node_index;
			node["node_name"] = node_name;
			node["node_type"] = node_type;
			node["top_px"] = top_px;
			node["left_px"] = left_px;
			
			nodeArr.push(node);
		}
		
		return nodeArr;
	};
	
	this.getConnects = function(){
		var connectArr = [];
		
		var connects = this.workFlowJsPlumb.getAllConnections();
		
		if(typeof(connects)=="undefined" || connects===null || connects.length==0){
			alert("流程连接不能为空！");
			return  false;
		}
		
		count = 0;
		for(var i=0;i<connects.length;i++){
			var connect = connects[i];
			
			var connect_label = WorkFlowTool.getInputValue(connect.getOverlay("label").getLabel());
			var source_node_id = WorkFlowTool.getInputValue(connect.sourceId);
			var source_node_index = WorkFlowTool.getInputValue($("#"+source_node_id).attr("node_index"));
			var target_node_id = WorkFlowTool.getInputValue(connect.targetId);
			var target_node_index = WorkFlowTool.getInputValue($("#"+target_node_id).attr("node_index"));
			
			var endPoints = connect.endpoints;
			
			if(typeof(endPoints)=="undefined" || endPoints===null || endPoints.length==0){
				alert("连接端点不能为空！");
				return  false;
			}
			
			var source_uuid;
			var target_uuid;
			
			for(var j=0;j<endPoints.length;j++){
				if(source_node_id == endPoints[j].elementId){
					source_uuid = source_node_id+"_"+endPoints[j].anchor.type;
				}
				
				if(target_node_id == endPoints[j].elementId){
					target_uuid = target_node_id+"_"+endPoints[j].anchor.type;
				}
			}
			
			if(typeof(source_uuid)=="undefined" || source_uuid===null ||
					typeof(target_uuid)=="undefined" || target_uuid===null){
				alert("流程端点UUID不能为空！");
				return  false;
			}
			
			var connectObj = {};
			connectObj["connect_label"] = connect_label;
			connectObj["source_node_id"] = source_node_id;
			connectObj["target_node_id"] = target_node_id;
			connectObj["source_node_index"] = source_node_index;
			connectObj["target_node_index"] = target_node_index;
			connectObj["source_uuid"] = source_uuid;
			connectObj["target_uuid"] = target_uuid;
			
			connectArr.push(connectObj);
		}
		
		return connectArr;
	};
	
	this.startWorkFlow = function(order_id){
		var orderId = "";
		
		if(typeof(order_id)!="undefined" && ""!=order_id.trim()){
			orderId = order_id;
			this.order_id = order_id;
		}else{
			orderId = this.order_id;
		}
		
		var param = {
				"workflow.order_id":orderId,
				"workflow.cfg_type":"order"
		};
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!startWorkFlow.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				alert("流程启动成功");
	     			}else{
	     				var msg = reply["msg"];
	     				alert("流程启动失败："+msg);
	     			}
	     		}else{
	     			alert("流程启动失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("流程启动失败："+msg);
	     	}
		});
	};
	
	this.continueWorkFlow = function(order_id){
		var orderId = "";
		
		if(typeof(order_id)!="undefined" && ""!=order_id.trim()){
			orderId = order_id;
			this.order_id = order_id;
		}else{
			orderId = this.order_id;
		}
		
		var param = {
				"ins.order_id":orderId
		};
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!continueWorkFlow.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				alert("继续执行成功");
	     			}else{
	     				var msg = reply["msg"];
	     				alert("继续执行失败："+msg);
	     			}
	     		}else{
	     			alert("继续执行失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("继续执行失败："+msg);
	     	}
		});
	};
	
	/**
	 * 订单管理页面重新查询
	 */
	this.orderListReQuery = function(){
		//需要重新查询的菜单订单管理、订单查询、订单处理
		var menus = ['2014112501','20141014227','99992']
		//sgf
		try{
		
				
				//获取订单管理菜单的main_frame
				var main_frame = $(window.top.document).find("iframe[app_menu_id='2014112501']");
					
				if($(main_frame).length < 1){
					return;
				}
				
				//获取订单管理菜单的auto_frame
				var auto_frame = $(main_frame).contents().find(".auto_frame");
				
				if($(auto_frame).length < 1){
					return;
				}
				
				for (var i=0;i<$(auto_frame).length;i++){
					if($(auto_frame)[i].attributes["0"].nodeValue=="99992" || $(auto_frame)[i].attributes["0"].nodeValue=="2014112501" || $(auto_frame)[i].attributes["0"].nodeValue=="20141014227"){
						$(auto_frame)[i].contentWindow.orderListReQuery();
					}
				}
			
		}catch(error){
			console.log(error.message);
		}
	};
	
	this.runNodeManual = function(instance_id,condition,remark){
		var insId = WorkFlowTool.getInputValue(instance_id);
		var con = WorkFlowTool.getInputValue(condition);
		var re = WorkFlowTool.getInputValue(remark);
		var order_id = "";
		
		for(i in this.nodes){
			order_id = this.nodes[i].order_id;
			
			if(""!=order_id)
				break;
		}
		
		var param = {
				"ins.instance_id":insId,
				"ins.ext_3":con,
				"ins.remark":re
		};
		
		var canvasInstance = this;
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!runNodeManual.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				alert("执行成功");
	     				
	     				if("order_intent" == canvasInstance.order_type){
	     					Eop.Dialog.close(canvasInstance.nodeAttrDiv);
		     				window.location.href=ctx+"/shop/admin/orderIntentAction!intentHandleForm.do";
	     				}else{
	     					//失败重新加载流程，显示执行到哪一步
//		     				canvasInstance.loadWorkFlow();
		     				debugger;
	     					canvasInstance.orderListReQuery();
	     					/*canvas.orderListReQuery();*/
	     					//关闭正式订单页面
	     					$(window.top.document).find("#bottom_tab_ul li[order_detail='"+order_id+"'] .tabClose")[0].click();
	     					window.location.href=ctx+"/shop/admin/ordAuto!showOrderList.do";
	     				}
	     			}else{
	     				var msg = reply["msg"];
	     				alert(msg);
	     				//失败重新加载流程，显示执行到哪一步
	     				canvasInstance.loadWorkFlow();
	     			}
	     		}else{
	     			alert("执行环节失败");
	     			//失败重新加载流程，显示执行到哪一步
	     			canvasInstance.loadWorkFlow();
	     		}
	     	},
	     	error:function(msg){
	     		alert("执行环节失败："+msg);
	     		//失败重新加载流程，显示执行到哪一步
	     		canvasInstance.loadWorkFlow();
	     	}
		});
	};
	
	this.gotoNode = function(instance_id,remark){
		var result = false;
		
		var param = {
				"ins.instance_id":instance_id,
				"ins.remark":remark
		};
		
		var canvasInstance = this;
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!gotoNode.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				result = true;
	     				
	     				alert("跳转成功");
	     				
	     				if("order_intent" == canvasInstance.order_type){
	     					Eop.Dialog.close(canvasInstance.nodeAttrDiv);
		     				window.location.href=ctx+"/shop/admin/orderIntentAction!intentHandleForm.do";
	     				}else{
	     					//失败重新加载流程，显示执行到哪一步
//		     				canvasInstance.loadWorkFlow();
	     					
	     					canvasInstance.orderListReQuery();
		     				
	     					//关闭正式订单页面
	     					$(window.top.document).find("#bottom_tab_ul li[order_detail='"+order_id+"'] .tabClose")[0].click();
	     				}
	     			}else{
	     				var msg = reply["msg"];
	     				alert("跳转失败："+msg);
	     			}
	     		}else{
	     			alert("跳转失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("跳转失败："+msg);
	     	}
		});
		
		return result;
	};
	
	this.rollback = function(instance_id,remark){
		
		var param = {
				"ins.instance_id":instance_id,
				"ins.remark":remark
		};
		
		var canvasInstance = this;
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!rollback.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				alert("回退成功");
	     				
	     				Eop.Dialog.close(canvasInstance.nodeAttrDiv);
	     				window.location.href=ctx+"/shop/admin/orderIntentAction!intentHandleForm.do";
	     				/*canvasInstance.loadWorkFlow();*/
	     			}else{
	     				var msg = reply["msg"];
	     				alert("回退失败："+msg);
	     			}
	     		}else{
	     			alert("回退失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("回退失败："+msg);
	     	}
		});
	};
	
	this.cancelWorkFlow = function(order_id){
		var orderId = "";
		
		if(typeof(order_id)!="undefined" && ""!=order_id.trim()){
			orderId = order_id;
			this.order_id = order_id;
		}else{
			orderId = this.order_id;
		}

		var param = {
				"ins.order_id":orderId
		};
		
		$.ajax({
	     	url:ctx+"/shop/admin/WorkCustom!cancelWorkFlow.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["code"]){
	     				alert("取消流程成功");
	     			}else{
	     				var msg = reply["msg"];
	     				alert("取消流程失败："+msg);
	     			}
	     		}else{
	     			alert("取消流程失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("取消流程失败："+msg);
	     	}
		});
	};
	
	this.clearCanvas = function(){
		if(typeof(this.workFlowJsPlumb)!="undefined" && this.workFlowJsPlumb!=null
				&& $("#"+this.canvas_id).find(".node_templete").length>0){
			$("#"+this.out_container_id).show();
			
			this.workFlowJsPlumb.deleteEveryEndpoint();
			
			this.nodes = {};
			this.id_count = 1;
			
			$("#"+this.canvas_id).find(".node_templete").remove();
			
			$("#"+this.nestWebDiv).hide();
			
			$("#"+this.hideWorkflowRadion).attr('checked','true');
		}
	};
	
	this.setNode = function(){
		var node_index = $("#"+this.curr_node).attr("node_index");
		
		var node;
		
		if(typeof(this.nodes[node_index])!="undefined" && this.nodes[node_index]!=null){
			node = this.nodes[node_index];
		}else{
			node = {};
		}
		
		//校验环节编码是否重复
		var node_code = $("#node_code").val();
		
		if(typeof(node_code)!="undefined" && "null"!=node_code && ""!=node_code){
			for(var index in this.nodes){
				if(index!=node_index && node_code==this.nodes[index].node_code){
					alert("同一流程中环节编码不能重复，【"+node_code+"】环节编码已被【"+this.nodes[index].node_name+"】环节使用！");
					return false;
				}
			}
		}
		
		node["node_index"] = node_index;
		node["node_name"] = $("#node_name").val();
		node["node_code"] = $("#node_code").val();
		node["old_node_code"] = $("#old_node_code").val();
		node["out_node_code"] = $("#out_node_code").val();
		node["node_type"] = $("#node_type").val();
		node["deal_type"] = $("#deal_type").val();
		node["deal_url"] = $("#deal_url").val();
		node["deal_bean"] = $("#deal_bean").val();
		node["deal_param"] = $("#deal_param").val();
		node["remark"] = $("#remark").val();
		
		this.nodes[node_index] = node;
		
		$("#"+this.curr_node).find(".node_name").html($("#node_name").val());
		
		Eop.Dialog.close(this.nodeAttrDiv);
	};
	
	this.removeNode = function(){
		var canvasInstance = this;
		
		if(window.confirm('确认要删除此流程环节？')){
			var endPoints = canvasInstance.workFlowJsPlumb.selectEndpoints({element:canvasInstance.curr_node});
			
			endPoints.deleteEveryConnection();
			
			endPoints.delete();
			
			var node_index = $("#"+canvasInstance.curr_node).attr("node_index");
			
			if(typeof(canvasInstance.nodes[node_index])!="undefined" && 
					canvasInstance.nodes[node_index]!=null){
				delete canvasInstance.nodes[node_index];
			}
			
			$("#"+canvasInstance.curr_node).remove();
			
			Eop.Dialog.close(this.nodeAttrDiv);
		}
	};
	this.loadWorkFlowCfg = function(){
		$("#"+this.container_id+"_cfg_info").hide();
		
		if("config_edit" == this.model || "config_his" == this.model
				|| "config_dealer" == this.model){
			//版本编号信息
			$(".id_info").show();
			
			if("config_edit" == this.model)
				$("#btn_workflow_copy").show();
			
			var param = {
					"cfg.version_id":this.cfg_version
			};
			
			var url = ctx + this.getWorkFlowCfgUrl;
			
			var retCfg;
			var retNodes;
			var retCons;
			var currUser;
			var	currOrg;
			
			$.ajax({
		     	url:url,
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				retCfg = reply["cfg"];
		     				retNodes = reply["nodes"];
		     				retCons = reply["connects"];
		     				currUser = reply["user"];
		     				currOrg = reply["org"];
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询流程信息失败"+msg);
		     			}
		     		}else{
		     			alert("查询流程信息失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询流程信息失败："+msg);
		     	}
			});
			
			if(typeof(retCfg)=="undefined" || retCfg==null){
				alert("查询到的流程配置信息为空");
				
				return false;
			}
			
			if(typeof(retNodes)=="undefined" || retNodes==null){
				alert("查询到的流程节点为空");
				
				return false;
			}
			
			if(typeof(retCons)=="undefined" || retCons==null){
				alert("查询到的流程连接为空");
				
				return false;
			}	
			
			this.cfg = retCfg;
			this.connects = retCons;
			this.user = currUser;
			this.org = currOrg;
			
			this.showWorkFlowCfgInfo(retCfg);
			
			var canvasInstance = this;
			
			this.workFlowJsPlumb.batch(function(){
				canvasInstance.loadNodes(retNodes);
				
				canvasInstance.loadConnections(retCons);
			});
			
			if("config_dealer"==this.model){
				//状态为历史的流程配置，重新绑定连接事件
				this.workFlowJsPlumb.unbind("connection");
				
				this.workFlowJsPlumb.bind("connection", function(info,originalEvent) {
					//连接事件
					var connection = info.connection;
					//删除连接
					this.workFlowJsPlumb.deleteConnection(connection);
				});
			}
		}
	};
	
	this.showWorkFlowCfgInfo = function(cfg){
		$("#edit_cfg_name").val(cfg.cfg_name);
		$("#edit_cfg_id").val(cfg.cfg_id);
		$("#edit_version_id").val(cfg.version_id);

		if("0" == cfg.state || "1" == cfg.state){
			$("#edit_state").empty();
			
			$("#edit_state").append("<option value='0'>禁用</option>");
			$("#edit_state").append("<option value='1'>正常</option>");
		}
		
		$("#edit_state").val(cfg.state);
		
		$("#edit_cfg_level").val(cfg.cfg_level);
		$("#edit_cfg_level").change();
		$("#edit_cfg_type").val(cfg.cfg_type);
		$("#edit_order_deal_method").val(cfg.order_deal_method);
		$("#edit_match_type").val(cfg.match_type);
		$("#edit_flow_code").val(cfg.flow_code);
		
		var goods_type_id = WorkFlowTool.getInputValue(cfg.goods_type_id);
		var goods_cat_id = WorkFlowTool.getInputValue(cfg.goods_cat_id);
		var goods_id = WorkFlowTool.getInputValue(cfg.goods_id);
		
		WorkFlowTool.addGoodsChanger.doGoodsTypeChange(goods_type_id,goods_cat_id);
		WorkFlowTool.addGoodsChanger.doGoodCatChange(goods_cat_id,goods_id);
		
		var order_from = WorkFlowTool.getInputValue(cfg.order_from);
		$("#edit_order_from").val(order_from);
	};
	
	/**
	 * 获取当前环节实例编号
	 */
	this.getCurrInsId = function(){
		var instance_id = "";
		
		for(i in this.nodes){
			var node = this.nodes[i];
			
			if("1" == node.is_curr_step){
				instance_id = node.instance_id;
				break;
			}
		}
		
		return instance_id;
	};
	
	this.getNodeByNodeCode = function (node_code){
		var node;
		
		for(i in this.nodes){
			var n = this.nodes[i];
			
			if(node_code == n.node_code){
				node = n;
				break;
			}
		}
		
		return node;
	}
}

/**
 * 规则选择工具
 */
var SelectRuleTool = {
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"select_rule_div",modal:false,title:"对象规则", height:"600px",width:"750px"});
		$("#ruleSearchBtn").bind("click",function(){
			var self = this;
			Eop.Dialog.open("select_rule_div");
			SelectRuleTool.selectObj(self);
		});
	},
	selectObj:function(cobj){
		var url = ctx+"/shop/admin/ruleManager!qryAssemblyLs.do?ajax=yes";
		var name="";
		
		var objName = $.trim($("#obj_name").val());
		if(objName != "undefined" && objName != ""){
			name = objName;
		}
		
		$("#select_rule_div").load(url,{obj_name:name},function(){
			$("#rule_obj_searchBtn").bind("click",function(){SelectRuleTool.selectObj(cobj);});
			$("#obj_select_confirm_btn").bind("click",function(){SelectRuleTool.addSelectObj(cobj);});
		});
	},addSelectObj:function(cobj){
		var obj_id = $("input[type='radio'][name='radionames']:checked").val();
		if(obj_id == undefined){
			alert("请选择一个对象");
			return ;
		}
		var obj_ids = obj_id.split("##");
		
		$("#deal_bean").val(obj_ids[1]!=undefined?obj_ids[1].trim():"");
		
		Eop.Dialog.close("select_rule_div");		
	}
};

/**
 * 地市、县分联动JS方法
 * @param region_select
 * @param county_select
 * @returns
 */
function RegionCountyChanger(region_select,county_select,limit_region,limt_county,empty_option){
	this.regions = {};
	this.countys = {};
	
	this.region_select = region_select;
	this.county_select = county_select;
	
	this.limit_region = limit_region;
	this.limt_county = limt_county;
	this.empty_option = empty_option;
	
	this.doRegionCountyChange = function(regionId,countyId){
		$("#"+region_select).val(regionId);
		
		$("#"+county_select).empty();
		$("#"+county_select).append("<option value=''></option>");
			
		for(var i=0;i<countys.length;i++){
			var region_Id = countys[i]["region_id"];
			var region_Name = countys[i]["region_name"];
			var pRegion_Id = countys[i]["parent_region_id"];
			
			if(regionId == pRegion_Id){
				var option = "<option value="+region_Id+">"+region_Name+"</option>";
			
				$("#"+county_select).append(option);
			}
		}
		
		if(countyId)
			$("#"+county_select).val(countyId);
	};
	
	$("#"+region_select).change(function(){
		var regionId = $("#"+region_select).val();
		
		$("#"+county_select).empty();
		
		if(typeof(limt_county)=="undefined" || limt_county==null ||
				limt_county.length==0){
			$("#"+county_select).append("<option value=''></option>");
		}else if(typeof(limt_county)!="undefined" && true==empty_option){
			$("#"+county_select).append("<option value=''></option>");
		}
		
		for(var i=0;i<countys.length;i++){
			var region_Id = countys[i]["region_id"];
			var region_Name = countys[i]["region_name"];
			var pRegion_Id = countys[i]["parent_region_id"];
			
			if(regionId == pRegion_Id){
				var option = "<option value="+region_Id+">"+region_Name+"</option>";
			
				$("#"+county_select).append(option);
			}
		}
	});
	
	$.ajax({
     	url:ctx+"/core/admin/org/orgAdmin!getLanAndShowRegion.do?ajax=yes",
     	dataType:"json",
     	async:false,
     	data:{},
     	success:function(reply){
     		if(typeof(reply) != "undefined" && typeof(reply["lan"]) != "undefined" &&
     				typeof(reply["region"]) != "undefined"){
     			regions = reply["lan"];
     			countys = reply["region"];
     			
     			var isLimit = false;
     			
     			if(typeof(limit_region)!="undefined" && limit_region!=null &&
     					limit_region.length>0 && typeof(limit_region[0])!="undefined" &&
     					limit_region[0]!=null && "null"!=limit_region[0]){
     				var temp_regions = [];
         			
         			for(var i=0;i<regions.length;i++){
         				var region = regions[i].lan_id;
         				
         				for(var j = 0; j < limit_region.length; j++) {
         					var region_id = limit_region[j];
         					
         					if(region_id == region){
         						temp_regions.push(regions[i]);
         						break;
         					}
         				}
         			}
         			
         			regions = temp_regions;
         			
         			isLimit = true;
     			}
     			
     			if(typeof(limt_county)!="undefined" && limt_county!=null &&
     					limt_county.length>0 && typeof(limt_county[0])!="undefined" &&
     					limt_county[0]!=null && "null"!=limt_county[0]){
     				var temp_countys = [];
         			
         			for(var i=0;i<countys.length;i++){
         				var county = countys[i].region_id;
         				
         				for(var j = 0; j < limt_county.length; j++) {
         					var county_id = limt_county[j];
         					
         					if(county_id == county){
         						temp_countys.push(countys[i]);
         						break;
         					}
         				}
         			}
         			
         			countys = temp_countys;
     			}
     			
     			$("#"+region_select).empty();
     			
     			if(!isLimit || true==empty_option)
     				$("#"+region_select).append("<option value=''></option>");
     			
     			for(var i=0;i<regions.length;i++){
     				var option = "<option value="+regions[i].lan_id+">"+regions[i].lan_name+"</option>";
     				
     				$("#"+region_select).append(option);
     			}
     			
     			if(isLimit)
     				$("#"+region_select).change();
     		}
     	},
     	error:function(msg){
     		alert(msg);
     	}
	});
}

/**
 * 地市、营业县分联动JS方法
 * @param region_select
 * @param county_select
 * @param limit_region
 * @param limt_county
 * @param empty_option
 * @returns
 */
function RegionBusiCountyChanger(region_select,county_select,limit_region,limt_county,empty_option){
	this.regions = {};
	this.countys = {};
	
	this.region_select = region_select;
	this.county_select = county_select;
	
	this.limit_region = limit_region;
	this.limt_county = limt_county;
	this.empty_option = empty_option;
	
	this.doRegionCountyChange = function(regionId,countyId){
		$("#"+region_select).val(regionId);
		
		$("#"+county_select).empty();
		$("#"+county_select).append("<option value=''></option>");
			
		for(var i=0;i<countys.length;i++){
			var region_Id = countys[i]["region_id"];
			var region_Name = countys[i]["region_name"];
			var pRegion_Id = countys[i]["parent_region_id"];
			
			if(regionId == pRegion_Id){
				var option = "<option value="+region_Id+">"+region_Name+"</option>";
			
				$("#"+county_select).append(option);
			}
		}
		
		if(countyId)
			$("#"+county_select).val(countyId);
	};
	
	$("#"+region_select).change(function(){
		var regionId = $("#"+region_select).val();
		
		$("#"+county_select).empty();
		
		if(typeof(limt_county)=="undefined" || limt_county==null ||
				limt_county.length==0){
			$("#"+county_select).append("<option value=''></option>");
		}else if(typeof(limt_county)!="undefined" && true==empty_option){
			$("#"+county_select).append("<option value=''></option>");
		}
		
		for(var i=0;i<countys.length;i++){
			var region_Id = countys[i]["region_id"];
			var region_Name = countys[i]["region_name"];
			var pRegion_Id = countys[i]["parent_region_id"];
			
			if(regionId == pRegion_Id){
				var option = "<option value="+region_Id+">"+region_Name+"</option>";
			
				$("#"+county_select).append(option);
			}
		}
	});
	
	$.ajax({
     	url:ctx+"/core/admin/org/orgAdmin!getLanAndBusiCounty.do?ajax=yes",
     	dataType:"json",
     	async:false,
     	data:{},
     	success:function(reply){
     		if(typeof(reply) != "undefined" && typeof(reply["lan"]) != "undefined" &&
     				typeof(reply["region"]) != "undefined"){
     			regions = reply["lan"];
     			countys = reply["region"];
     			
     			var isLimit = false;
     			
     			if(typeof(limit_region)!="undefined" && limit_region!=null &&
     					limit_region.length>0 && typeof(limit_region[0])!="undefined" &&
     					limit_region[0]!=null && "null"!=limit_region[0]){
     				var temp_regions = [];
         			
         			for(var i=0;i<regions.length;i++){
         				var region = regions[i].lan_id;
         				
         				for(var j = 0; j < limit_region.length; j++) {
         					var region_id = limit_region[j];
         					
         					if(region_id == region){
         						temp_regions.push(regions[i]);
         						break;
         					}
         				}
         			}
         			
         			regions = temp_regions;
         			
         			isLimit = true;
     			}
     			
     			if(typeof(limt_county)!="undefined" && limt_county!=null &&
     					limt_county.length>0 && typeof(limt_county[0])!="undefined" &&
     					limt_county[0]!=null && "null"!=limt_county[0]){
     				var temp_countys = [];
         			
         			for(var i=0;i<countys.length;i++){
         				var county = countys[i].region_id;
         				
         				for(var j = 0; j < limt_county.length; j++) {
         					var county_id = limt_county[j];
         					
         					if(county_id == county){
         						temp_countys.push(countys[i]);
         						break;
         					}
         				}
         			}
         			
         			countys = temp_countys;
     			}
     			
     			$("#"+region_select).empty();
     			
     			if(!isLimit || true==empty_option)
     				$("#"+region_select).append("<option value=''></option>");
     			
     			for(var i=0;i<regions.length;i++){
     				var option = "<option value="+regions[i].lan_id+">"+regions[i].lan_name+"</option>";
     				
     				$("#"+region_select).append(option);
     			}
     			
     			if(isLimit)
     				$("#"+region_select).change();
     		}
     	},
     	error:function(msg){
     		alert(msg);
     	}
	});
}

/**
 * 商品联动JS方法
 * @param type_select
 * @param cat_select
 * @param goods_select
 * @param limit_types
 * @param limit_cats
 * @param limit_goods
 * @returns
 */
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

/**
 * 省份地市县分联动JS方法
 * @param province_select
 * @param city_select
 * @param area_select
 * @returns
 */
function ProvincesChanger(province_select,city_select,area_select){
	this.provinces = {};
	this.citys = {};
	this.areas = {};
	
	this.province_select = province_select;
	this.city_select = city_select;
	this.area_select = area_select;
	
	
	$.ajax({
     	url:ctx+"/shop/admin/orderFlowAction!ProvList.do?ajax=yes",
     	dataType:"json",
     	data:{},
     	async:false,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			provinces = reply;
     			
     			$("#"+province_select).empty();
     			
				$("#"+province_select).append("<option value=''>请选择</option>");
     			
     			for(var i=0;i<provinces.length;i++){
     				var option = "<option value="+provinces[i].code+">"+provinces[i].name+"</option>";
     				
     				$("#"+province_select).append(option);
     			}
     		}else{
     			alert("获取省份信息为空");
     		}
     	},
     	error:function(msg){
     		alert("初始化省份信息失败："+msg);
     	}
	});
	
	this.doProvinceChange = function(provinceid,cityid){
		$("#"+province_select).val(provinceid);
		$("#"+province_select).change();
		
		$("#"+city_select).val(cityid);
		$("#"+city_select).change();
	};
	
	this.doCityChange = function(cityid,areaid){
		$("#"+city_select).val(cityid);
		$("#"+city_select).change();
		
		$("#"+area_select).val(areaid);
		$("#"+area_select).change();
	};
	
	$("#"+province_select).change(function(){
		var provinceid = $("#"+province_select).val();
		
		var param = {
				provinc_code:provinceid
		};
		
		$("#"+city_select).empty();
			
		$("#"+city_select).append("<option value=''>请选择</option>");
		
		$.ajax({
	     	url:ctx+"/shop/admin/orderFlowAction!getCityListByProvId.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			citys = reply;

	     			for(var i=0;i<citys.length;i++){
	     				var code = citys[i]["code"];
	     				var name = citys[i]["name"];
	     				
     					var option = "<option value="+code+">"+name+"</option>";
     				
     					$("#"+city_select).append(option);
	     			}
	     		}else{
	     			alert("获取地市信息为空");
	     		}
	     	},
	     	error:function(msg){
	     		alert("获取地市信息失败："+msg);
	     	}
		});
	});
	
	$("#"+city_select).change(function(){
		var city_code = $("#"+city_select).val();
		
		var param = {
				city_code:city_code
		};
		
		$("#"+area_select).empty();
		$("#"+area_select).append("<option value=''>请选择</option>");
		
		$.ajax({
	     	url:ctx+"/shop/admin/orderFlowAction!getDistrictListByCityId.do?ajax=yes",
	     	type: "POST",
	     	dataType:"json",
	     	async:false,
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			areas = reply;

	     			for(var i=0;i<areas.length;i++){
	     				var code = areas[i]["code"];
	     				var name = areas[i]["name"];
	     				
     					var option = "<option value="+code+">"+name+"</option>";
     				
     					$("#"+area_select).append(option);
	     			}
	     		}else{
	     			alert("获取县分信息为空");
	     		}
	     	},
	     	error:function(msg){
	     		alert("获取县分信息失败："+msg);
	     	}
		});
	});
}
/**
 * 流程工具类
 */
var WorkFlowTool = {
		canvas : {},
		goodsChanger : {},
		addGoodsChanger : {},
		orderFrom : {},
		curr_operate : "",
		curr_deal : "",
		dealerEditRegionCounty : {},
		regionCountyChanger : {},
		permission : {},
		doQuery : function(){
			var ext_1 = $("#ext_1").val();
			var cfg_id = $("#cfg_id").val();
			var cfg_name = $("#cfg_name").val();
			var version_id = $("#version_id").val();
			
			var cfg_level = $("#cfg_level").val();
			var goods_type_id = $("#goods_type_id").val();
			var goods_cat_id = $("#goods_cat_id").val();
			var goods_id = $("#goods_id").val();
			
			var order_from = $("#order_from").val();
			
			if(typeof(ext_1) == "undefined" || ext_1 == null)
				ext_1 = "";
			if(typeof(cfg_id) == "undefined" || cfg_id == null)
				cfg_id = "";
			if(typeof(cfg_name) == "undefined" || cfg_name == null)
				cfg_name = "";
			
			cfg_name = encodeURI(encodeURI(cfg_name));
			
			if(typeof(version_id) == "undefined" || version_id == null)
				version_id = "";
			
			if(typeof(cfg_level) == "undefined" || cfg_level == null)
				cfg_level = "";
			if(typeof(goods_type_id) == "undefined" || goods_type_id == null)
				goods_type_id = "";
			if(typeof(goods_cat_id) == "undefined" || goods_cat_id == null)
				goods_cat_id = "";
			if(typeof(goods_id) == "undefined" || goods_id == null)
				goods_id = "";
			
			if(typeof(order_from) == "undefined" || order_from == null)
				order_from = "";
			
			var url = ctx+"/shop/admin/WorkCustom!getWorkCustomCfgTable.do?ajax=yes";
			
			url += "&cfg.ext_1="+ext_1;
			url += "&cfg.cfg_id="+cfg_id;
			url += "&cfg.cfg_name="+cfg_name;
			url += "&cfg.version_id="+version_id;
			
			url += "&cfg.cfg_level="+cfg_level;
			url += "&cfg.goods_type_id="+goods_type_id;
			url += "&cfg.goods_cat_id="+goods_cat_id;
			url += "&cfg.goods_id="+goods_id;
			
			url += "&cfg.order_from="+order_from;
			
			$("#cfgFrame").attr("src",url);
		},
		showAddDiag:function(){
			WorkFlowTool.canvas = new WorkFlowCanvas("work_flow_div");
			WorkFlowTool.canvas.model = "config_new";
			
			Eop.Dialog.init({id:"EditDiag",modal:false,title:"新增流程",width:"1100px"});
			
			var url = ctx+"/shop/admin/WorkCustom!getCustomCfgEditUrl.do?ajax=yes";
			
			Eop.Dialog.open("EditDiag");
			$("#EditDiag").load(url);
			
			$("#dlg_EditDiag").find(".head").find(".title").html("新增流程");
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_EditDiag");
		},
		initOrderFrom : function(from_select){
			$.ajax({
		     	url:ctx+"/shop/admin/WorkCustom!getOrderFrom.do?ajax=yes",
		     	dataType:"json",
		     	data:{},
		     	async:false,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			WorkFlowTool.orderFrom = reply["orderFrom"];
		     			
		     			$("#"+from_select).empty();
		     			
		     			$("#"+from_select).append("<option value=''>请选择</option>");
		     			
		     			for(var i=0;i<WorkFlowTool.orderFrom.length;i++){
		     				var option = "<option value="+WorkFlowTool.orderFrom[i].value+">"+WorkFlowTool.orderFrom[i].value_desc+"</option>";
		     				
		     				$("#"+from_select).append(option);
		     			}
		     		}
		     	},
		     	error:function(msg){
		     		alert("初始化订单来源失败："+msg);
		     	}
			});
		},
		initCfgLevel : function (level_select,div_id){
			$("#"+level_select).change(function(){
				var v = $("#"+level_select).val();
				
				if("id" == v){
					$("#" + div_id + " .condition_type_id").show();
					$("#" + div_id + " .condition_cat_id").show();
					$("#" + div_id + " .condition_goods_id").show();
				}else if("cat" == v){
					$("#" + div_id + " .condition_type_id").show();
					$("#" + div_id + " .condition_cat_id").show();
					$("#" + div_id + " .condition_goods_id").hide();
				}else{
					$("#" + div_id + " .condition_type_id").show();
					$("#" + div_id + " .condition_cat_id").hide();
					$("#" + div_id + " .condition_goods_id").hide();
				}
			});
			
			$("#"+level_select).change();
		},
		saveWorkFlowCfg : function(){
			var param = {};
			
			//配置信息
			var cfg_id = WorkFlowTool.getInputValue($("#edit_cfg_id").val());
			var version_id = WorkFlowTool.getInputValue($("#edit_version_id").val());
			var state = "0";
			if("config_edit" == WorkFlowTool.canvas.model)
				state = WorkFlowTool.getInputValue($("#edit_state").val());
			
			var cfg_name = WorkFlowTool.getInputValue($("#edit_cfg_name").val());
			var cfg_level = WorkFlowTool.getInputValue($("#edit_cfg_level").val());
			var goods_type_id = WorkFlowTool.getInputValue($("#edit_goods_type_id").val());
			var goods_cat_id = WorkFlowTool.getInputValue($("#edit_goods_cat_id").val());
			var goods_id = WorkFlowTool.getInputValue($("#edit_goods_id").val());
			var order_from = WorkFlowTool.getInputValue($("#edit_order_from").val());
			var cfg_type = WorkFlowTool.getInputValue($("#edit_cfg_type").val());
			var order_deal_method = WorkFlowTool.getInputValue($("#edit_order_deal_method").val());
			
			var goods_type_name = WorkFlowTool.getInputValue($("#edit_goods_type_id option:selected").html());
			var goods_cat_name = WorkFlowTool.getInputValue($("#edit_goods_cat_id option:selected").html());
			var goods_name = WorkFlowTool.getInputValue($("#edit_goods_id option:selected").html());
			
			var match_type = WorkFlowTool.getInputValue($("#edit_match_type").val());
			var flow_code = WorkFlowTool.getInputValue($("#edit_flow_code").val());
			
			if(""==match_type){
				alert("匹配方式不能为空！");
				return  false;
			}
			
			if(""==cfg_name){
				alert("流程名称不能为空！");
				return  false;
			}
			
			if(""==cfg_level){
				alert("配置级别不能为空！");
				return  false;
			}
			
			if(""==cfg_type){
				alert("流程类型不能为空！");
				return  false;
			}
			
			if(""==goods_type_id && ""==goods_cat_id && ""==goods_id){
				alert("商品大类，商品小类，商品不能全为空！");
				return  false;
			}
			
			if("type" == cfg_level){
				goods_cat_id = "";
				goods_id = "";
			}else if("cat" == cfg_level){
				goods_id = "";
			}
			
			if("order" != cfg_type){
				order_deal_method = "";
			}
			
			if("1" == match_type){
				flow_code = "";
			}else if("" == flow_code){
				alert("流程编码不能为空！");
				return  false;
			}
			
			param["cfg.cfg_id"] = cfg_id;
			param["cfg.version_id"] = version_id;
			param["cfg.state"] = state;
			param["cfg.cfg_name"] = cfg_name;
			param["cfg.cfg_level"] = cfg_level;
			param["cfg.goods_type_id"] = goods_type_id;
			param["cfg.goods_cat_id"] = goods_cat_id;
			param["cfg.goods_id"] = goods_id;
			param["cfg.order_from"] = order_from;
			param["cfg.cfg_type"] = cfg_type;
			param["cfg.order_deal_method"] = order_deal_method;
			
			param["cfg.goods_type_name"] = goods_type_name;
			param["cfg.goods_cat_name"] = goods_cat_name;
			param["cfg.goods_name"] = goods_name;
			param["cfg.match_type"] = match_type;
			param["cfg.flow_code"] = flow_code;
			
			if($("#"+WorkFlowTool.canvas.canvas_id+" .node_templete").length==0){
				alert("流程节点不能为空！");
				return  false;
			}
			
			//节点信息
			var nodes = WorkFlowTool.canvas.getNodes();
			
			var count = 0;
			for(var i=0;i<nodes.length;i++){
				param["nodes["+count+"]"+".node_id"] = WorkFlowTool.getInputValue(nodes[i]["node_id"]);
				param["nodes["+count+"]"+".node_index"] = WorkFlowTool.getInputValue(nodes[i]["node_index"]);
				param["nodes["+count+"]"+".node_name"] = WorkFlowTool.getInputValue(nodes[i]["node_name"]);
				param["nodes["+count+"]"+".node_code"] = WorkFlowTool.getInputValue(nodes[i]["node_code"]);
				param["nodes["+count+"]"+".old_node_code"] = WorkFlowTool.getInputValue(nodes[i]["old_node_code"]);
				param["nodes["+count+"]"+".out_node_code"] = WorkFlowTool.getInputValue(nodes[i]["out_node_code"]);
				param["nodes["+count+"]"+".node_type"] = WorkFlowTool.getInputValue(nodes[i]["node_type"]);
				param["nodes["+count+"]"+".deal_type"] = WorkFlowTool.getInputValue(nodes[i]["deal_type"]);
				param["nodes["+count+"]"+".deal_url"] = WorkFlowTool.getInputValue(nodes[i]["deal_url"]);
				param["nodes["+count+"]"+".deal_bean"] = WorkFlowTool.getInputValue(nodes[i]["deal_bean"]);
				param["nodes["+count+"]"+".deal_param"] = WorkFlowTool.getInputValue(nodes[i]["deal_param"]);
				param["nodes["+count+"]"+".remark"] = WorkFlowTool.getInputValue(nodes[i]["remark"]);
				param["nodes["+count+"]"+".top_px"] = WorkFlowTool.getInputValue(nodes[i]["top_px"]);
				param["nodes["+count+"]"+".left_px"] = WorkFlowTool.getInputValue(nodes[i]["left_px"]);
				
				count++;
			}
			
			//连接信息
			var connects = WorkFlowTool.canvas.getConnects();
			
			if(typeof(connects)=="undefined" || connects===null || connects.length==0){
				alert("流程连接不能为空！");
				return  false;
			}
			
			count = 0;
			for(var i=0;i<connects.length;i++){
				param["connects["+count+"]"+".connect_label"] = WorkFlowTool.getInputValue(connects[i]["connect_label"]);
				param["connects["+count+"]"+".source_node_id"] = WorkFlowTool.getInputValue(connects[i]["source_node_id"]);
				param["connects["+count+"]"+".target_node_id"] = WorkFlowTool.getInputValue(connects[i]["target_node_id"]);
				param["connects["+count+"]"+".source_node_index"] = WorkFlowTool.getInputValue(connects[i]["source_node_index"]);
				param["connects["+count+"]"+".target_node_index"] = WorkFlowTool.getInputValue(connects[i]["target_node_index"]);
				param["connects["+count+"]"+".source_uuid"] = WorkFlowTool.getInputValue(connects[i]["source_uuid"]);
				param["connects["+count+"]"+".target_uuid"] = WorkFlowTool.getInputValue(connects[i]["target_uuid"]);
				
				count++;
			}
			
			var url;
			
			if("config_edit" == WorkFlowTool.canvas.model){
				url = ctx + "/shop/admin/WorkCustom!updateWorkCustomCfg.do?ajax=yes";
			}else{
				url = ctx + "/shop/admin/WorkCustom!addWorkCustomCfg.do?ajax=yes";
			}
			
			$.ajax({
		     	url:url,
		     	type: "POST",
		     	dataType:"json",
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				alert("保存成功");
		     				Eop.Dialog.close("EditDiag");
		     				
		     				WorkFlowTool.doQuery();
		     			}else{
		     				var msg = reply["msg"];
		     				alert(msg);
		     			}
		     		}else{
		     			alert("保存失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("保存失败："+msg);
		     	}
			});
		},
		showEditDiag : function(version_id,state){
			var title = "修改流程";
			var model = "config_edit";
			
			if("2" == state){
				title = "查看历史版本";
				model = "config_his";
			}else if("3" == state){
				title = "处理人配置";
				model = "config_dealer";
			}

			WorkFlowTool.canvas = new WorkFlowCanvas("work_flow_div");
			WorkFlowTool.canvas.model = model;
			WorkFlowTool.canvas.cfg_version = version_id;
			
			Eop.Dialog.init({id:"EditDiag",modal:false,title:title,width:"1100px"});
			
			var url = ctx+"/shop/admin/WorkCustom!getCustomCfgEditUrl.do?ajax=yes";
			
			Eop.Dialog.open("EditDiag");
			$("#EditDiag").load(url);
			
			$("#dlg_EditDiag").find(".head").find(".title").html(title);
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_EditDiag");
		},
		deleteCustomCfg : function (cfgId){
			if(window.confirm('确认要删除此流程配置？')){
				var param = {"cfg.cfg_id":cfgId};
				
				var url = ctx+"/shop/admin/WorkCustom!deleteWorkCustomCfg.do?ajax=yes";
				
				$.ajax({
			     	url:url,
			     	type: "POST",
			     	dataType:"json",
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["code"]){
			     				alert("删除成功");
			     				WorkFlowTool.doQuery();
			     			}else{
			     				var msg = reply["msg"];
			     				alert("删除失败：" + msg);
			     			}
			     		}else{
			     			alert("删除失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("删除失败："+msg);
			     	}
				});
			}
		},
		getInputValue : function(v){
			if(typeof(v) == "undefined" || v==null || ""==v.trim()){
				return "";
			}
			
			if("请选择" == v.trim()){
				return "";
			}
			
			return v.trim();
		},
		doDealQuery : function(){
			var ext_1 = WorkFlowTool.getInputValue($("#deal_ext_1").val());
			var cfg_id = WorkFlowTool.getInputValue($("#edit_cfg_id").val());
			var deal_level = WorkFlowTool.getInputValue($("#deal_cfg_level").val());
			var region_id = WorkFlowTool.getInputValue($("#deal_region_id").val());
			var county_id = WorkFlowTool.getInputValue($("#deal_county_id").val());
			var node_index = WorkFlowTool.getInputValue($("#deal_node").val());
			var deal_id = WorkFlowTool.getInputValue($("#deal_deal_id").val());
			var d_version_id = WorkFlowTool.getInputValue($("#deal_version_id").val());
			
			if("region" == deal_level){
				county_id = "";
			}
			
			var url = ctx+"/shop/admin/WorkCustom!getWorkCustomDealTable.do?ajax=yes";
			
			url += "&dealer.ext_1="+ext_1;
			url += "&dealer.cfg_id="+cfg_id;
			url += "&dealer.deal_level="+deal_level;
			url += "&dealer.region_id="+region_id;
			url += "&dealer.county_id="+county_id;
			url += "&dealer.node_index="+node_index;
			url += "&dealer.d_version_id="+d_version_id;
			url += "&dealer.deal_id="+deal_id;
			
			$("#deal_info_frame").attr("src",url);
		},
		showDealerCfg : function(){
			if("config_dealer" == WorkFlowTool.canvas.model){
				$("#div_deal_cfg").show();
				
				$("#deal_cfg_level").change(function(){
					var v = $("#deal_cfg_level").val();
					
					if("region" == v){
						$("#div_deal_cfg .deal_region").show();
						$("#div_deal_cfg .deal_county").hide();
					}else{
						$("#div_deal_cfg .deal_region").show();
						$("#div_deal_cfg .deal_county").show();
					}
				});
				
				if("1" == WorkFlowTool.permission.level || "1"==WorkFlowTool.permission.userid){
					$("#deal_cfg_level").val("");
				}else if("2" == WorkFlowTool.permission.level){
					$("#deal_cfg_level").val("");
				}else{
					$("#deal_cfg_level").val("county");
				}
				
				$("#deal_cfg_level").change();
				
				if(typeof(WorkFlowTool.permission)!="undefined" && "1" == WorkFlowTool.permission.level){
					WorkFlowTool.regionCountyChanger = new RegionCountyChanger("deal_region_id","deal_county_id");
				}else if(typeof(WorkFlowTool.permission.level)!="undefined"){
					WorkFlowTool.regionCountyChanger = new RegionCountyChanger("deal_region_id","deal_county_id",
							WorkFlowTool.permission.region,WorkFlowTool.permission.county);
				}else{
					WorkFlowTool.regionCountyChanger = new RegionCountyChanger("deal_region_id","deal_county_id");
				}
				
				$("#deal_node").empty();
				$("#deal_node").append("<option value=''></option>");
				
				for(var index in WorkFlowTool.canvas.nodes){
					var node = WorkFlowTool.canvas.nodes[index];
					
					if("web_condition" == node.node_type || "web" == node.node_type){
						var node_name = node.node_name;
						
						var option = "<option value="+index+">"+node_name+"</option>";
						
						$("#deal_node").append(option);
					}
				}
				
				WorkFlowTool.doDealQuery();
			}
		},
		showDealerEditDiag : function(operate,deal_id){
			var title = "新增处理单位配置";
			WorkFlowTool.curr_operate = operate;
			
			if("edit" == operate){
				WorkFlowTool.curr_deal = deal_id;
				title = "修改处理单位配置";
			}

			Eop.Dialog.init({id:"dealerEditDiag",modal:false,title:title,width:"1100px"});
			
			var url = ctx+"/shop/admin/WorkCustom!getDealerEdit.do?ajax=yes";
			
			Eop.Dialog.open("dealerEditDiag");
			$("#dealerEditDiag").load(url);
			
			$("#dlg_dealerEditDiag").find(".head").find(".title").html(title);
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_dealerEditDiag");
		},
		initDealerEdit : function(){
			
			$("#dealer_edit_deal_level").change(function(){
				var v = $("#dealer_edit_deal_level").val();
				
				if("region" == v){
					$("#dealer_edit_div .deal_region").show();
					$("#dealer_edit_div .deal_county").hide();
				}else{
					$("#dealer_edit_div .deal_region").show();
					$("#dealer_edit_div .deal_county").show();
				}
			});
			
			if("1" == WorkFlowTool.permission.level || "1"==WorkFlowTool.permission.userid){
				$("#dealer_edit_deal_level").val("");
			}else if("2" == WorkFlowTool.permission.level){
				$("#dealer_edit_deal_level").val("region");
			}else{
				$("#dealer_edit_deal_level").val("county");
				$("#dealer_edit_deal_level").attr("disabled","disabled");
			}
			
			$("#dealer_edit_deal_level").change();
			
			if(typeof(WorkFlowTool.permission)!="undefined" && "1" == WorkFlowTool.permission.level){
				WorkFlowTool.dealerEditRegionCounty = new RegionCountyChanger("dealer_edit_region_id","dealer_edit_county_id");
			}else if(typeof(WorkFlowTool.permission.level)!="undefined"){
				WorkFlowTool.dealerEditRegionCounty = new RegionCountyChanger("dealer_edit_region_id","dealer_edit_county_id",
						WorkFlowTool.permission.region,WorkFlowTool.permission.county);
			}else{
				WorkFlowTool.dealerEditRegionCounty = new RegionCountyChanger("dealer_edit_region_id","dealer_edit_county_id");
			}
			
			var nodeOptions = $("#deal_node").html();
			
			$("#dealer_edit_node").html(nodeOptions);
			
			$("#dealer_edit_dealer_type").change(function(){
				var v = $("#dealer_edit_dealer_type").val();
				
				$("#dealer_edit_dealer_code").val("");
				$("#dealer_edit_dealer_name").val("");
				
				if("org" == v){
					$("#dealer_edit_div .search_org").show();
					$("#dealer_edit_div .search_user").hide();
					$("#dealer_edit_div .search_team").hide();
					
					$("#org_info_frame").show();
					$("#user_info_frame").hide();
					$("#team_info_frame").hide();
				}else if("team" == v){
					$("#dealer_edit_div .search_org").hide();
					$("#dealer_edit_div .search_user").hide();
					$("#dealer_edit_div .search_team").show();
					
					$("#org_info_frame").hide();
					$("#user_info_frame").hide();
					$("#team_info_frame").show();
				}else{
					$("#dealer_edit_div .search_org").hide();
					$("#dealer_edit_div .search_user").show();
					$("#dealer_edit_div .search_team").hide();
					
					$("#org_info_frame").hide();
					$("#user_info_frame").show();
					$("#team_info_frame").hide();
				}
			});
			
			$("#dealer_edit_dealer_type").change();
			
			if("edit" == WorkFlowTool.curr_operate){
				WorkFlowTool.loadDealerInfo();
			}
			
			if("org" == $("#dealer_edit_dealer_type").val()){
				WorkFlowTool.doOrgSearch();
			}else if("team" == $("#dealer_edit_dealer_type").val()){
				WorkFlowTool.doTeamSearch();
			}else{
				WorkFlowTool.doUserSearch();
			}
		},
		loadDealerInfo : function(){
			$("#tr_deal_id").show();
			
			var param = {
					"dealer.deal_id":WorkFlowTool.curr_deal
			};
			
			$.ajax({
		     	url:ctx+"/shop/admin/WorkCustom!getDealById.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				var dealer = reply["dealer"];
		     				
		     				if(typeof(dealer) != "undefined" && dealer!=null){
		     					var deal_id = dealer.deal_id;
		     					var d_version_id = dealer.d_version_id;
		     					var node_index = dealer.node_index;
		     					var deal_level = dealer.deal_level;
		     					var region_id = dealer.region_id;
		     					var county_id = dealer.county_id;
		     					var dealer_type = dealer.dealer_type;
		     					var dealer_code = dealer.dealer_code;
		     					var dealer_name = dealer.dealer_name;
		     					
		     					$("#dealer_edit_deal_id").val(deal_id);
		     					$("#dealer_edit_version_id").val(d_version_id);
		     					
		     					$("#dealer_edit_deal_level").val(deal_level);
		     					$("#dealer_edit_deal_level").change();
		     					
		     					WorkFlowTool.dealerEditRegionCounty.doRegionCountyChange(region_id,county_id);
		     					
		     					$("#dealer_edit_node").val(node_index);
		     					
		     					$("#dealer_edit_dealer_type").val(dealer_type);
		     					$("#dealer_edit_dealer_type").change();
		     					
		     					$("#dealer_edit_dealer_code").val(dealer_code);
		     					$("#dealer_edit_dealer_name").val(dealer_name);
		     					
		     					if("org" == dealer_type){
		     						$("#search_org_party_id").val(dealer_code);
		     						WorkFlowTool.doOrgSearch();
		     					}else if("team" == dealer_type){
		     						$("#search_team_id").val(dealer_code)
		     						WorkFlowTool.doTeamSearch();
		     					}else if("person" == dealer_type){
		     						$("#search_user_id").val(dealer_code);
		     						WorkFlowTool.doUserSearch();
		     					}
		     				}else{
		     					alert("查询处理单位信息为空");
		     				}
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询处理单位信息失败"+msg);
		     			}
		     		}else{
		     			alert("查询处理单位信息失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询处理单位信息失败："+msg);
		     	}
			});
		},
		doOrgSearch : function(){
			var party_id = WorkFlowTool.getInputValue($("#search_org_party_id").val());
			var org_name = WorkFlowTool.getInputValue($("#search_org_org_name").val());
			var hq_dept_id = WorkFlowTool.getInputValue($("#search_org_hq_dept_id").val());
			var dept_id = WorkFlowTool.getInputValue($("#search_org_dept_id").val());
			var lan_id = WorkFlowTool.getInputValue($("#dealer_edit_region_id").val());
			var region_id = WorkFlowTool.getInputValue($("#dealer_edit_county_id").val());
			
			if("region" == $("#dealer_edit_deal_level").val())
				region_id = "";
			
			org_name = encodeURI(encodeURI(org_name));
			
			var url = ctx+"/core/admin/org/orgAdmin!searchOrgTable.do?ajax=yes";
			
			url += "&party_id="+party_id;
			url += "&org_name="+org_name;
			url += "&hq_dept_id="+hq_dept_id;
			url += "&dept_id="+dept_id;
			url += "&lan_id="+lan_id;
			url += "&region_id="+region_id;
			
			$("#org_info_frame").attr("src",url);
		},
		setDealerOrg : function(orgId,orgName){
			$("#dealer_edit_dealer_code").val(orgId);
			$("#dealer_edit_dealer_name").val(orgName);
		},
		setDealerTeam : function(team_id,team_name){
			$("#dealer_edit_dealer_code").val(team_id);
			$("#dealer_edit_dealer_name").val(team_name);
		},
		doUserSearch : function(){
			var username = WorkFlowTool.getInputValue($("#search_user_id").val());
			var realname = WorkFlowTool.getInputValue($("#search_user_name").val());
			var lan_id = WorkFlowTool.getInputValue($("#dealer_edit_region_id").val());
			var city_id = WorkFlowTool.getInputValue($("#dealer_edit_county_id").val());
			
			if("region" == $("#dealer_edit_deal_level").val())
				city_id = "";
			
			username = encodeURI(encodeURI(username));
			realname = encodeURI(encodeURI(realname));
			
			var url = ctx+"/core/admin/user/userAdmin!getCustomDealerUserTable.do?ajax=yes";
			
			url += "&username="+username;
			url += "&realname="+realname;
			url += "&lan_id="+lan_id;
			url += "&city_id="+city_id;
			
			$("#user_info_frame").attr("src",url);
		},
		doTeamSearch : function(){
			var team_level = WorkFlowTool.getInputValue($("#search_team_level").val());
			var team_id = WorkFlowTool.getInputValue($("#search_team_id").val());
			var team_name = WorkFlowTool.getInputValue($("#search_team_name").val());
			var region_id = WorkFlowTool.getInputValue($("#dealer_edit_region_id").val());
			var county_id = WorkFlowTool.getInputValue($("#dealer_edit_county_id").val());
			
			if("region" == $("#dealer_edit_deal_level").val())
				city_id = "";
			
			team_name = encodeURI(encodeURI(team_name));
			
			var url = ctx+"/shop/admin/WorkCustom!getUserTeamTable.do?ajax=yes";
			
			url += "&team.team_level="+team_level;
			url += "&team.team_id="+team_id;
			url += "&team.team_name="+team_name;
			url += "&team.region_id="+region_id;
			url += "&team.county_id="+county_id;
			
			$("#team_info_frame").attr("src",url);
		},
		setDealerUser : function(userId,userName){
			$("#dealer_edit_dealer_code").val(userId);
			$("#dealer_edit_dealer_name").val(userName);
		},
		saveDealer : function(){
			var param = {};
			
			//配置信息
			var deal_id = WorkFlowTool.getInputValue($("#dealer_edit_deal_id").val());
			var cfg_id = WorkFlowTool.getInputValue($("#edit_cfg_id").val());
			var deal_level = WorkFlowTool.getInputValue($("#dealer_edit_deal_level").val());
			var region_id = WorkFlowTool.getInputValue($("#dealer_edit_region_id").val());
			var region_name = WorkFlowTool.getInputValue($("#dealer_edit_region_id option:selected").html());
			var county_id = WorkFlowTool.getInputValue($("#dealer_edit_county_id").val());
			var county_name = WorkFlowTool.getInputValue($("#dealer_edit_county_id option:selected").html());
			var node_index = WorkFlowTool.getInputValue($("#dealer_edit_node").val());
			var dealer_type = WorkFlowTool.getInputValue($("#dealer_edit_dealer_type").val());
			var dealer_code = WorkFlowTool.getInputValue($("#dealer_edit_dealer_code").val());
			var dealer_name = WorkFlowTool.getInputValue($("#dealer_edit_dealer_name").val());
			var node_id = "";
			var node_name = "";
			
			if(""==cfg_id){
				alert("流程编号不能为空！");
				return  false;
			}
			
			if(""==deal_level){
				alert("处理单位级别不能为空！");
				return  false;
			}
			
			if(""==region_id){
				alert("地市不能为空！");
				return  false;
			}
			
			if("region" == deal_level){
				county_id = "";
			}else if(""==county_id){
				alert("县分不能为空！");
				return  false;
			}
			
			if(""==node_index){
				alert("流程环节序号不能为空！");
				return  false;
			}
			
			node_id = WorkFlowTool.getInputValue(WorkFlowTool.canvas.nodes[node_index].node_id);
			node_name = WorkFlowTool.getInputValue(WorkFlowTool.canvas.nodes[node_index].node_name);
			
			if(""==node_id){
				alert("环节编号不能为空！");
				return  false;
			}
			
			if(""==dealer_type){
				alert("处理单位类型不能为空！");
				return  false;
			}
			
			if(""==dealer_code){
				alert("处理单位编码不能为空！");
				return  false;
			}
			
			if(""==dealer_name){
				alert("处理单位名称不能为空！");
				return  false;
			}
			
			param["dealer.deal_id"] = deal_id;
			param["dealer.cfg_id"] = cfg_id;
			param["dealer.deal_level"] = deal_level;
			param["dealer.region_id"] = region_id;
			param["dealer.region_name"] = region_name;
			param["dealer.county_id"] = county_id;
			param["dealer.county_name"] = county_name;
			param["dealer.node_id"] = node_id;
			param["dealer.node_index"] = node_index;
			param["dealer.node_name"] = node_name;
			param["dealer.dealer_type"] = dealer_type;
			param["dealer.dealer_code"] = dealer_code;
			param["dealer.dealer_name"] = dealer_name;
			
			var url;
			
			if("edit" == WorkFlowTool.curr_operate){
				url = ctx+"/shop/admin/WorkCustom!updateDealer.do?ajax=yes"
			}else{
				url = ctx+"/shop/admin/WorkCustom!addDealer.do?ajax=yes"
			}
			
			$.ajax({
		     	url:url,
		     	type: "POST",
		     	dataType:"json",
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				alert("保存成功");
		     				Eop.Dialog.close("dealerEditDiag");
		     				
		     				WorkFlowTool.doDealQuery();
		     			}else{
		     				var msg = reply["msg"];
		     				alert(msg);
		     			}
		     		}else{
		     			alert("保存失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("保存失败："+msg);
		     	}
			});
		},

		deleteDealer : function(deal_id){
			if(window.confirm('确认要删除此处理单位配置？')){
				var param = {"dealer.deal_id":deal_id};
				
				var url = ctx+"/shop/admin/WorkCustom!deleteDealer.do?ajax=yes";
				
				$.ajax({
			     	url:url,
			     	type: "POST",
			     	dataType:"json",
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["code"]){
			     				alert("删除成功");
			     				
			     				WorkFlowTool.doDealQuery();
			     			}else{
			     				var msg = reply["msg"];
			     				alert("删除失败：" + msg);
			     			}
			     		}else{
			     			alert("删除失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("删除失败："+msg);
			     	}
				});
			}
		},
		
		getWorkCustomPermission : function(){
			var permission;
			
			var url = ctx+"/shop/admin/WorkCustom!getWorkCustomPermission.do?ajax=yes";
			
			$.ajax({
		     	url:url,
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:{},
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				permission = reply["permission"]
		     			}else{
		     				var msg = reply["msg"];
		     				alert("获取当前工号信息失败：" + msg);
		     			}
		     		}else{
		     			alert("获取当前工号信息失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("获取当前工号信息失败："+msg);
		     	}
			});
			
			return permission;
		},
		
		initPermission : function(){
			WorkFlowTool.permission = WorkFlowTool.getWorkCustomPermission();
		}
		,
		/**
		 * 获取订单树对象
		 */
		getOrderTree : function (orderId){
			var tree;
			
			var param = {
					"order_id":orderId
			};
			
			$.ajax({
		     	url:ctx+"/shop/admin/ordAuto!getOrderTreeById.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				tree = reply["orderTree"];
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询订单对象失败："+msg);
		     			}
		     		}else{
		     			alert("查询订单对象失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询订单对象失败："+msg);
		     	}
			});
			
			return tree;
		},
		
		/**
		 * 获取订单属性
		 * @param orderId
		 * @param field_names
		 */
		getOrderAttrValues : function (orderId,field_names,option_stypes){
			var ret = {};
			
			var param = {
					"order_id":orderId,
					"field_names":field_names,
					"option_stypes":option_stypes
			};
			
			$.ajax({
		     	url:ctx+"/shop/admin/ordAuto!getAttrValuesBatch.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				ret.attrs = reply["attrs"];
		     				ret.options = reply["options"];
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询订单属性失败："+msg);
		     			}
		     		}else{
		     			alert("查询订单属性失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询订单属性失败："+msg);
		     	}
			});

			return ret;
		},
		
		/**
		 * 为select增加配置的选项
		 */
		addOptions2Select : function (select_id,opts){
			$("#"+select_id).empty();
			
			if(typeof(opts) != "undefined"){
				$("#"+select_id).append("<option value=''></option>");
				
				for(var i=0;i<opts.length;i++){
					$("#"+select_id).append("<option value='"+opts[i].value
							+"'>"+opts[i].value_desc+"</option>");
				}
			}
		},
		

		workflowJump : function(target_instance_id){
			if(order_id == null || order_id == "") {
				alert("异常：order_id为空");
				return;
			}
			
			Eop.Dialog.open("WorkflowJumpDiag");
			
			url = ctx + "shop/admin/ordAuto!getWorkflowJumpComfirmUrl.do?ajax=yes&order_id=" + order_id;
			
			$("#WorkflowJumpDiag").load(url, {}, function() {
				//对话框初始化事件
				$("#workflow_jump_comfirm_btn").click(function(){
					var remark = $("#workflow_jump_remark").val();
					
					if(typeof(remark)=="undefined" || ""==remark){
						alert("请输入回退备注！");
						return false;
					}
					
					if(WorkFlowTool.doWorkflowJump(order_id,target_instance_id,remark)){
						//关闭对话框
						Eop.Dialog.close("WorkflowJumpDiag");
					}
				});
			});
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_WorkflowJumpDiag");
		},

		doWorkflowJump : function (order_id,instance_id,remark){
			var param = {
				"order_id":order_id
			};
			
			var has_todo_work = false;
			var result = false;
			
			$.ajax({
		     	url:ctx+"/shop/admin/ordAuto!getOrderIntentCount.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				result = true;
		     				
		     				var todo = reply["todo"];
		     				var done = reply["done"];
		     				
		     				todo = parseInt(todo);
		     				done = parseInt(done);
		     				
		     				if(todo > 0){
		     					//有待办工单，不能回退，不初始化回退按钮
		     					has_todo_work = true;
		     				}
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询工单信息失败："+msg);
		     			}
		     		}else{
		     			alert("查询工单信息失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询工单信息失败："+msg);
		     	}
			});
			
			if(!result)
				return result;
			
			if(has_todo_work){
				alert("有待办工单，不能回退！");
				return false;
			}
			
			if(canvas.gotoNode(instance_id,remark))
				return true;
			else
				return false;
		},
		
		initFlowTitleInfo : function (order_id){
			//查询订单属性
			var ret = WorkFlowTool.getOrderAttrValues(order_id,"order_deal_method","DIC_ORDER_NODE");
			
			//查询订单树
			var orderTree = WorkFlowTool.getOrderTree(order_id);
			
			//设置处理方式
			$("#select_order_deal_method").val(ret.attrs.order_deal_method);
			
			//加载select选项
			WorkFlowTool.addOptions2Select("select_flow_node",ret.options.DIC_ORDER_NODE);
			
			for(var index in canvas.nodes){
				var node = canvas.nodes[index];
				
				if("1"==node.is_curr_step){
					//设置所在环节
					$("#select_flow_node").val(node.old_node_code);
					
					//设置步骤名称
					$("#td_curr_node").html(node.node_name);
					
					$("#select_curr_node_status").val(node.state_code);
					$("#td_work_custom_flow_info").html(node.remark);
					
					//设置异常样式
					if("error" == node.state_code){
						$("#select_curr_node_status").css("color","red");
					}
					
					//获取锁定人
					var lock_user_id = "";
					
					for(var i=0;i<orderTree.orderLockBusiRequests.length;i++){
						if(orderTree.orderLockBusiRequests[i].tache_code == node.old_node_code){
							lock_user_id = orderTree.orderLockBusiRequests[i].lock_user_id;
						}
					}
					
					// 锁定人是当前处理人或者管理员的，且订单以线上方式受理的则初始化按钮
					if(lock_user_id == canvas.user.userid 
							|| "1"==canvas.user.userid){
						if("1" == orderTree.orderExtBusiRequest.order_deal_method && initbtn)
							initbtn();
					}else{
						//提示非当前处理人
						$("#tr_not_lock_user").show();
					}
					
					break;
				}
			}
			
			//如果流程配置了处理方式，则处理方式字段不可修改
			if(typeof(WorkFlowTool.canvas.cfg)!="undefined" 
				&& typeof(WorkFlowTool.canvas.cfg.order_deal_method)!="undefined"
				&& "" != WorkFlowTool.canvas.cfg.order_deal_method ){
				$("#order_deal_method").attr("disabled","disabled");
			}
		},
		
		/**
		 * 初始化回退按钮
		 */
		initGotoNodeBtn : function (goto_node){
			if(typeof(goto_node)=="undefined" || ""==goto_node
					|| "null"==goto_node){
				return false;
			}
			
			var goto_node_arr = goto_node.split(",");
			
			var html = "";
			
			for(var i=0;i<goto_node_arr.length;i++){
				var node_code = goto_node_arr[i];
				
				var node = WorkFlowTool.canvas.getNodeByNodeCode(node_code);
				
				if(typeof(node) != "undefined"){
					html += "<a class='dobtn' style='cursor: pointer;' onclick='WorkFlowTool.workflowJump(\""
						+node.instance_id+"\");'><span>回退到"+node.node_name+"</span></a>";
				}
			}
			
			$(".comBtns").append(html);
		},
		
		/**
		 * 改变退单按钮的绑定事件
		 */
		changeCancelBtnEvent : function(){
			//订单审核页面“审核不通过”按钮
			$(".comBtns").find("a[name=o_notpass]").removeAttr("ac_url");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("show_type");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("hide_page");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("order_id");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("form_id");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("check_fn");
			$(".comBtns").find("a[name=o_notpass]").removeAttr("callcack_fn");
			
			$(".comBtns").find("a[name=o_notpass]").unbind("click").click(function(){
				if("" == OrderInfoTool.change_fields)
					OrderInfoTool.change_fields = "contact_results_frist,contact_results_second";
				
				OrderInfoTool.cancelOrder();
			});
			
			//开户、写卡、发货、订单归档页面“退单”按钮
			if($(".comBtns").find("a[name=o_shipping]").length > 0){
				for(var i=0 ; i < $(".comBtns").find("a[name=o_shipping]").length ; i++){
					var btn = $(".comBtns").find("a[name=o_shipping]")[i];
					
					var html = $(btn).html();
					
					if("结单" == html || "退单" == html || "退单申请"==html){
						$(btn).removeAttr("ac_url");
						$(btn).removeAttr("show_type");
						$(btn).removeAttr("hide_page");
						$(btn).removeAttr("order_id");
						$(btn).removeAttr("form_id");
						$(btn).removeAttr("check_fn");
						$(btn).removeAttr("callcack_fn");
						
						$(btn).unbind("click").click(function(){
							if("" == OrderInfoTool.change_fields)
								OrderInfoTool.change_fields = "contact_results_frist,contact_results_second";
							
							OrderInfoTool.cancelOrder();
						});
					}
				}
			}
			
		},
		
		changeBack2OrderCheckEvent : function(order_id){
			//开户、写卡、发货、订单归档页面“回退到订单审核重试”按钮
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("ac_url");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("show_type");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("hide_page");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("order_id");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("form_id");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("check_fn");
			$(".comBtns").find("a[name=rebackvisit]").removeAttr("callcack_fn");
			
			$(".comBtns").find("a[name=rebackvisit]").unbind("click").click(function(){
				var node = WorkFlowTool.canvas.getNodeByNodeCode("order_check");
				
				if(typeof(node) != "undefined"){
					WorkFlowTool.workflowJump(node.instance_id);
				}
			});
		},
		
		/**
		 * 弹出复制流程对话框
		 */
		showCopyWorkFlowDiag : function(){
			Eop.Dialog.open("CopyWorkFlowComfirmDiag");
			
			url = ctx + "shop/admin/WorkCustom!getCopyWorkFlowComfirmUrl.do?ajax=yes";
			
			$("#CopyWorkFlowComfirmDiag").load(url, {}, function() {});
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_CopyWorkFlowComfirmDiag");
		},
		
		/**
		 * 复制流程
		 */
		copyWorkFlow : function(){
			if(typeof(WorkFlowTool.canvas.cfg.cfg_id) == "undefined" 
					|| "" == WorkFlowTool.canvas.cfg.cfg_id
					|| "null" == WorkFlowTool.canvas.cfg.cfg_id){
				alert("复制对象的配置编号为空！");
				return false;
			}
			
			var param = {};
			
			//配置信息
			var cfg_id = WorkFlowTool.canvas.cfg.cfg_id;
			var state = "0";
			
			var cfg_name = WorkFlowTool.getInputValue($("#copy_cfg_name").val());
			var cfg_level = WorkFlowTool.getInputValue($("#copy_cfg_level").val());
			var goods_type_id = WorkFlowTool.getInputValue($("#copy_goods_type_id").val());
			var goods_cat_id = WorkFlowTool.getInputValue($("#copy_goods_cat_id").val());
			var goods_id = WorkFlowTool.getInputValue($("#copy_goods_id").val());
			var order_from = WorkFlowTool.getInputValue($("#copy_order_from").val());
			var cfg_type = WorkFlowTool.getInputValue($("#copy_cfg_type").val());
			var order_deal_method = WorkFlowTool.getInputValue($("#copy_order_deal_method").val());
			var match_type = WorkFlowTool.getInputValue($("#copy_match_type").val());
			var flow_code = WorkFlowTool.getInputValue($("#copy_flow_code").val());
			
			var goods_type_name = WorkFlowTool.getInputValue($("#copy_goods_type_id option:selected").html());
			var goods_cat_name = WorkFlowTool.getInputValue($("#copy_goods_cat_id option:selected").html());
			var goods_name = WorkFlowTool.getInputValue($("#copy_goods_id option:selected").html());
			
			if("" == match_type){
				alert("匹配方式不能为空！");
				return  false;
			}
			
			if(""==cfg_name){
				alert("流程名称不能为空！");
				return  false;
			}
			
			if(""==cfg_level){
				alert("配置级别不能为空！");
				return  false;
			}
			
			if(""==cfg_type){
				alert("流程类型不能为空！");
				return  false;
			}
			
			if(""==goods_type_id && ""==goods_cat_id && ""==goods_id){
				alert("商品大类，商品小类，商品不能全为空！");
				return  false;
			}
			
			if("type" == cfg_level){
				goods_cat_id = "";
				goods_id = "";
			}else if("cat" == cfg_level){
				goods_id = "";
			}
			
			if("order" != cfg_type){
				order_deal_method = "";
			}
			
			if("1" == match_type){
				flow_code = "";
			}else if("" == flow_code){
				alert("流程编码不能为空！");
				return  false;
			}
			
			param["cfg.cfg_id"] = cfg_id;
			param["cfg.state"] = state;
			param["cfg.cfg_name"] = cfg_name;
			param["cfg.cfg_level"] = cfg_level;
			param["cfg.goods_type_id"] = goods_type_id;
			param["cfg.goods_cat_id"] = goods_cat_id;
			param["cfg.goods_id"] = goods_id;
			param["cfg.order_from"] = order_from;
			param["cfg.cfg_type"] = cfg_type;
			param["cfg.order_deal_method"] = order_deal_method;
			
			param["cfg.goods_type_name"] = goods_type_name;
			param["cfg.goods_cat_name"] = goods_cat_name;
			param["cfg.goods_name"] = goods_name;
			param["cfg.match_type"] = match_type;
			param["cfg.flow_code"] = flow_code;
			
			var url = ctx + "/shop/admin/WorkCustom!copyWorkFlow.do?ajax=yes";;
			
			$.ajax({
		     	url:url,
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				alert("复制成功");
		     				Eop.Dialog.close("CopyWorkFlowComfirmDiag");
		     				
		     				WorkFlowTool.doQuery();
		     			}else{
		     				var msg = reply["msg"];
		     				alert(msg);
		     			}
		     		}else{
		     			alert("复制失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("复制失败："+msg);
		     	}
			});
		},
		
		/**
		 * 初始化配置类型事件
		 */
		initCfgType : function(select_cfg_type){
			// 从配置中读取枚举值
			var param = {
					"option_stypes":"DIC_WORKFLOW_CFGTYPE"
			};
			
			$.ajax({
		     	url:ctx+"/shop/admin/ordAuto!getAttrValuesBatch.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				var options = reply["options"];
		     				
		     				if(typeof(options.DIC_WORKFLOW_CFGTYPE)!="undefined"){
		     					var html = "";
		     					
		     					for(var i=0;i<options.DIC_WORKFLOW_CFGTYPE.length;i++){
		     						html += "<option value='"+options.DIC_WORKFLOW_CFGTYPE[i].value+"'>"
		     							+options.DIC_WORKFLOW_CFGTYPE[i].value_desc+"</option>"
		     					}
		     					
		     					$("#"+select_cfg_type).append(html);
		     				}
		     			}else{
		     				var msg = reply["msg"];
		     				alert("查询流程类型枚举值失败："+msg);
		     			}
		     		}else{
		     			alert("查询流程类型枚举值失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("查询流程类型枚举值失败："+msg);
		     	}
			});	
			
			
			$("#"+select_cfg_type).change(function(){
				var v = $("#"+select_cfg_type).val();
				
				if("order" == v){
					$(".td_order_deal_method").show();
				}else{
					$(".td_order_deal_method").hide();
				}
			});
			
			$("#"+select_cfg_type).change();
		},
		
		/**
		 * 初始化匹配类型
		 */
		initMatchType : function(select_match_type){
			$("#"+select_match_type).change(function(){
				var v = $("#"+select_match_type).val();
				
				if("1" == v){
					$(".flow_code_info").hide();
				}else{
					$(".flow_code_info").show();
				}
			});
			
			$("#"+select_match_type).change();
		}
};

function setFrameHeight(frame_id){
	var frm = document.getElementById(frame_id);   
	
    var subWeb = document.frames ? document.frames[frame_id].document : frm.contentDocument; 
    
    if(frm != null && subWeb != null){ 
    	frm.height = subWeb.body.scrollHeight + 20;
    }   
}

/*客户联系结果加载*/
function ContactResult(frist_select,second_select){
	this.frist=[];
	this.second=[];
	
	this.frist_select=frist_select;
	this.second_select=second_select;
	
	$.ajax({
		url: ctx + '/shop/admin/orderIntentAction!getFristContactResults.do?ajax=yes',
		dataType:"json",
     	data:{},
     	async:false,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			frist = reply["frist_results"];
     			
     			$("#"+frist_select).empty();
     			
				$("#"+frist_select).append("<option value=''>请选择</option>");
				
				for(var i=0;i<frist.length;i++){
					var option = "<option value="+frist[i].result_id+">"+frist[i].results+"</option>";
					$("#"+frist_select).append(option);
				}
     		}else{
     			alert("客户联系结果为空");
     		}
     	},error:function(msg){
     		alert("初始化联系结果失败："+msg);
     	}
	});
	
	this.doGoodsTypeChange = function(frist,second){
		$("#"+frist_select).val(frist);
		$("#"+frist_select).change();
		
		$("#"+second_select).val(second);
		$("#"+second_select).change();
	};
	
	$("#"+frist_select).change(function(){
		var fristid = $("#"+frist_select).val();
		var param = {
				frist_id:fristid
		};
		$("#"+second_select).empty();
		
		$("#"+second_select).append("<option value=''>请选择</option>");
		
		$.ajax({
			url: ctx + '/shop/admin/orderIntentAction!getSecondContactResults.do?ajax=yes',
			dataType:"json",
	     	data:param,
	     	async:false,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			second = reply["second_results"];
	     			
	     			$("#"+second_select).empty();
	     			
					$("#"+second_select).append("<option value=''>请选择</option>");
					
					for(var i=0;i<second.length;i++){
						var option = "<option value="+second[i].result_id+">"+second[i].results+"</option>";
						$("#"+second_select).append(option);
					}
	     		}else{
	     			alert("客户联系结果为空");
	     		}
	     	},error:function(msg){
	     		alert("初始化联系结果失败："+msg);
	     	}
		});

	});
}

/**
 * 订单信息初始化工具
 */
var OrderInfoTool = {
		orderTree : {},
		
		contactResult : {},
		
		options : {},
		
		attrs : {},
		
		regionCountyChanger : {},
		
/*		deliveryRegionCountyChanger : {},*/
		
		newDeliveryRegionCountyChanger : {},
		
		goodsChanger : {},
		
		change_fields : "",
		
		option_stypes : "",
		
		field_names : "",
		
		/**
		 * 装载订单数据
		 */
		loadOrderData : function(){
			if(typeof(OrderInfoTool.orderTree)!="undefined" 
				&& OrderInfoTool.orderTree!=null){
				
				OrderInfoTool.initAttrOptions();
				
				OrderInfoTool.initContectResult();
				
				OrderInfoTool.initBaseInfo();
				
				OrderInfoTool.initContactInfo();
				
				OrderInfoTool.initCustInfo();
				
				OrderInfoTool.initGoodsInfo();
				
				OrderInfoTool.initDeveloperInfo();
				
				OrderInfoTool.initPayInfo();
				
				OrderInfoTool.initDeliveryInfo();
				
				OrderInfoTool.initDivSwtich();
			}
		},
		initAttrOptions : function(){
			if(typeof(OrderInfoTool.options)!="undefined"){
				//订单来源
				OrderInfoTool.addOptions2Select("order_from",OrderInfoTool.options.source_from);
				
				//激活状态
				OrderInfoTool.addOptions2Select("active_flag",OrderInfoTool.options.active_flag);
				
				//签收状态
				OrderInfoTool.addOptions2Select("sign_status",OrderInfoTool.options.sign_status);
				
				//王卡状态
				OrderInfoTool.addOptions2Select("kingcard_status",OrderInfoTool.options.DIC_ORDER_KINGCARD_STATE);
				
				//证件上传状态
				OrderInfoTool.addOptions2Select("if_Send_Photos",OrderInfoTool.options.certificate_type);
				
				//客户类型
				OrderInfoTool.addOptions2Select("CustomerType",OrderInfoTool.options.CustomerType);
				
				//支付状态
				OrderInfoTool.addOptions2Select("pay_status",OrderInfoTool.options.DIC_PAY_STATUS);
				
				//支付类型
				OrderInfoTool.addOptions2Select("paytype",OrderInfoTool.options.pay_type);
				
				//支付方式
				OrderInfoTool.addOptions2Select("pay_method",OrderInfoTool.options.pay_way);
			}
		},
		addOptions2Select : function(select_id,opts){
			$("#"+select_id).empty();
			
			if(typeof(opts) != "undefined"){
				$("#"+select_id).append("<option value=''></option>");
				
				for(var i=0;i<opts.length;i++){
					$("#"+select_id).append("<option value='"+opts[i].value
							+"'>"+opts[i].value_desc+"</option>");
				}
			}
		},
		initContectResult : function (){
			OrderInfoTool.contactResult = new ContactResult("contact_results_frist","contact_results_second");
			
			$("#order_deal_method").val(OrderInfoTool.attrs.order_deal_method);//订单受理方式
			
			OrderInfoTool.contactResult.doGoodsTypeChange(OrderInfoTool.attrs.contact_results_frist,
					OrderInfoTool.attrs.contact_results_second);
		},
		initChangeFields : function (){
			var change_fields_arr = OrderInfoTool.change_fields.split(",");
			
			for(var i=0;i<change_fields_arr.length;i++){
				var field_name = change_fields_arr[i];
				
				$("#"+field_name).removeAttr("disabled");
				
				var not_null = $("#"+field_name).attr("not_null");
				if("true" == not_null){
					$("#"+field_name).css("background-color","#FBE2E2");
				}else{
					$("#"+field_name).css("background-color","#ffc50082");
				}
			}
		},
		
		/**
		 * 初始化订单基础信息
		 */
		initBaseInfo : function (){
			OrderInfoTool.regionCountyChanger = new RegionCountyChanger("order_city_code","district_code");
			/*OrderInfoTool.deliveryRegionCountyChanger = new RegionCountyChanger("city_code","area_code");*/
			
			$("#order_id").val(OrderInfoTool.orderTree.order_id);//订单编号
			$("#out_tid").val(OrderInfoTool.attrs.out_tid);//外部单号
			$("#zb_inf_id").val(OrderInfoTool.attrs.zb_inf_id);//业务系统单号
			$("#tid_time").val(OrderInfoTool.attrs.tid_time);//下单时间
			$("#order_from").val(OrderInfoTool.attrs.order_from);//订单来源
			
			$("#phone_num").val(OrderInfoTool.attrs.phone_num);//业务号码
			if(typeof(OrderInfoTool.orderTree.orderAdslBusiRequest)!="undefined"){
				var kd_account = "";
				for(var i=0;i<OrderInfoTool.orderTree.orderAdslBusiRequest.length;i++){
					if("KD" == OrderInfoTool.orderTree.orderAdslBusiRequest[i].product_type){
						kd_account = OrderInfoTool.orderTree.orderAdslBusiRequest[i].adsl_account;
					}
				}
				
				$("#kd_account").val(kd_account);//宽带账号
			}
			$("#mainNumber").val(OrderInfoTool.attrs.mainnumber);//主卡号码
			$("#order_state").val(OrderInfoTool.attrs.order_state);//订单状态
			$("#if_Send_Photos").val(OrderInfoTool.attrs.if_send_photos);//证件照上传状态
			
			if(typeof(OrderInfoTool.orderTree.orderRealNameInfoBusiRequest)!="undefined")
				$("#active_flag").val(OrderInfoTool.orderTree.orderRealNameInfoBusiRequest.active_flag);//激活状态	
			$("#refund_status").val(OrderInfoTool.attrs.refund_status);//退单状态
			if(typeof(OrderInfoTool.orderTree.orderDeliveryBusiRequests)!="undefined")
				$("#sign_status").val(OrderInfoTool.orderTree.orderDeliveryBusiRequests[0].sign_status);//订单签收状态
			if(typeof(OrderInfoTool.orderTree.orderExtBusiRequest)!="undefined")
				$("#audit_type").val(OrderInfoTool.orderTree.orderExtBusiRequest.audit_type);//稽核状态
			$("#kingcard_status").val(OrderInfoTool.attrs.kingcard_status);//对应王卡状态
			
			$("#service_remarks").val(OrderInfoTool.attrs.service_remarks);//订单备注
			
			OrderInfoTool.regionCountyChanger.doRegionCountyChange(OrderInfoTool.orderTree.orderExtBusiRequest.order_city_code,
					"ZJ"+OrderInfoTool.attrs.district_code);
		},
		
		/**
		 * 初始化联系人信息
		 */
		initContactInfo : function (){
			if(typeof(OrderInfoTool.orderTree.orderDeliveryBusiRequests!="undefined")){
				$("#ship_name").val(OrderInfoTool.attrs.ship_name);//联系人姓名
				$("#reference_phone").val(OrderInfoTool.attrs.reference_phone);//联系电话
				$("#receiver_mobile").val(OrderInfoTool.attrs.receiver_mobile);//联系电话2
			}
		},
		
		/**
		 * 初始化客户信息
		 */
		initCustInfo : function (){
			$("#is_realname").val(OrderInfoTool.attrs.is_realname);//实名校验
			$("#phone_owner_name").val(OrderInfoTool.attrs.phone_owner_name);//客户姓名
			$("#certi_type").val(OrderInfoTool.attrs.certi_type);//证件类型
			$("#cert_card_num").val(OrderInfoTool.attrs.cert_card_num);//证件号码
			$("#cert_address").val(OrderInfoTool.attrs.cert_address);//证件地址
			
			$("#cert_card_nation").val(OrderInfoTool.attrs.cert_card_nation);//民族
			$("#certi_sex").val(OrderInfoTool.attrs.certi_sex);//性别
			$("#birthday").val(OrderInfoTool.attrs.birthday);//生日
			$("#cert_issuer").val(OrderInfoTool.attrs.cert_issuer);//签发机关
			$("#cert_failure_time").val(OrderInfoTool.attrs.cert_failure_time);//失效时间
			
			$("#cert_eff_time").val(OrderInfoTool.attrs.cert_eff_time);//生效时间
			$("#phone_num").val(OrderInfoTool.attrs.phone_num);//客户电话
			$("#cust_id").val(OrderInfoTool.attrs.cust_id);//客户编号
			$("#CustomerType").val(OrderInfoTool.attrs.customertype);//客户类型
			$("#group_code").val(OrderInfoTool.attrs.group_code);//收入归集集团
			
			$("#cert_num2").val(OrderInfoTool.attrs.cert_num2);//出入境号
			$("#messages_send_lasttime").val(OrderInfoTool.attrs.messages_send_lasttime);//短信最近发送时间
			$("#messages_send_count").val(OrderInfoTool.attrs.messages_send_count);//短信发送次数
			$("#cert_pic_flag").val(OrderInfoTool.attrs.cert_pic_flag);//证件审核状态
		},
		
		/**
		 * 初始化商品信息
		 */
		initGoodsInfo : function (){
			OrderInfoTool.goodsChanger = new GoodsChanger("goods_type","goods_cat_id","GoodsID");
			
			OrderInfoTool.goodsChanger.doGoodsTypeChange(OrderInfoTool.attrs.goods_type,OrderInfoTool.attrs.goods_cat_id);
			OrderInfoTool.goodsChanger.doGoodCatChange(OrderInfoTool.attrs.goods_cat_id,OrderInfoTool.attrs.goodsid);
			
			$("#GoodsName").val(OrderInfoTool.attrs.goodsname);//商品名称
			$("#sell_price").val(OrderInfoTool.attrs.sell_price);//商品价格
		},
		
		/**
		 * 初始化发展人
		 */
		initDeveloperInfo : function (){
			$("#develop_code_new").val(OrderInfoTool.attrs.develop_code_new);//发展人编码
			$("#develop_name_new").val(OrderInfoTool.attrs.develop_name_new);//发展人名称
			$("#develop_point_code_new").val(OrderInfoTool.attrs.develop_point_code_new);//发展点编码
			$("#development_point_name").val(OrderInfoTool.attrs.development_point_name);//发展点名称
			
			$("#cbss_development_point_code").val(OrderInfoTool.attrs.cbss_development_point_code);//cbss发展点编码
			$("#cbss_develop_code").val(OrderInfoTool.attrs.cbss_develop_code);//cbss发展人名称
			
			$("#out_operator").val(OrderInfoTool.attrs.out_operator);//下单人编码
			$("#out_operator_name").val(OrderInfoTool.attrs.out_operator_name);//下单人名称
			$("#out_office").val(OrderInfoTool.attrs.out_office);//下单点编码
			$("#out_office_name").val(OrderInfoTool.attrs.out_office_name);//下单点名称
		},
		
		/**
		 * 初始化支付
		 */
		initPayInfo : function (){
			$("#order_origfee").val(OrderInfoTool.attrs.order_origfee);//订单总价
			$("#pay_time").val(OrderInfoTool.attrs.pay_time);//支付时间
			$("#order_realfee").val(OrderInfoTool.attrs.order_realfee);//实收金额
			$("#pay_status").val(OrderInfoTool.attrs.pay_status);//支付状态
			$("#paytype").val(OrderInfoTool.attrs.paytype);//支付方式
			
			$("#pay_method").val(OrderInfoTool.attrs.pay_method);//支付方式
			$("#pay_sequ").val(OrderInfoTool.attrs.pay_sequ);//支付发起流水
			$("#pay_back_sequ").val(OrderInfoTool.attrs.pay_back_sequ);//支付返回流水
		},
		
		/**
		 * 初始化物流信息
		 */
		initDeliveryInfo : function (){
			$("#logi_no").val(OrderInfoTool.attrs.logi_no);//物流单号
			$("#post_fee").val(OrderInfoTool.attrs.post_fee);//应收运费（元）
			$("#n_shipping_amount").val(OrderInfoTool.attrs.n_shipping_amount);//实收运费（元）
			$("#shipping_company").val(OrderInfoTool.attrs.shipping_company);//物流公司名称
			$("#show_receiver_mobile_too").val(OrderInfoTool.attrs.receiver_mobile);//取件人联系电话
			
			$("#shipping_time").val(OrderInfoTool.attrs.shipping_time);//配送时间
			$("#contact_name").val(OrderInfoTool.attrs.ship_name);//收货人姓名
						
			OrderInfoTool.newDeliveryRegionCountyChanger = new ProvincesChanger("province_id","city_code","area_code");
			
			OrderInfoTool.newDeliveryRegionCountyChanger.doProvinceChange(OrderInfoTool.attrs.province_id,OrderInfoTool.attrs.city_code);
			OrderInfoTool.newDeliveryRegionCountyChanger.doCityChange(OrderInfoTool.attrs.city_code,OrderInfoTool.attrs.area_code);
			
			$("#ship_addr").val(OrderInfoTool.attrs.ship_addr);//详细地址
			
			//设置联系地址
			var  region = $("#"+OrderInfoTool.newDeliveryRegionCountyChanger.city_select).find("option:selected").text();
			var  county = $("#"+OrderInfoTool.newDeliveryRegionCountyChanger.area_select).find("option:selected").text();		
			var  region_value = $("#"+OrderInfoTool.newDeliveryRegionCountyChanger.city_select).find("option:selected").val();
			var  county_value = $("#"+OrderInfoTool.newDeliveryRegionCountyChanger.area_select).find("option:selected").val();
			
			if(typeof(region) == "undefined" || null == region || "null" == region || 
					typeof(region_value) == "undefined" || null == region_value || "null" == region_value || "" == region_value){
				region = "";
			}
			
			if(typeof(county) == "undefined" || null == county || "null" == county || 
					typeof(county_value) == "undefined" || null == county_value || "null" == county_value || "" == county_value){
				county = "";
			}
			
			var contact_addr = region + county + OrderInfoTool.attrs.ship_addr;
			
			$("#show_ship_addr_too").val(contact_addr);
		},
		
		initDivSwtich : function (){
			$(".expand_btn").click(function(){
				var div_id = $(this).attr("target_div");
				
				if($("#"+div_id).is(":hidden")){
					$("#"+div_id).show();
					$(this).removeClass("openArrow");
					$(this).addClass("closeArrow");
				}else{
					$("#"+div_id).hide();
					$(this).removeClass("closeArrow");
					$(this).addClass("openArrow");
				}
			});
		},
		
		/**
		 * 更新订单数据
		 * @returns {Boolean}
		 */
		updateInfo : function (){
			var result = false;
			
			var field_values = "";
			
			//可变字段为空，返回true
			if(typeof(OrderInfoTool.change_fields) == "undefined"
				|| "" == OrderInfoTool.change_fields
				|| null == OrderInfoTool.change_fields
				|| "null" == OrderInfoTool.change_fields){
				return true;
			}
			
			//需要修改的属性数组
			var change_fields_arr = OrderInfoTool.change_fields.split(",");
			
			for(var i=0;i<change_fields_arr.length;i++){
				//字段名
				var field_name = change_fields_arr[i];
				
				//字段值
				var value = $("#"+field_name).val();
				value = WorkFlowTool.getInputValue(value);
				
				if("area_code" == field_name){
					value = value.replace("ZJ","");
				}
				
				//是否可为空判断
				var not_null = $("#"+field_name).attr("not_null");
				if("true" == not_null && ""==value){
					//取字段名称
					var title = $("#"+field_name).parent().prev().html();
					//提示不能为空
					alert(title+"不能为空");
					//获取焦点
					$("#"+field_name).focus();
					//返回修改失败
					return false;
				}
				
				//拼接属性值字符串
				if("" != field_values){
					field_values += ",";
				}
				
				if("" == value)
					value = "null";
				
				field_values += value;
			}
			
			//提交后台
			var param = {
				"order_id":order_id,
				"field_names":OrderInfoTool.change_fields,
				"field_values":field_values
			};
			
			$.ajax({
		     	url:ctx+"/shop/admin/ordAuto!updateAttrsValuesBatch.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["code"]){
		     				result = true;
		     			}else{
		     				var msg = reply["msg"];
		     				alert("修改信息失败："+msg);
		     			}
		     		}else{
		     			alert("修改信息失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("修改信息失败："+msg);
		     	}
			});
			
			return result;
		},
		
		/**
		 * 客户联系提交
		 * @returns {Boolean}
		 */
		customerContactSubmit : function (){
			//分支条件
			var condition = $("#order_deal_method option:selected").html();
			//备注
			var remark = $("#service_remarks").val();
			
			//更新信息
			if(!OrderInfoTool.updateInfo()){
				return false;
			}
			if($("#is_next option:selected").html()!=null){
				condition = $("#is_next option:selected").html();
			}
			
			var instance_id = canvas.getCurrInsId();
			
			if("" == instance_id){
				alert("未找到当前环节！");
				return false;
			}

			//流转
			canvas.runNodeManual(instance_id,condition,remark);
		},
		doCancelOrder : function (order_id,dealDesc){
			var contact_results_frist = $("#select_contact_results_frist").val();
			var contact_results_second = $("#select_contact_results_second").val();
			var result=false;
			var param={
					"order_id":order_id
			}
			$.ajax({
		     	url:ctx+"/shop/admin/orderIntentAction!neworOldClose.do?ajax=yes",
		     	type: "POST",
		     	dataType:"json",
		     	async:false,
		     	data:param,
		     	success:function(reply){
		     		if(typeof(reply) != "undefined"){
		     			if("0" == reply["status"]){
		     				result=OrderInfoTool.newCloseOrder(order_id,dealDesc,contact_results_frist,contact_results_second);
		     			}else{
		     				result=OrderInfoTool.oldCloseOrder(order_id,dealDesc);
		     			}
		     		}else{
		     			alert("退单失败");
		     		}
		     	},
		     	error:function(msg){
		     		alert("退单失败："+msg);
		     	}
			});
			return result;
			
		},
		//泛智能退单
		newCloseOrder : function(order_id,dealDesc,contact_results_frist,contact_results_second){
			
			var param = {
					"order_id":order_id,
					"statement_work":dealDesc,
					"contactResults":contact_results_frist,
					"contactSecondResults":contact_results_second,
					"applyFrom":"1"
				};
				
				var result = false;
				
				$.ajax({
			     	url:ctx+"/shop/admin/orderIntentAction!newCloseOrder.do?ajax=yes",
			     	type: "POST",
			     	dataType:"json",
			     	async:false,
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["status"]){
			     				result = true;
			     				alert(reply["msg"]);
			     			}else{
			     				var msg = reply["msg"];
			     				alert("退单失败："+msg);
			     			}
			     		}else{
			     			alert("退单失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("退单失败："+msg);
			     	}
				});
				return result;
		},
		//以往订单退单
		oldCloseOrder : function(orderId,dealDesc){
			var param = {
					"order_id":order_id,
					"statement_work":dealDesc
				};
				
				var result = false;
				
				$.ajax({
			     	url:ctx+"/shop/admin/orderIntentAction!closeIntent.do?ajax=yes",
			     	type: "POST",
			     	dataType:"json",
			     	async:false,
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["result"]){
			     				result = true;
			     				alert(reply["message"]);
			     			}else{
			     				var msg = reply["message"];
			     				alert("退单失败："+msg);
			     			}
			     		}else{
			     			alert("退单失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("退单失败："+msg);
			     	}
				});
				
				//关闭意向单失败，返回
				if(!result)
					return false;
				
				result = false;
				
				//关闭正式订单
				var param = {
					"order_id":order_id,
					"dealDesc":dealDesc,
					"returnedReasonCode":"",
					"applyFrom":"1"
				};
				
				$.ajax({
			     	url:ctx+"/shop/admin/ordReturned!returnedApply.do?ajax=yes",
			     	type: "POST",
			     	dataType:"json",
			     	async:false,
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["status"]){
			     				alert("退单成功");
			     				
			     				result = true;
			     			}else{
			     				var msg = reply["msg"];
			     				alert("退单失败："+msg);
			     			}
			     		}else{
			     			alert("退单失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("退单失败："+msg);
			     	}
				});
				return result;
			
		},
		
		/**
		 * 结单（退单）
		 * @returns
		 */
		cancelOrder : function (){
			if(order_id == null || order_id == "") {
				alert("异常：order_id为空");
				return;
			}
			
			Eop.Dialog.open("cancelOrderDiag");
			
			url = ctx + "shop/admin/ordAuto!getCancelOrderComfirmUrl.do?ajax=yes&order_id=" + order_id;
			
			$("#cancelOrderDiag").load(url, {}, function() {
				//对话框初始化事件
				$("#cancel_order_comfirm_btn").click(function(){
					var contact_results_frist = $("#select_contact_results_frist").val();
					var contact_results_second = $("#select_contact_results_second").val();
					var remark = $("#cancel_order_remark").val();
					
					if(typeof(contact_results_frist)=="undefined" || ""==contact_results_frist
							|| typeof(contact_results_second)=="undefined" || ""==contact_results_second){
						alert("请选择客户联结果！");
						return false;
					}
					
					if(typeof(remark)=="undefined" || ""==remark){
						alert("请输入退单备注！");
						return false;
					}
					
					$("#contact_results_frist").val(contact_results_frist);
					$("#contact_results_second").val(contact_results_second);
					
					if(!OrderInfoTool.updateInfo()){
						return false;
					}
					
					var frist_text = $("#select_contact_results_frist option:selected").text();
					var second_text = $("#select_contact_results_second option:selected").text();
					
					remark = "客户联系结果："+frist_text+"--"+second_text+"。"+remark;
					
					if(OrderInfoTool.doCancelOrder(order_id,remark)){
						//关闭对话框
						Eop.Dialog.close("cancelOrderDiag");
						debugger;
						//订单管理页面重新查询
						canvas.orderListReQuery();
						
						//关闭页面
		 				$(window.top.document).find("#bottom_tab_ul li[order_detail='"+order_id+"'] .tabClose")[0].click();
		 				
					}else{
						// 刷新流程
						canvas.loadWorkFlow();
					}
				});
			});
			
			WorkFlowTool.canvas.moveDlg2Mid("dlg_cancelOrderDiag");
		}
};