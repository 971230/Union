<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="mk_div mtt">
	<div class="mk_title">
		<h2>终端列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>终端名称:</th>
					<td><input type="text" name="terminal.name"
						id="name_itv" value="${terminal.name}" class="searchipt" /></td>
					<th>终端品牌:</th>
					<td>
						<html:selectdict curr_val="${terminal.brand}"  
							style="width:90px" id="brand_itv" 
							appen_options="<option value=''>全部</option>" 
							name ="terminal.brand"  
							attr_code="DC_ITV_BRAND">
						</html:selectdict>
					</td>
					<th>MAC地址:</th>
					<td><input type="text" name="terminal.mac"
						id="mac_itv" value="${terminal.mac}" class="searchipt" /></td>
					<td style="text-align:center;"><a href="javascript:void;"
						id="searchTermBtnDia" class="searchBtn"><span>搜索</span></a></td>
				</tr>
			</table>
		</form>
	</div>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>终端名称</grid:cell>
				<grid:cell>终端品牌</grid:cell>
				<grid:cell>终端型号</grid:cell>
				<grid:cell>MAC地址</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell><input type="radio" name="id" value="${obj.mac }" /></grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.brand }</grid:cell>
				<grid:cell>${obj.model }</grid:cell>
				<grid:cell>${obj.mac }</grid:cell>
			</grid:body>

		</grid:grid>
		<a href="javascript:void;" id="confirmTermBtnDia" style="margin-left:300px;" class="searchBtn"><span>&nbsp;确&nbsp;定&nbsp;</span></a>
	</form>
</div>

<script type="text/javascript">
$(function(){
	$("#confirmTermBtnDia").bind("click",function(){
		var currObj=$("#selectMacAddress").find("tbody").find("tr").find("input[type='radio']:checked");//each(function(i,data){});
		if(currObj.length>0){
			$("#mac").val(currObj.val());
			Eop.Dialog.close("selectMacAddress");
		}else{
			alert("请选择一条记录！");
		}
	});
	$("#searchTermBtnDia").bind("click",function(){
		var name=$.trim($('#name_itv').val());
		var brand=$.trim($('#brand_itv').val());
		var mac=$.trim($('#mac_itv').val());
		name=encodeURI(encodeURI(name,true),true);
		brand=encodeURI(encodeURI(brand,true),true);
		mac=encodeURI(encodeURI(mac,true),true);
		var url=app_path+"/shop/admin/cms/terminal/terminal!getTermListDialog.do?ajax=yes&terminal.name="+name+"&terminal.brand="+brand+"&terminal.mac="+mac;
		$("#selectMacAddress").load(url,function(){
			Eop.Dialog.open("selectMacAddress");
		});
	});
})
</script>
