$(function() {
	
	$("#selectSpecsBtn").click(function(){
		OrderExp.showSelectSpecs();
	});
	$("#selectCatalogsBtn").click(function(){
		OrderExp.showSelectCatalogs($(this));
	});
	$("#showOrderExpMarkProcessedBtn").click(function(){
		OrderExp.showOrderExpMarkProcessed();
	});
	$("#showOrderBatchExpProcessedBtn").click(function(){
		OrderExp.showOrderExpProcess(null);
	});
});
var OrderExp={
	init : function() {
		Eop.Dialog.init({id:"specvaluesClassifyDialog",modal:false,title:"关键字归类",width:'450px'});
		Eop.Dialog.init({id:"esearchSpecListDialog",modal:false,title:"搜索编码",width:'550px'});
		Eop.Dialog.init({id:"esearchCatalogListDialog",modal:false,title:"归类信息",width:'550px'});
		Eop.Dialog.init({id:"orderExpMarkProcessedDialog",modal:false,title:"异常单标记",width:'550px'});
		Eop.Dialog.init({id:"orderExpProcessDialog",modal:false,title:"异常单处理",width:'850px'});
		Eop.Dialog.init({id:"orderExpInstDetailDialog",modal:false,title:"异常单详情",width:'680px'});
		Eop.Dialog.init({id:"orderExpInfLogDetailDialog",modal:false,title:"接口日志详情",width:'800px'});
		Eop.Dialog.init({id:"solutionDetailDialog",modal:false,title:"解决方案详情",width:'550px'});
	},
	showSpecvaluesClassify:function(key_id){
		var url= ctx+"/shop/admin/specvalue!catalogList.do?ajax=yes&show_type=dialog&inner.key_id=" + key_id;
		$("#specvaluesClassifyDialog").html("loading...");
		$("#specvaluesClassifyDialog").load(url,{},function(){});
		Eop.Dialog.open("specvaluesClassifyDialog");
	},
	showSelectSpecs:function(){
		var url =ctx + "/shop/admin/esearchSpec!list.do?ajax=yes&show_type=dialog";
		$("#esearchSpecListDialog").html("loading...");
		$("#esearchSpecListDialog").load(url,{},function(){});
		Eop.Dialog.open("esearchSpecListDialog");
	},
	showSelectCatalogs:function(obj){
		var button_id = $(obj).attr("id");
		var url =ctx + "/shop/admin/esearchCatalog!list.do?ajax=yes&show_type=dialog&button_id="+button_id;
		$("#esearchCatalogListDialog").html("loading...");
		$("#esearchCatalogListDialog").load(url,{},function(){});
		Eop.Dialog.open("esearchCatalogListDialog");
	},
	showOrderExpInstDetail:function(order_exp_inst_id){
		var url =ctx + "/shop/admin/orderExp!detail.do?ajax=yes&show_type=dialog&eiqInner.excp_inst_id="+order_exp_inst_id;
		$("#orderExpInstDetailDialog").html("loading...");
		$("#orderExpInstDetailDialog").load(url,function(){});
		Eop.Dialog.open("orderExpInstDetailDialog");
	},
	showExpInfLogDetail:function(log_id){
		var url =ctx + "/shop/admin/infLogs!showEsearch.do?ajax=yes&log_id="+log_id;
		$("#orderExpInfLogDetailDialog").html("loading...");
		$("#orderExpInfLogDetailDialog").load(url,function(){});
		Eop.Dialog.open("orderExpInfLogDetailDialog");
	},
	showSolutionDetail:function(solution_id,rel_obj_id){
		var url =ctx + "/shop/admin/solutionManage!view.do?ajax=yes&solution_id="+solution_id+"&rel_obj_id="+rel_obj_id;
		$("#solutionDetailDialog").html("loading...");
		$("#solutionDetailDialog").load(url,function(){});
		Eop.Dialog.open("solutionDetailDialog");
	},
	showOrderExpMarkProcessed:function(){
		var $eckBoxs = $("#order_exp_list_fm input[name='eckBox']:checked");
		if($eckBoxs.length < 1){
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return ;
		}
		var error = "";
		$eckBoxs.each(function(){  
			var record_status = $(this).attr("record_status");
			if(record_status == '1') {
				error = "["+$(this).val()+"]  该异常单已处理，无需标记";
				return ;
			}
        });
		if(error.length > 1) {
			MessageBox.show(error, 3, 2000);
			return ;
		}
		var url =ctx + "/shop/admin/orderexp/orderexp_mark_processed.jsp?ajax=yes";
		$("#orderExpMarkProcessedDialog").html("loading...");
		$("#orderExpMarkProcessedDialog").load(url,function(){});
		Eop.Dialog.open("orderExpMarkProcessedDialog");
	},
	showOrderExpProcess:function(single_exp_inst_id){
		if(single_exp_inst_id == null) {
			var $eckBoxs = $("#order_exp_list_fm input[name='eckBox']:checked");
			if($eckBoxs.length < 1){
				MessageBox.show("请选择要操作的记录", 3, 2000);
				return ;
			}
			
			var error = "";
			$eckBoxs.each(function(){  
				var record_status = $(this).attr("record_status");
				var is_batch_process = $(this).attr("is_batch_process");
				if(record_status == '1') {
					error = "["+$(this).val()+"]  该异常单已处理，无需再次处理";
					return ;
				}
				
				if(!is_batch_process || is_batch_process != 'Y') {
					error = "["+$(this).val()+"]  该异常单没有匹配到解决方案或者解决方案不支持批处理";
					return ;
				}
	        });
			if(error.length > 1) {
				MessageBox.show(error, 3, 2000);
				return ;
			}
		}
		
		var url =ctx + "/shop/admin/orderexp/orderexp_process.jsp?ajax=yes";
		if(single_exp_inst_id != null) {
			url += "&single_exp_inst_id="+single_exp_inst_id;
		}
		$("#orderExpProcessDialog").html("loading...");
		$("#orderExpProcessDialog").load(url,function(){});
		Eop.Dialog.open("orderExpProcessDialog");
	},
	orderExpProcess:function(expInstIds){
		if(!expInstIds && expInstIds.length < 1){
			MessageBox.show("异常单ID不能为空", 3, 2000);
			return ;
		}
		
		var record_status = $("#eopInner_record_status").val();
		var deal_result = $("#eopInner_deal_result").val();
		if(record_status.length < 1) {
			MessageBox.show("处理状态不能为空", 3, 2000);
			return ;
		}
		if(deal_result.length < 1) {
			MessageBox.show("处理结果不能为空", 3, 2000);
			return ;
		}
		
		var url = ctx + "/shop/admin/orderExp!expProcessed.do?ajax=yes";
		$.ajax({  
            type:'post',  
            traditional :true,
            url:url,
            dataType:'json',
            data:{'expInstIds':expInstIds,'record_status':record_status,'eipInner.deal_result':deal_result},  
            success:function(data){
            	alert(data.outer_msg);
            	$("#orderExpProcessBtn").val("处理");
            	orderExpProcessFlag = false;
            	if(data.outer_status == "0") {
            		//$("#search_order_exp_from").submit();
            		OrderExp.viewProcessResult(data.outers);
            	}
            },
			error:function(){
				$("#orderExpProcessBtn").val("处理");
				orderExpProcessFlag = false;
				alert("o,error.");
			}
        });
	},
	viewProcessResult:function(data){//单条异常单处理
		if(data && data.length > 0){
			var html = "";
			$(data).each(function(i,v){
				html += "<tr>";
				html += "<td  class='alignCen'>";
				html += (v.exp_inst_id ? v.exp_inst_id:"");
				html += "</td>";
				html += "<td  class='alignCen'>";
				html += (v.rel_obj_type == 'order' ? "订单：":"订单队列：");
				html += (v.rel_obj_id ? v.rel_obj_id:"");
				html += "</td>";
				html += "<td  class='alignCen'>";
				html += (v.deal_status == '1' ? "已处理":"未处理");
				html += "</td>";
				html += "<td class='alignCen'>";
				html += (v.solution_type ? v.solution_type:"");
				html += "</td>";
				html += "<td style='text-align: left;'>";
				html += (v.solution_value ? v.solution_value:"");
				html += "</td>";
				html += "<td style='text-align: left;'>";
				html += (v.deal_result ? v.deal_result:"");
				html += "</td>";
	        });
			$("#order_exp_process_result_tby").html(html);
		}
	},
	singleExpProcessed:function(expInstId){//单条异常单处理
		if(!expInstId){
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return ;
		}
		
		var expInstIds = new Array();
		expInstIds.push(expInstId); 
		OrderExp.orderExpProcess(expInstIds);
	},
	batchOrderExpProcess:function(){//异常单批量处理
		var single_exp_inst_id = $("#single_exp_inst_id").val();
		
		var expInstIds = new Array();
		if(single_exp_inst_id != null && single_exp_inst_id.length > 0) {
			expInstIds.push(single_exp_inst_id); 
		}else {
			var $eckBoxs = $("#order_exp_list_fm input[name='eckBox']:checked");
			if($eckBoxs.length < 1){
				MessageBox.show("请选择要操作的记录", 3, 2000);
				return ;
			}
			$eckBoxs.each(function(){
				expInstIds.push($(this).val()); 
	        });
		}
		OrderExp.orderExpProcess(expInstIds);
	},
	expMarkProcessed:function(expInstIds){
		if(!expInstIds && expInstIds.length < 1){
			MessageBox.show("异常单ID不能为空", 3, 2000);
			return ;
		}
		
		var record_status = $("#eopInner_record_status").val();
		var deal_result = $("#eopInner_deal_result").val();
		if(record_status.length < 1) {
			MessageBox.show("处理状态不能为空", 3, 2000);
			return ;
		}
		if(deal_result.length < 1) {
			MessageBox.show("处理结果不能为空", 3, 2000);
			return ;
		}
		var url = ctx + "/shop/admin/orderExp!expMark.do?ajax=yes";
		$.ajax({  
            type:'post',
            traditional :true,
            url:url,
            dataType:'json',
            data:{'expInstIds':expInstIds,'record_status':record_status,'deal_result':deal_result},  
            success:function(data){
            	alert(data.outer_msg);
            	if(data.outer_status == "0") {
            		Eop.Dialog.close("orderExpMarkProcessedDialog");
//            		Eop.Dialog.close("expMarkProcessedDialog");
            		$("#search_order_exp_from").submit();
            	}
            },
			error:function(){
				alert("o,error.");
			}
        });
	},
	singleExpMarkProcessed:function(expInstId){//单条标记已处理
		if(expInstId != null){
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return ;
		}
		
		var expInstIds = new Array();
		expInstIds.push(expInstId); 
		OrderExp.expMarkProcessed(expInstIds);
	},
	batchExpMarkProcessed:function(){//批量标记已处理
		var $eckBoxs = $("#order_exp_list_fm input[name='eckBox']:checked");
		if($eckBoxs.length < 1){
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return ;
		}
		var expInstIds = new Array();
		$eckBoxs.each(function(){  
			expInstIds.push($(this).val()); 
        });
		OrderExp.expMarkProcessed(expInstIds);
	},getActionUrl:function(order_id,go_url){
		if(go_url == "DEFAULT") {//如果是默认值，则跳转到订单处理页面
			var result = "";
			//获取订单处理页面地址
			var url = ctx+"/shop/admin/ordAuto!getJspPath.do?ajax=yes&order_id="+order_id;
			Cmp.asExcute('',url,{},function callBack(reply){
				result += reply.result;
				if(reply.result==0){
					result+="|"+reply.action_url;
				}else{
					result +="|"+reply.message;
				}
			},'json');
			var resultObj = result.split("|");
			if(resultObj[0]==0){
				go_url = resultObj[1];	
			}else{
				alert("获取链接失败！");
				return false;
			}
			
			//锁单
			var lockStatus = "1";
			var lockUrl = ctx+"/shop/admin/ordAuto!order_lock.do?ajax=yes&order_id="+order_id;
			Cmp.asExcute('',lockUrl,{},function callBack(reply){
				if(reply.result == "0") {
					lockStatus="0";
				}else {
					//alert(reply.message);
				}
			},'json');
			if(go_url != null || go_url.length > 0) {
				if(go_url && go_url.indexOf("?")!=-1){
					go_url += "&order_id="+order_id+"&is_order_exp=1";
				}else{
					go_url += "?order_id="+order_id+"&is_order_exp=1";
				}
			}
		}
		go_url = go_url.replace("{rel_obj_id}", order_id);
		return go_url;
	},
	redirectUrl : function(obj){//解决方案为URL,DEFAULT时，处理方法
		var me = this;
		var order_id = $(obj).attr("rel_obj_id");
		var go_url = $(obj).attr("solution_value");
		var url = me.getActionUrl(order_id,go_url);
		if(url){			
			window.location.href = url;
		}
	}
}