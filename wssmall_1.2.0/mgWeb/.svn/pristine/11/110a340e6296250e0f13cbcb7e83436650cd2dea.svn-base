<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
	<c:if test="${orderApply.apply_state==0}">
		<a href="applyn!applyReturnedGoods.do?action=audit&apply_id=${apply_id}&apply_status=1" class="dobtn" style="margin-right:5px;">审核通过</a>
		<a href="applyn!applyReturnedGoods.do?action=audit&apply_id=${apply_id}&apply_status=2" class="dobtn" style="margin-right:5px;">审核不通过</a>
	</c:if>
</div>
<form method="post" id="addApplyFrom"
	action="purchaseOrderAction!add.do" class="validate" validate="true">
	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="0"
				class="closeArrow"></a>退/换货信息
			<div class="dobtnDiv">
				<a href="javascript:void(0);" class="dograybtn">操作</a>
			</div>
		</div>
		<div id="order_tag_0" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tr>
					<th>问题描述：</th>
					<td colspan="3"><textarea name="orderApply.question_desc"
							id="question_desc" dataType="string" class="ipttxt"
							${isEdit=='yes'?'disabled':'' } autocomplete="off"
							required="true" style="width: 80%;height: 40px;" required="true">${orderApply.question_desc }</textarea>
						<span style="color: red;">*</span></td>
				</tr>
				<tr>
					<th>申请凭据：</th>
					<td><input type="checkbox" name="apply_proof"
						${apply_proof_1=='yes'?'checked':'' } value="1"
						id="CheckboxGroup1_0" ${isEdit=='yes'?'disabled':'' } />有发票
						&nbsp;&nbsp; <input type="checkbox" name="apply_proof"
						${apply_proof_2=='yes'?'checked':'' } value="2"
						id="CheckboxGroup1_1" ${isEdit=='yes'?'disabled':'' } />有检测报告 <span
						id="pru_order_name_message"></span></td>

					<th>商品返回方式：</th>
					<td><input type="radio" name="orderApply.good_return_type"
						${(orderApply.good_return_type=='1'||orderApply.good_return_type==null)?'checked':'' }
						value="1" id="RadioGroup2_0" ${isEdit=='yes'?'disabled':'' } />上门取件
						&nbsp;&nbsp; <input type="radio"
						name="orderApply.good_return_type"
						${orderApply.good_return_type=='3'?'checked':'' } value="3"
						id="RadioGroup2_2" ${isEdit=='yes'?'disabled':'' } />快递 <span
						id="pru_order_name_message"></span></td>

				</tr>
			</table>
		</div>
	</div>


	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="1"
				class="closeArrow"></a>退款银行信息
			<div class="dobtnDiv">
				<a href="javascript:void(0);" class="dograybtn">操作</a>
			</div>
		</div>
		<div id="order_tag_1" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tbody>
					<tr>
						<th>请选择银行：</th>
						<td><select name="orderApply.bank_info" class="ipttxt"
							${isEdit=='yes'?'disabled':'' } style="width:205px;">
								<c:forEach items="${bankList }" var="bank">
									<option value="${bank.bank_id}"
										${bank.bank_id==orderApply.bank_info?'selected=selected':'' }>${bank.bank_name}</option>
								</c:forEach>
						</select></td>
						<th>开户人姓名：</th>
						<td><input type="text" class="ipttxt"
							name="orderApply.account_holder_name"
							value="${orderApply.account_holder_name }"
							id="account_holder_name" dataType="string" style="width:200px;"
							autocomplete="off" required="true"
							${isEdit=='yes'?'disabled':'' } /> <span style="color: red;">*</span>
						</td>
					</tr>
					<tr>
						<th>银行账号：</th>
						<td><input type="text" class="ipttxt"
							name="orderApply.bank_account"
							value="${orderApply.bank_account }" dataType="string"
							style="width:200px;" autocomplete="off" required="true"
							${isEdit=='yes'?'disabled':'' } /> <span style="color: red;">*</span>
						</td>

						<th>请确认账号：</th>
						<td><input type="text" class="ipttxt" name="bank_account_qr"
							value="${orderApply.bank_account }" dataType="string"
							style="width:200px;" autocomplete="off" required="true"
							${isEdit=='yes'?'disabled':'' } /> <span style="color: red;">*</span></td>

					</tr>

					<tr>
						<th>联系人姓名：</th>
						<td width="40%"><input type="text" class="ipttxt"
							name="orderApply.linkman" value="${orderApply.linkman }"
							dataType="string" style="width:200px;" autocomplete="off"
							required="true" ${isEdit=='yes'?'disabled':'' } /> <span
							style="color: red;">*</span></td>
						<th>手机号码：</th>
						<td><input type="text" class="ipttxt"
							name="orderApply.phone_num" value="${orderApply.phone_num }"
							dataType="string" style="width:200px;" autocomplete="off"
							required="true" ${isEdit=='yes'?'disabled':'' } /> <span
							style="color: red;">*</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="2"
				class="closeArrow"></a>退货商品
			<div class="dobtnDiv">
				<a href="javascript:void(0);" class="dograybtn">操作</a>
			</div>
		</div>
		<div id="order_tag_2" class="formGridDiv">
			<input type="hidden" name="order_id" value="${order_id }" /> <input
				type="hidden" name="apply_id" value="${apply_id }" /> <input
				type="hidden" name="action" value="${action }" /> <input
				type="hidden" name="service_type" value="${service_type }" />
			<table class="gridlist" id="purchase_table"
				style="margin-left: 15px;">
				<thead>
					<tr>
						<th style="width: 100px;">选择</th>
						<th style="width: 150px;">商品ID</th>
						<th style="width: 150px;">商品名称</th>
						<th style="width: 150px;">购买数量</th>
						<th style="width: 150px;">已发货数量</th>
						<th style="width: 150px;">单价</th>
						<th style="width: 150px;">退货数量</th>
					</tr>
				</thead>
				<tbody id="goodsDataNode">
					<c:choose>
						<c:when test="${action=='edit'}">
							<c:forEach items="${orderItems}" var="item">
								<tr class="selected">
									<td><input type="checkbox" ${item.isChecked}
										name="itemIdArray" value="${item.item_id }"
										price="${item.coupon_price }" ${isEdit=='yes'?'disabled':'' } /></td>
									<td>${item.goods_id }</td>
									<td visibility="true" class="product-name">${item.name }</td>
									<td>${item.num }</td>
									<td>${item.ship_num }</td>
									<td>${item.coupon_price }元</td>
									<td class="count"><input type="text" size="3"
										buynum="${item.ship_num }" value="${item.return_num}"
										name="return_num" required="true" dataType="int"
										${item.isChecked=='checked'?'':'disabled=disabled' }
										${isEdit=='yes'?'disabled':'' } /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${itemsList }" var="items">
								<tr class="selected">
									<td><input type="checkbox" ${items.isChecked}
										name="itemIdArray" value="${items.item_id }"
										price="${items.coupon_price }" ${isEdit=='yes'?'disabled':'' } /></td>
									<td>${items.goods_id }</td>
									<td visibility="true" class="product-name">${items.name}</td>
									<td>${items.num }</td>
									<td>${items.ship_num }</td>
									<td>${items.coupon_price }元</td>
									<td class="count"><input type="text" size="3"
										buynum="${items.ship_num }" value="${items.ship_num}"
										name="return_num" required="true" dataType="int"
										${items.isChecked=='checked'?'':'disabled=disabled' }
										${isEdit=='yes'?'disabled':'' } /></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>

	<c:if test="${service_type=='3' }">
		<div class="formWarp">
			<div class="tit">
				<a href="javascript:void(0);" name="show_close_btn" tag_id="3"
					class="closeArrow"></a>换货目标商品
				<c:if test="${isEdit!='yes'}">
					<a href="javascript:void(0);" class="dobtn"
						style="margin-right:5px;" id="goods-find-btn">选择商品</a>
				</c:if>
				<div class="dobtnDiv">
					<a href="javascript:void(0);" class="dograybtn">操作</a>
				</div>
			</div>
		</div>
		<div id="order_tag_3" class="formGridDiv">
			<table class="gridlist" id="purchase_table"
				style="margin-left: 15px;">
				<thead>
					<tr>
						<th style="width: 200px;">商品ID</th>
						<th style="width: 200px;">商品名称</th>
						<th style="width: 200px;">单价</th>
						<th style="width: 200px;">购买数量</th>
						<th style="width: 200px;text-align: center;">操作</th>
					</tr>
				</thead>
				<tbody id="exgoodsDataNode">
					<c:if test="${action=='edit' }">
						<c:forEach items="${exApplyItemList }" var="item">
							<tr class="selected">
								<td>${item.goods_id }</td>
								<td visibility="true" class="product-name">${item.name }</td>
								<td>${item.price }元</td>
								<td class="count"><input type="text" size="3"
									value="${item.num }" price="${item.price }" name="ex_num"
									required="true" dataType="int" onblur="checkPrice();"
									${isEdit=='yes'?'disabled':'' } /></td>
								<td style="text-align: center;"><c:if
										test="${isEdit!='yes'}">
										<a href="javascript:void(0);" name="del_ex_btn">删除</a>
									</c:if>&nbsp; <input type="hidden" name="ex_productArray"
									value="${item.product_id }" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</c:if>


	<div class="formWarp">
		<div class="formWarp">
			<div class="tit">
				<a href="javascript:void(0);" name="show_close_btn" tag_id="4"
					class="closeArrow"></a>退款金额
				<div class="dobtnDiv">
					<a href="javascript:void(0);" class="dograybtn">操作</a>
				</div>
			</div>
		</div>
		<div id="order_tag_4" class="formGridDiv">
			<table class="gridlist" id="purchase_table" style="margin: 4px 0;">
				<thead>
					<tr>
						<th style="text-align: center;">应退款金额(元)</th>
						<th style="text-align: center;">折旧金额(元)</th>
						<th style="text-align: center;">物流费用(元)</th>
						<th style="text-align: center;">需退还用户费用(元)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="returnTotalPrice" style="text-align: center;width: 200px;">${orderApply.refund_value
							}</td>
						<td style="text-align: center;width: 200px;"><input
							type="text" size="8" value="${orderApply.depreciation_price }"
							${isEdit=='yes'?'disabled':'' } dataType="float"
							name="depreciation_price" onblur="checkPrice();" /></td>
						<td style="text-align: center;width: 200px;"><input
							type="text" size="8" value="${orderApply.ship_price }"
							${isEdit=='yes'?'disabled':'' } dataType="float"
							name="ship_price" onblur="checkPrice();" /></td>
						<td style="text-align: center;width: 200px;"><input
							type="text" size="8" value="${orderApply.pay_price }"
							${isEdit=='yes'?'disabled':'' } name="pay_price"
							readonly="readonly" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="formWarp" ${action=='audit'?'':'style="display: none;"' }>
		<div class="formWarp">
			<div class="tit">
				<a href="javascript:void(0);" name="show_close_btn" tag_id="5"
					class="closeArrow"></a>审核
				<div class="dobtnDiv">
					<a href="javascript:void(0);" class="dograybtn">操作</a>
				</div>
			</div>
		</div>
		<div id="order_tag_5" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tbody>
					<tr>
						<th>审核意见：</th>
						<td><select name="apply_status">
								<option value="1">审核通过</option>
								<option value="2">审核不通过</option>
						</select></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="submitlist" align="center">
		<table align="right" style="width: 100%;">
			<tr>
				<th>&nbsp;</th>
				<td style="text-align: center;"><c:if
						test="${orderApply.apply_state==0||orderApply.apply_state==null }">
						<a href="javascript:void(0);" class="dobtn"
							style="margin-right:5px;" id="addApplyOrderBaseBtn">提交</a>
					</c:if></td>
			</tr>
		</table>
	</div>
</form>
<div id="selectgoodsDlg"></div>