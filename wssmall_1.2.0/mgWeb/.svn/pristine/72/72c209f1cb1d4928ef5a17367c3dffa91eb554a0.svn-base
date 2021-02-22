<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/activity_modify_logs_esc.js"></script>
<div >
<form method="post" 
	action='activity!listActivityModifyLogs.do' id="queryActivityForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
			    <input type="hidden" name="isSearch" value="true" />
				<tr>
					<th>活动名称：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="ipttxt" style="width: 190px;"
							name="activity.name"
							value="${activity.name}" class="searchipt" />
					</td>
					<th>活动商品：&nbsp;&nbsp;</th>
					<td nowrap="nowrap">
						<input type="text" id="goodsname" style="width:150px" name="activityAttr.pmt_goods_names"
						       value="${activityAttr.pmt_goods_names }" class="ipttxt"  readonly="readonly" required="true"/>
						<input type="hidden" name="activityAttr.pmt_goods_ids" value="${activityAttr.pmt_goods_ids }" />
						<input type="button" id="q_select_goods_btn" value="选择">					
					</td>
					<th>活动商城：&nbsp;&nbsp;</th>
					<td >
						<input type="hidden" id="q_act_org_ids" class="ipttxt" name="activityAttr.act_org_ids" value="${activityAttr.act_org_ids }" />
						<input type="hidden" id="q_ae_org_id_belong" name="" value="" />
						<input type="text" id="q_act_org_names" readonly="readonly" class="ipttxt" readonly="readonly"
						        name="activityAttr.act_org_names" value="${activityAttr.act_org_names }" />
						<input type="button" style=""  value="选择" id="q_select_org_input" name="button">
					</td>
					
				</tr>
				<tr>
					<th>活动类型：&nbsp;&nbsp;</th>
					<td>
					    <html:selectdict name="promotion.pmt_type" curr_val="${promotion.pmt_type }"
					             attr_code="DC_ACT_PMT_TYPE" style="width:195px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
					</td>
					<th>开始时间：&nbsp;&nbsp;</th>
					<td>
					<input type="text" class="dateinput ipttxt" dataType="date"
						style="width: 150px" name="activity.begin_time"
						value="${activity.begin_time}" class="searchipt" /></td>
					<th>结束时间：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="dateinput ipttxt" dataType="date"
						style="width: 150px" name="activity.end_time"
						value="${activity.end_time}" class="searchipt" />
						
						</td>
					<th><input type="hidden" name="activity.rank" id="batchNumInput2" /></th>
					<td>
						<div style="float:right;"></div>
					</td>

				</tr>
			</tbody>
		</table>
	</div>
</form>

	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
				<!-- <input type="submit" style="margin-left:5px;" class="comBtn" value="搜&nbsp;索" type="submit" name="button"> -->
			</li>
	    </ul>
		<div style="clear:both"></div>
	</div>

<div class="grid">
<form method="POST" >
<grid:grid  from="webpage" ajax="yes" formId ="queryActivityForm">

	<grid:header>
	<grid:cell width="50px"><input type="hidden" id="toggleChk" /></grid:cell>
	<grid:cell  >活动编码</grid:cell>
	<grid:cell  >活动名称</grid:cell>
	<grid:cell  >活动类型 </grid:cell> 
	<!--<grid:cell  >活动规则 </grid:cell>--> 
	<grid:cell  >适用商品 </grid:cell> 
	<grid:cell  >适用商城 </grid:cell> 
	<grid:cell  >最低消费 </grid:cell> 
	<grid:cell  >最高消费 </grid:cell> 
	<grid:cell   >生效时间</grid:cell> 
	<grid:cell   >失效时间</grid:cell> 
	<grid:cell   >修改时间</grid:cell> 
	<grid:cell   >修改人</grid:cell> 
	</grid:header>

  <grid:body item="pmt">
  		<grid:cell><input type="radio" name="activity_id" value="${pmt.id }" statustxt="${pmt.pub_status}" isenable="${pmt.enable}" org_ids="${pmt.org_ids }" /></grid:cell>
  		<grid:cell>${pmt.pmt_code}</grid:cell> 
  		<grid:cell>${pmt.name}</grid:cell> 
  		<grid:cell>
  		    <input type="hidden" name="pmt_type" value="${pmt.pmt_type }" />
  			${pmt.pmt_type_name }
  		</grid:cell> 
  		<!--<grid:cell>${pmt.pmt_describe}</grid:cell>-->
  		<grid:cell>
			<span title="${pmt.goods_names }">${pmt.goods_names_view}</span>
  		</grid:cell>
  		<grid:cell>
  		    <input type="hidden" name="org_ids" value="${pmt.org_ids }" />
	        <span title="${pmt.org_names }">${pmt.org_names_view }</span>
  		</grid:cell>
  		<grid:cell>${pmt.order_money_from}</grid:cell> 
  		<grid:cell>${pmt.order_money_to}</grid:cell> 
        <grid:cell> ${fn:substring(pmt.begin_time , 0 , 10)} </grid:cell>
        <grid:cell>${fn:substring(pmt.end_time , 0 , 10)}</grid:cell>
		<grid:cell>${fn:substring(pmt.status_date , 0 , 19)}</grid:cell>
		<grid:cell>${pmt.oper_name}</grid:cell>
  </grid:body>
  
</grid:grid>  
</form>	
</div>
<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="q_activity_goods_dialog" ></div>
<div id="q_activity_orgs_dialog"></div>
<div id="activitySelectShopDialog"></div>
</div>
<script type="text/javascript">
$(function(){
	ActivityList.init();
	addGoods.init();
});
</script>
