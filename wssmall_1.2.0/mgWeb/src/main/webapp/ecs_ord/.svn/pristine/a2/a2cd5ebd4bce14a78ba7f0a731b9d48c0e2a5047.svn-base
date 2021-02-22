var ActivityList = {
	init:function(){
		Eop.Dialog.init({id:"importExcelDlg",modal:false,title:"导入报表",height:"300px",width:"700px"});


		Eop.Dialog.init({id:"manualAuditDlg",modal:false,title:"订单人工稽核",width:"1100px"});

		/*Eop.Dialog.init({id:"delExcelDlg",modal:false,title:"导入报表",height:"300px",width:"700px"});*/

		Eop.Dialog.init({id:"delExcelDlg",modal:false,title:"删除报表",height:"200px",width:"700px"});


		var self  = this;
		$(".importExcel").click(function(){
			self.openAddDlg();
		});
		$(".delExcle").click(function(){
			self.openDelDlg();
		});
		
		$("#searchFormSubmit").bind("click",function(){
		if (check()) {
			$("#queryListForm").attr('action','moneyAuditAction!queryList.do');
			$("#queryListForm").submit();
			}
		});
		$(".export_busi_excle").click(function(){
			if (check()) {
			$("#queryListForm").attr('action','moneyAuditAction!queryList.do?excel=busi');
			$("#queryListForm").submit();
			}
		});
		$(".export_receive_excle").click(function(){
			if (check()) {
			$("#queryListForm").attr('action','moneyAuditAction!queryList.do?excel=receive');
			$("#queryListForm").submit();
			}
		});
		
   },
    /**
	 * 打开导入excel对话框
	 */
	openAddDlg:function(){
		var self  = this;
		$("#importExcelDlg").load("moneyAuditAction!toImportExcel.do?ajax=yes",function(){
			/*$("#saveAddBtn").click(function(){
				self.add();
			});*/
		});
		Eop.Dialog.open("importExcelDlg");
	}, /**
	 * 打开删除excel对话框
	 */
	openDelDlg:function(){
		var self  = this;
		$("#delExcelDlg").load("moneyAuditAction!toDelExcel.do?ajax=yes",function(){
			/*$("#saveAddBtn").click(function(){
				self.add();
			});*/
		});
		Eop.Dialog.open("delExcelDlg");
	},
	
}

//验证日期
function check() {

	var create_start = $("input[name=auditQueryParame.start_time]");
	var create_end = $("input[name=auditQueryParame.end_time]");
	
	if ($.trim(create_start.val())=="" || $.trim(create_end.val())=="") {
		alert("请选择时间,并且范围小于等于31天!");
		
		if($.trim(create_start.val())=="" && $.trim(create_end.val())==""){
			$("input[name=auditQueryParame.start_time]").focus();
		}else if ($.trim(create_start.val())==""){
			$("input[name=auditQueryParame.start_time]").focus();
		}else if($.trim(create_end.val())==""){
			$("input[name=auditQueryParame.end_time]").focus();
		}
		return false;
	}
	
	var startTime = new Date(create_start.val().replace(/-/g, "/"));
 	var endTime = new Date(create_end.val().replace(/-/g, "/"));
	var days = endTime.getTime() - startTime.getTime();
 	var time = parseInt(days / (1000 * 60 * 60 * 24));
 	if (time < 0 || time > 30) {
		alert("时间范围必须小于等于31天!");
		return false;
 	}
 	
	return true;
}


function manualAudit(e){
	var order_id  = e.getAttribute("order_id");
	$("#manualAuditDlg").load("moneyAuditAction!manualAudit.do?ajax=yes&order_id="+order_id,function(){
		/*$("#saveAddBtn").click(function(){
			self.add();
		});*/
	});
	Eop.Dialog.open("manualAuditDlg");
}
function openOrderDetails(order_id){

	
	 var o_url="/shop/admin/orderFlowAction!order_detail_view.do?order_id="+order_id+"&query_type=order_view";
	var url = ctx+o_url;
	window.open(url,"newwindow","height=500,width=1100, top=115, left=210, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
}





$(function(){
	 ActivityList.init();
});