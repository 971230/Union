<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<input  id="addIdx" type="hidden" name="ids" value="${cfg_id}">
<div class="grid" id="selectChoices" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell ><input type="checkbox" id="bankId" ></grid:cell>
		<grid:cell >银行图片</grid:cell>
		<grid:cell >银行编码</grid:cell>
		<grid:cell >银行名称</grid:cell> 
	</grid:header>
  <grid:body item="acc">
     <grid:cell ><input type="checkbox" name="checkBox1" bank_id="${acc.bank_id }" value="${acc.bank_id }" /></grid:cell>
	 <grid:cell ><img src="${pageContext.request.contextPath}${acc.img_url} "> </grid:cell>
     <grid:cell >${acc.bank_code } </grid:cell> 
     <grid:cell >${acc.bank_name } </grid:cell> 
  </grid:body>   
</grid:grid>  
<div class="submitlist" align="center">
	 <table>
	 <tr>
	
	 <td style="text-align: left;">
           <input  type="button"  value=" 确定" class="submitBtn" id="selectOkBotten"  />
	 </td>
	 </tr>
	 </table>
</div>
</form>
</div>
<script type="text/javascript" >
$(function (){

	//$("input[name=checkBox1]").click(function(){
		
        $("#selectOkBotten").click(function() {
        	var cfg_id=$("#addIds").val();
			var bank_idList=$("input[type=checkbox][name=checkBox1]:checked");
			var ids = "";
			if(!bank_idList || bank_idList.length==0){
				alert("请选择银行");
				return ;
			}
			$.each(bank_idList,function(idx,item){
				if(idx==0){
					ids += $(item).attr("value");
				}else{
					ids += ","+$(item).attr("value");
				}
			});
			var url = ctx+ "/shop/admin/creditAction!addBanks.do?ajax=yes&cfg_id="+cfg_id+"&bank_ids="+ids;
			$.ajax({
				url:url,
				dataType:"json",
				success:function(reply){
					if(reply.result==1){
		                  alert("操作成功!");
					      var url ="creditAction!bankList.do?ajax=yes&id="+cfg_id; 
						  $("#adddBankListsss").load(url,function(){
					      }); 
		                 Eop.Dialog.close("addBankDialogssss");
		             }
		             if(reply.result==0){
		                 alert(reply.message);
		             }  
				}
			});
		});
	//});
		
  });
     

</script>

