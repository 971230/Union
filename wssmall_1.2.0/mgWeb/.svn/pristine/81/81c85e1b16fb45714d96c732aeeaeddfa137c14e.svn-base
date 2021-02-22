<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<%-- <link rel="stylesheet" href="${ctx}/shop/admin/css/style_min.css" type="text/css" rel="stylesheet"> --%>
<%-- <link rel="stylesheet" href="${ctx}/shop/admin/css/singlepage_min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/style.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/ome.css" type="text/css" rel="stylesheet"> --%>


<style>
.tableform {
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}

</style>

<div style="width: 100%; height: 380px;overflow-Y: auto;">
	<div class="rightDiv">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li id="show_click_1" class="selected">
							<span class="word">退/换货申请单</span><span class="bg"></span>
						</li>
						<div class="clear"></div>
					</ul>
				</div>
			</h3>
		</div>
		<form method="post" id="addApplyFrom"
			action="purchaseOrderAction!add.do" class="validate" validate="true">
			<div class="top_up_div">
				<h3>
					退/换货信息
				</h3>
				<div class="top_up_con">
					<table class="gridlist" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td align="right" nowrap="nowrap" width="10%">
									问题描述：
								</td>
								<td colspan="3">
									<textarea name="orderApply.question_desc" id="question_desc" dataType="string" ${isEdit=='yes'?'disabled':'' }
										autocomplete="off" required="true" style="width: 80%;height: 40px;" required="true">${orderApply.question_desc }</textarea>
									<span style="color: red;">*</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="10%">
									申请凭据：
								</td>
								<td>
									<input type="checkbox" name="apply_proof" ${apply_proof_1=='yes'?'checked':'' }
									 value="1" id="CheckboxGroup1_0" ${isEdit=='yes'?'disabled':'' } />有发票
									&nbsp;&nbsp;
									<input type="checkbox" name="apply_proof" ${apply_proof_2=='yes'?'checked':'' } value="2" id="CheckboxGroup1_1" ${isEdit=='yes'?'disabled':'' } />有检测报告
									<span id="pru_order_name_message"></span>
								</td>
								
								<td align="right" nowrap="nowrap" width="10%">
									商品返回方式：
								</td>
								<td>
									<input type="radio" name="orderApply.good_return_type" ${(orderApply.good_return_type=='1'||orderApply.good_return_type==null)?'checked':'' } value="1" id="RadioGroup2_0" ${isEdit=='yes'?'disabled':'' }/>上门取件
									&nbsp;&nbsp;
									<input type="radio" name="orderApply.good_return_type" ${orderApply.good_return_type=='3'?'checked':'' } value="3" id="RadioGroup2_2" ${isEdit=='yes'?'disabled':'' }/>快递
									<span id="pru_order_name_message"></span>
								</td>
								
							</tr>

						</thead>
					</table>
					</div>
				
			</div>
			
			<div class="top_up_div">
				<h3>
					退款银行信息
				</h3>
				<div class="top_up_con">
					<table class="gridlist" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td align="right" nowrap="nowrap" width="10%">
									请选择银行：
								</td>
								<td >
									<select name="orderApply.bank_info" ${isEdit=='yes'?'disabled':'' } style="width:200px;">
										<c:forEach items="${bankList }" var="bank">
											<option value="${bank.bank_id}" ${bank.bank_id==orderApply.bank_info?'selected=selected':'' }>${bank.bank_name}</option>
										</c:forEach>
									</select>
								</td>
								<td align="right" nowrap="nowrap" width="10%">
									开户人姓名：
								</td>
								<td>
									<input type="text" class="ipttxt" name="orderApply.account_holder_name" value="${orderApply.account_holder_name }" id="account_holder_name" dataType="string"
											style="width:200px;" autocomplete="off" required="true" ${isEdit=='yes'?'disabled':'' }/>
									<span style="color: red;">*</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="10%">
									银行账号：
								</td>
								<td>
									<input type="text" class="ipttxt" name="orderApply.bank_account" value="${orderApply.bank_account }" dataType="string" style="width:200px;"
											 autocomplete="off" required="true" ${isEdit=='yes'?'disabled':'' }/>
									<span style="color: red;">*</span>
								</td>
								
								<td align="right" nowrap="nowrap" width="10%">
									请确认账号：
								</td>
								<td>
									<input type="text" class="ipttxt" name="bank_account_qr" value="${orderApply.bank_account }" dataType="string" style="width:200px;"
											 autocomplete="off" required="true" ${isEdit=='yes'?'disabled':'' }/>
									<span style="color: red;">*</span>
								</td>
								
							</tr>

							<tr>
								<td align="right">
									联系人姓名：
								</td>
								<td width="40%">
									<input type="text" class="ipttxt" name="orderApply.linkman" value="${orderApply.linkman }" dataType="string" style="width:200px;"
											 autocomplete="off" required="true" ${isEdit=='yes'?'disabled':'' } />
								    <span style="color: red;">*</span>
								</td>
								<td align="right" width="10%">
									手机号码：
								</td>
								<td>
									<input type="text" class="ipttxt" name="orderApply.phone_num" value="${orderApply.phone_num }" dataType="string" style="width:200px;"
											 autocomplete="off" required="true" ${isEdit=='yes'?'disabled':'' }/>
									<span style="color: red;">*</span>
								</td>
							</tr>
						</thead>
					</table>
					</div>
			</div>
			
			<div class="top_up_div">
				<h3>
					退货商品&nbsp;&nbsp;&nbsp;
					<c:if test="${isEdit!='yes'}">
					<!-- <button id="order-find-btn" class="btn" type="button">
						<span><span>选择订单</span> </span>
					</button> -->
					<a href="javascript:void(0);" id="order-find-btn" class="dobtn" style="margin-right:5px;">选择订单</a>
					</c:if>
				</h3>
				<div class="top_up_con">
					<input type="hidden" name="order_id" value="${order_id }" />
					<input type="hidden" name="apply_id" value="${apply_id }" />
					<input type="hidden" name="action" value="${action }" />
					<input type="hidden" name="service_type" value="${service_type }" />
					<input type="hidden" id="order_amount_price" value="${order.order_amount }" />
					<table class="gridlist blueTable" id="purchase_table" style="margin: 4px 0;width: 100%;">
						<thead>
							<tr>
								<th>选择</th>
								<th>
									商品ID
								</th>
								<th>
									商品名称
								</th>
								<th>
									购买数量
								</th>
								<th style="width: 60px;">
									已发货数量
								</th>
								<th >
									单价
								</th>
								<th >
									退货数量
								</th>
							</tr>
						</thead>
						<tbody id="goodsDataNode">
							<%-- <c:if test="${action=='edit' }"> --%>
								<c:forEach items="${orderItems }" var="item">
									<tr class="selected">
										<td><input type="checkbox" ${item.isChecked } name="itemIdArray" value="${item.item_id }" price="${item.coupon_price }" ${isEdit=='yes'?'disabled':'' }/></td>
										<td>${item.goods_id }</td><td visibility="true" class="product-name">${item.name }</td>
										<td>${item.num }</td><td>${item.ship_num }</td><td>${item.coupon_price }元</td>
										<td class="count"><input type="text" size="3" buynum="${item.ship_num }" value="${item.return_num}" name="return_num" required="true" dataType="int" ${item.isChecked=='checked'?'':'disabled=disabled' } ${isEdit=='yes'?'disabled':'' }/></td>
									</tr>
								</c:forEach>
							<%-- </c:if> --%>
						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test="${service_type=='3' }">
				<div class="top_up_div">
					<h3>
						换货目标商品&nbsp;&nbsp;
						<c:if test="${isEdit!='yes'}">
						<!-- <button id="goods-find-btn" class="btn" type="button">
							<span><span>选择商品</span> </span>
						</button> -->
						<a href="javascript:void(0);" id="goods-find-btn" class="dobtn" style="margin-right:5px;">选择商品</a>
						</c:if>
					</h3>
					<div class="top_up_con">
						<table class="gridlist blueTable" id="purchase_table" style="margin: 4px 0;width: 100%;">
							<thead>
								<tr>
									<th>
										商品ID
									</th>
									<th>
										商品名称
									</th>
									<th >
										单价
									</th>
									<th style="width: 60px;">
										购买数量
									</th>
									<th style="text-align: center;">
										操作
									</th>
								</tr>
							</thead>
							<tbody id="exgoodsDataNode">
								<c:if test="${action=='edit' }">
									<c:forEach items="${exApplyItemList }" var="item">
										<tr class="selected">
											<td>${item.goods_id }</td><td visibility="true" class="product-name">${item.name }</td>
											<td>${item.price }元</td>
											<td class="count"><input type="text" size="3" value="${item.num }" price="${item.price }" name="ex_num" required="true" dataType="int" onblur="checkPrice();" ${isEdit=='yes'?'disabled':'' }/></td>
											<td style="text-align: center;">
											<c:if test="${isEdit!='yes'}">
											<a href="javascript:void(0);" name="del_ex_btn" >删除</a>
											</c:if>&nbsp;
											<input type="hidden" name="ex_productArray" value="${item.product_id }" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			
			<div class="top_up_div">
				<h3>
					退款金额
				</h3>
				<div class="top_up_con">
					<table class="gridlist blueTable" id="purchase_table" style="margin: 4px 0;width: 100%;">
						<thead>
							<tr>
								<th style="text-align: center;">应退款金额(元)</th>
								<th style="text-align: center;">
									折旧金额(元)
								</th>
								<th style="text-align: center;">
									物流费用(元)
								</th>
								<th style="text-align: center;">
									需退还用户费用(元)
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td id="returnTotalPrice" style="text-align: center;">${orderApply.refund_value }</td>
								<td style="text-align: center;">
									<input type="text" size="8" value="${orderApply.depreciation_price }" ${isEdit=='yes'?'disabled':'' } dataType="float" name="depreciation_price" onblur="checkPrice();" />
								</td>
								<td style="text-align: center;">
									<input type="text" size="8" value="${orderApply.ship_price }" ${isEdit=='yes'?'disabled':'' } dataType="float" name="ship_price" onblur="checkPrice();" />
								</td>
								<td style="text-align: center;">
									<input type="text" size="8" value="${orderApply.pay_price }" ${isEdit=='yes'?'disabled':'' } name="pay_price" readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
			</div>
			
			<div class="top_up_div" ${action=='audit'?'':'style="display: none;"' } >
				<h3>
					审核
				</h3>
				<div class="top_up_con">
					<table class="gridlist" id="purchase_table" style="margin: 4px 0;width: 100%;text-align: center;">
							
							<tr>
					          	<th style="text-align: right;width: 120px;">选择环节:</th>
					            <td colspan="5">
					            	<select id="order_flow_select" name="flow_def_id" >
					            		<c:forEach items="${flowDefList }" var="flow">
					            		<c:if test="${'returned'==flow.flow_type || 'exchange'==flow.flow_type}">
					            			<option value="${flow.flow_def_id }" ${'returned'==flow.flow_type?'selected':'' }>${flow.flow_name }</option>
					            		</c:if>
					            		</c:forEach>
					            	</select>
					            </td>
					            <th style="display: none;">订单处理组:</th>
					            <td style="display: none;">
					            	<select id="order_group_select" name="flow_group_id">
					            		<c:forEach items="${orderGroupList }" var="og">
					            			<option value="${og.group_id }" ${og.group_id==orderGroup.group_id?'selected':'' }>${og.group_name }</option>
					            		</c:forEach>
					            	</select>
					            </td>
					            <th style="display: none;">订单处理人:</th>
					            <td style="display: none;">
					            	<p style="height:26px;">
						            	<input type="hidden"  name="flow_user_id" value="${user.userid }" />
	            						<input name="flow_user_name" type="text" class="formIpt" value="${user.username }"  readonly="readonly" />
						            	<a href="javascript:void(0);" name="user_clear_btn" class="dobtn" style="margin-right:5px;">清空</a>
						            	<a href="javascript:void(0);" name="user_add_btn" class="dobtn" style="margin-right:5px;">+</a>
					            	</p>
					            	<span></span>
					            </td>
					          </tr>
					          <tr>
					          	<th style="text-align: right;">处理意见：</th>
					          	<td colspan="5">
					          		<input type="radio" name="apply_status" value="1" checked="checked" />通过&nbsp;&nbsp;<input type="radio" name="apply_status" value="2" />不通过&nbsp;&nbsp;
					          	</td>
					          </tr>
					          <tr>
					            <th style="text-align: right;">备注：</th>
					            <td colspan="5">
					            	<textarea  style="width: 95%;" name="hint" type="textarea"></textarea>
					            </td>
					          </tr>
					          
					</table>
					</div>
			</div>
			
			<div class="submitlist" align="center">
				<table align="right" style="width: 90%;">
					<tr>
						<th>
							&nbsp;
						</th>
						<td style="text-align: right;">
						<c:if test="${orderApply.apply_state==0||orderApply.apply_state==null }">
							<input id="addApplyOrderBaseBtn" type="button" value=" 提交  " 
								class="submitBtn" />&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<div id="select_admin_dialog"></div>
<script src="<%=request.getContextPath() %>/shop/admin/ddgj/js/orderbusiness.js"></script>
<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"select_admin_dialog",modal:true,title:"选择处理用户", height:"600px",width:"600px"});
	});
</script>