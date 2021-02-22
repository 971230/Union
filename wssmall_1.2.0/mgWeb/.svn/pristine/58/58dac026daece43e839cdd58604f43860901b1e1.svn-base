<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="common_form_e" > 
<div class="formWarp">
<input type="hidden" name="order_id" value="${order_id }" />
<input type="hidden" name="orderId" value="${order_id }" />
  <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5000" class="closeArrow"></a>换货信息
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_5000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th>订单编号：</th>
            <td>${order.order_id }</td>
            <th>下单日期：</th>
            <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
            <th>订单总价：</th>
            <td><fmt:formatNumber value="${order.order_amount }" type="currency" pattern="￥.00"/></td>
          </tr>
          <tr>
          	<th>已收金额：</th>
            <td><fmt:formatNumber value="${order.paymoney}" type="currency" pattern="￥.00"/></td>
            <th>折旧费用：</th>
            <td><fmt:formatNumber value="${depreciation_price}" type="currency" pattern="￥.00"/></td>
            <th>物流费用：</th>
            <td><fmt:formatNumber value="${ship_price}" type="currency" pattern="￥.00"/></td>
          </tr>
          <tr>
          	<th>应退款金额：</th>
            <td><fmt:formatNumber value="${returned_price}" type="currency" pattern="￥.00"/></td>
            <th>实退款金额：</th>
            <td><fmt:formatNumber value="${refundAmount}" type="currency" pattern="￥.00"/></td>
            <th>换货原因：</th>
            <td>
            	<select class="ipttxt" style="width: 136px;" name="delivery.reason">
			      <option value="质量原因">质量原因</option>
			      <option value="无理由">无理由</option>
			      <option value="其他">其他</option>
			    </select>
            </td>
          </tr>
          <tr>
          	<th>物流公司：</th>
            <td>
            	<select  class="ipttxt" style="width: 136px;" name="logi_id_name"  onchange="logiChange(this);">
				<c:forEach items="${logiList}" var="logi" >
					<option value="${logi.id }|${logi.name }" label="${logi.name }">${logi.name }</option>
				</c:forEach>
			   </select>
            </td>
            <th>物流单号：</th>
            <td>
            	<input type="text" class="formIpt" style="width: 136px;" name="delivery.logi_no" autocomplete="off">
            </td>
            <th>&nbsp;</th>
			<td>&nbsp;</td>
          </tr>
          <script type="text/javascript">
			function logiChange(obj){
				if(obj.value=='0|--其它--'){
					$("#flowInfo").show();
				}else{
					$("#flowInfo").hide();
				}
			}
		 </script>
		<tr id="flowInfo" style="display: none;">
			<th>物流公司名称:</th>
			<td><input type="text" class="formIpt" style="width: 136px;" value="" name="flow.logi_name" autocomplete="off"></td>
			<th>物流描述:</th>
			<td colspan="3">
				<input id="delivery_flow_desc" type="text" class="formIpt" style="width: 90%;" name="flow.description" autocomplete="off" />
			</td>
		</tr>
		
		<tr>
			<th>收货人姓名:</th>
			<td><input type="text" class="formIpt" style="width: 136px;" value="${order.ship_name}" name="delivery.ship_name" autocomplete="off"></td>
			<th>电话:</th>
			<td><input type="text" class="formIpt" style="width: 136px;" value="${order.ship_tel}" name="delivery.ship_tel" autocomplete="off"></td>
			<th>手机:</th>
			<td><input type="text" class="formIpt" style="width: 136px;" value="${order.ship_mobile}" name="delivery.ship_mobile" autocomplete="off"></td>
		</tr>
		<tr>
			<th>邮政编码:</th>
			<td><input type="text" class="formIpt" style="width: 136px;" value="${order.ship_zip}" name="delivery.ship_zip" autocomplete="off"></td>
			<th>地址:</th>
			<td colspan="3"><input type="text" class="formIpt" style="width: 90%;" value="${order.ship_addr}" name="delivery.ship_addr" autocomplete="off"></td>
		</tr>
		
        </table>
    </div>
</div> 

