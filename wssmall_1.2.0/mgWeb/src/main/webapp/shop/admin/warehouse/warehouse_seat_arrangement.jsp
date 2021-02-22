<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/warehouse_seat_arrangement.js"></script>
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
	<form action="warehouseSeatAction!arrangementSearch.do" method="post" id="warehouseForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td >
							商品货号:<input type="text" size="18" class="ipttxt" id="sn" name="sn" value="${sn }" class="searchipt" />
						</td>
						<td >
							商品名称:<input type="text" size="18" class="ipttxt" id="name" name="name" value="${name }" class="searchipt" />
						</td>
						<td >
							仓库名称:<input type="text" size="18" class="ipttxt" id="house_name" name="house_name" value="${house_name }" class="searchipt" />
						</td>
						<td >
							商品货位:<input type="text" size="18" class="ipttxt" id="seat_name" name="seat_name" value="${seat_name }" class="searchipt" />
						</td>
						<td >
							状态:
							<select id="disabled" name="disabled">
								<option value="">全部</option>
								<option value="0">正常</option>
								<option value="1">失效</option>
							</select>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
			<a href="javascript:;" id="arrangementButton" name="arrangementButton" style="margin-right: 10px;" class="graybtn1" attr_code="WAREH_SEAT_APP_BUTTON"><span>解绑</span></a>
			<%-- <html:buttondict value="解绑" id="arrangementButton" name="arrangementButton" attr_code="WAREH_SEAT_APP_BUTTON" type='a'></html:buttondict> --%>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" >
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>商品货号</grid:cell>
				<grid:cell>商品名称</grid:cell>
				<grid:cell sort="house_name">仓库名称</grid:cell>
				<grid:cell>商品货位</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" value="${obj.seat_id},${obj.goods_id}" />
				</grid:cell>
				<grid:cell>${obj.sn}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.seat_name}</grid:cell>
				<grid:cell>
					<c:if test="${obj.disabled==0}"><span>正常</span></c:if>
					<c:if test="${obj.disabled==1}"><span>失效</span></c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.create_date}"></html:dateformat>
				</grid:cell>
			</grid:body>
		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	<div id="showDlg"></div>
	<div id="showWarehouseDiog" style="width: 800;height: 800"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
    $("#disabled option[value='${disabled}']").attr("selected", true);
    $("#disabled").change(function (){
       $("#button").trigger("click")
    });
});
</script>