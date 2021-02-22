<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<script type="text/javascript">

	function onJsonStrStyleChange(){
		if($("#busiSelect").val()=='ddtd'){
			$("#jsonStrStyle").val('总部订单号@订单来源,总部订单号@订单来源,....');
		}else{
			$("#jsonStrStyle").val('');
		}
	}
	$(function(){
		$("#buttonSms").click(function(){alert(123);
			var url = "/wss_login!sendSmsToUser.do";
			$.ajax({
				url : url,
				type : "POST",
				data:{"user_name":"admin"},
				dataType : 'json',
				success : function(data) {
					alert(321);
				},error : function(a,b) {
					alert(a+b);
				}
			});
		})
	})
</script>

<div>
	<form action="ropAction!ROPTest.do" method="post" id="ropTestForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>请求地址</th>
						<td>
						
						 	<input type="text" class="ipt_new" style="width:400px;" value="http://localhost:8080/servlet/ZBMallOrderServlet" name="url">
						 <!-- 
							<input type="text" class="ipt_new" style="width:400px;" value="http://localhost:8001/router" name="url">
							-->
							<!--<input type="text" class="ipt_new" style="width:400px;" value="http://localhost:8080/servlet/CommonServlet" name="url">-->
						</td>
					</tr>
					<tr>
						<th>返回业务</th>
						<td>
							<select style="width:300px" id = "busiSelect" name="bussType" class="ipttxt"  onchange="onJsonStrStyleChange()">
								<option value="">------------请选择------------</option>
								<option value="">------------总部返回------------</option>
								<option value="zte.net.ecsord.params.zb.req.SynchronousOrderGDRequest">J102订单信息同步接口（总部）_老系统编码：synOrderInfo</option>
								<option value="zte.net.ecsord.params.zb.req.NotifyStringGDRequest">J104将货品信息反馈给省分能力平台（总部）_老系统编码：/service/forward_synProductInfo_ZB</option>
								<option value="zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest" >J105开户完成接口（总部）_老系统编码store_synAccount_ZB</option>
								<option value="zte.net.ecsord.params.zb.req.NotifyDeliveryGDRequest">J109发货通知接口（总部）_老系统编码forward_synOrderPostInfo_ZB</option>
								<option value="zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToSystemRequest">J112订单异常通知（总部）_老系统编码forward_synExceptionNotice_ZB</option>
								<option value="zte.net.ecsord.params.zb.req.ReplacementNoticeRequest">J113换货通知接口（总部）</option>
								<option value="zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest">J114状态同步接口（总部）_老系统编码forward_synStatus_ZB</option>
								<option value="">------------WMS返回------------</option>
								<option value="zte.net.ecsord.params.wms.req.SynSerialNumWMSReq">J32同步串号数据接口 </option>
								<option value="zte.net.ecsord.params.wms.req.SynCardInfoWMSReq">J34 同步写卡数据接口 </option>
								<option value="zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq">J36订单状态通知接口</option>
								<option value="zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq">J37 订单信息变更</option>
								<option value="zte.net.ecsord.params.wms.req.SynchronousCheckFromWMSReq">J38 同步稽核数据接口</option>
								<option value="">------------南都返回------------</option>
								<option value="wl_oper_to_order">J82 订单信息变更</option>
								<option value="wl_syn_status">J83 订单状态通知接口</option>
								<option value="wl_order_deal_sucess">J84 处理完成通知</option>
								<option value="">------------顺丰接口联调------------</option>
								<option value="wl_result_to_order">J93路由推送</option>
								<option value="sf_artificial_select">J94人工筛单信息</option>
								<option value="zte.net.ecsord.params.sf.req.NotifyOrderInfoSFRequset">J91顺丰下单</option>
								<option value="zte.net.ecsord.params.sf.req.RouteServiceRequest">J95顺丰物流路由查询</option>
								<option value="zte.net.ecsord.params.sf.req.OrderSearchServiceRequest">J96顺丰订单信息查询</option>
								<option value="">------------订单处理------------</option>
								<option value="ddgj" >订单归集(新商城)</option>
								<option value="ddgj_zb" selected="selected">订单归集(总部)</option>
								<option value="ddtd_tmfx" >订单归集(天猫分销)</option>
								<option value="ddtd_tmtj" >订单归集(天猫天机)</option>
								<option value="ddtd" >订单退单(总部)</option>
								<option value="">------------沃云购返回------------</option>
								<option value="wyg_syn_order_status">J22退单申请【状态通知接口】</option>
							</select>
						</td>
					</tr>
					<tr>
					    <th>报文样式</th>
						<td>
							<input type="text" class="ipt_new" style="width:600px;" id="jsonStrStyle"  ></input>
							
						</td>
					</tr>
					<tr>
						<th>请求报文:</th>
						<td>
						<!--  
							<textarea style="width: 700px; height: 400px;" value="这是测试ROP报文" name="jsonStr" rows="6" cols="80" class="">{"order_req":{"source_system":"10008","receive_system":"10011","serial_no":"c1317bcc-7b1a-493b-a1af-3202a6a08b48","time":"20141201155346","order_id":"WCS20141202<%=String.valueOf(System.currentTimeMillis())%>","order_city":"440100","order_type":"1","status":"TRADE_CLOSED_BY_TAOBAO","platform_status":"TRADE_CLOSED_BY_TAOBAO","create_time":"20140816075054","order_amount":4999000,"order_heavy":"0","order_disacount":0,"pay_money":4999000,"source_from_system":"10008","source_from":"10031","ship_area":"","order_provinc_code":"440000","order_city_code":"440100","alipay_id":"无账号","uid":"510000","is_deal":"0","pay_type":"HDFK","payment_type":"XJZF","channel_id":"detecon","chanel_name":"沃百富","channel_mark":"13","ssyh":"detecon","order_comment":"沃百富用户赠送 优志魔术棒移动电源 1个；渠道标记：自营渠道","shipping_type":"XJ","shipping_time":"NOLIMIT","ship_city":"440100","ship_country":"440106","ship_name":"郭瑾","ship_addr":"测试","ship_tel":"18602031658","ship_phone":"18602031658","name":"郭瑾","uname":"郭瑾","abnormal_status":"正常","delivery_status":"未发货","shipping_amount":0,"n_shipping_amount":0,"shipping_quick":"01","is_to4g":"0","order_list":[{"prod_offer_code":"201403215011570","prod_offer_name":"三星SM-N9002黑（16G双卡）WCDMA(3G)-286元基本套餐A286元3G基本套餐36个月","prod_offer_type":"20002","offer_disacount_price":0,"offer_coupon_price":4999000,"prod_offer_num":1,"prod_offer_heavy":"0","certi_type":"SFZ18","cust_name":"郭瑾","certi_num":"210103198312310324","invoice_print_type":"3","inventory_code":"0","inventory_name":"广州互联网省级集中仓","offer_price":4999000,"good_no_deposit":360,"offer_eff_type":"HALF","package_sale":"YES","is_change":"0","sku_param":[{"param_code":"acc_nbr","param_name":"用户号码","param_value_code":"","param_value":"18520782644"},{"param_code":"is_old","param_name":"是否老用户","param_value_code":"","param_value":"0"},{"param_code":"family_no","param_name":"亲情号码","param_value_code":"","param_value":""},{"param_code":"if_love_no","param_name":"是否情侣号","param_value_code":"","param_value":"0"},{"param_code":"net_type","param_name":"网别","param_value_code":"","param_value":"3G"},{"param_code":"is_goodno","param_name":"靓号","param_value_code":"","param_value":"1"},{"param_code":"bill_type","param_name":"账户付费方式","param_value_code":"","param_value":"10"},{"param_code":"card_type","param_name":"卡类型","param_value_code":"","param_value":"none"},{"param_code":"guarantor","param_name":"担保人","param_value_code":"","param_value":"无"},{"param_code":"bill_mail_type","param_name":"账单寄送方式","param_value_code":"","param_value":"00"}]}]}}</textarea> 
						-->
							<textarea style="width: 700px; height: 400px;" value="这是测试ROP报文" name="jsonStr" rows="6" cols="80" class=""></textarea> 
							<input type="submit" style="margin-right: 10px;" class="comBtn" value="测&nbsp;试" id="button" name="button">
							<input type="button" style="margin-right: 10px;" class="comBtn" value="测&nbsp;试" id="buttonSms" name="buttonSms">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>