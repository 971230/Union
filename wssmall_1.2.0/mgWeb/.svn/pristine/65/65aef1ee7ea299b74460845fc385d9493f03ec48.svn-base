<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/BrandModelEcs.js"></script>
<div class="input">
	<div class="main-div">
		 <form class="validate" method="post" action="brandModel!saveOrUpdateBrandMode.do" name="editForm" id="editForm"  >
			<input type="hidden" id="brand_model_id" name="brandModel.brand_model_id" value="${brandModel.brand_model_id }" />
			<input type="hidden" name="action" value="${action }" />
		   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		     <tr>
				<th><span class="red">*</span>发布商城：</th>
				<td colspan="3">
					<input type="text" id="brand_name" style="width:150" name="brandModel.brand_name"
					 value="${brandModel.brand_name }" class="ipttxt"  readonly="readonly" required="true"/>
					<input type="hidden" id="brand_code" name="brandModel.brand_code" value="${brandModel.brand_code }" />
					<input type="button" id="select_brand_btn" value="选择">
				</td>		      
			  </tr>
		      <tr>
				<th><span class="red">*</span>型号名称：</th>
				<%-- <td><input type="text" class="ipttxt"  readonly="readonly" maxlength="60"
					value="${names }" dataType="string" required="true" /></td> --%>
					<td colspan="3"><textarea>${names }</textarea></td>
			</tr>

		   </table>
<div class="submitlist" align="center">
 <table>
 <tr>
   <th></th>
   <td >
     <input  type="button" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>		   
		 </form>
	</div>
</div>
<div id="brand_dialog"></div>

<script type="text/javascript">
$(function(){
	var brand_model_id= $("#brand_model_id").val();
	if(null!=brand_model_id&&""!=brand_model_id){
		$("#machine_code").val('${brandModel.machine_code}');
	}
	BrandModelInput.init();
	selectBrand.init();
	$("form.validate").validate();
});

</script>