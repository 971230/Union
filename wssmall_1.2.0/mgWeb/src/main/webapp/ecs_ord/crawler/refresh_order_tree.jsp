<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
<div>
	<form method="post">
	<table class="tab_form">
		<tr>
			<h2>订单树刷新</h2>
			<th>请输入订单编号   （","分开）：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="order_ids" name="order_ids"></textarea>
			</td>
			<td>
				<a href="javascript:void(0);" id="refreshOrderTree_a" class="dobtn" style="margin-left:5px;">刷新订单树</a>
				<a href="javascript:void(0);" id="resetOrderId_a" class="dobtn" style="margin-left:5px;">重置订单ID</a>
			</td>
		</tr>
	</table>
	</form>
	<br><br>
	<form action="orderRefreshTreeAttrAction!refreshGoodsInfoByCondition.do" method="post" id="refreshGoodsform">
		<table class="tab_form">
		
			<tr>
				<h1>商品刷新</h2>
			</tr>
			
			<tr>
				<th>创建时间：</th>
	            <td>
	                <input type="text" id="create_start" name="goodsRefreshDTO.create_start" value="${goodsRefreshDTO.create_start}" readonly="readonly" class="dateinput ipt_new"  dataType="date">
	                -
	                <input type="text" id="create_end" name="goodsRefreshDTO.create_end" value="${goodsRefreshDTO.create_end}" readonly="readonly" class="dateinput ipt_new"  dataType="date">
	            </td>
			</tr>
			
			<tr>
				<th>最近时间：</th>
	            <td>
	            	<input type="radio" id="recentlyOneTime"   value="24"  name="goodsRefreshDTO.recently_time">近一小时&nbsp;&nbsp;&nbsp;&nbsp;
	            	<input type="radio" id="recentlyTwoTime"   value="12"  name="goodsRefreshDTO.recently_time">近两小时&nbsp;&nbsp;&nbsp;&nbsp;
	            	<input type="radio" id="recentlyFiveTime"  value="5"  name="goodsRefreshDTO.recently_time">近五小时&nbsp;&nbsp;&nbsp;&nbsp;
	            </td>
			</tr>
			
			<tr>
				<th>请输入商品编号（","分开）：</th>
				<td style="width:180px;"><textarea 
						rows="5" cols="50" id="goods_ids" name="goodsRefreshDTO.goods_ids"></textarea>
				</td>
				<td>
					<a href="javascript:void(0);" id="refreshgoods" class="dobtn" style="margin-left:5px;">刷新商品信息</a>
					<a href="javascript:void(0);" id="cleragoods" class="dobtn" style="margin-left:5px;">重置商品查询条件</a>
				</td>
			</tr>
			
		</table>
	</form>
	<br/>
	<br/>
	<br/>
	<form method="post">
	<table class="tab_form">
		<tr>
			<h2>ESS工号绑定关系刷新</h2>
			<th>请输入订单操作员ID（","分开）：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="orderOperId" name="orderOperId"></textarea>
			</td>
			<td>
				<a href="javascript:void(0);" id="refreshOrderopraid" class="dobtn" style="margin-left:5px;">刷新订单操作员ID</a>
			</td>
		</tr>
	</table>
	</form>
	<br/>
	<br/>
	<br/>
	<form method="post">
	<table class="tab_form">
		<tr>
			<h2>渠道货主映射关系刷新</h2>
			<th>请输入渠道ID（","分开）：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="channel_id" name="channel_id"></textarea>
			</td>
			<td>
				<a href="javascript:void(0);" id="refreshChannelid" class="dobtn" style="margin-left:5px;">刷新渠道ID</a>
			</td>
		</tr>
	</table>
	</form>
</div>
</html>

<script>
	$(function() {

		$("#refreshChannelid").bind("click", function() {
			
			flag = confirm("确认刷新渠道ID吗？");
			if (!flag)return;
			var channel_id = $("#channel_id").val();
			if (channel_id == "") {
				alert("请输入渠道ID！");
				return;
			} else {
				$.ajax({
					type : "post",
					async : false,
					url : "orderRefreshTreeAttrAction!refreshChannelIdInfoCache.do?ajax=yes&orderOperId="+channel_id,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
					}
				});
			}
		});

		$("#refreshOrderopraid").bind("click", function() {
			
			flag = confirm("确认刷新订单操作员ID吗？");
			if (!flag)return;
			var orderOperId = $("#orderOperId").val();
			if (orderOperId == "") {
				alert("请输入订单操作员ID！");
				return;
			} else {
				$.ajax({
					type : "post",
					async : false,
					url : "orderRefreshTreeAttrAction!refreshorderOperIdInfoCache.do?ajax=yes&orderOperId="+orderOperId,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
					}
				});
			}
		});
		
		$("#refreshOrderTree_a").bind("click", function() {
			
			flag = confirm("确认刷新订单树吗？");
			if (!flag)return;
			
			var order_ids = $("#order_ids").val();
			if (order_ids == "") {
				alert("请输入订单编号！");
				return;
			} else {
				$.ajax({
					type : "post",
					async : false,
					url : "orderRefreshTreeAttrAction!refreshOrderTree.do?ajax=yes&order_ids="+order_ids,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
					}
				});
			}
		});	
		
		$("#resetOrderId_a").bind("click", function() {
			$("#order_ids").val("");
		});
		
		$("#cleragoods").bind("click", function() {
			$("#goods_ids").val("");
			$(":radio").attr("checked",null);
			$(":text").attr("value",null);
		});
		
		$("#refreshgoods").bind("click",function(){
			if(check()){
				alert("缺少商品刷新条件!")
				return;
			}
			flag = confirm("确认刷新商品信息吗？");
			if (!flag)return;
			var goodIds = $("#goods_ids").val();
			var options = {
					url :"orderRefreshTreeAttrAction!refreshGoodsInfoByCondition.do?ajax=yes",
					type : "POST",
					async : false,
					dataType : 'json',
					success : function(data) {
						alert(data.msg);
					},
					error : function(error) {
						alert(error.msg);
	 				}
	 		};
			$("#refreshGoodsform").ajaxSubmit(options);
		});

	});
	
	function check(){
		var isNull = true;
		if($.trim($("#create_start").val()).length > 0){
			isNull = false;
		}
		if($.trim($("#create_end").val()).length > 0){
			isNull = false;
		}
		if('undefined' != typeof($('input:radio:checked').val())){
			isNull = false;
		}
		if($.trim($("#goods_ids").val()).length > 0){
			isNull = false;
		}
		return isNull;
	}
</script>

