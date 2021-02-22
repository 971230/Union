<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GoodsType.js"></script>
<script type="text/javascript">
	$(function() {
		Model.init();
	});
</script>
<style>
.input table th{
	text-align: left;
}
</style>
<div class="input">
	<div style="text-align:center">
		<div class="list-div" id="listDiv" style="width:100%;text-align:left">

			<div class="toolbar">
				<a href="javascript:;" id="modelAddBtn"><input class="comBtn"
					type="button" name="" id="" value="新增型号"
					style="margin-right:10px;outline-style:none" /></a>
				<div style="clear:both"></div>
			</div>
			<form method="post" action="type!saveModels.do" name="theForm"
				id="theForm">
				<input type="hidden" name="is_edit" value="${is_edit }" />
				<table id="models_table" cellpadding="3" cellspacing="1"
					style="width:100%">
					<tbody>
						<tr>
							<th>型号名称</th>
							<th>型号值</th>
							<th>&nbsp;</th>
						</tr>

						<c:if test="${goodsType!=null}">
							<c:forEach items="${goodsType.joinModel.specLists }" var="model"
								varStatus="pStatus">
								<tr>
									<td width="35%"><input type="text" class="ipttxt"
										name="modelnames" maxlength="60" value="${model.name }"
										dataType="string" required="true" /></td>
									<td width="35%">&nbsp;<input type="text" class="ipttxt"
										name="modelcodes" style="width:300px" value="${model.code }" /></td>
									<td width="30%"><a href="javascript:;"><img
											class="delete" src="images/transparent.gif"></a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>

				<div class="submitlist" align="center">
					<table>
						<tr>
							<td><input type="submit" value=" 下一步   " class="submitBtn" />
							</td>
						</tr>
					</table>
				</div>

			</form>
		</div>
	</div>
</div>