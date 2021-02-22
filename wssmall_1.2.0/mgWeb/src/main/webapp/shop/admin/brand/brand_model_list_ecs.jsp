<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript" src="js/BrandModelEcs.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<div >
<form method="post" 
	action='brandModel!brandModelListECS.do' id="queryForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>型号名称:&nbsp;&nbsp;</th>
					<td nowrap="nowrap">
						<input type="text"  style="width:150px" name="brandModel.model_name"
						       value="${brandModel.model_name }" class="ipttxt" />				
					</td>
					<th>终端品牌:&nbsp;&nbsp;</th>
					<td >
						<input type="text"  style="width:150px" name="brandModel.brand_name"
						       value="${brandModel.brand_name }" class="ipttxt" />		
					</td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" 
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid">

	
	<div class="comBtnDiv">
	<a href="brandModel!brandModelDetailECS.do?action=add" style="margin-right:10px;" class="graybtn1" ><span id="addBrand">添加</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="delBtn"><span>删除</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="publishBrandModel"><span>全量发布</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1"  onclick="liberacion()"><span>批量发布</span></a>
    <div class="help_icon" helpid="goodscat"></div>
		<div style="clear:both"></div>
     </div>
	
		
	
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
		<grid:cell width="10px"><input type="checkbox" id="toggleChk" /></grid:cell>
		<grid:cell width="250px">型号名称</grid:cell> 
		<grid:cell width="250px">型号编码</grid:cell> 
		<grid:cell width="250px">终端品牌</grid:cell> 
		<grid:cell width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="brandModel">
  		<grid:cell><input type="checkbox"  name="id" value="${brandModel.brand_model_id }" /></grid:cell>
        <grid:cell><span id="model_name_${brandModel.brand_model_id }">${brandModel.model_name }</span></grid:cell>
        <grid:cell>${brandModel.model_code } </grid:cell>
        <grid:cell>${brandModel.brand_name } </grid:cell>
        <grid:cell> <a  href="brandModel!brandModelDetailECS.do?action=edit&brandModel.brand_model_id=${brandModel.brand_model_id }" ><img class="modify" src="images/transparent.gif" ></a> </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
</div>
<!-- 商品发布 -->
<div id="goods_pub_dialog" ></div>
<script type="text/javascript">
Eop.Dialog.init({id:"goods_pub_dialog",modal:false,title:"商品发布",width:"500px"});
//商品发布
function liberacion(){
	var $checkedBrandModel = $('input[name="id"]:checked');
	if($checkedBrandModel.length<1){
		alert("请选择需要发布的品牌型号!");
		return;
	}
	var ids = [];
	var names = [];
	var id = "";
	$('input[name="id"]:checked').each(function(){
		id = $(this).val();
		ids.push(id);
		names.push($("#model_name_"+id).text());
	});
	var url ="goodsOrg!brandModelPubtree.do?brandModelIds="+ids;
	abrirCajaVentana("goods_pub_dialog",url);
	
}

</script>
