<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
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
  
</style>

<body>
	<div class="searchformDiv" style="background: white;border: 0px;" >
		<table>
			
			<tr>
				<th>连接条件：</th>
				<td>
					<input id="connect_label" type="text" class="ipttxt" style="width: 250px;"/>
				</td>
			</tr>
			
			<tr>
				<th></th>
				<td>
			    	<input class="comBtn" type="button" onclick="confirmLabelEdit();" value="确定" style="margin-left:30px;"/>
			    	<input class="comBtn" type="button" onclick="deleteConnect();" value="删除" style="margin-left:10px;"/>
				</td>
			</tr>
		</table>
	</div>
</body>

<script type="text/javascript">
$(function(){
	
	var label = WorkFlowTool.canvas.curr_connect.getOverlay("label").getLabel();
	
	$("#connect_label").val(label);
});	

function confirmLabelEdit(){
	var label = $("#connect_label").val();
	
	WorkFlowTool.canvas.setCurrConnectLabel(label);
	
	Eop.Dialog.close("labelEditDiag");
}

function deleteConnect(){
	WorkFlowTool.canvas.deleteCurrConnect();
	
	Eop.Dialog.close("labelEditDiag");
}


</script>

