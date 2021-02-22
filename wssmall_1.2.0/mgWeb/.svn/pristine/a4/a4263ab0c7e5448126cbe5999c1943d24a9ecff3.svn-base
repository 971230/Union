<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="mk_div mtt">
	<div class="mk_title">
		<h2>终端列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="terminal!getTermList.do" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>终端名称:</th>
					<td><input type="text" name="terminal.name"
						id="app_name" value="${terminal.name}" class="searchipt" /></td>
					<th>终端品牌:</th>
					<td>
						<html:selectdict curr_val="${terminal.brand}"  style="width:90px" id="terminal.brand" appen_options="<option value=''>全部</option>" name ="terminal.brand"  attr_code="DC_ITV_BRAND"></html:selectdict>
					</td>
					<th>MAC地址:</th>
					<td><input type="text" name="terminal.mac"
						id="app_name" value="${terminal.mac}" class="searchipt" /></td>
					<td style="text-align:center;"><a href="javascript:void;"
						onclick="document.forms.qryForm.submit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="comBtnDiv">
	<form action="terminal!importTerminal.do" id="batchForm" method="post" enctype="multipart/form-data">
		<a href="terminal!detail.do" onclick="" class="searchBtn"><span>新&nbsp;建</span></a>
		<input type="file" class="ipttxt" name="file" id="uploadFile" /> 
		<a href="javascript:void(0);" onclick="importFile();" class="searchBtn"><span>批量导入</span></a>
		<a href="javascript:void(0);" onclick="exportFile();" class="searchBtn"><span>下载模板</span></a>
	</form>
	</div>

	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>终端名称</grid:cell>
				<grid:cell>终端品牌</grid:cell>
				<grid:cell>终端型号</grid:cell>
				<grid:cell>MAC地址</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.brand }</grid:cell>
				<grid:cell>${obj.model }</grid:cell>
				<grid:cell>${obj.mac }</grid:cell>
				<grid:cell>
					<a href="terminal!detail.do?terminal.id=${obj.id }"
						name="app_change" style="margin-left: 10px">修改</a>
					<a
						href="javascript:void(0);" terminal_id="${obj.id }"
						class="app_delete" style="margin-left: 10px">删除</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>
<div id="terminalImportDiv"></div>
<div id="del_app_div"></div>
<script type="text/javascript">
$(function(){
	$("#app_disable").val($("#hidden_app_disable").val());
	$(".app_delete").bind("click",function(){
		if(!confirm("确定删除此终端？")){
			return;
		}
		var app_id = $(this).attr("terminal_id");
		var url = "terminal!deleteTerminal.do?ajax=yes&terminal.id="+app_id;
		Cmp.ajaxSubmit('del_app_div', '', url, {}, TermList.delJsonBack,'json');
	});
	
})

var TermList = {
		delJsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.reload();
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
	
function importFile() {
	var fileName = document.getElementById("uploadFile").value;debugger
	if (fileName==null||fileName=="") {
		alert("上传文件为空 !");
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var url = $("#batchForm").attr("action");
	if(url.indexOf("?")>=0){
		url = url +"&fileFileName="+fileName;
	}
	else{
		url = url +"?fileFileName="+fileName;
	}
	$("#batchForm").attr("action",url);
	var extPattern = /.+\.(xls|xlsx)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			$("#batchForm").submit();
		} else {
			alert("只能上传EXCEL文件！");
			return;
		}
	}
	$.Loading.show("正在导入终端，请耐心等待...");

}	
	
function exportFile(){
	var url = ctx+ "/servlet/batchAcceptExcelServlet?url=/shop/admin/cms/terminal/terminal_list.jsp&lx=mb&service=terminalLqy";
	var temp_form = "<form id='hidden_export_excel_form' action='"+url+"' method='POST'></form>";
	if($("#hidden_export_excel_form").length>0)
		$("#hidden_export_excel_form").remove();
	$("#terminalImportDiv").append(temp_form);
	$("#hidden_export_excel_form").submit();
}
</script>
