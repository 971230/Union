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
				<th><span class="red">*</span>终端品牌：</th>
				<td colspan="3">
					<input type="text" id="brand_name" style="width:150" name="brandModel.brand_name"
					 value="${brandModel.brand_name }" class="ipttxt"  readonly="readonly" required="true"/>
					<input type="hidden" id="brand_code" name="brandModel.brand_code" value="${brandModel.brand_code }" />
					<input type="button" id="select_brand_btn" value="选择">
				</td>		      
			  </tr>
		      <tr>
				<th><span class="red">*</span>型号名称：</th>
				<td><input type="text" class="ipttxt"  name="brandModel.model_name" id="model_name" maxlength="60"
					value="${brandModel.model_name }" dataType="string" required="true" fun="checkModelName" /></td>
			</tr>
			   <tr>
				<th><span class="red">*</span>型号编码：</th>
				<td><input type="text" class="ipttxt"  name="brandModel.model_code" id="model_code" maxlength="60"
					value="${brandModel.model_code }" dataType="string" required="true" /></td>
			</tr>
			<tr id="isCardTr">
				<th><span class="red"></span>是否卡类：</th>
				<td>
					<input type="radio" name="is_card_type" value="1" onclick="showOrHideCardType()" checked="checked">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="is_card_type" value="0"  onclick="showOrHideCardType()">否
				</td>
			</tr>
			<tr id="machineCodeTr">
				<th><span class="red">*</span>卡类型：</th>
				<td>
					<select id="machine_code" name="brandModel.machine_code" style="width: 150px;">
						<option selected="selected" value=""  class="choice">---请选择---</option>
						<option value="5869080000020140918001398">nano卡 </option>
						<option value="5869080000020140918001435">小卡 </option>
						<option value="5869080000020140918001397">大卡 </option>
					</select>
				</td>
			</tr>

		   </table>
<div class="submitlist" align="center">
 <table>
 <tr>
   <th></th>
   <td >
     <input  type="button" value=" 返    回   " class="submitBtn"  id="backBtn"/>
     <input  type="button" value=" 确    定   " class="submitBtn"  id="submitBtn"/>
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
	var code='${brandModel.machine_code}';
	if(null!=brand_model_id&&""!=brand_model_id){
		$("#isCardTr").hide();
		if(null!=code&&""!=code){
			$("#machine_code").val(code);
		}else{
			$("#machineCodeTr").hide();
		}
	}
	BrandModelInput.init();
	selectBrand.init();
	$("form.validate").validate();
	
});

function showOrHideCardType(){
	var val=$("[name='is_card_type']").filter(":checked").val();
	if("1"==val){
		$("#machineCodeTr").fadeIn();
	}else{
		$("#machineCodeTr").fadeOut();
		$("select :first-child").attr("selected","selected");
	}
}

</script>