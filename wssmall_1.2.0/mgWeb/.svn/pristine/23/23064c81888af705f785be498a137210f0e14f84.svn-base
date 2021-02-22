<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
ul.tab li {
	min-width: 70px;
}

.grid ul li {
	float: center;
	margin-top: 5px;
	text-align: left;
}

.grid table tr:hover {
	cursor: pointer;
	background: none;
}

.grid table {
	border-collapse: collapse;
	border-top: 1px solid #cdcdcd;
	border-right: 1px solid #cdcdcd;
	font-family: Arial, Helvetica, sans-serif;
}

.tr_has_border td {
	border: 1px solid #cdcdcd;
}
</style>

<div class="input">
	<form method="post" name="theForm" action="${actionName }" id="theForm"
		class="validate">
		<div style="display: block;" class="goods_input">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="">货品信息</li>
					<li tabid="0" class="">货品参数</li>
					<li tabid="0" class="active">合约套餐关系</li>
				</ul>
			</div>
			<div class="tab-page">
				<div class='clear'></div>
				<div class="grid">
				<table width="100%" cellspacing="0" cellpadding="3">
					<colgroup>
						<col class="span-4 ColColorGray">
						<col class="span-2 ColColorOrange">
						<col class="textleft">
					</colgroup>
					<thead>
						<tr>
							<th>套餐月费(元)</th>
							<th>46</th>
							<th>66</th>
							<th>96</th>
							<th>126</th>
							<th>156</th>
							<th>186</th>
							<th>226</th>
							<th>286</th>
						</tr>
						<tr>
							<td>月送费金额(元)</td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
						</tr>
						<tr>
							<td>协议期送费总额(元)</td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="0" autocomplete="off"></td>
						</tr>
						
					</thead>
					<tbody>
						<tr class="tr_has_border">
							<td>选择套餐</td>
							<td>
								<ul>
									<li><input type="checkbox" value="" name="lvidArray">A-46元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-46元</li>
									<li><input type="checkbox" value="" name="lvidArray">C-46元</li>
									<li><input type="checkbox" value="" name="lvidArray">D-46元</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-66元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-66元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-66元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-66元套餐</li>
									<li><input type="checkbox" value="" name="lvidArray">A-66元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-66元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-66元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-66元套餐</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-96元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-96元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-96元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-96元套餐</li>
									<li><input type="checkbox" value="" name="lvidArray">A-96元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-96元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-96元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-96元套餐</li>
									<li><input type="checkbox" value="" name="lvidArray">A-96元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-96元套餐(流量王微信沃)</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-126元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-126元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-126元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-126元套餐</li>
									<li><input type="checkbox" value="" name="lvidArray">A-126元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-126元套餐(流量王微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-126元套餐(微信沃)</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-156元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-156元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-156元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-1566元套餐</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">C-186元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-186元套餐</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-226元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-226元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-226元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-226元套餐</li>
								</ul>
							</td>
							<td><ul>
									<li><input type="checkbox" value="" name="lvidArray">A-286元</li>
									<li><input type="checkbox" value="" name="lvidArray">B-286元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">C-286元套餐(微信沃)</li>
									<li><input type="checkbox" value="" name="lvidArray">D-286元套餐</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>合约期(月)</td>
							<td><input type="text" size="5" name="" class="top_up_ipt" value="12" autocomplete="off"></td>
							<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
						</tr>
					</tbody>
				</table>
				</div>
				<div class="inputBox">
					<table>
						<tr>
							<td><span style="padding-left:50px;"><a
									class="greenbtnbig" href="javascript:void(0)">货品保存</a></span> <span
								style="padding-left:100px;"><a class="greenbtnbig"
									href="javascript:void(0)">货品发布</a></span></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</div>
