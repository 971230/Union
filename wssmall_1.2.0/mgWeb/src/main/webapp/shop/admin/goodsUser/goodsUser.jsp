<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="goodsUser/goodsUser.js"></script>

<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
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

#qrUserDiv UL {
	WIDTH: 540px;
	MARGIN-LEFT: 5px;
	PADDING-BOTTOM: 10px;
	MARGIN-BOTTOM: 10px;
	MARGIN-TOP: -10px;
	text-align: left;
}

#qrUserDiv LI {
	WIDTH: 90px;
	display: block;
	FLOAT: left;
	text-align: left;
	ACKGROUND: url(../../Img/post/menu.gif) 0px 10px no-repeat;
	PADDING-LEFT: 40px;
	PADDING-RIGHT: 40px;
	line-height: 30px;
}
</style>

<div class="rightDiv">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected"><span class="word">商品员工设置</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>

	<div class="input">
		<form method="post" name="goodsUserForm" id="goodsUserForm">

			<div class="tableform">
				<h4>
					查询商品: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="allGoodsBtn" id="allGoodsBtn"><span>商品</span></a>
				</h4>
				<div class="division" id="contractDiv">
					<table width="100%" id="goodsTable">
						<thead>
							<tr>

								<td>商品名称</td>
								<td>商品编号</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:forEach var="goods" items="">
							<tr>
								<input type="hidden" name="goodsUser.goods_id"
									value="${goods.goods_id }" />
								<td>${goods.goods_name}</td>
								<td>${goods.sn}</td>
								<td width="20%"><div align="center">
										<a href='javascript:;' name='delGoodsUser'>删除</a>
									</div></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>


			<div class="tableform">
				<h4>商品服务类型:</h4>
				<div class="division" id="contractDiv">
					<table width="100%" id="qrUserDiv">
						<thead>
							<ul>
								<c:forEach var="serviceCode" items="${serviceCodeList}">
										<li style="width: 150px;">
											<input type="radio" name="goodsUser.service_code"
												value="${serviceCode.service_code}">${serviceCode.service_offer_name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</li>
								</c:forEach>
							</ul>
						</thead>
					</table>
				</div>
			</div>

			<div class="tableform">
				<h4>是否需要操作:</h4>
				<div class="division" id="contractDiv">
					<table width="100%" id="qrUserDiv">
						<thead>
							<tr>
								<th><span class="red">*</span>是否需要确认：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.insure" id="qr_Radio" value="yes"
									checked="checked" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" goodsRule='no' name="goodsRules.insure"
									id="qr_Radio" value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要支付：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.pay" id="pay_Radio" value="yes"
									checked="checked" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" goodsRule='no' name="goodsRules.pay"
									id="pay_Radio" value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要受理：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.accept" id="accept_Radio" value="yes"
									checked="checked" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" goodsRule='no' name="goodsRules.accept"
									id="accept_Radio" value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要发货：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.delivery" id="delvery_Radio" value="yes"
									checked="checked" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" goodsRule='no' name="goodsRules.delivery"
									id="delvery_Radio" value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要查询：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.query" id="query_Radio" value="yes"
									checked="checked" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" goodsRule='no' name="goodsRules.query"
									id="query_Radio" value="no" /> 否</td>
							</tr>

							<tr>
								<th><span class="red">*</span>是否生成订单：</th>
								<td><input type="radio" goodsRule='yes'
									name="goodsRules.create_order" id="create_order_Radio"
									value="yes" checked="checked" /> 是&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" goodsRule='no'
									name="goodsRules.create_order" id="create_order_Radio"
									value="no" /> 否</td>
							</tr>
						</thead>
					</table>
				</div>

			</div>


			<div style="clear:both"></div>

			<div class="tableform" id="qr_table">
				<h4>
					确认工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="qr" id="qrUserBtn"><span>选择确认分组</span></a>
					<a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1 rule_btn" name="insure" id="insure_rule_btn"><span>选择关联规则</span></a>
				</h4>
				<div class="division" id="qrUserDiv">
					<input type="hidden" name="goodsUser.qr_group_id"> <input
						type="hidden" name="goodsUser.qr_user_id">
					<table width="100%">
						<thead>
							<ul>
								<li><input type='radio' name='qrUserRadio'
									id='goodsUser.qr_user_id' groupValue='no' checked="checked"
									value="${cur_userId}">self</li>
								<div id="qr"></div>
							</ul>
						</thead>
					</table>
				</div>
				<div class="division" >
					<table width="100%" >
						<thead id="insure_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>

			<div style="clear:both"></div>


			<div class="tableform" id="pay_table">
				<h4>
					支付工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="pay" id="qrUserBtn"><span>选择支付分组</span></a>
					<a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1 rule_btn" name="pay" id="pay_rule_btn"><span>选择关联规则</span></a>
				</h4>
				<div class="division" id="qrUserDiv">
					<input type="hidden" name="goodsUser.pay_group_id"> <input
						type="hidden" name="goodsUser.pay_user_id">
					<table width="100%">
						<thead>
							<ul>
								<li><input type='radio' name='payUserRadio'
									checked="checked" id='goodsUser.pay_user_id' groupValue='no'
									value="${cur_userId}">self</li>
								<div id="pay"></div>
							</ul>

						</thead>
					</table>
				</div>
				<div class="division rule_div" >
					<table width="100%" >
						<thead id="pay_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>


			<div style="clear:both"></div>

			<div class="tableform" id="accept_table">
				<h4>
					受理工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="accept"
						id="qrUserBtn"><span>选择受理分组</span></a> <a
						href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1 rule_btn" name="accept" id="accept_rule_btn"><span>选择关联规则</span></a>
				</h4>
				<div class="division" id="qrUserDiv">
					<input type="hidden" name="goodsUser.accept_group_id"> <input
						type="hidden" name="goodsUser.accept_user_id">
					<table width="100%">
						<thead>
							<ul>
								<li><input type='radio' name='acceptUserRadio'
									checked="checked" id='goodsUser.accept_user_id' groupValue='no'
									value="${cur_userId}">self</li>
								<div id="accept"></div>
							</ul>

						</thead>
					</table>
				</div>
				<div class="division rule_div" >
					<table width="100%" >
						<thead id="accept_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>


			<div style="clear:both"></div>

			<div class="tableform" id="delvery_table">
				<h4>
					发货工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="delvery"
						id="qrUserBtn"><span>选择发货分组</span></a> <a
						href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1 rule_btn" name="delvery" id="accept_rule_btn"><span>选择关联规则</span></a>
				</h4>
				<div class="division" id="qrUserDiv">
					<input type="hidden" name="goodsUser.ship_group_id"> <input
						type="hidden" name="goodsUser.ship_user_id">
					<table width="100%">
						<thead>
							<ul>
								<li><input type='radio' name='shipUserRadio'
									id='goodsUser.ship_user_id' groupValue='no' checked="checked"
									value="${cur_userId}">self</li>
								<div id="delvery"></div>
							</ul>

						</thead>
					</table>
				</div>
				<div class="division rule_div" >
					<table width="100%">
						<thead id="delvery_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>


			<div style="clear:both"></div>

			<div class="tableform" id="query_table">
				<h4>
					查询工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="query"
						id="qrUserBtn"><span>选择查询分组</span></a>
				</h4>
				<div class="division" id="qrUserDiv">
					<input type="hidden" name="goodsUser.query_group_id"> <input
						type="hidden" name="goodsUser.query_user_id">
					<table width="100%">
						<thead>
							<ul>
								<li><input type='radio' name='queryUserRadio'
									id='goodsUser.query_user_id' groupValue='no' checked="checked"
									value="${cur_userId}">self</li>
								<div id="query"></div>
							</ul>
						</thead>
					</table>
				</div>
			</div>

			<div style="clear:both"></div>

			<div class="input2">
				<div class="submitlist" align="center">
					<table>
						<tr>
							<td>
								<div align="center">
									<input type="button" value=" 保存 " class="submitBtn"
										id="saveGoodsUser" />
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>

		</form>
	</div>
</div>


<div id="showGoodsDlg"></div>
<div id="showGroupDlg"></div>
<div id="insure_rule_dialog" name="insure"></div>
<div id="accept_rule_dialog" name="accept"></div>
<div id="pay_rule_dialog" name="pay"></div>
<div id="delvery_rule_dialog" name="delvery"></div>
