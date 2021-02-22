<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<div>
	<form action="/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do" method="post" id="qryFrm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>业务名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.seg_no" value="${params.biz_name}" /> 
	    	      </td>	
	    	      <th>状态：</th>
	    	      <td>
						<select id="params.status" class="ipttxt" name="params.status" style="width: 155px">
						<option value="">--请选择--</option>
						<option value="00A">有效</option>
						<option value="00X">无效</option>
						</select>
	    	      </td>
	    	      <th></th>
	    	      <td></td>
	    	      <th></th>
	    	      <td>
	    	          <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      </td>       
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 
	  	 <div class="comBtnDiv">
	  	    <input class="comBtn" type="button" id="searchBtn" value="添加" style="margin-right:10px;"
				onclick="window.location.href='bizCheckCfgAdmin!bizAdd.do'" />
			<input class="comBtn" type="button" id="refreshBtn" value="刷新" style="margin-right:10px;"
				onclick="refreshData()" />
		</div>			
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage">
	    <grid:header>
		    <grid:cell>业务名称</grid:cell>
		    <grid:cell>业务ID</grid:cell>
		    <grid:cell>状态</grid:cell>
			<grid:cell>操作</grid:cell>
		</grid:header>

		<grid:body item="cfg">
		    <grid:cell>&nbsp;${cfg.biz_name}</grid:cell>
		    <grid:cell>&nbsp;${cfg.biz_id}</grid:cell>
		    <grid:cell>&nbsp;${cfg.status}</grid:cell>
			<grid:cell>
				<a href="bizCheckCfgAdmin!editBiz.do?id=${cfg.biz_id}">编辑</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>
 <script type="text/javascript">
$(function(){

	$("#yx_order table tr").find("#show_order_dtl").unbind("click").bind("click",function(){
		  var url="zJOrderAction!b2b_orderAudt.do";
		  var order_auto = $("#warehouseForm #order_auto").val();
	      Cmp.excute('', url, {order_auto:order_auto}, function(reply){
	    	  alert(order_auto);
	    	  $("#warehouseForm #order_auto").find("option[value='"+order_auto+"']").selected();
	       });
	});
	
});

function operPolicy(){
	var self =this;
    if(window.confirm('确认要刷新吗？')){
    	
    	var url =  "/core/admin/ecc/bizCheckCfgAdmin!refreshData.do?ajax=yes&refresh_type=0";
	    Cmp.ajaxSubmit('', '', url, {},  function jsonback(responseText) { // json回调函数
			if (responseText.result == 1) {
				alert(responseText.message);
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}, 'json');

	}
} 
function refreshData(){
	var self =this;
	$.ajax({
		 type: "POST",
		 url: "/core/admin/ecc/bizCheckCfgAdmin!refreshData.do",
		 data: "ajax=yes&refresh_type=0",
		 dataType:"json",
		 success: function(result){
			 if(result.result==1){
			 	alert(result.message);
		     }else{
		    	 alert(result.message);
			 }
		 },
		 error:function(){
			 alert("操作失败，请重试");
		 }
	}); 		
}
</script>