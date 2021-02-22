<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/warehouse.js"></script>
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
<div>
	<form action="warehouse!search.do" method="post" id="warehouseForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>

						<th>
							仓库名称:
						</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="warehouse.house_name" value="${warehouse.house_name }" class="searchipt" />
						</td>
						<td>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
			<a href="javascript:;" id="delWarehouseBtn"style="margin-right: 10px;" class="graybtn1"><span>删除</span></a>
			<a href="javascript:;" id="addWarehouseBtn"style="margin-right: 10px;" class="graybtn1"><span>添加</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="company_name">仓库名称</grid:cell>
				<grid:cell>联系人姓名</grid:cell>
				<grid:cell>联系人手机</grid:cell>
				<grid:cell>联系人电话</grid:cell>
				<grid:cell>仓库编号</grid:cell>
				<grid:cell>发货属性</grid:cell>
				<grid:cell>仓库归属</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" value="${obj.houst_id}" supplier_state="${obj.houst_id }" />
				</grid:cell>
				<grid:cell>
					<a title="查看" href="warehouse!detail.do?flag=view&houst_id=${obj.houst_id }">${obj.house_name}</a>
				</grid:cell>
				<grid:cell>${obj.telephone} </grid:cell>
				<grid:cell>${obj.phone_num} </grid:cell>
				<grid:cell>${obj.house_code} </grid:cell>
				<grid:cell>
					<c:if test="${obj.attr_inline_type eq 'T'}">
     					发货仓库
     				</c:if>
					<c:if test="${obj.attr_inline_type eq 'F'}">
     					备货仓库
     				</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${obj.attribution eq 'T'}">
     					自建仓库
     				</c:if>
					<c:if test="${obj.attribution eq 'F'}">
     					第三方仓库
     				</c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<a title="编辑"href="supplier!detail.do?flag=edit&&houst_id=${obj.houst_id }">修改 </a>
				</grid:cell>
			</grid:body>

		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	Supplier.init();
});
</script>


