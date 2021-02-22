<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="goodsUser/goodsUserList.js"></script>
<script type="text/javascript">
   $("#selUserType").val(${user_type});
</script>

<form id="serchform"
	action="/shop/admin/goodsUserAction!goodsUserList.do" method="post">

	<div class="searchformDiv">
		<table>
			<tr>
				<th>商品名称：</th>
				<td><input type="text" class="ipttxt" style="width:90px"
					id="goodsName" name="goodsName" value="${goodsName}" /></td>
				<th>员工类型：</th>
				<td><select class="ipttxt" id="selUserType" name="user_type">
						<option value="-1">--请选择--</option>
						<c:forEach var="typeList" items="${typeList}">
							<option value="${typeList.pkey }">${typeList.pname}</option>
						</c:forEach>
				</select></td>
				<th></th>
				<td><input class="comBtn" type="submit" name="searchBtn"
					id="searchBtn" value="搜索" style="margin-right:10px;" /></td>
			</tr>
		</table>
		<div style="clear:both"></div>
	</div>
</form>
<div class="comBtnDiv">
	<a href="${ctx}/shop/admin/goodsUserAction!goodsUser.do"
		style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
</div>
<form>
	<div class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>

				<grid:cell width="160px">商品编号</grid:cell>
				<grid:cell width="160px">商品名称</grid:cell>
				<grid:cell width="100px">员工类型</grid:cell>
				<grid:cell width="50px">确认</grid:cell>
				<grid:cell width="50px">支付</grid:cell>
				<grid:cell width="50px">受理</grid:cell>
				<grid:cell width="50px">发货</grid:cell>
				<grid:cell width="50px">查询</grid:cell>
				<grid:cell width="180px">创建时间</grid:cell>
				<grid:cell width="180px">操作</grid:cell>
			</grid:header>
			<grid:body item="goodsUser">
				<grid:cell>&nbsp;${goodsUser.goods_id} </grid:cell>
				<grid:cell>&nbsp;${goodsUser.goods_name} </grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.user_type==0}">${typeMap.manager}</c:if>
					<c:if test="${goodsUser.user_type==1}">${typeMap.admin}</c:if>
					<c:if test="${goodsUser.user_type==2}">${typeMap.second_partner}</c:if>
					<c:if test="${goodsUser.user_type==3}">${typeMap.partner}</c:if>
					<c:if test="${goodsUser.user_type==4}">${typeMap.supplier}</c:if>
					<c:if test="${goodsUser.user_type==5}">${typeMap.supplier_employee}</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.qrType=='yes'}">是</c:if>
					<c:if test="${goodsUser.qrType=='no'}">否</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.payType=='yes'}">是</c:if>
					<c:if test="${goodsUser.payType=='no'}">否</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.acceptType=='yes'}">是</c:if>
					<c:if test="${goodsUser.acceptType=='no'}">否</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.deliveryType=='yes'}">是</c:if>
					<c:if test="${goodsUser.deliveryType=='no'}">否</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${goodsUser.queryType=='yes'}">是</c:if>
					<c:if test="${goodsUser.queryType=='no'}">否</c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd"
						d_time="${goodsUser.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<c:if test="${cur_userId==goodsUser.oper_id or founder == '0' or founder == '1'}">
						<a title="修改"
							href="goodsUserAction!editGoodsUser.do?goods_id=${goodsUser.goods_id}&&user_type=${goodsUser.user_type}&&service_code=${goodsUser.service_code }"
							class="p_prted">修改</a>
						<span class='tdsper'></span>
						<a title="删除" href="javascript:void(0)" name="del"
							user_type="${goodsUser.user_type }"
							goods_id="${goodsUser.goods_id}"
							service_code="${goodsUser.service_code }" class="p_prted">删除</a>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</div>
</form>
