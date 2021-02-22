<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<form  class="validate" validate="true" method="post" action="" id='addParStaffformss' name="form1" >
<table cellspacing="1" cellpadding="3" width="100%" style="height: 400px;width:300" class="form-table" id="taable_bane">
    <tr>
     <input type="hidden" name="goods_id" value="${goodsid}">
    <input type="hidden" name="editGoodsId" value="${editGoodsId}">
    <input type="hidden" name="attr_spec_id" value="${attr_spec_id}">
    <input type="hidden" name="field_attr_id" value="${field_attr_id}">
     <input type="hidden" name="attr_spec_type" value="${attr_spec_type}">
    <tr>
        <th><label class="text"><span class='red'>* </span>属性业务类型：</label></th>
        <td>
             <select id="sub_attr_spec_type" class="ipttxt" sub_attr_spec_type="sub_attr_spec_type" name="sub_attr_spec_type" value="${sub_attr_spec_type }" style="width: 154px;height: 25px">
			    
				<option value="accept ">受理</option>
				<option value="pay">支付</option>
				<option value="delivery">发货</option>
				<option value="insure">确认</option>
			</select>
		</td>
    </tr>
   
    <tr>
        <th><label class="text"><span class='red'>* </span>属性英文名：</label></th>
        <td id="fidles" >
            <input type="text" class=" ipttxt" id="field_name" field_name="field_name" name="field_name" value="${field_name}" required="true" >
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>属性中文名：</label></th>
        <td id="chinese">
            <input type="text" class="ipttxt" id="field_desc" field_desc="field_desc" name="field_desc" value="${field_desc }" required="true" >
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>属&nbsp;性&nbsp;类&nbsp;型：</label></th>
        <td>
		 <html:selectdict name="field_type"  curr_val="${field_type}" attr_code="DC_FILED_NAME" ></html:selectdict>
	  <td>
    </tr> 
    <tr>
        <th><label class="text"><span class='red'>* </span>ATTR_CODE：</label></th>
        <td id="attr_codes">
            <input type="text" class=" ipttxt" id="attr_code" name="attr_code" attr_code="attr_code" value="${attr_code}" required="true" >
        </td>
    </tr> 
    <tr>
        <th><label class="text"><span class='red'>* </span>缺&nbsp;&nbsp;省&nbsp;&nbsp;值：</label></th>
        <td id="value">
            <input type="text" class="ipttxt" id="default_value" default_value="default_value" name="default_value" value="${default_value }" required="true" >
        </td>
    </tr>
    <tr>
        <th ><label class="text"><span class='red'>* </span>缺省值描述：</label></th>
        <td id="tezts">
            <input type="text" class="ipttxt" default_value_desc="default_value_desc" id="default_value_desc" name="default_value_desc" value="${default_value_desc }" required="true" >
        </td>
    </tr>
     <tr>
        <th><label class="text"><span class='red'>* </span>是否关联表：</label></th>
        <td>
             <select id="sub_attr_spec_type" class="ipttxt" name="owner_table_fields" value="${owner_table_fields }" style="width: 154px;height: 25px">
			    <option value="" >------请选择----</option>
				<option value="yes">是</option>
				<option value="no">否</option>
			</select>
		</td>
    </tr>
    <tr id="relTableTr">
        <th><label class="text"><span class='red'>* </span>关&nbsp;联&nbsp;表&nbsp;名：</label></th>
        <td id="table_name">
            <input type="text" class="ipttxt" rel_table_name="rel_table_name" id="rel_table_name" name="rel_table_name" value="${rel_table_name }" required="true" >
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>映射字段名：</label></th>
        <td id="field_name">
            <input type="text" class="ipttxt" name="o_field_name" o_field_name="o_field_name" id="o_field_name" value="${o_field_name }" required="true" >
        </td>
    </tr>
</table>
<div class="submitlist" align="center">
	 <table>
		 <tr>
		 <th style="width:55px;"></th>
		 	<td>
	           <input  type="button"  id="butSaveAdd" value=" 确定 " class="submitBtn" name='submitBtn' onclick="CheckForm();"/>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
 </form>
 </div>
<script type="text/javascript" >




var addGood= {
		//$("#addParStaffformss").validate();
        init:function(){
            var me=this;
            $("#butSaveAdd").unbind("click").bind("click",function() {
		      me. addSelectGoods();
	        });
        },
        addSelectGoods:function(){
        	
        	var attrId=$("input[name=attr_spec_id]").val();
			var spec_type=$("select[name=sub_attr_spec_type]").val();
			var name=$("input[name=field_name]").val();
			var desc=$("input[name=field_desc]").val();
			var type=$("select[name=field_type]").val();
			var code=$("input[name=attr_code]").val();
			var value=$("input[name=default_value]").val();
			var value_desc=$("input[name=default_value_desc]").val();
			var table_fields=$("select[name=owner_table_fields]").val();
			var table_name=$("input[name=rel_table_name]").val();
			var field_names=$("input[name=o_field_name]").val();
	        var tr= "<tr>";
	        tr += "<input type='hidden' name='hasselect' value='off'>";
	        tr += "<input type='hidden' name='attrId' value='"+attrId+"'>";
	        tr += "<input type='hidden' name='spec_type' value='"+spec_type+"'>";
	        tr += "<input type='hidden' name='name' value='"+name+"'>";
	        tr += "<input type='hidden' name='desc' value='"+desc+"'>";
	        tr += "<input type='hidden' name='type' value='"+type+"'>";
	        tr += "<input type='hidden' name='code' value='"+code+"'>";
	        tr += "<input type='hidden' name='value' value='"+value+"'>";
	        tr += "<input type='hidden' name='value_desc' value='"+value_desc+"'>";
	        tr += "<input type='hidden' name='table_fields' value='"+table_fields+"'>";
	        tr += "<input type='hidden' name='table_name' value='"+table_name+"'>";
	        tr += "<input type='hidden' name='field_names' value='"+field_names+"'>";
	        
	        tr += "<td><input type='checkbox' name='select' onclick='selectedChanss(this)' num='"+num+"'  /></td>";
	        tr += "<td>"+spec_type+"</td>";
        	tr += "<td>"+name+"</td>";
       		tr += "<td>"+code+"</td>";
  			tr += "<td>"+table_name+"</td>";
			tr += "<td>"+field_names+"</td>";
			tr += "<td><a href='javascript:void(0);' id='editProperty'>修改</a></td>";
			tr += "<td><a href='javascript:void(0);' name='deletec'>删除</a></td>";
            tr += "</tr>";
            $("#table_module1").append(tr);
            num++;
            Eop.Dialog.close("addGoodsProperties");
    },
  
}
addGood.init();     
$("#sub_attr_spec_type option").bind("click",function(){
      var attr_spec_type = $(this).val();
		 if(attr_spec_type=='yes'){
			 if(document.form1.rel_table_name.value.length   ==   0)   {  
		      		alert("请输入请输入关联表名!");
		      		document.form1.rel_table_name.focus();
		      		return   false;
		      	 }
          $("#relTableTr").show();
         
        }
          if(attr_spec_type=='no'){
           $("#relTableTr").hide();
        }
});
</script>

