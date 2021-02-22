<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/warehouse_seat.js"></script>
<script type="text/javascript" src="warehouse/js/warehouseSeatDalog.js"></script>
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
	<form action="warehouseSeatAction!search.do" method="post" id="warehouseForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th >
							货位名称:
						</th>
						<td >
							<input type="text" class="ipttxt" id="seat_name" name="seat_name" value="${seat_name }" class="searchipt" />
						</td>
						<th >
							仓库名称:
						</th>
						<td >
							<input type="text" class="ipttxt" id="house_name" name="house_name" value="${house_name }" class="searchipt" />
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
			<a href="javascript:;" id="delWarehouseSeatBtn"style="margin-right: 10px;" class="graybtn1"><span>删除</span></a>
			<a href="javascript:;" id="addWarehouseSeatBtn"style="margin-right: 10px;" class="graybtn1"><span>添加</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" >
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>货位名称</grid:cell>
				<grid:cell sort="house_name">仓库名称</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" value="${obj.seat_id}" />
				</grid:cell>
				<grid:cell>${obj.seat_name}</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>
					<a title="编辑"href="warehouseSeatAction!detail.do?flag=edit&seat_id=${obj.seat_id }">修改 </a>
				</grid:cell>
			</grid:body>

		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	<div id="showDlg"></div>
	<div id="showWarehouseDiog" style="width: 800;height: 800"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>