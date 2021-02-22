<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <div class="searchformDiv">
   <a href="javascript:;" style="margin-right:10px;" name="addAccountss" class="graybtn1" >新增</a>	 
</div> 
<input id="idxx" type="hidden" value="${cfg_id}">
 <div class="grid" id="selectChoices" >	
 <form id="gridform" class="grid" ajax="yes">
	<table >
		<tr>
			<td>银行帐号</td>
			<td>银行key</td>
			<td>帐号归属用户</td>
			<td>操作</td>
		</tr>
		<c:forEach var="acc" items="${account}">
		<tr>
			<td>${acc.accounted_code}</td>
			<td>${acc.account_key }</td>
			<td>${acc.owner_userid }</td>
			<td><a href="javascript:;"  name="addBtn3"　cfgid="${acc.cfg_id }" class="delPayment" value="${acc.accounted_code}">删除</a>	</td>
		</tr>
		</c:forEach>
	</table>
</form>
</div>	   
<div class="submitlist" align="center" style="display: none">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定" class="submitBtn" id="selectOkBot1"/>
	 </td>
	 </tr>
	 </table>
</div>
<div title="新增支付方式" id ="addAccountDialogssss"></div> 
<script type="text/javascript">
var showAccount = {
	    init:function(){
	    var self = this;
	    Eop.Dialog.init({id:"addAccountDialogssss",modal:true,title:"新增银行",height:"100px",width:"400px"});
	    
	 	//删除
	      $(".delPayment").bind("click",self.del);
	      $("a[name=addAccountss]").unbind("click").bind("click",function() {
	           self.showAddAccDialogs();
	      }); 
	      },
	      //弹出新增页面
		   showAddAccDialogs : function() {
			     Eop.Dialog.open("addAccountDialogssss");
			     var cfg_id=$("#idxx").val();
			     var url ="creditAction!addAccount.do?ajax=yes&cfg_id="+cfg_id; 
			     $("#addAccountDialogssss").load(url,function(){
		   });
		  }, 
	     del:function(){
	         if(!confirm("是否确定要删除?")){
	            return ;
	         }  
	         var accounted_code = $(this).attr("value");
	         var cfg_id = $("#idxx").val();
	    	 var url ="creditAction!delAcc.do?ajax=yes&accounted_code="+accounted_code+"&cfg_id="+cfg_id;    
	         Cmp.excute('', url, {}, showAccount.delJsonBack, 'json');
	    },
	     delJsonBack:function(reply){
			    if(reply.result==0){
			      alert(reply.message);
			    }  
			    if(reply.result==1){
			     alert("操作成功!");
			     var cfg_id=$("#idxx").val();
			     var url ="creditAction!showAccount.do?ajax=yes&cfg_id="+cfg_id; 
			     $("#addAccountDialog").load(url,function(){
		         });
			    }
		},
	}; 
	$(function(){
		showAccount.init();
	});
</script>

