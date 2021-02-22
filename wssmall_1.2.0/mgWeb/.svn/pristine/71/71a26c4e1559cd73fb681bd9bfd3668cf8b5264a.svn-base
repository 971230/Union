<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <div class="table_list" id="listGoods" style="overflow-Y: auto;height: 470px;">
<input id="addIds" type="hidden" value="${id }">
<form  id="form_tc" class="grid">
<div class="searchformDiv" >
   <a href="javascript:;" style="margin-right:10px;" cfg_id="${cfg_id }" name="addBankss" class="graybtn1" >新增</a>	 
</div> 
<div style="overflow: auto;">
	<table >
		<tr>
			<td>银行图片</td>
			<td>银行编码</td>
			<td>银行名称</td>
			<td>操作</td>
		</tr>
		<c:forEach var="acc" items="${banks }">
		<tr>
			<td><img src="<%=request.getContextPath() %>${acc.img_url} "></td>
			<td>${acc.bank_code }</td>
			<td>${acc.bank_name }</td>
			<td><a href="javascript:;"  name="addBtn3"  cfg_id="${acc.cfg_id }"  class="delBanksment" value="${acc.bank_id}">删除</a>	</td>
		</tr>
		</c:forEach>
	</table>
	</div>
</form> 
</div> 	   
<%-- 
<div class="searchformDiv">
   <a href="javascript:;" style="margin-right:10px;" cfg_id="${cfg_id }" name="addBankss" class="graybtn1" >新增</a>	 
</div> 
<input id="addIds" type="hidden" value="${id }">
<div class="grid" id="selectChoices" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
		<grid:cell >银行图片</grid:cell>
		<grid:cell >银行编码</grid:cell>
		<grid:cell >银行名称</grid:cell> 
		<grid:cell width="180px">操作 </grid:cell>
	</grid:header>
  <grid:body item="obj">
	 <grid:cell ><img src="${obj.img_url} "> </grid:cell>
     <grid:cell >${obj.bank_code }</grid:cell> 
     <grid:cell >${obj.bank_name } </grid:cell> 
     <grid:cell>  
		   <a href="javascript:;"  name="addBtn3"  cfg_id="${acc.cfg_id }"  class="delBanksment" value="${acc.bank_id}">删除</a>
	</grid:cell>
  </grid:body>   
</grid:grid>  
</div>
</form>
	    --%>
<div class="submitlist" align="center" style="display: none;">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定" class="submitBtn" id="selectOkBot1" />
	 </td>
	 </tr>
	 </table>
</div>
<div title="新增银行" id ="addBankDialogssss"></div> 
<script type="text/javascript">
var showAccount = {
	    init:function(){
	    var self = this;
	    Eop.Dialog.init({id:"addBankDialogssss",modal:true,title:"新增银行",height:"100px",width:"800px"});
	 	//删除
	      $(".delBanksment").bind("click",function(){
	          var bank_id = $(this).attr("value");
	          var cfg_id = $(this).attr("cfg_id");
	          self.delBank(bank_id,cfg_id);
	      });
	      $("a[name=addBankss]").unbind("click").bind("click",function() {
	    	   var cfg_id=$("#addIds").val();
	    	   
	           self.showAddBankDialogs(cfg_id);
	      }); 
	      },
	      //弹出新增页面
		   showAddBankDialogs : function(cfg_id) {
			     Eop.Dialog.open("addBankDialogssss");
			     var url ="creditAction!addBan.do?ajax=yes&cfg_id="+cfg_id; 
			     $("#addBankDialogssss").load(url,function(){
		   });
		  }, 
	     delBank:function(bank_id,cfg_id){
	         var self = this;
	         if(!confirm("是否确定要删除?")){
	            return ;
	         }  
	    	   url ="creditAction!delBanks.do?ajax=yes&bank_id="+bank_id+"&cfg_id="+cfg_id;    	   
	           Cmp.excute('', url, {}, self.delJsonBack, 'json');
	    },
	     delJsonBack:function(reply){
			    if(reply.result==0){
			      alert(reply.message);
			    }  
			    if(reply.result==1){
			      alert("操作成功!");
			      var id=$("#addIds").val();
			      var url ="creditAction!bankList.do?ajax=yes&id="+id; 
				  $("#adddBankListsss").load(url,function(){
			      }); 
				  
			    }
		},
	}; 
	$(function(){
		showAccount.init();
	});
	
	
</script>

