<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
 <form class="validate" id="addForm">
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
      <tr> 
       <th><label class="text">属性编码：</label></th>
    	 <td><input type="text" id="cname" value="${cname}" name="cname" dataType="string" class="resigterIpt" autocomplete="on" required="true" ></input> </td>
    	  </tr>
      <tr> 
       <th><label class="text">属性名称：</label></th>
     	<td><input type="text" id="ename" value="${ename }" name="ename" dataType="string" class="resigterIpt" autocomplete="on" required="true" ></input></td>
     	</tr>
      <tr> 
       <th><label class="text">属性值：</label></th>
      	<td><input type="text" id="value" value="${value}" name="value" dataType="string" class="resigterIpt" autocomplete="on" required="true" ></input></td>
       </tr>
   </table>
	<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td >
          <input  type="button"  value=" 确定 " class="submitBtn" id="saveAddBtn"/>
	  </td>
	 </tr>
	 </table>
	</div>   
   </form>
   </div>
<script type="text/javascript" >
var addProper= {
        init:function(){
            var me=this;
            $("#saveAddBtn").unbind("click").bind("click",function() {
		      me.addP();
	        });
        },
        addP:function(){
			var cname=$("input[name=cname]").val();
			var ename=$("input[name=ename]").val();
			var value=$("input[name=value]").val();
			
	        var tr= "<tr>";
	       // tr += "<input type='hidden' name='hasselect' value='off'>";
	        tr += "<input type='text' name='cname' value='"+cname+"'>";
	        tr += "<input type='text' name='ename' value='"+ename+"'>";
	        tr += "<input type='text' name='value' value='"+value+"'>";
			tr += "<td><a href='' name='delete'>删除</a></td>";
            tr += "</tr>";
            $("#table_property").append(tr);
            Eop.Dialog.close("addPropertyDialogs");
    },
  
}
addProper.init();  
</script>
