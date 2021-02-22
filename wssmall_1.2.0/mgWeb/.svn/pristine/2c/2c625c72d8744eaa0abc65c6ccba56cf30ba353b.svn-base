<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<div>
	<form action="ciudad!list.do" method="post" id="searchNoSegForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>归属地市：</th>
	    	      <td>
	    	          <html:selectdict name="params.region_id" curr_val="${params.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
	    	      </td>
	    	      <th>号段：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.seg_no" value="${params.seg_no}" /> 
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
				onclick="window.location.href='ciudad!add.do'" />
		</div>			
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage">
	    <grid:header>
		    <grid:cell>号段</grid:cell>
		    <grid:cell>归属地市</grid:cell>
		    <grid:cell>地市编码</grid:cell>
			<grid:cell>操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
		    <grid:cell>${objeto.seg_no }  </grid:cell>
		    <grid:cell>${objeto.region_name } </grid:cell>
		    <grid:cell>${objeto.region_id }</grid:cell>
			<grid:cell>
				<!-- <a href="ciudad!add.do?id=${objeto.seg_id}">修改</a>&nbsp;&nbsp; -->
				<a href="#" onclick="del(this,'${objeto.seg_id }');">删除</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>

<script>
	function importacion() {
		var v = document.getElementById("uploadFile").value;
		if (isNull(v)) {
			MessageBox.show("上传文件为空 ", 3, 2000);
			return;
		}
		document.getElementById("uploadForm").submit();
	}
	
	function del(obj, seg_id) {
		if (!confirm("确定要删除这条数据吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: "ciudad!del.do",
			 data: "ajax=yes&id="+seg_id,
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