<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="queryForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>终端型号:&nbsp;&nbsp;</th>
					<td nowrap="nowrap">
						<input type="text"  style="width:150px" id="bmmn" name="brandModel.model_name"
						       value="${brandModel.model_name }" class="ipttxt" />				
					</td>
					<th>终端品牌:&nbsp;&nbsp;</th>
					<td >
						<input type="text"  style="width:150px" id="bmbn" name="brandModel.brand_name"
						       value="${brandModel.brand_name }" class="ipttxt" />		
					</td>
					<td><input style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="button" 
						id="searchForm"></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid">
<form method="POST" >
<grid:grid  from="webpage" ajax="yes">

	<grid:header>
		<grid:cell width="10px"><input type="hidden" id="toggleChk" /></grid:cell>
		<grid:cell width="250px">终端型号</grid:cell> 
		<grid:cell width="250px">型号编码</grid:cell> 
		<grid:cell width="250px">终端品牌</grid:cell> 
		<grid:cell width="250px">品牌编码</grid:cell> 
	</grid:header>

  <grid:body item="brandModel">
  		<grid:cell><input type="radio" style="width:50px;" name="code" mname="${brandModel.model_name }" mvalue="${brandModel.model_code }" /></grid:cell>
        <grid:cell>${brandModel.model_name } </grid:cell>
        <grid:cell>${brandModel.model_code } </grid:cell>
        <grid:cell>${brandModel.brand_name } </grid:cell>
        <grid:cell>${brandModel.brand_code } </grid:cell>
  </grid:body>  
</grid:grid>  
<div class="comBtnDiv">
	<a href="javascript:void(0);" style="margin-left:200px;width:70px;" id="selectConfrim" class="graybtn1"><span>确&nbsp;定</span></a>
  </div>
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<script>
$(function(){
	$("#searchForm").bind("click",function(){
		var bm = $.trim($("#bmmn").val());
			bm = encodeURI(encodeURI(bm,true),true);
		var bn = $.trim($("#bmbn").val());
			bn = encodeURI(encodeURI(bn,true),true);
		var url = "brandModel!brandModelListECSDialog.do?ajax=yes&brandModel.model_name="+bm+"&brandModel.brand_name="+bn;
		$("#BrandModelQuickBtn_dialog").load(url,function(){
			Eop.Dialog.open("BrandModelQuickBtn_dialog");
		});
	});
	
	$("#selectConfrim").bind("click",function(){
		if($(".grid").find("tbody").find("input[type='radio']:checked").length>0){
			var obj = $(".grid").find("tbody").find("input[type='radio']:checked");
			$("#goodsBrandModelInputDialog").val($(obj).attr("mname"));
			$("#modelCodeInput").val($(obj).attr("mvalue"));
		}else{
			$("#goodsBrandModelInputDialog").val("");
			$("#modelCodeInput").val("");
		}
		Eop.Dialog.close("BrandModelQuickBtn_dialog");
	});
});
</script>