/**
 * 
 */


function initErrorInfo(){
	if(window.parent.canvas){
		var node = window.parent.canvas.nodes[node_index];
		
		var node_type = node.node_type;
		var state_code = node.state_code;
		
		if("error" == state_code){
			$("#error_msg").empty().html(node.remark);
			$("#tr_wait").remove();
		}else{
			$("#tr_error").remove();
			$("#tr_remark").remove();
		}
		
		$("#span_re_try").empty().html(node.node_name+"重试");
		
		if(typeof(back_node_code)=="undefined" || null==back_node_code 
				|| ""==back_node_code || "null"==back_node_code){
			//没有回退的环节编码，移除回退按钮
			$("#roll_back_btn").remove();
		}else{
			//设置回退按钮展示的信息
			for( i in window.parent.canvas.nodes){
				var n = window.parent.canvas.nodes[i];
				
				if(n.node_code == back_node_code){
					$("#span_goto").empty().html("返回"+n.node_name);
					break;
				}
			}
		}
	}else{
		alert("初始化异常信息失败：JS流程画板对象为空");
	}
}

function rollBack(){
	var insid = "";
	
	for( i in window.parent.canvas.nodes){
		var n = window.parent.canvas.nodes[i];
		
		if(n.node_code == back_node_code){
			insid = n.instance_id;
			break;
		}
	}
	
	if("" == insid){
		alert("未找到返回节点的实例编号！");
		return false;
	}
	
	var remark = $("#remark").val();
	
	if("" == remark){
		alert("请填写备注！");
		return false;
	}
	
	if(window.parent.canvas){
		$("#div_btn").children().hide();
		window.parent.canvas.gotoNode(insid,remark);
		$("#div_btn").children().show();
	}
}

function reTry(){
	if(window.parent.canvas){
		$("#div_btn").children().hide();
		window.parent.canvas.runNodeManual(instance_id,"","");
		$("#div_btn").children().show();
	}
}


function doPrintCard(){
	var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id+'&print_type=1';
	//弹出打印页面
	var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
}
