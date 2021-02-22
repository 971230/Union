<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<div>
	<form action="verifyModeCfg!list.do" method="post" id="searchVerifyModeForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>校验名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.fun_name" value="" /> 
	    	      </td>	
	    	      <th>校验方式：</th>
	    	      <td>
	    	          <html:selectdict name="params.verify_mode"  attr_code="DC_VERIFY_MODE" style="width:150px" appen_options='<option value="">全部</option>'></html:selectdict>
	    	      </td>	    	       
	    	      <th>状态</th>
	    	      	<td><html:selectdict name="params.status"  attr_code="DC_VERIFY_STATUS" style="width:150px" appen_options='<option value="">全部</option>'></html:selectdict></td>
	    	      <th></th>
	    	      <td>
	    	          <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      </td>    
	    	      <td>
				  	    <input class="comBtn" type="button" id="searchBtn" value="添加" style="margin-right:10px;"
							onclick="window.location.href='verifyModeCfg!edit.do'" />
			 	 </td>
			 	 <td>
			 	 	<input class="comBtn" type="button" id="refreshBtn" value="刷新" style="margin-right:10px;"
							onclick="refreshData()" />
			 	 </td>   
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>	
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage">
	    <grid:header>
		    <grid:cell>序号</grid:cell>
		    <grid:cell>校验名称</grid:cell>
		    <grid:cell>校验方式</grid:cell>
			<grid:cell>校验逻辑</grid:cell>
			<grid:cell>状态</grid:cell>
			<grid:cell>操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
		    <grid:cell style="width:50px;">${objeto.seq_id }  </grid:cell>
		    <grid:cell style="width:150px;">${objeto.fun_name } </grid:cell>
		    <grid:cell style="width:80px;">${objeto.verify_mode }</grid:cell>
		    <grid:cell style="width:550px;">${objeto.clazz } </grid:cell>
		    <grid:cell style="width:50px;">${objeto.verify_status }</grid:cell>
			<grid:cell style="width:50px;">
				<a href="#" onclick="edit(this,'${objeto.fun_id }');">编辑</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>

<script>
	function edit(obj, fun_id) {
		if (!confirm("确定要编辑这条数据吗？")) {
			return false;
		}
		window.location.href="verifyModeCfg!edit.do?id="+fun_id;
	}
	
	function refreshData(){
		var self =this;
		$.ajax({
			 type: "POST",
			 url: "/core/admin/ecc/bizCheckCfgAdmin!refreshData.do",
			 data: "ajax=yes&refresh_type=1",
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