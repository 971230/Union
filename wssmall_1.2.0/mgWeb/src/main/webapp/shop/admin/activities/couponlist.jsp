<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Coupons.js"></script>
<form method="post" id='coupon_list_form' action='coupon!list.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>优惠券名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="couponsSearch.cpns_name" value="${couponsSearch.cpns_name}"
						class="searchipt" /></td>
					<th>优惠券号码:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="couponsSearch.cpns_prefix"
						value="${couponsSearch.cpns_prefix}" class="searchipt" /></td>
					<th>类型:&nbsp;&nbsp;</th>
					<td><select class="ipttxt" style="width: 150px;"
						name="couponsSearch.cpns_type">
							<option value="" selected="selected">--请选择--</option>
							<c:forEach var="list" items="${typeList}">
								<option value="${list.pkey }"
									${couponsSearch.cpns_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>状态:&nbsp;&nbsp;</th>
					<td><select class="ipttxt" style="width: 100px"
						name="couponsSearch.cpns_status">
							<option value="" selected="selected">--请选择--</option>
							<c:forEach var="list" items="${statusList}">
								<option value="${list.pkey }"
									${couponsSearch.cpns_status == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="couponsSearch.pmt_time_begin"
						value="${couponsSearch.pmt_time_begin}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="couponsSearch.pmt_time_end"
						value="${couponsSearch.pmt_time_end}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="comBtnDiv">
		<a href="coupon!add.do" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" class="graybtn1"
			id="delBtn"><span>删除</span></a>
		<div style="clear:both"></div>
	</div>
</form>

<div class="grid">
	<form method="POST" id="coupon_list_form">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="cpns_name" width="250px">优惠券名称</grid:cell>
				<grid:cell sort="cpns_prefix">优惠券号码</grid:cell>
				<grid:cell sort="cpns_type">类型</grid:cell>
				<grid:cell sort="cpns_gen_quantity">已发数量</grid:cell>
				<grid:cell sort="cpns_point">兑换积分</grid:cell>
				<grid:cell sort="cpns_status">状态</grid:cell>
				<grid:cell sort="pmt_time_begin">起始时间</grid:cell>
				<grid:cell sort="pmt_time_end">终止时间</grid:cell>
				<grid:cell width="100px">操作</grid:cell>
			</grid:header>

			<grid:body item="coupon">
				<grid:cell>
					<input type="checkbox" name="id" value="${coupon.cpns_id }" />
					<!--  ${coupon.cpns_id }-->
					<input type="hidden" name="pmtIdArray" value="${coupon.pmt_id }"
						disabled="disabled" />
				</grid:cell>
				<grid:cell>${coupon.cpns_name } </grid:cell>
				<grid:cell>${coupon.cpns_prefix } </grid:cell>
				<grid:cell>
					<c:if test="${coupon.cpns_type == 0 }">一张可无限使用</c:if>
					<c:if test="${coupon.cpns_type == 1 }">多张只适用一次</c:if>
				</grid:cell>
				<grid:cell>${coupon.cpns_gen_quantity} </grid:cell>
				<grid:cell>${coupon.cpns_point} </grid:cell>
				<grid:cell>
					<c:if test="${coupon.cpns_status == 1 }">启用</c:if>
					<c:if test="${coupon.cpns_status == 0 }">禁用</c:if>
				</grid:cell>
				<grid:cell>${fn:substring(coupon.pmt_time_begin  , 0 , 10)} </grid:cell>
				<grid:cell>${fn:substring(coupon.pmt_time_end  , 0 , 10)}  </grid:cell>
				<grid:cell>
					<a href="coupon!edit.do?cpnsid=${coupon.cpns_id }"> <img
						class="modify" src="images/transparent.gif">
					</a>
					<span class='tdsper'></span>
					<a href="coupon!show.do?cpnsid=${coupon.cpns_id }">查看详细</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>
<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>