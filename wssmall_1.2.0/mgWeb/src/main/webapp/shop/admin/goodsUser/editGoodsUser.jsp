<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="goodsUser/editGoodsUser.js"></script>

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
					<li id="show_click_1" class="selected"><span class="word">商品员工修改</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>

	<div class="input">
		<form method="post" name="goodsUserForm" id="goodsUserForm">

			<div class="tableform">
				<h4>基本信息:</h4>
				<div class="division" id="contractDiv">
					<table width="100%" id="goodsTable">
						<thead>
							<tr>
								<td>商品名称</td>
								<td>商品服务类型</td>
								<td>员工类型</td>
								<td></td>
							</tr>
						</thead>

						<tr>
							<input type="hidden" name="goodsUser.goodsIds"
								value="${goodsUser.goods_id }" />
							<input type="hidden" name="goodsUser.user_type"
								value="${goodsUser.user_type}" />
							<input type="hidden" name="goodsUser.service_code"
								value="${goodsUser.service_code}" />
							<td>${goodsUser.goods_name}</td>
							<td>${goodsUser.service_code_name}</td>
							<td><c:if test="${goodsUser.user_type==0}">${typeMap.manager}</c:if>
								<c:if test="${goodsUser.user_type==1}">${typeMap.admin}</c:if> <c:if
									test="${goodsUser.user_type==2}">${typeMap.second_partner}</c:if>
								<c:if test="${goodsUser.user_type==3}">${typeMap.partner}</c:if>
								<c:if test="${goodsUser.user_type==4}">${typeMap.supplier}</c:if>
								<c:if test="${goodsUser.user_type==5}">${typeMap.supplier_employee}</c:if>
							</td>
						</tr>

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
								<td><input type="radio" name="goodsRules.insure"
									goodsRule='yes' id="qr_Radio"
									<c:if test="${goodsRules.insure == 'yes' }"> checked="checked" </c:if>
									value="yes" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" name="goodsRules.insure" goodsRule='no'
									id="qr_Radio"
									<c:if test="${goodsRules.insure == 'no' }"> checked="checked" </c:if>
									value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要支付：</th>
								<td><input type="radio" name="goodsRules.pay"
									goodsRule='yes' id="pay_Radio"
									<c:if test="${goodsRules.pay == 'yes' }"> checked=true </c:if>
									value="yes" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" name="goodsRules.pay" goodsRule='no'
									id="pay_Radio"
									<c:if test="${goodsRules.pay == 'no' }"> checked="true" </c:if>
									value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要受理：</th>
								<td><input type="radio" name="goodsRules.accept"
									goodsRule='yes' id="accept_Radio"
									<c:if test="${goodsRules.accept == 'yes' }"> checked=true </c:if>
									value="yes" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" name="goodsRules.accept" goodsRule='no'
									id="accept_Radio"
									<c:if test="${goodsRules.accept == 'no' }"> checked="true </c:if>
									value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要发货：</th>
								<td><input type="radio" name="goodsRules.delivery"
									goodsRule='yes' id="delvery_Radio"
									<c:if test="${goodsRules.delivery == 'yes' }"> checked=true </c:if>
									value="yes" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" name="goodsRules.delivery" goodsRule='no'
									id="delvery_Radio"
									<c:if test="${goodsRules.delivery == 'no' }"> checked=true </c:if>
									value="no" /> 否</td>
							</tr>
							<tr>
								<th><span class="red">*</span>是否需要查询：</th>
								<td><input type="radio" name="goodsRules.query"
									goodsRule='yes' id="query_Radio"
									<c:if test="${goodsRules.query == 'yes' }"> checked=true </c:if>
									value="yes" /> 是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" name="goodsRules.query" goodsRule='no'
									id="query_Radio"
									<c:if test="${goodsRules.query == 'no' }"> checked=true </c:if>
									value="no" /> 否</td>
							</tr>
						</thead>
					</table>
				</div>

			</div>


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
									id='goodsUser.qr_user_id' groupValue='no'
									<c:if test="${goodsUser.qr_user_id !=null&&goodsUser.qr_user_id !='' }"> checked=true </c:if>
									value="${cur_userId}">self</li>
								<div id="qr">
									<c:if
										test="${goodsUser.qr_group_id!=''&&goodsUser.qr_group_id!=null}">
										<li><input type='radio' name='qrUserRadio'
											value='${goodsUser.qr_group_id}' id='goodsUser.qr_group_id'
											checked="ckecked" groupValue='yes'>${goodsUser.qr_group_name}</li>
									</c:if>

								</div>
							</ul>
						</thead>
					</table>
				</div>
				<div class="division rule_div">
					<table width="100%"">
						<thead id="insure_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:forEach items="${ruleList}" var="rule">
							<c:if test="${rule.rule_type == 'insure'}">
								<tr>
									<td>${rule.rule_name}</td>
									<td>确认类规则</td>
									<td>${rule.rule_code}</td>
									<td><a href="javascript:void(0);" class="delLinkRule">删除
									</a></td>
									<input type="hidden" value="${rule.rule_id}" name="rule_ids"
										class='hidden_rule_id' />
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</div>




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
									<c:if test="${goodsUser.pay_user_id != null&&goodsUser.pay_user_id !='' }"> checked=true </c:if>
									id='goodsUser.pay_user_id' groupValue='no'
									value="${cur_userId}">self</li>
								<div id="pay">
									<c:if
										test="${goodsUser.pay_group_id!=''&&goodsUser.pay_group_id!=null}">
										<li><input type='radio' name='payUserRadio'
											value='${goodsUser.pay_group_id}' id='goodsUser.pay_group_id'
											checked="ckecked" groupValue='yes'>${goodsUser.pay_group_name}</li>
									</c:if>
								</div>
							</ul>
						</thead>
					</table>
				</div>
				<div class="division rule_div">
					<table width="100%"">
						<thead id="pay_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:forEach items="${ruleList}" var="rule">
							<c:if test="${rule.rule_type == 'pay'}">
								<tr>
									<td>${rule.rule_name}</td>
									<td>支付类规则</td>
									<td>${rule.rule_code}</td>
									<td><a href="javascript:void(0);" class="delLinkRule">删除
									</a></td>
									<input type="hidden" value="${rule.rule_id}" name="rule_ids"
										class='hidden_rule_id' />
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</div>

			<div class="tableform" id="accept_table">
				<h4>
					受理工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="accept"
						id="qrUserBtn"><span>选择受理分组</span></a><a
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
									<c:if test="${goodsUser.accept_user_id != null&&goodsUser.accept_user_id !=''}"> checked=true </c:if>
									id='goodsUser.accept_user_id' groupValue='no'
									value="${cur_userId}">self</li>
								<div id="accept">
									<c:if
										test="${goodsUser.accept_group_id!=''&&goodsUser.accept_group_id!=null}">
										<li><input type='radio' name='accpetUserRadio'
											value='${goodsUser.accept_group_id}'
											id='goodsUser.accept_group_id' checked="ckecked"
											groupValue='yes'>${goodsUser.accept_group_name}</li>
									</c:if>
								</div>
							</ul>
							</ul>
						</thead>
					</table>
				</div>
				<div class="division rule_div">
					<table width="100%"">
						<thead id="accept_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:forEach items="${ruleList}" var="rule">
							<c:if test="${rule.rule_type == 'accept'}">
								<tr>
									<td>${rule.rule_name}</td>
									<td>受理类规则</td>
									<td>${rule.rule_code}</td>
									<td><a href="javascript:void(0);" class="delLinkRule">删除
									</a></td>
									<input type="hidden" value="${rule.rule_id}" name="rule_ids"
										class='hidden_rule_id' />
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</div>

			<div class="tableform" id="delvery_table">
				<h4>
					发货工号设置: <a href="javascript:void(0)" style="margin-right:10px;"
						class="graybtn1" name="qrUserBtn" group_type="delvery"
						id="qrUserBtn"><span>选择发货分组</span></a><a
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
									id='goodsUser.ship_user_id' groupValue='no'
									<c:if test="${goodsUser.ship_user_id != null&&goodsUser.ship_user_id !='' }"> checked=true </c:if>
									value="${cur_userId}">self</li>
								<div id="delvery">
									<c:if
										test="${goodsUser.ship_group_id!=''&&goodsUser.ship_group_id!=null}">
										<li><input type='radio' name='shipUserRadio'
											value='${goodsUser.ship_group_id}'
											id='goodsUser.ship_group_id' checked="ckecked"
											groupValue='yes'>${goodsUser.ship_group_name}</li>
									</c:if>
								</div>
							</ul>
						</thead>
					</table>
				</div>
				<div class="division rule_div">
					<table width="100%"">
						<thead id="delvery_rule_th">
							<tr>
								<td>规则名称</td>
								<td>规则类型</td>
								<td>规则编码</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:forEach items="${ruleList}" var="rule">
							<c:if test="${rule.rule_type == 'delvery'}">
								<tr>
									<td>${rule.rule_name}</td>
									<td>发货类规则</td>
									<td>${rule.rule_code}</td>
									<td><a href="javascript:void(0);" class="delLinkRule">删除
									</a></td>
									<input type="hidden" value="${rule.rule_id}" name="rule_ids"
										class='hidden_rule_id' />
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</div>


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
									id='goodsUser.query_user_id' groupValue='no'
									<c:if test="${goodsUser.query_user_id != null&&goodsUser.query_user_id !='' }"> checked=true </c:if>
									value="${cur_userId}">self</li>
								<div id="query">
									<c:if
										test="${goodsUser.query_group_id!=''&&goodsUser.query_group_id!=null}">
										<li><input type='radio' name='queryUserRadio'
											value='${goodsUser.query_group_id}'
											id='goodsUser.query_group_id' groupValue='yes'
											checked="ckecked">${goodsUser.query_group_name}</li>
									</c:if>
								</div>
							</ul>
						</thead>
					</table>
				</div>
			</div>
			<div style="clear:both"></div>
			
			
			<div class="input2">
				<div class="submitlist" align="center" style="display: ;">
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


<div id="showGroupDlg"></div>
<div id="insure_rule_dialog" name="insure"></div>
<div id="accept_rule_dialog" name="accept"></div>
<div id="pay_rule_dialog" name="pay"></div>
<div id="delvery_rule_dialog" name="delvery"></div>