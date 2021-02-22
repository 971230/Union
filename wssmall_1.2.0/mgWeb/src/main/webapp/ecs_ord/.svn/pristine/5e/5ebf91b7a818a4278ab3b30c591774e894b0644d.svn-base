<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
<form id="editProcFrom" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr style="line-height:20px;">
				<th>进程编码：</th>
				<td>
					<input type="text"  name="code" id="code" value='${crawlerProc.proc_code}' style="width: 148px;" maxlength="60" class="ipttxt" />
				<th>进程名称：</th>
				<td>
				    <input type="text" name="name" id="name" value='${crawlerProc.proc_name}' style="width: 148px;" maxlength="60" class="ipttxt /">
				</td>
			</tr>
			<tr style="line-height:20px;">
				<th>运行机器ip：</th>
				<td>
				    <input type="text" name="ip" id="ip" value='${crawlerProc.ip}' style="width: 148px;" maxlength="60" class="ipttxt /">
				</td>
				<th>运行端口：</th>
				<td>
					<input type="text"  name="port" id="port" value='${crawlerProc.port}' style="width: 148px;" maxlength="60" class="ipttxt" />
				</td>
			</tr>
			<tr style="line-height:20px;">
				<th>线程名称：</th>
				<td>
				    <input type="text" name="thread_name" id="thread_name" value='${crawlerProc.thread_name}' style="width: 148px;" maxlength="60" class="ipttxt" />
				</td>
			</tr>
			<tr style="line-height:20px;">
				<th></th>
				<td rowspan="2" style="margin-right: 500px; text-align: center;">
					<input type="button"  name="save" id="save" value='保存' flag="${flag}" proc_id ="${crawlerProc.proc_id}" class="comBtn" />
				    <input type="button" name="cancel" id="cancel" value='取消' flag="${flag}"  class="comBtn" />
				</td>
			</tr>
		</table>
		<div style="clear: both"></div>
	</div>	
</form>
<script type="text/javascript">

var editProc = { 	
	init : function(){
		editProc.save();
		editProc.cancel();
	},	
	save : function(){
		$("[name='save']").unbind("click").bind("click",function(){
			var id = $(this).attr("proc_id");
			var flag = $(this).attr("flag");
			var code = $("#code").val();
			var name = $("#name").val();
			var ip = $("#ip").val();
			var port = $("input[name='port']").val();
			var thread_name = $("#thread_name").val();
			
			if(code=='' || code==undefined || name=='' || name==undefined || ip=='' || ip==undefined || port=='' || port==undefined || thread_name=='' || thread_name==undefined){
				alert("参数不能为空!");
				return;
			}
			
			var url = ctx+ "/shop/admin/orderCrawlerAction!addProc.do?ajax=yes&flag="+flag;
			$.ajax({
				url : url,
				data : {"crawlerProc.proc_id":id,
						"crawlerProc.proc_code":code,
						"crawlerProc.proc_name":name,
						"crawlerProc.ip":ip,
						"crawlerProc.port":port,
						"crawlerProc.thread_name":thread_name
					},
				type : "post",
				dataType : "json",
				success : function(rsq) {
					if(rsq.result=='0'){
						alert(rsq.message);
						Eop.Dialog.close("adddProc");
						window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
					}else{
						alert(rsq.message);
					}
				},
				error : function() {
				}
			});
			
		});

	},
	cancel : function(){
		$("[name='cancel']").unbind("click").bind("click",function(){
			var flag = $(this).attr("flag");
			if(flag=='add'){
				Eop.Dialog.close("adddProc");
			}else if(flag=='edit'){
				Eop.Dialog.close("procEdit");
			}
		});
	},
}

$(function(){
	editProc.init();
});

</script>