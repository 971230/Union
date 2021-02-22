<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<div>
	<form action="sysSetting!msgSetList.do" method="post" id="searchNoSegForm">
		<%-- <div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>信息接收者：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.seg_no" value="${params.receiver}" /> 
	    	      </td>
	    	      <th>号段：</th>
	    	      <td>
	    	          <html:selectdict name="params.region_id" curr_val="${params.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
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
	  	 </div> --%>
	  	 
	  	 <div class="comBtnDiv">
	  	    <input class="comBtn" type="button" id="searchBtn" value="添加" style="margin-right:10px;"
				onclick="window.location.href='sysSetting!msgSetAdd.do'" />
		</div>			
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage">
	    <grid:header>
		    <grid:cell>接收者名称</grid:cell>
		    <grid:cell>接收者号码</grid:cell>
		    <grid:cell>短信内容</grid:cell>
			<grid:cell width="90px">操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
		    <grid:cell>${objeto.receiver }  </grid:cell>
		    <grid:cell><span title="${objeto.mobile_no}">${fn:substring(objeto.mobile_no , 0 , 47)}...</span></grid:cell>
		    <grid:cell><span title="${objeto.msg}">${fn:substring(objeto.msg , 0 , 40)}...</span></grid:cell>
			<grid:cell>
				<!-- <a href="ciudad!add.do?id=${objeto.seg_id}">修改</a>&nbsp;&nbsp; -->
				<a href="#" onclick="del(this,'${objeto.set_id }');">删除</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>

<script>
	
	function del(obj, set_id) {
		if (!confirm("确定要删除这条数据吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: "sysSetting!msgSetDel.do",
			 data: "ajax=yes&msgSet.set_id=" + set_id,
			 dataType:"json",
			 success: function(result){
				 
				 if (result.result == '0') {
					 $(obj).parents('tr').remove();
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
</script>