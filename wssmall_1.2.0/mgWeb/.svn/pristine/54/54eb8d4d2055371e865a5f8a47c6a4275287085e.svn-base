 <%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<div class="input">

<div  id="brandInfo">
	<form method="post" action="brand!save.do" class="validate" id="brandForm" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			<tr>
				<th><label class="text">品牌名称:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.name" id="name" maxlength="60"
					value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_code" id="brand_code" maxlength="60"
					value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">外部品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_outer_code" id="brand_outer_code" maxlength="60"
					value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">总部品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_group_code" id="brand_group_code" maxlength="60"
					value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">品牌网址:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.url" id="url" maxlength="60"
					size="40" value="" /> 以http://开头</td>
			</tr>
			<tr>
				<th><label class="text">品牌LOGO:</label></th>
				<td><input type="file" name="logo" id="logo" size="45" /> <span
					class="notice-span" id="warn_brandlogo">请上传图片，做为品牌的LOGO</span></td>
			</tr>
			<tr>
				<th><label class="text">机型:</label></th>
				<td><a class="back"><span id="search_machine">查看</span></a></td>
			</tr>
			<tr>
				<th><label class="text">详细说明:</label></th>
				<td><textarea id="brief" name="brand.brief"></textarea></td>
			</tr>
		  <input type="hidden" value="${type_id}"  name="p_type_id" id="p_type_id">
		  <input type="hidden" value=${goodsAddBindType} name="goodsAddBindType">
		</table>
		<div class="submitlist" align="center">
		<table>
			<tr>
			   <th></th>
				<td><input id="add_brand_btn" type="button" value=" 确    定   " class="submitBtn" />
				 
				    <input type="hidden" value="${selfProdType }" id="add_goods_type">  
				  
				</td>
			</tr>
		</table>
		</div>
		
	</form>
</div>
</div>
<!-- 机型选择对话框 -->
<div id="brand_model_dialog"></div>
<script type="text/javascript">
	$(function() {
	    $("#url").bind("blur",function(){
	   var urlVal = $(this).val().substring(0,7);
	    if(urlVal!="http://"){
		   alert("品牌网址格式不正确");
		    return false;
		  }
	   });
		$("form.validate").validate();
		var type_id = '${type_id}';
		!type_id && (BrandInput.init());
		var p_type_id = $("#p_type_id").val();
		 if(p_type_id.length==0||p_type_id=='undefined'){
		  $('#brief').ckeditor();
		}
		$("#return_addGoods").bind("click",function(){
		  history.back();
		  window.reload();
		});
	});
</script>