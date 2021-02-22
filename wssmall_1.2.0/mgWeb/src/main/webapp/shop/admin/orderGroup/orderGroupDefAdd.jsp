<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>


<div class="input">
 <form class="validate" method="post" name="addOrderGroupForm" id="addOrderGroupForm" enctype="multipart/form-data">
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table" align="center">
   
     <tr >
       <th  class="label"><label class="text" >分组名：</label></th>
       <td><input type="text" class="ipttxt"  name="orderGroupDef.group_name" maxlength="60" value=""  dataType="string" required="true" />       </td>
      </tr>
      
      <tr>
       <th><label class="text">分组编码：</label></th>
       <td  ><input type="text"  class="ipttxt"  name="orderGroupDef.group_code" maxlength="60" value=""  dataType="String" required="true" class="dateinput"/></td>
      </tr>
      <tr>
       <th><label class="text">分组类型：</label></th>
       <td>
       <html:selectdict curr_val="${orderGroupDef.group_type}"  id ="group_type" name="orderGroupDef.group_type" attr_code="DC_GROUP_TYPE"></html:selectdict>
       </td>
      </tr>
   
 
   </table>
   <div class="submitlist" align="left">
 <table>
 <tr>
  <th></th>
    <td >
      <input  type="button" id ="orderGroupAddSaveBtn" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
 </form>

</div>

<script type="text/javascript">
 $(function(){
    $("#orderGroupAddSaveBtn").click(function(){
        var group_type = $("#group_type").val();
        if(group_type=='0'){
         alert("请选择分组类型");
         return false;
        }
    });
 });
</script>