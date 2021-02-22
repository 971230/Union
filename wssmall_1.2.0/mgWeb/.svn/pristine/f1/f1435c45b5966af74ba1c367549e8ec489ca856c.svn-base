<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<form   method="post" action="" id='editForm' class="validate" validate="true" >
<table cellspacing="1" cellpadding="3" width="100%" style="height: 400px;width:300" class="form-table">
    <tr>
    <input type="hidden" name="goodsAttrDef.attr_spec_id" value="${goodsAttrDef.attr_spec_id}">
    <input type="hidden" name="goodsAttrDef.field_attr_id" value="${goodsAttrDef.field_attr_id}">
    <tr>
        <th><label class="text"><span class='red'>* </span>属性归属类型：</label></th>
        <td>
             <select id="sub_attr_spec_types" class="ipttxt" required="true" name="goodsAttrDef.sub_attr_spec_type" value="${goodsAttrDef.sub_attr_spec_type }"style="width: 154px;height: 25px">
					<option value="accept" >
						受理
					</option>
					<option value="pay" >
						支付
					</option>
					<option value="delivery" >
						发货
					</option>
					<option value="insure" >
						确认
					</option>
			</select>
		</td>
    </tr>
  
    <tr>
        <th><label class="text"><span class='red'>* </span>属性英文名：</label></th>
        <td>
            <input type="text" class=" ipttxt" id="field_name" name="goodsAttrDef.field_name" value="${goodsAttrDef.field_name}" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>属性中文名：</label></th>
        <td>
            <input type="text" class="ipttxt" id="field_desc" name="goodsAttrDef.field_desc" value="${goodsAttrDef.field_desc }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>ATTR_CODE：</label></th>
        <td>
		 <html:selectdict name="field_type"  curr_val="${field_type}" attr_code="DC_FILED_NAME" style="min-width:156px;"></html:selectdict>
	  <td>
    </tr>
    <tr>
        <th><label class="text"></span>缺&nbsp;&nbsp;省&nbsp;&nbsp;值：</label></th>
        <td>
            <input type="text" class="ipttxt" id="default_value" name="goodsAttrDef.default_value" value="${goodsAttrDef.default_value }" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th ><label class="text"></span>缺省值描述：</label></th>
        <td>
            <input type="text" class="ipttxt" id="default_value_desc" name="goodsAttrDef.default_value_desc" value="${goodsAttrDef.default_value_desc }" autocomplete="on" dataType="string">
        </td>
    </tr>
     <tr>
        <th><label class="text"><span class='red'>* </span>是否关联表：</label></th>
		       <td>
		      	  <select id="sub_attr_spec_type" class="ipttxt" name="owner_table_fields"  style="width: 154px;height: 25px" required="true" autocomplete="on" dataType="string">
			   	   <option value="yes" >
						是
					</option>
					<option value="no" >
						否
					</option>
		          </select>
		       </td>  
		    </tr>
			   
					
			</select>
		</td>
    </tr>
    <tr id ="relTableTr">
        <th><label class="text">关&nbsp;联&nbsp;表&nbsp;名：</label></th>
        <td>
            <input type="text" class="ipttxt"  id="rel_table_name" name="goodsAttrDef.rel_table_name" value="${goodsAttrDef.rel_table_name }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>映射字段名：</label></th>
        <td>
            <input type="text" class="ipttxt" name="goodsAttrDef.o_field_name" id="o_field_name" value="${goodsAttrDef.o_field_name }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
</table>
<div class="submitlist" align="center">
	 <table>
		 <tr>
		 <th style="width:55px;"></th>
		 	<td>
	           <input  type="button"  id="butSaves" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
 </form>
 </div>
<script type="text/javascript">
$(function(){
    var attr_spec_type = $("#sub_attr_spec_type").find("option:selected").val();
      if(attr_spec_type=='no'){
       $("#relTableTr").hide();
      }
       $("#editForm").validate();
       $("#sub_attr_spec_type option").bind("click",function(){
              var attr_spec_type = $(this).val();
			  if(attr_spec_type=='yes'){
                 $("#relTableTr").show();
               }
                 if(attr_spec_type=='no'){
                  $("#relTableTr").hide();
               }
       });
           
			
});

      

</script>