<div class="formWarp">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5001" class="closeArrow"></a>原商品明细<div class="dobtnDiv"></div></div>
<div id="order_tag_5001" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>商品名称</th>
        <th>购买数量</th>
        <th>已发货数</th>
        <th>退货数量</th>
      </tr>
      <tbody>
      <c:forEach items="${orderItemList }" var="item">
	      <tr>
	        <td>
	        	${ item.name}
	        	<input type="hidden" name="goods_idArray" value="${ item.goods_id}"/>
				<input type="hidden" name="goods_nameArray" value="${ item.name}"/>
				<input type="hidden" name="product_idArray" value="${ item.spec_id}"/>
				 <input type="hidden" name="goods_snArray" value="${ item.sn}"/>
				 <input type="hidden" name="itemid_idArray" value="${ item.item_id}"/>
				 <input type="hidden" name="order_applyArray" value="${ item.order_apply_id}"/>
	        </td>
	        <td>
	        	${ item.num}
	        </td>
	        <td>
	        	${ item.ship_num}
	        </td>
	        <td>
	        	<input type="text" class="formIpt"  style="width: 50px;" value="${item.back_num}" name="numArray" autocomplete="off" readonly="readonly">
	        </td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>

<div class="formWarp">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5008" class="closeArrow"></a>目标商品明细<div class="dobtnDiv"></div></div>
<div id="order_tag_5008" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>商品名称</th>
        <th>购买数量</th>
      </tr>
      <tbody>
      <c:forEach items="${orderApplyItemList }" var="oai">
	     <c:if test="${oai.item_type==3 }">
	      <tr>
	        <td>
	        	${ oai.itemName}
	        </td>
	        <td>
	        	${ oai.returned_num}
	        </td>
	      </tr>
	      </c:if>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>

<div class="formWarp">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5009" class="closeArrow"></a>环节信息<div class="dobtnDiv"></div></div>
    <div id="order_tag_5009" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
    	<c:if test="${refundAmount>0}">
          <tr>
          	<th>选择下一环节:</th>
            <td colspan="5">
            	<input type="hidden" name="flow_def_id" value="${flowDef.flow_def_id }" /> 
            	<select id="order_flow_select" name="flow_def_id_disable" disabled="disabled">
            		<c:forEach items="${flowDefList }" var="flow">
            			<option value="${flow.flow_def_id }" ${flowDef.flow_type==flow.flow_type?'selected':'' }>${flow.flow_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th style="display: none;">订单处理组:</th>
            <td style="display: none;">
            	<select id="order_group_select" name="flow_group_id">
            		<c:forEach items="${orderGroupList }" var="og">
            			<option value="${og.group_id }"  ${og.group_id==order.pay_group_id?'selected':'' }>${og.group_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th style="display: none;">订单处理人:</th>
            <td style="display: none;">
            	<p style="height:26px;">
	            	<input type="hidden"  name="flow_user_id" value="${user.userid }" />
	            	<input name="flow_user_name" type="text" class="formIpt" value="${user.username }" readonly="readonly" />
	            	<a href="javascript:void(0);" name="user_clear_btn" class="dobtn" style="margin-right:5px;">清空</a>
	            	<a href="javascript:void(0);" name="user_add_btn" class="dobtn" style="margin-right:5px;">+</a>
            	</p>
            	<span></span>
            </td>
          </tr>
          </c:if>
          <tr>
          	<th>处理意见：</th>
          	<td colspan="5">
          		<input type="radio" name="flag_status" value="1" checked="checked" />通过&nbsp;&nbsp;<input type="radio" name="flag_status" value="2" />不通过&nbsp;&nbsp;
          	</td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="5">
            	<textarea  style="width: 95%;" name="hint" type="textarea"></textarea>
            </td>
          </tr>
          <tr>
	         <th colspan="6" style="text-align: center;">
	         	<a href="javascript:void(0);" id="allowChangeBtn" class="dobtn" style="margin-right:5px;">换货 </a>
	         </th>
	       </tr>
        </table>
    </div>
</div> 

</form>
<div id="select_admin_dialog"></div>
<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"select_admin_dialog",modal:true,title:"选择处理用户", height:"600px",width:"600px"});
	});
</script>