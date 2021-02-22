<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<form  class="validate" validate="true" method="post" action="" id='addParStaffform'  >
<table cellspacing="1" cellpadding="3" width="100%" style="height: 400px;width:300" class="form-table">
    <tr>
     <input type="hidden" name="goods_id" value="${goods_id}">
    <input type="hidden" name="editGoodsId" value="${editGoodsId}">
    <input type="hidden" name="attr_spec_id" value="${attr_spec_id}">
    <input type="hidden" name="field_attr_id" value="${field_attr_id}">
   <%--   <input type="hidden" name="attr_spec_type" value="${attr_spec_type}"> --%>
    <tr>
        <th><label class="text"><span class='red'>* </span>属性业务类型：</label></th>
        <td>
             <select  class="ipttxt" name="attr_spec_type" style="width: 154px;height: 25px">
			    <option value="" >-----请选择----</option>
				<option value="accept">受理</option>
				<option value="pay">支付</option>
				<option value="delivery">发货</option>
				<option value="insure" >
						确认
					</option>
			</select>
		</td>
    </tr>
   
    <tr>
        <th><label class="text"><span class='red'>* </span>属性英文名：</label></th>
        <td>
            <input type="text" class=" ipttxt" id="field_name" name="field_name" value="${field_name}" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>属性中文名：</label></th>
        <td>
            <input type="text" class="ipttxt" id="field_desc" name="field_desc" value="${field_desc }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>属&nbsp;性&nbsp;类&nbsp;型：</label></th>
       <td>
		 <html:selectdict name="field_type"  curr_val="${field_type}" attr_code="DC_FILED_NAME"></html:selectdict>
	  <td>
    </tr> 
    <tr>
        <th><label class="text"><span class='red'>* </span>ATTR_CODE：</label></th>
        <td>
            <input type="text" class=" ipttxt" id="attr_code" name="attr_code" value="${attr_code}" required="true" autocomplete="on" dataType="string">
        </td>
    </tr> 
    <tr>
        <th><label class="text"><span class='red'>* </span>缺&nbsp;&nbsp;省&nbsp;&nbsp;值：</label></th>
        <td>
            <input type="text" class="ipttxt" id="default_value" name="default_value" value="${default_value }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th ><label class="text"><span class='red'>* </span>缺省值描述：</label></th>
        <td>
            <input type="text" class="ipttxt" id="default_value_desc" name="default_value_desc" value="${default_value_desc }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
     <tr>
        <th><label class="text"><span class='red'>* </span>是否关联表：</label></th>
        <td>
             <select id="sub_attr_spec_type" class="ipttxt" name="owner_table_fields" value="${owner_table_fields }"style="width: 154px;height: 25px">
			    <option value="" >------请选择----</option>
				<option value="yes">是</option>
				<option value="no">否</option>
			</select>
		</td>
    </tr>
    <tr id="relTableTr">
        <th><label class="text"><span class='red'>* </span>关&nbsp;联&nbsp;表&nbsp;名：</label></th>
        <td>
            <input type="text" class="ipttxt"  id="rel_table_name" name="rel_table_name" value="${rel_table_name }" required="true"  autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>映射字段名：</label></th>
        <td>
            <input type="text" class="ipttxt" name="o_field_name" id="o_field_name" value="${o_field_name }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
</table>
<div class="submitlist" align="center">
	 <table>
		 <tr>
		 <th style="width:55px;"></th>
		 	<td>
	           <input  type="button"  id="butSave" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
 </form>
 </div>

<script type="text/javascript" >
$(function (){
	  $("#addParStaffform").validate();
      $("#butSave").click(function() {
    	 
			var url = ctx+ "/shop/admin/goodsPropertyAction!addProperty.do?ajax=yes";
			Cmp.ajaxSubmit('addParStaffform', '', url, {}, function(reply){
			 if(reply.result==0){
               alert(reply.message);
             }  
             if(reply.result==1){
               alert("操作成功!");
               
               var goods_id=$("input[name='goods_id_e']").val();
               var url = ctx+"/shop/admin/goodsPropertyAction!List.do?ajax=yes&goods_id="+goods_id;
               $("#contractNodes").empty().load(url,function(){
            	   
            	   $("a[name='edit']").live("click",function(){
            	    	var sid = $(this).attr("attr_spec_id");
            	   		var id = $(this).attr("field_attr_id");
            	   		serviceAppss.edit(sid,id);
            	    });
            	    $("a[name='del']").live("click",function(){
            	       var field_attr_id = $(this).attr("field_attr_id");
            	       serviceAppss.del(field_attr_id);
            	    });
               });
               
               Eop.Dialog.close("goodsProperty");
               
             }
			 },'json');
		});
		$("#sub_attr_spec_type").bind("change",function(){
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

