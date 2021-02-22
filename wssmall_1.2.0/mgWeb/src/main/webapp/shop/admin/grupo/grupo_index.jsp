<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<div >
	
	<form action="grupo!list.do" method="post" id ="theForm" name="theForm">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>分组名称:</th>
	    	      <td><input type="text" class="ipttxt"
						name="params.nombre" value="${params.nombre}" />
	    	      </td>
	    	      <th>分组编码:</th>
				  <td>
				  <input type="text" class="ipttxt"
						name="params.codigo" value="${params.codigo}" />
				  </td>
	    	      <td>
	    	      	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      	   	<input
						class="comBtn" type="button" id="searchBtn" value="添加"
						style="margin-right:10px;"
						onclick="window.location.href='grupo!add.do'" />
	    	      </td>
				
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
	</form>
	<form id="gridform" class="grid">
	
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell>分组名称</grid:cell>
				<grid:cell>分组编码</grid:cell>
				<grid:cell>分组状态</grid:cell>
				<grid:cell>描述</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>${objeto.group_name } </grid:cell>
				<grid:cell>${objeto.group_code }</grid:cell>
				<grid:cell>${objeto.status }  </grid:cell>
				<grid:cell>${objeto.group_desc } </grid:cell>
				<grid:cell>
					<a href="grupo!add.do?id=${objeto.group_id}">修改</a>&nbsp;&nbsp;
					<a href="#" onclick="del('${objeto.group_id }');">删除</a>
				</grid:cell>

			</grid:body>


		</grid:grid>
		<input type="hidden" name="join_suced" value=${join_suced }>
	</form>
</div>



<script>
	function del(ids) {
		if (!confirm("确定要删除这条数据吗？")) {
			return false;
		}
		url = "grupo!del.do?ids=" + ids;
		doAjax("theForm", url, "callBack");
	}

	function loteDel() {
		var ids = before("eckBox");
		if (!confirm("确定要删除这些数据吗？"))
			return;

		url = "equipo!del.do?ajax=yes&ids=" + ids;
		doAjax("equipoForm", url, "callBack");
	}
</script>
