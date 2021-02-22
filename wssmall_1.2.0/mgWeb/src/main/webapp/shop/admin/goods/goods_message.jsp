<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript"
	src="${ctx}/shop/admin/goods/js/showGoodsMsg.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/goods.js"></script>
<div class="input">

	<c:if test="${is_edit }">
		<input type="hidden" name="goods.goods_id"
			value="${goodsView.goods_id  }" />
	</c:if>

	<div style="display: block;" class="goods_input">
		<div class="tab-bar" style="position: relative;">
			<ul class="tab">
				<li tabid="0" class="active">
					基本信息
				</li>
				<c:forEach var="tab" items="${tabs}">
					<li tabid="${tab.tab_id }">
						${tab.tab_name }
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="tab-page">
			${goodstype}
			<div tabid="tab_0" class="tab-panel">

				<table class="form-table" style="width: 55%; float: left">

					<tr>
						<th>
							商品名称：
						</th>
						<td>
							<input type="text" class="ipttxt" name="goods.name"
								id="goods_name" class="input_text" dataType="string"
								required="true" value="${goodsView.name }" style="width: 250px" />
						</td>
					</tr>
					<tr style="display:none;">
						<th>
							充值卡类型：
						</th>
						<td>
							<html:selectdict id="charge_type" name="goods.effective_area_flag" curr_val="${goodsView.effective_area_flag}" attr_code="dc_charge_type" style='width:155px;' >
							</html:selectdict>
							&nbsp;&nbsp;
							<span class="help_icon" helpid="goods_effective_area_flag"></span>
						</td>
					</tr>
					<!-- 
					<tr>
						<th>
							库存：
						</th>
						<td>
							<c:if test="${have_stock==-1}">
								<input type="text" readonly="readonly" class="ipttxt"
									maxlength="25" style="width: 60px;" name="store" id="store"
									autocomplete="off" value="有货">
					&nbsp;&nbsp;<span class="help_icon" helpid="goods_store"></span>
							</c:if>
							<c:if test="${have_stock==0}">
								<input type="text" readonly="readonly" class="ipttxt"
									maxlength="25" style="width: 60px;" name="store" id="store"
									autocomplete="off" value="${goodsView.store}">
					&nbsp;&nbsp;<span class="help_icon" helpid="goods_store"></span>
							</c:if>

						</td>
					</tr>
					 -->
					 
					 <tr>
						<th>
							销售价格：
						</th>
						<td>
							<input type="text" class="ipttxt" maxlength="25"
								style="width: 60px;" name="goods.price" id="price" class="price"
								autocomplete="off" dataType=float value="${goodsView.price}">
							&nbsp;&nbsp;
							<span class="help_icon" helpid="goods_price"></span>
						</td>
					</tr>
					
					<tr>
						<th>
							面值：
						</th>
						<td>
							<input type="text" class="ipttxt" name="goods.mktprice"
								id="mktprice" class="input_text" dataType=float required="true"
								value="${goodsView.mktprice}" style="width: 100px"
								autocomplete="off" />
							&nbsp;&nbsp;
							<span class="help_icon" helpid="goods_mktprice"></span>
						</td>
					</tr>

					
					<tr>
						<th>
							CRM销售品ID：
						</th>
						<td>
							<input type="text" class="ipttxt" maxlength="25"
								style="width: 100px" name="goods.crm_offer_id" id="sellId"
								autocomplete="off" required="true"
								value="${goodsView.crm_offer_id}">
							&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				${album }
				<div class="inputBox">
					<div class="textareaBox">
						<textarea class="textareaClass" id="intro" name="goods.intro" cols="100" rows="8"
							style="width: 98%; height: 300px;">
							<c:out value="${goodsView.intro }" escapeXml="false"></c:out>
						</textarea>
						</div>
					${goodstag} ${goodsarea}

				</div>
				${xianGuoGoodsPlugin }
				<input type="hidden" id="adminUserLanid"
					value="${adminUser.lan_id }" />
				<input type="hidden" id="adminUserid" value="${adminUser.userid }" />
				<input type="hidden" id="goodsStaff_no"
					value="${goodsView.staff_no }" />
				<input type="hidden" id="goodsTypeValue"
					value="${goodsType.type_id }" />
			</div>
		</div>
		<script>
			$(function(){
				$("#uploadProgress").hide();
			})
		</script>